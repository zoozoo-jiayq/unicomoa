package cn.com.qytx.platform.base.application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import cn.com.qytx.platform.base.datafilter.FilterContext;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.RoleInfo;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IGroup;
import cn.com.qytx.platform.org.service.IRole;
import cn.com.qytx.platform.org.service.IUser;
import cn.com.qytx.platform.security.SessionVariable;
import cn.com.qytx.platform.security.SystemContextHolder;
import cn.com.qytx.platform.security.UserDetailServiceImpl;
import cn.com.qytx.platform.utils.spring.SpringUtil;

/**
 *用户每次请求的时候都把当前用户保存在TransprotUser的threadlocal中,同时初始化数据权限系统的上下文环境(SystemContextHolder)
 */
public class TransportUserFilter implements Filter {

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)arg0;
		UserInfo userInfo =(UserInfo) request.getSession().getAttribute("adminUser");
		//保存在ThreadLocal中
		if(userInfo!=null){
			TransportUser.set(userInfo);
			GroupInfo groupInfo = (GroupInfo) request.getSession().getAttribute(UserDetailServiceImpl.USER_GROUP);
			List<RoleInfo> roles = (List<RoleInfo>) request.getSession().getAttribute(UserDetailServiceImpl.USER_ROLES);
			setDataPowerForPC(userInfo,groupInfo,roles);
		}else{
			//兼容手机服务
			String userId = request.getParameter("userId");
			if(userId!=null && !userId.equals("") && !userId.equals("null")){
				IUser userService = (IUser) SpringUtil.getBean("userService");
				UserInfo ui = userService.findOne(Integer.parseInt(userId));
				TransportUser.set(ui);
				setDataPowerForMobile(ui);
			}else{
				TransportUser.set(null);
				SystemContextHolder.setSessionContext(null);
			}
		}
		
		arg2.doFilter(arg0, arg1);
		SystemContextHolder.setSessionContext(null);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

	/**
     * 功能：设置数据权限,将从session中获取的变量放进ThreadLocal中
     * 作者：jiayongqiang
     * 参数：
     * 输出：
     */
    private void setDataPowerForPC(UserInfo user,GroupInfo groupInfo,List<RoleInfo> roles){
    	SystemContextHolder.setSessionContext(getSessionVariable(user,groupInfo,roles));
    }
    
    /**
     * 功能：设置数据权限
     * 作者：jiayongqiang
     * 参数：
     * 输出：
     */
    private void setDataPowerForMobile(UserInfo user){
    	if(user!=null){
	    	Integer groupId = user.getGroupId();
	    	if(groupId!=null){
	    		IGroup groupService = SpringContextHolder.getBean(IGroup.class);
	    		IRole roleService = SpringContextHolder.getBean(IRole.class);
		    	GroupInfo gInfo = groupService.findOne(groupId);
		    	//List<GroupInfo> subgrouplist = groupService.getSubGroupList(groupId);
		    	List<RoleInfo> roleList =roleService.getRoleByUser(user.getUserId());
		    	SystemContextHolder.setSessionContext(getSessionVariable(user,gInfo,roleList));
	    	}
    	}
    }
    
    /**
     * @param user：当前登录用户
     * @param groupInfo：当前登录用户所在的部门
     * @param gs：当前登录用户所属部门的所有子部门，不包括本部门
     * @param roles：当前用户的所有角色
     * @return
     */
    public static SessionVariable getSessionVariable(UserInfo user,GroupInfo groupInfo,List<RoleInfo> roles)
    {
    	//初始化上下文
		SessionVariable sv = new SessionVariable(user);
		//设置用户所属部门权限
		GroupInfo dept = groupInfo;
		if(null!=dept){
			
			//设置用户的上一级部门
			List<Integer> parentDepts = new ArrayList<Integer>();
			if(dept.getParentId()!=null){
				parentDepts.add(dept.getParentId());
			}
			sv.getFilterParameters().put(FilterContext.USER_PARENT_DEPT_IDS,parentDepts);
			List<Integer> depts = new ArrayList<Integer>();
			depts.add(dept.getGroupId());
			
			//设置用户所属部门
			sv.getFilterParameters().put(FilterContext.USER_DEPT_IDS, depts);
			
			//设置用户默认部门
			sv.getFilterParameters().put(FilterContext.USER_DEFAULT_DEPT_ID, depts);
			
			sv.getFilterParameters().put(FilterContext.IS_FORK_GROUP, user.getIsForkGroup());
			
			//设置用户子部门集合
//			List<GroupInfo> subgrouplist = gs;
//			if(subgrouplist!=null){
//				List<Integer> childs = new ArrayList<Integer>();
//				for(int i=0; i<subgrouplist.size(); i++){
//					childs.add(subgrouplist.get(i).getGroupId());
//				}
//				sv.getFilterParameters().put(FilterContext.USER_CHILD_DEPT_IDS, childs);
//			}
			//---------------------------------------------以上是数据权限的运行上下文，以下是限制条件(即对符合这些条件的数据启用数据权限)---------------------------------------------------
			//设置关系ID
			List<String> relationIds = new ArrayList<String>();
			//王永刚修改，增加全部用户的启用
			relationIds.add("all");
			//限制条件是当前登录用户
			relationIds.add("user_"+user.getUserId());
			//限制条件是当前登录用户的分支机构ID
			if(user.getIsForkGroup()!=null){
				relationIds.add("isForkGroup_"+user.getIsForkGroup());
			}
			//限制条件是当前用户的部门
			if(dept!=null){
				relationIds.add("dept_"+dept.getGroupId());
			}
			//限制条件是当前用户的角色
			List<RoleInfo> roleList =roles;
			if(roleList!=null){
				for(int i=0; i<roleList.size(); i++){
					RoleInfo r = roleList.get(i);
					relationIds.add("role_"+r.getRoleId());
				}
			}
			sv.setRelationIds(relationIds);
		}
		
		return sv;
    }
   
}
