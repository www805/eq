package com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.req;


public class AddOrUpdateToOutFlushbonadingEttdParam extends BaseParam {

    /**
     * 设备通道表(针对审讯主机)
     */
    private Integer id;

    /**
     * 审讯主机ssid
     */
    private String flushbonadingssid;

    /**
     * 通道编号
     */
    private String tdnum;

    /**
     * 通道拉流地址
     */
    private String pullflowurl;

    /**
     * 通道类型,1音频通道/2视频通道
     */
    private Integer tdtype;

    private String ssid;

    private Integer shockenergy=3500;

    public Integer getShockenergy() {
        return shockenergy;
    }

    public void setShockenergy(Integer shockenergy) {
        this.shockenergy = shockenergy;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFlushbonadingssid() {
        return flushbonadingssid;
    }

    public void setFlushbonadingssid(String flushbonadingssid) {
        this.flushbonadingssid = flushbonadingssid;
    }

    public String getTdnum() {
        return tdnum;
    }

    public void setTdnum(String tdnum) {
        this.tdnum = tdnum;
    }

    public String getPullflowurl() {
        return pullflowurl;
    }

    public void setPullflowurl(String pullflowurl) {
        this.pullflowurl = pullflowurl;
    }

    public Integer getTdtype() {
        return tdtype;
    }

    public void setTdtype(Integer tdtype) {
        this.tdtype = tdtype;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }
}
