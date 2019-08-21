package com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.xmljsonobject.param;

public class Disc_iid {

    private String rs;//==1数据才有效

    private String crc32;//内容CRC校验码

    private String md5;//哈希值

    private String iid;//光盘编号

    private int cdnum;//0第一个光驱，1第二个光驱

    public int getCdnum() {
        return cdnum;
    }

    public void setCdnum(int cdnum) {
        this.cdnum = cdnum;
    }

    public String getRs() {
        return rs;
    }

    public void setRs(String rs) {
        this.rs = rs;
    }

    public String getCrc32() {
        return crc32;
    }

    public void setCrc32(String crc32) {
        this.crc32 = crc32;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getIid() {
        return iid;
    }

    public void setIid(String iid) {
        this.iid = iid;
    }
}
