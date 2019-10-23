package com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.v1;

import com.avst.equipmentcontrol.common.conf.FDType;
import com.avst.equipmentcontrol.common.util.DateUtil;
import com.avst.equipmentcontrol.common.util.JacksonUtil;
import com.avst.equipmentcontrol.common.util.LogUtil;
import com.avst.equipmentcontrol.common.util.baseaction.BaseAction;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.common.util.baseaction.ReqParam;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.req.*;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.v1.service.BaseToOutServiceImpl_qrs;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.v1.service.ToOutService_fd_avst;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.v1.service.ToOutService_qrs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 到底需不需要加密验证的环节，
 * 如果是只给自己人用就不需要，以后给被人用就需要
 * 设备控制的接口
 */
@Controller
@RequestMapping("/flushbonading/v1")
public class ToOutAction_qrs extends BaseAction {

    @Autowired
    private BaseToOutServiceImpl_qrs baseToOutServiceImpl_qrs;

    @Autowired
    private ToOutService_fd_avst toOutService_avst;

    private ToOutService_qrs getToOutServiceImpl(String fdType){
        if(null!=fdType){
            if(fdType.equals(FDType.FD_AVST)){//avst 设备控制
                return toOutService_avst;
            }
        }
        return null;
    }

    //开始工作
    @RequestMapping("/workStart")
    @ResponseBody
    public RResult workStart(@RequestBody ReqParam<WorkStartParam> param){
        RResult rResult=createNewResultOfFail();
        WorkStartParam sparam=param.getParam();
        if(null!=sparam){
            rResult=getToOutServiceImpl(sparam.getFdType()).workStart(sparam,rResult);
        }else{
            LogUtil.intoLog(this.getClass(),"workStart---param.getParam() ---参数异常");
            rResult.setMessage("参数异常");
        }
        return rResult;
    }

    //结束工作
    @RequestMapping("/workOver")
    @ResponseBody
    public RResult workOver(@RequestBody ReqParam<WorkOverParam> param){
        RResult rResult=createNewResultOfFail();
        WorkOverParam sparam=param.getParam();
        if(null!=sparam){
            rResult=getToOutServiceImpl(sparam.getFdType()).workOver(sparam,rResult);
        }else{
            LogUtil.intoLog(this.getClass(),"workOver---param.getParam() ---参数异常");
            rResult.setMessage("参数异常");
        }
        return rResult;
    }

    //暂停/继续工作
    @RequestMapping("/workPauseOrContinue")
    @ResponseBody
    public RResult workPauseOrContinue(@RequestBody ReqParam<WorkPauseOrContinueParam> param){
        RResult rResult=createNewResultOfFail();
        WorkPauseOrContinueParam sparam=param.getParam();
        if(null!=sparam){
            rResult=getToOutServiceImpl(sparam.getFdType()).workPauseOrContinue(sparam,rResult);
        }else{
            LogUtil.intoLog(this.getClass(),"workOver---param.getParam() ---参数异常");
            rResult.setMessage("参数异常");
        }
        return rResult;
    }

    //设备状态查询

    //加上一套设备的增删改查（有一套base？）(后期所有的增删改查都可以合成一套用泛型接受传参，多带一个类型参数就可以了)

    /**
     * 通过审讯主机设备ssid查找审讯主机信息
     * @param param
     * @return
     */
    @RequestMapping("/getFlushbonadingBySsid")
    @ResponseBody
    public RResult getFlushbonadingBySsid(@RequestBody ReqParam<GetFlushbonadingBySsidParam> param){
        RResult rResult=createNewResultOfFail();

        GetFlushbonadingBySsidParam sparam=param.getParam();
        if(null!=sparam){
            rResult=baseToOutServiceImpl_qrs.getFlushbonadingBySsid(sparam,rResult);
        }else{
            LogUtil.intoLog(this.getClass(),"startAsr---param.getParam() ---参数异常");
            rResult.setMessage("参数异常");
        }
        return rResult;
    }

    /**
     * 通过审讯主机设备ssid查找通道信息
     * @param param
     * @return
     */
    @RequestMapping("/getFlushbonadingTDByETSsid")
    @ResponseBody
    public RResult getFlushbonadingTDByETSsid(@RequestBody ReqParam<GetFlushbonadingTDByETSsidParam> param){
        RResult rResult=createNewResultOfFail();

        GetFlushbonadingTDByETSsidParam sparam=param.getParam();
        if(null!=sparam){
            //通过asrEquipmentssid
            rResult=baseToOutServiceImpl_qrs.getFlushbonadingTDByETSsid(sparam,rResult);
        }else{
            LogUtil.intoLog(this.getClass(),"startAsr---param.getParam() ---参数异常");
            rResult.setMessage("参数异常");
        }
        return rResult;
    }


    /**
     * 通过iid在主机设备中找到设备录像的文件列表
     * @param param
     * @return
     */
    @RequestMapping("/getRecordByIid")
    @ResponseBody
    public RResult getRecordByIid(@RequestBody ReqParam<GetRecordByIidParam> param){
        RResult rResult=createNewResultOfFail();

        GetRecordByIidParam sparam=param.getParam();
        if(null!=sparam){
            //通过asrEquipmentssid
            rResult=getToOutServiceImpl(sparam.getFdType()).getRecordByIid(sparam,rResult);
        }else{
            LogUtil.intoLog(this.getClass(),"startAsr---param.getParam() ---参数异常");
            rResult.setMessage("参数异常");
        }
        return rResult;
    }

    //------------------------------------分割线-----------------------------------------
    /**
     * 提供给其他用的，获取liveurl
     * @param param
     * @returngetFDListByFdid
     */
    @RequestMapping("/getFDListByFdid")
    @ResponseBody
    public RResult getFDListByFdid(@RequestBody GetFDListByFdidParam  param){
        RResult rResult=createNewResultOfFail();
        if(null!=param){
            rResult=getToOutServiceImpl(param.getFdType()).getFDListByFdid(param,rResult);
        }else{
            LogUtil.intoLog(this.getClass(),"getFDListByFdid---param.getParam() ---参数异常");
            rResult.setMessage("参数异常");
        }
        return rResult;
    }

    /**
     * 获取当前设备的ftp上传的进度列表
     * @param param
     * @return rResult
     */
    @RequestMapping("/getFTPUploadSpeedByIp")
    @ResponseBody
    public RResult getFTPUploadSpeedByIp(@RequestBody GetFTPUploadSpeedByIpParam  param){
        RResult rResult=createNewResultOfFail();
        if(null!=param){
            rResult=getToOutServiceImpl(param.getFdType()).getFTPUploadSpeedByIp(param,rResult);
        }else{
            LogUtil.intoLog(this.getClass(),"getFTPUploadSpeedByIp---param.getParam() ---参数异常");
            rResult.setMessage("参数异常");
        }
        return rResult;
    }


    @RequestMapping("/getFDState")
    @ResponseBody
    public RResult getFDState(@RequestBody ReqParam<GetFDStateParam> param){
        RResult result=this.createNewResultOfFail();
        if(null!=param.getParam()){
            GetFDStateParam pParam=param.getParam();
            if(null != pParam.getFdType()){
                result=getToOutServiceImpl(pParam.getFdType()).getFDState(pParam,result);
            }
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    @RequestMapping("/startRec_Rom")
    @ResponseBody
    public RResult startRec_Rom(@RequestBody ReqParam<StartRec_RomParam_out> param){
        RResult result=this.createNewResultOfFail();
        if(null!=param.getParam()){
            StartRec_RomParam_out pParam=param.getParam();
            if(null != pParam.getFdType()){
                result=getToOutServiceImpl(pParam.getFdType()).startRec_Rom(pParam,result);
            }
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    @RequestMapping("/stopRec_Rom")
    @ResponseBody
    public RResult stopRec_Rom(@RequestBody ReqParam<StopRec_RomParam_out> param){
        RResult result=this.createNewResultOfFail();
        if(null!=param.getParam()){
            StopRec_RomParam_out pParam=param.getParam();
            if(null != pParam.getFdType()){
                result=getToOutServiceImpl(pParam.getFdType()).stopRec_Rom(pParam,result);
            }
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    @RequestMapping("/pauseOrContinueRec_Rom")
    @ResponseBody
    public RResult pauseOrContinueRec_Rom(@RequestBody ReqParam<PauseOrContinueRec_RomParam_out> param){
        RResult result=this.createNewResultOfFail();
        if(null!=param.getParam()){
            PauseOrContinueRec_RomParam_out pParam=param.getParam();
            if(null != pParam.getFdType()){
                result=getToOutServiceImpl(pParam.getFdType()).pauseOrContinueRec_Rom(pParam,result);
            }
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    @RequestMapping("/dvdOutOrIn")
    @ResponseBody
    public RResult dvdOutOrIn(@RequestBody ReqParam<DvdOutOrInParam_out> param){
        RResult result=this.createNewResultOfFail();
        if(null!=param.getParam()){
            DvdOutOrInParam_out pParam=param.getParam();
            if(null != pParam.getFdType()){
                result=getToOutServiceImpl(pParam.getFdType()).dvdOutOrIn(pParam,result);
            }
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    @RequestMapping("/ptdj")
    @ResponseBody
    public RResult ptdj(@RequestBody ReqParam<PtdjParam_out> param){
        RResult result=this.createNewResultOfFail();
        if(null!=param.getParam()){
            PtdjParam_out pParam=param.getParam();
            if(null != pParam.getFdType()){
                result=getToOutServiceImpl(pParam.getFdType()).ptdj(pParam,result);
            }
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    @RequestMapping("/getptdjconst")
    @ResponseBody
    public RResult getptdjconst(@RequestBody ReqParam<GetptdjconstParam_out> param){
        RResult result=this.createNewResultOfFail();
        if(null!=param.getParam()){
            GetptdjconstParam_out pParam=param.getParam();
            if(null != pParam.getFdType()){
                result=getToOutServiceImpl(pParam.getFdType()).getptdjconst(pParam,result);
            }
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    @RequestMapping("/yuntaiControl")
    @ResponseBody
    public RResult yuntaiControl(@RequestBody ReqParam<YuntaiControlParam_out> param){
        RResult result=this.createNewResultOfFail();
        if(null!=param.getParam()){
            YuntaiControlParam_out pParam=param.getParam();
            if(null != pParam.getFdType()){
                result=getToOutServiceImpl(pParam.getFdType()).yuntaiControl(pParam,result);
            }
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    @RequestMapping("/changeBurnMode")
    @ResponseBody
    public RResult changeBurnMode(@RequestBody ReqParam<ChangeBurnModeParam_out> param){
        RResult result=this.createNewResultOfFail();
        if(null!=param.getParam()){
            ChangeBurnModeParam_out pParam=param.getParam();
            if(null != pParam.getFdType()){
                result=getToOutServiceImpl(pParam.getFdType()).changeBurnMode(pParam,result);
            }
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    @RequestMapping("/getCDNumber")
    @ResponseBody
    public RResult getCDNumber(@RequestBody ReqParam<GetCDNumberParam_out> param){
        RResult result=this.createNewResultOfFail();
        if(null!=param.getParam()){
            GetCDNumberParam_out pParam=param.getParam();
            if(null != pParam.getFdType()){
                result=getToOutServiceImpl(pParam.getFdType()).getCDNumber(pParam,result);
            }
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    /**
     * 获取刻录选时
     * @param param
     * @return
     */
    @RequestMapping("/getBurnTime")
    @ResponseBody
    public RResult getBurnTime(@RequestBody ReqParam<GetBurnTimeParam> param){
        RResult result=this.createNewResultOfFail();
        if(null!=param.getParam()){
            GetBurnTimeParam pParam=param.getParam();
            if(null != pParam.getFdType()){
                result=getToOutServiceImpl(pParam.getFdType()).getBurnTime(pParam,result);
            }
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    /**
     * 修改刻录选时
     * @param param
     * @return
     */
    @RequestMapping("/updateBurnTime")
    @ResponseBody
    public RResult updateBurnTime(@RequestBody ReqParam<GetBurnTimeParam> param){
        RResult result=this.createNewResultOfFail();
        if(null!=param.getParam()){
            GetBurnTimeParam pParam=param.getParam();
            if(null != pParam.getFdType()){
                result=getToOutServiceImpl(pParam.getFdType()).updateBurnTime(pParam,result);
            }
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    /**
     * 日志查询信息
     * @param param
     * @return
     */
    @RequestMapping("/getFDLog")
    @ResponseBody
    public RResult getFDLog(@RequestBody GetFDLogParam_out param){
        RResult result=this.createNewResultOfFail();
        if(null!=param){
            if(null != param.getFdType()){
                result=getToOutServiceImpl(param.getFdType()).getFDLog(param,result);
            }
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }


    /**
     * 获得 设备现场的音频振幅
     * @param param
     * @return
     */
    @RequestMapping("/getFDAudPowerMap")
    @ResponseBody
    public RResult getFDAudPowerMap(@RequestBody GetFDAudPowerMapParam_out param){
        RResult result=this.createNewResultOfFail();
        if(null!=param){
            if(null != param.getFdType()){
                result=getToOutServiceImpl(param.getFdType()).getFDAudPowerMap(param,result);
            }
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }


    /**
     * 配置设备网口 IP、子网掩码、网关
     * @param param
     * @return
     */
    @RequestMapping("/setFDnetwork")
    @ResponseBody
    public RResult setFDnetwork(@RequestBody SetFDnetworkParam_out param){
        RResult result=this.createNewResultOfFail();
        if(null!=param){
            SetFDnetworkParam_out pParam=param;
            if(null != pParam.getFdType()){
                result=getToOutServiceImpl(pParam.getFdType()).setFDnetwork(pParam,result);
            }
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    /**
     * 设置设备某一个通道的通道音量
     * @param param
     * @return
     */
    @RequestMapping("/setFDAudioVolume")
    @ResponseBody
    public RResult setFDAudioVolume(@RequestBody SetFDAudioVolumeParam_out param){
        RResult result=this.createNewResultOfFail();
        if(null!=param){
            SetFDAudioVolumeParam_out pParam=param;
            if(null != pParam.getFdType()){
                result=getToOutServiceImpl(pParam.getFdType()).setFDAudioVolume(pParam,result);
            }
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    /**
     * 获得设备音频配置
     * @param param
     * @return
     */
    @RequestMapping("/getFDAudioConf")
    @ResponseBody
    public RResult getFDAudioConf(@RequestBody GetFDAudioConfParam_out param){
        RResult result=this.createNewResultOfFail();
        if(null!=param){
            GetFDAudioConfParam_out pParam=param;
            if(null != pParam.getFdType()){
                result=getToOutServiceImpl(pParam.getFdType()).getFDAudioConf(pParam,result);
            }
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    /**
     * 获得设备网络配置
     * @param param
     * @return
     */
    @RequestMapping("/getFDNetWork")
    @ResponseBody
    public RResult getFDNetWork(@RequestBody GetFDNetWorkParam_out param){
        RResult result=this.createNewResultOfFail();
        if(null!=param){
            GetFDNetWorkParam_out pParam=param;
            if(null != pParam.getFdType()){
                result=getToOutServiceImpl(pParam.getFdType()).getFDNetWork(pParam,result);
            }
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }





    @RequestMapping(value = "/ceshi" )
    @ResponseBody
    public RResult ceshi(int type,Integer state,String pm){



        RResult rResult=createNewResultOfFail();
        if(type==1){
            ReqParam<WorkStartParam> param=new ReqParam<WorkStartParam>();
            WorkStartParam workStartParam=new WorkStartParam();
            workStartParam.setFdid("123456");
            workStartParam.setFdType(FDType.FD_AVST);
            workStartParam.setFlushbonadingetinfossid("sxsba1");
            param.setParam(workStartParam);
            rResult=workStart(param);
        }else if (type==2){
            ReqParam<WorkOverParam> param=new ReqParam<WorkOverParam>();
            WorkOverParam workOverParam=new WorkOverParam();
            workOverParam.setFdid("123456");
            workOverParam.setFdType(FDType.FD_AVST);
            workOverParam.setFlushbonadingetinfossid("sxsba1");
            param.setParam(workOverParam);
            workOver(param);
        }else if(type==3){

            GetFTPUploadSpeedByIpParam  param=new GetFTPUploadSpeedByIpParam();
            param.setFdType(FDType.FD_AVST);
            param.setFlushbonadingetinfossid("sxsba2");
            getFTPUploadSpeedByIp(param);
        }else if(type==4){

            ReqParam<GetFDStateParam> param=new ReqParam<GetFDStateParam>();
            GetFDStateParam  gparam=new GetFDStateParam();
            gparam.setFdType(FDType.FD_AVST);
            gparam.setFlushbonadingetinfossid("sxsba2");
            param.setParam(gparam);
            System.out.println(JacksonUtil.objebtToString(getFDState(param)));
        }else if(type==5){

            ReqParam<PauseOrContinueRec_RomParam_out> param=new ReqParam<PauseOrContinueRec_RomParam_out>();
            PauseOrContinueRec_RomParam_out  gparam=new PauseOrContinueRec_RomParam_out();
            gparam.setFdType(FDType.FD_AVST);
            gparam.setFlushbonadingetinfossid("sxsba2");
            gparam.setPauseOrContinue(state);
            param.setParam(gparam);
            System.out.println(JacksonUtil.objebtToString(pauseOrContinueRec_Rom(param)));
        }else if(type==6){

            ReqParam<StartRec_RomParam_out> param=new ReqParam<StartRec_RomParam_out>();
            StartRec_RomParam_out  gparam=new StartRec_RomParam_out();
            gparam.setFdType(FDType.FD_AVST);
            gparam.setFlushbonadingetinfossid("sxsba2");
            gparam.setBurntime(1);
            gparam.setIid("cf25d0b26aee44629adb72368ab88db7_sxsba2");
            gparam.setDx(0);
            param.setParam(gparam);
            System.out.println(JacksonUtil.objebtToString(startRec_Rom(param)));
        }else if(type==7){

            ReqParam<StopRec_RomParam_out> param=new ReqParam<StopRec_RomParam_out>();
            StopRec_RomParam_out  gparam=new StopRec_RomParam_out();
            gparam.setFdType(FDType.FD_AVST);
            gparam.setFlushbonadingetinfossid("sxsba2");
            gparam.setDx(0);
            param.setParam(gparam);
            System.out.println(JacksonUtil.objebtToString(stopRec_Rom(param)));
        }else if(type==8){

            ReqParam<GetCDNumberParam_out> param=new ReqParam<GetCDNumberParam_out>();
            GetCDNumberParam_out  gparam=new GetCDNumberParam_out();
            gparam.setFdType(FDType.FD_AVST);
            gparam.setFlushbonadingetinfossid("sxsba2");
            param.setParam(gparam);
            System.out.println(JacksonUtil.objebtToString(getCDNumber(param)));
        }else if(type==9){

            ReqParam<ChangeBurnModeParam_out> param=new ReqParam<ChangeBurnModeParam_out>();
            ChangeBurnModeParam_out  gparam=new ChangeBurnModeParam_out();
            gparam.setFdType(FDType.FD_AVST);
            gparam.setFlushbonadingetinfossid("sxsba2");
            gparam.setDx(state);
            param.setParam(gparam);
            System.out.println(JacksonUtil.objebtToString(changeBurnMode(param)));
        }else if(type==10){

            SetFDnetworkParam_out  gparam=new SetFDnetworkParam_out();
            gparam.setFdType(FDType.FD_AVST);
            gparam.setFlushbonadingetinfossid("sxsba2");
            gparam.setAjust(1);
            gparam.setDev("eth1");
            gparam.setGateway("192.1.1.1");
            gparam.setIp_new("1.1.1.1");
            gparam.setNetmask("255.255.0.0");
            System.out.println("setFDnetwork--"+JacksonUtil.objebtToString(setFDnetwork(gparam)));
        }else if(type==11){

            GetFDLogParam_out  gparam=new GetFDLogParam_out();
            gparam.setFdType(FDType.FD_AVST);
            gparam.setFlushbonadingetinfossid("sxsba2");
            gparam.setFd(19);
            gparam.setFm(9);
            gparam.setFy(2019);
            gparam.setLogtype(0);
            gparam.setPage(2);
            System.out.println("getFDLog--"+JacksonUtil.objebtToString(getFDLog(gparam)));
        }else if(type==12){

            GetFDAudPowerMapParam_out  gparam=new GetFDAudPowerMapParam_out();
            gparam.setFdType(FDType.FD_AVST);
            gparam.setFlushbonadingetinfossid("sxsba2");
            System.out.println("getFDAudPowerMap--"+JacksonUtil.objebtToString(getFDAudPowerMap(gparam)));
        }else if(type==13){

            GetFDAudioConfParam_out  gparam=new GetFDAudioConfParam_out();
            gparam.setFdType(FDType.FD_AVST);
            gparam.setFlushbonadingetinfossid("sxsba2");
            System.out.println("getFDAudPowerMap--"+JacksonUtil.objebtToString(getFDAudioConf(gparam)));
        }else if(type==14){

            GetFDNetWorkParam_out  gparam=new GetFDNetWorkParam_out();
            gparam.setFdType(FDType.FD_AVST);
            gparam.setFlushbonadingetinfossid("sxsba2");
            System.out.println("getFDAudPowerMap--"+JacksonUtil.objebtToString(getFDNetWork(gparam)));
        }

        return rResult;
    }

}
