package com.avst.equipmentcontrol.web.req.baseEquipmentinfo;

import javax.validation.constraints.NotNull;

public class AddOrUpBaseEquipmentinfoParam {

    //设备编号
    private String etnum;

    //设备IP
    private String etip;

    //设备类型ssid
    private String etypessid;

    //ssid
    private String ssid;

    //设备中文解释
    private String explain;

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

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }
}
