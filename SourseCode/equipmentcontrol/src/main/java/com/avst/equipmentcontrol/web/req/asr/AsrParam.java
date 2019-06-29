package com.avst.equipmentcontrol.web.req.asr;

import com.avst.equipmentcontrol.common.util.baseaction.Page;

public class AsrParam extends Page {

    private String port;
    private String ssid;

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }
}
