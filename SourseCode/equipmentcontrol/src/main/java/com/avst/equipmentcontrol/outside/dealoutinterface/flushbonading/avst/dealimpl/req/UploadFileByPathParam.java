package com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.req;

public class UploadFileByPathParam extends BaseParam {

    private String recordpath;//上传文件的路径

    private int reupload=1;//reupload 0|1 默认值为 0,0 为不重新上传，值为 1 时则优先判断是否曾经上传完成以及断点续传

    public String getRecordpath() {
        return recordpath;
    }

    public void setRecordpath(String recordpath) {
        this.recordpath = recordpath;
    }

    public int getReupload() {
        return reupload;
    }

    public void setReupload(int reupload) {
        this.reupload = reupload;
    }
}
