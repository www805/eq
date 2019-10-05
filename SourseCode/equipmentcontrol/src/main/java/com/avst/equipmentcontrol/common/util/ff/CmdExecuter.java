package com.avst.equipmentcontrol.common.util.ff;

import com.avst.equipmentcontrol.common.util.LogUtil;

import java.io.*;
import java.util.List;

/** 
 * CmdExecuter 
 * <p>Title: 命令执行器</p> 
 * <p>Description: 封装对操作系统命令行发送指令相关操作</p>
 * 同时最后只有1个cmd。要不然就不要返回的输出数据
 */  
public class CmdExecuter {  
      
    /** 
     * 执行指令 
     * @param cmd 执行指令 
     * @param getter 指令返回处理接口，若为null则不处理输出 
     */  
    public static void exec( List<String> cmd, IStringGetter getter,String inputurl ){  
    	 ProcessBuilder builder=null;
    	 Process proc=null;
        try {
            builder = new ProcessBuilder();    
            builder.command(cmd);  
            proc = builder.start();
			wait(proc);
            proc.waitFor();
			proc.destroy();
			builder.directory();
            LogUtil.intoLog("线程关闭："+inputurl);
			LogUtil.intoLog("执行返回结果："+result);

        } catch (Exception e) {  
            e.printStackTrace();  
        }finally{


        }  
    }

    private static String result=null;//上一个结果的返回

    private static void wait(Process p){

    	result="";//初始化输出

		InputStream in1= p.getInputStream();
		InputStream in2= p.getErrorStream();
		OutputStream out= p.getOutputStream();

		try {
			//正确输出
			new Thread(new Runnable() {
				@Override
				public void run() {
					Reader reader = new InputStreamReader(in1);
					BufferedReader bf = new BufferedReader(reader);
					String line = null;
					String str="";
					try {
						while((line=bf.readLine())!=null) {
							str+=line;
							System.out.println(line);
						}
						result=str;
					} catch (IOException e) {
						e.printStackTrace();
					}finally {
						try {
							if(null!=bf){
								bf.close();
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						try {
							if(null!=reader){
								reader.close();
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						try {
							if(null!=in1){
								in1.close();
							}
						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				}
			}).start();

			//错误输出
			new Thread(new Runnable() {
				@Override
				public void run() {
					Reader reader = new InputStreamReader(in2);
					BufferedReader bf = new BufferedReader(reader);
					String line = null;
					try {
						while((line=bf.readLine())!=null) {
							System.out.println(line);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}finally {
						try {
							if(null!=bf){
								bf.close();
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						try {
							if(null!=reader){
								reader.close();
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						try {
							if(null!=in2){
								in2.close();
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}).start();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				if(null!=out){
					out.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}
} 