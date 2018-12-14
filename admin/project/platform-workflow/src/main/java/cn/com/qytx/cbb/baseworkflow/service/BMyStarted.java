package cn.com.qytx.cbb.baseworkflow.service;

import java.util.List;
import java.util.Map;

/**
 * 我发起的
 * @author jiayongqiang
 *
 */
public class BMyStarted {

	//固定流程类型
	private String code;
	
	//标题
	private String title;
	
	//状态
	private String state;
	
	//申请日期
	private String applyTime;
	
	//流程ID
	private String instanceId;
	
	private String moduleCode;
	
	private List<Map<String,String>> processerUser;//当前所有审批人信息

	public String getModuleCode() {
		return moduleCode;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public List<Map<String, String>> getProcesserUser() {
		return processerUser;
	}

	public void setProcesserUser(List<Map<String, String>> processerUser) {
		this.processerUser = processerUser;
	}
	
	
}
