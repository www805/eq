package com.avst.equipmentcontrol.outside.dealoutinterface.polygraph.cmcross.v1;

import com.avst.equipmentcontrol.outside.dealoutinterface.polygraph.cmcross.v1.req.BaseParam;
import com.avst.equipmentcontrol.outside.dealoutinterface.polygraph.cmcross.v1.vo.XBOX_CheckStatusVO;
import com.avst.equipmentcontrol.outside.dealoutinterface.polygraph.cmcross.v1.vo.XBOX_GetImageVO;
import com.avst.equipmentcontrol.outside.dealoutinterface.polygraph.cmcross.v1.vo.XBOX_GetResultVO;
import com.avst.equipmentcontrol.outside.dealoutinterface.polygraph.cmcross.v1.vo.XBOX_ShutdownVO;

public interface BaseInterface {

    /**
     * 盒子启动状态检测
     * @param param
     * @return
     */
    public XBOX_CheckStatusVO xBOX_CheckStatus(BaseParam param);

    /**
     * 生理心理检测结果获取
     * @param param
     * @return
     */
    public XBOX_GetResultVO xBOX_GetResult(BaseParam param);

    /**
     *关闭盒子
     * @param param
     * @return
     */
    public XBOX_ShutdownVO xBOX_Shutdown(BaseParam param);

    /**
     *相机实时图像获取
     * @param param
     * @return
     */
    public XBOX_GetImageVO xBOX_GetImage(BaseParam param);

}
