package com.avst.equipmentcontrol.outside.interfacetoout.polygraph.v1.service;

import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.outside.interfacetoout.polygraph.req.AddOrUpdateToOutPolygraphParam;
import com.avst.equipmentcontrol.outside.interfacetoout.polygraph.req.GetToOutPolygraphListParam;

public interface ToOutPolygraphService {

    public RResult getToOutPolygraphList(GetToOutPolygraphListParam param, RResult result);

    public RResult getToOutPolygraphById(GetToOutPolygraphListParam param, RResult result);

    public RResult addToOutPolygraph(AddOrUpdateToOutPolygraphParam param, RResult result);

    public RResult updateToOutPolygraph(AddOrUpdateToOutPolygraphParam param, RResult result);

}
