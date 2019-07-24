package com.avst.equipmentcontrol.common.util.ff;


import java.util.HashMap;
import java.util.Map;

/**
 * ff处理转码的缓存
 * key 是转码的转出的文件路径
 */
public class FFThreadCache {

    private static Map<String , VideoChangeThread> videoChangeThreadmap;


    public static  synchronized VideoChangeThread getVideoChangeThread(String outputurl){

        if(null!=videoChangeThreadmap&&videoChangeThreadmap.containsKey(outputurl)){
            return videoChangeThreadmap.get(outputurl);
        }
        return null;
    }


    public static  synchronized boolean setVideoChangeThread(String outputurl,VideoChangeThread videoChangeThread){

        if(null==videoChangeThreadmap){
            videoChangeThreadmap=new HashMap<String,VideoChangeThread>();
        }
        if(videoChangeThreadmap.containsKey(outputurl)){//应该不会有的才是
            //以防万一
            videoChangeThreadmap.remove(outputurl);
        }
        videoChangeThreadmap.put(outputurl,videoChangeThread);
        return true;
    }

    public static synchronized  boolean delVideoChangeThread(String outputurl){

        if(null!=videoChangeThreadmap&&videoChangeThreadmap.containsKey(outputurl)){
            videoChangeThreadmap.remove(outputurl);
            return true;
        }
        return false;
    }

}
