package com.avst.equipmentcontrol.outside.dealoutinterface.storage.avstss.cache;

import com.avst.equipmentcontrol.outside.dealoutinterface.storage.avstss.conf.SSAddDataThread;
import com.avst.equipmentcontrol.outside.dealoutinterface.storage.avstss.conf.SSChecckDataThread;
import com.avst.equipmentcontrol.outside.dealoutinterface.storage.avstss.conf.SSCreateDataUrlThread;

import java.util.HashMap;
import java.util.Map;

/**
 * 管理3个工作线程
 */
public class SSThreadCache {

    private static Map<String , SSAddDataThread> addmap;//新增的线程


    public static  synchronized SSAddDataThread getSSAddDataThread(String iid){

        if(null!=addmap&&addmap.containsKey(iid)){
            return addmap.get(iid);
        }
        return null;
    }


    public static  synchronized boolean setSSAddDataThread(String iid,SSAddDataThread ssAddDataThread){

        if(null==addmap){
            addmap=new HashMap<String,SSAddDataThread>();
        }
        if(addmap.containsKey(iid)){//应该不会有的才是
            //以防万一
            addmap.get(iid).bool=false;
            addmap.remove(iid);
        }
        addmap.put(iid,ssAddDataThread);
        return true;
    }

    public static synchronized  boolean delSSAddDataThread(String iid){

        if(null!=addmap&&addmap.containsKey(iid)){
            addmap.remove(iid);
            return true;
        }
        return false;
    }

    private static Map<String , SSChecckDataThread> checkmap;


    public static  synchronized SSChecckDataThread getSSChecckDataThread(String iid){

        if(null!=checkmap&&checkmap.containsKey(iid)){
            return checkmap.get(iid);
        }
        return null;
    }


    public static  synchronized boolean setSSChecckDataThread(String iid,SSChecckDataThread ssChecckDataThread){

        if(null==checkmap){
            checkmap=new HashMap<String,SSChecckDataThread>();
        }
        if(checkmap.containsKey(iid)){//应该不会有的才是
            //以防万一
            checkmap.get(iid).bool=false;
            checkmap.remove(iid);
        }
        checkmap.put(iid,ssChecckDataThread);
        return true;
    }

    public static synchronized  boolean delSSChecckDataThread(String iid){

        if(null!=checkmap&&checkmap.containsKey(iid)){
            checkmap.remove(iid);
            return true;
        }
        return false;
    }

    private static Map<String , SSCreateDataUrlThread> createurlmap;


    public static  synchronized SSCreateDataUrlThread getSSCreateDataUrlThread(String iid){

        if(null!=createurlmap&&createurlmap.containsKey(iid)){
            return createurlmap.get(iid);
        }
        return null;
    }


    public static  synchronized boolean setSSCreateDataUrlThread(String iid,SSCreateDataUrlThread ssCreateDataUrlThread){

        if(null==createurlmap){
            createurlmap=new HashMap<String,SSCreateDataUrlThread>();
        }
        if(createurlmap.containsKey(iid)){//应该不会有的才是
            //以防万一
            createurlmap.get(iid).bool=false;
            createurlmap.remove(iid);
        }
        createurlmap.put(iid,ssCreateDataUrlThread);
        return true;
    }

    public static synchronized  boolean delSSCreateDataUrlThread(String iid){

        if(null!=createurlmap&&createurlmap.containsKey(iid)){
            createurlmap.remove(iid);
            return true;
        }
        return false;
    }

}
