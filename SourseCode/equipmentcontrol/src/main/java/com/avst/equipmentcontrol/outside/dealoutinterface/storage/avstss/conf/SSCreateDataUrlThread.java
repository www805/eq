package com.avst.equipmentcontrol.outside.dealoutinterface.storage.avstss.conf;

import com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.entity.Ss_database;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.entity.Ss_saveinfo;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.entity.param.Ss_dataMessageParam;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.mapper.Ss_databaseMapper;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.mapper.Ss_saveinfoMapper;
import com.avst.equipmentcontrol.common.util.FileUtil;
import com.avst.equipmentcontrol.common.util.LogUtil;
import com.avst.equipmentcontrol.common.util.OpenUtil;
import com.avst.equipmentcontrol.common.util.SpringUtil;
import com.avst.equipmentcontrol.common.util.ff.FFThreadCache;
import com.avst.equipmentcontrol.common.util.ff.VideoChangeThread;
import com.avst.equipmentcontrol.common.util.properties.PropertiesListenerConfig;
import com.avst.equipmentcontrol.outside.dealoutinterface.storage.avstss.cache.SSThreadCache;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.apache.commons.lang.StringUtils;

import java.io.File;

/**
 * 进入时state=1 or state=-2
 *用于服务器文件创建对外开放的请求地址，并且生成请求地址state=2
 */
public class SSCreateDataUrlThread extends  Thread{

    private Ss_databaseMapper ss_databaseMapper;

    private Ss_dataMessageParam ss_dataMessageParam;

    private Ss_saveinfoMapper ss_saveinfoMapper ;

    public SSCreateDataUrlThread( Ss_databaseMapper ss_databaseMapper, Ss_dataMessageParam ss_dataMessageParam) {
        this.ss_databaseMapper = ss_databaseMapper;
        this.ss_dataMessageParam = ss_dataMessageParam;
        ss_saveinfoMapper = SpringUtil.getBean(Ss_saveinfoMapper.class);
    }

    public boolean bool=true;

    @Override
    public void run() {

       LogUtil.intoLog(this.getClass(),"SSCreateDataUrlThread is coming "+ss_dataMessageParam.getIid());

        try {
            String filename=ss_dataMessageParam.getFilename();
            String savepath=ss_dataMessageParam.getDatasavepath();
            String iid=ss_dataMessageParam.getIid();
            if(StringUtils.isEmpty(filename)||StringUtils.isEmpty(savepath)){
                LogUtil.intoLog(4,this.getClass(),filename+":filename=====有一个参数为空====savepath:"+savepath);
                SSThreadCache.delSSCreateDataUrlThread(iid);//关闭这个生成网络地址的线程的缓存
                return ;
            }

            String staticpath= "ftpdata";//默认
            if(null!=ss_saveinfoMapper){
                try {
                    Ss_saveinfo ss_saveinfo=new Ss_saveinfo();
                    ss_saveinfo.setSsid(ss_dataMessageParam.getSaveinfossid());
                    ss_saveinfo=ss_saveinfoMapper.selectOne(ss_saveinfo);
                    if(null!=ss_saveinfo&&StringUtils.isNotEmpty(ss_saveinfo.getSsstatic())){
                        staticpath=ss_saveinfo.getSsstatic();
                    }else{
                        LogUtil.intoLog(4,this.getClass(),staticpath+":staticpath 获取http切割路径未找到，采用默认的路径,ss_saveinfo:"+ss_saveinfo+"--ss_saveinfo.getSsstatic()"+ ss_saveinfo==null?"null":ss_saveinfo.getSsstatic());
                    }
                } catch (Exception e) {
                    LogUtil.intoLog(4,this.getClass(),staticpath+":staticpath 获取http切割路径报错，采用默认的路径");
                }
            }else{
                LogUtil.intoLog(4,this.getClass(),ss_saveinfoMapper+":ss_saveinfoMapper is null 获取http切割路径异常，采用默认的路径");
            }

            while (bool){

                try {
                    Thread.sleep(20000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(!bool){
                    LogUtil.intoLog(3,this.getClass(),"SSCreateDataUrlThread 被主动中止--filename："+filename);
                    break;
                }

                try {

                    //先判断是否有转码，有的话就需要判断转码是否成功，只有成功才能生成地址
                    String leastvideolength= PropertiesListenerConfig.getProperty("leastvideolength");
                    String changetype = PropertiesListenerConfig.getProperty("changetype");
                    if(null!=leastvideolength && !leastvideolength.trim().equals("")
                        &&null!=changetype && savepath.endsWith(changetype)){//一定要是转换后的文件类型才需要再判断一遍长度问题

                        int leastvideolength_=Integer.parseInt(leastvideolength);

                        VideoChangeThread videoChangeThread=FFThreadCache.getVideoChangeThread(savepath);//查看是否还有这个转码线程有的话就再等一下
                        if(null!=videoChangeThread){
                            LogUtil.intoLog(3,this.getClass(),"VideoChangeThread 转码线程存在，再等一下，continue，savepath："+savepath);
                            continue;
                        }else{
                            LogUtil.intoLog(1,this.getClass(),"VideoChangeThread 转码线程不存在，可以继续，savepath："+savepath);
                        }

                        File file=new File(savepath);
                        if(!file.exists()||file.length() < leastvideolength_){
                            file=null;
                            LogUtil.intoLog(3,this.getClass(),"转video文件正常，文件长度不正常，需要等待再次检测，----leastvideolength:"+leastvideolength);

                            continue;
                        }else{
                            LogUtil.intoLog(1,this.getClass(),"转video文件正常，可以下一步，----leastvideolength:"+leastvideolength);

                        }

                    }else{
                        LogUtil.intoLog(3,this.getClass(),savepath+"：savepath，不是video，或者转video文件的属性配置参数异常,跳过检测环节直接生成点播地址，----leastvideolength:"+leastvideolength);
                    }

                    try {
                        //去掉名字的特殊字符以前的所有字符
                        String savename=OpenUtil.getfilename(savepath);
                        String specialchars=PropertiesListenerConfig.getProperty("specialchars");
                        String newsavename=OpenUtil.delSpecialBeforeChar(savename,specialchars);
                        savepath= OpenUtil.FileRenameTo(savepath,newsavename);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    //建立对外开放的请求地址(给个NGINX，可以配置网络资源请求地址)
                    //http 最简单的

                    String httpbasestaticpath=PropertiesListenerConfig.getProperty("httpbasestaticpath");
                    String uploadparh=httpbasestaticpath+OpenUtil.strMinusBasePath(staticpath,savepath);//下载路径
                    //修改数据库的原始保存文件记录表
                    Gson gson=new Gson();
                    Ss_database ss_database=(Ss_database)gson.fromJson(gson.toJson(ss_dataMessageParam),Ss_database.class);
                    ss_database.setHttpurl(uploadparh);
                    ss_database.setDatasavepath(savepath);
                    ss_database.setDefaulturl("http");//暂时只提供http请求的网络地址
                    ss_database.setState(2);//文件网络请求路径完成
                    int ss_databaseupdateById=ss_databaseMapper.updateById(ss_database);
                    LogUtil.intoLog(this.getClass(),ss_databaseupdateById+":ss_databaseupdateById----SSCreateDataUrlThread ss_dataMessageParam.getId()："+ss_dataMessageParam.getId());
                    if(ss_databaseupdateById> -1){
                        //完了就出去了
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if(!bool){
                    LogUtil.intoLog(this.getClass(),"SSCreateDataUrlThread 被主动中止--filename："+filename);
                    break;
                }

            }

            LogUtil.intoLog(this.getClass(),"正常结束生成网络地址的线程 iid："+iid);
            SSThreadCache.delSSCreateDataUrlThread(iid);//关闭这个生成网络地址的线程的缓存
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
