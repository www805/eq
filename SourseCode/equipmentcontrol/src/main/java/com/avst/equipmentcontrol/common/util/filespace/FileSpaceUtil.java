package com.avst.equipmentcontrol.common.util.filespace;

import com.avst.equipmentcontrol.common.util.JacksonUtil;
import com.avst.equipmentcontrol.common.util.LogUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import sun.rmi.runtime.Log;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

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
            driverSpaceParam.setDriverName(driverName);
            if(driverName.equals("ALL")){
                driverSpaceParam.setDriverName("ALL");
            }else if(!driverName.endsWith(":")){
                driverName=driverName+":";
            }
            driverSpaceParam.setDriverPath(driverName);
            driverSpaceParam.setFolderBool(true);

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
     * 获取文件夹对应的下一级所有文件/文件夹的容量信息
     * win可用
     * @param filepath 文件路径
     */
    public static List<DriverSpaceParam> getFilePathSpaceByParentNodePath(String filepath){

        try {
            if(StringUtils.isEmpty(filepath)){
                return null;
            }
            List<String> nextNodePaths=getAllFilePath(filepath);
            if(null==nextNodePaths||nextNodePaths.size()==0){
                LogUtil.intoLog(4,FileSpaceUtil.class,filepath+":filepath 文件路径查询为空");
                return null;
            }
            List<DriverSpaceParam> spacelist=new ArrayList<DriverSpaceParam>();
            for(String path:nextNodePaths){
                spacelist.add(getFilePathSpace(path));
            }
            return spacelist;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取文件夹/文件夹对应的容量信息
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
            long usespace=file.length();
            if(null!=file&&file.exists()){

                if(file.isDirectory()){
                    driverSpaceParam.setFolderBool(true);
                    usespace = getTotalSizeOfFilesInDir(file);
                }
                long freespace=file.getFreeSpace();
                long totalspace=file.getTotalSpace();
                driverSpaceParam.setDriverName(getsavename(filepath));
                driverSpaceParam.setDriverPath(filepath);
                driverSpaceParam.setFreeSpace(freespace);
                driverSpaceParam.setUseSpace(usespace);
                driverSpaceParam.setFreeSpace_str(FormetFileSize(freespace));
                driverSpaceParam.setTotalSpace_str(FormetFileSize(totalspace));
                driverSpaceParam.setUseSpace_str(FormetFileSize(usespace));
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

    /**
     * 获取文件夹下一级的所有文件的路径
     * @param basepath 获取文件路径的文件夹的路径
     * @return
     */
    public static List<String> getAllFilePath(String basepath){

        File file_base = new File(basepath);
        try {
            List<String> filelist = new ArrayList<String>();
            if (file_base.exists()){
                File[] fs = file_base.listFiles();
                for (int i = 0; i < fs.length; i++) {
                    //只存文件路径
                    File file=fs[i];
                    if(file.exists()){
                       filelist.add(file.getAbsolutePath());
                    }
                }
                return filelist;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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

    /**
     * 获取文件路径下的文件名
     * @param filePath
     * @return
     */
    public static String getsavename(String filePath) {

        if(StringUtils.isEmpty(filePath)){
            LogUtil.intoLog(4,FileSpaceUtil.class,"is null filePath:"+filePath);
            return null;
        }
        String savename=null;
        if(filePath.indexOf("\\") > -1){
            savename=filePath.substring(filePath.lastIndexOf("\\")+1);
        }else if(filePath.indexOf("/") > -1){
            savename=filePath.substring(filePath.lastIndexOf("/")+1);
        }else{
            LogUtil.intoLog(4,FileSpaceUtil.class,"getsavename filePath.indexOf(\\) and filePath.indexOf(/) is < 0,filePath:"+filePath);
            return null;
        }

        return savename;
    }
    /**
     * 获取文件所在文件夹路径
     * @param filePath
     * @return
     */
    public static String getsavepath(String filePath) {

        if(StringUtils.isEmpty(filePath)){
            LogUtil.intoLog(4,FileSpaceUtil.class,"is null filePath:"+filePath);
            return null;
        }
        String savapath=null;
        if(filePath.indexOf("\\") >= 0){
            savapath=filePath.substring(0,filePath.lastIndexOf("\\"));
        }else if(filePath.indexOf("/") >= 0){
            savapath=filePath.substring(0,filePath.lastIndexOf("/"));
        }else{
            LogUtil.intoLog(4,FileSpaceUtil.class,"getsavepath filePath.indexOf(\\) and filePath.indexOf(/) is < 0,filePath:"+filePath);
            return null;
        }

        return savapath;
    }

    // 递归方式 计算文件的大小
    private static long getTotalSizeOfFilesInDir(final File file) {
        if (file.isFile())
            return file.length();
        final File[] children = file.listFiles();
        long total = 0;
        if (children != null)
            for (final File child : children)
                total += getTotalSizeOfFilesInDir(child);
        return total;
    }

    public static void main(String[] args) {
        System.out.println(JacksonUtil.objebtToString(getFilePathSpace("D:\\ftpdata")));
        System.out.println(JacksonUtil.objebtToString(getAllFilePath("D:\\ftpdata")));
        System.out.println(JacksonUtil.objebtToString(getFilePathSpaceByParentNodePath("D:\\ftpdata")));
    }

}
