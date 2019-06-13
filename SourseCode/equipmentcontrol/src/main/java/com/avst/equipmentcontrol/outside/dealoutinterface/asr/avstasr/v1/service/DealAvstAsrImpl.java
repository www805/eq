package com.avst.equipmentcontrol.outside.dealoutinterface.asr.avstasr.v1.service;

import com.avst.equipmentcontrol.common.util.LogUtil;
import com.avst.equipmentcontrol.common.util.XMLUtil;
import com.avst.equipmentcontrol.common.util.baseaction.RRParam;
import com.avst.equipmentcontrol.outside.dealoutinterface.asr.cache.AsrCache;
import com.avst.equipmentcontrol.outside.dealoutinterface.asr.avstasr.req.AVSTAsrParam_heartbeat;
import com.avst.equipmentcontrol.outside.dealoutinterface.asr.avstasr.req.AVSTAsrParam_quit;
import com.avst.equipmentcontrol.outside.dealoutinterface.asr.avstasr.req.AVSTAsrParam_reg;
import com.avst.equipmentcontrol.outside.dealoutinterface.asr.avstasr.vo.AvstSDKInterfaceBackParam_Dealreg;
import com.avst.equipmentcontrol.outside.dealoutinterface.asr.avstasr.vo.AvstSDKInterfaceBackParam_hearbeat;
import com.avst.equipmentcontrol.outside.dealoutinterface.asr.avstasr.vo.AvstSDKInterfaceBackParam_quit;
import com.avst.requestUtil.HttpRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;

/**
 * 处理avst SDK接口请求返回
 */
@Service
public class DealAvstAsrImpl {

    public static RRParam<String> reg(AVSTAsrParam_reg param, RRParam<String> vo){

        try {
            String ip=param.getIp();
            String port=param.getPort();
            String audiourl=param.getAudiourl();
            String txtcallbackurl=param.getTxtcallbackurl();
            String asrverssid=param.getAsrserverssid();
            if(StringUtils.isEmpty(ip)||StringUtils.isEmpty(port)||
                    StringUtils.isEmpty(audiourl)||StringUtils.isEmpty(txtcallbackurl)){
                vo.setMessage("有部分参数为空");
                LogUtil.intoLog(DealAvstAsrImpl.class,param.toString()+"--------reg--DealAvstAsrImpl");
                return vo;
            }

            String url="http://"+ip+":"+port+"/audiodiscern/impl" ;
            String regparam=       "method=reg&audiourl="+ Base64.encodeBase64String(audiourl.getBytes("utf8")) +
                    "&txtcallbackurl="+Base64.encodeBase64String(txtcallbackurl.getBytes("utf8"));
            String rr= HttpRequest.readContentFromGet_noencode(url,regparam);

            AvstSDKInterfaceBackParam_Dealreg avstRegSDKInterfaceBackParam=new AvstSDKInterfaceBackParam_Dealreg();
            avstRegSDKInterfaceBackParam=(AvstSDKInterfaceBackParam_Dealreg) XMLUtil.xmlToStr(avstRegSDKInterfaceBackParam,rr);

            if(null!=avstRegSDKInterfaceBackParam){//说明请求有正确的返回
                String code=avstRegSDKInterfaceBackParam.getCode();
                if(StringUtils.isEmpty(code)||!code.equals("1")){
                    LogUtil.intoLog(DealAvstAsrImpl.class,code+":code 请求返回异常 reg  avstRegSDKInterfaceBackParam.getMsg():"+avstRegSDKInterfaceBackParam.getMsg());
                    vo.setMessage("语音服务器请求返回异常");
                }else{
                    String id=avstRegSDKInterfaceBackParam.getId();
                    if(StringUtils.isEmpty(id)){
                        LogUtil.intoLog(DealAvstAsrImpl.class,code+":code 请求返回异常 reg  id:"+id);
                        vo.setMessage("语音服务器请求返回异常");
                    }else{
                        vo.setT(id);
                        vo.setCode(1);

                        //存到语音唯一码对应服务ssid中去
                        AsrCache.setAsrServerssidByAsrid(id,asrverssid);
                        LogUtil.intoLog(DealAvstAsrImpl.class,id+":asrid 开启识别成功存到语音唯一码对应服务ssid中  asrverssid:"+asrverssid);
                    }
                }
            }else{
                LogUtil.intoLog(DealAvstAsrImpl.class,avstRegSDKInterfaceBackParam+":avstRegSDKInterfaceBackParam 请求返回为空 reg");
                vo.setMessage("语音服务器请求异常");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return vo;
    };

    public static RRParam<Boolean> heartbeat(AVSTAsrParam_heartbeat param, RRParam<Boolean> vo){

        String ip=param.getIp();
        String port=param.getPort();
        String id=param.getId();
        if(StringUtils.isEmpty(ip)||StringUtils.isEmpty(port)||StringUtils.isEmpty(id)){
            vo.setMessage("有部分参数为空");
            LogUtil.intoLog(DealAvstAsrImpl.class,param.toString()+"---------heartbeat--DealAvstAsrImpl");
            return vo;
        }

        String url="http://"+ip+":"+port+"/audiodiscern/impl";
        String heartbeatparam="method=heartbeat&id="+id;
        String rr= HttpRequest.readContentFromGet_noencode(url,heartbeatparam);

        AvstSDKInterfaceBackParam_hearbeat hearbeat=new AvstSDKInterfaceBackParam_hearbeat();
        hearbeat=(AvstSDKInterfaceBackParam_hearbeat) XMLUtil.xmlToStr(hearbeat,rr);

        if(null!=hearbeat){//说明请求有正确的返回
            String code=hearbeat.getCode();
            if(StringUtils.isEmpty(code)||!code.equals("1")){
                LogUtil.intoLog(DealAvstAsrImpl.class,code+":code 请求返回异常 heartbeat  hearbeat.getMsg():"+hearbeat.getMsg());
                vo.setMessage("语音服务器请求返回异常");
            }else{
                vo.setT(true);
                vo.setCode(1);
            }
        }else{
            LogUtil.intoLog(DealAvstAsrImpl.class,hearbeat+":hearbeat 请求返回为空 hearbeat");
            vo.setMessage("语音服务器请求异常");
        }

        return vo;
    };


    public static RRParam<Boolean> quit(AVSTAsrParam_quit param, RRParam<Boolean> vo){

        String ip=param.getIp();
        String port=param.getPort();
        String id=param.getId();
        if(StringUtils.isEmpty(ip)||StringUtils.isEmpty(port)||StringUtils.isEmpty(id)){
            vo.setMessage("有部分参数为空");
            LogUtil.intoLog(DealAvstAsrImpl.class,param.toString()+"--------quit--DealAvstAsrImpl");
            return vo;
        }

        String url="http://"+ip+":"+port+"/audiodiscern/impl" ;
        String quitparam= "method=quit&id="+id;
        String rr= HttpRequest.readContentFromGet_noencode(url,quitparam);

        AvstSDKInterfaceBackParam_quit quit=new AvstSDKInterfaceBackParam_quit();
        quit=(AvstSDKInterfaceBackParam_quit) XMLUtil.xmlToStr(quit,rr);

        if(null!=quit){//说明请求有正确的返回
            String code=quit.getCode();
            if(StringUtils.isEmpty(code)||!code.equals("1")){
                LogUtil.intoLog(DealAvstAsrImpl.class,code+":code 请求返回异常 quit  quit.getMsg():"+quit.getMsg());
                vo.setMessage("语音服务器请求返回异常");
            }else{
                vo.setT(true);
                vo.setCode(1);
            }
        }else{
            LogUtil.intoLog(DealAvstAsrImpl.class,quit+":quit 请求返回为空 quit");
            vo.setMessage("语音服务器请求异常");
        }
        return vo;
    };

}
