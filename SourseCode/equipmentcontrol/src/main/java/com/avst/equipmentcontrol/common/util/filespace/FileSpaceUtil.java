package com.avst.equipmentcontrol.common.util.filespace;

import com.avst.equipmentcontrol.common.util.JacksonUtil;
import com.avst.equipmentcontrol.common.util.LogUtil;
import org.apache.commons.lang.StringUtils;
import sun.rmi.runtime.Log;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.text.DecimalFormat;

public class FileSpaceUtil {


    public static String FormetFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }

    /**
     * 获取硬盘的盘符对应的磁盘的信息
     * win可用
     * @param driverName ALL或者空所有磁盘一起算，A,B,C,D,E,F,G,H,I单算
     */
    public static DriverSpaceParam driver(String driverName){

        try {
            if(StringUtils.isNotEmpty(driverName)){
                driverName=toUpper(driverName.trim());
            }else{
                driverName="ALL";
            }
            DriverSpaceParam driverSpaceParam=new DriverSpaceParam();
            if(driverName.equals("ALL")){
                driverSpaceParam.setDriverName("ALL");
            }else if(!driverName.endsWith(":")){
                driverName=driverName+":";
            }else{
                driverSpaceParam.setDriverName(driverName);
            }

            // 当前文件系统类
            FileSystemView fsv = FileSystemView.getFileSystemView();
            // 列出所有windows 磁盘
            File[] fs = File.listRoots();
            long freespace=0;
            long totalspace=0;
            // 显示磁盘卷标
            for (int i = 0; i < fs.length; i++) {
                String drivername=fsv.getSystemDisplayName(fs[i]);
                if(driverName.equals("ALL")){
                    freespace+=fs[i].getFreeSpace();
                    totalspace+=fs[i].getTotalSpace();
                }else if(drivername.indexOf(driverName) > -1){

                    driverSpaceParam.setFreeSpace(fs[i].getFreeSpace());
                    driverSpaceParam.setFreeSpace_str(FormetFileSize(fs[i].getFreeSpace()));
                    driverSpaceParam.setTotalSpace_str(FormetFileSize(fs[i].getTotalSpace()));
                    driverSpaceParam.setTotalSpace(fs[i].getTotalSpace());
                    return driverSpaceParam;
                }
            }
            driverSpaceParam.setFreeSpace(freespace);
            driverSpaceParam.setFreeSpace_str(FormetFileSize(freespace));
            driverSpaceParam.setTotalSpace_str(FormetFileSize(totalspace));
            driverSpaceParam.setTotalSpace(totalspace);
            return driverSpaceParam;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取文件夹对应的容量信息
     * win可用
     * @param filepath 文件路径
     */
    public static DriverSpaceParam getFilePathSpace(String filepath){

        try {
            if(StringUtils.isEmpty(filepath)){
                return null;
            }
            DriverSpaceParam driverSpaceParam=new DriverSpaceParam();

            File file=new File(filepath);
            if(null!=file&&file.exists()){
                long freespace=file.getFreeSpace();
                long totalspace=file.getTotalSpace();
                driverSpaceParam.setDriverName(filepath);
                driverSpaceParam.setFreeSpace(freespace);
                driverSpaceParam.setFreeSpace_str(FormetFileSize(freespace));
                driverSpaceParam.setTotalSpace_str(FormetFileSize(totalspace));
                driverSpaceParam.setTotalSpace(totalspace);
                return driverSpaceParam;
            }else{
                LogUtil.intoLog(4,FileSpaceUtil.class,filepath+":filepath 文件路径查询为空");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     * 小写转大写
     */
    public static String  toUpper(String str) {

        if(null==str||str.length()==0){
            return null;
        }
        char[] ch=str.toCharArray();
        String rr="";
        for(char c:ch){
            rr+=Character.isLowerCase(c) ? Character.toUpperCase(c):c;
        }
        return rr;
    }


    public static void main(String[] args) {
        System.out.println(JacksonUtil.objebtToString(getFilePathSpace("D:\\ftpdata")));
    }

}
