package com.avst.equipmentcontrol.outside.dealoutinterface.asr.avstasr.req;

import com.avst.equipmentcontrol.common.conf.AsrServerModel;

public class BaseParam {

    /**
     * 语音识别服务器的IP
     */
    private String ip ;
    /**
     * 语音识别服务器的port
     */
    private String port;

    private String asrServerModel= AsrServerModel.m1;//语音识别服务器的识别模式

    public String getAsrServerModel() {
        return asrServerModel;
    }

    public void setAsrServerModel(String asrServerModel) {
        this.asrServerModel = asrServerModel;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public BaseParam(String ip, String port,String asrServerModel) {
        this.ip = ip;
        this.port = port;
        this.asrServerModel=asrServerModel;
    }
    public BaseParam(String ip, String port) {
        this.ip = ip;
        this.port = port;
        this.asrServerModel= AsrServerModel.m1;
    }

    @Override
    public String toString() {
        return "BaseParam{" +
                "ip='" + ip + '\'' +
                ", port='" + port + '\'' +
                '}';
    }
}
