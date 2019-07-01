package com.avst.equipmentcontrol.outside.dealoutinterface.storage.avstss.req;

public class SaveFileByIid_localParam  {

    private String iid;

    private String saveinfossid;//存储服务ssid

    private String uploadbasepath;//该存储服务的本地存放位置

    private String sourseRelativePath;//源文件的相对路径，相对存储服务器的base路径

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

    public String getSourseRelativePath() {
        return sourseRelativePath;
    }

    public void setSourseRelativePath(String sourseRelativePath) {
        this.sourseRelativePath = sourseRelativePath;
    }
}
