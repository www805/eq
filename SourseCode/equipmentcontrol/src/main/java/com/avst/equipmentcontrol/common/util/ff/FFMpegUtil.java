package com.avst.equipmentcontrol.common.util.ff;

import com.avst.equipmentcontrol.common.util.LogUtil;
import com.avst.equipmentcontrol.common.util.properties.PropertiesListenerConfig;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;




public class FFMpegUtil implements IStringGetter {



/**
 * 转video文件
 * @param inputurl
 * @param outputurl
 */
public void videoChange(String inputurl, String outputurl) {

	String ffmpegpath=PropertiesListenerConfig.getProperty("ffmpegpath");
	String changetype=PropertiesListenerConfig.getProperty("changetype");
	if(null==ffmpegpath || ffmpegpath.trim().equals("")
		||null==changetype || changetype.trim().equals("")){
		LogUtil.intoLog(4,this.getClass(),"转video文件的属性配置参数异常，ffmpegpath："+ffmpegpath+"-----changetype:"+changetype);
		return ;
	}

	cmd.clear();
	cmd.add(ffmpegpath+"ffmpeg");
	cmd.add("-i");
	cmd.add(inputurl);
	cmd.add("-acodec");
	cmd.add("copy");
	cmd.add("-vcodec");
	cmd.add("copy");
	cmd.add("-f");
	cmd.add(changetype);
	cmd.add(outputurl);
	
	CmdExecuter.exec(cmd, this,outputurl);
}

	
	private static List<String> cmd = new ArrayList<String>();

	public void dealString(String str) {

		System.out.println("str输出为:"+str);
	}
	
	public static void main(String[] args) {
		
		
	}

	@Override
	public void dealString(String str, String inputurl) {

		System.out.println(inputurl+"转化输出==dealString==="+str);
		
	}
}

