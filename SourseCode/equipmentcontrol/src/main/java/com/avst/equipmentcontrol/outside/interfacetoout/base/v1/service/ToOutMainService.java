package com.avst.equipmentcontrol.outside.interfacetoout.base.v1.service;

import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.outside.interfacetoout.base.req.AddOrUpdateToOutEttypeParam;
import com.avst.equipmentcontrol.outside.interfacetoout.base.req.GetToOutBaseEcParam;
import com.avst.equipmentcontrol.outside.interfacetoout.base.req.GetToOutEttypeListParam;

public interface ToOutMainService {

    //查询全部基础类型
    public RResult getToOutBaseList(RResult result);

    //获取所有基础设备
    public RResult getToOutBaseEc(GetToOutBaseEcParam param, RResult result);

}
