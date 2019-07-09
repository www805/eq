package com.avst.equipmentcontrol.web.action;

import com.avst.equipmentcontrol.common.util.DateUtil;
import com.avst.equipmentcontrol.common.util.baseaction.BaseAction;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.common.util.baseaction.ReqParam;
import com.avst.equipmentcontrol.web.req.asr.AsrParam;
import com.avst.equipmentcontrol.web.req.asr.UpdateAsrParam;
import com.avst.equipmentcontrol.web.service.AsrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

//语音服务器
@RestController
@RequestMapping("/Asr")
public class AsrAction extends BaseAction {

    @Autowired
    private AsrService asrService;

    //查询
    @RequestMapping(value = "/getAsrList")
    public RResult getAsrList(@RequestBody ReqParam<AsrParam> param){
        RResult result=this.createNewResultOfFail();
        asrService.getAsrList(result,param);
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    //查询单个
    @RequestMapping(value = "/getAsrById")
    public RResult getAsrById(@RequestBody ReqParam<AsrParam> param){
        RResult result=this.createNewResultOfFail();
        asrService.getAsrById(result,param);
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    //新增
    @RequestMapping(value = "/addAsr")
    public RResult addAsr(@RequestBody ReqParam<UpdateAsrParam> param){
        RResult result=this.createNewResultOfFail();
        asrService.addAsr(result,param);
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    //修改
    @RequestMapping(value = "/updateAsr")
    public RResult updateAsr(@RequestBody ReqParam<UpdateAsrParam> param){
        RResult result=this.createNewResultOfFail();
        asrService.updateAsr(result,param);
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    //删除
    @RequestMapping(value = "/delAsr")
    public RResult delAsr(@RequestBody ReqParam<AsrParam> param){
        RResult result=this.createNewResultOfFail();
        asrService.delAsr(result,param);
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    //显示列表页面
    @RequestMapping(value = "/getAsrIndex")
    public ModelAndView getAsrIndex(Model model) {
        model.addAttribute("title", "语音服务器列表");
        return new ModelAndView("sweb/asrhtml/getAsrIndex", "getAsrIndexModel", model);
    }

    //显示新增/修改页面
    @RequestMapping(value = "/addOrUpdateAsr")
    public ModelAndView addOrUpdateAsr(Model model) {
        model.addAttribute("title", "语音服务器 新增/修改");
        return new ModelAndView("sweb/asrhtml/addOrUpdateAsr", "addOrUpdateAsrModel", model);
    }


}
