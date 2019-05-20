package com.avst.equipmentcontrol.outside.interfacetoout.asr.req;

/**
 * 关闭asr服务需要带的参数
 */
public class OverAsrParam<T> extends  BaseReqParam {

    private String asrid;//语音识别唯一码

    public String getAsrid() {
        return asrid;
    }

    public void setAsrid(String asrid) {
        this.asrid = asrid;
    }
}
