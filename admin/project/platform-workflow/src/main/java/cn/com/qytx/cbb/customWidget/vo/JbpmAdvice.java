package cn.com.qytx.cbb.customWidget.vo;
/**
 * 功能: 任务审批意见实体类
 * 版本: 1.0
 * 开发人员: 陈秋利
 * 创建日期: 2013-3-26
 * 修改日期: 2013-3-26
 * 修改列表:
 */
public class JbpmAdvice implements java.io.Serializable  {
	private static final long serialVersionUID = 1L;
	private String step; //步骤
	private String taskName;//任务名称
	private String userName;//用户名
	private String userId;//用户ID
	private String createTime;//创建时间
	private String content;//内容
	private String taskId = ""; //任务ID
    private String result="待审批";//审批结果  待审批  同意   不同意  挂起
    private String job; //
    private String groupName;
    
    private int signType = 0;//0：不启用；1：默认；2：图片签名
    private String signUrl;//印章URL
    private String instanceId;//流程实例ID
    private int orderIndex;
    private String editorName;//审批控件名称
    
    //add by jiayq,创建时间，yyyy-MM-dd HH:mm:ss
    private String createTimeOrial;
    private String headPhoto;
    
    public String getCreateTimeOrial() {
		return createTimeOrial;
	}

	public void setCreateTimeOrial(String createTimeOrial) {
		this.createTimeOrial = createTimeOrial;
	}

	public String getHeadPhoto() {
		return headPhoto;
	}

	public void setHeadPhoto(String headPhoto) {
		this.headPhoto = headPhoto;
	}

	//审批日期
    private String adviceTime;
    
    public String getAdviceTime() {
		return adviceTime;
	}

	public void setAdviceTime(String adviceTime) {
		this.adviceTime = adviceTime;
	}

	public String getEditorName() {
		return editorName;
	}

	public void setEditorName(String editorName) {
		this.editorName = editorName;
	}

	public int getSignType() {
		return signType;
	}

	public void setSignType(int signType) {
		this.signType = signType;
	}

	public String getSignUrl() {
		return signUrl;
	}

	public void setSignUrl(String signUrl) {
		this.signUrl = signUrl;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public int getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(int orderIndex) {
		this.orderIndex = orderIndex;
	}

	public String getResult() {
    	if("".equals(result)){
    		result="";
    	}
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getStep() {
		return step;
	}
	public void setStep(String step) {
		this.step = step;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
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

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
 
	
	
}
