package com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.xmljsonobject;


import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.xmljsonobject.param.Ftp_process_status;


public class GetFTPUploadSpeedXml {

    private String version;

    private Ftp_process_status ftp_process_status;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Ftp_process_status getFtp_process_status() {
        return ftp_process_status;
    }

    public void setFtp_process_status(Ftp_process_status ftp_process_status) {
        this.ftp_process_status = ftp_process_status;
    }
}
