package com.avst.equipmentcontrol.outside.dealoutinterface.asr.avstasr.req.param;

/**
 * 每一个麦的音频通道信息
 */
public class TaskParam {

    private String index;//通道对应的麦的编号

    private int shockenergy;//音频音能激活语音阀值

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public int getShockenergy() {
        return shockenergy;
    }

    public void setShockenergy(int shockenergy) {
        this.shockenergy = shockenergy;
    }
}
