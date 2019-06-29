package com.avst.equipmentcontrol.web.vo.storage;

import com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.entity.Storage_ettype;
import com.avst.equipmentcontrol.web.req.storage.StorageParam;

import java.util.List;

public class StorageVO {

    private List<Storage_ettype> pagelist;
    private StorageParam pageparam;

    public List<Storage_ettype> getPagelist() {
        return pagelist;
    }

    public void setPagelist(List<Storage_ettype> pagelist) {
        this.pagelist = pagelist;
    }

    public StorageParam getPageparam() {
        return pageparam;
    }

    public void setPageparam(StorageParam pageparam) {
        this.pageparam = pageparam;
    }
}
