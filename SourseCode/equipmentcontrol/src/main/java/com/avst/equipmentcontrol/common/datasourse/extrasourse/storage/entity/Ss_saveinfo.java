package com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * InnoDB free: 37888 kB
 * </p>
 *
 * @author Mht
 * @since 2019-05-28
 */
public class Ss_saveinfo extends Model<Ss_saveinfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 存储设备表
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 设备ssid
     */
    private String mtssid;

    private String sstype;

    private Integer port;

    /**
     * 存储总容量,MB为单位
     */
    private Integer totalcapacity;

    /**
     * 存储已用容量,MB为单位
     */
    private Integer usedcapacity;

    /**
     * 存储本地文件夹base路径
     */
    private String datasavebasepath;

    /**
     * 服务中文说明
     */
    private String explain;

    private String string1;

    private String string2;

    private Integer integer1;

    private Integer integer2;

    private String ssid;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getMtssid() {
        return mtssid;
    }

    public void setMtssid(String mtssid) {
        this.mtssid = mtssid;
    }
    public String getSstype() {
        return sstype;
    }

    public void setSstype(String sstype) {
        this.sstype = sstype;
    }
    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
    public Integer getTotalcapacity() {
        return totalcapacity;
    }

    public void setTotalcapacity(Integer totalcapacity) {
        this.totalcapacity = totalcapacity;
    }
    public Integer getUsedcapacity() {
        return usedcapacity;
    }

    public void setUsedcapacity(Integer usedcapacity) {
        this.usedcapacity = usedcapacity;
    }
    public String getDatasavebasepath() {
        return datasavebasepath;
    }

    public void setDatasavebasepath(String datasavebasepath) {
        this.datasavebasepath = datasavebasepath;
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
        return "Ss_saveinfo{" +
        "id=" + id +
        ", mtssid=" + mtssid +
        ", sstype=" + sstype +
        ", port=" + port +
        ", totalcapacity=" + totalcapacity +
        ", usedcapacity=" + usedcapacity +
        ", datasavebasepath=" + datasavebasepath +
        ", explain=" + explain +
        ", string1=" + string1 +
        ", string2=" + string2 +
        ", integer1=" + integer1 +
        ", integer2=" + integer2 +
        ", ssid=" + ssid +
        "}";
    }
}
