package com.avst.equipmentcontrol.outside.interfacetoout.storage.vo;

import com.avst.equipmentcontrol.outside.interfacetoout.storage.vo.param.RecordPlayParam;

import java.util.List;

public class GetURLToPlayVO {

    private String iid;

    private List<RecordPlayParam> recordList;//播放文件集合

    public String getIid() {
        return iid;
    }

    public void setIid(String iid) {
        this.iid = iid;
    }

    public List<RecordPlayParam> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<RecordPlayParam> recordList) {
        this.recordList = recordList;
    }
}
