package com.avst.equipmentcontrol.web.req.polygraph;

import com.avst.equipmentcontrol.common.datasourse.extrasourse.polygraph.entity.param.PolygraphInfo;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * 测谎参数类
 */
public class UpdatePolygraphParam {


    /**
     * 设备ssid
     */
    private String equipmentssid;

    /**
     * 服务类型
     */
    private String polygraphtype;

    /**
     * 验证密匙
     */
    private String polygraphkey;

    /**
     * 设备名称
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

    /**
     * 服务中文说明
     */
    private String explain;

    /**
     * 端口
     */
    private Integer port;

    private String ssid;


    public String getEquipmentssid() {
        return equipmentssid;
    }

    public void setEquipmentssid(String equipmentssid) {
        this.equipmentssid = equipmentssid;
    }

    public String getPolygraphtype() {
        return polygraphtype;
    }

    public void setPolygraphtype(String polygraphtype) {
        this.polygraphtype = polygraphtype;
    }

    public String getPolygraphkey() {
        return polygraphkey;
    }

    public void setPolygraphkey(String polygraphkey) {
        this.polygraphkey = polygraphkey;
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

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }
}
