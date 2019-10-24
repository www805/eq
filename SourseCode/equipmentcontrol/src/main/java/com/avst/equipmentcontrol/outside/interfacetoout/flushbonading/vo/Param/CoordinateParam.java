package com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.vo.Param;

public class CoordinateParam {

    private int x;
    private int y;

    public CoordinateParam(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public CoordinateParam(String x, String y) {
        try {
            this.x = Integer.parseInt(x);
            this.y = Integer.parseInt(y);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
