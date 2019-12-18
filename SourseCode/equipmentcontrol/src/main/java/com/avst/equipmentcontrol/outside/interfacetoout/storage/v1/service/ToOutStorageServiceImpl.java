package com.avst.equipmentcontrol.outside.interfacetoout.storage.v1.service;

import com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.entity.Ss_saveinfo;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.entity.Storage_ettype;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.entity.param.Ss_dataMessageParam;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.mapper.Ss_databaseMapper;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.mapper.Ss_saveinfoMapper;
import com.avst.equipmentcontrol.common.util.JacksonUtil;
import com.avst.equipmentcontrol.common.util.LogUtil;
import com.avst.equipmentcontrol.common.util.baseaction.BaseService;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.common.util.baseaction.ReqParam;
import com.avst.equipmentcontrol.common.util.filespace.DriverSpaceParam;
import com.avst.equipmentcontrol.common.util.filespace.FileSpaceUtil;
import com.avst.equipmentcontrol.common.util.ftp.FTPServer;
import com.avst.equipmentcontrol.common.util.ftp.FTPUser;
import com.avst.equipmentcontrol.common.util.properties.PropertiesListenerConfig;
import com.avst.equipmentcontrol.outside.interfacetoout.storage.req.AddOrUpdateToOutStorageParam;
import com.avst.equipmentcontrol.outside.interfacetoout.storage.req.GetDefaultSaveInfoParam;
import com.avst.equipmentcontrol.outside.interfacetoout.storage.req.GetToOutStorageListParam;
import com.avst.equipmentcontrol.outside.interfacetoout.storage.req.ReStartFTPServerParam;
import com.avst.equipmentcontrol.outside.interfacetoout.storage.vo.GetDefaultSaveInfoVO;
import com.avst.equipmentcontrol.outside.interfacetoout.storage.vo.param.Ss_saveinfoParam;
import com.avst.equipmentcontrol.web.req.storage.FileSpaceByssidParam;
import com.avst.equipmentcontrol.web.req.storage.StorageParam;
import com.avst.equipmentcontrol.web.req.storage.UpdateStorageParam;
import com.avst.equipmentcontrol.web.service.StorageService;
import com.avst.equipmentcontrol.web.vo.storage.StorageVO;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ToOutStorageServiceImpl extends BaseService implements ToOutStorageService {

    @Autowired
    private StorageService storageService;

    @Autowired
    private Ss_databaseMapper ssDatabaseMapper;

    @Autowired
    private Ss_saveinfoMapper ss_saveinfoMapper;

    //查询列表
    @Override
    public RResult getToOutStorageList(GetToOutStorageListParam param, RResult result) {

        ReqParam<StorageParam> reqParam = new ReqParam<StorageParam>();

        StorageParam StorageParam = new StorageParam();
        reqParam.setParam(StorageParam);

        storageService.getStorageList(result, reqParam);

        if(!"FAIL".equals(result.getActioncode())){
            StorageVO data = (StorageVO) result.getData();
            List<Storage_ettype> list = data.getPagelist();
            result.setData(list);
        }

        return result;
    }

    //查询单个
    @Override
    public RResult getToOutStorageById(GetToOutStorageListParam param, RResult result) {

        ReqParam<StorageParam> reqParam = new ReqParam<StorageParam>();

        StorageParam StorageParam = new StorageParam();
        StorageParam.setSsid(param.getSsid());
        reqParam.setParam(StorageParam);

        storageService.getStorageById(result, reqParam);

        return result;
    }

    //新增
    @Override
    public RResult addToOutStorage(AddOrUpdateToOutStorageParam param, RResult result) {

        ReqParam<UpdateStorageParam> reqParam = new ReqParam<UpdateStorageParam>();

        UpdateStorageParam storageParam = new UpdateStorageParam();
//        storageParam.setTotalcapacity(param.getTotalcapacity());
        storageParam.setPort(param.getPort());
        storageParam.setSstype(param.getSstype());
        storageParam.setDatasavebasepath(param.getDatasavebasepath());
        storageParam.setEtnum(param.getEtnum());
        storageParam.setEtip(param.getEtip());
        storageParam.setEtypessid(param.getEtypessid());
        storageParam.setExplain(param.getExplain());
        reqParam.setParam(storageParam);

        storageService.addStorage(result, reqParam);

        return result;
    }

    //修改
    @Override
    public RResult updateToOutStorage(AddOrUpdateToOutStorageParam param, RResult result) {

        ReqParam<UpdateStorageParam> reqParam = new ReqParam<UpdateStorageParam>();

        UpdateStorageParam storageParam = new UpdateStorageParam();
        storageParam.setSsid(param.getSsid());
//        storageParam.setTotalcapacity(param.getTotalcapacity());
        storageParam.setPort(param.getPort());
        storageParam.setSstype(param.getSstype());
        storageParam.setDatasavebasepath(param.getDatasavebasepath());
        storageParam.setEtnum(param.getEtnum());
        storageParam.setEtip(param.getEtip());
        storageParam.setEtypessid(param.getEtypessid());
        storageParam.setExplain(param.getExplain());
        reqParam.setParam(storageParam);

        storageService.updateStorage(result, reqParam);

        return result;
    }

    //根据iid查询
    @Override
    public RResult getToOutStorageByiid(GetToOutStorageListParam param, RResult result) {

        //请求参数转换
        if (null==param){
            result.setMessage("参数为空");
            return result;
        }

        if (StringUtils.isBlank(param.getIid())){
            result.setMessage("iid不能为空");
            return result;
        }

        EntityWrapper ew = new EntityWrapper();
        ew.eq("iid", param.getIid());

        List<Ss_dataMessageParam> ss_databaseByIid = ssDatabaseMapper.getSs_databaseByIid(ew);
        result.setData(ss_databaseByIid);
        changeResultToSuccess(result);
        return result;
    }

    @Override
    public RResult getToOutFileSpaceList(GetToOutStorageListParam pParam, RResult result) {

        List<Ss_saveinfo> selectList = ss_saveinfoMapper.selectList(null);

        ArrayList<Ss_saveinfoParam> list = new ArrayList<>();

        if (selectList.size() > 0) {
            for (int i = 0; i < selectList.size(); i++) {

                Ss_saveinfo ss_saveinfo = selectList.get(i);
                DriverSpaceParam filePathSpace = FileSpaceUtil.getFilePathSpace(ss_saveinfo.getDatasavebasepath());//获取文件夹/文件夹对应的容量信息

                Ss_saveinfoParam ss_saveinfoParam = new Ss_saveinfoParam();
                ss_saveinfoParam.setSs_saveinfo(ss_saveinfo);
                ss_saveinfoParam.setDriverSpaceParam(filePathSpace);

                list.add(ss_saveinfoParam);
            }

            result.changeToTrue(list);
        }

        return result;
    }

    @Override
    public RResult getToOutFileSpaceByssid(GetToOutStorageListParam pParam, RResult result) {

        FileSpaceByssidParam fileSpaceByssidParam = new FileSpaceByssidParam();
        fileSpaceByssidParam.setSsid(pParam.getSsid());
        fileSpaceByssidParam.setPath(pParam.getPath());

        ReqParam reqParam = new ReqParam();
        reqParam.setParam(fileSpaceByssidParam);

        storageService.getFileSpaceByssid(result, reqParam);

        return result;
    }

    @Override
    public RResult getToOutFileSpaceAll(GetToOutStorageListParam pParam, RResult result) {

        FileSpaceByssidParam fileSpaceByssidParam = new FileSpaceByssidParam();
        fileSpaceByssidParam.setSsid(pParam.getSsid());
        fileSpaceByssidParam.setPath(pParam.getPath());

        ReqParam reqParam = new ReqParam();
        reqParam.setParam(fileSpaceByssidParam);

        storageService.getFileSpaceAll(result, reqParam);

        return result;
    }

    @Override
    public RResult delToOutFileSpaceAll(GetToOutStorageListParam pParam, RResult result) {

        FileSpaceByssidParam fileSpaceByssidParam = new FileSpaceByssidParam();
        fileSpaceByssidParam.setSsid(pParam.getSsid());
        fileSpaceByssidParam.setPath(pParam.getPath());

        ReqParam reqParam = new ReqParam();
        reqParam.setParam(fileSpaceByssidParam);

        storageService.delFileSpaceAll(result, reqParam);

        return result;
    }

    @Override
    public RResult delToOutFileSpaceByPath(GetToOutStorageListParam pParam, RResult result) {

        FileSpaceByssidParam fileSpaceByssidParam = new FileSpaceByssidParam();
        fileSpaceByssidParam.setSsid(pParam.getSsid());
        fileSpaceByssidParam.setPath(pParam.getPath());

        ReqParam reqParam = new ReqParam();
        reqParam.setParam(fileSpaceByssidParam);

        storageService.delFileSpaceByPath(result, reqParam);

        return result;
    }

    @Override
    public RResult getDefaultSaveInfo(GetDefaultSaveInfoParam pParam, RResult result) {

        EntityWrapper<Ss_saveinfo> wrapper=new EntityWrapper<Ss_saveinfo>();
        wrapper.eq("defaultsavebool",1);
        wrapper.eq("ssstate",1);
        List<Ss_saveinfo> sslist=ss_saveinfoMapper.selectList(wrapper);
        if(null!=sslist&&sslist.size() > 0){
            Ss_saveinfo ss_saveinfo=sslist.get(0);
            Gson gson=new Gson();
            GetDefaultSaveInfoVO getDefaultSaveInfoVO=gson.fromJson(gson.toJson(ss_saveinfo),GetDefaultSaveInfoVO.class);

            String httpbasestaticpath=PropertiesListenerConfig.getProperty("httpbasestaticpath");
            getDefaultSaveInfoVO.setHttpbasestaticpath(httpbasestaticpath);//trm会做非空判断

            result.changeToTrue(getDefaultSaveInfoVO);
        }else{
            result.setMessage("没有找到默认存储设备");
            LogUtil.intoLog(4,this.getClass(),"数据库异常，没有找到默认存储设备");
        }
        return result;
    }

    @Override
    public RResult reStartFTPServer(ReStartFTPServerParam pParam, RResult result) {

        try {
            int port=21;
            String ftpport=PropertiesListenerConfig.getProperty("ftpport");
            if(StringUtils.isNotEmpty(ftpport)){
                try {
                    port=Integer.parseInt(ftpport);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            List<FTPUser> ftpUsers=new ArrayList<FTPUser>();
            Wrapper<Ss_saveinfo> wrapper=new EntityWrapper<Ss_saveinfo>();
            List<Ss_saveinfo> userlist=ss_saveinfoMapper.selectList(wrapper);
            if(null!=userlist&&userlist.size() > 0){

                for(Ss_saveinfo ss:userlist){
                    if(null!=ss.getSsstate()&&ss.getSsstate()==1){//ss.getXytype()==ftp 以后还需要判断是否是ftp服务器
                        FTPUser ftpUser=new FTPUser();

                        ftpUser.setHomeDirectory(ss.getDatasavebasepath());
                        ftpUser.setName(ss.getUser());
                        ftpUser.setPassword(ss.getPasswd());
                        ftpUsers.add(ftpUser);
                        LogUtil.intoLog(1,this.getClass(),"ftp存储服务开启了一个用户："+ JacksonUtil.objebtToString(ftpUser));
                    }
                }

            }else{
                FTPUser ftpUser=new FTPUser();
                ftpUser.setHomeDirectory("d:/ftpdata");
                ftpUser.setName("admin");
                ftpUser.setPassword("admin123");
                ftpUsers.add(ftpUser);
                LogUtil.intoLog(3,this.getClass(),"ftp存储服务在数据库中未找到，默认开启了一个用户："+JacksonUtil.objebtToString(ftpUser));
            }

            FTPServer.startFTPServer(port,ftpUsers);//启动ftp服务器

            result.changeToTrue();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
