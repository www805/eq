package com.avst.equipmentcontrol.web.vo.polygraph;

import com.avst.equipmentcontrol.common.datasourse.extrasourse.polygraph.entity.param.PolygraphInfo;
import com.avst.equipmentcontrol.web.req.polygraph.PolygraphParam;

import java.util.List;

public class PolygraphVO {

    private List<PolygraphInfo> pagelist;
    private PolygraphParam pageparam;

    public List<PolygraphInfo> getPagelist() {
        return pagelist;
    }

    public void setPagelist(List<PolygraphInfo> pagelist) {
        this.pagelist = pagelist;
    }

    public PolygraphParam getPageparam() {
        return pageparam;
    }

    public void setPageparam(PolygraphParam pageparam) {
        this.pageparam = pageparam;
    }
}
