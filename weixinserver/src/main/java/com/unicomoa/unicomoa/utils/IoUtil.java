package com.unicomoa.unicomoa.utils;


import java.io.Closeable;
import java.io.InputStream;
import java.io.OutputStream;

public class IoUtil{
    
	public static void safeClose(InputStream is){
		if(is != null){
			try{
				is.close();
			}catch(Exception e){}
		}
	}
	
	public static void safeClose(OutputStream os){
		if(os != null){
			try{
				os.close();
			}catch(Exception e){}
		}
	}
	
	public static void safeClose(Closeable c){
		if(c != null){
			try{
				c.close();
			}catch(Exception e){}
		}
	}	
}