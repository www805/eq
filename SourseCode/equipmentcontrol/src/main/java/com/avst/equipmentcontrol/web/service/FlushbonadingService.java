package com.avst.equipmentcontrol.web.service;

import com.avst.equipmentcontrol.common.cache.BaseEcCache;
import com.avst.equipmentcontrol.common.conf.TaskExecutorConfig;
import com.avst.equipmentcontrol.common.util.LogUtil;
import com.avst.equipmentcontrol.common.util.properties.PropertiesListenerConfig;
import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.FDInterface;
import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.req.GetMiddleware_FTPParam;
import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.req.SetMiddleware_FTPParam;
import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.vo.GetMiddleware_FTPVO;
import com.avst.equipmentcontrol.web.req.flushbonading.*;
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
import com.avst.equipmentcontrol.web.vo.flushbonading.BaseEquipmentinfoOrEttypeVO;
import com.avst.equipmentcontrol.web.vo.flushbonading.FlushbonadinginfoVO;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

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

    @Autowired
    private FDInterface fdInterface;

    @Autowired
    private MiddlewareAsyncService middlewareAsyncService;

    private Gson gson=new Gson();

    private String pattern = ".*[\\u4E00-\\u9FA5].*";

    //查询
    public void getFlushbonadingList(RResult result, ReqParam<FlushbonadinginfoParam> param) {
        FlushbonadinginfoVO flushbonadinginfoVO = new FlushbonadinginfoVO();

        //请求参数转换
        FlushbonadinginfoParam flushbonadinginfoParam = param.getParam();
        if (null == flushbonadinginfoParam) {
            result.setMessage("参数为空");
            return;
        }

        EntityWrapper ew = new EntityWrapper();
        if (StringUtils.isNotBlank(flushbonadinginfoParam.getLivingurl())) {
            ew.like("fet.livingurl", flushbonadinginfoParam.getLivingurl());
        }
        if (StringUtils.isNotBlank(flushbonadinginfoParam.getUser())) {
            ew.like("fet.user", flushbonadinginfoParam.getUser());
        }
        if (StringUtils.isNotBlank(flushbonadinginfoParam.getEtnum())) {
            ew.like("et.etnum", flushbonadinginfoParam.getEtnum());
        }
        if (StringUtils.isNotBlank(flushbonadinginfoParam.getEtypessid())) {
            ew.eq("et.etypessid", flushbonadinginfoParam.getEtypessid());
        }

        int count = flushbonading_etinfoMapper.getFlushbonadingCount(ew);
        flushbonadinginfoParam.setRecordCount(count);

        ew.orderBy("fet.id", false);
        Page<Flushbonadinginfo> page = new Page<Flushbonadinginfo>(flushbonadinginfoParam.getCurrPage(), flushbonadinginfoParam.getPageSize());

        List<Flushbonadinginfo> flushbonadingList = flushbonading_etinfoMapper.getFlushbonadingList(page, ew);

        flushbonadinginfoVO.setPagelist(flushbonadingList);
        flushbonadinginfoVO.setPageparam(flushbonadinginfoParam);

        result.setData(flushbonadinginfoVO);
        changeResultToSuccess(result);
        return;
    }

    //查询单个
    public void getFlushbonadingById(RResult result, ReqParam<FlushbonadinginfoParam> param) {

        //请求参数转换
        FlushbonadinginfoParam flushbonadinginfoParam = param.getParam();
        if (null == flushbonadinginfoParam) {
            result.setMessage("参数为空");
            return;
        }

        if (StringUtils.isBlank(flushbonadinginfoParam.getSsid())) {
            result.setMessage("查询的参数不能为空");
            return;
        }

        EntityWrapper ew = new EntityWrapper();
        ew.eq("fet.ssid", flushbonadinginfoParam.getSsid());

        Flushbonadinginfo flushbonadinginfo = flushbonading_etinfoMapper.getFlushbonadinginfo(ew);

        result.setData(flushbonadinginfo);
        changeResultToSuccess(result);
        return;
    }

    //新增
    @Transactional
    public void addFlushbonading(RResult result, ReqParam<Flushbonadinginfo> param) {

        //请求参数转换
        Flushbonadinginfo paramParam = param.getParam();
        if (null == paramParam) {
            result.setMessage("参数为空");
            return;
        }
        if (null == paramParam.getPort()) {
            result.setMessage("开放接口的端口不能为空");
            return;
        }
        if (StringUtils.isBlank(paramParam.getUser())) {
            result.setMessage("登录用户名不能为空");
            return;
        }
        if (StringUtils.isBlank(paramParam.getPasswd())) {
            result.setMessage("登录密码不能为空");
            return;
        }
        if (StringUtils.isBlank(paramParam.getUploadbasepath())) {
            result.setMessage("ftp上传存储路径不能为空");
            return;
        }
        if (StringUtils.isBlank(paramParam.getEtypessid())) {
            result.setMessage("设备类型不能为空");
            return;
        }
        if (StringUtils.isBlank(paramParam.getEtnum())) {
            result.setMessage("设备名称不能为空");
            return;
        }
        if(Pattern.matches(pattern, paramParam.getUser())){
            result.setMessage("登录用户名不能有中文");
            return;
        }
        if(Pattern.matches(pattern, paramParam.getPasswd())){
            result.setMessage("登录密码不能有中文");
            return;
        }
        if(Pattern.matches(pattern, paramParam.getEtypessid())){
            result.setMessage("设备类型不能有中文");
            return;
        }
        if (StringUtils.isBlank(paramParam.getEtip())) {
            result.setMessage("设备IP不能为空");
            return;
        }
        if (null == paramParam.getBurntime()) {
            paramParam.setBurntime(8);
        }
        if (null == paramParam.getBurnbool()) {
            paramParam.setBurnbool(0);
        }
        if (null == paramParam.getPtshowtime()) {
            paramParam.setPtshowtime(5);
        }
        if (null == paramParam.getDiskrecbool()) {
            paramParam.setDiskrecbool(1);
        }

        boolean isip = OpenUtil.isIp(paramParam.getEtip());
        if(!isip){
            result.setMessage("设备IP不是一个正确的IP");
            return;
        }

        if (!StringUtils.isNumeric(paramParam.getPort() + "")) {
            result.setMessage("端口号只能由数字组成");
            return;
        }

        EntityWrapper<Flushbonading_etinfo> wrapper = new EntityWrapper<>();
        wrapper.eq("b.etnum", paramParam.getEtnum());
        wrapper.eq("b.etip", paramParam.getEtip());

        int repetitionCount = flushbonading_etinfoMapper.getRepetition(wrapper);
        if (repetitionCount > 0) {
            result.setMessage("该审讯设备已经存在，设备名称和设备IP的组合一定是唯一的");
            return;
        }

        //如果存在就不添加了
        EntityWrapper<Base_equipmentinfo> ew = new EntityWrapper<>();
        ew.eq("etnum", paramParam.getEtnum());
        ew.eq("etip", paramParam.getEtip());
        ew.eq("etypessid", paramParam.getEtypessid());

        Base_equipmentinfo base_equipmentinfo = new Base_equipmentinfo();

        List<Base_equipmentinfo> equipmentinfoList = base_equipmentinfoMapper.selectList(ew);
        if (equipmentinfoList.size() == 0) {
            base_equipmentinfo.setEtnum(paramParam.getEtnum());
            base_equipmentinfo.setEtip(paramParam.getEtip());
            base_equipmentinfo.setEtypessid(paramParam.getEtypessid());
            base_equipmentinfo.setSsid(OpenUtil.getUUID_32());

            base_equipmentinfoMapper.insert(base_equipmentinfo);
            BaseEcCache.delBaseEcCache();
        }else{
            base_equipmentinfo = equipmentinfoList.get(0);
        }

        if(StringUtils.isBlank(base_equipmentinfo.getSsid())){
            result.setMessage("设备表获取失败。。");
            return;
        }

        //拼接直播地址，直播预览地址
        String urlModeLlivingurl = PropertiesListenerConfig.getProperty("urlModel.livingurl");//value
        String urlModePreviewurl = PropertiesListenerConfig.getProperty("urlModel.previewurl");

        String livingurl = urlModeLlivingurl.replace("@url", paramParam.getEtip());
        String previewurl = urlModePreviewurl.replace("@url", paramParam.getEtip());

        Flushbonading_etinfo flushbonading_etinfo = new Flushbonading_etinfo();
        flushbonading_etinfo.setLivingurl(livingurl);
        flushbonading_etinfo.setPreviewurl(previewurl);
        flushbonading_etinfo.setDefaulturlbool(paramParam.getDefaulturlbool());
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

        if (StringUtils.isBlank(paramParam.getExplain())) {
            flushbonading_etinfo.setExplain(paramParam.getEtip());
        }

        if (1 == paramParam.getDefaulturlbool()) {
            //全部设置为0
            Flushbonading_etinfo flushbonading = new Flushbonading_etinfo();
            flushbonading.setDefaulturlbool(0);
            int update_bool=flushbonading_etinfoMapper.update(flushbonading, null);
            LogUtil.intoLog(1,this.getClass(),update_bool+":update_bool 修改设备是否成功--");
        }

        Integer insert = flushbonading_etinfoMapper.insert(flushbonading_etinfo);
        LogUtil.intoLog(1,this.getClass(),insert+":insert 修改录像设备是否成功--");
        if (insert == 1) {
            result.setData(flushbonading_etinfo.getSsid());
            changeResultToSuccess(result);
        } else {
            result.setData(insert);
            result.setMessage("录像设备新增失败");
        }

    }

    /**
     * 修改
     */
    @Transactional
    public void updateFlushbonading(RResult result, ReqParam<Flushbonadinginfo> param) {

        /**请求参数转换*/
        Flushbonadinginfo paramParam = param.getParam();
        if (null == paramParam) {
            result.setMessage("参数为空");
            return;
        }

        if (StringUtils.isBlank(paramParam.getSsid())) {
            result.setMessage("修改的ssid不能为空");
            return;
        }
        if (null == paramParam.getPort()) {
            result.setMessage("开放接口的端口不能为空");
            return;
        }
        if (StringUtils.isBlank(paramParam.getUser())) {
            result.setMessage("登录用户名不能为空");
            return;
        }
        if (StringUtils.isBlank(paramParam.getPasswd())) {
            result.setMessage("登录密码不能为空");
            return;
        }
        if (StringUtils.isBlank(paramParam.getUploadbasepath())) {
            result.setMessage("ftp上传存储路径不能为空");
            return;
        }
        if (StringUtils.isBlank(paramParam.getEtypessid())) {
            result.setMessage("设备类型不能为空");
            return;
        }
        if (StringUtils.isBlank(paramParam.getEtnum())) {
            result.setMessage("设备名称不能为空");
            return;
        }
        if(Pattern.matches(pattern, paramParam.getUser())){
            result.setMessage("登录用户名不能有中文");
            return;
        }
        if(Pattern.matches(pattern, paramParam.getPasswd())){
            result.setMessage("登录密码不能有中文");
            return;
        }
        if(Pattern.matches(pattern, paramParam.getEtypessid())){
            result.setMessage("设备类型不能有中文");
            return;
        }
        if (StringUtils.isBlank(paramParam.getEtip())) {
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
        if (null == paramParam.getPtshowtime()) {
            result.setMessage("片头显示时间不能为空");
            return;
        }
        if (null == paramParam.getDiskrecbool()) {
            result.setMessage("是否需要硬盘录像不能为空");
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

        EntityWrapper<Flushbonading_etinfo> wrapper2 = new EntityWrapper<>();
        wrapper2.eq("f.port", paramParam.getPort());
        wrapper2.eq("f.user", paramParam.getUser());
        wrapper2.eq("f.passwd", paramParam.getPasswd());
        wrapper2.eq("f.uploadbasepath", paramParam.getUploadbasepath());
        wrapper2.eq("f.diskrecbool", paramParam.getDiskrecbool());
        wrapper2.eq("f.burnbool", paramParam.getBurnbool());
        wrapper2.eq("f.burntime", paramParam.getBurntime());
        wrapper2.eq("f.ptshowtime", paramParam.getPtshowtime());
        wrapper2.eq("b.etnum", paramParam.getEtnum());
        wrapper2.eq("b.etip", paramParam.getEtip());
        wrapper2.ne("f.ssid", paramParam.getSsid());

        int repetitionCount = flushbonading_etinfoMapper.getRepetition(wrapper2);
        if (repetitionCount > 0) {
            result.setMessage("该审讯设备已经存在");
            return;
        }

        /**查询审讯主机从里面拿到他的设备ssid*/
        EntityWrapper ew0 = new EntityWrapper();
        ew0.eq("fet.ssid", paramParam.getSsid());
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
            ew3.eq("e.flushbonadingssid", flushbonadingEtinfo.getSsid());
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

        //拼接直播地址，直播预览地址
        String urlModeLlivingurl = PropertiesListenerConfig.getProperty("urlModel.livingurl");//value
        String urlModePreviewurl = PropertiesListenerConfig.getProperty("urlModel.previewurl");

        String livingurl = urlModeLlivingurl.replace("@url", paramParam.getEtip());
        String previewurl = urlModePreviewurl.replace("@url", paramParam.getEtip());

        Flushbonading_etinfo flushbonading_etinfo = new Flushbonading_etinfo();
        flushbonading_etinfo.setLivingurl(livingurl);
        flushbonading_etinfo.setPreviewurl(previewurl);
        flushbonading_etinfo.setDefaulturlbool(paramParam.getDefaulturlbool());
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

        if (StringUtils.isBlank(paramParam.getExplain())) {
            flushbonading_etinfo.setExplain(paramParam.getEtip());
        }

        if (1 == paramParam.getDefaulturlbool()) {
            //全部设置为0
            Flushbonading_etinfo flushbonading = new Flushbonading_etinfo();
            flushbonading.setDefaulturlbool(0);
            flushbonading_etinfoMapper.update(flushbonading, null);
        }

        Integer update = flushbonading_etinfoMapper.update(flushbonading_etinfo, ew);
        System.out.println("update_boot：" + update);
        if (update == 1) {
            result.setData(flushbonading_etinfo.getSsid());
        } else {
            result.setData(update);
        }

        //异步修改ftp上传存储路径名和设备ID保持一致，保持最终一致性
        middlewareAsyncService.updateMiddleware_ftpByID(flushbonadingEtinfo, result, paramParam);

        changeResultToSuccess(result);
    }

    /**
     * 删除
     */
    @Transactional
    public void delFlushbonading(RResult result, ReqParam<FlushbonadinginfoParam> param) {

        //请求参数转换
        FlushbonadinginfoParam paramParam = param.getParam();
        if (null == paramParam) {
            result.setMessage("参数为空");
            return;
        }

        if (StringUtils.isBlank(paramParam.getSsid())) {
            result.setMessage("删除的ssid不能为空");
            return;
        }

        //查询审讯主机从里面拿到他的设备ssid
        Flushbonadinginfo flushbonadinginfo = new Flushbonadinginfo();
        flushbonadinginfo.setSsid(paramParam.getSsid());
        Flushbonading_etinfo flushbonadingEtinfo = flushbonading_etinfoMapper.selectOne(flushbonadinginfo);

        if (null == flushbonadingEtinfo) {
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
     *
     * @param result
     */
    public void getBaseEttype(RResult result) {

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
     * 设为默认设备
     * @param result
     * @param param
     */
    @Transactional
    public void updateDefaulturlbool(RResult result, ReqParam<UpdateBurnboolFoDiskrecboolParam> param) {

        UpdateBurnboolFoDiskrecboolParam paramParam = param.getParam();

        if (StringUtils.isBlank(paramParam.getSsid())) {
            result.setMessage("修改的ssid不能为空");
            return;
        }
        if (null == paramParam.getState()) {
            result.setMessage("设为默认设备状态不能为空");
            return;
        }

        EntityWrapper ew = new EntityWrapper();
        ew.eq("ssid", paramParam.getSsid());

        List<Flushbonading_etinfo> list = flushbonading_etinfoMapper.selectList(ew);
        if (list.size() > 0) {
            Flushbonading_etinfo etinfo = list.get(0);
            if (etinfo.getDefaulturlbool() == 1) {
                result.setMessage("当前是最后一个默认设备，不允许设置为不默认");
                return;
            }
        }

        //全部设置为0
        Flushbonading_etinfo flushbonading = new Flushbonading_etinfo();
        flushbonading.setDefaulturlbool(0);
        flushbonading_etinfoMapper.update(flushbonading, null);

        Flushbonading_etinfo flushbonading_etinfo = new Flushbonading_etinfo();
        flushbonading_etinfo.setDefaulturlbool(paramParam.getState());

        Integer update = flushbonading_etinfoMapper.update(flushbonading_etinfo, ew);
        System.out.println("update_boot：" + update);
        if (update == 1) {
            result.setData(flushbonading_etinfo.getSsid());
        } else {
            result.setData(update);
        }
        changeResultToSuccess(result);
    }

    /**
     * 修改硬盘录像状态
     *
     * @param result
     * @param param
     */
    @Transactional
    public void updateDiskrecbool(RResult result, ReqParam<UpdateBurnboolFoDiskrecboolParam> param) {

        UpdateBurnboolFoDiskrecboolParam paramParam = param.getParam();

        if (StringUtils.isBlank(paramParam.getSsid())) {
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
        if (update == 1) {
            result.setData(flushbonading_etinfo.getSsid());
        } else {
            result.setData(update);
        }
        changeResultToSuccess(result);
    }

    /**
     * 修改光盘同刻状态
     *
     * @param result
     * @param param
     */
    @Transactional
    public void updateBurnbool(RResult result, ReqParam<UpdateBurnboolFoDiskrecboolParam> param) {

        UpdateBurnboolFoDiskrecboolParam paramParam = param.getParam();

        if (StringUtils.isBlank(paramParam.getSsid())) {
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
        if (update == 1) {
            result.setData(flushbonading_etinfo.getSsid());
        } else {
            result.setData(update);
        }
        changeResultToSuccess(result);

    }

    public void getBurnTime(Flushbonadinginfo param, RResult result) {

        Flushbonading_etinfo flushbonading_etinfo = flushbonading_etinfoMapper.selectOne(param);
        if(null != flushbonading_etinfo){
            result.setData(flushbonading_etinfo);
            result.changeToTrue();
        }
    }

    @Transactional
    public void updateBurnTime(Flushbonadinginfo param, RResult result) {

        EntityWrapper<Flushbonading_etinfo> ew = new EntityWrapper();
        ew.eq("ssid", param.getSsid());

        Flushbonadinginfo flushbonadinginfo = new Flushbonadinginfo();
        flushbonadinginfo.setSsid(param.getSsid());
        flushbonadinginfo.setBurntime(param.getBurntime());

        Integer update = flushbonading_etinfoMapper.update(flushbonadinginfo, ew);
        result.setData(update);
        result.changeToTrue();
    }


    public void getMiddleware_FTP(GetMiddleware_FTPParam_web param, RResult result) {

        String fdssid=param.getFdssid();
        //查询数据库找到设备
        EntityWrapper<Flushbonading_etinfo> ew=new EntityWrapper<Flushbonading_etinfo>();
        ew.eq("fet.ssid",fdssid);
        Flushbonadinginfo flushbonadinginfo=flushbonading_etinfoMapper.getFlushbonadinginfo(ew);
        if(null==flushbonadinginfo){
            result.setMessage("设备未找到，请查询数据");
            return ;
        }
        GetMiddleware_FTPParam getMiddleware_ftpParam=new GetMiddleware_FTPParam();
        getMiddleware_ftpParam.setIp(flushbonadinginfo.getEtip());
        getMiddleware_ftpParam.setPasswd(flushbonadinginfo.getPasswd());
        getMiddleware_ftpParam.setPort(flushbonadinginfo.getPort());
        getMiddleware_ftpParam.setUser(flushbonadinginfo.getUser());

        RResult middlewareFtp = fdInterface.getMiddleware_FTP(getMiddleware_ftpParam, result);

        if(null != middlewareFtp){
            result.setVersion(middlewareFtp.getVersion());
            result.setActioncode(middlewareFtp.getActioncode());
            result.setData(middlewareFtp.getData());
            result.setMessage(middlewareFtp.getMessage());
            result.setEndtime(middlewareFtp.getEndtime());
        }else{
            result.setMessage("返回的数据是空的");
        }
    }

    @Transactional
    public void setMiddleware_FTP(SetMiddleware_FTPParam_web param, RResult result) {

        String fdssid=param.getFdssid();
        //查询数据库找到设备
        EntityWrapper<Flushbonading_etinfo> ew=new EntityWrapper<Flushbonading_etinfo>();
        ew.eq("fet.ssid",fdssid);
        Flushbonadinginfo flushbonadinginfo=flushbonading_etinfoMapper.getFlushbonadinginfo(ew);
        if(null==flushbonadinginfo){
            result.setMessage("设备未找到，请查询数据");
            return ;
        }
        SetMiddleware_FTPParam setMiddleware_ftpParam=new SetMiddleware_FTPParam();
        setMiddleware_ftpParam=gson.fromJson(gson.toJson(param),SetMiddleware_FTPParam.class);
        if(null==setMiddleware_ftpParam){
            result.setMessage("设置集中控制参数异常");
            return ;
        }

        //因为设备ID要和ftp上传存储路径名要保持一致
        if(!flushbonadinginfo.getUploadbasepath().equals(param.getDeviceid())){
            Flushbonading_etinfo flushInfo = new Flushbonadinginfo();
            flushInfo.setUploadbasepath(param.getDeviceid());
            EntityWrapper<Flushbonading_etinfo> wrapper = new EntityWrapper<>();
            wrapper.eq("ssid",fdssid);
            Integer update = flushbonading_etinfoMapper.update(flushInfo, wrapper);
            LogUtil.intoLog(1, this.getClass(), "设备ID和ftp储路径名修改成一致：" + update);
        }

        setMiddleware_ftpParam.setIp(flushbonadinginfo.getEtip());
        setMiddleware_ftpParam.setPasswd(flushbonadinginfo.getPasswd());
        setMiddleware_ftpParam.setPort(flushbonadinginfo.getPort());
        setMiddleware_ftpParam.setUser(flushbonadinginfo.getUser());

        RResult middlewareFtp = fdInterface.setMiddleware_FTP(setMiddleware_ftpParam, result);

        if(null != middlewareFtp){
            result.setVersion(middlewareFtp.getVersion());
            result.setActioncode(middlewareFtp.getActioncode());
            result.setData(middlewareFtp.getData());
            result.setMessage(middlewareFtp.getMessage());
            result.setEndtime(middlewareFtp.getEndtime());
        }else{
            result.setMessage("返回的数据是空的");
        }
    }




}
