package com.avst.equipmentcontrol.outside.interfacetoout.base.v1;

import com.avst.equipmentcontrol.common.conf.BASEType;
import com.avst.equipmentcontrol.common.util.DateUtil;
import com.avst.equipmentcontrol.common.util.baseaction.BaseAction;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.common.util.baseaction.ReqParam;
import com.avst.equipmentcontrol.outside.interfacetoout.base.req.GetServerIpALLParam;
import com.avst.equipmentcontrol.outside.interfacetoout.base.req.GetServerIpParam;
import com.avst.equipmentcontrol.outside.interfacetoout.base.req.GetToOutBaseEcParam;
import com.avst.equipmentcontrol.outside.interfacetoout.base.req.GetToOutBaseListParam;
import com.avst.equipmentcontrol.outside.interfacetoout.base.v1.service.ToOutMainServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.reflect.generics.tree.BaseType;

@RestController
@RequestMapping("/base/v1/main")
public class ToOutMainAction extends BaseAction {
    @Autowired
    private ToOutMainServiceImpl toOutMainService;

    private ToOutMainServiceImpl getToOutServiceImpl(String bsType){
        if(null!=bsType){
            if(bsType.equals(BASEType.Base)){//基础配置
                return toOutMainService;
            }
        }
        return null;
    }

    @RequestMapping("/gethome")
    @ResponseBody
    public RResult gethome(@RequestBody ReqParam param){
        RResult result=this.createNewResultOfFail();
        toOutMainService.gethome(param,result);
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    };

    /**
     * 获取其他全部设备IP
     * @param param
     * @return
     */
    @RequestMapping("/getServerIpALL")
    @ResponseBody
    public RResult getServerIpALL(@RequestBody ReqParam<GetServerIpALLParam> param){
        RResult result=this.createNewResultOfFail();
        if(null!=param){
            GetServerIpALLParam param1 = param.getParam();
            if(null != param1.getBaseType()){
                getToOutServiceImpl(param1.getBaseType()).getServerIpALL(param,result);
            }
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    };

    /**
     * 修改配置
     * @return
     */
    @PostMapping(value = "/updateServerIp")
    @ResponseBody
    public RResult updateServerIp(@RequestBody GetServerIpParam getServerIpParam) {
        RResult result=this.createNewResultOfFail();
        if(null!=getServerIpParam){
            if(null != getServerIpParam.getBaseType()){
                getToOutServiceImpl(getServerIpParam.getBaseType()).updateServerIp(getServerIpParam,result);
            }
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    /**
     * 提供查询基本类型的接口
     * @param param
     * @return
     */
    @PostMapping(value = "/getToOutBaseList")
    @ResponseBody
    public RResult getToOutBaseList(@RequestBody GetToOutBaseListParam param) {
        RResult result=this.createNewResultOfFail();
        if(null!=param){
            if(null != param.getBaseType()){
                result=getToOutServiceImpl(param.getBaseType()).getToOutBaseList(result);
            }
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }


    /**
     * 获取所有基础设备
     * @param param
     * @return
     */
    @PostMapping(value = "/getToOutBaseEc")
    @ResponseBody
    public RResult getToOutBaseEc(@RequestBody GetToOutBaseEcParam param) {
        RResult result=this.createNewResultOfFail();
        if(null!=param){
            if(null != param.getBaseType()){
                result = getToOutServiceImpl(param.getBaseType()).getToOutBaseEc(param, result);
            }
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

}
