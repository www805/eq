package com.avst.equipmentcontrol.web.vo.tts;

import com.avst.equipmentcontrol.common.datasourse.extrasourse.tts.entity.param.TTS_et_ettype;
import com.avst.equipmentcontrol.web.req.tts.TtsetinfoParam;

import java.util.List;

public class TtsetinfoVO {

    private List<TTS_et_ettype> pagelist;
    private TtsetinfoParam pageparam;

    public List<TTS_et_ettype> getPagelist() {
        return pagelist;
    }

    public void setPagelist(List<TTS_et_ettype> pagelist) {
        this.pagelist = pagelist;
    }

    public TtsetinfoParam getPageparam() {
        return pageparam;
    }

    public void setPageparam(TtsetinfoParam pageparam) {
        this.pageparam = pageparam;
    }
}
