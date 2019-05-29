package com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.xmljsonobject;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement//根节点
public class SetMiddleware_FTPXml {

    @XmlElement
    private String version;

    @XmlElement
    private String status;

    @XmlElement
    private String enable;

    @XmlElement
    private String pasvmode;

    @XmlElement
    private String servicename;

    @XmlElement
    private String serviceport;

    @XmlElement
    private String serverip;

    @XmlElement
    private String hreadbeatip;

    @XmlElement
    private String usehreadbeatip;

    @XmlElement
    private String deviceid;

    @XmlElement
    private String user;

    @XmlElement
    private String pass;

    @XmlElement
    private String limit_speed;

    @XmlElement
    private String filter_enable;

    @XmlElement
    private String search_filter;

    @XmlElement
    private String reboot;

    @XmlElement
    private String msg;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }

    public String getPasvmode() {
        return pasvmode;
    }

    public void setPasvmode(String pasvmode) {
        this.pasvmode = pasvmode;
    }

    public String getServicename() {
        return servicename;
    }

    public void setServicename(String servicename) {
        this.servicename = servicename;
    }

    public String getServiceport() {
        return serviceport;
    }

    public void setServiceport(String serviceport) {
        this.serviceport = serviceport;
    }

    public String getServerip() {
        return serverip;
    }

    public void setServerip(String serverip) {
        this.serverip = serverip;
    }

    public String getHreadbeatip() {
        return hreadbeatip;
    }

    public void setHreadbeatip(String hreadbeatip) {
        this.hreadbeatip = hreadbeatip;
    }

    public String getUsehreadbeatip() {
        return usehreadbeatip;
    }

    public void setUsehreadbeatip(String usehreadbeatip) {
        this.usehreadbeatip = usehreadbeatip;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getLimit_speed() {
        return limit_speed;
    }

    public void setLimit_speed(String limit_speed) {
        this.limit_speed = limit_speed;
    }

    public String getFilter_enable() {
        return filter_enable;
    }

    public void setFilter_enable(String filter_enable) {
        this.filter_enable = filter_enable;
    }

    public String getSearch_filter() {
        return search_filter;
    }

    public void setSearch_filter(String search_filter) {
        this.search_filter = search_filter;
    }

    public String getReboot() {
        return reboot;
    }

    public void setReboot(String reboot) {
        this.reboot = reboot;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
