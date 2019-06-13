package test;

import com.avst.equipmentcontrol.common.util.JacksonUtil;
import com.avst.equipmentcontrol.common.util.LogUtil;
import com.avst.equipmentcontrol.common.util.XMLUtil;
import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.FDDealImpl;
import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.xmljsonobject.GetETRecordByIidXml;
import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.xmljsonobject.UploadFileByPathXml;
import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.xmljsonobject.Xml2Object;
import com.avst.equipmentcontrol.outside.dealoutinterface.polygraph.cmcross.v1.vo.XBOX_GetResultVO;

public class Test2 {

    public static String rr="{\"age\":0,\"bp\":0,\"br\":0.0002894740900956094,\"emotion\":6,\"face_rect\":{\"height\":121,\"width\":121,\"x\":159,\"y\":94},\"fps\":29.97003173828125,\"gender\":0,\"have_face\":true,\"hr\":0,\"hr_snr\":0,\"hrv\":0,\"relax\":0,\"spo2\":0,\"status\":0,\"stress\":0,\"stress_snr\":0}";

    public static void main(String[] args) {

        XBOX_GetResultVO vo= (XBOX_GetResultVO)JacksonUtil.stringToObjebt_1(rr,XBOX_GetResultVO.class);
        LogUtil.intoLog(Test2.class,JacksonUtil.objebtToString(vo));



    }

}
