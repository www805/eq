package com.avst.equipmentcontrol.outside.interfacetoout.asr.req;

public class PauseOrContinueAsrParam extends  BaseReqParam {

    private int pauseOrContinue;//1请求暂停，2请求继续

    private String asrid;//本次语音识别的唯一码

    public String getAsrid() {
        return asrid;
    }

    public void setAsrid(String asrid) {
        this.asrid = asrid;
    }

    public int getPauseOrContinue() {
        return pauseOrContinue;
    }

    public void setPauseOrContinue(int pauseOrContinue) {
        this.pauseOrContinue = pauseOrContinue;
    }
}
