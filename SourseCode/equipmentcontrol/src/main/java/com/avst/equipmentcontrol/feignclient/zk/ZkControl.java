package com.avst.equipmentcontrol.feignclient.zk;


import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.common.util.baseaction.ReqParam;
import com.avst.equipmentcontrol.feignclient.base.vo.ControlInfoParamVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient("zk")//, fallback = ClientResult.class
public interface ZkControl {

    /**
     * 获取注册的服务器
     */
    @RequestMapping( value = "/zk/getControlInfoAll")
    @ResponseBody
    public RResult getControlInfoAll();

    /**
     * 获取服务器时间
     * @return
     */
    @RequestMapping( value = "/zk/getControlTime")
    @ResponseBody
    public RResult getControlTime();

    /**
     * 心跳接口
     * @param param
     * @return
     */
    @RequestMapping(value = "/zk/getHeartbeat")
    public RResult getHeartbeat(@RequestBody ReqParam<ControlInfoParamVO> param);

}
