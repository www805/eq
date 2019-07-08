package com.avst.equipmentcontrol.outside.dealoutinterface.tts.avsttts.util.param;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement//根节点
public class Str2TtsXml {

    @XmlElement
    private String ResCode;

    @XmlElement
    private String ResMessage;

    @XmlElement
    private String ErrorNo;

    @XmlElement
    private String ResultToken;

    @XmlElement
    private String Data_Len;

    public String getResCode() {
        return ResCode;
    }

    public void setResCode(String resCode) {
        ResCode = resCode;
    }

    public String getResMessage() {
        return ResMessage;
    }

    public void setResMessage(String resMessage) {
        ResMessage = resMessage;
    }

    public String getErrorNo() {
        return ErrorNo;
    }

    public void setErrorNo(String errorNo) {
        ErrorNo = errorNo;
    }

    public String getResultToken() {
        return ResultToken;
    }

    public void setResultToken(String resultToken) {
        ResultToken = resultToken;
    }

    public String getData_Len() {
        return Data_Len;
    }

    public void setData_Len(String data_Len) {
        Data_Len = data_Len;
    }
}
