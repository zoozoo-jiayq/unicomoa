package cn.com.qytx.cbb.org.action;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.org.domain.RoleInfo;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IGroupUser;
import cn.com.qytx.platform.org.service.IRole;
import cn.com.qytx.platform.org.service.IRoleUser;
import cn.com.qytx.platform.org.service.IUser;
import cn.com.qytx.platform.utils.UpdateFieldUtil;
import cn.com.qytx.platform.utils.encrypt.MD5;
import cn.com.qytx.platform.utils.pinyin.FormatPinyinTo9Data;
import cn.com.qytx.platform.utils.pinyin.Pinyin4jUtil;
import cn.com.qytx.platform.utils.validate.Validate;

import com.google.gson.Gson;

/**
 * <br/>
 * 功能:添加用户 <br/>
 * 版本: 1.0 <br/>
 * 开发人员: 任 <br/>
 * 创建日期: 2013-4-9 <br/>
 * 修改日期: 2013-4-9 <br/>
 * 修改列表:
 */
public class UserAddAction extends BaseActionSupport{
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
    
    @Resource
    IRole roleService;


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
    // 类型
    private String type;

    private String birthDay;
    
    private String otherGroupJson;
    /**
     * 添加人员
     * @return
     */
    @SuppressWarnings("unchecked")
    public String addUser(){
      try{
    	  // 登录密码默认123456
	        MD5 md5 = new MD5();
	        String pass = md5.encrypt("123456");
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
	                ajax("用户名不能为空！");
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
	            user.setUserState(0);
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
	        if(userService.phoneIsRepeat(user.getPhone())){
	            ajax("手机号码重复!");
	            return null;
	        }
	
	        if(StringUtils.isNotBlank(birthDay)){
	    		try {
	    			user.setBirthDay(DateUtils.parseDate(birthDay, "yyyy-MM-dd"));
				} catch (ParseException e) {
					e.printStackTrace();
				}
	    	}
	        user.setIsDelete(0);
	        user.setIsDefault(1);
	        user.setLoginPass(pass);
	        user.setCompanyId(getLoginUser().getCompanyId());
	        user.setPartitionCompanyId(getLoginUser().getCompanyId()%10);
	        user.setSkinLogo(1);
	        user.setCreateTime(new Date());
	        user.setLastUpdateTime(new Date());
	        String py = Pinyin4jUtil.getPinYinHeadChar(user.getUserName());
	        user.setPy(py);
	        //获得全拼拼音 带空格
	        String fullPy = Pinyin4jUtil.getPinYinWithBlank(user.getUserName());
	        user.setFullPy(fullPy);
	        //获得用户姓名全拼对应九宫格按键
	        String formattedNumber = FormatPinyinTo9Data.getFormattedNumber(fullPy);
	        user.setFormattedNumber(formattedNumber);
	        user.setGroupId(groupId);	        	        
	        
	        // TODO 需要判断人员是否重复
	        if (StringUtils.isEmpty(type))
	        {
	            UserInfo tmp = userService.getUserByUserName(user.getLoginName());
	            if (tmp != null)
	            {
	                ajax("用户名已经存在！");
	                return null;
	            }
	        }
	
	        if (groupId == 0)
	        {
	            ajax("请选择部门！");
	            return null;
	        }
	        
	        // 判断姓名和电话不能同时重复
	        UserInfo existUser = userService.getUserByNamePhone(getLoginUser().getCompanyId(),user.getUserName(), user.getPhone());
	        if (null != existUser && existUser.getUserName().equals(user.getUserName())
	                && existUser.getPhone().equals(user.getPhone()))
	        {
	            ajax("该成员已存在！");
	            return null;
	        }
	        //设置用户排序,如果用户排序号为空，则取最大值+1
	        if(user.getOrderIndex()==null){
	        	int maxOrder = userService.getMaxOrderIndex(user.getCompanyId(), groupId);
	        	user.setOrderIndex(maxOrder+1);
	        }
	        
	        //add by jiayq,添加的用户默认就是登录用户，用户名是手机号码，密码是123456
	        user.setUserState(UserInfo.USERSTATE_LOGIN);
	        user.setLoginName(user.getPhone());
	        user.setRegisterTime(new Date());
	        userService.addOrUpdateUserWithOrder(user);
	        
	        //add by jiayq,添加默认角色，common_role
	    	/*boolean flag = roleService.isExistRoleCode(RoleInfo.COMMON_ROLE);
	        if(flag){
	        	RoleInfo role = roleService.findByRoleCode(RoleInfo.COMMON_ROLE);
	        	roleUserService.saveRoleUsersByRoleIds(new Integer[]{role.getRoleId()}, user.getUserId(),
	        		getLoginUser().getCompanyId(), 1, true);
	        }*/

	        /**==================添加从属部门 start=======================**/
	        Gson json = new Gson();
	        if(StringUtils.isNotEmpty(otherGroupJson)){
	        	List<Map<String,Object>> otherGroupUsers = (List<Map<String,Object>>) json.fromJson(otherGroupJson,List.class);
	        	if(otherGroupUsers != null || otherGroupUsers.size() > 0){
	        		for(Map<String,Object> map : otherGroupUsers){
	        			if(map.get("groupId") != null){
	        				UserInfo temp = new UserInfo();
	        				int iGroupId = Integer.valueOf(map.get("groupId").toString());
	        				String iJob = map.get("job")==null?"":map.get("job").toString();
	        				String iTelphone = map.get("telphone")==null?"":map.get("telphone").toString();
	        				int iOrderIndex = map.get("orderIndex")==null?0:(Integer.valueOf(map.get("orderIndex").toString()));
	        				
	        				UpdateFieldUtil.update(user, temp,"userId","groupId","officeTel","job","orderIndex", "loginName","USERSTATE_UNLOGIN","USERSTATE_LOGIN","USERSTATE_FORBIDDEN_LOGIN","lastUpdateTime");
	        				temp.setUserState(1);//不允许登陆
	        				temp.setGroupId(iGroupId);
	        				temp.setIsVirtual(1);
	        				temp.setOfficeTel(iTelphone);
	        				temp.setJob(iJob);
	        				if(iOrderIndex!=0){
	        					temp.setOrderIndex(iOrderIndex);
	        				}else{
	        					int maxOrder = userService.getMaxOrderIndex(user.getCompanyId(), iGroupId);
	        					temp.setOrderIndex(maxOrder+1);
	        				}
	        				temp.setLinkId(user.getUserId());
	        				userService.addOrUpdateUserWithOrder(temp);
	        				
	        			}
	        		}
	        	}
	        }
	        /**==================添加从属部门 end=======================**/
	        
	        ajax("");
	        return null;
      }catch(Exception e){
    	  ajax("服务器异常!");
    	  return null;
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
