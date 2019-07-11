package com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.req;

import java.util.List;

public class GetToOutFlushbonadingEttdByListParam extends BaseParam {

    private List<Avstmt_tduserParam> pagelist;//ssid

    public List<Avstmt_tduserParam> getPagelist() {
        return pagelist;
    }

    public void setPagelist(List<Avstmt_tduserParam> pagelist) {
        this.pagelist = pagelist;
    }
}
