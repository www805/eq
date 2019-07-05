package com.avst.equipmentcontrol.outside.interfacetoout.base.req;

import com.avst.equipmentcontrol.outside.interfacetoout.polygraph.req.BaseParam;

public class addOrUpdateToOutEttypeParam extends BaseParam {

    private String ettypenum;
    private String ssid;
    private String explain;

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
