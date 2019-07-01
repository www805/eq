package com.avst.equipmentcontrol.outside.interfacetoout.storage.v1.service;

import com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.entity.Ss_database;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.entity.Ss_saveinfo;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.entity.param.Ss_dataMessageParam;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.mapper.Ss_databaseMapper;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.mapper.Ss_saveinfoMapper;
import com.avst.equipmentcontrol.common.util.LogUtil;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
