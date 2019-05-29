package com.avst.equipmentcontrol.outside.dealoutinterface.storage.avstss.conf;

import com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.entity.Ss_database;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.entity.param.Ss_dataMessageParam;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.mapper.Ss_databaseMapper;
import com.avst.equipmentcontrol.common.util.OpenUtil;
import com.avst.equipmentcontrol.common.util.properties.PropertiesListenerConfig;
import com.avst.equipmentcontrol.outside.dealoutinterface.storage.avstss.cache.SSThreadCache;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.apache.commons.lang.StringUtils;

/**
 * 进入时state=1 or state=-2
 *用于服务器文件创建对外开放的请求地址，并且生成请求地址state=2
 */
public class SSCreateDataUrlThread extends  Thread{

    private Ss_databaseMapper ss_databaseMapper;

    private Ss_dataMessageParam ss_dataMessageParam;

    public SSCreateDataUrlThread(Ss_databaseMapper ss_databaseMapper, Ss_dataMessageParam ss_dataMessageParam) {
        this.ss_databaseMapper = ss_databaseMapper;
        this.ss_dataMessageParam = ss_dataMessageParam;
    }

    public boolean bool=true;

    @Override
    public void run() {

        System.out.println("SSCreateDataUrlThread is coming "+ss_dataMessageParam.getIid());

        String filename=ss_dataMessageParam.getFilename();
        String savepath=ss_dataMessageParam.getDatasavepath();
        String iid=ss_dataMessageParam.getIid();
        if(StringUtils.isEmpty(filename)||StringUtils.isEmpty(savepath)){
            System.out.println(filename+":filename=====有一个参数为空====savepath:"+savepath);
            SSThreadCache.delSSCreateDataUrlThread(iid);//关闭这个生成网络地址的线程的缓存
            return ;
        }

        while (bool){

            if(!bool){
                System.out.println("SSCreateDataUrlThread 被主动中止--filename："+filename);
                break;
            }

            try {
                //建立对外开放的请求地址(给个NGINX，可以配置网络资源请求地址)
                //http 最简单的
                String staticpath= PropertiesListenerConfig.getProperty("staticpath");
                String httpbasestaticpath=PropertiesListenerConfig.getProperty("httpbasestaticpath");
                String uploadparh=httpbasestaticpath+OpenUtil.strMinusBasePath(staticpath,savepath);//下载路径
                //修改数据库的原始保存文件记录表
                Gson gson=new Gson();
                Ss_database ss_database=(Ss_database)gson.fromJson(gson.toJson(ss_dataMessageParam),Ss_database.class);
                ss_database.setHttpurl(uploadparh);
                ss_database.setDefaulturl("http");//暂时只提供http请求的网络地址
                ss_database.setState(2);//文件网络请求路径完成
                int ss_databaseupdateById=ss_databaseMapper.updateById(ss_database);
                System.out.println(ss_databaseupdateById+":ss_databaseupdateById----SSCreateDataUrlThread ss_dataMessageParam.getId()："+ss_dataMessageParam.getId());
                if(ss_databaseupdateById> -1){
                    //完了就出去了
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if(!bool){
                System.out.println("SSCreateDataUrlThread 被主动中止--filename："+filename);
                break;
            }

            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("正常结束生成网络地址的线程 iid："+iid);
        SSThreadCache.delSSCreateDataUrlThread(iid);//关闭这个生成网络地址的线程的缓存

    }

}
