package com.avst.equipmentcontrol.outside.interfacetoout.polygraph.v1;

import com.avst.equipmentcontrol.common.conf.PHType;
import com.avst.equipmentcontrol.common.util.DateUtil;
import com.avst.equipmentcontrol.common.util.baseaction.BaseAction;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.common.util.baseaction.ReqParam;
import com.avst.equipmentcontrol.outside.interfacetoout.polygraph.req.AddOrUpdateToOutPolygraphParam;
import com.avst.equipmentcontrol.outside.interfacetoout.polygraph.req.GetToOutPolygraphListParam;
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
    public RResult getToOutPolygraphList(@RequestBody ReqParam<GetToOutPolygraphListParam> param){
        RResult result=this.createNewResultOfFail();
        if(null!=param.getParam()){
            GetToOutPolygraphListParam pParam=param.getParam();
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
    public RResult getToOutPolygraphById(@RequestBody ReqParam<GetToOutPolygraphListParam> param){
        RResult result=this.createNewResultOfFail();
        if(null!=param.getParam()){
            GetToOutPolygraphListParam pParam=param.getParam();
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
    public RResult addToOutPolygraph(@RequestBody ReqParam<AddOrUpdateToOutPolygraphParam> param){
        RResult result=this.createNewResultOfFail();
        if(null!=param.getParam()){
            AddOrUpdateToOutPolygraphParam pParam=param.getParam();
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
    public RResult updateToOutPolygraph(@RequestBody ReqParam<AddOrUpdateToOutPolygraphParam> param){
        RResult result=this.createNewResultOfFail();
        if(null!=param.getParam()){
            AddOrUpdateToOutPolygraphParam pParam=param.getParam();
            if(null != pParam.getPhType()){
                result=getToOutService(pParam.getPhType()).updateToOutPolygraph(pParam,result);
            }
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

}
