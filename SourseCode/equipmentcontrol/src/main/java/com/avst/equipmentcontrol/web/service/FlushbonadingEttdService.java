package com.avst.equipmentcontrol.web.service;

import com.avst.equipmentcontrol.common.datasourse.extrasourse.flushbonading.entity.FlushbonadingEttd;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.flushbonading.entity.Flushbonading_ettd;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.flushbonading.mapper.Flushbonading_etinfoMapper;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.flushbonading.mapper.Flushbonading_ettdMapper;
import com.avst.equipmentcontrol.common.util.OpenUtil;
import com.avst.equipmentcontrol.common.util.baseaction.BaseService;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.common.util.baseaction.ReqParam;
import com.avst.equipmentcontrol.web.req.flushbonadingEttd.FlushbonadingEttdParam;
import com.avst.equipmentcontrol.web.req.flushbonadingEttd.UpdateFlushbonadingEttdParam;
import com.avst.equipmentcontrol.web.vo.flushbonadingEttd.FlushbonadingEttdVO;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlushbonadingEttdService extends BaseService {

    @Autowired
    private Flushbonading_ettdMapper flushbonading_ettdMapper;

    @Autowired
    private Flushbonading_etinfoMapper flushbonading_etinfoMapper;

    //查询
    public void getFlushbonadingEttdList(RResult result, ReqParam<FlushbonadingEttdParam> param){

        FlushbonadingEttdVO flushbonadingEttdVO = new FlushbonadingEttdVO();

        //请求参数转换
        FlushbonadingEttdParam paramParam = param.getParam();
        if (null==paramParam){
            result.setMessage("参数为空");
            return;
        }

        if (StringUtils.isBlank(paramParam.getSsid())){
            result.setMessage("查询的参数ssid不能为空");
            return;
        }

        EntityWrapper ew=new EntityWrapper();
        ew.eq("f.ssid", paramParam.getSsid());

        int count = flushbonading_ettdMapper.getFlushbonadingEttdCount(ew);
        paramParam.setRecordCount(count);

        ew.orderBy("f.id",false);
        Page<Flushbonading_ettd> page=new Page<Flushbonading_ettd>(paramParam.getCurrPage(),paramParam.getPageSize());

        List<FlushbonadingEttd> flushbonadingEttdPage = flushbonading_ettdMapper.getFlushbonadingEttdPage(page, ew);

        flushbonadingEttdVO.setPagelist(flushbonadingEttdPage);
        flushbonadingEttdVO.setPageparam(paramParam);

        result.setData(flushbonadingEttdVO);
        changeResultToSuccess(result);
    }


    //查询单次
    public void getFlushbonadingEttdById(RResult result, ReqParam<FlushbonadingEttdParam> param){

        //请求参数转换
        FlushbonadingEttdParam paramParam = param.getParam();
        if (null==paramParam){
            result.setMessage("参数为空");
            return;
        }

        if (StringUtils.isBlank(paramParam.getSsid())){
            result.setMessage("查询的参数不能为空");
            return;
        }

        EntityWrapper ew=new EntityWrapper();
        ew.eq("e.ssid",paramParam.getSsid());

        FlushbonadingEttd flushbonadingEttd = flushbonading_ettdMapper.getFlushbonadingEttd(ew);

        result.setData(flushbonadingEttd);
        changeResultToSuccess(result);

    }


    //新增
    public void addFlushbonadingEttd(RResult result, ReqParam<UpdateFlushbonadingEttdParam> param){

        //请求参数转换
        UpdateFlushbonadingEttdParam paramParam = param.getParam();
        if (null==paramParam){
            result.setMessage("参数为空");
            return;
        }

        if (StringUtils.isBlank(paramParam.getTdnum())){
            result.setMessage("通道编号不能为空");
            return;
        }
        if (StringUtils.isBlank(paramParam.getPullflowurl())){
            result.setMessage("通道拉流地址不能为空");
            return;
        }
        if (null == paramParam.getTdtype()){
            result.setMessage("通道类型不能为空");
            return;
        }

        FlushbonadingEttd flushbonadingEttd = new FlushbonadingEttd();
        flushbonadingEttd.setFlushbonadingssid(paramParam.getFlushbonadingssid());
        flushbonadingEttd.setTdnum(paramParam.getTdnum());
        flushbonadingEttd.setPullflowurl(paramParam.getPullflowurl());
        flushbonadingEttd.setTdtype(paramParam.getTdtype());
        flushbonadingEttd.setSsid(OpenUtil.getUUID_32());

        Integer insert = flushbonading_ettdMapper.insert(flushbonadingEttd);
        System.out.println("add_boot：" + insert);
        if(insert == 1){
            result.setData(flushbonadingEttd.getSsid());
        }else{
            result.setData(insert);
        }

        changeResultToSuccess(result);

    }

    //修改
    public void updateFlushbonadingEttd(RResult result, ReqParam<UpdateFlushbonadingEttdParam> param){

        //请求参数转换
        UpdateFlushbonadingEttdParam paramParam = param.getParam();
        if (null==paramParam){
            result.setMessage("参数为空");
            return;
        }

        if (StringUtils.isBlank(paramParam.getFlushbonadingssid())){
            result.setMessage("父参数的ssid不能为空");
            return;
        }
        if (StringUtils.isBlank(paramParam.getSsid())){
            result.setMessage("参数ssid不能为空");
            return;
        }
        if (StringUtils.isBlank(paramParam.getTdnum())){
            result.setMessage("通道编号不能为空");
            return;
        }
        if (StringUtils.isBlank(paramParam.getPullflowurl())){
            result.setMessage("通道拉流地址不能为空");
            return;
        }
        if (null == paramParam.getTdtype()){
            result.setMessage("通道类型不能为空");
            return;
        }


        EntityWrapper ew = new EntityWrapper();
        ew.eq("ssid", paramParam.getSsid());

        FlushbonadingEttd flushbonadingEttd = new FlushbonadingEttd();
        flushbonadingEttd.setTdnum(paramParam.getTdnum());
        flushbonadingEttd.setPullflowurl(paramParam.getPullflowurl());
        flushbonadingEttd.setTdtype(paramParam.getTdtype());

        Integer update = flushbonading_ettdMapper.update(flushbonadingEttd, ew);
        System.out.println("update_boot：" + update);
        if(update == 1){
            result.setData(flushbonadingEttd.getSsid());
        }else{
            result.setData(update);
        }
        changeResultToSuccess(result);
    }

    //删除
    public void delFlushbonadingEttd(RResult result, ReqParam<FlushbonadingEttdParam> param){

        //请求参数转换
        FlushbonadingEttdParam paramParam = param.getParam();
        if (null==paramParam){
            result.setMessage("参数为空");
            return;
        }

        if (StringUtils.isBlank(paramParam.getSsid())){
            result.setMessage("删除的ssid不能为空");
            return;
        }

        EntityWrapper ew = new EntityWrapper();
        ew.eq("ssid", paramParam.getSsid());
        Integer delete = flushbonading_ettdMapper.delete(ew);

        result.setData(delete);
        changeResultToSuccess(result);

    }

}
