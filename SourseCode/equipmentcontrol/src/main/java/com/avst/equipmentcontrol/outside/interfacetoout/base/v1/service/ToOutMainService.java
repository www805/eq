package com.avst.equipmentcontrol.outside.interfacetoout.base.v1.service;

import com.avst.equipmentcontrol.common.datasourse.extrasourse.asr.mapper.Asr_etinfoMapper;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.flushbonading.mapper.Flushbonading_etinfoMapper;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.polygraph.mapper.Polygraph_etinfoMapper;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.mapper.Ss_saveinfoMapper;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.tts.mapper.Tts_etinfoMapper;
import com.avst.equipmentcontrol.common.util.baseaction.BaseService;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.common.util.baseaction.ReqParam;
import com.avst.equipmentcontrol.outside.interfacetoout.base.vo.GethomeVO;
import com.avst.equipmentcontrol.web.vo.EcCountVO;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ToOutMainService extends BaseService {

    @Autowired
    private Flushbonading_etinfoMapper flushbonading_etinfoMapper;

    @Autowired
    private Asr_etinfoMapper asr_etinfoMapper;

    @Autowired
    private Polygraph_etinfoMapper polygraph_etinfoMapper;

    @Autowired
    private Ss_saveinfoMapper ss_saveinfoMapper;

    @Autowired
    private Tts_etinfoMapper tts_etinfoMapper;



    public  void gethome(ReqParam param, RResult result){
        EntityWrapper ew = new EntityWrapper();

        //设备总数
        Integer fdcount = flushbonading_etinfoMapper.selectCount(ew);
        Integer asrcount = asr_etinfoMapper.selectCount(ew);
        Integer phcount = polygraph_etinfoMapper.selectCount(ew);
        Integer ttscount = tts_etinfoMapper.selectCount(ew);

        GethomeVO gethomeVO = new GethomeVO();
        gethomeVO.setFdcount(fdcount);
        gethomeVO.setAsrcount(asrcount);
        gethomeVO.setPhcount(phcount);
        gethomeVO.setTtscount(ttscount);

        result.setData(gethomeVO);
        changeResultToSuccess(result);
        return;
    }

}
