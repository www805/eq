package com.avst.equipmentcontrol.outside.interfacetoout.asr.v1.service;

import com.avst.equipmentcontrol.common.datasourse.extrasourse.asr.entity.Asr_et_ettype;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.asr.entity.Asr_etinfo;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.asr.mapper.Asr_etinfoMapper;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.common.util.baseaction.ReqParam;
import com.avst.equipmentcontrol.outside.interfacetoout.asr.req.AddOrUpdateToOutAsrParam;
import com.avst.equipmentcontrol.outside.interfacetoout.asr.req.GetToOutAsrListParam;
import com.avst.equipmentcontrol.web.req.asr.AsrParam;
import com.avst.equipmentcontrol.web.req.asr.UpdateAsrParam;
import com.avst.equipmentcontrol.web.service.AsrService;
import com.avst.equipmentcontrol.web.vo.asr.AsrVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToOutAsrServiceImpl implements ToOutAsrService {

    @Autowired
    private AsrService asrService;

    @Autowired
    private Asr_etinfoMapper asr_etinfoMapper;

    //查询列表
    @Override
    public RResult getToOutAsrList(GetToOutAsrListParam param, RResult result) {

        List<Asr_etinfo> asr_etinfos = asr_etinfoMapper.selectList(null);
        result.changeToTrue(asr_etinfos);

//        ReqParam<AsrParam> reqParam = new ReqParam<AsrParam>();
//
//        AsrParam asrParam = new AsrParam();
//        reqParam.setParam(asrParam);
//
//        asrService.getAsrList(result, reqParam);
//
//        if(!"FAIL".equals(result.getActioncode())){
//            AsrVO data = (AsrVO) result.getData();
//            List<Asr_et_ettype> list = data.getPagelist();
//            result.setData(list);
//        }

        return result;
    }

    //查询单个
    @Override
    public RResult getToOutAsrById(GetToOutAsrListParam param, RResult result) {

        ReqParam<AsrParam> reqParam = new ReqParam<AsrParam>();

        AsrParam asrParam = new AsrParam();
        asrParam.setSsid(param.getSsid());
        reqParam.setParam(asrParam);

        asrService.getAsrById(result, reqParam);

        return result;
    }

    //新增
    @Override
    public RResult addToOutAsr(AddOrUpdateToOutAsrParam param, RResult result) {

        ReqParam<UpdateAsrParam> reqParam = new ReqParam<UpdateAsrParam>();

        UpdateAsrParam updateAsrParam = new UpdateAsrParam();
        updateAsrParam.setLanguage(param.getLanguage());
        updateAsrParam.setMaxnum(param.getMaxnum());
        updateAsrParam.setPort(param.getPort());
        updateAsrParam.setAsrkey(param.getAsrkey());
        updateAsrParam.setAsrtype(param.getAsrtype());
        updateAsrParam.setEtnum(param.getEtnum());
        updateAsrParam.setEtip(param.getEtip());
        updateAsrParam.setBacktxtinterface(param.getBacktxtinterface());
        updateAsrParam.setEtypessid(param.getEtypessid());
        updateAsrParam.setExplain(param.getExplain());
        reqParam.setParam(updateAsrParam);

        asrService.addAsr(result, reqParam);

        return result;
    }

    //修改
    @Override
    public RResult updateToOutAsr(AddOrUpdateToOutAsrParam param, RResult result) {

        ReqParam<UpdateAsrParam> reqParam = new ReqParam<UpdateAsrParam>();

        UpdateAsrParam updateAsrParam = new UpdateAsrParam();
        updateAsrParam.setSsid(param.getSsid());
        updateAsrParam.setLanguage(param.getLanguage());
        updateAsrParam.setMaxnum(param.getMaxnum());
        updateAsrParam.setPort(param.getPort());
        updateAsrParam.setAsrkey(param.getAsrkey());
        updateAsrParam.setAsrtype(param.getAsrtype());
        updateAsrParam.setEtnum(param.getEtnum());
        updateAsrParam.setEtip(param.getEtip());
        updateAsrParam.setEtypessid(param.getEtypessid());
        updateAsrParam.setExplain(param.getExplain());
        updateAsrParam.setBacktxtinterface(param.getBacktxtinterface());
        reqParam.setParam(updateAsrParam);

        asrService.updateAsr(result, reqParam);

        return result;
    }
}
