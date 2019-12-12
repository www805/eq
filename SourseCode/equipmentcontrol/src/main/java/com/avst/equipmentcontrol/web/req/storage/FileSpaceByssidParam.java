package com.avst.equipmentcontrol.web.req.storage;

public class FileSpaceByssidParam {

    private String ssid;
    private String path;//路径

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }
}
