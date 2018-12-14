package cn.com.qytx.cbb.jbpmApp.action.mobile.jsonModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 发起流程的时候，选择流程定义的接口
 * @author jiayongqiang
 *
 */
public class ProcessTypeModel {

	//流程分类ID
	private int categoryId;
	//流程分类名称
	private String categoryName;
	//该流程分类下的流程定义数量
	private int num = 0 ;
	//流程定义列表
	private List<ProcessDefineModule> definelist = new ArrayList<ProcessDefineModule>();

	public void addDefine(int processId,String defineName){
		this.definelist.add(new ProcessDefineModule(processId, defineName));
	}
	
	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public List<ProcessDefineModule> getDefinelist() {
		return definelist;
	}

	public void setDefinelist(List<ProcessDefineModule> definelist) {
		this.definelist = definelist;
	}

	class ProcessDefineModule{
		private int processId;
		private String processName;
		
		public ProcessDefineModule(int processId, String processName) {
			super();
			this.processId = processId;
			this.processName = processName;
		}
		public int getProcessId() {
			return processId;
		}
		public void setProcessId(int processId) {
			this.processId = processId;
		}
		public String getProcessName() {
			return processName;
		}
		public void setProcessName(String processName) {
			this.processName = processName;
		}
		
	}
}

