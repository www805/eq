package com.avst.equipmentcontrol.outside.interfacetoout.polygraph.v1.service;

import com.avst.equipmentcontrol.common.datasourse.extrasourse.polygraph.entity.param.PolygraphInfo;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.polygraph.mapper.Polygraph_etinfoMapper;
import com.avst.equipmentcontrol.common.util.LogUtil;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.common.util.baseaction.ReqParam;
import com.avst.equipmentcontrol.outside.dealoutinterface.polygraph.cmcross.v1.DealPolygraphImpl;
import com.avst.equipmentcontrol.outside.dealoutinterface.polygraph.cmcross.v1.req.BaseParam;
import com.avst.equipmentcontrol.outside.dealoutinterface.polygraph.cmcross.v1.vo.XBOX_CheckStatusVO;
import com.avst.equipmentcontrol.outside.dealoutinterface.polygraph.cmcross.v1.vo.XBOX_GetImageVO;
import com.avst.equipmentcontrol.outside.dealoutinterface.polygraph.cmcross.v1.vo.XBOX_GetResultVO;
import com.avst.equipmentcontrol.outside.dealoutinterface.polygraph.cmcross.v1.vo.XBOX_ShutdownVO;
import com.avst.equipmentcontrol.outside.interfacetoout.polygraph.req.*;
import com.avst.equipmentcontrol.outside.interfacetoout.polygraph.vo.CheckPolygraphStateVO;
import com.avst.equipmentcontrol.outside.interfacetoout.polygraph.vo.GetPolygraphAnalysisVO;
import com.avst.equipmentcontrol.outside.interfacetoout.polygraph.vo.GetPolygraphRealTimeImageVO;
import com.avst.equipmentcontrol.outside.interfacetoout.polygraph.vo.OverPolygraphVO;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ToOutServiceImpl_ph_avst implements ToOutService_ph{

    @Autowired
    private DealPolygraphImpl dealPolygraph;

    @Autowired
    private Polygraph_etinfoMapper polygraph_etinfoMapper;

    @Override
    public RResult checkPolygraphState(CheckPolygraphStateParam param, RResult result) {

        String phssid=param.getPolygraphssid();
        if(StringUtils.isEmpty(phssid)){
            LogUtil.intoLog(4,this.getClass()," phssid is null---"+phssid);
            result.setMessage("身心监护标识为空");
            return result;
        }
        EntityWrapper<PolygraphInfo> entityWrapper=new EntityWrapper<PolygraphInfo>();
        entityWrapper.eq("pet.ssid",phssid);
        PolygraphInfo polygraphInfo=polygraph_etinfoMapper.getPolygraphInfo(entityWrapper);
        if(null==polygraphInfo){
            LogUtil.intoLog(4,this.getClass(),"身心监护没有找到，请查看 phssid："+phssid);
            result.setMessage("身心监护没有找到");
            return result;
        }
        BaseParam xBoxParam=new BaseParam();
        xBoxParam.setIp(polygraphInfo.getEtip());
        xBoxParam.setPort(polygraphInfo.getPort());
        XBOX_CheckStatusVO xbox_checkStatusVO=dealPolygraph.xBOX_CheckStatus(xBoxParam);
        if(null!=xbox_checkStatusVO){
            Integer status=xbox_checkStatusVO.getStatus();
            if(status==0){//成功
                CheckPolygraphStateVO vo=new CheckPolygraphStateVO();
                vo.setWorkstate(1);
                result.changeToTrue(vo);
                LogUtil.intoLog(this.getClass(),"身心监护状态正常 polygraphInfo.getEtip()："+polygraphInfo.getEtip());
            }else if(status==10){//正在初始化
                LogUtil.intoLog(3,this.getClass(),"正在初始化请稍后 polygraphInfo.getEtip()："+polygraphInfo.getEtip());
                CheckPolygraphStateVO vo=new CheckPolygraphStateVO();
                vo.setWorkstate(1);
                result.changeToTrue(vo);
                result.setMessage("正在初始化请稍后");
            }else{

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                xbox_checkStatusVO=dealPolygraph.xBOX_CheckStatus(xBoxParam);
                if(null!=xbox_checkStatusVO&&(xbox_checkStatusVO.getStatus()==0||xbox_checkStatusVO.getStatus()==10)){
                    CheckPolygraphStateVO vo=new CheckPolygraphStateVO();
                    vo.setWorkstate(1);
                    result.changeToTrue(vo);
                    LogUtil.intoLog(3,this.getClass(),"警告，2次检测，身心监护状态正常 polygraphInfo.getEtip()："+polygraphInfo.getEtip());
                }else{
                    LogUtil.intoLog(4,this.getClass(),"身心监护状态异常："+polygraphInfo.getEtip());
                    result.setMessage("身心监护状态异常");
                }
            }
        }else{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            xbox_checkStatusVO=dealPolygraph.xBOX_CheckStatus(xBoxParam);
            if(null!=xbox_checkStatusVO&&(xbox_checkStatusVO.getStatus()==0||xbox_checkStatusVO.getStatus()==10)){
                CheckPolygraphStateVO vo=new CheckPolygraphStateVO();
                vo.setWorkstate(1);
                result.changeToTrue(vo);
                LogUtil.intoLog(3,this.getClass(),"警告，2次检测，身心监护状态正常 polygraphInfo.getEtip()："+polygraphInfo.getEtip());
            }else{
                LogUtil.intoLog(4,this.getClass(),"身心监护状态异常："+polygraphInfo.getEtip());
                result.setMessage("身心监护状态异常");
            }
        }
        return result;
    }

    @Override
    public RResult startPolygraph(StartPolygraphParam param, RResult result) {
        Gson gson=new Gson();
        //因为mc公司的身心监护不需要临时开启，所以只需要检测是否在正常工作就可以了
        CheckPolygraphStateParam pparam=gson.fromJson(gson.toJson(param),CheckPolygraphStateParam.class);
        result=checkPolygraphState(pparam,result);
        return result;
    }

    @Override
    public RResult OverPolygraph(OverPolygraphParam param, RResult result) {

        String phssid=param.getPolygraphssid();
        if(StringUtils.isEmpty(phssid)){
            LogUtil.intoLog(this.getClass()," phssid is null---"+phssid);
            result.setMessage("身心监护标识为空");
            return result;
        }
        EntityWrapper<PolygraphInfo> entityWrapper=new EntityWrapper<PolygraphInfo>();
        entityWrapper.eq("pet.ssid",phssid);
        PolygraphInfo polygraphInfo=polygraph_etinfoMapper.getPolygraphInfo(entityWrapper);
        if(null==polygraphInfo){
            LogUtil.intoLog(this.getClass(),"身心监护没有找到，请查看 phssid："+phssid);
            result.setMessage("身心监护没有找到");
            return result;
        }
        BaseParam xBoxParam=new BaseParam();
        xBoxParam.setIp(polygraphInfo.getEtip());
        xBoxParam.setPort(polygraphInfo.getPort());
        XBOX_ShutdownVO xbox_shutdownVO =dealPolygraph.xBOX_Shutdown(xBoxParam);
        if(null!=xbox_shutdownVO){
            Integer status=xbox_shutdownVO.getStatus();
            if(status==0){//成功
                OverPolygraphVO vo=new OverPolygraphVO();//暂时没有数据
                result.changeToTrue(vo);
            }else if(status==10){//正在初始化
                LogUtil.intoLog(this.getClass(),"正在初始化请稍后 polygraphInfo.getEtip()："+polygraphInfo.getEtip());
                result.setMessage("正在初始化请稍后");
            }
        }
        return result;
    }

    @Override
    public RResult<GetPolygraphAnalysisVO<XBOX_GetResultVO>> getPolygraphAnalysis(GetPolygraphAnalysisParam param, RResult result) {

        String phssid=param.getPolygraphssid();
        if(StringUtils.isEmpty(phssid)){
            LogUtil.intoLog(this.getClass()," phssid is null---"+phssid);
            result.setMessage("身心监护标识为空");
            return result;
        }
        EntityWrapper<PolygraphInfo> entityWrapper=new EntityWrapper<PolygraphInfo>();
        entityWrapper.eq("pet.ssid",phssid);
        PolygraphInfo polygraphInfo=polygraph_etinfoMapper.getPolygraphInfo(entityWrapper);
        if(null==polygraphInfo){
            LogUtil.intoLog(this.getClass(),"身心监护没有找到，请查看 phssid："+phssid);
            result.setMessage("身心监护没有找到");
            return result;
        }
        BaseParam xBoxParam=new BaseParam();
        xBoxParam.setIp(polygraphInfo.getEtip());
        xBoxParam.setPort(polygraphInfo.getPort());
        XBOX_GetResultVO xbox_getResultVO =dealPolygraph.xBOX_GetResult(xBoxParam);
        if(null!=xbox_getResultVO){
            GetPolygraphAnalysisVO<XBOX_GetResultVO> vo=new GetPolygraphAnalysisVO<XBOX_GetResultVO>();
            vo.setT(xbox_getResultVO);
            result.changeToTrue(vo);
        }
        return result;
    }

    @Override
    public RResult getPolygraphRealTimeImage(GetPolygraphRealTimeImageParam param, RResult result) {

        String phssid=param.getPolygraphssid();
        if(StringUtils.isEmpty(phssid)){
            LogUtil.intoLog(this.getClass()," phssid is null---"+phssid);
            result.setMessage("身心监护标识为空");
            return result;
        }
        EntityWrapper<PolygraphInfo> entityWrapper=new EntityWrapper<PolygraphInfo>();
        entityWrapper.eq("pet.ssid",phssid);
        PolygraphInfo polygraphInfo=polygraph_etinfoMapper.getPolygraphInfo(entityWrapper);
        if(null==polygraphInfo){
            LogUtil.intoLog(this.getClass(),"身心监护没有找到，请查看 phssid："+phssid);
            result.setMessage("身心监护没有找到");
            return result;
        }
        BaseParam xBoxParam=new BaseParam();
        xBoxParam.setIp(polygraphInfo.getEtip());
        xBoxParam.setPort(polygraphInfo.getPort());
        XBOX_GetImageVO xbox_getImageVO =dealPolygraph.xBOX_GetImage(xBoxParam);
        if(null!=xbox_getImageVO&&StringUtils.isNotEmpty(xbox_getImageVO.getImage())){
            GetPolygraphRealTimeImageVO vo=new GetPolygraphRealTimeImageVO();
            vo.setBase64(xbox_getImageVO.getImage());
            vo.setImgType(1);
            result.changeToTrue(vo);
        }
        return result;
    }
}
