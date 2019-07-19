package com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.req;

public class YuntaiControlParam extends BaseParam{

    private String ptzaction;//up、down、left、right、focus_increase(聚焦+）、 focus_decrease(聚焦-)、depth_far（倍变+）、depth_near（倍 变-）、stop

    private int ptzch;//取值范围：0-7 云台通道，根据所选云台通道配置的参数执 行对应的 action 动作

    public String getPtzaction() {
        return ptzaction;
    }

    public void setPtzaction(String ptzaction) {
        this.ptzaction = ptzaction;
    }

    public int getPtzch() {
        return ptzch;
    }

    public void setPtzch(int ptzch) {
        this.ptzch = ptzch;
    }
}
