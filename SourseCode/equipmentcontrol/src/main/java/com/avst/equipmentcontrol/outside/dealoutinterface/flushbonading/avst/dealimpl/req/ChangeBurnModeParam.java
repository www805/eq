package com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.req;

public class ChangeBurnModeParam extends BaseParam {

    private int dx;//0：同步刻录 2：接力刻录

    public int getDx() {
        return dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }
}
