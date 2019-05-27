package com.avst.equipmentcontrol.outside.interfacetoout.asr.conf;

import com.avst.equipmentcontrol.outside.dealoutinterface.asr.cache.AsrCache;
import com.avst.equipmentcontrol.outside.interfacetoout.asr.cache.AsrCache_toout;

/**
 * 当用户关闭asr识别的时候asr识别可能还有部分数据没有回传，所以需要延迟关闭数据
 */
public class AsrOverThread<T> extends Thread{

    private int heartbeatTime=10;//关闭等待时间S

    private String asrid;//语音识别的唯一通用标识


    public AsrOverThread(int heartbeatTime, String asrid) {
        if(heartbeatTime!=0){
           this.heartbeatTime = heartbeatTime;
        }
        this.asrid = asrid;
    }

    public AsrOverThread( String asrssid) {
        this.asrid = asrssid;
    }

    @Override
    public void run() {

        try {
            Thread.sleep(heartbeatTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //先关闭对外的通用的asr数据
        AsrCache_toout.delAsrTxt(asrid);

        //关闭对内的通用的asr数据
        AsrCache.delAsrTxtByASRSsid(asrid);

        //关闭识别信息缓存
        AsrCache_toout.delAsrMassege(asrid);

    }
}
