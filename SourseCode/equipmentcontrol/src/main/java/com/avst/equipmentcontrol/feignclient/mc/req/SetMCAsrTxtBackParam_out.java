package com.avst.equipmentcontrol.feignclient.mc.req;

import com.avst.equipmentcontrol.outside.interfacetoout.asr.cache.param.AsrTxtParam_toout;

public class SetMCAsrTxtBackParam_out extends BaseReqParam {

    private String mcType;//会议采用版本，现阶段只有AVST

    private AsrTxtParam_toout asrTxtParam_toout;//最新的识别结果

    private String asrid;//唯一识别码

    public String getAsrid() {
        return asrid;
    }

    public void setAsrid(String asrid) {
        this.asrid = asrid;
    }

    public String getMcType() {
        return mcType;
    }

    public void setMcType(String mcType) {
        this.mcType = mcType;
    }

    public AsrTxtParam_toout getAsrTxtParam_toout() {
        return asrTxtParam_toout;
    }

    public void setAsrTxtParam_toout(AsrTxtParam_toout asrTxtParam_toout) {
        this.asrTxtParam_toout = asrTxtParam_toout;
    }
}
