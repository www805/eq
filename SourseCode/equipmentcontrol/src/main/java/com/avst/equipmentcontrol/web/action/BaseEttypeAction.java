package com.avst.equipmentcontrol.web.action;

import com.avst.equipmentcontrol.common.util.DateUtil;
import com.avst.equipmentcontrol.common.util.baseaction.BaseAction;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.common.util.baseaction.ReqParam;
import com.avst.equipmentcontrol.web.req.baseEttype.AddOrUpEttypeParam;
import com.avst.equipmentcontrol.web.req.baseEttype.BaseEttypeParam;
import com.avst.equipmentcontrol.web.service.BaseEttypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

//设备类型
@RestController
@RequestMapping("/BaseEttype")
public class BaseEttypeAction extends BaseAction {

    @Autowired
    private BaseEttypeService baseEttypeService;

    //查询
    @RequestMapping(value = "/getBaseEttype")
    public RResult getBaseEttype(@RequestBody ReqParam<BaseEttypeParam> param){
        RResult result=this.createNewResultOfFail();
        baseEttypeService.getBaseEttype(result,param);
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    //查询单个
    @RequestMapping(value = "/getBaseEttypeById")
    public RResult getBaseEttypeById(@RequestBody ReqParam<BaseEttypeParam> param){
        RResult result=this.createNewResultOfFail();
        baseEttypeService.getBaseEttypeById(result,param);
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    //新增
    @RequestMapping(value = "/addBaseEttype")
    public RResult addBaseEttype(@RequestBody ReqParam<AddOrUpEttypeParam> param){
        RResult result=this.createNewResultOfFail();
        baseEttypeService.addBaseEttype(result,param);
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    //修改
    @RequestMapping(value = "/updateBaseEttype")
    public RResult updateBaseEttype(@RequestBody ReqParam<AddOrUpEttypeParam> param){
        RResult result=this.createNewResultOfFail();
        baseEttypeService.updateBaseEttype(result,param);
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    //删除
    @RequestMapping(value = "/delBaseEttype")
    public RResult delBaseEttype(@RequestBody ReqParam<BaseEttypeParam> param){
        RResult result=this.createNewResultOfFail();
        baseEttypeService.delBaseEttype(result,param);
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    //显示列表页面
    @RequestMapping(value = "/getBaseEttypeList")
    public ModelAndView getBaseEttypeList(Model model) {
        model.addAttribute("title", "设备类型");
        return new ModelAndView("sweb/baseEttype/getBaseEttypeList", "getBaseEttypeListModel", model);
    }



}
