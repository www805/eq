package com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.req;

public class StopRec_RomParam extends BaseParam{

    private int dx;//dx 取值定义0：全部光驱 1：光驱一 2：光驱二

    private int disconly=1;//==1的时候只需要结束光驱刻录，不用关联停止硬盘

    public int getDisconly() {
        return disconly;
    }

    public void setDisconly(int disconly) {
        this.disconly = disconly;
    }

    public int getDx() {
        return dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }
}
