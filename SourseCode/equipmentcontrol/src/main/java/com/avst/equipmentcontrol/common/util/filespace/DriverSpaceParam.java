package com.avst.equipmentcontrol.common.util.filespace;

/**
 * 磁盘路径描述对象
 */
public class DriverSpaceParam {
    /**
     * 总容量大小
     */
    private long totalSpace;

    /**
     * 可用容量大小
     */
    private long freeSpace;

    /**
     * 总容量大小
     */
    private String totalSpace_str;

    /**
     * 可用容量大小
     */
    private String freeSpace_str;

    /**
     * 本级的路径
     */
    private String driverPath;

    /**
     * 名称
     */
    private String driverName;

    /**
     * 是否是文件夹,不是的话就是文件
     */
    private boolean folderBool=false;

    public boolean isFolderBool() {
        return folderBool;
    }

    public void setFolderBool(boolean folderBool) {
        this.folderBool = folderBool;
    }

    public String getDriverPath() {
        return driverPath;
    }

    public void setDriverPath(String driverPath) {
        this.driverPath = driverPath;
    }

    public long getTotalSpace() {
        return totalSpace;
    }

    public void setTotalSpace(long totalSpace) {
        this.totalSpace = totalSpace;
    }

    public long getFreeSpace() {
        return freeSpace;
    }

    public void setFreeSpace(long freeSpace) {
        this.freeSpace = freeSpace;
    }

    public String getTotalSpace_str() {
        return totalSpace_str;
    }

    public void setTotalSpace_str(String totalSpace_str) {
        this.totalSpace_str = totalSpace_str;
    }

    public String getFreeSpace_str() {
        return freeSpace_str;
    }

    public void setFreeSpace_str(String freeSpace_str) {
        this.freeSpace_str = freeSpace_str;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }
}
