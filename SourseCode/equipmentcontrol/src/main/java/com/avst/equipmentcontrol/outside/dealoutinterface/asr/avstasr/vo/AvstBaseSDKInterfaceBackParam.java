package com.avst.equipmentcontrol.outside.dealoutinterface.asr.avstasr.vo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement//根节点
public class AvstBaseSDKInterfaceBackParam {

    @XmlElement
    private String code;

    @XmlElement
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
