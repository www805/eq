package com.avst.equipmentcontrol.outside.dealoutinterface.storage.avstss.conf;

import com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.entity.Ss_database;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.entity.param.Ss_dataMessageParam;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.mapper.Ss_databaseMapper;
import com.avst.equipmentcontrol.common.util.FileUtil;
import com.avst.equipmentcontrol.common.util.LogUtil;
import com.avst.equipmentcontrol.common.util.OpenUtil;
import com.avst.equipmentcontrol.common.util.baseaction.Code;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.common.util.ff.VideoChangeThread;
import com.avst.equipmentcontrol.common.util.properties.PropertiesListenerConfig;
import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.req.UploadFileByPathParam;
import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.vo.UploadFileByPathVO;
import com.avst.equipmentcontrol.outside.dealoutinterface.storage.avstss.cache.SSThreadCache;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 进入时state=0 or state=-1
 *检测数据是否完整的传送到服务器,修改状态state=1，
 */
public class SSChecckDataThread extends  Thread{

    private Ss_databaseMapper ss_databaseMapper;

    private Ss_dataMessageParam ss_dataMessageParam;

    public SSChecckDataThread(Ss_databaseMapper ss_databaseMapper,  Ss_dataMessageParam ss_dataMessageParam) {
        this.ss_databaseMapper = ss_databaseMapper;
        this.ss_dataMessageParam = ss_dataMessageParam;
    }

    public boolean bool=true;

    int notchangenum=0;//上传的文件已经多少次没有增长了，当10次的时候就重新上传
    long videosize=0;//当前检测到的上传文件的大小

    @Override
    public void run() {

        String savebasepath=ss_dataMessageParam.getDatasavebasepath();//这一类文件存储的主路径
        String sorsefilepath=ss_dataMessageParam.getSoursedatapath();
        String iid=ss_dataMessageParam.getIid();
        if(StringUtils.isEmpty(sorsefilepath)||StringUtils.isEmpty(savebasepath)){
            LogUtil.intoLog(this.getClass(),sorsefilepath+":sorsefilepath=====有一个参数为空====savebasepath:"+savebasepath);
            SSThreadCache.delSSChecckDataThread(iid);//关闭这个检测文件是否正常的线程的缓存
            return ;
        }
        long filesize_db=ss_dataMessageParam.getDatasize();//数据库中记录的文件大小
        String filename=sorsefilepath.substring(sorsefilepath.lastIndexOf("/")+1);
        LogUtil.intoLog(this.getClass(),savebasepath+"--savebasepath filename："+filename);

        while(bool){
            if(!bool){
                LogUtil.intoLog(this.getClass(),"SSChecckDataThread 被主动中止--filename："+filename);

                break;
            }

            try {
                //开始检测
                List<String> savefilepathlist=new ArrayList<String>();
                File file=new File(savebasepath);
                savefilepathlist=FileUtil.getAllFiles(file,savefilepathlist,1);
                if(null!=savefilepathlist&&savefilepathlist.size() > 0){
                    int size=savefilepathlist.size();
                    for(int i=size-1;i>=0;i--){//从最后面查起
                        String path=savefilepathlist.get(i);
                        LogUtil.intoLog(this.getClass(),path+":path 路径对比 filename:"+filename);
                        if(path.endsWith(filename)){//当找到指定文件后进行大小比较

                            File savefile=new File(path);
                            long filerealsize=savefile.length();
                            LogUtil.intoLog(this.getClass(),filerealsize+":filerealsize--filename:"+filename+"--filesize_db："+filesize_db);
                            if(filerealsize==filesize_db){//判断文件是否全部上传完毕

                                try {
                                    //去掉名字的特殊字符以前的所有字符
                                    String savename= OpenUtil.getfilename(path);
                                    String specialchars=PropertiesListenerConfig.getProperty("specialchars");
                                    String newsavename=OpenUtil.delSpecialBeforeChar(savename,specialchars);
                                    path= OpenUtil.FileRenameTo(path,newsavename);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                String changetype= PropertiesListenerConfig.getProperty("changetype");
                                if(null==changetype || changetype.trim().equals("")){
                                    LogUtil.intoLog(4,this.getClass(),"转video文件的属性配置参数异常，----changetype:"+changetype);
                                }else{

                                    //执行转码操作
                                    String inputpath=path;
                                    String outputurl=inputpath.substring(0, inputpath.lastIndexOf("."))+"."+changetype;
                                    VideoChangeThread videoChangeThread=new VideoChangeThread(inputpath,outputurl);
                                    videoChangeThread.start();
                                    path=outputurl;
                                }


                                //修改数据库的原始保存文件记录表
                                Gson gson=new Gson();
                                Ss_database ss_database=(Ss_database)gson.fromJson(gson.toJson(ss_dataMessageParam),Ss_database.class);
                                ss_database.setDatasavepath(path);
                                ss_database.setState(1);//文件完成
                                ss_dataMessageParam.setDatasavepath(path);
                                ss_dataMessageParam.setState(1);
                                int ss_databaseupdateById=ss_databaseMapper.updateById(ss_database);
                                LogUtil.intoLog(this.getClass(),ss_databaseupdateById+":ss_databaseupdateById----SSChecckDataThread ss_dataMessageParam.getId()："+ss_dataMessageParam.getId());
                                bool=false;//判断完了就出去了


                            }else{

                                if(notchangenum>=10&&filerealsize==videosize){//如果上传10次没有动的话就重新上传
                                    //需要重新上传这个文件
                                    LogUtil.intoLog(4,this.getClass(),path+"：path---文件需要重新上传----SSChecckDataThread ss_dataMessageParam.getId()："+ss_dataMessageParam.getId());
                                    LogUtil.intoLog(4,this.getClass(),path+"：path,有问题，需要重新处理，这里没有重新上传的接口");

                                }else{
                                    //计算上传文件的大小和变动次数
                                    if(filerealsize==videosize){
                                        notchangenum++;

                                    }else{
                                        //还在上传中
                                        LogUtil.intoLog(1,this.getClass(),path+"：path---文件还在上传中，请等待----SSChecckDataThread ss_dataMessageParam.getId()："+ss_dataMessageParam.getId());
                                    }
                                }
                                videosize=filerealsize;
                            }
                            break;//找到了文件就不需要再找了
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if(!bool){
                LogUtil.intoLog(this.getClass(),"SSChecckDataThread 被主动中止,可能是已经完成了--filename："+filename);
                break;
            }

            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
        LogUtil.intoLog(this.getClass(),"正常结束检测文件是否正常的线程 iid："+iid);
        SSThreadCache.delSSChecckDataThread(iid);//关闭这个检测文件是否正常的线程的缓存


        //进入创建网络地址的线程
        SSCreateDataUrlThread ssThread=new SSCreateDataUrlThread(ss_databaseMapper,ss_dataMessageParam);
        ssThread.start();
        SSThreadCache.setSSCreateDataUrlThread(iid,ssThread);

    }

}
