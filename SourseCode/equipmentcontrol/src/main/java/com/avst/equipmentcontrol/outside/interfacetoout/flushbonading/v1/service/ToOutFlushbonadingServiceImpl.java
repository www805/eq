package com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.v1.service;

import com.avst.equipmentcontrol.common.datasourse.extrasourse.flushbonading.entity.Flushbonading_etinfo;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.flushbonading.entity.param.Flushbonadinginfo;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.flushbonading.mapper.Flushbonading_etinfoMapper;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.common.util.baseaction.ReqParam;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.req.*;
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


}
