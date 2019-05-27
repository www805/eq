package test;

import com.avst.equipmentcontrol.common.util.JacksonUtil;
import com.avst.equipmentcontrol.common.util.baseaction.ReqParam;
import com.avst.equipmentcontrol.outside.dealoutinterface.asr.avstasr.vo.AvstSDKInterfaceBackParam_Dealreg;
import com.avst.equipmentcontrol.outside.interfacetoout.asr.req.GetAsrServerBySsidParam;

public class Test1 {

    public static void main(String[] args) {

        ReqParam<GetAsrServerBySsidParam> param=new ReqParam<GetAsrServerBySsidParam>();
        GetAsrServerBySsidParam getAsrServerBySsidParam=new GetAsrServerBySsidParam();
        getAsrServerBySsidParam.setAsrEquipmentSsid("123456");
        param.setParam(getAsrServerBySsidParam);
        System.out.println(JacksonUtil.objebtToString(param));

    }
}
