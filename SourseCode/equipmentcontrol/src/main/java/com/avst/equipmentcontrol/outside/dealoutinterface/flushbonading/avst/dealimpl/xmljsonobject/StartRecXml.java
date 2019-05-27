package com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.xmljsonobject;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement//根节点
public class StartRecXml {

    @XmlElement
    private String version;

    @XmlElement
    private String startrec;

    @XmlElement
    private String iid;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getStartrec() {
        return startrec;
    }

    public void setStartrec(String startrec) {
        this.startrec = startrec;
    }

    public String getIid() {
        return iid;
    }

    public void setIid(String iid) {
        this.iid = iid;
    }
}
