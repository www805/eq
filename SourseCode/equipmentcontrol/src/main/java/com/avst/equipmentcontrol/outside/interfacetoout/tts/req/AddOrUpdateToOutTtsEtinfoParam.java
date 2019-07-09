package com.avst.equipmentcontrol.outside.interfacetoout.tts.req;

public class AddOrUpdateToOutTtsEtinfoParam extends BaseParam {

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
     * 最大并发数
     */
    private Integer maxnum;

    /**
     * 开放接口的端口
     */
    private Integer port;

    /**
     * tts服务类型
     */
    private String ttstype;

    /**
     * TTS密匙集,Tts请求服务中的部分参数集合，键值对组合
     */
    private String ttskeys;

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

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getTtstype() {
        return ttstype;
    }

    public void setTtstype(String ttstype) {
        this.ttstype = ttstype;
    }

    public String getTtskeys() {
        return ttskeys;
    }

    public void setTtskeys(String ttskeys) {
        this.ttskeys = ttskeys;
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
