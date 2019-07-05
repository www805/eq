package com.avst.equipmentcontrol.outside.interfacetoout.base.v1.service;

import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.outside.interfacetoout.base.req.addOrUpdateToOutEttypeParam;
import com.avst.equipmentcontrol.outside.interfacetoout.base.req.getToOutEttypeListParam;

public interface ToOutEttypeService {

    //查询全部
    public RResult getToOutEttypeList(getToOutEttypeListParam param, RResult result);

    //单个查询
    public RResult getToOutEttypeByTerm(getToOutEttypeListParam param, RResult result);

    //新增
    public RResult addToOutEttype(addOrUpdateToOutEttypeParam param, RResult result);

    //修改
    public RResult updateToOutEttype(addOrUpdateToOutEttypeParam param, RResult result);


}
