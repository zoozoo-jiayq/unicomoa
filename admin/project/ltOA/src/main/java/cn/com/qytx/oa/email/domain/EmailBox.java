package cn.com.qytx.oa.email.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import cn.com.qytx.oa.email.service.EmailConst;
import cn.com.qytx.platform.base.domain.BaseDomain;
import cn.com.qytx.platform.base.domain.DeleteState;
import cn.com.qytx.platform.org.domain.UserInfo;

/**
 * 功能:邮件箱Domain类
 * 版本:1.0
 * 开发人员: 汤波涛
 * 创建日期: 2013-03-14
 * 修改日期: 2013-03-14
 * 修改人员: 汤波涛
 * 修改列表:初始加入的方法
 */
@Entity
@Table(name="tb_op_email_box")
public class EmailBox extends BaseDomain{

	/**
	 * 主键ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	 @Column(name="id", unique=true,nullable=false)
	private Integer id;
	
	/**
	 * 邮件箱所属用户id
	 */
	@Column(name="user_id",nullable=false)
	private Integer userId;
	
	/**
	 * 邮件箱名称
	 */
	@Column(name="box_name",length=50,nullable=false)
	private String boxName;
	
	/**
	 * 排序号
	 */
	@Column(name="order_no")
	private Integer orderNo;
	
	/**
	 * 每页显示的大小
	 */
	@Column(name="page_max",nullable=false)
	private Integer pageMax = EmailConst.EMAIL_PAGE_MAX;
	
	/**
	 * 邮件箱当前容量
	 */
	@Column(name="box_size")
	private Long boxSize;

	/**
	 * 邮件箱类型 1：系统-收件箱 2：系统-发件箱 3：系统-草稿箱 4：系统-废纸篓 5：自定义邮件箱
	 */
	@Column(name="box_type",nullable=false)
	private Integer boxType = 5;// 默认为自定义

	@Column(name="create_time",nullable=false,length=23)
	private Timestamp createTime = new Timestamp(System.currentTimeMillis());

	@Column(name="create_user_id",nullable=false)
	private Integer createUserId;

	@Column(name="last_update_time",nullable=false,length=23)
	private Timestamp lastUpdateTime;
	
	@Column(name="last_update_user_id",nullable=false)
	private Integer lastUpdateUserId;
	
	@DeleteState
    @Column(name = "is_delete")
    private Integer isDelete = 0;
	
    /**
     * 邮件份数，不存数据库
     */
	@Transient
    private Integer emailCount=0;
	
	@Transient
	private UserInfo createUserInfo;

	@Transient
	private UserInfo lastUpdateInfo;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getBoxName() {
		return boxName;
	}

	public void setBoxName(String boxName) {
		this.boxName = boxName;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getPageMax() {
		return pageMax;
	}

	public void setPageMax(Integer pageMax) {
		this.pageMax = pageMax;
	}

	public Long getBoxSize() {
		return boxSize;
	}

	public void setBoxSize(Long boxSize) {
		this.boxSize = boxSize;
	}

	public Integer getBoxType() {
		return boxType;
	}

	public void setBoxType(Integer boxType) {
		this.boxType = boxType;
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

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public Integer getEmailCount() {
		return emailCount;
	}

	public void setEmailCount(Integer emailCount) {
		this.emailCount = emailCount;
	}

	public UserInfo getCreateUserInfo() {
		return createUserInfo;
	}

	public void setCreateUserInfo(UserInfo createUserInfo) {
		this.createUserInfo = createUserInfo;
	}

	public UserInfo getLastUpdateInfo() {
		return lastUpdateInfo;
	}

	public void setLastUpdateInfo(UserInfo lastUpdateInfo) {
		this.lastUpdateInfo = lastUpdateInfo;
	}

	
}
