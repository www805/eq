package com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.req;

public class GetFTPUploadSpeedParam extends BaseParam {

    private String path;//上传文件的路径，设备上的路径

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
