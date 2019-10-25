package com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.v1.service;

import com.avst.equipmentcontrol.common.conf.FDType;
import com.avst.equipmentcontrol.common.conf.SSType;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.flushbonading.entity.Flushbonading_etinfo;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.flushbonading.entity.param.Flushbonadinginfo;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.flushbonading.mapper.Flushbonading_etinfoMapper;
import com.avst.equipmentcontrol.common.util.DateUtil;
import com.avst.equipmentcontrol.common.util.JacksonUtil;
import com.avst.equipmentcontrol.common.util.LogUtil;
import com.avst.equipmentcontrol.common.util.OpenUtil;
import com.avst.equipmentcontrol.common.util.baseaction.Code;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.FDDealImpl;
import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.req.*;
import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.vo.*;
import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.xmljsonobject.CheckFDStateXml;
import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.xmljsonobject.param.Aud;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.cache.FDCache;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.cache.param.FDCacheParam;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.req.*;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.vo.GetFDAudioConfVO;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.vo.GetFDOSDVO;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.vo.GetFDStateVO;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.vo.Param.CoordinateParam;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.vo.Param.GetAudioConfVO_param;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.vo.WorkStartVO;
import com.avst.equipmentcontrol.outside.interfacetoout.storage.req.SaveFileParam;
import com.avst.equipmentcontrol.outside.interfacetoout.storage.v1.service.ToOutServiceImpl_ss_avst;
import com.avst.equipmentcontrol.outside.interfacetoout.storage.v1.service.ToOutService_ss;
import com.avst.equipmentcontrol.web.service.FlushbonadingService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * avst设备的对外接口处理
 */
@Service
public class ToOutService_fd_avst implements ToOutService_qrs{

    @Autowired
    private FDDealImpl fdDeal;

    @Autowired
    private Flushbonading_etinfoMapper flushbonading_etinfoMapper;

    @Autowired
    private FlushbonadingService flushbonadingService;

    @Autowired
    private ToOutServiceImpl_ss_avst toOutServiceImpl_ss_avst;

    private ToOutService_ss getToOutServiceImpl(String type){//获取存储处理对象

        if(null!=type&&type.equals(SSType.AVST)){
            return toOutServiceImpl_ss_avst;
        }
        return null;
    }

    public RResult workStart(WorkStartParam param,RResult result){

        String fdid=param.getFdid();
        String fdssid=param.getFlushbonadingetinfossid();
        //查询是否有一台机子多次被用
        FDCacheParam fdCacheParam=FDCache.getFDByFDSsid(fdid,fdssid);
        if(null!=fdCacheParam){
            WorkStartVO workStartVO=new WorkStartVO();
            workStartVO.setFdlivingurl(fdCacheParam.getLivingUrl());
            workStartVO.setFdpreviewurl(fdCacheParam.getPreviewurl());
            workStartVO.setIid(fdCacheParam.getRecordFileiid());
            workStartVO.setStartrecordtime(fdCacheParam.getRecordStartTime());
            result.changeToTrue(workStartVO);
            result.setMessage("设备已经开始工作");
            return result;
        }

        //查询数据库找到设备
        EntityWrapper<Flushbonading_etinfo> ew=new EntityWrapper<Flushbonading_etinfo>();
        ew.eq("fet.ssid",fdssid);
        Flushbonadinginfo flushbonadinginfo=flushbonading_etinfoMapper.getFlushbonadinginfo(ew);
        if(null==flushbonadinginfo){
            result.setMessage("设备未找到，请查询数据");
            return result;
        }

        long leastRecordTime=FDCache.getLeastRecordTime(fdssid);//最后一次关闭录像时间
        long nowtime=(new Date()).getTime();
        if((nowtime - leastRecordTime) < FDCache.minRecordinterval){
            result.setMessage("小于最小关闭再开启间隔时间，请稍后开启，最小开启间隔时间（秒）："+FDCache.minRecordinterval);
            return result;
        }

        //开启设备工作
        StartRecParam sparam=new StartRecParam();
        sparam.setAl("1");//
        String iid=fdid+"_"+fdssid;
        sparam.setIid(iid);//把会议的ssid+设备的唯一标识当做唯一标识传给设备
        sparam.setIp(flushbonadinginfo.getEtip());
        sparam.setPort(flushbonadinginfo.getPort());
        sparam.setPasswd(flushbonadinginfo.getPasswd());
        sparam.setUser(flushbonadinginfo.getUser());
        result=fdDeal.startRec(sparam,result);

        long startrecordtime=(new Date()).getTime();//录音开始时间 ms
        if(null==result){
            result=new RResult();
            result.setMessage("开启设备录像失败");
            LogUtil.intoLog(this.getClass(),"开启设备录像失败 result："+result);
        }else{
            if(null!=result&&result.getActioncode().equals(Code.SUCCESS.toString())){
                fdCacheParam=new FDCacheParam();
                fdCacheParam.setFdSsid(fdssid);
                fdCacheParam.setFdType(FDType.FD_AVST);
                fdCacheParam.setLivingUrl(flushbonadinginfo.getLivingurl());
                fdCacheParam.setPreviewurl(flushbonadinginfo.getPreviewurl());
                fdCacheParam.setPort(flushbonadinginfo.getPort());
                fdCacheParam.setUseRecord(1);
                fdCacheParam.setIp(flushbonadinginfo.getEtip());
                fdCacheParam.setPasswd(flushbonadinginfo.getPasswd());
                fdCacheParam.setUser(flushbonadinginfo.getUser());
                fdCacheParam.setRecordFileiid(iid);
                fdCacheParam.setWorkbool(true);
                fdCacheParam.setBurnbool(flushbonadinginfo.getBurnbool());
                fdCacheParam.setRecordStartTime(startrecordtime);
                FDCache.setFD(fdid,fdCacheParam);

                WorkStartVO workStartVO=new WorkStartVO();
                workStartVO.setFdlivingurl(flushbonadinginfo.getLivingurl());
                workStartVO.setFdpreviewurl(flushbonadinginfo.getPreviewurl());
                workStartVO.setIid(iid);
                workStartVO.setStartrecordtime(startrecordtime);
                result.changeToTrue(workStartVO);
            }
        }

        //检测是否需要开启光盘刻录
        Integer burnbool=flushbonadinginfo.getBurnbool();
        if(null!=burnbool&&burnbool==1){//需要进行光盘刻录
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        StartRec_RomParam  gparam=new StartRec_RomParam();
                        String iid=fdid+"_"+fdssid;
                        gparam.setIid(iid);//把会议的ssid+设备的唯一标识当做唯一标识传给设备
                        gparam.setIp(flushbonadinginfo.getEtip());
                        gparam.setPort(flushbonadinginfo.getPort());
                        gparam.setPasswd(flushbonadinginfo.getPasswd());
                        gparam.setUser(flushbonadinginfo.getUser());
                        Integer burntime=flushbonadinginfo.getBurntime();
                        if(null==burntime||burntime<1){
                            burntime=6;
                        }
                        gparam.setBtime(burntime);
                        gparam.setDx(0);

                        //需要查询设备状态获取刻录模式
                        CheckFDStateParam cparam=new CheckFDStateParam();
                        cparam.setIp(flushbonadinginfo.getEtip());
                        cparam.setPort(flushbonadinginfo.getPort());
                        cparam.setPasswd(flushbonadinginfo.getPasswd());
                        cparam.setUser(flushbonadinginfo.getUser());
                        RResult<CheckFDStateVO> ckvo=new RResult<CheckFDStateVO>();
                        try {
                            ckvo= fdDeal.CheckFDState(cparam,ckvo);
                            if(null!=ckvo&&ckvo.getActioncode().equals(Code.SUCCESS.toString())&&null!=ckvo.getData()){
                                CheckFDStateVO vo=ckvo.getData();
                                Gson gson=new Gson();
                                CheckFDStateXml checkFDStateXml=gson.fromJson(gson.toJson(vo.getData()),CheckFDStateXml.class);
                                String burnmode=checkFDStateXml.getBurn_mode();
                                if(StringUtils.isNotEmpty(burnmode)&&burnmode.equals("2")){
                                    gparam.setBmode("exchange");
                                    LogUtil.intoLog(1,this.getClass(),"光盘刻录模式为exchange");
                                }else{
                                    LogUtil.intoLog(1,this.getClass(),"光盘刻录模式为bmode");
                                }
                            }else{
                                LogUtil.intoLog(4,this.getClass(),"请求设备状态失败，主要是为了找光盘刻录模式");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        RResult<StartRec_RomVO> startrec=fdDeal.startRec_Rom(gparam,new RResult<StartRec_RomVO>());
                        LogUtil.intoLog(1,this.getClass(),"请求光盘开始刻录结果："+JacksonUtil.objebtToString(startrec));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }

        return result;
    }


    public RResult workOver(WorkOverParam param, RResult result){

        String fdid=param.getFdid();
        String fdssid=param.getFlushbonadingetinfossid();
        //查询是否存在任务
        FDCacheParam fdCacheParam=FDCache.getFDByFDSsid(fdid,fdssid);
        if(null==fdCacheParam){
            result.setMessage("该设备可能已经停止录像了");//后期还需要查一遍状态
            result.changeToTrue();
            return result;
        }

        //关闭设备工作
        StopRecParam sparam=new StopRecParam();
        sparam.setIp(fdCacheParam.getIp());
        sparam.setPort(fdCacheParam.getPort());
        sparam.setPasswd(fdCacheParam.getPasswd());
        sparam.setUser(fdCacheParam.getUser());
        result=fdDeal.stopRec(sparam,result);
        if(null==result){
            result=new RResult();
            result.setMessage("关闭设备录像失败");
            LogUtil.intoLog(this.getClass(),"关闭设备录像失败 result："+result);
        }else{
            if(null!=result&&result.getActioncode().equals(Code.SUCCESS.toString())){

                //准备存储的处理
                String sstype=SSType.AVST;
                SaveFileParam saveFileParam=new SaveFileParam();
                saveFileParam.setIid(fdCacheParam.getRecordFileiid());
                saveFileParam.setSourseIp(fdCacheParam.getIp());
                saveFileParam.setSsType(sstype);
                saveFileParam.setSourseFDETSsid(fdssid);
                saveFileParam.setStartrecordtime(fdCacheParam.getRecordStartTime());
                RResult result2=new RResult();
                result2=getToOutServiceImpl(sstype).saveFile(saveFileParam,result2);//v1默认给avst版的存储服务
                if(null!=result2&&result2.getActioncode().equals(Code.SUCCESS.toString())){
                    LogUtil.intoLog(this.getClass(),"推送设备保存文件到服务器成功fdssid："+fdssid);
                }else{
                    LogUtil.intoLog(this.getClass(),"推送设备保存文件到服务器失败 fdssid："+fdssid);
                }

                result.setData(fdCacheParam.getRecordFileiid());
                FDCache.delFDList(fdid);

                FDCache.setLeastRecordTime(fdssid,(new Date()).getTime());//最后一次关闭录像时间
            }
        }

        //查看是否需要把光盘刻录结束掉
        Integer burnbool=fdCacheParam.getBurnbool();
        if(null!=burnbool&&burnbool==1){//需要进行光盘刻录
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        StopRec_RomParam  gparam=new StopRec_RomParam();
                        gparam.setIp(fdCacheParam.getIp());
                        gparam.setPort(fdCacheParam.getPort());
                        gparam.setPasswd(fdCacheParam.getPasswd());
                        gparam.setUser(fdCacheParam.getUser());
                        gparam.setDx(0);

                        RResult<StopRec_RomVO> stoprec=fdDeal.stopRec_Rom(gparam,new RResult<StopRec_RomVO>());
                        LogUtil.intoLog(1,this.getClass(),"请求光盘刻录结束的结果："+JacksonUtil.objebtToString(stoprec));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }


        return result;
    }

    public RResult workOver_Accident(WorkOver_AccidentParam param, RResult result){

        String fdid=param.getFdid();
        String fdssid=param.getFlushbonadingetinfossid();
        //查询是否存在任务
        if(null!=FDCache.getFDByFDSsid(fdid,fdssid)){//按正常关闭来
            return workOver(param,result);
        }

        //查询数据库找到设备
        EntityWrapper<Flushbonading_etinfo> ew=new EntityWrapper<Flushbonading_etinfo>();
        ew.eq("fet.ssid",fdssid);
        Flushbonadinginfo flushbonadinginfo=flushbonading_etinfoMapper.getFlushbonadinginfo(ew);
        if(null==flushbonadinginfo){
            result.setMessage("设备未找到，请查询数据");
            return result;
        }

        String iid=param.getIid();
        if(StringUtils.isEmpty(iid)){
            iid=fdid+"_"+fdssid;//avstmc的iid的组合默认是会议ssid_设备ssid
        }

        if(param.isCloaseRecbool()){//需要关闭设备录像
            //关闭设备工作
            StopRecParam sparam=new StopRecParam();
            sparam.setIp(flushbonadinginfo.getEtip());
            sparam.setPort(flushbonadinginfo.getPort());
            sparam.setPasswd(flushbonadinginfo.getPasswd());
            sparam.setUser(flushbonadinginfo.getUser());
            result=fdDeal.stopRec(sparam,result);
            if(null==result||!result.getActioncode().equals(Code.SUCCESS.toString())){
                LogUtil.intoLog(4,this.getClass(),"workOver_Accident 关闭设备录像失败 没有什么影响，继续执行");
            }
        }

        //准备存储的处理
        String sstype=SSType.AVST;
        SaveFileParam saveFileParam=new SaveFileParam();
        saveFileParam.setIid(iid);
        saveFileParam.setSourseIp(flushbonadinginfo.getEtip());
        saveFileParam.setSsType(sstype);
        saveFileParam.setSourseFDETSsid(fdssid);
        saveFileParam.setStartrecordtime(param.getMtRecordTime());
        RResult result2=new RResult();
        result2=getToOutServiceImpl(sstype).saveFile(saveFileParam,result2);//v1默认给avst版的存储服务
        if(null!=result2&&result2.getActioncode().equals(Code.SUCCESS.toString())){
            LogUtil.intoLog(this.getClass(),"推送设备保存文件到服务器成功fdssid："+fdssid);
        }else{
            LogUtil.intoLog(this.getClass(),"推送设备保存文件到服务器失败 fdssid："+fdssid);
        }

        result.setData(iid);
        FDCache.delFDList(fdid);

        FDCache.setLeastRecordTime(fdssid,(new Date()).getTime());//最后一次关闭录像时间

        //查看是否需要把光盘刻录结束掉
        Integer burnbool=flushbonadinginfo.getBurnbool();
        if(null!=burnbool&&burnbool==1){//需要进行光盘刻录
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        StopRec_RomParam  gparam=new StopRec_RomParam();
                        gparam.setIp(flushbonadinginfo.getEtip());
                        gparam.setPort(flushbonadinginfo.getPort());
                        gparam.setPasswd(flushbonadinginfo.getPasswd());
                        gparam.setUser(flushbonadinginfo.getUser());
                        gparam.setDx(0);

                        RResult<StopRec_RomVO> stoprec=fdDeal.stopRec_Rom(gparam,new RResult<StopRec_RomVO>());
                        LogUtil.intoLog(1,this.getClass(),"请求光盘刻录结束的结果："+JacksonUtil.objebtToString(stoprec));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        return result;
    }

    public RResult workPauseOrContinue(WorkPauseOrContinueParam param, RResult result){

        String iid =param.getIid();
        int pauseOrContinue=param.getPauseOrContinue();
        String fdssid=param.getFlushbonadingetinfossid();
        String fdid=param.getFdid();

        //查询数据库找到设备
        EntityWrapper<Flushbonading_etinfo> ew=new EntityWrapper<Flushbonading_etinfo>();
        ew.eq("fet.ssid",fdssid);
        Flushbonadinginfo flushbonadinginfo=flushbonading_etinfoMapper.getFlushbonadinginfo(ew);
        if(null==flushbonadinginfo){
            result.setMessage("设备未找到，请查询数据");
            return result;
        }

        if(pauseOrContinue==1){
            //查询是否存在任务
            FDCacheParam fdCacheParam=FDCache.getFDByFDSsid(fdid,fdssid);
            if(null==fdCacheParam){
                result.setMessage("该设备可能已经停止录像了，不需要暂停");
                return result;
            }
            if(!fdCacheParam.isWorkbool()){
                result.setMessage("该设备已经暂停");
                result.changeToTrue();
                return result;
            }

            //关闭设备工作
            StopRecParam sparam=new StopRecParam();
            sparam.setIp(fdCacheParam.getIp());
            sparam.setPort(fdCacheParam.getPort());
            sparam.setPasswd(fdCacheParam.getPasswd());
            sparam.setUser(fdCacheParam.getUser());
            result=fdDeal.stopRec(sparam,result);
            if(null==result){
                result=new RResult();
                result.setMessage("关闭设备录像失败");
                LogUtil.intoLog(this.getClass(),"关闭设备录像失败,请求设备竟然返回了空，可能设备连接中断了 ");
            }else{
                if(result.getActioncode().equals(Code.SUCCESS.toString())){
                    fdCacheParam.setWorkbool(false);
                    FDCache.setFD(fdid,fdCacheParam);
                    FDCache.setLeastRecordTime(fdssid,(new Date()).getTime());//最后一次关闭录像时间
                }else{
                    LogUtil.intoLog(this.getClass(),"暂停设备录像失败 result："+JacksonUtil.objebtToString(result));
                }
            }

            //暂停光盘刻录
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {

                        PauseRec_RomParam  gparam=new PauseRec_RomParam();
                        gparam.setIp(fdCacheParam.getIp());
                        gparam.setPort(fdCacheParam.getPort());
                        gparam.setPasswd(fdCacheParam.getPasswd());
                        gparam.setUser(fdCacheParam.getUser());

                        RResult<PauseRec_RomVO> vo=fdDeal.pauseRec_Rom(gparam,new RResult<PauseRec_RomVO>());
                        LogUtil.intoLog(1,this.getClass(),"请求光盘暂停刻录的结果："+JacksonUtil.objebtToString(vo));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();


        }else if(pauseOrContinue==2){

            //查询是否有一台机子多次被用
            FDCacheParam fdCacheParam=FDCache.getFDByFDSsid(fdid,fdssid);

            if(null==fdCacheParam){
                result.setMessage("该设备可能已经停止录像了，不能继续录像");
                return result;
            }

            if(fdCacheParam.isWorkbool()){
                result.setMessage("该设备已经在工作中，不需要继续");
                result.changeToTrue();
                return result;
            }

            long leastRecordTime=FDCache.getLeastRecordTime(fdssid);//最后一次关闭录像时间
            long nowtime=(new Date()).getTime();
            if((nowtime - leastRecordTime) < FDCache.minRecordinterval){
                result.setMessage("小于最小关闭再开启间隔时间，请稍后开启，最小开启间隔时间（秒）："+FDCache.minRecordinterval);
                LogUtil.intoLog(4,this.getClass(),"小于最小关闭再开启间隔时间，请稍后开启，最小开启间隔时间（秒）："+FDCache.minRecordinterval);
                return result;
            }

            //开启设备工作
            StartRecParam sparam=new StartRecParam();
            sparam.setAl("1");//
            sparam.setIid(iid);
            sparam.setIp(flushbonadinginfo.getEtip());
            sparam.setPort(flushbonadinginfo.getPort());
            sparam.setPasswd(flushbonadinginfo.getPasswd());
            sparam.setUser(flushbonadinginfo.getUser());
            result=fdDeal.startRec(sparam,result);

            if(null==result){
                result=new RResult();
                result.setMessage("开启设备录像失败");
                LogUtil.intoLog(this.getClass(),"开启设备录像失败 result："+result);
            }else{
                if(result.getActioncode().equals(Code.SUCCESS.toString())){
                    fdCacheParam.setWorkbool(true);
                    FDCache.setFD(fdid,fdCacheParam);
                }else{
                    LogUtil.intoLog(this.getClass(),"继续设备录像失败 result："+JacksonUtil.objebtToString(result));
                }
            }

            //继续光盘刻录
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {

                        GoonRec_RomParam  gparam=new GoonRec_RomParam();
                        gparam.setIp(fdCacheParam.getIp());
                        gparam.setPort(fdCacheParam.getPort());
                        gparam.setPasswd(fdCacheParam.getPasswd());
                        gparam.setUser(fdCacheParam.getUser());

                        RResult<GgoonRec_RomVO> vo=fdDeal.goonRec_Rom(gparam,new RResult<GgoonRec_RomVO>());
                        LogUtil.intoLog(1,this.getClass(),"请求光盘继续刻录的结果："+JacksonUtil.objebtToString(vo));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }else{
            LogUtil.intoLog(3,this.getClass(),"请求动作不是暂停或者继续设备工作,pauseOrContinue："+pauseOrContinue);
            result.setMessage("请求动作不是暂停或者继续设备工作："+pauseOrContinue);
        }

        return result;
    }


    public RResult getRecordByIid(GetRecordByIidParam param, RResult rResult){

        String equipmentSsid=param.getFlushbonadingetinfossid();

        EntityWrapper<Flushbonadinginfo> entityWrapper=new EntityWrapper<Flushbonadinginfo>();
        entityWrapper.eq("fet.ssid",equipmentSsid);

        try {
            Flushbonadinginfo flushbonadinginfo=flushbonading_etinfoMapper.getFlushbonadinginfo(entityWrapper);
            if(null!=flushbonadinginfo){

                GetETRecordByIidParam recordparam=new GetETRecordByIidParam();
                recordparam.setRec_id(param.getIid());
                recordparam.setIp(flushbonadinginfo.getEtip());
                recordparam.setUser(flushbonadinginfo.getUser());
                recordparam.setPort(flushbonadinginfo.getPort());
                recordparam.setPasswd(flushbonadinginfo.getPasswd());
                rResult=fdDeal.getETRecordByIid(recordparam,rResult);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rResult;
    }


    public RResult getFDListByFdid(GetFDListByFdidParam param,RResult result){
        String fdid=param.getFdid();
        if (StringUtils.isBlank(fdid)){
            LogUtil.intoLog(this.getClass(),"getFDListByFdid__fdid__"+fdid);
            result.setMessage("参数为空");
            return result;
        }
        List<FDCacheParam> list= FDCache.getFDList(fdid);
        result.changeToTrue(list);
        return  result;
    }

    public RResult getFTPUploadSpeedByIp(GetFTPUploadSpeedByIpParam param,RResult result){
        String fdssid=param.getFlushbonadingetinfossid();
        if (StringUtils.isBlank(fdssid)){
            LogUtil.intoLog(this.getClass(),"getFTPUploadSpeedByIp"+fdssid);
            result.setMessage("参数为空");
            return result;
        }

        //查询数据库找到设备
        EntityWrapper<Flushbonading_etinfo> ew=new EntityWrapper<Flushbonading_etinfo>();
        ew.eq("fet.ssid",fdssid);
        Flushbonadinginfo flushbonadinginfo=flushbonading_etinfoMapper.getFlushbonadinginfo(ew);
        if(null==flushbonadinginfo){
            result.setMessage("设备未找到，请查询数据");
            return result;
        }

        GetFTPUploadSpeedParam fparam=new GetFTPUploadSpeedParam();
        fparam.setIp(flushbonadinginfo.getEtip());
        fparam.setUser(flushbonadinginfo.getUser());
        fparam.setPort(flushbonadinginfo.getPort());
        fparam.setPasswd(flushbonadinginfo.getPasswd());
        result=fdDeal.getFTPUploadSpeed(fparam,result);
        if(null==result){
            result=new RResult();
            result.setMessage("查询设备当前ftp上传进度失败");
            LogUtil.intoLog(this.getClass(),"查询设备当前ftp上传进度失败 fdssid："+fdssid);
        }else{
            if(null!=result&&result.getActioncode().equals(Code.SUCCESS.toString())){

                System.out.println(JacksonUtil.objebtToString(result)+"请求成功");

            }else{
                LogUtil.intoLog(this.getClass(),"查询设备当前ftp上传进度返回失败,result.getMessage()："+result.getMessage());
            }
        }


        return  result;
    }

    public RResult getFDState(GetFDStateParam param,RResult result){
        int statetype=param.getStateType();

        String fdssid=param.getFlushbonadingetinfossid();
        if (StringUtils.isBlank(fdssid)){
            LogUtil.intoLog(this.getClass(),"param.getFdssid():"+fdssid);
            result.setMessage("参数为空");
            return result;
        }

        //查询数据库找到设备
        EntityWrapper<Flushbonading_etinfo> ew=new EntityWrapper<Flushbonading_etinfo>();
        ew.eq("fet.ssid",fdssid);
        Flushbonadinginfo flushbonadinginfo=flushbonading_etinfoMapper.getFlushbonadinginfo(ew);
        if(null==flushbonadinginfo){
            result.setMessage("设备未找到，请查询数据");
            return result;
        }

        CheckFDStateParam stateparam=new CheckFDStateParam();
        stateparam.setPort(flushbonadinginfo.getPort());
        stateparam.setIp(flushbonadinginfo.getEtip());
        stateparam.setPasswd(flushbonadinginfo.getPasswd());
        stateparam.setUser(flushbonadinginfo.getUser());
        stateparam.setStateType(statetype);
        RResult<CheckFDStateVO> rResult2=new RResult();
        rResult2=fdDeal.CheckFDState(stateparam,rResult2);
        if(null!=rResult2&&rResult2.getActioncode().equals(Code.SUCCESS.toString())&&null!=rResult2.getData()){

            CheckFDStateVO checkFDStateVO=rResult2.getData();
            Gson gson=new Gson();

            GetFDStateVO vo=gson.fromJson(gson.toJson(checkFDStateVO.getData()),GetFDStateVO.class);
            result.changeToTrue(vo);
        }else{
            LogUtil.intoLog(4,this.getClass(),"请求fdDeal.CheckFDState异常，没有数据，rResult2："+JacksonUtil.objebtToString(rResult2));
        }
        return  result;
    }


    @Override
    /**
     * 这里只是临时的开启刻盘，不能影响硬盘的录制
     * 开启设备哪里才会有是否关联硬盘录像
     */
    public RResult startRec_Rom(StartRec_RomParam_out param, RResult result) {

        int dx=param.getDx();
        String iid=param.getIid();
        String bmode=param.getBmode();
        int burntime=param.getBurntime();

        String fdssid=param.getFlushbonadingetinfossid();
        if (StringUtils.isBlank(fdssid)){
            LogUtil.intoLog(this.getClass(),"param.getFdssid():"+fdssid);
            result.setMessage("参数为空");
            return result;
        }

        //查询数据库找到设备
        EntityWrapper<Flushbonading_etinfo> ew=new EntityWrapper<Flushbonading_etinfo>();
        ew.eq("fet.ssid",fdssid);
        Flushbonadinginfo flushbonadinginfo=flushbonading_etinfoMapper.getFlushbonadinginfo(ew);
        if(null==flushbonadinginfo){
            result.setMessage("设备未找到，请查询数据");
            return result;
        }

        StartRec_RomParam startRec_romParam=new StartRec_RomParam();

        startRec_romParam.setAldisk(0);
        startRec_romParam.setDisconly(1);//这一对组合参数要保证只需要光盘刻录不管硬盘的操作

        startRec_romParam.setBmode(bmode);
        startRec_romParam.setDx(dx);
        startRec_romParam.setIid(iid);
        if(burntime==0){//默认给6小时的刻录时间
            burntime=flushbonadinginfo.getBurntime()==null?6:flushbonadinginfo.getBurntime();
        }
        startRec_romParam.setBtime(burntime);
        startRec_romParam.setPort(flushbonadinginfo.getPort());
        startRec_romParam.setIp(flushbonadinginfo.getEtip());
        startRec_romParam.setPasswd(flushbonadinginfo.getPasswd());
        startRec_romParam.setUser(flushbonadinginfo.getUser());

        RResult<StartRec_RomVO> result3=new RResult<StartRec_RomVO>();
        result3=fdDeal.startRec_Rom(startRec_romParam,result3);
        if(null!=result3&&result3.getActioncode().equals(Code.SUCCESS.toString())){
            result.changeToTrue(true);
        }else{
            result.setMessage("请求单独的光盘刻录失败");
        }

        return result;
    }

    @Override
    public RResult stopRec_Rom(StopRec_RomParam_out param, RResult result) {

        int dx=param.getDx();

        String fdssid=param.getFlushbonadingetinfossid();
        if (StringUtils.isBlank(fdssid)){
            LogUtil.intoLog(this.getClass(),"param.getFdssid():"+fdssid);
            result.setMessage("参数为空");
            return result;
        }

        //查询数据库找到设备
        EntityWrapper<Flushbonading_etinfo> ew=new EntityWrapper<Flushbonading_etinfo>();
        ew.eq("fet.ssid",fdssid);
        Flushbonadinginfo flushbonadinginfo=flushbonading_etinfoMapper.getFlushbonadinginfo(ew);
        if(null==flushbonadinginfo){
            result.setMessage("设备未找到，请查询数据");
            return result;
        }

        StopRec_RomParam stopRec_Romparam=new StopRec_RomParam();
        stopRec_Romparam.setDx(dx);
        stopRec_Romparam.setPort(flushbonadinginfo.getPort());
        stopRec_Romparam.setIp(flushbonadinginfo.getEtip());
        stopRec_Romparam.setPasswd(flushbonadinginfo.getPasswd());
        stopRec_Romparam.setUser(flushbonadinginfo.getUser());
        RResult<StopRec_RomVO> result2=new RResult<StopRec_RomVO>();
        result2=fdDeal.stopRec_Rom(stopRec_Romparam,result2);

        if(null!=result2&&result2.getActioncode().equals(Code.SUCCESS.toString())){
            result.changeToTrue(true);
        }else{
            result.setMessage("请求单独的光盘结束刻录失败");
        }

        return result;
    }

    @Override
    public RResult pauseOrContinueRec_Rom(PauseOrContinueRec_RomParam_out param, RResult result) {

        String fdssid=param.getFlushbonadingetinfossid();

        int pauseOrContinue=param.getPauseOrContinue();
        if (StringUtils.isBlank(fdssid)){
            LogUtil.intoLog(this.getClass(),"param.getFdssid():"+fdssid);
            result.setMessage("参数为空");
            return result;
        }

        //查询数据库找到设备
        EntityWrapper<Flushbonading_etinfo> ew=new EntityWrapper<Flushbonading_etinfo>();
        ew.eq("fet.ssid",fdssid);
        Flushbonadinginfo flushbonadinginfo=flushbonading_etinfoMapper.getFlushbonadinginfo(ew);
        if(null==flushbonadinginfo){
            result.setMessage("设备未找到，请查询数据");
            return result;
        }

        if(pauseOrContinue==1){
            PauseRec_RomParam pauseRec_romParam=new PauseRec_RomParam();
            pauseRec_romParam.setPort(flushbonadinginfo.getPort());
            pauseRec_romParam.setIp(flushbonadinginfo.getEtip());
            pauseRec_romParam.setPasswd(flushbonadinginfo.getPasswd());
            pauseRec_romParam.setUser(flushbonadinginfo.getUser());
            RResult<PauseRec_RomVO> result2=new RResult<PauseRec_RomVO>();
            result2=fdDeal.pauseRec_Rom(pauseRec_romParam,result2);

            if(null!=result2&&result2.getActioncode().equals(Code.SUCCESS.toString())){
                result.changeToTrue(true);
            }else{
                result.setMessage("请求单独的光盘暂停刻录失败");
            }
        }else if (pauseOrContinue==2){
            GoonRec_RomParam ggoonRec_romParam=new GoonRec_RomParam();
            ggoonRec_romParam.setPort(flushbonadinginfo.getPort());
            ggoonRec_romParam.setIp(flushbonadinginfo.getEtip());
            ggoonRec_romParam.setPasswd(flushbonadinginfo.getPasswd());
            ggoonRec_romParam.setUser(flushbonadinginfo.getUser());
            RResult<GgoonRec_RomVO> result2=new RResult<GgoonRec_RomVO>();
            result2=fdDeal.goonRec_Rom(ggoonRec_romParam,result2);

            if(null!=result2&&result2.getActioncode().equals(Code.SUCCESS.toString())){
                result.changeToTrue(true);
            }else{
                result.setMessage("请求单独的光盘继续刻录失败");
            }
        }else{
            result.setMessage("请求单独的光盘继续/暂停参数异常，请确认后再操作");
        }

        return result;
    }

    @Override
    public RResult dvdOutOrIn(DvdOutOrInParam_out param, RResult result) {

        int inOrOut=param.getInOrOut();
        int dx=param.getDx();

        String fdssid=param.getFlushbonadingetinfossid();
        if (StringUtils.isBlank(fdssid)){
            LogUtil.intoLog(this.getClass(),"param.getFdssid():"+fdssid);
            result.setMessage("参数为空");
            return result;
        }

        //查询数据库找到设备
        EntityWrapper<Flushbonading_etinfo> ew=new EntityWrapper<Flushbonading_etinfo>();
        ew.eq("fet.ssid",fdssid);
        Flushbonadinginfo flushbonadinginfo=flushbonading_etinfoMapper.getFlushbonadinginfo(ew);
        if(null==flushbonadinginfo){
            result.setMessage("设备未找到，请查询数据");
            return result;
        }

        if(inOrOut==1){//进仓
            Closetray_RomParam closetray_RomParam = new Closetray_RomParam();
            closetray_RomParam.setPort(flushbonadinginfo.getPort());
            closetray_RomParam.setIp(flushbonadinginfo.getEtip());
            closetray_RomParam.setPasswd(flushbonadinginfo.getPasswd());
            closetray_RomParam.setUser(flushbonadinginfo.getUser());
            closetray_RomParam.setDx(dx);
            RResult<Closetray_RomVO> result2=new RResult<Closetray_RomVO>();
            result2=fdDeal.closetray_Rom(closetray_RomParam,result2);
            if(null!=result2&&result2.getActioncode().equals(Code.SUCCESS.toString())){
                result.changeToTrue(true);
            }else{
                result.setMessage("请求光盘进仓失败");
            }
        }else if(inOrOut==2){//出仓
            Eject_RomParam eject_romParam = new Eject_RomParam();
            eject_romParam.setPort(flushbonadinginfo.getPort());
            eject_romParam.setIp(flushbonadinginfo.getEtip());
            eject_romParam.setPasswd(flushbonadinginfo.getPasswd());
            eject_romParam.setUser(flushbonadinginfo.getUser());
            eject_romParam.setDx(dx);
            RResult<Eject_RomVO> result2=new RResult<Eject_RomVO>();
            result2=fdDeal.eject_Rom(eject_romParam,result2);
            if(null!=result2&&result2.getActioncode().equals(Code.SUCCESS.toString())){
                result.changeToTrue(true);
            }else{
                result.setMessage("请求光盘出仓失败");
            }
        }else{
            result.setMessage("请求光盘进出仓参数异常，请确认后再操作");
        }

        return result;
    }

    @Override
    public RResult ptdj(PtdjParam_out param, RResult result) {

        String fdssid=param.getFlushbonadingetinfossid();
        int ct=param.getCt();
        List<String> linelist=param.getLineList();

        if (StringUtils.isBlank(fdssid)){
            LogUtil.intoLog(this.getClass(),"param.getFdssid():"+fdssid);
            result.setMessage("参数为空");
            return result;
        }

        if (null==linelist||linelist.size() ==0){
            LogUtil.intoLog(this.getClass(),"param.linelist is null");
            result.setMessage("片头叠加的数据为空");
            return result;
        }

        //查询数据库找到设备
        EntityWrapper<Flushbonading_etinfo> ew=new EntityWrapper<Flushbonading_etinfo>();
        ew.eq("fet.ssid",fdssid);
        Flushbonadinginfo flushbonadinginfo=flushbonading_etinfoMapper.getFlushbonadinginfo(ew);
        if(null==flushbonadinginfo){
            result.setMessage("设备未找到，请查询数据");
            return result;
        }

        if (ct ==0){
            ct= flushbonadinginfo.getBurntime()==null ? 15 : flushbonadinginfo.getBurntime();
        }

        PtdjParam ptdjParam=new PtdjParam();
        ptdjParam.setPort(flushbonadinginfo.getPort());
        ptdjParam.setIp(flushbonadinginfo.getEtip());
        ptdjParam.setPasswd(flushbonadinginfo.getPasswd());
        ptdjParam.setUser(flushbonadinginfo.getUser());
        ptdjParam.setCt(ct);
        ptdjParam.setLineList(linelist);
        RResult<PtdjVO> result2=new RResult<PtdjVO>();
        result2=fdDeal.ptdj(ptdjParam,result2);
        if(null!=result2&&result2.getActioncode().equals(Code.SUCCESS.toString())){
            result.changeToTrue(true);
        }else{
            result.setMessage("请求片头叠加失败");
        }

        return result;
    }

    @Override
    public RResult getptdjconst(GetptdjconstParam_out param, RResult result) {

        boolean mustbool=param.isMustUpdateBool();
        String fdssid=param.getFlushbonadingetinfossid();
        if (StringUtils.isBlank(fdssid)){
            LogUtil.intoLog(this.getClass(),"param.getFdssid():"+fdssid);
            result.setMessage("参数为空");
            return result;
        }

        //查询数据库找到设备
        EntityWrapper<Flushbonading_etinfo> ew=new EntityWrapper<Flushbonading_etinfo>();
        ew.eq("fet.ssid",fdssid);
        Flushbonadinginfo flushbonadinginfo=flushbonading_etinfoMapper.getFlushbonadinginfo(ew);
        if(null==flushbonadinginfo){
            result.setMessage("设备未找到，请查询数据");
            return result;
        }

        List<String> linelist=new ArrayList<String>();
        if(StringUtils.isEmpty(flushbonadinginfo.getPtjson()) || mustbool){//如果没有找到片头就需要去设备中找

            GetptdjconstParam ptdjparam=new GetptdjconstParam();
            ptdjparam.setPort(flushbonadinginfo.getPort());
            ptdjparam.setIp(flushbonadinginfo.getEtip());
            ptdjparam.setPasswd(flushbonadinginfo.getPasswd());
            ptdjparam.setUser(flushbonadinginfo.getUser());
            RResult<GetptdjconstVO> rResult2=new RResult<GetptdjconstVO>();
            rResult2=fdDeal.getptdjconst(ptdjparam,rResult2);
            if(null!=rResult2&&rResult2.getActioncode().equals(Code.SUCCESS.toString())&&null!=rResult2.getData()){

                GetptdjconstVO ptdjvo=rResult2.getData();
                linelist=ptdjvo.getLineList();
                if(null!=linelist&&linelist.size() > 0){
                    String lines="";
                    for(String line:linelist){
                        lines+=line+",";
                    }
                    lines=OpenUtil.subStringToEnd(lines);

                    //写入数据库
                    Flushbonading_etinfo etinfo=flushbonadinginfo;
                    etinfo.setPtjson(lines);
                    int updateById=flushbonading_etinfoMapper.updateById(etinfo);
                    LogUtil.intoLog(3,this.getClass(),"修改数据库的片头数据是否成功，flushbonading_etinfoMapper.updateById："+updateById);

                }

            }else{
                LogUtil.intoLog(4,this.getClass(),"请求fdDeal.getptdjconst，没有数据，rResult2："+JacksonUtil.objebtToString(rResult2));
            }
        }else{
            String lines=flushbonadinginfo.getPtjson();
            linelist=OpenUtil.split(lines,",");
        }

        if(null!=linelist&&linelist.size() > 0){
            result.changeToTrue(linelist);
        }else{
            result.setMessage("数据库中存储的片头字段集合不正确，需要强制修改(或修改正确的ip和端口地址)");
        }

        return result;
    }

    @Override
    public RResult yuntaiControl(YuntaiControlParam_out param, RResult result) {

        String ptzaction=param.getPtzaction();
        int ptzch=param.getPtzch();

        String fdssid=param.getFlushbonadingetinfossid();
        if (StringUtils.isBlank(fdssid)){
            LogUtil.intoLog(this.getClass(),"param.getFdssid():"+fdssid);
            result.setMessage("参数为空");
            return result;
        }

        if (StringUtils.isBlank(ptzaction)){
            LogUtil.intoLog(this.getClass(),"is null ,param.getPtzch():"+ptzch);
            result.setMessage("请求云台的操作参数为空");
            return result;
        }

        //查询数据库找到设备
        EntityWrapper<Flushbonading_etinfo> ew=new EntityWrapper<Flushbonading_etinfo>();
        ew.eq("fet.ssid",fdssid);
        Flushbonadinginfo flushbonadinginfo=flushbonading_etinfoMapper.getFlushbonadinginfo(ew);
        if(null==flushbonadinginfo){
            result.setMessage("设备未找到，请查询数据");
            return result;
        }

        YuntaiControlParam yuntaiControlParam=new YuntaiControlParam();
        yuntaiControlParam.setPort(flushbonadinginfo.getPort());
        yuntaiControlParam.setIp(flushbonadinginfo.getEtip());
        yuntaiControlParam.setPasswd(flushbonadinginfo.getPasswd());
        yuntaiControlParam.setUser(flushbonadinginfo.getUser());
        yuntaiControlParam.setPtzaction(ptzaction);
        yuntaiControlParam.setPtzch(ptzch);
        RResult<YuntaiControlVO> result2=new RResult<YuntaiControlVO>();
        result2=fdDeal.yuntaiControl(yuntaiControlParam,result2);
        if(null!=result2&&result2.getActioncode().equals(Code.SUCCESS.toString())){
            result.changeToTrue(true);
        }else{
            result.setMessage("请求云台控制失败");
        }

        return result;
    }


    @Override
    public RResult changeBurnMode(ChangeBurnModeParam_out param, RResult result) {

        int dx=param.getDx();

        String fdssid=param.getFlushbonadingetinfossid();
        if (StringUtils.isBlank(fdssid)){
            LogUtil.intoLog(this.getClass(),"param.getFdssid():"+fdssid);
            result.setMessage("参数为空");
            return result;
        }

        if (dx < 0){
            LogUtil.intoLog(this.getClass(),"is error ,param.getDx():"+dx);
            result.setMessage("请求切换刻录模式参数异常");
            return result;
        }

        //查询数据库找到设备
        EntityWrapper<Flushbonading_etinfo> ew=new EntityWrapper<Flushbonading_etinfo>();
        ew.eq("fet.ssid",fdssid);
        Flushbonadinginfo flushbonadinginfo=flushbonading_etinfoMapper.getFlushbonadinginfo(ew);
        if(null==flushbonadinginfo){
            result.setMessage("设备未找到，请查询数据");
            return result;
        }

        ChangeBurnModeParam changeBurnModeParam=new ChangeBurnModeParam();
        changeBurnModeParam.setPort(flushbonadinginfo.getPort());
        changeBurnModeParam.setIp(flushbonadinginfo.getEtip());
        changeBurnModeParam.setPasswd(flushbonadinginfo.getPasswd());
        changeBurnModeParam.setUser(flushbonadinginfo.getUser());
        changeBurnModeParam.setDx(dx);
        RResult<ChangeBurnModelVO> result2=new RResult<ChangeBurnModelVO>();
        result2=fdDeal.changeBurnMode(changeBurnModeParam,result2);
        if(null!=result2&&result2.getActioncode().equals(Code.SUCCESS.toString())){

            result.changeToTrue(true);
        }else{
            result.setMessage("请求切换刻录模式失败,如果是刻录中就不能切换模式");
        }
        return result;
    }


    @Override
    public RResult getCDNumber(GetCDNumberParam_out param, RResult result) {

        String fdssid=param.getFlushbonadingetinfossid();
        if (StringUtils.isBlank(fdssid)){
            LogUtil.intoLog(this.getClass(),"param.getFdssid():"+fdssid);
            result.setMessage("参数为空");
            return result;
        }

        //查询数据库找到设备
        EntityWrapper<Flushbonading_etinfo> ew=new EntityWrapper<Flushbonading_etinfo>();
        ew.eq("fet.ssid",fdssid);
        Flushbonadinginfo flushbonadinginfo=flushbonading_etinfoMapper.getFlushbonadinginfo(ew);
        if(null==flushbonadinginfo){
            result.setMessage("设备未找到，请查询数据");
            return result;
        }

        GetCDNumberParam getCDNumberParam=new GetCDNumberParam();
        getCDNumberParam.setPort(flushbonadinginfo.getPort());
        getCDNumberParam.setIp(flushbonadinginfo.getEtip());
        getCDNumberParam.setPasswd(flushbonadinginfo.getPasswd());
        getCDNumberParam.setUser(flushbonadinginfo.getUser());
        RResult<GetCDNumberVO> result2=new RResult<GetCDNumberVO>();
        result2=fdDeal.getCDNumber(getCDNumberParam,result2);
        if(null!=result2&&result2.getActioncode().equals(Code.SUCCESS.toString())){
            GetCDNumberVO getCDNumberVO=result2.getData();
            result.changeToTrue(getCDNumberVO);
        }else{
            result.setMessage("请求获取光盘序号失败");
        }
        return result;

    }

    @Override
    public RResult getBurnTime(GetBurnTimeParam pParam, RResult result) {

        Flushbonadinginfo flushbonadinginfo = new Flushbonadinginfo();
        flushbonadinginfo.setSsid(pParam.getFlushbonadingetinfossid());

        /**查询选时**/
        flushbonadingService.getBurnTime(flushbonadinginfo,result);

        return result;
    }

    @Override
    public RResult updateBurnTime(GetBurnTimeParam pParam, RResult result) {

        Flushbonadinginfo flushbonadinginfo = new Flushbonadinginfo();
        flushbonadinginfo.setSsid(pParam.getFlushbonadingetinfossid());
        flushbonadinginfo.setBurntime(pParam.getBurntime());

        /**修改刻录选时**/
        flushbonadingService.updateBurnTime(flushbonadinginfo, result);

        return result;
    }

    @Override
    public RResult getFDLog(GetFDLogParam_out param, RResult result) {

        String fdssid=param.getFlushbonadingetinfossid();
        //查询数据库找到设备
        EntityWrapper<Flushbonading_etinfo> ew=new EntityWrapper<Flushbonading_etinfo>();
        Flushbonadinginfo flushbonadinginfo=null;
        if (StringUtils.isBlank(fdssid)){
            //查找默认的设备，其实应该把单机版的授权发过来验证一下的
            ew.eq("fet.defaulturlbool",1);
        }else{
            ew.eq("fet.ssid",fdssid);
        }

        try {
            flushbonadinginfo=flushbonading_etinfoMapper.getFlushbonadinginfo(ew);
        } catch (Exception e) {
            LogUtil.intoLog(4,this.getClass(),"查找默认的设备，抛错了，可能找到了2个默认的设备，不允许");
            flushbonadinginfo=null;
        }
        if(null==flushbonadinginfo){
            result.setMessage("设备未找到，请查询数据");
            return result;
        }

        GetFDLogParam getFDLogParam=new GetFDLogParam();
        getFDLogParam.setPort(flushbonadinginfo.getPort());
        getFDLogParam.setIp(flushbonadinginfo.getEtip());
        getFDLogParam.setPasswd(flushbonadinginfo.getPasswd());
        getFDLogParam.setUser(flushbonadinginfo.getUser());

        getFDLogParam.setFd(param.getFd() > 0 ? param.getFd():DateUtil.getDay());
        getFDLogParam.setFm(param.getFm()> 0 ? param.getFm():DateUtil.getMonth());
        getFDLogParam.setFy(param.getFy()> 1990 ? param.getFy():DateUtil.getYear());
//        getFDLogParam.setLogtype(param.getLogtype());//暂时只查0,
        getFDLogParam.setPage(param.getPage()> 0 ? param.getPage():1);
        RResult<GetFDLogVO> result2=new RResult<GetFDLogVO>();
        result2=fdDeal.getFDLog(getFDLogParam,result2);
        if(null!=result2){
            result=result2;
        }else{
            result.setMessage("请求获取日志失败");
        }
        return result;
    }

    @Override
    public RResult getFDAudPowerMap(GetFDAudPowerMapParam_out param, RResult result) {

        String fdssid=param.getFlushbonadingetinfossid();
        if (StringUtils.isBlank(fdssid)){
            LogUtil.intoLog(this.getClass(),"param.getFdssid():"+fdssid);
            result.setMessage("参数为空");
            return result;
        }

        //查询数据库找到设备
        EntityWrapper<Flushbonading_etinfo> ew=new EntityWrapper<Flushbonading_etinfo>();
        ew.eq("fet.ssid",fdssid);
        Flushbonadinginfo flushbonadinginfo=flushbonading_etinfoMapper.getFlushbonadinginfo(ew);
        if(null==flushbonadinginfo){
            result.setMessage("设备未找到，请查询数据");
            return result;
        }

        GetAudPowerMapParam getAudPowerMapParam=new GetAudPowerMapParam();
        getAudPowerMapParam.setPort(flushbonadinginfo.getPort());
        getAudPowerMapParam.setIp(flushbonadinginfo.getEtip());
        getAudPowerMapParam.setPasswd(flushbonadinginfo.getPasswd());
        getAudPowerMapParam.setUser(flushbonadinginfo.getUser());
        RResult<GetAudPowerMapVO> result2=new RResult<GetAudPowerMapVO>();
        result2=fdDeal.getAudPowerMap(getAudPowerMapParam,result2);
        if(null!=result2){
            result=result2;
        }else{
            result.setMessage("请求获取实时声音振幅失败");
        }
        return result;
    }

    @Override
    public RResult setFDnetwork(SetFDnetworkParam_out param, RResult result) {

        String fdssid=param.getFlushbonadingetinfossid();
        if (StringUtils.isBlank(fdssid)){
            LogUtil.intoLog(this.getClass(),"param.getFdssid():"+fdssid);
            result.setMessage("参数为空");
            return result;
        }

        //查询数据库找到设备
        EntityWrapper<Flushbonading_etinfo> ew=new EntityWrapper<Flushbonading_etinfo>();
        ew.eq("fet.ssid",fdssid);
        Flushbonadinginfo flushbonadinginfo=flushbonading_etinfoMapper.getFlushbonadinginfo(ew);
        if(null==flushbonadinginfo){
            result.setMessage("设备未找到，请查询数据");
            return result;
        }

        Set_networkParam set_networkParam=new Set_networkParam();
        set_networkParam.setPort(flushbonadinginfo.getPort());
        set_networkParam.setIp(flushbonadinginfo.getEtip());
        set_networkParam.setPasswd(flushbonadinginfo.getPasswd());
        set_networkParam.setUser(flushbonadinginfo.getUser());

        set_networkParam.setAjust(param.getAjust());
        set_networkParam.setDev(param.getDev());
        if(StringUtils.isNotEmpty(param.getGateway())){
            set_networkParam.setGateway(param.getGateway());
        }else{
            set_networkParam.setGateway("255.255.255.0");
        }
        set_networkParam.setIp_new(param.getIp_new());
        set_networkParam.setNetmask(param.getNetmask());

        RResult<Set_networkVO> result2=new RResult<Set_networkVO>();
        result2=fdDeal.set_network(set_networkParam,result2);
        if(null!=result2&&result2.getActioncode().equals(Code.SUCCESS.toString())){
            result.changeToTrue();
        }else{
            if(null!=result){
                result.setMessage(result.getMessage());
            }else{
                result.setMessage("设置设备网络失败");
            }
        }
        return result;
    }


    @Override
    public RResult setFDAudioVolume(SetFDAudioVolumeParam_out param, RResult result) {
        String fdssid=param.getFlushbonadingetinfossid();
        if (StringUtils.isBlank(fdssid)){
            LogUtil.intoLog(this.getClass(),"param.getFdssid():"+fdssid);
            result.setMessage("参数为空");
            return result;
        }

        //查询数据库找到设备
        EntityWrapper<Flushbonading_etinfo> ew=new EntityWrapper<Flushbonading_etinfo>();
        ew.eq("fet.ssid",fdssid);
        Flushbonadinginfo flushbonadinginfo=flushbonading_etinfoMapper.getFlushbonadinginfo(ew);
        if(null==flushbonadinginfo){
            result.setMessage("设备未找到，请查询数据");
            return result;
        }

        SetAudioVolumeParam setAudioVolumeParam=new SetAudioVolumeParam();
        setAudioVolumeParam.setPort(flushbonadinginfo.getPort());
        setAudioVolumeParam.setIp(flushbonadinginfo.getEtip());
        setAudioVolumeParam.setPasswd(flushbonadinginfo.getPasswd());
        setAudioVolumeParam.setUser(flushbonadinginfo.getUser());

        int ch=param.getCh();
        int save=param.getSave();
        int volume=param.getVolume();

        setAudioVolumeParam.setCh(ch+"");
        setAudioVolumeParam.setSave(save+"");
        setAudioVolumeParam.setVolume(volume+"");

        RResult<SetAudioVolumeVO> result2=new RResult<SetAudioVolumeVO>();
        result2=fdDeal.setAudioVolume(setAudioVolumeParam,result2);
        if(null!=result2&&result2.getActioncode().equals(Code.SUCCESS.toString())){
            result.changeToTrue();
        }else{
            if(null!=result){
                result.setMessage(result.getMessage());
            }else{
                result.setMessage("设置设备通道音量失败");
            }
        }
        return result;
    }

    @Override
    public RResult getFDAudioConf(GetFDAudioConfParam_out param, RResult result) {
        String fdssid=param.getFlushbonadingetinfossid();
        if (StringUtils.isBlank(fdssid)){
            LogUtil.intoLog(this.getClass(),"param.getFdssid():"+fdssid);
            result.setMessage("参数为空");
            return result;
        }

        //查询数据库找到设备
        EntityWrapper<Flushbonading_etinfo> ew=new EntityWrapper<Flushbonading_etinfo>();
        ew.eq("fet.ssid",fdssid);
        Flushbonadinginfo flushbonadinginfo=flushbonading_etinfoMapper.getFlushbonadinginfo(ew);
        if(null==flushbonadinginfo){
            result.setMessage("设备未找到，请查询数据");
            return result;
        }

        GetFDAudioConfVO vo=new GetFDAudioConfVO();
        int a_in=0;//音频有效通道

        //先获取有多少个有效音频通道
        RResult<GetCapabilitySetVO> result2=new RResult<GetCapabilitySetVO>();
        GetCapabilitySetParam getCapabilitySetParam=new GetCapabilitySetParam();
        getCapabilitySetParam.setPort(flushbonadinginfo.getPort());
        getCapabilitySetParam.setIp(flushbonadinginfo.getEtip());
        getCapabilitySetParam.setPasswd(flushbonadinginfo.getPasswd());
        getCapabilitySetParam.setUser(flushbonadinginfo.getUser());
        result2=fdDeal.getCapabilitySet(getCapabilitySetParam,result2);
        if(null!=result2&&result2.getActioncode().equals(Code.SUCCESS.toString())){

            GetCapabilitySetVO getCapabilitySetVO=result2.getData();
            if(null!=getCapabilitySetVO){
                try {
                    a_in=(Integer.parseInt(getCapabilitySetVO.getA_in()));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }else{
                result.setMessage("没有找到设备的任何能力集，请联系管理员");
            }
        }else{
            if(null!=result){
                result.setMessage(result.getMessage());
            }else{
                result.setMessage("获取音频配置失败");
            }
            return result;
        }

        if(a_in > 0){

            GetAudioConfParam getAudioConfParam=new GetAudioConfParam();
            getAudioConfParam.setPort(flushbonadinginfo.getPort());
            getAudioConfParam.setIp(flushbonadinginfo.getEtip());
            getAudioConfParam.setPasswd(flushbonadinginfo.getPasswd());
            getAudioConfParam.setUser(flushbonadinginfo.getUser());

            RResult<GetAudioConfVO> result3=new RResult<GetAudioConfVO>();
            result3=fdDeal.getAudioConf(getAudioConfParam,result3);
            if(null!=result3&&result3.getActioncode().equals(Code.SUCCESS.toString())){

                GetAudioConfVO getAudioConfVO=result3.getData();
                if(null!=getAudioConfVO&&null!=getAudioConfVO.getAudList()&&getAudioConfVO.getAudList().size() > 0){
                    List<Aud> audlist=getAudioConfVO.getAudList();
                    vo.setAudioPassagewayNum(a_in);
                    List<GetAudioConfVO_param> audiolist=new ArrayList<GetAudioConfVO_param>();
                    for(int i=0;i<a_in;i++){//从所有的音频通道中读取有效音频通道数，前多少个就是有效通道数
                        GetAudioConfVO_param getAudioConfVO_param=new GetAudioConfVO_param();
                        getAudioConfVO_param.setCh(Integer.parseInt(audlist.get(i).getCh()));
                        getAudioConfVO_param.setVolume(Integer.parseInt(audlist.get(i).getVolume()));
                        audiolist.add(getAudioConfVO_param);
                    }
                    vo.setAudiolist(audiolist);
                    result.changeToTrue(vo);

                }else{
                    result.setMessage("设备没有找到任何音频通道");
                }

            }else{
                if(null!=result){
                    result.setMessage(result.getMessage());
                }else{
                    result.setMessage("获取音频配置失败");
                }
            }
        }else{
            result.setMessage("设备没有有效的音频通道");
        }

        return result;
    }

    @Override
    public RResult getFDNetWork(GetFDNetWorkParam_out pParam, RResult result) {

        String fdssid=pParam.getFlushbonadingetinfossid();
        if (StringUtils.isBlank(fdssid)){
            LogUtil.intoLog(this.getClass(),"param.getFdssid():"+fdssid);
            result.setMessage("参数为空");
            return result;
        }

        //查询数据库找到设备
        EntityWrapper<Flushbonading_etinfo> ew=new EntityWrapper<Flushbonading_etinfo>();
        ew.eq("fet.ssid",fdssid);
        Flushbonadinginfo flushbonadinginfo=flushbonading_etinfoMapper.getFlushbonadinginfo(ew);
        if(null==flushbonadinginfo){
            result.setMessage("设备未找到，请查询数据");
            return result;
        }

        Get_networkParam get_networkParam=new Get_networkParam();
        get_networkParam.setPort(flushbonadinginfo.getPort());
        get_networkParam.setIp(flushbonadinginfo.getEtip());
        get_networkParam.setPasswd(flushbonadinginfo.getPasswd());
        get_networkParam.setUser(flushbonadinginfo.getUser());
        RResult<Get_networkVO> result2=new RResult<Get_networkVO>();
        result2=fdDeal.get_network(get_networkParam,result2);
        if(null!=result2){
            result=result2;
        }else{
            result.setMessage("请求获取设备网络配置失败");
        }

        return result;
    }

    @Override
    public RResult setFDTime(SetFDTimeParam_out pParam, RResult result) {

        try {
            String fdssid=pParam.getFlushbonadingetinfossid();
            if (StringUtils.isBlank(fdssid)){
                LogUtil.intoLog(4,this.getClass(),"setFDTime param.getFdssid():"+fdssid);
                result.setMessage("参数为空");
                return result;
            }

            //查询数据库找到设备
            EntityWrapper<Flushbonading_etinfo> ew=new EntityWrapper<Flushbonading_etinfo>();
            ew.eq("fet.ssid",fdssid);
            Flushbonadinginfo flushbonadinginfo=flushbonading_etinfoMapper.getFlushbonadinginfo(ew);
            if(null==flushbonadinginfo){
                result.setMessage("设备未找到，请查询数据");
                return result;
            }

            String dateTime=pParam.getDateTime();
            if (StringUtils.isEmpty(dateTime)){
                LogUtil.intoLog(4,this.getClass(),"setFDTime pParam.getDateTime():"+dateTime);
                result.setMessage("参数为空");
                return result;
            }
            Date date=DateUtil.strToDate(dateTime);
            if(null==date){//说明传过来的是一个格式不正确的日期
                result.setMessage("日期格式不正确");
                return result;
            }

            SetTimeParam setAudioVolumeParam=new SetTimeParam();
            setAudioVolumeParam.setPort(flushbonadinginfo.getPort());
            setAudioVolumeParam.setIp(flushbonadinginfo.getEtip());
            setAudioVolumeParam.setPasswd(flushbonadinginfo.getPasswd());
            setAudioVolumeParam.setUser(flushbonadinginfo.getUser());
            setAudioVolumeParam.setDay(Integer.parseInt(DateUtil.getDay(date)));
            setAudioVolumeParam.setMonth(Integer.parseInt(DateUtil.getMonth(date)));
            setAudioVolumeParam.setYear(Integer.parseInt(DateUtil.getYear(date)));
            setAudioVolumeParam.setHour(Integer.parseInt(DateUtil.getHour(date)));
            setAudioVolumeParam.setMm(Integer.parseInt(DateUtil.getMM(date)));
            setAudioVolumeParam.setSs(Integer.parseInt(DateUtil.getSS(date)));

            String tz=pParam.getTimeZone();
            if(StringUtils.isNotEmpty(tz)){
                setAudioVolumeParam.setTimeZone(tz);
            }

            RResult<SetTimeVO> result2=new RResult<SetTimeVO>();
            result2=fdDeal.setTime(setAudioVolumeParam,result2);
            if(null!=result2&&result2.getActioncode().equals(Code.SUCCESS.toString())){
                result.changeToTrue();
            }else{
                if(null!=result){
                    result.setMessage(result.getMessage());
                }else{
                    result.setMessage("设置设备当前时间失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public RResult setFDNTP(SetFDNTPParam_out pParam, RResult result) {

        try {
            String fdssid=pParam.getFlushbonadingetinfossid();
            if (StringUtils.isBlank(fdssid)){
                LogUtil.intoLog(4,this.getClass(),"setFDNTP param.getFdssid():"+fdssid);
                result.setMessage("参数为空");
                return result;
            }

            //查询数据库找到设备
            EntityWrapper<Flushbonading_etinfo> ew=new EntityWrapper<Flushbonading_etinfo>();
            ew.eq("fet.ssid",fdssid);
            Flushbonadinginfo flushbonadinginfo=flushbonading_etinfoMapper.getFlushbonadinginfo(ew);
            if(null==flushbonadinginfo){
                result.setMessage("设备未找到，请查询数据");
                return result;
            }

            String ntpintervaltime=pParam.getNtp_intervaltime();
            String ntphost=pParam.getNtp_host();
            String ntpport=pParam.getNtp_port();
            if (StringUtils.isEmpty(ntpintervaltime)||StringUtils.isEmpty(ntphost)||StringUtils.isEmpty(ntpport)){
                LogUtil.intoLog(4,this.getClass(),"NTP参数异常，pParam：:"+JacksonUtil.objebtToString(pParam));
                result.setMessage("参数为空");
                return result;
            }

            SetNTPParam pparam=new SetNTPParam();
            pparam.setPort(flushbonadinginfo.getPort());
            pparam.setIp(flushbonadinginfo.getEtip());
            pparam.setPasswd(flushbonadinginfo.getPasswd());
            pparam.setUser(flushbonadinginfo.getUser());
            pparam.setNtp_enable(pParam.getNtp_enable());
            pparam.setNtp_host(ntphost);
            pparam.setNtp_intervaltime(ntpintervaltime);
            pparam.setNtp_port(ntpport);

            RResult<SetNTPVO> result2=new RResult<SetNTPVO>();
            result2=fdDeal.setNTP(pparam,result2);
            if(null!=result2&&result2.getActioncode().equals(Code.SUCCESS.toString())){
                result.changeToTrue();
            }else{
                if(null!=result){
                    result.setMessage(result.getMessage());
                }else{
                    result.setMessage("设置NTP配置失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public RResult supplementBurn(SupplementBurnParam_out pParam, RResult result) {

        try {
            String fdssid=pParam.getFlushbonadingetinfossid();
            if (StringUtils.isBlank(fdssid)){
                LogUtil.intoLog(4,this.getClass(),"supplementBurn param.getFdssid():"+fdssid);
                result.setMessage("参数为空");
                return result;
            }

            //查询数据库找到设备
            EntityWrapper<Flushbonading_etinfo> ew=new EntityWrapper<Flushbonading_etinfo>();
            ew.eq("fet.ssid",fdssid);
            Flushbonadinginfo flushbonadinginfo=flushbonading_etinfoMapper.getFlushbonadinginfo(ew);
            if(null==flushbonadinginfo){
                result.setMessage("设备未找到，请查询数据");
                return result;
            }

            SupplementBurnParam pparam=new SupplementBurnParam();
            pparam.setPort(flushbonadinginfo.getPort());
            pparam.setIp(flushbonadinginfo.getEtip());
            pparam.setPasswd(flushbonadinginfo.getPasswd());
            pparam.setUser(flushbonadinginfo.getUser());
            pparam.setBurn_bl(pParam.getBurn_bl());
            pparam.setBurn_payer(pParam.getBurn_payer());
            pparam.setBurn_v(pParam.getBurn_v());

            RResult<SupplementBurnVO> result2=new RResult<SupplementBurnVO>();
            result2=fdDeal.supplementBurn(pparam,result2);
            if(null!=result2&&result2.getActioncode().equals(Code.SUCCESS.toString())){
                result.changeToTrue();
            }else{
                if(null!=result){
                    result.setMessage(result.getMessage());
                }else{
                    result.setMessage("请求补充刻录失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public RResult getFDNTP(GetFDNTPParam_out pParam, RResult result) {

        String fdssid=pParam.getFlushbonadingetinfossid();
        if (StringUtils.isBlank(fdssid)){
            LogUtil.intoLog(this.getClass(),"param.getFdssid():"+fdssid);
            result.setMessage("参数为空");
            return result;
        }

        //查询数据库找到设备
        EntityWrapper<Flushbonading_etinfo> ew=new EntityWrapper<Flushbonading_etinfo>();
        ew.eq("fet.ssid",fdssid);
        Flushbonadinginfo flushbonadinginfo=flushbonading_etinfoMapper.getFlushbonadinginfo(ew);
        if(null==flushbonadinginfo){
            result.setMessage("设备未找到，请查询数据");
            return result;
        }

        GetNTPParam pparam=new GetNTPParam();
        pparam.setPort(flushbonadinginfo.getPort());
        pparam.setIp(flushbonadinginfo.getEtip());
        pparam.setPasswd(flushbonadinginfo.getPasswd());
        pparam.setUser(flushbonadinginfo.getUser());
        RResult<GetNTPVO> result2=new RResult<GetNTPVO>();
        result2=fdDeal.getNTP(pparam,result2);
        if(null!=result2&&result2.getActioncode().equals(Code.SUCCESS.toString())&&null!=result2.getData()){
            result=result2;
        }else{
            result.setMessage("请求获取NTP配置失败");
        }

        return result;
    }

    @Override
    public RResult setFDOSD(SetFDOSDParam_out pParam, RResult result) {

        try {
            String fdssid=pParam.getFlushbonadingetinfossid();
            if (StringUtils.isBlank(fdssid)){
                LogUtil.intoLog(4,this.getClass(),"setFDOSD param.getFdssid():"+fdssid);
                result.setMessage("参数为空");
                return result;
            }else{
                LogUtil.intoLog(this.getClass(),"setFDOSD 片头叠加的参数集合 param:"+JacksonUtil.objebtToString(pParam));
            }

            //查询数据库找到设备
            EntityWrapper<Flushbonading_etinfo> ew=new EntityWrapper<Flushbonading_etinfo>();
            ew.eq("fet.ssid",fdssid);
            Flushbonadinginfo flushbonadinginfo=flushbonading_etinfoMapper.getFlushbonadinginfo(ew);
            if(null==flushbonadinginfo){
                result.setMessage("设备未找到，请查询数据");
                return result;
            }

            SetOSDParam pparam=new SetOSDParam();
            pparam.setPort(flushbonadinginfo.getPort());
            pparam.setIp(flushbonadinginfo.getEtip());
            pparam.setPasswd(flushbonadinginfo.getPasswd());
            pparam.setUser(flushbonadinginfo.getUser());
            pparam.setOsd_saved(pParam.getOsd_saved());
            pparam.setOsd_sync_ajust(pParam.getOsd_saved());
            pparam.setOsdpart_title_y(pParam.getOsdpart_title_y()+"");
            pparam.setOsdpart_title_x(pParam.getOsdpart_title_y()+"");
            pparam.setOsdpart_time_y(pParam.getOsdpart_time_y()+"");
            pparam.setOsdpart_time_x(pParam.getOsdpart_time_x()+"");
            pparam.setOsdpart_temperature_y(pParam.getOsdpart_temperature_y()+"");
            pparam.setOsdpart_temperature_x(pParam.getOsdpart_temperature_x()+"");
            pparam.setOsdpart_pttext_y(pParam.getOsdpart_pttext_y()+"");
            pparam.setOsdpart_pttext_x(pParam.getOsdpart_pttext_x()+"");

            RResult<SetOSDVO> result2=new RResult<SetOSDVO>();
            result2=fdDeal.setOSD(pparam,result2);
            if(null!=result2&&result2.getActioncode().equals(Code.SUCCESS.toString())){
                result.changeToTrue();
            }else{
                if(null!=result){
                    result.setMessage(result.getMessage());
                }else{
                    result.setMessage("请求OSD信息叠加失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public RResult getFDOSD(GetFDOSDParam_out pParam, RResult result) {

        String fdssid=pParam.getFlushbonadingetinfossid();
        if (StringUtils.isBlank(fdssid)){
            LogUtil.intoLog(this.getClass(),"param.getFdssid():"+fdssid);
            result.setMessage("参数为空");
            return result;
        }

        //查询数据库找到设备
        EntityWrapper<Flushbonading_etinfo> ew=new EntityWrapper<Flushbonading_etinfo>();
        ew.eq("fet.ssid",fdssid);
        Flushbonadinginfo flushbonadinginfo=flushbonading_etinfoMapper.getFlushbonadinginfo(ew);
        if(null==flushbonadinginfo){
            result.setMessage("设备未找到，请查询数据");
            return result;
        }

        boolean ptbool=pParam.isPtbool();
        boolean temperaturebool=pParam.isTemperaturebool();
        boolean timebool=pParam.isTimebool();
        boolean titlebool=pParam.isTitlebool();
        if (!ptbool&&!temperaturebool&&!timebool&&!titlebool){
            LogUtil.intoLog(this.getClass(),"用户不想要任何一个osd的坐标，直接返回，"+JacksonUtil.objebtToString(pParam));
            result.setMessage("参数异常");
            return result;
        }

        GetOSDParam pparam=new GetOSDParam();
        pparam.setPort(flushbonadinginfo.getPort());
        pparam.setIp(flushbonadinginfo.getEtip());
        pparam.setPasswd(flushbonadinginfo.getPasswd());
        pparam.setUser(flushbonadinginfo.getUser());
        RResult<GetOSDVO> result2=new RResult<GetOSDVO>();
        result2=fdDeal.getOSD(pparam,result2);
        if(null!=result2&&result2.getActioncode().equals(Code.SUCCESS.toString())&&null!=result2.getData()){
            GetOSDVO getOSDVO=result2.getData();
            if(null!=getOSDVO){
                GetFDOSDVO getFDOSDVO=new GetFDOSDVO();
                Map<String, CoordinateParam> osdMap=new HashMap<String, CoordinateParam>();
                if(ptbool){
                    osdMap.put("pt",new CoordinateParam(getOSDVO.getOsdpart_pttext_x(),getOSDVO.getOsdpart_pttext_y()));
                }
                if(temperaturebool){
                    osdMap.put("temperature",new CoordinateParam(getOSDVO.getOsdpart_temperature_x(),getOSDVO.getOsdpart_temperature_y()));
                }
                if(timebool){
                    osdMap.put("time",new CoordinateParam(getOSDVO.getOsdpart_time_x(),getOSDVO.getOsdpart_time_y()));
                }
                if(titlebool){
                    osdMap.put("title",new CoordinateParam(getOSDVO.getOsdpart_title_x(),getOSDVO.getOsdpart_title_y()));
                }
                getFDOSDVO.setOsdMap(osdMap);

                result.changeToTrue(getFDOSDVO);

            }else{
                result.setMessage("请求获取的OSD信息叠加为空，请联系管理员");
            }
        }else{
            result.setMessage("请求获取设备网络配置失败");
        }

        return result;
    }

    @Override
    public RResult getFDAllFileListByIid(GetFDAllFileListByIidParam_out pParam, RResult result) {

        String fdssid=pParam.getFlushbonadingetinfossid();
        if (StringUtils.isBlank(fdssid)){
            LogUtil.intoLog(this.getClass(),"param.getFdssid():"+fdssid);
            result.setMessage("参数为空");
            return result;
        }

        //查询数据库找到设备
        EntityWrapper<Flushbonading_etinfo> ew=new EntityWrapper<Flushbonading_etinfo>();
        ew.eq("fet.ssid",fdssid);
        Flushbonadinginfo flushbonadinginfo=flushbonading_etinfoMapper.getFlushbonadinginfo(ew);
        if(null==flushbonadinginfo){
            result.setMessage("设备未找到，请查询数据");
            return result;
        }

        String iid=pParam.getIid();

        if (StringUtils.isEmpty(iid)){
            LogUtil.intoLog(this.getClass(),"参数iid为空，"+JacksonUtil.objebtToString(pParam));
            result.setMessage("参数异常");
            return result;
        }

        GetAllFileListByIidParam pparam=new GetAllFileListByIidParam();
        pparam.setPort(flushbonadinginfo.getPort());
        pparam.setIp(flushbonadinginfo.getEtip());
        pparam.setPasswd(flushbonadinginfo.getPasswd());
        pparam.setUser(flushbonadinginfo.getUser());
        pparam.setIid(iid);
        RResult<GetAllFileListByIidVO> result2=new RResult<GetAllFileListByIidVO>();
        result2=fdDeal.getAllFileListByIid(pparam,result2);
        if(null!=result2&&result2.getActioncode().equals(Code.SUCCESS.toString())&&null!=result2.getData()){
            GetAllFileListByIidVO allFileListByIidVO=result2.getData();

        }else{
            result.setMessage("请求获取设备文件下的文件列表失败");
        }

        return result;
    }
}
