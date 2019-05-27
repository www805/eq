package com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.xmljsonobject;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement//根节点
public class StopRecXml {

    @XmlElement
    private String version;

    @XmlElement
    private String stoprec;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getStoprec() {
        return stoprec;
    }

    public void setStoprec(String stoprec) {
        this.stoprec = stoprec;
    }
}
