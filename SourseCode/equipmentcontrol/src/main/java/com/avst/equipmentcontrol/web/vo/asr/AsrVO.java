package com.avst.equipmentcontrol.web.vo.asr;

import com.avst.equipmentcontrol.common.datasourse.extrasourse.asr.entity.Asr_et_ettype;
import com.avst.equipmentcontrol.web.req.asr.AsrParam;

import java.util.List;

public class AsrVO {

    private List<Asr_et_ettype> pagelist;
    private AsrParam pageparam;

    public List<Asr_et_ettype> getPagelist() {
        return pagelist;
    }

    public void setPagelist(List<Asr_et_ettype> pagelist) {
        this.pagelist = pagelist;
    }

    public AsrParam getPageparam() {
        return pageparam;
    }

    public void setPageparam(AsrParam pageparam) {
        this.pageparam = pageparam;
    }
}
