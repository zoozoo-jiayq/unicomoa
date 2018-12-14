package cn.com.qytx.platform.base.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.owasp.encoder.Encode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.PageRequest;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.query.Sort;
import cn.com.qytx.platform.org.domain.CompanyInfo;
import cn.com.qytx.platform.org.domain.UserInfo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 框架Action基类，封装了分页、ajax返回，获取当前用户等基本功能
 * @author jiayongqiang
 *
 */
public class BaseActionSupport extends ActionSupport implements ApplicationContextAware{

	protected final static Logger LOGGER = LoggerFactory.getLogger(BaseActionSupport.class);

    /** 分页开始条数 */
    private Integer iDisplayStart=0;

    /** 每页显示条数 */
    private Integer  iDisplayLength = 15;

    private Pageable pageable;

    /**
     * 获取分页开始索引值，默认从0开始
     * @return 分页开始索引值
     */
    public Integer getIDisplayStart() {
        return iDisplayStart;
    }

    /**
     * 设置分页开始索引值
     * @param iDisplayStart 分页开始索引值
     */
    public void setIDisplayStart(Integer iDisplayStart) {
        this.iDisplayStart = iDisplayStart;
    }

    /**
     * 获取每个页面显示的记录数
     * @return 返回每个页面显示的数量，默认值15
     */
    public Integer getIDisplayLength() {
        return iDisplayLength;
    }

    /**
     * 设置每个页面显示数量
     * @param iDisplayLength 每个页面的显示数量
     */
    public void setIDisplayLength(Integer iDisplayLength) {
        this.iDisplayLength = iDisplayLength;
    }


    /**
     * 获取Pageable类型的分页对象
     * @return 返回Pageable类型的分页对象，
     */
    public Pageable getPageable(){
        int page = iDisplayStart / iDisplayLength ;
        
        pageable = new PageRequest(page,iDisplayLength);
        return pageable;
    }
    
    /**
     * 获取Pageable类型的分页对象，附带排序效果
     * @return 返回Pageable类型的分页对象，按照Sort对象定义的排序规则排序
     */
    public Pageable getPageable(Sort sort){
        int page = iDisplayStart / iDisplayLength ;

        pageable = new PageRequest(page,iDisplayLength,sort);
        return pageable;
    }


    /**
     * spring 上下文
     */
    protected ApplicationContext applicationContext;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 获取当前的httpSession对象
     * @return 返回httpSession对象
     */
    protected HttpSession getSession() {
        return getRequest().getSession();
    }

    /**
     * 获取当前的httpServletRequest请求对象
     * @return 返回httpServletRequest对象
     */
    protected HttpServletRequest getRequest() {
        return ServletActionContext.getRequest();
    }

    /**
     * 获取httpServletResponse对象
     * @return 返回httpServletRequest对象
     */
    protected HttpServletResponse getResponse(){
        return ServletActionContext.getResponse();
    }

    /**
     * 将data对象的json格式字符串通过HttpServletResponse对象输出
     * @param data 任意java对象
     */
    protected void ajax(Object data){
        ajax(data,false);
    }
    /**
     * 将data对象的json格式字符串通过HttpServletResponse对象输出，
     * @param data java对象
     * @param excludeExpose true:只输出含有@Expose注解的属性;false:输出同ajax(Object data)
     */
    protected void ajax(Object data,boolean excludeExpose){

        Gson json = excludeExpose?new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create():new GsonBuilder().create();
        ajax(json.toJson(data));
    }

    /**
     * 以UTF-8编码通过HttpSerlvetResponse对象向页面输出字符串
     * @param data 要输出的字符串
     */
    protected void ajax(String data){
    	ajaxWithJavaScriptEncode(data,false);
    }
    
    /**输出string字符串
     * @param data 要输出的字符串
     * @param flag true:对输出的字符串做JavaScriptEncode编码，false不做编码，默认做编码
     */
    protected void ajaxWithJavaScriptEncode(String data,Boolean flag){
        try {
        	if(data == null){
        		data = "";
        	}
        	
        	//add by jiayq
        	//对所有的相应做编码，防止在javaScript中出现xss泄漏
        	if(flag == null){
        		flag = true;
        	}
        	if(flag){
        		data = Encode.forJavaScript(data);
        	}
        	
            HttpServletResponse response = ServletActionContext.getResponse();
            response.setHeader("ContentType", "text/json");
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=utf-8");   
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.getWriter().write(data);
            response.getWriter().flush();
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    /**
     * 以datatable插件能是被的格式输出分页查询结果
     * @param page 分页查询结果
     */
    public void ajaxPage(Page page){
        ajaxPage(page,null);
    }

    /**
     * 以datatable插件能是被的格式输出分页查询结果
     * @param page 分页对象
     * @param list 分页查询记录集
     */
    public void ajaxPage(Page page,List list){
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("iTotalRecords", page.getTotalElements());
        jsonMap.put("iTotalDisplayRecords", page.getTotalElements());
        jsonMap.put("aaData", list!=null?list:page.getContent());
        ajax(jsonMap);
    }
    
    /**
     * 从session中获取当前登录用户
     * @return 返回当前登录用户
     */
    protected UserInfo getLoginUser(){
    	return (UserInfo) getSession().getAttribute("adminUser");
    }
    
    public CompanyInfo getCurrentCompanyInfo(){
    	return (CompanyInfo) getSession().getAttribute("companyInfo");
    }
    
    /*
     * 获取用户IP
     */
    protected  String getIpAddr(HttpServletRequest request) { 
        String ip = request.getHeader("x-forwarded-for"); 
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("Proxy-Client-IP"); 
        } 
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("WL-Proxy-Client-IP"); 
        } 
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getRemoteAddr(); 
        } 
        return ip; 
    } 
}