package com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.req;

public class SetFTPModeParam extends BaseParam {

    /**
     * yes|no 是否启用被动上传模式，默认为主动上传
     */
    private String enable;

    /**
     * 1|0 可选
     */
    private String save;

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }

    public String getSave() {
        return save;
    }

    public void setSave(String save) {
        this.save = save;
    }
}
