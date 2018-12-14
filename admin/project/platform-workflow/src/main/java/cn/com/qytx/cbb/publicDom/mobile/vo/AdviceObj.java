package cn.com.qytx.cbb.publicDom.mobile.vo;

/**
 * 功能：手机端使用的审批意见对象
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 上午11:20:13 
 * 修改日期：上午11:20:13 
 * 修改列表：
 */
public class AdviceObj {

	private String userName;
	private String time;
	private String content;
	private String taskId;
	private String editorId;
	private String headPhoto;
	private String createTime;
	private String userId;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getEditorId() {
		return editorId;
	}
	public void setEditorId(String editorId) {
		this.editorId = editorId;
	}
	public String getHeadPhoto() {
		return headPhoto;
	}
	public void setHeadPhoto(String headPhoto) {
		this.headPhoto = headPhoto;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
