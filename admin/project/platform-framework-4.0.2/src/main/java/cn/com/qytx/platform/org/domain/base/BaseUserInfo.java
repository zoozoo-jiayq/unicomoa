package cn.com.qytx.platform.org.domain.base;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import cn.com.qytx.platform.base.domain.BaseDomain;
import cn.com.qytx.platform.base.domain.DeleteState;

import com.google.gson.annotations.Expose;


/**
 * 功能：用户基本信息表
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 下午3:07:58 
 * 修改日期：下午3:07:58 
 * 修改列表：
 */
@MappedSuperclass
public class BaseUserInfo  extends BaseDomain{

	@Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	private Integer userId;
	
	//用户姓名
	@Column(name="user_name",length=50)
	@Expose
	private String userName;
	
	//登录名
	@Column(name="login_name",length=50,unique = true)
	private String loginName;
	
	//登录密码
	@Column(name="login_pass",length=50)
	private String loginPass;
	
	@Column(name="Phone",length=50)
	@Expose
	private String phone;
	
	@Column(name="Sex")
	private Integer sex;
	
	//注册时间
	@Column(name="register_time")
	private Date registerTime;
	
	//工号
	@Column(name="work_no",length=50)
	private String workNo;
	
	//职位
	@Column(name="Job",length=50)
    @Expose
	private String job;
	
	//生日
	@Column(name="Birthday")
	private Date birthDay;
	
	//邮箱
	@Column(name="Email",length=50)
    @Expose
	private String email;
	
	
	//序列
	@Column(name="order_index")
	private Integer orderIndex;
	
	
	//拼音
	@Column(name="PY",length=50)
	@Expose
    private String py;

	//最后一次登录时间
	@Column(name="last_login_time")
	private Date lastLoginTime;
	
	@DeleteState
	@Column(name="is_delete")
    private Integer isDelete = 0;
	
	//电话号码是否公开<!-- 1电话号码公开，0电话号码不公开  -->
	@Column(name="phone_public")
    private Integer phonePublic;
	
	//别名
	@Column(name="alter_name",length=50)
    private String alterName;

    //头像
    @Column(name="Photo",length=300)
    private String photo;
	//<!-- 是否为系统默认用户 0默认用户,不允许维护权限及删除 1为配置用户  -->
	@Column(name="is_default")
    private Integer isDefault;

	//用户状态,是否允许登陆  1否；0允许
	@Column(name="user_state")
	private Integer userState;
	
	//最后一次修改时间
	@Column(name="last_update_time")
	private Date lastUpdateTime;
	
	@Column(name="create_time")
	private Date createTime;
	
	//是否是虚拟人 0不是 1是
	@Column(name="is_virtual")
	private Integer isVirtual;
	
	//如果是虚拟人，则代表关联的主用户id
	@Column(name="link_id")
	private Integer linkId;
	
	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public String getPy() {
        return py;
    }

    public void setPy(String py) {
        this.py = py;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }



    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getWorkNo() {
        return workNo;
    }

    public void setWorkNo(String workNo) {
        this.workNo = workNo;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    public String getLoginPass() {
        return loginPass;
    }

    public void setLoginPass(String loginPass) {
        this.loginPass = loginPass;
    }

    public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Integer getPhonePublic() {
		return phonePublic;
	}

	public void setPhonePublic(Integer phonePublic) {
		this.phonePublic = phonePublic;
	}
	
	public String getAlterName() {
		return alterName;
	}

	public void setAlterName(String alterName) {
		this.alterName = alterName;
	}	
	
	public Integer getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}

	public Integer getUserState() {
		return userState;
	}

	public void setUserState(Integer userState) {
		this.userState = userState;
	}

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getIsVirtual() {
		return isVirtual;
	}

	public void setIsVirtual(Integer isVirtual) {
		this.isVirtual = isVirtual;
	}

	public Integer getLinkId() {
		return linkId;
	}

	public void setLinkId(Integer linkId) {
		this.linkId = linkId;
	}

    
}