package com.avst.equipmentcontrol.web.req.tts;

import com.avst.equipmentcontrol.common.util.baseaction.Page;

public class TtsetinfoParam extends Page {

    /**
     * 识别语种
     */
    private String language;

    /**
     * 最大并发数
     */
    private Integer maxnum;

    /**
     * 开放接口的端口
     */
    private String port;

    /**
     * tts服务类型
     */
    private String ttstype;

    /**
     * TTS密匙集,Tts请求服务中的部分参数集合，键值对组合
     */
    private String ttskeys;

    //设备类型
    private String etypessid;

    private String etnum;

    private String etip;

    private String ssid;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getEtnum() {
        return etnum;
    }

    public void setEtnum(String etnum) {
        this.etnum = etnum;
    }

    public String getEtip() {
        return etip;
    }

    public void setEtip(String etip) {
        this.etip = etip;
    }

    public String getEtypessid() {
        return etypessid;
    }

    public void setEtypessid(String etypessid) {
        this.etypessid = etypessid;
    }

    public Integer getMaxnum() {
        return maxnum;
    }

    public void setMaxnum(Integer maxnum) {
        this.maxnum = maxnum;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getTtstype() {
        return ttstype;
    }

    public void setTtstype(String ttstype) {
        this.ttstype = ttstype;
    }

    public String getTtskeys() {
        return ttskeys;
    }

    public void setTtskeys(String ttskeys) {
        this.ttskeys = ttskeys;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }
}
