package com.avst.equipmentcontrol.outside.interfacetoout.asr.v1.service;

import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.common.util.baseaction.ReqParam;
import com.avst.equipmentcontrol.outside.interfacetoout.asr.req.OverAsrParam;
import com.avst.equipmentcontrol.outside.interfacetoout.asr.req.PauseOrContinueAsrParam;
import com.avst.equipmentcontrol.outside.interfacetoout.asr.req.StartAsrParam;
import org.springframework.stereotype.Service;

@Service
public interface ToOutService_asr {

    public RResult startAsr(StartAsrParam param,RResult rResult);

    /**
     * 整个会议里面的所有语音识别全部一起启动
     * @param param
     * @param rResult
     * @return
     */
    public RResult startAsr_all(StartAsrParam param,RResult rResult);

    public RResult overAsr(OverAsrParam param,RResult rResult);

    public RResult pauseOrContinueAsr(PauseOrContinueAsrParam param, RResult rResult);

}
