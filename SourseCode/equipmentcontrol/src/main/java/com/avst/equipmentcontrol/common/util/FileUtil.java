package com.avst.equipmentcontrol.common.util;


import java.io.File;
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
}
