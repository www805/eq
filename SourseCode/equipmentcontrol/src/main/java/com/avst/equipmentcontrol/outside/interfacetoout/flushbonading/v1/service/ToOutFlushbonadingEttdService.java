package com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.v1.service;

import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.req.addOrUpdateToOutFlushbonadingEttdParam;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.req.getToOutFlushbonadingEttdListParam;

public interface ToOutFlushbonadingEttdService {

    //查询列表
    public RResult getToOutFlushbonadingEttdList(getToOutFlushbonadingEttdListParam param, RResult result);

    //查询单个
    public RResult getToOutFlushbonadingEttdById(getToOutFlushbonadingEttdListParam param, RResult result);

    //新增
    public RResult addToOutFlushbonadingEttd(addOrUpdateToOutFlushbonadingEttdParam param, RResult result);

    //修改
    public RResult updateToOutFlushbonadingEttd(addOrUpdateToOutFlushbonadingEttdParam param, RResult result);

}
