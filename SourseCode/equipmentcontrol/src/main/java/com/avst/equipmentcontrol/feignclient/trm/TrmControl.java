package com.avst.equipmentcontrol.feignclient.trm;

import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.common.util.baseaction.ReqParam;
import com.avst.equipmentcontrol.feignclient.trm.req.UserloginParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("trm")
public interface TrmControl {

    //提供给总控的登录账号密码
    @RequestMapping("/trm/v1/getLoginUser")
    public RResult getLoginUser(@RequestBody ReqParam<UserloginParam> param);


    //获取trm当前登录的用户信息
    @RequestMapping("/trm/v1/getUserPwd")
    public RResult getUserPwd(@RequestBody ReqParam param);

}