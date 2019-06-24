package com.avst.equipmentcontrol.outside.interfacetoout.toout.v1.service;


import com.avst.equipmentcontrol.common.util.baseaction.BaseService;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.common.util.baseaction.ReqParam;
import com.avst.equipmentcontrol.outside.interfacetoout.toout.vo.ToOutVO;
import org.springframework.stereotype.Service;

@Service
public class ToOutService extends BaseService {

    public RResult checkClient(RResult rresult, ReqParam<ToOutVO> param){
        ToOutVO toOutVO=new ToOutVO();
        toOutVO.setTotal_item(4);
        toOutVO.setUse_item(4);
        rresult.setData(toOutVO);
        changeResultToSuccess(rresult);
        return  rresult;
    }
}
