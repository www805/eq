package com.avst.equipmentcontrol.outside.interfacetoout.polygraph.v1.service;

import com.avst.equipmentcontrol.common.datasourse.extrasourse.flushbonading.entity.param.Flushbonadinginfo;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.polygraph.entity.param.PolygraphInfo;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.common.util.baseaction.ReqParam;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.req.addOrUpdateToOutFlushbonadingParam;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.req.getToOutFlushbonadingListParam;
import com.avst.equipmentcontrol.outside.interfacetoout.polygraph.req.addOrUpdateToOutPolygraphParam;
import com.avst.equipmentcontrol.outside.interfacetoout.polygraph.req.getToOutPolygraphListParam;
import com.avst.equipmentcontrol.outside.interfacetoout.polygraph.v1.service.ToOutPolygraphService;
import com.avst.equipmentcontrol.web.req.flushbonading.FlushbonadinginfoParam;
import com.avst.equipmentcontrol.web.req.polygraph.PolygraphParam;
import com.avst.equipmentcontrol.web.req.polygraph.UpdatePolygraphParam;
import com.avst.equipmentcontrol.web.service.FlushbonadingService;
import com.avst.equipmentcontrol.web.service.PolygraphService;
import com.avst.equipmentcontrol.web.vo.polygraph.PolygraphVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToOutPolygraphServiceImpl implements ToOutPolygraphService {

    @Autowired
    private PolygraphService polygraphService;

    //查询列表
    @Override
    public RResult getToOutPolygraphList(getToOutPolygraphListParam param, RResult result) {

        ReqParam<PolygraphParam> reqParam = new ReqParam<PolygraphParam>();

        PolygraphParam polygraphParam = new PolygraphParam();
        reqParam.setParam(polygraphParam);

        polygraphService.getPolygraphList(result, reqParam);

        if(!"FAIL".equals(result.getActioncode())){
            PolygraphVO data = (PolygraphVO) result.getData();
            List<PolygraphInfo> list = data.getPagelist();
            result.setData(list);
        }

        return result;
    }

    //查询单个
    @Override
    public RResult getToOutPolygraphById(getToOutPolygraphListParam param, RResult result) {

        ReqParam<PolygraphParam> reqParam = new ReqParam<PolygraphParam>();

        PolygraphParam polygraphParam = new PolygraphParam();
        polygraphParam.setSsid(param.getSsid());
        reqParam.setParam(polygraphParam);

        polygraphService.getPolygraphById(result, reqParam);

        return result;
    }

    //新增
    @Override
    public RResult addToOutPolygraph(addOrUpdateToOutPolygraphParam param, RResult result) {

        ReqParam<UpdatePolygraphParam> reqParam = new ReqParam<UpdatePolygraphParam>();

        UpdatePolygraphParam polygraphParam = new UpdatePolygraphParam();
        polygraphParam.setPort(param.getPort());
        polygraphParam.setPolygraphtype(param.getPolygraphtype());
        polygraphParam.setPolygraphkey(param.getPolygraphkey());
        polygraphParam.setEtnum(param.getEtnum());
        polygraphParam.setEtip(param.getEtip());
        polygraphParam.setEtypessid(param.getEtypessid());
        polygraphParam.setExplain(param.getExplain());
        reqParam.setParam(polygraphParam);

        polygraphService.addPolygraph(result, reqParam);

        return result;
    }

    //修改
    @Override
    public RResult updateToOutPolygraph(addOrUpdateToOutPolygraphParam param, RResult result) {

        ReqParam<UpdatePolygraphParam> reqParam = new ReqParam<UpdatePolygraphParam>();

        UpdatePolygraphParam polygraphParam = new UpdatePolygraphParam();
        polygraphParam.setSsid(param.getSsid());
        polygraphParam.setPort(param.getPort());
        polygraphParam.setPolygraphtype(param.getPolygraphtype());
        polygraphParam.setPolygraphkey(param.getPolygraphkey());
        polygraphParam.setEtnum(param.getEtnum());
        polygraphParam.setEtip(param.getEtip());
        polygraphParam.setEtypessid(param.getEtypessid());
        polygraphParam.setExplain(param.getExplain());
        reqParam.setParam(polygraphParam);

        polygraphService.updatePolygraph(result, reqParam);

        return result;
    }
}
