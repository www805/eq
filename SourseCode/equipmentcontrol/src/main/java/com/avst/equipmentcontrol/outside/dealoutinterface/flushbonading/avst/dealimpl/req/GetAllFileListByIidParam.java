package com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.req;

public class GetAllFileListByIidParam extends BaseParam {

    private String iid;//唯一标识

    private String dev_type="3";

    private String partition_index="0";

    public String getDev_type() {
        return dev_type;
    }

    public void setDev_type(String dev_type) {
        this.dev_type = dev_type;
    }

    public String getPartition_index() {
        return partition_index;
    }

    public void setPartition_index(String partition_index) {
        this.partition_index = partition_index;
    }

    public String getIid() {
        return iid;
    }

    public void setIid(String iid) {
        this.iid = iid;
    }
}
