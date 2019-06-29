package com.avst.equipmentcontrol.web.req.storage;

import com.avst.equipmentcontrol.common.util.baseaction.Page;

public class StorageParam extends Page {

    private String ssid;
    private String port;

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
