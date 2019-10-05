package test;

import com.avst.equipmentcontrol.common.util.JacksonUtil;
import com.avst.equipmentcontrol.common.util.LogUtil;
import com.avst.equipmentcontrol.common.util.XMLUtil;
import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.FDDealImpl;
import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.xmljsonobject.GetETRecordByIidXml;
import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.xmljsonobject.UploadFileByPathXml;
import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.xmljsonobject.Xml2Object;
import com.avst.equipmentcontrol.outside.dealoutinterface.polygraph.cmcross.v1.vo.XBOX_GetResultVO;
import com.avst.equipmentcontrol.outside.dealoutinterface.polygraph.cmcross.v1.vo.XBOX_ShutdownVO;

public class Test2 {

    public static String rr="<root>\n" +
            "<version>AICBH:1.0</version>\n" +
            "<log t=\"disk\" totalpage=\"16\" currpage=\"1\">\n" +
            "<item date=\"20190919085053\" tp=\"sys_oper\">系统关闭语音激励</item>\n" +
            "<item date=\"20190919110644\" tp=\"ftp_oper\">admin[192.168.17.175]=>请求预览视频通道0</item>\n" +
            "<item date=\"20190919110825\" tp=\"ftp_oper\">重启设备 界面[操作] 来自 操作</item>\n" +
            "<item date=\"20190919110918\" tp=\"sys_oper\">系统关闭语音激励</item>\n" +
            "<item date=\"20190919110936\" tp=\"ftp_oper\">开始硬盘录像成功(手动) 录像文件: 2019-09-19-11-09-35.ts</item>\n" +
            "<item date=\"20190919110936\" tp=\"sys_oper\">自动执行硬盘码率设定</item>\n" +
            "<item date=\"20190919110948\" tp=\"sys_oper\">\n" +
            "检测上传文件名 [/tmp/hd0/2019-09-19/IwDI6o89j4vq/2019-09-19-11-09-35_info.st]\n" +
            "</item>\n" +
            "<item date=\"20190919110948\" tp=\"sys_oper\">检测上传文件名 类型:0:4 []</item>\n" +
            "<item date=\"20190919110948\" tp=\"sys_oper\">\n" +
            "检测上传文件名 [/tmp/hd0/2019-09-19/IwDI6o89j4vq/2019-09-19-11-09-35_info.st]\n" +
            "</item>\n" +
            "<item date=\"20190919110948\" tp=\"sys_oper\">检测上传文件名 类型:0:4 []</item>\n" +
            "<item date=\"20190919112140\" tp=\"sys_oper\">硬盘录像分包成功 录像文件: 2019-09-19-11-09-35_01.ts</item>\n" +
            "<item date=\"20190919112159\" tp=\"sys_oper\">\n" +
            "检测上传文件名 [/tmp/hd0/2019-09-19/IwDI6o89j4vq/2019-09-19-11-09-35_01_info.st]\n" +
            "</item>\n" +
            "<item date=\"20190919112159\" tp=\"sys_oper\">检测上传文件名 类型:0:4 []</item>\n" +
            "<item date=\"20190919112159\" tp=\"sys_oper\">\n" +
            "检测上传文件名 [/tmp/hd0/2019-09-19/IwDI6o89j4vq/2019-09-19-11-09-35_01_info.st]\n" +
            "</item>\n" +
            "<item date=\"20190919112200\" tp=\"sys_oper\">检测上传文件名 类型:0:4 []</item>\n" +
            "</log>\n" +
            "</root>";

    public static void main(String[] args) {

        XBOX_GetResultVO vo= (XBOX_GetResultVO)JacksonUtil.stringToObjebt_1(rr, XBOX_ShutdownVO.class);
        LogUtil.intoLog(Test2.class,JacksonUtil.objebtToString(vo));



    }

}
