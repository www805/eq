package com.avst.equipmentcontrol.web.vo.baseEquipmentinfo;

import com.avst.equipmentcontrol.common.datasourse.publicsourse.entity.Base_ettype;
import com.avst.equipmentcontrol.web.req.baseEquipmentinfo.BaseEquipmentinfoParam;
import com.avst.equipmentcontrol.web.vo.baseEttype.EquipmentBasicsVO;

import java.util.List;

public class BaseEquipmentinfoVO {

    private List<EquipmentBasicsVO> pagelist;
    private BaseEquipmentinfoParam pageparam;
    private List<Base_ettype> EttypeList;

    public List<Base_ettype> getEttypeList() {
        return EttypeList;
    }

    public void setEttypeList(List<Base_ettype> ettypeList) {
        EttypeList = ettypeList;
    }

    public List<EquipmentBasicsVO> getPagelist() {
        return pagelist;
    }

    public void setPagelist(List<EquipmentBasicsVO> pagelist) {
        this.pagelist = pagelist;
    }

    public BaseEquipmentinfoParam getPageparam() {
        return pageparam;
    }

    public void setPageparam(BaseEquipmentinfoParam pageparam) {
        this.pageparam = pageparam;
    }
}
