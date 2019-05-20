package com.avst.equipmentcontrol.outside.interfacetoout.asr.cache;


import com.avst.equipmentcontrol.outside.interfacetoout.asr.cache.param.AsrMessageParam;
import com.avst.equipmentcontrol.outside.interfacetoout.asr.cache.param.AsrTxtParam_toout;
import com.avst.equipmentcontrol.outside.interfacetoout.asr.conf.AsrHeartbeatThread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 关于语音识别的缓存
 * 以asrssid为key进行保存
 * 这个asrssid是asr服务器返回回来的
 */
public class AsrCache_toout {

    /**
     * avst语言服务器的文本返回接口
     */
    public static String avstbacktxtinterface="http://192.168.17.175:8081/toasr/v1/toAsrServerForTxtBack";

    /**
     * 语音识别的数据缓存
     */
    private static Map<String, List<AsrTxtParam_toout>> asrTxtMap=null;

    /**
     * 获取本次语音识别的所有识别语句
     * 每条语句都是一句话
     * 这句话可能会识别多次，但是返回给用户的都是最后一次识别（流式识别的）
     * @return
     */
    public synchronized static List<AsrTxtParam_toout> getAsrTxtList(String asrid){

        if(null!=asrTxtMap&&asrTxtMap.containsKey(asrid)){
            List<AsrTxtParam_toout> list=asrTxtMap.get(asrid);
            if(null!=list&&list.size() > 0){
                return list;
            }
        }
        return null;
    }

    public synchronized static int getAsrTxtCount(String asrid){

        if(null!=asrTxtMap&&asrTxtMap.containsKey(asrid)){
            List<AsrTxtParam_toout> list=asrTxtMap.get(asrid);
            if(null!=list&&list.size() > 0){
                return list.size();
            }
        }
        return 0;
    }

    /**
     * 获取本次语音识别的某一句
     * 通过这句话的第一次识别的开始时间来判断
     * @return
     */
    public synchronized static AsrTxtParam_toout getAsrTxtByStartTime(String asrid,String time){

        List<AsrTxtParam_toout> list = getAsrTxtList(asrid);
        if(null!=list&&list.size() > 0){
            for(AsrTxtParam_toout out:list){
                if(time.equals(out.getStarttime())){
                    return out;
                }
            }
        }
        return null;
    }

    /**
     * 获取本次语音识别的最后一句话
     * @return
     */
    public synchronized static AsrTxtParam_toout getAsrTxtLastOne(String asrid){

        List<AsrTxtParam_toout> list = getAsrTxtList(asrid);
        if(null!=list&&list.size() > 0){
            return list.get(list.size()-1);
        }
        return null;
    }

    /**
     * 往缓存里面插入数据
     * @param asrid 本次语音识别的ssid
     * @param param 识别返回的结果集，注意里面的几个int参数
     * @return
     */
    public synchronized static boolean setAsrTxtLastOne(String asrid, AsrTxtParam_toout param){

        if(null==asrTxtMap){
            asrTxtMap=new HashMap<String, List<AsrTxtParam_toout>>();
        }

        if(asrTxtMap.containsKey(asrid)){
            List<AsrTxtParam_toout> list=asrTxtMap.get(asrid);
            if(null==list){
                list=new ArrayList<AsrTxtParam_toout>();
            }
            if(list.size() > 0){
                int i=0;
                for(AsrTxtParam_toout a:list){
                    if(a.getAsrsort()==param.getAsrsort()){//asr流式识别一句话可能会识别几次，所以每次新的就会把旧的挤掉
                        list.remove(i);
                        break;
                    }
                    i++;
                }
            }
            list.add(param);
            asrTxtMap.put(asrid,list);
        }else{
            List<AsrTxtParam_toout> list=new ArrayList<AsrTxtParam_toout>();
            list.add(param);
            asrTxtMap.put(asrid,list);
        }
        return false;
    }


    /**
     * 删除识别结果集缓存，一般用于识别结束后
     * @param asrid
     * @return
     */
    public synchronized static boolean delAsrTxt(String asrid){
        if(null!=asrTxtMap&&asrTxtMap.containsKey(asrid)){
            asrTxtMap.remove(asrid);
            return true;
        }
        return false;
    }


    //一个语音识别的缓存，区别于数据存储，这里存的是本次语音识别的基本信息
    private static Map<String , AsrMessageParam> asrMassegeMap=null;

    public synchronized static AsrMessageParam getAsrMassege(String asrid){

        if(null!=asrMassegeMap&&asrMassegeMap.containsKey(asrid)){
            return asrMassegeMap.get(asrid);
        }

        return null;
    }

    public synchronized static boolean setAsrMassege(String asrid,AsrMessageParam asr){

        if(null==asrMassegeMap){
            asrMassegeMap=new HashMap<String,AsrMessageParam>();
        }
        asrMassegeMap.put(asrid,asr);

        return false;
    }

    public synchronized static boolean delAsrMassege(String asrid){

        if(null!=asrMassegeMap&&asrMassegeMap.containsKey(asrid)){

            AsrHeartbeatThread thread=asrMassegeMap.get(asrid).getAsrHeartbeatThread();
            if(null!=thread){
                thread.bool=false;//强制关闭线程；
            }

            asrMassegeMap.remove(asrid);
            return true;
        }
        return false;
    }


}
