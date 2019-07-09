package com.avst.equipmentcontrol.outside.interfacetoout.tts.v1;

import com.avst.equipmentcontrol.common.conf.SSType;
import com.avst.equipmentcontrol.common.conf.TTSType;
import com.avst.equipmentcontrol.common.util.DateUtil;
import com.avst.equipmentcontrol.common.util.baseaction.BaseAction;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.common.util.baseaction.ReqParam;
import com.avst.equipmentcontrol.outside.interfacetoout.storage.req.AddOrUpdateToOutStorageParam;
import com.avst.equipmentcontrol.outside.interfacetoout.storage.req.GetToOutStorageListParam;
import com.avst.equipmentcontrol.outside.interfacetoout.storage.v1.service.ToOutStorageServiceImpl;
import com.avst.equipmentcontrol.outside.interfacetoout.tts.req.AddOrUpdateToOutTtsEtinfoParam;
import com.avst.equipmentcontrol.outside.interfacetoout.tts.req.GetToOutTtsEtinfoListParam;
import com.avst.equipmentcontrol.outside.interfacetoout.tts.v1.service.ToOutTtsEtinfoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tts/v1")
public class ToOutTtsEtinfoAction extends BaseAction {

    @Autowired
    private ToOutTtsEtinfoServiceImpl toOutTtsEtinfoService;

    private ToOutTtsEtinfoServiceImpl getToOutService(String fdType){
        if(null!=fdType){
            if(fdType.equals(TTSType.AVST)){//设备控制
                return toOutTtsEtinfoService;
            }
        }
        return null;
    }

    //查询
    @RequestMapping("/getToOutTtsEtinfoList")
    @ResponseBody
    public RResult getToOutTtsEtinfoList(@RequestBody ReqParam<GetToOutTtsEtinfoListParam> param){
        RResult result=this.createNewResultOfFail();
        if(null!=param.getParam()){
            GetToOutTtsEtinfoListParam pParam=param.getParam();
            if(null != pParam.getTtsType()){
                result=getToOutService(pParam.getTtsType()).getToOutTtsEtinfoList(pParam,result);
            }
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    };

    //单个查询
    @RequestMapping("/getToOutTtsEtinfoById")
    @ResponseBody
    public RResult getToOutTtsEtinfoById(@RequestBody ReqParam<GetToOutTtsEtinfoListParam> param){
        RResult result=this.createNewResultOfFail();
        if(null!=param.getParam()){
            GetToOutTtsEtinfoListParam pParam=param.getParam();
            if(null != pParam.getTtsType()){
                result=getToOutService(pParam.getTtsType()).getToOutTtsEtinfoById(pParam,result);
            }
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    //新增
    @RequestMapping("/addToOutTtsEtinfo")
    @ResponseBody
    public RResult addToOutTtsEtinfo(@RequestBody ReqParam<AddOrUpdateToOutTtsEtinfoParam> param){
        RResult result=this.createNewResultOfFail();
        if(null!=param.getParam()){
            AddOrUpdateToOutTtsEtinfoParam pParam=param.getParam();
            if(null != pParam.getTtsType()){
                result=getToOutService(pParam.getTtsType()).addToOutTtsEtinfo(pParam,result);
            }
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    //修改
    @RequestMapping("/updateToOutTtsEtinfo")
    @ResponseBody
    public RResult updateToOutTtsEtinfo(@RequestBody ReqParam<AddOrUpdateToOutTtsEtinfoParam> param){
        RResult result=this.createNewResultOfFail();
        if(null!=param.getParam()){
            AddOrUpdateToOutTtsEtinfoParam pParam=param.getParam();
            if(null != pParam.getTtsType()){
                result=getToOutService(pParam.getTtsType()).updateToOutTtsEtinfo(pParam,result);
            }
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

}
