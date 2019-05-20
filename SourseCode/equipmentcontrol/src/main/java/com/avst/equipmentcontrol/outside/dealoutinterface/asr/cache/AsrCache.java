package com.avst.equipmentcontrol.outside.dealoutinterface.asr.cache;

import com.avst.equipmentcontrol.outside.dealoutinterface.asr.cache.param.AsrTxtParam;
import com.avst.equipmentcontrol.outside.dealoutinterface.asr.cache.param.AsrTxtParam_avst;
import com.avst.equipmentcontrol.outside.interfacetoout.asr.cache.AsrCache_toout;
import com.avst.equipmentcontrol.outside.interfacetoout.asr.cache.param.AsrTxtParam_toout;
import com.avst.equipmentcontrol.common.conf.ASRType;

import java.util.*;

/**
 * 关于第三方语音识别的缓存
 * asrEquipmentssid和asrssid都是唯一的，所以通过asrssid个就可以找到asrEquipmentssid
 * 这里的缓存直接关联数据库语音服务的ssid（不是原始设备的ssid，是语音服务的ssid）
 * 最外层 map 每种语音识别服务的识别结果
 * AsrTxtParam_toout 这一次语音识别的结果集，一次语音识别有很多句
 * List<T> T每句话的识别结果
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
     * @param asrssid 本次语音识别的ssid（这一次识别的唯一码）
     * @return
     */
    public synchronized static List getAsrTxtListByASRssid(String asrEquipmentssid,String asrssid){

        List<AsrTxtParam> list = getAsrListByEquipmentssid(asrEquipmentssid);
        if(null!=list&&list.size() > 0){
            for(AsrTxtParam asr:list){
                Map<String, List> asrmap=asr.getAsrmap();
                if(null!=asrmap&&asrmap.containsKey(asrssid)){
                    return asrmap.get(asrssid);
                }
            }
        }
        return null;
    }



    /**
     * 获取这个语言服务器的某一次的任务集合的最后一句
     * @param asrEquipmentssid 语音服务的ssid
     * @param asrssid 本次语音识别的ssid（这一次识别的唯一码）
     * @return
     */
    public synchronized static Object getAsrTxtLastOneByASRssid(String asrEquipmentssid,String asrssid){

        List asrlist=getAsrTxtListByASRssid(asrEquipmentssid,asrssid);
        if(null!=asrlist&&asrlist.size() > 0){
            return asrlist.get(asrlist.size()-1);
        }
        return null;
    }

    /**
     * avst asr获取这个语言服务器的某一次的任务集合的某一句话
     * 每一个语音服务获取某一句话这个get方法都有一个人自己独享的方法（特别注意）
     * @param asrEquipmentssid 语音服务的ssid
     * @param asrssid 本次语音识别的ssid（这一次识别的唯一码）
     * @param time 这句话识别的开始时间（不管识别几次，取它识别这句话最开始的时间）
     * @return
     */
    public synchronized static AsrTxtParam_avst getAsrTxtOneByASRssid(String asrEquipmentssid,String asrssid,String time){

        List<AsrTxtParam_avst> asrlist=getAsrTxtListByASRssid(asrEquipmentssid,asrssid);
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
     * @param asrssid
     * @param param
     * @return
     */
    public synchronized static boolean setAsrByASRssid(String asrEquipmentssid, String asrssid, AsrTxtParam_avst param){

        long asrtime=(new Date()).getTime();//本句识别的本地时间

        try {
            if(null==asrTxtMap){
                asrTxtMap=new HashMap<String, List<AsrTxtParam>>();
            }

            if(asrTxtMap.containsKey(asrEquipmentssid)){
                List<AsrTxtParam> list=asrTxtMap.get(asrEquipmentssid);//这个语言服务的所有正在识别的任务
                AsrTxtParam asrTxtParam=new AsrTxtParam();
                List<AsrTxtParam_avst> asrTxtParam_avsts=new ArrayList<AsrTxtParam_avst>();
                if(null==list){
                    list=new ArrayList<AsrTxtParam>();
                    asrTxtParam.setAsrtype(ASRType.AVST);//这里需要插入语音服务器类型
                    asrTxtParam.setAsrEquipmentssid(asrEquipmentssid);
                }else{
                    for(AsrTxtParam a:list){
                        Map<String, List<AsrTxtParam_avst>> asrmap=a.getAsrmap();
                        if(null!=asrmap&&asrmap.containsKey(asrssid)){//判断语音识别服务服务器的某一次识别任务
                            asrTxtParam=a;
                        }
                    }
                }
                Map<String, List<AsrTxtParam_avst>> asrmap=asrTxtParam.getAsrmap();//某一次识别任务
                if(null==asrmap){
                    asrmap=new HashMap<String, List<AsrTxtParam_avst>>();
                }
                if(asrmap.containsKey(asrssid)){
                    asrTxtParam_avsts=asrmap.get(asrssid);
                }
                int i=0;
                for(AsrTxtParam_avst asr:asrTxtParam_avsts){
                    if(asr.getTime().equals(param.getTime())){//判断这是那一句，去掉有更新的旧语句
                        asrTxtParam_avsts.remove(i);
                        break;
                    }
                    i++;
                }
                asrTxtParam_avsts.add(param);//如果没有直接插入
                asrmap.put(asrssid,asrTxtParam_avsts);
                asrTxtParam.setAsrmap(asrmap);
                setAsrByEquipmentssid(asrEquipmentssid,asrTxtParam);

            }else{
                List<AsrTxtParam> list=new ArrayList<AsrTxtParam>();
                AsrTxtParam<AsrTxtParam_avst> asrTxtParam=new AsrTxtParam<AsrTxtParam_avst>();
                Map<String, List<AsrTxtParam_avst>> asrmap=new HashMap<String, List<AsrTxtParam_avst>>();
                List<AsrTxtParam_avst>asrTxtParam_avsts =new ArrayList<AsrTxtParam_avst>();
                asrTxtParam_avsts.add(param);
                asrmap.put(asrssid,asrTxtParam_avsts);
                asrTxtParam.setAsrmap(asrmap);
                asrTxtParam.setAsrEquipmentssid(asrEquipmentssid);
                asrTxtParam.setAsrtype(ASRType.AVST);
                list.add(asrTxtParam);
                asrTxtMap.put(asrEquipmentssid,list);
            }

            //修改对外缓存
            String starttime=param.getTime();//这一句话的开始识别时间
            AsrTxtParam_toout asr_out=AsrCache_toout.getAsrTxtByStartTime(asrssid,starttime);
            int asrsort=1;
            System.out.println(asr_out==null?null:(asr_out.getAsrnum()+"--原来的out--"+asr_out.getTxt()));
            if(null==asr_out){
                asr_out=new AsrTxtParam_toout();
                asrsort=AsrCache_toout.getAsrTxtCount(asrssid)+1;//只有新增的句子才会在原有的基础上加1
                asr_out.setAsrsort(asrsort);//本次语音识别这是识别的第几句
            }
            asr_out.setAsrtime(asrtime+"");//返回本句识别的时间
            asr_out.setStarttime(starttime);
            asr_out.setTxt(param.getMsg());
            AsrCache_toout.setAsrTxtLastOne(asrssid,asr_out);

            AsrTxtParam_toout asr_out_new=AsrCache_toout.getAsrTxtByStartTime(asrssid,starttime);
            System.out.println(asr_out_new==null?null:(asr_out_new.getAsrnum()+"--现在的out--"+asr_out_new.getTxt()));

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
    public synchronized static boolean delAsrTxtByEquipmentssid(String asrEquipmentssid,String asrssid){
        if(null!=asrTxtMap&&asrTxtMap.containsKey(asrEquipmentssid)){

            List<AsrTxtParam> list= asrTxtMap.get(asrEquipmentssid);
            if(null!=list&&list.size() > 0){
                for(AsrTxtParam a:list){
                    Map<String, List> asrmap=a.getAsrmap();
                    if(null!=asrmap&&asrmap.containsKey(asrssid)){
                        asrmap.remove(asrssid);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     *
     * @param asrssid 也是唯一的，所以通过ssid也是可以查到设备ssid的
     * @return
     */
    public synchronized static String getAsrEquipmentssidByAsrssid(String asrssid){

        if(null!=asrTxtMap){
            for(List<AsrTxtParam> asr1:asrTxtMap.values()){
                for(AsrTxtParam asr2: asr1){
                    if(asr2.getAsrmap().containsKey(asrssid)){
                        return asr2.getAsrEquipmentssid();
                    }
                }
            };
        }
        return null;
    }

    /**
     * 删除识别结果集缓存，一般用于识别结束后
     * 删除某一个asr语音服务的某一次识别任务
     * @param asrssid
     * @return
     */
    public synchronized static boolean delAsrTxtByASRSsid(String asrssid){

        String asrEquipmentssid=getAsrEquipmentssidByAsrssid(asrssid);//通过asrssid找到asrEquipmentssid

        if(null==asrEquipmentssid){
            System.out.println("getAsrEquipmentssidByAsrssid(asrssid) is null ,请检查asrssid获取设备ssid的逻辑");
            return false;
        }

        if(null!=asrTxtMap&&asrTxtMap.containsKey(asrEquipmentssid)){
            List<AsrTxtParam> list= asrTxtMap.get(asrEquipmentssid);
            if(null!=list&&list.size() > 0){
                for(AsrTxtParam a:list){
                    Map<String, List> asrmap=a.getAsrmap();
                    if(null!=asrmap&&asrmap.containsKey(asrssid)){
                        asrmap.remove(asrssid);
                        return true;
                    }
                }
            }
        }
        return false;
    }


}
