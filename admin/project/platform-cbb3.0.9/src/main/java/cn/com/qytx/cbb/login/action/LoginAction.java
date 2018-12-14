package cn.com.qytx.cbb.login.action;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.qytx.cbb.file.config.FilePathConfig;
import cn.com.qytx.cbb.sso.server.cache.SSOCache;
import cn.com.qytx.cbb.util.OnlineUserListener;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.log.domain.Log;
import cn.com.qytx.platform.log.service.ILog;
import cn.com.qytx.platform.log.service.LogType;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IModule;
import cn.com.qytx.platform.org.service.IRole;
import cn.com.qytx.platform.org.service.IUser;

public class LoginAction extends BaseActionSupport {
	Logger logger = LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);
	
	@Resource
	private IModule moduleService;
	@Resource
	private IUser userService;
	@Resource
	private IRole roleService;
	/** 系统日志接口 */
	@Resource
    private ILog logService;
	private int skinLogo;//皮肤标志
    @Resource(name="filePathConfig")
    private FilePathConfig filePathConfig;
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
		 if(!OnlineUserListener.onlineUserIdList.contains(userInfo.getUserId())){
         	OnlineUserListener.onlineUserIdList.add(userInfo.getUserId());
         }
	    //sso单点登录
	    String sso_token = UUID.randomUUID().toString();
		SSOCache.getInstance().store(sso_token,userInfo);
		super.getRequest().getSession().setAttribute("sso_token",sso_token); 
		
		String downPath = filePathConfig.getFileViewPath();
		this.getSession().setAttribute("downPath", downPath);
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
	
	public int getSkinLogo() {
		return skinLogo;
	}
	public void setSkinLogo(int skinLogo) {
		this.skinLogo = skinLogo;
	}
    
    
}
