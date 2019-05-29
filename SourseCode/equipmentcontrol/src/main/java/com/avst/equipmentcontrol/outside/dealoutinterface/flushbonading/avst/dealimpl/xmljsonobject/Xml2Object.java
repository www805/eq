package com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.xmljsonobject;

import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.xmljsonobject.param.File;
import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.xmljsonobject.param.Files;
import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.xmljsonobject.param.Ftp_pasv_upload_file;
import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.xmljsonobject.param.Get_rec_files;
import com.thoughtworks.xstream.XStream;

public class Xml2Object {

    /**
     * API 接口-获取录像 ID 的录像文件列表 xml解析对象
     * 吐血
     * @param xml
     * @return
     */
    public static GetETRecordByIidXml xml2GetETRecordByIidXml( String xml) {
        try {

            XStream xstream = new XStream();
            xstream.alias("root", GetETRecordByIidXml.class);

            xstream.alias("get_rec_files", Get_rec_files.class);
            xstream.aliasAttribute(Get_rec_files.class, "t", "t");
            xstream.aliasAttribute(Get_rec_files.class, "currpage", "currpage");
            xstream.aliasAttribute(Get_rec_files.class, "totalpage", "totalpage");
            xstream.aliasField("files", Get_rec_files.class, "files");
            xstream.addImplicitCollection(Files.class, "fileList");
            xstream.aliasField("file", Files.class, "file");
            xstream.alias("file", File.class);
            xstream.aliasField("name", File.class, "name");
            xstream.aliasField("path", File.class, "path");
            xstream.aliasField("stinfopath", File.class, "stinfopath");
            xstream.aliasAttribute(File.class, "fsize", "fsize");
            xstream.aliasAttribute(File.class, "stime", "stime");
            xstream.aliasAttribute(File.class, "etime", "etime");
            Object o=xstream.fromXML(xml);
            return (GetETRecordByIidXml)o;

        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 解析录像上传文件到服务器的xml
     * @param xml
     * @return
     */
    public static UploadFileByPathXml uploadFileByPathXml( String xml) {
        try {

            XStream xstream = new XStream();
            xstream.alias("root", UploadFileByPathXml.class);
            xstream.aliasField("version", UploadFileByPathXml.class, "version");
            xstream.aliasField("ftp_pasv_upload_file", UploadFileByPathXml.class, "ftp_pasv_upload_file");

            xstream.alias("ftp_pasv_upload_file", Ftp_pasv_upload_file.class);
            xstream.aliasAttribute(Ftp_pasv_upload_file.class, "t", "t");
            xstream.aliasField("rs", Ftp_pasv_upload_file.class, "rs");

            Object o=xstream.fromXML(xml);
            return (UploadFileByPathXml)o;

        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }




}
