package com.avst.equipmentcontrol.outside.interfacetoout.base.v1.service;

import com.avst.equipmentcontrol.common.datasourse.extrasourse.asr.entity.Asr_et_ettype;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.asr.entity.Asr_etinfo;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.asr.mapper.Asr_etinfoMapper;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.flushbonading.entity.Flushbonading_etinfo;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.flushbonading.entity.Flushbonading_ettd;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.flushbonading.entity.param.Flushbonadinginfo;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.flushbonading.mapper.Flushbonading_etinfoMapper;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.flushbonading.mapper.Flushbonading_ettdMapper;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.polygraph.entity.param.PolygraphInfo;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.polygraph.mapper.Polygraph_etinfoMapper;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.mapper.Ss_saveinfoMapper;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.tts.mapper.Tts_etinfoMapper;
import com.avst.equipmentcontrol.common.datasourse.publicsourse.entity.Base_equipmentinfo;
import com.avst.equipmentcontrol.common.datasourse.publicsourse.entity.Base_ettype;
import com.avst.equipmentcontrol.common.datasourse.publicsourse.mapper.Base_equipmentinfoMapper;
import com.avst.equipmentcontrol.common.datasourse.publicsourse.mapper.Base_ettypeMapper;
import com.avst.equipmentcontrol.common.util.LogUtil;
import com.avst.equipmentcontrol.common.util.OpenUtil;
import com.avst.equipmentcontrol.common.util.baseaction.BaseService;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.common.util.baseaction.ReqParam;
import com.avst.equipmentcontrol.common.util.properties.PropertiesListenerConfig;
import com.avst.equipmentcontrol.outside.interfacetoout.base.req.GetServerIpALLParam;
import com.avst.equipmentcontrol.outside.interfacetoout.base.req.GetServerIpParam;
import com.avst.equipmentcontrol.outside.interfacetoout.base.req.ServerIpssidParam;
import com.avst.equipmentcontrol.outside.interfacetoout.base.vo.GethomeVO;
import com.avst.equipmentcontrol.web.req.flushbonadingEttd.FlushbonadingEttd;
import com.avst.equipmentcontrol.web.vo.baseEttype.EquipmentBasicsVO;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Service
public class ToOutMainServiceImpl extends BaseService implements ToOutMainService{

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

    @Autowired
    private Flushbonading_ettdMapper flushbonading_ettdMapper;

    @Autowired
    private Base_ettypeMapper base_ettypeMapper;

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

        String fdssid=paramParam.getFdssid();
        EntityWrapper ew = new EntityWrapper();
        ew.eq("fet.ssid", fdssid);
        /**审讯设备**/
        Flushbonadinginfo flushbonadinginfo = flushbonading_etinfoMapper.getFlushbonadinginfo(ew);
        if(null==flushbonadinginfo){
            LogUtil.intoLog(this.getClass(),fdssid+":fdssid 没有找到这个审讯设备");
            result.setMessage("没有找到这个审讯设备");
        }

        String asrssid=paramParam.getAsrssid();
        EntityWrapper ew1=new EntityWrapper();
        ew1.eq("aet.ssid",asrssid);
        /**语音服务器**/
        Asr_et_ettype asrinfo = asr_etinfoMapper.getAsrinfo(ew1);
        if(null==asrinfo){
            LogUtil.intoLog(this.getClass(),asrssid+":asrssid 没有找到这个语音服务器");
            result.setMessage("没有找到这个语音服务器");
        }

        String phssid=paramParam.getPolygraphssid();
        EntityWrapper ew2 = new EntityWrapper();
        ew2.eq("pet.ssid",phssid);
        /**审讯监护服务**/
        PolygraphInfo polygraphInfo = polygraph_etinfoMapper.getPolygraphInfo(ew2);
        if(null==polygraphInfo){
            LogUtil.intoLog(4,this.getClass(),phssid+":phssid 没有找到这个审讯监护服务");
            result.setMessage("没有找到这个审讯监护服务");
        }

        EntityWrapper ew3 = new EntityWrapper();
        ew3.eq("et.EXPLAIN", "存储服务");
        ew3.last("LIMIT 1");
        /**存储服务**/
        EquipmentBasicsVO ccfwIp = base_equipmentinfoMapper.getEquipmentBasicsByIp(ew3);
        if(null==ccfwIp){
            LogUtil.intoLog(4,this.getClass(),"没有找到这个存储服务");
            result.setMessage("没有找到这个存储服务");
        }

        EntityWrapper ew4 = new EntityWrapper();
        ew4.eq("et.EXPLAIN", "文字转语音服务");
        ew4.last("LIMIT 1");
        /**文字转语音服务**/
        EquipmentBasicsVO wzzyyfwIp = base_equipmentinfoMapper.getEquipmentBasicsByIp(ew4);
        if(null==wzzyyfwIp){
            LogUtil.intoLog(4,this.getClass(),"没有找到文字转语音服务");
            result.setMessage("没有找到文字转语音服务");
        }

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

        Integer update = 0;
        Integer update1 = 0;

        if(null != asrip && StringUtils.isNotBlank(asrip.getEtip())){
            EntityWrapper ewasr = new EntityWrapper();
            ewasr.eq("ssid", asrip.getSsid());
            Base_equipmentinfo base_equipmentinfo = new Base_equipmentinfo();
            base_equipmentinfo.setEtip(asrip.getEtip());
            update = base_equipmentinfoMapper.update(base_equipmentinfo, ewasr);
        }

        if(null != flushbonadingip && StringUtils.isNotBlank(flushbonadingip.getEtip())){
            EntityWrapper ewflushbonadingip = new EntityWrapper();
            ewflushbonadingip.eq("ssid", flushbonadingip.getSsid());
            Base_equipmentinfo base_equipmentinfo2 = new Base_equipmentinfo();
            base_equipmentinfo2.setEtip(flushbonadingip.getEtip());
            update1 = base_equipmentinfoMapper.update(base_equipmentinfo2, ewflushbonadingip);
        }

        if(null != polygraphip && StringUtils.isNotBlank(polygraphip.getEtip())){
            EntityWrapper ewpolygraphip = new EntityWrapper();
            ewpolygraphip.eq("ssid", polygraphip.getSsid());
            Base_equipmentinfo base_equipmentinfo3 = new Base_equipmentinfo();
            base_equipmentinfo3.setEtip(polygraphip.getEtip());
            Integer update2 = base_equipmentinfoMapper.update(base_equipmentinfo3, ewpolygraphip);
        }

        if(null != storageip && StringUtils.isNotBlank(storageip.getEtip())){
            EntityWrapper ewstorageip = new EntityWrapper();
            ewstorageip.eq("ssid", storageip.getSsid());
            Base_equipmentinfo base_equipmentinfo4 = new Base_equipmentinfo();
            base_equipmentinfo4.setEtip(storageip.getEtip());
            Integer update3 = base_equipmentinfoMapper.update(base_equipmentinfo4, ewstorageip);
        }

        if(null != ttsetinfoip && StringUtils.isNotBlank(ttsetinfoip.getEtip())){
            EntityWrapper ewttsetinfoip = new EntityWrapper();
            ewttsetinfoip.eq("ssid", ttsetinfoip.getSsid());
            Base_equipmentinfo base_equipmentinfo5 = new Base_equipmentinfo();
            base_equipmentinfo5.setEtip(ttsetinfoip.getEtip());
            Integer update4 = base_equipmentinfoMapper.update(base_equipmentinfo5, ewttsetinfoip);
        }

        if (1 == update1) {

            //修改所有语音识别识别到推给trm的接口
            String trmip = getServerIpParam.getTrmip();
            List<Asr_etinfo> asr_etinfos = asr_etinfoMapper.selectList(null);
            String port=PropertiesListenerConfig.getProperty("server.port");
            for (Asr_etinfo asr_etinfo : asr_etinfos) {
                String backtxtinterface = asr_etinfo.getBacktxtinterface();
                int indexOf = backtxtinterface.indexOf(":" + port + "/");
                if (indexOf != -1) {
                    String endPath = backtxtinterface.substring(indexOf);
                    asr_etinfo.setBacktxtinterface("http://" + trmip + endPath);
                    asr_etinfo.updateById();
                }
            }

            EntityWrapper ew3 = new EntityWrapper();
            ew3.eq("f.equipmentssid", flushbonadingip.getSsid());
            List<FlushbonadingEttd> flushbonadingEttdList = flushbonading_ettdMapper.getFlushbonadingEttdList(ew3);

            if (null != flushbonadingEttdList && flushbonadingEttdList.size() > 0) {

                for (FlushbonadingEttd flushbonadingEttd : flushbonadingEttdList) {

                    //获取通道url地址
                    String pullflowurl = flushbonadingEttd.getPullflowurl();

                    //取出ip字符串
                    Set<String> strContainData = OpenUtil.getStrContainData(pullflowurl, "http://", "/", false);
                    if (strContainData.size() <= 0) {
                        strContainData = OpenUtil.getStrContainData(pullflowurl, "https://", "/", false);
                        if (strContainData.size() <= 0) {
                            break;//如果没有就跳出不修改了
                        }
                    }

                    String etinfoip = (String) strContainData.toArray()[0];

                    /**修改通道里面的地址*/
                    String newEtip = pullflowurl.replace(etinfoip, flushbonadingip.getEtip());

                    Flushbonading_ettd flushbonading_ettd = new Flushbonading_ettd();
                    flushbonading_ettd.setPullflowurl(newEtip);

                    EntityWrapper wrapper = new EntityWrapper();
                    wrapper.eq("ssid", flushbonadingEttd.getSsid());

                    flushbonading_ettdMapper.update(flushbonading_ettd, wrapper);
                }
            }

            Flushbonading_etinfo etinfo = new Flushbonading_etinfo();
            etinfo.setEquipmentssid(flushbonadingip.getSsid());
            Flushbonading_etinfo flu_etinfo = flushbonading_etinfoMapper.selectOne(etinfo);

            if (null != flu_etinfo) {
                EntityWrapper ew = new EntityWrapper();
                ew.eq("ssid", flu_etinfo.getSsid());

                //拼接直播地址，直播预览地址
                String urlModeLlivingurl = PropertiesListenerConfig.getProperty("urlModel.livingurl");//value
                String urlModePreviewurl = PropertiesListenerConfig.getProperty("urlModel.previewurl");

                String livingurl = urlModeLlivingurl.replace("@url", flushbonadingip.getEtip());
                String previewurl = urlModePreviewurl.replace("@url", flushbonadingip.getEtip());

                Flushbonading_etinfo flushbonading_etinfo = new Flushbonading_etinfo();
                flushbonading_etinfo.setLivingurl(livingurl);
                flushbonading_etinfo.setPreviewurl(previewurl);

                Integer update5 = flushbonading_etinfoMapper.update(flushbonading_etinfo, ew);
                System.out.println("update_boot：" + update5);
                if (update == 1) {
                    result.setData(flushbonading_etinfo.getSsid());
                } else {
                    result.setData(update);
                }
            }
        }

        result.changeToTrue();

        return;
    }

    @Override
    public RResult getToOutBaseList(RResult result) {

        List<Base_ettype> base_ettypes = base_ettypeMapper.selectList(null);
        if(null != base_ettypes){
            result.changeToTrue(base_ettypes);
        }

        return result;
    }
}
