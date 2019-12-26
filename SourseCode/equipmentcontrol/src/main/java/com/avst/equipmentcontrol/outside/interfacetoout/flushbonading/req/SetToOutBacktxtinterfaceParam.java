package com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.req;

public class SetToOutBacktxtinterfaceParam extends BaseParam {

    private Integer port;
    private Integer ftpport;

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getFtpport() {
        return ftpport;
    }

    public void setFtpport(Integer ftpport) {
        this.ftpport = ftpport;
    }
}
