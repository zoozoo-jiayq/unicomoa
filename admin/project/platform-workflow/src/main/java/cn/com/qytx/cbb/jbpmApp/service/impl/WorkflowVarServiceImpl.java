package cn.com.qytx.cbb.jbpmApp.service.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.jbpmApp.dao.WorkflowVarDao;
import cn.com.qytx.cbb.jbpmApp.domain.WorkflowVar;
import cn.com.qytx.cbb.jbpmApp.service.IWorkflowVar;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IGroup;
import cn.com.qytx.platform.org.service.IUser;

/**
 * 功能  工作流流程变量实现  
 * <br/>版本  1.0
 * <br/>开发人员  zyf
 * <br/>创建日期  2016年1月8日
 * <br/>修改日期  2016年1月8日
 * <br/>修改列表
 */
@Service
@Transactional
public class WorkflowVarServiceImpl extends BaseServiceImpl<WorkflowVar> implements
		IWorkflowVar {

	@Resource
	private WorkflowVarDao workflowVarDao;
	@Resource
	private IGroup groupService;
	@Resource
	private IUser userService;
	
	/**
	 * 功能：根据流程id获得工作流流程变量
	 * @param instanceId
	 * @return
	 */
	@Override
	public WorkflowVar findByInstanceId(String instanceId) {
		return workflowVarDao.findByInstanceId(instanceId);
	}

	/**
	 * 功能：根据流程id获得创建人信息
	 * @param instanceId
	 * @return
	 */
	@Override
	public Map<String, String> findCreaterInfoByInstanceId(String instanceId) {
		// TODO Auto-generated method stub
		Map<String,String> map = new HashMap<String, String>();
		WorkflowVar var = workflowVarDao.findByInstanceId(instanceId);
		if(var!=null){
			Timestamp d = var.getCreateTime();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String date = sdf.format(d);
			map.put("dataTime", date);
			String createrLoginName = var.getCreater();
			if (createrLoginName!=null&&createrLoginName.indexOf("_")>-1) {
				createrLoginName=createrLoginName.substring(createrLoginName.indexOf("_")+1);
			}
			UserInfo u = userService.findByLoginName(createrLoginName);
			map.put("userName", u.getUserName());
			int groupId = u.getGroupId();
			String groupName = "";
			if(groupId>0){
				GroupInfo g = groupService.findOne(groupId);
				if(g!=null){
					groupName = g.getGroupName();
				}
			}
			map.put("groupName", groupName);
		}
		return map;
	}

	
}
