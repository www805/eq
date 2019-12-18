package com.avst.equipmentcontrol.outside.dealoutinterface.tts.avsttts.v1;

import com.avst.equipmentcontrol.common.util.JacksonUtil;
import com.avst.equipmentcontrol.common.util.LogUtil;
import com.avst.equipmentcontrol.common.util.OpenUtil;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.common.util.properties.PropertiesListenerConfig;
import com.avst.equipmentcontrol.outside.dealoutinterface.tts.avsttts.req.Str2ttsReq;
import com.avst.equipmentcontrol.outside.dealoutinterface.tts.avsttts.util.TTSUtil;
import com.avst.equipmentcontrol.outside.dealoutinterface.tts.avsttts.vo.Str2ttsVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * tts的内部处理
 * 联动请求第三方的tts服务
 */
@Service
public class DealAvstTtsImpl {


    public RResult<Str2ttsVO> str2tts(Str2ttsReq param,RResult<Str2ttsVO> result){

        String text=param.getText();
        if(StringUtils.isEmpty(text)){
            LogUtil.intoLog(4,DealAvstTtsImpl.class,"需要转的文字为空，text："+text);
            return result;
        }
        String appkey=param.getApp_key();
        String capkey=param.getCap_key();
        String devkey=param.getDev_key();

        String lang_speaker_domain=param.getLang_speaker_domain();
        if(StringUtils.isEmpty(appkey)||StringUtils.isEmpty(capkey)
                ||StringUtils.isEmpty(devkey)||StringUtils.isEmpty(lang_speaker_domain)){
            LogUtil.intoLog(4,DealAvstTtsImpl.class,"部分密匙为空，keys："+ JacksonUtil.objebtToString(param));
            return result;
        }
        String ip=param.getIp();
        int port=param.getPort();
        if(StringUtils.isEmpty(ip)||0==port){
            LogUtil.intoLog(4,DealAvstTtsImpl.class,"部分密匙为空，keys："+ JacksonUtil.objebtToString(param));
            return result;
        }

        String ttspath=param.getTtsbasepath();
        String ttsstatic=param.getTtsstatic();
        String ttsurl=param.getTtsURL();

        String url="http://"+ip+":"+port+ttsurl;

        //先获取本次转的WAV的路径
        String wavfilename= OpenUtil.getUUID_32()+".wav";
        String httpbasestaticpath = PropertiesListenerConfig.getProperty("httpbasestaticpath");
        String wavfilepath=OpenUtil.createpath_fileByBasepath(ttspath,wavfilename);
        //
        boolean bool=TTSUtil.tts(text,url,appkey,lang_speaker_domain,capkey,devkey,wavfilepath);
        if(bool){//成功之后返回播放地址
            Str2ttsVO vo=new Str2ttsVO();
            String uploadpath=OpenUtil.strMinusBasePath(ttsstatic,wavfilepath);
            uploadpath=httpbasestaticpath+uploadpath;
            LogUtil.intoLog(1,DealAvstTtsImpl.class,text+"：text，返回的播放地址，uploadpath："+uploadpath);
            vo.setUploadpath(uploadpath);
            result.changeToTrue(vo);
        }

        return result;
    }



}
