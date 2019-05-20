package com.avst.equipmentcontrol.outside.interfacetoout.asr.req;

/**
 * 开启asr服务需要带的参数
 */
public class StartAsrParam extends  BaseReqParam {

    private String tdssid;//通道ssid，也可以是这次语音识别提供音频一方的音频唯一识别码，通过它找到音频

    public String getTdssid() {
        return tdssid;
    }

    public void setTdssid(String tdssid) {
        this.tdssid = tdssid;
    }
}
