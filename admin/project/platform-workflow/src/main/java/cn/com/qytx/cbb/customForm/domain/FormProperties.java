package cn.com.qytx.cbb.customForm.domain;

import java.sql.Timestamp;

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
 * 表单控件实体类
 * 版本: 1.0
 * 开发人员: 吴洲
 * 创建日期: 2013-3-21
 * 修改日期: 2013-3-21
 * 修改列表:
 */
@Entity
@Table(name="tb_cbb_form_properties")
public class FormProperties extends BaseDomain {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="property_id")
	private Integer propertyId;//表单控件id
	@Column(name="form_id")
	private Integer formId;//表单id
	@Column(name="property_name_ch",length=100)
	private String propertyNameCh;//控件中文名称
	@Column(name="property_name",length=100)
	private String propertyName;//控件名称
	@Column(name="html_type")
	private String htmlType;//控件类型
	@Transient
	private String state = "未设置";
	@Transient
	private Integer canEdit;
	//排序
	@Column(name="order_index")
	private Integer orderIndex;
	
	//父控件的名称，主要针对明细控件的实现
	@Column(name="parent_name")
	private String parentName;
	
	public Integer getOrderIndex() {
		return orderIndex;
	}
	public void setOrderIndex(Integer orderIndex) {
		this.orderIndex = orderIndex;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
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
	 * 得到表单控件id
	 * @return 控件id
	 */
	public Integer getPropertyId() {
		return this.propertyId;
	}
	/**
	 * 设置表单控件id
	 * @param propertyId 表单控件id
	 */
	public void setPropertyId(Integer propertyId) {
		this.propertyId = propertyId;
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
	 * 得到控件中文名称
	 * @return 控件中文名称
	 */
	public String getPropertyNameCh() {
		return this.propertyNameCh;
	}
	/**
	 * 设置控件中文名称
	 * @param propertyNameCh 控件中文名称
	 */
	public void setPropertyNameCh(String propertyNameCh) {
		this.propertyNameCh = propertyNameCh;
	}
	/**
	 * 得到控件名称
	 * @return 控件名称
	 */
	public String getPropertyName() {
		return this.propertyName;
	}
	/**
	 * 设置控件名称
	 * @param propertyName 控件名称
	 */
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	/**
	 * 得到控件类型
	 * @return 控件类型
	 */
	public String getHtmlType() {
		return this.htmlType;
	}
	/**
	 * 设置控件类型
	 * @param htmlType 控件类型
	 */
	public void setHtmlType(String htmlType) {
		this.htmlType = htmlType;
	}
	public Integer getCanEdit() {
		return canEdit;
	}
	public void setCanEdit(Integer canEdit) {
		this.canEdit = canEdit;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

}