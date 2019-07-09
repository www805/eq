package com.avst.equipmentcontrol.outside.interfacetoout.asr.v1.service;

import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.outside.interfacetoout.asr.req.AddOrUpdateToOutAsrParam;
import com.avst.equipmentcontrol.outside.interfacetoout.asr.req.GetToOutAsrListParam;

public interface ToOutAsrService {

    //查询全部
    public RResult getToOutAsrList(GetToOutAsrListParam param, RResult result);

    //单个查询
    public RResult getToOutAsrById(GetToOutAsrListParam param, RResult result);

    //新增
    public RResult addToOutAsr(AddOrUpdateToOutAsrParam param, RResult result);

    //修改
    public RResult updateToOutAsr(AddOrUpdateToOutAsrParam param, RResult result);


}
