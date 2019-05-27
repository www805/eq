package com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.v1.service;

import com.avst.equipmentcontrol.common.conf.FDType;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.flushbonading.entity.Flushbonading_etinfo;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.flushbonading.entity.param.Flushbonadinginfo;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.flushbonading.mapper.Flushbonading_etinfoMapper;
import com.avst.equipmentcontrol.common.util.DateUtil;
import com.avst.equipmentcontrol.common.util.JacksonUtil;
import com.avst.equipmentcontrol.common.util.baseaction.Code;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.common.util.baseaction.ReqParam;
import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.FDDealImpl;
import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.req.CheckFDStateParam;
import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.req.StartRecParam;
import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.req.StopRecParam;
import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.vo.StopRecVO;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.cache.FDCache;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.cache.param.FDCacheParam;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.req.WorkOverParam;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.req.WorkStartParam;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.vo.WorkStartVO;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

/**
 * avst设备的对外接口处理
 */
@Service
public class ToOutService_avst implements ToOutService_qrs{

    @Autowired
    private FDDealImpl fdDeal;

    @Autowired
    private Flushbonading_etinfoMapper flushbonading_etinfoMapper;

    public RResult workStart(WorkStartParam param,RResult result){

        String fdid=param.getFdid();
        String fdssid=param.getFlushbonadingetinfossid();
        //查询是否有一台机子多次被用
        FDCacheParam fdCacheParam=FDCache.getFDByFDSsid(fdid,fdssid);
        if(null!=fdCacheParam){
            result.setMessage("设备已经开始工作");
            result.changeToTrue(fdid+"_"+fdssid);
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

        if(null==result){
            result=new RResult();
            result.setMessage("开启设备录像失败");
            System.out.println("开启设备录像失败 result："+result);
        }else{
            if(null!=result&&result.getActioncode().equals(Code.SUCCESS.toString())){
                fdCacheParam=new FDCacheParam();
                fdCacheParam.setFdSsid(fdssid);
                fdCacheParam.setFdType(FDType.FD_AVST);
                fdCacheParam.setLivingUrl(flushbonadinginfo.getLivingurl());
                fdCacheParam.setPort(flushbonadinginfo.getPort());
                fdCacheParam.setRecordStartTime(DateUtil.getSeconds()+"");
                fdCacheParam.setUseRecord(1);
                fdCacheParam.setIp(flushbonadinginfo.getEtip());
                fdCacheParam.setPasswd(flushbonadinginfo.getPasswd());
                fdCacheParam.setUser(flushbonadinginfo.getUser());
                fdCacheParam.setRecordFileiid(iid);
                FDCache.setFD(fdid,fdCacheParam);


                WorkStartVO workStartVO=new WorkStartVO();
                workStartVO.setFdlivingurl(flushbonadinginfo.getLivingurl());
                workStartVO.setIid(iid);
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
            System.out.println("关闭设备录像失败 result："+result);
        }else{
            if(null!=result&&result.getActioncode().equals(Code.SUCCESS.toString())){
                result.setData(fdCacheParam.getRecordFileiid());
                FDCache.delFDList(fdid);
            }
        }
        return result;
    }


}
