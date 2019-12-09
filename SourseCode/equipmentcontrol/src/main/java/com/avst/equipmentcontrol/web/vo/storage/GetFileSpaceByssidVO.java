package com.avst.equipmentcontrol.web.vo.storage;

import com.avst.equipmentcontrol.common.util.filespace.DriverSpaceParam;

import java.util.List;

public class GetFileSpaceByssidVO {

    private DriverSpaceParam filePathSpace;
    private List<DriverSpaceParam> filePathSpaceByParentNodePath;

    public DriverSpaceParam getFilePathSpace() {
        return filePathSpace;
    }

    public void setFilePathSpace(DriverSpaceParam filePathSpace) {
        this.filePathSpace = filePathSpace;
    }

    public List<DriverSpaceParam> getFilePathSpaceByParentNodePath() {
        return filePathSpaceByParentNodePath;
    }

    public void setFilePathSpaceByParentNodePath(List<DriverSpaceParam> filePathSpaceByParentNodePath) {
        this.filePathSpaceByParentNodePath = filePathSpaceByParentNodePath;
    }
}
