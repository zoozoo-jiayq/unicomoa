package cn.com.qytx.oa.publicaddress.domain;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

import cn.com.qytx.platform.base.domain.BaseDomain;
import cn.com.qytx.platform.base.domain.DeleteState;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.utils.CbbConst;
import cn.com.qytx.platform.utils.datetime.DateTimeUtil;

/**
 * 功能:通讯薄基本信息实体类
 * 版本: 1.0
 * 修改列表:
 */
@Entity
@Table(name="tb_oab_address")
public class Address extends BaseDomain implements java.io.Serializable {

	/**
	 * 主键ID
	 */
	@Id
	@Column(name="vid")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;	
	
	@JoinColumn(name="create_user_id")
	@ManyToOne(cascade=CascadeType.REFRESH,fetch=FetchType.LAZY)
	private UserInfo createUserInfo;
	
	@Column(name="create_time",length=23)
	private Timestamp createTime;
	
	@DeleteState
    @Column(name = "is_delete")
    private Integer isDelete = 0;
	
	/**
	 * 排序号
	 */
	@Column(name="order_no")
	private Integer orderNo;
	
	@Column(name="last_update_time",length=23)
	private Timestamp lastUpdateTime;
	
	@Column(name="last_update_user_id")
	private Integer lastUpdateUserId;
	
	/**
	 * 联系人姓名
	 */
	@Column(name="name",length=100,nullable=false)
	private String name;
	
	/**
	 * 联系人首字母
	 */
	@Column(name="first_letter",length=10,nullable=false)
	private String firstLetter;
	
	/**
	 * 性别
	 */
	@Column(name="sex")
	private Integer sex;
	
	/**
	 * 生日
	 */
	@Column(name="birthday")
	private Timestamp birthday;
	
	/**
	 * 昵称
	 */
	@Column(name="nickname",length=100)
	private String nickname;
	
	/**
	 * 职务信息
	 */
	@Column(name="post_info",length=100)
	private String postInfo;
	
	/**
	 * 配偶姓名
	 */
	@Column(name="wife_name",length=100)
	private String wifeName;
	
	/**
	 * 子女
	 */
	@Column(name="children",length=100)
	private String children;
	
	/**
	 * 照片url
	 */
	@Column(name="photo",length=1000)
	private String photo;
	
	/**
	 * 公司名称
	 */
	@Column(name="company_name",length=100)
	private String companyName;
	
	/**
	 * 公司地址
	 */
	@Column(name="company_address",length=200)
	private String companyAddress;
	
	/**
	 * 单位邮编
	 */
	@Column(name="company_post_code",length=20)
	private String companyPostCode;
	
	/**
	 * 工作电话
	 */
	@Column(name="office_phone",length=20)
	private String officePhone;
	
	/**
	 * 工作传真
	 */
	@Column(name="office_fax",length=20)
	private String officeFax;
	
	/**
	 * 家庭住址
	 */
	@Column(name="family_address",length=200)
	private String familyAddress;
	
	/**
	 * 家庭邮编
	 */
	@Column(name="family_post_code",length=20)
	private String familyPostCode;
	
	/**
	 * 家庭电话
	 */
	@Column(name="family_phone_no",length=20)
	private String familyPhoneNo;
	
	/**
	 * 家庭手机
	 */
	@Column(name="family_mobile_no",length=20)
	private String familyMobileNo;
	
	/**
	 * 电子邮件
	 */
	@Column(name="family_email")
	private String familyEmail;
	
	/**
	 * 备注
	 */
	@Column(name="remark",length=500)
	private String remark;
	
	/**
	 * 开始共享时间
	 */
	@Column(name="start_share_time",length=23)
	private Timestamp startShareTime;
	
	/**
	 * 结束共享时间
	 */
	@Column(name="end_share_time",length=23)
	private Timestamp endShareTime;
	
	/**
	 * 共享人员ID集合
	 */
	@Column(name="share_to_user_ids")
	private String shareToUserIds;
	
	/**
	 * 允许修改人员ID集合
	 */
	@Column(name="allow_update_user_ids")
	private String allowUpdateUserIds;
	
	/**
	 * 联系人组名
	 */
	@Column(name="groupname")
	private String groupName;
	
	/**
	 * 联系人所属群组
	 */
	@Column(name="address_Group_Id")
	private Integer addressGroupId;
	
	/**
     * 生日
     */
	@Transient
    private String birthdayStr;
    
	
	
	
	/**
     * 共享人员名字集合
     */
	@Transient
    private String shareToUserNames;
    
    /**
     * 允许修改人员姓名集合
     */
	@Transient
    private String allowUpdateUserNames;
    
    /**
     * 事务提醒标志 1.提醒 0不提醒
     */
	@Transient
    public Integer affairsSign;
    
	public Integer getAddressGroupId() {
		return addressGroupId;
	}

	public void setAddressGroupId(Integer addressGroupId) {
		this.addressGroupId = addressGroupId;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstLetter() {
		return firstLetter;
	}

	public void setFirstLetter(String firstLetter) {
		this.firstLetter = firstLetter;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Timestamp getBirthday() {
		return birthday;
	}

	public void setBirthday(Timestamp birthday) {
		this.birthday = birthday;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPostInfo() {
		return postInfo;
	}

	public void setPostInfo(String postInfo) {
		this.postInfo = postInfo;
	}

	public String getWifeName() {
		return wifeName;
	}

	public void setWifeName(String wifeName) {
		this.wifeName = wifeName;
	}

	public String getChildren() {
		return children;
	}

	public void setChildren(String children) {
		this.children = children;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public String getCompanyPostCode() {
		return companyPostCode;
	}

	public void setCompanyPostCode(String companyPostCode) {
		this.companyPostCode = companyPostCode;
	}

	public String getOfficePhone() {
		return officePhone;
	}

	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}

	public String getOfficeFax() {
		return officeFax;
	}

	public void setOfficeFax(String officeFax) {
		this.officeFax = officeFax;
	}

	public String getFamilyAddress() {
		return familyAddress;
	}

	public void setFamilyAddress(String familyAddress) {
		this.familyAddress = familyAddress;
	}

	public String getFamilyPostCode() {
		return familyPostCode;
	}

	public void setFamilyPostCode(String familyPostCode) {
		this.familyPostCode = familyPostCode;
	}

	public String getFamilyPhoneNo() {
		return familyPhoneNo;
	}

	public void setFamilyPhoneNo(String familyPhoneNo) {
		this.familyPhoneNo = familyPhoneNo;
	}

	public String getFamilyMobileNo() {
		return familyMobileNo;
	}

	public void setFamilyMobileNo(String familyMobileNo) {
		this.familyMobileNo = familyMobileNo;
	}

	public String getFamilyEmail() {
		return familyEmail;
	}

	public void setFamilyEmail(String familyEmail) {
		this.familyEmail = familyEmail;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Timestamp getStartShareTime() {
		return startShareTime;
	}

	public void setStartShareTime(Timestamp startShareTime) {
		this.startShareTime = startShareTime;
	}

	public Timestamp getEndShareTime() {
		return endShareTime;
	}

	public void setEndShareTime(Timestamp endShareTime) {
		this.endShareTime = endShareTime;
	}

	public String getShareToUserIds() {
		return shareToUserIds;
	}

	public void setShareToUserIds(String shareToUserIds) {
		this.shareToUserIds = shareToUserIds;
	}

	public String getAllowUpdateUserIds() {
		return allowUpdateUserIds;
	}

	public void setAllowUpdateUserIds(String allowUpdateUserIds) {
		this.allowUpdateUserIds = allowUpdateUserIds;
	}

    public String getGroupName()
    {
        return groupName;
    }

    public void setGroupName(String groupName)
    {
        this.groupName = groupName;
    }

    public String getBirthdayStr()
    {
        return birthdayStr;
    }

    public void setBirthdayStr(String birthdayStr)
    {
        if (!StringUtils.isEmpty(birthdayStr))
        {
            this.setBirthday(DateTimeUtil.stringToTimestamp(birthdayStr, CbbConst.DATE_FORMAT_STR));
        }
        this.birthdayStr = birthdayStr;
    }

    public Integer getAffairsSign()
    {
        return affairsSign;
    }

    public void setAffairsSign(Integer affairsSign)
    {
        this.affairsSign = affairsSign;
    }

    public String getShareToUserNames()
    {
        return shareToUserNames;
    }

    public void setShareToUserNames(String shareToUserNames)
    {
        this.shareToUserNames = shareToUserNames;
    }

    public String getAllowUpdateUserNames()
    {
        return allowUpdateUserNames;
    }

    public void setAllowUpdateUserNames(String allowUpdateUserNames)
    {
        this.allowUpdateUserNames = allowUpdateUserNames;
    }

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

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
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

}