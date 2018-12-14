package cn.com.qytx.cbb.org.action;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import cn.com.qytx.cbb.util.UpdateFieldUtil;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.log.domain.Log;
import cn.com.qytx.platform.log.service.ILog;
import cn.com.qytx.platform.log.service.LogType;
import cn.com.qytx.platform.org.domain.RoleInfo;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IGroup;
import cn.com.qytx.platform.org.service.IGroupUser;
import cn.com.qytx.platform.org.service.IRole;
import cn.com.qytx.platform.org.service.IRoleUser;
import cn.com.qytx.platform.org.service.IUser;
import cn.com.qytx.platform.utils.encrypt.MD5;
import cn.com.qytx.platform.utils.pinyin.FormatPinyinTo9Data;
import cn.com.qytx.platform.utils.pinyin.Pinyin4jUtil;
import cn.com.qytx.platform.utils.validate.Validate;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * <br/>
 * 功能:更新人员信息 <br/>
 * 版本: 1.0 <br/>
 * 开发人员: 任 <br/>
 * 创建日期: 2013-4-9 <br/>
 * 修改日期: 2013-4-9 <br/>
 * 修改列表:
 */
public class UserUpdateAction extends BaseActionSupport
{

    /**
     * 用户信息
     */
    @Resource(name = "userService")
    IUser userService;
    /**
     * 部门人员信息
     */
    @Resource(name = "groupUserService")
    IGroupUser groupUserService;

    
    /**
     * 角色人员信息
     */
    @Resource(name = "roleUserService")
    IRoleUser roleUserService;

    /**
     * 部门,群组管理接口
     */
    @Resource
    private IGroup groupService;

    @Resource
    private IRole roleService;
    
    /** 系统日志接口 */
	@Resource
    private ILog logService;			

    

    // 用户
    private UserInfo user;
    // 组id
    private int groupId;
    // 主角色id字符串
    private String roleIds;
    // 辅助角色id字符串
    private String assistIds;

    // 管理范围
    private Integer managementRange;
    // 管理组Id集合
    private String appointGroupIds;
    // 管理组名集合
    private String appointGroupNames;
    // 操作类型 根据type区分是维护通讯簿还是维护登录用户
    private String type;

    // 修改时,修改前的用户ID
    private Integer oldUserId;
    /**
     * 角色名称
     */
    private String roleNames;
    private String birthDay;
    
    private String otherGroupJson;
    
    /**
     * 更新人员
     * @return
     */
    @SuppressWarnings("unchecked")
    public String updateUser()
    {
     try{
    	if (StringUtils.isEmpty(type))
        {
            // 如果登录名不为空
            if (StringUtils.isNotEmpty(user.getLoginName()))
            {
                if (!Validate.isLoginName(user.getLoginName()))
                {
                   ajax("用户名必须为数字、字母或者下划线！");
                   return null;
                }
            }
            else
            {
                ajax("登录名不能为空！");
                return null;
            }
            if (StringUtils.isNotEmpty(user.getLoginPass()))
            {
                if (!Validate.isLoginPass(user.getLoginPass()))
                {
                    ajax("密码格式不正确, 长度6-16位，区分大小写，只能使用字母、数字！");
                    return null;
                }
            }
        }

        if (StringUtils.isNotEmpty(user.getEmail()))
        {
            if (!Validate.isEmail(user.getEmail()))
            {
                ajax("邮箱格式不正确！");
                return null;
            }
        }
        if (StringUtils.isNotEmpty(user.getPhone()))
        {
            if (!Validate.isPhone(user.getPhone()))
            {
                ajax("手机格式不正确！");
                return null;
            }
        }
        
        UserInfo userOld = userService.findOne(user.getUserId());
        List<UserInfo> us = userService.findUsersByPhone(user.getPhone());
        if(us!=null && us.size()>0){
            for(int i=0; i<us.size(); i++){
                UserInfo u = us.get(i);
                if(userOld.getIsVirtual()!=null && userOld.getIsVirtual().intValue() == 1){
                	//当是虚拟关联人的时候，不判断关联的主用户和与该用户关联同一用户的虚拟关联人
                	if(userOld.getLinkId().equals(u.getUserId()) || (u.getIsVirtual() != null && u.getIsVirtual().intValue() ==1 && userOld.getLinkId().equals(u.getLinkId()))){
                		continue;
                	}
                }
                
                //如果是当前用户的虚拟关联人，则不判断重复号码
                if(u.getIsVirtual() != null && u.getIsVirtual().intValue() == 1 && userOld.getUserId().equals(u.getLinkId())){
                	continue;
                }
                if((u.getUserId().intValue() != user.getUserId()) && (u.getPhone().equals(user.getPhone()))){
                    ajax("手机号码不能重复!");
                    return null;
                }
            }
        }
        
        // 判断姓名和电话不能同时重复
        UserInfo existUser = userService.getUserByNamePhone(getLoginUser().getCompanyId(),user.getUserName(), user.getPhone());
        // TODO 需要判断人员是否重复
        
        if (null != existUser && existUser.getUserName().equals(user.getUserName())
                && existUser.getPhone().equals(user.getPhone()) && userOld.getUserId().intValue() != existUser.getUserId().intValue() && (existUser.getIsVirtual() == null || existUser.getIsVirtual().intValue() != 1))
        {
            ajax("该成员已存在！");
            return null;
        }
        
        UserInfo tmp = userService.getUserByUserName(user.getLoginName());
        boolean isExist = userOld.getLoginName().equals(user.getLoginName());
        if (!isExist && tmp != null)
        {
            ajax("登录名已经存在！");
            return null;
        }
        if (groupId == 0)
        {
            ajax("请选择部门！");
            return null;
        }
        int oldGroupId = userOld.getGroupId();
        userOld.setUserName(user.getUserName());
        userOld.setOrderIndex(user.getOrderIndex());
        userOld.setAlterName(user.getAlterName());
        userOld.setSex(user.getSex());
        if(StringUtils.isNotBlank(birthDay)){
    		try {
    			userOld.setBirthDay(DateUtils.parseDate(birthDay, "yyyy-MM-dd"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
    	}else{
    		userOld.setBirthDay(null);
    	}
        userOld.setPhone(user.getPhone());
        userOld.setPhonePublic(user.getPhonePublic());
        userOld.setPhone2(user.getPhone2());
        userOld.setOfficeTel(user.getOfficeTel());
        userOld.setEmail(user.getEmail());
        userOld.setSignType(user.getSignType());
        userOld.setSinWidget(user.getSinWidget());
        userOld.setOfficeWidget(user.getOfficeWidget());
        userOld.setTaoDa(user.getTaoDa());
        userOld.setPhoto(user.getPhoto());
        userOld.setJob(user.getJob());
        if(user.getSignUrl()!=null && !user.getSignUrl().equals("")){
        	userOld.setSignUrl(user.getSignUrl());
        }
        if(user.getNtkoUrl()!=null && !user.getNtkoUrl().equals("")){
        	userOld.setNtkoUrl(user.getNtkoUrl());
        }

        String py = Pinyin4jUtil.getPinYinHeadChar(user.getUserName());
        userOld.setPy(py);
        //获得全拼拼音 带空格
        String fullPy = Pinyin4jUtil.getPinYinWithBlank(user.getUserName());
        userOld.setFullPy(fullPy);
        //获得用户姓名全拼对应九宫格按键
        String formattedNumber = FormatPinyinTo9Data.getFormattedNumber(fullPy);
        userOld.setFormattedNumber(formattedNumber);
        userOld.setGroupId(groupId);
        userOld.setLastUpdateTime(new Date());
        userOld.setPhone2(user.getPhone2());
        userOld.setHomeTel(user.getHomeTel());
        
        userService.addOrUpdateUserWithOrder(userOld);
        this.updateGroupIFUserChangeGroup(userOld, oldGroupId);
        
        if (user.getUserId() > 0)
        {
	        /**================判断修改虚拟关联人 start=======================**/
	        Gson json = new Gson();
	        if(StringUtils.isNotEmpty(otherGroupJson)){
	        	List<Map<String,String>> otherGroupUsers = json.fromJson(otherGroupJson,new TypeToken<List<Map<String,String>>>(){}.getType());
	        	for(int i=0;i<otherGroupUsers.size();i++){
	        		Map<String,String> map = otherGroupUsers.get(i);
	        		if(map.get("id")!=null&&StringUtils.isNotEmpty(map.get("id").toString())){
	        			if(map.get("isDelete")!=null && map.get("isDelete").toString().equals("1")){
	        				//如果在前台删除该用户的关联用户，则处理删除业务
	        				userService.deleteUserByIds(map.get("id").toString(),false,this.getLoginUser().getCompanyId());
	        				groupService.updateGroupTime(Integer.valueOf(map.get("groupId").toString()));//通知部门
	        				continue;
	        			}
	        		}
	        		
	        		UserInfo temp = new UserInfo();
	        		int tempOldGroupId = 0;
	        		if(map.get("id")!=null&&StringUtils.isNotEmpty(map.get("id").toString())&&(map.get("isDelete")==null || !map.get("isDelete").toString().equals("1"))){
	        			temp = userService.findOne(Integer.valueOf(map.get("id").toString()));
	        			tempOldGroupId = temp.getGroupId();
	        		}
	        		UpdateFieldUtil.update(userOld, temp,"userId","groupId","officeTel", "loginName","job","loginPass","userState","orderIndex","USERSTATE_UNLOGIN","USERSTATE_LOGIN","USERSTATE_FORBIDDEN_LOGIN","registerTime","lastLoginTime","createTime","lastUpdateTime");
					
					int iGroupId = Integer.valueOf(map.get("groupId").toString());
					String iJob = map.get("job")==null?"":map.get("job").toString();
					String iTelphone = map.get("telphone")==null?"":map.get("telphone").toString();
					int iOrderIndex = map.get("orderIndex")==null?0:(Integer.valueOf(map.get("orderIndex").toString()));
					
					temp.setGroupId(iGroupId);
					temp.setOfficeTel(iTelphone);
					temp.setJob(iJob);
					temp.setUserState(1);//不允许登陆
					temp.setIsVirtual(1);
					temp.setLinkId(userOld.getUserId());
					if(iOrderIndex!=0){
						temp.setOrderIndex(iOrderIndex);
					}else{
						int maxOrder = userService.getMaxOrderIndex(userOld.getCompanyId(), iGroupId);
						temp.setOrderIndex(maxOrder+1);
					}
					
					userService.addOrUpdateUserWithOrder(temp);
		            
		            if(tempOldGroupId != 0){
		            	this.updateGroupIFUserChangeGroup(temp, tempOldGroupId);
		            }
	        	}
	        }
	        
//	                
//	        List<UserInfo> virtualUsers = userService.findVirtualUsers(user.getUserId(), this.getLoginUser().getCompanyId());
//	        if(virtualUsers!=null && virtualUsers.size()>0){
//	        	for(UserInfo virtualUser:virtualUsers){
//	        		UpdateFieldUtil.update(user, virtualUser,"userId", "loginName","job","loginPass","userState","phone2","orderIndex","USERSTATE_UNLOGIN","USERSTATE_LOGIN","USERSTATE_FORBIDDEN_LOGIN");
//	    		    userService.addOrUpdateUserWithOrder(userOld,getLoginUser());
//	        	}
//	        }
	        /**================判断修改虚拟关联人 end=======================**/
	        
            ajax("success");
            return null;
        }
        else
        {
            ajax("人员修改失败！");
            return null;
        }
     }catch(Exception e){
    	 e.printStackTrace();
    	 ajax("人员修改失败！");
         return null;
     }
    }

    /**
     * 功能：新增或者修改登录用户管理
     */
    public String updateLoginUser()
    {
        // 如果登录名不为空
        if (StringUtils.isNotEmpty(user.getLoginName()))
        {
            if (!Validate.isLoginName(user.getLoginName()))
            {
                ajax("用户名必须为数字、字母或者下划线！");
                return null;
            }
        }
        else
        {
            ajax("登录名不能为空！");
            return null;
        }

        UserInfo userOld = userService.findOne(user.getUserId());

        // 新增时用户必须未配置过登录信息
        if ("add".equals(type))
        {
            Integer userState = userOld.getUserState();
            if (userState.intValue() != UserInfo.USERSTATE_UNLOGIN)
            {
                ajax("user.user_is_exist");
                return null;
            }
        }

        // 修改时,也需要按照新增处理,原因是可以选择其他用户
        if ("update".equals(type) && null != oldUserId
                && oldUserId.intValue() != user.getUserId().intValue())
        {
        	UserInfo original = userService.findOne(oldUserId.intValue());
            Integer userState = userOld.getUserState();
            if (userState.intValue() != UserInfo.USERSTATE_UNLOGIN)
            {
                ajax("user.user_is_exist");
                return null;
            }

            // 清空原用户登录信息
            original.setUserState(UserInfo.USERSTATE_UNLOGIN);

            // 更新用户名和密码信息,保证用户不能再登陆
            original.setLoginPass("");
            original.setLoginName(UUID.randomUUID().toString());
            userService.saveOrUpdate(original);

         // 同时删除该用户配置的角色信息
            roleUserService.saveRoleUsersByRoleIds(null, original.getUserId(),
                    original.getCompanyId(), 1, true);
        }

        // 需要判断登录名是否重复
        UserInfo tmp = userService.getUserByUserName(user.getLoginName());
        boolean isExist = userOld.getLoginName().equals(user.getLoginName());
        if (!isExist && tmp != null)
        {
            ajax("user.loginName_is_exist");
            return null;
        }

        // 设置登录名和密码
        String ln = user.getLoginName();
        userOld.setLoginName(ln);
        MD5 md5 = new MD5();
        if(StringUtils.isNotBlank(user.getLoginPass())){
        	 String pass = md5.encrypt(user.getLoginPass());
             userOld.setLoginPass(pass);
        }
        userOld.setUserState(user.getUserState());
        
        if(userOld.getRegisterTime()==null){
        	userOld.setRegisterTime(new Date());
        }
        userService.saveOrUpdate(userOld);

        
        if (user.getUserId() > 0)
        {
            if (StringUtils.isNotEmpty(roleIds))
            {
                // 添加主角色
                String[] roleIdArr = StringUtils.split(roleIds, ",");
                Integer[] roleIdArrInteger = new Integer[roleIdArr.length];
                for (int i = 0; i < roleIdArr.length; i++)
                {
                    String roleId = roleIdArr[i];
                    roleIdArrInteger[i] = Integer.parseInt(roleId);
                }
                roleUserService.saveRoleUsersByRoleIds(roleIdArrInteger, user.getUserId(),
                        getLoginUser().getCompanyId(), 1, true);
            }else{
            	roleUserService.deleteRoleUserByUserIds(user.getUserId()+"");
            }
            //系统日志添加
            UserInfo userInfo = (UserInfo)getSession().getAttribute("adminUser");
    		Log log = new Log();
    		log.setCompanyId(userInfo.getCompanyId());
    		log.setInsertTime(new Timestamp(new Date().getTime()));
    		log.setIp(this.getRequest().getRemoteAddr());
    		log.setIsDelete(0);
    		if("add".equals(type)){
    			log.setLogType(LogType.LOG_USER_ADD);//对照LogType类中的常量修改
    		}else if("update".equals(type)){
    			log.setLogType(LogType.LOG_USER_UPDATE);//对照LogType类中的常量修改
    		}
    		log.setRefId(userInfo.getUserId());
    		if("add".equals(type)){
    			log.setRemark("添加用户成功");
    		}else if("update".equals(type)){
    			log.setRemark("修改用户成功");
    		}
    		log.setUserId(userInfo.getUserId());
    		log.setUserName(userInfo.getUserName());
    		log.setType(0);//手动添加系统日志
    		logService.saveOrUpdate(log);

            ajax("success");
            return null;
        }
        else
        {
            ajax("人员登录设置失败！");
            return null;
        }

    }

    public String detailLoginUser()
    {
        user = userService.findOne( user.getUserId());

        // 获取用户配置的角色信息
        List<RoleInfo> roleList = roleService.findRolesByUserId(user.getUserId(), 1);
        
        roleIds = "";
        roleNames = "";
        for (RoleInfo roleInfo : roleList)
        {
            Integer roleId = roleInfo.getRoleId();
            String roleName = roleInfo.getRoleName();
            roleIds += roleId + ",";
            roleNames += roleName + ",";
        }

        if(!"".equals(roleNames)){
        	roleNames = roleNames.substring(0,roleNames.lastIndexOf(","));
        }
        // 获取单位/部门级联关系
        String groupName = groupService.getGroupNamePathByUserId(user.getCompanyId(), user.getUserId());
        this.getRequest().setAttribute("groupName", groupName);
        return type;
    }

    /**
     * 功能：更新虚拟人信息
     */
    public void updateUserForVirtual(){
    	try{
    		UserInfo userOld = userService.findOne(user.getUserId());
    		userOld.setJob(user.getJob()==null?"":user.getJob());
    		userOld.setPhone2(user.getPhone2()==null?"":user.getPhone2());
    		if(user.getOrderIndex()!=null){
    			userOld.setOrderIndex(user.getOrderIndex());
    		}
    		userOld.setLastUpdateTime(new Date());
    		userService.addOrUpdateUserWithOrder(userOld);
    		ajax("success");
    	}catch(Exception e){
    		ajax("人员修改失败！");
    	}
    }
    
    /**
     * 功能：如果用户的部门变动，则同时将两个部门的更新时间换成当前时间
     */
    private void updateGroupIFUserChangeGroup(UserInfo user,int oldGroupId) throws Exception{
    	if(user.getGroupId()!=oldGroupId && !user.getGroupId().equals(oldGroupId)){
    		groupService.updateGroupTime(user.getGroupId());
    		groupService.updateGroupTime(oldGroupId);
    	}
    }
    public UserInfo getUser()
    {
        return user;
    }

    public void setUser(UserInfo user)
    {
        this.user = user;
    }

    public int getGroupId()
    {
        return groupId;
    }

    public void setGroupId(int groupId)
    {
        this.groupId = groupId;
    }

    public String getRoleIds()
    {
        return roleIds;
    }

    public void setRoleIds(String roleIds)
    {
        this.roleIds = roleIds;
    }

    public String getAssistIds()
    {
        return assistIds;
    }

    public void setAssistIds(String assistIds)
    {
        this.assistIds = assistIds;
    }

    public Integer getManagementRange()
    {
        return managementRange;
    }

    public void setManagementRange(Integer managementRange)
    {
        this.managementRange = managementRange;
    }

    public String getAppointGroupIds()
    {
        return appointGroupIds;
    }

    public void setAppointGroupIds(String appointGroupIds)
    {
        this.appointGroupIds = appointGroupIds;
    }

    public String getAppointGroupNames()
    {
        return appointGroupNames;
    }

    public void setAppointGroupNames(String appointGroupNames)
    {
        this.appointGroupNames = appointGroupNames;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getRoleNames()
    {
        return roleNames;
    }

    public void setRoleNames(String roleNames)
    {
        this.roleNames = roleNames;
    }

    public Integer getOldUserId()
    {
        return oldUserId;
    }

    public void setOldUserId(Integer oldUserId)
    {
        this.oldUserId = oldUserId;
    }

	/**
	 * @return the birthDay
	 */
	public String getBirthDay() {
		return birthDay;
	}

	/**
	 * @param birthDay the birthDay to set
	 */
	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}

	public String getOtherGroupJson() {
		return otherGroupJson;
	}

	public void setOtherGroupJson(String otherGroupJson) {
		this.otherGroupJson = otherGroupJson;
	}
       
    
}
