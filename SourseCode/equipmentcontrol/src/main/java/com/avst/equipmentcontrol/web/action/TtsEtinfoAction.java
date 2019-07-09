package com.avst.equipmentcontrol.web.action;

import com.avst.equipmentcontrol.common.util.DateUtil;
import com.avst.equipmentcontrol.common.util.baseaction.BaseAction;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.common.util.baseaction.ReqParam;
import com.avst.equipmentcontrol.web.req.tts.TtsetinfoParam;
import com.avst.equipmentcontrol.web.req.tts.UpdateTtsetinfoParam;
import com.avst.equipmentcontrol.web.service.TtsEtinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

//文字转语音服务
@RestController
@RequestMapping("/ttsetinfo")
public class TtsEtinfoAction extends BaseAction {

    @Autowired
    private TtsEtinfoService ttsetinfoService;

    //查询
    @RequestMapping(value = "/getTtsetinfoList")
    public RResult getTtsetinfoList(@RequestBody ReqParam<TtsetinfoParam> param){
        RResult result=this.createNewResultOfFail();
        ttsetinfoService.getTtsetinfoList(result,param);
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    //查询单个
    @RequestMapping(value = "/getTtsetinfoById")
    public RResult getTtsetinfoById(@RequestBody ReqParam<TtsetinfoParam> param){
        RResult result=this.createNewResultOfFail();
        ttsetinfoService.getTtsetinfoById(result,param);
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    //新增
    @RequestMapping(value = "/addTtsetinfo")
    public RResult addTtsetinfo(@RequestBody ReqParam<UpdateTtsetinfoParam> param){
        RResult result=this.createNewResultOfFail();
        ttsetinfoService.addTtsetinfo(result,param);
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    //修改
    @RequestMapping(value = "/updateTtsetinfo")
    public RResult updateTtsetinfo(@RequestBody ReqParam<UpdateTtsetinfoParam> param){
        RResult result=this.createNewResultOfFail();
        ttsetinfoService.updateTtsetinfo(result,param);
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    //删除
    @RequestMapping(value = "/delTtsetinfo")
    public RResult delTtsetinfo(@RequestBody ReqParam<TtsetinfoParam> param){
        RResult result=this.createNewResultOfFail();
        ttsetinfoService.delTtsetinfo(result,param);
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    //显示列表页面
    @RequestMapping(value = "/getTtsetinfoIndex")
    public ModelAndView getTtsetinfoIndex(Model model) {
        model.addAttribute("title", "文字转语音服务列表");
        model.addAttribute("servername", "文字转语音服务");
        return new ModelAndView("sweb/ttsetinfo/getTtsetinfoIndex", "getTtsetinfoIndexModel", model);
    }

    //显示修改页面
    @RequestMapping(value = "/addOrUpdateTtsetinfo")
    public ModelAndView addOrUpdateTtsetinfo(Model model) {
        model.addAttribute("title", "文字转语音服务新增/修改");
        return new ModelAndView("sweb/ttsetinfo/addOrUpdateTtsetinfo", "addOrUpdateTtsetinfoModel", model);
    }

}
