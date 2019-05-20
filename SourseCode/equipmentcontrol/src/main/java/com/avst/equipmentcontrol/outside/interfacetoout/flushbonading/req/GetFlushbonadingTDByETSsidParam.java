package com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.req;

public class GetFlushbonadingTDByETSsidParam {

    private String flushbonadingEquipmentSsid;//嵌入式审讯设备ssid（不是基本设备ssid）

    private int tdtype=0;//0全部通道/1音频通道/2视频通道

    public String getFlushbonadingEquipmentSsid() {
        return flushbonadingEquipmentSsid;
    }

    public void setFlushbonadingEquipmentSsid(String flushbonadingEquipmentSsid) {
        this.flushbonadingEquipmentSsid = flushbonadingEquipmentSsid;
    }

    public int getTdtype() {
        return tdtype;
    }

    public void setTdtype(int tdtype) {
        this.tdtype = tdtype;
    }
}
