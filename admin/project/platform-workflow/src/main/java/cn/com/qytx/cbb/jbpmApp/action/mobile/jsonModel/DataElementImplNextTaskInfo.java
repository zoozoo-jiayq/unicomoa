package cn.com.qytx.cbb.jbpmApp.action.mobile.jsonModel;

import java.util.ArrayList;
import java.util.List;

public class DataElementImplNextTaskInfo {

	private String currentTaskId;
	private String nextTaskName;//下一步任务的名称
	private boolean onlySelectOne = true;//true只允许选择一个人;false可以选择多个人
	private List<User> candidate = new ArrayList<User>();//候选人

	public List<User> getCandidate() {
		return candidate;
	}

	public void setCandidate(List<User> candidate) {
		this.candidate = candidate;
	}

	public boolean isOnlySelectOne() {
		return onlySelectOne;
	}

	public void setOnlySelectOne(boolean onlySelectOne) {
		this.onlySelectOne = onlySelectOne;
	}

	public void addUser(String id,String name,String photo){
		this.candidate.add(new User(id,name,photo));
	}

	public String getCurrentTaskId() {
		return currentTaskId;
	}

	public void setCurrentTaskId(String currentTaskId) {
		this.currentTaskId = currentTaskId;
	}

	public String getNextTaskName() {
		return nextTaskName;
	}

	public void setNextTaskName(String nextTaskName) {
		this.nextTaskName = nextTaskName;
	}
	
}

