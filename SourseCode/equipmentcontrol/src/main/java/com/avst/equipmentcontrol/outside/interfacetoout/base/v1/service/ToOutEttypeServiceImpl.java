package com.avst.equipmentcontrol.outside.interfacetoout.base.v1.service;

import com.avst.equipmentcontrol.common.datasourse.publicsourse.entity.Base_ettype;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.common.util.baseaction.ReqParam;
import com.avst.equipmentcontrol.outside.interfacetoout.base.req.AddOrUpdateToOutEttypeParam;
import com.avst.equipmentcontrol.outside.interfacetoout.base.req.GetToOutEttypeListParam;
import com.avst.equipmentcontrol.web.req.baseEttype.AddOrUpEttypeParam;
import com.avst.equipmentcontrol.web.req.baseEttype.BaseEttypeParam;
import com.avst.equipmentcontrol.web.service.BaseEttypeService;
import com.avst.equipmentcontrol.web.vo.baseEttype.BaseEttypeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToOutEttypeServiceImpl implements ToOutEttypeService{

    @Autowired
    private BaseEttypeService baseEttypeService;

    //查询全部
    @Override
    public RResult getToOutEttypeList(GetToOutEttypeListParam param, RResult result) {

        ReqParam<BaseEttypeParam> reqParam = new ReqParam<BaseEttypeParam>();

        BaseEttypeParam baseEttypeParam = new BaseEttypeParam();
        reqParam.setParam(baseEttypeParam);

        baseEttypeService.getBaseEttype(result, reqParam);

        if(!"FAIL".equals(result.getActioncode())){
            BaseEttypeVO data = (BaseEttypeVO) result.getData();
            List<Base_ettype> list = data.getPagelist();
            result.setData(list);
        }

        return result;
    }

    //单个查询
    @Override
    public RResult getToOutEttypeById(GetToOutEttypeListParam param, RResult result) {

        ReqParam<BaseEttypeParam> reqParam = new ReqParam<BaseEttypeParam>();

        BaseEttypeParam baseEttypeParam = new BaseEttypeParam();
        baseEttypeParam.setSsid(param.getEttypessid()); //传递查询的ssid
        reqParam.setParam(baseEttypeParam);

        baseEttypeService.getBaseEttypeById(result, reqParam);

        return result;
    }

    //新增
    @Override
    public RResult addToOutEttype(AddOrUpdateToOutEttypeParam param, RResult result) {

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
    public RResult updateToOutEttype(AddOrUpdateToOutEttypeParam param, RResult result) {

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
