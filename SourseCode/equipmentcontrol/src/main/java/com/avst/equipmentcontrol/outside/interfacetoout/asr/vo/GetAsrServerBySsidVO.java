package com.avst.equipmentcontrol.outside.interfacetoout.asr.vo;

public class GetAsrServerBySsidVO {

    private Integer id;

    /**
     * 设备ssid
     */
    private String equipmentssid;

    /**
     * 识别语种
     */
    private String language;

    /**
     * 并发数
     */
    private Integer maxnum;

    /**
     * 语音服务类型
     */
    private String asrtype;

    /**
     * 识别验证密匙
     */
    private String asrkey;

    /**
     * 服务中文说明
     */
    private String explain;


    private String ssid;

    private Integer port;

    /**
     * 设备名称
     */
    private Integer etnum;

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

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
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

    public Integer getEtnum() {
        return etnum;
    }

    public void setEtnum(Integer etnum) {
        this.etnum = etnum;
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

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getMaxnum() {
        return maxnum;
    }

    public void setMaxnum(Integer maxnum) {
        this.maxnum = maxnum;
    }

    public String getAsrtype() {
        return asrtype;
    }

    public void setAsrtype(String asrtype) {
        this.asrtype = asrtype;
    }

    public String getAsrkey() {
        return asrkey;
    }

    public void setAsrkey(String asrkey) {
        this.asrkey = asrkey;
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
