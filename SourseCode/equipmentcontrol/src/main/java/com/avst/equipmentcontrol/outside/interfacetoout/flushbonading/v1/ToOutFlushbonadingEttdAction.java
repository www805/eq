package com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.v1;

import com.avst.equipmentcontrol.common.conf.FDType;
import com.avst.equipmentcontrol.common.util.DateUtil;
import com.avst.equipmentcontrol.common.util.baseaction.BaseAction;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.common.util.baseaction.ReqParam;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.req.AddOrUpdateToOutFlushbonadingEttdParam;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.req.GetToOutFlushbonadingEttdListParam;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.v1.service.ToOutFlushbonadingEttdServiceImpl;
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
    public RResult getToOutFlushbonadingEttdList(@RequestBody ReqParam<GetToOutFlushbonadingEttdListParam> param){
        RResult result=this.createNewResultOfFail();
        if(null!=param.getParam()){
            GetToOutFlushbonadingEttdListParam pParam=param.getParam();
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
    public RResult getToOutFlushbonadingEttdById(@RequestBody ReqParam<GetToOutFlushbonadingEttdListParam> param){
        RResult result=this.createNewResultOfFail();
        if(null!=param.getParam()){
            GetToOutFlushbonadingEttdListParam pParam=param.getParam();
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
    public RResult addToOutFlushbonadingEttd(@RequestBody ReqParam<AddOrUpdateToOutFlushbonadingEttdParam> param){
        RResult result=this.createNewResultOfFail();
        if(null!=param.getParam()){
            AddOrUpdateToOutFlushbonadingEttdParam pParam=param.getParam();
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
    public RResult updateToOutFlushbonadingEttd(@RequestBody ReqParam<AddOrUpdateToOutFlushbonadingEttdParam> param){
        RResult result=this.createNewResultOfFail();
        if(null!=param.getParam()){
            AddOrUpdateToOutFlushbonadingEttdParam pParam=param.getParam();
            if(null != pParam.getFdType()){
                result=getToOutService(pParam.getFdType()).updateToOutFlushbonadingEttd(pParam,result);
            }
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

}
