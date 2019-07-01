package com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.xmljsonobject;

import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.xmljsonobject.param.*;
import com.thoughtworks.xstream.XStream;

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
                "<dev_version>1.906051</dev_version>\n" +
                "<devmid_id>\n" +
                "<![CDATA[ sb3 ]]>\n" +
                "</devmid_id>\n" +
                "<cpu>13</cpu>\n" +
                "<ddr_used>45</ddr_used>\n" +
                "<ddr_total>1008</ddr_total>\n" +
                "<serialnumber>01-23-38-3f-61-21-38-0a-ee</serialnumber>\n" +
                "<factory>1001</factory>\n" +
                "<hw>350</hw>\n" +
                "<sw>189</sw>\n" +
                "<tcp_port>9996</tcp_port>\n" +
                "<map_tcp_port>8000</map_tcp_port>\n" +
                "<laninfo>\n" +
                "<count>2</count>\n" +
                "<lan0>\n" +
                "<ip>192.168.1.65</ip>\n" +
                "<mac>8A:72:3F:78:65:11</mac>\n" +
                "<mask>255.255.255.0</mask>\n" +
                "<gtway>0.0.0.0</gtway>\n" +
                "<link>0</link>\n" +
                "</lan0>\n" +
                "<lan1>\n" +
                "<ip>192.168.6.163</ip>\n" +
                "<mac>00:00:00:00:00:00</mac>\n" +
                "<mask>255.255.255.0</mask>\n" +
                "<gtway>192.168.6.254</gtway>\n" +
                "<link>0</link>\n" +
                "</lan1>\n" +
                "</laninfo>\n" +
                "<device_ability>234087039</device_ability>\n" +
                "<capacitys>\n" +
                "<networkstreamset_capacity>1359020305</networkstreamset_capacity>\n" +
                "<extinputset_capacity>66</extinputset_capacity>\n" +
                "<viewmodeset_capacity>33540</viewmodeset_capacity>\n" +
                "<diskset_capacity>15</diskset_capacity>\n" +
                "<burnset_capacity>0</burnset_capacity>\n" +
                "</capacitys>\n" +
                "<running_taskID>0</running_taskID>\n" +
                "<onvoicetalk>0</onvoicetalk>\n" +
                "<onlanlinked>1</onlanlinked>\n" +
                "<onmsgtlask>0</onmsgtlask>\n" +
                "<oncalling>0</oncalling>\n" +
                "<splitsize>4068474880</splitsize>\n" +
                "<dh_set>1</dh_set>\n" +
                "<temperature_value>30</temperature_value>\n" +
                "<humidity_value>38</humidity_value>\n" +
                "<diskrec_isrec>0</diskrec_isrec>\n" +
                "<diskrec_from>0</diskrec_from>\n" +
                "<diskrec_status>0</diskrec_status>\n" +
                "<diskrec_begintime>00:00:00</diskrec_begintime>\n" +
                "<diskrec_continuettime>0</diskrec_continuettime>\n" +
                "<disk_loaded>1</disk_loaded>\n" +
                "<disk_freespace>472309</disk_freespace>\n" +
                "<disk_totalspace>500107</disk_totalspace>\n" +
                "<reciid>\n" +
                "<![CDATA[ ]]>\n" +
                "</reciid>\n" +
                "<disk_recdir/>\n" +
                "<disk_recpath/>\n" +
                "<diskrec_list>\n" +
                "<diskrec ch=\"0\" isrec=\"0\" ispause=\"0\"/>\n" +
                "<diskrec ch=\"1\" isrec=\"0\" ispause=\"0\"/>\n" +
                "<diskrec ch=\"2\" isrec=\"0\" ispause=\"0\"/>\n" +
                "<diskrec ch=\"3\" isrec=\"0\" ispause=\"0\"/>\n" +
                "<diskrec ch=\"4\" isrec=\"0\" ispause=\"0\"/>\n" +
                "<diskrec ch=\"5\" isrec=\"0\" ispause=\"0\"/>\n" +
                "<diskrec ch=\"6\" isrec=\"0\" ispause=\"0\"/>\n" +
                "<diskrec ch=\"7\" isrec=\"0\" ispause=\"0\"/>\n" +
                "</diskrec_list>\n" +
                "<dvdnum>2</dvdnum>\n" +
                "<roma_status>0</roma_status>\n" +
                "<romb_status>0</romb_status>\n" +
                "<roma_isburn>0</roma_isburn>\n" +
                "<romb_isburn>0</romb_isburn>\n" +
                "<roma_isfinishburn>0</roma_isfinishburn>\n" +
                "<romb_isfinishburn>0</romb_isfinishburn>\n" +
                "<roma_disktype>0</roma_disktype>\n" +
                "<romb_disktype>0</romb_disktype>\n" +
                "<roma_discCap>0</roma_discCap>\n" +
                "<romb_discCap>0</romb_discCap>\n" +
                "<roma_discCapUsed>0</roma_discCapUsed>\n" +
                "<romb_discCapUsed>0</romb_discCapUsed>\n" +
                "<roma_setburntime>0</roma_setburntime>\n" +
                "<romb_setburntime>0</romb_setburntime>\n" +
                "<roma_begintime>00:00:00</roma_begintime>\n" +
                "<romb_begintime>00:00:00</romb_begintime>\n" +
                "<roma_lefttime>0</roma_lefttime>\n" +
                "<romb_lefttime>0</romb_lefttime>\n" +
                "<burn_mode>0</burn_mode>\n" +
                "<burn_status>0</burn_status>\n" +
                "<selburntime>21600</selburntime>\n" +
                "<audioout_type>1</audioout_type>\n" +
                "<burn_syn_yw>0</burn_syn_yw>\n" +
                "<next_burndev>0</next_burndev>\n" +
                "<prev_burndev>0</prev_burndev>\n" +
                "<ypjl>0</ypjl>\n" +
                "<subypjl>0</subypjl>\n" +
                "<audswitch_mode_type>0</audswitch_mode_type>\n" +
                "<calp>0</calp>\n" +
                "<codecver>11</codecver>\n" +
                "<audiosample>48000</audiosample>\n" +
                "<tusrs>1</tusrs>\n" +
                "<dswch>4</dswch>\n" +
                "<maxvichannel>8</maxvichannel>\n" +
                "<audprocessmuti_type>1</audprocessmuti_type>\n" +
                "<aec_no_liveoutput>0</aec_no_liveoutput>\n" +
                "<remote_audioout_type>0</remote_audioout_type>\n" +
                "<inuse_remote_audioout_type>0</inuse_remote_audioout_type>\n" +
                "<audmode>0</audmode>\n" +
                "<audspeaker>1</audspeaker>\n" +
                "<audmic>0</audmic>\n" +
                "<audout>1</audout>\n" +
                "<aotype>1</aotype>\n" +
                "<audio_enable>1</audio_enable>\n" +
                "<subscreen>0</subscreen>\n" +
                "<dbmode>0</dbmode>\n" +
                "<mainview_index>0</mainview_index>\n" +
                "<subview_index>0</subview_index>\n" +
                "<mem_mainview_index>0</mem_mainview_index>\n" +
                "<mem_subview_index>0</mem_subview_index>\n" +
                "<rs232baud>9600</rs232baud>\n" +
                "<debuginfo>\n" +
                "<dmsg_lst>BASE:0,MAIN:0,ROM1:0,ROM2:0,BUS:0</dmsg_lst>\n" +
                "<key_lst>0</key_lst>\n" +
                "<httpmsg_lst/>\n" +
                "<thr_map>tasktimer[0] [1 0 1 1 1 0 0 224]</thr_map>\n" +
                "<sys_tick>113</sys_tick>\n" +
                "</debuginfo>\n" +
                "<audpow>0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0</audpow>\n" +
                "</root>";

        CheckFDStateXml state=getCheckFDStateXml(xml);
        System.out.println(state.getDevmid_id());
    }

}
