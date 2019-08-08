package com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.req;

public class WorkStartParam extends BaseParam{

    /**
     * fdid实际上就是mtssid
     */
    private String fdid;//本次开启设备工作的唯一识别码，可能多个设备使用这个识别码，因为它们都是这一次使用的设备

    public String getFdid() {
        return fdid;
    }

    public void setFdid(String fdid) {
        this.fdid = fdid;
    }
}
