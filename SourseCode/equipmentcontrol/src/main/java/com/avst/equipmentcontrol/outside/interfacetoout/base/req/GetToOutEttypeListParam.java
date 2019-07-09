package com.avst.equipmentcontrol.outside.interfacetoout.base.req;

import com.avst.equipmentcontrol.outside.interfacetoout.polygraph.req.BaseParam;

public class GetToOutEttypeListParam {

    private String type;//测谎仪请求类型 avst PH_AVST

    private String ettypessid;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEttypessid() {
        return ettypessid;
    }

    public void setEttypessid(String ettypessid) {
        this.ettypessid = ettypessid;
    }
}
