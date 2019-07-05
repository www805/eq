package com.avst.equipmentcontrol.outside.interfacetoout.asr.req;

import com.avst.equipmentcontrol.outside.interfacetoout.polygraph.req.BaseParam;

public class getToOutAsrListParam extends BaseParam {

    private String ssid;

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }
}
