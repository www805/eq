package com.avst.equipmentcontrol.outside.dealoutinterface.storage.avstss.req;

public class BaseParam {

    private String ip;//这个IP可能有问题，传过来之前是设备IP，传过来接收是ssip存储设备IP，虽然暂时没有用，以后要处理一下

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
