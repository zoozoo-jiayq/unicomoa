package cn.com.qytx.platform.org.dao;



import cn.com.qytx.platform.org.domain.UserInfo;
/**
 * 
 * <br/>功能: 人员Vo
 * <br/>版本: 1.0
 * <br/>开发人员: 任
 * <br/>创建日期: 2013-4-10
 * <br/>修改日期: 2013-4-10
 * <br/>修改列表:
 */
public class UserVo extends UserInfo
{
    /**
     * 序列号
     */
    private static final long serialVersionUID = 1L;

	//部门
    private Integer groupId;
	//角色
    private Integer roleId;
    //是否按登录时间排序
    private Boolean loginTimeOrder;
    //导入失败原因
    private String failureReason;
    // 查询来源 address 表示来自通讯簿 loginManager 表示来自登录用户管理
    private String from;
    
    //项目来源 OA TXZL
    public final String OA="OA";
    public final String TXZL="TXZL";
    private String projectName=OA;
    // 查看类型
    private String type;
    private Integer loginOrder;
    
    private String childrenGroupIds;//子部门
    
    //登录用户管理查询关键字
    private String keyWordForLogin;
    
    //登陆名，用户名，phone
    private String keyWordForLogin_hotline;
    
	public String getKeyWordForLogin() {
		return keyWordForLogin;
	}
	public void setKeyWordForLogin(String keyWordForLogin) {
		this.keyWordForLogin = keyWordForLogin;
	}
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public Boolean getLoginTimeOrder() {
		return loginTimeOrder;
	}
	public void setLoginTimeOrder(Boolean loginTimeOrder) {
		this.loginTimeOrder = loginTimeOrder;
	}
	public String getFailureReason() {
		return failureReason;
	}
	public void setFailureReason(String failureReason) {
		this.failureReason = failureReason;
	}
    public String getFrom()
    {
        return from;
    }
    public void setFrom(String from)
    {
        this.from = from;
    }
    public String getType()
    {
        return type;
    }
    public void setType(String type)
    {
        this.type = type;
    }
    public Integer getLoginOrder()
    {
        return loginOrder;
    }
    public void setLoginOrder(Integer loginOrder)
    {
        this.loginOrder = loginOrder;
    }
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getChildrenGroupIds() {
		return childrenGroupIds;
	}
	public void setChildrenGroupIds(String childrenGroupIds) {
		this.childrenGroupIds = childrenGroupIds;
	}
	public String getKeyWordForLogin_hotline() {
		return keyWordForLogin_hotline;
	}
	public void setKeyWordForLogin_hotline(String keyWordForLogin_hotline) {
		this.keyWordForLogin_hotline = keyWordForLogin_hotline;
	}
	
	
    
}
