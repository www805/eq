package com.avst.equipmentcontrol.outside.interfacetoout.base.v1.service;

import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.common.util.baseaction.ReqParam;
import com.avst.equipmentcontrol.outside.interfacetoout.base.req.addOrUpdateToOutEttypeParam;
import com.avst.equipmentcontrol.outside.interfacetoout.base.req.getToOutEttypeListParam;
import com.avst.equipmentcontrol.web.req.baseEttype.AddOrUpEttypeParam;
import com.avst.equipmentcontrol.web.req.baseEttype.BaseEttypeParam;
import com.avst.equipmentcontrol.web.service.BaseEttypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ToOutEttypeServiceImpl implements ToOutEttypeService{

    @Autowired
    private BaseEttypeService baseEttypeService;

    //查询全部
    @Override
    public RResult getToOutEttypeList(getToOutEttypeListParam param, RResult result) {

        ReqParam<BaseEttypeParam> reqParam = new ReqParam<BaseEttypeParam>();

        BaseEttypeParam baseEttypeParam = new BaseEttypeParam();
        reqParam.setParam(baseEttypeParam);

        baseEttypeService.getBaseEttype(result, reqParam);

        return result;
    }

    //单个查询
    @Override
    public RResult getToOutEttypeByTerm(getToOutEttypeListParam param, RResult result) {

        ReqParam<BaseEttypeParam> reqParam = new ReqParam<BaseEttypeParam>();

        BaseEttypeParam baseEttypeParam = new BaseEttypeParam();
        baseEttypeParam.setSsid(param.getEttypessid()); //传递查询的ssid
        reqParam.setParam(baseEttypeParam);

        baseEttypeService.getBaseEttypeById(result, reqParam);

        return result;
    }

    //新增
    @Override
    public RResult addToOutEttype(addOrUpdateToOutEttypeParam param, RResult result) {

        ReqParam<AddOrUpEttypeParam> reqParam = new ReqParam<AddOrUpEttypeParam>();

        AddOrUpEttypeParam addOrUpEttypeParam = new AddOrUpEttypeParam();
        addOrUpEttypeParam.setExplain(param.getExplain());
        addOrUpEttypeParam.setEttypenum(param.getEttypenum());

        reqParam.setParam(addOrUpEttypeParam);

        baseEttypeService.addBaseEttype(result, reqParam);

        return result;
    }

    //修改
    @Override
    public RResult updateToOutEttype(addOrUpdateToOutEttypeParam param, RResult result) {

        ReqParam<AddOrUpEttypeParam> reqParam = new ReqParam<AddOrUpEttypeParam>();

        AddOrUpEttypeParam addOrUpEttypeParam = new AddOrUpEttypeParam();
        addOrUpEttypeParam.setSsid(param.getSsid());
        addOrUpEttypeParam.setExplain(param.getExplain());
        addOrUpEttypeParam.setEttypenum(param.getEttypenum());

        reqParam.setParam(addOrUpEttypeParam);

        baseEttypeService.updateBaseEttype(result, reqParam);

        return result;
    }
}
