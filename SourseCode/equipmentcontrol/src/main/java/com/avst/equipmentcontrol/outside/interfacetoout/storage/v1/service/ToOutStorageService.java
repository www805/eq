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

}
