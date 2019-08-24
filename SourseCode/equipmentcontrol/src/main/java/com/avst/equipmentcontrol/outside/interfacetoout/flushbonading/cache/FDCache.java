package com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.cache;

import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.cache.param.FDCacheParam;

import java.util.*;

/**
 * 嵌入式设备工作缓存
 * fdid 作为key键
 * FDCacheParam 每个嵌入式设备一条数据
 */
public class FDCache {

    private static Map<String, List<FDCacheParam>> fdMap;

    public static synchronized  List<FDCacheParam> getFDList(String fdid){
        if(null!=fdMap&&fdMap.containsKey(fdid)){
            return fdMap.get(fdid);
        }
        return null;
    }


    public static synchronized  FDCacheParam getFDByFDSsid(String fdid,String fdssid){
        List<FDCacheParam> list=getFDList(fdid);
        if(null!=list&&list.size() > 0){
            for(FDCacheParam fd:list){
                if(fdssid.equals(fd.getFdSsid())){
                    return fd;
                }
            }
        }
        return null;
    }
    public static synchronized  boolean setFDList(String fdid,List<FDCacheParam> fdlist){

        if(null==fdMap){
            fdMap=new HashMap<String, List<FDCacheParam>>();
        }
        if(fdMap.containsKey(fdid)){
            fdMap.remove(fdid);
        }
        fdMap.put(fdid,fdlist);
        return true;
    }

    public static synchronized  boolean setFD(String fdid,FDCacheParam fdparam){
        List<FDCacheParam> list=getFDList(fdid);
        if(null!=list){
            if(list.size() > 0){
                int i=0;
                for(FDCacheParam fd:list){
                    if(fdparam.getFdSsid().equals(fd.getFdSsid())){
                        list.remove(i);
                        break;
                    }
                    i++;
                }
            }
        }else{
            list=new ArrayList<FDCacheParam>();
        }
        list.add(fdparam);
        setFDList(fdid,list);
        return true;
    }

    public static synchronized  boolean delFDList(String fdid){

        if(null!=fdMap&&fdMap.containsKey(fdid)){
            fdMap.remove(fdid);
            return true;
        }
        return false;
    }


    /**
     * 结束录像再开始录像的最小间隔时间ms 10秒
     */
    public static int minRecordinterval=1;

    /**
     * 最后一次录像的时间
     * K  fdssid
     * V long类型 ms格式的时间
     */
    private static Map<String ,Long> leastRecordTimeMap=new HashMap<String ,Long>();

    public static long getLeastRecordTime(String fdssid){

        if(null!=leastRecordTimeMap&&leastRecordTimeMap.containsKey(fdssid)){
            return leastRecordTimeMap.get(fdssid);
        }
        return 0;
    }

    public static void setLeastRecordTime(String fdssid,long leastRecordTime){

        if(null!=leastRecordTimeMap&&leastRecordTimeMap.containsKey(fdssid)){
            leastRecordTimeMap.remove(fdssid);
        }
        leastRecordTimeMap.put(fdssid,leastRecordTime);
    }


}
