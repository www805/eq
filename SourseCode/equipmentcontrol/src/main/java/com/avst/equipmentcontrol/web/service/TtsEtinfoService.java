package com.avst.equipmentcontrol.web.service;

import com.avst.equipmentcontrol.common.datasourse.extrasourse.polygraph.entity.Polygraph_etinfo;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.polygraph.entity.param.PolygraphInfo;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.polygraph.mapper.Polygraph_etinfoMapper;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.tts.entity.Tts_etinfo;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.tts.entity.param.TTS_et_ettype;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.tts.mapper.Tts_etinfoMapper;
import com.avst.equipmentcontrol.common.datasourse.publicsourse.entity.Base_equipmentinfo;
import com.avst.equipmentcontrol.common.datasourse.publicsourse.entity.Base_ettype;
import com.avst.equipmentcontrol.common.datasourse.publicsourse.mapper.Base_equipmentinfoMapper;
import com.avst.equipmentcontrol.common.datasourse.publicsourse.mapper.Base_ettypeMapper;
import com.avst.equipmentcontrol.common.util.OpenUtil;
import com.avst.equipmentcontrol.common.util.baseaction.BaseService;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.common.util.baseaction.ReqParam;
import com.avst.equipmentcontrol.web.req.polygraph.PolygraphParam;
import com.avst.equipmentcontrol.web.req.polygraph.UpdatePolygraphParam;
import com.avst.equipmentcontrol.web.req.tts.TtsetinfoParam;
import com.avst.equipmentcontrol.web.req.tts.UpdateTtsetinfoParam;
import com.avst.equipmentcontrol.web.vo.polygraph.PolygraphVO;
import com.avst.equipmentcontrol.web.vo.tts.TtsetinfoVO;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TtsEtinfoService extends BaseService {

    @Autowired
    private Tts_etinfoMapper tts_etinfoMapper;

    @Autowired
    private Base_equipmentinfoMapper base_equipmentinfoMapper;

    @Autowired
    private Base_ettypeMapper base_ettypeMapper;

    //查询
    public void getTtsetinfoList(RResult result, ReqParam<TtsetinfoParam> param){

        TtsetinfoVO ttsetinfoVO = new TtsetinfoVO();

        //请求参数转换
        TtsetinfoParam paramParam = param.getParam();
        if (null==paramParam){
            result.setMessage("参数为空");
            return;
        }

        EntityWrapper ew=new EntityWrapper();
        if (StringUtils.isNotBlank(paramParam.getLanguage())){
            ew.like("aet.language",paramParam.getLanguage());
        }
        if (StringUtils.isNotBlank(paramParam.getPort())){
            ew.like("aet.port", paramParam.getPort() + "");
        }
        if (StringUtils.isNotBlank(paramParam.getTtskeys())){
            ew.like("aet.ttskeys",paramParam.getTtskeys());
        }
        if (StringUtils.isNotBlank(paramParam.getEtnum())){
            ew.like("et.etnum",paramParam.getEtnum());
        }

        int count = tts_etinfoMapper.getttsinfoCount(ew);
        paramParam.setRecordCount(count);

        ew.orderBy("aet.id",false);
        Page<TTS_et_ettype> page=new Page<TTS_et_ettype>(paramParam.getCurrPage(),paramParam.getPageSize());

        List<TTS_et_ettype> tts_et_ettypes = tts_etinfoMapper.getttsinfoListPage(page, ew);

        ttsetinfoVO.setPagelist(tts_et_ettypes);
        ttsetinfoVO.setPageparam(paramParam);


        result.setData(ttsetinfoVO);
        changeResultToSuccess(result);
    }


    //查询单次
    public void getTtsetinfoById(RResult result, ReqParam<TtsetinfoParam> param){

        //请求参数转换
        TtsetinfoParam paramParam = param.getParam();
        if (null==paramParam){
            result.setMessage("参数为空");
            return;
        }

        if (StringUtils.isBlank(paramParam.getSsid())){
            result.setMessage("查询的参数不能为空");
            return;
        }

        EntityWrapper ew=new EntityWrapper();
        ew.eq("aet.ssid",paramParam.getSsid());

        TTS_et_ettype ttsEtEttype = tts_etinfoMapper.getttsinfo(ew);

        result.setData(ttsEtEttype);
        changeResultToSuccess(result);
    }


    //新增
    @Transactional
    public void addTtsetinfo(RResult result, ReqParam<UpdateTtsetinfoParam> param){

        //请求参数转换
        UpdateTtsetinfoParam paramParam = param.getParam();
        if (null==paramParam){
            result.setMessage("参数为空");
            return;
        }

        if (StringUtils.isBlank(paramParam.getEtypessid())) {
            result.setMessage("类型ssid不能为空");
            return;
        }
        if (StringUtils.isBlank(paramParam.getLanguage())){
            result.setMessage("识别语种不能为空");
            return;
        }
        if (null == paramParam.getMaxnum()){
            result.setMessage("并发数不能为空");
            return;
        }
        if (null == paramParam.getPort()) {
            result.setMessage("开放接口的端口不能为空");
            return;
        }
        if (StringUtils.isBlank(paramParam.getTtstype())){
            result.setMessage("tts服务类型不能为空");
            return;
        }
        if (StringUtils.isBlank(paramParam.getTtskeys())){
            result.setMessage("TTS密匙集不能为空");
            return;
        }
        if (StringUtils.isBlank(paramParam.getEtnum())){
            result.setMessage("设备编号不能为空");
            return;
        }
        if (StringUtils.isBlank(paramParam.getEtip())){
            result.setMessage("设备IP不能为空");
            return;
        }

        if(!OpenUtil.isIp(paramParam.getEtip())){
            result.setMessage("设备IP不是一个正确的IP");
            return;
        }

        if (!StringUtils.isNumeric(paramParam.getPort() + "")) {
            result.setMessage("端口号只能由数字组成");
            return;
        }

        EntityWrapper<Polygraph_etinfo> wrapper = new EntityWrapper<>();
        wrapper.eq("t.language", paramParam.getLanguage());
        wrapper.eq("t.maxnum", paramParam.getMaxnum());
        wrapper.eq("t.port", paramParam.getPort());
        wrapper.eq("t.ttstype", paramParam.getTtstype());
        wrapper.eq("t.ttskeys", paramParam.getTtskeys());
        wrapper.eq("b.etnum", paramParam.getEtnum());
        wrapper.eq("b.etip", paramParam.getEtip());

        int repetitionCount = tts_etinfoMapper.getRepetition(wrapper);
        if (repetitionCount > 0) {
            result.setMessage("该文件转语音识别服务已经存在");
            return;
        }

        Base_equipmentinfo base_equipmentinfo = new Base_equipmentinfo();
        base_equipmentinfo.setEtnum(paramParam.getEtnum());
        base_equipmentinfo.setEtip(paramParam.getEtip());
        base_equipmentinfo.setEtypessid(paramParam.getEtypessid());
        base_equipmentinfo.setSsid(OpenUtil.getUUID_32());

        base_equipmentinfoMapper.insert(base_equipmentinfo);

        Tts_etinfo tts_etinfo = new Tts_etinfo();
        tts_etinfo.setLanguage(paramParam.getLanguage());
        tts_etinfo.setMaxnum(paramParam.getMaxnum());
        tts_etinfo.setPort(paramParam.getPort());
        tts_etinfo.setTtskeys(paramParam.getTtskeys());
        tts_etinfo.setTtstype(paramParam.getTtstype());
        tts_etinfo.setExplain(paramParam.getExplain());
        tts_etinfo.setEquipmentssid(base_equipmentinfo.getSsid());
        tts_etinfo.setSsid(OpenUtil.getUUID_32());

        if (StringUtils.isBlank(paramParam.getExplain())) {
            tts_etinfo.setExplain(paramParam.getEtip());
        }

        Integer insert = tts_etinfoMapper.insert(tts_etinfo);
        System.out.println("add_boot：" + insert);
        if(insert == 1){
            result.setData(tts_etinfo.getSsid());
        }else{
            result.setData(insert);
        }
        changeResultToSuccess(result);
    }

    //修改
    @Transactional
    public void updateTtsetinfo(RResult result, ReqParam<UpdateTtsetinfoParam> param){

        //请求参数转换
        UpdateTtsetinfoParam paramParam = param.getParam();
        if (null==paramParam){
            result.setMessage("参数为空");
            return;
        }

        if (StringUtils.isBlank(paramParam.getSsid())){
            result.setMessage("修改的ssid不能为空");
            return;
        }
        if (StringUtils.isBlank(paramParam.getLanguage())){
            result.setMessage("识别语种不能为空");
            return;
        }
        if (null == paramParam.getMaxnum()){
            result.setMessage("并发数不能为空");
            return;
        }
        if (null == paramParam.getPort()) {
            result.setMessage("开放接口的端口不能为空");
            return;
        }
        if (StringUtils.isBlank(paramParam.getEtypessid())) {
            result.setMessage("类型ssid不能为空");
            return;
        }
        if (StringUtils.isBlank(paramParam.getTtstype())){
            result.setMessage("tts服务类型不能为空");
            return;
        }
        if (StringUtils.isBlank(paramParam.getTtskeys())){
            result.setMessage("TTS密匙集不能为空");
            return;
        }
        if (StringUtils.isBlank(paramParam.getEtnum())){
            result.setMessage("设备编号不能为空");
            return;
        }
        if (StringUtils.isBlank(paramParam.getEtip())){
            result.setMessage("设备IP不能为空");
            return;
        }

        if(!OpenUtil.isIp(paramParam.getEtip())){
            result.setMessage("设备IP不是一个正确的IP");
            return;
        }

        if (!StringUtils.isNumeric(paramParam.getPort() + "")) {
            result.setMessage("端口号只能由数字组成");
            return;
        }

        EntityWrapper<Polygraph_etinfo> wrapper = new EntityWrapper<>();
        wrapper.eq("t.language", paramParam.getLanguage());
        wrapper.eq("t.maxnum", paramParam.getMaxnum());
        wrapper.eq("t.port", paramParam.getPort());
        wrapper.eq("t.ttstype", paramParam.getTtstype());
        wrapper.eq("t.ttskeys", paramParam.getTtskeys());
        wrapper.eq("b.etnum", paramParam.getEtnum());
        wrapper.eq("b.etip", paramParam.getEtip());
        wrapper.ne("t.ssid", paramParam.getSsid());

        int repetitionCount = tts_etinfoMapper.getRepetition(wrapper);
        if (repetitionCount > 0) {
            result.setMessage("该文件转语音识别服务已经存在");
            return;
        }

        //查询审讯主机从里面拿到他的设备ssid
        Tts_etinfo tts_etinfo = new Tts_etinfo();
        tts_etinfo.setSsid(paramParam.getSsid());
        Tts_etinfo ttsEtinfo = tts_etinfoMapper.selectOne(tts_etinfo);


        //删除设备再新增，不然就是修改那个设备
        EntityWrapper ew2 = new EntityWrapper();
        ew2.eq("ssid", ttsEtinfo.getEquipmentssid());

        Base_equipmentinfo equipmentinfo = new Base_equipmentinfo();
        equipmentinfo.setEtnum(paramParam.getEtnum());
        equipmentinfo.setEtip(paramParam.getEtip());
        equipmentinfo.setEtypessid(paramParam.getEtypessid());

        base_equipmentinfoMapper.update(equipmentinfo, ew2);

        EntityWrapper ew = new EntityWrapper();
        ew.eq("ssid", paramParam.getSsid());

        Tts_etinfo etinfo = new Tts_etinfo();
        etinfo.setLanguage(paramParam.getLanguage());
        etinfo.setMaxnum(paramParam.getMaxnum());
        etinfo.setPort(paramParam.getPort());
        etinfo.setTtskeys(paramParam.getTtskeys());
        etinfo.setTtstype(paramParam.getTtstype());
        etinfo.setExplain(paramParam.getExplain());
        etinfo.setEquipmentssid(equipmentinfo.getSsid());

        if (StringUtils.isBlank(paramParam.getExplain())) {
            etinfo.setExplain(paramParam.getEtip());
        }

        Integer update = tts_etinfoMapper.update(etinfo, ew);
        System.out.println("update_boot：" + update);
        if(update == 1){
            result.setData(paramParam.getSsid());
        }else{
            result.setData(update);
        }
        changeResultToSuccess(result);
    }

    //删除
    @Transactional
    public void delTtsetinfo(RResult result, ReqParam<TtsetinfoParam> param){

        //请求参数转换
        TtsetinfoParam paramParam = param.getParam();
        if (null==paramParam){
            result.setMessage("参数为空");
            return;
        }

        if (StringUtils.isBlank(paramParam.getSsid())){
            result.setMessage("删除的ssid不能为空");
            return;
        }

        //查询审讯主机从里面拿到他的设备ssid
        Tts_etinfo tts_etinfo = new Tts_etinfo();
        tts_etinfo.setSsid(paramParam.getSsid());
        Tts_etinfo ttsEtinfo = tts_etinfoMapper.selectOne(tts_etinfo);

        if (null == ttsEtinfo){
            result.setMessage("没找到相应的设备ssid");
            return;
        }

        EntityWrapper ew2 = new EntityWrapper();
        ew2.eq("ssid", ttsEtinfo.getEquipmentssid());
        base_equipmentinfoMapper.delete(ew2);

        EntityWrapper ew = new EntityWrapper();
        ew.eq("ssid", paramParam.getSsid());
        Integer delete = tts_etinfoMapper.delete(ew);

        result.setData(delete);
        changeResultToSuccess(result);

    }
}
