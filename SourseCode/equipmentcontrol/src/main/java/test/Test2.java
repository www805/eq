package test;

import com.avst.equipmentcontrol.common.util.JacksonUtil;
import com.avst.equipmentcontrol.common.util.XMLUtil;
import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.FDDealImpl;
import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.xmljsonobject.GetETRecordByIidXml;
import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.xmljsonobject.UploadFileByPathXml;
import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.xmljsonobject.Xml2Object;

public class Test2 {

    public static String rr="<?xml version=\"1.0\" encoding=\"GBK\"?><root><version>AICBH:1.0</version><ftp_pasv_upload_file t=\"do\"><rs>1</rs></ftp_pasv_upload_file></root>";

    public static void main(String[] args) {

        UploadFileByPathXml xml=Xml2Object.uploadFileByPathXml(rr);
        System.out.println(JacksonUtil.objebtToString(xml));



    }

}
