package com.avst.equipmentcontrol.web.vo;

public class EcCountVO {

    private Integer flushbonadingCount;
    private Integer asrCount;
    private Integer polygraphCount;
    private Integer ssCount;

    //设备类型总数
    private Integer ettypeCount;

    public Integer getFlushbonadingCount() {
        return flushbonadingCount;
    }

    public void setFlushbonadingCount(Integer flushbonadingCount) {
        this.flushbonadingCount = flushbonadingCount;
    }

    public Integer getAsrCount() {
        return asrCount;
    }

    public void setAsrCount(Integer asrCount) {
        this.asrCount = asrCount;
    }

    public Integer getPolygraphCount() {
        return polygraphCount;
    }

    public void setPolygraphCount(Integer polygraphCount) {
        this.polygraphCount = polygraphCount;
    }

    public Integer getSsCount() {
        return ssCount;
    }

    public void setSsCount(Integer ssCount) {
        this.ssCount = ssCount;
    }

    public Integer getEttypeCount() {
        return ettypeCount;
    }

    public void setEttypeCount(Integer ettypeCount) {
        this.ettypeCount = ettypeCount;
    }
}
