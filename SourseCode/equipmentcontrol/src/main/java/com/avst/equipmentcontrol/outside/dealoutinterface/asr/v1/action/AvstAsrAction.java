package com.avst.equipmentcontrol.outside.dealoutinterface.asr.v1.action;

import com.avst.equipmentcontrol.common.conf.MCType;
import com.avst.equipmentcontrol.common.util.*;
import com.avst.equipmentcontrol.common.util.baseaction.ReqParam;
import com.avst.equipmentcontrol.feignclient.MeetingControl;
import com.avst.equipmentcontrol.feignclient.req.SetMCAsrTxtBackParam_out;
import com.avst.equipmentcontrol.outside.dealoutinterface.asr.cache.AsrCache;
import com.avst.equipmentcontrol.outside.dealoutinterface.asr.cache.param.AsrTxtParam_avst;
import com.avst.equipmentcontrol.outside.interfacetoout.asr.cache.AsrCache_toout;
import com.avst.equipmentcontrol.outside.interfacetoout.asr.cache.param.AsrTxtParam_toout;
import com.avst.equipmentcontrol.outside.interfacetoout.asr.v1.service.BaseToOutServiceImpl_asr;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URLDecoder;
import java.util.Collections;

/**
 * 跟第三方语音服务器通讯
 */
@Controller
@RequestMapping("/toasr/v1")
public class AvstAsrAction {

    @Autowired
    private MeetingControl meetingControl;

    /**
     * 第三方语音服务器需要返回识别的TXT文本，就是调用这个接口
     * @param data
     * @return
     */
    @RequestMapping("/toAsrServerForTxtBack")
    @ResponseBody
    public boolean txtBack(@RequestBody byte[] data){

        try {
            if(null==data||data.length==0){
                return false;
            }

            String rr = new String(data,"utf8");
            rr= URLDecoder.decode(rr,"utf-8");
            System.out.println(rr+":rr--"+ DateUtil.getDateAndMinute());

            AsrTxtParam_avst asrTxtParam_avst=new AsrTxtParam_avst();
            asrTxtParam_avst=(AsrTxtParam_avst)XMLUtil.xmlToStr(asrTxtParam_avst,rr);
            System.out.println(asrTxtParam_avst.getId()+"-------------asrTxtParam_avst.getId()");

            if("1".equals(asrTxtParam_avst.getTime())){//过滤掉本句结束
                System.out.println(asrTxtParam_avst.getId()+"--"+asrTxtParam_avst.getTime()+"这一句已经结束 ");
                return true;
            }

            if(StringUtils.isEmpty(asrTxtParam_avst.getMsg())){//过滤掉本句结束
                System.out.println(asrTxtParam_avst.getId()+"--"+asrTxtParam_avst.getTime()+"这一句返回返回为空 ");
                return true;
            }
            String newStr= JacksonUtil.objebtToString(asrTxtParam_avst);
            String path="I:\\wubin\\笔录管理系统\\ceshi3.txt";
            ReadWriteFile.writeApptoTxtFile(newStr+"\r\n",path);
            //查询数据库，把avst语音服务的ssid找出来，这里是avst asr语音服务的处理（以后写入缓存）
            String asrid=asrTxtParam_avst.getId();//语音识别唯一通用识别码，用于关联本次识别服务的双方
            String Equipmentssid=AsrCache.getAsrServerssidByAsrid(asrid);

            if(null==Equipmentssid){//如果为null就需要从数据库中获取,这种一般情况下不会
                Equipmentssid="errorEquipmentssid";//万一
            }


            //往缓存中插数据
            AsrCache.setAsrByASRssid(Equipmentssid,asrid,asrTxtParam_avst);
            System.out.println(asrTxtParam_avst.getTime()+"--现在--"+asrTxtParam_avst.getMsg());

            //把对外缓存的最后一句返回给上一级系统
            AsrTxtParam_toout asrTxtParam_toout=AsrCache_toout.getAsrTxtLastOne(asrid);//如果为空的话就是有问题的
            if(null==asrTxtParam_toout){
                System.out.println(asrTxtParam_toout+"--如果为空的话就是有问题的--- ");
            }else{
                //这里直接返回给了avstmc,以后加判断
                ReqParam<SetMCAsrTxtBackParam_out> param=new ReqParam<SetMCAsrTxtBackParam_out>();
                SetMCAsrTxtBackParam_out setMCAsrTxtBackParam_out=new SetMCAsrTxtBackParam_out();
                setMCAsrTxtBackParam_out.setAsrid(asrid);
                setMCAsrTxtBackParam_out.setAsrTxtParam_toout(asrTxtParam_toout);
                setMCAsrTxtBackParam_out.setMcType(MCType.AVST);//制定avstmc接受返回
                param.setParam(setMCAsrTxtBackParam_out);
                boolean bool=meetingControl.setMCAsrTxtBack(param);
                System.out.println(asrid+":asrid--同步给上级数据的返回--- bool："+bool+"---asrTxtParam_toout:"+ JacksonUtil.objebtToString(asrTxtParam_toout));

            }
            return true ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


}
