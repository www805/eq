package com.avst.equipmentcontrol.outside.interfacetoout.storage.v1.service;

import com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.entity.Ss_database;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.entity.Ss_saveinfo;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.entity.param.Ss_dataMessageParam;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.mapper.Ss_databaseMapper;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.mapper.Ss_saveinfoMapper;
import com.avst.equipmentcontrol.common.util.FileUtil;
import com.avst.equipmentcontrol.common.util.LogUtil;
import com.avst.equipmentcontrol.common.util.OpenUtil;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.common.util.properties.PropertiesListenerConfig;
import com.avst.equipmentcontrol.outside.dealoutinterface.storage.avstss.req.SaveFileByIidParam;
import com.avst.equipmentcontrol.outside.dealoutinterface.storage.avstss.req.SaveFileByIid_localParam;
import com.avst.equipmentcontrol.outside.dealoutinterface.storage.avstss.v1.DealImpl;
import com.avst.equipmentcontrol.outside.interfacetoout.storage.req.*;
import com.avst.equipmentcontrol.outside.interfacetoout.storage.vo.CheckRecordFileStateVO;
import com.avst.equipmentcontrol.outside.interfacetoout.storage.vo.GetSavepathVO;
import com.avst.equipmentcontrol.outside.interfacetoout.storage.vo.GetURLToPlayVO;
import com.avst.equipmentcontrol.outside.interfacetoout.storage.vo.param.RecordFileParam;
import com.avst.equipmentcontrol.outside.interfacetoout.storage.vo.param.RecordPlayParam;
import com.avst.equipmentcontrol.outside.interfacetoout.storage.vo.param.RecordSavepathParam;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class ToOutServiceImpl_ss_avst implements ToOutService_ss {

    @Autowired
    private DealImpl deal;

    @Autowired
    private Ss_saveinfoMapper ss_saveinfoMapper;

    @Autowired
    private Ss_databaseMapper ss_databaseMapper;

    @Override
    public RResult saveFile(SaveFileParam param, RResult result) {

        try {
            String iid = param.getIid();
            String sourseip = param.getSourseIp();
            String sstype = param.getSsType();
            String fdssid = param.getSourseFDETSsid();
            long startrecordtime=param.getStartrecordtime();
            //根据类型找存储服务器
            EntityWrapper<Ss_saveinfo> ew = new EntityWrapper<Ss_saveinfo>();
            ew.eq("sstype", sstype);
            List<Ss_saveinfo> savelist = ss_saveinfoMapper.selectList(ew);
            if (null == savelist || savelist.size() < 1) {
                result.setMessage("没有找到存储服务");
                return result;
            }
            //检查这个存储服务是否在正常工作
            //获取正常工作的存储服务（暂时取0）
            Ss_saveinfo ss_saveinfo = savelist.get(0);

            SaveFileByIidParam sfparam = new SaveFileByIidParam();
            sfparam.setIid(iid);
            sfparam.setSaveinfossid(ss_saveinfo.getSsid());
            sfparam.setIp(sourseip);
            sfparam.setFdssid(fdssid);
            sfparam.setUploadbasepath(ss_saveinfo.getDatasavebasepath());
            sfparam.setStartrecordtime(startrecordtime);
            result = deal.saveFileByIid(sfparam, result);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.intoLog(this.getClass(),"saveFile is error");
        }

        return result;
    }

    @Override
    public RResult saveFile_local(SaveFile_localParam param, RResult result) {

        try {
            String iid = param.getIid();
            String sourseRelativePath = param.getSourseRelativePath();
            String sstype = param.getSsType();
            //根据类型找存储服务器
            EntityWrapper<Ss_saveinfo> ew = new EntityWrapper<Ss_saveinfo>();
            ew.eq("sstype", sstype);
            List<Ss_saveinfo> savelist = ss_saveinfoMapper.selectList(ew);
            if (null == savelist || savelist.size() < 1) {
                result.setMessage("没有找到存储服务");
                return result;
            }
            //检查这个存储服务是否在正常工作
            //获取正常工作的存储服务（暂时取0）
            Ss_saveinfo ss_saveinfo = savelist.get(0);

            SaveFileByIid_localParam sfparam = new SaveFileByIid_localParam();
            sfparam.setIid(iid);
            sfparam.setSaveinfossid(ss_saveinfo.getSsid());
            sfparam.setSourseRelativePath(sourseRelativePath);
            sfparam.setUploadbasepath(ss_saveinfo.getDatasavebasepath());
            result = deal.saveFileByIid_local(sfparam, result);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.intoLog(this.getClass(),"saveFile is error");
        }

        return result;
    }


    public RResult getSaveFilesPathByiid(GetSaveFilesPathByiidParam param, RResult result){

        try {
            String iid = param.getIid();
            int videobool = param.getVideobool();

            List<Ss_dataMessageParam> datalist = deal.getSs_databaseList(iid);
            if (null != datalist && datalist.size() > 0) {
                String file_folder=null;
                for(Ss_dataMessageParam data:datalist){
                    String datasavepath=data.getDatasavepath();
                    if(StringUtils.isNotEmpty(datasavepath)){//把iid存储视频的地址给发过去，ph以后也直接存在这里面
                        //以后iid对应的所有的数据都放在一起
                        file_folder=OpenUtil.getfile_folder(datasavepath);
                        if(file_folder.indexOf("/") > 0){
                            file_folder+="/";
                        }else{
                            file_folder+="\\";
                        }
                        break;
                    }
                }

                if(null==file_folder){
                    result.setMessage("没有找到任何一个可用的地址，怀疑数据出错了，iid="+iid);
                    return result;
                }

                List<String> pathlist=FileUtil.getAllFiles(file_folder,1);
                if(null!=pathlist&&pathlist.size() > 0){
                    String needpathlist="";
                    String changetype=PropertiesListenerConfig.getProperty("changetype");
                    if(StringUtils.isEmpty(changetype)){
                        changetype="mp4";
                    }
                    String stfiletype=PropertiesListenerConfig.getProperty("stfiletype");
                    if(StringUtils.isEmpty(stfiletype)){
                        stfiletype="st";
                    }
                    for(String path:pathlist){
                        //判断视频需不需要
                        if(path.endsWith(changetype)||path.endsWith(stfiletype)){
                            if(videobool==1){
                                needpathlist+=path+",";
                            }
                        }else{
                            needpathlist+=path+",";
                        }
                    }
                    if(StringUtils.isNotEmpty(needpathlist)){
                        result.changeToTrue(needpathlist);
                    }else{
                        result.setMessage("没有找到一个需要打包的文件，iid="+iid);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.intoLog(this.getClass(),"saveFile is error");
        }

        return result;
    }

    @Override
    public RResult getSaveFilePath_local(GetSaveFilePath_localParam param, RResult result) {

        try {
            String iid = param.getIid();
            List<Ss_dataMessageParam> datalist = deal.getSs_databaseList(iid);
            if (null != datalist && datalist.size() > 0) {
                Ss_dataMessageParam ss_dataMessageParam=datalist.get(0);
                String datasavepath=ss_dataMessageParam.getDatasavepath();

                if(StringUtils.isNotEmpty(datasavepath)){//把iid存储视频的地址给发过去，ph以后也直接存在这里面
                    String file_folder=OpenUtil.getfile_folder(datasavepath);
                    if(file_folder.indexOf("/") > 0){
                        file_folder+="/";
                    }else{
                        file_folder+="\\";
                    }
                    result.changeToTrue(file_folder);
                    return result;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.intoLog(this.getClass(),"getSaveFilePath_local is error");
        }

        return result;
    }

    @Override
    public RResult<GetURLToPlayVO> getURLToPlay(GetURLToPlayParam param, RResult result) {

        String iid = param.getIid();
        try {
            List<Ss_dataMessageParam> datalist = deal.getSs_databaseList(iid);
            if (null != datalist && datalist.size() > 0) {
                GetURLToPlayVO getURLToPlayVO = new GetURLToPlayVO();
                getURLToPlayVO.setIid(iid);
                List<RecordPlayParam> recordList = new ArrayList<RecordPlayParam>();
                for (Ss_dataMessageParam data : datalist) {
                    RecordPlayParam recordPlayParam = new RecordPlayParam();
                    recordPlayParam.setDatatype(data.getDatatype());
                    recordPlayParam.setFilename(data.getFilename());
                    recordPlayParam.setFilenum(data.getFilenum());
                    recordPlayParam.setRepeattime(data.getRepeattime());
                    recordPlayParam.setRecordstarttime(data.getRecordstarttime());
                    //计算结束的时间戳
                    long recordendtime=(data.getEndtime()-data.getStarttime())*1000+data.getRecordstarttime();
                    recordPlayParam.setRecordendtime(recordendtime);
                    String dxy = data.getDefaulturl();//默认协议
                    recordPlayParam.setXyType(dxy);
                    if (null != dxy && dxy.equals("http")) {//根据协议的不同播放地址也不一样
                        recordPlayParam.setPlayUrl(data.getHttpurl());
                    }
                    recordList.add(recordPlayParam);
                }
                getURLToPlayVO.setRecordList(recordList);
                result.changeToTrue(getURLToPlayVO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.intoLog(this.getClass(),"getURLToPlay is error");
        }

        return result;
    }

    @Override
    public RResult<GetSavepathVO> getSavePath(GetSavePathParam param, RResult result) {

        String iid = param.getIid();
        try {
            List<Ss_dataMessageParam> datalist = deal.getSs_databaseList(iid);
            if (null != datalist && datalist.size() > 0) {
                GetSavepathVO vo = new GetSavepathVO();
                vo.setIid(iid);
                List<RecordSavepathParam> recordList = new ArrayList<RecordSavepathParam>();
                for (Ss_dataMessageParam data : datalist) {
                    RecordSavepathParam recordPathParam = new RecordSavepathParam();
                    recordPathParam.setDatatype(data.getDatatype());
                    recordPathParam.setFilename(data.getFilename());
                    String dxy = data.getDefaulturl();//默认协议
                    recordPathParam.setXyType(dxy);
                    recordPathParam.setSavepath(data.getDatasavepath());
                    recordPathParam.setSoursedatapath(data.getSoursedatapath());
                    recordPathParam.setFilenum(data.getFilenum());
                    recordPathParam.setRepeattime(data.getRepeattime());
                    recordList.add(recordPathParam);
                }
                vo.setRecordList(recordList);
                result.changeToTrue(vo);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.intoLog(this.getClass(),"getSavePath is error");
        }

        return result;
    }



    @Override
    public RResult checkRecordFileState(CheckRecordFileStateParam param, RResult result) {

        String iid = param.getIid();
        try {
            List<Ss_dataMessageParam> ss_databaseList = deal.getSs_databaseList(iid);
            CheckRecordFileStateVO checkRecordFileStateVO = new CheckRecordFileStateVO();
            if (null != ss_databaseList && ss_databaseList.size() > 0) {
                checkRecordFileStateVO.setIid(iid);
                List<RecordFileParam> recordList = new ArrayList<RecordFileParam>();
                for (Ss_dataMessageParam db : ss_databaseList) {
                    RecordFileParam recordFileParam = new RecordFileParam();
                    recordFileParam.setFilename(db.getFilename());
                    recordFileParam.setState(db.getState());
                    recordList.add(recordFileParam);
                }
                checkRecordFileStateVO.setRecordList(recordList);
            }
            result.changeToTrue(checkRecordFileStateVO);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.intoLog(this.getClass(),"checkRecordFileState is error");
        }
        return result;
    }
}
