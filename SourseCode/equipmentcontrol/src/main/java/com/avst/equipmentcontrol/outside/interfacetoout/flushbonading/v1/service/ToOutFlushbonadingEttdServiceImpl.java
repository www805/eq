package com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.v1.service;

import com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.entity.param.Ss_dataMessageParam;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.mapper.Ss_databaseMapper;
import com.avst.equipmentcontrol.common.util.baseaction.BaseService;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.common.util.baseaction.ReqParam;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.req.addOrUpdateToOutFlushbonadingEttdParam;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.req.getToOutFlushbonadingEttdListParam;
import com.avst.equipmentcontrol.outside.interfacetoout.storage.req.addOrUpdateToOutStorageParam;
import com.avst.equipmentcontrol.outside.interfacetoout.storage.req.getToOutStorageListParam;
import com.avst.equipmentcontrol.outside.interfacetoout.storage.v1.service.ToOutStorageService;
import com.avst.equipmentcontrol.web.req.flushbonadingEttd.FlushbonadingEttdParam;
import com.avst.equipmentcontrol.web.req.flushbonadingEttd.UpdateFlushbonadingEttdParam;
import com.avst.equipmentcontrol.web.req.storage.StorageParam;
import com.avst.equipmentcontrol.web.req.storage.UpdateStorageParam;
import com.avst.equipmentcontrol.web.service.FlushbonadingEttdService;
import com.avst.equipmentcontrol.web.service.StorageService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToOutFlushbonadingEttdServiceImpl implements ToOutFlushbonadingEttdService {

    @Autowired
    private FlushbonadingEttdService flushbonadingEttdService;

    //查询列表
    @Override
    public RResult getToOutFlushbonadingEttdList(GetToOutFlushbonadingEttdListParam param, RResult result) {

        ReqParam<FlushbonadingEttdParam> reqParam = new ReqParam<FlushbonadingEttdParam>();

        FlushbonadingEttdParam flushbonadingEttdParam = new FlushbonadingEttdParam();
        flushbonadingEttdParam.setSsid(param.getSsid());
        reqParam.setParam(flushbonadingEttdParam);

        flushbonadingEttdService.getFlushbonadingEttdList(result, reqParam);

        return result;
    }

    //查询单个
    @Override
    public RResult getToOutFlushbonadingEttdById(GetToOutFlushbonadingEttdListParam param, RResult result) {

        ReqParam<FlushbonadingEttdParam> reqParam = new ReqParam<FlushbonadingEttdParam>();

        FlushbonadingEttdParam flushbonadingEttdParam = new FlushbonadingEttdParam();
        flushbonadingEttdParam.setSsid(param.getSsid());
        reqParam.setParam(flushbonadingEttdParam);

        flushbonadingEttdService.getFlushbonadingEttdById(result, reqParam);

        return result;
    }

    //新增
    @Override
    public RResult addToOutFlushbonadingEttd(AddOrUpdateToOutFlushbonadingEttdParam param, RResult result) {

        ReqParam<UpdateFlushbonadingEttdParam> reqParam = new ReqParam<UpdateFlushbonadingEttdParam>();

        UpdateFlushbonadingEttdParam flushbonadingEttdParam = new UpdateFlushbonadingEttdParam();
        flushbonadingEttdParam.setFlushbonadingssid(param.getFlushbonadingssid());
        flushbonadingEttdParam.setTdnum(param.getTdnum());
        flushbonadingEttdParam.setPullflowurl(param.getPullflowurl());
        flushbonadingEttdParam.setTdtype(param.getTdtype());
        reqParam.setParam(flushbonadingEttdParam);

        flushbonadingEttdService.addFlushbonadingEttd(result, reqParam);

        return result;
    }

    //修改
    @Override
    public RResult updateToOutFlushbonadingEttd(AddOrUpdateToOutFlushbonadingEttdParam param, RResult result) {

        ReqParam<UpdateFlushbonadingEttdParam> reqParam = new ReqParam<UpdateFlushbonadingEttdParam>();

        UpdateFlushbonadingEttdParam flushbonadingEttdParam = new UpdateFlushbonadingEttdParam();
        flushbonadingEttdParam.setSsid(param.getSsid());
        flushbonadingEttdParam.setFlushbonadingssid(param.getFlushbonadingssid());
        flushbonadingEttdParam.setTdnum(param.getTdnum());
        flushbonadingEttdParam.setPullflowurl(param.getPullflowurl());
        flushbonadingEttdParam.setTdtype(param.getTdtype());
        reqParam.setParam(flushbonadingEttdParam);

        flushbonadingEttdService.updateFlushbonadingEttd(result, reqParam);

        return result;
    }


}
