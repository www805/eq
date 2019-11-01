package com.avst.equipmentcontrol.outside.interfacetoout.asr.v1.service;

import com.avst.equipmentcontrol.common.conf.AsrServerModel;
import com.avst.equipmentcontrol.common.conf.NetTool;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.asr.entity.Asr_et_ettype;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.asr.mapper.Asr_etinfoMapper;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.flushbonading.entity.Flushbonading_ettd;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.flushbonading.mapper.Flushbonading_ettdMapper;
import com.avst.equipmentcontrol.common.util.LogUtil;
import com.avst.equipmentcontrol.common.util.OpenUtil;
import com.avst.equipmentcontrol.common.util.baseaction.RRParam;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.outside.dealoutinterface.asr.avstasr.req.AVSTAsrParam_settaskinfo;
import com.avst.equipmentcontrol.outside.dealoutinterface.asr.avstasr.req.param.TaskParam;
import com.avst.equipmentcontrol.outside.dealoutinterface.asr.avstasr.v1.action.AvstAsrImpl;
import com.avst.equipmentcontrol.outside.dealoutinterface.asr.avstasr.req.AVSTAsrParam_heartbeat;
import com.avst.equipmentcontrol.outside.dealoutinterface.asr.avstasr.req.AVSTAsrParam_quit;
import com.avst.equipmentcontrol.outside.dealoutinterface.asr.avstasr.req.AVSTAsrParam_reg;
import com.avst.equipmentcontrol.outside.dealoutinterface.asr.cache.AsrCache;
import com.avst.equipmentcontrol.outside.dealoutinterface.asr.cache.param.AsrTxtParam;
import com.avst.equipmentcontrol.outside.interfacetoout.asr.cache.AsrCache_toout;
import com.avst.equipmentcontrol.outside.interfacetoout.asr.cache.param.AsrMessageParam;
import com.avst.equipmentcontrol.outside.interfacetoout.asr.conf.AsrHeartbeatThread;
import com.avst.equipmentcontrol.outside.interfacetoout.asr.conf.AsrOverThread;
import com.avst.equipmentcontrol.outside.interfacetoout.asr.req.OverAsrParam;
import com.avst.equipmentcontrol.outside.interfacetoout.asr.req.PauseOrContinueAsrParam;
import com.avst.equipmentcontrol.outside.interfacetoout.asr.req.StartAsrParam;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

        String tdssid=param.getTdssid();
        if(StringUtils.isEmpty(tdssid)){
            rResult.setMessage("设备通道唯一标识参数为空，开启失败");
            return rResult;
        }
        if(tdssid.indexOf(",") > 0){//说明是多音频一起开启，调用另一个开启方法

            return startAsr_all(param,rResult);
        }


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
        String avstbacktxtinterface=AsrCache_toout.avstbacktxtinterface;
        if(avstbacktxtinterface.indexOf("localhost") > -1){
            avstbacktxtinterface=avstbacktxtinterface.replace("localhost", OpenUtil.getMyIP());
        }
        reg.setTxtcallbackurl(avstbacktxtinterface);
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
    public RResult startAsr_all(StartAsrParam param, RResult rResult) {

        String tdssid=param.getTdssid();
        if(StringUtils.isEmpty(tdssid)){
            rResult.setMessage("设备通道唯一标识参数为空，开启失败");
            return rResult;
        }
        if(tdssid.indexOf(",") < 1){//说明是单音频开启，调用另一个开启方法
            return startAsr(param,rResult);
        }

//        if(tdssid.indexOf(";") < 0){
//            LogUtil.intoLog(this.getClass(),tdssid+":tdssid 一对多的模式下通道标识异常，开启失败");
//            rResult.setMessage("一对多的模式下通道标识异常，开启失败");
//            return rResult;
//        }

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

        String[] tdarr=tdssid.split(";");
        String  audiourl="<root><asrchannel>asrnum</asrchannel>";
        int asrnum=0;
        int asrchannel=param.getAsrchannel();
        if(asrchannel==0){
            asrchannel=1;//最少要有一个识别数
        }
        List<TaskParam> taskParamList=new ArrayList<TaskParam>();
        for(String td:tdarr){

            if(StringUtils.isEmpty(td)){
                continue;
            }

            try {
                String[] tdarr2=td.split(",");
                String index =tdarr2[0];
                String ssid =tdarr2[1];

                EntityWrapper<Flushbonading_ettd> ew2=new EntityWrapper<Flushbonading_ettd>();
                ew2.eq("ssid",ssid);
                Flushbonading_ettd flushbonading_ettd=flushbonading_ettdMapper.selectList(ew2).get(0);
                if(null==flushbonading_ettd|| StringUtils.isEmpty(flushbonading_ettd.getPullflowurl())){
                    LogUtil.intoLog(this.getClass(),tdssid+":tdssid 没有找到这个设备通道，开启失败");
                    rResult.setMessage("没有找到这个设备通道，开启失败");
                    continue;
                }
                audiourl+="<task><index>"+index+"</index><stream>"+flushbonading_ettd.getPullflowurl()+"</stream></task>";
                asrnum++;

                TaskParam taskParam=new TaskParam();
                taskParam.setIndex(index);
                if(null==flushbonading_ettd.getShockenergy()||flushbonading_ettd.getShockenergy().intValue()<1){
                    taskParam.setShockenergy(3500);//默认3500
                }else{
                    taskParam.setShockenergy(flushbonading_ettd.getShockenergy());
                }
                taskParamList.add(taskParam);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }

        if(asrnum==0){
            rResult.setMessage("没有找到设备通道，开启失败");
            return rResult;
        }else if(asrnum>asrchannel){//当要开启的通道大于规定的识别通道数，就给这个语音识别任务最大识别数
            audiourl=audiourl.replace("asrnum",asrchannel+"");
        }else{
            audiourl=audiourl.replace("asrnum",asrnum+"");
        }
        audiourl+="</root>";

        AVSTAsrParam_reg reg=new AVSTAsrParam_reg(asr_et_ettype.getEtip(),asr_et_ettype.getPort()+"",audiourl,asrserverssid, AsrServerModel.m2);
        //参数txtcallbackurl如果接口不传就去缓存中拿
        String avstbacktxtinterface=AsrCache_toout.avstbacktxtinterface;
        if(avstbacktxtinterface.indexOf("localhost") > -1){
            avstbacktxtinterface=avstbacktxtinterface.replace("localhost", OpenUtil.getMyIP());
        }
        reg.setTxtcallbackurl(avstbacktxtinterface);
        RRParam<String> rrParam= AvstAsrImpl.reg(reg);
        String reqendtime= new Date().getTime()+"";//当前时间long ms值，作为asr识别开始时间
        //2给一个线程用于定时刷新心跳
        if(null!=rrParam&&rrParam.getCode()==1){
            String asrid=rrParam.getT();
            String asrtype=param.getAsrtype();//这里需要查数据库，通过asrEquipmentssid，换着写成缓存，找出类型
            AVSTAsrParam_heartbeat avstAsrParam_heartbeat=new AVSTAsrParam_heartbeat(reg.getIp(),reg.getPort(),asrid, AsrServerModel.m2);
            AsrMessageParam<AVSTAsrParam_heartbeat> asr=new AsrMessageParam<AVSTAsrParam_heartbeat>();
            AsrHeartbeatThread<AVSTAsrParam_heartbeat> asrHeartbeatThread=
                    new AsrHeartbeatThread<AVSTAsrParam_heartbeat>(avstAsrParam_heartbeat,asrtype);
            asrHeartbeatThread.start();
            asr.setAsrtype(asrtype);
            asr.setAsrHeartbeatThread(asrHeartbeatThread);

            AsrCache_toout.setAsrMassege(asrid,asr);//写入本次语音识别信息的缓存
            rResult.changeToTrue(asrid);
            rResult.setEndtime(reqendtime);

            //这种1对多的模式需要给每一路音频赋予一个阀值
            AVSTAsrParam_settaskinfo settaskparam=new AVSTAsrParam_settaskinfo(reg.getIp(),reg.getPort(), AsrServerModel.m2);
            settaskparam.setAsrid(asrid);
            settaskparam.setTaskParamList(taskParamList);
            RRParam<Boolean> settaskvo=AvstAsrImpl.settaskinfo(settaskparam);
            if(null!=settaskvo&&settaskvo.getCode()==1){
                LogUtil.intoLog(1,this.getClass(),asrid+":asrid,设置音频流音频能量激活语音阀值 成功");
            }else{
                LogUtil.intoLog(4,this.getClass(),asrid+":asrid,设置音频流音频能量激活语音阀值 失败");
            }
        }else{
            rResult.setMessage("开启语音识别失败");
        }
        return rResult;
    }

    public RResult pauseOrContinueAsr(PauseOrContinueAsrParam param, RResult rResult){

        int PauseOrContinue=param.getPauseOrContinue();
        String asrid=param.getAsrid();
        String asrssid=param.getAsrEquipmentssid();
        //给缓存状态添加一个暂停
        AsrTxtParam asrTxtParam=AsrCache.getAsrByEquipmentssid(asrssid,asrid);
        if(null==asrTxtParam){
            LogUtil.intoLog(this.getClass(),asrssid+":asrssid 没有找到这个asr服务器对应的通道的识别服务,asrid:"+asrid);
            rResult.setMessage("没有找到这个语音识别服务器对应的通道的识别服务");
            return rResult;
        }
        boolean workbool=asrTxtParam.isWorkbool();
        if(PauseOrContinue==1){//要暂停
            if(!workbool){
                rResult.setMessage("已经是暂停状态不需要再一次暂停");
                rResult.changeToTrue();
                return rResult;
            }
            asrTxtParam.setWorkbool(false);
            AsrCache.setAsrByEquipmentssid(asrssid,asrTxtParam);
            rResult.changeToTrue();
        }else if(PauseOrContinue==2){//要继续
            if(workbool){
                rResult.setMessage("已经是工作状态不需要再一次继续");
                rResult.changeToTrue();
                return rResult;
            }
            asrTxtParam.setWorkbool(true);
            AsrCache.setAsrByEquipmentssid(asrssid,asrTxtParam);
            rResult.changeToTrue();
        }else{
            LogUtil.intoLog(this.getClass(),PauseOrContinue+":PauseOrContinue 请求暂停/继续语音识别的状态参数不对,只能是1/2");
            rResult.setMessage("请求暂停/继续语音识别的状态参数不对");
        }

        asrTxtParam.isWorkbool();

        return rResult;
    }


    @Override
    public RResult overAsr(OverAsrParam param,RResult rResult) {

        String asrid=param.getAsrid();
        String asrserverssid=param.getAsrEquipmentssid();
        int asrServerModel=param.getAsrServerModel();
        EntityWrapper ew=new EntityWrapper();
        ew.eq("aet.ssid",asrserverssid);
        Asr_et_ettype asr_et_ettype= asr_etinfoMapper.getAsrinfo(ew);
        if(null==asr_et_ettype){
            LogUtil.intoLog(this.getClass(),asrserverssid+":asrserverssid 没有找到这个asr服务器，开启失败");
            rResult.setMessage("没有找到这个asr服务器，开启失败");
            return rResult;
        }
        String asrServerModel_=asrServerModel==2?AsrServerModel.m2:AsrServerModel.m1;
        AVSTAsrParam_quit qparam=new AVSTAsrParam_quit(asr_et_ettype.getEtip(),asr_et_ettype.getPort()+"",asrid,asrServerModel_);

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
