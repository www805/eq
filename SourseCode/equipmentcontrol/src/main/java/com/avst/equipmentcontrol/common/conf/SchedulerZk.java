package com.avst.equipmentcontrol.common.conf;

import com.avst.equipmentcontrol.common.util.DateUtil;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.common.util.baseaction.ReqParam;
import com.avst.equipmentcontrol.feignclient.base.req.ControlInfoParam;
import com.avst.equipmentcontrol.feignclient.base.vo.ControlInfoParamVO;
import com.avst.equipmentcontrol.feignclient.zk.ZkControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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

    @Value("${spring.application.name}")
    private String servername;

    @Value("${control.servser.url}")
    private String url;

    //每个小时的第五分钟执行

    /**
     * 10秒心跳一次
     */
//    @Scheduled(cron = "0 05 1/1 * * *")
    @Scheduled(fixedRate = 10000)
    public void testTasks() {

        ReqParam<ControlInfoParamVO> param = new ReqParam<>();

        ControlInfoParamVO controlInfoParamVO = new ControlInfoParamVO();
        controlInfoParamVO.setServername(servername);//服务器注册名
        controlInfoParamVO.setServertitle("设备系统");//服务器中文名
        controlInfoParamVO.setUrl(url);
        controlInfoParamVO.setLoginusername("admin");
        controlInfoParamVO.setLoginpassword("admin123");
        controlInfoParamVO.setTotal_item(4);
        controlInfoParamVO.setUse_item(4);
//        controlInfoParamVO.setCreatetime(DateUtil.getDateAndMinute());//设置当前时间
        controlInfoParamVO.setStatus(1);//状态

//        System.out.println(controlInfoParamVO);

        param.setParam(controlInfoParamVO);

        try {
            RResult heartbeat = zkControl.getHeartbeat(param);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
