package com.avst.equipmentcontrol.outside.dealoutinterface.asr.v1.action;

import com.avst.equipmentcontrol.common.util.DateUtil;
import com.avst.equipmentcontrol.common.util.XMLUtil;
import com.avst.equipmentcontrol.outside.dealoutinterface.asr.cache.AsrCache;
import com.avst.equipmentcontrol.outside.dealoutinterface.asr.cache.param.AsrTxtParam_avst;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URLDecoder;

/**
 * 跟第三方语音服务器通讯
 */
@Controller
@RequestMapping("/toasr/v1")
public class AvstAsrAction {


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

            //查询数据库，把avst语音服务的ssid找出来，这里是avst asr语音服务的处理（以后写入缓存）
            String Equipmentssid="123456";

            //往缓存中插数据
            String asrid=asrTxtParam_avst.getId();//语音识别唯一通用识别码，用于关联本次识别服务的双方
            AsrTxtParam_avst asrTxtParam_avst_yuan=(AsrTxtParam_avst)AsrCache.getAsrTxtOneByASRssid(Equipmentssid,asrid,asrTxtParam_avst.getTime());
            AsrCache.setAsrByASRssid(Equipmentssid,asrid,asrTxtParam_avst);
            AsrTxtParam_avst asrTxtParam_avst_new=(AsrTxtParam_avst)AsrCache.getAsrTxtOneByASRssid(Equipmentssid,asrid,asrTxtParam_avst.getTime());
            System.out.println(asrTxtParam_avst_yuan==null? null:asrTxtParam_avst_yuan.getTime()+"--原来--"+(asrTxtParam_avst_yuan==null? null:asrTxtParam_avst_yuan.getMsg()));
            System.out.println(asrTxtParam_avst_new.getTime()+"--现在--"+asrTxtParam_avst_new.getMsg());

            return true ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


}
