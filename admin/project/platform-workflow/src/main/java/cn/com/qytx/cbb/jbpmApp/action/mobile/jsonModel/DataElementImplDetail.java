package cn.com.qytx.cbb.jbpmApp.action.mobile.jsonModel;

import java.util.ArrayList;
import java.util.List;

import cn.com.qytx.cbb.jbpmApp.service.mobile.htmlElement.HtmlElement;

public class DataElementImplDetail {

	private List<HtmlElement> detail = new ArrayList<HtmlElement>();
	private List<AttachValue> attach = new ArrayList<AttachValue>();
	private List<ApproveHistory> history = new ArrayList<ApproveHistory>();
	private String instanceId;
	private String totalState;
	private String taskId;
	private String createUserPhoto;
	private String createUserName;
	private String processId;
	
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public String getCreateUserPhoto() {
		return createUserPhoto;
	}
	public void setCreateUserPhoto(String createUserPhoto) {
		this.createUserPhoto = createUserPhoto;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public List<AttachValue> getAttach() {
		return attach;
	}
	public List<ApproveHistory> getHistory() {
		return history;
	}
	
	public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	}
	//更新状态:审批通过/驳回/撤销
	public void approve(String approveResult){
		int index = 0;
		for(int i=0; i<history.size(); i++){
			if(history.get(i).getState().equals(ApproveHistory.ARRIVED)){
				index = i;
			}
		}
		history.get(index).setApproveResult(approveResult);
	}
	
	public String getTotalState() {
		return totalState;
	}
	public void setTotalState(String totalState) {
		this.totalState = totalState;
	}
	public List<HtmlElement> getDetail() {
		return detail;
	}
	public void setDetail(List<HtmlElement> detail) {
		this.detail = detail;
	}
	public void addAttach(String name,String url){
		this.attach.add(new AttachValue(name, url));
	}
	
	public void addAttach(String name,String url,String size){
		this.attach.add(new AttachValue(name, url,size));
	}
	
	public void addHistory(ApproveHistory approveHistroy){
		this.history.add(approveHistroy);
	}
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	
	public void addAllHistory(List<ApproveHistory> list){
		this.history.addAll(list);
	}
	

	//附件对象
	public class AttachValue{
		private String name;
		private String url;
		private String size;
		public AttachValue(String name, String url,String size) {
			super();
			this.name = name;
			this.url = url;
			this.size = size;
		}
		
		public AttachValue() {
	        super();
	        // TODO Auto-generated constructor stub
        }

		public AttachValue(String name, String url) {
			super();
			this.name = name;
			this.url = url;
			 
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		
		public String getSize() {
			return size;
		}
		public void setSize(String size) {
			this.size = size;
		}
	}	
	
}

//表单属性对象
class FormPropertyValue{
	private String name;
	private String value;
	
	public FormPropertyValue(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FormPropertyValue other = (FormPropertyValue) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
	
}



