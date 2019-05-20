package com.avst.equipmentcontrol.outside.interfacetoout.asr.v1.service;

import com.avst.equipmentcontrol.common.datasourse.extrasourse.asr.entity.Asr_et_ettype;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.asr.entity.Asr_etinfo;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.asr.mapper.Asr_etinfoMapper;
import com.avst.equipmentcontrol.common.util.JacksonUtil;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.outside.interfacetoout.asr.req.GetAsrServerBySsidParam;
import com.avst.equipmentcontrol.outside.interfacetoout.asr.vo.GetAsrServerBySsidVO;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 设备的基本service处理类，增删改查
 */
@Service
public class BaseToOutServiceImpl_asr {

    @Autowired
    private Asr_etinfoMapper asr_etinfoMapper;

    /**
     * 语音服务器的查找
     * @param param
     * @param rResult
     * @return
     */
    public RResult getAsrServerBySsid(GetAsrServerBySsidParam param, RResult rResult){

        String asrEquipmentSsid=param.getAsrEquipmentSsid();

        EntityWrapper<Asr_etinfo> entityWrapper=new EntityWrapper<Asr_etinfo>();
        entityWrapper.eq("aet.ssid",asrEquipmentSsid);
        try {
            Asr_et_ettype asrinfo=asr_etinfoMapper.getAsrinfo(entityWrapper);
            if(null!=asrinfo){
                Gson gson = new Gson();
                GetAsrServerBySsidVO vo=gson.fromJson(gson.toJson(asrinfo),GetAsrServerBySsidVO.class);
                rResult.changeToTrue(vo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rResult;
    }

}
