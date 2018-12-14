package cn.com.qytx.cbb.customForm.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.com.qytx.platform.base.domain.BaseDomain;

// default package

/**
 * TbFormAttribute entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="tb_cbb_form_attribute")
public class FormAttribute  extends BaseDomain{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="form_id")
	private Integer formId;//表单id
	@Column(name="category_id")
	private Integer categoryId;//类别id
	@Column(name="form_name",length=100)
	private String formName;//表单名称
	@Column(name="dept_id",length=100)
	private String deptId;//部门id
	@Column(name="form_source",length=5000)
	private String formSource;//表单源代码
	@Column(name="version",length=100)
	private String version;//版本号
	@Column(name="is_new_version")
	private Integer isNewVersion;//是否是最新版本
	/**
	 * 创建时间
	 */
	@Column(name="create_time")
	private Timestamp createTime;

	/**
	 * 最后修改时间
	 */
	@Column(name="update_time")
	private Timestamp lastUpdateTime;
	/**
	 * 得到创建时间
	 * @return 创建时间
	 */
	public Timestamp getCreateTime() {
		return createTime;
	}
	/**
	 * 设置创建时间
	 * @param createTime
	 */
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	/**
	 * 得到最后修改时间
	 * @return 最后修改时间
	 */
	public Timestamp getLastUpdateTime() {
		return lastUpdateTime;
	}
	/**
	 * 设置最后修改时间
	 * @param lastUpdateTime
	 */
	public void setLastUpdateTime(Timestamp lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	/**
	 * 得到表单id
	 * @return 表单id
	 */
	public Integer getFormId() {
		return this.formId;
	}
	/**
	 * 设置表单id
	 * @param formId 表单id
	 */
	public void setFormId(Integer formId) {
		this.formId = formId;
	}
	/**
	 * 得到类别id
	 * @return 类别id
	 */
	public Integer getCategoryId() {
		return this.categoryId;
	}
	/**
	 * 设置类别id
	 * @param categoryId 类别id
	 */
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	/**
	 * 得到表单名字
	 * @return 表单名字
	 */
	public String getFormName() {
		return this.formName;
	}
	/**
	 * 设置表单名字
	 * @param formName 表单名字
	 */
	public void setFormName(String formName) {
		this.formName = formName;
	}
	/**
	 * 得到部门id
	 * @return 部门id
	 */
	public String getDeptId() {
		return this.deptId;
	}
	/**
	 * 设置部门id
	 * @param deptId 部门id
	 */
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}	
	/**
	 * 得到表单源代码
	 * @return 表单源代码
	 */
	public String getFormSource() {
		return this.formSource;
	}
	/**
	 * 设置表单源代码
	 * @param formSource 表单源代码
	 */
	public void setFormSource(String formSource) {
		this.formSource = formSource;
	}
	/**
	 * 得到版本号
	 * @return 版本号
	 */
	public String getVersion() {
		return this.version;
	}
	/**
	 * 设置版本号
	 * @param version 版本号
	 */
	public void setVersion(String version) {
		this.version = version;
	}
	/**
	 * 得到是否是最新版本
	 * @return 是否是最新版本
	 */
	public Integer getIsNewVersion() {
		return this.isNewVersion;
	}
	/**
	 * 设置是否是最新版本
	 * @param isNewVersion 是否是最新版本
	 */
	public void setIsNewVersion(Integer isNewVersion) {
		this.isNewVersion = isNewVersion;
	}
	

}