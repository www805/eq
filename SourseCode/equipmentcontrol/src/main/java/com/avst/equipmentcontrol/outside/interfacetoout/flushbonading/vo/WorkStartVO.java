package com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.vo;

public class WorkStartVO {

    private String iid;//设备录像唯一识别码

    private String fdlivingurl;//设备的直播地址

    private String fdpreviewurl;//设备的直播预览地址

    private long startrecordtime;//开始录音的毫秒数

    public long getStartrecordtime() {
        return startrecordtime;
    }

    public void setStartrecordtime(long startrecordtime) {
        this.startrecordtime = startrecordtime;
    }

    public String getIid() {
        return iid;
    }

    public void setIid(String iid) {
        this.iid = iid;
    }

    public String getFdlivingurl() {
        return fdlivingurl;
    }

    public void setFdlivingurl(String fdlivingurl) {
        this.fdlivingurl = fdlivingurl;
    }

    public String getFdpreviewurl() {
        return fdpreviewurl;
    }

    public void setFdpreviewurl(String fdpreviewurl) {
        this.fdpreviewurl = fdpreviewurl;
    }
}
