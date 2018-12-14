package cn.com.qytx.cbb.schedule.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.com.qytx.platform.base.domain.BaseDomain;

/**
 * @author lilipo
 *
 */
@Entity
@Table(name="tb_schedule_lifecycle")
public class ScheduleLifeCycle extends BaseDomain implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4897650307158433349L;
	
	/**
	 * 主键ID(自增)
	 */
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	/**
	 * 日程Id
	 */
	@Column(name="schedule_id")
	private String scheduleId; 
	
	/**
	 * 操作内容
	 */
	@Column(name="operation_content")
	private String operationContent;
	
	/**
	 * 操作时间
	 */
	@Column(name="operation_time")
	private Timestamp operationTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
	}

	public String getOperationContent() {
		return operationContent;
	}

	public void setOperationContent(String operationContent) {
		this.operationContent = operationContent;
	}

	public Timestamp getOperationTime() {
		return operationTime;
	}

	public void setOperationTime(Timestamp operationTime) {
		this.operationTime = operationTime;
	}

}
