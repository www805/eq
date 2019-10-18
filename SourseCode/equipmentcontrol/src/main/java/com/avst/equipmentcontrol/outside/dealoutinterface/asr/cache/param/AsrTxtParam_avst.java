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
     * //本句话的开始时间，基于本次语音识别开始的ms数值，
     * 这个参数2个作用，1用于标记在总录音的位置，2用来区分句与句之间的间隔，
     * 每句话的time时间不同
     */
    private String time;

    @XmlElement
    private String id;

    @XmlElement
    private String type;

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
