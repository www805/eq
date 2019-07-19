package com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.vo;

import java.util.List;

/**
 * 这里用list返回给上级系统，系统里面用list，保持它的顺序就好
 */
public class GetptdjconstVO {

    List<String> lineList;

    public List<String> getLineList() {
        return lineList;
    }

    public void setLineList(List<String> lineList) {
        this.lineList = lineList;
    }
}
