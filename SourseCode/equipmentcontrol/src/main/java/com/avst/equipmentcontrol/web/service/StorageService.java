package com.avst.equipmentcontrol.web.service;

import com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.entity.Ss_saveinfo;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.entity.Storage_ettype;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.mapper.Ss_saveinfoMapper;
import com.avst.equipmentcontrol.common.datasourse.publicsourse.entity.Base_equipmentinfo;
import com.avst.equipmentcontrol.common.datasourse.publicsourse.entity.Base_ettype;
import com.avst.equipmentcontrol.common.datasourse.publicsourse.mapper.Base_equipmentinfoMapper;
import com.avst.equipmentcontrol.common.datasourse.publicsourse.mapper.Base_ettypeMapper;
import com.avst.equipmentcontrol.common.util.OpenUtil;
import com.avst.equipmentcontrol.common.util.baseaction.BaseService;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.common.util.baseaction.ReqParam;
import com.avst.equipmentcontrol.web.req.storage.StorageParam;
import com.avst.equipmentcontrol.web.req.storage.UpdateStorageParam;
import com.avst.equipmentcontrol.web.vo.storage.StorageVO;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StorageService extends BaseService {

    @Autowired
    private Ss_saveinfoMapper ss_saveinfoMapper;

    @Autowired
    private Base_ettypeMapper base_ettypeMapper;

    @Autowired
    private Base_equipmentinfoMapper base_equipmentinfoMapper;

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
    public void addStorage(RResult result, ReqParam<UpdateStorageParam> param){

        //请求参数转换
        UpdateStorageParam paramParam = param.getParam();
        if (null==paramParam){
            result.setMessage("参数为空");
            return;
        }

        if (null == paramParam.getTotalcapacity()) {
            result.setMessage("总容量不能为空");
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
            result.setMessage("设备编号不能为空");
            return;
        }
        if (StringUtils.isBlank(paramParam.getEtip())){
            result.setMessage("设备IP不能为空");
            return;
        }

        Base_equipmentinfo base_equipmentinfo = new Base_equipmentinfo();
        base_equipmentinfo.setEtnum(paramParam.getEtnum());
        base_equipmentinfo.setEtip(paramParam.getEtip());
        base_equipmentinfo.setEtypessid(paramParam.getEtypessid());
        base_equipmentinfo.setSsid(OpenUtil.getUUID_32());

        base_equipmentinfoMapper.insert(base_equipmentinfo);

        Ss_saveinfo ss_saveinfo = new Ss_saveinfo();
        ss_saveinfo.setTotalcapacity(paramParam.getTotalcapacity());
        ss_saveinfo.setUsedcapacity(0);
        ss_saveinfo.setPort(paramParam.getPort());
        ss_saveinfo.setSstype(paramParam.getSstype());
        ss_saveinfo.setDatasavebasepath(paramParam.getDatasavebasepath());
        ss_saveinfo.setExplain(paramParam.getExplain());
        ss_saveinfo.setMtssid(base_equipmentinfo.getSsid());
        ss_saveinfo.setSsid(OpenUtil.getUUID_32());

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
        if (null == paramParam.getTotalcapacity()) {
            result.setMessage("总容量不能为空");
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
            result.setMessage("设备编号不能为空");
            return;
        }
        if (StringUtils.isBlank(paramParam.getEtip())){
            result.setMessage("设备IP不能为空");
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
        saveinfo.setTotalcapacity(paramParam.getTotalcapacity());
        saveinfo.setPort(paramParam.getPort());
        saveinfo.setSstype(paramParam.getSstype());
        saveinfo.setDatasavebasepath(paramParam.getDatasavebasepath());
        saveinfo.setExplain(paramParam.getExplain());
        saveinfo.setMtssid(equipmentinfo.getSsid());

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

        EntityWrapper ew2 = new EntityWrapper();
        ew2.eq("ssid", ssSaveinfo.getMtssid());
        base_equipmentinfoMapper.delete(ew2);

        EntityWrapper ew = new EntityWrapper();
        ew.eq("ssid", paramParam.getSsid());
        Integer delete = ss_saveinfoMapper.delete(ew);

        result.setData(delete);
        changeResultToSuccess(result);

    }



}
