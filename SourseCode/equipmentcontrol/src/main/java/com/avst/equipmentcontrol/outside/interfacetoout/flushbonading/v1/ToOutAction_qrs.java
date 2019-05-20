package com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.v1;

import com.avst.equipmentcontrol.common.util.baseaction.BaseAction;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.common.util.baseaction.ReqParam;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.req.GetFlushbonadingBySsidParam;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.req.GetFlushbonadingTDByETSsidParam;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.v1.service.BaseToOutServiceImpl_qrs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/flushbonading/v1")
public class ToOutAction_qrs extends BaseAction {

    @Autowired
    private BaseToOutServiceImpl_qrs baseToOutServiceImpl_qrs;

    //开始工作

    //结束工作

    //设备状态查询

    //加上一套设备的增删改查（有一套base？）(后期所有的增删改查都可以合成一套用泛型接受传参，多带一个类型参数就可以了)

    /**
     * 通过审讯主机设备ssid查找审讯主机信息
     * @param param
     * @return
     */
    @RequestMapping("/getFlushbonadingBySsid")
    @ResponseBody
    public RResult getFlushbonadingBySsid(@RequestBody ReqParam<GetFlushbonadingBySsidParam> param){
        RResult rResult=createNewResultOfFail();

        GetFlushbonadingBySsidParam sparam=param.getParam();
        if(null!=sparam){
            rResult=baseToOutServiceImpl_qrs.getFlushbonadingBySsid(sparam,rResult);
        }else{
            System.out.println("startAsr---param.getParam() ---参数异常");
            rResult.setMessage("参数异常");
        }
        return rResult;
    }

    /**
     * 通过审讯主机设备ssid查找通道信息
     * @param param
     * @return
     */
    @RequestMapping("/getFlushbonadingTDByETSsid")
    @ResponseBody
    public RResult getFlushbonadingTDByETSsid(@RequestBody ReqParam<GetFlushbonadingTDByETSsidParam> param){
        RResult rResult=createNewResultOfFail();

        GetFlushbonadingTDByETSsidParam sparam=param.getParam();
        if(null!=sparam){
            //通过asrEquipmentssid
            rResult=baseToOutServiceImpl_qrs.getFlushbonadingTDByETSsid(sparam,rResult);
        }else{
            System.out.println("startAsr---param.getParam() ---参数异常");
            rResult.setMessage("参数异常");
        }
        return rResult;
    }


}
