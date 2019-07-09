package com.avst.equipmentcontrol.outside.interfacetoout.storage.v1.service;

import com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.entity.Storage_ettype;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.entity.param.Ss_dataMessageParam;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.mapper.Ss_databaseMapper;
import com.avst.equipmentcontrol.common.util.baseaction.BaseService;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.common.util.baseaction.ReqParam;
import com.avst.equipmentcontrol.outside.interfacetoout.storage.req.addOrUpdateToOutStorageParam;
import com.avst.equipmentcontrol.outside.interfacetoout.storage.req.getToOutStorageListParam;
import com.avst.equipmentcontrol.web.req.storage.StorageParam;
import com.avst.equipmentcontrol.web.req.storage.UpdateStorageParam;
import com.avst.equipmentcontrol.web.service.StorageService;
import com.avst.equipmentcontrol.web.vo.storage.StorageVO;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToOutStorageServiceImpl extends BaseService implements ToOutStorageService {

    @Autowired
    private StorageService storageService;

    @Autowired
    private Ss_databaseMapper ssDatabaseMapper;

    //查询列表
    @Override
    public RResult getToOutStorageList(getToOutStorageListParam param, RResult result) {

        ReqParam<StorageParam> reqParam = new ReqParam<StorageParam>();

        StorageParam StorageParam = new StorageParam();
        reqParam.setParam(StorageParam);

        storageService.getStorageList(result, reqParam);

        if(!"FAIL".equals(result.getActioncode())){
            StorageVO data = (StorageVO) result.getData();
            List<Storage_ettype> list = data.getPagelist();
            result.setData(list);
        }

        return result;
    }

    //查询单个
    @Override
    public RResult getToOutStorageById(getToOutStorageListParam param, RResult result) {

        ReqParam<StorageParam> reqParam = new ReqParam<StorageParam>();

        StorageParam StorageParam = new StorageParam();
        StorageParam.setSsid(param.getSsid());
        reqParam.setParam(StorageParam);

        storageService.getStorageById(result, reqParam);

        return result;
    }

    //新增
    @Override
    public RResult addToOutStorage(addOrUpdateToOutStorageParam param, RResult result) {

        ReqParam<UpdateStorageParam> reqParam = new ReqParam<UpdateStorageParam>();

        UpdateStorageParam storageParam = new UpdateStorageParam();
        storageParam.setTotalcapacity(param.getTotalcapacity());
        storageParam.setPort(param.getPort());
        storageParam.setSstype(param.getSstype());
        storageParam.setDatasavebasepath(param.getDatasavebasepath());
        storageParam.setEtnum(param.getEtnum());
        storageParam.setEtip(param.getEtip());
        storageParam.setEtypessid(param.getEtypessid());
        storageParam.setExplain(param.getExplain());
        reqParam.setParam(storageParam);

        storageService.addStorage(result, reqParam);

        return result;
    }

    //修改
    @Override
    public RResult updateToOutStorage(addOrUpdateToOutStorageParam param, RResult result) {

        ReqParam<UpdateStorageParam> reqParam = new ReqParam<UpdateStorageParam>();

        UpdateStorageParam storageParam = new UpdateStorageParam();
        storageParam.setSsid(param.getSsid());
        storageParam.setTotalcapacity(param.getTotalcapacity());
        storageParam.setPort(param.getPort());
        storageParam.setSstype(param.getSstype());
        storageParam.setDatasavebasepath(param.getDatasavebasepath());
        storageParam.setEtnum(param.getEtnum());
        storageParam.setEtip(param.getEtip());
        storageParam.setEtypessid(param.getEtypessid());
        storageParam.setExplain(param.getExplain());
        reqParam.setParam(storageParam);

        storageService.updateStorage(result, reqParam);

        return result;
    }

    //根据iid查询
    @Override
    public RResult getToOutStorageByiid(getToOutStorageListParam param, RResult result) {

        //请求参数转换
        if (null==param){
            result.setMessage("参数为空");
            return result;
        }

        if (StringUtils.isBlank(param.getIid())){
            result.setMessage("iid不能为空");
            return result;
        }

        EntityWrapper ew = new EntityWrapper();
        ew.eq("iid", param.getIid());

        List<Ss_dataMessageParam> ss_databaseByIid = ssDatabaseMapper.getSs_databaseByIid(ew);
        result.setData(ss_databaseByIid);
        changeResultToSuccess(result);
        return result;
    }


}
