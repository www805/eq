package com.avst.equipmentcontrol.outside.interfacetoout.asr.v1;

import com.avst.equipmentcontrol.common.util.LogUtil;
import com.avst.equipmentcontrol.common.util.baseaction.BaseAction;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.common.util.baseaction.ReqParam;
import com.avst.equipmentcontrol.outside.interfacetoout.asr.cache.AsrCache_toout;
import com.avst.equipmentcontrol.outside.interfacetoout.asr.cache.param.AsrTxtParam_toout;
import com.avst.equipmentcontrol.common.conf.ASRType;
import com.avst.equipmentcontrol.outside.interfacetoout.asr.req.GetAsrServerBySsidParam;
import com.avst.equipmentcontrol.outside.interfacetoout.asr.req.OverAsrParam;
import com.avst.equipmentcontrol.outside.interfacetoout.asr.req.StartAsrParam;
import com.avst.equipmentcontrol.outside.interfacetoout.asr.req.TxtBackParam;
import com.avst.equipmentcontrol.outside.interfacetoout.asr.v1.service.BaseToOutServiceImpl_asr;
import com.avst.equipmentcontrol.outside.interfacetoout.asr.v1.service.ToOutService_asr;
import com.avst.equipmentcontrol.outside.interfacetoout.asr.v1.service.ToOutServiceImpl_asr_avst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

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
     * @param txtBackParam 语音识别记录id以及该ID对应的最后一次返回的sort
     *                     (暂时弃用)
     * @return
     */
    @RequestMapping("/toClientForTxtBack")
    @ResponseBody
    public RResult<List<AsrTxtParam_toout>> txtBack(@RequestBody TxtBackParam txtBackParam){

        RResult<List<AsrTxtParam_toout>> vo=new RResult<List<AsrTxtParam_toout>>();
        //暂时都没有做权限的限制
        List<AsrTxtParam_toout> asrlist= AsrCache_toout.getAsrTxtLastList(txtBackParam.getAsrid(),txtBackParam.getAsrsort());

        if(null!=asrlist&&asrlist.size() > 0){
            vo.changeToTrue(asrlist);
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
            LogUtil.intoLog(this.getClass(),"startAsr---param.getParam() ---参数异常");
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
            LogUtil.intoLog(this.getClass(),"overAsr---param.getParam() ---参数异常");
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
LogUtil.intoLog(this.getClass(),"getAsrServerBySsid is coming");
        GetAsrServerBySsidParam sparam=param.getParam();
        if(null!=sparam){
            //通过asrEquipmentssid
            rResult=baseToOutService.getAsrServerBySsid(sparam,rResult);
        }else{
            LogUtil.intoLog(this.getClass(),"getAsrServerBySsid---param.getParam() ---参数异常");
            rResult.setMessage("参数异常");
        }
        return rResult;
    }


    @RequestMapping(value = "/ceshi" )
    @ResponseBody
    public RResult ceshi(int type,String asrid,Integer asrsort){


        RResult rResult=createNewResultOfFail();
        if(type==1){
            ReqParam<StartAsrParam> param=new ReqParam<StartAsrParam>();
            StartAsrParam startAsrParam =new StartAsrParam();
            startAsrParam.setTdssid("sxsbtd123gty6");
            startAsrParam.setAsrEquipmentssid("wsefscv12");
            startAsrParam.setAsrtype(ASRType.AVST);
            param.setParam(startAsrParam);
            rResult=startAsr(param);
            rResult.getData().toString();
        }else if(type==2){
            ReqParam<OverAsrParam> param=new ReqParam<OverAsrParam>();
            OverAsrParam overAsrParam=new OverAsrParam();
            overAsrParam.setAsrid(asrid);
            overAsrParam.setAsrEquipmentssid("wsefscv12");
            overAsrParam.setAsrtype(ASRType.AVST);
            param.setParam(overAsrParam);
            rResult=overAsr(param);
        }else if(type==3){
            TxtBackParam txtBackParam=new TxtBackParam();
            txtBackParam.setAsrid(asrid);
            txtBackParam.setAsrsort(asrsort);
            rResult=txtBack(txtBackParam);
        }

        return rResult;
    }

}
