package cn.com.qytx.cbb.signcheck.domain;

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
import cn.com.qytx.platform.org.domain.UserInfo;



/**
 * 
 * 功能:签阅表
 * 版本: 1.0
 * 开发人员: 徐长江
 * 创建日期: 2013-3-21
 * 修改日期: 2013-3-21
 * 修改列表:
 */
@Entity
@Table(name="tb_oc_check")
public class SignCheck extends BaseDomain implements java.io.Serializable {

	/**自增id */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="check_id")
	private int checkId;
	/** 引用的内容的id*/
	@Column(name="ref_id")
	private int refId;
	/**签阅时间*/
	@Column(name="read_time")
	private Timestamp readTime;
	
	/**模块名字 */
	@Column(name="module_name")
	private String moduleName;	
	 /**创建人名字*/
	@Column(name="create_user")
	private String createUser;
	
	@Column(name="version")
	private String version;
	
	@Column(name="create_time")
	private Timestamp createTime;
	
	@Column(name="create_user_id")
	private Integer createUserId;
	
	@DeleteState
	@Column(name="is_delete")
	private Integer isDelete;
	
	@Column(name="last_update_time")
	private Timestamp lastUpdateTime;
	
	@Column(name="last_update_user_id")
	private Integer lastUpdateUserId;
	
	public int getCheckId() {
		return checkId;
	}
	public void setCheckId(int checkId) {
		this.checkId = checkId;
	}
	
	public int getRefId() {
		return refId;
	}
	public void setRefId(int refId) {
		this.refId = refId;
	}
	
	public Timestamp getReadTime() {
		return readTime;
	}
	public void setReadTime(Timestamp readTime) {
		this.readTime = readTime;
	}
	
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public Integer getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}
	public Integer getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
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
	/**
	 * id
	 */
	@Transient
	private Integer id;
	
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -7197618489742346183L;

	/**
	 * 创建人
	 */
	@Transient
	private UserInfo createUserInfo;


	/**
	 * 创建人
	 */
	@Transient
	private UserInfo lastUpdateUserInfo;



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public UserInfo getCreateUserInfo() {
		return createUserInfo;
	}

	public void setCreateUserInfo(UserInfo createUserInfo) {
		this.createUserInfo = createUserInfo;
	}


	public UserInfo getLastUpdateUserInfo() {
		return lastUpdateUserInfo;
	}

	public void setLastUpdateUserInfo(UserInfo lastUpdateUserInfo) {
		this.lastUpdateUserInfo = lastUpdateUserInfo;
	}
}
