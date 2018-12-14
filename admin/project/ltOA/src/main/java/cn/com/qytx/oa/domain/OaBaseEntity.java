package cn.com.qytx.oa.domain;

import java.sql.Timestamp;

import cn.com.qytx.platform.org.domain.UserInfo;

/**
 * 
 * @author liyanlei 
 *
 * @date : May 4, 2014  5:06:31 PM
 *  
 * @version 1.0
 */
public class OaBaseEntity {
	/**
	 * id
	 */
	private Integer id;
	
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -7197618489742346183L;

	/**
	 * 创建人Id
	 */
	private Integer createUserId;

	/**
	 * 创建人
	 */
	private UserInfo createUserInfo;

	/**
	 * 创建时间
	 */
	private Timestamp createTime;

	/**
	 * 最后修改时间
	 */
	private Timestamp lastUpdateTime;

	/**
	 * 最后修改人ID
	 */
	private Integer lastUpdateUserId;

	/**
	 * 创建人
	 */
	private UserInfo lastUpdateUserInfo;

	/**
	 * 公司ID
	 */
	private Integer compyId;

	/**
	 * 是否删除，0：未删除，1：已删除
	 */
	private Integer isDelete = 0;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	public UserInfo getCreateUserInfo() {
		return createUserInfo;
	}

	public void setCreateUserInfo(UserInfo createUserInfo) {
		this.createUserInfo = createUserInfo;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Timestamp lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public Integer getLastUpdateUserId() {
		return lastUpdateUserId;
	}

	public void setLastUpdateUserId(Integer lastUpdateUserId) {
		this.lastUpdateUserId = lastUpdateUserId;
	}

	public UserInfo getLastUpdateUserInfo() {
		return lastUpdateUserInfo;
	}

	public void setLastUpdateUserInfo(UserInfo lastUpdateUserInfo) {
		this.lastUpdateUserInfo = lastUpdateUserInfo;
	}

	public Integer getCompyId() {
		return compyId;
	}

	public void setCompyId(Integer compyId) {
		this.compyId = compyId;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
	/**
	 * 保证缓存对象和持久化对象数据一致，避免同一对象即处于新建状态又处于持久化状态
	 */
	public boolean equals(Object obj) {
		if(obj == null){
			return false;
		}
		if(this == obj){
			return true;
		}
		if(!OaBaseEntity.class.isAssignableFrom(obj.getClass())){
			return false;
		}
		OaBaseEntity localBaseEntity = (OaBaseEntity) obj;
		return getId()!=null?getId()==(localBaseEntity.getId()):false;
	}
	
	
}
