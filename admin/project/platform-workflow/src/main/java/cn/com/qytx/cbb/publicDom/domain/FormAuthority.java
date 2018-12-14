package cn.com.qytx.cbb.publicDom.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.com.qytx.platform.base.domain.BaseDomain;


/**
 * 功能：表单权限对应表
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 上午8:53:33 
 * 修改日期：上午8:53:33 
 * 修改列表：
 */
@Entity
@Table(name="tb_cbb_form_authority")
public class FormAuthority extends BaseDomain{
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(name="form_property_id")
	private Integer formPropertyId;
	
	@Column(name="candidate",length=500)
	private String candidate;
	
	@Column(name="groups",length=500)
	private String groups;
	
	@Column(name="roles",length=500)
	private String roles;
	
	@Column(name="formid")
	private Integer formId;
	
	@Column(name="propertyname")
	private String propertyName;
	
	public Integer getFormPropertyId() {
		return formPropertyId;
	}
	public void setFormPropertyId(Integer formPropertyId) {
		this.formPropertyId = formPropertyId;
	}
	public String getCandidate() {
		return candidate;
	}
	public void setCandidate(String candidate) {
		this.candidate = candidate;
	}
	public String getGroups() {
		return groups;
	}
	public void setGroups(String groups) {
		this.groups = groups;
	}
	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}
	public Integer getFormId() {
		return formId;
	}
	public void setFormId(Integer formId) {
		this.formId = formId;
	}
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
}
