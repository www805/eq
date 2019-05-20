package com.avst.equipmentcontrol.outside.dealoutinterface.asr.cache.param;

import java.util.List;
import java.util.Map;

/**
 * 服务返回文本的集合
 *
 */
public class AsrTxtParam<T> {

    private String asrtype;//当前识别的语音服务器的类型

    private String asrEquipmentssid;//当前识别的语音服务器的ssid

    private String asrid;//语音识别的唯一码

    /**
     * //这种类型的所有识别任务，key 是关联语音服务的唯一识别码，avst asr 是id
     * T 每句话都是一个对象，（流式识别）这句话不管识别了几次，永远记录最后一次识别
     */
    private  List<T> asrlist=null;

    public String getAsrtype() {
        return asrtype;
    }

    public void setAsrtype(String asrtype) {
        this.asrtype = asrtype;
    }

    public String getAsrEquipmentssid() {
        return asrEquipmentssid;
    }

    public void setAsrEquipmentssid(String asrEquipmentssid) {
        this.asrEquipmentssid = asrEquipmentssid;
    }

    public String getAsrid() {
        return asrid;
    }

    public void setAsrid(String asrid) {
        this.asrid = asrid;
    }

    public List<T> getAsrlist() {
        return asrlist;
    }

    public void setAsrlist(List<T> asrlist) {
        this.asrlist = asrlist;
    }
}
