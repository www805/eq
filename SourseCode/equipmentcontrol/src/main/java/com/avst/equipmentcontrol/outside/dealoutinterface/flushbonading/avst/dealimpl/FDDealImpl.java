package com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl;

import com.avst.equipmentcontrol.common.util.XMLUtil;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.req.*;
import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.vo.*;
import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.vo.checkstate.CheckState0;
import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.xmljsonobject.StartRecXml;
import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.xmljsonobject.StopRecXml;
import com.avst.requestUtil.HttpRequest;
import com.google.gson.Gson;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.xmljsonobject.CheckFDStateXml;

import java.util.HashMap;
import java.util.Map;

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
            System.out.println(param.toString()+"--------reg--CheckFDState");
            return result;
        }

        String url="http://"+ip+":"+port+"/stcmd" ;
        String regparam="action=get&type=devstatus&authvusr="+user+"&authpwd="+passwd;
        String rr= HttpRequest.readContentFromGet_noencode(url,regparam);

        CheckFDStateXml xml=new CheckFDStateXml();
//        xml=(CheckFDStateXml)XMLUtil.xmlToStr(xml,rr);

        Gson gson=new Gson();
        xml= (CheckFDStateXml)XMLUtil.map2JavaBean(xml,XMLUtil.xml2map(rr));

        if(null!=xml){//说明请求有正确的返回

            CheckFDStateVO CheckFDStateVO=new CheckFDStateVO();
            CheckFDStateVO.setStateType(type);
            if(type==0){
                CheckFDStateVO.setData(xml);
            }
            result.changeToTrue(CheckFDStateVO);

        }else{
            System.out.println(xml+":xml 请求返回为空 reg");
            result.setMessage("语音服务器请求异常");
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
            System.out.println(param.toString()+"--------reg--startRec");
            return result;
        }
        String url="http://"+ip+":"+port+"/stcmd" ;
        String regparam="action=do&type=disk&cmd=startrec&al="+al +
                "&iid="+iid+"&authvusr="+user+"&authpwd="+passwd;
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
            System.out.println(param.toString()+"--------reg--stopRec");
            return result;
        }

        String url="http://"+ip+":"+port+"/stcmd" ;
        String regparam="action=do&type=disk&cmd=stoprec"+
                "&authvusr="+user+"&authpwd="+passwd;
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
}
