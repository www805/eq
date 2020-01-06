package com.avst.equipmentcontrol.web.service;

import com.avst.equipmentcontrol.common.datasourse.extrasourse.flushbonading.entity.param.Flushbonadinginfo;
import com.avst.equipmentcontrol.common.util.LogUtil;
import com.avst.equipmentcontrol.common.util.SpringUtil;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.FDInterface;
import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.req.GetMiddleware_FTPParam;
import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.req.SetMiddleware_FTPParam;
import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.vo.GetMiddleware_FTPVO;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MiddlewareAsyncService {

    /**
     * 异步修改ftp上传存储路径名和设备ID保持一致
     * @param flushbonadingEtinfo
     * @param result
     * @param paramParam
     */
    @Async
    public void updateMiddleware_ftpByID(Flushbonadinginfo flushbonadingEtinfo, RResult result, Flushbonadinginfo paramParam){

        GetMiddleware_FTPParam getMiddleware_ftpParam=new GetMiddleware_FTPParam();
        getMiddleware_ftpParam.setIp(flushbonadingEtinfo.getEtip());
        getMiddleware_ftpParam.setPasswd(flushbonadingEtinfo.getPasswd());
        getMiddleware_ftpParam.setPort(flushbonadingEtinfo.getPort());
        getMiddleware_ftpParam.setUser(flushbonadingEtinfo.getUser());

        FDInterface fdInterface = SpringUtil.getBean(FDInterface.class);

        RResult middlewareFtp = fdInterface.getMiddleware_FTP(getMiddleware_ftpParam, result);

        if(!"FAIL".equals(middlewareFtp.getActioncode())){
            GetMiddleware_FTPVO middlewareData = (GetMiddleware_FTPVO) middlewareFtp.getData();

            //因为设备ID要和ftp上传存储路径名要保持一致
            if(null != middlewareData && null != middlewareData.getDeviceid() && !middlewareData.getDeviceid().equals(paramParam.getUploadbasepath())){
                SetMiddleware_FTPParam setMiddleware_ftpParam = new SetMiddleware_FTPParam();
                setMiddleware_ftpParam.setEnable(middlewareData.getEnable());
                setMiddleware_ftpParam.setPasvmode(middlewareData.getPassvmode());
                setMiddleware_ftpParam.setServicename(middlewareData.getServicename());
                setMiddleware_ftpParam.setServerport(middlewareData.getServiceport());
                setMiddleware_ftpParam.setServerip(middlewareData.getServerip());
                setMiddleware_ftpParam.setHreadbeatip(middlewareData.getHreadbeatip());
                setMiddleware_ftpParam.setIp(paramParam.getEtip());
                setMiddleware_ftpParam.setPort(paramParam.getPort());
                setMiddleware_ftpParam.setPasswd(middlewareData.getPass());
                setMiddleware_ftpParam.setUser(middlewareData.getUser());
                setMiddleware_ftpParam.setLimit_speed(middlewareData.getLimit_speed());
                setMiddleware_ftpParam.setFilter_enable(middlewareData.getFilter_enable());
                setMiddleware_ftpParam.setSearch_filter(middlewareData.getSearch_filter());
                setMiddleware_ftpParam.setDeviceid(paramParam.getUploadbasepath());

                middlewareFtp = fdInterface.setMiddleware_FTP(setMiddleware_ftpParam, result);
                LogUtil.intoLog(1, this.getClass(), "设备ID和ftp储路径名修改成一致：" + middlewareFtp.getMessage());
            }
        }

    }

}
