package cn.com.qytx.oa.exception;
/*
 * OA 自定义异常
 */
public class OAException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public  OAException(String msg)   {  
        super(msg);  
   } 
	
}
