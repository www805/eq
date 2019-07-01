package com.avst.equipmentcontrol.outside.dealoutinterface.storage.avstss.conf;

import com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.entity.Ss_database;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.entity.Ss_datasave;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.entity.param.Ss_dataMessageParam;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.mapper.Ss_databaseMapper;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.mapper.Ss_datasaveMapper;
import com.avst.equipmentcontrol.common.util.LogUtil;
import com.avst.equipmentcontrol.common.util.OpenUtil;
import com.avst.equipmentcontrol.common.util.baseaction.Code;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.FDDealImpl;
import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.req.GetETRecordByIidParam;
import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.req.UploadFileByPathParam;
import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.vo.GetETRecordByIidVO;
import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.vo.UploadFileByPathVO;
import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.xmljsonobject.param.File;
import com.avst.equipmentcontrol.outside.dealoutinterface.storage.avstss.cache.SSThreadCache;
import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.vo.GetFlushbonadingBySsidVO;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * 传入iid，准备写入数据库记录数据state=0
 */
public class SSAddDataThread extends  Thread{

    private String iid;

    private String saveinfossid;

    private GetFlushbonadingBySsidVO getFlushbonadingBySsidVO;

    private String savebasepath;//存储服务的本地位置

    private String sourseRelativePath;//源文件的相对路径，相对存储服务器的base路径

    private Ss_databaseMapper ss_databaseMapper;

    private Ss_datasaveMapper ss_datasaveMapper;

    private FDDealImpl fdDeal;

    public boolean bool=true;

    private int addtype=1;//1设备ftp存储，2本地文件存储

    public SSAddDataThread(String iid, String saveinfossid, GetFlushbonadingBySsidVO getFlushbonadingBySsidVO,String savebasepath,
                           Ss_databaseMapper ss_databaseMapper,Ss_datasaveMapper ss_datasaveMapper,FDDealImpl fdDeal) {
        this.iid = iid;
        this.saveinfossid = saveinfossid;
        this.ss_databaseMapper = ss_databaseMapper;
        this.ss_datasaveMapper = ss_datasaveMapper;
        this.fdDeal=fdDeal;
        this.getFlushbonadingBySsidVO=getFlushbonadingBySsidVO;
        this.savebasepath=savebasepath;
    }

    public SSAddDataThread(String iid, String saveinfossid, String sourseRelativePath,String savebasepath,
                           Ss_databaseMapper ss_databaseMapper,Ss_datasaveMapper ss_datasaveMapper) {
        this.iid = iid;
        this.saveinfossid = saveinfossid;
        this.ss_databaseMapper = ss_databaseMapper;
        this.ss_datasaveMapper = ss_datasaveMapper;
        this.savebasepath=savebasepath;
        this.sourseRelativePath=sourseRelativePath;
        this.addtype=2;
    }

    @Override
    public void run() {

        //iid非空，唯一判断
        EntityWrapper<Ss_datasave> ew=new EntityWrapper<Ss_datasave>();
        ew.eq("iid",iid);
        List<Ss_datasave> datalist=ss_datasaveMapper.selectList(ew);
        if(null!=datalist&&datalist.size() > 0){
            LogUtil.intoLog(this.getClass(),iid+":iid不是唯一的，已存在数据库，外部没有判断吗？？？？");
            SSThreadCache.delSSAddDataThread(iid);//删除这个等待新增
            return ;
        }

        if(addtype==1){
            run_ftp();
        }else{
            run_local();
        }

    }

    /**
     * 本地文件的保存
     */
    private void run_local(){

        String filepath="";
        String filename="";
        String filetype="";
        if(StringUtils.isNotEmpty(sourseRelativePath)){
            if(sourseRelativePath.startsWith("/")||savebasepath.endsWith("/")){
                filepath=savebasepath+sourseRelativePath;
            }else{
                filepath=savebasepath+"/"+sourseRelativePath;
            }
            if(sourseRelativePath.indexOf("/") > -1){

                filename=sourseRelativePath.substring(sourseRelativePath.lastIndexOf("/")+1);
            }else{
                filename=sourseRelativePath.substring(sourseRelativePath.lastIndexOf("\\")+1);
            }

            if(filename.indexOf(".") <  0){
                LogUtil.intoLog(this.getClass(),"该路径不是一个文件--filepath:"+filepath);
                return ;
            };
            filetype=filename.split("\\.")[1];
            filename=filename.split("\\.")[0];

        }else{
            LogUtil.intoLog(this.getClass(),sourseRelativePath+"：sourseRelativePath，该文件的存储地址有问题savebasepath，"+savebasepath);
            return ;
        }
        LogUtil.intoLog(this.getClass(),"该文件的原始存储位置--filepath:"+filepath);

        //开始新增数据库

        //先新增数据存储记录表
        String ss_datasavessid= OpenUtil.getUUID_32();
        Ss_datasave ss_datasave=new Ss_datasave();
        ss_datasave.setIid(iid);
        ss_datasave.setSaveinfossid(saveinfossid);
        ss_datasave.setSsid(ss_datasavessid);
        ss_datasave.setDatasavebasepath(savebasepath);
        int datasaveinsert=ss_datasaveMapper.insert(ss_datasave);
        if(datasaveinsert > -1){

            //再新增数据基表

            if(null==filepath||filepath.trim().equals("")){
                LogUtil.intoLog(this.getClass(),"文件路径或者文件名为空，直接退出，filepath："+filepath);
                return ;
            }

            Ss_database ss_database=new Ss_database();
            ss_database.setDatasavessid(ss_datasavessid);
            ss_database.setDatatype(filetype);
            //ss_database.setDatasize(datasize);//暂时不用给，可能还在增长中
            ss_database.setState(1);//新增状态为1，因为本地文件保存不需要检测大小
            ss_database.setSsid(OpenUtil.getUUID_32());
            ss_database.setSoursedatapath(filepath);
            ss_database.setDatasavepath(filepath);
            ss_database.setFilename(filename);
            int databaseinsert=ss_databaseMapper.insert(ss_database);
            if(databaseinsert > -1){
                LogUtil.intoLog(this.getClass(),iid+":iid  数据库新增成功，");
            }else{
                LogUtil.intoLog(this.getClass(),iid+":iid  ss_datasaveMapper.insert is error datasaveinsert:"+datasaveinsert);
            }

        }else{
            LogUtil.intoLog(this.getClass(),iid+":iid  ss_datasaveMapper.insert is error datasaveinsert:"+datasaveinsert);
        }

        SSThreadCache.delSSAddDataThread(iid);//删除这个等待新增

        LogUtil.intoLog(this.getClass(),iid+":iid----这个新增线程出来了");

        //进入检查文件地址和大小的线程
        EntityWrapper ew=new EntityWrapper();
        ew.eq("db.state",1);//查询状态1，新增成功的数据
        ew.eq("ds.iid",iid);
        List<Ss_dataMessageParam> ssdatalist= ss_databaseMapper.getSs_databaseByIid(ew);
        //0和-1 还没有检测到文件上传成功
        //1和-2 还没有生产对外开放的地址
        if(null==ssdatalist||ssdatalist.size() < 1){
            LogUtil.intoLog(this.getClass(),ssdatalist+":ssdatalist SSAddDataThread 没有查到需要处理的数据 1");
            return ;
        }

        for(Ss_dataMessageParam dm:ssdatalist){
            String iid=dm.getIid();
            if(1==dm.getState().intValue()||-2==dm.getState().intValue()){//1

                //进入创建网络地址的线程
                SSCreateDataUrlThread ssThread=new SSCreateDataUrlThread(ss_databaseMapper,dm);
                ssThread.start();
                SSThreadCache.setSSCreateDataUrlThread(iid,ssThread);

            }
        }

    }

    /**
     * ftp的文件的保存
     */
    private void run_ftp(){

        String fdbasepath=getFlushbonadingBySsidVO.getUploadbasepath();
        if(StringUtils.isNotEmpty(fdbasepath)){
            if(fdbasepath.startsWith("/")||savebasepath.endsWith("/")){
                fdbasepath=savebasepath+fdbasepath;
            }else{
                fdbasepath=savebasepath+"/"+fdbasepath;
            }
        }else{
            fdbasepath=savebasepath;
        }
        LogUtil.intoLog(this.getClass(),"该文件的原始存储位置--fdbasepath:"+fdbasepath);

        while(bool){

            if(!bool){
                LogUtil.intoLog(this.getClass(),bool+"---中断线程SSAddDataThread");
                break;
            }

            //只会在结束录制的时候去请求嵌入式设备，得到基本数据才是准确的
            RResult<GetETRecordByIidVO> result=new RResult<GetETRecordByIidVO>();
            GetETRecordByIidParam param=new GetETRecordByIidParam();
            param.setRec_id(iid);
            param.setIp(getFlushbonadingBySsidVO.getEtip());
            param.setUser(getFlushbonadingBySsidVO.getUser());
            param.setPort(getFlushbonadingBySsidVO.getPort());
            param.setPasswd(getFlushbonadingBySsidVO.getPasswd());
            result=fdDeal.getETRecordByIid(param,result);
            if(null!=result&&result.getActioncode().equals(Code.SUCCESS.toString())){
                GetETRecordByIidVO getETRecordByIidVO=result.getData();
                //开始新增数据库
                if(null!=getETRecordByIidVO&&null!=getETRecordByIidVO.getFileList()&&getETRecordByIidVO.getFileList().size() > 0){

                    //先新增数据存储记录表
                    String ss_datasavessid= OpenUtil.getUUID_32();
                    Ss_datasave ss_datasave=new Ss_datasave();
                    ss_datasave.setIid(iid);
                    ss_datasave.setSaveinfossid(saveinfossid);
                    ss_datasave.setSsid(ss_datasavessid);
                    ss_datasave.setDatasavebasepath(fdbasepath);
                    int datasaveinsert=ss_datasaveMapper.insert(ss_datasave);
                    if(datasaveinsert > -1){

                        //再新增数据基表
                        int boolinsert=0;
                        List<File> fileList=getETRecordByIidVO.getFileList();
                        for(File file:fileList){

                            String path=file.getPath();
                            String filename=file.getName();
                            if(null==path||path.trim().equals("")||
                                    null==filename||filename.trim().equals("")){
                                LogUtil.intoLog(this.getClass(),"文件路径或者文件名为空，直接退出，下次再查--file.getName()："+file.getName());
                                break;
                            }else{
                                path=path.trim();
                                filename=filename.trim();
                            }
                            //主动请求设备把文件送过来
                            UploadFileByPathParam ufparam=new UploadFileByPathParam();
                            ufparam.setRecordpath(path);
                            ufparam.setIp(getFlushbonadingBySsidVO.getEtip());
                            ufparam.setUser(getFlushbonadingBySsidVO.getUser());
                            ufparam.setPort(getFlushbonadingBySsidVO.getPort());
                            ufparam.setPasswd(getFlushbonadingBySsidVO.getPasswd());
                            RResult<UploadFileByPathVO > ufresult=new RResult<>();
                            ufresult=fdDeal.uploadFileByPath(ufparam,ufresult);
                            if(null==ufresult||!ufresult.getActioncode().equals(Code.SUCCESS.toString())){
                                //请求文件上传失败，直接退出去
                                LogUtil.intoLog(this.getClass(),"请求文件上传失败，直接退出去 ");
                                break;
                            }

                            Ss_database ss_database=new Ss_database();
                            ss_database.setDatasavessid(ss_datasavessid);
                            ss_database.setDatatype("video");
                            long datasize=Long.valueOf(file.getFsize());
                            ss_database.setDatasize(datasize);
                            ss_database.setState(0);
                            ss_database.setStarttime(Long.valueOf(file.getStime()));
                            ss_database.setEndtime(Long.valueOf(file.getEtime()));
                            ss_database.setSsid(OpenUtil.getUUID_32());
                            ss_database.setSoursedatapath(path);
                            ss_database.setFilename(filename);
                            int databaseinsert=ss_databaseMapper.insert(ss_database);
                            if(databaseinsert > -1){
                                boolinsert++;
                            }else{
                                LogUtil.intoLog(this.getClass(),iid+":iid  ss_datasaveMapper.insert is error datasaveinsert:"+datasaveinsert);
                            }
                        }

                        if(fileList.size()==boolinsert){//说明新增成功，可以跳出
                            bool=false;
                            LogUtil.intoLog(this.getClass(),iid+":iid  数据库新增成功，可以跳出循环");
                            break;
                        }
                    }else{
                        LogUtil.intoLog(this.getClass(),iid+":iid  ss_datasaveMapper.insert is error datasaveinsert:"+datasaveinsert);
                    }
                }
            }else{
                LogUtil.intoLog(this.getClass(),"fdDeal.getETRecordByIid 从设备中获取录像文件信息失败--等待再次请求 iid:"+iid);
            }

            if(!bool){
                LogUtil.intoLog(this.getClass(),bool+"---中断线程SSAddDataThread");
                break;
            }

            try {
                Thread.sleep(10*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        SSThreadCache.delSSAddDataThread(iid);//删除这个等待新增

        LogUtil.intoLog(this.getClass(),iid+":iid----这个新增线程出来了");

        //进入检查文件地址和大小的线程
        EntityWrapper entityWrapper=new EntityWrapper();
        entityWrapper.eq("db.state",0);//查询状态0，新增成功的数据
        entityWrapper.eq("ds.iid",iid);
        List<Ss_dataMessageParam> ssdatalist= ss_databaseMapper.getSs_databaseByIid(entityWrapper);
        //0和-1 还没有检测到文件上传成功
        //1和-2 还没有生产对外开放的地址
        if(null==ssdatalist||ssdatalist.size() < 1){
            LogUtil.intoLog(this.getClass(),ssdatalist+":ssdatalist SSAddDataThread 没有查到需要处理的数据 0");
            return ;
        }
        for(Ss_dataMessageParam dm:ssdatalist){
            String iid=dm.getIid();
            if(0==dm.getState().intValue()||-1==dm.getState().intValue()){//0
                SSChecckDataThread ssThread=new SSChecckDataThread(ss_databaseMapper,dm);
                ssThread.start();
                SSThreadCache.setSSChecckDataThread(iid,ssThread);

            }
        }
    }
}
