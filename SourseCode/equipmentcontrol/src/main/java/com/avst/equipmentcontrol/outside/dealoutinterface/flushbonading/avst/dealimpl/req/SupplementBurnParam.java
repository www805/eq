package com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.req;

public class SupplementBurnParam extends BaseParam {

    private int burn_v;//是否需要刻录视频

    private int burn_bl;//是否需要刻录笔录信息

    private int burn_payer;//是否需要刻录播放器

    public int getBurn_v() {
        return burn_v;
    }

    public void setBurn_v(int burn_v) {
        this.burn_v = burn_v;
    }

    public int getBurn_bl() {
        return burn_bl;
    }

    public void setBurn_bl(int burn_bl) {
        this.burn_bl = burn_bl;
    }

    public int getBurn_payer() {
        return burn_payer;
    }

    public void setBurn_payer(int burn_payer) {
        this.burn_payer = burn_payer;
    }
}
