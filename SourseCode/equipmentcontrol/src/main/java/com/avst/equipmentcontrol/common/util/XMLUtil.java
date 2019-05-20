package com.avst.equipmentcontrol.common.util;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

public class XMLUtil{

    /**
     * xml字符串转对象
     * @param o
     * @param rr
     * @return
     */
    public static Object  xmlToStr(Object o,String rr){

        //定义序列化对象
        Serializer serializer = new Persister();
        try {
            System.out.println("--xmlToStr rr:"+rr);
            o=serializer.read(o, rr);
            return o;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
