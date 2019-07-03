package com.avst.equipmentcontrol.web.req.polygraph;

import com.avst.equipmentcontrol.common.util.baseaction.Page;

public class PolygraphParam extends Page {

    private String port;
    private String polygraphkey;
    private String etnum;
    private String etypessid;
    private String ssid;

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getPolygraphkey() {
        return polygraphkey;
    }

    public void setPolygraphkey(String polygraphkey) {
        this.polygraphkey = polygraphkey;
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
