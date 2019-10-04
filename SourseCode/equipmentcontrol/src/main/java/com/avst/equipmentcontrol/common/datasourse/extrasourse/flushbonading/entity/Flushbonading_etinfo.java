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
public class Flushbonading_etinfo extends Model<Flushbonading_etinfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 审讯主机
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 设备ssid
     */
    private String equipmentssid;

    /**
     * 直播地址
     */
    private String livingurl;

    /**
     * 直播预览地址（标清）
     */
    private String previewurl;


    /**
     * 默认直播地址
     */
    private Integer defaulturlbool;

    /**
     * 开放接口的端口
     */
    private Integer port;

    private String user;

    private String passwd;

    /**
     * ftp上传存储备设路径,只是一级路径，其实就是集中管理里面的本机设备ID，就是用来ftp上传时加以及路径，方便区分
     */
    private String uploadbasepath;

    /**
     * 中文解释
     */
    private String explain;

    /**
     * 刻录选时时长,小时为单位（1-24）
     */
    private Integer burntime;

    /**
     * 是否需要光盘同刻,1需要/0不需要
     */
    private Integer burnbool;

    /**
     * 片头列表,设备刻录的片头,片头的名称，用,分割
     */
    private String ptjson;

    /**
     * 片头显示时间,秒（5-60）
     */
    private Integer ptshowtime;

    /**
     * 是否需要硬盘录像,
     * 如果程序要求录，这里录像与否都会录像，当程序不要求录像，但是这里要录像也会录像1需要/0不需要
     */
    private Integer diskrecbool;

    /**
     * 分盘重复时间
     * 设备录像，视频文件超出录制时长，重新录制一个新文件，2个文件之间的重复的视频的时间s(秒)
     */
    private Integer repeattime;

    private String string1;

    private String string2;

    private Integer integer1;

    private Integer integer2;

    private String ssid;

    public Integer getDefaulturlbool() {
        return defaulturlbool;
    }

    public void setDefaulturlbool(Integer defaulturlbool) {
        this.defaulturlbool = defaulturlbool;
    }

    public Integer getRepeattime() {
        return repeattime;
    }

    public void setRepeattime(Integer repeattime) {
        this.repeattime = repeattime;
    }

    public String getPreviewurl() {
        return previewurl;
    }

    public void setPreviewurl(String previewurl) {
        this.previewurl = previewurl;
    }

    public Integer getBurntime() {
        return burntime;
    }

    public void setBurntime(Integer burntime) {
        this.burntime = burntime;
    }

    public Integer getBurnbool() {
        return burnbool;
    }

    public void setBurnbool(Integer burnbool) {
        this.burnbool = burnbool;
    }

    public String getPtjson() {
        return ptjson;
    }

    public void setPtjson(String ptjson) {
        this.ptjson = ptjson;
    }

    public Integer getPtshowtime() {
        return ptshowtime;
    }

    public void setPtshowtime(Integer ptshowtime) {
        this.ptshowtime = ptshowtime;
    }

    public Integer getDiskrecbool() {
        return diskrecbool;
    }

    public void setDiskrecbool(Integer diskrecbool) {
        this.diskrecbool = diskrecbool;
    }

    public String getUploadbasepath() {
        return uploadbasepath;
    }

    public void setUploadbasepath(String uploadbasepath) {
        this.uploadbasepath = uploadbasepath;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
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
    public String getLivingurl() {
        return livingurl;
    }

    public void setLivingurl(String livingurl) {
        this.livingurl = livingurl;
    }
    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
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
        return "Flushbonading_etinfo{" +
        "id=" + id +
        ", equipmentssid=" + equipmentssid +
        ", livingurl=" + livingurl +
        ", port=" + port +
        ", explain=" + explain +
        ", string1=" + string1 +
        ", string2=" + string2 +
        ", integer1=" + integer1 +
        ", integer2=" + integer2 +
        ", ssid=" + ssid +
        "}";
    }
}
