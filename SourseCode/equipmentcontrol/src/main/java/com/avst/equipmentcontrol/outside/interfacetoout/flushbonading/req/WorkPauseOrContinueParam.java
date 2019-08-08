package com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.req;

public class WorkPauseOrContinueParam extends BaseParam {

    /**
     * 其实就是mtssid
     */
    private String fdid;//设备缓存的key
    private String iid;//设备录像的唯一识别码

    private int pauseOrContinue;//1请求暂停，2请求继续

    public String getFdid() {
        return fdid;
    }

    public void setFdid(String fdid) {
        this.fdid = fdid;
    }

    public String getIid() {
        return iid;
    }

    public void setIid(String iid) {
        this.iid = iid;
    }

    public int getPauseOrContinue() {
        return pauseOrContinue;
    }

    public void setPauseOrContinue(int pauseOrContinue) {
        this.pauseOrContinue = pauseOrContinue;
    }
}
