package com.avst.equipmentcontrol.web.req.storage;

import com.avst.equipmentcontrol.common.util.baseaction.Page;

public class StorageParam extends Page {

    private String ssid;
    private String port;
    private String totalcapacity;
    private String etnum;
    private String etypessid;

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

    public String getTotalcapacity() {
        return totalcapacity;
    }

    public void setTotalcapacity(String totalcapacity) {
        this.totalcapacity = totalcapacity;
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
}
