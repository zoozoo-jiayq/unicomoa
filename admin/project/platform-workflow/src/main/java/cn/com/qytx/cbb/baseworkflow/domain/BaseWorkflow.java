package cn.com.qytx.cbb.baseworkflow.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.com.qytx.platform.base.domain.BaseDomain;

@Entity
@Table(name="tb_cbb_baseworkflow")
public class BaseWorkflow extends BaseDomain {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	/**
	 * 表单内容
	 */
	@Column(name="form_data")
	private String formData;
	
	/**
	 * 流程人ids
	 */
	@Column(name="userIds")
	private String userIds;
	
	/**
	 * 流程分类 例：qingjia/baoxiao....
	 */
	@Column(name="code")
	private String code;
	
	/**
	 * 流程id
	 */
	@Column(name="instanceId")
	private String instanceId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFormData() {
		return formData;
	}

	public void setFormData(String formData) {
		this.formData = formData;
	}

	public String getUserIds() {
		return userIds;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
}
