package com.avst.equipmentcontrol.outside.interfacetoout.polygraph.v1.service;

import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.outside.interfacetoout.polygraph.req.addOrUpdateToOutPolygraphParam;
import com.avst.equipmentcontrol.outside.interfacetoout.polygraph.req.getToOutPolygraphListParam;

public interface ToOutPolygraphService {

    public RResult getToOutPolygraphList(getToOutPolygraphListParam param, RResult result);

    public RResult getToOutPolygraphById(getToOutPolygraphListParam param, RResult result);

    public RResult addToOutPolygraph(addOrUpdateToOutPolygraphParam param, RResult result);

    public RResult updateToOutPolygraph(addOrUpdateToOutPolygraphParam param, RResult result);

}
