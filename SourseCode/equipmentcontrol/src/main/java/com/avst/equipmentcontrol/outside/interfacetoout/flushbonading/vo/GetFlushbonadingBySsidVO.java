package com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.vo;

public class GetFlushbonadingBySsidVO {

    private Integer id;

    /**
     * 设备ssid
     */
    private String equipmentssid;

    /**
     * 直播地址
     */
    private String livingurl;

    /**
     * 开放接口的端口
     */
    private Integer port;

    /**
     * 中文解释
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

    private String user;

    private String passwd;

    /**
     * 其实就是集中管理里面的本机设备ID，就是用来ftp上传时加以及路径，方便区分
     */
    private String uploadbasepath;

    public String getUploadbasepath() {
        return uploadbasepath;
    }

    public void setUploadbasepath(String uploadbasepath) {
        this.uploadbasepath = uploadbasepath;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getEttypenum() {
        return ettypenum;
    }

    public void setEttypenum(String ettypenum) {
        this.ettypenum = ettypenum;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEquipmentssid() {
        return equipmentssid;
    }

    public void setEquipmentssid(String equipmentssid) {
        this.equipmentssid = equipmentssid;
    }

    public String getLivingurl() {
        return livingurl;
    }

    public void setLivingurl(String livingurl) {
        this.livingurl = livingurl;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
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
}
