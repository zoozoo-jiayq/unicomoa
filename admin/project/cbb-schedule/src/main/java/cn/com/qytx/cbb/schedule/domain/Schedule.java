package cn.com.qytx.cbb.schedule.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

import cn.com.qytx.platform.base.domain.BaseDomain;
import cn.com.qytx.platform.base.domain.DeleteState;

/**
 * @author panbo
 * @time 2016-05-30
 * @des 日程表
 */
@Entity
@Table(name="tb_schedule")
public class Schedule extends BaseDomain implements Serializable{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -2584114040779899083L;

	/**
	 * 主键ID
	 */
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	/**
	 * 模块实例Id
	 */
	@Column(name="instance_id")
	private String instanceId; 
	
	/**
	 * 模块实例类型
	 */
	@Column(name="type")
	private String type;
	
	/**
	 * 日程内容
	 */
	@Column(name="content")
	private String content;
	
	/**
	 * 任务完成时间
	 */
	@Column(name="complete_time")
	private Timestamp completeTime;
	
	/**
	 * 创建人ID/日程承办人
	 */
	@Column(name="create_user_id")
	private Integer createUserId;
	
	/**
	 * 创建时间
	 */
	@Column(name="create_time")
	private Timestamp createTime;
	
	/**
	 * 最后修改人ID
	 */
	@Column(name="last_update_user_id")
	private Integer lastUpdateUserId;
	
	/**
	 * 最后修改时间
	 */
	@Column(name="last_update_time")
	private Timestamp lastUpdateTime;
	
	/**
	 * 日程状态
	 * 进行中0;已完成1;未完成2
	 */
	@Column(name="status")
	private Integer status;
	
	/**
	 * 日程排序
	 */
	@Column(name="order_index")
	private Integer orderIndex;
	
	/**
	 * 未完成原因
	 */
	@Column(name="undone_reason")
	private String undoneReason;
	
	@DeleteState
	@Column(name="is_delete")
	private Integer isDelete;
	
	/**
	 * 查询用， 不存库。 查询开始时间
	 */
	@Transient
	private String beginTime;
	
	/**
	 * 查询用， 不存库。 查询结束时间
	 */
	@Transient
	private String endTime;
	
	/**
	 * 前段展示时间
	 */
	@Transient
	private String time;
	
	
	/**构造器*/
	public Schedule(){}

	/**get set方法*/
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Timestamp getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(String completeTime) {
		this.completeTime = Timestamp.valueOf(completeTime);
	}

	public Integer getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Integer getLastUpdateUserId() {
		return lastUpdateUserId;
	}

	public void setLastUpdateUserId(Integer lastUpdateUserId) {
		this.lastUpdateUserId = lastUpdateUserId;
	}

	public Timestamp getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Timestamp lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}


	public Integer getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(Integer orderIndex) {
		this.orderIndex = orderIndex;
	}

	public String getUndoneReason() {
		return undoneReason;
	}

	public void setUndoneReason(String undoneReason) {
		this.undoneReason = undoneReason;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
   
	
	
}
