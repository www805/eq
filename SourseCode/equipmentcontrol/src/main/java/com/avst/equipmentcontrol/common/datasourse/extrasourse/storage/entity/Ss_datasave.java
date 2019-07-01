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
public class Ss_datasave extends Model<Ss_datasave> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String saveinfossid;

    private String datasavebasepath;

    private String iid;

    private String string1;

    private String string2;

    private Integer integer1;

    private Integer integer2;

    private String ssid;

    private Date createtime;

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getSaveinfossid() {
        return saveinfossid;
    }

    public void setSaveinfossid(String saveinfossid) {
        this.saveinfossid = saveinfossid;
    }
    public String getDatasavebasepath() {
        return datasavebasepath;
    }

    public void setDatasavebasepath(String datasavebasepath) {
        this.datasavebasepath = datasavebasepath;
    }
    public String getIid() {
        return iid;
    }

    public void setIid(String iid) {
        this.iid = iid;
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
        return "Ss_datasave{" +
        "id=" + id +
        ", saveinfossid=" + saveinfossid +
        ", datasavebasepath=" + datasavebasepath +
        ", iid=" + iid +
        ", string1=" + string1 +
        ", string2=" + string2 +
        ", integer1=" + integer1 +
        ", integer2=" + integer2 +
        ", ssid=" + ssid +
        "}";
    }
}
