package com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.req;

public class GetptdjconstParam_out extends BaseParam {


    private boolean mustUpdateBool;//是否强制修改数据库中保存的片头

    public boolean isMustUpdateBool() {
        return mustUpdateBool;
    }

    public void setMustUpdateBool(boolean mustUpdateBool) {
        this.mustUpdateBool = mustUpdateBool;
    }
}
