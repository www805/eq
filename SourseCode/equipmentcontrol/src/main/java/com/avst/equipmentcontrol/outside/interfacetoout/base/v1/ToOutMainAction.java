package com.avst.equipmentcontrol.outside.interfacetoout.base.v1;

import com.avst.equipmentcontrol.common.util.DateUtil;
import com.avst.equipmentcontrol.common.util.baseaction.BaseAction;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.common.util.baseaction.ReqParam;
import com.avst.equipmentcontrol.outside.interfacetoout.base.req.GetServerIpALLParam;
import com.avst.equipmentcontrol.outside.interfacetoout.base.req.GetServerIpParam;
import com.avst.equipmentcontrol.outside.interfacetoout.base.v1.service.ToOutMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 获取其他全部设备IP
     * @param param
     * @return
     */
    @RequestMapping("/getServerIpALL")
    @ResponseBody
    public RResult getServerIpALL(@RequestBody ReqParam<GetServerIpALLParam> param){
        RResult result=this.createNewResultOfFail();
        toOutMainService.getServerIpALL(param,result);
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    };

    /**
     * 修改配置
     * @return
     */
    @PostMapping(value = "/updateServerIp")
    @ResponseBody
    public RResult updateServerIp(@RequestBody GetServerIpParam getServerIpParam) {
        RResult result=this.createNewResultOfFail();
        toOutMainService.updateServerIp(getServerIpParam,result);
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

}
