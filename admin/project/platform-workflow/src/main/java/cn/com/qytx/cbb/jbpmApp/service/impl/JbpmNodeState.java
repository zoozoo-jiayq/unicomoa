package cn.com.qytx.cbb.jbpmApp.service.impl;


/**
 * 功能：
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 下午6:00:51 
 * 修改日期：下午6:00:51 
 * 修改列表：
 */
public class JbpmNodeState {
	private String nodeName;
	private boolean completed;//true,该节点已经审批过了，false，该节点还未审批
	private String userName;//节点处理人
	private String result;//处理结果
	private String approveTime;//
	/*
	 * 
	 * start:发起流程节点状态
	 * before:已经审批的节点状态
	 * endOk:审批结束节点状态(审批通过)
	 * now:当前活动节点
	 * after:为到达节点
	 */
	private String state;
	private String content;
	
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFormatTime(){
		if(approveTime!=null && !approveTime.equals("")){
			int endIndex = approveTime.length()-3;
			return approveTime.substring(5,endIndex);
		}
		return "";
	}
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public boolean isCompleted() {
		return completed;
	}
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getApproveTime() {
		return approveTime;
	}
	public void setApproveTime(String approveTime) {
		this.approveTime = approveTime;
	}
	
}
