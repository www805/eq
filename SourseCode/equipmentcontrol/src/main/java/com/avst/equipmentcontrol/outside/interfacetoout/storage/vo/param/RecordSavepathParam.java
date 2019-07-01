package com.avst.equipmentcontrol.outside.interfacetoout.storage.vo.param;

public class RecordSavepathParam {

    private String xyType;//地址的协议

    private String savepath;//存储地址

    private String filename;//文件名

    private String datatype;//文件类型

    public String getXyType() {
        return xyType;
    }

    public void setXyType(String xyType) {
        this.xyType = xyType;
    }

    public String getSavepath() {
        return savepath;
    }

    public void setSavepath(String savepath) {
        this.savepath = savepath;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getDatatype() {
        return datatype;
    }

    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }
}
