package com.avst.equipmentcontrol.web.service;

import com.avst.equipmentcontrol.web.req.flushbonading.UpdateBurnboolFoDiskrecboolParam;
import com.avst.equipmentcontrol.web.req.flushbonadingEttd.FlushbonadingEttd;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.flushbonading.entity.Flushbonading_etinfo;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.flushbonading.entity.Flushbonading_ettd;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.flushbonading.entity.param.Flushbonadinginfo;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.flushbonading.mapper.Flushbonading_etinfoMapper;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.flushbonading.mapper.Flushbonading_ettdMapper;
import com.avst.equipmentcontrol.common.datasourse.publicsourse.entity.Base_equipmentinfo;
import com.avst.equipmentcontrol.common.datasourse.publicsourse.entity.Base_ettype;
import com.avst.equipmentcontrol.common.datasourse.publicsourse.mapper.Base_equipmentinfoMapper;
import com.avst.equipmentcontrol.common.datasourse.publicsourse.mapper.Base_ettypeMapper;
import com.avst.equipmentcontrol.common.util.OpenUtil;
import com.avst.equipmentcontrol.common.util.baseaction.BaseService;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.common.util.baseaction.ReqParam;
import com.avst.equipmentcontrol.web.req.flushbonading.FlushbonadinginfoParam;
import com.avst.equipmentcontrol.web.vo.flushbonading.BaseEquipmentinfoOrEttypeVO;
import com.avst.equipmentcontrol.web.vo.flushbonading.FlushbonadinginfoVO;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class FlushbonadingService extends BaseService {

    @Autowired
    private Flushbonading_etinfoMapper flushbonading_etinfoMapper;

    @Autowired
    private Base_equipmentinfoMapper base_equipmentinfoMapper;

    @Autowired
    private Base_ettypeMapper base_ettypeMapper;

    @Autowired
    private Flushbonading_ettdMapper flushbonading_ettdMapper;

    //查询
    public void getFlushbonadingList(RResult result, ReqParam<FlushbonadinginfoParam> param){
        FlushbonadinginfoVO flushbonadinginfoVO=new FlushbonadinginfoVO();

        //请求参数转换
        FlushbonadinginfoParam flushbonadinginfoParam = param.getParam();
        if (null==flushbonadinginfoParam){
            result.setMessage("参数为空");
            return;
        }

        EntityWrapper ew=new EntityWrapper();
        if (StringUtils.isNotBlank(flushbonadinginfoParam.getLivingurl())){
            ew.like("fet.livingurl",flushbonadinginfoParam.getLivingurl());
        }
        if (StringUtils.isNotBlank(flushbonadinginfoParam.getUser())){
            ew.like("fet.user",flushbonadinginfoParam.getUser());
        }
        if (StringUtils.isNotBlank(flushbonadinginfoParam.getEtnum())){
            ew.like("et.etnum",flushbonadinginfoParam.getEtnum());
        }
        if (StringUtils.isNotBlank(flushbonadinginfoParam.getEtypessid())){
            ew.eq("et.etypessid",flushbonadinginfoParam.getEtypessid());
        }

        int count = flushbonading_etinfoMapper.getFlushbonadingCount(ew);
        flushbonadinginfoParam.setRecordCount(count);

        ew.orderBy("fet.id",false);
        Page<Flushbonadinginfo> page=new Page<Flushbonadinginfo>(flushbonadinginfoParam.getCurrPage(),flushbonadinginfoParam.getPageSize());

        List<Flushbonadinginfo> flushbonadingList = flushbonading_etinfoMapper.getFlushbonadingList(page,ew);

        flushbonadinginfoVO.setPagelist(flushbonadingList);
        flushbonadinginfoVO.setPageparam(flushbonadinginfoParam);

        result.setData(flushbonadinginfoVO);
        changeResultToSuccess(result);
        return;
    }

    //查询单个
    public void getFlushbonadingById(RResult result, ReqParam<FlushbonadinginfoParam> param){

        //请求参数转换
        FlushbonadinginfoParam flushbonadinginfoParam = param.getParam();
        if (null==flushbonadinginfoParam){
            result.setMessage("参数为空");
            return;
        }

        if (StringUtils.isBlank(flushbonadinginfoParam.getSsid())){
            result.setMessage("查询的参数不能为空");
            return;
        }

        EntityWrapper ew=new EntityWrapper();
        ew.eq("fet.ssid",flushbonadinginfoParam.getSsid());

        Flushbonadinginfo flushbonadinginfo = flushbonading_etinfoMapper.getFlushbonadinginfo(ew);

        result.setData(flushbonadinginfo);
        changeResultToSuccess(result);
        return;
    }

    //新增
    public void addFlushbonading(RResult result, ReqParam<Flushbonadinginfo> param){

        //请求参数转换
        Flushbonadinginfo paramParam = param.getParam();
        if (null==paramParam){
            result.setMessage("参数为空");
            return;
        }

        if (StringUtils.isBlank(paramParam.getLivingurl())){
            result.setMessage("直播地址不能为空");
            return;
        }
        if (StringUtils.isBlank(paramParam.getPreviewurl())){
            result.setMessage("直播预览地址不能为空");
            return;
        }
        if (null == paramParam.getPort()) {
            result.setMessage("开放接口的端口不能为空");
            return;
        }
        if (StringUtils.isBlank(paramParam.getUser())){
            result.setMessage("登录用户名不能为空");
            return;
        }
        if (StringUtils.isBlank(paramParam.getPasswd())){
            result.setMessage("登录密码不能为空");
            return;
        }
        if (StringUtils.isBlank(paramParam.getUploadbasepath())){
            result.setMessage("ftp上传存储路径不能为空");
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
        if (null == paramParam.getBurntime()) {
            result.setMessage("刻录选时时长不能为空");
            return;
        }
        if (null == paramParam.getBurnbool()) {
            result.setMessage("是否需要光盘同刻不能为空");
            return;
        }
        if (StringUtils.isBlank(paramParam.getPtjson())){
            result.setMessage("片头列表不能为空");
            return;
        }
        if (null == paramParam.getPtshowtime()) {
            result.setMessage("片头显示时间不能为空");
            return;
        }
        if (null == paramParam.getDiskrecbool()) {
            result.setMessage("是否需要硬盘录像不能为空");
            return;
        }

        Base_equipmentinfo base_equipmentinfo = new Base_equipmentinfo();
        base_equipmentinfo.setEtnum(paramParam.getEtnum());
        base_equipmentinfo.setEtip(paramParam.getEtip());
        base_equipmentinfo.setEtypessid(paramParam.getEtypessid());
        base_equipmentinfo.setSsid(OpenUtil.getUUID_32());

        base_equipmentinfoMapper.insert(base_equipmentinfo);

        Flushbonading_etinfo flushbonading_etinfo = new Flushbonading_etinfo();
        flushbonading_etinfo.setLivingurl(paramParam.getLivingurl());
        flushbonading_etinfo.setPreviewurl(paramParam.getPreviewurl());
        flushbonading_etinfo.setPort(paramParam.getPort());
        flushbonading_etinfo.setUser(paramParam.getUser());
        flushbonading_etinfo.setPasswd(paramParam.getPasswd());
        flushbonading_etinfo.setUploadbasepath(paramParam.getUploadbasepath());
        flushbonading_etinfo.setBurntime(paramParam.getBurntime());
        flushbonading_etinfo.setBurnbool(paramParam.getBurnbool());
        flushbonading_etinfo.setPtjson(paramParam.getPtjson());
        flushbonading_etinfo.setPtshowtime(paramParam.getPtshowtime());
        flushbonading_etinfo.setDiskrecbool(paramParam.getDiskrecbool());
        flushbonading_etinfo.setExplain(paramParam.getExplain());
        flushbonading_etinfo.setEquipmentssid(base_equipmentinfo.getSsid());
        flushbonading_etinfo.setSsid(OpenUtil.getUUID_32());

        Integer insert = flushbonading_etinfoMapper.insert(flushbonading_etinfo);
        System.out.println("add_boot：" + insert);
        if(insert == 1){
            result.setData(flushbonading_etinfo.getSsid());
        }else{
            result.setData(insert);
        }

        changeResultToSuccess(result);
    }

    /**
     * 修改
     */
    public void updateFlushbonading(RResult result, ReqParam<Flushbonadinginfo> param){

        /**请求参数转换*/
        Flushbonadinginfo paramParam = param.getParam();
        if (null==paramParam){
            result.setMessage("参数为空");
            return;
        }

        if (StringUtils.isBlank(paramParam.getSsid())){
            result.setMessage("修改的ssid不能为空");
            return;
        }
        if (StringUtils.isBlank(paramParam.getLivingurl())){
            result.setMessage("直播地址不能为空");
            return;
        }
        if (StringUtils.isBlank(paramParam.getPreviewurl())){
            result.setMessage("直播预览地址不能为空");
            return;
        }
        if (null == paramParam.getPort()) {
            result.setMessage("开放接口的端口不能为空");
            return;
        }
        if (StringUtils.isBlank(paramParam.getUser())){
            result.setMessage("登录用户名不能为空");
            return;
        }
        if (StringUtils.isBlank(paramParam.getPasswd())){
            result.setMessage("登录密码不能为空");
            return;
        }
        if (StringUtils.isBlank(paramParam.getUploadbasepath())){
            result.setMessage("ftp上传存储路径不能为空");
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
        if (null == paramParam.getBurntime()) {
            result.setMessage("刻录选时时长不能为空");
            return;
        }
        if (null == paramParam.getBurnbool()) {
            result.setMessage("是否需要光盘同刻不能为空");
            return;
        }
        if (StringUtils.isBlank(paramParam.getPtjson())){
            result.setMessage("片头列表不能为空");
            return;
        }
        if (null == paramParam.getPtshowtime()) {
            result.setMessage("片头显示时间不能为空");
            return;
        }
        if (null == paramParam.getDiskrecbool()) {
            result.setMessage("是否需要硬盘录像不能为空");
            return;
        }

        /**查询审讯主机从里面拿到他的设备ssid*/
        EntityWrapper ew0 = new EntityWrapper();
        ew0.eq("fet.ssid",paramParam.getSsid());
        Flushbonadinginfo flushbonadingEtinfo = flushbonading_etinfoMapper.getFlushbonadinginfo(ew0);

        /**删除设备再新增，不然就是修改那个设备*/
        EntityWrapper ew2 = new EntityWrapper();
        ew2.eq("ssid", flushbonadingEtinfo.getEquipmentssid());

        Base_equipmentinfo equipmentinfo = new Base_equipmentinfo();
        equipmentinfo.setEtnum(paramParam.getEtnum());
        equipmentinfo.setEtip(paramParam.getEtip());
        equipmentinfo.setEtypessid(paramParam.getEtypessid());

        base_equipmentinfoMapper.update(equipmentinfo, ew2);

        

        /**修改通道里面的直播地址*/
        /**如果真是要修改就进入里面*/
        if (!paramParam.getEtip().equalsIgnoreCase(flushbonadingEtinfo.getEtip())) {

            EntityWrapper ew3 = new EntityWrapper();
            ew3.eq("e.flushbonadingssid",flushbonadingEtinfo.getSsid());
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
                    String newEtip = pullflowurl.replace(etinfoip, paramParam.getEtip());

                    Flushbonading_ettd flushbonading_ettd = new Flushbonading_ettd();
                    flushbonading_ettd.setPullflowurl(newEtip);

                    EntityWrapper wrapper = new EntityWrapper();
                    wrapper.eq("ssid", flushbonadingEttd.getSsid());

                    flushbonading_ettdMapper.update(flushbonading_ettd, wrapper);
                }
            }
        }

        EntityWrapper ew = new EntityWrapper();
        ew.eq("ssid", paramParam.getSsid());

        Flushbonading_etinfo flushbonading_etinfo = new Flushbonading_etinfo();
        flushbonading_etinfo.setLivingurl(paramParam.getLivingurl());
        flushbonading_etinfo.setPreviewurl(paramParam.getPreviewurl());
        flushbonading_etinfo.setPort(paramParam.getPort());
        flushbonading_etinfo.setUser(paramParam.getUser());
        flushbonading_etinfo.setPasswd(paramParam.getPasswd());
        flushbonading_etinfo.setUploadbasepath(paramParam.getUploadbasepath());
        flushbonading_etinfo.setBurntime(paramParam.getBurntime());
        flushbonading_etinfo.setBurnbool(paramParam.getBurnbool());
        flushbonading_etinfo.setPtjson(paramParam.getPtjson());
        flushbonading_etinfo.setPtshowtime(paramParam.getPtshowtime());
        flushbonading_etinfo.setDiskrecbool(paramParam.getDiskrecbool());
        flushbonading_etinfo.setExplain(paramParam.getExplain());
        flushbonading_etinfo.setSsid(paramParam.getSsid());

        Integer update = flushbonading_etinfoMapper.update(flushbonading_etinfo, ew);
        System.out.println("update_boot：" + update);
        if(update == 1){
            result.setData(flushbonading_etinfo.getSsid());
        }else{
            result.setData(update);
        }
        changeResultToSuccess(result);
    }

    /**
     * 删除
     */
    public void delFlushbonading(RResult result, ReqParam<FlushbonadinginfoParam> param){

        //请求参数转换
        FlushbonadinginfoParam paramParam = param.getParam();
        if (null==paramParam){
            result.setMessage("参数为空");
            return;
        }

        if (StringUtils.isBlank(paramParam.getSsid())){
            result.setMessage("删除的ssid不能为空");
            return;
        }

        //查询审讯主机从里面拿到他的设备ssid
        Flushbonadinginfo flushbonadinginfo = new Flushbonadinginfo();
        flushbonadinginfo.setSsid(paramParam.getSsid());
        Flushbonading_etinfo flushbonadingEtinfo = flushbonading_etinfoMapper.selectOne(flushbonadinginfo);

        if (null == flushbonadingEtinfo){
            result.setMessage("没找到相应的设备ssid");
            return;
        }

        EntityWrapper ew2 = new EntityWrapper();
        ew2.eq("ssid", flushbonadingEtinfo.getEquipmentssid());
        base_equipmentinfoMapper.delete(ew2);

        EntityWrapper ew3 = new EntityWrapper();
        ew3.eq("flushbonadingssid", flushbonadingEtinfo.getSsid());
        flushbonading_ettdMapper.delete(ew3);

        EntityWrapper ew = new EntityWrapper();
        ew.eq("ssid", paramParam.getSsid());
        Integer delete = flushbonading_etinfoMapper.delete(ew);

        result.setData(delete);
        changeResultToSuccess(result);
    }

    /**
     * 查询设备类型
     * @param result
     */
    public void getBaseEttype(RResult result){

        BaseEquipmentinfoOrEttypeVO baseEquipmentinfoOrEttypeVO = new BaseEquipmentinfoOrEttypeVO();

//        EntityWrapper ew = new EntityWrapper();
//        List<EquipmentBasicsVO> equipmentBasicsAll = base_equipmentinfoMapper.getEquipmentBasicsAll(ew);
//
//        baseEquipmentinfoOrEttypeVO.setEquipmentBasicsAll(equipmentBasicsAll);

        EntityWrapper wrapper = new EntityWrapper();
        List<Base_ettype> ettypeList = base_ettypeMapper.selectList(wrapper);
        baseEquipmentinfoOrEttypeVO.setEttypeList(ettypeList);

        result.setData(baseEquipmentinfoOrEttypeVO);
        changeResultToSuccess(result);
    }

    /**
     * 修改硬盘录像状态
     * @param result
     * @param param
     */
    public void updateDiskrecbool(RResult result, ReqParam<UpdateBurnboolFoDiskrecboolParam> param) {

        UpdateBurnboolFoDiskrecboolParam paramParam = param.getParam();

        if (StringUtils.isBlank(paramParam.getSsid())){
            result.setMessage("修改的ssid不能为空");
            return;
        }
        if (null == paramParam.getState()) {
            result.setMessage("硬盘录像状态不能为空");
            return;
        }

        EntityWrapper ew = new EntityWrapper();
        ew.eq("ssid", paramParam.getSsid());

        Flushbonading_etinfo flushbonading_etinfo = new Flushbonading_etinfo();
        flushbonading_etinfo.setDiskrecbool(paramParam.getState());

        Integer update = flushbonading_etinfoMapper.update(flushbonading_etinfo, ew);
        System.out.println("update_boot：" + update);
        if(update == 1){
            result.setData(flushbonading_etinfo.getSsid());
        }else{
            result.setData(update);
        }
        changeResultToSuccess(result);
    }

    /**
     * 修改光盘同刻状态
     * @param result
     * @param param
     */
    public void updateBurnbool(RResult result, ReqParam<UpdateBurnboolFoDiskrecboolParam> param) {

        UpdateBurnboolFoDiskrecboolParam paramParam = param.getParam();

        if (StringUtils.isBlank(paramParam.getSsid())){
            result.setMessage("修改的ssid不能为空");
            return;
        }
        if (null == paramParam.getState()) {
            result.setMessage("光盘同刻状态不能为空");
            return;
        }

        EntityWrapper ew = new EntityWrapper();
        ew.eq("ssid", paramParam.getSsid());

        Flushbonading_etinfo flushbonading_etinfo = new Flushbonading_etinfo();
        flushbonading_etinfo.setBurnbool(paramParam.getState());

        Integer update = flushbonading_etinfoMapper.update(flushbonading_etinfo, ew);
        System.out.println("update_boot：" + update);
        if(update == 1){
            result.setData(flushbonading_etinfo.getSsid());
        }else{
            result.setData(update);
        }
        changeResultToSuccess(result);

    }
}
