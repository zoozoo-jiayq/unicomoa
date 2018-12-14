package cn.com.qytx.cbb.myapply.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import cn.com.qytx.platform.base.domain.BaseDomain;

/**
 * 
 * @author liyanlei
 *
 */
@Entity
@Table(name="tb_cbb_my_started")
public class MyStarted extends BaseDomain implements Serializable{
	
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
	
	@Column(name="state",length=50)
	private String state;
	
	@Column(name="instance_id",length=50)
	private String instanceId;
	
	@Column(name="creater_id")
	private Integer createrId;
	
	@Column(name="creater_name",length=50)
	private String createrName;
	
	@Column(name="creater_time")
	private Timestamp createrTime;
	
	@Column(name="module_code",length=50)
	private String moduleCode;
	
	@Transient
	private List<Map<String,String>> processerUser;//当前所有审批人信息
	
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
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	public Integer getCreaterId() {
		return createrId;
	}
	public void setCreaterId(Integer createrId) {
		this.createrId = createrId;
	}
	public String getCreaterName() {
		return createrName;
	}
	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}
	
	public Timestamp getCreaterTime() {
		return createrTime;
	}
	public void setCreaterTime(Timestamp createrTime) {
		this.createrTime = createrTime;
	}
	public String getModuleCode() {
		return moduleCode;
	}
	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}
	public List<Map<String, String>> getProcesserUser() {
		return processerUser;
	}
	public void setProcesserUser(List<Map<String, String>> processerUser) {
		this.processerUser = processerUser;
	}
	
	
	
}
