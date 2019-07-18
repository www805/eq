package com.avst.equipmentcontrol.outside.interfacetoout.storage.v1;

import com.avst.equipmentcontrol.common.conf.SSType;
import com.avst.equipmentcontrol.common.util.baseaction.BaseAction;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.outside.interfacetoout.asr.v1.service.ToOutServiceImpl_asr_avst;
import com.avst.equipmentcontrol.outside.interfacetoout.storage.req.*;
import com.avst.equipmentcontrol.outside.interfacetoout.storage.v1.service.ToOutServiceImpl_ss_avst;
import com.avst.equipmentcontrol.outside.interfacetoout.storage.v1.service.ToOutService_ss;
import com.avst.equipmentcontrol.outside.interfacetoout.storage.vo.GetSavepathVO;
import com.avst.equipmentcontrol.outside.interfacetoout.storage.vo.GetURLToPlayVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 存储管理的对外开放接口
 */
@Controller
@RequestMapping("/ss/v1")
public class ToOutAction_SS extends BaseAction {

    @Autowired
    private ToOutServiceImpl_ss_avst toOutServiceImpl_ss_avst;

    private ToOutService_ss getToOutServiceImpl(String type){

        if(null!=type&&type.equals(SSType.AVST)){
            return toOutServiceImpl_ss_avst;
        }
        return null;
    }


    /**
     * 存储嵌入式设备的数据
     * @param param
     * @return
     */
    @RequestMapping("/saveFile")
    @ResponseBody
    public RResult saveFile(@RequestBody SaveFileParam param){
        RResult rResult=this.createNewResultOfFail();

        if(null!=param&&null!=param.getSsType()){
            rResult=getToOutServiceImpl(param.getSsType()).saveFile(param,rResult);
        }

        return rResult;
    };

    /**
     * 存储本地服务的数据
     * @param param
     * @return
     */
    @RequestMapping("/saveFile_local")
    @ResponseBody
    public RResult saveFile_local(@RequestBody SaveFile_localParam param){
        RResult rResult=this.createNewResultOfFail();

        if(null!=param&&null!=param.getSsType()){
            rResult=getToOutServiceImpl(param.getSsType()).saveFile_local(param,rResult);
        }

        return rResult;
    };

    @RequestMapping("/getURLToPlay")
    @ResponseBody
    public RResult<GetURLToPlayVO> getURLToPlay(@RequestBody GetURLToPlayParam param){
        RResult rResult=this.createNewResultOfFail();

        if(null!=param&&null!=param.getSsType()){
            rResult=getToOutServiceImpl(param.getSsType()).getURLToPlay(param,rResult);
        }

        return rResult;
    }

    /**
     * 获取iid对应的本地存储地址
     * @param param
     * @return
     */
    @RequestMapping("/getSavePath")
    @ResponseBody
    public RResult<GetSavepathVO> getSavePath(@RequestBody GetSavePathParam param){
        RResult rResult=this.createNewResultOfFail();

        if(null!=param&&null!=param.getSsType()){
            rResult=getToOutServiceImpl(param.getSsType()).getSavePath(param,rResult);
        }

        return rResult;
    }

    @RequestMapping("/checkRecordFileState")
    @ResponseBody
    public RResult checkRecordFileState(@RequestBody CheckRecordFileStateParam param){
        RResult rResult=this.createNewResultOfFail();

        if(null!=param&&null!=param.getSsType()){
            rResult=getToOutServiceImpl(param.getSsType()).checkRecordFileState(param,rResult);
        }

        return rResult;
    }
}
