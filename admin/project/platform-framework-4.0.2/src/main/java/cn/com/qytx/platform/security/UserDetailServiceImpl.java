package cn.com.qytx.platform.security;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.com.qytx.platform.log.service.ILog;
import cn.com.qytx.platform.org.domain.CompanyInfo;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.ModuleInfo;
import cn.com.qytx.platform.org.domain.RoleInfo;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.ICompany;
import cn.com.qytx.platform.org.service.IGroup;
import cn.com.qytx.platform.org.service.IModule;
import cn.com.qytx.platform.org.service.IRole;
import cn.com.qytx.platform.org.service.IUser;
import cn.com.qytx.platform.utils.datetime.DateTimeUtil;

/**
 * Spring Security框架登陆验证类
 * <br/>登陆失败时需要在session里面添加loginFailure属性表示失败原因,然后就可以在登陆页面通过request获取loginFailure
 * <br/>User: 黄普友
 * <br/>Date: 13-2-2
 * <br/>Time: 下午2:15
 */
public class UserDetailServiceImpl  implements UserDetailsService {
    private final static Logger LOGGER = Logger.getLogger(UserDetailServiceImpl.class);
    //用户信息
    @Resource(name = "userService")
    private IUser userService;
    //模块信息
    @Resource(name = "moduleService")
    private IModule moduleService;
    //角色信息
    @Resource(name = "roleService")
    private IRole roleService;
    @Resource(name = "companyService")
    private ICompany companyService;
    @Resource(name = "securityMetadataSource")
    private SecurityMetadataSource dataSource;
    @Resource
    private ILog logImpl;
    @Resource
    private IGroup groupService;
    
    //用户所属部门
    public final static String USER_GROUP = "usergroup";
    //用户部门的子部门集合
    public final static String USER_SUBGROUPS = "usersubgroups";
    //用户的角色集合
    public final static String USER_ROLES = "userroles";
    
    public UserDetails loadUserByUsername(String loginName) throws UsernameNotFoundException, DataAccessException {
        //获取请求        
        ServletRequestAttributes attr = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request =attr.getRequest();
        request.getSession().setAttribute("loginFailure",null); //清空session保存的登录失败原因
        
        if (loginName == null) {
            request.getSession().setAttribute("loginFailure","failure"); //登陆失败
        }else{
            try
            {

                UserInfo user=userService.findByLoginName(loginName);
                List<UserInfo> list = userService.findUsersByLoginNames(loginName);
                
                if(user==null){
                	request.getSession().setAttribute("loginFailure","failure");//登录名不存在
                }else if (list!=null&&list.size()>1) {
                	request.getSession().setAttribute("loginFailure","loginNameRepeat");//账号重复
				}else if(user.getUserState() == null || user.getUserState()>0){//禁止登陸
                	request.getSession().setAttribute("loginFailure","loginForbid");
                }else{
                    CompanyInfo companyInfo=companyService.findOne(user.getCompanyId());
                    Timestamp insertTime = companyInfo.getInsertTime(); //公司开通日期
                    Date expirationTime = companyInfo.getExpirationTime();//公司到期日期
                    String currStr = DateTimeUtil.dateToString(new Date(),"yyyy-MM-dd"); //格式化当前时间
                    Timestamp currTs = Timestamp.valueOf(currStr+" 00:00:00.000"); //转换为时间戳类型
                    Integer companyState=companyInfo.getCompanyState();
                    if (companyState!=null&&companyState==1) {
                    	request.getSession().setAttribute("loginFailure","companyCancel");
                    	return null;
					}else if(companyInfo.getExpirationTime()!=null&&currTs.getTime()>expirationTime.getTime()){
						request.getSession().setAttribute("loginFailure","companyExpiration"); //公司已到期
                    	return null;
					}else if(insertTime != null && insertTime.getTime() >  currTs.getTime()){
						request.getSession().setAttribute("loginFailure","companyNoOpen");//公司未开通
                    	return null;
					}
                    setUserDetailInfo(user, request.getSession());
                    request.getSession().setAttribute("companyInfo",companyInfo);//把单位列表存放到session
                    dataSource.loadResourceDefine();//加载模块列表
                    String roleIdArr="";
                    List<RoleInfo> roleList =roleService.getRoleByUser(user.getUserId()); //根据人员Id获取角色列表
                    if(roleList!=null)
                    {
                        roleIdArr= getRoleIds(roleList);
                    }
                    
                    //add by jiayq,如果是超级管理员用户，则有所有的菜单权限
                    List<ModuleInfo> moduleList = null;
                    if(user.getIsDefault()!=null&&user.getIsDefault() == 0 ){
                    	moduleList = moduleService.findAll();
                    }else{
                    	moduleList =moduleService.getModuleByRole(roleIdArr);//获取模块列表
                    }
                    
                    request.getSession().setAttribute("moduleList",moduleList);//把模块列表存放到session
                    //给该用户添加授权模块
                    List<GrantedAuthority> authsList = new ArrayList<GrantedAuthority>();
                    for (ModuleInfo moduleInfo : moduleList) {
                        authsList.add(new GrantedAuthorityImpl(moduleInfo.getModuleCode())); //保存模块代码
                    }
                    
                    //获取用户IP
                    String ip = getIpAddr(request);
                    user.setIp(ip);
                    
                    request.getSession().setAttribute("adminUser",user);//登录信息保存在Session
                    request.getSession().setAttribute("adminUserXC",user);//登录信息保存在Session
                    userService.updateLastLoginTime(user.getUserId());
                    user.setLastLoginTime(new Timestamp(System.currentTimeMillis()));
                    org.springframework.security.core.userdetails.User userdetail = new org.springframework.security.core.userdetails.User(
                            user.getLoginName(), user.getLoginPass(), true, true, true, true, authsList);
                    
                    return userdetail;
                }
            }
            catch (Exception ex)
            {
            	ex.printStackTrace();
                LOGGER.error(ex);
            }
        }
        return new org.springframework.security.core.userdetails.User(
        		loginName, "", true, true, true, true, new ArrayList<GrantedAuthority>());
    }
   

    /**
     * 获取角色id列表
     * @param roleList
     * @return
     */
    private String getRoleIds(final List<RoleInfo> roleList)
    {
        StringBuffer roleIdArr= new StringBuffer();
        String result = "";
        for(RoleInfo role:roleList)
        {
            roleIdArr.append(role.getRoleId()+",");
        }
        if(roleIdArr.toString().endsWith(","))
        {
            result=roleIdArr.substring(0,roleIdArr.length()-1);
        }
        return result;
    }
    
    /*
     * 获取用户IP
     */
    public  String getIpAddr(HttpServletRequest request) { 
        String ip = request.getHeader("x-forwarded-for"); 
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("Proxy-Client-IP"); 
        } 
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("WL-Proxy-Client-IP"); 
        } 
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getRemoteAddr(); 
        } 
        return ip; 
    } 
    
    private void setUserDetailInfo(UserInfo userInfo,HttpSession session){
    	Integer groupId = userInfo.getGroupId();
    	if(groupId!=null){
	    	GroupInfo gInfo = groupService.findOne(groupId);
	    	List<GroupInfo> subgrouplist = groupService.getSubGroupList(groupId);
	    	List<RoleInfo> roleList =roleService.getRoleByUser(userInfo.getUserId());
	    	session.setAttribute(USER_GROUP, gInfo);
	    	session.setAttribute(USER_SUBGROUPS, subgrouplist);
	    	session.setAttribute(USER_ROLES, roleList);
    	}
    }
}
