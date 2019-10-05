package com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.xmljsonobject;

import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.xmljsonobject.param.FDLogItem;

import java.util.List;

public class GetFDLogXml {

    private String t;

    private String totalpage;

    private String currpage;

    private List<FDLogItem> fdLogItemlist;

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public String getTotalpage() {
        return totalpage;
    }

    public void setTotalpage(String totalpage) {
        this.totalpage = totalpage;
    }

    public String getCurrpage() {
        return currpage;
    }

    public void setCurrpage(String currpage) {
        this.currpage = currpage;
    }

    public List<FDLogItem> getFdLogItemlist() {
        return fdLogItemlist;
    }

    public void setFdLogItemlist(List<FDLogItem> fdLogItemlist) {
        this.fdLogItemlist = fdLogItemlist;
    }
}
