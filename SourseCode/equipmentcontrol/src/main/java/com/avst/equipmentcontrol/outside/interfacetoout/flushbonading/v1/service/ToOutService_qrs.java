package com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.v1.service;

import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.common.util.baseaction.ReqParam;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.req.WorkOverParam;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.req.WorkStartParam;

public interface ToOutService_qrs {

    public RResult workStart( WorkStartParam param,RResult result);

    public RResult workOver(WorkOverParam param, RResult result);
}
