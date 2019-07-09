package com.avst.equipmentcontrol.outside.interfacetoout.base.req;

import com.avst.equipmentcontrol.outside.interfacetoout.polygraph.req.BaseParam;

public class addOrUpdateToOutEttypeParam {

    private String type;
    private String ettypenum;
    private String ssid;
    private String explain;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

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

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }


}
