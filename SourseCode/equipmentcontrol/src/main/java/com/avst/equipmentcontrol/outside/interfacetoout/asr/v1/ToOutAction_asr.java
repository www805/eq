package com.avst.equipmentcontrol.outside.interfacetoout.asr.v1;

import com.avst.equipmentcontrol.common.util.baseaction.BaseAction;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.common.util.baseaction.ReqParam;
import com.avst.equipmentcontrol.outside.interfacetoout.asr.cache.AsrCache_toout;
import com.avst.equipmentcontrol.outside.interfacetoout.asr.cache.param.AsrTxtParam_toout;
import com.avst.equipmentcontrol.common.conf.ASRType;
import com.avst.equipmentcontrol.outside.interfacetoout.asr.req.GetAsrServerBySsidParam;
import com.avst.equipmentcontrol.outside.interfacetoout.asr.req.OverAsrParam;
import com.avst.equipmentcontrol.outside.interfacetoout.asr.req.StartAsrParam;
import com.avst.equipmentcontrol.outside.interfacetoout.asr.v1.service.BaseToOutServiceImpl_asr;
import com.avst.equipmentcontrol.outside.interfacetoout.asr.v1.service.ToOutService_asr;
import com.avst.equipmentcontrol.outside.interfacetoout.asr.v1.service.ToOutServiceImpl_asr_avst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/asr/v1")
public class ToOutAction_asr extends BaseAction {

    @Autowired
    private ToOutService_asr toOutService_asr;
    @Autowired
    private ToOutServiceImpl_asr_avst toOutServiceImpl_avst;

    @Autowired
    private BaseToOutServiceImpl_asr baseToOutService;

    /**
     * 语音服务类型
     * @param asrType
     * @return
     */
    private ToOutService_asr getToOutService(String  asrType){
        if(asrType.equals(ASRType.AVST)){//不同的语音服务有自己的处理
            return toOutServiceImpl_avst;
        }
        return null;
    }

    /**
     * 提供给其他的微服务用于读取本次语音识别的文本返回
     * @param asrid 语音识别记录id
     * @return
     */
    @RequestMapping("/toClientForTxtBack")
    @ResponseBody
    public RResult<AsrTxtParam_toout> txtBack(@RequestBody String asrid){

        RResult<AsrTxtParam_toout> vo=new RResult<AsrTxtParam_toout>();
        //暂时都没有做权限的限制
        AsrTxtParam_toout asr= AsrCache_toout.getAsrTxtLastOne(asrid);
        if(null!=asr){
            vo.changeToTrue(asr);
        }
        return vo;
    }




    @RequestMapping("/startAsr")
    @ResponseBody
    public RResult startAsr(@RequestBody ReqParam<StartAsrParam> param){
        RResult rResult=createNewResultOfFail();

        StartAsrParam sparam=param.getParam();
        if(null!=sparam){
            String asrtype=sparam.getAsrtype();//这里测试使用，后期要去查询
            rResult=getToOutService(asrtype).startAsr(sparam,rResult);

        }else{
            System.out.println("startAsr---param.getParam() ---参数异常");
            rResult.setMessage("参数异常");
        }
        return rResult;
    }

    @RequestMapping("/overAsr")
    @ResponseBody
    public RResult overAsr(@RequestBody ReqParam<OverAsrParam> param){
        RResult rResult=createNewResultOfFail();

        OverAsrParam oparam=param.getParam();
        if(null!=oparam){
            String asrEquipmentssid=oparam.getAsrEquipmentssid();//通过asrEquipmentssid找到语音识别的类型
            String asrtype=oparam.getAsrtype();//这里测试使用，后期要去查询
            rResult=getToOutService(asrtype).overAsr(oparam,rResult);
        }else{
            System.out.println("overAsr---param.getParam() ---参数异常");
            rResult.setMessage("参数异常");
        }

        return rResult;
    }

    //一套语音服务器的增删改查接口

    /**
     * 通过ssid查找语音服务器信息
     * @param param
     * @return
     */
    @RequestMapping(value = "/getAsrServerBySsid" )
    @ResponseBody
    public RResult getAsrServerBySsid(@RequestBody ReqParam<GetAsrServerBySsidParam> param){
        RResult rResult=createNewResultOfFail();
System.out.println("getAsrServerBySsid is coming");
        GetAsrServerBySsidParam sparam=param.getParam();
        if(null!=sparam){
            //通过asrEquipmentssid
            rResult=baseToOutService.getAsrServerBySsid(sparam,rResult);
        }else{
            System.out.println("getAsrServerBySsid---param.getParam() ---参数异常");
            rResult.setMessage("参数异常");
        }
        return rResult;
    }


    @RequestMapping(value = "/ceshi" )
    @ResponseBody
    public RResult ceshi(int type,String asrid){


        RResult rResult=createNewResultOfFail();
        if(type==1){
            ReqParam<StartAsrParam> param=new ReqParam<StartAsrParam>();
            StartAsrParam startAsrParam =new StartAsrParam();
            startAsrParam.setTdssid("sxsbtd123gty6");
            startAsrParam.setAsrEquipmentssid("wsefscv12");
            startAsrParam.setAsrtype("AVST");
            param.setParam(startAsrParam);
            rResult=startAsr(param);
            rResult.getData().toString();
        }else if(type==2){
            ReqParam<OverAsrParam> param=new ReqParam<OverAsrParam>();
            OverAsrParam overAsrParam=new OverAsrParam();
            overAsrParam.setAsrid(asrid);
            overAsrParam.setAsrEquipmentssid("wsefscv12");
            overAsrParam.setAsrtype("AVST");
            param.setParam(overAsrParam);
            rResult=overAsr(param);
        }else if(type==3){
            rResult=txtBack(asrid);
        }

        return rResult;
    }

}
