package com.avst.equipmentcontrol.outside.dealoutinterface.asr.avstasr.req;

public class AVSTAsrParam_heartbeat extends BaseParam{

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AVSTAsrParam_heartbeat(String ip, String port, String id) {
        super(ip, port);
        this.id = id;
    }

    @Override
    public String toString() {
        return "AVSTAsrParam_heartbeat{" +
                "id='" + id + '\'' +
                "} " + super.toString();
    }
}
