package cn.com.qytx.cbb.secret.domain;

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
 * 功能: 单位设置可保密信息字段
 * 版本: 1.0
 * 开发人员: zyf
 * 创建日期: 2014年12月23日
 * 修改日期: 2014年12月23日
 * 修改列表:
 */
@Entity
@Table(name="tb_cbb_secret_property")
public class SecretProperty extends BaseDomain implements Serializable{

	/**
	 * 描述含义
	 */
	private static final long serialVersionUID = -1368687974493917259L;

	/**
	 * 主键ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id", unique=true,nullable=false)
	private Integer id;
	
	/**
	 * 属性编码 
	 */
	@Column(name="attribute")
	private String attribute;
	
	/**
	 * 属性名称
	 */
	@Column(name="attribute_name")
	private String attributeName;
	
	/**
	 * 创建人id 
	 */
	@Column(name="create_user_id")
	private Integer createUserId;
	
	/**
	 * 创建时间
	 */
	@Column(name="create_time")
	private Timestamp creatTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}


	public Integer getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	public Timestamp getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(Timestamp creatTime) {
		this.creatTime = creatTime;
	}
	
}
