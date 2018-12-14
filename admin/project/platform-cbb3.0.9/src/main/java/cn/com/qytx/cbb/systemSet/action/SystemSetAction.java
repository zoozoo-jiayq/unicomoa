package cn.com.qytx.cbb.systemSet.action;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.log.domain.Log;
import cn.com.qytx.platform.log.service.ILog;
import cn.com.qytx.platform.log.service.LogType;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.RoleInfo;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IGroup;
import cn.com.qytx.platform.org.service.IRole;
import cn.com.qytx.platform.org.service.IRoleUser;
import cn.com.qytx.platform.org.service.IUser;
import cn.com.qytx.platform.utils.encrypt.MD5;
import cn.com.qytx.platform.utils.pinyin.Pinyin4jUtil;

/**
 * 功能: 系统设置 action
 * 版本: 1.0
 * 开发人员: 冯东旭
 * 创建日期: 2013-3-21
 * 修改日期: 2013-3-21
 * 修改列表:
 */
public class SystemSetAction  extends BaseActionSupport {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    
    @Resource(name = "roleUserService")
    private IRoleUser roleUserService;

    @Resource(name = "userService")
    private IUser userService;
    
    @Resource(name="roleService")
    private IRole roleService;
    
    @Autowired
    private IGroup groupService;
    /** 系统日志接口 */
	@Resource
    private ILog logService;
//    @Resource(name = "logImpl")
//    ILog logImpl;
//    
//    @Resource(name="userConfigService")
//    private UserConfigService userConfigService;
    //档案ID
    private Integer recordId;
    //人员ID
    private Integer userId;
    //人员对象
    private UserInfo user;
    //主要角色
    private String roleMain;
    //辅助角色
    private String roleMinor;
    //管理范围
    private String managementRange;
    //时间
    private String time;
    
    private String birthDay;
    
    //原密码
    private String oldPass;
    
    private Integer isTXZL=0;

	/**
     * 跳转到个人认识档案修改页面
     * 功能：
     * @return
	 * @throws ParseException 
     */
    public  String toRecordSet() throws ParseException{
    	UserInfo ui =  getLoginUser();
    	user = userService.findOne( ui.getUserId());
    	if(user.getBirthDay()!=null){
    		SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd");
    		birthDay = sdf.format(user.getBirthDay());
    	}
    	//主角色
    	StringBuffer s1=new StringBuffer();
//        List<RoleInfo> l1 = roleUserService.findRolesByUserId(ui.getUserId(),1,1);
    	List<RoleInfo> l1 = roleService.findRolesByUserId(ui.getUserId(), 1);
        if(l1!=null&&l1.size()!=0){
        	for(int i=0;i<l1.size();i++){
        		s1.append(l1.get(i).getRoleName());
        		if(i<l1.size()-1){
        			s1.append(",");
        		}
        	}
        }
        GroupInfo group = groupService.getGroupByUserId(ui.getCompanyId(), ui.getUserId());   //获取部门信息
        if(group!=null){
            this.getRequest().setAttribute("groupName", group.getGroupName());
        }else{
        	this.getRequest().setAttribute("groupName", "");
        }
        roleMain = s1.toString();

    	return SUCCESS;
    }

    private GroupInfo getGroupInfo(final List<GroupInfo> groupList) {
        GroupInfo group = null;
        for (GroupInfo info : groupList) {
            //类型为部门
            if (info.getGroupType() == 1) {
                group = info;
                break;
            }
        }
        return group;
    }
    
    
    /**
     * 跳转到密码修改页面
     * 功能：
     * @return
     */
    public  String toPwdSet(){
    	UserInfo ui =  getLoginUser();
    	user = userService.findOne(ui.getUserId());
    	if(user.getLastUpdateTime()!=null){
    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    		time = sdf.format(user.getLastUpdateTime());
    	}
    	user.setLastUpdateTime(new Date());
    	if(time==null||time.equals("")){
    		time ="无";
    	}
    	return SUCCESS;
    }
    
    /**
     * 功能：验证密码是否正确
     * @return
     */
    public String validatePwd(){
    	UserInfo ui = userService.findOne( userId);
    	int retType = 0;
    	
    	MD5 md5=new MD5();
    	String oldPass = md5.encrypt(user.getLoginPass());
    	if(isTXZL!=null && isTXZL.intValue() == 1){
    		oldPass = user.getLoginPass();
    	}
    	if(!ui.getLoginPass().equals(oldPass)){
    		retType =1;
    	}
        ajax(retType);
		return null;
    }
    /**
     * 密码修改
     * 功能：
     * @return
     */
    public String updatePwd(){
	    String result = "0";
        UserInfo userInfo = getLoginUser();            
    	UserInfo ui = userService.findOne( userId);
    	if (null == userInfo){
            userInfo = ui;
            result="100||0";
        }
    	MD5 md5=new MD5();
    	String oldPass1 = md5.encrypt(oldPass);
    	if(isTXZL!=null && isTXZL.intValue() == 1){
    		oldPass1 = oldPass;
    	}
    	if(!oldPass1.equals(userInfo.getLoginPass())){
    		ajax("1");
    	}else{
        	String newPass = md5.encrypt(user.getLoginPass());
        	if(isTXZL!=null && isTXZL.intValue() == 1){
        		newPass = user.getLoginPass();
        	}
        	ui.setLoginPass(newPass);
        	ui.setLastUpdateTime(new Date());
        	userService.saveOrUpdate(ui);
        	getSession().setAttribute("adminUser", ui);
    		Log log = new Log();
    		log.setCompanyId(userInfo.getCompanyId());
    		log.setInsertTime(new Timestamp(new Date().getTime()));
    		log.setIp(this.getRequest().getRemoteAddr());
    		log.setIsDelete(0);
    		log.setLogType(LogType.LOG_USER_UPDATE_LOGPASS);
    		log.setRefId(userInfo.getUserId());
    		log.setRemark("密码修改成功");
    		log.setUserId(userInfo.getUserId());
    		log.setUserName(userInfo.getUserName());
    		log.setType(0);//手动添加系统日志
    		logService.saveOrUpdate(log);
    		ajax(result);
    	}    	
		return null;
    }
    
    /**
     * 基础资料修改
     * 功能：
     * @return
     */
    public String updateRecord(){
    	UserInfo ui = userService.findOne( userId);
    	
    	ui.setUserName(user.getUserName());
    	ui.setSex(user.getSex());
    	if(StringUtils.isNotBlank(birthDay)){
    		try {
    			user.setBirthDay(DateUtils.parseDate(birthDay, "yyyy-MM-dd"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
    	}
    	ui.setBirthDay(user.getBirthDay());
    	ui.setPhone(user.getPhone());
    	ui.setPhone2(user.getPhone2());
    	ui.setEmail(user.getEmail());
    	ui.setSignName(user.getSignName());
    	ui.setSignType(user.getSignType());
    	ui.setTaoDa(user.getTaoDa());
    	ui.setPhoto(user.getPhoto());
    	ui.setAlterName(user.getAlterName());
    	ui.setOfficeWidget(user.getOfficeWidget());
    	ui.setSinWidget(user.getSinWidget());
    	String pinyin = Pinyin4jUtil.getPinYinHeadChar(user.getUserName());
    	ui.setPy(pinyin);
    	if(user.getSignUrl()!=null && !user.getSignUrl().equals("")){
    		ui.setSignUrl(user.getSignUrl());
    	}
    	if(user.getNtkoUrl()!=null && !user.getNtkoUrl().equals("")){
    		ui.setNtkoUrl(user.getNtkoUrl());
    	}
    	user = userService.saveOrUpdate(ui);
    	getSession().setAttribute("adminUser", user);
		ajax(0);
		return null;
    }
    
	public Integer getRecordId() {
		return recordId;
	}
	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public UserInfo getUser() {
		return user;
	}
	public void setUser(UserInfo user) {
		this.user = user;
	}

	public String getRoleMain() {
		return roleMain;
	}

	public void setRoleMain(String roleMain) {
		this.roleMain = roleMain;
	}

	public String getRoleMinor() {
		return roleMinor;
	}

	public void setRoleMinor(String roleMinor) {
		this.roleMinor = roleMinor;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getManagementRange() {
		return managementRange;
	}

	public void setManagementRange(String managementRange) {
		this.managementRange = managementRange;
	}

	public String getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}

	public String getOldPass() {
		return oldPass;
	}

	public void setOldPass(String oldPass) {
		this.oldPass = oldPass;
	}

	public Integer getIsTXZL() {
		return isTXZL;
	}

	public void setIsTXZL(Integer isTXZL) {
		this.isTXZL = isTXZL;
	}
	
}
