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

}
