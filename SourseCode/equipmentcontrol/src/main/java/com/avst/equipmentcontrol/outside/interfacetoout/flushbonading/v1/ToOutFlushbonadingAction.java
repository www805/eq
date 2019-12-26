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

    //获取默认的设备信息
    @RequestMapping("/getToOutDefault")
    @ResponseBody
    public RResult getToOutDefault(@RequestBody ReqParam<GetToOutFlushbonadingListParam> param){
        RResult result=this.createNewResultOfFail();
        if(null!=param.getParam()){
            GetToOutFlushbonadingListParam pParam=param.getParam();
            if(null != pParam.getFdType()){
                result = getToOutService(pParam.getFdType()).getToOutDefault(pParam, result);
            }
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    };


    /**
     * 获取集中存储配置,ftp的上传服务器配置
     * @param param
     * @return
     */
    @RequestMapping("/getToOutMiddleware_FTP")
    @ResponseBody
    public RResult getToOutMiddleware_FTP(@RequestBody ReqParam<GetToOutMiddleware_FTPParam> param){
        RResult result=this.createNewResultOfFail();
        if(null!=param.getParam()){
            GetToOutMiddleware_FTPParam pParam=param.getParam();
            if(null != pParam.getFdType()){
                result = getToOutService(pParam.getFdType()).getToOutMiddleware_FTP(pParam, result);
            }
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }


    /**
     * 集中存储配置,配置设备ftp上传
     * @param param
     * @return
     */
    @RequestMapping("/setToOutMiddleware_FTP")
    @ResponseBody
    public RResult setToOutMiddleware_FTP(@RequestBody ReqParam<SetToOutMiddleware_FTPParam> param){
        RResult result=this.createNewResultOfFail();
        if(null!=param.getParam()){
            SetToOutMiddleware_FTPParam pParam=param.getParam();
            if(null != pParam.getFdType()){
                result = getToOutService(pParam.getFdType()).setToOutMiddleware_FTP(pParam, result);
            }
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    /**
     * 获取默认设备ftp端口
     * @param param
     * @return
     */
    @RequestMapping("/getToOutFtpPort")
    @ResponseBody
    public RResult getToOutFtpPort(@RequestBody ReqParam<GetToOutMiddleware_FTPParam> param){
        RResult result=this.createNewResultOfFail();
        if(null!=param.getParam()){
            GetToOutMiddleware_FTPParam pParam=param.getParam();
            if(null != pParam.getFdType()){
                result = getToOutService(pParam.getFdType()).getToOutFtpPort(pParam, result);
            }
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    /**
     * 修改数据库ec的asr_etinfo中的backtxtinterface的端口
     * @param param
     * @return
     */
    @RequestMapping("/setToOutBacktxtinterface")
    @ResponseBody
    public RResult setToOutBacktxtinterface(@RequestBody ReqParam<SetToOutBacktxtinterfaceParam> param){
        RResult result=this.createNewResultOfFail();
        if(null!=param.getParam()){
            SetToOutBacktxtinterfaceParam pParam=param.getParam();
            if(null != pParam.getFdType()){
                result = getToOutService(pParam.getFdType()).setToOutBacktxtinterface(pParam, result);
            }
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    //数据库，ec的ss_saveinfo中的port
    //设备集中控制ftp的port也要修改
    @RequestMapping("/setFtpAndSaveinfoPort")
    @ResponseBody
    public RResult setFtpAndSaveinfoPort(@RequestBody ReqParam<SetToOutBacktxtinterfaceParam> param){
        RResult result=this.createNewResultOfFail();
        if(null!=param.getParam()){
            SetToOutBacktxtinterfaceParam pParam=param.getParam();
            if(null != pParam.getFdType()){
                result = getToOutService(pParam.getFdType()).setFtpAndSaveinfoPort(pParam, result);
            }
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

}
