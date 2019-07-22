package com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.v1.service;

import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.req.*;

public interface ToOutService_qrs {

    /**
     * 设备开始工作
     * @param param
     * @param result
     * @return
     */
    public RResult workStart(WorkStartParam param, RResult result);

    /**
     * 设备结束工作
     * @param param
     * @param result
     * @return
     */
    public RResult workOver(WorkOverParam param, RResult result);

    /**
     * 获取嵌入式设备录像文件信息
     * @param param
     * @param result
     * @return
     */
    public RResult getRecordByIid(GetRecordByIidParam param, RResult result);


    /**
     * 获取嵌入式设备工作缓存
     * @param param
     * @param result
     * @return
     */
    public RResult getFDListByFdid(GetFDListByFdidParam param, RResult result);

    /**
     *
     * @param param
     * @param result
     * @return
     */
    public RResult getFTPUploadSpeedByIp(GetFTPUploadSpeedByIpParam param, RResult result);

    /**
     * 获取设备状态信息
     * @param param
     * @param result
     * @return
     */
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