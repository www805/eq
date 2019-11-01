package com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.v1.service;

import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.common.util.baseaction.ReqParam;
import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.req.GetMiddleware_FTPParam;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.req.*;

public interface ToOutFlushbonadingService {

    //查询全部
    public RResult getToOutFlushbonadingList(GetToOutFlushbonadingListParam param, RResult result);

    //单个查询
    public RResult getToOutFlushbonadingById(GetToOutFlushbonadingListParam param, RResult result);

    //新增
    public RResult addToOutFlushbonading(AddOrUpdateToOutFlushbonadingParam param, RResult result);

    //修改
    public RResult updateToOutFlushbonading(AddOrUpdateToOutFlushbonadingParam param, RResult result);

    //获取默认的视频直播地址
    public RResult getToOutDefault(GetToOutFlushbonadingListParam param, RResult result);

    public RResult getToOutMiddleware_FTP(GetToOutMiddleware_FTPParam param, RResult result);

    public RResult setToOutMiddleware_FTP(SetToOutMiddleware_FTPParam param, RResult result);

}
