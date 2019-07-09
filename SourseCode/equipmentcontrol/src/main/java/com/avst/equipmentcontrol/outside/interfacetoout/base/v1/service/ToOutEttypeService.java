package com.avst.equipmentcontrol.outside.interfacetoout.base.v1.service;

import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.outside.interfacetoout.base.req.AddOrUpdateToOutEttypeParam;
import com.avst.equipmentcontrol.outside.interfacetoout.base.req.GetToOutEttypeListParam;

public interface ToOutEttypeService {

    //查询全部
    public RResult getToOutEttypeList(GetToOutEttypeListParam param, RResult result);

    //单个查询
    public RResult getToOutEttypeById(GetToOutEttypeListParam param, RResult result);

    //新增
    public RResult addToOutEttype(AddOrUpdateToOutEttypeParam param, RResult result);

    //修改
    public RResult updateToOutEttype(AddOrUpdateToOutEttypeParam param, RResult result);


}
