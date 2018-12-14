package cn.com.qytx.cbb.jbpmApp.domain;

import java.sql.Timestamp;

/**
 * 流程办理历史记录
 * User:黄普友
 * Date: 13-7-5
 * Time: 上午9:46
 */
public class AdviceHistory   {
    private String taskName;//任务名称
    private String userName;//用户名字
    private String userId ;//操作人id
    private Timestamp createTime;//创建时间
    private  String content;//内容
    private  String taskId;//任务id
    private  boolean startNode;//是否开始节点
    private  boolean currentNode;//是否当前节点
    private  int state=1; //状态 0 未处理 1 已处理
    private String result="";//审批结果 0 待审批 1 同意 2 不同意 3 挂起
    private String job; //职务
    private String groupName;//部门名字
    private  String approvalResult="";//审核意见

	public String getApprovalResult() {
        return approvalResult;
    }

    public void setApprovalResult(String approvalResult) {
        this.approvalResult = approvalResult;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public boolean isStartNode() {
        return startNode;
    }

    public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
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

	public void setStartNode(boolean startNode) {
        this.startNode = startNode;
    }

    public boolean isCurrentNode() {
        return currentNode;
    }

    public void setCurrentNode(boolean currentNode) {
        this.currentNode = currentNode;
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

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
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
}
