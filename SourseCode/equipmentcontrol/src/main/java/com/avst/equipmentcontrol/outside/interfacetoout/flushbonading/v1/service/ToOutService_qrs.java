package com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.v1.service;

import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.req.*;

public interface ToOutService_qrs {

    public RResult workStart(WorkStartParam param, RResult result);

    public RResult workOver(WorkOverParam param, RResult result);

    public RResult getRecordByIid(GetRecordByIidParam param, RResult result);

    public RResult getFDListByFdid(GetFDListByFdidParam param, RResult result);

    public RResult getFTPUploadSpeedByIp(GetFTPUploadSpeedByIpParam param, RResult result);

    public RResult getFDState(GetFDStateParam param,RResult result);

}