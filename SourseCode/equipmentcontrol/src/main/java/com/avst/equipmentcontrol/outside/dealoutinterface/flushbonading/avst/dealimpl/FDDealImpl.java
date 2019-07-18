package com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl;

import com.avst.equipmentcontrol.common.util.LogUtil;
import com.avst.equipmentcontrol.common.util.OpenUtil;
import com.avst.equipmentcontrol.common.util.XMLUtil;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.req.*;
import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.vo.*;
import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.xmljsonobject.*;
import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.xmljsonobject.param.File;
import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.xmljsonobject.param.Files;
import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.xmljsonobject.param.Get_rec_files;
import com.avst.requestUtil.HttpRequest;
import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

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
        String rr= HttpRequest.readContentFromGet_noencode(url,regparam);

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
        LogUtil.intoLog(this.getClass(),url+":url  regparam:"+regparam);
        String rr= HttpRequest.readContentFromGet_noencode(url,regparam);

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
        String rr= HttpRequest.readContentFromGet_noencode(url,regparam);

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
                "&authvusr="+user+"&authpwd="+passwd;
        LogUtil.intoLog(this.getClass(),url+":url  regparam:"+regparam);
        String rr= HttpRequest.readContentFromGet_noencode(url,regparam);

        SetMiddleware_FTPXml xml=new SetMiddleware_FTPXml();
        xml=(SetMiddleware_FTPXml)XMLUtil.xmlToStr(xml,rr);

        if(null!=xml&&null!=xml.getMsg()&&xml.getMsg().equals("200")){
            //设置ftp成功
            result.changeToTrue();

        }else{
            result.setMessage("设置ftp失败 --");
        }

        return result;
    }

    @Override
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
        String rr= HttpRequest.readContentFromGet_noencode(url,regparam);
        LogUtil.intoLog(this.getClass(),rr+"--getETRecordByIid");
        GetETRecordByIidXml xml=Xml2Object.xml2GetETRecordByIidXml(rr);

        if(null!=xml&&null!=xml.getGet_rec_files()){
            GetETRecordByIidVO getETRecordPathByIidVO=new GetETRecordByIidVO();
            try {//可能报错，返回的对象中的参数首尾都没有去空格
                getETRecordPathByIidVO.setFileList(xml.getGet_rec_files().getFiles().getFileList());
                result.changeToTrue(getETRecordPathByIidVO);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            result.setMessage("设置ftp失败 --");
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
        String rr= HttpRequest.readContentFromGet_noencode(url,regparam);
        LogUtil.intoLog(this.getClass(),rr+"--uploadFileByPath");
        UploadFileByPathXml xml=Xml2Object.uploadFileByPathXml(rr);

        if(null!=xml&&null!=xml.getFtp_pasv_upload_file()&&xml.getFtp_pasv_upload_file().getRs().trim().equals("1")){

            try {
                UploadFileByPathVO uploadFileByPathVO=new UploadFileByPathVO();
                result.changeToTrue(uploadFileByPathVO);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            result.setMessage("设置ftp失败 --");
        }
        return result;
    }

    public RResult<UploadServiceByIidVO> uploadServiceByIid(UploadServiceByIidParam param, RResult<UploadServiceByIidVO> result){

//        String iid=param.getIid();
//        String soursepath=param.getSoursepath();
//        String ip=param.getIp();
//        String passwd=param.getPasswd();
//        String user=param.getUser();
//        int port=param.getPort();
//
//        if(StringUtils.isEmpty(ip)||StringUtils.isEmpty(user)||StringUtils.isEmpty(passwd)){
//            result.setMessage("有部分参数为空");
//            LogUtil.intoLog(this.getClass(),param.toString()+"----------uploadFileByPath");
//            return result;
//        }
//
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


}
