package com.avst.equipmentcontrol.outside.dealoutinterface.asr.vo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement//根节点
public class AvstSDKInterfaceBackParam_Dealreg extends AvstBaseSDKInterfaceBackParam{

    @XmlElement//作为元素
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
