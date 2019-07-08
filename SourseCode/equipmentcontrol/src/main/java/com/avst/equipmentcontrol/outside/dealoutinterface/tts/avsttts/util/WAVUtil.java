package com.avst.equipmentcontrol.outside.dealoutinterface.tts.avsttts.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class WAVUtil {

	private static byte[]  RIFF="RIFF".getBytes();  
    private static byte[] RIFF_SIZE=new byte[4];  
    private static byte[] RIFF_TYPE="WAVE".getBytes();  
      
      
    private static byte[] FORMAT="fmt ".getBytes();  
    private static byte[] FORMAT_SIZE=new byte[4];  
    private static byte[] FORMATTAG=new byte[2];  
    private static byte[] CHANNELS=new byte[2];  
    private static byte[] SamplesPerSec =new byte[4];  
    private static byte[] AvgBytesPerSec=new byte[4];  
    private static byte[] BlockAlign =new byte[2];  
    private static byte[] BitsPerSample =new byte[2];  
      
    private static byte[] DataChunkID="data".getBytes();  
    private static byte[] DataSize=new byte[4];  
    public static boolean isrecording=false;  
	
	
	
	public static boolean writeFile(InputStream in,String filepath){
		
		int toaldatasize=0;  
	    int audiolen;  
	    byte[] audiochunk=new byte[1024];  
	//因为文件需要顺序读写，并且只能在最后才能确定riffsize和datasize参数，所以对前面的data要缓存。  
	    ByteArrayOutputStream bytebuff=new ByteArrayOutputStream(9600000);  
	     
	    try {
			try {
				while((audiolen=in.read(audiochunk)) > 0){
							    		 
					toaldatasize+=audiolen;  
					bytebuff.write(audiochunk, 0, audiolen);  
				}
			     
			} catch (IOException e1) {  
			    e1.printStackTrace();  
			}  
			  
			DataSize=revers(intToBytes(toaldatasize));  
			RIFF_SIZE=revers(intToBytes(toaldatasize+36-8));  
			File wavfile= new File(filepath);  
			FileOutputStream file=null;  
			BufferedOutputStream fw=null;
			 try {  
			    file=new FileOutputStream(wavfile);  
			    fw=new BufferedOutputStream(file);  
			    init();  
			   
			    fw.write(RIFF);  
			    fw.write(RIFF_SIZE);  
			    fw.write(RIFF_TYPE);  
			    fw.write(FORMAT);  
			    fw.write(FORMAT_SIZE);  
			    fw.write(FORMATTAG);  
			    fw.write(CHANNELS);  
			    fw.write(SamplesPerSec);  
			    fw.write(AvgBytesPerSec);  
			    fw.write(BlockAlign);  
			    fw.write(BitsPerSample);  
			      
			    fw.write(DataChunkID);  
			    fw.write(DataSize);  
			    fw.write(bytebuff.toByteArray());

			    return true;
			 } catch (IOException e) {  
		        e.printStackTrace();  
		    }finally{
				try {
					if(null!=fw){
						fw.flush();  
					    fw.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				try {
					if(null!=file){
						file.flush();
						file.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			} 
		} catch (Exception e) {
			
			e.printStackTrace();
		} finally{
			try {
				if(null!=bytebuff){
					bytebuff.flush();
					bytebuff.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} 
		return false;
	}
	
	
	public static void init(){  
		//这里主要就是设置参数，要注意revers函数在这里的作用  
		       
	     FORMAT_SIZE=new byte[]{(byte)16,(byte)0,(byte)0,(byte)0};  
	     byte[] tmp=revers(intToBytes(1));  
	     FORMATTAG=new byte[]{tmp[0],tmp[1]};  
	     CHANNELS=new byte[]{tmp[0],tmp[1]};  
	     SamplesPerSec=revers(intToBytes(16000));  
	     AvgBytesPerSec=revers(intToBytes(32000));  
	      tmp=revers(intToBytes(2));  
	     BlockAlign=new byte[]{tmp[0],tmp[1]};  
	     tmp=revers(intToBytes(16));  
	     BitsPerSample=new byte[]{tmp[0],tmp[1]};  
	 }  
	 public static byte[] revers(byte[] tmp){  
	     byte[] reversed=new byte[tmp.length];  
	     for(int i=0;i<tmp.length;i++){  
	         reversed[i]=tmp[tmp.length-i-1];  
	                           
	     }  
	     return reversed;  
	 }  
	 public static byte[] intToBytes(int num){  
	     byte[]  bytes=new byte[4];  
	     bytes[0]=(byte)(num>>24);  
	     bytes[1]=(byte)((num>>16)& 0x000000FF);  
	     bytes[2]=(byte)((num>>8)& 0x000000FF);  
	     bytes[3]=(byte)(num & 0x000000FF);  
	     return bytes;  
	       
	 }  
	
	
	 
	    
	    public static void main(String[] args) {
			
	    	
		}
	 
	
	
}
