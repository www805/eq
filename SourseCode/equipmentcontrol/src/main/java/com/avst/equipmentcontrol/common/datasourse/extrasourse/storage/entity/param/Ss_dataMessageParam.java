package com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.entity.param;

import com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.entity.Ss_database;

public class Ss_dataMessageParam extends Ss_database {

    private String iid;

    private String datasavebasepath;

    private String saveinfossid;

    public String getIid() {
        return iid;
    }

    public void setIid(String iid) {
        this.iid = iid;
    }

    public String getDatasavebasepath() {
        return datasavebasepath;
    }

    public void setDatasavebasepath(String datasavebasepath) {
        this.datasavebasepath = datasavebasepath;
    }

    public String getSaveinfossid() {
        return saveinfossid;
    }

    public void setSaveinfossid(String saveinfossid) {
        this.saveinfossid = saveinfossid;
    }
}
