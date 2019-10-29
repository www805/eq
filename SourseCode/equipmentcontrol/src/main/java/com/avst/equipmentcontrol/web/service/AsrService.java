package com.avst.equipmentcontrol.web.service;

import com.avst.equipmentcontrol.common.datasourse.extrasourse.asr.entity.Asr_et_ettype;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.asr.entity.Asr_etinfo;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.asr.mapper.Asr_etinfoMapper;
import com.avst.equipmentcontrol.common.datasourse.publicsourse.entity.Base_equipmentinfo;
import com.avst.equipmentcontrol.common.datasourse.publicsourse.entity.Base_ettype;
import com.avst.equipmentcontrol.common.datasourse.publicsourse.mapper.Base_equipmentinfoMapper;
import com.avst.equipmentcontrol.common.datasourse.publicsourse.mapper.Base_ettypeMapper;
import com.avst.equipmentcontrol.common.util.OpenUtil;
import com.avst.equipmentcontrol.common.util.baseaction.BaseService;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.common.util.baseaction.ReqParam;
import com.avst.equipmentcontrol.web.req.asr.AsrParam;
import com.avst.equipmentcontrol.web.req.asr.UpdateAsrParam;
import com.avst.equipmentcontrol.web.vo.asr.AsrVO;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AsrService extends BaseService {

    @Autowired
    private Asr_etinfoMapper asr_etinfoMapper;

    @Autowired
    private Base_ettypeMapper base_ettypeMapper;

    @Autowired
    private Base_equipmentinfoMapper base_equipmentinfoMapper;

    //查询
    public void getAsrList(RResult result, ReqParam<AsrParam> param){

        AsrVO asrVO = new AsrVO();

        //请求参数转换
        AsrParam paramParam = param.getParam();
        if (null==paramParam){
            result.setMessage("参数为空");
            return;
        }

        EntityWrapper ew=new EntityWrapper();
        if (StringUtils.isNotBlank(paramParam.getLanguage())){
            ew.like("aet.language",paramParam.getLanguage());
        }
        if (StringUtils.isNotBlank(paramParam.getPort())){
            ew.like("aet.port",paramParam.getPort());
        }
        if (StringUtils.isNotBlank(paramParam.getAsrkey())){
            ew.like("aet.asrkey",paramParam.getAsrkey());
        }
        if (StringUtils.isNotBlank(paramParam.getEtnum())){
            ew.like("et.etnum",paramParam.getEtnum());
        }
        if (StringUtils.isNotBlank(paramParam.getEtypessid())){
            ew.eq("et.etypessid",paramParam.getEtypessid());
        }

        int count = asr_etinfoMapper.getAsrInfoCount(ew);
        paramParam.setRecordCount(count);

        ew.orderBy("aet.id",false);
        Page<Asr_et_ettype> page=new Page<Asr_et_ettype>(paramParam.getCurrPage(),paramParam.getPageSize());

        List<Asr_et_ettype> asrEtEttypeList = asr_etinfoMapper.getAsrInfoPage(page,ew);

        asrVO.setPagelist(asrEtEttypeList);
        asrVO.setPageparam(paramParam);

        result.setData(asrVO);
        changeResultToSuccess(result);
    }


    //查询单次
    public void getAsrById(RResult result, ReqParam<AsrParam> param){

        //请求参数转换
        AsrParam paramParam = param.getParam();
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

        Asr_et_ettype asrinfo = asr_etinfoMapper.getAsrinfo(ew);

        result.setData(asrinfo);
        changeResultToSuccess(result);
    }


    //新增
    @Transactional
    public void addAsr(RResult result, ReqParam<UpdateAsrParam> param){

        //请求参数转换
        UpdateAsrParam paramParam = param.getParam();
        if (null==paramParam){
            result.setMessage("参数为空");
            return;
        }

        if (StringUtils.isBlank(paramParam.getLanguage())){
            result.setMessage("识别语种不能为空");
            return;
        }
        if (null == paramParam.getMaxnum()) {
            result.setMessage("并发数不能为空");
            return;
        }
        if (null == paramParam.getPort()) {
            result.setMessage("开放接口的端口不能为空");
            return;
        }
        if (StringUtils.isBlank(paramParam.getAsrtype())){
            result.setMessage("服务类型不能为空");
            return;
        }
        if (StringUtils.isBlank(paramParam.getAsrkey())){
            result.setMessage("验证密匙不能为空");
            return;
        }
        if (StringUtils.isBlank(paramParam.getEtypessid())){
            result.setMessage("设备类型不能为空");
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

        boolean isip = OpenUtil.isIp(paramParam.getEtip());
        if(isip == false){
            result.setMessage("设备IP不是一个正确的IP");
            return;
        }

        if (!StringUtils.isNumeric(paramParam.getPort() + "")) {
            result.setMessage("端口号只能由数字组成");
            return;
        }

        EntityWrapper<Asr_et_ettype> wrapper = new EntityWrapper<>();
        wrapper.eq("a.language", paramParam.getLanguage());
        wrapper.eq("a.maxnum", paramParam.getMaxnum());
        wrapper.eq("a.port", paramParam.getPort());
        wrapper.eq("a.asrtype", paramParam.getAsrtype());
        wrapper.eq("a.asrkey", paramParam.getAsrkey());
        wrapper.eq("b.etnum", paramParam.getEtnum());
        wrapper.eq("b.etip", paramParam.getEtip());

        int repetitionCount = asr_etinfoMapper.getRepetition(wrapper);
        if (repetitionCount > 0) {
            result.setMessage("该语音服务器已经存在");
            return;
        }

        Base_equipmentinfo base_equipmentinfo = new Base_equipmentinfo();
        base_equipmentinfo.setEtnum(paramParam.getEtnum());
        base_equipmentinfo.setEtip(paramParam.getEtip());
        base_equipmentinfo.setEtypessid(paramParam.getEtypessid());
        base_equipmentinfo.setSsid(OpenUtil.getUUID_32());

        base_equipmentinfoMapper.insert(base_equipmentinfo);

        Asr_et_ettype asr_et_ettype = new Asr_et_ettype();
        asr_et_ettype.setLanguage(paramParam.getLanguage());
        asr_et_ettype.setMaxnum(paramParam.getMaxnum());
        asr_et_ettype.setPort(paramParam.getPort());
        asr_et_ettype.setAsrtype(paramParam.getAsrtype());
        asr_et_ettype.setAsrkey(paramParam.getAsrkey());
        asr_et_ettype.setExplain(paramParam.getExplain());
        asr_et_ettype.setEquipmentssid(base_equipmentinfo.getSsid());
        asr_et_ettype.setSsid(OpenUtil.getUUID_32());

        if (StringUtils.isBlank(paramParam.getExplain())) {
            asr_et_ettype.setExplain(paramParam.getEtip());
        }

        Integer insert = asr_etinfoMapper.insert(asr_et_ettype);
        System.out.println("add_boot：" + insert);
        if(insert == 1){
            result.setData(asr_et_ettype.getSsid());
        }else{
            result.setData(insert);
        }

        changeResultToSuccess(result);
    }

    //修改
    @Transactional
    public void updateAsr(RResult result, ReqParam<UpdateAsrParam> param){

        //请求参数转换
        UpdateAsrParam paramParam = param.getParam();
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
        if (null == paramParam.getMaxnum()) {
            result.setMessage("并发数不能为空");
            return;
        }
        if (null == paramParam.getPort()) {
            result.setMessage("开放接口的端口不能为空");
            return;
        }
        if (StringUtils.isBlank(paramParam.getAsrtype())){
            result.setMessage("服务类型不能为空");
            return;
        }
        if (StringUtils.isBlank(paramParam.getAsrkey())){
            result.setMessage("验证密匙不能为空");
            return;
        }
        if (StringUtils.isBlank(paramParam.getEtypessid())){
            result.setMessage("设备类型不能为空");
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

        boolean isip = OpenUtil.isIp(paramParam.getEtip());
        if(isip == false){
            result.setMessage("设备IP不是一个正确的IP");
            return;
        }

        if (!StringUtils.isNumeric(paramParam.getPort() + "")) {
            result.setMessage("端口号只能由数字组成");
            return;
        }

        EntityWrapper<Asr_et_ettype> wrapper = new EntityWrapper<>();
        wrapper.eq("a.language", paramParam.getLanguage());
        wrapper.eq("a.maxnum", paramParam.getMaxnum());
        wrapper.eq("a.port", paramParam.getPort());
        wrapper.eq("a.asrtype", paramParam.getAsrtype());
        wrapper.eq("a.asrkey", paramParam.getAsrkey());
        wrapper.eq("b.etnum", paramParam.getEtnum());
        wrapper.eq("b.etip", paramParam.getEtip());
        wrapper.ne("a.ssid", paramParam.getSsid());

        int repetitionCount = asr_etinfoMapper.getRepetition(wrapper);
        if (repetitionCount > 0) {
            result.setMessage("该语音服务器已经存在");
            return;
        }

        //查询审讯主机从里面拿到他的设备ssid
        Asr_et_ettype asr_et_ettype = new Asr_et_ettype();
        asr_et_ettype.setSsid(paramParam.getSsid());
        Asr_etinfo asr_etinfo = asr_etinfoMapper.selectOne(asr_et_ettype);


        //删除设备再新增，不然就是修改那个设备
        EntityWrapper ew2 = new EntityWrapper();
        ew2.eq("ssid", asr_etinfo.getEquipmentssid());

        Base_equipmentinfo equipmentinfo = new Base_equipmentinfo();
        equipmentinfo.setEtnum(paramParam.getEtnum());
        equipmentinfo.setEtip(paramParam.getEtip());
        equipmentinfo.setEtypessid(paramParam.getEtypessid());

        base_equipmentinfoMapper.update(equipmentinfo, ew2);

        EntityWrapper ew = new EntityWrapper();
        ew.eq("ssid", paramParam.getSsid());

        Asr_et_ettype asrEtEttype = new Asr_et_ettype();
        asrEtEttype.setLanguage(paramParam.getLanguage());
        asrEtEttype.setMaxnum(paramParam.getMaxnum());
        asrEtEttype.setPort(paramParam.getPort());
        asrEtEttype.setAsrtype(paramParam.getAsrtype());
        asrEtEttype.setAsrkey(paramParam.getAsrkey());
        asrEtEttype.setExplain(paramParam.getExplain());
        asrEtEttype.setEquipmentssid(equipmentinfo.getSsid());

        if (StringUtils.isBlank(paramParam.getExplain())) {
            asrEtEttype.setExplain(paramParam.getEtip());
        }

        Integer update = asr_etinfoMapper.update(asrEtEttype, ew);
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
    public void delAsr(RResult result, ReqParam<AsrParam> param){

        //请求参数转换
        AsrParam paramParam = param.getParam();
        if (null==paramParam){
            result.setMessage("参数为空");
            return;
        }

        if (StringUtils.isBlank(paramParam.getSsid())){
            result.setMessage("删除的ssid不能为空");
            return;
        }

        //查询审讯主机从里面拿到他的设备ssid
        Asr_et_ettype asr_et_ettype = new Asr_et_ettype();
        asr_et_ettype.setSsid(paramParam.getSsid());
        Asr_etinfo asr_etinfo = asr_etinfoMapper.selectOne(asr_et_ettype);

        if (null == asr_etinfo){
            result.setMessage("没找到相应的设备ssid");
            return;
        }

        EntityWrapper ew2 = new EntityWrapper();
        ew2.eq("ssid", asr_etinfo.getEquipmentssid());
        base_equipmentinfoMapper.delete(ew2);

        EntityWrapper ew = new EntityWrapper();
        ew.eq("ssid", paramParam.getSsid());
        Integer delete = asr_etinfoMapper.delete(ew);

        result.setData(delete);
        changeResultToSuccess(result);
    }

}
