package com.avst.equipmentcontrol.web.action;

import com.avst.equipmentcontrol.common.util.DateUtil;
import com.avst.equipmentcontrol.common.util.baseaction.BaseAction;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.common.util.baseaction.ReqParam;
import com.avst.equipmentcontrol.web.req.storage.FileSpaceByssidParam;
import com.avst.equipmentcontrol.web.req.storage.StorageParam;
import com.avst.equipmentcontrol.web.req.storage.UpdateDefaultsaveboolParam;
import com.avst.equipmentcontrol.web.req.storage.UpdateStorageParam;
import com.avst.equipmentcontrol.web.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

//存储设备
@RestController
@RequestMapping("/Storage")
public class StorageAction extends BaseAction {

    @Autowired
    private StorageService storageService;

    //查询
    @RequestMapping(value = "/getStorageList")
    public RResult getStorageList(@RequestBody ReqParam<StorageParam> param){
        RResult result=this.createNewResultOfFail();
        storageService.getStorageList(result,param);
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    //查询单个
    @RequestMapping(value = "/getStorageById")
    public RResult getStorageById(@RequestBody ReqParam<StorageParam> param){
        RResult result=this.createNewResultOfFail();
        storageService.getStorageById(result,param);
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    //新增
    @RequestMapping(value = "/addStorage")
    public RResult addStorage(@RequestBody ReqParam<UpdateStorageParam> param){
        RResult result=this.createNewResultOfFail();
        storageService.addStorage(result,param);
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    //修改
    @RequestMapping(value = "/updateStorage")
    public RResult updateStorage(@RequestBody ReqParam<UpdateStorageParam> param){
        RResult result=this.createNewResultOfFail();
        storageService.updateStorage(result,param);
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    //删除
    @RequestMapping(value = "/delStorage")
    public RResult delStorage(@RequestBody ReqParam<StorageParam> param){
        RResult result=this.createNewResultOfFail();
        storageService.delStorage(result,param);
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    //通过ssid查询文件管理
    @RequestMapping(value = "/getFileSpaceByssid")
    public RResult getFileSpaceByssid(@RequestBody ReqParam<FileSpaceByssidParam> param){
        RResult result=this.createNewResultOfFail();
        storageService.getFileSpaceByssid(result,param);
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    //查询路径下的所有文件
    @RequestMapping(value = "/getFileSpaceAll")
    public RResult getFileSpaceAll(@RequestBody ReqParam<FileSpaceByssidParam> param){
        RResult result=this.createNewResultOfFail();
        storageService.getFileSpaceAll(result,param);
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    //删除当前路径下的所有文件
    @RequestMapping(value = "/delFileSpaceAll")
    public RResult delFileSpaceAll(@RequestBody ReqParam<FileSpaceByssidParam> param){
        RResult result=this.createNewResultOfFail();
        storageService.delFileSpaceAll(result,param);
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    //删除单个文件
    @RequestMapping(value = "/delFileSpaceByPath")
    public RResult delFileSpaceByPath(@RequestBody ReqParam<FileSpaceByssidParam> param){
        RResult result=this.createNewResultOfFail();
        storageService.delFileSpaceByPath(result,param);
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    //设置为默认
    @RequestMapping(value = "/updateDefaultsavebool")
    public RResult updateDefaultsavebool(@RequestBody ReqParam<UpdateDefaultsaveboolParam> param){
        RResult result=this.createNewResultOfFail();
        storageService.updateDefaultsavebool(result,param);
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    //显示列表页面
    @RequestMapping(value = "/getStorageIndex")
    public ModelAndView getStorageIndex(Model model) {
        model.addAttribute("title", "存储服务列表");
        return new ModelAndView("sweb/storagehtml/getStorageIndex", "getStorageIndexModel", model);
    }

    //显示新增/修改页面
    @RequestMapping(value = "/addOrUpdateStorage")
    public ModelAndView addOrUpdateStorage(Model model) {
        model.addAttribute("title", "存储服务 新增/修改");
        return new ModelAndView("sweb/storagehtml/addOrUpdateStorage", "addOrUpdateStorageModel", model);
    }

    //磁盘管理
    @RequestMapping("/getFileSpacePage")
    public ModelAndView getFileSpacePage(Model model) {
        model.addAttribute("title", "磁盘文件管理");
        return new ModelAndView("sweb/storagehtml/getFileSpacePage", "getFileSpacePage", model);
    }
}
