package com.avst.equipmentcontrol.common.util.ff;

import com.avst.equipmentcontrol.common.util.FileUtil;
import com.avst.equipmentcontrol.common.util.LogUtil;
import com.avst.equipmentcontrol.common.util.properties.PropertiesListenerConfig;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.util.Collections;
import java.util.Date;

/**
 * 视频转换
 * @author wb
 *
 */
public class VideoChangeThread extends Thread{

	private String inputurl;
	private String outputurl;

	private int maxzhuanmanum=5;//一次线程最大可重复转码次数
	private int usezhuanmanum=0;//当前已经使用了几次转码

	private boolean zhunamabool=false;//是否转码成功


	public VideoChangeThread(String inputurl, String outputurl){
		
		this.inputurl=inputurl;
		this.outputurl=outputurl;
	}
	
	@Override
	public synchronized void start() {
		
		super.start();
	}
	

	@Override
	public void run() {

		System.out.println("转码开始-----"+(new Date()).getTime());

		while(!zhunamabool){
			try {
				if(StringUtils.isNotEmpty(inputurl)&&StringUtils.isNotEmpty(outputurl)){


					String changetype= PropertiesListenerConfig.getProperty("changetype");
					if(null==changetype || changetype.trim().equals("")){
						LogUtil.intoLog(4,this.getClass(),"转video文件的属性配置参数异常，----changetype:"+changetype);
						return ;
					}
					FFThreadCache.setVideoChangeThread(outputurl,this);

					//检测文件是否是MP3文件，不是的话就需要转成MP3文件，之后再转WAV文件
					if(inputurl.endsWith(changetype)){
						System.out.println(inputurl+":inputurl 不需要转"+changetype);
					}else{
						System.out.println(inputurl+":inputurl 需要转"+changetype+"以适应播放");
						boolean bool=zhuanma();//转码
						while(!bool&&usezhuanmanum<maxzhuanmanum){//如果转码失败，并且转码次数小于转码次数准许再次转码
							zhuanma();
						}
						if(bool){//转码成功，删除源文件，释放空间
							boolean filebool=false;
							try {
								File file=new File(inputurl);
								filebool=file.delete();
								file=null;
							} catch (Exception e) {
								e.printStackTrace();
							}
							LogUtil.intoLog(3,this.getClass(),bool+"：bool转码成功,删除源文件，filebool："+filebool);
							break;
						}

						LogUtil.intoLog(3,this.getClass(),bool+"：bool转码是否成功，转码次数usezhuanmanum："+usezhuanmanum);

					}
				}else{
					System.out.println(inputurl+":inputurl,有一个为空,outputurl:"+outputurl);
				}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		LogUtil.intoLog(3,this.getClass(),"还需要重新转码");

		}

		if(StringUtils.isNotEmpty(outputurl)){
			FFThreadCache.delVideoChangeThread(outputurl);
		}

		System.out.println("转码结束-----"+(new Date()).getTime());
		
	}

	private boolean zhuanma(){

		FFMpegUtil ffMpegUtil=new FFMpegUtil();
		ffMpegUtil.videoChange(inputurl, outputurl);

		usezhuanmanum++;

		System.out.println("转码已经出来了，准备检测文件是否存在和大小");
		File file=new File(outputurl);
		String leastvideolength= PropertiesListenerConfig.getProperty("leastvideolength");
		int leastvideolength_=Integer.parseInt(leastvideolength);
		if(file.exists()&&file.length() >= leastvideolength_){
			System.out.println(outputurl+":文件已正常转码---");
			return true;
		}else{
			LogUtil.intoLog(3,this.getClass(),"转码异常，可能需要再次转码，----outputurl:"+outputurl);
		}
		return false;
	}
	
	
}
