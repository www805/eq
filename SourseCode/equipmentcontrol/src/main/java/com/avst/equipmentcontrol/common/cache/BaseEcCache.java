package com.avst.equipmentcontrol.common.cache;

import com.avst.equipmentcontrol.common.cache.param.Base_ettypeCacheParam;
import com.avst.equipmentcontrol.common.conf.NavType;
import com.avst.equipmentcontrol.common.datasourse.publicsourse.entity.Base_equipmentinfo;
import com.avst.equipmentcontrol.common.datasourse.publicsourse.entity.Base_ettype;
import com.avst.equipmentcontrol.common.datasourse.publicsourse.mapper.Base_equipmentinfoMapper;
import com.avst.equipmentcontrol.common.datasourse.publicsourse.mapper.Base_ettypeMapper;
import com.avst.equipmentcontrol.common.util.SpringUtil;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

//基础设备缓存
public class BaseEcCache {

    private static List<Base_equipmentinfo> base_ecListCache;

    public static synchronized List<Base_equipmentinfo> getBaseEcCache(String etnum) {

        if(null == base_ecListCache){
            base_ecListCache = init();
        }

        List<Base_equipmentinfo> list = new ArrayList<>();

        if(StringUtils.isNotBlank(etnum)){
            for (Base_equipmentinfo eq : base_ecListCache) {
                if (eq.getEtnum().indexOf(etnum) > -1) {
                    list.add(eq);
                }
            }
        }else{
            list = base_ecListCache;
        }


        return list;
    }

    public static synchronized void delBaseEcCache(){
        base_ecListCache = null;
    }

    private static synchronized List<Base_equipmentinfo> init(){

        Base_equipmentinfoMapper base_equipmentinfoMapper = SpringUtil.getBean(Base_equipmentinfoMapper.class);
        List<Base_equipmentinfo> equipmentinfos = base_equipmentinfoMapper.selectList(null);

        return equipmentinfos;
    }





}
