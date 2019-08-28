package com.avst.equipmentcontrol.outside.interfacetoout.storage.v1.service;

import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.outside.interfacetoout.storage.req.*;
import com.avst.equipmentcontrol.outside.interfacetoout.storage.vo.GetSavepathVO;
import com.avst.equipmentcontrol.outside.interfacetoout.storage.vo.GetURLToPlayVO;
import org.springframework.stereotype.Service;

@Service
public interface ToOutService_ss {

    public RResult saveFile(SaveFileParam saveFileParam,RResult result);

    public RResult saveFile_local(SaveFile_localParam param, RResult result);

    public RResult<GetURLToPlayVO> getURLToPlay(GetURLToPlayParam param, RResult result);

    public RResult<GetSavepathVO> getSavePath(GetSavePathParam param, RResult result);

    public RResult checkRecordFileState(CheckRecordFileStateParam param, RResult result);

    /**
     * 获取对应iid的真实的本地路径
     * @param param
     * @param result
     * @return
     */
    public RResult getSaveFilePath_local(GetSaveFilePath_localParam param, RResult result);

    /**
     * 通过iid获取这个iid对应文件夹下的所有有用文件
     * @param param
     * @return
     */
    public RResult getSaveFilesPathByiid(GetSaveFilesPathByiidParam param, RResult result);



}
