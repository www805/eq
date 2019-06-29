package com.avst.equipmentcontrol.web.req.polygraph;

import com.avst.equipmentcontrol.common.util.baseaction.Page;

public class PolygraphParam extends Page {

    private String port;
    private String ssid;

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
