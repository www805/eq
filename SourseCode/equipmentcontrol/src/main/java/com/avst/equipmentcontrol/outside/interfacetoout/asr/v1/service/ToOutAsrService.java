package com.avst.equipmentcontrol.outside.interfacetoout.asr.v1.service;

import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.outside.interfacetoout.asr.req.addOrUpdateToOutAsrParam;
import com.avst.equipmentcontrol.outside.interfacetoout.asr.req.getToOutAsrListParam;

public interface ToOutAsrService {

    //查询全部
    public RResult getToOutAsrList(getToOutAsrListParam param, RResult result);

    //单个查询
    public RResult getToOutAsrById(getToOutAsrListParam param, RResult result);

    //新增
    public RResult addToOutAsr(addOrUpdateToOutAsrParam param, RResult result);

    //修改
    public RResult updateToOutAsr(addOrUpdateToOutAsrParam param, RResult result);


}
