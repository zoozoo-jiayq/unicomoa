package cn.com.qytx.platform.base.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.qytx.platform.base.query.Page;

import com.google.gson.Gson;

public abstract class BaseControllerSupport {
	protected static final String SPLIT = "||";
	//成功状态码
	private static final Integer SUCCESS_FLAG = 100;
	//服务/错误/异常
	private static final Integer SERVER_ERROR = 101;
	//参数错误
	private static final Integer PARAMETER_ERROR = 102;
    /**
     * 以datatable插件能是被的格式输出分页查询结果
     * @param page 分页查询结果
     */
    public void SUCCESS_PAGE(Page page,HttpServletResponse response){
        SUCCESS_PAGE(page,null,response);
    }

    /**
     * 以datatable插件能是被的格式输出分页查询结果
     * @param page 分页对象
     * @param list 分页查询记录集
     */
    public void SUCCESS_PAGE(Page page,List list,HttpServletResponse response){
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("iTotalRecords", page.getTotalElements());
        jsonMap.put("iTotalDisplayRecords", page.getTotalElements());
        jsonMap.put("aaData", list!=null?list:page.getContent());
        Gson gson = new Gson();
        String r = SUCCESS_FLAG+SPLIT+gson.toJson(jsonMap);
        writeMsg(response, r);
    }
	/**
	 * 成功的时候返回的数据
	 * @param obj
	 * @return
	 */
	public void SUCCESS(Object obj,HttpServletResponse response){
		if(obj==null){
			obj = "";
		}
		
		Gson gson = new Gson();
		if(obj instanceof String){
			writeMsg(response, SUCCESS_FLAG+SPLIT+obj.toString());
		}else{
			String r = SUCCESS_FLAG+SPLIT+gson.toJson(obj); 
			writeMsg(response, r);
		}
	}
	
	/**
	 * 默认返回时间戳
	 * @return
	 */
	public void SUCCESS(HttpServletResponse response){
		SUCCESS(null,response);
	}
	
	/**
	 * 
	 * @param obj
	 * @return
	 */
	public void SERVER_ERROR(Object obj,HttpServletResponse response){
		if(obj==null){
			obj = "";
		}
		
		if(obj instanceof String){
			writeMsg(response, SERVER_ERROR+SPLIT+obj.toString());
		}else{
			writeMsg(response,SERVER_ERROR+SPLIT+new Gson().toJson(obj));
		}
	}
	
	public void SERVER_ERROR(HttpServletResponse response){
		SERVER_ERROR(null,response);
	}
	
	private void PARAMETER_ERROR(HttpServletResponse response){
		writeMsg(response, PARAMETER_ERROR+SPLIT+"参数错误");
	}
	
	@ExceptionHandler
	@ResponseBody
	public void exeception(HttpServletRequest request,HttpServletResponse response, Exception ex){
		if(ex instanceof MissingServletRequestParameterException){
			PARAMETER_ERROR(response);
		}else{
			ex.printStackTrace();
			SERVER_ERROR(response);
		}
	}
	
	private void writeMsg(HttpServletResponse response,String msg){
		try {
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.getOutputStream().write(msg.getBytes("UTF-8"));
			response.getOutputStream().flush();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
