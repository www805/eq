package com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.xmljsonobject;

import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.xmljsonobject.param.Ftp_pasv_upload_file;

public class UploadFileByPathXml {

    private  String version;

    private Ftp_pasv_upload_file ftp_pasv_upload_file;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Ftp_pasv_upload_file getFtp_pasv_upload_file() {
        return ftp_pasv_upload_file;
    }

    public void setFtp_pasv_upload_file(Ftp_pasv_upload_file ftp_pasv_upload_file) {
        this.ftp_pasv_upload_file = ftp_pasv_upload_file;
    }
}
