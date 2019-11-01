package com.avst.equipmentcontrol.common.cache;

import com.avst.equipmentcontrol.common.cache.param.Base_ettypeCacheParam;
import com.avst.equipmentcontrol.common.conf.NavType;
import com.avst.equipmentcontrol.common.datasourse.publicsourse.entity.Base_ettype;
import com.avst.equipmentcontrol.common.datasourse.publicsourse.mapper.Base_ettypeMapper;
import com.avst.equipmentcontrol.common.util.SpringUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

//菜单栏缓存
public class NavCache {

    private static List<Base_ettypeCacheParam> base_ettypeCacheParamList;

    public static synchronized List<Base_ettypeCacheParam> getNavCache() {

        if(null == base_ettypeCacheParamList){
            base_ettypeCacheParamList = init();
        }
        return base_ettypeCacheParamList;
    }

    public static synchronized void delNavCache(){
        base_ettypeCacheParamList = null;
    }

    private static synchronized List<Base_ettypeCacheParam> init(){

        Base_ettypeMapper base_ettypeMapper = SpringUtil.getBean(Base_ettypeMapper.class);
        List<Base_ettype> base_ettypes = base_ettypeMapper.selectList(null);

        ArrayList<Base_ettypeCacheParam> list = null;
        if (null != base_ettypes && base_ettypes.size() > 0) {
            list = new ArrayList<>();

            for (Base_ettype base_ettype : base_ettypes) {
                Gson gson = new Gson();
                Base_ettypeCacheParam baseEttypeCacheParam = gson.fromJson(gson.toJson(base_ettype), Base_ettypeCacheParam.class);

                if (baseEttypeCacheParam.getEttypenum().equals(NavType.ASR)) {
                    baseEttypeCacheParam.setUrl("/Asr/getAsrIndex?etypessid=" + baseEttypeCacheParam.getSsid());
                }else if(baseEttypeCacheParam.getEttypenum().equals(NavType.FLUSHBONADING)){
                    baseEttypeCacheParam.setUrl("/Flushbonading/getFlushbonadingIndex?etypessid=" + baseEttypeCacheParam.getSsid());
                }else if(baseEttypeCacheParam.getEttypenum().equals(NavType.POLYGRAPH)){
                    baseEttypeCacheParam.setUrl("/Polygraph/getPolygraphIndex?etypessid=" + baseEttypeCacheParam.getSsid());
                }else if(baseEttypeCacheParam.getEttypenum().equals(NavType.DATA)){
                    baseEttypeCacheParam.setUrl("/Storage/getStorageIndex?etypessid=" + baseEttypeCacheParam.getSsid());
                }else if(baseEttypeCacheParam.getEttypenum().equals(NavType.TTS)){
                    baseEttypeCacheParam.setUrl("/ttsetinfo/getTtsetinfoIndex?etypessid=" + baseEttypeCacheParam.getSsid());
                }

                list.add(baseEttypeCacheParam);
            }

        }

        return list;
    }





}
