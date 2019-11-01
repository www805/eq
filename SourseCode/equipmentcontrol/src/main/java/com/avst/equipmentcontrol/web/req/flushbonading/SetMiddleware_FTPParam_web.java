package com.avst.equipmentcontrol.web.req.flushbonading;

public class SetMiddleware_FTPParam_web {

    private String enable="1";//0|1,1允许使用

    private String limit_speed="4096";//限制速度 单位 KB

    private String search_filter;//过滤该日期之前的文件不上传，日期格式，2014-01-01

    private String filter_enable="0";//是否启用过滤日期 0|1,1是过滤

    private String serverip ;//服务器 ip

    private String serverport="21";//服务器端口

    private String hreadbeatip;//心跳服务器 ip

    private String servicename;//中间件服务名称

    private String deviceid="sb3";//设备id 标识

    private String svrusr="admin" ;//服务器用户名

    private String svrpwd="admin123" ;//服务器密码

    private String pasvmode="0" ;//是否为被动模式 0|1,1设置被动模式

    private String restart ;//是否自动重启该服务 0|1

    private String fdssid;//嵌入式设备ssid

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }

    public String getLimit_speed() {
        return limit_speed;
    }

    public void setLimit_speed(String limit_speed) {
        this.limit_speed = limit_speed;
    }

    public String getSearch_filter() {
        return search_filter;
    }

    public void setSearch_filter(String search_filter) {
        this.search_filter = search_filter;
    }

    public String getFilter_enable() {
        return filter_enable;
    }

    public void setFilter_enable(String filter_enable) {
        this.filter_enable = filter_enable;
    }

    public String getServerip() {
        return serverip;
    }

    public void setServerip(String serverip) {
        this.serverip = serverip;
    }

    public String getServerport() {
        return serverport;
    }

    public void setServerport(String serverport) {
        this.serverport = serverport;
    }

    public String getHreadbeatip() {
        return hreadbeatip;
    }

    public void setHreadbeatip(String hreadbeatip) {
        this.hreadbeatip = hreadbeatip;
    }

    public String getServicename() {
        return servicename;
    }

    public void setServicename(String servicename) {
        this.servicename = servicename;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getSvrusr() {
        return svrusr;
    }

    public void setSvrusr(String svrusr) {
        this.svrusr = svrusr;
    }

    public String getSvrpwd() {
        return svrpwd;
    }

    public void setSvrpwd(String svrpwd) {
        this.svrpwd = svrpwd;
    }

    public String getPasvmode() {
        return pasvmode;
    }

    public void setPasvmode(String pasvmode) {
        this.pasvmode = pasvmode;
    }

    public String getRestart() {
        return restart;
    }

    public void setRestart(String restart) {
        this.restart = restart;
    }

    public String getFdssid() {
        return fdssid;
    }

    public void setFdssid(String fdssid) {
        this.fdssid = fdssid;
    }
}
