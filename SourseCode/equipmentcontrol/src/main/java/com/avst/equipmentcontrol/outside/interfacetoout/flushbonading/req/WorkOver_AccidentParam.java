package com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.req;

public class WorkOver_AccidentParam extends WorkOverParam  {

    //如果没有的话，也不需要这个参数，它是用于同步
    private long mtRecordTime;//如果有语音识别或者身心监护就会由这个

    private String iid;//设备录像的唯一标识

    private boolean cloaseRecbool=true;//是否可以关闭设备录像

    public boolean isCloaseRecbool() {
        return cloaseRecbool;
    }

    public void setCloaseRecbool(boolean cloaseRecbool) {
        this.cloaseRecbool = cloaseRecbool;
    }

    public String getIid() {
        return iid;
    }

    public void setIid(String iid) {
        this.iid = iid;
    }

    public long getMtRecordTime() {
        return mtRecordTime;
    }

    public void setMtRecordTime(long mtRecordTime) {
        this.mtRecordTime = mtRecordTime;
    }
}
