package com.avst.equipmentcontrol.outside.interfacetoout.polygraph.v1.service;

import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.outside.interfacetoout.polygraph.req.*;

public interface ToOutService_ph {

    public RResult checkPolygraphState(CheckPolygraphStateParam param,RResult result);

    public RResult startPolygraph(StartPolygraphParam param,RResult result);

    public RResult OverPolygraph(OverPolygraphParam param,RResult result);

    public RResult getPolygraphAnalysis(GetPolygraphAnalysisParam param,RResult result);//获取测谎心里分析数据

    public RResult getPolygraphRealTimeImage(GetPolygraphRealTimeImageParam param,RResult result);//获取身心监护心理分析的实时图像

}
