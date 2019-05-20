package com.avst.equipmentcontrol.outside.dealoutinterface.asr.req;

public class BaseParam {

    /**
     * 语音识别服务器的IP
     */
    private String ip ;
    /**
     * 语音识别服务器的port
     */
    private String port;


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

    public BaseParam(String ip, String port) {
        this.ip = ip;
        this.port = port;
    }

    @Override
    public String toString() {
        return "BaseParam{" +
                "ip='" + ip + '\'' +
                ", port='" + port + '\'' +
                '}';
    }
}
