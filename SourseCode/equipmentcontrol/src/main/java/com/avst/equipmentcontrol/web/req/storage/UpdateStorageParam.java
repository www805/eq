package com.avst.equipmentcontrol.web.req.storage;


public class UpdateStorageParam {

    private String ssid;

    private Integer port;

    private String sstype;

    /**
     * 存储本地文件夹base路径
     */
    private String datasavebasepath;

    /**
     * 设备类型ssid
     */
    private String etypessid;

    /**
     * 设备名称
     */
    private String etnum;

    /**
     * 设备IP
     */
    private String etip;

    /**
     * 服务中文说明
     */
    private String explain;

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getSstype() {
        return sstype;
    }

    public void setSstype(String sstype) {
        this.sstype = sstype;
    }

    public String getDatasavebasepath() {
        return datasavebasepath;
    }

    public void setDatasavebasepath(String datasavebasepath) {
        this.datasavebasepath = datasavebasepath;
    }

    public String getEtypessid() {
        return etypessid;
    }

    public void setEtypessid(String etypessid) {
        this.etypessid = etypessid;
    }

    public String getEtnum() {
        return etnum;
    }

    public void setEtnum(String etnum) {
        this.etnum = etnum;
    }

    public String getEtip() {
        return etip;
    }

    public void setEtip(String etip) {
        this.etip = etip;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }
}
