package com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.vo;

public class StartRecVO {

    String iid;//设备录像对应上层服务的唯一标识，有fdid_fdssid(其实就是会议ssid_设备ssid)

    public String getIid() {
        return iid;
    }

    public void setIid(String iid) {
        this.iid = iid;
    }
}
