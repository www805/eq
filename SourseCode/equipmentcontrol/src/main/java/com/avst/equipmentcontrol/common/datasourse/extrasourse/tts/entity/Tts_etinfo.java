package com.avst.equipmentcontrol.common.datasourse.extrasourse.tts.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * InnoDB free: 37888 kB
 * </p>
 *
 * @author Mht
 * @since 2019-07-04
 */
public class Tts_etinfo extends Model<Tts_etinfo> {

    private static final long serialVersionUID = 1L;

    /**
     * tts文字转文字服务器
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
     * 最大并发数
     */
    private Integer maxnum;

    /**
     * 开放接口的端口
     */
    private Integer port;

    /**
     * tts服务类型
     */
    private String ttstype;

    /**
     * TTS密匙集,Tts请求服务中的部分参数集合，键值对组合
     */
    private String ttskeys;

    /**
     * 服务中文说明
     */
    private String explain;

    private String string1;

    private String string2;

    private Integer integer1;

    private Integer integer2;

    private String ssid;

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
    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
    public String getTtstype() {
        return ttstype;
    }

    public void setTtstype(String ttstype) {
        this.ttstype = ttstype;
    }
    public String getTtskeys() {
        return ttskeys;
    }

    public void setTtskeys(String ttskeys) {
        this.ttskeys = ttskeys;
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
        return "Tts_etinfo{" +
        "id=" + id +
        ", equipmentssid=" + equipmentssid +
        ", language=" + language +
        ", maxnum=" + maxnum +
        ", port=" + port +
        ", ttstype=" + ttstype +
        ", ttskeys=" + ttskeys +
        ", explain=" + explain +
        ", string1=" + string1 +
        ", string2=" + string2 +
        ", integer1=" + integer1 +
        ", integer2=" + integer2 +
        ", ssid=" + ssid +
        "}";
    }
}
