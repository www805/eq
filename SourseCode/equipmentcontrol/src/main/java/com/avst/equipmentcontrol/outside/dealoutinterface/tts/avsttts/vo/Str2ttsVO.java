package com.avst.equipmentcontrol.outside.dealoutinterface.tts.avsttts.vo;

/**
 * tts文字转语音
 */
public class Str2ttsVO {

    private String uploadpath;//可供播放的http的WAV文件

    public String getUploadpath() {
        return uploadpath;
    }

    public void setUploadpath(String uploadpath) {
        this.uploadpath = uploadpath;
    }
}
