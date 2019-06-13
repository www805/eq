package com.avst.equipmentcontrol.outside.interfacetoout.asr.v1.service;

import com.avst.equipmentcontrol.common.datasourse.extrasourse.asr.entity.Asr_et_ettype;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.asr.mapper.Asr_etinfoMapper;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.flushbonading.entity.Flushbonading_ettd;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.flushbonading.mapper.Flushbonading_ettdMapper;
import com.avst.equipmentcontrol.common.util.LogUtil;
import com.avst.equipmentcontrol.common.util.baseaction.RRParam;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.outside.dealoutinterface.asr.avstasr.v1.action.AvstAsrImpl;
import com.avst.equipmentcontrol.outside.dealoutinterface.asr.avstasr.req.AVSTAsrParam_heartbeat;
import com.avst.equipmentcontrol.outside.dealoutinterface.asr.avstasr.req.AVSTAsrParam_quit;
import com.avst.equipmentcontrol.outside.dealoutinterface.asr.avstasr.req.AVSTAsrParam_reg;
import com.avst.equipmentcontrol.outside.interfacetoout.asr.cache.AsrCache_toout;
import com.avst.equipmentcontrol.outside.interfacetoout.asr.cache.param.AsrMessageParam;
import com.avst.equipmentcontrol.outside.interfacetoout.asr.conf.AsrHeartbeatThread;
import com.avst.equipmentcontrol.outside.interfacetoout.asr.conf.AsrOverThread;
import com.avst.equipmentcontrol.outside.interfacetoout.asr.req.OverAsrParam;
import com.avst.equipmentcontrol.outside.interfacetoout.asr.req.StartAsrParam;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * avst语音服务器的service处理类
 */
@Service
public class ToOutServiceImpl_asr_avst implements ToOutService_asr {

    @Autowired
    private Asr_etinfoMapper asr_etinfoMapper;

    @Autowired
    private Flushbonading_ettdMapper flushbonading_ettdMapper;


    @Override
    public RResult startAsr(StartAsrParam param,RResult rResult) {

        //1调第三方的开始
        String asrserverssid=param.getAsrEquipmentssid();
        EntityWrapper ew=new EntityWrapper();
        ew.eq("aet.ssid",asrserverssid);
        Asr_et_ettype asr_et_ettype= asr_etinfoMapper.getAsrinfo(ew);
        if(null==asr_et_ettype){
            LogUtil.intoLog(this.getClass(),asrserverssid+":asrserverssid 没有找到这个asr服务器，开启失败");
            rResult.setMessage("没有找到这个asr服务器，开启失败");
            return rResult;
        }
        String audiourl="";
        try {
            String tdssid=param.getTdssid();
            EntityWrapper<Flushbonading_ettd> ew2=new EntityWrapper<Flushbonading_ettd>();
            ew2.eq("ssid",tdssid);
            Flushbonading_ettd flushbonading_ettd=flushbonading_ettdMapper.selectList(ew2).get(0);
            if(null==flushbonading_ettd|| StringUtils.isEmpty(flushbonading_ettd.getPullflowurl())){
                LogUtil.intoLog(this.getClass(),tdssid+":tdssid 没有找到这个设备通道，开启失败");
                rResult.setMessage("没有找到这个设备通道，开启失败");
                return rResult;
            }
            audiourl=flushbonading_ettd.getPullflowurl();
        } catch (Exception e) {
            e.printStackTrace();
            rResult.setMessage("没有找到设备通道，开启失败");
            return rResult;
        }

        AVSTAsrParam_reg reg=new AVSTAsrParam_reg(asr_et_ettype.getEtip(),asr_et_ettype.getPort()+"",audiourl,asrserverssid);
        //参数txtcallbackurl如果接口不传就去缓存中拿
        reg.setTxtcallbackurl(AsrCache_toout.avstbacktxtinterface);
        RRParam<String> rrParam= AvstAsrImpl.reg(reg);
        String reqendtime= new Date().getTime()+"";//当前时间long ms值，作为asr识别开始时间
        //2给一个线程用于定时刷新心跳
        if(null!=rrParam&&rrParam.getCode()==1){
            String asrid=rrParam.getT();
            String asrtype=param.getAsrtype();//这里需要查数据库，通过asrEquipmentssid，换着写成缓存，找出类型
            AVSTAsrParam_heartbeat avstAsrParam_heartbeat=new AVSTAsrParam_heartbeat(reg.getIp(),reg.getPort(),asrid);
            AsrMessageParam<AVSTAsrParam_heartbeat> asr=new AsrMessageParam<AVSTAsrParam_heartbeat>();
            AsrHeartbeatThread<AVSTAsrParam_heartbeat> asrHeartbeatThread=
                    new AsrHeartbeatThread<AVSTAsrParam_heartbeat>(avstAsrParam_heartbeat,asrtype);
            asrHeartbeatThread.start();
            asr.setAsrtype(asrtype);
            asr.setAsrHeartbeatThread(asrHeartbeatThread);

            AsrCache_toout.setAsrMassege(asrid,asr);//写入本次语音识别信息的缓存
            rResult.changeToTrue(asrid);
            rResult.setEndtime(reqendtime);
        }else{
            rResult.setMessage("开启语音识别失败");
        }
        return rResult;
    }

    @Override
    public RResult overAsr(OverAsrParam param,RResult rResult) {

        String asrid=param.getAsrid();
        String asrserverssid=param.getAsrEquipmentssid();
        EntityWrapper ew=new EntityWrapper();
        ew.eq("aet.ssid",asrserverssid);
        Asr_et_ettype asr_et_ettype= asr_etinfoMapper.getAsrinfo(ew);
        if(null==asr_et_ettype){
            LogUtil.intoLog(this.getClass(),asrserverssid+":asrserverssid 没有找到这个asr服务器，开启失败");
            rResult.setMessage("没有找到这个asr服务器，开启失败");
            return rResult;
        }
        AVSTAsrParam_quit qparam=new AVSTAsrParam_quit(asr_et_ettype.getEtip(),asr_et_ettype.getPort()+"",asrid);

        //1、调第三方的关闭
        RRParam<Boolean> rrParam=AvstAsrImpl.quit(qparam);
        if(null!=rrParam&&rrParam.getCode()==1){

            //2给个定时监管缓存的线程，时间自定义
            //结束本次心跳
            String asrEquipmentssid=param.getAsrEquipmentssid();//
            String asrtype=param.getAsrtype();//这里需要查数据库，通过asrEquipmentssid，换着写成缓存，找出类型
            AsrOverThread asrOverThread=new AsrOverThread(qparam.getId());
            asrOverThread.start();

            rResult.changeToTrue(true);
        }else{
            LogUtil.intoLog(this.getClass(),"AvstAsrImpl.quit 关闭失败，qparam.getId()："+qparam.getId());
            rResult.setMessage("关闭"+qparam.getId()+"语音识别失败");
        }
        return rResult;
    }


}
