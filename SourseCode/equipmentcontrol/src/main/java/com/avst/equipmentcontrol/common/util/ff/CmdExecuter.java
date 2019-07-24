package com.avst.equipmentcontrol.common.util.ff;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/** 
 * CmdExecuter 
 * <p>Title: 命令执行器</p> 
 * <p>Description: 封装对操作系统命令行发送指令相关操作</p> 
 */  
public class CmdExecuter {  
      
    /** 
     * 执行指令 
     * @param cmd 执行指令 
     * @param getter 指令返回处理接口，若为null则不处理输出 
     */  
    public static void exec( List<String> cmd, IStringGetter getter,String inputurl ){  
    	 ProcessBuilder builder=null;
    	 BufferedReader stdout=null;
    	 Process proc=null;
    	 InputStreamReader in=null;
        try {  
            builder = new ProcessBuilder();    
            builder.command(cmd);  
            builder.redirectErrorStream(true);  
            proc = builder.start();  
            in= new InputStreamReader(proc.getInputStream());
            stdout = new BufferedReader( in );  
            String line;  
            while ((line = stdout.readLine()) != null) {  
                if( getter != null )
					try {
						getter.dealString(line,inputurl);
					} catch (Exception e) {
						e.printStackTrace();
					}  
            }  
            proc.waitFor(); 
            System.out.println("线程关闭："+inputurl);
            stdout.close(); 
           
        } catch (Exception e) {  
            e.printStackTrace();  
        }finally{
        	try {
				if(null!=stdout){
					stdout.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
        	try {
				if(null!=in){
					in.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
        	try {
				if(null!=proc){
					 proc.destroy();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
        	try {
				if(null!=builder){
					builder.directory();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
        }  
    }  
} 