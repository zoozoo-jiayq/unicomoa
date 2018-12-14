package cn.com.qytx.ydzj.action;

import java.sql.Timestamp;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.log.domain.Log;
import cn.com.qytx.platform.log.service.ILog;
import cn.com.qytx.platform.log.service.LogType;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IUser;

/**
 * 功能:修改用户性别
 * 版本: 1.0
 * 开发人员: REN
 * 创建日期: 2014-9-16
 * 修改日期: 2014-9-16
 * 修改列表: 
 */
public class UserSexAction extends BaseActionSupport {
	private static final long serialVersionUID = 1L;
	
	protected static final Logger logger = Logger.getLogger(UserSexAction.class.getName());

	
	//用户业务接口
	@Resource(name="userService")
	IUser userService;	
	//日志业务接口
	@Resource(name = "logService")
	ILog logService;

	private Integer userId;
	private Integer sex;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	/**
	 * 更新用户性别
	 * @return
	 */
	public String updateUserSex(){
		String ret = "102||操作失败";
		try {
			if(userId==null){
				ret="101||用户编号不能为空";
				return null;
			}
			if(sex==null){
				ret="101||用户性别不能为空";
				return null;
			}
			UserInfo userInfo=userService.findOne(userId);
			if(userInfo!=null){
				userInfo.setSex(sex);
				userService.saveOrUpdate(userInfo);
				ret = "100||修改成功";
				//插入日志
				String logContent = "修改用户性别成功";
				String action = "修改用户性别";
				Log log = new Log();
				log.setCompanyId(userInfo.getCompanyId());
				log.setAction(action);
				log.setInsertTime(new Timestamp(new Date().getTime()));
				log.setIp(this.getRequest().getRemoteAddr());
				log.setIsDelete(0);
				log.setLogType(LogType.LOG_USER_UPDATE);//对照LogType类中的常量修改
				log.setRemark(logContent);
				log.setType(0);//手动添加系统日志
				logService.saveOrUpdate(log);
			}
		} catch (Exception e) {
			logger.error(e);
			ret = "102||操作失败";
		} finally {
			ajax(ret);
		}
		return null;
	}
}
