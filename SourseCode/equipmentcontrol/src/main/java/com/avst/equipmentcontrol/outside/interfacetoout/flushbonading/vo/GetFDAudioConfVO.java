package com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.vo;

import com.avst.equipmentcontrol.outside.interfacetoout.flushbonading.vo.Param.GetAudioConfVO_param;

import java.util.List;

public class GetFDAudioConfVO {

    private int audioPassagewayNum;//总计多少个音频通道

    private List<GetAudioConfVO_param> audiolist;

    public int getAudioPassagewayNum() {
        return audioPassagewayNum;
    }

    public void setAudioPassagewayNum(int audioPassagewayNum) {
        this.audioPassagewayNum = audioPassagewayNum;
    }

    public List<GetAudioConfVO_param> getAudiolist() {
        return audiolist;
    }

    public void setAudiolist(List<GetAudioConfVO_param> audiolist) {
        this.audiolist = audiolist;
    }
}
