package cn.com.qytx.cbb.org.action;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import cn.com.qytx.cbb.org.util.ExportExcel;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.PageImpl;
import cn.com.qytx.platform.base.query.Sort;
import cn.com.qytx.platform.base.query.Sort.Direction;
import cn.com.qytx.platform.base.query.Sort.Order;
import cn.com.qytx.platform.org.dao.UserVo;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.RoleInfo;
import cn.com.qytx.platform.org.domain.UserGroup;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IGroup;
import cn.com.qytx.platform.org.service.IGroupUser;
import cn.com.qytx.platform.org.service.IRole;
import cn.com.qytx.platform.org.service.IUser;
import cn.com.qytx.platform.org.service.IUserGroup;

/**
 * 
 * <br/>功能:用户列表
 * <br/>版本: 1.0
 * <br/>开发人员: 任
 * <br/>创建日期: 2013-4-9
 * <br/>修改日期: 2013-4-9
 * <br/>修改列表:
 */
public class UserListAction extends BaseActionSupport {

    /**用户信息*/
    @Resource(name = "userService")
    IUser userService;
    
    /**部门人员信息*/
    @Resource(name = "groupUserService")
    IGroupUser groupUserService;
    
    @Resource(name = "groupService")
    private IGroup groupService;
    private String    userIds;
    
    @Resource(name="userGroupService")
    private IUserGroup userGroupService;
    /**
     * 角色service
     */
    @Resource(name = "roleService")
    IRole roleService;
    
    /**部门id*/
    private UserVo userVo;
    
    private Integer loginOrder;

    private int  pd;
    
    //选中的要导出的内容ids
    private String selectedIds;
 
	public int getPd() {
		return pd;
	}
	public void setPd(int pd) {
		this.pd = pd;
	}
	/**
     * 获取人员列表
     * @return String    返回类型
     */
	public String getUserListByGroupId(){
        //add by jiayq,
        Order order = new Order(Direction.ASC,"orderIndex");
		Sort s = new Sort(order);
		UserInfo userInfo = getLoginUser();
		userVo.setCompanyId(userInfo.getCompanyId());
		Integer groupId = userVo.getGroupId();
		String childrenGroupIds="";
		List<GroupInfo> groups = null;
		childrenGroupIds =String.valueOf(userVo.getGroupId());
		if (groupId!=null&&groupId!=0) {
			groups=groupService.getSubGroupList(groupId);
		}else{
			groups = groupService.getGroupList(userInfo.getCompanyId(), 1);
		}
		if(groups!=null&&!groups.isEmpty()){
			int size = groups.size();
			for (int i = 0; i < size; i++) {
				childrenGroupIds=childrenGroupIds+","+groups.get(i).getGroupId();
			}
		}
		userVo.setChildrenGroupIds(childrenGroupIds);
        Page<Map<String, Object>> userMaplist = userService.findByPageAll(userVo, getPageable(s),null);
        Page<UserInfo> page = generateUserlist(userMaplist);
        List<UserInfo> userList = page.getContent();  //获取结果
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        List<GroupInfo> groupList = new ArrayList<GroupInfo>();
        List<String> ugPowerList = new ArrayList<String>();
        if ("TXZL".equals(userVo.getProjectName())){
        	  groupList=groupService.getGroupList(getLoginUser().getCompanyId(), GroupInfo.DEPT);
        	  UserGroup ug = userGroupService.findByUserCompany(getLoginUser().getUserId(), getLoginUser().getCompanyId());
        	  if(ug!=null){
	        	  if (StringUtils.isNotBlank(ug.getGroupPower())) {
	        		  ugPowerList=Arrays.asList(ug.getGroupPower().split(","));
	        	  }
        	  }
        }
        if (userList != null) {
            for(int i=0; i<userList.size(); i++) {
            	UserInfo user = userList.get(i);
            	Map<String, Object> map = new HashMap<String, Object>();
            	map.put("id", user.getUserId());
                map.put("no", (page.getNumber())*(page.getSize())+i+1);
                //姓名
                String userNameOut=user.getUserName();
                if(StringUtils.isNotEmpty(userNameOut)) {
                    map.put("userName", userNameOut);
                } else {
                    map.put("userName", "&nbsp;");
                }
                //手机号
                String phoneOut=user.getPhone();
                if(StringUtils.isNotEmpty(phoneOut)) {
                    map.put("phone", phoneOut);
                } else {
                    map.put("phone", "&nbsp;");
                }
                if ("TXZL".equals(userVo.getProjectName())) {
                	  // 部门
                    String groupName = "";
                    for (int j = 0; j < groupList.size(); j++) {
  						if (user.getGroupId()!=null&&user.getGroupId().equals(groupList.get(j).getGroupId())) {
  							groupName=groupList.get(j).getGroupName();
  							break;
  						}
  					}
                    map.put("groupName", groupName);
                    //称呼
                    map.put("title", null==user.getTitle()?"&nbsp":user.getTitle());
                    //v网
                    map.put("vNum", null==user.getVNum()?"&nbsp":user.getVNum());
                    boolean hasPower=false;
                    if(user.getGroupId()!=null){
                  	  hasPower=ugPowerList.contains(user.getGroupId().toString());
                    }
                    map.put("hasPower", hasPower);
                    // 职务
                    String job = user.getJob();
                    map.put("job", job == null ? "&nbsp;":job);
  				}
                else {
                    //用户名
                    String loginNameOut=user.getLoginName();
                    if(StringUtils.isNotEmpty(loginNameOut)) {
                        map.put("loginName", loginNameOut);
                    } else {
                        map.put("loginName", "&nbsp;");
                    }
                    //性别
                    Integer sexOut=user.getSex();
                    if(sexOut!=null){
                        map.put("sex", sexOut);
                    }else{
                        map.put("sex", "&nbsp;");
                    }
                    
                    String groupName = "";
                    if(user.getGroupId()!=null){
                    	GroupInfo group = groupService.findOne(user.getGroupId());
                    	if(group!=null){
                    		groupName = group.getGroupName();
                    	}
                    }
                    map.put("groupName", groupName==""?"&nbsp;":groupName);
                    
                    map.put("job", user.getJob()== null ? "&nbsp;":user.getJob());
                    //最后登录日期
                    Date lastLoginTime=user.getLastLoginTime();
                    if(lastLoginTime!=null) {
                        map.put("lastLoginTime",  new SimpleDateFormat("yyyy-MM-dd HH:mm").format(lastLoginTime));
                    } else {
                        map.put("lastLoginTime", "&nbsp;");
                    }
                   
                    // 是否公开
                    Integer phonePublic  = user.getPhonePublic();
                    if(null != phonePublic) {
                        map.put("phonePublic", phonePublic);
                    } else {
                        map.put("phonePublic", "&nbsp;");
                    }
                    
                    map.put("officeTel", user.getOfficeTel()==null?"":user.getOfficeTel());
                    map.put("workNo", user.getWorkNo()==null?"":user.getWorkNo());
                    
                    //注册日期
                    Date registerTime=user.getRegisterTime();
                    if(registerTime!=null) {
                        map.put("registerTime",  new SimpleDateFormat("yyyy-MM-dd").format(user.getRegisterTime()));
                    } else {
                        map.put("registerTime", "&nbsp;");
                    }
                    
                    Date createTime=user.getCreateTime();
                    if(createTime!=null) {
                        map.put("createTime",  new SimpleDateFormat("yyyy-MM-dd").format(createTime));
                    } else {
                        map.put("createTime", "&nbsp;");
                    }  
                    //用户是否禁止登录
                    Integer userStateOut=user.getUserState();
                    if(userStateOut==null){
                    	userStateOut=UserInfo.USERSTATE_UNLOGIN;
                    }
                    map.put("userState", userStateOut);
                    //是否系统用户0,系统用户,1,普通用户
                    Integer isDefault=user.getIsDefault();
                    if(isDefault==null){
                    	isDefault=1;
                    }
                    map.put("isDefault", isDefault);
                    map.put("isVirtual", user.getIsVirtual()==null?0:user.getIsVirtual());
                    // 角色
                    List<RoleInfo> roleList = roleService.getRoleByUser(user.getUserId());
                    StringBuffer roleString = new StringBuffer();
                    if (null != roleList && !roleList.isEmpty()){
                        for (int r = 0 ; r < roleList.size(); r ++){
                            roleString.append(roleList.get(r).getRoleName());
                            if (r != roleList.size() - 1){
                                roleString.append(',');
                            }
                        }
                    }
                    map.put("role", roleString.toString());
				}
        
                mapList.add(map);
            }
        }
        ajaxPage(page, mapList);
        return null;
    }
    
    private List<String> getExportHeadList(){
        List<String> headList = new ArrayList<String>();
        headList.add("姓名");
        headList.add("部门");
        headList.add("性别");
        headList.add("手机");
        return headList;
    }
    
    private List<String> getExportKeyList(){
        List<String> headList = new ArrayList<String>();
        headList.add("userName");
        headList.add("groupName");
        headList.add("sexStr");
        headList.add("phone");
        return headList;
    }
    
    private List<Map<String, Object>> analyzeResult(List<UserInfo> list,Map<String,String> groupIdToName,Map<String,String> groupIdToPath)
    {
        // 把订单信息填充到map里面
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        
        // 获取访问来源
        String type = null != userVo ? userVo.getType() : null;
        if (list != null)
        {
            for (UserInfo userInfo : list)
            {
                Map<String, Object> map = new HashMap<String, Object>();

                // 用户
                map.put("loginName", userInfo.getLoginName());

                // 姓名
                String userName = userInfo.getUserName();
                map.put("userName", userName);
                map.put("groupName", getGroupPathName(userInfo.getGroupId().toString(), groupIdToName, groupIdToPath));
                
                
                // 性别
                Integer sex = userInfo.getSex();
                map.put("sex", null == sex ? "" : sex);
                if (null != sex && 1 == sex)
                {
                    map.put("sexStr", "男");
                }
                else if (null != sex && 0 == sex)
                {
                    map.put("sexStr", "女");
                }
                //电话
                String phone = userInfo.getPhone();
                // 需要判断用户是否公开
                if (null != type && "view".equals(type)){
                    Integer phonePublic = userInfo.getPhonePublic();
                    if (null != phonePublic && 0 == phonePublic.intValue()){
                        map.put("phone", "");
                    }else{
                        map.put("phone", phone);
                    }
                }else{
                    map.put("phone", phone);
                }
                
                
                mapList.add(map);
            }
        }
        return mapList;
    }
    
    private String getGroupPathName(String groupId,Map<String,String> groupIdToName,Map<String,String> groupIdToPath){
    	String result="";
    	String path = groupIdToPath.get(groupId)==null?"":groupIdToPath.get(groupId).toString();
        if(StringUtils.isNotEmpty(path)){
        	String[] pathArr = path.split(",");
        	for(String pathId:pathArr){
        		if(groupIdToName.get(pathId)!=null){
        			String pathName = groupIdToName.get(pathId).toString();
        			result+=pathName+"\\";
        		}
        	}
        }
        if(result.endsWith("\\")){
        	result = result.substring(0,result.length()-1);
        }
        return result;
    }
    
    /**
     * 导出人员
     */
    public void exportUserInfo()
    {
        HttpServletResponse response = this.getResponse();
        response.setContentType("application/vnd.ms-excel");

        OutputStream outp = null;
        try
        {
        	if(userVo!=null){
        		 UserInfo adminUser=(UserInfo)this.getSession().getAttribute("adminUser");
        		 userVo.setSignType(null);
        		 userVo.setCompanyId(getLoginUser().getCompanyId());
        		 Integer groupId = userVo.getGroupId();
	        	String childrenGroupIds="";
	    		List<GroupInfo> groups = null;
	    		childrenGroupIds =String.valueOf(userVo.getGroupId());
	    		if (groupId!=null&&groupId!=0) {
	    			groups=groupService.getSubGroupList(groupId);
	    		}else{
	    			groups = groupService.getGroupList(adminUser.getCompanyId(), 1);
	    		}
	    		if(groups!=null&&!groups.isEmpty()){
	    			int size = groups.size();
	    			for (int i = 0; i < size; i++) {
	    				childrenGroupIds=childrenGroupIds+","+groups.get(i).getGroupId();
	    			}
	    		}
	    		userVo.setChildrenGroupIds(childrenGroupIds);
        	}
        	setIDisplayLength(Integer.MAX_VALUE);
        	Order order = new Order(Direction.ASC,"orderIndex");
    		Sort s = new Sort(order);
//            Page<UserInfo> page = userService.findAllUsersByPage(userVo, getPageable(s),groupIds);
            Page<Map<String, Object>> userMaplist = userService.findByPageAll(userVo, getPageable(s),null);
            Page<UserInfo> page = generateUserlist(userMaplist);
            List<UserInfo> list = page.getContent();
            
            List<UserInfo> listSelected = new ArrayList<UserInfo>();
            if(selectedIds!=null&&!"".equals(selectedIds)){
            	if(list!=null&&list.size()>0){
            		Iterator ite = list.iterator();
            		while(ite.hasNext()){
	        			UserInfo lis = (UserInfo) ite.next();
	        			if(selectedIds.indexOf(lis.getUserId()+",")==0||selectedIds.indexOf(","+lis.getUserId()+",")>0){
	        				listSelected.add(lis);
	        				continue;
	        			}
            		}
            	}
            }
            if(listSelected.size()>0){
            	list=listSelected;
            }
            
            String fileName = URLEncoder.encode("人员信息.xls", "UTF-8");
            // 把联系人信息填充到map里面
            response.addHeader("Content-Disposition",
                    "attachment;filename=" + fileName);// 解决中文
                                                                                            // 文件名问题
            outp = response.getOutputStream();
            
            List<GroupInfo> allGroupList = groupService.getGroupList(this.getLoginUser().getCompanyId(), 1);
            Map<String,String> groupIdToName=new HashMap<String, String>();
            Map<String,String> groupIdToPath=new HashMap<String, String>();
            if(allGroupList!=null&&allGroupList.size()>0){
        		for(GroupInfo group:allGroupList){
        			groupIdToName.put(group.getGroupId().toString(), group.getGroupName());
        			groupIdToPath.put(group.getGroupId().toString(), group.getPath());
        		}
        	}
            
            List<Map<String, Object>> mapList = analyzeResult(list,groupIdToName,groupIdToPath);

            ExportExcel exportExcel = new ExportExcel(outp, getExportHeadList(), mapList, getExportKeyList());
            exportExcel.exportWithSheetName("通讯录");
        }catch(Exception e){
        	e.printStackTrace();
        }
        
    }


    /**
     * 导出人员 按照复选框
     */
    public void exportUserInfoCheck()
    {
        HttpServletResponse response = this.getResponse();
        response.setContentType("application/vnd.ms-excel");

        OutputStream outp = null;
        try
        {
        	String groupIds = getGroupIds(getLoginUser().getUserId());
            List<UserInfo> list = userService.findUsersByIds(userIds);
            // 把联系人信息填充到map里面
            response.addHeader("Content-Disposition",
                    "attachment;filename=" + new String("人员信息.xls".getBytes(), "iso8859-1"));// 解决中文
                                                                                            // 文件名问题
            outp = response.getOutputStream();

            List<GroupInfo> allGroupList = groupService.getGroupList(this.getLoginUser().getCompanyId(), 1);
            Map<String,String> groupIdToName=new HashMap<String, String>();
            Map<String,String> groupIdToPath=new HashMap<String, String>();
            if(allGroupList!=null&&allGroupList.size()>0){
        		for(GroupInfo group:allGroupList){
        			groupIdToName.put(group.getGroupId().toString(), group.getGroupName());
        			groupIdToPath.put(group.getGroupId().toString(), group.getPath());
        		}
        	}
            List<Map<String, Object>> mapList = analyzeResult(list,groupIdToName,groupIdToPath);

            ExportExcel exportExcel = new ExportExcel(outp, getExportHeadList(), mapList, getExportKeyList());
            exportExcel.exportWithSheetName("通讯录");
        }
        catch (Exception e)
        {
        }
    }
    
    
    private Page<UserInfo> generateUserlist(Page<Map<String, Object>> mapPage){
		List<UserInfo> content = new ArrayList<UserInfo>();
		List<Map<String, Object>> userList = mapPage.getContent();
		for (int i = 0; i < userList.size(); i++) {
			UserInfo user= new UserInfo();
			user.setCompanyId(Integer.parseInt(userList.get(i).get("companyId").toString()));
			user.setUserId(Integer.parseInt(userList.get(i).get("userId").toString()));
			user.setUserName(userList.get(i).get("userName")==null?"":userList.get(i).get("userName").toString());
			user.setPhone(userList.get(i).get("phone")==null?"":userList.get(i).get("phone").toString());
			user.setGroupName(userList.get(i).get("groupName")==null?"":userList.get(i).get("groupName").toString());
			user.setTitle(userList.get(i).get("title")==null?"":userList.get(i).get("title").toString());
			user.setvNum(userList.get(i).get("vNum")==null?"":userList.get(i).get("vNum").toString());
			user.setGroupId((Integer) (userList.get(i).get("groupId")==null?"":userList.get(i).get("groupId")));
			user.setJob(userList.get(i).get("job")==null?"":userList.get(i).get("job").toString());
			user.setLoginName(userList.get(i).get("loginName")==null?"":userList.get(i).get("loginName").toString());
			user.setSex((Integer) (userList.get(i).get("sex")==null?0:userList.get(i).get("sex")));
			user.setLastLoginTime((Date) userList.get(i).get("lastLoginTime"));
			user.setPhonePublic((Integer) userList.get(i).get("phonePublic"));
			user.setRegisterTime((Date) userList.get(i).get("registerTime"));
			user.setCreateTime((Date) userList.get(i).get("createTime"));
			user.setUserState((Integer) (userList.get(i).get("userState")==null?UserInfo.USERSTATE_UNLOGIN:userList.get(i).get("userState")));
			user.setIsDefault((Integer) (userList.get(i).get("isDefault")==null?1:userList.get(i).get("isDefault")));
			user.setMobileShowState((Integer) (userList.get(i).get("mobileShowState")==null?0:userList.get(i).get("mobileShowState")));
			user.setOfficeTel(userList.get(i).get("officeTel")==null?"":userList.get(i).get("officeTel").toString());
			user.setWorkNo(userList.get(i).get("workNo")==null?"":userList.get(i).get("workNo").toString());
			user.setIsVirtual(Integer.parseInt(userList.get(i).get("isVirtual")==null?"0":userList.get(i).get("isVirtual").toString()));
			content.add(user);
		}
		
		return new PageImpl<UserInfo>(content, getPageable(), mapPage.getTotalElements());
	}

    public UserVo getUserVo() {
		return userVo;
	}


	public void setUserVo(UserVo userVo) {
		this.userVo = userVo;
	}
	
	public Integer getLoginOrder() {
		return loginOrder;
	}


	public void setLoginOrder(Integer loginOrder) {
		this.loginOrder = loginOrder;
	}
	
	private String getGroupIds(int userId){
        String groupIds = "";
        GroupInfo forkGroup = groupService.getForkGroup(getLoginUser().getCompanyId(),userId);
        if(forkGroup!=null){
        	List<GroupInfo> grouplist = groupService.getSubGroupList(forkGroup.getGroupId());
        	grouplist.add(forkGroup);
        	for(Iterator<GroupInfo> it = grouplist.iterator(); it.hasNext();){
        		groupIds+=it.next().getGroupId();
        		if(it.hasNext()){
        			groupIds+=",";
        		}
        	}
        }
        return groupIds;
	}
	/**
	 * @return the userIds
	 */
	public String getUserIds() {
		return userIds;
	}
	/**
	 * @param userIds the userIds to set
	 */
	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}
	public String getSelectedIds() {
		return selectedIds;
	}
	public void setSelectedIds(String selectedIds) {
		this.selectedIds = selectedIds;
	}
	
}
