package com.avst.equipmentcontrol.web.action;

import com.avst.equipmentcontrol.common.datasourse.extrasourse.flushbonading.entity.param.Flushbonadinginfo;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.polygraph.mapper.Polygraph_etinfoMapper;
import com.avst.equipmentcontrol.common.util.DateUtil;
import com.avst.equipmentcontrol.common.util.baseaction.BaseAction;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.common.util.baseaction.ReqParam;
import com.avst.equipmentcontrol.web.req.flushbonading.FlushbonadinginfoParam;
import com.avst.equipmentcontrol.web.req.polygraph.PolygraphParam;
import com.avst.equipmentcontrol.web.req.polygraph.UpdatePolygraphParam;
import com.avst.equipmentcontrol.web.service.PolygraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

//测谎表
@RestController
@RequestMapping("/Polygraph")
public class PolygraphAction extends BaseAction {

    @Autowired
    private PolygraphService polygraphService;

    //查询
    @RequestMapping(value = "/getPolygraphList")
    public RResult getPolygraphList(@RequestBody ReqParam<PolygraphParam> param){
        RResult result=this.createNewResultOfFail();
        polygraphService.getPolygraphList(result,param);
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    //查询单个
    @RequestMapping(value = "/getPolygraphById")
    public RResult getPolygraphById(@RequestBody ReqParam<PolygraphParam> param){
        RResult result=this.createNewResultOfFail();
        polygraphService.getPolygraphById(result,param);
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    //新增
    @RequestMapping(value = "/addPolygraph")
    public RResult addPolygraph(@RequestBody ReqParam<UpdatePolygraphParam> param){
        RResult result=this.createNewResultOfFail();
        polygraphService.addPolygraph(result,param);
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    //修改
    @RequestMapping(value = "/updatePolygraph")
    public RResult updatePolygraph(@RequestBody ReqParam<UpdatePolygraphParam> param){
        RResult result=this.createNewResultOfFail();
        polygraphService.updatePolygraph(result,param);
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    //删除
    @RequestMapping(value = "/delPolygraph")
    public RResult delPolygraph(@RequestBody ReqParam<PolygraphParam> param){
        RResult result=this.createNewResultOfFail();
        polygraphService.delPolygraph(result,param);
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    //显示列表页面
    @RequestMapping(value = "/getPolygraphIndex")
    public ModelAndView getPolygraphIndex(Model model) {
        model.addAttribute("title", "测谎仪列表");
        return new ModelAndView("sweb/polygraphhtml/getPolygraphIndex", "getPolygraphIndexModel", model);
    }

    //显示修改页面
    @RequestMapping(value = "/addOrUpdatePolygraph")
    public ModelAndView addOrUpdatePolygraph(Model model) {
        model.addAttribute("title", "测谎仪新增/修改");
        return new ModelAndView("sweb/polygraphhtml/addOrUpdatePolygraph", "addOrUpdatePolygraphModel", model);
    }

}
