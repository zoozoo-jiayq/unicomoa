package cn.com.qytx.cbb.customJpdl.service.impl.template;

/**
 * 功能：回退信息数据模型，记录每个任务节点的用户和任务名称
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 上午10:34:54 
 * 修改日期：上午10:34:54 
 * 修改列表：
 */
public class RollbackTaskInfo {

	private String user;
	private String taskName;
	
	//是否是首节点，0否1是
	private int isFirst;
	
	public RollbackTaskInfo(String user, String taskName, int isFirst) {
		super();
		this.user = user;
		this.taskName = taskName;
		this.isFirst = isFirst;
	}

	public int getIsFirst() {
		return isFirst;
	}

	public void setIsFirst(int isFirst) {
		this.isFirst = isFirst;
	}

	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	
}
