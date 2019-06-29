package com.avst.equipmentcontrol.web.action;

import com.avst.equipmentcontrol.common.datasourse.extrasourse.flushbonading.entity.param.Flushbonadinginfo;
import com.avst.equipmentcontrol.common.datasourse.publicsourse.entity.Base_equipmentinfo;
import com.avst.equipmentcontrol.common.util.DateUtil;
import com.avst.equipmentcontrol.common.util.baseaction.BaseAction;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.common.util.baseaction.ReqParam;
import com.avst.equipmentcontrol.web.req.baseEquipmentinfo.AddOrUpBaseEquipmentinfoParam;
import com.avst.equipmentcontrol.web.req.baseEquipmentinfo.BaseEquipmentinfoParam;
import com.avst.equipmentcontrol.web.req.flushbonading.FlushbonadinginfoParam;
import com.avst.equipmentcontrol.web.service.BaseEquipmentinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

//设备基础
@RestController
@RequestMapping("/BaseEquipmentinfo")
public class BaseEquipmentinfoAction extends BaseAction {

    @Autowired
    private BaseEquipmentinfoService baseEquipmentinfoService;

    //查询
    @RequestMapping(value = "/getEquipmentBasics")
    public RResult getEquipmentBasics(@RequestBody ReqParam<BaseEquipmentinfoParam> param){
        RResult result=this.createNewResultOfFail();
        baseEquipmentinfoService.getEquipmentBasics(result,param);
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    //查询单个
    @RequestMapping(value = "/getEquipmentBasicsById")
    public RResult getEquipmentBasicsById(@RequestBody ReqParam<BaseEquipmentinfoParam> param){
        RResult result=this.createNewResultOfFail();
        baseEquipmentinfoService.getEquipmentBasicsById(result,param);
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    //新增
    @RequestMapping(value = "/addEquipmentBasics")
    public RResult addEquipmentBasics(@RequestBody ReqParam<AddOrUpBaseEquipmentinfoParam> param){
        RResult result=this.createNewResultOfFail();
        baseEquipmentinfoService.addEquipmentBasics(result,param);
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    //修改
    @RequestMapping(value = "/updateEquipmentBasics")
    public RResult updateEquipmentBasics(@RequestBody ReqParam<AddOrUpBaseEquipmentinfoParam> param){
        RResult result=this.createNewResultOfFail();
        baseEquipmentinfoService.updateEquipmentBasics(result,param);
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    //删除
    @RequestMapping(value = "/delEquipmentBasics")
    public RResult delEquipmentBasics(@RequestBody ReqParam<BaseEquipmentinfoParam> param){
        RResult result=this.createNewResultOfFail();
        baseEquipmentinfoService.delEquipmentBasics(result,param);
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    //显示列表页面
    @RequestMapping(value = "/getBaseEquipmentinfoList")
    public ModelAndView getBaseEquipmentinfoList(Model model) {
        model.addAttribute("title", "设备基础");
        return new ModelAndView("sweb/baseEquipmentinfo/getBaseEquipmentinfoList", "getBaseEquipmentinfoListModel", model);
    }



}
