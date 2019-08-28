package test;

import com.avst.equipmentcontrol.common.util.JacksonUtil;
import com.avst.equipmentcontrol.common.util.LogUtil;
import com.avst.equipmentcontrol.common.util.baseaction.ReqParam;
import com.avst.equipmentcontrol.outside.dealoutinterface.asr.avstasr.vo.AvstSDKInterfaceBackParam_Dealreg;
import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.xmljsonobject.param.File;
import com.avst.equipmentcontrol.outside.interfacetoout.asr.req.GetAsrServerBySsidParam;

import java.util.ArrayList;
import java.util.List;

public class Test1 {

    public static void main(String[] args) {

        List<File> fileList=new ArrayList<File>();
        File file=new File();
        file.setStime("123456789");
        fileList.add(file);
        file=new File();
        file.setStime("123456788");
        fileList.add(file);
        file=new File();
        file.setStime("123456782");
        fileList.add(file);
        file=new File();
        file.setStime("123456786");
        fileList.add(file);
        if(null!=fileList&&fileList.size() > 0){

            for(int i=0;i<fileList.size()-1;i++) {
                for(int j=0;j<fileList.size()-i-1;j++) {
                    try {
                        File file1 = fileList.get(j);
                        File file2 = fileList.get(j + 1);
                        long stime1 = Long.parseLong(file1.getStime());
                        long stime2 = Long.parseLong(file2.getStime());
                        if (stime1 > stime2) {
                            File temp = file1;
                            file1 = file2;
                            file2 = temp;
                            fileList.set(j,file1);
                            fileList.set((j+1),file2);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println(fileList.size());
        }


    }
}
