package com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.v1;

import com.avst.equipmentcontrol.common.conf.FDType;
import com.avst.equipmentcontrol.common.util.DateUtil;
import com.avst.equipmentcontrol.common.util.baseaction.BaseAction;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.common.util.baseaction.ReqParam;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.req.*;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.v1.service.ToOutFlushbonadingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 设备数据编辑的接口
 */
@RestController
@RequestMapping("/flushbonading/v1")
public class ToOutFlushbonadingAction extends BaseAction {

    @Autowired
    private ToOutFlushbonadingServiceImpl toOutFlushbonadingService;

    private ToOutFlushbonadingServiceImpl getToOutService(String fdType){
        if(null!=fdType){
            if(fdType.equals(FDType.FD_AVST)){//设备控制
                return toOutFlushbonadingService;
            }
        }
        return null;
    }

    //查询
    @RequestMapping("/getToOutFlushbonadingList")
    @ResponseBody
    public RResult getToOutFlushbonadingList(@RequestBody ReqParam<GetToOutFlushbonadingListParam> param){
        RResult result=this.createNewResultOfFail();
        if(null!=param.getParam()){
            GetToOutFlushbonadingListParam pParam=param.getParam();
            if(null != pParam.getFdType()){
                result=getToOutService(pParam.getFdType()).getToOutFlushbonadingList(pParam,result);
            }
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    };

    //单个查询
    @RequestMapping("/getToOutFlushbonadingById")
    @ResponseBody
    public RResult getToOutFlushbonadingById(@RequestBody ReqParam<GetToOutFlushbonadingListParam> param){
        RResult result=this.createNewResultOfFail();
        if(null!=param.getParam()){
            GetToOutFlushbonadingListParam pParam=param.getParam();
            if(null != pParam.getFdType()){
                result=getToOutService(pParam.getFdType()).getToOutFlushbonadingById(pParam,result);
            }
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    //新增
    @RequestMapping("/addToOutFlushbonading")
    @ResponseBody
    public RResult addToOutFlushbonading(@RequestBody ReqParam<AddOrUpdateToOutFlushbonadingParam> param){
        RResult result=this.createNewResultOfFail();
        if(null!=param.getParam()){
            AddOrUpdateToOutFlushbonadingParam pParam=param.getParam();
            if(null != pParam.getFdType()){
                result=getToOutService(pParam.getFdType()).addToOutFlushbonading(pParam,result);
            }
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    //修改
    @RequestMapping("/updateToOutFlushbonading")
    @ResponseBody
    public RResult updateToOutFlushbonading(@RequestBody ReqParam<AddOrUpdateToOutFlushbonadingParam> param){
        RResult result=this.createNewResultOfFail();
        if(null!=param.getParam()){
            AddOrUpdateToOutFlushbonadingParam pParam=param.getParam();
            if(null != pParam.getFdType()){
                result=getToOutService(pParam.getFdType()).updateToOutFlushbonading(pParam,result);
            }
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    /**
     * 获取刻录选时
     * @param param
     * @return
     */
    @RequestMapping("/getBurnTime")
    @ResponseBody
    public RResult getBurnTime(@RequestBody ReqParam<GetBurnTimeParam> param){
        RResult result=this.createNewResultOfFail();
        if(null!=param.getParam()){
            GetBurnTimeParam pParam=param.getParam();
            if(null != pParam.getFdType()){
                result=getToOutService(pParam.getFdType()).getBurnTime(pParam,result);
            }
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    /**
     * 修改刻录选时
     * @param param
     * @return
     */
    @RequestMapping("/updateBurnTime")
    @ResponseBody
    public RResult updateBurnTime(@RequestBody ReqParam<GetBurnTimeParam> param){
        RResult result=this.createNewResultOfFail();
        if(null!=param.getParam()){
            GetBurnTimeParam pParam=param.getParam();
            if(null != pParam.getFdType()){
                result=getToOutService(pParam.getFdType()).updateBurnTime(pParam,result);
            }
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }
}
