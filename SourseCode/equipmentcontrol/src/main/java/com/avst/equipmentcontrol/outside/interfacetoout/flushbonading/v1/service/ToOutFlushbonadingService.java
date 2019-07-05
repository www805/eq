package com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.v1.service;

import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.outside.interfacetoout.asr.req.addOrUpdateToOutAsrParam;
import com.avst.equipmentcontrol.outside.interfacetoout.asr.req.getToOutAsrListParam;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.req.addOrUpdateToOutFlushbonadingParam;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.req.getToOutFlushbonadingListParam;

public interface ToOutFlushbonadingService {

    //查询全部
    public RResult getToOutFlushbonadingList(getToOutFlushbonadingListParam param, RResult result);

    //单个查询
    public RResult getToOutFlushbonadingById(getToOutFlushbonadingListParam param, RResult result);

    //新增
    public RResult addToOutFlushbonading(addOrUpdateToOutFlushbonadingParam param, RResult result);

    //修改
    public RResult updateToOutFlushbonading(addOrUpdateToOutFlushbonadingParam param, RResult result);


}
