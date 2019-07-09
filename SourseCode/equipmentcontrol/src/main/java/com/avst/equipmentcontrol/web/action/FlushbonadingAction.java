package com.avst.equipmentcontrol.web.action;

import com.avst.equipmentcontrol.common.datasourse.extrasourse.flushbonading.entity.param.Flushbonadinginfo;
import com.avst.equipmentcontrol.common.util.DateUtil;
import com.avst.equipmentcontrol.common.util.baseaction.BaseAction;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.common.util.baseaction.ReqParam;
import com.avst.equipmentcontrol.web.req.flushbonading.FlushbonadinginfoParam;
import com.avst.equipmentcontrol.web.service.FlushbonadingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

//审讯设备
@RestController
@RequestMapping("/Flushbonading")
public class FlushbonadingAction extends BaseAction {

    @Autowired
    private FlushbonadingService flushbonadingService;

    //查询
    @RequestMapping(value = "/getFlushbonadingList")
    public RResult getFlushbonadingList(@RequestBody ReqParam<FlushbonadinginfoParam> param){
        RResult result=this.createNewResultOfFail();
        flushbonadingService.getFlushbonadingList(result,param);
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    //查询单个
    @RequestMapping(value = "/getFlushbonadingById")
    public RResult getFlushbonadingById(@RequestBody ReqParam<FlushbonadinginfoParam> param){
        RResult result=this.createNewResultOfFail();
        flushbonadingService.getFlushbonadingById(result,param);
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    //新增
    @RequestMapping(value = "/addFlushbonading")
    public RResult addFlushbonading(@RequestBody ReqParam<Flushbonadinginfo> param){
        RResult result=this.createNewResultOfFail();
        flushbonadingService.addFlushbonading(result,param);
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    //修改
    @RequestMapping(value = "/updateFlushbonading")
    public RResult updateFlushbonading(@RequestBody ReqParam<Flushbonadinginfo> param){
        RResult result=this.createNewResultOfFail();
        flushbonadingService.updateFlushbonading(result,param);
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    //删除
    @RequestMapping(value = "/delFlushbonading")
    public RResult delFlushbonading(@RequestBody ReqParam<FlushbonadinginfoParam> param){
        RResult result=this.createNewResultOfFail();
        flushbonadingService.delFlushbonading(result,param);
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    //查询设备类型/基础设备
    @RequestMapping(value = "/getBaseEttype")
    public RResult getBaseEttype(){
        RResult result=this.createNewResultOfFail();
        flushbonadingService.getBaseEttype(result);
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    //显示列表页面
    @RequestMapping(value = "/getFlushbonadingIndex")
    public ModelAndView getFlushbonadingIndex(Model model) {
        model.addAttribute("title", "审讯设备列表");
        return new ModelAndView("sweb/flushbonadinghtml/getFlushbonadingIndex", "getFlushbonadingIndexModel", model);
    }

    //显示增改页面
    @RequestMapping(value = "/addOrUpdateFlushbonading")
    public ModelAndView addOrUpdateFlushbonading(Model model) {
        model.addAttribute("title", "审讯设备新增/修改");
        return new ModelAndView("sweb/flushbonadinghtml/addOrUpdateFlushbonading", "addOrUpdateFlushbonadingModel", model);
    }



}
