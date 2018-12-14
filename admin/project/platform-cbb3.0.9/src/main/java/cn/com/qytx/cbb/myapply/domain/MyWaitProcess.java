package cn.com.qytx.cbb.myapply.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.com.qytx.platform.base.domain.BaseDomain;

@Entity
@Table(name="tb_cbb_my_wait_process")
public class MyWaitProcess extends BaseDomain implements Serializable{
		
	/**
	 * 描述含义
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="my_started_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer startId;
	
	@Column(name="category_name",length=50)
	private String categoryName;
	
	@Column(name="title",length=50)
	private String title;
	
	@Column(name="instance_id",length=50)
	private String instanceId;
	
	@Column(name="processer_id")
	private Integer processerId;
	
	@Column(name="processer_name")
	private String processerName;
	
	@Column(name="start_time")
	private Timestamp startTime;
	
	@Column(name="module_code",length=50)
	private String moduleCode;
	
	@Column(name="creater_name",length=50)
    private String createrName;

	public Integer getStartId() {
		return startId;
	}

	public void setStartId(Integer startId) {
		this.startId = startId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public Integer getProcesserId() {
		return processerId;
	}

	public void setProcesserId(Integer processerId) {
		this.processerId = processerId;
	}

	public String getProcesserName() {
		return processerName;
	}

	public void setProcesserName(String processerName) {
		this.processerName = processerName;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public String getModuleCode() {
		return moduleCode;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

	public String getCreaterName() {
		return createrName;
	}

	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}
	
}
