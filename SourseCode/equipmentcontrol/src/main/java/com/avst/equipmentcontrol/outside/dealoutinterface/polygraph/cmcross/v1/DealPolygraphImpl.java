package com.avst.equipmentcontrol.outside.dealoutinterface.polygraph.cmcross.v1;

import com.avst.equipmentcontrol.common.util.JacksonUtil;
import com.avst.equipmentcontrol.common.util.baseaction.BaseAction;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.outside.dealoutinterface.polygraph.cmcross.conf.CMAction;
import com.avst.equipmentcontrol.outside.dealoutinterface.polygraph.cmcross.v1.req.BaseParam;
import com.avst.equipmentcontrol.outside.dealoutinterface.polygraph.cmcross.v1.vo.XBOX_CheckStatusVO;
import com.avst.equipmentcontrol.outside.dealoutinterface.polygraph.cmcross.v1.vo.XBOX_GetImageVO;
import com.avst.equipmentcontrol.outside.dealoutinterface.polygraph.cmcross.v1.vo.XBOX_GetResultVO;
import com.avst.equipmentcontrol.outside.dealoutinterface.polygraph.cmcross.v1.vo.XBOX_ShutdownVO;
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
                XBOX_CheckStatusVO vo= (XBOX_CheckStatusVO)JacksonUtil.stringToObjebt_1(rr,XBOX_CheckStatusVO.class);
                return vo;
            } catch (Exception e) {
                e.printStackTrace();
            }
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
                XBOX_GetResultVO vo= (XBOX_GetResultVO)JacksonUtil.stringToObjebt_1(rr,XBOX_GetResultVO.class);
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
                XBOX_ShutdownVO vo= (XBOX_ShutdownVO)JacksonUtil.stringToObjebt_1(rr,XBOX_ShutdownVO.class);
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
                    XBOX_GetImageVO vo= (XBOX_GetImageVO)JacksonUtil.stringToObjebt_1(rr,XBOX_GetImageVO.class);
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
