package com.avst.equipmentcontrol.outside.dealoutinterface.asr.cache;

import com.avst.equipmentcontrol.common.util.LogUtil;
import com.avst.equipmentcontrol.outside.dealoutinterface.asr.cache.param.AsrTxtParam;
import com.avst.equipmentcontrol.outside.dealoutinterface.asr.cache.param.AsrTxtParam_avst;
import com.avst.equipmentcontrol.outside.interfacetoout.asr.cache.AsrCache_toout;
import com.avst.equipmentcontrol.outside.interfacetoout.asr.cache.param.AsrTxtParam_toout;
import com.avst.equipmentcontrol.common.conf.ASRType;

import java.util.*;

/**
 * 所有版本的asr的缓存都是公用的，只是有一套自己的get/set方法
 * 关于第三方语音识别的缓存
 * 这里的缓存直接关联数据库语音服务的id（不是原始设备的ssid，是语音服务的ssid）
 * 最外层 map 每种语音识别服务的识别结果
 * AsrTxtParam 这一次语音识别的结果集，一次语音识别有很多句
 * List<T> T每句话的识别结果
 * (层级关系：语音服务器ssid、本次识别唯一码asrid、每一句的识别结果starttime)
 */
public class AsrCache {

    /**
     *  key 语音服务器的ssid，一个语音服务器一个id
     */
    private static Map<String, List<AsrTxtParam>> asrTxtMap=null;


    /**
     * 获取这个语言服务器的所有任务
     * @param asrEquipmentssid
     * @return
     */
    public synchronized static List<AsrTxtParam> getAsrListByEquipmentssid(String asrEquipmentssid){

        if(null!=asrTxtMap&&asrTxtMap.containsKey(asrEquipmentssid)){
            List<AsrTxtParam> list=asrTxtMap.get(asrEquipmentssid);
            if(null!=list&&list.size() > 0){
                return list;
            }
        }
        return null;
    }

    /**
     * 获取这个语言服务器的某一次的任务集合
     * @param asrEquipmentssid 语音服务的ssid
     * @param asrid 本次语音识别的ssid（这一次识别的唯一码）
     * @return
     */
    public synchronized static List getAsrTxtListByASRssid(String asrEquipmentssid,String asrid){

        List<AsrTxtParam> list = getAsrListByEquipmentssid(asrEquipmentssid);
        if(null!=list&&list.size() > 0){
            for(AsrTxtParam asr:list){
                if(asr.getAsrid().equals(asrid)){
                    return asr.getAsrlist();
                }
            }
        }
        return null;
    }



    /**
     * 获取这个语言服务器的某一次的任务集合的最后一句
     * @param asrEquipmentssid 语音服务的ssid
     * @param asrid 本次语音识别的ssid（这一次识别的唯一码）
     * @return
     */
    public synchronized static Object getAsrTxtLastOneByASRssid(String asrEquipmentssid,String asrid){

        List asrlist=getAsrTxtListByASRssid(asrEquipmentssid,asrid);
        if(null!=asrlist&&asrlist.size() > 0){
            return asrlist.get(asrlist.size()-1);
        }
        return null;
    }

    /**
     * avst asr获取这个语言服务器的某一次的任务集合的某一句话
     * 每一个语音服务获取某一句话这个get方法都有一个人自己独享的方法（特别注意）
     * @param asrEquipmentssid 语音服务的ssid
     * @param asrid 本次语音识别的id（这一次识别的唯一码）
     * @param time 这句话识别的开始时间（不管识别几次，取它识别这句话最开始的时间）
     * @return
     */
    public synchronized static AsrTxtParam_avst getAsrTxtOneByASRssid(String asrEquipmentssid,String asrid,String time){

        List<AsrTxtParam_avst> asrlist=getAsrTxtListByASRssid(asrEquipmentssid,asrid);
        if(null!=asrlist&&asrlist.size() > 0){
            for(AsrTxtParam_avst asr:asrlist){
                if(asr.getTime().equals(time)){
                    return asr;
                }
            }
        }
        return null;
    }



    /**
     * 往缓存里面插入数据
     * 插入的是这个语音设备当前的所有识别任务
     * @param asrEquipmentssid 本次语音识别的ssid
     * @param param
     * @return
     */
    public synchronized static boolean setAsrByEquipmentssid(String asrEquipmentssid, AsrTxtParam param){

        if(null==asrTxtMap){
            asrTxtMap=new HashMap<String, List<AsrTxtParam>>();
        }

        if(asrTxtMap.containsKey(asrEquipmentssid)){
            List<AsrTxtParam> list=asrTxtMap.get(asrEquipmentssid);
            if(null==list){
                list=new ArrayList<AsrTxtParam>();
            }else{
                if(list.size() > 0){
                    int i=0;
                    for(AsrTxtParam asrTxtParam:list){
                        if(asrTxtParam.getAsrid().equals(param.getAsrid())){
                            list.remove(i);
                            break;
                        }
                        i++;
                    }
                }
            }
            list.add(param);
            asrTxtMap.put(asrEquipmentssid,list);
        }else{
            List<AsrTxtParam> list=new ArrayList<AsrTxtParam>();
            list.add(param);
            asrTxtMap.put(asrEquipmentssid,list);
        }
        return false;
    }




    /**
     * 更新 avst asr语音识别服务服务器的某一次识别的某一句的结果
     * 每一个asr服务器有自己的一个专有的set方法
     * @param asrEquipmentssid
     * @param asrid
     * @param param
     * @return
     */
    public synchronized static boolean setAsrByASRssid(String asrEquipmentssid, String asrid, AsrTxtParam_avst param){

        long asrtime=(new Date()).getTime();//本句识别的本地时间

        try {
            if(null==asrTxtMap){
                asrTxtMap=new HashMap<String, List<AsrTxtParam>>();
            }

            if(asrTxtMap.containsKey(asrEquipmentssid)){
                List<AsrTxtParam> list=asrTxtMap.get(asrEquipmentssid);//这个语言服务的所有正在识别的任务
                AsrTxtParam asrTxtParam=new AsrTxtParam();
                if(null==list){
                    list=new ArrayList<AsrTxtParam>();
                    asrTxtParam.setAsrtype(ASRType.AVST);//这里需要插入语音服务器类型
                    asrTxtParam.setAsrEquipmentssid(asrEquipmentssid);
                    asrTxtParam.setAsrid(asrid);
                }else{
                    if(list.size() > 0){
                        for(AsrTxtParam a:list){
                            if(a.getAsrid().equals(asrid)){
                                //if(null!=a.getAsrid()&&a.getAsrid().equals(asrid)){//有问题，要调试
                                asrTxtParam=a;
                                break;
                            }
                        }
                    }
                }
                 List<AsrTxtParam_avst> asrlist=asrTxtParam.getAsrlist();//某一次识别任务
                if(null==asrlist){
                    asrlist=new ArrayList<AsrTxtParam_avst>();
                }else{
                    if(asrlist.size() > 0 ){
                        int i=0;
                        for(AsrTxtParam_avst asr:asrlist){
                            if(asr.getTime().equals(param.getTime())){//判断这是那一句，去掉有更新的旧语句
                                asrlist.remove(i);
                                break;
                            }
                            i++;
                        }
                    }
                }
                asrlist.add(param);//如果没有直接插入
                asrTxtParam.setAsrlist(asrlist);
                asrTxtParam.setAsrid(asrid);
                setAsrByEquipmentssid(asrEquipmentssid,asrTxtParam);

            }else{
                List<AsrTxtParam> list=new ArrayList<AsrTxtParam>();
                AsrTxtParam<AsrTxtParam_avst> asrTxtParam=new AsrTxtParam<AsrTxtParam_avst>();
                List<AsrTxtParam_avst> asrTxtParam_avsts =new ArrayList<AsrTxtParam_avst>();
                asrTxtParam_avsts.add(param);
                asrTxtParam.setAsrlist(asrTxtParam_avsts);
                asrTxtParam.setAsrEquipmentssid(asrEquipmentssid);
                asrTxtParam.setAsrtype(ASRType.AVST);
                asrTxtParam.setAsrid(asrid);
                list.add(asrTxtParam);
                asrTxtMap.put(asrEquipmentssid,list);
            }

            //修改对外缓存
            String starttime=param.getTime();//这一句话的开始识别时间
            AsrTxtParam_toout asr_out=AsrCache_toout.getAsrTxtByStartTime(asrid,starttime);
            int asrsort=1;
            //LogUtil.intoLog(AsrCache.class,asr_out==null?null:(asr_out.getAsrnum()+"--原来的out--"+asr_out.getTxt()));
            if(null==asr_out){
                asr_out=new AsrTxtParam_toout();
                asrsort=AsrCache_toout.getAsrTxtCount(asrid)+1;//只有新增的句子才会在原有的基础上加1
                asr_out.setAsrsort(asrsort);//本次语音识别这是识别的第几句
            }
            asr_out.setAsrtime(asrtime+"");//返回本句识别的时间
            asr_out.setStarttime(starttime);
            asr_out.setTxt(param.getMsg());
            AsrCache_toout.setAsrTxtLastOne(asrid,asr_out);

            //AsrTxtParam_toout asr_out_new=AsrCache_toout.getAsrTxtByStartTime(asrid,starttime);
            //LogUtil.intoLog(AsrCache.class,asr_out_new==null?null:(asr_out_new.getAsrnum()+"--现在的out--"+asr_out_new.getTxt()));

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }



    /**
     * 删除识别结果集缓存，一般用于识别结束后
     * 删除某一个asr服务器的所有识别任务（不推荐）
     * @param asrEquipmentssid
     * @return
     */
    public synchronized static boolean delAsrTxtByEquipmentssid(String asrEquipmentssid){
        if(null!=asrTxtMap&&asrTxtMap.containsKey(asrEquipmentssid)){
            asrTxtMap.remove(asrEquipmentssid);
            return true;
        }
        return false;
    }

    /**
     * 删除识别结果集缓存，一般用于识别结束后
     * 删除某一个asr语音服务的某一次识别任务
     * @param asrEquipmentssid
     * @return
     */
    public synchronized static boolean delAsrTxtByEquipmentssid(String asrEquipmentssid,String asrid){
        if(null!=asrTxtMap&&asrTxtMap.containsKey(asrEquipmentssid)){

            List<AsrTxtParam> list= asrTxtMap.get(asrEquipmentssid);
            if(null!=list&&list.size() > 0){
                int i=0;
                for(AsrTxtParam a:list){
                    if(a.getAsrid().equals(asrid)){
                        list.remove(i);
                        return true;
                    }
                    i++;
                }
            }
        }
        return false;
    }


    /**
     * 删除识别结果集缓存，一般用于识别结束后
     * 删除某一个asr语音服务的某一次识别任务
     * @param asrid
     * @return
     */
    public synchronized static boolean delAsrTxtByASRSsid(String asrid){

        String asrEquipmentssid=getAsrServerssidByAsrid(asrid);//通过asrssid找到asrEquipmentssid

        if(null==asrEquipmentssid){
            LogUtil.intoLog(AsrCache.class,"getAsrEquipmentssidByAsrssid(asrid) is null ,请检查asrid获取设备ssid的逻辑");
            return false;
        }
        return delAsrTxtByEquipmentssid(asrEquipmentssid,asrid);
    }



    //本次语音识别唯一码asrid对应语音识别ssid
    //key asrid
    private static Map<String ,String > serverssidToIdMap=null;

    public synchronized static String getAsrServerssidByAsrid(String asrid){
        if(null!=serverssidToIdMap&&serverssidToIdMap.containsKey(asrid)){
            return serverssidToIdMap.get(asrid);
        }
        return null;
    }

    public synchronized static boolean setAsrServerssidByAsrid(String asrid,String asrserverssid){

        if(null==serverssidToIdMap){
            serverssidToIdMap=new HashMap<String ,String >();
        }
        serverssidToIdMap.put(asrid,asrserverssid);
        return true;
    }

    public synchronized static boolean delAsrServerssidByAsrid(String asrid){

        if(null!=serverssidToIdMap&&serverssidToIdMap.containsKey(asrid)){
            serverssidToIdMap.remove(asrid);
            return true;
        }
        return false;
    }


}
