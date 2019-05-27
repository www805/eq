package com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.req;

public class StartRecParam extends BaseParam {

    /*
    0:如果硬盘当前在录像就不进行重新开始录像
    1:不管硬盘当前是否在录像始终开始录像.如果当前在录像该命令主动结束上一次录像重新开始硬盘录像
     */
    private String al;//

    private String iid;//录音跟案件关联的唯一标识

    public String getAl() {
        return al;
    }

    public void setAl(String al) {
        this.al = al;
    }

    public String getIid() {
        return iid;
    }

    public void setIid(String iid) {
        this.iid = iid;
    }
}
