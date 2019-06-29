package com.avst.equipmentcontrol.web.req.baseEttype;

import com.avst.equipmentcontrol.common.util.baseaction.Page;

public class BaseEttypeParam extends Page {

    private String ettypenum;
    private String ssid;

    public String getEttypenum() {
        return ettypenum;
    }

    public void setEttypenum(String ettypenum) {
        this.ettypenum = ettypenum;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }
}
