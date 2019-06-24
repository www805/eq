package com.avst.equipmentcontrol.feignclient.zk;


import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.feignclient.base.ClientResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(value = "zk", url = "localhost:8079/")//, fallback = ClientResult.class
public interface ZkControl {

    @RequestMapping(value = "/zk/getControlTime")
    @ResponseBody
    public RResult getControlTime();

}
