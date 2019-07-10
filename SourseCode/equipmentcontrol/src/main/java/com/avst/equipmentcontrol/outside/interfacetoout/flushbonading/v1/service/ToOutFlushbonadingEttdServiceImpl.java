package com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.v1.service;

import com.avst.equipmentcontrol.common.datasourse.extrasourse.flushbonading.entity.FlushbonadingEttd;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.common.util.baseaction.ReqParam;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.req.AddOrUpdateToOutFlushbonadingEttdParam;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.req.GetToOutFlushbonadingEttdListParam;
import com.avst.equipmentcontrol.web.req.flushbonadingEttd.FlushbonadingEttdParam;
import com.avst.equipmentcontrol.web.req.flushbonadingEttd.UpdateFlushbonadingEttdParam;
import com.avst.equipmentcontrol.web.service.FlushbonadingEttdService;
import com.avst.equipmentcontrol.web.vo.flushbonadingEttd.FlushbonadingEttdVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToOutFlushbonadingEttdServiceImpl implements ToOutFlushbonadingEttdService {

    @Autowired
    private FlushbonadingEttdService flushbonadingEttdService;

    //查询列表
    @Override
    public RResult getToOutFlushbonadingEttdList(GetToOutFlushbonadingEttdListParam param, RResult result) {

        ReqParam<FlushbonadingEttdParam> reqParam = new ReqParam<FlushbonadingEttdParam>();

        FlushbonadingEttdParam flushbonadingEttdParam = new FlushbonadingEttdParam();
        flushbonadingEttdParam.setSsid(param.getSsid());
        reqParam.setParam(flushbonadingEttdParam);

        flushbonadingEttdService.getFlushbonadingEttdList(result, reqParam);
        if(!"FAIL".equals(result.getActioncode())){
            FlushbonadingEttdVO data = (FlushbonadingEttdVO) result.getData();
            List<FlushbonadingEttd> list = data.getPagelist();
            result.setData(list);
        }
        return result;
    }

    //查询单个
    @Override
    public RResult getToOutFlushbonadingEttdById(GetToOutFlushbonadingEttdListParam param, RResult result) {

        ReqParam<FlushbonadingEttdParam> reqParam = new ReqParam<FlushbonadingEttdParam>();

        FlushbonadingEttdParam flushbonadingEttdParam = new FlushbonadingEttdParam();
        flushbonadingEttdParam.setSsid(param.getSsid());
        reqParam.setParam(flushbonadingEttdParam);

        flushbonadingEttdService.getFlushbonadingEttdById(result, reqParam);

        return result;
    }

    //新增
    @Override
    public RResult addToOutFlushbonadingEttd(AddOrUpdateToOutFlushbonadingEttdParam param, RResult result) {

        ReqParam<UpdateFlushbonadingEttdParam> reqParam = new ReqParam<UpdateFlushbonadingEttdParam>();

        UpdateFlushbonadingEttdParam flushbonadingEttdParam = new UpdateFlushbonadingEttdParam();
        flushbonadingEttdParam.setFlushbonadingssid(param.getFlushbonadingssid());
        flushbonadingEttdParam.setTdnum(param.getTdnum());
        flushbonadingEttdParam.setPullflowurl(param.getPullflowurl());
        flushbonadingEttdParam.setTdtype(param.getTdtype());
        reqParam.setParam(flushbonadingEttdParam);

        flushbonadingEttdService.addFlushbonadingEttd(result, reqParam);

        return result;
    }

    //修改
    @Override
    public RResult updateToOutFlushbonadingEttd(AddOrUpdateToOutFlushbonadingEttdParam param, RResult result) {

        ReqParam<UpdateFlushbonadingEttdParam> reqParam = new ReqParam<UpdateFlushbonadingEttdParam>();

        UpdateFlushbonadingEttdParam flushbonadingEttdParam = new UpdateFlushbonadingEttdParam();
        flushbonadingEttdParam.setSsid(param.getSsid());
        flushbonadingEttdParam.setFlushbonadingssid(param.getFlushbonadingssid());
        flushbonadingEttdParam.setTdnum(param.getTdnum());
        flushbonadingEttdParam.setPullflowurl(param.getPullflowurl());
        flushbonadingEttdParam.setTdtype(param.getTdtype());
        reqParam.setParam(flushbonadingEttdParam);

        flushbonadingEttdService.updateFlushbonadingEttd(result, reqParam);

        return result;
    }


}
