package com.avst.equipmentcontrol.outside.dealoutinterface.polygraph.action;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import javax.xml.namespace.QName;

public class WebServiceClient {

    public static void main (String args[]){
        String result="";
        Service service = new Service();
        Call call=null;
        try {
            call = (Call)service.createCall();
            //endpoint 值为上面提到的调用地址
            String endpoint = "http://host_ip:10000/cmcross/api";
            call.setTargetEndpointAddress(endpoint);
            //设置命名空间和需要调用的方法名;第一个参数为命名空间,第二个参数为方法名,调用时候只需要根据需求修改方法名
            QName opAddEntry = new QName("http://api.cmcross.com/",
                    "XBOX_GetResult");
            call.setOperationName(opAddEntry);
            //设置返回类型
            call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);
            //设置请求超时
            call.setTimeout(Integer.valueOf(2000));
            //此处返回 json 格式的字符串
            result= (String)call.invoke(new Object[]{});
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
