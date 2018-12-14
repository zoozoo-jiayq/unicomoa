package cn.com.qytx.oa.util;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

public class RequestParamter implements Serializable{
	/**
	 * 获取request请求的参数，避免空
	 * @param request
	 * @param par
	 * @return
	 */
	public String getParameterStr(HttpServletRequest request,String par){
		String res="";
		if(request.getParameter(par)!=null){
			res = request.getParameter(par).toString();
		}
	    return res;
	}
	
	/**
	 * 获取request请求的参数，避免空指针
	 * @param request
	 * @param par
	 * @return
	 */
	public int getParameterInt(HttpServletRequest request,String par){
		int res=0;
		if(request.getParameter(par)!=null){
			res = Integer.parseInt(request.getParameter(par).toString());
		}
	    return res;
	}
	
	
	
}
