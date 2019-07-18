package com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.req;

public class UploadServiceByIidParam extends BaseParam {

    private String iid;//设备中的iid

    private String soursepath;//需要上传的文件的路径；

    public String getIid() {
        return iid;
    }

    public void setIid(String iid) {
        this.iid = iid;
    }

    public String getSoursepath() {
        return soursepath;
    }

    public void setSoursepath(String soursepath) {
        this.soursepath = soursepath;
    }
}
