package com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.vo;

import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.vo.Param.CoordinateParam;

import java.util.Map;

public class GetFDOSDVO {

    private Map<String, CoordinateParam> osdMap;//坐标集合

    public Map<String, CoordinateParam> getOsdMap() {
        return osdMap;
    }

    public void setOsdMap(Map<String, CoordinateParam> osdMap) {
        this.osdMap = osdMap;
    }
}
