package com.avst.equipmentcontrol.outside.dealoutinterface.polygraph.cmcross.conf;

/**
 * CM身心监护的接口名
 */
public enum  CMAction {

    XBOX_CheckStatus,//盒子启动状态检测
    XBOX_GetResult,//生理心理检测结果获取
    XBOX_GetImage,//相机实时图像获取
    XBOX_Shutdown,//关闭盒子

}
