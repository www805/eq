package com.avst.equipmentcontrol.outside.interfacetoout.storage.req;

public class SaveFileParam {

    private String sourseIp;//源文件所在设备的IP

    private String sourseFDETSsid;//嵌入式设备ssid

    private String ssType;//请求的是哪一个存储服务，avst ss_avst

    private String iid;//文件在源设备中的唯一识别码

    private long startrecordtime;//本次录像开始时间

    public long getStartrecordtime() {
        return startrecordtime;
    }

    public void setStartrecordtime(long startrecordtime) {
        this.startrecordtime = startrecordtime;
    }

    public String getSourseFDETSsid() {
        return sourseFDETSsid;
    }

    public void setSourseFDETSsid(String sourseFDETSsid) {
        this.sourseFDETSsid = sourseFDETSsid;
    }

    public String getSourseIp() {
        return sourseIp;
    }

    public void setSourseIp(String sourseIp) {
        this.sourseIp = sourseIp;
    }

    public String getSsType() {
        return ssType;
    }

    public void setSsType(String ssType) {
        this.ssType = ssType;
    }

    public String getIid() {
        return iid;
    }

    public void setIid(String iid) {
        this.iid = iid;
    }
}
