package com.avst.equipmentcontrol.outside.dealoutinterface.asr.avstasr.req;

import com.avst.equipmentcontrol.outside.dealoutinterface.asr.avstasr.req.param.TaskParam;

import java.util.List;

public class AVSTAsrParam_settaskinfo extends BaseParam {

    private String asrid;

    private List<TaskParam> taskParamList;

    public String getAsrid() {
        return asrid;
    }

    public void setAsrid(String asrid) {
        this.asrid = asrid;
    }

    public List<TaskParam> getTaskParamList() {
        return taskParamList;
    }

    public void setTaskParamList(List<TaskParam> taskParamList) {
        this.taskParamList = taskParamList;
    }

    public AVSTAsrParam_settaskinfo(String ip, String port) {
        super(ip, port);
    }

    public AVSTAsrParam_settaskinfo(String ip, String port,String asrServerModel) {
        super(ip, port,asrServerModel);
    }
}
