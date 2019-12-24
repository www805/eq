package com.avst.equipmentcontrol.common.conf;

import com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.entity.Ss_saveinfo;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.mapper.Ss_saveinfoMapper;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.tts.entity.Tts_etinfo;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.tts.mapper.Tts_etinfoMapper;
import com.avst.equipmentcontrol.common.util.*;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.common.util.ftp.FTPServer;
import com.avst.equipmentcontrol.common.util.ftp.FTPUser;
import com.avst.equipmentcontrol.common.util.properties.PropertiesListenerConfig;
import com.avst.equipmentcontrol.feignclient.zk.ZkControl;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlExpression;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 当springboot启动的时候，会自动执行该类
 */
@Component
@Order(value = 1)
public class SysStartTimer implements ApplicationRunner {

    @Autowired
    private ZkControl zkControl;

    @Autowired
    private Ss_saveinfoMapper ss_saveinfoMapper;

    @Autowired
    private Tts_etinfoMapper tts_etinfoMapper;

    //获取服务器时间进行比对
    @Override
    public void run(ApplicationArguments args) {

        pushMessageToZK();

        //清理以往的硬盘缓存
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    checkAndClearSys();
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        startFTPServer();
    }

    public void startFTPServer(){

        int port=6001;
        String ftpport=PropertiesListenerConfig.getProperty("ftpport");
        if(StringUtils.isNotEmpty(ftpport)){
            try {
                port=Integer.parseInt(ftpport);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        List<FTPUser> ftpUsers=new ArrayList<FTPUser>();
        Wrapper<Ss_saveinfo> wrapper=new EntityWrapper<Ss_saveinfo>();
        List<Ss_saveinfo> userlist=ss_saveinfoMapper.selectList(wrapper);
        if(null!=userlist&&userlist.size() > 0){

            for(Ss_saveinfo ss:userlist){
                if(null!=ss.getSsstate()&&ss.getSsstate()==1){//ss.getXytype()==ftp 以后还需要判断是否是ftp服务器
                    FTPUser ftpUser=new FTPUser();

                    ftpUser.setHomeDirectory(ss.getDatasavebasepath());
                    ftpUser.setName(ss.getUser());
                    ftpUser.setPassword(ss.getPasswd());
                    ftpUsers.add(ftpUser);
                    LogUtil.intoLog(1,this.getClass(),"ftp存储服务开启了一个用户："+JacksonUtil.objebtToString(ftpUser));
                }
            }

        }else{
            FTPUser ftpUser=new FTPUser();
            ftpUser.setHomeDirectory("d:/ftpdata");
            ftpUser.setName("admin");
            ftpUser.setPassword("admin123");
            ftpUsers.add(ftpUser);
            LogUtil.intoLog(3,this.getClass(),"ftp存储服务在数据库中未找到，默认开启了一个用户："+JacksonUtil.objebtToString(ftpUser));
        }

        FTPServer.startFTPServer(port,ftpUsers);//启动ftp服务器
    }

    /**
     * 向总站推送信息，并获取总站的最新时间，检测并同步时间
     * @return
     */
    public void pushMessageToZK(){

        try {
            //从总控获取时间
            RResult controlTime = zkControl.getControlTime();

            if(null != controlTime){

                //日期的差距
                String createTime = (String) controlTime.getData();  //获取总控服务器时间
                if (StringUtils.isNotEmpty(createTime)) {

                    String newTime = DateUtil.getDateAndMinute();  //当前服务器时间
                    SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    //把获取到的时间转为时间戳
                    Date date = dateFormatter.parse(createTime);

                    //把转成总控时间和当前服务器时间戳进行计算
                    Date newday = dateFormatter.parse(newTime);
                    Date oldDay = dateFormatter.parse(createTime);

                    Integer servserDate = Integer.parseInt(PropertiesListenerConfig.getProperty("control.servser.date"));
                    String formulas = PropertiesListenerConfig.getProperty("control.servser.formulas");

                    //计算公式转换成整数
                    JexlEngine jexlEngine = new JexlBuilder().create();
                    JexlExpression expression = jexlEngine.createExpression(formulas);
                    Integer evaluate = (Integer) expression.evaluate(null);

                    long intervalDay = (newday.getTime() - oldDay.getTime())/(evaluate);

                    //如果时间差过1小时以上，就修改系统时间
                    if (Math.abs(intervalDay) >= servserDate) {

                        //修改系统时间
                        String osName = System.getProperty("os.name");
                        String cmd = "";
                        try {
                            SimpleDateFormat rq = new SimpleDateFormat("yyyy-MM-dd");
                            SimpleDateFormat xs = new SimpleDateFormat("HH:mm:ss");

                            if (osName.matches("^(?i)Windows.*$")) {// Window 系统

                                // 格式 HH:mm:ss  22:35:00
                                cmd = xs.format(date);
                                cmd = "  cmd /c time " + cmd;
//                                Runtime.getRuntime().exec(cmd);
                                ExecuteCMD.executeCMD(cmd);
                                // 格式：yyyy-MM-dd  2009-03-26
                                cmd = rq.format(date);
                                cmd = " cmd /c date " + cmd;
//                                Runtime.getRuntime().exec(cmd);
                                ExecuteCMD.executeCMD(cmd);
                            } else {// Linux 系统
                                // 格式：yyyyMMdd  20090326
                                rq = new SimpleDateFormat("yyyyMMdd");
                                cmd = rq.format(date);
                                cmd = "  date -s " + cmd;
//                                Runtime.getRuntime().exec(cmd);
                                ExecuteCMD.executeCMD(cmd);
                                // 格式 HH:mm:ss  22:35:00
                                cmd = xs.format(date);
                                cmd = "  date -s " + cmd;
//                                Runtime.getRuntime().exec(cmd);
                                ExecuteCMD.executeCMD(cmd);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }

//                System.out.println("服务器获取到的时间：" + date);
            }
        } catch (Exception e) {
            LogUtil.intoLog(4,this.getClass(),"getControlTime ZkTimeConfig。run 总控同步时间，请求异常");
        }
    }

    /**
     * 检查并清理服务器的垃圾
     */
    public void checkAndClearSys(){

        //wav
        try {
            EntityWrapper entityWrapper=new EntityWrapper();
            List<Tts_etinfo> ttslist=tts_etinfoMapper.selectList(entityWrapper);
            if(null!=ttslist&&ttslist.size() > 0){
                for(Tts_etinfo tts:ttslist){
                    String ttsbasepath=tts.getTtsbasepath();
                    System.out.println(ttsbasepath+":ttsbasepath 清理tts服务器的垃圾");
                    if(StringUtils.isNotEmpty(ttsbasepath)){
                        FileUtil.delAllFile(ttsbasepath);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        //ts和压缩
        try {
            EntityWrapper entityWrapper2=new EntityWrapper();
            List<Ss_saveinfo> sslist=ss_saveinfoMapper.selectList(entityWrapper2);

            String changetype=PropertiesListenerConfig.getProperty("changetype");
            String leastvideolength=PropertiesListenerConfig.getProperty("leastvideolength");
            long leastvideolength_long=Long.parseLong(leastvideolength);

            if(null!=sslist&&sslist.size() > 0){
                for(Ss_saveinfo ss:sslist){
                    String datasavebasepath=ss.getDatasavebasepath();
                    if(StringUtils.isNotEmpty(datasavebasepath)){

                        //TS
                        try {
                            List<String> pathlist=FileUtil.getAllFiles(datasavebasepath,1);
                            if(null!=pathlist&&pathlist.size() > 0){
                                for(String path:pathlist){
                                    try {
                                        if(!path.endsWith(changetype)){//判断不是我们需要的视频文件
                                            File file=new File(path);
                                            String checktype="."+OpenUtil.getfiletype(path);
                                            String newtype="."+changetype;
                                            String newpath=path.replace(checktype,newtype);
                                            if(file.length()>leastvideolength_long //判断大于1M的不是我们需要的文件
                                                    && OpenUtil.fileisexist(newpath)){ //并且这个文件夹下的可播放的文件存在
                                                file.delete(); //就可以直接删除大于1M的非播放文件
                                            }
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        //清理压缩
                        try {
                            delGZIP(datasavebasepath);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 开启的时候自动删除打包的文件
     */
    private void delGZIP(String datasavebasepath){

        LogUtil.intoLog(1,this.getClass(),"准备清理上一次开启产生的zip压缩包,监测自身的定时器");
        //先获取要删除文件的格式
        String gztype= PropertiesListenerConfig.getProperty("gztype");
        if(StringUtils.isEmpty(gztype)){
            gztype=".zip";
        }

        //获取所有等待检测的文件
        String ftpsavebasepath= datasavebasepath;
        if(StringUtils.isEmpty(ftpsavebasepath)){
            ftpsavebasepath="d:/ftpdata/";
        }
        List<String> filelist=FileUtil.getAllFilePath(ftpsavebasepath,2);
        if(null!=filelist&&filelist.size() > 0){
            for(String path:filelist){
                //对比删除
                if(path.endsWith(gztype)){//删除GZIP的文件
                    File file=new File(path);
                    boolean bool=file.delete();
                    System.out.println(bool+":bool 删除GZIP的文件,path:"+path);
                    file=null;
                }
            }
        }

    }




}
