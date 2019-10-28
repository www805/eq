package com.avst.equipmentcontrol.outside.dealoutinterface.asr.cache.param;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * avst asr识别服务器返回的数据存储集合
 * 一句话的语音识别只会有一条数据，后来的会自动覆盖前面的
 */
@XmlRootElement//根节点
public class AsrTxtParam_avst {

    @XmlElement
    private String msg;

    @XmlElement
    /**
     * 跟msg是一样的意思，lyj个坑货适配2个asr类型
     */
    private String txt;

    @XmlElement
    private String endtime;

    @XmlElement
    /**
     * //本句话的开始时间，基于本次语音识别开始的ms数值，
     * 这个参数2个作用，1用于标记在总录音的位置，2用来区分句与句之间的间隔，
     * 每句话的time时间不同
     */
    private String time;

    @XmlElement
    /**
     * 跟time是一样的意思，lyj个坑货适配2个asr类型
     */
    private String starttime;

    @XmlElement
    private String id;

    @XmlElement
    private String type;

    @XmlElement
    /**
     * 音频通道的索引，如果是单asrid对应多路语音识别的话，就需要这个参数来标记到底是哪个麦克风
     */
    private String index;

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "AsrTxtParam_avst{" +
                "msg='" + msg + '\'' +
                ", time='" + time + '\'' +
                ", id='" + id + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
