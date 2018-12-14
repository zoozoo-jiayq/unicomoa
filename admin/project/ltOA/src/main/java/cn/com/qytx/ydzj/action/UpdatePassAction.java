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
 * 
 * <br/>功能: 更新密码
 * <br/>版本: 1.0
 * <br/>开发人员: REN
 * <br/>创建日期: 2014-10-8
 * <br/>修改日期: 2014-10-8
 * <br/>修改列表:
 */
public class UpdatePassAction extends BaseActionSupport {

	private static final long serialVersionUID = 1L;
	
	protected static final Logger logger = Logger.getLogger(UpdatePassAction.class.getName());
	
	//用户业务接口
	@Resource(name="userService")
	IUser userService;	
	//日志业务接口
	@Resource(name = "logService")
	ILog logService;	
    //人员id
    private Integer userId;
    //新密码
    private String newPass;
    //老密码
    private String oldPass;


	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getNewPass() {
		return newPass;
	}

	public void setNewPass(String newPass) {
		this.newPass = newPass;
	}

	public String getOldPass() {
		return oldPass;
	}

	public void setOldPass(String oldPass) {
		this.oldPass = oldPass;
	}

	/**
	 * 更新密码
	 * @return
	 */
	public String updatePass(){
		String ret = "102||操作失败";
		try {
			if (userId==null) {
				ret = "101||用户编号不能为空";
				return null;
			}
			if (StringUtils.isEmpty(oldPass)) {
				ret="101||旧密码不能为空";
				return null;
			}
			if (StringUtils.isEmpty(newPass)) {
				ret="101||新密码不能为空";
				return null;
			}
			//判断旧密码是否正确
			UserInfo userInfo=userService.findOne(userId);
			if(userInfo!=null){
				String loginPassOld=userInfo.getLoginPass();
				if(oldPass.equals(loginPassOld)){
					//修改为新密码
					userService.updatePasswordByIds(userId+"", newPass);
					ret="100||设置密码成功";
					//插入日志
					String logContent = "修改用户密码成功";
					String action = "修改用户密码";
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
