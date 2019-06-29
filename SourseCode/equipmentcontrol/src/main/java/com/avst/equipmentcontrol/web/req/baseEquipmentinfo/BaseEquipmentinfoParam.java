package com.avst.equipmentcontrol.web.req.baseEquipmentinfo;

import com.avst.equipmentcontrol.common.util.baseaction.Page;

public class BaseEquipmentinfoParam extends Page {

    private String etnum;
    private String ssid;

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getEtnum() {
        return etnum;
    }

    public void setEtnum(String etnum) {
        this.etnum = etnum;
    }
}
