package com.avst.equipmentcontrol.web.req.flushbonadingEttd;

import com.avst.equipmentcontrol.common.datasourse.extrasourse.flushbonading.entity.FlushbonadingEttd;

public class UpdateFlushbonadingEttdParam extends FlushbonadingEttd {

    private String masterSsid;

    public String getMasterSsid() {
        return masterSsid;
    }

    public void setMasterSsid(String masterSsid) {
        this.masterSsid = masterSsid;
    }
}
