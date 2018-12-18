package com.unicomoa.unicomoa.utils;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;


public class IoUtils{
    
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
	
	public static String toString(final InputStream input){
		return toString(input, "UTF-8");
	}
	
	public static String toString(final InputStream input, String encoding){
		return toString(input, encoding, 4096);
	}
	
    public static String toString(final InputStream input, String encoding, 
    		final int bufferSize){
        final StringWriter sw = new StringWriter();
        try{
	        final Reader rd = new InputStreamReader(input,encoding);
	        copy( rd, sw, bufferSize );
	        return sw.toString();
        }catch(Exception e){
        	e.printStackTrace();
        	throw new RuntimeException(e);
        }
    }
    
    public static void copy(final Reader input, final Writer output, 
    		final int bufferSize ){
		final char[] buf = new char[bufferSize];
		int n = 0;
		try{
			while(true){
				n = input.read(buf);
				if(n <= 0)
				   return;
				output.write(buf,0,n);
			}
		}catch(IOException e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
    
    public static void copy(final InputStream input, final OutputStream output,
            final int bufferSize ){
		final byte[] buf = new byte[bufferSize];
		int n = 0;
		try{
			while(true){
				n = input.read(buf);
				if(n <= 0)
				   return;
				output.write(buf,0,n);
			}
		}catch(IOException e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
    
    public static void copy(final InputStream input, final OutputStream output){
    	copy(input,output,4096);
    }
    
   
	
}