package com.avst.equipmentcontrol.outside.dealoutinterface.storage.avstss.req;

public class SaveFileByIidParam extends BaseParam {

    private String iid;

    private String saveinfossid;//存储服务ssid

    private String uploadbasepath;//该存储服务的本地存放位置

    private String fdssid;//这里是嵌入式设备ssid，V1版本的数据都是从

    private long startrecordtime;//本次录像的服务器开始时间

    public long getStartrecordtime() {
        return startrecordtime;
    }

    public void setStartrecordtime(long startrecordtime) {
        this.startrecordtime = startrecordtime;
    }

    public String getUploadbasepath() {
        return uploadbasepath;
    }

    public void setUploadbasepath(String uploadbasepath) {
        this.uploadbasepath = uploadbasepath;
    }

    public String getSaveinfossid() {
        return saveinfossid;
    }

    public void setSaveinfossid(String saveinfossid) {
        this.saveinfossid = saveinfossid;
    }

    public String getIid() {
        return iid;
    }

    public void setIid(String iid) {
        this.iid = iid;
    }

    public String getFdssid() {
        return fdssid;
    }

    public void setFdssid(String fdssid) {
        this.fdssid = fdssid;
    }
}
