package cn.com.qytx.cbb.jbpmApp.service;

import java.util.Map;

import cn.com.qytx.cbb.jbpmApp.domain.WorkflowVar;
import cn.com.qytx.platform.base.service.BaseService;

/**
 * 功能：
 * 作者： jiayongqiang
 * 创建时间：2014年7月10日
 */
public interface IWorkflowVar extends BaseService<WorkflowVar> {

	/**
	 * 功能：根据流程id获得工作流流程变量
	 * @param instanceId
	 * @return
	 */
	public WorkflowVar findByInstanceId(String instanceId);
	
	/**
	 * 功能：获取任务发起人信息
	 * 作者：jiayongqiang
	 * 参数：
	 * 输出：
	 */
	public Map<String,String> findCreaterInfoByInstanceId(String instanceId);
}
