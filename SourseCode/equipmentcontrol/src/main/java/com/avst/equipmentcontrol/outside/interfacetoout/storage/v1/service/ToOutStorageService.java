package com.avst.equipmentcontrol.outside.interfacetoout.storage.v1.service;

import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.outside.interfacetoout.storage.req.AddOrUpdateToOutStorageParam;
import com.avst.equipmentcontrol.outside.interfacetoout.storage.req.GetToOutStorageListParam;

public interface ToOutStorageService {

    //查询列表
    public RResult getToOutStorageList(GetToOutStorageListParam param, RResult result);

    //查询单个
    public RResult getToOutStorageById(GetToOutStorageListParam param, RResult result);

    //新增
    public RResult addToOutStorage(AddOrUpdateToOutStorageParam param, RResult result);

    //修改
    public RResult updateToOutStorage(AddOrUpdateToOutStorageParam param, RResult result);

    //根据iid查询
    public RResult getToOutStorageByiid(GetToOutStorageListParam param, RResult result);

    //提供磁盘使用信息列表
    RResult getToOutFileSpaceList(GetToOutStorageListParam pParam, RResult result);

    //通过ssid查询文件管理
    RResult getToOutFileSpaceByssid(GetToOutStorageListParam pParam, RResult result);

    //查询路径下的所有文件
    RResult getToOutFileSpaceAll(GetToOutStorageListParam pParam, RResult result);

    //删除当前路径下的所有文件
    RResult delToOutFileSpaceAll(GetToOutStorageListParam pParam, RResult result);

    //删除单个文件
    RResult delToOutFileSpaceByPath(GetToOutStorageListParam pParam, RResult result);

}
