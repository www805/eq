package com.avst.equipmentcontrol.outside.interfacetoout.tts.v1.service;

import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.outside.interfacetoout.tts.req.AddOrUpdateToOutTtsEtinfoParam;
import com.avst.equipmentcontrol.outside.interfacetoout.tts.req.GetToOutTtsEtinfoListParam;

public interface ToOutTtsEtinfoService {

    //查询列表
    public RResult getToOutTtsEtinfoList(GetToOutTtsEtinfoListParam param, RResult result);

    //查询单个
    public RResult getToOutTtsEtinfoById(GetToOutTtsEtinfoListParam param, RResult result);

    //新增
    public RResult addToOutTtsEtinfo(AddOrUpdateToOutTtsEtinfoParam param, RResult result);

    //修改
    public RResult updateToOutTtsEtinfo(AddOrUpdateToOutTtsEtinfoParam param, RResult result);

}
