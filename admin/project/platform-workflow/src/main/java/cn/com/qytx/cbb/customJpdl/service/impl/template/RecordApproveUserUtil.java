package cn.com.qytx.cbb.customJpdl.service.impl.template;

import java.util.ArrayList;
import java.util.List;

import org.jbpm.api.ExecutionService;
import org.jbpm.api.ProcessEngine;

import cn.com.qytx.cbb.customJpdl.service.JpdlInterface;
import cn.com.qytx.platform.utils.spring.SpringUtil;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 功能：记录审批用户
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 上午10:09:36 
 * 修改日期：上午10:09:36 
 * 修改列表：
 */
public class RecordApproveUserUtil {
	
	public static  void record(String instanceId,String userId,String taskName){
		ProcessEngine processEngine = (ProcessEngine) SpringUtil.getBean("processEngine");
		ExecutionService executionService = processEngine.getExecutionService();
		
		//获取经过的每个节点的信息
		String rollbackInfo = (String) executionService.getVariable(instanceId,JpdlInterface.ROLL_BACK_INFO);
		Gson gson = new Gson();
		List<RollbackTaskInfo> list = gson.fromJson(rollbackInfo, 
				new TypeToken<List<RollbackTaskInfo>>(){}.getType()); 
		
		//只保留该节点以前的信息，以后的信息忽略
		List<RollbackTaskInfo> result = new ArrayList<RollbackTaskInfo>();
		if(list!=null){
			for (RollbackTaskInfo rollbackTaskInfo : list) {
				if(!rollbackTaskInfo.getTaskName().equals(taskName)){
					result.add(rollbackTaskInfo);
				}else{
					break;
				}
			}
		}
		//保存当前节点的信息
		result.add(new RollbackTaskInfo(userId, taskName,0));
		String str = gson.toJson(result);
		executionService.setVariable(instanceId,JpdlInterface.ROLL_BACK_INFO, str);
	}
}
