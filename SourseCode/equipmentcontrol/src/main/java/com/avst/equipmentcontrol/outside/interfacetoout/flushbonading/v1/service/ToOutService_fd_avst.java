package com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.v1.service;

import com.avst.equipmentcontrol.common.conf.FDType;
import com.avst.equipmentcontrol.common.conf.SSType;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.flushbonading.entity.Flushbonading_etinfo;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.flushbonading.entity.Flushbonading_ettd;
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
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.cache.FDCache;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.cache.param.FDCacheParam;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.req.*;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.vo.GetFDStateVO;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.vo.WorkStartVO;
import com.avst.equipmentcontrol.outside.interfacetoout.storage.req.SaveFileParam;
import com.avst.equipmentcontrol.outside.interfacetoout.storage.v1.service.ToOutServiceImpl_ss_avst;
import com.avst.equipmentcontrol.outside.interfacetoout.storage.v1.service.ToOutService_ss;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

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
                fdCacheParam.setPort(flushbonadinginfo.getPort());
                fdCacheParam.setUseRecord(1);
                fdCacheParam.setIp(flushbonadinginfo.getEtip());
                fdCacheParam.setPasswd(flushbonadinginfo.getPasswd());
                fdCacheParam.setUser(flushbonadinginfo.getUser());
                fdCacheParam.setRecordFileiid(iid);
                fdCacheParam.setRecordStartTime(startrecordtime);
                FDCache.setFD(fdid,fdCacheParam);


                WorkStartVO workStartVO=new WorkStartVO();
                workStartVO.setFdlivingurl(flushbonadinginfo.getLivingurl());
                workStartVO.setIid(iid);
                workStartVO.setStartrecordtime(startrecordtime);
                result.changeToTrue(workStartVO);
            }
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
                RResult result2=new RResult();
                result2=getToOutServiceImpl(sstype).saveFile(saveFileParam,result2);//v1默认给avst版的存储服务
                if(null!=result2&&result2.getActioncode().equals(Code.SUCCESS.toString())){
                    LogUtil.intoLog(this.getClass(),"推送设备保存文件到服务器成功fdssid："+fdssid);
                }else{
                    LogUtil.intoLog(this.getClass(),"推送设备保存文件到服务器失败 fdssid："+fdssid);
                }

                result.setData(fdCacheParam.getRecordFileiid());
                FDCache.delFDList(fdid);
            }
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
     * 这里只是零时的开启刻盘，不能影响硬盘的录制
     * 开启设备哪里才会有是否关联硬盘录像
     */
    public RResult startRec_Rom(StartRec_RomParam_out param, RResult result) {

        int dx=param.getDx();
        String iid=param.getIid();
        String bmode=param.getBmode();

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
        startRec_romParam.setBtime(flushbonadinginfo.getBurntime()==null?6:flushbonadinginfo.getBurntime());//默认给6小时的刻录时间
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
            result.setMessage("数据库中存储的片头字段集合不正确，需要强制修改");
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
}
