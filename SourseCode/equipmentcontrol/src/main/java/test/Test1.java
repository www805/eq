package test;

import com.avst.equipmentcontrol.common.util.JacksonUtil;
import com.avst.equipmentcontrol.common.util.LogUtil;
import com.avst.equipmentcontrol.common.util.baseaction.ReqParam;
import com.avst.equipmentcontrol.outside.dealoutinterface.asr.avstasr.vo.AvstSDKInterfaceBackParam_Dealreg;
import com.avst.equipmentcontrol.outside.interfacetoout.asr.req.GetAsrServerBySsidParam;

import java.io.File;

public class Test1 {

    public static void main(String[] args) {

        String path="/ds/fh//njy/56/nh.txt";
        path=path.substring(path.lastIndexOf("/")+1);
        System.out.println(path.split("\\.")[0]);


    }
}
