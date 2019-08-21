package com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.vo;

import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.xmljsonobject.param.Disc_iid;

import java.util.List;

public class GetCDNumberVO {

    public List<Disc_iid> getCdNumList() {
        return cdNumList;
    }

    public void setCdNumList(List<Disc_iid> cdNumList) {
        this.cdNumList = cdNumList;
    }

    List<Disc_iid> cdNumList;//CD光盘序号列表
}
