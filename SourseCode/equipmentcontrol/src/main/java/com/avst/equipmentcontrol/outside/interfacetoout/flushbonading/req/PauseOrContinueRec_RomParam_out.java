package com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.req;

public class PauseOrContinueRec_RomParam_out extends BaseParam {

    private int pauseOrContinue=1;//1暂停、2继续

    public int getPauseOrContinue() {
        return pauseOrContinue;
    }

    public void setPauseOrContinue(int pauseOrContinue) {
        this.pauseOrContinue = pauseOrContinue;
    }
}
