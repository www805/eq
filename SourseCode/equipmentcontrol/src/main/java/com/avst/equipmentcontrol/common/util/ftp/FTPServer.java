package com.avst.equipmentcontrol.common.util.ftp;

import com.avst.equipmentcontrol.common.util.LogUtil;
import com.avst.equipmentcontrol.common.util.OpenUtil;
import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.Authority;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.listener.Listener;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.WritePermission;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FTPServer {

    public static FtpServer ftpserver=null;

    public static boolean startFTPServer(int port,List<FTPUser> ftpUsers){

        try {

            if(null!=ftpserver){
                LogUtil.intoLog(3,FTPServer.class,"ftp服务器正在运行中，需要关闭才能再次开启");
                ftpserver.stop();
            }

            FtpServerFactory serverFactory = new FtpServerFactory();

            ListenerFactory factory = new ListenerFactory();
            //设置监听端口
            factory.setPort(port);

            Listener listener=factory.createListener();

            //替换默认监听
            serverFactory.addListener("default", listener);

            for(FTPUser ftp:ftpUsers){
                //用户名
                BaseUser user = new BaseUser();
                user.setName(ftp.getName());
                //密码 如果不设置密码就是匿名用户
                user.setPassword(ftp.getPassword());
                //用户主目录
                user.setHomeDirectory(ftp.getHomeDirectory());

                List<Authority> authorities = new ArrayList<Authority>();
                //增加写权限
                authorities.add(new WritePermission());
                user.setAuthorities(authorities);

                //增加该用户
                try {
                    serverFactory.getUserManager().save(user);
                } catch (FtpException e) {
                    e.printStackTrace();
                }
            }


            try {
                ftpserver = serverFactory.createServer();
                ftpserver.start();
                return true;
            } catch (FtpException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }

        return false;
    }

    public static void main(String[] args) {
//        System.out.println(startFTPServer());
    }


}