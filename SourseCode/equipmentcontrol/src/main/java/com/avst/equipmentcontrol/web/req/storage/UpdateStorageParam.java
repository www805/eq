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
     * 存储服务的协议类型
     */
    private String xytype;

    /**
     * 默认用户密码
     */
    private String passwd;
    /**
     * 默认用户名
     */
    private String user;

    /**
     * 存储服务器的状态
     */
    private Integer ssstate;

    /**
     * 割切存储路径
     */
    private String ssstatic;


    /**
     * 是否是默认的存储设备
     */
    private Integer defaultsavebool;

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

    public String getXytype() {
        return xytype;
    }

    public void setXytype(String xytype) {
        this.xytype = xytype;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Integer getSsstate() {
        return ssstate;
    }

    public void setSsstate(Integer ssstate) {
        this.ssstate = ssstate;
    }

    public String getSsstatic() {
        return ssstatic;
    }

    public void setSsstatic(String ssstatic) {
        this.ssstatic = ssstatic;
    }

    public Integer getDefaultsavebool() {
        return defaultsavebool;
    }

    public void setDefaultsavebool(Integer defaultsavebool) {
        this.defaultsavebool = defaultsavebool;
    }
}
