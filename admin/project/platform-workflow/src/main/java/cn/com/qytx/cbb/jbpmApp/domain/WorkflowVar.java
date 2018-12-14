package cn.com.qytx.cbb.jbpmApp.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.com.qytx.platform.base.domain.BaseDomain;

/**
 * 功能：存储工作流的流程实例变量
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 下午2:20:45 
 * 修改日期：下午2:20:45 
 * 修改列表：
 */
@Entity
@Table(name="tb_cbb_workflow_var")
public class WorkflowVar extends BaseDomain{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private Integer  	id;
	
	/**
	 * 流程id
	 */
	@Column(name="instanceid")
	private String   	instanceId;
	
	/**
	 * 创建时间
	 */
	@Column(name="createtime")
	private Timestamp	createTime;
	
	/**
	 * 标题
	 */
	@Column(name="title")
	private String 		title;
	
	/**
	 * 流程属性id
	 */
	@Column(name="processattributeid") 
	private Integer		processAttributeId;
	
	/**
	 * 创建人
	 */
	@Column(name="creater")
	private String		creater;
	
	/**
	 * 上一步用户id
	 */
	@Column(name="beforeuser")
	private String		beforeUser;
	
	/**
	 * 当前任务名字
	 */
	@Column(name="currenttaskname")
	private String		currentTaskName;
	
	/**
	 * 当前节点操作人
	 */
	@Column(name="currentuser")
	private String		currentUser;
	
	/**
	 * 上一步任务名字
	 */
	@Column(name="breforetaskname")
	private String		beforeTaskName;
	
	/**
	 * 当前任务状态 例：approve、endnoagree、end、repeal...
	 */
	@Column(name="current_state")
	private String		currentState;
	
	/**
	 * 挂起时间
	 */
	@Column(name="suspendtime")
	private Timestamp	suspendTime;
	//分类名称
	@Column(name="advice")
	private String categoryName;
	
	/**
	 * 最新更新时间
	 */
	@Column(name="last_update_time")
	private Timestamp lastUpdateTime;
	
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	//用来存放申请人姓名
	@Column(name="refprocess")
	private String refprocess;
	
	public String getRefprocess() {
		return refprocess;
	}
	public void setRefprocess(String refprocess) {
		this.refprocess = refprocess;
	}
	public Timestamp getSuspendTime() {
		return suspendTime;
	}
	public void setSuspendTime(Timestamp suspendTime) {
		this.suspendTime = suspendTime;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getProcessAttributeId() {
		return processAttributeId;
	}
	public void setProcessAttributeId(Integer processAttributeId) {
		this.processAttributeId = processAttributeId;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	public String getBeforeUser() {
		return beforeUser;
	}
	public void setBeforeUser(String beforeUser) {
		this.beforeUser = beforeUser;
	}
	public String getCurrentTaskName() {
		return currentTaskName;
	}
	public void setCurrentTaskName(String currentTaskName) {
		this.currentTaskName = currentTaskName;
	}
	public String getCurrentUser() {
		return currentUser;
	}
	public void setCurrentUser(String currentUser) {
		this.currentUser = currentUser;
	}
	public String getBeforeTaskName() {
		return beforeTaskName;
	}
	public void setBeforeTaskName(String beforeTaskName) {
		this.beforeTaskName = beforeTaskName;
	}
	
	public String getCurrentState() {
		return currentState;
	}
	public void setCurrentState(String currentState) {
		this.currentState = currentState;
	}
	public Timestamp getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(Timestamp lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	
}
