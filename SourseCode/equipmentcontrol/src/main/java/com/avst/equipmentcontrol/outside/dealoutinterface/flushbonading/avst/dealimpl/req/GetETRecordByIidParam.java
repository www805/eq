package com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.req;

public class GetETRecordByIidParam extends BaseParam{

    private String rec_id;

    private String dev_type="3";

    private String partition_index="0";

    public String getRec_id() {
        return rec_id;
    }

    public void setRec_id(String rec_id) {
        this.rec_id = rec_id;
    }

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
}
