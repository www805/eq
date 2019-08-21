package com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.req;

/**
 * 光驱录像就不使用硬盘录像
 * 当显示值需要硬盘录像才会调用硬盘开始录像
 */
public class StartRec_RomParam extends BaseParam {

    private int dx=0;//0：全部光驱 1：光驱一 2：光驱二

    private int aldisk=0;//0:不始终开始硬盘录像，在光驱开启因某些原因失败的情况下，硬盘不联动录像开启，跟随光驱 1:不管光驱是否开启正常，硬盘始终请求录像

    private int disconly=1;//仅当 aldisk!=1 时生效，1标记为仅刻录光驱请求，不联动请求硬盘录像,

    private int btime;//刻录选时，小时，1-24

    private String bmode="bmode";//仅当 dx 为 0 时有效，exchange 表示为使用接力刻录模式，当没有值或其他值是表示使用双光驱刻录

    private String iid;//视频文件的唯一标识

    public int getDx() {
        return dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public int getAldisk() {
        return aldisk;
    }

    public void setAldisk(int aldisk) {
        this.aldisk = aldisk;
    }

    public int getDisconly() {
        return disconly;
    }

    public void setDisconly(int disconly) {
        this.disconly = disconly;
    }

    public int getBtime() {
        return btime;
    }

    public void setBtime(int btime) {
        this.btime = btime;
    }

    public String getBmode() {
        return bmode;
    }

    public void setBmode(String bmode) {
        this.bmode = bmode;
    }

    public String getIid() {
        return iid;
    }

    public void setIid(String iid) {
        this.iid = iid;
    }
}
