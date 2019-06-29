package com.avst.equipmentcontrol.web.vo.flushbonading;

import com.avst.equipmentcontrol.common.datasourse.publicsourse.entity.Base_equipmentinfo;
import com.avst.equipmentcontrol.common.datasourse.publicsourse.entity.Base_ettype;
import com.avst.equipmentcontrol.web.vo.baseEttype.EquipmentBasicsVO;

import java.util.List;

public class BaseEquipmentinfoOrEttypeVO {

    private List<Base_ettype> ettypeList;
    private List<Base_equipmentinfo> equipmentinfoList;
    private List<EquipmentBasicsVO> equipmentBasicsAll;

    public List<EquipmentBasicsVO> getEquipmentBasicsAll() {
        return equipmentBasicsAll;
    }

    public void setEquipmentBasicsAll(List<EquipmentBasicsVO> equipmentBasicsAll) {
        this.equipmentBasicsAll = equipmentBasicsAll;
    }

    public List<Base_ettype> getEttypeList() {
        return ettypeList;
    }

    public void setEttypeList(List<Base_ettype> ettypeList) {
        this.ettypeList = ettypeList;
    }

    public List<Base_equipmentinfo> getEquipmentinfoList() {
        return equipmentinfoList;
    }

    public void setEquipmentinfoList(List<Base_equipmentinfo> equipmentinfoList) {
        this.equipmentinfoList = equipmentinfoList;
    }
}
