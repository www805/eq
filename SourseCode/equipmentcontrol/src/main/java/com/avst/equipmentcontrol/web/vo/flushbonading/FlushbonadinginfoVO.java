package com.avst.equipmentcontrol.web.vo.flushbonading;

import com.avst.equipmentcontrol.common.datasourse.extrasourse.flushbonading.entity.param.Flushbonadinginfo;
import com.avst.equipmentcontrol.common.datasourse.publicsourse.entity.Base_ettype;
import com.avst.equipmentcontrol.web.req.flushbonading.FlushbonadinginfoParam;

import java.util.List;

public class FlushbonadinginfoVO {

    private List<Flushbonadinginfo> pagelist;
    private FlushbonadinginfoParam pageparam;

    public List<Flushbonadinginfo> getPagelist() {
        return pagelist;
    }

    public void setPagelist(List<Flushbonadinginfo> pagelist) {
        this.pagelist = pagelist;
    }

    public FlushbonadinginfoParam getPageparam() {
        return pageparam;
    }

    public void setPageparam(FlushbonadinginfoParam pageparam) {
        this.pageparam = pageparam;
    }
}
