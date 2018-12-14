package cn.com.qytx.cbb.jbpmApp.service.impl;

import java.io.Serializable;

import javax.annotation.Resource;

import org.jbpm.api.ProcessEngine;

import cn.com.qytx.cbb.todocount.service.IDesktopTask;
import cn.com.qytx.platform.org.dao.UserDao;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IUser;
import cn.com.qytx.platform.utils.spring.SpringUtil;

public abstract class BaseDeskImpl implements IDesktopTask,Serializable  {
  @Resource(name="")
  UserDao<UserInfo> userDao;
	public ProcessEngine getProcessEngin(){
		return (ProcessEngine) SpringUtil.getBean("processEngine");
	}
	
	/**
	 * 功能：根据用户id获得登陆名
	 * @param userId
	 * @return
	 */
	public String  getLoginNameByUserId(int userId){
		IUser userService = (IUser) SpringUtil.getBean("userService");
		UserInfo ui =userDao.getUserById(userId);
		return ui.getLoginName();
	}
	

}
