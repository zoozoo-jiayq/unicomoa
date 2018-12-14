package cn.com.qytx.cbb.login.action.mobile;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import cn.com.qytx.cbb.module.domain.ModuleInfoMobile;
import cn.com.qytx.cbb.module.service.IModuleInfoMobile;
import cn.com.qytx.cbb.systemSet.domain.SysConfig;
import cn.com.qytx.cbb.systemSet.service.SysConfigService;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.log.domain.Log;
import cn.com.qytx.platform.log.service.ILog;
import cn.com.qytx.platform.log.service.LogType;
import cn.com.qytx.platform.org.domain.CompanyInfo;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.RoleInfo;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.ICompany;
import cn.com.qytx.platform.org.service.IGroup;
import cn.com.qytx.platform.org.service.IRole;
import cn.com.qytx.platform.org.service.IUser;

import com.google.gson.Gson;

/**
 * 登陆action User: 陈秋利 Date: 13-2-19 Time: 下午7:40
 */
public class LoginWapAction extends BaseActionSupport {
	private static final long serialVersionUID = 1L;
	/** 用户信息 */
	@Autowired
	IUser userService;
	// 角色信息
	@Autowired
	IRole roleService;
	
	@Autowired
	IGroup groupService;
	
	@Autowired
	ICompany companyService;
	
	@Autowired
	IModuleInfoMobile moduleInfoMobileService;

	@Resource
	private ILog logService;
	private String userName;// 用户名
	private String userPass;// 密码

	@Resource
	private SysConfigService sysConfigService;

	/**
	 * 登录
	 * 
	 * @return
	 */
	public String loginAjax() {
		UserInfo adminUser = null;
		Object adminUserObj = this.getSession().getAttribute("adminUser");
		if (adminUserObj != null) {
			adminUser = (UserInfo) adminUserObj;
		}
		try {
			LOGGER.info("\n-->Wap客户端登录请求开始");
			if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(userPass)) {
				ajax("用户名或密码为空");
				return null;
			}
			String msg = goLogin(userName, userPass);
			if (StringUtils.isNotEmpty(msg)) {
				ajax(msg);
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 功能：登陆方法
	 * 
	 * @param userName
	 * @param userPass
	 * @return
	 */
	private String goLogin(String userName, String userPass) {

		UserInfo userInfo = userService.findByLoginName(userName);
		String path = this.getRequest().getContextPath();
		String basePath = this.getRequest().getScheme() + "://"
				+ this.getRequest().getServerName() + ":"
				+ this.getRequest().getServerPort() + path + "/";
		if (userInfo == null) {
			return "101||用户名或密码错误,请重新输入";
		} else if (!userInfo.getLoginPass().equals(
				new Md5PasswordEncoder().encodePassword(userPass, null))) {
			return "102||用户名或密码错误,请重新输入";
		} else {
			if (userInfo.getUserState() != null && userInfo.getUserState() == 1) {
				return "103||对不起，该账号暂未启用，请与管理员联系";
			}
			/**
			 * 返回信息
			 */
			Map<String, Object> result = new HashMap<String, Object>();
			
			Gson json = new Gson();
			
			result.put("sex", userInfo.getSex());// 性别
			result.put("phone", userInfo.getPhone());// 电话号码
			result.put("userId", userInfo.getUserId());// 用户id
			result.put("vgroup", userInfo.getVGroup());// v网号码段
			result.put("userName", userInfo.getUserName());// 用户姓名
			result.put("signName", userInfo.getSignName());
			result.put("job", userInfo.getJob());
			result.put("groupId", userInfo.getGroupId());
			result.put("userNum", userInfo.getUserNum());// 分机号码
			result.put("companyId", userInfo.getCompanyId());
			result.put("vnum", userInfo.getVNum());
			result.put("pwd", userPass);
			//获得用户部门信息
			GroupInfo groupInfo = groupService.findOne(userInfo.getGroupId());
			result.put("groupName", "");// 部门名称
			if(groupInfo!=null){
				result.put("groupName", groupInfo.getGroupName());// 部门名称
			}
			//获得单位信息
			CompanyInfo companyInfo = companyService.findOne(userInfo.getCompanyId());
			result.put("companyName", "");
			result.put("companyCode", "");
			result.put("companyLogo", "");
			if(companyInfo!=null){
				result.put("companyName", companyInfo.getCompanyName());
				result.put("companyCode", companyInfo.getCompanyCode());
				result.put("companyLogo", companyInfo.getLogUrl());
			}
			
			List<ModuleInfoMobile> moduleList = new ArrayList<ModuleInfoMobile>();
			if (userInfo.getIsDefault() == 0) {
				moduleList = moduleInfoMobileService.companyId().findAll();
			} else {
				moduleList = moduleInfoMobileService.mobileIndex(null, userInfo.getUserId());
				
				
			}
			Map<String,Object> userRoleMap = new HashMap<String, Object>();
			userRoleMap = getUserRoleMap(moduleList,userRoleMap);
			result.put("userRoleMap", userRoleMap);// 权限及发送范围
			
			/**===================设置默认信息====================**/
			result.put("companyPhone", "");// 单位号码
			result.put("regesiter", 1);// 1：正常 2：停机 默认为1
			result.put("slogan", "");// 公司口号 默认空
			result.put("role", 1);// 角色权限 默认1
			result.put("power", 0);// 角色权限 默认0 普通用户
			result.put("clientCanLogin", 1);// 是否允许登录 默认1
			result.put("hangUpSms", 0);// 是否发送挂机短信 0不发送 1发送默认0
			result.put("adminPhone", "");// 管理员号码 默认空
			result.put("isCompleted", 1);// 是否已经完善信息 0.否1.是 默认1
			result.put("adminName", "");// 管理员姓名 默认空
			result.put("orderType", 1);// 是否订购了 0未订购1已订购 默认1
			result.put("userAno", "");// 职务 默认空
			result.put("adminId", "");//
			result.put("serviceInfo", "");// 语音通知 区分通知系统
			result.put("needCompleted", 0);// 是否需要完善信息 0.否1.是 默认0
			/**===================设置默认信息 end====================**/
			
			//转换json格式
			String jsons = json.toJson(result);

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
			log.setType(0);// 手动添加系统日志
			logService.saveOrUpdate(log);

			userInfo.setLastLoginTime(new Date());
			userService.saveOrUpdate(userInfo);
			return "100||" + jsons;
		}
	}

	/**
	 * 功能：将模块列表转换为手机端模块权限结构
	 * @param moduleList
	 * @return
	 */
	private Map<String, Object> getUserRoleMap(List<ModuleInfoMobile> moduleList,Map<String,Object> userRoleMap){
		//全局变量 存放各个moduleId下面的子模块
		Map<String, Object> map = new HashMap<String, Object>();
		//非空判断
		if(moduleList!=null && moduleList.size()>0){
			//循环将所有模块按照parentId归类为键值对
			for(int i=0;i<moduleList.size();i++){
				ModuleInfoMobile moduleInfoMobile = moduleList.get(i);
				if(moduleInfoMobile!=null){
					String parentId = moduleInfoMobile.getParent()==null?"0":moduleInfoMobile.getParent().getId().toString();
					//将所有的模块按照parentId分类
					if(map.get(parentId)!=null){
						List<ModuleInfoMobile> newList = (List<ModuleInfoMobile>) map.get(parentId);
						newList.add(moduleInfoMobile);
						map.put(parentId, newList);
						continue;
					}else{
						List<ModuleInfoMobile> newList = new ArrayList<ModuleInfoMobile>();
						newList.add(moduleInfoMobile);
						map.put(parentId, newList);
						continue;
					}
					
				}
			}//end
			
			//从parentId=0 开始
			if(map.get("0")!=null){
				List<ModuleInfoMobile> newList = (List<ModuleInfoMobile>) map.get("0");
				if(newList.size()>0){
					for(int i=0;i<newList.size();i++){
						ModuleInfoMobile moduleInfoMobile = newList.get(i);
						Map<String, Object> moduleMap = new HashMap<String, Object>();
						moduleMap.put("uOpen", 1);
						moduleMap.put("menu", 1);
						moduleMap.put("cOpen", 1);
						moduleMap.put("groups", "");
						moduleMap.put("moduleId", moduleInfoMobile.getId());
						moduleMap.put("order", moduleInfoMobile.getOrderList());
						moduleMap.put("moduleName", moduleInfoMobile.getName());
						List<Map<String,Object>> list = transRoleMap(map, moduleInfoMobile.getId().toString()) ;
						moduleMap.put("childModile", list);
						
						String moduleCode = moduleInfoMobile.getCode();
						if(userRoleMap.get(moduleCode)!=null){//兼容多模块功能
							List<Map<String, Object>> listCode = (List<Map<String, Object>>) userRoleMap.get(moduleInfoMobile.getCode());
							listCode.add(moduleMap);
							userRoleMap.put(moduleCode, listCode);
							continue;
						}else{
							List<Map<String, Object>> listCode = new ArrayList<Map<String,Object>>();
							listCode.add(moduleMap);
							userRoleMap.put(moduleCode, listCode);
							continue;
						}
					}
				}
			}
		}
		return userRoleMap;
	}
	
	/**
	 * 功能：获取子模块
	 * @return
	 */
	private List<Map<String,Object>> transRoleMap(Map<String,Object> map,String parentId){
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		if(map.get(parentId)!=null){
			List<ModuleInfoMobile> newList = (List<ModuleInfoMobile>) map.get(parentId);
			if(newList.size()>0){
				for(int i=0;i<newList.size();i++){
					ModuleInfoMobile moduleInfoMobile = newList.get(i);
					Map<String, Object> moduleMap = new HashMap<String, Object>();
					moduleMap.put("uOpen", 1);
					moduleMap.put("menu", 1);
					moduleMap.put("cOpen", 1);
					moduleMap.put("groups", "");
					moduleMap.put("code", moduleInfoMobile.getCode());
					moduleMap.put("moduleId", moduleInfoMobile.getId());
					moduleMap.put("order", moduleInfoMobile.getOrderList());
					moduleMap.put("moduleName", moduleInfoMobile.getName());
					result.add(moduleMap);
				}
			}
		}
		return result;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPass() {
		return userPass;
	}

	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}

}
