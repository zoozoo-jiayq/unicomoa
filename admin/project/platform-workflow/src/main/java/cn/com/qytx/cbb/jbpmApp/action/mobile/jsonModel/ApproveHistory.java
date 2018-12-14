package cn.com.qytx.cbb.jbpmApp.action.mobile.jsonModel;

public class ApproveHistory{
	
	/*********************************************/
	//未到达的节点
	public static final String NOT_ARRIVED = "0";
	//已到达的节点
	public static final String ARRIVED = "1";
	
	/********************以下是审批状态**********************/
	//申请
	public static final String APPLY = "-1";
	//审批通过
	public static final String APPROVED = "0";
	//驳回
	public static final String ROOLBACK = "1";
	//撤销
	public static final String REPEAL = "2";
	//审批中
	public static final String APPROVE = "3";
	//转交
	public static final String TURN = "4";
	//待审批
	public static final String WAIT = "5";
	/**************************************/
	
	private int index;
	private String taskName;
	
	//审批人姓名
	private String approver;
	private String approverId;
	
	//0,未到达的节点，1已到达的节点
	private String state;
	private String time;
	
	//审批意见
	private String advice;
	private String taskId;
	private String content;
	
	//0,通过，1驳回，2撤销，3审批中，4转角
	private String approveResult;
	
	private String photoUrl;
	
	public String getPhotoUrl() {
		return photoUrl;
	}
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	public String getApproverId() {
		return approverId;
	}
	public void setApproverId(String approverId) {
		this.approverId = approverId;
	}
	public String getApproveResult() {
		return approveResult;
	}
	public void setApproveResult(String approveResult) {
		this.approveResult = approveResult;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getAdvice() {
		return advice;
	}
	public void setAdvice(String advice) {
		this.advice = advice;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getApprover() {
		return approver;
	}
	public void setApprover(String approver) {
		this.approver = approver;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
}