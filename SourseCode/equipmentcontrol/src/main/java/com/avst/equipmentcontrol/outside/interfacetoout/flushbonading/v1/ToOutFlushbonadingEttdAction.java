package com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.v1;

import com.avst.equipmentcontrol.common.conf.FDType;
import com.avst.equipmentcontrol.common.conf.SSType;
import com.avst.equipmentcontrol.common.util.DateUtil;
import com.avst.equipmentcontrol.common.util.baseaction.BaseAction;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.common.util.baseaction.ReqParam;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.req.addOrUpdateToOutFlushbonadingEttdParam;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.req.getToOutFlushbonadingEttdListParam;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.v1.service.ToOutFlushbonadingEttdServiceImpl;
import com.avst.equipmentcontrol.outside.interfacetoout.storage.req.addOrUpdateToOutStorageParam;
import com.avst.equipmentcontrol.outside.interfacetoout.storage.req.getToOutStorageListParam;
import com.avst.equipmentcontrol.outside.interfacetoout.storage.v1.service.ToOutStorageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/flushbonadingEttd/v1")
public class ToOutFlushbonadingEttdAction extends BaseAction {

    @Autowired
    private ToOutFlushbonadingEttdServiceImpl toOutFlushbonadingEttdService;

    private ToOutFlushbonadingEttdServiceImpl getToOutService(String fdType){
        if(null!=fdType){
            if(fdType.equals(FDType.FD_AVST)){//设备控制
                return toOutFlushbonadingEttdService;
            }
        }
        return null;
    }

    //查询
    @RequestMapping("/getToOutFlushbonadingEttdList")
    @ResponseBody
    public RResult getToOutFlushbonadingEttdList(@RequestBody ReqParam<getToOutFlushbonadingEttdListParam> param){
        RResult result=this.createNewResultOfFail();
        if(null!=param.getParam()){
            getToOutFlushbonadingEttdListParam pParam=param.getParam();
            if(null != pParam.getFdType()){
                result=getToOutService(pParam.getFdType()).getToOutFlushbonadingEttdList(pParam,result);
            }
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    };

    //单个查询
    @RequestMapping("/getToOutFlushbonadingEttdById")
    @ResponseBody
    public RResult getToOutFlushbonadingEttdById(@RequestBody ReqParam<getToOutFlushbonadingEttdListParam> param){
        RResult result=this.createNewResultOfFail();
        if(null!=param.getParam()){
            getToOutFlushbonadingEttdListParam pParam=param.getParam();
            if(null != pParam.getFdType()){
                result=getToOutService(pParam.getFdType()).getToOutFlushbonadingEttdById(pParam,result);
            }
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    //新增
    @RequestMapping("/addToOutFlushbonadingEttd")
    @ResponseBody
    public RResult addToOutFlushbonadingEttd(@RequestBody ReqParam<addOrUpdateToOutFlushbonadingEttdParam> param){
        RResult result=this.createNewResultOfFail();
        if(null!=param.getParam()){
            addOrUpdateToOutFlushbonadingEttdParam pParam=param.getParam();
            if(null != pParam.getFdType()){
                result=getToOutService(pParam.getFdType()).addToOutFlushbonadingEttd(pParam,result);
            }
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    //修改
    @RequestMapping("/updateToOutFlushbonadingEttd")
    @ResponseBody
    public RResult updateToOutFlushbonadingEttd(@RequestBody ReqParam<addOrUpdateToOutFlushbonadingEttdParam> param){
        RResult result=this.createNewResultOfFail();
        if(null!=param.getParam()){
            addOrUpdateToOutFlushbonadingEttdParam pParam=param.getParam();
            if(null != pParam.getFdType()){
                result=getToOutService(pParam.getFdType()).updateToOutFlushbonadingEttd(pParam,result);
            }
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

}
