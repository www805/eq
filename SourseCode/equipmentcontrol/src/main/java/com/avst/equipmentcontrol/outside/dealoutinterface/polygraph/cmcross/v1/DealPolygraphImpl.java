package com.avst.equipmentcontrol.outside.dealoutinterface.polygraph.cmcross.v1;

import com.avst.equipmentcontrol.common.util.JacksonUtil;
import com.avst.equipmentcontrol.common.util.LogUtil;
import com.avst.equipmentcontrol.common.util.baseaction.BaseAction;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.outside.dealoutinterface.polygraph.cmcross.conf.CMAction;
import com.avst.equipmentcontrol.outside.dealoutinterface.polygraph.cmcross.v1.req.BaseParam;
import com.avst.equipmentcontrol.outside.dealoutinterface.polygraph.cmcross.v1.vo.XBOX_CheckStatusVO;
import com.avst.equipmentcontrol.outside.dealoutinterface.polygraph.cmcross.v1.vo.XBOX_GetImageVO;
import com.avst.equipmentcontrol.outside.dealoutinterface.polygraph.cmcross.v1.vo.XBOX_GetResultVO;
import com.avst.equipmentcontrol.outside.dealoutinterface.polygraph.cmcross.v1.vo.XBOX_ShutdownVO;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

/**
 * 处理对接cmcross测谎仪
 */
@Service
public class DealPolygraphImpl extends BaseAction implements BaseInterface {

    @Override
    public XBOX_CheckStatusVO xBOX_CheckStatus(BaseParam param) {

        RResult result=this.createNewResultOfFail();
        String ip=param.getIp();
        int port=param.getPort();
        String action= CMAction.XBOX_CheckStatus.toString();
        String rr=WebServiceClient.webClient(ip,port,action);
        if(null!=rr){
            try {
                LogUtil.intoLog(3,this.getClass(),rr+"WebServiceClient.webClient XBOX_CheckStatus");
                Gson gson=new Gson();
                XBOX_CheckStatusVO vo=gson.fromJson(rr,XBOX_CheckStatusVO.class);
                return vo;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            LogUtil.intoLog(4,this.getClass(),"WebServiceClient.webClient XBOX_CheckStatus is null");
        }
        return null;
    }

    @Override
    public XBOX_GetResultVO xBOX_GetResult(BaseParam param) {
        RResult result=this.createNewResultOfFail();
        String ip=param.getIp();
        int port=param.getPort();
        String action= CMAction.XBOX_GetResult.toString();
        String rr=WebServiceClient.webClient(ip,port,action);
        if(null!=rr){
            try {
                Gson gson=new Gson();
                XBOX_GetResultVO vo=gson.fromJson(rr,XBOX_GetResultVO.class);
                return vo;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public XBOX_ShutdownVO xBOX_Shutdown(BaseParam param) {
        RResult result=this.createNewResultOfFail();
        String ip=param.getIp();
        int port=param.getPort();
        String action= CMAction.XBOX_Shutdown.toString();
        String rr=WebServiceClient.webClient(ip,port,action);
        if(null!=rr){
            try {
                Gson gson=new Gson();
                XBOX_ShutdownVO vo=gson.fromJson(rr,XBOX_ShutdownVO.class);
                return vo;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public XBOX_GetImageVO xBOX_GetImage(BaseParam param) {
        RResult result= null;
        try {
            result = this.createNewResultOfFail();
            String ip=param.getIp();
            int port=param.getPort();
            String action= CMAction.XBOX_GetImage.toString();
            String rr=WebServiceClient.webClient(ip,port,action);
            if(null!=rr){
                try {
                    Gson gson=new Gson();
                    XBOX_GetImageVO vo=gson.fromJson(rr,XBOX_GetImageVO.class);
                    return vo;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
