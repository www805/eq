package com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.req;

public class FtpUploadRecordByIidParam extends BaseParam {

    /**
     * 录像时指定的 id 或同步开始刻录录像产生的录像 id
     */
    private String rec_id;
    /**
     *值参考如下：
     *             const unsigned short store_devtype_usbdev = 1;//U 盘
     * const unsigned short store_devtype_usbhd = 2;//
     * const unsigned short store_devtype_satahd = 3;//sda
     */
    private String dev_type;
    /**
     *0-4 对应分区，默认第一个分区查询，分区总数
     *     通过 disk_info 命令获得
     */
    private String partition_index;
    /**
     * 0|1 默认值为 0,0 为不重新上传，值为 1 时则优先判断
     */
    private String reupload;
}
