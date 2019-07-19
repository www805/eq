package com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.req;

public class DvdOutOrInParam_out extends BaseParam {

    private int dx=0;//现阶段这个参数只能是0，不区分单独的光驱

    public int getDx() {
        return dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }
}
