package com.avst.equipmentcontrol.outside.interfacetoout.tts.req;

public class Str2TtsParam extends BaseParam{

    private String text;//需要识别的text文件

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
