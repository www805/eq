package com.avst.equipmentcontrol.outside.interfacetoout.polygraph.v1;

import com.avst.equipmentcontrol.common.conf.PHType;
import com.avst.equipmentcontrol.common.util.DateUtil;
import com.avst.equipmentcontrol.common.util.baseaction.BaseAction;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.common.util.baseaction.ReqParam;
import com.avst.equipmentcontrol.outside.interfacetoout.polygraph.req.*;
import com.avst.equipmentcontrol.outside.interfacetoout.polygraph.v1.service.ToOutServiceImpl_ph_avst;
import com.avst.equipmentcontrol.outside.interfacetoout.polygraph.v1.service.ToOutService_ph;
import com.avst.equipmentcontrol.outside.interfacetoout.polygraph.vo.GetPolygraphAnalysisVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/ph/v1")
public class ToOutAction_ph extends BaseAction {

    @Autowired
    private ToOutServiceImpl_ph_avst toOutServiceImpl_ph_avst;

    private ToOutService_ph getToOutService(String phType){
        if(phType.equals(PHType.CMCROSS)){
            return toOutServiceImpl_ph_avst;
        }
        return null;
    }

    /**
     * 检测测谎仪状态
     * @param param
     * @return
     */
    @RequestMapping("/checkPolygraphState")
    @ResponseBody
    public RResult checkPolygraphState(@RequestBody  ReqParam<CheckPolygraphStateParam> param){

        RResult result=this.createNewResultOfFail();

        if(null!=param.getParam()){
            CheckPolygraphStateParam pParam=param.getParam();
            result=getToOutService(pParam.getPhType()).checkPolygraphState(pParam,result);
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    };

    /**
     * 开启测谎仪
     * @param param
     * @return
     */
    @RequestMapping("/startPolygraph")
    @ResponseBody
    public RResult startPolygraph(@RequestBody  ReqParam<StartPolygraphParam> param){

        RResult result=this.createNewResultOfFail();
        if(null!=param.getParam()){
            StartPolygraphParam pParam=param.getParam();
            result=getToOutService(pParam.getPhType()).startPolygraph(pParam,result);
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    };

    /**
     * //结束测谎仪
     * @param param
     * @return
     */
    @RequestMapping("/overPolygraph")
    @ResponseBody
    public RResult overPolygraph(@RequestBody  ReqParam<OverPolygraphParam> param){

        RResult result=this.createNewResultOfFail();
        if(null!=param.getParam()){
            OverPolygraphParam pParam=param.getParam();
            result=getToOutService(pParam.getPhType()).OverPolygraph(pParam,result);
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    };

    /**
     * //获取测谎心里分析数据
     * @param param
     * @return
     */
    @RequestMapping("/getPolygraphAnalysis")
    @ResponseBody
    public RResult<GetPolygraphAnalysisVO> getPolygraphAnalysis(@RequestBody  ReqParam<GetPolygraphAnalysisParam> param){

        RResult result=this.createNewResultOfFail();
        if(null!=param.getParam()){
            GetPolygraphAnalysisParam pParam=param.getParam();
            result=getToOutService(pParam.getPhType()).getPolygraphAnalysis(pParam,result);
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    };

    /**
     * //获取测谎仪心理分析的实时图像
     * @param param
     * @return
     */
    @RequestMapping("/getPolygraphRealTimeImage")
    @ResponseBody
    public RResult getPolygraphRealTimeImage(@RequestBody  ReqParam<GetPolygraphRealTimeImageParam> param){

        RResult result=this.createNewResultOfFail();
        if(null!=param.getParam()){
            GetPolygraphRealTimeImageParam pParam=param.getParam();
            result=getToOutService(pParam.getPhType()).getPolygraphRealTimeImage(pParam,result);
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    };

}
