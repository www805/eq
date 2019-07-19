package com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl;

import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.req.*;
import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.vo.*;

/**
 * 需要对接的设备接口
 */
public interface FDInterface {

    /**
     * 设备状态查询
     * @param param
     * @return
     */
    public RResult<CheckFDStateVO> CheckFDState(CheckFDStateParam param,RResult<CheckFDStateVO> result);

    /**
     * 设备硬盘开始录像
     * @param param
     * @return
     */
    public RResult<StartRecVO>  startRec(StartRecParam param,RResult<StartRecVO> result);

    /**
     * 设备硬盘停止录像
     * @param param
     * @return
     */
    public RResult<StopRecVO>  stopRec(StopRecParam param,RResult<StopRecVO> result);

    /**
     * 关闭系统
     * @param param
     * @return
     */
    public RResult<ShutdownVO>  shutdown(ShutdownParam param,RResult<ShutdownVO> result);

    /**
     * API 接口-设置 ftp 上传模式
     * @param param
     * @return
     */
    public RResult<SetFTPModeVO>  setFTPMode(SetFTPModeParam param,RResult<SetFTPModeVO> result);

    /**
     * API 接口-获得 ftp 上传进度
     * @param param
     * @return
     */
    public RResult<GetFTPUploadSpeedVO>  getFTPUploadSpeed(GetFTPUploadSpeedParam param,RResult<GetFTPUploadSpeedVO> result);

    /**
     * API 接口-上传 ftp 指定的录像 ID
     * @param param
     * @return
     */
    public RResult<FtpUploadRecordByIidVO>  ftpUploadRecordByIid(FtpUploadRecordByIidParam param,RResult<FtpUploadRecordByIidVO> result);

    /**
     * 获取硬盘信息，主要是分区信息
     * 用于查看硬盘大小和 ftpUploadRecordByIid需要的参数，盘符index
     * @param param
     * @return
     */
    public RResult<GetDiskInfoVO>  getDiskInfo(GetDiskInfoParam param,RResult<GetDiskInfoVO> result);

    /**
     * 集中存储配置,配置设备ftp上传
     * @param param
     * @return
     */
    public RResult<SetMiddleware_FTPVO> setMiddleware_FTP(SetMiddleware_FTPParam param,RResult<SetMiddleware_FTPVO> result);

    /**
     * 获取设备录像文件地址，通过该录像关联的唯一码来获取
     * @param param
     * @return
     */
    public RResult<GetETRecordByIidVO> getETRecordByIid(GetETRecordByIidParam param, RResult<GetETRecordByIidVO> result);

    /**
     * API 接口-指定 ftp 上传文件
     * 上传指定文件
     * @param param
     * @return
     */
    public RResult<UploadFileByPathVO> uploadFileByPath(UploadFileByPathParam param, RResult<UploadFileByPathVO> result);

    /**
     * API 接口-上传文件到设备指定iid的文件夹中
     * 上传指定文件
     * @param param
     * @return
     */
    public RResult<UploadServiceByIidVO> uploadServiceByIid(UploadServiceByIidParam param, RResult<UploadServiceByIidVO> result);

    /**
     * API 接口-获取当前配置片头字段
     * @param param
     * @param result
     * @return
     */
    public RResult<GetptdjconstVO> getptdjconst(GetptdjconstParam param, RResult<GetptdjconstVO> result);


    /**
     * 开始光驱刻录（异步请求）
     * @param param
     * @param result
     * @return
     */
    public RResult<StartRec_RomVO> startRec_Rom(StartRec_RomParam param, RResult<StartRec_RomVO> result);

    /**
     * 暂停光驱刻录
     * @param param
     * @param result
     * @return
     */
    public RResult<PauseRec_RomVO> pauseRec_Rom(PauseRec_RomParam param, RResult<PauseRec_RomVO> result);

    /**
     * 继续光驱刻录
     * @param param
     * @param result
     * @return
     */
    public RResult<GgoonRec_RomVO> goonRec_Rom(GgoonRec_RomParam param, RResult<GgoonRec_RomVO> result);

    /**
     * 停止光驱刻录（异步请求）
     * @param param
     * @param result
     * @return
     */
    public RResult<StopRec_RomVO> stopRec_Rom(StopRec_RomParam param, RResult<StopRec_RomVO> result);

    /**
     * 光驱出仓
     * @param param
     * @param result
     * @return
     */
    public RResult<Eject_RomVO> eject_Rom(Eject_RomParam param, RResult<Eject_RomVO> result);

    /**
     * 光驱进仓
     * @param param
     * @param result
     * @return
     */
    public RResult<Closetray_RomVO> closetray_Rom(Closetray_RomParam param, RResult<Closetray_RomVO> result);

    /**
     * 片头叠加
     * @param param
     * @param result
     * @return
     */
    public RResult<PtdjVO> ptdj(PtdjParam param, RResult<PtdjVO> result);

    /**
     * 云台控制
     * 这里是一个按钮点住、松开2个事件，
     * 按住调用up、down、left、right、focus_increase(聚焦+）、 focus_decrease(聚焦-)、depth_far（倍变+）、depth_near（倍 变-）
     * 松开调用 stop
     * 一个操作要调用2次接口
     * @param param
     * @param result
     * @return
     */
    public RResult<YuntaiControlVO> yuntaiControl(YuntaiControlParam param, RResult<YuntaiControlVO> result);



}
