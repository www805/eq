package com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.v1.service;

import com.avst.equipmentcontrol.common.datasourse.extrasourse.flushbonading.entity.Flushbonading_etinfo;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.flushbonading.entity.param.Flushbonadinginfo;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.flushbonading.mapper.Flushbonading_etinfoMapper;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.common.util.baseaction.ReqParam;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.req.AddOrUpdateToOutFlushbonadingParam;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.req.BaseParam;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.req.GetBurnTimeParam;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.req.GetToOutFlushbonadingListParam;
import com.avst.equipmentcontrol.web.req.flushbonading.FlushbonadinginfoParam;
import com.avst.equipmentcontrol.web.service.FlushbonadingService;
import com.avst.equipmentcontrol.web.vo.flushbonading.FlushbonadinginfoVO;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
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

        flushbonadingService.getFlushbonadingList(result, reqParam);

        if(!"FAIL".equals(result.getActioncode())){
            FlushbonadinginfoVO data = (FlushbonadinginfoVO) result.getData();
            List<Flushbonadinginfo> list = data.getPagelist();
            result.setData(list);
        }

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
        reqParam.setParam(flushbonadinginfo);

        flushbonadingService.updateFlushbonading(result, reqParam);

        return result;
    }

    @Override
    public RResult getToOutDefault(GetToOutFlushbonadingListParam param, RResult result) {

        EntityWrapper<Flushbonadinginfo> ew = new EntityWrapper<>();
        ew.eq("defaulturlbool", 1);

        Flushbonadinginfo flushbonadinginfo = flushbonading_etinfoMapper.getFlushbonadinginfo(ew);
        if (null != flushbonadinginfo) {
            result.changeToTrue(flushbonadinginfo);
        }else{
            result.setMessage("没找到默认的审讯主机...");
        }

        return result;
    }


}
