package cn.com.qytx.cbb.secret.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import cn.com.qytx.platform.base.domain.BaseDomain;
import cn.com.qytx.platform.base.domain.DeleteState;

/**
 * 功能: 保密信息设置
 * 版本: 1.0
 * 开发人员: zyf
 * 创建日期: 2014年12月23日
 * 修改日期: 2014年12月23日
 * 修改列表:
 */
@Entity
@Table(name="tb_cbb_secret_settings")
public class SecretSettings extends BaseDomain implements Serializable {

	/**
	 * 描述含义
	 */
	private static final long serialVersionUID = 4246236048108566063L;
	
	/**
	 * 主键ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id", unique=true,nullable=false)
	private Integer id;
	
	/**
	 * 字段代码
	 */
	@Column(name="attribute")
	private String attribute;
	
	/**
	 * 字段名称
	 */
	@Column(name="attribute_name")
	private String attributeName;
	
	/**
	 * 保密范围
	 */
	@Column(name="apply_user_ids",columnDefinition="text")
	private String applyUserIds;
	
	@Transient
	private String applyUserNames;
	
	/**
	 * 黑名单
	 */
	@Column(name="invisible_user_ids",columnDefinition="text")
	private String invisibleUserIds;
	
	@Transient
	private String invisibleUserNames;
	
	
	@DeleteState
	@Column(name="is_delete",nullable=false)
	private Integer isDelete = 0; //删除状态
	
	/**
	 * 创建人id
	 */
	@Column(name="create_user_id")
	private Integer createUserId;

	/**
	 * 创建时间
	 */
	@Column(name="create_time")
	private Timestamp createTime;

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

	public String getApplyUserIds() {
		return applyUserIds;
	}

	public void setApplyUserIds(String applyUserIds) {
		this.applyUserIds = applyUserIds;
	}

	public String getInvisibleUserIds() {
		return invisibleUserIds;
	}

	public void setInvisibleUserIds(String invisibleUserIds) {
		this.invisibleUserIds = invisibleUserIds;
	}


	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
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

	public String getApplyUserNames() {
		return applyUserNames;
	}

	public void setApplyUserNames(String applyUserNames) {
		this.applyUserNames = applyUserNames;
	}

	public String getInvisibleUserNames() {
		return invisibleUserNames;
	}

	public void setInvisibleUserNames(String invisibleUserNames) {
		this.invisibleUserNames = invisibleUserNames;
	}
	
}
