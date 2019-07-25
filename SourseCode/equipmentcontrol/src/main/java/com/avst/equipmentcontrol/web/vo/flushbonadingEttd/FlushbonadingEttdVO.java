package com.avst.equipmentcontrol.web.vo.flushbonadingEttd;

import com.avst.equipmentcontrol.web.req.flushbonadingEttd.FlushbonadingEttd;
import com.avst.equipmentcontrol.web.req.flushbonadingEttd.FlushbonadingEttdParam;

import java.util.List;

public class FlushbonadingEttdVO{

    private List<FlushbonadingEttd> pagelist;
    private FlushbonadingEttdParam pageparam;

    public List<FlushbonadingEttd> getPagelist() {
        return pagelist;
    }

    public void setPagelist(List<FlushbonadingEttd> pagelist) {
        this.pagelist = pagelist;
    }

    public FlushbonadingEttdParam getPageparam() {
        return pageparam;
    }

    public void setPageparam(FlushbonadingEttdParam pageparam) {
        this.pageparam = pageparam;
    }
}
