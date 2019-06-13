package com.avst.equipmentcontrol.outside.dealoutinterface.asr.avstasr.v1.action;

import com.avst.equipmentcontrol.common.util.LogUtil;
import com.avst.equipmentcontrol.common.util.baseaction.RRParam;
import com.avst.equipmentcontrol.outside.dealoutinterface.asr.avstasr.req.AVSTAsrParam_heartbeat;
import com.avst.equipmentcontrol.outside.dealoutinterface.asr.avstasr.req.AVSTAsrParam_quit;
import com.avst.equipmentcontrol.outside.dealoutinterface.asr.avstasr.req.AVSTAsrParam_reg;
import com.avst.equipmentcontrol.outside.dealoutinterface.asr.avstasr.v1.service.DealAvstAsrImpl;

/**
 * 顺泰伟成语音识别服务接口实现
 */
public class AvstAsrImpl {


    public static RRParam<String> reg(AVSTAsrParam_reg param){
        RRParam<String> vo = new RRParam<String>();

        if(null==param){
            vo.setMessage("参数为空");
        }else{
            vo= DealAvstAsrImpl.reg(param,vo);
        }
        return vo;
    }

    public static RRParam<Boolean> heartbeat(AVSTAsrParam_heartbeat param){
        RRParam<Boolean> vo = new RRParam<Boolean>();
        if(null==param){
            vo.setMessage("参数为空");
        }else{
            vo= DealAvstAsrImpl.heartbeat(param,vo);
        }
        return vo;
    }

    public static RRParam<Boolean> quit(AVSTAsrParam_quit param){
        RRParam<Boolean> vo = new RRParam<Boolean>();
        if(null==param){
            vo.setMessage("参数为空");
        }else{
            vo= DealAvstAsrImpl.quit(param,vo);
        }
        return vo;
    }


    public static void main(String[] args) {

        AVSTAsrParam_reg reg=new AVSTAsrParam_reg("192.168.17.175","8000","http://192.168.17.154:80/1.wav","http://192.168.17.175:8081/toasr/v1/toAsrServerForTxtBack");

        RRParam<String> rrParam=AvstAsrImpl.reg(reg);

        if(1==rrParam.getCode()){
             String id=rrParam.getT();
            LogUtil.intoLog(AvstAsrImpl.class,id+":id======");

            new Thread(){
                @Override
                public void run() {
                    while(true){
                        try {
                            Thread.sleep(10000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        AVSTAsrParam_heartbeat heartbeat=new AVSTAsrParam_heartbeat("192.168.17.175","8000",id);
                        RRParam<Boolean> booleanRRParam=AvstAsrImpl.heartbeat(heartbeat);
                    }
                }
            }.start();

        }else{
            LogUtil.intoLog(AvstAsrImpl.class,rrParam.getMessage()+":rrParam.getMessage()======");
        }

    }
}
