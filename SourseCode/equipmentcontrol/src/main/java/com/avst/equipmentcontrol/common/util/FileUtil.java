package com.avst.equipmentcontrol.common.util;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    /**
     *
     * @param dir 获取文件路径的文件夹的路径de file
     * @param isfilespath 是否显示里面的文件夹的路径2/1 2是需要1是不需要
     * @param  filelist 最后的返回结果，中间桥梁作用
     * @return
     */
    public static List<String> getAllFiles(File dir, List<String> filelist, int isfilespath){
        try {
            File[] fs = dir.listFiles();
            if(null==fs||fs.length==0){
                return null;
            }
            for (int i = 0; i < fs.length; i++) {
                //只存文件路径
                File file=fs[i];
                if(file.exists()){
                    if(2==isfilespath){

                        filelist.add(file.getAbsolutePath());
                    }else if(1==isfilespath ){
                        if(!file.isDirectory()){
                            filelist.add(file.getAbsolutePath());
                        }
                    }

                }
                //若为文件夹，就调用getAllFiles方法
                if (file.isDirectory()) {
                    try {
                        getAllFiles(file, filelist, isfilespath);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filelist;
    }

    /**
     * 通过源路径找到这个路径的所有文件的路径
     * @param basepath
     * @param isfilespath 是否显示里面的文件夹的路径2/1 2是需要1是不需要
     * @return
     */
    public static List<String> getAllFiles(String basepath, int isfilespath){
        try {
            if(null==basepath||basepath.trim().equals("")){
                return null;
            }
            File dir=new File(basepath);

            List<String> filelist=new ArrayList<String>();
            return getAllFiles(dir,filelist,isfilespath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过源路径找到这个路径下的下一个文件列表
     * 文件和文件夹在一块
     * @param basepath
     * @return
     */
    public static List<String> getAllFiles_OneClass(String basepath){
        try {
            if(null==basepath||basepath.trim().equals("")){
                return null;
            }
            File dir=new File(basepath);

            List<String> filelist=new ArrayList<String>();
            File[] fs = dir.listFiles();
            if(null==fs||fs.length==0){
                return null;
            }
            for (int i = 0; i < fs.length; i++) {
                filelist.add(fs[i].getAbsolutePath());
            }
            return filelist;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除单个文件
     * @param   sPath    被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    //删除指定文件夹下的所有文件
    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
                flag = true;
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);//再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }

    //删除文件夹
    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); //删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            java.io.File myFilePath = new java.io.File(filePath);
            myFilePath.delete(); //删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除文件路径下的指定类型的文件
     */
    public static void delFileByType(String basepath,String type){

        try {
            List<String> pathlist=getAllFiles(basepath,1);
            if(null!=pathlist&&pathlist.size() > 0){
                for(String path:pathlist){
                    if(path.endsWith(type)){
                        try {
                            deleteFile(path);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }else{
                System.out.println(basepath+":basepath,getAllFiles is null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
