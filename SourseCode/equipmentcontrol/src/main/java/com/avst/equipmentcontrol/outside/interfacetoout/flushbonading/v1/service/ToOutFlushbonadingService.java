package com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.v1.service;

import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.req.AddOrUpdateToOutFlushbonadingParam;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.req.BaseParam;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.req.GetBurnTimeParam;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.req.GetToOutFlushbonadingListParam;

public interface ToOutFlushbonadingService {

    //查询全部
    public RResult getToOutFlushbonadingList(GetToOutFlushbonadingListParam param, RResult result);

    //单个查询
    public RResult getToOutFlushbonadingById(GetToOutFlushbonadingListParam param, RResult result);

    //新增
    public RResult addToOutFlushbonading(AddOrUpdateToOutFlushbonadingParam param, RResult result);

    //修改
    public RResult updateToOutFlushbonading(AddOrUpdateToOutFlushbonadingParam param, RResult result);

    //获取刻录选时
    RResult getBurnTime(GetBurnTimeParam pParam, RResult result);

    //修改刻录选时
    RResult updateBurnTime(GetBurnTimeParam pParam, RResult result);
}
