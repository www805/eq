package com.avst.equipmentcontrol.outside.interfacetoout.asr.cache.param;

import com.avst.equipmentcontrol.outside.interfacetoout.asr.conf.AsrHeartbeatThread;

/**
 * 语音识别服务器的信息
 */
public class AsrMessageParam<T> {

    private String asrtype;

    private AsrHeartbeatThread<T> asrHeartbeatThread;

    public String getAsrtype() {
        return asrtype;
    }

    public void setAsrtype(String asrtype) {
        this.asrtype = asrtype;
    }

    public AsrHeartbeatThread<T> getAsrHeartbeatThread() {
        return asrHeartbeatThread;
    }

    public void setAsrHeartbeatThread(AsrHeartbeatThread<T> asrHeartbeatThread) {
        this.asrHeartbeatThread = asrHeartbeatThread;
    }
}
