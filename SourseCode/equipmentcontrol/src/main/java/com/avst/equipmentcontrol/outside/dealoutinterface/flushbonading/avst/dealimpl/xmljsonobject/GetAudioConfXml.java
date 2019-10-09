package com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.xmljsonobject;

import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.xmljsonobject.param.Aud;

import java.util.List;

public class GetAudioConfXml {

    private String version;

    private String mutech;

    private String exch0;

    private String exch1;

    private List<Aud> audList;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMutech() {
        return mutech;
    }

    public void setMutech(String mutech) {
        this.mutech = mutech;
    }

    public String getExch0() {
        return exch0;
    }

    public void setExch0(String exch0) {
        this.exch0 = exch0;
    }

    public String getExch1() {
        return exch1;
    }

    public void setExch1(String exch1) {
        this.exch1 = exch1;
    }

    public List<Aud> getAudList() {
        return audList;
    }

    public void setAudList(List<Aud> audList) {
        this.audList = audList;
    }
}
