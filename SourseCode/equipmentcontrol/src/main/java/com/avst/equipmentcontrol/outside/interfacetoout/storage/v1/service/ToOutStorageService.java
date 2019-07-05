package com.avst.equipmentcontrol.outside.interfacetoout.storage.v1.service;

import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.outside.interfacetoout.storage.req.addOrUpdateToOutStorageParam;
import com.avst.equipmentcontrol.outside.interfacetoout.storage.req.getToOutStorageListParam;

public interface ToOutStorageService {

    //查询列表
    public RResult getToOutStorageList(getToOutStorageListParam param, RResult result);

    //查询单个
    public RResult getToOutStorageById(getToOutStorageListParam param, RResult result);

    //新增
    public RResult addToOutStorage(addOrUpdateToOutStorageParam param, RResult result);

    //修改
    public RResult updateToOutStorage(addOrUpdateToOutStorageParam param, RResult result);

    //根据iid查询
    public RResult getToOutStorageByiid(getToOutStorageListParam param, RResult result);

}
