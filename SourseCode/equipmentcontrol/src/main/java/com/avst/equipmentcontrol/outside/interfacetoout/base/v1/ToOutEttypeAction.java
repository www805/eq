package com.avst.equipmentcontrol.outside.interfacetoout.base.v1;

import com.avst.equipmentcontrol.common.conf.FDType;
import com.avst.equipmentcontrol.common.conf.PHType;
import com.avst.equipmentcontrol.common.conf.SSType;
import com.avst.equipmentcontrol.common.util.DateUtil;
import com.avst.equipmentcontrol.common.util.baseaction.BaseAction;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.common.util.baseaction.ReqParam;
import com.avst.equipmentcontrol.outside.interfacetoout.base.req.addOrUpdateToOutEttypeParam;
import com.avst.equipmentcontrol.outside.interfacetoout.base.req.getToOutEttypeListParam;
import com.avst.equipmentcontrol.outside.interfacetoout.base.v1.service.ToOutEttypeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/base/v1")
public class ToOutEttypeAction extends BaseAction {

    @Autowired
    private ToOutEttypeServiceImpl toOutEttypeService;

    //查询
    @RequestMapping("/getToOutEttypeList")
    @ResponseBody
    public RResult getToOutEttypeList(@RequestBody ReqParam<getToOutEttypeListParam> param){
        RResult result=this.createNewResultOfFail();
        if(null!=param.getParam()){
            getToOutEttypeListParam pParam=param.getParam();
            result=toOutEttypeService.getToOutEttypeList(pParam,result);
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    };

    //单个查询
    @RequestMapping("/getToOutEttypeById")
    @ResponseBody
    public RResult getToOutEttypeById(@RequestBody ReqParam<getToOutEttypeListParam> param){
        RResult result=this.createNewResultOfFail();
        if(null!=param.getParam()){
            getToOutEttypeListParam pParam=param.getParam();
            result=toOutEttypeService.getToOutEttypeById(pParam,result);
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    //新增
    @RequestMapping("/addToOutEttype")
    @ResponseBody
    public RResult addToOutEttype(@RequestBody ReqParam<addOrUpdateToOutEttypeParam> param){
        RResult result=this.createNewResultOfFail();
        if(null!=param.getParam()){
            addOrUpdateToOutEttypeParam pParam=param.getParam();
            result=toOutEttypeService.addToOutEttype(pParam,result);
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    //修改
    @RequestMapping("/updateToOutEttype")
    @ResponseBody
    public RResult updateToOutEttype(@RequestBody ReqParam<addOrUpdateToOutEttypeParam> param){
        RResult result=this.createNewResultOfFail();
        if(null!=param.getParam()){
            addOrUpdateToOutEttypeParam pParam=param.getParam();
            result=toOutEttypeService.updateToOutEttype(pParam,result);
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

}
