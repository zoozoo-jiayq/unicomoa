package cn.com.qytx.oa.publicaddress.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import cn.com.qytx.platform.base.domain.BaseDomain;

/**
 * 功能:通讯薄群组人员关联实体类
 * 版本: 1.0
 * 修改列表:
 */
@Entity
@Table(name="tb_oab_address_group")
public class AddressOfGroup extends BaseDomain implements Serializable {
	
	@Transient
	private static final long serialVersionUID = 515231220515724445L;

	/**
	 * 主键ID
	 */
	@Id
	@Column(name="vid")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;	
    
	/**
	 * 通讯薄ID
	 */
	@Column(name="addressid",nullable=false)
	private Integer addressId;
	
	/**
	 * 通讯簿群组ID
	 */
	@Column(name="groupid",nullable=false)
	private Integer groupId;

	public Integer getAddressId() {
		return this.addressId;
	}

	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}

	public Integer getGroupId() {
		return this.groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

}