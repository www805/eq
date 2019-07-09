package com.avst.equipmentcontrol.outside.interfacetoout.polygraph.v1.service;

import com.avst.equipmentcontrol.common.datasourse.extrasourse.polygraph.entity.param.PolygraphInfo;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.common.util.baseaction.ReqParam;
import com.avst.equipmentcontrol.outside.interfacetoout.polygraph.req.AddOrUpdateToOutPolygraphParam;
import com.avst.equipmentcontrol.outside.interfacetoout.polygraph.req.GetToOutPolygraphListParam;
import com.avst.equipmentcontrol.web.req.polygraph.PolygraphParam;
import com.avst.equipmentcontrol.web.req.polygraph.UpdatePolygraphParam;
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
    public RResult getToOutPolygraphList(GetToOutPolygraphListParam param, RResult result) {

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
    public RResult getToOutPolygraphById(GetToOutPolygraphListParam param, RResult result) {

        ReqParam<PolygraphParam> reqParam = new ReqParam<PolygraphParam>();

        PolygraphParam polygraphParam = new PolygraphParam();
        polygraphParam.setSsid(param.getSsid());
        reqParam.setParam(polygraphParam);

        polygraphService.getPolygraphById(result, reqParam);

        return result;
    }

    //新增
    @Override
    public RResult addToOutPolygraph(AddOrUpdateToOutPolygraphParam param, RResult result) {

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
    public RResult updateToOutPolygraph(AddOrUpdateToOutPolygraphParam param, RResult result) {

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
