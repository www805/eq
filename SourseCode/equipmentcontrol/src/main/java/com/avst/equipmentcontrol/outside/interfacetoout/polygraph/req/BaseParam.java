package com.avst.equipmentcontrol.outside.interfacetoout.polygraph.req;

public class BaseParam {

    private String phType;//身心监护请求类型 avst PH_AVST

    private  String polygraphssid;//身心监护服务ssid;

    public String getPolygraphssid() {
        return polygraphssid;
    }

    public void setPolygraphssid(String polygraphssid) {
        this.polygraphssid = polygraphssid;
    }

    public String getPhType() {
        return phType;
    }

    public void setPhType(String phType) {
        this.phType = phType;
    }
}
