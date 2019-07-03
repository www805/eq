package com.avst.equipmentcontrol.feignclient.base;


import com.avst.equipmentcontrol.common.util.baseaction.BaseAction;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.common.util.baseaction.ReqParam;
import com.avst.equipmentcontrol.feignclient.base.req.ControlInfoParam;
import com.avst.equipmentcontrol.feignclient.base.vo.ControlInfoParamVO;
import com.avst.equipmentcontrol.feignclient.zk.ZkControl;
import org.springframework.stereotype.Component;

//@Component
public class ClientResult extends BaseAction implements ZkControl {

    @Override
    public RResult getControlInfoAll() {
        RResult rresult=createNewResultOfFail();
        return null;
    }

    @Override
    public RResult getControlTime() {
        RResult rresult=createNewResultOfFail();
        return rresult;
    }

    @Override
    public RResult getHeartbeat(ReqParam<ControlInfoParamVO> param) {
        RResult rresult=createNewResultOfFail();
        return null;
    }


}
