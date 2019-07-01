package com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.xmljsonobject;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 只选自己用的
 */
public class CheckFDStateXml {

    private String version;

    
    private String dev_version;

    
    private String devmid_id;

    /**
     * 是否正在本地硬盘录像
     */
    private String diskrec_isrec;

    private String diskrec_status;

    /**
     * 本地硬盘开始录像时间
     */
    private String diskrec_begintime;

    /**
     * /硬盘录像 持续时间
     */
    private String diskrec_continuettime;

    /**
     * 硬盘是否加载
     */
    private String disk_loaded;

    /**
     * 硬盘剩余容量
     */
    private String disk_freespace;

    /**
     * /硬盘总容量
     */
    private String disk_totalspace;

    public String getDiskrec_status() {
        return diskrec_status;
    }

    public void setDiskrec_status(String diskrec_status) {
        this.diskrec_status = diskrec_status;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDev_version() {
        return dev_version;
    }

    public void setDev_version(String dev_version) {
        this.dev_version = dev_version;
    }

    public String getDevmid_id() {
        return devmid_id;
    }

    public void setDevmid_id(String devmid_id) {
        this.devmid_id = devmid_id;
    }

    public String getDiskrec_isrec() {
        return diskrec_isrec;
    }

    public void setDiskrec_isrec(String diskrec_isrec) {
        this.diskrec_isrec = diskrec_isrec;
    }

    public String getDiskrec_begintime() {
        return diskrec_begintime;
    }

    public void setDiskrec_begintime(String diskrec_begintime) {
        this.diskrec_begintime = diskrec_begintime;
    }

    public String getDiskrec_continuettime() {
        return diskrec_continuettime;
    }

    public void setDiskrec_continuettime(String diskrec_continuettime) {
        this.diskrec_continuettime = diskrec_continuettime;
    }

    public String getDisk_loaded() {
        return disk_loaded;
    }

    public void setDisk_loaded(String disk_loaded) {
        this.disk_loaded = disk_loaded;
    }

    public String getDisk_freespace() {
        return disk_freespace;
    }

    public void setDisk_freespace(String disk_freespace) {
        this.disk_freespace = disk_freespace;
    }

    public String getDisk_totalspace() {
        return disk_totalspace;
    }

    public void setDisk_totalspace(String disk_totalspace) {
        this.disk_totalspace = disk_totalspace;
    }
}
