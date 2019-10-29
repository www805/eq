package com.avst.equipmentcontrol.outside.dealoutinterface.asr.avstasr.req;

import com.avst.equipmentcontrol.common.conf.AsrServerModel;

public class AVSTAsrParam_quit extends BaseParam{

   private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AVSTAsrParam_quit(String ip, String port, String id) {
        super(ip, port);
        this.id = id;
    }

    public AVSTAsrParam_quit(String ip, String port, String id,String asrServerModel) {
        super(ip, port,asrServerModel);
        this.id = id;
    }

    @Override
    public String toString() {
        return "AVSTAsrParam_quit{" +
                "id='" + id + '\'' +
                "} " + super.toString();
    }
}
