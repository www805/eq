package com.avst.equipmentcontrol.common.conf;

import com.avst.equipmentcontrol.common.util.DateUtil;
import com.avst.equipmentcontrol.common.util.LogUtil;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.common.util.baseaction.ReqParam;
import com.avst.equipmentcontrol.common.util.properties.PropertiesListenerConfig;
import com.avst.equipmentcontrol.feignclient.base.req.ControlInfoParam;
import com.avst.equipmentcontrol.feignclient.base.vo.ControlInfoParamVO;
import com.avst.equipmentcontrol.feignclient.zk.ZkControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 定时器任务
 * 注意：这种定时器一定要用try包一下，以免内存泄露或者线程异常不能释放
 */
@Component
public class SchedulerZk {

    @Autowired
    private ZkControl zkControl;


//    @Scheduled(fixedRate = 10000) //10秒心跳一次
    /**
     * 1分钟心跳一次
     */
    @Scheduled(cron = "0 0/1 * * * ? ")
    public void testTasks() {

        ReqParam<ControlInfoParamVO> param = new ReqParam<>();

        //获取本机ip地址
        String hostAddress = "localhost";
        try {
            InetAddress addr = InetAddress.getLocalHost();
            hostAddress = addr.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        String servername = PropertiesListenerConfig.getProperty("spring.application.name");
        String url = PropertiesListenerConfig.getProperty("control.servser.url");
        String port = PropertiesListenerConfig.getProperty("server.port");
        String loginusername = PropertiesListenerConfig.getProperty("control.servser.loginusername");
        String loginpassword = PropertiesListenerConfig.getProperty("control.servser.loginpassword");

        System.out.println(servername);
        System.out.println(port);

        ControlInfoParamVO controlInfoParamVO = new ControlInfoParamVO();
        controlInfoParamVO.setServername(servername);//服务器注册名
        controlInfoParamVO.setServertitle("设备系统");//服务器中文名
        controlInfoParamVO.setUrl("http://" + hostAddress + ":" + port + url); //访问本服务器地址
        controlInfoParamVO.setLoginusername(loginusername);
        controlInfoParamVO.setLoginpassword(loginpassword);
        controlInfoParamVO.setTotal_item(4);
        controlInfoParamVO.setUse_item(4);
//        controlInfoParamVO.setCreatetime(DateUtil.getDateAndMinute());//设置当前时间
        controlInfoParamVO.setStatus(1);//状态

        param.setParam(controlInfoParamVO);

        System.out.println(controlInfoParamVO);

        try {
            RResult heartbeat = zkControl.getHeartbeat(param);
        } catch (Exception e) {
            LogUtil.intoLog(4,this.getClass(),"Scheduler.testTasks is error, 上报心跳到总控失败");
        }
    }

}
