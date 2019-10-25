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
     * 设备暂停或者继续工作
     * @param param
     * @param result
     * @return
     */
    public RResult workPauseOrContinue(WorkPauseOrContinueParam param, RResult result);


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

    /**
     * 刻录模式选择
     * @param param
     * @param result
     * @return
     */
    public RResult changeBurnMode(ChangeBurnModeParam_out param,RResult result);


    /**
     * 获取光盘序号
     * @param param
     * @param result
     * @return
     */
    public RResult getCDNumber(GetCDNumberParam_out param,RResult result);

    /**
     * 获取刻录选时
     * @param pParam
     * @param result
     * @return
     */
    RResult getBurnTime(GetBurnTimeParam pParam, RResult result);

    /**
     * 修改刻录选时
     * @param pParam
     * @param result
     * @return
     */
    RResult updateBurnTime(GetBurnTimeParam pParam, RResult result);


    /**
     * 日志查询信息
     * @param pParam
     * @param result
     * @return
     */
    RResult getFDLog(GetFDLogParam_out pParam, RResult result);


    /**
     * 获得 设备现场的音频振幅
     * @param pParam
     * @param result
     * @return
     */
    RResult getFDAudPowerMap(GetFDAudPowerMapParam_out pParam, RResult result);


    /**
     * 配置设备网口 IP、子网掩码、网关
     * @param pParam
     * @param result
     * @return
     */
    RResult setFDnetwork(SetFDnetworkParam_out pParam, RResult result);

    /**
     * 获取设备网口 IP、子网掩码、网关
     * @param pParam
     * @param result
     * @return
     */
    RResult getFDNetWork(GetFDNetWorkParam_out pParam, RResult result);


    /**
     * 设置设备某一个通道的通道音量
     * @param pParam
     * @param result
     * @return
     */
    RResult setFDAudioVolume(SetFDAudioVolumeParam_out pParam, RResult result);

    /**
     * 获得设备音频配置
     * @param pParam
     * @param result
     * @return
     */
    RResult getFDAudioConf(GetFDAudioConfParam_out pParam, RResult result);

    /**
     * 设置设备当前时间
     * @param pParam
     * @param result
     * @return
     */
    RResult setFDTime(SetFDTimeParam_out pParam, RResult result);

    /**
     * 设置设备NTP同步
     * @param result
     * @param pParam
     * @return
     */
    RResult setFDNTP(SetFDNTPParam_out pParam, RResult result);

    /**
     * 光盘补刻
     * @param pParam
     * @param result
     * @return
     */
    RResult supplementBurn(SupplementBurnParam_out pParam, RResult result);

    /**
     * 获取设备NTP同步设置
     * @param pParam
     * @param result
     * @return
     */
    RResult getFDNTP(GetFDNTPParam_out pParam, RResult result);

    /**
     * 设置设备部分信息叠加位置
     * @param pParam
     * @param result
     * @return
     */
    RResult setFDOSD(SetFDOSDParam_out pParam, RResult result);

    /**
     * 获取设备部分信息叠加位置
     * @param pParam
     * @param result
     * @return
     */
    RResult getFDOSD(GetFDOSDParam_out pParam, RResult result);


    /**
     * 获取设备硬盘中iid对应的所有文件（包括视频、文本、Word等）
     * @param pParam
     * @param result
     * @return
     */
    RResult getFDAllFileListByIid(GetFDAllFileListByIidParam_out pParam, RResult result);


}