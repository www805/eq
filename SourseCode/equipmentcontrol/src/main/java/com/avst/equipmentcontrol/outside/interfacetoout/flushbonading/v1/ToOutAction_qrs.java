package com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.v1;

import com.avst.equipmentcontrol.common.conf.FDType;
import com.avst.equipmentcontrol.common.util.baseaction.BaseAction;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.common.util.baseaction.ReqParam;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.req.*;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.v1.service.BaseToOutServiceImpl_qrs;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.v1.service.ToOutService_fd_avst;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.v1.service.ToOutService_qrs;
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

    @Autowired
    private ToOutService_fd_avst toOutService_avst;

    private ToOutService_qrs getToOutServiceImpl(String fdType){
        if(null!=fdType){
            if(fdType.equals(FDType.FD_AVST)){//avst 设备控制
                return toOutService_avst;
            }
        }
        return null;
    }

    //开始工作
    @RequestMapping("/workStart")
    @ResponseBody
    public RResult workStart(@RequestBody ReqParam<WorkStartParam> param){
        RResult rResult=createNewResultOfFail();
        WorkStartParam sparam=param.getParam();
        if(null!=sparam){
            rResult=getToOutServiceImpl(sparam.getFdType()).workStart(sparam,rResult);
        }else{
            System.out.println("workStart---param.getParam() ---参数异常");
            rResult.setMessage("参数异常");
        }
        return rResult;
    }

    //结束工作
    @RequestMapping("/workOver")
    @ResponseBody
    public RResult workOver(@RequestBody ReqParam<WorkOverParam> param){
        RResult rResult=createNewResultOfFail();
        WorkOverParam sparam=param.getParam();
        if(null!=sparam){
            rResult=getToOutServiceImpl(sparam.getFdType()).workOver(sparam,rResult);
        }else{
            System.out.println("workOver---param.getParam() ---参数异常");
            rResult.setMessage("参数异常");
        }
        return rResult;
    }

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


    /**
     * 通过iid在主机设备中找到设备录像的文件列表
     * @param param
     * @return
     */
    @RequestMapping("/getRecordByIid")
    @ResponseBody
    public RResult getRecordByIid(@RequestBody ReqParam<GetRecordByIidParam> param){
        RResult rResult=createNewResultOfFail();

        GetRecordByIidParam sparam=param.getParam();
        if(null!=sparam){
            //通过asrEquipmentssid
            rResult=getToOutServiceImpl(sparam.getFdType()).getRecordByIid(sparam,rResult);
        }else{
            System.out.println("startAsr---param.getParam() ---参数异常");
            rResult.setMessage("参数异常");
        }
        return rResult;
    }


    @RequestMapping(value = "/ceshi" )
    @ResponseBody
    public RResult ceshi(int type,String fdid){



        RResult rResult=createNewResultOfFail();
        if(type==1){
            ReqParam<WorkStartParam> param=new ReqParam<WorkStartParam>();
            WorkStartParam workStartParam=new WorkStartParam();
            workStartParam.setFdid("123456");
            workStartParam.setFdType(FDType.FD_AVST);
            workStartParam.setFlushbonadingetinfossid("sxsba1");
            param.setParam(workStartParam);
            rResult=workStart(param);
        }else if (type==2){
            ReqParam<WorkOverParam> param=new ReqParam<WorkOverParam>();
            WorkOverParam workOverParam=new WorkOverParam();
            workOverParam.setFdid("123456");
            workOverParam.setFdType(FDType.FD_AVST);
            workOverParam.setFlushbonadingetinfossid("sxsba1");
            param.setParam(workOverParam);
            workOver(param);
        }else if(type==3){

        }
        return rResult;
    }

}
