package com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.xmljsonobject;

import com.avst.equipmentcontrol.common.util.LogUtil;
import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.xmljsonobject.param.*;
import com.thoughtworks.xstream.XStream;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    /**
     * 解析获取ftp上传指定上传文件的上传进度条
     * @param xml
     * @return
     */
    public static GetFTPUploadSpeedXml getFTPUploadSpeedXml( String xml) {
        try {

            XStream xstream = new XStream();
            xstream.alias("root", GetFTPUploadSpeedXml.class);
            xstream.alias("ftp_process_status", GetFTPUploadSpeedXml.class);

            xstream.aliasAttribute(Ftp_process_status.class, "t", "t");
            xstream.aliasField("fi", Ftp_process_status.class, "fi");
            xstream.aliasAttribute(Fi.class, "rate", "rate");
            xstream.aliasAttribute(Fi.class, "progress", "progress");
            xstream.aliasAttribute(Fi.class, "fipath", "fipath");
            xstream.aliasAttribute(Fi.class, "svrip", "svrip");

            Object o=xstream.fromXML(xml);
            return (GetFTPUploadSpeedXml)o;

        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 解析获取设备状态的参数集合
     * @param xml
     * @return
     */
    public static CheckFDStateXml getCheckFDStateXml( String xml) {
        try {

            CheckFDStateXml checkFDStateXml=new CheckFDStateXml();
            checkFDStateXml=(CheckFDStateXml)setJavaBeanParam(checkFDStateXml,xml);

            return checkFDStateXml;

        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解析获取当前配置片头字段
     * @param xml
     * @return
     */
    public static PtdjconstXml getptdjconstXml( String xml) {
        try {
            if(StringUtils.isEmpty(xml)){
                LogUtil.intoLog(4,Xml2Object.class,"getptdjconstXml xml is null,传入的xml字符串为空");
                return null;
            }

            PtdjconstXml obj=new PtdjconstXml();
            obj=(PtdjconstXml)setJavaBeanParam(obj,xml);

            return obj;

        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解析开始光盘刻录
     * @param xml
     * @return
     */
    public static int startRec_RomXml( String xml) {
        try {

            String startstr="<startrec t=\"rom\">";
            String endstr="</startrec>";
            return jxXml(xml,startstr,endstr);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 解析开始光盘暂停刻录
     * @param xml
     * @return
     */
    public static int pauseRec_RomXml( String xml) {
        try {

            String startstr="<pauserec t=\"rom\">";
            String endstr="</pauserec>";

            return jxXml(xml,startstr,endstr);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 解析开始光盘继续刻录
     * @param xml
     * @return
     */
    public static int goonRec_RomXml( String xml) {
        try {

            String startstr="<goonrec t=\"rom\">";
            String endstr="</goonrec>";

            return jxXml(xml,startstr,endstr);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 解析开始光盘结束刻录
     * @param xml
     * @return
     */
    public static int stopRec_RomXml( String xml) {
        try {

            String startstr="<stoprec t=\"rom\">";
            String endstr="</stoprec>";

            return jxXml(xml,startstr,endstr);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }


    /**
     * 解析光驱出仓
     * @param xml
     * @return
     */
    public static int eject_RomXml( String xml) {
        try {

            String startstr="<eject t=\"rom\">";
            String endstr="</eject>";

            return jxXml(xml,startstr,endstr);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }


    /**
     * 解析光驱出仓
     * @param xml
     * @return
     */
    public static int closetray_RomXml( String xml) {
        try {

            String startstr="<closetray t=\"rom\">";
            String endstr="</closetray>";

            return jxXml(xml,startstr,endstr);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 解析片头叠加
     * @param xml
     * @return
     */
    public static int ptdjXml( String xml) {
        try {

            String startstr="<ptdj t=\"view\">";
            String endstr="</ptdj>";

            return jxXml(xml,startstr,endstr);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 解析云台控制
     * @param xml
     * @return
     */
    public static int yuntaiControlXml( String xml) {
        try {

            String startstr="<ptz t=\"ctrl\">";
            String endstr="</ptz>";

            return jxXml(xml,startstr,endstr);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }




private static int jxXml(String xml,String startstr,String endstr){
    String rr= null;
    try {
        if(StringUtils.isEmpty(xml)||xml.indexOf(startstr)<0||xml.indexOf(endstr)<0){
            return-1;
        }
        rr = xml.substring(xml.indexOf(startstr)+(startstr.length()));
        rr=rr.substring(0,rr.indexOf(endstr));
        return Integer.parseInt(rr);
    } catch (Exception e) {
        e.printStackTrace();
    }
    return -1;
}

    /**
     * 给一个对象的所有属性进行自定义赋值
     * @param o
     * @return
     */
    public static Object setJavaBeanParam(Object o,String xml) {
        try {

            if(null==o){
                return null;
            }
            Class<?> clz = Class.forName(o.getClass().getName());
            Object javabean = clz.newInstance(); // 构建对象
            Method[] methods = clz.getMethods(); // 获取所有方法
            for (Method method : methods) {
                try {
                    String field = method.getName(); // 截取属性名
                    if (field.startsWith("set")) {
                        field = field.substring(field.indexOf("set") + 3);//shuxingming
                        field = field.toLowerCase().charAt(0) + field.substring(1);//小写化

                        String startstr="<"+field+">";
                        String endstr="</"+field+">";
                        if(xml.indexOf(startstr) > -1){
                            String rr=xml.substring(xml.indexOf(startstr)+(startstr.length()));
                            rr=rr.substring(0,rr.indexOf(endstr));
                            if(!rr.trim().equals("")&&rr.indexOf("<![CDATA[") > -1&&rr.indexOf("]]>") > 0){

                                rr=rr.replace("]]>","");
                                rr=rr.replace("<![CDATA[","");
                            }
                            rr=rr.trim();

                            method.invoke(javabean, rr);
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            return javabean;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        String xml="<root>\n" +
                "<version>AICBH:1.0</version>\n" +
                "<ptdjconst>\n" +
                "<line0>案件编号</line0>\n" +
                "<line1>案件名称</line1>\n" +
                "<line2>案件类型</line2>\n" +
                "<line3>案 由</line3>\n" +
                "<line4>审讯类型</line4>\n" +
                "<line5>办案部门</line5>\n" +
                "<line6>被询(讯)问人</line6>\n" +
                "<line7>询(讯)问人</line7>\n" +
                "<line8>录制(记录)员</line8>\n" +
                "<line9>询(讯)问地址</line9>\n" +
                "</ptdjconst>\n" +
                "</root>";

        PtdjconstXml state=getptdjconstXml(xml);
        System.out.println(state.toList().size());
    }

}
