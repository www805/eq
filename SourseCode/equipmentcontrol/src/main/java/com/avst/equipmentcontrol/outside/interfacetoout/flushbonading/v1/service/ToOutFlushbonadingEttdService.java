package com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.v1.service;

import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.req.AddOrUpdateToOutFlushbonadingEttdParam;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.req.GetToOutFlushbonadingEttdListParam;

public interface ToOutFlushbonadingEttdService {

    //查询列表
    public RResult getToOutFlushbonadingEttdList(GetToOutFlushbonadingEttdListParam param, RResult result);

    //查询单个
    public RResult getToOutFlushbonadingEttdById(GetToOutFlushbonadingEttdListParam param, RResult result);

    //新增
    public RResult addToOutFlushbonadingEttd(AddOrUpdateToOutFlushbonadingEttdParam param, RResult result);

    //修改
    public RResult updateToOutFlushbonadingEttd(AddOrUpdateToOutFlushbonadingEttdParam param, RResult result);

}
