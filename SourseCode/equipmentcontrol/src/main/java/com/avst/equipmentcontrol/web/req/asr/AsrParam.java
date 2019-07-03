package com.avst.equipmentcontrol.web.req.asr;

import com.avst.equipmentcontrol.common.util.baseaction.Page;

public class AsrParam extends Page {

    private String language;
    private String port;
    private String asrkey;
    private String etnum;
    private String etypessid;
    private String ssid;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getAsrkey() {
        return asrkey;
    }

    public void setAsrkey(String asrkey) {
        this.asrkey = asrkey;
    }

    public String getEtnum() {
        return etnum;
    }

    public void setEtnum(String etnum) {
        this.etnum = etnum;
    }

    public String getEtypessid() {
        return etypessid;
    }

    public void setEtypessid(String etypessid) {
        this.etypessid = etypessid;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }
}
