package cn.com.qytx.cbb.login.action;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import cn.com.qytx.cbb.file.config.FilePathConfig;
import cn.com.qytx.cbb.sso.server.cache.SSOCache;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.log.domain.Log;
import cn.com.qytx.platform.log.service.ILog;
import cn.com.qytx.platform.log.service.LogType;
import cn.com.qytx.platform.org.domain.CompanyInfo;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.ModuleInfo;
import cn.com.qytx.platform.org.domain.RoleInfo;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.ICompany;
import cn.com.qytx.platform.org.service.IGroup;
import cn.com.qytx.platform.org.service.IModule;
import cn.com.qytx.platform.org.service.IRole;
import cn.com.qytx.platform.org.service.IUser;
import cn.com.qytx.platform.utils.OnlineUserListener;

public class LoginAction extends BaseActionSupport {
	private static final long serialVersionUID = 1L;

	Logger logger = LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);
	
	private String j_username;
	
	@Resource
	private IModule moduleService;
	@Resource
	private IUser userService;
	@Resource
	private IRole roleService;
	@Resource
    private IGroup groupService;
	@Resource(name = "companyService")
    private ICompany companyService;
	/** 系统日志接口 */
	@Resource
    private ILog logService;
	private int skinLogo;//皮肤标志
    @Resource(name="filePathConfig")
    private FilePathConfig filePathConfig;
    
    private String loginSysName;
    
  //用户所属部门
    public final static String USER_GROUP = "usergroup";
    //用户部门的子部门集合
    public final static String USER_SUBGROUPS = "usersubgroups";
    //用户的角色集合
    public final static String USER_ROLES = "userroles";
    
    
	private String instanceId;//实例id
	private String moduleCode;//模块code
	private String type;//类型 view跳转详情页面，list跳转列表，add跳转添加
	private String processId;//处理Id
	private String executionId;//执行Id
	
	
	
    /**
     * 登陆后跳转
     * @return
     */
    public String loginForward() throws Exception{
		//登录日志
		UserInfo userInfo = (UserInfo)getSession().getAttribute("adminUser");
		Log log = new Log();
		log.setCompanyId(userInfo.getCompanyId());
		log.setInsertTime(new Timestamp(new Date().getTime()));
		log.setModuleName("user");
		log.setSysName("xyoa3.0");
		log.setIp(this.getRequest().getRemoteAddr());
		log.setIsDelete(0);
		log.setLogType(LogType.LOG_LOGIN_SUCCESS);
		log.setRefId(userInfo.getUserId());
		log.setRemark("登录成功");
		log.setUserId(userInfo.getUserId());
		log.setUserName(userInfo.getUserName());
		log.setType(0);//手动添加系统日志
		logService.saveOrUpdate(log);
		//监听登录用户
		OnlineUserListener.addOnlineUser(userInfo.getUserId());
	    //sso单点登录
	    String sso_token = UUID.randomUUID().toString();
		SSOCache.getInstance().store(sso_token,userInfo);
		super.getRequest().getSession().setAttribute("sso_token",sso_token); 
		
		String downPath = filePathConfig.getFileViewPath();
		this.getSession().setAttribute("downPath", downPath);
		return SUCCESS;
    }
    
    /**
     * 功能：统一登录后跳转
     * @return
     */
    public String unitLogin(){
    	this.getSession().setAttribute("loginSysName",loginSysName);
    	String redirectResult = "toOA";
    	if(StringUtils.isNotBlank(loginSysName)&&"HR".equals(loginSysName)){
    		redirectResult = "toHR";
    	}
    	return redirectResult;
    }
    
    
    /**
     * 登陆后跳转
     * @return
     */
    public String toLogin() {
    	Map<String,Object> resultMap = new HashMap<String,Object>();
    	resultMap.put("result", "fail");
    	resultMap.put("msg", "");
    	try {
    		HttpServletRequest request = this.getRequest();
    		UserInfo userInfo=userService.findByLoginName(j_username);
    		 if(userInfo==null){
    			 resultMap.put("msg", "用户名或密码不正确!");
             }else if(userInfo.getUserState() == null || userInfo.getUserState()>0){//禁止登陸
            	 resultMap.put("msg", "该用户禁止登陆!");
             }else{
            	 setUserDetailInfo(userInfo, request.getSession());
            	 doLogin(userInfo);
            	//登录日志
         		Log log = new Log();
         		log.setCompanyId(userInfo.getCompanyId());
         		log.setInsertTime(new Timestamp(new Date().getTime()));
         		log.setModuleName("user");
         		log.setSysName("xyoa3.0");
         		log.setIp(this.getRequest().getRemoteAddr());
         		log.setIsDelete(0);
         		log.setLogType(LogType.LOG_LOGIN_SUCCESS);
         		log.setRefId(userInfo.getUserId());
         		log.setRemark("登录成功");
         		log.setUserId(userInfo.getUserId());
         		log.setUserName(userInfo.getUserName());
         		log.setType(0);//手动添加系统日志
         		logService.saveOrUpdate(log);
         		//监听登录用户
         		OnlineUserListener.addOnlineUser(userInfo.getUserId());
         		 //sso单点登录
          	    String sso_token = UUID.randomUUID().toString();
          		SSOCache.getInstance().store(sso_token,userInfo);
          		super.getRequest().getSession().setAttribute("sso_token",sso_token);
         		
         		String downPath = filePathConfig.getFileViewPath();
         		this.getSession().setAttribute("downPath", downPath);
         		resultMap.put("result", "success");
         		resultMap.put("userId", userInfo.getUserId());
         		resultMap.put("userName", userInfo.getUserName());
             }
		} catch (Exception e) {
			resultMap.put("msg", "系统异常,请稍后重试!");
			resultMap.put("result", "fail");
			e.printStackTrace();
		}
    	Gson gson = new Gson();
    	ajax(gson.toJson(resultMap));
    	return null;
    }
    
    
    /**
   	 * 功能：存放session
   	 * @param user
   	 */
   	private void doLogin(UserInfo user){
   		/**--------------初始化菜单 start----------------------*/
   		String roleIdArr="";
           List<RoleInfo> roleList =roleService.getRoleByUser(user.getUserId()); //根据人员Id获取角色列表
           if(roleList!=null)
           {
               roleIdArr= getRoleIds(roleList);
           }
         //add by jiayq,如果是超级管理员用户，则有所有的菜单权限
           List<ModuleInfo> moduleList = null;
           List<ModuleInfo> moduleOaList = new ArrayList<ModuleInfo>();
           List<ModuleInfo> moduleHrList = new ArrayList<ModuleInfo>();
           if(user.getIsDefault()!=null&&user.getIsDefault() == 0 ){
           	moduleList = moduleService.findAll();
           }else{
           	moduleList =moduleService.getModuleByRole(roleIdArr);//获取模块列表
           }
           if(moduleList!=null && moduleList.size()>0){
           	 for(ModuleInfo m:moduleList){
                	if("OA".equals(m.getSysName())){
                		moduleOaList.add(m);
                	}else if ("HR".equals(m.getSysName())){
                		moduleHrList.add(m);
                	}
                }
           }
           getSession().setAttribute("moduleList",moduleList);//把模块列表存放到session
           getSession().setAttribute("moduleOAList",moduleOaList);//把办公模块列表存放到session
           getSession().setAttribute("moduleHRList",moduleHrList);//把个人事务模块存放到session
           /**--------------初始化菜单 end----------------------*/
           
           CompanyInfo companyInfo=companyService.findOne(user.getCompanyId());
           getRequest().getSession().setAttribute("companyInfo",companyInfo);//把单位列表存放到session
           getRequest().getSession().setAttribute("adminUser",user);//把用户信息存放到session
           Integer groupId = user.getGroupId();
       	if(groupId!=null){
   	    	GroupInfo gInfo = groupService.findOne(groupId);
   	    	List<GroupInfo> subgrouplist = groupService.getSubGroupList(groupId);
   	    	List<RoleInfo> roleList1 =roleService.getRoleByUser(user.getUserId());
   	    	getRequest().getSession().setAttribute("usergroup", gInfo);
   	    	getRequest().getSession().setAttribute("usersubgroups", subgrouplist);
   	    	getRequest().getSession().setAttribute("userroles", roleList1);
       	}
       	//SSO单点登录服务启动
   	}
   	
   	/**
   	 * 功能：退出登陆
   	 * @return
   	 */
   	public String quitLogin(){
   		try{
	   			UserInfo userInfo = (UserInfo)getSession().getAttribute("adminUser");
	     	    getSession().invalidate(); //清空Session
	     	   if(userInfo !=null ){
	     	        Log log = new Log();
	     			log.setCompanyId(userInfo.getCompanyId());
	     			log.setInsertTime(new Timestamp(new Date().getTime()));
	     			log.setModuleName("user");
	     			log.setSysName("xmjcyOA1.0");
	     			log.setIp(getRequest().getRemoteAddr());
	     			log.setIsDelete(0);
	     			log.setLogType(LogType.LOG_LOGIN_OUT);
	     			log.setRefId(userInfo.getUserId());
	     			log.setRemark("退出登录成功");
	     			log.setUserId(userInfo.getUserId());
	     			log.setUserName(userInfo.getUserName());
	     			log.setType(0);//手动添加系统日志
	     			logService.saveOrUpdate(log);
	     	   }
	     	   ajax("0");
   		}catch(Exception e){
   			e.printStackTrace();
   			logger.error("退出登陆失败");
   		}
   	   return null;
   	}
    
    public String checkIsLogin(){
    	UserInfo userInfo = this.getLoginUser();
    	String userName = "";
    	if(userInfo!=null){
    		userName = userInfo.getUserName();
    	}
    	ajax(userName);
    	return null;
    }
    
    /**
	 * 直接跳转到详情页面
	 * @return
	 */
	public String redirect(){
		try {
			getSession().setAttribute("moduleCode", moduleCode);
			getSession().setAttribute("instanceId", instanceId);
    		getSession().setAttribute("type", type);
    		if("workflow".equals(moduleCode)){//待审批申请
				if(StringUtils.isNotBlank(instanceId)){
					getSession().setAttribute("processId", processId);
					getSession().setAttribute("executionId", executionId);
				}
			}
			if("notify".equals(moduleCode)||"workflow".equals(moduleCode)){
				getSession().setAttribute("loginSysName","OA");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
			return "error";
		}
		return SUCCESS;
	}
    
    
	/**
	 * 根据用户修改皮肤标志
	 * @param id
	 * @param skinLogo
	 * @throws Exception 
	 */
	public String updateUserSkinLogo() throws Exception{
		UserInfo loginUser = (UserInfo) getSession().getAttribute("adminUser");
		if(skinLogo != 0){
			userService.updateUserSkinLogo(loginUser.getUserId(), skinLogo);
		}
		loginUser = userService.findOne(loginUser.getUserId());
		this.getSession().setAttribute("adminUser", loginUser);
		loginForward();
		return null;
	}
	
	/**
	 * 判断session是否有效
	 * 功能：
	 * @return
	 */
	public String checkSession(){
		UserInfo loginUser = (UserInfo) getSession().getAttribute("adminUser");
		if(loginUser!=null){
			ajax("0");
		}else{
			ajax("1");
		}
		return null;
	}
	
	private void setUserDetailInfo(UserInfo userInfo,HttpSession session){
    	Integer groupId = userInfo.getGroupId();
    	if(groupId!=null){
	    	GroupInfo gInfo = groupService.findOne(groupId);
	    	List<GroupInfo> subgrouplist = groupService.getSubGroupList(groupId);
	    	List<RoleInfo> roleList =roleService.getRoleByUser(userInfo.getUserId());
	    	session.setAttribute(USER_GROUP, gInfo);
	    	session.setAttribute(USER_SUBGROUPS, subgrouplist);
	    	session.setAttribute(USER_ROLES, roleList);
    	}
    }
	
	/**
     * 获取角色id列表
     * @param roleList
     * @return
     */
    private String getRoleIds(final List<RoleInfo> roleList)
    {
        StringBuffer roleIdArr= new StringBuffer();
        String result = "";
        for(RoleInfo role:roleList)
        {
            roleIdArr.append(role.getRoleId()+",");
        }
        if(roleIdArr.toString().endsWith(","))
        {
            result=roleIdArr.substring(0,roleIdArr.length()-1);
        }
        return result;
    }
	
	public int getSkinLogo() {
		return skinLogo;
	}
	public void setSkinLogo(int skinLogo) {
		this.skinLogo = skinLogo;
	}

	/**
	 * @return the loginSysName
	 */
	public String getLoginSysName() {
		return loginSysName;
	}

	/**
	 * @param loginSysName the loginSysName to set
	 */
	public void setLoginSysName(String loginSysName) {
		this.loginSysName = loginSysName;
	}

	public String getJ_username() {
		return j_username;
	}

	public void setJ_username(String j_username) {
		this.j_username = j_username;
	}

	public String getModuleCode() {
		return moduleCode;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getExecutionId() {
		return executionId;
	}

	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}
    
    
}
