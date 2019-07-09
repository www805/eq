package com.avst.equipmentcontrol.outside.interfacetoout.tts.v1.service;

import com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.entity.Storage_ettype;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.entity.param.Ss_dataMessageParam;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.mapper.Ss_databaseMapper;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.tts.entity.param.TTS_et_ettype;
import com.avst.equipmentcontrol.common.util.baseaction.BaseService;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.common.util.baseaction.ReqParam;
import com.avst.equipmentcontrol.outside.interfacetoout.storage.req.AddOrUpdateToOutStorageParam;
import com.avst.equipmentcontrol.outside.interfacetoout.storage.req.GetToOutStorageListParam;
import com.avst.equipmentcontrol.outside.interfacetoout.storage.v1.service.ToOutStorageService;
import com.avst.equipmentcontrol.outside.interfacetoout.tts.req.AddOrUpdateToOutTtsEtinfoParam;
import com.avst.equipmentcontrol.outside.interfacetoout.tts.req.GetToOutTtsEtinfoListParam;
import com.avst.equipmentcontrol.web.req.storage.StorageParam;
import com.avst.equipmentcontrol.web.req.storage.UpdateStorageParam;
import com.avst.equipmentcontrol.web.req.tts.TtsetinfoParam;
import com.avst.equipmentcontrol.web.req.tts.UpdateTtsetinfoParam;
import com.avst.equipmentcontrol.web.service.StorageService;
import com.avst.equipmentcontrol.web.service.TtsEtinfoService;
import com.avst.equipmentcontrol.web.vo.storage.StorageVO;
import com.avst.equipmentcontrol.web.vo.tts.TtsetinfoVO;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToOutTtsEtinfoServiceImpl extends BaseService implements ToOutTtsEtinfoService {

    @Autowired
    private TtsEtinfoService ttsEtinfoService;

    //查询列表
    @Override
    public RResult getToOutTtsEtinfoList(GetToOutTtsEtinfoListParam param, RResult result) {

        ReqParam<TtsetinfoParam> reqParam = new ReqParam<TtsetinfoParam>();

        TtsetinfoParam ttsetinfoParam = new TtsetinfoParam();
        reqParam.setParam(ttsetinfoParam);

        ttsEtinfoService.getTtsetinfoList(result, reqParam);

        if(!"FAIL".equals(result.getActioncode())){
            TtsetinfoVO data = (TtsetinfoVO) result.getData();
            List<TTS_et_ettype> list = data.getPagelist();
            result.setData(list);
        }

        return result;
    }

    //查询单个
    @Override
    public RResult getToOutTtsEtinfoById(GetToOutTtsEtinfoListParam param, RResult result) {

        ReqParam<TtsetinfoParam> reqParam = new ReqParam<TtsetinfoParam>();

        TtsetinfoParam TtsEtinfoParam = new TtsetinfoParam();
        TtsEtinfoParam.setSsid(param.getSsid());
        reqParam.setParam(TtsEtinfoParam);

        ttsEtinfoService.getTtsetinfoById(result, reqParam);

        return result;
    }

    //新增
    @Override
    public RResult addToOutTtsEtinfo(AddOrUpdateToOutTtsEtinfoParam param, RResult result) {

        ReqParam<UpdateTtsetinfoParam> reqParam = new ReqParam<UpdateTtsetinfoParam>();

        UpdateTtsetinfoParam ttsetinfoParam = new UpdateTtsetinfoParam();
        ttsetinfoParam.setLanguage(param.getLanguage());
        ttsetinfoParam.setMaxnum(param.getMaxnum());
        ttsetinfoParam.setPort(param.getPort());
        ttsetinfoParam.setEtypessid(param.getEtypessid());
        ttsetinfoParam.setTtstype(param.getTtstype());
        ttsetinfoParam.setTtskeys(param.getTtskeys());
        ttsetinfoParam.setEtnum(param.getEtnum());
        ttsetinfoParam.setEtip(param.getEtip());
        ttsetinfoParam.setExplain(param.getExplain());
        reqParam.setParam(ttsetinfoParam);

        ttsEtinfoService.addTtsetinfo(result, reqParam);

        return result;
    }

    //修改
    @Override
    public RResult updateToOutTtsEtinfo(AddOrUpdateToOutTtsEtinfoParam param, RResult result) {

        ReqParam<UpdateTtsetinfoParam> reqParam = new ReqParam<UpdateTtsetinfoParam>();

        UpdateTtsetinfoParam ttsetinfoParam = new UpdateTtsetinfoParam();
        ttsetinfoParam.setSsid(param.getSsid());
        ttsetinfoParam.setLanguage(param.getLanguage());
        ttsetinfoParam.setMaxnum(param.getMaxnum());
        ttsetinfoParam.setPort(param.getPort());
        ttsetinfoParam.setEtypessid(param.getEtypessid());
        ttsetinfoParam.setTtstype(param.getTtstype());
        ttsetinfoParam.setTtskeys(param.getTtskeys());
        ttsetinfoParam.setEtnum(param.getEtnum());
        ttsetinfoParam.setEtip(param.getEtip());
        ttsetinfoParam.setExplain(param.getExplain());
        reqParam.setParam(ttsetinfoParam);

        ttsEtinfoService.updateTtsetinfo(result, reqParam);

        return result;
    }


}
