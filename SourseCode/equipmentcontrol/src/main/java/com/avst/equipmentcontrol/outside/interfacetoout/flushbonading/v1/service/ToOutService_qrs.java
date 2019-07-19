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

    /**
     * 开始光盘刻录
     * @param param
     * @param result
     * @return
     */
    public RResult startRec_Rom(StartRec_RomParam_out param,RResult result);

    /**
     * 结束光盘刻录
     * @param param
     * @param result
     * @return
     */
    public RResult stopRec_Rom(StopRec_RomParam_out param,RResult result);

    /**
     * 暂停/继续光盘刻录
     * @param param
     * @param result
     * @return
     */
    public RResult pauseOrContinueRec_Rom(PauseOrContinueRec_RomParam_out param,RResult result);

    /**
     * 光盘出仓/进仓
     * @param param
     * @param result
     * @return
     */
    public RResult dvdOutOrIn(DvdOutOrInParam_out param,RResult result);

    /**
     * 片头叠加
     * @param param
     * @param result
     * @return
     */
    public RResult ptdj(PtdjParam_out param,RResult result);

    /**
     * 获取当前配置片头字段
     * @param param
     * @param result
     * @return
     */
    public RResult getptdjconst(GetptdjconstParam_out param,RResult result);

    /**
     * 云台控制
     * @param param
     * @param result
     * @return
     */
    public RResult yuntaiControl(YuntaiControlParam_out param,RResult result);




}