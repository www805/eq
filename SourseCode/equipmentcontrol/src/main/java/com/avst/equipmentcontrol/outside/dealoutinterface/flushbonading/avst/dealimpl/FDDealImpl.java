package com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl;

import com.avst.equipmentcontrol.common.util.DateUtil;
import com.avst.equipmentcontrol.common.util.LogUtil;
import com.avst.equipmentcontrol.common.util.OpenUtil;
import com.avst.equipmentcontrol.common.util.XMLUtil;
import com.avst.equipmentcontrol.common.util.baseaction.Code;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.req.*;
import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.vo.*;
import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.xmljsonobject.*;
import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.xmljsonobject.param.Disc_iid;
import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.xmljsonobject.param.File;
import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.xmljsonobject.param.Files;
import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.xmljsonobject.param.Get_rec_files;
import com.avst.requestUtil.HttpRequest;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 接口处理类
 */
@Service
public class FDDealImpl implements FDInterface{

    @Override
    public RResult<CheckFDStateVO> CheckFDState(CheckFDStateParam param,RResult<CheckFDStateVO> result) {

        String user=param.getUser();
        String passwd=param.getPasswd();
        int type=param.getStateType();
        String ip=param.getIp();
        int port=param.getPort();

        if(StringUtils.isEmpty(ip)||StringUtils.isEmpty(user)||StringUtils.isEmpty(passwd)){
            result.setMessage("有部分参数为空");
            LogUtil.intoLog(this.getClass(),param.toString()+"--------reg--CheckFDState");
            return result;
        }

        String url="http://"+ip+":"+port+"/stcmd" ;
        String regparam="action=get&type=devstatus&authvusr="+user+"&authpwd="+passwd;
        LogUtil.intoLog(this.getClass(),url+":url  regparam:"+regparam);
        String rr= HttpRequest.readContentFromGet_noencode(url,regparam,20000);//大一点超时时间

        CheckFDStateXml xml=Xml2Object.getCheckFDStateXml(rr);

        if(null!=xml){//说明请求有正确的返回

            CheckFDStateVO CheckFDStateVO=new CheckFDStateVO();
            CheckFDStateVO.setStateType(type);
            if(type==0){
                CheckFDStateVO.setData(xml);
            }
            result.changeToTrue(CheckFDStateVO);

        }else{
            LogUtil.intoLog(this.getClass(),xml+":xml 请求返回为空 reg");
            result.setMessage("设备状态请求异常");
        }
        return result;
    }

    @Override
    public RResult<StartRecVO> startRec(StartRecParam param,RResult<StartRecVO> result) {

        String al=param.getAl();
        String iid=param.getIid();
        String ip=param.getIp();
        String passwd=param.getPasswd();
        String user=param.getUser();
        int port=param.getPort();

        if(StringUtils.isEmpty(ip)||StringUtils.isEmpty(user)||StringUtils.isEmpty(passwd)){
            result.setMessage("有部分参数为空");
            LogUtil.intoLog(this.getClass(),param.toString()+"--------reg--startRec");
            return result;
        }
        String url="http://"+ip+":"+port+"/stcmd" ;
        String regparam="action=do&type=disk&cmd=startrec&al="+al +
                "&iid="+iid+"&authvusr="+user+"&authpwd="+passwd;
        LogUtil.intoLog(this.getClass(),url+":url  regparam:"+regparam);
        String rr= HttpRequest.readContentFromGet_noencode(url,regparam);

        StartRecXml xml=new StartRecXml();
        xml=(StartRecXml)XMLUtil.xmlToStr(xml,rr);

        if(null!=xml&&null!=xml.getStartrec()&&xml.getStartrec().equals("0")){
            //xml.getStartrec()
            //0：开始录像成功
            //99：提交请求前已经开始硬盘录像
            //4：设备内部错误，也许路径不正确
            //5：设备内部错误，也许路径不正确
            //6：硬盘容量不足
            //-1：没有找到硬盘
            StartRecVO startRecVO=new StartRecVO();
            startRecVO.setIid(xml.getIid());
            result.changeToTrue(startRecVO);
        }else{
            result.setMessage("开启录像失败 ");
        }

        return result;
    }

    @Override
    public RResult<StopRecVO> stopRec(StopRecParam param,RResult<StopRecVO> result) {
        String ip=param.getIp();
        String passwd=param.getPasswd();
        String user=param.getUser();
        int port=param.getPort();

        if(StringUtils.isEmpty(ip)||StringUtils.isEmpty(user)||StringUtils.isEmpty(passwd)){
            result.setMessage("有部分参数为空");
            LogUtil.intoLog(this.getClass(),param.toString()+"--------reg--stopRec");
            return result;
        }

        String url="http://"+ip+":"+port+"/stcmd" ;
        String regparam="action=do&type=disk&cmd=stoprec"+
                "&authvusr="+user+"&authpwd="+passwd;
        LogUtil.intoLog(this.getClass(),url+":url --time:"+ (new Date().getTime()) +" regparam:"+regparam);
        String rr= HttpRequest.readContentFromGet_noencode(url,regparam,20000);//大一点超时时间
        LogUtil.intoLog(this.getClass(),rr+":rr 结束录像--time:"+ (new Date().getTime()) );
        StopRecXml xml=new StopRecXml();
        xml=(StopRecXml)XMLUtil.xmlToStr(xml,rr);

        if(null!=xml&&null!=xml.getStoprec()&&xml.getStoprec().equals("1")){
            //xml.getStoprec()
            // 正确提交停止录像命令，返回值始终为
            result.changeToTrue();

        }else{
            result.setMessage("结束录像失败 ");
        }

        return result;
    }

    @Override
    public RResult<ShutdownVO> shutdown(ShutdownParam param,RResult<ShutdownVO> result) {
        return null;
    }

    @Override
    public RResult<SetFTPModeVO> setFTPMode(SetFTPModeParam param,RResult<SetFTPModeVO> result) {
        return null;
    }

    @Override
    public RResult<GetFTPUploadSpeedVO> getFTPUploadSpeed(GetFTPUploadSpeedParam param,RResult<GetFTPUploadSpeedVO> result) {

        String ip=param.getIp();
        String passwd=param.getPasswd();
        String user=param.getUser();
        int port=param.getPort();

        if(StringUtils.isEmpty(ip)||StringUtils.isEmpty(user)||StringUtils.isEmpty(passwd)){
            result.setMessage("有部分参数为空");
            LogUtil.intoLog(this.getClass(),param.toString()+"----------setMiddleware_FTP");
            return result;
        }

        String url="http://"+ip+":"+port+"/stcmd" ;
        String regparam="action=api&type=ftp_process_status"+
                "&authvusr="+user+"&authpwd="+passwd;
        LogUtil.intoLog(this.getClass(),url+":url  regparam:"+regparam);
        String rr= HttpRequest.readContentFromGet_noencode(url,regparam,20000);//大一点超时时间

        GetFTPUploadSpeedXml xml=Xml2Object.getFTPUploadSpeedXml(rr);

        if(null!=xml){
            //设置ftp成功
            result.changeToTrue();
            System.out.println(xml);

        }else{
            result.setMessage("ftp请求进度条失败 --");
        }

        return null;
    }

    @Override
    public RResult<FtpUploadRecordByIidVO> ftpUploadRecordByIid(FtpUploadRecordByIidParam param,RResult<FtpUploadRecordByIidVO> result) {
        return null;
    }

    @Override
    public RResult<GetDiskInfoVO> getDiskInfo(GetDiskInfoParam param,RResult<GetDiskInfoVO> result) {
        return null;
    }

    @Override
    public RResult<GetMiddleware_FTPVO> getMiddleware_FTP(GetMiddleware_FTPParam param, RResult<GetMiddleware_FTPVO> result) {

        String ip=param.getIp();
        String passwd=param.getPasswd();
        String user=param.getUser();
        int port=param.getPort();

        if(StringUtils.isEmpty(ip)||StringUtils.isEmpty(user)||StringUtils.isEmpty(passwd)){
            result.setMessage("有部分参数为空");
            LogUtil.intoLog(this.getClass(),param.toString()+"----------getMiddleware_FTP");
            return result;
        }

        String url="http://"+ip+":"+port+"/stcmd" ;
        String regparam="action=get&type=middleware"+
                "&authvusr="+user+"&authpwd="+passwd+"&usr="+user+"&pwd="+passwd;
        LogUtil.intoLog(this.getClass(),url+":url  regparam:"+regparam);
        String rr= HttpRequest.readContentFromGet_noencode(url,regparam,20000);//大一点超时时间
        LogUtil.intoLog(this.getClass(),url+":url  rr:"+rr);
        GetMiddleware_FTPXml xml=Xml2Object.getMiddleware_FTPXml(rr);

        if(null!=xml){
            try {
                Gson gson=new Gson();
                GetMiddleware_FTPVO getMiddleware_ftpvo=gson.fromJson(gson.toJson(xml),GetMiddleware_FTPVO.class);
                result.changeToTrue(getMiddleware_ftpvo);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }else{
            result.setMessage("请求集中管理ftp配置失败 --");
        }

        return null;
    }

    @Override
    public RResult<SetMiddleware_FTPVO> setMiddleware_FTP(SetMiddleware_FTPParam param,RResult<SetMiddleware_FTPVO> result) {

        String ip=param.getIp();
        String passwd=param.getPasswd();
        String user=param.getUser();
        int port=param.getPort();

        if(StringUtils.isEmpty(ip)||StringUtils.isEmpty(user)||StringUtils.isEmpty(passwd)){
            result.setMessage("有部分参数为空");
            LogUtil.intoLog(this.getClass(),param.toString()+"----------setMiddleware_FTP");
            return result;
        }

        String url="http://"+ip+":"+port+"/stcmd" ;
        String regparam="action=set&type=set_middleware"+
                "&enable="+param.getEnable()+"&limit_speed="+param.getLimit_speed()+"&search_filter="+param.getSearch_filter()+"&filter_enable="+param.getFilter_enable()+
                "&serverip="+param.getServerip()+"&hreadbeatip="+param.getHreadbeatip()+"&servicename="+param.getServicename()+"&deviceid="+param.getDeviceid()+
                "&svrusr="+param.getSvrusr()+"&svrpwd="+param.getSvrpwd()+
                "&restart="+param.getRestart()+"&serverport="+param.getServerport()+"&pasvmode="+param.getPasvmode()+
                "&authvusr="+user+"&authpwd="+passwd+"&usr="+user+"&pwd="+passwd;
        LogUtil.intoLog(this.getClass(),url+":url  regparam:"+regparam);
        String rr= HttpRequest.readContentFromGet_noencode(url,regparam,20000);//大一点超时时间
        LogUtil.intoLog(this.getClass(),url+":url  rr:"+rr);
        SetMiddleware_FTPXml xml=Xml2Object.setMiddleware_FTPXml(rr);

        if(null!=xml){
            try {

                if(xml.getMsg().equals("200")){//判断msg==200就说明设置成功
                    result.changeToTrue();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }else{
            result.setMessage("请求集中管理ftp配置失败 --");
        }

        return result;
    }

    @Override
    /**
     * 要测试是否已经排了序的，排序是否正常，程序中冒泡是否正常
     */
    public RResult<GetETRecordByIidVO> getETRecordByIid(GetETRecordByIidParam param, RResult<GetETRecordByIidVO> result) {

        String ip=param.getIp();
        String passwd=param.getPasswd();
        String user=param.getUser();
        int port=param.getPort();

        if(StringUtils.isEmpty(ip)||StringUtils.isEmpty(user)||StringUtils.isEmpty(passwd)){
            result.setMessage("有部分参数为空");
            LogUtil.intoLog(this.getClass(),param.toString()+"----------getETRecordPathByIid");
            return result;
        }

        String url="http://"+ip+":"+port+"/stcmd" ;
        String regparam="action=api&type=get_rec_files"+
                "&rec_id="+param.getRec_id()+"&dev_type="+param.getDev_type()+"&&partition_index="+param.getPartition_index()+
                "&authvusr="+user+"&authpwd="+passwd;
        LogUtil.intoLog(this.getClass(),url+":url getETRecordByIid regparam:"+regparam);
        String rr= HttpRequest.readContentFromGet_noencode(url,regparam,20000);//大一点超时时间
        LogUtil.intoLog(this.getClass(),rr+"--getETRecordByIid");
        GetETRecordByIidXml xml=Xml2Object.xml2GetETRecordByIidXml(rr);

        if(null!=xml&&null!=xml.getGet_rec_files()){
            GetETRecordByIidVO getETRecordPathByIidVO=new GetETRecordByIidVO();
            try {//可能报错，返回的对象中的参数首尾都没有去空格
                List<File> fileList=xml.getGet_rec_files().getFiles().getFileList();
                if(null!=fileList&&fileList.size() > 0){

                    for(int i=0;i<fileList.size()-1;i++) {//冒泡排序
                        for(int j=0;j<fileList.size()-i-1;j++) {
                            try {
                                File file1 = fileList.get(j);
                                File file2 = fileList.get(j + 1);
                                long stime1 = Long.parseLong(file1.getStime());
                                long stime2 = Long.parseLong(file2.getStime());
                                if (stime1 > stime2) {
                                    File temp = file1;
                                    file1 = file2;
                                    file2 = temp;
                                    fileList.set(j,file1);
                                    fileList.set((j+1),file2);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    getETRecordPathByIidVO.setFileList(fileList);
                    result.changeToTrue(getETRecordPathByIidVO);
                }else{
                    result.setMessage("根据iid获取视频文件没有找到任何一个文件");
                    LogUtil.intoLog(this.getClass(),param.getRec_id()+"：iid----------根据iid获取视频文件没有找到任何一个文件");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            result.setMessage("根据iid获取视频文件失败 --");
        }

        return result;
    }


    @Override
    public RResult<UploadFileByPathVO> uploadFileByPath(UploadFileByPathParam param, RResult<UploadFileByPathVO> result) {

        String ip=param.getIp();
        String passwd=param.getPasswd();
        String user=param.getUser();
        int port=param.getPort();
        String recordpath=param.getRecordpath();
        int reupload=param.getReupload();

        if(StringUtils.isEmpty(ip)||StringUtils.isEmpty(user)||StringUtils.isEmpty(passwd)){
            result.setMessage("有部分参数为空");
            LogUtil.intoLog(this.getClass(),param.toString()+"----------uploadFileByPath");
            return result;
        }

        String url="http://"+ip+":"+port+"/stcmd" ;
        String regparam="action=api&type=ftp_pasv_upload_file"+
                "&reupload="+reupload+"&path="+recordpath+
                "&authvusr="+user+"&authpwd="+passwd;
        String rr= HttpRequest.readContentFromGet_noencode(url,regparam,20000);//大一点超时时间
        LogUtil.intoLog(this.getClass(),rr+"--uploadFileByPath");
        UploadFileByPathXml xml=Xml2Object.uploadFileByPathXml(rr);

        //到底是rs=0还是rs=1
        if(null!=xml&&null!=xml.getFtp_pasv_upload_file()&&(xml.getFtp_pasv_upload_file().getRs().trim().equals("1")||xml.getFtp_pasv_upload_file().getRs().trim().equals("0"))){

            try {
                UploadFileByPathVO uploadFileByPathVO=new UploadFileByPathVO();
                result.changeToTrue(uploadFileByPathVO);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            result.setMessage("上传文件到设备失败 --");
        }
        return result;
    }

    public RResult<UploadServiceByIidVO> uploadServiceByIid(UploadServiceByIidParam param, RResult<UploadServiceByIidVO> result){

        String iid=param.getIid();
        String soursepath=param.getSoursepath();
        String ip=param.getIp();
        String passwd=param.getPasswd();
        String user=param.getUser();
        int port=param.getPort();

        if(StringUtils.isEmpty(ip)||StringUtils.isEmpty(user)||StringUtils.isEmpty(passwd)){
            result.setMessage("有部分参数为空");
            LogUtil.intoLog(this.getClass(),param.toString()+"----------uploadFileByPath");
            return result;
        }

//        String uuid=OpenUtil.getUUID_32();
//        String url="http://"+ip+":"+port+"/httpFileUpload" ;
//        String regparam="token="+ uuid +"&upload_task_id="+uuid+
//                "&dstPath="+reupload+"&fileName="+recordpath+
//                "&action="+upload_file+"&fileName="+recordpath+
//                "&filebinary="+upload_file;
//        String rr= HttpRequest.readContentFromGet_noencode(url,regparam);
//        LogUtil.intoLog(this.getClass(),rr+"--uploadFileByPath");
//        UploadFileByPathXml xml=Xml2Object.uploadFileByPathXml(rr);
//
//        if(null!=xml&&null!=xml.getFtp_pasv_upload_file()&&xml.getFtp_pasv_upload_file().getRs().trim().equals("1")){
//
//            try {
//                UploadFileByPathVO uploadFileByPathVO=new UploadFileByPathVO();
//                result.changeToTrue(uploadFileByPathVO);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }else{
//            result.setMessage("设置ftp失败 --");
//        }

        return result;
    }

    @Override
    public RResult<GetptdjconstVO> getptdjconst(GetptdjconstParam param, RResult<GetptdjconstVO> result) {

        String ip=param.getIp();
        String passwd=param.getPasswd();
        String user=param.getUser();
        int port=param.getPort();

        if(StringUtils.isEmpty(ip)||StringUtils.isEmpty(user)||StringUtils.isEmpty(passwd)){
            result.setMessage("有部分参数为空");
            LogUtil.intoLog(this.getClass(),param.toString()+"----------getptdjconst");
            return result;
        }

        String url="http://"+ip+":"+port+"/stcmd" ;
        String regparam="action=get&type=ptdjconst"+
                "&usr="+user+"&pwd="+passwd+"&authvusr="+user+"&authpwd="+passwd;
        String rr= HttpRequest.readContentFromGet_noencode(url,regparam,20000,"gbk");//大一点超时时间
        LogUtil.intoLog(this.getClass(),rr+"--getptdjconst");
        PtdjconstXml xml=Xml2Object.getptdjconstXml(rr);

        if(null!=xml&&null!=xml.getLine1()&&!xml.getLine1().trim().equals("")){

            try {
                GetptdjconstVO vo=new GetptdjconstVO();
                List<String> list=xml.toList();
                vo.setLineList(list);
                result.changeToTrue(vo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            result.setMessage("获取当前配置片头字段失败 --");
        }
        return result;
    }

    @Override
    public RResult<StartRec_RomVO> startRec_Rom(StartRec_RomParam param, RResult<StartRec_RomVO> result) {

        String ip=param.getIp();
        String passwd=param.getPasswd();
        String user=param.getUser();
        int port=param.getPort();
        int aldisk=param.getAldisk();
        String bmode=param.getBmode();
        int btime=param.getBtime();
        int disconly=param.getDisconly();
        int dx=param.getDx();
        String iid=param.getIid();

        if(StringUtils.isEmpty(ip)||StringUtils.isEmpty(user)||StringUtils.isEmpty(passwd)){
            result.setMessage("有部分参数为空");
            LogUtil.intoLog(this.getClass(),param.toString()+"----------startRec_Rom");
            return result;
        }
        String url="http://"+ip+":"+port+"/stcmd" ;
        String regparam="action=do&type=rom&cmd=startrec"+
                "&dx="+dx+"&aldisk="+aldisk+"&bmode="+bmode+"&btime="+btime+"&disconly="+disconly+"&iid="+iid+
                "&usr="+user+"&pwd="+passwd+"&authvusr="+user+"&authpwd="+passwd;
        LogUtil.intoLog(this.getClass(),regparam+"--regparam");
        String rr= HttpRequest.readContentFromGet_noencode(url,regparam,20000);//大一点超时时间
        LogUtil.intoLog(this.getClass(),rr+"--startRec_Rom");
        int i=Xml2Object.startRec_RomXml(rr);
        if(i>0){
            result.changeToTrue();
        }else{
            result.setMessage("请求开始光盘刻录失败");
        }
        return result;
    }

    @Override
    public RResult<PauseRec_RomVO> pauseRec_Rom(PauseRec_RomParam param, RResult<PauseRec_RomVO> result) {

        String ip=param.getIp();
        String passwd=param.getPasswd();
        String user=param.getUser();
        int port=param.getPort();

        if(StringUtils.isEmpty(ip)||StringUtils.isEmpty(user)||StringUtils.isEmpty(passwd)){
            result.setMessage("有部分参数为空");
            LogUtil.intoLog(this.getClass(),param.toString()+"----------pauseRec_Rom");
            return result;
        }

        String url="http://"+ip+":"+port+"/stcmd" ;
        String regparam="action=do&type=rom&cmd=pauserec"+
                "&usr="+user+"&pwd="+passwd+"&authvusr="+user+"&authpwd="+passwd;
        String rr= HttpRequest.readContentFromGet_noencode(url,regparam,20000);//大一点超时时间
        LogUtil.intoLog(this.getClass(),rr+"--pauseRec_Rom");
        int i=Xml2Object.pauseRec_RomXml(rr);
        if(i>0){
            result.changeToTrue();
        }else{
            result.setMessage("请求光盘暂停刻录失败");
        }

        return result;
    }

    @Override
    public RResult<GgoonRec_RomVO> goonRec_Rom(GoonRec_RomParam param, RResult<GgoonRec_RomVO> result) {

        String ip=param.getIp();
        String passwd=param.getPasswd();
        String user=param.getUser();
        int port=param.getPort();

        if(StringUtils.isEmpty(ip)||StringUtils.isEmpty(user)||StringUtils.isEmpty(passwd)){
            result.setMessage("有部分参数为空");
            LogUtil.intoLog(this.getClass(),param.toString()+"----------goonRec_Rom");
            return result;
        }

        String url="http://"+ip+":"+port+"/stcmd" ;
        String regparam="action=do&type=rom&cmd=goonrec"+
                "&usr="+user+"&pwd="+passwd+"&authvusr="+user+"&authpwd="+passwd;
        LogUtil.intoLog(this.getClass(),regparam+"--regparam");
        String rr= HttpRequest.readContentFromGet_noencode(url,regparam,20000);//大一点超时时间
        LogUtil.intoLog(this.getClass(),rr+"--goonRec_Rom");
        int i=Xml2Object.goonRec_RomXml(rr);
        if(i>0){
            result.changeToTrue();
        }else{
            result.setMessage("请求光盘继续刻录失败");
        }
        return result;
    }

    @Override
    public RResult<StopRec_RomVO> stopRec_Rom(StopRec_RomParam param, RResult<StopRec_RomVO> result) {

        String ip=param.getIp();
        String passwd=param.getPasswd();
        String user=param.getUser();
        int port=param.getPort();
        int dx=param.getDx();
        int disconly=param.getDisconly();

        if(StringUtils.isEmpty(ip)||StringUtils.isEmpty(user)||StringUtils.isEmpty(passwd)){
            result.setMessage("有部分参数为空");
            LogUtil.intoLog(this.getClass(),param.toString()+"----------stopRec_Rom");
            return result;
        }

        String url="http://"+ip+":"+port+"/stcmd" ;
        String regparam="action=do&type=rom&cmd=stoprec"+
                "&dx="+dx+"&disconly="+disconly+
                "&usr="+user+"&pwd="+passwd+"&authvusr="+user+"&authpwd="+passwd;
        LogUtil.intoLog(this.getClass(),url+"：url---time:"+ (new Date().getTime()) +" -regparam："+regparam);
        String rr= HttpRequest.readContentFromGet_noencode(url,regparam,20000);//大一点超时时间
        LogUtil.intoLog(this.getClass(),rr+"--stopRec_Rom --time:"+ (new Date().getTime()) );
        int i=Xml2Object.stopRec_RomXml(rr);
        if(i>0){
            result.changeToTrue();
        }else{
            result.setMessage("请求光盘结束刻录失败");
        }
        return result;
    }

    @Override
    public RResult<Eject_RomVO> eject_Rom(Eject_RomParam param, RResult<Eject_RomVO> result) {

        String ip=param.getIp();
        String passwd=param.getPasswd();
        String user=param.getUser();
        int port=param.getPort();
        int dx=param.getDx();

        if(StringUtils.isEmpty(ip)||StringUtils.isEmpty(user)||StringUtils.isEmpty(passwd)){
            result.setMessage("有部分参数为空");
            LogUtil.intoLog(this.getClass(),param.toString()+"----------eject_Rom");
            return result;
        }

        String url="http://"+ip+":"+port+"/stcmd" ;
        String regparam="action=do&type=rom&cmd=eject"+
                "&dx="+dx+
                "&usr="+user+"&pwd="+passwd+"&authvusr="+user+"&authpwd="+passwd;
        String rr= HttpRequest.readContentFromGet_noencode(url,regparam,20000);//大一点超时时间
        LogUtil.intoLog(this.getClass(),rr+"--eject_Rom");
        int i=Xml2Object.eject_RomXml(rr);
        if(i>0){
            result.changeToTrue();
        }else{
            result.setMessage("请求光驱出仓失败");
        }

        return result;
    }

    @Override
    public RResult<Closetray_RomVO> closetray_Rom(Closetray_RomParam param, RResult<Closetray_RomVO> result) {

        String ip=param.getIp();
        String passwd=param.getPasswd();
        String user=param.getUser();
        int port=param.getPort();
        int dx=param.getDx();

        if(StringUtils.isEmpty(ip)||StringUtils.isEmpty(user)||StringUtils.isEmpty(passwd)){
            result.setMessage("有部分参数为空");
            LogUtil.intoLog(this.getClass(),param.toString()+"----------closetray_Rom");
            return result;
        }

        String url="http://"+ip+":"+port+"/stcmd" ;
        String regparam="action=do&type=rom&cmd=closetray"+
                "&dx="+dx+
                "&usr="+user+"&pwd="+passwd+"&authvusr="+user+"&authpwd="+passwd;
        String rr= HttpRequest.readContentFromGet_noencode(url,regparam,20000);//大一点超时时间
        LogUtil.intoLog(this.getClass(),rr+"--closetray_Rom");
        int i=Xml2Object.closetray_RomXml(rr);
        if(i>0){
            result.changeToTrue();
        }else{
            result.setMessage("请求光驱进仓失败");
        }
        return result;
    }

    @Override
    public RResult<PtdjVO> ptdj(PtdjParam param, RResult<PtdjVO> result) {

        String ip=param.getIp();
        String passwd=param.getPasswd();
        String user=param.getUser();
        int port=param.getPort();
        List<String> linelist=param.getLineList();
        int ct=param.getCt();
        if(StringUtils.isEmpty(ip)||StringUtils.isEmpty(user)||StringUtils.isEmpty(passwd)||null==linelist||linelist.size()==0){
            result.setMessage("有部分参数为空");
            LogUtil.intoLog(this.getClass(),param.toString()+"----------ptdj");
            return result;
        }
        String lines="";
        int num=1;
        for(String line:linelist){//组建片头的数据集合
            try {
                lines+="&line"+num+"="+URLEncoder.encode(line, "gbk");
            } catch (Exception e) {
                e.printStackTrace();
            }
            num++;
        }

        String url="http://"+ip+":"+port+"/stcmd" ;
        String regparam="action=do&type=view&cmd=ptdj"+
                "&ct="+ct+lines+
                "&usr="+user+"&pwd="+passwd+"&authvusr="+user+"&authpwd="+passwd;
        String rr= HttpRequest.readContentFromGet_noencode(url,regparam,20000,"gbk");//大一点超时时间
        LogUtil.intoLog(this.getClass(),rr+"--ptdj");
        int i=Xml2Object.ptdjXml(rr);
        if(i>0){
            result.changeToTrue();
        }else{
            result.setMessage("请求片头叠加失败");
        }

        return result;
    }

    @Override
    public RResult<YuntaiControlVO> yuntaiControl(YuntaiControlParam param, RResult<YuntaiControlVO> result) {

        String ip=param.getIp();
        String passwd=param.getPasswd();
        String user=param.getUser();
        int port=param.getPort();
        String ptzaction=param.getPtzaction();
        int ptzch=param.getPtzch();

        if(StringUtils.isEmpty(ip)||StringUtils.isEmpty(user)||StringUtils.isEmpty(passwd)){
            result.setMessage("有部分参数为空");
            LogUtil.intoLog(this.getClass(),param.toString()+"----------yuntaiControl");
            return result;
        }

        String url="http://"+ip+":"+port+"/stcmd" ;
        String regparam="action=do&type=ctrl&cmd=ptz"+
                "&ptzaction="+ptzaction+"&ptzch="+ptzch+
                "&usr="+user+"&pwd="+passwd+"&authvusr="+user+"&authpwd="+passwd;
        String rr= HttpRequest.readContentFromGet_noencode(url,regparam,20000);//大一点超时时间
        LogUtil.intoLog(this.getClass(),rr+"--yuntaiControl");
        int i=Xml2Object.yuntaiControlXml(rr);
        if(i>0){
            result.changeToTrue();
        }else{
            result.setMessage("请求云台控制失败");
        }

        return result;
    }


    @Override
    public RResult<ChangeBurnModelVO> changeBurnMode(ChangeBurnModeParam param, RResult<ChangeBurnModelVO> result) {

        String ip=param.getIp();
        String passwd=param.getPasswd();
        String user=param.getUser();
        int port=param.getPort();
        int dx=param.getDx();

        if(StringUtils.isEmpty(ip)||StringUtils.isEmpty(user)||StringUtils.isEmpty(passwd)){
            result.setMessage("有部分参数为空");
            LogUtil.intoLog(this.getClass(),param.toString()+"----------changeBurnMode");
            return result;
        }
        String url="http://"+ip+":"+port+"/stcmd" ;
        String regparam="action=do&type=rom&cmd=burnmode"+
                "&bmode="+dx+
                "&usr="+user+"&pwd="+passwd+"&authvusr="+user+"&authpwd="+passwd;
        String rr= HttpRequest.readContentFromGet_noencode(url,regparam,20000);//大一点超时时间
        LogUtil.intoLog(this.getClass(),rr+"--changeBurnMode");
        int i=Xml2Object.changeBurnModeXml(rr);
        if(i==1){
            result.changeToTrue();
        }else{
            result.setMessage("请求刻录模式切换失败");
        }

        return result;
    }

    @Override
    public RResult<GetCDNumberVO> getCDNumber(GetCDNumberParam param, RResult<GetCDNumberVO> result) {

        String ip=param.getIp();
        String passwd=param.getPasswd();
        String user=param.getUser();
        int port=param.getPort();

        int dx=param.getDx();

        if(StringUtils.isEmpty(ip)||StringUtils.isEmpty(user)||StringUtils.isEmpty(passwd)){
            result.setMessage("有部分参数为空");
            LogUtil.intoLog(this.getClass(),param.toString()+"----------getCDNumber");
            return result;
        }
        String url="http://"+ip+":"+port+"/stcmd" ;
        String regparam="action=do&type=rom&cmd=disc_iid"+
                "&dx="+dx+
                "&usr="+user+"&pwd="+passwd+"&authvusr="+user+"&authpwd="+passwd;
        String rr= HttpRequest.readContentFromGet_noencode(url,regparam,20000);//大一点超时时间
        LogUtil.intoLog(this.getClass(),rr+"--getCDNumber");
        GetCDNumberXml cdnum=Xml2Object.getCDNumberXml(rr);
        if(null!=cdnum&&(null!=cdnum.getDisc_iid0()||null!=cdnum.getDisc_iid1())){
            GetCDNumberVO getCDNumberVO=new GetCDNumberVO();
            List<Disc_iid> cdNumList=new ArrayList<Disc_iid>();
            if(null!=cdnum.getDisc_iid0()&&cdnum.getDisc_iid0().getRs().trim().equals("1")){
                cdNumList.add(cdnum.getDisc_iid0());
            }
            if(null!=cdnum.getDisc_iid1()&&cdnum.getDisc_iid1().getRs().trim().equals("1")){
                cdNumList.add(cdnum.getDisc_iid1());
            }
            getCDNumberVO.setCdNumList(cdNumList);
            result.changeToTrue(getCDNumberVO);
        }else{
            result.setMessage("请求获取光盘序列号失败");
        }
        return result;
    }
}
