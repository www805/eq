package com.avst.equipmentcontrol.outside.interfacetoout.storage.v1;

import com.avst.equipmentcontrol.common.conf.PHType;
import com.avst.equipmentcontrol.common.conf.SSType;
import com.avst.equipmentcontrol.common.util.DateUtil;
import com.avst.equipmentcontrol.common.util.baseaction.BaseAction;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.common.util.baseaction.ReqParam;
import com.avst.equipmentcontrol.outside.interfacetoout.storage.req.addOrUpdateToOutStorageParam;
import com.avst.equipmentcontrol.outside.interfacetoout.storage.req.getToOutStorageListParam;
import com.avst.equipmentcontrol.outside.interfacetoout.storage.v1.service.ToOutStorageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/storage/v1")
public class ToOutStorageAction extends BaseAction {

    @Autowired
    private ToOutStorageServiceImpl toOutStorageService;

    private ToOutStorageServiceImpl getToOutService(String fdType){
        if(null!=fdType){
            if(fdType.equals(SSType.AVST)){//设备控制
                return toOutStorageService;
            }
        }
        return null;
    }

    //查询
    @RequestMapping("/getToOutStorageList")
    @ResponseBody
    public RResult getToOutStorageList(@RequestBody ReqParam<getToOutStorageListParam> param){
        RResult result=this.createNewResultOfFail();
        if(null!=param.getParam()){
            getToOutStorageListParam pParam=param.getParam();
            if(null != pParam.getSsType()){
                result=getToOutService(pParam.getSsType()).getToOutStorageList(pParam,result);
            }
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    };

    //单个查询
    @RequestMapping("/getToOutStorageById")
    @ResponseBody
    public RResult getToOutStorageById(@RequestBody ReqParam<getToOutStorageListParam> param){
        RResult result=this.createNewResultOfFail();
        if(null!=param.getParam()){
            getToOutStorageListParam pParam=param.getParam();
            if(null != pParam.getSsType()){
                result=getToOutService(pParam.getSsType()).getToOutStorageById(pParam,result);
            }
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    //新增
    @RequestMapping("/addToOutStorage")
    @ResponseBody
    public RResult addToOutStorage(@RequestBody ReqParam<addOrUpdateToOutStorageParam> param){
        RResult result=this.createNewResultOfFail();
        if(null!=param.getParam()){
            addOrUpdateToOutStorageParam pParam=param.getParam();
            if(null != pParam.getSsType()){
                result=getToOutService(pParam.getSsType()).addToOutStorage(pParam,result);
            }
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    //修改
    @RequestMapping("/updateToOutStorage")
    @ResponseBody
    public RResult updateToOutStorage(@RequestBody ReqParam<addOrUpdateToOutStorageParam> param){
        RResult result=this.createNewResultOfFail();
        if(null!=param.getParam()){
            addOrUpdateToOutStorageParam pParam=param.getParam();
            if(null != pParam.getSsType()){
                result=getToOutService(pParam.getSsType()).updateToOutStorage(pParam,result);
            }
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }


    //根据iid查询
    @RequestMapping("/getToOutStorageByiid")
    @ResponseBody
    public RResult getToOutStorageByiid(@RequestBody ReqParam<getToOutStorageListParam> param){
        RResult result=this.createNewResultOfFail();
        if(null!=param.getParam()){
            getToOutStorageListParam pParam=param.getParam();
            if(null != pParam.getSsType()){
                result=getToOutService(pParam.getSsType()).getToOutStorageByiid(pParam,result);
            }
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

}
