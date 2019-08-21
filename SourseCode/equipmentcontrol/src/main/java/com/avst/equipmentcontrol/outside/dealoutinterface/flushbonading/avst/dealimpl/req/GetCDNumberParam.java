package com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.req;

public class GetCDNumberParam extends BaseParam{


    /**
     * dx = 0 是指自动判断所有光驱，读取光盘序号
     * 1 是指 指定读取光驱1的光盘序号
     * 2 是指 指定读取光驱2的光盘序号
     */
    private int dx=0;

    public int getDx() {
        return dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }
}
