package com.avst.equipmentcontrol.outside.dealoutinterface.asr.req;

public class AVSTAsrParam_reg extends BaseParam{

    /**
     * 顺泰伟成刻录主机设备实时音频流
     */
    private String audiourl;

    /**
     * 语音识别回调的接口地址
     */
    private  String txtcallbackurl;


    public String getAudiourl() {
        return audiourl;
    }

    public void setAudiourl(String audiourl) {
        this.audiourl = audiourl;
    }

    public String getTxtcallbackurl() {
        return txtcallbackurl;
    }

    public void setTxtcallbackurl(String txtcallbackurl) {
        this.txtcallbackurl = txtcallbackurl;
    }

    public AVSTAsrParam_reg(String ip, String port, String audiourl) {
        super(ip, port);
        this.audiourl = audiourl;
        this.txtcallbackurl = txtcallbackurl;
    }

    public AVSTAsrParam_reg(String ip, String port, String audiourl, String txtcallbackurl) {
        super(ip, port);
        this.audiourl = audiourl;
        this.txtcallbackurl = txtcallbackurl;
    }

    @Override
    public String toString() {
        return "AVSTAsrParam_reg{" +
                "audiourl='" + audiourl + '\'' +
                ", txtcallbackurl='" + txtcallbackurl + '\'' +
                "} " + super.toString();
    }
}
