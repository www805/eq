package com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.vo;

public class CheckFDStateVO<T> {

    private int stateType=0;

    private T data;

    public int getStateType() {
        return stateType;
    }

    public void setStateType(int stateType) {
        this.stateType = stateType;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
