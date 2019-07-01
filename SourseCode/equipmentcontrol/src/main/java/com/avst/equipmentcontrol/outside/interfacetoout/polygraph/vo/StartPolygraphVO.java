package com.avst.equipmentcontrol.outside.interfacetoout.polygraph.vo;


public class StartPolygraphVO {

    private int workstate;//约定一套状态指令，1成功，0初始化中，其他都为错误

    public int getWorkstate() {
        return workstate;
    }

    public void setWorkstate(int workstate) {
        this.workstate = workstate;
    }

}
