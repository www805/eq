package com.avst.equipmentcontrol.web.vo.baseEttype;

import com.avst.equipmentcontrol.common.datasourse.publicsourse.entity.Base_ettype;
import com.avst.equipmentcontrol.web.req.baseEttype.BaseEttypeParam;

import java.util.List;

public class BaseEttypeVO {

    private List<Base_ettype> pagelist;
    private BaseEttypeParam pageparam;

    public List<Base_ettype> getPagelist() {
        return pagelist;
    }

    public void setPagelist(List<Base_ettype> pagelist) {
        this.pagelist = pagelist;
    }

    public BaseEttypeParam getPageparam() {
        return pageparam;
    }

    public void setPageparam(BaseEttypeParam pageparam) {
        this.pageparam = pageparam;
    }
}
