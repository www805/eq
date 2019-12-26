package com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.v1.service;

import com.avst.equipmentcontrol.common.datasourse.extrasourse.asr.entity.Asr_etinfo;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.asr.mapper.Asr_etinfoMapper;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.flushbonading.entity.Flushbonading_etinfo;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.flushbonading.entity.param.Flushbonadinginfo;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.flushbonading.mapper.Flushbonading_etinfoMapper;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.entity.Ss_saveinfo;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.mapper.Ss_saveinfoMapper;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.common.util.baseaction.ReqParam;
import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.FDInterface;
import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.req.GetMiddleware_FTPParam;
import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.req.SetMiddleware_FTPParam;
import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.vo.GetMiddleware_FTPVO;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.req.*;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.vo.GetToOutFtpPortVO;
import com.avst.equipmentcontrol.web.req.flushbonading.FlushbonadinginfoParam;
import com.avst.equipmentcontrol.web.req.flushbonading.GetMiddleware_FTPParam_web;
import com.avst.equipmentcontrol.web.req.flushbonading.SetMiddleware_FTPParam_web;
import com.avst.equipmentcontrol.web.service.FlushbonadingService;
import com.avst.equipmentcontrol.web.vo.flushbonading.FlushbonadinginfoVO;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 数据库的编辑
 */
@Service
public class ToOutFlushbonadingServiceImpl implements ToOutFlushbonadingService {

    @Autowired
    private FlushbonadingService flushbonadingService;

    @Autowired
    private Flushbonading_etinfoMapper flushbonading_etinfoMapper;

    @Autowired
    private Ss_saveinfoMapper ss_saveinfoMapper;

    @Autowired
    private Asr_etinfoMapper asr_etinfoMapper;

    @Autowired
    private FDInterface fdInterface;

    private Gson gson=new Gson();

    //查询列表
    @Override
    public RResult getToOutFlushbonadingList(GetToOutFlushbonadingListParam param, RResult result) {

        ReqParam<FlushbonadinginfoParam> reqParam = new ReqParam<FlushbonadinginfoParam>();

        FlushbonadinginfoParam flushbonadinginfoParam = new FlushbonadinginfoParam();
        reqParam.setParam(flushbonadinginfoParam);

        List<Flushbonading_etinfo> flushbonading_etinfos = flushbonading_etinfoMapper.selectList(null);

        result.changeToTrue(flushbonading_etinfos);

//        flushbonadingService.getFlushbonadingList(result, reqParam);

//        if(!"FAIL".equals(result.getActioncode())){
//            FlushbonadinginfoVO data = (FlushbonadinginfoVO) result.getData();
//            List<Flushbonadinginfo> list = data.getPagelist();
//            result.setData(list);
//        }

        return result;
    }

    //查询单个
    @Override
    public RResult getToOutFlushbonadingById(GetToOutFlushbonadingListParam param, RResult result) {

        ReqParam<FlushbonadinginfoParam> reqParam = new ReqParam<FlushbonadinginfoParam>();

        FlushbonadinginfoParam flushbonadinginfoParam = new FlushbonadinginfoParam();
        flushbonadinginfoParam.setSsid(param.getFlushbonadingetinfossid());
        reqParam.setParam(flushbonadinginfoParam);

        flushbonadingService.getFlushbonadingById(result, reqParam);

        return result;
    }

    //新增
    @Override
    public RResult addToOutFlushbonading(AddOrUpdateToOutFlushbonadingParam param, RResult result) {

        ReqParam<Flushbonadinginfo> reqParam = new ReqParam<Flushbonadinginfo>();

        Flushbonadinginfo flushbonadinginfo = new Flushbonadinginfo();
        flushbonadinginfo.setLivingurl(param.getLivingurl());
        flushbonadinginfo.setPort(param.getPort());
        flushbonadinginfo.setUser(param.getUser());
        flushbonadinginfo.setPasswd(param.getPasswd());
        flushbonadinginfo.setUploadbasepath(param.getUploadbasepath());
        flushbonadinginfo.setEtnum(param.getEtnum());
        flushbonadinginfo.setEtip(param.getEtip());
        flushbonadinginfo.setEtypessid(param.getEtypessid());
        flushbonadinginfo.setExplain(param.getExplain());
        flushbonadinginfo.setPreviewurl(param.getPreviewurl());
        flushbonadinginfo.setDiskrecbool(param.getDiskrecbool());
        flushbonadinginfo.setBurnbool(param.getBurnbool());
        flushbonadinginfo.setDefaulturlbool(param.getDefaulturlbool());
        flushbonadinginfo.setBurntime(param.getBurntime());
        flushbonadinginfo.setPtjson(param.getPtjson());
        flushbonadinginfo.setPtshowtime(param.getPtshowtime());
        reqParam.setParam(flushbonadinginfo);

        flushbonadingService.addFlushbonading(result, reqParam);

        return result;
    }

    //修改
    @Override
    public RResult updateToOutFlushbonading(AddOrUpdateToOutFlushbonadingParam param, RResult result) {

        ReqParam<Flushbonadinginfo> reqParam = new ReqParam<Flushbonadinginfo>();

        Flushbonadinginfo flushbonadinginfo = new Flushbonadinginfo();
        flushbonadinginfo.setSsid(param.getSsid());
        flushbonadinginfo.setLivingurl(param.getLivingurl());
        flushbonadinginfo.setPort(param.getPort());
        flushbonadinginfo.setUser(param.getUser());
        flushbonadinginfo.setPasswd(param.getPasswd());
        flushbonadinginfo.setUploadbasepath(param.getUploadbasepath());
        flushbonadinginfo.setEtnum(param.getEtnum());
        flushbonadinginfo.setEtip(param.getEtip());
        flushbonadinginfo.setEtypessid(param.getEtypessid());
        flushbonadinginfo.setExplain(param.getExplain());
        flushbonadinginfo.setPreviewurl(param.getPreviewurl());
        flushbonadinginfo.setDiskrecbool(param.getDiskrecbool());
        flushbonadinginfo.setBurnbool(param.getBurnbool());
        flushbonadinginfo.setDefaulturlbool(param.getDefaulturlbool());
        flushbonadinginfo.setBurntime(param.getBurntime());
        flushbonadinginfo.setPtjson(param.getPtjson());
        flushbonadinginfo.setPtshowtime(param.getPtshowtime());
        reqParam.setParam(flushbonadinginfo);

        flushbonadingService.updateFlushbonading(result, reqParam);

        return result;
    }

    @Override
    public RResult getToOutDefault(GetToOutFlushbonadingListParam param, RResult result) {

        EntityWrapper<Flushbonadinginfo> ew = new EntityWrapper<>();
        String flushbonadingetinfossid=param.getFlushbonadingetinfossid();
        if (StringUtils.isNotEmpty(flushbonadingetinfossid)){
            ew.eq("fet.ssid", flushbonadingetinfossid);
        }else {
            ew.eq("fet.defaulturlbool", 1);
        }

        Flushbonadinginfo flushbonadinginfo = flushbonading_etinfoMapper.getFlushbonadinginfo(ew);
        if (null != flushbonadinginfo) {
            result.changeToTrue(flushbonadinginfo);
        }else{
            result.setMessage("没找到默认的审讯主机...");
        }

        return result;
    }

    @Override
    public RResult getToOutMiddleware_FTP(GetToOutMiddleware_FTPParam param, RResult result) {
        Gson gson=new Gson();
        if (null!=param){
            GetMiddleware_FTPParam_web getMiddleware_ftpParam= gson.fromJson(gson.toJson(param),GetMiddleware_FTPParam_web.class);
            getMiddleware_ftpParam.setFdssid(param.getFlushbonadingetinfossid());
            flushbonadingService.getMiddleware_FTP(getMiddleware_ftpParam,result);
        }
        return result;
    }

    @Override
    public RResult setToOutMiddleware_FTP(SetToOutMiddleware_FTPParam param, RResult result) {
        Gson gson=new Gson();
        if (null!=param){
            SetMiddleware_FTPParam_web setMiddleware_ftpParam= gson.fromJson(gson.toJson(param),SetMiddleware_FTPParam_web.class);
            setMiddleware_ftpParam.setFdssid(param.getFlushbonadingetinfossid());
            flushbonadingService.setMiddleware_FTP(setMiddleware_ftpParam,result);
        }
        return result;
    }

    @Override
    public RResult getToOutFtpPort(GetToOutMiddleware_FTPParam param, RResult result) {

        GetToOutFtpPortVO vo = new GetToOutFtpPortVO();

        if (null!=param){
            //获取默认设备
            GetMiddleware_FTPParam_web getMiddleware_ftpParam = new GetMiddleware_FTPParam_web();

            String flushbonadingetinfossid = param.getFlushbonadingetinfossid();
            if (StringUtils.isNotEmpty(flushbonadingetinfossid)){
                getMiddleware_ftpParam.setFdssid(flushbonadingetinfossid);
            }else {
                EntityWrapper<Flushbonadinginfo> ew = new EntityWrapper<>();
                ew.eq("fet.defaulturlbool", 1);

                Flushbonadinginfo flushbonadinginfo = flushbonading_etinfoMapper.getFlushbonadinginfo(ew);
                if (null != flushbonadinginfo) {
                    result.changeToTrue(flushbonadinginfo);
                }else{
                    result.setMessage("没找到默认的审讯主机...");
                    return result;
                }
                getMiddleware_ftpParam.setFdssid(flushbonadinginfo.getSsid());
            }

            flushbonadingService.getMiddleware_FTP(getMiddleware_ftpParam,result);

            Ss_saveinfo saveinfo = new Ss_saveinfo();
            saveinfo.setDefaultsavebool(1);
            Ss_saveinfo ss_saveinfo = ss_saveinfoMapper.selectOne(saveinfo);
            if(null != ss_saveinfo){
                vo.setSaveinfoport(ss_saveinfo.getPort() + "");
            }

            GetMiddleware_FTPVO middlewareFtpvo = (GetMiddleware_FTPVO) result.getData();
            vo.setFtpport(middlewareFtpvo.getServiceport());
            result.setData(vo);
        }
        return result;
    }

    @Override
    public RResult setToOutBacktxtinterface(SetToOutBacktxtinterfaceParam pParam, RResult result) {

        if(null == pParam.getPort()){
            result.setMessage("端口号不能为空");
            return result;
        }

        //遍历每一条，逐条修改端口
        List<Asr_etinfo> asr_etinfos = asr_etinfoMapper.selectList(null);
        for (Asr_etinfo asr_etinfo : asr_etinfos) {
            String backtxtinterface = asr_etinfo.getBacktxtinterface();

            int startNum = backtxtinterface.indexOf(":", 10);
            int endNum = backtxtinterface.indexOf("/", 10);

            if (startNum > 0 && endNum > 0) {
                String subPort = backtxtinterface.substring(startNum, endNum);
                if(StringUtils.isNotBlank(subPort)){
                    backtxtinterface = backtxtinterface.replace(subPort + "/", ":" + pParam.getPort() + "/");
                    asr_etinfo.setBacktxtinterface(backtxtinterface);
                    asr_etinfo.updateById();
                }
            }
        }

        result.changeToTrue();

        return result;
    }

    @Override
    public RResult setFtpAndSaveinfoPort(SetToOutBacktxtinterfaceParam pParam, RResult result) {

        if(null == pParam.getPort()){
            result.setMessage("端口号不能为空");
            return result;
        }

        //数据库ec的ss_saveinfo中的port
        //设备集中控制ftp的port也要修改


        EntityWrapper<Ss_saveinfo> ew = new EntityWrapper<>();
        Ss_saveinfo saveinfo = new Ss_saveinfo();
        saveinfo.setPort(pParam.getPort());

        Integer update = ss_saveinfoMapper.update(saveinfo, ew);

        if(null != pParam.getFtpport()){

            EntityWrapper<Flushbonading_etinfo> ew2 = new EntityWrapper<>();
            ew2.eq("defaulturlbool", "1");

            List<Flushbonading_etinfo> flushbonading_etinfoList = flushbonading_etinfoMapper.selectList(ew2);
            if (flushbonading_etinfoList.size() > 0) {

                Flushbonading_etinfo flushbonading_etinfo = flushbonading_etinfoList.get(0);
                //请求ftp集中控制信息
                EntityWrapper<Flushbonading_etinfo> ewFlush=new EntityWrapper<Flushbonading_etinfo>();
                ewFlush.eq("fet.ssid",flushbonading_etinfo.getSsid());
                Flushbonadinginfo flushbonadinginfo = flushbonading_etinfoMapper.getFlushbonadinginfo(ewFlush);

                GetMiddleware_FTPParam getMiddleware_ftpParam=new GetMiddleware_FTPParam();
                getMiddleware_ftpParam.setIp(flushbonadinginfo.getEtip());
                getMiddleware_ftpParam.setPasswd(flushbonadinginfo.getPasswd());
                getMiddleware_ftpParam.setPort(flushbonadinginfo.getPort());
                getMiddleware_ftpParam.setUser(flushbonadinginfo.getUser());

                RResult middlewareFtp = fdInterface.getMiddleware_FTP(getMiddleware_ftpParam, result);
                if(null != middlewareFtp && "SUCCESS".equalsIgnoreCase(middlewareFtp.getActioncode())){
                    SetMiddleware_FTPParam setMiddleware_ftpParam=gson.fromJson(gson.toJson(middlewareFtp.getData()), SetMiddleware_FTPParam.class);
                    setMiddleware_ftpParam.setServerport(pParam.getFtpport() + "");
                    setMiddleware_ftpParam.setIp(flushbonadinginfo.getEtip());
                    setMiddleware_ftpParam.setPort(flushbonadinginfo.getPort());

                    middlewareFtp = fdInterface.setMiddleware_FTP(setMiddleware_ftpParam, result);
                    System.out.println(middlewareFtp);
                }
            }
        }

        if (update >= 1) {
            result.changeToTrue();
        }

        return result;
    }


}
