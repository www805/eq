package com.avst.equipmentcontrol.common.util;

import com.google.gson.Gson;
import org.dom4j.*;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class XMLUtil{

    /**
     * xml字符串转对象(单个对象没问题)
     * @param o
     * @param rr
     * @return
     */
    public static Object  xmlToStr(Object o,String rr){

        //定义序列化对象
        Serializer serializer = new Persister();
        try {
            LogUtil.intoLog(XMLUtil.class,"--xmlToStr rr:"+rr);
            o=serializer.read(o, rr);
            return o;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }





    public static Object xml2JavaBean(Object o, String xml) {
        try {
            Class<?> clz = Class.forName(o.getClass().getName());
            Object javabean = clz.newInstance(); // 构建对象
            Method[] methods = clz.getMethods(); // 获取所有方法
            for (Method method : methods) {
                String field = method.getName(); // 截取属性名
                if (field.startsWith("set")) {
                    field = field.substring(field.indexOf("set") + 3);//shuxingming
                    field = field.toLowerCase().charAt(0) + field.substring(1);//小写化
                    int start=xml.indexOf("<"+field+">")+field.length()+2;
                    int end=xml.indexOf("</"+field+">");
                    LogUtil.intoLog(XMLUtil.class,start+"----"+end);
                    String v=xml.substring(start,end);
                    if(!v.trim().equals("")){
                        method.invoke(javabean, v);
                    }

                }
            }
            return javabean;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Object map2JavaBean(Object o, Map<String, Object> map) {
        try {
            Class<?> clz = Class.forName(o.getClass().getName());
            Object javabean = clz.newInstance(); // 构建对象
            Method[] methods = clz.getMethods(); // 获取所有方法
            for (Method method : methods) {
                String field = method.getName(); // 截取属性名
                if (field.startsWith("set")) {
                    field = field.substring(field.indexOf("set") + 3);//shuxingming
                    field = field.toLowerCase().charAt(0) + field.substring(1);//小写化
                    if (map.containsKey(field)&&null!=map.get(field)) {
                        String v=map.get(field).toString();
                        if(!v.trim().equals("")){
                            method.invoke(javabean, v);
                        }
                    }
                }
            }
            return javabean;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }




    public static Map xml2map(String xmlString)  {
        Map<String, Object> map = null;
        try {
            Document doc = DocumentHelper.parseText(xmlString);
            Element rootElement = doc.getRootElement();
            map = new HashMap<>();
            ele2map(map, rootElement);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }


    private static void ele2map(Map map, Element ele) {
        // 获得当前节点的子节点
        List<Element> elements = ele.elements();
        if (elements.size() == 0) {
            // 没有子节点说明当前节点是叶子节点，直接取值即可
            map.put(ele.getName(), ele.getText());
        } else if (elements.size() == 1) {
            // 只有一个子节点说明不用考虑list的情况，直接继续递归即可
            Map<String, Object> tempMap = new HashMap<String, Object>();
            ele2map(tempMap, elements.get(0));
            map.put(ele.getName(), tempMap);
        } else {
            // 多个子节点的话就得考虑list的情况了，比如多个子节点有节点名称相同的
            // 构造一个map用来去重
            Map<String, Object> tempMap = new HashMap<String, Object>();
            for (Element element : elements) {
                tempMap.put(element.getName(), null);
            }
            Set<String> keySet = tempMap.keySet();
            for (String string : keySet) {
                Namespace namespace = elements.get(0).getNamespace();
                List<Element> elements2 = ele.elements(new QName(string,namespace));
                // 如果同名的数目大于1则表示要构建list
                if (elements2.size() > 1) {
                    List<Map> list = new ArrayList<Map>();
                    for (Element element : elements2) {
                        Map<String, Object> tempMap1 = new HashMap<String, Object>();
                        ele2map(tempMap1, element);
                        list.add(tempMap1);
                    }
                    map.put(string, list);
                } else {
                    // 同名的数量不大于1则直接递归去
                    Map<String, Object> tempMap1 = new HashMap<String, Object>();
                    ele2map(tempMap1, elements2.get(0));
                    map.put(string, tempMap1);
                }
            }
        }
    }
}
