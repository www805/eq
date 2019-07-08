package com.avst.equipmentcontrol.outside.interfacetoout.tts.v1;

import com.avst.equipmentcontrol.common.conf.PHType;
import com.avst.equipmentcontrol.common.conf.TTSType;
import com.avst.equipmentcontrol.common.util.DateUtil;
import com.avst.equipmentcontrol.common.util.JacksonUtil;
import com.avst.equipmentcontrol.common.util.baseaction.BaseAction;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.common.util.baseaction.ReqParam;
import com.avst.equipmentcontrol.outside.interfacetoout.polygraph.req.*;
import com.avst.equipmentcontrol.outside.interfacetoout.polygraph.v1.service.ToOutServiceImpl_ph_avst;
import com.avst.equipmentcontrol.outside.interfacetoout.polygraph.v1.service.ToOutService_ph;
import com.avst.equipmentcontrol.outside.interfacetoout.polygraph.vo.GetPolygraphAnalysisVO;
import com.avst.equipmentcontrol.outside.interfacetoout.tts.req.Str2TtsParam;
import com.avst.equipmentcontrol.outside.interfacetoout.tts.v1.service.ToOutServiceImpl_tts_avst;
import com.avst.equipmentcontrol.outside.interfacetoout.tts.v1.service.ToOutService_tts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/tts/v1")
public class ToOutAction_tts extends BaseAction {

    @Autowired
    private ToOutServiceImpl_tts_avst toOutServiceImpl_tts_avst;

    private ToOutService_tts getToOutService(String ttsType){
        if(ttsType.equals(TTSType.AVST)){
            return toOutServiceImpl_tts_avst;
        }
        return null;
    }

    /**
     * @param param
     * @return
     */
    @RequestMapping("/str2Tts")
    @ResponseBody
    public RResult str2Tts(@RequestBody  ReqParam<Str2TtsParam> param){

        RResult result=this.createNewResultOfFail();

        if(null!=param.getParam()){
            Str2TtsParam pParam=param.getParam();
            result=getToOutService(pParam.getTtsType()).str2Tts(pParam,result);
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    };

    @RequestMapping("/ceshi")
    @ResponseBody
    public void ceshi(int type){

        if(type==1){
            ReqParam<Str2TtsParam> param=new ReqParam<Str2TtsParam>();
            Str2TtsParam str2TtsParam=new Str2TtsParam();
            str2TtsParam.setText("我的家乡在湖北啊");
            str2TtsParam.setTtsType(TTSType.AVST);
            param.setParam(str2TtsParam);
            System.out.println(JacksonUtil.objebtToString(str2Tts(param)));
        }
    }



}
