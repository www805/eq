package test;

import com.avst.equipmentcontrol.common.util.JacksonUtil;
import com.avst.equipmentcontrol.common.util.baseaction.ReqParam;
import com.avst.equipmentcontrol.outside.dealoutinterface.asr.avstasr.vo.AvstSDKInterfaceBackParam_Dealreg;
import com.avst.equipmentcontrol.outside.interfacetoout.asr.req.GetAsrServerBySsidParam;

import java.io.File;

public class Test1 {

    public static void main(String[] args) {

        String path="D:/ftpdata/sb1/2019-05-29/f3c250b067dd4402bca0874f54f50476_sxsba1/2019-05-29-16-37-10.ts";
        File file=new File(path);
        System.out.println(file.length());

    }
}
