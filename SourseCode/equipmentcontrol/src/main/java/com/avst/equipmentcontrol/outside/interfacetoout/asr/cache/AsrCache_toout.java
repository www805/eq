package com.avst.equipmentcontrol.outside.interfacetoout.asr.cache;


import com.avst.equipmentcontrol.common.util.properties.PropertiesListenerConfig;
import com.avst.equipmentcontrol.outside.interfacetoout.asr.cache.param.AsrMessageParam;
import com.avst.equipmentcontrol.outside.interfacetoout.asr.cache.param.AsrTxtParam_toout;
import com.avst.equipmentcontrol.outside.interfacetoout.asr.conf.AsrHeartbeatThread;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * 关于语音识别的缓存
 * 以asrid为key进行保存
 * 这个asrid是asr服务器返回回来的
 */
public class AsrCache_toout {

    /**
     * avst语言服务器的文本返回接口
     */
    public static String avstbacktxtinterface= PropertiesListenerConfig.getProperty("avstasrbacktxtinterface");

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
     * 获取本次语音识别的最后几句没有得到的话
     * @return
     */
    public synchronized static List<AsrTxtParam_toout> getAsrTxtLastList(String asrid,Integer asrsort){

        List<AsrTxtParam_toout> list = getAsrTxtList(asrid);
        if(null!=list&&list.size() > 0){
            List<AsrTxtParam_toout> rrlist=new ArrayList<AsrTxtParam_toout>();

            int index=list.size()-1;
            AsrTxtParam_toout out=list.get(index);
            //list后面的sort总是比前面的大，所以只需要判断临界值大于这个值得写入，其他的就可以都不要
            while(out.getAsrsort()>asrsort.intValue()){//检测那些是已经得到的数据,大于打钱观看的sort才会提那家到返回中去
                rrlist.add(out);
                index--;
                if(index<0){
                    break;
                }
                out=list.get(index);
            }

            return rrlist;
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

        if(asrid.indexOf("_") > -1){
            String[] arr=asrid.split("_");
            asrid=arr[0];
        }

        if(null!=asrMassegeMap&&asrMassegeMap.containsKey(asrid)){
            return asrMassegeMap.get(asrid);
        }

        return null;
    }

    public synchronized static Set<String> getAsridList(){

        if(null!=asrMassegeMap&&null!=asrMassegeMap.keySet()){
            return asrMassegeMap.keySet();
        }
        return null;
    }

    public synchronized static boolean setAsrMassege(String asrid,AsrMessageParam asr){

        if(null==asrMassegeMap){
            asrMassegeMap=new HashMap<String,AsrMessageParam>();
        }

        if(StringUtils.isNotEmpty(asrid)){
            if(asrid.indexOf("_") > -1){
                String[] arr=asrid.split("_");
                asrid=arr[0];
            }
            asrMassegeMap.put(asrid,asr);
        }

        return true;
    }

    public synchronized static boolean delAsrMassege(String asrid){

        if(asrid.indexOf("_") > -1){
            String[] arr=asrid.split("_");
            asrid=arr[0];
        }

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
