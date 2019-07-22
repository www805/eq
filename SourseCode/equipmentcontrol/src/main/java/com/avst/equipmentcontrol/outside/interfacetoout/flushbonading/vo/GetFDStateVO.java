package com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.vo;

public class GetFDStateVO {

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

    /**
     * 光盘数量，1个的时候就只有a
     */
    private String dvdnum;

    /**
     * 判断温湿度是否有效
     * ==1就说明有效
     */
    private String dh_set;

    /**
     * 温度值
     */
    private String temperature_value;

    /**
     * 湿度值
     */
    private String humidity_value;

    /**
     * 光驱1工作状态 0 空闲 1 刻录中 2 正在停止刻录 3 正在 封盘 4 正在恢复光盘 5 正在格式化光盘 6 光盘读取中 7 正 在开始刻录 8 正在开始格式化光盘 9 刻录中(数据) 10 正在停 止刻录(数据) 11 正在封盘(数据) 个别机型支持[12 刻录中(推 送刻录) 13 正在停止刻录(推送刻录) 14 正在封盘(推送刻录)
     */
    private String roma_status;

    /**
     * 光驱2工作状态 0 空闲 1 刻录中 2 正在停止刻录 3 正在 封盘 4 正在恢复光盘 5 正在格式化光盘 6 光盘读取中 7 正 在开始刻录 8 正在开始格式化光盘 9 刻录中(数据) 10 正在停 止刻录(数据) 11 正在封盘(数据) 个别机型支持[12 刻录中(推 送刻录) 13 正在停止刻录(推送刻录) 14 正在封盘(推送刻录)
     */
    private String romb_status;

    /**
     * 光驱1是否在刻录中 0:未刻录 1:刻录中
     */
    private String roma_isburn;

    /**
     * /光驱2是否在刻录中 0:未刻录 1:刻录中
     */
    private String romb_isburn;

    /**
     * /光驱1是否在封盘中 0:未执行封盘 1:封盘中
     */
    private String roma_isfinishburn;

    /**
     * /光驱2是否在封盘中 0:未执行封盘 1:封盘中
     */
    private String romb_isfinishburn;

    /**
     * 光驱1当前的光盘类型 0:未知的 1:DVD+R 2:DVD-R 3:DVD+R DL 4:DVD-R DL 5:BuleRay DVD 10:可播放的 11:已格式化可直 接刻录的 12:错误的盘片 13:无光盘
     */
    private String roma_disktype;

    /**
     * 光驱2当前的光盘类型 0:未知的 1:DVD+R 2:DVD-R 3:DVD+R DL 4:DVD-R DL 5:BuleRay DVD 10:可播放的 11:已格式化可直 接刻录的 12:错误的盘片 13:无光盘
     */
    private String romb_disktype;

    /**
     * 光驱1 总容量(单位:字节)
     */
    private String roma_discCap;

    /**
     * 光驱2 总容量(单位:字节)
     */
    private String romb_discCap;

    /**
     * 光驱1 已用容量(单位:字节)
     */
    private String roma_discCapUsed;

    /**
     * 光驱2 已用容量(单位:字节)
     */
    private String romb_discCapUsed;

    /**
     * 光驱1已设的刻录时间(如果当前在直刻,那么该参数有效)
     */
    private String roma_setburntime;

    /**
     * 光驱2已设的刻录时间(如果当前在直刻,那么该参数有效)
     */
    private String romb_setburntime;

    /**
     * 光驱1开始直刻的时间(如果当前在直刻,那么该参数有效)
     */
    private String roma_begintime;

    /**
     * 光驱2开始直刻的时间(如果当前在直刻,那么该参数有效)
     */
    private String romb_begintime;

    /**
     * 光驱1直刻剩余的倒计时(单位:秒)(如果当前在直刻,那么该 参数有效)
     */
    private String roma_lefttime;

    /**
     * 光驱2直刻剩余的倒计时(单位:秒)(如果当前在直刻,那么该 参数有效)
     */
    private String romb_lefttime;

    /**
     * 当前刻录模式 0:直刻模式 1:硬盘导刻模式 2:接力刻录模 式
     */
    private String burn_mode;

    /**
     * 当前直刻状态暂停刻录中 1:暂停刻录中 0:未暂停刻录(如 果当前在直刻,那么该参数有效)
     */
    private String burn_status;

    /**
     * 当前选择的刻录时长(单位:秒)
     */
    private String selburntime;

    /**
     * 是否为双光驱同步刻录 1:是 0:否
     */
    private String burn_syn_yw;

    /**
     * 下一个接力刻录的光驱序号 0:光驱1 1:光驱2(如果当前在接 力,那么该参数有效)
     */
    private String next_burndev;

    /**
     * 上一个接力刻录的光驱序号 0:光驱1 1:光驱2(如果当前在接 力,那么该参数有效)
     */
    private String prev_burndev;

    /**
     * 刻录选时
     */
    private String bsettimerrecburntime;

    public String getDvdnum() {
        return dvdnum;
    }

    public void setDvdnum(String dvdnum) {
        this.dvdnum = dvdnum;
    }

    public String getDh_set() {
        return dh_set;
    }

    public void setDh_set(String dh_set) {
        this.dh_set = dh_set;
    }

    public String getTemperature_value() {
        return temperature_value;
    }

    public void setTemperature_value(String temperature_value) {
        this.temperature_value = temperature_value;
    }

    public String getHumidity_value() {
        return humidity_value;
    }

    public void setHumidity_value(String humidity_value) {
        this.humidity_value = humidity_value;
    }

    public String getBsettimerrecburntime() {
        return bsettimerrecburntime;
    }

    public void setBsettimerrecburntime(String bsettimerrecburntime) {
        this.bsettimerrecburntime = bsettimerrecburntime;
    }

    public String getRoma_status() {
        return roma_status;
    }

    public void setRoma_status(String roma_status) {
        this.roma_status = roma_status;
    }

    public String getRomb_status() {
        return romb_status;
    }

    public void setRomb_status(String romb_status) {
        this.romb_status = romb_status;
    }

    public String getRoma_isburn() {
        return roma_isburn;
    }

    public void setRoma_isburn(String roma_isburn) {
        this.roma_isburn = roma_isburn;
    }

    public String getRomb_isburn() {
        return romb_isburn;
    }

    public void setRomb_isburn(String romb_isburn) {
        this.romb_isburn = romb_isburn;
    }

    public String getRoma_isfinishburn() {
        return roma_isfinishburn;
    }

    public void setRoma_isfinishburn(String roma_isfinishburn) {
        this.roma_isfinishburn = roma_isfinishburn;
    }

    public String getRomb_isfinishburn() {
        return romb_isfinishburn;
    }

    public void setRomb_isfinishburn(String romb_isfinishburn) {
        this.romb_isfinishburn = romb_isfinishburn;
    }

    public String getRoma_disktype() {
        return roma_disktype;
    }

    public void setRoma_disktype(String roma_disktype) {
        this.roma_disktype = roma_disktype;
    }

    public String getRomb_disktype() {
        return romb_disktype;
    }

    public void setRomb_disktype(String romb_disktype) {
        this.romb_disktype = romb_disktype;
    }

    public String getRoma_discCap() {
        return roma_discCap;
    }

    public void setRoma_discCap(String roma_discCap) {
        this.roma_discCap = roma_discCap;
    }

    public String getRomb_discCap() {
        return romb_discCap;
    }

    public void setRomb_discCap(String romb_discCap) {
        this.romb_discCap = romb_discCap;
    }

    public String getRoma_discCapUsed() {
        return roma_discCapUsed;
    }

    public void setRoma_discCapUsed(String roma_discCapUsed) {
        this.roma_discCapUsed = roma_discCapUsed;
    }

    public String getRomb_discCapUsed() {
        return romb_discCapUsed;
    }

    public void setRomb_discCapUsed(String romb_discCapUsed) {
        this.romb_discCapUsed = romb_discCapUsed;
    }

    public String getRoma_setburntime() {
        return roma_setburntime;
    }

    public void setRoma_setburntime(String roma_setburntime) {
        this.roma_setburntime = roma_setburntime;
    }

    public String getRomb_setburntime() {
        return romb_setburntime;
    }

    public void setRomb_setburntime(String romb_setburntime) {
        this.romb_setburntime = romb_setburntime;
    }

    public String getRoma_begintime() {
        return roma_begintime;
    }

    public void setRoma_begintime(String roma_begintime) {
        this.roma_begintime = roma_begintime;
    }

    public String getRomb_begintime() {
        return romb_begintime;
    }

    public void setRomb_begintime(String romb_begintime) {
        this.romb_begintime = romb_begintime;
    }

    public String getRoma_lefttime() {
        return roma_lefttime;
    }

    public void setRoma_lefttime(String roma_lefttime) {
        this.roma_lefttime = roma_lefttime;
    }

    public String getRomb_lefttime() {
        return romb_lefttime;
    }

    public void setRomb_lefttime(String romb_lefttime) {
        this.romb_lefttime = romb_lefttime;
    }

    public String getBurn_mode() {
        return burn_mode;
    }

    public void setBurn_mode(String burn_mode) {
        this.burn_mode = burn_mode;
    }

    public String getBurn_status() {
        return burn_status;
    }

    public void setBurn_status(String burn_status) {
        this.burn_status = burn_status;
    }

    public String getSelburntime() {
        return selburntime;
    }

    public void setSelburntime(String selburntime) {
        this.selburntime = selburntime;
    }

    public String getBurn_syn_yw() {
        return burn_syn_yw;
    }

    public void setBurn_syn_yw(String burn_syn_yw) {
        this.burn_syn_yw = burn_syn_yw;
    }

    public String getNext_burndev() {
        return next_burndev;
    }

    public void setNext_burndev(String next_burndev) {
        this.next_burndev = next_burndev;
    }

    public String getPrev_burndev() {
        return prev_burndev;
    }

    public void setPrev_burndev(String prev_burndev) {
        this.prev_burndev = prev_burndev;
    }

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
