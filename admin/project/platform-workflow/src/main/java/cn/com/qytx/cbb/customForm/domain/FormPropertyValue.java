package cn.com.qytx.cbb.customForm.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.com.qytx.platform.base.domain.BaseDomain;

/**
 * 表单属性值实体类
 * 版本: 1.0
 * 开发人员: 吴洲
 * 创建日期: 2013-3-21
 * 修改日期: 2013-3-21
 * 修改列表:
 */
@Entity
@Table(name="tb_cbb_form_property_value")
public class FormPropertyValue  extends BaseDomain{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private Integer id;//id
    @Column(name="bean_id")
	private String beanId;//流程实例id
    @Column(name="bean_property_id",nullable=false)
	private Integer beanPropertyId;//控件id
    @Column(name="bean_value")
	private String beanValue;//控件的值
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
	 * 得到id
	 * @return id
	 */
	public Integer getId() {
		return this.id;
	}
	/**
	 * 设置id
	 * @param id 
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 得到流程id
	 * @return 流程id
	 */
	public String getBeanId() {
		return beanId;
	}
	/**
	 * 设置流程id
	 * @param beanId 流程id
	 */
	public void setBeanId(String beanId) {
		this.beanId = beanId;
	}
	/**
	 * 得到表单控件id
	 * @return 表单控件id
	 */
	public Integer getBeanPropertyId() {
		return this.beanPropertyId;
	}
	/**
	 * 设置表单控件id
	 * @param beanPropertyId 表单控件id
	 */
	public void setBeanPropertyId(Integer beanPropertyId) {
		this.beanPropertyId = beanPropertyId;
	}
	/**
	 * 得到控件的值
	 * @return 控件的值
	 */
	public String getBeanValue() {
		return this.beanValue;
	}
	/**
	 * 设置控件的值
	 * @param beanValue 控件的值
	 */
	public void setBeanValue(String beanValue) {
		this.beanValue = beanValue;
	}

	

}