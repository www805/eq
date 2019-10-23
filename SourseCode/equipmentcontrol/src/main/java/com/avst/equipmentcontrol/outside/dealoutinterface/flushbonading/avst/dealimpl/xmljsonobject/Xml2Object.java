package com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.xmljsonobject;

import com.avst.equipmentcontrol.common.util.JacksonUtil;
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

    /**
     * 解析刻录模式切换
     * @param xml
     * @return
     */
    public static int changeBurnModeXml( String xml) {
        try {

            String startstr="<burnmode t=\"rom\">";
            String endstr="</burnmode>";

            return jxXml(xml,startstr,endstr);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }


    /**
     * 解析 配置设备网络
     * @param xml
     * @return
     */
    public static int set_networkXml( String xml) {
        try {

            String startstr="<set_network t=\"set\">";
            String endstr="</set_network>";

            return jxXml(xml,startstr,endstr);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 解析 设置当前通道音量
     * @param xml
     * @return
     */
    public static int setAudioVolumeXml( String xml) {
        try {

            String startstr="<set_audio_cfg t=\"set\">";
            String endstr="</set_audio_cfg>";

            return jxXml(xml,startstr,endstr);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 解析 日志查询信息
     * @param xml
     * @return
     */
    public static GetFDLogXml getFDLogXml( String xml) {
        try {


            //判断格式 必须有log item totalpage，
            //  no active aud active，3条item 代表没有找到对应的任何一条日志

            if(xml.indexOf("log") > -1&&xml.indexOf("item") > -1&&xml.indexOf("totalpage") > -1){//说明请求成功

                GetFDLogXml getFDLogXml=new GetFDLogXml();

                //解析log
                String logstr=xml.substring(xml.indexOf("log"));
                logstr=logstr.substring(0,logstr.indexOf(">"));
                String t=logstr.substring(logstr.indexOf("t=")+3);
                t=t.substring(0,t.indexOf("\""));//截取t
                getFDLogXml.setT(t);
                String totalpage=logstr.substring(logstr.indexOf("totalpage=")+11);
                totalpage=totalpage.substring(0,totalpage.indexOf("\""));//截取totalpage
                getFDLogXml.setTotalpage(totalpage);
                String currpage=logstr.substring(logstr.indexOf("currpage=")+10);
                currpage=currpage.substring(0,currpage.indexOf("\""));//截取currpage
                getFDLogXml.setCurrpage(currpage);
                System.out.println(t+":t--"+totalpage+":totalpage--"+currpage+":currpage--");
                //解析item
                String itemstr=xml.substring(xml.indexOf(logstr)+logstr.length());
                itemstr=itemstr.substring(0,itemstr.indexOf("</log>"));//截取item集合

                List<FDLogItem> fdLogItemlist=new ArrayList<FDLogItem>();
                do{
                    FDLogItem fdLogItem=new FDLogItem();
                    String itemone=itemstr.substring(itemstr.indexOf("<item"));
                    itemone=itemone.substring(0,itemone.indexOf("</item>"));
                    //处理一个日志
                    String date=itemone.substring(itemone.indexOf("date=")+6);
                    date=date.substring(0,date.indexOf("\""));//date
                    try {
                        date=date.substring(0,4)+"-"+date.substring(4,6)+"-"+date.substring(6,8)+" "+date.substring(8,10)+":"+date.substring(10,12)+":"+date.substring(12);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    String tp=itemone.substring(itemone.indexOf("tp=")+4);
                    tp=tp.substring(0,tp.indexOf("\""));//tp
                    String message=itemone.substring(itemone.indexOf(">")+1);
                    System.out.println(date.trim()+":date----tp:"+tp.trim()+"----message:"+message.trim());
                    fdLogItem.setDate(date);
                    fdLogItem.setTp(tp);
                    fdLogItem.setMessage(message);
                    fdLogItemlist.add(fdLogItem);

                    itemstr=itemstr.substring(itemstr.indexOf("</item>")+8);
                }while(itemstr.indexOf("<item") > -1);

                if(null!=fdLogItemlist&&fdLogItemlist.size() > 0){
                    getFDLogXml.setFdLogItemlist(fdLogItemlist);
                    return getFDLogXml;
                }

            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 解析 获得设备能力信息
     * @param xml
     * @return
     */
    public static GetCapabilitySetXml getCapabilitySetXml( String xml) {
        try {

            GetCapabilitySetXml getCapabilitySetXml=new GetCapabilitySetXml();

            return (GetCapabilitySetXml)setJavaBeanParam(getCapabilitySetXml,xml);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解析 获得设备能力信息
     * @param xml
     * @return
     */
    public static GetAudioConfXml getAudioConfXml( String xml) {
        try {

            GetAudioConfXml getAudioConfXml=new GetAudioConfXml();

            if(StringUtils.isNotEmpty(xml)){
                if(xml.indexOf("audio_info" )> -1 && xml.indexOf("ch_audiomix" )> -1){//说明有数据，并且是可以用的

                    getAudioConfXml=(GetAudioConfXml)setJavaBeanParam(getAudioConfXml,xml);
                    String ch_audiomixSTR=jxXml(xml,"<ch_audiomix>","</ch_audiomix>",1);

                    //切割音频列表
                    if(StringUtils.isNotEmpty(ch_audiomixSTR)){
                        String[] audarr=ch_audiomixSTR.split("<aud");
                        if(null!=audarr&&audarr.length > 0){
                            List<Aud> audlist=new ArrayList<Aud>();
                            for(String str:audarr){

                                //解析audarr每一个参数
                                Aud aud=new Aud();
                                aud=(Aud)setJavaBeanParam2(aud,str);
                                if(null==aud||null==aud.getCh()||null==aud.getVolume()){
                                    continue;
                                }
                                audlist.add(aud);
                            }
                            getAudioConfXml.setAudList(audlist);
                        }
                    }
                }
            }
            return getAudioConfXml;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解析 获得设备网络信息
     * @param xml
     * @return
     */
    public static Get_networkVOXml get_networkVOXml( String xml) {
        try {

            Get_networkVOXml get_networkVOXml=new Get_networkVOXml();

            if(StringUtils.isNotEmpty(xml)){
                if(xml.indexOf("network_info" )> -1 && xml.indexOf("network" )> -1 && xml.indexOf("lan" )> -1){//说明有数据，并且是可以用的

                    String linnetworkSTR=jxXml(xml,"<network ","</network>",1);

                    //切割音频列表
                    if(StringUtils.isNotEmpty(linnetworkSTR)){
                        String[] arr=linnetworkSTR.split("<lan");
                        if(null!=arr&&arr.length > 0){
                            List<Lan_net> linlist=new ArrayList<Lan_net>();
                            for(String str:arr){

                                //解析arr每一个参数
                                Lan_net lan_net=new Lan_net();
                                lan_net=(Lan_net)setJavaBeanParam(lan_net,str);
                                if(null==lan_net||null==lan_net.getDevice()||null==lan_net.getIp()){
                                    continue;
                                }
                                linlist.add(lan_net);
                            }
                            get_networkVOXml.setLanList(linlist);
                        }
                    }
                }
            }
            return get_networkVOXml;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    /**
     * 解析 获得 audmap 音量信息
     * @param xml
     * @return
     */
    public static String getAudPowerMapXml( String xml) {
        try {

            String startstr="<audpow>";
            String endstr="</audpow>";

            return jxXml(xml,startstr,endstr,0);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解析获得集中管理配置
     * @param xml
     * @return
     */
    public static GetMiddleware_FTPXml getMiddleware_FTPXml( String xml) {
        try {

            GetMiddleware_FTPXml obj=new GetMiddleware_FTPXml();
            obj=(GetMiddleware_FTPXml)setJavaBeanParam(obj,xml);

            return obj;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解析设置集中管理配置（ftp）
     * @param xml
     * @return
     */
    public static SetMiddleware_FTPXml setMiddleware_FTPXml( String xml) {
        try {

            SetMiddleware_FTPXml obj=new SetMiddleware_FTPXml();
            obj=(SetMiddleware_FTPXml)setJavaBeanParam(obj,xml);

            return obj;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 解析获取光盘序列号
     * @param xml
     * @return
     */
    public static GetCDNumberXml getCDNumberXml( String xml) {
        try {

            String startstr="<disc_iid t=\"rom\">";
            String endstr="</disc_iid>";
            String disc_iid=jxXml(xml,startstr,endstr,0);

            GetCDNumberXml getCDNumberXml= null;
            try {
                startstr="<disc_iid0>";
                endstr="</disc_iid0>";
                String cd0=jxXml(disc_iid,startstr,endstr,0);
                Disc_iid disc_iid0=new Disc_iid();
                disc_iid0=(Disc_iid)setJavaBeanParam(disc_iid0,cd0);
                getCDNumberXml = new GetCDNumberXml();
                disc_iid0.setCdnum(0);
                getCDNumberXml.setDisc_iid0(disc_iid0);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                startstr="<disc_iid1>";
                endstr="</disc_iid1>";
                String cd1=jxXml(disc_iid,startstr,endstr,0);
                Disc_iid disc_iid1=new Disc_iid();
                disc_iid1=(Disc_iid)setJavaBeanParam(disc_iid1,cd1);
                disc_iid1.setCdnum(1);
                getCDNumberXml.setDisc_iid1(disc_iid1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return getCDNumberXml;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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

    private static String jxXml(String xml,String startstr,String endstr,int jx){
        String rr= null;
        try {
            if(StringUtils.isEmpty(xml)||xml.indexOf(startstr)<0||xml.indexOf(endstr)<0){
                return null;
            }
            rr = xml.substring(xml.indexOf(startstr)+(startstr.length()));
            rr=rr.substring(0,rr.indexOf(endstr));
            return rr.trim();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 给一个对象的所有属性进行自定义赋值
     * <audio_info ><mutech>32</mutech><exch0>15</exch0></audio_info> 解析多个类似的这个value里面的值
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

    /**
     * 给一个对象的所有属性进行自定义赋值
     * <aud cap="0" bind_viewmode="0"input_type="0"/>  解析类似的这个attribute上的数据
     * @param o
     * @return
     */
    public static Object setJavaBeanParam2(Object o,String xml) {
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

                        String startstr=field+"=\"";
                        String endstr="\"";
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
                "<network_info t=\"get\">\n" +
                "<network extern=\"0\">\n" +
                "<lan>\n" +
                "<device>eth0</device>\n" +
                "<type>3</type>\n" +
                "<ip>192.168.17.186</ip>\n" +
                "<netmask>255.255.255.0</netmask>\n" +
                "<gateway>192.168.17.254</gateway>\n" +
                "</lan>\n" +
                "<lan>\n" +
                "<device>eth1</device>\n" +
                "<type>3</type>\n" +
                "<ip>1.1.1.1</ip>\n" +
                "<netmask>255.255.0.0</netmask>\n" +
                "<gateway>192.1.1.1</gateway>\n" +
                "</lan>\n" +
                "</network>\n" +
                "</network_info>\n" +
                "</root>";

        System.out.println(JacksonUtil.objebtToString(get_networkVOXml(xml)));
    }

}
