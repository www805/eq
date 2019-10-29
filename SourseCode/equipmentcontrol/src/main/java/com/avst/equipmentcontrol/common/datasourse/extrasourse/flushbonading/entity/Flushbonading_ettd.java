package com.avst.equipmentcontrol.common.datasourse.extrasourse.flushbonading.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * InnoDB free: 38912 kB
 * </p>
 *
 * @author Mht
 * @since 2019-05-14
 */
public class Flushbonading_ettd extends Model<Flushbonading_ettd> {

    private static final long serialVersionUID = 1L;

    /**
     * 设备通道表(针对审讯主机)
     */
    @TableId(value = "id", type = IdType.AUTO)
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

    /**
     * 音频音能激活语音阀值(0-20000)
     * 默认3500
     */
    private Integer shockenergy=3500;


    private String string1;

    private String string2;

    private Integer integer1;

    private Integer integer2;

    private String ssid;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

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
    public String getString1() {
        return string1;
    }

    public void setString1(String string1) {
        this.string1 = string1;
    }
    public String getString2() {
        return string2;
    }

    public void setString2(String string2) {
        this.string2 = string2;
    }
    public Integer getInteger1() {
        return integer1;
    }

    public void setInteger1(Integer integer1) {
        this.integer1 = integer1;
    }
    public Integer getInteger2() {
        return integer2;
    }

    public void setInteger2(Integer integer2) {
        this.integer2 = integer2;
    }
    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Flushbonading_ettd{" +
        "id=" + id +
        ", flushbonadingssid=" + flushbonadingssid +
        ", tdnum=" + tdnum +
        ", pullflowurl=" + pullflowurl +
        ", tdtype=" + tdtype +
        ", string1=" + string1 +
        ", string2=" + string2 +
        ", integer1=" + integer1 +
        ", integer2=" + integer2 +
        ", ssid=" + ssid +
        "}";
    }
}
