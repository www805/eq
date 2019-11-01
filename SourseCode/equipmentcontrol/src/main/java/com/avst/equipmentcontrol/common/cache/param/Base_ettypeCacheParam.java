package com.avst.equipmentcontrol.common.cache.param;

public class Base_ettypeCacheParam {

    private Integer id;

    /**
     * 设备类型标号,asrhtml,flushbonadinghtml
     */
    private String ettypenum;

    /**
     * 设备类型中文解释
     */
    private String explain;

    private String url;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
    public String toString() {
        return "Base_ettypeCacheParam{" +
                "id=" + id +
                ", ettypenum='" + ettypenum + '\'' +
                ", explain='" + explain + '\'' +
                ", url='" + url + '\'' +
                ", string1='" + string1 + '\'' +
                ", string2='" + string2 + '\'' +
                ", integer1=" + integer1 +
                ", integer2=" + integer2 +
                ", ssid='" + ssid + '\'' +
                '}';
    }
}
