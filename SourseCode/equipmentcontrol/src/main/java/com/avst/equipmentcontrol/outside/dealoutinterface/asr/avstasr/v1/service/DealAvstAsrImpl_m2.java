package com.avst.equipmentcontrol.outside.dealoutinterface.asr.avstasr.v1.service;

import com.avst.equipmentcontrol.common.util.LogUtil;
import com.avst.equipmentcontrol.common.util.XMLUtil;
import com.avst.equipmentcontrol.common.util.baseaction.RRParam;
import com.avst.equipmentcontrol.outside.dealoutinterface.asr.avstasr.req.AVSTAsrParam_heartbeat;
import com.avst.equipmentcontrol.outside.dealoutinterface.asr.avstasr.req.AVSTAsrParam_quit;
import com.avst.equipmentcontrol.outside.dealoutinterface.asr.avstasr.req.AVSTAsrParam_reg;
import com.avst.equipmentcontrol.outside.dealoutinterface.asr.avstasr.req.AVSTAsrParam_settaskinfo;
import com.avst.equipmentcontrol.outside.dealoutinterface.asr.avstasr.req.param.TaskParam;
import com.avst.equipmentcontrol.outside.dealoutinterface.asr.avstasr.vo.AvstSDKInterfaceBackParam_Dealreg;
import com.avst.equipmentcontrol.outside.dealoutinterface.asr.avstasr.vo.AvstSDKInterfaceBackParam_hearbeat;
import com.avst.equipmentcontrol.outside.dealoutinterface.asr.avstasr.vo.AvstSDKInterfaceBackParam_quit;
import com.avst.equipmentcontrol.outside.dealoutinterface.asr.avstasr.vo.AvstSDKInterfaceBackParam_settaskinfo;
import com.avst.equipmentcontrol.outside.dealoutinterface.asr.cache.AsrCache;
import com.avst.requestUtil.HttpRequest;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * 处理avst SDK接口请求返回
 * 新版的识别模式m2
 */
@Service
public class DealAvstAsrImpl_m2 {

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
                LogUtil.intoLog(DealAvstAsrImpl_m2.class,param.toString()+"--------reg--DealAvstAsrImpl_m2");
                return vo;
            }

            String url="http://"+ip+":"+port+"/audiodiscern/activate" ;
            String regparam="method=reg&audiourl="+ Base64.encodeBase64String(audiourl.getBytes("utf8")) +
                    "&txtcallbackurl="+Base64.encodeBase64String(txtcallbackurl.getBytes("utf8"));
            LogUtil.intoLog(1, DealAvstAsrImpl_m2.class,url+":url----audiourl:"+audiourl+"----txtcallbackurl:"+txtcallbackurl);
            String rr= HttpRequest.readContentFromGet_noencode(url,regparam);
            LogUtil.intoLog(1, DealAvstAsrImpl_m2.class,url+":url---regparam:"+regparam);
            AvstSDKInterfaceBackParam_Dealreg avstRegSDKInterfaceBackParam=new AvstSDKInterfaceBackParam_Dealreg();
            avstRegSDKInterfaceBackParam=(AvstSDKInterfaceBackParam_Dealreg) XMLUtil.xmlToStr(avstRegSDKInterfaceBackParam,rr);

            if(null!=avstRegSDKInterfaceBackParam){//说明请求有正确的返回
                String code=avstRegSDKInterfaceBackParam.getCode();
                if(StringUtils.isEmpty(code)||!code.equals("1")){
                    LogUtil.intoLog(DealAvstAsrImpl_m2.class,code+":code 请求返回异常 reg  avstRegSDKInterfaceBackParam.getMsg():"+avstRegSDKInterfaceBackParam.getMsg());
                    vo.setMessage("语音服务器请求返回异常");
                }else{
                    String id=avstRegSDKInterfaceBackParam.getId();
                    if(StringUtils.isEmpty(id)){
                        LogUtil.intoLog(DealAvstAsrImpl_m2.class,code+":code 请求返回异常 reg  id:"+id);
                        vo.setMessage("语音服务器请求返回异常");
                    }else{
                        vo.setT(id);
                        vo.setCode(1);

                        //存到语音唯一码对应服务ssid中去
                        AsrCache.setAsrServerssidByAsrid(id,asrverssid);
                        LogUtil.intoLog(DealAvstAsrImpl_m2.class,id+":asrid 开启识别成功存到语音唯一码对应服务ssid中  asrverssid:"+asrverssid);

                    }
                }
            }else{
                LogUtil.intoLog(DealAvstAsrImpl_m2.class,avstRegSDKInterfaceBackParam+":avstRegSDKInterfaceBackParam 请求返回为空 reg");
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
            LogUtil.intoLog(DealAvstAsrImpl_m2.class,param.toString()+"---------heartbeat--DealAvstAsrImpl_m2");
            return vo;
        }

        String url="http://"+ip+":"+port+"/audiodiscern/activate";
        String heartbeatparam="method=heartbeat&id="+id;
        String rr= HttpRequest.readContentFromGet_noencode(url,heartbeatparam);

        AvstSDKInterfaceBackParam_hearbeat hearbeat=new AvstSDKInterfaceBackParam_hearbeat();
        hearbeat=(AvstSDKInterfaceBackParam_hearbeat) XMLUtil.xmlToStr(hearbeat,rr);

        if(null!=hearbeat){//说明请求有正确的返回
            String code=hearbeat.getCode();
            if(StringUtils.isEmpty(code)||!code.equals("1")){
                LogUtil.intoLog(DealAvstAsrImpl_m2.class,code+":code 请求返回异常 heartbeat  hearbeat.getMsg():"+hearbeat.getMsg());
                vo.setMessage("语音服务器请求返回异常");
            }else{
                vo.setT(true);
                vo.setCode(1);
            }
        }else{
            LogUtil.intoLog(DealAvstAsrImpl_m2.class,hearbeat+":hearbeat 请求返回为空 hearbeat");
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
            LogUtil.intoLog(DealAvstAsrImpl_m2.class,param.toString()+"--------quit--DealAvstAsrImpl_m2");
            return vo;
        }

        boolean bool_id=false;//确认是关闭的哪一种类型，带_的可以忽略返回
        if(id.indexOf("_") > -1){
            String[] arr=id.split("_");
            id=arr[0];
            bool_id=true;
        }

        String url="http://"+ip+":"+port+"/audiodiscern/activate" ;
        String quitparam= "method=quit&id="+id;
        String rr= HttpRequest.readContentFromGet_noencode(url,quitparam);

        AvstSDKInterfaceBackParam_quit quit=new AvstSDKInterfaceBackParam_quit();
        quit=(AvstSDKInterfaceBackParam_quit) XMLUtil.xmlToStr(quit,rr);

        if(null!=quit){//说明请求有正确的返回
            if(bool_id){
                vo.setT(true);
                vo.setCode(1);
            }else{
                String code=quit.getCode();
                if(StringUtils.isEmpty(code)||!code.equals("1")){
                    LogUtil.intoLog(4, DealAvstAsrImpl_m2.class,code+":code 请求返回异常 quit  quit.getMsg():"+quit.getMsg());
                    vo.setMessage("语音服务器请求返回异常");
                }else{
                    vo.setT(true);
                    vo.setCode(1);
                }
            }
        }else{
            LogUtil.intoLog(4, DealAvstAsrImpl_m2.class,quit+":quit 请求返回为空 quit");
            vo.setMessage("语音服务器请求异常");
        }
        return vo;
    };

    /**
     * 设置音频流音频能量激活语音阀值
     * @param param
     * @param vo
     * @return
     */
    public static RRParam<Boolean> settaskinfo(AVSTAsrParam_settaskinfo param, RRParam<Boolean> vo){

        String ip=param.getIp();
        String port=param.getPort();
        String id=param.getAsrid();
        List<TaskParam> taskParamList=new ArrayList<TaskParam>();
        if(StringUtils.isEmpty(ip)||StringUtils.isEmpty(port)||StringUtils.isEmpty(id)
                ||null==taskParamList||taskParamList.size()==0){
            vo.setMessage("有部分参数为空");
            LogUtil.intoLog(4,DealAvstAsrImpl_m2.class,param.toString()+"--------settaskinfo--DealAvstAsrImpl_m2");
            return vo;
        }

         String xml="<root>";
        for(TaskParam task:taskParamList){
            xml+="<task><index>"+task.getIndex()+"</index><shockenergy>"+task.getShockenergy()+"</shockenergy></task>";
        }
        xml+="<root>";

        try {
            String url="http://"+ip+":"+port+"/audiodiscern/activate" ;

            String settaskparam= "method=settaskinfo&id="+id+"&xml"+Base64.encodeBase64String(xml.getBytes("utf8"));
            LogUtil.intoLog(1, DealAvstAsrImpl_m2.class,url+":url ---xml:"+xml+":url ---id:"+id);
            String rr= HttpRequest.readContentFromGet_noencode(url,settaskparam);
            LogUtil.intoLog(1, DealAvstAsrImpl_m2.class,rr+":rr ---regparam:"+settaskparam);
            AvstSDKInterfaceBackParam_settaskinfo settask=new AvstSDKInterfaceBackParam_settaskinfo();
            settask=(AvstSDKInterfaceBackParam_settaskinfo) XMLUtil.xmlToStr(settask,rr);

            if(null!=settask){//说明请求有正确的返回

                String code=settask.getCode();
                if(StringUtils.isEmpty(code)||!code.equals("1")){
                    LogUtil.intoLog(4, DealAvstAsrImpl_m2.class,code+":code 请求返回异常 settaskinfo  settaskinfo.getMsg():"+settask.getMsg());
                    vo.setMessage("语音服务器请求返回异常");
                }else{
                    vo.setT(true);
                    vo.setCode(1);
                }

            }else{
                LogUtil.intoLog(4, DealAvstAsrImpl_m2.class,settask+":settask 请求返回为空 settask");
                vo.setMessage("语音服务器请求异常");
            }
        } catch (Exception e) {
            e.printStackTrace();
            vo.setMessage("语音服务器请求异常");
        }
        return vo;
    };

}
