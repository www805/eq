package com.avst.equipmentcontrol.feignclient.mc;


import com.avst.equipmentcontrol.common.util.baseaction.ReqParam;
import com.avst.equipmentcontrol.feignclient.mc.req.SetMCAsrTxtBackParam_out;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 设备控制的代理
 */
@FeignClient(name = "mc", url = "localhost:8082/")
public interface MeetingControl {

    //avstmt
    @RequestMapping( value = "/mt/v1/setMCAsrTxtBack")
    @ResponseBody
    public boolean setMCAsrTxtBack(@RequestBody ReqParam<SetMCAsrTxtBackParam_out> param);





}
