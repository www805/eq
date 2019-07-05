package com.avst.equipmentcontrol.outside.interfacetoout.polygraph.v1;

import com.avst.equipmentcontrol.common.conf.FDType;
import com.avst.equipmentcontrol.common.conf.PHType;
import com.avst.equipmentcontrol.common.util.DateUtil;
import com.avst.equipmentcontrol.common.util.baseaction.BaseAction;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.common.util.baseaction.ReqParam;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.req.addOrUpdateToOutFlushbonadingParam;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.req.getToOutFlushbonadingListParam;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.v1.service.ToOutFlushbonadingServiceImpl;
import com.avst.equipmentcontrol.outside.interfacetoout.polygraph.req.addOrUpdateToOutPolygraphParam;
import com.avst.equipmentcontrol.outside.interfacetoout.polygraph.req.getToOutPolygraphListParam;
import com.avst.equipmentcontrol.outside.interfacetoout.polygraph.v1.service.ToOutPolygraphServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/polygraph/v1")
public class ToOutPolygraphAction extends BaseAction {

    @Autowired
    private ToOutPolygraphServiceImpl toOutPolygraphService;

    private ToOutPolygraphServiceImpl getToOutService(String fdType){
        if(null!=fdType){
            if(fdType.equals(PHType.CMCROSS)){//设备控制
                return toOutPolygraphService;
            }
        }
        return null;
    }

    //查询
    @RequestMapping("/getToOutPolygraphList")
    @ResponseBody
    public RResult getToOutPolygraphList(@RequestBody ReqParam<getToOutPolygraphListParam> param){
        RResult result=this.createNewResultOfFail();
        if(null!=param.getParam()){
            getToOutPolygraphListParam pParam=param.getParam();
            if(null != pParam.getPhType()){
                result=getToOutService(pParam.getPhType()).getToOutPolygraphList(pParam,result);
            }
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    };

    //单个查询
    @RequestMapping("/getToOutPolygraphById")
    @ResponseBody
    public RResult getToOutPolygraphById(@RequestBody ReqParam<getToOutPolygraphListParam> param){
        RResult result=this.createNewResultOfFail();
        if(null!=param.getParam()){
            getToOutPolygraphListParam pParam=param.getParam();
            if(null != pParam.getPhType()){
                result=getToOutService(pParam.getPhType()).getToOutPolygraphById(pParam,result);
            }
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    //新增
    @RequestMapping("/addToOutPolygraph")
    @ResponseBody
    public RResult addToOutPolygraph(@RequestBody ReqParam<addOrUpdateToOutPolygraphParam> param){
        RResult result=this.createNewResultOfFail();
        if(null!=param.getParam()){
            addOrUpdateToOutPolygraphParam pParam=param.getParam();
            if(null != pParam.getPhType()){
                result=getToOutService(pParam.getPhType()).addToOutPolygraph(pParam,result);
            }
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    //修改
    @RequestMapping("/updateToOutPolygraph")
    @ResponseBody
    public RResult updateToOutPolygraph(@RequestBody ReqParam<addOrUpdateToOutPolygraphParam> param){
        RResult result=this.createNewResultOfFail();
        if(null!=param.getParam()){
            addOrUpdateToOutPolygraphParam pParam=param.getParam();
            if(null != pParam.getPhType()){
                result=getToOutService(pParam.getPhType()).updateToOutPolygraph(pParam,result);
            }
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

}
