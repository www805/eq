package com.avst.equipmentcontrol.common.cache;

import com.avst.equipmentcontrol.common.cache.param.Base_ettypeCacheParam;
import com.avst.equipmentcontrol.common.conf.NavType;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.asr.mapper.Asr_etinfoMapper;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.flushbonading.mapper.Flushbonading_etinfoMapper;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.polygraph.mapper.Polygraph_etinfoMapper;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.mapper.Ss_saveinfoMapper;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.tts.mapper.Tts_etinfoMapper;
import com.avst.equipmentcontrol.common.datasourse.publicsourse.entity.Base_equipmentinfo;
import com.avst.equipmentcontrol.common.datasourse.publicsourse.entity.Base_ettype;
import com.avst.equipmentcontrol.common.datasourse.publicsourse.mapper.Base_equipmentinfoMapper;
import com.avst.equipmentcontrol.common.datasourse.publicsourse.mapper.Base_ettypeMapper;
import com.avst.equipmentcontrol.common.util.SpringUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//基础设备缓存
public class BaseEcCache {

    private static List<Base_equipmentinfo> base_ecListCache;

    public static synchronized List<Base_equipmentinfo> getBaseEcCache(String etip) {

        if (null == base_ecListCache || base_ecListCache.size() == 0) {
            base_ecListCache = init();
        }

        List<Base_equipmentinfo> list = new ArrayList<>();

        if(StringUtils.isNotBlank(etip)){
            for (Base_equipmentinfo eq : base_ecListCache) {
                if (eq.getEtip().indexOf(etip) > -1) {
                    list.add(eq);
                }
            }
        }else{
            list = base_ecListCache;
        }


        return list;
    }

    public static synchronized void addBaseEcCache(Base_equipmentinfo equipmentinfo){

        if (null == base_ecListCache || base_ecListCache.size() == 0) {
            base_ecListCache = init();
        }

        Boolean isadd = true;

        Iterator<Base_equipmentinfo> iterator = base_ecListCache.iterator();
        while (iterator.hasNext()) {

            synchronized (base_ecListCache) {
                Base_equipmentinfo next = iterator.next();

                if(equipmentinfo.getEtnum().equals(next.getEtnum()) || equipmentinfo.getEtip().equals(next.getEtip())){
                    isadd = false;
                    break;
                }
            }
        }

        if(isadd){
            base_ecListCache.add(equipmentinfo);
        }


    }

    public static synchronized void delBaseEcCache(){
        base_ecListCache.clear();
        base_ecListCache = null;
    }

    private static synchronized List<Base_equipmentinfo> init(){

        Base_equipmentinfoMapper base_equipmentinfoMapper = SpringUtil.getBean(Base_equipmentinfoMapper.class);
        List<Base_equipmentinfo> equipmentinfos = base_equipmentinfoMapper.selectList(null);

        Asr_etinfoMapper asr_etinfoMapper = SpringUtil.getBean(Asr_etinfoMapper.class);
        Flushbonading_etinfoMapper flushbonading_etinfoMapper = SpringUtil.getBean(Flushbonading_etinfoMapper.class);
        Polygraph_etinfoMapper polygraph_etinfoMapper = SpringUtil.getBean(Polygraph_etinfoMapper.class);
        Ss_saveinfoMapper ss_saveinfoMapper = SpringUtil.getBean(Ss_saveinfoMapper.class);
        Tts_etinfoMapper tts_etinfoMapper = SpringUtil.getBean(Tts_etinfoMapper.class);


        EntityWrapper<Base_equipmentinfo> ew = new EntityWrapper<>();

        List<Base_equipmentinfo> a_equipmentinfos = asr_etinfoMapper.getBase_equipmentinfo(ew);
        List<Base_equipmentinfo> f_equipmentinfos = flushbonading_etinfoMapper.getBase_equipmentinfo(ew);
        List<Base_equipmentinfo> p_equipmentinfos = polygraph_etinfoMapper.getBase_equipmentinfo(ew);
        List<Base_equipmentinfo> s_equipmentinfos = ss_saveinfoMapper.getBase_equipmentinfo(ew);
        List<Base_equipmentinfo> t_equipmentinfos = tts_etinfoMapper.getBase_equipmentinfo(ew);

        Boolean isBool = false;//如果等true将不再进入循环判断删除
        Iterator<Base_equipmentinfo> iterator = equipmentinfos.iterator();
        //先获取出基础表，再获取其他设备表，逐条判断如果已经使用的就在基础表里删除
        while (iterator.hasNext()) {

            synchronized (equipmentinfos){
                Base_equipmentinfo next = iterator.next();

                if(!isBool){
                    for (Base_equipmentinfo equipmentinfo : a_equipmentinfos) {
                        if(equipmentinfo.getEtnum().equals(next.getEtnum()) || equipmentinfo.getEtip().equals(next.getEtip())){
                            iterator.remove();
                            isBool = true;//被删除了，就不进入这个删除了
                            break;
                        }
                    }
                }

                if(!isBool){
                    for (Base_equipmentinfo equipmentinfo : f_equipmentinfos) {
                        if(equipmentinfo.getEtnum().equals(next.getEtnum()) || equipmentinfo.getEtip().equals(next.getEtip())){
                            iterator.remove();
                            isBool = true;//被删除了，就不进入这个删除了
                            break;
                        }
                    }
                }

                if(!isBool){
                    for (Base_equipmentinfo equipmentinfo : p_equipmentinfos) {
                        if(equipmentinfo.getEtnum().equals(next.getEtnum()) || equipmentinfo.getEtip().equals(next.getEtip())){
                            iterator.remove();
                            isBool = true;//被删除了，就不进入这个删除了
                            break;
                        }
                    }
                }

                if(!isBool){
                    for (Base_equipmentinfo equipmentinfo : s_equipmentinfos) {
                        if(equipmentinfo.getEtnum().equals(next.getEtnum()) || equipmentinfo.getEtip().equals(next.getEtip())){
                            iterator.remove();
                            isBool = true;//被删除了，就不进入这个删除了
                            break;
                        }
                    }
                }

                if(!isBool){
                    for (Base_equipmentinfo equipmentinfo : t_equipmentinfos) {
                        if(equipmentinfo.getEtnum().equals(next.getEtnum()) || equipmentinfo.getEtip().equals(next.getEtip())){
                            iterator.remove();
                            isBool = true;//被删除了，就不进入这个删除了
                            break;
                        }
                    }
                }

                isBool = false;
            }
        }

        return equipmentinfos;
    }





}
