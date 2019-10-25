package com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.req;

public class GetFDAllFileListByIidParam_out extends BaseParam {

    private String iid;//对应某一个文件夹的标识

    public String getIid() {
        return iid;
    }

    public void setIid(String iid) {
        this.iid = iid;
    }
}
