package com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.vo;

import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.xmljsonobject.GetCapabilitySetXml;

public class GetCapabilitySetVO extends GetCapabilitySetXml {

    //a_in 音频有效通道数,v_in 视频有效通道数,vdec_in 扩展视频有效通道数,规则:v_in + vdec_in必然小于等于8
    private String v_in;

    private String vdec_in;

    private String a_in;

    private String max_chn;

    private String a_samplerate;

    private String vdec_mem_total;

    private String vdec_mem_free;

    private String device_ability;

    private String extinputset_capacity;

    private String networkstreamset_capacity;

    private String viewmodeset_capacity;

    private String diskset_capacity;

    private String burnset_capacity;

    private String remote_audio_act;

    @Override
    public String getV_in() {
        return v_in;
    }

    @Override
    public void setV_in(String v_in) {
        this.v_in = v_in;
    }

    @Override
    public String getVdec_in() {
        return vdec_in;
    }

    @Override
    public void setVdec_in(String vdec_in) {
        this.vdec_in = vdec_in;
    }

    @Override
    public String getA_in() {
        return a_in;
    }

    @Override
    public void setA_in(String a_in) {
        this.a_in = a_in;
    }

    @Override
    public String getMax_chn() {
        return max_chn;
    }

    @Override
    public void setMax_chn(String max_chn) {
        this.max_chn = max_chn;
    }

    @Override
    public String getA_samplerate() {
        return a_samplerate;
    }

    @Override
    public void setA_samplerate(String a_samplerate) {
        this.a_samplerate = a_samplerate;
    }

    @Override
    public String getVdec_mem_total() {
        return vdec_mem_total;
    }

    @Override
    public void setVdec_mem_total(String vdec_mem_total) {
        this.vdec_mem_total = vdec_mem_total;
    }

    @Override
    public String getVdec_mem_free() {
        return vdec_mem_free;
    }

    @Override
    public void setVdec_mem_free(String vdec_mem_free) {
        this.vdec_mem_free = vdec_mem_free;
    }

    @Override
    public String getDevice_ability() {
        return device_ability;
    }

    @Override
    public void setDevice_ability(String device_ability) {
        this.device_ability = device_ability;
    }

    @Override
    public String getExtinputset_capacity() {
        return extinputset_capacity;
    }

    @Override
    public void setExtinputset_capacity(String extinputset_capacity) {
        this.extinputset_capacity = extinputset_capacity;
    }

    @Override
    public String getNetworkstreamset_capacity() {
        return networkstreamset_capacity;
    }

    @Override
    public void setNetworkstreamset_capacity(String networkstreamset_capacity) {
        this.networkstreamset_capacity = networkstreamset_capacity;
    }

    @Override
    public String getViewmodeset_capacity() {
        return viewmodeset_capacity;
    }

    @Override
    public void setViewmodeset_capacity(String viewmodeset_capacity) {
        this.viewmodeset_capacity = viewmodeset_capacity;
    }

    @Override
    public String getDiskset_capacity() {
        return diskset_capacity;
    }

    @Override
    public void setDiskset_capacity(String diskset_capacity) {
        this.diskset_capacity = diskset_capacity;
    }

    @Override
    public String getBurnset_capacity() {
        return burnset_capacity;
    }

    @Override
    public void setBurnset_capacity(String burnset_capacity) {
        this.burnset_capacity = burnset_capacity;
    }

    @Override
    public String getRemote_audio_act() {
        return remote_audio_act;
    }

    @Override
    public void setRemote_audio_act(String remote_audio_act) {
        this.remote_audio_act = remote_audio_act;
    }
}
