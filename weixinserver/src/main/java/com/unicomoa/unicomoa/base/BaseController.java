package com.unicomoa.unicomoa.base;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.google.gson.Gson;

public class BaseController {
	
	private String AJAX_HEADER = "X-Requested-With";
	protected Logger logger = LoggerFactory.getLogger(BaseController.class);
	
	@Resource
	protected HttpServletRequest request;
	
	@Resource
	protected HttpServletResponse response;

	@ExceptionHandler(value = Exception.class)
	public String handleException(Exception e) throws ServletException, IOException{
		logger.info("-------------------------------"+request.getRequestURI());
		e.printStackTrace();
		String ajaxHeader = request.getHeader(AJAX_HEADER);
		if(StringUtils.isEmpty(ajaxHeader)){
			return "/error";
		}else{
			response.getWriter().write(new Gson().toJson(ERROR("服务异常")));
			response.getWriter().flush();
		}
		return null;
	}
	
	public Object SUCCESS(Object data){
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("result", "success");
		result.put("data", data);
		return result;
	}
	
	public Object SUCCESS(){
		return SUCCESS(null);
	}
	
	public Object ERROR(String desc){
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("result", "fail");
		result.put("data", desc);
		return result;
	}
	
}
