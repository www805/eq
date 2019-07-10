package com.avst.equipmentcontrol.outside.interfacetoout.base.v1;

import com.avst.equipmentcontrol.common.util.DateUtil;
import com.avst.equipmentcontrol.common.util.baseaction.BaseAction;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.common.util.baseaction.ReqParam;
import com.avst.equipmentcontrol.outside.interfacetoout.base.v1.service.ToOutEttypeServiceImpl;
import com.avst.equipmentcontrol.outside.interfacetoout.base.v1.service.ToOutMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/base/v1/main")
public class ToOutMainAction extends BaseAction {
    @Autowired
    private ToOutMainService toOutMainService;

    @RequestMapping("/gethome")
    @ResponseBody
    public RResult gethome(@RequestBody ReqParam param){
        RResult result=this.createNewResultOfFail();
        toOutMainService.gethome(param,result);
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    };
}
