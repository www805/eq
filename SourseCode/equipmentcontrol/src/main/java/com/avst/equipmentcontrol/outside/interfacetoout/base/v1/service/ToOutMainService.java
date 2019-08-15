package com.avst.equipmentcontrol.outside.interfacetoout.base.v1.service;

import com.avst.equipmentcontrol.common.datasourse.extrasourse.asr.entity.Asr_et_ettype;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.asr.mapper.Asr_etinfoMapper;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.flushbonading.entity.param.Flushbonadinginfo;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.flushbonading.mapper.Flushbonading_etinfoMapper;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.polygraph.entity.param.PolygraphInfo;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.polygraph.mapper.Polygraph_etinfoMapper;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.mapper.Ss_saveinfoMapper;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.tts.mapper.Tts_etinfoMapper;
import com.avst.equipmentcontrol.common.datasourse.publicsourse.entity.Base_equipmentinfo;
import com.avst.equipmentcontrol.common.datasourse.publicsourse.mapper.Base_equipmentinfoMapper;
import com.avst.equipmentcontrol.common.util.baseaction.BaseService;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.common.util.baseaction.ReqParam;
import com.avst.equipmentcontrol.outside.interfacetoout.base.req.GetServerIpALLParam;
import com.avst.equipmentcontrol.outside.interfacetoout.base.req.GetServerIpParam;
import com.avst.equipmentcontrol.outside.interfacetoout.base.req.ServerIpssidParam;
import com.avst.equipmentcontrol.outside.interfacetoout.base.vo.GethomeVO;
import com.avst.equipmentcontrol.web.vo.baseEttype.EquipmentBasicsVO;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

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

    @Autowired
    private Base_equipmentinfoMapper base_equipmentinfoMapper;

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

    public void getServerIpALL(ReqParam<GetServerIpALLParam> param, RResult result) {

        GetServerIpALLParam paramParam = param.getParam();

        EntityWrapper ew = new EntityWrapper();
        ew.eq("fet.ssid", paramParam.getFdssid());
        /**审讯设备**/
        Flushbonadinginfo flushbonadinginfo = flushbonading_etinfoMapper.getFlushbonadinginfo(ew);

        EntityWrapper ew1=new EntityWrapper();
        ew1.eq("aet.ssid",paramParam.getAsrssid());
        /**语音服务器**/
        Asr_et_ettype asrinfo = asr_etinfoMapper.getAsrinfo(ew1);

        EntityWrapper ew2 = new EntityWrapper();
        ew2.eq("pet.ssid",paramParam.getPolygraphssid());
        /**测谎仪**/
        PolygraphInfo polygraphInfo = polygraph_etinfoMapper.getPolygraphInfo(ew2);

        EntityWrapper ew3 = new EntityWrapper();
        ew3.eq("et.EXPLAIN", "存储服务");
        ew3.last("LIMIT 1");
        /**存储服务**/
        EquipmentBasicsVO ccfwIp = base_equipmentinfoMapper.getEquipmentBasicsByIp(ew3);

        EntityWrapper ew4 = new EntityWrapper();
        ew4.eq("et.EXPLAIN", "文字转语音服务");
        ew4.last("LIMIT 1");
        /**文字转语音服务**/
        EquipmentBasicsVO wzzyyfwIp = base_equipmentinfoMapper.getEquipmentBasicsByIp(ew4);

        HashMap<String, Object> map = new HashMap<>();
        map.put("flushbonadingip", flushbonadinginfo);
        map.put("asrip", asrinfo);
        map.put("polygraphip", polygraphInfo);
        map.put("storageip", ccfwIp);
        map.put("ttsetinfoip", wzzyyfwIp);

        result.setData(map);
        result.changeToTrue();

        return;
    }

    public void updateServerIp(GetServerIpParam getServerIpParam, RResult result) {

        ServerIpssidParam asrip = getServerIpParam.getAsrip();
        ServerIpssidParam flushbonadingip = getServerIpParam.getFlushbonadingip();
        ServerIpssidParam polygraphip = getServerIpParam.getPolygraphip();
        ServerIpssidParam storageip = getServerIpParam.getStorageip();
        ServerIpssidParam ttsetinfoip = getServerIpParam.getTtsetinfoip();

        EntityWrapper ewasr = new EntityWrapper();
        ewasr.eq("ssid", asrip.getSsid());
        Base_equipmentinfo base_equipmentinfo = new Base_equipmentinfo();
        base_equipmentinfo.setEtip(asrip.getEtip());
        Integer update = base_equipmentinfoMapper.update(base_equipmentinfo, ewasr);

        EntityWrapper ewflushbonadingip = new EntityWrapper();
        ewflushbonadingip.eq("ssid", flushbonadingip.getSsid());
        Base_equipmentinfo base_equipmentinfo2 = new Base_equipmentinfo();
        base_equipmentinfo2.setEtip(flushbonadingip.getEtip());
        Integer update1 = base_equipmentinfoMapper.update(base_equipmentinfo2, ewflushbonadingip);

        EntityWrapper ewpolygraphip = new EntityWrapper();
        ewpolygraphip.eq("ssid", polygraphip.getSsid());
        Base_equipmentinfo base_equipmentinfo3 = new Base_equipmentinfo();
        base_equipmentinfo3.setEtip(polygraphip.getEtip());
        Integer update2 = base_equipmentinfoMapper.update(base_equipmentinfo3, ewpolygraphip);

        EntityWrapper ewstorageip = new EntityWrapper();
        ewstorageip.eq("ssid", storageip.getSsid());
        Base_equipmentinfo base_equipmentinfo4 = new Base_equipmentinfo();
        base_equipmentinfo4.setEtip(storageip.getEtip());
        Integer update3 = base_equipmentinfoMapper.update(base_equipmentinfo4, ewstorageip);

        EntityWrapper ewttsetinfoip = new EntityWrapper();
        ewttsetinfoip.eq("ssid", ttsetinfoip.getSsid());
        Base_equipmentinfo base_equipmentinfo5 = new Base_equipmentinfo();
        base_equipmentinfo5.setEtip(ttsetinfoip.getEtip());
        Integer update4 = base_equipmentinfoMapper.update(base_equipmentinfo5, ewttsetinfoip);

        result.changeToTrue();

        return;
    }
}
