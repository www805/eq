package com.avst.equipmentcontrol.outside.interfacetoout.storage.vo.param;

import com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.entity.Ss_saveinfo;
import com.avst.equipmentcontrol.common.util.filespace.DriverSpaceParam;

public class Ss_saveinfoParam {

    private Ss_saveinfo ss_saveinfo;
    private DriverSpaceParam driverSpaceParam;

    public Ss_saveinfo getSs_saveinfo() {
        return ss_saveinfo;
    }

    public void setSs_saveinfo(Ss_saveinfo ss_saveinfo) {
        this.ss_saveinfo = ss_saveinfo;
    }

    public DriverSpaceParam getDriverSpaceParam() {
        return driverSpaceParam;
    }

    public void setDriverSpaceParam(DriverSpaceParam driverSpaceParam) {
        this.driverSpaceParam = driverSpaceParam;
    }
}
