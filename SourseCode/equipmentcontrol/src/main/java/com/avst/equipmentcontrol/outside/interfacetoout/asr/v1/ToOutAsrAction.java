package com.avst.equipmentcontrol.outside.interfacetoout.asr.v1;

import com.avst.equipmentcontrol.common.conf.ASRType;
import com.avst.equipmentcontrol.common.conf.SSType;
import com.avst.equipmentcontrol.common.util.DateUtil;
import com.avst.equipmentcontrol.common.util.baseaction.BaseAction;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.common.util.baseaction.ReqParam;
import com.avst.equipmentcontrol.outside.interfacetoout.asr.req.AddOrUpdateToOutAsrParam;
import com.avst.equipmentcontrol.outside.interfacetoout.asr.req.GetToOutAsrListParam;
import com.avst.equipmentcontrol.outside.interfacetoout.asr.v1.service.ToOutAsrServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/asr/v1")
public class ToOutAsrAction extends BaseAction {

    @Autowired
    private ToOutAsrServiceImpl toOutAsrService;

    private ToOutAsrServiceImpl getToOutService(String fdType){
        if(null!=fdType){
            if(fdType.equals(ASRType.AVST)){//设备控制
                return toOutAsrService;
            }
        }
        return null;
    }

    //查询
    @RequestMapping("/getToOutAsrList")
    @ResponseBody
    public RResult getToOutAsrList(@RequestBody ReqParam<GetToOutAsrListParam> param){
        RResult result=this.createNewResultOfFail();
        if(null!=param.getParam()){
            GetToOutAsrListParam pParam=param.getParam();
            if(null != pParam.getAsrtype()){
                result=getToOutService(pParam.getAsrtype()).getToOutAsrList(pParam,result);
            }
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    };

    //单个查询
    @RequestMapping("/getToOutAsrById")
    @ResponseBody
    public RResult getToOutAsrById(@RequestBody ReqParam<GetToOutAsrListParam> param){
        RResult result=this.createNewResultOfFail();
        if(null!=param.getParam()){
            GetToOutAsrListParam pParam=param.getParam();
            if(null != pParam.getAsrtype()){
                result=getToOutService(pParam.getAsrtype()).getToOutAsrById(pParam,result);
            }
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    //新增
    @RequestMapping("/addToOutAsr")
    @ResponseBody
    public RResult addToOutAsr(@RequestBody ReqParam<AddOrUpdateToOutAsrParam> param){
        RResult result=this.createNewResultOfFail();
        if(null!=param.getParam()){
            AddOrUpdateToOutAsrParam pParam=param.getParam();
            if(null != pParam.getAsrtype()){
                result=getToOutService(pParam.getAsrtype()).addToOutAsr(pParam,result);
            }
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    //修改
    @RequestMapping("/updateToOutAsr")
    @ResponseBody
    public RResult updateToOutAsr(@RequestBody ReqParam<AddOrUpdateToOutAsrParam> param){
        RResult result=this.createNewResultOfFail();
        if(null!=param.getParam()){
            AddOrUpdateToOutAsrParam pParam=param.getParam();
            if(null != pParam.getAsrtype()){
                result=getToOutService(pParam.getAsrtype()).updateToOutAsr(pParam,result);
            }
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }



}
