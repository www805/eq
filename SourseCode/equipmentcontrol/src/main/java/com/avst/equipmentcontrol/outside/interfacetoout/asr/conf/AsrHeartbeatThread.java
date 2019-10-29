package com.avst.equipmentcontrol.outside.interfacetoout.asr.conf;

import com.avst.equipmentcontrol.common.conf.ASRType;
import com.avst.equipmentcontrol.common.conf.AsrServerModel;
import com.avst.equipmentcontrol.common.util.DateUtil;
import com.avst.equipmentcontrol.common.util.LogUtil;
import com.avst.equipmentcontrol.common.util.baseaction.RRParam;
import com.avst.equipmentcontrol.outside.dealoutinterface.asr.avstasr.v1.action.AvstAsrImpl;
import com.avst.equipmentcontrol.outside.dealoutinterface.asr.avstasr.req.AVSTAsrParam_heartbeat;

/**
 * 定时检测语音服务的存在，保持一致在线的状态
 */
public class AsrHeartbeatThread<T> extends Thread{

    private int heartbeatTime=20;//心跳间隔时间S

    private T heartbeatParam;//心跳的参数集合

    private String asrType;//语音识别类型，暂时只有一种 avst，这里没有写的换复杂，不知道其他类型asr的处理

    public boolean bool=true;//中断机制

    public AsrHeartbeatThread(int heartbeatTime, T heartbeatParam, String asrType) {
        this.heartbeatTime = heartbeatTime;
        this.heartbeatParam = heartbeatParam;
        this.asrType = asrType;
    }

    public AsrHeartbeatThread( T heartbeatParam, String asrType) {
        this.heartbeatParam = heartbeatParam;
        this.asrType = asrType;
    }

    @Override
    public void run() {

        while(bool){
            try {
                Thread.sleep(10*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(!bool){
                break;
            }
            LogUtil.intoLog(this.getClass(),DateUtil.getDateAndMinute()+"---AVST语音识别服务心跳检测处理中---asrType："+asrType);
            if(asrType.equals(ASRType.AVST)){//AVST语音识别服务的心跳处理
                AVSTAsrParam_heartbeat heartbeat=(AVSTAsrParam_heartbeat)heartbeatParam;
                RRParam<Boolean> booleanRRParam= AvstAsrImpl.heartbeat(heartbeat);
                if(null!=booleanRRParam){
                    LogUtil.intoLog(this.getClass(),booleanRRParam.getT()+"------booleanRRParam");
                }else{
                    LogUtil.intoLog(this.getClass(),"AvstAsrImpl.heartbeat(heartbeat) is null");
                }
            }else{
                LogUtil.intoLog(this.getClass(),"暂时没有其他asr服务器需要心跳处理---");
            }
            if(!bool){
                break;
            }
        }
        LogUtil.intoLog(this.getClass(),"AsrHeartbeatThread 定时检测语音服务的线程关闭了，asrType:"+asrType);
    }
}
