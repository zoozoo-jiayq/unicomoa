package cn.com.qytx.ydzj.action;

import java.sql.Timestamp;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.log.domain.Log;
import cn.com.qytx.platform.log.service.ILog;
import cn.com.qytx.platform.log.service.LogType;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IUser;

/**
 * 功能:用户签名
 * 版本: 1.0
 * 开发人员: REN
 * 创建日期: 2014-9-16
 * 修改日期: 2014-9-16
 * 修改列表: 
 */
public class UserSignAction extends BaseActionSupport {
	private static final long serialVersionUID = 1L;
	
	protected static final Logger logger = Logger.getLogger(UserSignAction.class.getName());

	
	//用户业务接口
	@Resource(name="userService")
	IUser userService;	
	
	@Resource(name = "logService")
	ILog logService;	//日志业务接口

	private Integer userId;
	private String sign;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	/**
	 * 更新用户签名
	 * @return
	 */
	public String updateSign(){
		String ret = "102||操作失败";
		try {
			if(userId==null){
				ret="101||用户编号不能为空";
				return null;
			}
			if(StringUtils.isEmpty(sign)){
				ret="101||用户签名不能为空";
				return null;
			}
			UserInfo userInfo=userService.findOne(userId);
			if(userInfo!=null){
				userInfo.setSignName(sign);
				userInfo.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
				userService.saveOrUpdate(userInfo);
				ret = "100||修改成功";
				//插入日志
				String logContent = "修改用户签名成功";
				String action = "修改用户签名";
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
