package com.avst.equipmentcontrol.outside.interfacetoout.base.req;

public class GetServerIpALLParam {

    private String baseType;
    private String fdssid;
    private String polygraphssid;
    private String asrssid;

    public String getBaseType() {
        return baseType;
    }

    public void setBaseType(String baseType) {
        this.baseType = baseType;
    }

    public String getFdssid() {
        return fdssid;
    }

    public void setFdssid(String fdssid) {
        this.fdssid = fdssid;
    }

    public String getPolygraphssid() {
        return polygraphssid;
    }

    public void setPolygraphssid(String polygraphssid) {
        this.polygraphssid = polygraphssid;
    }

    public String getAsrssid() {
        return asrssid;
    }

    public void setAsrssid(String asrssid) {
        this.asrssid = asrssid;
    }
}
