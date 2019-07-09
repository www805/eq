package com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.v1.service;

import com.avst.equipmentcontrol.common.datasourse.extrasourse.flushbonading.entity.param.Flushbonadinginfo;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.common.util.baseaction.ReqParam;
import com.avst.equipmentcontrol.outside.interfacetoout.asr.req.addOrUpdateToOutAsrParam;
import com.avst.equipmentcontrol.outside.interfacetoout.asr.req.getToOutAsrListParam;
import com.avst.equipmentcontrol.outside.interfacetoout.asr.v1.service.ToOutAsrService;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.req.addOrUpdateToOutFlushbonadingParam;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.req.getToOutFlushbonadingListParam;
import com.avst.equipmentcontrol.web.req.asr.AsrParam;
import com.avst.equipmentcontrol.web.req.asr.UpdateAsrParam;
import com.avst.equipmentcontrol.web.req.flushbonading.FlushbonadinginfoParam;
import com.avst.equipmentcontrol.web.service.AsrService;
import com.avst.equipmentcontrol.web.service.FlushbonadingService;
import com.avst.equipmentcontrol.web.vo.flushbonading.FlushbonadinginfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToOutFlushbonadingServiceImpl implements ToOutFlushbonadingService {

    @Autowired
    private FlushbonadingService flushbonadingService;

    //查询列表
    @Override
    public RResult getToOutFlushbonadingList(getToOutFlushbonadingListParam param, RResult result) {

        ReqParam<FlushbonadinginfoParam> reqParam = new ReqParam<FlushbonadinginfoParam>();

        FlushbonadinginfoParam flushbonadinginfoParam = new FlushbonadinginfoParam();
        reqParam.setParam(flushbonadinginfoParam);

        flushbonadingService.getFlushbonadingList(result, reqParam);

        if(!"FAIL".equals(result.getActioncode())){
            FlushbonadinginfoVO data = (FlushbonadinginfoVO) result.getData();
            List<Flushbonadinginfo> list = data.getPagelist();
            result.setData(list);
        }

        return result;
    }

    //查询单个
    @Override
    public RResult getToOutFlushbonadingById(getToOutFlushbonadingListParam param, RResult result) {

        ReqParam<FlushbonadinginfoParam> reqParam = new ReqParam<FlushbonadinginfoParam>();

        FlushbonadinginfoParam flushbonadinginfoParam = new FlushbonadinginfoParam();
        flushbonadinginfoParam.setSsid(param.getSsid());
        reqParam.setParam(flushbonadinginfoParam);

        flushbonadingService.getFlushbonadingById(result, reqParam);

        return result;
    }

    //新增
    @Override
    public RResult addToOutFlushbonading(addOrUpdateToOutFlushbonadingParam param, RResult result) {

        ReqParam<Flushbonadinginfo> reqParam = new ReqParam<Flushbonadinginfo>();

        Flushbonadinginfo flushbonadinginfo = new Flushbonadinginfo();
        flushbonadinginfo.setLivingurl(param.getLivingurl());
        flushbonadinginfo.setPort(param.getPort());
        flushbonadinginfo.setUser(param.getUser());
        flushbonadinginfo.setPasswd(param.getPasswd());
        flushbonadinginfo.setUploadbasepath(param.getUploadbasepath());
        flushbonadinginfo.setEtnum(param.getEtnum());
        flushbonadinginfo.setEtip(param.getEtip());
        flushbonadinginfo.setEtypessid(param.getEtypessid());
        flushbonadinginfo.setExplain(param.getExplain());
        reqParam.setParam(flushbonadinginfo);

        flushbonadingService.addFlushbonading(result, reqParam);

        return result;
    }

    //修改
    @Override
    public RResult updateToOutFlushbonading(addOrUpdateToOutFlushbonadingParam param, RResult result) {

        ReqParam<Flushbonadinginfo> reqParam = new ReqParam<Flushbonadinginfo>();

        Flushbonadinginfo flushbonadinginfo = new Flushbonadinginfo();
        flushbonadinginfo.setSsid(param.getSsid());
        flushbonadinginfo.setLivingurl(param.getLivingurl());
        flushbonadinginfo.setPort(param.getPort());
        flushbonadinginfo.setUser(param.getUser());
        flushbonadinginfo.setPasswd(param.getPasswd());
        flushbonadinginfo.setUploadbasepath(param.getUploadbasepath());
        flushbonadinginfo.setEtnum(param.getEtnum());
        flushbonadinginfo.setEtip(param.getEtip());
        flushbonadinginfo.setEtypessid(param.getEtypessid());
        flushbonadinginfo.setExplain(param.getExplain());
        reqParam.setParam(flushbonadinginfo);

        flushbonadingService.updateFlushbonading(result, reqParam);

        return result;
    }
}
