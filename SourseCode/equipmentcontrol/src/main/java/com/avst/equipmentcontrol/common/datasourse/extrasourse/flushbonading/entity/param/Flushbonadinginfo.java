package com.avst.equipmentcontrol.common.datasourse.extrasourse.flushbonading.entity.param;

import com.avst.equipmentcontrol.common.datasourse.extrasourse.flushbonading.entity.Flushbonading_etinfo;
import com.avst.equipmentcontrol.common.datasourse.publicsourse.entity.Base_ettype;
import com.avst.equipmentcontrol.web.vo.baseEttype.EquipmentBasicsVO;

import java.util.List;

/**
 * 详细的审讯设备信息
 */
public class Flushbonadinginfo extends Flushbonading_etinfo {

    /**
     * 设备编号
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

    //设备类型集合
    private List<Base_ettype> ettypeList;

    public List<Base_ettype> getEttypeList() {
        return ettypeList;
    }

    public void setEttypeList(List<Base_ettype> ettypeList) {
        this.ettypeList = ettypeList;
    }

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
