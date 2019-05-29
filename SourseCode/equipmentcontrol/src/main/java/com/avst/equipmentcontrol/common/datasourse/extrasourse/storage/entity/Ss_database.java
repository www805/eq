package com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.entity;

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
 * @since 2019-05-28
 */
public class Ss_database extends Model<Ss_database> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 数据存储记录ssid
     */
    private String datasavessid;

    /**
     * 文件本次存储地址
     */
    private String datasavepath;

    private String datatype;

    /**
     * Rtmp地址
     */
    private String rtmpurl;

    /**
     * Httpflv地址
     */
    private String httpflvurl;

    /**
     * http地址
     */
    private String httpurl;

    /**
     * Hls地址
     */
    private String hlsurl;

    /**
     * 默认协议
     */
    private String defaulturl;

    /**
     * 文件状态
     * 0文件未获取，等待中；1文件正常，生成请求地址中；2文件可以正常使用；-1文件未正常获取，需强制获取；-2文件请求地址有误，需重新生成
     */
    private Integer state;

    /**
     * 文件大小MB
     */
    private long datasize;

    private long starttime;

    private long endtime;

    private String filename;

    private String soursedatapath;

    private String string1;

    private String string2;

    private Integer integer1;

    private Integer integer2;

    private String ssid;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getSoursedatapath() {
        return soursedatapath;
    }

    public void setSoursedatapath(String soursedatapath) {
        this.soursedatapath = soursedatapath;
    }

    public long getStarttime() {
        return starttime;
    }

    public void setStarttime(long starttime) {
        this.starttime = starttime;
    }

    public long getEndtime() {
        return endtime;
    }

    public void setEndtime(long endtime) {
        this.endtime = endtime;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public long getDatasize() {
        return datasize;
    }

    public void setDatasize(long datasize) {
        this.datasize = datasize;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getDatasavessid() {
        return datasavessid;
    }

    public void setDatasavessid(String datasavessid) {
        this.datasavessid = datasavessid;
    }
    public String getDatasavepath() {
        return datasavepath;
    }

    public void setDatasavepath(String datasavepath) {
        this.datasavepath = datasavepath;
    }
    public String getDatatype() {
        return datatype;
    }

    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }
    public String getRtmpurl() {
        return rtmpurl;
    }

    public void setRtmpurl(String rtmpurl) {
        this.rtmpurl = rtmpurl;
    }
    public String getHttpflvurl() {
        return httpflvurl;
    }

    public void setHttpflvurl(String httpflvurl) {
        this.httpflvurl = httpflvurl;
    }
    public String getHttpurl() {
        return httpurl;
    }

    public void setHttpurl(String httpurl) {
        this.httpurl = httpurl;
    }
    public String getHlsurl() {
        return hlsurl;
    }

    public void setHlsurl(String hlsurl) {
        this.hlsurl = hlsurl;
    }
    public String getDefaulturl() {
        return defaulturl;
    }

    public void setDefaulturl(String defaulturl) {
        this.defaulturl = defaulturl;
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
        return "Ss_database{" +
        "id=" + id +
        ", datasavessid=" + datasavessid +
        ", datasavepath=" + datasavepath +
        ", datatype=" + datatype +
        ", rtmpurl=" + rtmpurl +
        ", httpflvurl=" + httpflvurl +
        ", httpurl=" + httpurl +
        ", hlsurl=" + hlsurl +
        ", defaulturl=" + defaulturl +
        ", string1=" + string1 +
        ", string2=" + string2 +
        ", integer1=" + integer1 +
        ", integer2=" + integer2 +
        ", ssid=" + ssid +
        "}";
    }
}
