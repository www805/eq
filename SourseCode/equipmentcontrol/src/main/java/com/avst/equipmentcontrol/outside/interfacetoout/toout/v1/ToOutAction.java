package com.avst.equipmentcontrol.outside.interfacetoout.toout.v1;

import com.avst.equipmentcontrol.common.util.baseaction.BaseAction;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.common.util.baseaction.ReqParam;
import com.avst.equipmentcontrol.outside.interfacetoout.toout.v1.service.ToOutService;
import com.avst.equipmentcontrol.outside.interfacetoout.toout.vo.ToOutVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 对外提供接口
 */
@RestController
@RequestMapping("/ec/v1")
public class ToOutAction  extends BaseAction {
    @Autowired
    private ToOutService toOutService;

    /**
     * 提供给总控的心跳检测
     * @return
     */
    @RequestMapping("/checkClient")
    public RResult checkClient(@RequestBody ReqParam param){
        RResult rresult=createNewResultOfFail();
        rresult=toOutService.checkClient(rresult,param);
        return rresult;
    }


}
