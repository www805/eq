package com.avst.equipmentcontrol.common.datasourse.extrasourse.polygraph.entity.param;

import com.avst.equipmentcontrol.common.datasourse.extrasourse.polygraph.entity.Polygraph_etinfo;
import com.avst.equipmentcontrol.common.datasourse.publicsourse.entity.Base_ettype;

import java.util.List;

public class PolygraphInfo extends Polygraph_etinfo {

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

    public String getEttypenum() {
        return ettypenum;
    }

    public void setEttypenum(String ettypenum) {
        this.ettypenum = ettypenum;
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
}
