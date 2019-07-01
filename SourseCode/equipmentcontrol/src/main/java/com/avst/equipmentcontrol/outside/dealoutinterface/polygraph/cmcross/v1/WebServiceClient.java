package com.avst.equipmentcontrol.outside.dealoutinterface.polygraph.cmcross.v1;

import com.avst.equipmentcontrol.common.util.Base64ToPhotoUtil;
import com.avst.equipmentcontrol.common.util.JacksonUtil;
import com.avst.equipmentcontrol.common.util.LogUtil;
import com.avst.equipmentcontrol.outside.dealoutinterface.polygraph.cmcross.v1.vo.XBOX_GetImageVO;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import javax.xml.namespace.QName;
import java.util.Date;

public class WebServiceClient {


    private static String basenamespaceURL="http://api.cmcross.com/";

    public static String webClient (String ip,int port,String action){
        String result=null;
        Service service = new Service();
        Call call=null;
        try {
            call = (Call)service.createCall();
            String clientip=ip;
            Integer clientport=port;
            String endpoint="http://"+clientip+":"+clientport+"/cmcross/api";
            call.setTargetEndpointAddress(endpoint);
            //设置命名空间和需要调用的方法名;第一个参数为命名空间,第二个参数为方法名,调用时候只需要根据需求修改方法名
            QName opAddEntry = new QName(basenamespaceURL,action);
            call.setOperationName(opAddEntry);
            //设置返回类型
            call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);
            //设置请求超时
            call.setTimeout(Integer.valueOf(3000));
            //此处返回 json 格式的字符串
            result= (String)call.invoke(new Object[]{});
        }catch (Exception e){
            e.printStackTrace();
        }
        LogUtil.intoLog(WebServiceClient.class,result);
        return result;
    }





    public static void main (String args[]){
        long starttime=new Date().getTime();
        String result="";
        Service service = new Service();
        Call call=null;
        try {
            call = (Call)service.createCall();
            //endpoint 值为上面提到的调用地址
            String endpoint = "http://192.168.17.144:10000/cmcross/api";
            call.setTargetEndpointAddress(endpoint);
            //设置命名空间和需要调用的方法名;第一个参数为命名空间,第二个参数为方法名,调用时候只需要根据需求修改方法名
            QName opAddEntry = new QName("http://api.cmcross.com/","XBOX_GetImage");
            call.setOperationName(opAddEntry);
            //设置返回类型
            call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);
            //设置请求超时
            call.setTimeout(Integer.valueOf(8000));
            //此处返回 json 格式的字符串
            result= (String)call.invoke(new Object[]{});
        }catch (Exception e){
            e.printStackTrace();
        }
        LogUtil.intoLog(WebServiceClient.class,new Date().getTime()-starttime+":not do");
        LogUtil.intoLog(WebServiceClient.class,new Date().getTime()+"------"+starttime);
        LogUtil.intoLog(WebServiceClient.class,result);
        XBOX_GetImageVO vo=(XBOX_GetImageVO)JacksonUtil.stringToObjebt_1(result,XBOX_GetImageVO.class);
        String imgpath = "I:\\wubin\\笔录管理系统\\系统设计\\测谎仪服务\\img.png";
        Base64ToPhotoUtil.generateImage(vo.getImage(),imgpath);
        LogUtil.intoLog(WebServiceClient.class,new Date().getTime()-starttime+": do over");

    }
}
