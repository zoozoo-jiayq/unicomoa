package cn.com.qytx.cbb.jbpmApp.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.jbpmApp.service.IJbpmApp;
import cn.com.qytx.cbb.todocount.service.IDesktopTask;

/**
 * 功能  我的工作的桌面实现  
 * <br/>版本  1.0
 * <br/>开发人员  zyf
 * <br/>创建日期  2016年1月8日
 * <br/>修改日期  2016年1月8日
 * <br/>修改列表
 */
@Service("desktopTaskJbpmImpl")
@Transactional
public class DesktopTaskJbpmImpl implements IDesktopTask{

	/** 工作流应用服务类 */
	@Resource(name="jbpmAppService")
	private IJbpmApp jbpmAppService;
	
	@Override
	public int countOfTask(int userId) {
		return jbpmAppService.countOfTask(userId); 
	}

	@Override
	public String getTaskUrl() {
		return "/jbpmApp/myjob_management.action";
	}

	@Override
	public String getModuleName() {
		return "我的工作";
	}

}
