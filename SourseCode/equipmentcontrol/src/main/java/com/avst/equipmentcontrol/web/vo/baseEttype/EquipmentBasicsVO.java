package com.avst.equipmentcontrol.web.vo.baseEttype;

import com.avst.equipmentcontrol.common.datasourse.publicsourse.entity.Base_equipmentinfo;

public class EquipmentBasicsVO extends Base_equipmentinfo {

    private String typename;

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }
}
