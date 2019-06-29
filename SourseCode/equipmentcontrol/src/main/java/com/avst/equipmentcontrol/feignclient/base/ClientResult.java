package com.avst.equipmentcontrol.feignclient.base;


import com.avst.equipmentcontrol.common.util.baseaction.BaseAction;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.feignclient.zk.ZkControl;
import org.springframework.stereotype.Component;

//@Component
public class ClientResult extends BaseAction implements ZkControl {

    @Override
    public RResult getControlTime() {
        RResult rresult=createNewResultOfFail();
        return rresult;
    }
}
