package com.avst.equipmentcontrol.web.action;

import com.avst.equipmentcontrol.common.util.DateUtil;
import com.avst.equipmentcontrol.common.util.baseaction.BaseAction;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.common.util.baseaction.ReqParam;
import com.avst.equipmentcontrol.web.req.flushbonadingEttd.FlushbonadingEttdParam;
import com.avst.equipmentcontrol.web.req.flushbonadingEttd.UpdateFlushbonadingEttdParam;
import com.avst.equipmentcontrol.web.service.FlushbonadingEttdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

//设备通道
@RestController
@RequestMapping("/FlushbonadingEttd")
public class FlushbonadingEttdAction extends BaseAction {

    @Autowired
    private FlushbonadingEttdService flushbonadingEttdService;

    //查询
    @RequestMapping(value = "/getFlushbonadingEttdList")
    public RResult getFlushbonadingEttdList(@RequestBody ReqParam<FlushbonadingEttdParam> param){
        RResult result=this.createNewResultOfFail();
        flushbonadingEttdService.getFlushbonadingEttdList(result,param);
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    //查询单个
    @RequestMapping(value = "/getFlushbonadingEttdById")
    public RResult getFlushbonadingEttdById(@RequestBody ReqParam<FlushbonadingEttdParam> param){
        RResult result=this.createNewResultOfFail();
        flushbonadingEttdService.getFlushbonadingEttdById(result,param);
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    //新增
    @RequestMapping(value = "/addFlushbonadingEttd")
    public RResult addFlushbonadingEttd(@RequestBody ReqParam<UpdateFlushbonadingEttdParam> param){
        RResult result=this.createNewResultOfFail();
        flushbonadingEttdService.addFlushbonadingEttd(result,param);
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    //修改
    @RequestMapping(value = "/updateFlushbonadingEttd")
    public RResult updateFlushbonadingEttd(@RequestBody ReqParam<UpdateFlushbonadingEttdParam> param){
        RResult result=this.createNewResultOfFail();
        flushbonadingEttdService.updateFlushbonadingEttd(result,param);
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    //删除
    @RequestMapping(value = "/delFlushbonadingEttd")
    public RResult delFlushbonadingEttd(@RequestBody ReqParam<FlushbonadingEttdParam> param){
        RResult result=this.createNewResultOfFail();
        flushbonadingEttdService.delFlushbonadingEttd(result,param);
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    //显示列表页面
    @RequestMapping(value = "/getFlushbonadingEttdIndex")
    public ModelAndView getFlushbonadingEttdIndex(Model model) {
        model.addAttribute("title", "设备通道列表");
        return new ModelAndView("sweb/flushbonadingEttd/getFlushbonadingEttdIndex", "getFlushbonadingEttdIndexModel", model);
    }

    //显示新增/修改页面
    @RequestMapping(value = "/addOrUpdateFlushbonadingEttd")
    public ModelAndView addOrUpdateFlushbonadingEttd(Model model) {
        model.addAttribute("title", "设备通道 新增/修改");
        return new ModelAndView("sweb/flushbonadingEttd/addOrUpdateFlushbonadingEttd", "addOrUpdateFlushbonadingEttdModel", model);
    }


}
