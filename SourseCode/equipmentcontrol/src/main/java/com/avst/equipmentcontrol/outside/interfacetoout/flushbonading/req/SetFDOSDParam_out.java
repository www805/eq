package com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.req;

/**
 * 一定要记得，设置那些叠加就传那些参数，没有的别乱传
 */
public class SetFDOSDParam_out extends BaseParam {

    private int osd_sync_ajust;//是否立即生效

    private int osd_saved;//是否保存到设备

    private int osdpart_time_x;//时间日期

    private int osdpart_time_y;//时间日期

    private int osdpart_pttext_x;//片头位置

    private int osdpart_pttext_y;//片头位置

    private int osdpart_title_x;//房间名

    private int osdpart_title_y;//房间名

    private int osdpart_temperature_x;//温湿度

    private int osdpart_temperature_y;//温湿度

    public int getOsd_sync_ajust() {
        return osd_sync_ajust;
    }

    public void setOsd_sync_ajust(int osd_sync_ajust) {
        this.osd_sync_ajust = osd_sync_ajust;
    }

    public int getOsd_saved() {
        return osd_saved;
    }

    public void setOsd_saved(int osd_saved) {
        this.osd_saved = osd_saved;
    }

    public int getOsdpart_time_x() {
        return osdpart_time_x;
    }

    public void setOsdpart_time_x(int osdpart_time_x) {
        this.osdpart_time_x = osdpart_time_x;
    }

    public int getOsdpart_time_y() {
        return osdpart_time_y;
    }

    public void setOsdpart_time_y(int osdpart_time_y) {
        this.osdpart_time_y = osdpart_time_y;
    }

    public int getOsdpart_pttext_x() {
        return osdpart_pttext_x;
    }

    public void setOsdpart_pttext_x(int osdpart_pttext_x) {
        this.osdpart_pttext_x = osdpart_pttext_x;
    }

    public int getOsdpart_pttext_y() {
        return osdpart_pttext_y;
    }

    public void setOsdpart_pttext_y(int osdpart_pttext_y) {
        this.osdpart_pttext_y = osdpart_pttext_y;
    }

    public int getOsdpart_title_x() {
        return osdpart_title_x;
    }

    public void setOsdpart_title_x(int osdpart_title_x) {
        this.osdpart_title_x = osdpart_title_x;
    }

    public int getOsdpart_title_y() {
        return osdpart_title_y;
    }

    public void setOsdpart_title_y(int osdpart_title_y) {
        this.osdpart_title_y = osdpart_title_y;
    }

    public int getOsdpart_temperature_x() {
        return osdpart_temperature_x;
    }

    public void setOsdpart_temperature_x(int osdpart_temperature_x) {
        this.osdpart_temperature_x = osdpart_temperature_x;
    }

    public int getOsdpart_temperature_y() {
        return osdpart_temperature_y;
    }

    public void setOsdpart_temperature_y(int osdpart_temperature_y) {
        this.osdpart_temperature_y = osdpart_temperature_y;
    }
}
