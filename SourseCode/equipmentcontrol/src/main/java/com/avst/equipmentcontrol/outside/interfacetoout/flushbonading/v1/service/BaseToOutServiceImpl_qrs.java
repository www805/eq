package com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.v1.service;

import com.avst.equipmentcontrol.common.datasourse.extrasourse.flushbonading.entity.Flushbonading_etinfo;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.flushbonading.entity.Flushbonading_ettd;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.flushbonading.entity.param.Flushbonadinginfo;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.flushbonading.mapper.Flushbonading_etinfoMapper;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.flushbonading.mapper.Flushbonading_ettdMapper;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.req.GetFlushbonadingBySsidParam;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.req.GetFlushbonadingTDByETSsidParam;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.req.GetRecordByIidParam;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.vo.GetFlushbonadingBySsidVO;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 设备的基本service处理类，增删改查
 */
@Service
public class BaseToOutServiceImpl_qrs {

    @Autowired
    private Flushbonading_etinfoMapper flushbonading_etinfoMapper;

    @Autowired
    private Flushbonading_ettdMapper flushbonading_ettdMapper;


    public RResult getFlushbonadingBySsid(GetFlushbonadingBySsidParam param, RResult rResult){

        String equipmentSsid=param.getFlushbonadingEquipmentSsid();

        EntityWrapper<Flushbonadinginfo> entityWrapper=new EntityWrapper<Flushbonadinginfo>();
        entityWrapper.eq("fet.ssid",equipmentSsid);
        try {
            Flushbonadinginfo etinfo=flushbonading_etinfoMapper.getFlushbonadinginfo(entityWrapper);
            if(null!=etinfo){
                Gson gson = new Gson();
                GetFlushbonadingBySsidVO vo=gson.fromJson(gson.toJson(etinfo),GetFlushbonadingBySsidVO.class);
                rResult.changeToTrue(vo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rResult;
    }

    public RResult getFlushbonadingTDByETSsid(GetFlushbonadingTDByETSsidParam param, RResult rResult){

        String equipmentSsid=param.getFlushbonadingEquipmentSsid();
        int ettype=param.getTdtype();

        EntityWrapper<Flushbonading_ettd> entityWrapper=new EntityWrapper<Flushbonading_ettd>();
        entityWrapper.eq("flushbonadingssid",equipmentSsid);
        if(0!=ettype){
            entityWrapper.eq("tdtype",ettype);
        }

        try {
            List<Flushbonading_ettd> etlist=flushbonading_ettdMapper.selectList(entityWrapper);
            if(null!=etlist&&etlist.size() > 0){
                Gson gson = new Gson();
                List<GetFlushbonadingTDByETSsidParam> vo=new ArrayList<GetFlushbonadingTDByETSsidParam>();
                vo=gson.fromJson(gson.toJson(etlist),vo.getClass());
                rResult.changeToTrue(vo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rResult;
    }



}
