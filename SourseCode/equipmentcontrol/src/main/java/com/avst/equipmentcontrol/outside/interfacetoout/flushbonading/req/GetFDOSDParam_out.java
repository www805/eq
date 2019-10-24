package com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.req;

public class GetFDOSDParam_out extends BaseParam {

    private boolean timebool=false;//是否需要时间坐标

    private boolean ptbool=false;//是否需要片头叠加坐标

    private boolean titlebool=false;//是否需要设备房间名坐标

    private boolean temperaturebool=false;//是否需要温湿度坐标

    public boolean isTimebool() {
        return timebool;
    }

    public void setTimebool(boolean timebool) {
        this.timebool = timebool;
    }

    public boolean isPtbool() {
        return ptbool;
    }

    public void setPtbool(boolean ptbool) {
        this.ptbool = ptbool;
    }

    public boolean isTitlebool() {
        return titlebool;
    }

    public void setTitlebool(boolean titlebool) {
        this.titlebool = titlebool;
    }

    public boolean isTemperaturebool() {
        return temperaturebool;
    }

    public void setTemperaturebool(boolean temperaturebool) {
        this.temperaturebool = temperaturebool;
    }
}
