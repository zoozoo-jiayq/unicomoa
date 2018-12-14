package cn.com.qytx.platform.org.domain;


import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import cn.com.qytx.platform.base.domain.Partition;
import cn.com.qytx.platform.org.domain.base.BaseUserInfo;

import com.google.gson.annotations.Expose;

/**
 * 功能：用户信息表
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 下午3:07:58 
 * 修改日期：下午3:07:58 
 * 修改列表：
 */
@Entity
@Table(name="tb_user_info")
public class UserInfo  extends BaseUserInfo{
	
    /**
     * 用户状态 未设置用户登录(删除登录用户后的状态)
     */
	@Transient
    public static final int USERSTATE_UNLOGIN = 2;
	
	//允许登陆
	@Transient
	public static final int USERSTATE_LOGIN = 0;
	
	//禁止登陆
	@Transient
	public static final int USERSTATE_FORBIDDEN_LOGIN = 1;
	
	//原始密码
	@Transient
	private String orialPassword;
	
	@Column(name="phone2",length=50)
	private String phone2;
	
	//称呼
	@Column(name="title",length=50)
    @Expose
	private String title;

    @Transient
    @Expose
    private String groupName;//群组名称，只为显示用
	
	@Transient
    private String ip;
	
	//签章类型；0不启用；1默认；2图片签章
	@Column(name="sign_type")
    private Integer signType = 0 ;
	
	//签章URL
	@Column(name="sign_url",length=100)
    private String signUrl;
	
	//印章URL
	@Column(name="ntko_url",length=100)
    private String ntkoUrl;
	
	@Column(name="skin_logo")
    private Integer skinLogo;//换肤标志
	
	//0,不启用；1启用
	//印章控件
	@Column(name="sign_widget")
	private Integer sinWidget;
	//office控件
	@Column(name="office_widget")
	private Integer officeWidget;
    //套打控件
    @Column(name="tao_da")
    private Integer taoDa;
    //办公室电话
    @Column(name="office_tel")
    private String officeTel;
    //家庭电话
    @Column(name="home_tel")
    private String homeTel;
    //个人权限
    @Column(name="user_power")
    private Integer userPower;

    //部门ID 与通讯助理兼容使用
    @Column(name="group_id")
    private Integer groupId;


    //分机号码(移动通讯助理业务中总机业务使用)
    @Column(name="user_num")
    private String userNum;
    //呼转对应类别(移动通讯助理业务中总机业务使用) 0：UserPhone, 1 ：UserOffPhone,2：UserHomPhone, 3：UserAnothPhone
    @Column(name="user_num_type")
    private Integer userNumType;

    //呼转类别(移动通讯助理业务中总机业务使用)
    @Column(name="turn_type")
    private Integer turnType;
    
    //呼转号码(移动通讯助理业务中总机业务使用)
    @Column(name="turn_list",length=50)
    private String turnList;

    //漏话提醒类别(移动通讯助理业务中总机业务使用)
    @Column(name="mcn_type")
    private Integer mcnType;

    //V网短号(移动通讯助理业务中使用)
    @Column(name="v_num")
    @Expose
    private String vNum;
    //角色权限(移动通讯助理业务中使用)
    @Column(name="role")
    private Integer role;
    //V网群组号码(移动通讯助理业务使用)
    @Column(name="v_group")
    private String vGroup;
    //签名(移动通讯助理业务使用)
    @Column(name="sign_name")
    private String signName;
    //创建人(移动通讯助理业务使用)
    @Column(name="create_user_id")
    private Integer createUserId;
    
    //控制手机端该用户是否展示 1,隐藏；0展示，默认0
    @Column(name="mobile_show_state")
    private Integer mobileShowState = 0;

    //分区字段，该字段值得生成规则：companyId%10
    @Partition
    @Column(name="partition_companyid")
    private Integer partitionCompanyId;
    
    /**
     * 用户姓名全拼 
     */
    @Column(name="full_py")
    private String fullPy;
    
    /**
     * 用户姓名拼音对应九宫格数字
     */
    @Column(name="formatted_number")
    private String formattedNumber;
    
    /**
     * 号码隐藏
     * 0关闭 1开启
     */
    @Column(name="hide_phone")
    private Integer hidePhone=0;
    
    /**
     * 高管模式
     * 0关闭 1开启
     */
    @Column(name="is_leader")
    private Integer isLeader=0;
    
    //一级部门
  	@Transient
  	private String firstGroupName;
  	
  	//二级部门
  	@Transient
	private String secondGroupName;
	
	//三级部门
  	@Transient
	private String thirdGroupName;
	
	//四级部门
  	@Transient
	private String fourthGroupName;
	
	//五级部门
  	@Transient
	private String fifthGroupName;
	
	//六级部门
  	@Transient
	private String SixthGroupName;
	
	//导入失败原因
  	@Transient
	private String failureReason;
  	
  	@Transient
  	private Integer isLogined;
  	
  	@Column(name="is_fork_group")
 	private Integer isForkGroup;

    //是否订购   0未订购1订购
    @Column(name="OrderType")
    private Integer orderType = 0;
    
    //订购时间
    @Column(name="OrderChangedDate")
    private Timestamp orderChangedDate;
  	
	public Integer getIsForkGroup() {
		return isForkGroup;
	}

	public void setIsForkGroup(Integer isForkGroup) {
		this.isForkGroup = isForkGroup;
	}

	public Integer getMobileShowState() {
		return mobileShowState;
	}

	public void setMobileShowState(Integer mobileShowState) {
		this.mobileShowState = mobileShowState;
	}

	public Integer getSinWidget() {
		return sinWidget;
	}

	public void setSinWidget(Integer sinWidget) {
		this.sinWidget = sinWidget;
	}

	public Integer getOfficeWidget() {
		return officeWidget;
	}

	public void setOfficeWidget(Integer officeWidget) {
		this.officeWidget = officeWidget;
	}

	public Integer getTaoDa() {
		return taoDa;
	}

	public void setTaoDa(Integer taoDa) {
		this.taoDa = taoDa;
	}

	public String getNtkoUrl() {
		return ntkoUrl;
	}

	public void setNtkoUrl(String ntkoUrl) {
		this.ntkoUrl = ntkoUrl;
	}

	public String getSignUrl() {
		return signUrl;
	}

	public void setSignUrl(String signUrl) {
		this.signUrl = signUrl;
	}

	public Integer getSignType() {
		return signType;
	}

	public void setSignType(Integer signType) {
		this.signType = signType;
	}

    public String getGroupName() {
        return groupName;
    }
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}



    public Integer getSkinLogo() {
		return skinLogo;
	}

	public void setSkinLogo(Integer skinLogo) {
		this.skinLogo = skinLogo;
	}

	public String getOfficeTel() {
        return officeTel;
    }

    public void setOfficeTel(String officeTel) {
        this.officeTel = officeTel;
    }

    public String getHomeTel() {
        return homeTel;
    }

    public void setHomeTel(String homeTel) {
        this.homeTel = homeTel;
    }

    public Integer getUserPower() {
        return userPower;
    }

    public void setUserPower(Integer userPower) {
        this.userPower = userPower;
    }

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

    public Integer getUserNumType() {
        return userNumType;
    }

    public void setUserNumType(Integer userNumType) {
        this.userNumType = userNumType;
    }

    public Integer getTurnType() {
        return turnType;
    }

    public void setTurnType(Integer turnType) {
        this.turnType = turnType;
    }

    public Integer getMcnType() {
        return mcnType;
    }

    public void setMcnType(Integer mcnType) {
        this.mcnType = mcnType;
    }

    public String getVNum() {
        return vNum;
    }

    public void setVNum(String vNum) {
        this.vNum = vNum;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getVGroup() {
        return vGroup;
    }

    public void setVGroup(String vGroup) {
        this.vGroup = vGroup;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

	public String getOrialPassword() {
		return orialPassword;
	}

	public void setOrialPassword(String orialPassword) {
		this.orialPassword = orialPassword;
	}

	/**
	 * @return the turnList
	 */
	public String getTurnList() {
		return turnList;
	}

	/**
	 * @param turnList the turnList to set
	 */
	public void setTurnList(String turnList) {
		this.turnList = turnList;
	}

	/**
	 * @return the vNum
	 */
	public String getvNum() {
		return vNum;
	}

	/**
	 * @param vNum the vNum to set
	 */
	public void setvNum(String vNum) {
		this.vNum = vNum;
	}

	/**
	 * @return the vGroup
	 */
	public String getvGroup() {
		return vGroup;
	}

	/**
	 * @param vGroup the vGroup to set
	 */
	public void setvGroup(String vGroup) {
		this.vGroup = vGroup;
	}

	/**
	 * @return the createUserId
	 */
	public Integer getCreateUserId() {
		return createUserId;
	}

	/**
	 * @param createUserId the createUserId to set
	 */
	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}
	
	/**
	 * @return the firstGroupName
	 */
	public String getFirstGroupName() {
		return firstGroupName;
	}
	/**
	 * @param firstGroupName the firstGroupName to set
	 */
	public void setFirstGroupName(String firstGroupName) {
		this.firstGroupName = firstGroupName;
	}
	/**
	 * @return the secondGroupName
	 */
	public String getSecondGroupName() {
		return secondGroupName;
	}
	/**
	 * @param secondGroupName the secondGroupName to set
	 */
	public void setSecondGroupName(String secondGroupName) {
		this.secondGroupName = secondGroupName;
	}
	/**
	 * @return the thirdGroupName
	 */
	public String getThirdGroupName() {
		return thirdGroupName;
	}
	/**
	 * @param thirdGroupName the thirdGroupName to set
	 */
	public void setThirdGroupName(String thirdGroupName) {
		this.thirdGroupName = thirdGroupName;
	}
	/**
	 * @return the fourthGroupName
	 */
	public String getFourthGroupName() {
		return fourthGroupName;
	}
	/**
	 * @param fourthGroupName the fourthGroupName to set
	 */
	public void setFourthGroupName(String fourthGroupName) {
		this.fourthGroupName = fourthGroupName;
	}
	/**
	 * @return the fifthGroupName
	 */
	public String getFifthGroupName() {
		return fifthGroupName;
	}
	/**
	 * @param fifthGroupName the fifthGroupName to set
	 */
	public void setFifthGroupName(String fifthGroupName) {
		this.fifthGroupName = fifthGroupName;
	}
	/**
	 * @return the sixthGroupName
	 */
	public String getSixthGroupName() {
		return SixthGroupName;
	}
	/**
	 * @param sixthGroupName the sixthGroupName to set
	 */
	public void setSixthGroupName(String sixthGroupName) {
		SixthGroupName = sixthGroupName;
	}
	/**
	 * @return the failureReason
	 */
	public String getFailureReason() {
		return failureReason;
	}
	/**
	 * @param failureReason the failureReason to set
	 */
	public void setFailureReason(String failureReason) {
		this.failureReason = failureReason;
	}

	public String getFullPy() {
		return fullPy;
	}

	public void setFullPy(String fullPy) {
		this.fullPy = fullPy;
	}

	public String getFormattedNumber() {
		return formattedNumber;
	}

	public void setFormattedNumber(String formattedNumber) {
		this.formattedNumber = formattedNumber;
	}

	public Integer getPartitionCompanyId() {
		return partitionCompanyId;
	}

	public void setPartitionCompanyId(Integer partitionCompanyId) {
		this.partitionCompanyId = partitionCompanyId;
	}

	public Integer getIsLogined() {
		return isLogined;
	}

	public void setIsLogined(Integer isLogined) {
		this.isLogined = isLogined;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public Timestamp getOrderChangedDate() {
		return orderChangedDate;
	}

	public void setOrderChangedDate(Timestamp orderChangedDate) {
		this.orderChangedDate = orderChangedDate;
	}

	public Integer getHidePhone() {
		return hidePhone;
	}

	public void setHidePhone(Integer hidePhone) {
		this.hidePhone = hidePhone;
	}

	public Integer getIsLeader() {
		return isLeader;
	}

	public void setIsLeader(Integer isLeader) {
		this.isLeader = isLeader;
	}

}