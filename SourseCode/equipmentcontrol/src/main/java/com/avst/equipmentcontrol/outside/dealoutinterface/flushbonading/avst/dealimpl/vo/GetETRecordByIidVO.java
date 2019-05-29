package com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.vo;

import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.xmljsonobject.param.File;

import java.util.List;

public class GetETRecordByIidVO {

    List<File> fileList;

    public List<File> getFileList() {
        return fileList;
    }

    public void setFileList(List<File> fileList) {
        this.fileList = fileList;
    }
}
