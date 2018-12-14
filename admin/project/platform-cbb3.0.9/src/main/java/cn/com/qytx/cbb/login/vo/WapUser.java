package cn.com.qytx.cbb.login.vo;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import cn.com.qytx.platform.org.domain.ModuleInfo;

import com.google.gson.annotations.Expose;

/**
 * 功能:登录信息VO类
 * 版本:1.0
 * 开发人员: 汤波涛
 * 创建日期: 13-5-9 上午10:41
 * 修改日期: 13-5-9 上午10:41
 * 修改人员: 汤波涛
 * 修改列表:
 */
public class WapUser {
 
	@Expose
	private String userName;
    /**
     * 显示名
     */
	@Expose
    private String userShowName;
	@Expose
    private Integer sex;
	@Expose
	private String workNo;
	@Expose
	private String job;
	@Expose
	private String birthDay;
	@Expose
	private String email;
	@Expose
    private String py;
	@Expose
    private Integer companyId;
	@Expose
    private String loginName;
	@Expose
    private String loginPass;
	@Expose
    private String phone;
	@Expose
    private String phone2;
	@Expose
	private String signName;
    @Expose
	private int groupId;
    /**
     * 密码
     */
	@Expose
	private String userPass;

    /**
     * 新密码，供给修改密码时使用
     */
	@Expose
    private String newUserPass;

    /**
     * 用户ID
     */
	@Expose
    private int userId;

    /**
     * session id
     */
	@Expose
    private String jsessionid;

    /**
     * 首页菜单Map集合，key：模块名，value：菜单URL
     */
	@Expose
    private LinkedHashMap<String, String> moduleInfoMap;

    /**
     * 模块任务数量结婚，key：模块名，value：数量
     */
	@Expose
    private HashMap<String, Integer> moduleCountMap;
	@Expose
    private List<ModuleInfo> moduleList;
    
	@Expose
	private String photo;
	
	//是否强制修改密码，yes修改，no不修改
	@Expose
    private String noticeUpdatePwd ; 

    public String getNoticeUpdatePwd() {
        return noticeUpdatePwd;
    }

    public void setNoticeUpdatePwd(String noticeUpdatePwd) {
        this.noticeUpdatePwd = noticeUpdatePwd;
    }

    public List<ModuleInfo> getModuleList() {
		return moduleList;
	}

	public void setModuleList(List<ModuleInfo> moduleList) {
		this.moduleList = moduleList;
	}

	/*
     * 扩展List，后期使用
     */
	@Expose
    private List extendList;

   

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getJsessionid() {
        return jsessionid;
    }

    public void setJsessionid(String jsessionid) {
        this.jsessionid = jsessionid;
    }

    public LinkedHashMap<String, String> getModuleInfoMap() {
        return moduleInfoMap;
    }

    public void setModuleInfoMap(LinkedHashMap<String, String> moduleInfoMap) {
        this.moduleInfoMap = moduleInfoMap;
    }

    public String getNewUserPass() {
        return newUserPass;
    }

    public void setNewUserPass(String newUserPass) {
        this.newUserPass = newUserPass;
    }

    public HashMap<String, Integer> getModuleCountMap() {
        return moduleCountMap;
    }

    public void setModuleCountMap(HashMap<String, Integer> moduleCountMap) {
        this.moduleCountMap = moduleCountMap;
    }

    public List getExtendList() {
        return extendList;
    }

    public void setExtendList(List extendList) {
        this.extendList = extendList;
    }

    public String getUserShowName() {
        return userShowName;
    }

    public void setUserShowName(String userShowName) {
        this.userShowName = userShowName;
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

	public String getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPy() {
		return py;
	}

	public void setPy(String py) {
		this.py = py;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginPass() {
		return loginPass;
	}

	public void setLoginPass(String loginPass) {
		this.loginPass = loginPass;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getPhone2()
    {
        return phone2;
    }

    public void setPhone2(String phone2)
    {
        this.phone2 = phone2;
    }

	public String getSignName() {
		return signName;
	}

	public void setSignName(String signName) {
		this.signName = signName;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
}
