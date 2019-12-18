package com.avst.equipmentcontrol.outside.dealoutinterface.tts.avsttts.req;

public class Str2ttsReq extends BaseParam{

    private String text;//需要转的文字

    private String app_key;

    private String lang_speaker_domain;

    private String cap_key;

    private String dev_key;

    private String ttsstatic;//tts http调用的切割路径

    private String ttsbasepath;//tts音频存储的路径

    private String ttsURL="/tts/SynthText";//tts请求路径

    public String getTtsURL() {
        return ttsURL;
    }

    public void setTtsURL(String ttsURL) {
        this.ttsURL = ttsURL;
    }

    public String getTtsstatic() {
        return ttsstatic;
    }

    public void setTtsstatic(String ttsstatic) {
        this.ttsstatic = ttsstatic;
    }

    public String getTtsbasepath() {
        return ttsbasepath;
    }

    public void setTtsbasepath(String ttsbasepath) {
        this.ttsbasepath = ttsbasepath;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getApp_key() {
        return app_key;
    }

    public void setApp_key(String app_key) {
        this.app_key = app_key;
    }

    public String getLang_speaker_domain() {
        return lang_speaker_domain;
    }

    public void setLang_speaker_domain(String lang_speaker_domain) {
        this.lang_speaker_domain = lang_speaker_domain;
    }

    public String getCap_key() {
        return cap_key;
    }

    public void setCap_key(String cap_key) {
        this.cap_key = cap_key;
    }

    public String getDev_key() {
        return dev_key;
    }

    public void setDev_key(String dev_key) {
        this.dev_key = dev_key;
    }
}
