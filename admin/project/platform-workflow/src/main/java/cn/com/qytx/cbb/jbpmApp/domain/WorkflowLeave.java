package cn.com.qytx.cbb.jbpmApp.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import cn.com.qytx.platform.base.domain.BaseDomain;
@Entity
@Table(name="tb_cbb_leave")
public class WorkflowLeave extends BaseDomain{
	private static final long serialVersionUID = -8373103318205053276L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	/**
	 * 用户Id
	 */
	@Column(name="user_id")
	private Integer userId;
	
	/**
	 * 创建时间
	 */
	@Column(name="create_time")
	private Timestamp createTime;
	
	/**
	 * 开始请假时间
	 */
	@Column(name="start_leave_time")
	private Timestamp startLeaveTime;
	
	/**
	 * 结束请求时间
	 */
	@Column(name="end_leave_time")
	private Timestamp endLeaveTime;
	
	/**
	 * 是否销假  0 未销假 1 销假
	 */
	@Column(name="is_remove")
	private Integer isRemove;
	
	/**
	 * 销假时间
	 */
	@Column(name="remove_time")
	private Timestamp removeTime;
	
	/**
	 * 是否撤销  0 未撤销 1 撤销
	 */
	@Column(name="is_cancel")
	private Integer isCancel; 
	/**
	 * 请假状态  0 未生效 1 生效
	 */
	@Column(name="state")
	private Integer state;
	
	/**
	 * 是否删除
	 */
	@Column(name="is_delete")
	private Integer isDelete;
	
	/**
	 * 流程Id
	 */
	@Column(name="instance_id")
	private String instanceId;
	/**
	 * 1 请假 2 工作假 3 公休假
	 */
	@Column(name="type")
	private Integer type;  
	
	@Transient
	private String userName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getStartLeaveTime() {
		return startLeaveTime;
	}

	public void setStartLeaveTime(Timestamp startLeaveTime) {
		this.startLeaveTime = startLeaveTime;
	}

	public Timestamp getEndLeaveTime() {
		return endLeaveTime;
	}

	public void setEndLeaveTime(Timestamp endLeaveTime) {
		this.endLeaveTime = endLeaveTime;
	}

	public Integer getIsRemove() {
		return isRemove;
	}

	public void setIsRemove(Integer isRemove) {
		this.isRemove = isRemove;
	}

	public Timestamp getRemoveTime() {
		return removeTime;
	}

	public void setRemoveTime(Timestamp removeTime) {
		this.removeTime = removeTime;
	}

	public Integer getIsCancel() {
		return isCancel;
	}

	public void setIsCancel(Integer isCancel) {
		this.isCancel = isCancel;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	
	
	
}
