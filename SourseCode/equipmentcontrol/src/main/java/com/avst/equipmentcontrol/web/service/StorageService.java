package com.avst.equipmentcontrol.web.service;

import com.avst.equipmentcontrol.common.cache.BaseEcCache;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.entity.Ss_saveinfo;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.entity.Storage_ettype;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.mapper.Ss_saveinfoMapper;
import com.avst.equipmentcontrol.common.datasourse.publicsourse.entity.Base_equipmentinfo;
import com.avst.equipmentcontrol.common.datasourse.publicsourse.entity.Base_ettype;
import com.avst.equipmentcontrol.common.datasourse.publicsourse.mapper.Base_equipmentinfoMapper;
import com.avst.equipmentcontrol.common.datasourse.publicsourse.mapper.Base_ettypeMapper;
import com.avst.equipmentcontrol.common.util.FileUtil;
import com.avst.equipmentcontrol.common.util.OpenUtil;
import com.avst.equipmentcontrol.common.util.baseaction.BaseService;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.common.util.baseaction.ReqParam;
import com.avst.equipmentcontrol.common.util.filespace.DriverSpaceParam;
import com.avst.equipmentcontrol.common.util.filespace.FileSpaceUtil;
import com.avst.equipmentcontrol.web.req.storage.FileSpaceByssidParam;
import com.avst.equipmentcontrol.web.req.storage.StorageParam;
import com.avst.equipmentcontrol.web.req.storage.UpdateStorageParam;
import com.avst.equipmentcontrol.web.vo.storage.GetFileSpaceByssidVO;
import com.avst.equipmentcontrol.web.vo.storage.StorageVO;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class StorageService extends BaseService {

    @Autowired
    private Ss_saveinfoMapper ss_saveinfoMapper;

    @Autowired
    private Base_ettypeMapper base_ettypeMapper;

    @Autowired
    private Base_equipmentinfoMapper base_equipmentinfoMapper;

    private String pattern = ".*[\\u4E00-\\u9FA5].*";

    //查询
    public void getStorageList(RResult result, ReqParam<StorageParam> param){

        StorageVO storageVO = new StorageVO();

        //请求参数转换
        StorageParam paramParam = param.getParam();
        if (null==paramParam){
            result.setMessage("参数为空");
            return;
        }

        EntityWrapper ew=new EntityWrapper();
        if (StringUtils.isNotBlank(paramParam.getPort())){
            ew.like("ss.port",paramParam.getPort());
        }
        if (StringUtils.isNotBlank(paramParam.getTotalcapacity())){
            ew.ge("ss.totalcapacity",paramParam.getTotalcapacity());
        }
        if (StringUtils.isNotBlank(paramParam.getEtnum())){
            ew.like("et.etnum",paramParam.getEtnum());
        }

        int count = ss_saveinfoMapper.getStorageInfoCount(ew);
        paramParam.setRecordCount(count);

        ew.orderBy("ss.id",false);
        Page<Storage_ettype> page=new Page<Storage_ettype>(paramParam.getCurrPage(),paramParam.getPageSize());

        List<Storage_ettype> storageInfoPage = ss_saveinfoMapper.getStorageInfoPage(page, ew);

        storageVO.setPagelist(storageInfoPage);
        storageVO.setPageparam(paramParam);

        result.setData(storageVO);
        changeResultToSuccess(result);
    }


    //查询单次
    public void getStorageById(RResult result, ReqParam<StorageParam> param){

        //请求参数转换
        StorageParam paramParam = param.getParam();
        if (null==paramParam){
            result.setMessage("参数为空");
            return;
        }

        if (StringUtils.isBlank(paramParam.getSsid())){
            result.setMessage("查询的参数不能为空");
            return;
        }

        EntityWrapper ew=new EntityWrapper();
        ew.eq("ss.ssid",paramParam.getSsid());

        Storage_ettype storageinfo = ss_saveinfoMapper.getStorageinfo(ew);

        result.setData(storageinfo);
        changeResultToSuccess(result);

    }


    //新增
    @Transactional
    public void addStorage(RResult result, ReqParam<UpdateStorageParam> param){

        //请求参数转换
        UpdateStorageParam paramParam = param.getParam();
        if (null==paramParam){
            result.setMessage("参数为空");
            return;
        }

        if (null == paramParam.getPort()) {
            result.setMessage("开放接口的端口不能为空");
            return;
        }
        if (StringUtils.isBlank(paramParam.getSstype())){
            result.setMessage("服务类型不能为空");
            return;
        }
        if (StringUtils.isBlank(paramParam.getDatasavebasepath())){
            result.setMessage("存储本地文件夹base路径不能为空");
            return;
        }
        if (StringUtils.isBlank(paramParam.getEtypessid())){
            result.setMessage("设备类型不能为空");
            return;
        }
        if (StringUtils.isBlank(paramParam.getEtnum())){
            result.setMessage("设备名称不能为空");
            return;
        }
        if(Pattern.matches(pattern, paramParam.getSstype())){
            result.setMessage("服务类型不能有中文");
            return;
        }
        if(Pattern.matches(pattern, paramParam.getEtypessid())){
            result.setMessage("设备类型不能有中文");
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

        File dataPathFile = new File(paramParam.getDatasavebasepath());
        if (!dataPathFile.exists()) {
            result.setMessage("存储本地文件夹base路径不存在！");
            return;
        }

        String datasavebasepath = paramParam.getDatasavebasepath();
        String substring1 = datasavebasepath.substring(datasavebasepath.length() - 3);
        String substring2 = datasavebasepath.substring(datasavebasepath.length() - 2);
        String substring3 = datasavebasepath.substring(datasavebasepath.length() - 1);

        if (":".equals(substring3) || ":/".equals(substring2) || "://".equals(substring1)) {
            result.setMessage("存储路径不能是磁盘！");
            return;
        }

        //获取已用容量
        DriverSpaceParam filePathSpace = FileSpaceUtil.getFilePathSpace(paramParam.getDatasavebasepath());

        long usedcapacity = filePathSpace.getUseSpace();//已用容量
        long totalSpace = filePathSpace.getTotalSpace();//总容量

        Integer totalSpaceNum = 1;
        if (totalSpace >= 1000d) {
            String totalSpaceStr = String.valueOf(totalSpace);
            //去除后四位数
            String substring = totalSpaceStr.substring(0, totalSpaceStr.length() - 4);
            totalSpaceNum = Integer.valueOf(substring);
        }else{
            totalSpaceNum = Double.hashCode(totalSpace);
        }

        Integer usedcapacityNum = 1;
        if (usedcapacity >= 1000d) {
            String usedcapacityStr = String.valueOf(usedcapacity);
            //去除后四位数
            String substring = usedcapacityStr.substring(0, usedcapacityStr.length() - 4);
            usedcapacityNum = Integer.valueOf(substring);
        }else{
            usedcapacityNum = Double.hashCode(usedcapacity);
        }

        EntityWrapper<Ss_saveinfo> wrapper = new EntityWrapper<>();
        wrapper.eq("s.port", paramParam.getPort());
        wrapper.eq("s.sstype", paramParam.getSstype());
        wrapper.eq("s.datasavebasepath", paramParam.getDatasavebasepath());
        wrapper.eq("b.etnum", paramParam.getEtnum());
        wrapper.eq("b.etip", paramParam.getEtip());

        int repetitionCount = ss_saveinfoMapper.getRepetition(wrapper);
        if (repetitionCount > 0) {
            result.setMessage("该存储服务已经存在");
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

        Ss_saveinfo ss_saveinfo = new Ss_saveinfo();
        ss_saveinfo.setTotalcapacity(totalSpaceNum);
        ss_saveinfo.setUsedcapacity(usedcapacityNum);
        ss_saveinfo.setPort(paramParam.getPort());
        ss_saveinfo.setSstype(paramParam.getSstype());
        ss_saveinfo.setDatasavebasepath(paramParam.getDatasavebasepath());
        ss_saveinfo.setExplain(paramParam.getExplain());
        ss_saveinfo.setMtssid(base_equipmentinfo.getSsid());
        ss_saveinfo.setSsid(OpenUtil.getUUID_32());

        if (StringUtils.isBlank(paramParam.getExplain())) {
            ss_saveinfo.setExplain(paramParam.getEtip());
        }

        Integer insert = ss_saveinfoMapper.insert(ss_saveinfo);
        System.out.println("add_boot：" + insert);
        if(insert == 1){
            result.setData(ss_saveinfo.getSsid());
        }else{
            result.setData(insert);
        }
        changeResultToSuccess(result);

    }

    //修改
    @Transactional
    public void updateStorage(RResult result, ReqParam<UpdateStorageParam> param){

        //请求参数转换
        UpdateStorageParam paramParam = param.getParam();
        if (null==paramParam){
            result.setMessage("参数为空");
            return;
        }

        if (StringUtils.isBlank(paramParam.getSsid())){
            result.setMessage("修改的ssid不能为空");
            return;
        }
        if (null == paramParam.getPort()) {
            result.setMessage("开放接口的端口不能为空");
            return;
        }
        if (StringUtils.isBlank(paramParam.getSstype())){
            result.setMessage("服务类型不能为空");
            return;
        }
        if (StringUtils.isBlank(paramParam.getDatasavebasepath())){
            result.setMessage("存储本地文件夹base路径不能为空");
            return;
        }
        if (StringUtils.isBlank(paramParam.getEtypessid())){
            result.setMessage("设备类型不能为空");
            return;
        }
        if (StringUtils.isBlank(paramParam.getEtnum())){
            result.setMessage("设备名称不能为空");
            return;
        }
        if(Pattern.matches(pattern, paramParam.getSstype())){
            result.setMessage("服务类型不能有中文");
            return;
        }
        if(Pattern.matches(pattern, paramParam.getEtypessid())){
            result.setMessage("设备类型不能有中文");
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


        String datasavebasepath = paramParam.getDatasavebasepath().trim();
        String substring1 = datasavebasepath.substring(datasavebasepath.length() - 3);
        String substring2 = datasavebasepath.substring(datasavebasepath.length() - 2);
        String substring3 = datasavebasepath.substring(datasavebasepath.length() - 1);

        if (":".equals(substring3) || ":/".equals(substring2) || "://".equals(substring1)) {
            result.setMessage("存储路径不能是磁盘！");
            return;
        }

        File dataPathFile = new File(datasavebasepath);
        if (!dataPathFile.exists()) {
            result.setMessage("存储本地文件夹base路径不存在！");
            return;
        }

        //获取已用容量
        DriverSpaceParam filePathSpace = FileSpaceUtil.getFilePathSpace(datasavebasepath);

        long usedcapacity = filePathSpace.getUseSpace();//已用容量
        long totalSpace = filePathSpace.getTotalSpace();//总容量

        Integer totalSpaceNum = 1;
        if (totalSpace >= 1000d) {
            String totalSpaceStr = String.valueOf(totalSpace);
            //去除后四位数
            String substring = totalSpaceStr.substring(0, totalSpaceStr.length() - 4);
            totalSpaceNum = Integer.valueOf(substring);
        }else{
            totalSpaceNum = Double.hashCode(totalSpace);
        }

        Integer usedcapacityNum = 1;
        if (usedcapacity >= 1000d) {
            String usedcapacityStr = String.valueOf(usedcapacity);
            //去除后四位数
            String substring = usedcapacityStr.substring(0, usedcapacityStr.length() - 4);
            usedcapacityNum = Integer.valueOf(substring);
        }else{
            usedcapacityNum = Double.hashCode(usedcapacity);
        }


//        String fileSize = FileSpaceUtil.FormetFileSize(usedcapacity);

        EntityWrapper<Ss_saveinfo> wrapper = new EntityWrapper<>();
        wrapper.eq("s.port", paramParam.getPort());
        wrapper.eq("s.sstype", paramParam.getSstype());
        wrapper.eq("s.datasavebasepath", paramParam.getDatasavebasepath().trim());
        wrapper.eq("b.etnum", paramParam.getEtnum());
        wrapper.eq("b.etip", paramParam.getEtip());
        wrapper.ne("s.ssid", paramParam.getSsid());

        int repetitionCount = ss_saveinfoMapper.getRepetition(wrapper);
        if (repetitionCount > 0) {
            result.setMessage("该存储服务已经存在");
            return;
        }

        //查询审讯主机从里面拿到他的设备ssid
        Ss_saveinfo ss_saveinfo = new Ss_saveinfo();
        ss_saveinfo.setSsid(paramParam.getSsid());
        Ss_saveinfo ssSaveinfo = ss_saveinfoMapper.selectOne(ss_saveinfo);


        //删除设备再新增，不然就是修改那个设备
        EntityWrapper ew2 = new EntityWrapper();
        ew2.eq("ssid", ssSaveinfo.getMtssid());

        Base_equipmentinfo equipmentinfo = new Base_equipmentinfo();
        equipmentinfo.setEtnum(paramParam.getEtnum());
        equipmentinfo.setEtip(paramParam.getEtip());
        equipmentinfo.setEtypessid(paramParam.getEtypessid());

        base_equipmentinfoMapper.update(equipmentinfo, ew2);

        EntityWrapper ew = new EntityWrapper();
        ew.eq("ssid", paramParam.getSsid());

        Ss_saveinfo saveinfo = new Ss_saveinfo();
        saveinfo.setTotalcapacity(totalSpaceNum);
        saveinfo.setUsedcapacity(usedcapacityNum);
        saveinfo.setPort(paramParam.getPort());
        saveinfo.setSstype(paramParam.getSstype());
        saveinfo.setDatasavebasepath(paramParam.getDatasavebasepath().trim());
        saveinfo.setExplain(paramParam.getExplain());
        saveinfo.setMtssid(equipmentinfo.getSsid());

        if (StringUtils.isBlank(paramParam.getExplain())) {
            saveinfo.setExplain(paramParam.getEtip());
        }

        Integer update = ss_saveinfoMapper.update(saveinfo, ew);
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
    public void delStorage(RResult result, ReqParam<StorageParam> param){

        //请求参数转换
        StorageParam paramParam = param.getParam();
        if (null==paramParam){
            result.setMessage("参数为空");
            return;
        }

        if (StringUtils.isBlank(paramParam.getSsid())){
            result.setMessage("删除的ssid不能为空");
            return;
        }

        //查询审讯主机从里面拿到他的设备ssid
        Ss_saveinfo ss_saveinfo = new Ss_saveinfo();
        ss_saveinfo.setSsid(paramParam.getSsid());
        Ss_saveinfo ssSaveinfo = ss_saveinfoMapper.selectOne(ss_saveinfo);

        if (null == ssSaveinfo){
            result.setMessage("没找到相应的设备ssid");
            return;
        }

        EntityWrapper ew2 = new EntityWrapper();
        ew2.eq("ssid", ssSaveinfo.getMtssid());
        base_equipmentinfoMapper.delete(ew2);

        EntityWrapper ew = new EntityWrapper();
        ew.eq("ssid", paramParam.getSsid());
        Integer delete = ss_saveinfoMapper.delete(ew);

        result.setData(delete);
        changeResultToSuccess(result);

    }

    public void getFileSpaceByssid(RResult result, ReqParam<FileSpaceByssidParam> param) {

        FileSpaceByssidParam paramParam = param.getParam();
        if(StringUtils.isBlank(paramParam.getSsid())){
            result.setMessage("ssid不能为空");
            return;
        }

        Ss_saveinfo ss_saveinfo = new Ss_saveinfo();
        ss_saveinfo.setSsid(paramParam.getSsid());

        Ss_saveinfo saveinfo = ss_saveinfoMapper.selectOne(ss_saveinfo);
        if(null == saveinfo || StringUtils.isBlank(saveinfo.getDatasavebasepath())){
            result.setMessage("没找到当前存储信息");
            return;
        }

        DriverSpaceParam filePathSpace = FileSpaceUtil.getFilePathSpace(saveinfo.getDatasavebasepath());//获取文件夹/文件夹对应的容量信息
        List<DriverSpaceParam> filePathSpaceByParentNodePath = FileSpaceUtil.getFilePathSpaceByParentNodePath(saveinfo.getDatasavebasepath());//获取文件夹对应的下一级所有文件/文件夹的容量信息

        GetFileSpaceByssidVO fileSpaceByssidVO = new GetFileSpaceByssidVO();
        fileSpaceByssidVO.setFilePathSpace(filePathSpace);
        fileSpaceByssidVO.setFilePathSpaceByParentNodePath(filePathSpaceByParentNodePath);

        result.changeToTrue(fileSpaceByssidVO);

    }

    public void delFileSpaceByPath(RResult result, ReqParam<FileSpaceByssidParam> param) {

        FileSpaceByssidParam paramParam = param.getParam();
        if(StringUtils.isBlank(paramParam.getPath())){
            result.setMessage("文件路径不能为空");
            return;
        }

        String path = paramParam.getPath();

        boolean b = false;

        File file = new File(path);//判断是否是文件夹 true=文件夹 false=文件
        if(file.isDirectory()){
            FileUtil.delFolder(path);
            b = true;
        }else{
            b = FileUtil.deleteFile(path);
        }

        if (b) {
            result.changeToTrue(b);
        }

    }

    public void delFileSpaceAll(RResult result, ReqParam<FileSpaceByssidParam> param) {

        FileSpaceByssidParam paramParam = param.getParam();
        if(StringUtils.isBlank(paramParam.getPath())){
            result.setMessage("文件路径不能为空");
            return;
        }

        String path = paramParam.getPath();
        File file = new File(path);
        String[] files = file.list();
        if (files.length == 0) {
            result.setMessage("该文件夹是空的！");
            return;
        }

        boolean b = FileUtil.delAllFile(path);
        if (b) {
            result.changeToTrue(b);
        }
    }

    public void getFileSpaceAll(RResult result, ReqParam<FileSpaceByssidParam> param) {

        FileSpaceByssidParam paramParam = param.getParam();
        if(StringUtils.isBlank(paramParam.getPath())){
            result.setMessage("文件路径不能为空");
            return;
        }

        String path = paramParam.getPath();
        File file = new File(path);//判断是否是文件夹 true=文件夹 false=文件
        if(!file.isDirectory()){
            result.setMessage("该文件不是文件夹，不能进入");
            return;
        }

        DriverSpaceParam filePathSpace = FileSpaceUtil.getFilePathSpace(path);//获取文件夹/文件夹对应的容量信息
        List<DriverSpaceParam> filePathSpaceByParentNodePath = FileSpaceUtil.getFilePathSpaceByParentNodePath(path);//获取文件夹对应的下一级所有文件/文件夹的容量信息

        GetFileSpaceByssidVO fileSpaceByssidVO = new GetFileSpaceByssidVO();
        fileSpaceByssidVO.setFilePathSpace(filePathSpace);
        fileSpaceByssidVO.setFilePathSpaceByParentNodePath(filePathSpaceByParentNodePath);

        result.changeToTrue(fileSpaceByssidVO);

    }
}
