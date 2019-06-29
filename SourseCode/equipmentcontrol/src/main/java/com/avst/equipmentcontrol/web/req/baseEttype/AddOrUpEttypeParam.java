package com.avst.equipmentcontrol.web.req.baseEttype;

public class AddOrUpEttypeParam {

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
