package com.avst.equipmentcontrol.outside.interfacetoout.storage.req;

public class GetToOutStorageListParam extends BaseParam{

    private String iid;
    private String ssid;

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getIid() {
        return iid;
    }

    public void setIid(String iid) {
        this.iid = iid;
    }
}
