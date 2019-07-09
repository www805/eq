package com.avst.equipmentcontrol.outside.interfacetoout.storage.req;

public class AddOrUpdateToOutStorageParam extends BaseParam {

    /**
     * 存储设备表
     */
    private Integer id;

    /**
     * 设备ssid
     */
    private String mtssid;

    private String sstype;

    private Integer port;

    /**
     * 存储总容量,MB为单位
     */
    private Integer totalcapacity;

    /**
     * 存储已用容量,MB为单位
     */
    private Integer usedcapacity;

    /**
     * 存储本地文件夹base路径
     */
    private String datasavebasepath;

    /**
     * 服务中文说明
     */
    private String explain;

    private String ssid;

    /**
     * 设备编号
     */
    private String etnum;

    /**
     * 设备IP
     */
    private String etip;

    /**
     * 设备类型ssid
     */
    private String etypessid;

    /**
     * 设备类型
     */
    private String ettypenum;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMtssid() {
        return mtssid;
    }

    public void setMtssid(String mtssid) {
        this.mtssid = mtssid;
    }

    public String getSstype() {
        return sstype;
    }

    public void setSstype(String sstype) {
        this.sstype = sstype;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getTotalcapacity() {
        return totalcapacity;
    }

    public void setTotalcapacity(Integer totalcapacity) {
        this.totalcapacity = totalcapacity;
    }

    public Integer getUsedcapacity() {
        return usedcapacity;
    }

    public void setUsedcapacity(Integer usedcapacity) {
        this.usedcapacity = usedcapacity;
    }

    public String getDatasavebasepath() {
        return datasavebasepath;
    }

    public void setDatasavebasepath(String datasavebasepath) {
        this.datasavebasepath = datasavebasepath;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
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

    public String getEtypessid() {
        return etypessid;
    }

    public void setEtypessid(String etypessid) {
        this.etypessid = etypessid;
    }

    public String getEttypenum() {
        return ettypenum;
    }

    public void setEttypenum(String ettypenum) {
        this.ettypenum = ettypenum;
    }
}
