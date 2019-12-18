package com.avst.equipmentcontrol.outside.dealoutinterface.storage.avstss.conf;

import com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.entity.param.Ss_dataMessageParam;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.mapper.Ss_databaseMapper;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.mapper.Ss_datasaveMapper;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.mapper.Ss_saveinfoMapper;
import com.avst.equipmentcontrol.common.util.LogUtil;
import com.avst.equipmentcontrol.outside.dealoutinterface.storage.avstss.cache.SSThreadCache;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 定时器任务
 * 注意：这种定时器一定要用try包一下，以免内存泄露或者线程异常不能释放
 */
@Component
public class Scheduler2 {

    @Autowired
    private Ss_databaseMapper ss_databaseMapper;


    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    //每隔20秒执行一次
    @Scheduled(fixedRate = 20000)
    public void checkDateSave() {
        LogUtil.intoLog(this.getClass(),"checkDateSave 定时任务执行时间：" + dateFormat.format(new Date()));

        EntityWrapper entityWrapper=new EntityWrapper();
        entityWrapper.ne("db.state",2);//查询未完成的所有存储任务
        List<Ss_dataMessageParam> datalist= ss_databaseMapper.getSs_databaseByIid(entityWrapper);
        //0和-1 还没有检测到文件上传成功
        //1和-2 还没有生产对外开放的地址
        if(null==datalist||datalist.size() < 1){
            LogUtil.intoLog(this.getClass(),datalist+":datalist checkDateSave没有查到需要处理的数据 0，-1,1，-2");
            return ;
        }
        for(Ss_dataMessageParam dm:datalist){
            String iid=dm.getIid();
            if(0==dm.getState().intValue()||-1==dm.getState().intValue()){//0，-1
                if(null==SSThreadCache.getSSChecckDataThread(iid)){//不重复添加
                    SSChecckDataThread ssThread=new SSChecckDataThread(ss_databaseMapper,dm);
                    ssThread.start();
                    SSThreadCache.setSSChecckDataThread(iid,ssThread);
                }
            }else{//1，-2
                if(null==SSThreadCache.getSSCreateDataUrlThread(iid)){//不重复添加
                    SSCreateDataUrlThread ssThread=new SSCreateDataUrlThread(ss_databaseMapper,dm);
                    ssThread.start();
                    SSThreadCache.setSSCreateDataUrlThread(iid,ssThread);
                }
            }
        }








    }



}
