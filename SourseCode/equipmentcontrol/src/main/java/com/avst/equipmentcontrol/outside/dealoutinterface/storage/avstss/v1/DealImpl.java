package com.avst.equipmentcontrol.outside.dealoutinterface.storage.avstss.v1;

import com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.entity.Ss_database;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.entity.Ss_saveinfo;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.entity.param.Ss_dataMessageParam;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.mapper.Ss_databaseMapper;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.mapper.Ss_datasaveMapper;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.mapper.Ss_saveinfoMapper;
import com.avst.equipmentcontrol.common.util.LogUtil;
import com.avst.equipmentcontrol.common.util.baseaction.Code;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.FDDealImpl;
import com.avst.equipmentcontrol.outside.dealoutinterface.storage.avstss.cache.SSThreadCache;
import com.avst.equipmentcontrol.outside.dealoutinterface.storage.avstss.conf.SSAddDataThread;
import com.avst.equipmentcontrol.outside.dealoutinterface.storage.avstss.req.SaveFileByIidParam;
import com.avst.equipmentcontrol.outside.dealoutinterface.storage.avstss.req.SaveFileByIid_localParam;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.req.GetFlushbonadingBySsidParam;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.v1.service.BaseToOutServiceImpl_qrs;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.vo.GetFlushbonadingBySsidVO;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * avst版的存储服务的处理方法
 * 第一版的avst存储服务是直接关联ftp服务器和ftp客户端直接的通讯，比较的呆板
 */
@Service
public class DealImpl {

    @Autowired
    private BaseToOutServiceImpl_qrs baseToOutServiceImpl_qrs;

    @Autowired
    private Ss_databaseMapper ss_databaseMapper;

    @Autowired
    private Ss_datasaveMapper ss_datasaveMapper;

    @Autowired
    private Ss_saveinfoMapper ss_saveinfoMapper;

    @Autowired
    private FDDealImpl fdDeal;


    public RResult saveFileByIid(SaveFileByIidParam param, RResult result){

        String iid=param.getIid();
        String savessid=param.getSaveinfossid();
        String ssip=param.getIp();
        String fdssid=param.getFdssid();
        String savebasepath=  param.getUploadbasepath();


        GetFlushbonadingBySsidParam getfdparam=new GetFlushbonadingBySsidParam();
        getfdparam.setFlushbonadingEquipmentSsid(fdssid);
        RResult<GetFlushbonadingBySsidVO> result2=baseToOutServiceImpl_qrs.getFlushbonadingBySsid(getfdparam,result);
        if(null!=result2&&result2.getActioncode().equals(Code.SUCCESS.toString())){

            SSAddDataThread ssAddDataThread=new SSAddDataThread(iid,savessid,result2.getData(),savebasepath,
                                                                            ss_databaseMapper,ss_datasaveMapper,fdDeal);
            ssAddDataThread.start();
            //写入缓存
            SSThreadCache.setSSAddDataThread(iid,ssAddDataThread);

            result.changeToTrue(true);

        }else{
            LogUtil.intoLog(4,this.getClass(),"baseToOutServiceImpl_qrs.getFlushbonadingBySsid is error,fdssid:"+fdssid);
        }

        return result;
    };

    public RResult saveFileByIid_local(SaveFileByIid_localParam param, RResult result){

        String iid=param.getIid();
        String savessid=param.getSaveinfossid();
        String sourseRelativePath=param.getSourseRelativePath();
        String savebasepath=  param.getUploadbasepath();

        SSAddDataThread ssAddDataThread=new SSAddDataThread(iid,savessid,sourseRelativePath,savebasepath,
                ss_databaseMapper,ss_datasaveMapper);
        ssAddDataThread.start();
        //写入缓存
        SSThreadCache.setSSAddDataThread(iid,ssAddDataThread);

        String filepath="";
        if(sourseRelativePath.startsWith("/") && savebasepath.endsWith("/")){
            filepath=savebasepath+sourseRelativePath.substring(1);
        }else if(sourseRelativePath.startsWith("/")||savebasepath.endsWith("/")){
            filepath=savebasepath+sourseRelativePath;
        }else{
            filepath=savebasepath+"/"+sourseRelativePath;
        }
        filepath=savebasepath+sourseRelativePath;

        result.changeToTrue(filepath);//把该存储服务的出事路径返回

        return result;
    };

    public List<Ss_dataMessageParam> getSs_databaseList(String iid){

        EntityWrapper entityWrapper=new EntityWrapper();
        entityWrapper.eq("ds.iid",iid);
        List<Ss_dataMessageParam> ss_databaseList=ss_databaseMapper.getSs_databaseByIid(entityWrapper);
        if(null!=ss_databaseList&&ss_databaseList.size() > 0){
            return ss_databaseList;
        }
        return null;
    }


}
