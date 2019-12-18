package com.avst.equipmentcontrol.common.datasourse.extrasourse.asr.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * <p>
 * InnoDB free: 38912 kB
 * </p>
 *
 * @author Mht
 * @since 2019-05-14
 */
public class Asr_etinfo extends Model<Asr_etinfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 识别服务器表
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 设备ssid
     */
    private String equipmentssid;

    /**
     * 识别语种
     */
    private String language;

    /**
     * 并发数
     */
    private Integer maxnum;

    /**
     * 语音服务类型
     */
    private String asrtype;

    /**
     * 识别验证密匙
     */
    private String asrkey;

    /**
     * 语音识别的返回接口
     * 被动式接受数据
     */
    private String backtxtinterface;


    /**
     * 服务中文说明
     */
    private String explain;

    private String string1;

    private String string2;

    private Integer integer1;

    private Integer integer2;

    private String ssid;

    private Integer port;

    public String getBacktxtinterface() {
        return backtxtinterface;
    }

    public void setBacktxtinterface(String backtxtinterface) {
        this.backtxtinterface = backtxtinterface;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getEquipmentssid() {
        return equipmentssid;
    }

    public void setEquipmentssid(String equipmentssid) {
        this.equipmentssid = equipmentssid;
    }
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
    public Integer getMaxnum() {
        return maxnum;
    }

    public void setMaxnum(Integer maxnum) {
        this.maxnum = maxnum;
    }
    public String getAsrtype() {
        return asrtype;
    }

    public void setAsrtype(String asrtype) {
        this.asrtype = asrtype;
    }
    public String getAsrkey() {
        return asrkey;
    }

    public void setAsrkey(String asrkey) {
        this.asrkey = asrkey;
    }
    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
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
        return "Asr_etinfo{" +
        "id=" + id +
        ", equipmentssid=" + equipmentssid +
        ", language=" + language +
        ", maxnum=" + maxnum +
        ", asrtype=" + asrtype +
        ", asrkey=" + asrkey +
        ", explain=" + explain +
        ", string1=" + string1 +
        ", string2=" + string2 +
        ", integer1=" + integer1 +
        ", integer2=" + integer2 +
        ", ssid=" + ssid +
        "}";
    }
}
