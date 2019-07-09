package com.avst.equipmentcontrol.web.service;

import com.avst.equipmentcontrol.common.conf.Constant;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.asr.mapper.Asr_etinfoMapper;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.flushbonading.mapper.Flushbonading_etinfoMapper;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.polygraph.mapper.Polygraph_etinfoMapper;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.mapper.Ss_saveinfoMapper;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.tts.mapper.Tts_etinfoMapper;
import com.avst.equipmentcontrol.common.datasourse.publicsourse.mapper.Base_equipmentinfoMapper;
import com.avst.equipmentcontrol.common.datasourse.publicsourse.mapper.Base_ettypeMapper;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.web.req.LoginParam;
import com.avst.equipmentcontrol.web.vo.EcCountVO;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class MainService {

    @Autowired
    private Flushbonading_etinfoMapper flushbonading_etinfoMapper;

    @Autowired
    private Asr_etinfoMapper asr_etinfoMapper;

    @Autowired
    private Polygraph_etinfoMapper polygraph_etinfoMapper;

    @Autowired
    private Ss_saveinfoMapper ss_saveinfoMapper;

    @Autowired
    private Base_ettypeMapper base_ettypeMapper;

    @Autowired
    private Tts_etinfoMapper tts_etinfoMapper;

    public RResult logining(RResult result, HttpServletRequest request, LoginParam loginParam){

        if(loginParam.getLoginaccount().equals("admin")&&loginParam.getPassword().equals("admin123")){
            result.changeToTrue();

            request.getSession().setAttribute(Constant.MANAGE_WEB,loginParam);

        }else{
            result.setMessage("登录失败");
        }
        return result;
    }

    //首页统计
    public EcCountVO homeCount(){

        EntityWrapper ew = new EntityWrapper();

        //设备总数
        Integer flushbonadingCount = flushbonading_etinfoMapper.selectCount(ew);
        Integer asrCount = asr_etinfoMapper.selectCount(ew);
        Integer polygraphCount = polygraph_etinfoMapper.selectCount(ew);
        Integer ssCount = ss_saveinfoMapper.selectCount(ew);
        //设备类型总数
        Integer ettypeCount = base_ettypeMapper.selectCount(ew);
        Integer ttsCount = tts_etinfoMapper.selectCount(ew);

        EcCountVO ecCountVO = new EcCountVO();
        ecCountVO.setFlushbonadingCount(flushbonadingCount);
        ecCountVO.setAsrCount(asrCount);
        ecCountVO.setPolygraphCount(polygraphCount);
        ecCountVO.setSsCount(ssCount);
        ecCountVO.setEttypeCount(ettypeCount);
        ecCountVO.setTtsCount(ttsCount);

        return ecCountVO;
    }

}
