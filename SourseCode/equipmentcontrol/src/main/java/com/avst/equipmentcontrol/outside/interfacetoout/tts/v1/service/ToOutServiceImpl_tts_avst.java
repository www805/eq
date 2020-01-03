package com.avst.equipmentcontrol.outside.interfacetoout.tts.v1.service;

import com.avst.equipmentcontrol.common.datasourse.extrasourse.tts.entity.Tts_etinfo;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.tts.entity.param.TTS_et_ettype;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.tts.mapper.Tts_etinfoMapper;
import com.avst.equipmentcontrol.common.util.JacksonUtil;
import com.avst.equipmentcontrol.common.util.LogUtil;
import com.avst.equipmentcontrol.common.util.baseaction.Code;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.outside.dealoutinterface.polygraph.cmcross.v1.req.BaseParam;
import com.avst.equipmentcontrol.outside.dealoutinterface.tts.avsttts.req.Str2ttsReq;
import com.avst.equipmentcontrol.outside.dealoutinterface.tts.avsttts.v1.DealAvstTtsImpl;
import com.avst.equipmentcontrol.outside.dealoutinterface.tts.avsttts.vo.Str2ttsVO;
import com.avst.equipmentcontrol.outside.interfacetoout.polygraph.req.*;
import com.avst.equipmentcontrol.outside.interfacetoout.tts.req.Str2TtsParam;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToOutServiceImpl_tts_avst implements ToOutService_tts {

    @Autowired
    private Tts_etinfoMapper tts_etinfoMapper;

    @Autowired
    private DealAvstTtsImpl dealAvstTts;

    @Override
    public RResult str2Tts(Str2TtsParam param, RResult result) {

        String text=param.getText();
        if(StringUtils.isEmpty(text)){
            LogUtil.intoLog(this.getClass()," text is null---"+text);
            result.setMessage("需要转语音的文本为空");
            return result;
        }
        int textlength=text.length();
        if(textlength > 1024){//（utf8）tts最大只能识别4096个字节，绝大多数汉字都是3个字节，很少部分是4个字节，这里杜绝一切可能，最大只能有1024个字
            LogUtil.intoLog(this.getClass()," text is too long---text.length()："+textlength);
            result.setMessage("需要转语音的文本太长了，请按照标点截取一下，重新多次提交");
            return result;
        }


        List<TTS_et_ettype> ttslist=tts_etinfoMapper.getttsinfoList();
        if(null==ttslist||ttslist.size()==0){
            LogUtil.intoLog(this.getClass(),"tts没有没有找到一个可以用的");
            result.setMessage("身心监护没有找到");
            return result;
        }
        TTS_et_ettype tts_etinfo=ttslist.get(0);//只需要获取任何一个就可以了

        String keys=tts_etinfo.getTtskeys();
        if(StringUtils.isEmpty(keys)){
            LogUtil.intoLog(this.getClass(),"tts的密匙为空，keys："+keys);
            result.setMessage("tts的密匙为空");
            return result;
        }
        String[] key=keys.split(",");
        if(null==key||key.length==0){
            LogUtil.intoLog(this.getClass(),"tts的密匙数量有问题，keys："+keys);
            result.setMessage("tts的密匙数量有问题");
            return result;
        }
        String appkey="ac5d5452";
        String Cap_key="tts.cloud.synth";
        String Dev_key="developer_key";
        String Lang_speaker_domain="cn_xumengjuan_common";
        for(String k:key){
            if(StringUtils.isNotEmpty(k)&&k.trim().indexOf("app_key=") > -1){
                appkey=k.trim().split("=")[1];
            }
            if(StringUtils.isNotEmpty(k)&&k.trim().indexOf("cap_key=") > -1){
                Cap_key=k.trim().split("=")[1];
            }
            if(StringUtils.isNotEmpty(k)&&k.trim().indexOf("dev_key=") > -1){
                Dev_key=k.trim().split("=")[1];
            }
            if(StringUtils.isNotEmpty(k)&&k.trim().indexOf("lang_speaker_domain=") > -1){
                Lang_speaker_domain=k.trim().split("=")[1];
            }
        }

        Str2ttsReq ttsReq=new Str2ttsReq();
        ttsReq.setApp_key(appkey);
        ttsReq.setCap_key(Cap_key);
        ttsReq.setDev_key(Dev_key);
        ttsReq.setLang_speaker_domain(Lang_speaker_domain);
        ttsReq.setIp(tts_etinfo.getEtip());
        ttsReq.setPort(tts_etinfo.getPort());
        ttsReq.setTtsbasepath(tts_etinfo.getTtsbasepath());
        ttsReq.setTtsstatic(tts_etinfo.getTtsstatic());
        ttsReq.setTtsURL(tts_etinfo.getTtsurl());
        ttsReq.setText(text);
        RResult<Str2ttsVO> rResult = dealAvstTts.str2tts(ttsReq,new RResult());
        if(null!=rResult&&rResult.getActioncode().equals(Code.SUCCESS.toString())){
            Str2ttsVO vo=rResult.getData();
            result.changeToTrue(vo.getUploadpath());
        }else{
            LogUtil.intoLog(4,this.getClass(),"请求第三方tts识别失败，rResult："+ JacksonUtil.objebtToString(rResult));
            result.setMessage("");
        }
        return result;
    }


}
