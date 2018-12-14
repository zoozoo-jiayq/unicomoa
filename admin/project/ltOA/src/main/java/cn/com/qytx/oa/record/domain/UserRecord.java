package cn.com.qytx.oa.record.domain;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import cn.com.qytx.platform.base.domain.BaseDomain;
import cn.com.qytx.platform.base.domain.DeleteState;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.utils.DateUtils;

/**
 * 功能:人事档案信息Domain类
 * 版本:1.0
 * 开发人员: 汤波涛
 * 创建日期: 2013-03-14
 * 修改日期: 2013-03-14
 * 修改人员: 汤波涛
 * 修改列表:初始加入的方法
 */
@Entity
@Table(name="tb_ohr_user_record")
public class UserRecord extends BaseDomain{

    private static final long serialVersionUID = -8884521532309575138L;
	/**
	 * 主键ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	 @Column(name="id", unique=true,nullable=false)
	private Integer id;
    
    /**
     * 档案编号
     */
    @Column(name="record_no",length=50,nullable=false)
    private String recordNo;

    /**
     * 用户id
     */
    @Transient
    private Integer userId;

    /**
     * 用户 对象
     */
    @JoinColumn(name="user_id",nullable=false)
   	@ManyToOne(cascade=CascadeType.REFRESH)
    private UserInfo userInfo;
    
  
    /** 
     * 登录名
     */
    @Column(name="login_name")
    private String loginName;
    
    /** 
     * 姓名
     */
    @Column(name="user_name")
    private String userName;
    
    //工号
  	@Column(name="work_no",length=50)
  	private String workNo;
    
    @Column(name="sex")
	private Integer sex;
    
   //生日
  	@Column(name="birth_day")
  	private Date birthDay;
  	
  	/**
  	 * 生日字符串
  	 */
  	@Transient
  	private String birthDayStr;
    
   //职位
  	@Column(name="job",length=50)
  	private String job;
    
    
    @Column(name="phone",length=50)
	private String phone;

    //联系电话
    @Column(name="phone2",length=50)
	private String phone2;
    
    
  //邮箱
  	@Column(name="email",length=50)
  	private String email;
    
    
    /**
     * 角色ID集合字符串，不存数据库
     */
    @Transient
    private String roleIds;

    /**
     * 角色名称集合字符串 
     */
    @Column(name="role_names",length=100)
    private String roleNames;

    /**
     * 组ID集合字符串 不存数据库
     */
    @Transient
    private String groupIds;

    /**
     *  部门名称
     */
    @Column(name="group_names",length=100)
    private String groupNames;

    /**
     * 曾用名
     */
    @Column(name="name_old",length=100)
    private String nameOld;

    /**
     * 英文名
     */
    @Column(name="name_english",length=100)
    private String nameEnglish;

    /**
     * 是否为农历生日，1：是，0|null:否
     */
    @Column(name="lunar_birthday")
    private Integer lunarBirthDay;

    /**
     * 年龄
     */
    @Column(name="age")
    private Integer age;

    /**
     * 年休假天数
     */
    @Column(name="year_holiday")
    private Integer yearHoliday;

    /**
     * 政治面貌
     */
    @Column(name="politics_face")
    private Integer politicsFace;

    /**
     * 入党时间
     */
    @Column(name="party_time",length=23)
    private Timestamp partyTime;

    /**
     * 入党时间
     */
    @Transient
    private String partyTimeStr;

    /**
     * 民族
     */
    @Column(name="nationality")
    private String nationality;


    /**
     * 籍贯-选择部分
     */
    @Column(name="nativity_place_select",length=200)
    private String nativityPlaceSelect;

    /**
     * 籍贯输入部分
     */
    @Column(name="nativity_place",length=200)
    private String nativityPlace;

    /**
     * 户口类型 1：本市城镇职员 2：外埠城镇职员 3：本市农民工
     */
    @Column(name="registered_type")
    private Integer registeredType;

    /**
     * 户口所在地
     */
    @Column(name="registered_address",length=300)
    private String registeredAddress;

    /**
     * 身份证号码
     */
    @Column(name="identity_no",length=30)
    private String identityNo;

    /**
     * 婚姻状态
     * 0:未婚，1：已婚
     */
    @Column(name="marriage_status")
    private Integer marriageStatus;

    /**
     * 健康状况
     */
    @Column(name="health_info",length=300)
    private String healthInfo;

    /**
     * 照片地址
     */
    @Column(name="photo_url",length=200)
    private String photoUrl;


    //------职位情况及联系方式--------
    /**
     * 工种
     */
    @Column(name="work_type",length=100)
    private String workType;

    /**
     * 行政级别
     */
    @Column(name="administration_level",length=100)
    private String administrationLevel;

    /**
     * 职务
     */
    @Column(name="position",length=100)
    private String position;

    /**
     * 职称
     */
    @Column(name="job_title",length=100)
    private Integer jobTitle;

    /**
     * 职称级别
     */
    @Column(name="job_title_level")
    private Integer jobTitleLevel;

    /**
     * 岗位
     */
    @Column(name="station")
    private Integer station;

    /**
     * QQ号码
     */
    @Column(name="qq",length=50)
    private String qq;

    /**
     * 入职时间
     */
    @Column(name="join_time")
    private Timestamp joinTime;

    /**
     * 入职时间字符串形式
     */
    @Transient
    private String joinTimeStr;

    /**
     * 员工类型
     */
    @Column(name="user_type")
    private Integer userType;

    /**
     * 家庭地址
     */
    @Column(name="home_address",length=300)
    private String homeAddress;

    /**
     * 其他联系方式
     */
    @Column(name="other_contact_way",length=200)
    private String otherContactWay;

    /**
     * 开户行1
     */
    @Column(name="bank_name_1",length=100)
    private String bankName1;

    /**
     * 帐号1
     */
    @Column(name="bank_no_1",length=50)
    private String bankNo1;

    /**
     * 开户行2
     */
    @Column(name="bank_name_2",length=100)
    private String bankName2;

    /**
     * 帐号2
     */
    @Column(name="bank_no_2",length=50)
    private String bankNo2;

    /**
     * 毕业院校
     */
    @Column(name="graduation_school",length=200)
    private String graduationSchool;

    /**
     * 毕业时间
     */
    @Column(name="graduation_time",length=23)
    private Timestamp graduationTime;

    /**
     * 毕业时间
     */
    @Transient
    private String graduationTimeStr;

    /**
     * 学历：1:无，2：小学 3：初中 4：高中 5：中专 6:大专 7：本科
     */
    @Column(name="edu_qualifications")
    private Integer eduQualifications;

    /**
     * 学位:1:无，2：学士 3：双学士 4： 研究生 5：硕士 6 : 博士
     */
    @Column(name="edu_level")
    private Integer eduLevel;

    /**
     * 专业
     */
    
    @Column(name="profession",length=100)
    private String profession;

    /**
     * 计算机水平
     */
    @Column(name="computer_level")
    private String computerLevel;

    /**
     * 外语语种1
     */
    @Column(name="foreign_language1")
    private String foreignLanguage1;

    /**
     * 外语水平1
     */
    @Column(name="language_level1")
    private String languageLevel1;

    /**
     * 外语语种2
     */
    @Column(name="foreign_language2")
    private String foreignLanguage2;

    /**
     * 外语水平2
     */
    @Column(name="language_level2")
    private String languageLevel2;

    /**
     * 外语语种3
     */
    @Column(name="foreign_language3")
    private String foreignLanguage3;

    /**
     * 外语水平3
     */
    @Column(name="language_level3")
    private String languageLevel3;

    /**
     * 特长
     */
    @Column(name="strong_point",length=200)
    private String strongPoint;

    /**
     * 职务情况
     */
    @Column(name="post_state",length=200)
    private String postState;

    /**
     * 本单位工龄
     */
    @Column(name="company_work_age")
    private Integer companyWorkAge;

    /**
     * 起薪日期
     */
    @Column(name="start_pay_time",length=23)
    private Timestamp startPayTime;
    /**
     * 起薪日期
     */
    @Transient
    private String startPayTimeStr;

    /**
     * 在职状态
     */
    @Column(name="work_status")
    private Integer workStatus;

    /**
     * 总工龄
     */
    @Column(name="total_work_age")
    private Integer totalWorkAge;

    /**
     * 参加工作时间
     */
    @Column(name="join_work_time",length=23)
    private Timestamp joinWorkTime;

    /**
     * 参加工作时间
     */
    @Transient
    private String joinWorkTimeStr;

    /**
     * msn帐号
     */
    @Column(name="msn",length=100)
    private String msn;


    /**
     * 担保记录
     */
    @Column(name="vouch_record",length=200)
    private String vouchRecord;

    /**
     * 社保缴纳情况
     */
    @Column(name="social_security_state",length=200)
    private String socialSecurityState;

    /**
     * 体检记录
     */
    @Column(name="health_check_record",length=200)
    private String healthCheckRecord;

    /**
     * 备注信息
     */
    @Column(name="remark")
    private String remark;

    /**
     * 附件地址 JSON串
     */
    @Column(name="attachment")
    private String attachment;

    @Column(name="resume_info")
    private String resumeInfo;

    
	@Column(name="create_time",nullable=false,length=23)
	private Timestamp createTime = new Timestamp(System.currentTimeMillis());
	
	@JoinColumn(name="create_user_id",nullable=false)
	@ManyToOne(cascade=CascadeType.REFRESH)
	private UserInfo createUserInfo;
	
	@Column(name="last_update_time",nullable=false,length=23)
	private Timestamp lastUpdateTime;
	
	@JoinColumn(name="last_update_user_id",nullable=false)
	@ManyToOne(cascade=CascadeType.REFRESH)
	private UserInfo lastUpdateUserInfo; //最后一次修改人
    
	@DeleteState
    @Column(name = "is_delete")
    private Integer isDelete = 0;
	

    public String getRecordNo() {
        return this.recordNo;
    }

    public void setRecordNo(String recordNo) {
        this.recordNo = recordNo;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNameOld() {
        return nameOld;
    }

    public void setNameOld(String nameOld) {
        this.nameOld = nameOld;
    }

    public String getNameEnglish() {
        return nameEnglish;
    }

    public void setNameEnglish(String nameEnglish) {
        this.nameEnglish = nameEnglish;
    }

    public Integer getPoliticsFace() {
        return this.politicsFace;
    }

    public void setPoliticsFace(Integer politicsFace) {
        this.politicsFace = politicsFace;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getNativityPlace() {
        return this.nativityPlace;
    }

    public void setNativityPlace(String nativityPlace) {
        this.nativityPlace = nativityPlace;
    }

    public String getRegisteredAddress() {
        return this.registeredAddress;
    }

    public void setRegisteredAddress(String registeredAddress) {
        this.registeredAddress = registeredAddress;
    }

    public String getIdentityNo() {
        return this.identityNo;
    }

    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo;
    }

    public Integer getMarriageStatus() {
        return this.marriageStatus;
    }

    public void setMarriageStatus(Integer marriageStatus) {
        this.marriageStatus = marriageStatus;
    }

    public String getPhotoUrl() {
        return this.photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Integer getUserType() {
        return this.userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getHomeAddress() {
        return this.homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getOtherContactWay() {
        return this.otherContactWay;
    }

    public void setOtherContactWay(String otherContactWay) {
        this.otherContactWay = otherContactWay;
    }

    public String getBankName1() {
        return this.bankName1;
    }

    public void setBankName1(String bankName1) {
        this.bankName1 = bankName1;
    }

    public String getBankNo1() {
        return this.bankNo1;
    }

    public void setBankNo1(String bankNo1) {
        this.bankNo1 = bankNo1;
    }

    public String getBankName2() {
        return this.bankName2;
    }

    public void setBankName2(String bankName2) {
        this.bankName2 = bankName2;
    }

    public String getBankNo2() {
        return this.bankNo2;
    }

    public void setBankNo2(String bankNo2) {
        this.bankNo2 = bankNo2;
    }

    public String getGraduationSchool() {
        return this.graduationSchool;
    }

    public void setGraduationSchool(String graduationSchool) {
        this.graduationSchool = graduationSchool;
    }

    public Timestamp getGraduationTime() {
        return graduationTime;
    }

    public void setGraduationTime(Timestamp graduationTime) {
        this.graduationTime = graduationTime;
    }

    public Integer getEduQualifications() {
        return eduQualifications;
    }

    public void setEduQualifications(Integer eduQualifications) {
        this.eduQualifications = eduQualifications;
    }

    public Integer getEduLevel() {
        return eduLevel;
    }

    public void setEduLevel(Integer eduLevel) {
        this.eduLevel = eduLevel;
    }

    public String getProfession() {
        return this.profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getComputerLevel() {
        return computerLevel;
    }

    public void setComputerLevel(String computerLevel) {
        this.computerLevel = computerLevel;
    }

    public String getForeignLanguage1() {
        return foreignLanguage1;
    }

    public void setForeignLanguage1(String foreignLanguage1) {
        this.foreignLanguage1 = foreignLanguage1;
    }

    public String getLanguageLevel1() {
        return languageLevel1;
    }

    public void setLanguageLevel1(String languageLevel1) {
        this.languageLevel1 = languageLevel1;
    }

    public String getForeignLanguage2() {
        return foreignLanguage2;
    }

    public void setForeignLanguage2(String foreignLanguage2) {
        this.foreignLanguage2 = foreignLanguage2;
    }

    public String getLanguageLevel2() {
        return languageLevel2;
    }

    public void setLanguageLevel2(String languageLevel2) {
        this.languageLevel2 = languageLevel2;
    }

    public String getForeignLanguage3() {
        return foreignLanguage3;
    }

    public void setForeignLanguage3(String foreignLanguage3) {
        this.foreignLanguage3 = foreignLanguage3;
    }

    public String getLanguageLevel3() {
        return languageLevel3;
    }

    public void setLanguageLevel3(String languageLevel3) {
        this.languageLevel3 = languageLevel3;
    }

    public String getStrongPoint() {
        return this.strongPoint;
    }

    public void setStrongPoint(String strongPoint) {
        this.strongPoint = strongPoint;
    }

    public String getPostState() {
        return this.postState;
    }

    public void setPostState(String postState) {
        this.postState = postState;
    }

    public String getVouchRecord() {
        return this.vouchRecord;
    }

    public void setVouchRecord(String vouchRecord) {
        this.vouchRecord = vouchRecord;
    }

    public String getSocialSecurityState() {
        return this.socialSecurityState;
    }

    public void setSocialSecurityState(String socialSecurityState) {
        this.socialSecurityState = socialSecurityState;
    }

    public String getHealthCheckRecord() {
        return this.healthCheckRecord;
    }

    public void setHealthCheckRecord(String healthCheckRecord) {
        this.healthCheckRecord = healthCheckRecord;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAttachment() {
        return this.attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }

    public String getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(String roleNames) {
        this.roleNames = roleNames;
    }

    public String getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(String groupIds) {
        this.groupIds = groupIds;
    }

    public String getGroupNames() {
        return groupNames;
    }

    public void setGroupNames(String groupNames) {
        this.groupNames = groupNames;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getYearHoliday() {
        return yearHoliday;
    }

    public void setYearHoliday(Integer yearHoliday) {
        this.yearHoliday = yearHoliday;
    }

    public String getHealthInfo() {
        return healthInfo;
    }

    public void setHealthInfo(String healthInfo) {
        this.healthInfo = healthInfo;
    }

    public Timestamp getPartyTime() {
        return partyTime;
    }

    public void setPartyTime(Timestamp partyTime) {
        this.partyTime = partyTime;
    }

    public Integer getRegisteredType() {
        return registeredType;
    }

    public void setRegisteredType(Integer registeredType) {
        this.registeredType = registeredType;
    }

    public String getResumeInfo() {
        return resumeInfo;
    }

    public void setResumeInfo(String resumeInfo) {
        this.resumeInfo = resumeInfo;
    }

    public Timestamp getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Timestamp joinTime) {
        this.joinTime = joinTime;
    }

    public Integer getLunarBirthDay() {
        return lunarBirthDay;
    }

    public void setLunarBirthDay(Integer lunarBirthDay) {
        this.lunarBirthDay = lunarBirthDay;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public String getAdministrationLevel() {
        return administrationLevel;
    }

    public void setAdministrationLevel(String administrationLevel) {
        this.administrationLevel = administrationLevel;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(Integer jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public Integer getJobTitleLevel() {
        return jobTitleLevel;
    }

    public void setJobTitleLevel(Integer jobTitleLevel) {
        this.jobTitleLevel = jobTitleLevel;
    }

    public Integer getStation() {
        return station;
    }

    public void setStation(Integer station) {
        this.station = station;
    }

    public Integer getCompanyWorkAge() {
        return companyWorkAge;
    }

    public void setCompanyWorkAge(Integer companyWorkAge) {
        this.companyWorkAge = companyWorkAge;
    }

    public Timestamp getStartPayTime() {
        return startPayTime;
    }

    public void setStartPayTime(Timestamp startPayTime) {
        this.startPayTime = startPayTime;
    }

    public Integer getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(Integer workStatus) {
        this.workStatus = workStatus;
    }

    public Integer getTotalWorkAge() {
        return totalWorkAge;
    }

    public void setTotalWorkAge(Integer totalWorkAge) {
        this.totalWorkAge = totalWorkAge;
    }

    public Timestamp getJoinWorkTime() {
        return joinWorkTime;
    }

    public void setJoinWorkTime(Timestamp joinWorkTime) {
        this.joinWorkTime = joinWorkTime;
    }

    public String getMsn() {
        return msn;
    }

    public void setMsn(String msn) {
        this.msn = msn;
    }

    public String getNativityPlaceSelect() {
        return nativityPlaceSelect;
    }

    public void setNativityPlaceSelect(String nativityPlaceSelect) {
        this.nativityPlaceSelect = nativityPlaceSelect;
    }

    public String getPartyTimeStr() {
        return partyTimeStr;
    }

    public void setPartyTimeStr(String partyTimeStr) {
        this.partyTimeStr = partyTimeStr;
        this.partyTime=DateUtils.dateShort2DateNoExp(partyTimeStr);
    }

    public String getJoinTimeStr() {
        return joinTimeStr;
    }

    public void setJoinTimeStr(String joinTimeStr) {
        this.joinTimeStr = joinTimeStr;
        this.joinTime=DateUtils.dateShort2DateNoExp(joinTimeStr);
    }

    public String getGraduationTimeStr() {
        return graduationTimeStr;
    }

    public void setGraduationTimeStr(String graduationTimeStr) {
        this.graduationTimeStr = graduationTimeStr;
        this.graduationTime=DateUtils.dateShort2DateNoExp(graduationTimeStr);
    }

    public String getStartPayTimeStr() {
        return startPayTimeStr;
    }

    public void setStartPayTimeStr(String startPayTimeStr) {
        this.startPayTimeStr = startPayTimeStr;
        this.startPayTime=DateUtils.dateShort2DateNoExp(startPayTimeStr);
    }

    public String getJoinWorkTimeStr() {
        return joinWorkTimeStr;
    }

    public void setJoinWorkTimeStr(String joinWorkTimeStr) {
        this.joinWorkTimeStr = joinWorkTimeStr;
        this.joinWorkTime=DateUtils.dateShort2DateNoExp(joinWorkTimeStr);
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public UserInfo getCreateUserInfo() {
		return createUserInfo;
	}

	public void setCreateUserInfo(UserInfo createUserInfo) {
		this.createUserInfo = createUserInfo;
	}

	public Timestamp getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Timestamp lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public UserInfo getLastUpdateUserInfo() {
		return lastUpdateUserInfo;
	}

	public void setLastUpdateUserInfo(UserInfo lastUpdateUserInfo) {
		this.lastUpdateUserInfo = lastUpdateUserInfo;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	/**
	 * @return the loginName
	 */
	public String getLoginName() {
		return loginName;
	}

	/**
	 * @param loginName the loginName to set
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the workNo
	 */
	public String getWorkNo() {
		return workNo;
	}

	/**
	 * @param workNo the workNo to set
	 */
	public void setWorkNo(String workNo) {
		this.workNo = workNo;
	}

	/**
	 * @return the sex
	 */
	public Integer getSex() {
		return sex;
	}

	/**
	 * @param sex the sex to set
	 */
	public void setSex(Integer sex) {
		this.sex = sex;
	}

	/**
	 * @return the birthDay
	 */
	public Date getBirthDay() {
		return birthDay;
	}

	/**
	 * @param birthDay the birthDay to set
	 */
	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}
	
	public String getBirthDayStr() {
		return birthDayStr;
	}

	public void setBirthDayStr(String birthDayStr) {
		this.birthDayStr = birthDayStr;
	    this.birthDay=DateUtils.dateShort2DateNoExp(birthDayStr);
	}

	/**
	 * @return the job
	 */
	public String getJob() {
		return job;
	}

	/**
	 * @param job the job to set
	 */
	public void setJob(String job) {
		this.job = job;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the phone2
	 */
	public String getPhone2() {
		return phone2;
	}

	/**
	 * @param phone2 the phone2 to set
	 */
	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
    
    
	
	
	
}
