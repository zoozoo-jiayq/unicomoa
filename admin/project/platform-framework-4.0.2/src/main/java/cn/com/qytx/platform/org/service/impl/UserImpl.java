package cn.com.qytx.platform.org.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;
import cn.com.qytx.platform.event.PublishEventUtil;
import cn.com.qytx.platform.org.dao.CompanyDao;
import cn.com.qytx.platform.org.dao.UserDao;
import cn.com.qytx.platform.org.dao.UserVo;
import cn.com.qytx.platform.org.domain.CompanyInfo;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.GroupUser;
import cn.com.qytx.platform.org.domain.RoleInfo;
import cn.com.qytx.platform.org.domain.RoleUser;
import cn.com.qytx.platform.org.domain.UserGroup;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.EventForAddUser;
import cn.com.qytx.platform.org.service.EventForDeleteUser;
import cn.com.qytx.platform.org.service.EventForSortUser;
import cn.com.qytx.platform.org.service.EventForUpdateUser;
import cn.com.qytx.platform.org.service.IGroup;
import cn.com.qytx.platform.org.service.IGroupUser;
import cn.com.qytx.platform.org.service.IRole;
import cn.com.qytx.platform.org.service.IRoleUser;
import cn.com.qytx.platform.org.service.IUser;
import cn.com.qytx.platform.org.service.IUserGroup;
import cn.com.qytx.platform.utils.datetime.DateTimeUtil;
import cn.com.qytx.platform.utils.tree.TreeNode;



/**
 * 用户管理实现类
 * User: 黄普友
 * Date: 13-2-16
 * Time: 下午3:00
 */
@Transactional
@Service("userService")
public class UserImpl   extends BaseServiceImpl<UserInfo> implements IUser {
    private static Logger logger =Logger.getLogger(UserImpl.class.getName());
    
    @Resource(name="userDao")
    private UserDao<UserInfo> userDao;
    @Resource
    private CompanyDao<CompanyInfo> companyDao;
    @Resource
    private IGroup groupService;
    @Resource
    private IGroupUser groupUserService;
    @Resource
    private IRole roleService;
    @Resource
    private IRoleUser roleUserService;
	@Resource(name = "userGroupService")
	private IUserGroup userGroupService;
    
    /**
     * 根据人员ID，部门ID，角色ID获取人员列表
     * @param userIds 人员Id，之间用，隔开
     * @param groupIds 部门Id，之间用，隔开
     * @param roleIds 角色Id，之间用，隔开
     * @return
     */
    public List<UserInfo> findUsersByIds(String userIds,String groupIds,String roleIds)
    {
          List<UserInfo> userList=new ArrayList<UserInfo>();
          List idsList=new ArrayList();//人员ID列表
          List<UserInfo> ulist=findUsersByIds(userIds);//根据人员id获取人员列表
          List<UserInfo> glist=findUsersByGroupId(groupIds);//根据部门id获取人员列表
          List<UserInfo> rlist=findUsersByRoleId(roleIds);//根据角色id获取人员列表
        if(ulist!=null)
        {
          for(UserInfo user:ulist)
          {

              if(!idsList.contains(user.getUserId()))
              {
                  idsList.add(user.getUserId());
                  userList.add(user);
              }
          }
        }
        if(glist!=null)
        {
            for(UserInfo user:glist)
            {
                if(!idsList.contains(user.getUserId()))
                {
                    idsList.add(user.getUserId());
                    userList.add(user);
                }
            }
        }
        if(rlist!=null)
        {
            for(UserInfo user:rlist)
            {
                if(!idsList.contains(user.getUserId()))
                {
                    idsList.add(user.getUserId());
                    userList.add(user);
                }
            }
        }
        return userList;
    }
  
	/**
     * 根据人员ID，部门ID，角色ID获取人员列表
     * @param userIds 人员Id，之间用，隔开
     * @return
     */
    public List<UserInfo> findUsersByIds(String userIds)
    {
        return userDao.findUsersByIds(userIds);
    }
    /**
     * 查找人员
     * @param companyId 企业ID
     * @param searchName  查找人员条件，姓名，电话，手机
     * @return
     */
    public List<UserInfo> findAllUsers(int companyId,String searchName)
    {
           return userDao.findAllUsers(companyId,searchName);
    }
	/**
	 *  得到未登录人员
	 * @param companyId
	 * @param searchName
	 * @return
	 */
	public List<UserInfo> findAllNoLoginUsers(int companyId, String searchName) {
		return userDao.findAllNoLoginUsers(companyId, searchName);
	}
    /**
     * 根据角色 id获取人员列表
     * @param ids 角色 id,多个id之间，隔开
     * @return
     */
    public List<UserInfo> findUsersByRoleId(String ids)
    {
           return userDao.findUsersByRoleId(ids);
    }
    /**
     * 根据部门/群组 id获取人员列表
     * @param ids  部门/群组 id,多个id之间，隔开
     * @return
     */
    public List<UserInfo> findUsersByGroupId(String ids)
    {
        return  userDao.findUsersByGroupId(ids);
    }
    
    
    @Override
    public UserInfo getUserByUserName(String loginName) {
        return userDao.getUserByUserName(loginName);
    }

    @Override
    public UserInfo getUserByPhone(String phone) {
        return  userDao.getUserByPhone(phone);
    }

    @Override
    public Page<UserInfo> findByPage(int companyId, Pageable page, int groupId) throws Exception {
        return userDao.findByPage(companyId,page,groupId);
    }

    /**
     * 根据id列表删除人员，多个id之间用，隔开
     * @param companyId 单位ID
     * @param ids
     */
    @CacheEvict(value="userTreeCache",allEntries=true)
  public  void deleteUserByIds(Integer companyId,String ids){
    	List<UserInfo> userList =  this.findUsersByIds(ids);
        
        //发布删除用户的事件
        PublishEventUtil.publishEvent(new EventForDeleteUser(userList));
        userDao.deleteUserByIds(companyId, ids);
    }
 
	/**
	 * 
	 * 功能：更新用户状态
	 * @param ids
	 */
    public void updateStateByIds(String ids){
    	userDao.updateStateByIds(ids);
    }
	/**
	 * 
	 * 功能：更新用户状态
	 * @param ids
	 */
    public void updatePasswordByIds(String ids,String password){
    	userDao.updatePasswordByIds(ids, password);
    }

	/**
	 * 功能：修改用户密码
	 * @param phone
	 * @param password
	 */
	public void updatePasswordByPhone(String phone, String password) {
		userDao.updatePasswordByPhone(phone, password);
	}
    /**
     * 更新上次登录时间
     * @param userId
     */
    public  void updateLastLoginTime(int userId)
    {
    	UserInfo u = userDao.findOne(userId);
    	u.setLastLoginTime(DateTimeUtil.stringToDate(DateTimeUtil.getCurrentTime(),"yyyy-MM-dd HH:mm:ss"));
        userDao.saveOrUpdate(u);
    }

    
	/**
	 * @Title: findAllUsers 
	 * @Description: TODO(管理员列表) 
	 * @param userName 用户名
	 * @param phone 电话
	 * @param PY 拼音
	 * @return List<User>   返回类型
	 */
	public List<UserInfo> findAllUsers(int companyId,String userName,String phone,String PY){
		return userDao.findAllUsers(companyId,userName, phone, PY);
	}
	
    /**
     * 根据vo查询联系人信息
     * @param vo UserVo
     * @return List<Address>
     */
    public Page<UserInfo> findAllUsersByPage(UserVo vo,Pageable page,String groupIds){
    	return userDao.findAllUsersByPage(vo,page,groupIds);
    }
    public Page<Map<String, Object>> findByPageAll(UserVo vo,Pageable page,String groupIds){
    	return userDao.findByPageAll(vo,page,groupIds);
    }
	@Override
	public List<UserInfo> findAll() {
		// TODO Auto-generated method stub
		return userDao.findAll();
	}
	
	@Override
	public UserInfo findByLoginName(String loginName) {
		// TODO Auto-generated method stub
		return userDao.findByLoginName(loginName);
	}
	
    /**
     * 
     * 功能：根据姓名和电话查询人员
     * @param companyId 公司Id
     * @param name 姓名
     * @param phone 电话
     * @return 人员信息
     */
	public UserInfo getUserByNamePhone(int companyId,String name, String phone)
    {
        return userDao.getUserByNamePhone(companyId, name, phone);
    }
	
	/**
     * 
     * 功能：根据公司id和电话查询人员
     * @param companyId 公司Id
     * @param phone 电话
     * @return 人员信息
     */
	public UserInfo getUserByNamePhone(int companyId, String phone)
    {
        return userDao.getUserByNamePhone(companyId, phone);
    }
	
	@Override
	public List<UserInfo> findByPage(Pageable page) {
		// TODO Auto-generated method stub
		return userDao.findUsersByPage(page);
	}
	/**
	 * 根据用户修改皮肤标志
	 * @param id
	 * @param skinLogo
	 */
	@Override
	public void updateUserSkinLogo(int id, int skinLogo) {
		userDao.updateUserSkinLogo(id, skinLogo);
	}
	
	/**
	 * 功能：
	 * @param
	 * @return
	 * @throws   
	 */
	@Override
    public Map<Integer, String> getAllUserName() {
	    // TODO Auto-generated method stub
	    return userDao.getAllUserName();
    }
	@Override
	public List<UserInfo> findUsersByRoleId(Integer roleId, Integer companyId,
			Integer type) {
		// TODO Auto-generated method stub
		return userDao.findUsersByRoleId(roleId, companyId, type);
	}

    /**
     * 人员排序
     * @param companyId 单位ID
     * @param groupId   部门ID
     * @param userId    人员ID
     * @param startIndex 开始索引
     * @param endIndex   结束索引
     */
    public void sortOrder(Integer companyId,Integer groupId,Integer userId,Integer startIndex,Integer endIndex)
    {
        startIndex=startIndex+1;
        endIndex=endIndex+1;
        UserInfo user=userDao.findOne("companyId=?1 and userId=?2",companyId,userId);
        if(user!=null)
        {
            //人员当前排序
            int startOrder=user.getOrderIndex();
            UserInfo endUser=userDao.getSortOrderUser(companyId,groupId,endIndex);
            //人员目标排序
            int endOrder=0;
            if(endUser!=null)
            {
                endOrder=endUser.getOrderIndex();
            }
            //更新开始结束之间索引
            if(startOrder>=0&&endOrder>=0){
                //排序
                userDao.sortOrder(companyId,groupId,startOrder,endOrder);
                user.setOrderIndex(endOrder);
                user.setLastUpdateTime(DateTimeUtil.stringToDate(DateTimeUtil.getCurrentTime(), "yyyy-MM-dd HH:mm:ss"));
                //更改人员排序信息
                userDao.saveOrUpdate(user);
            }
        }
    }
    /**
     * 获取最大排序号
     * @param companyId
     * @param groupId
     * @return
     */
    public Integer getMaxOrderIndex(Integer companyId,Integer groupId)
    {
         return userDao.getMaxOrderIndex(companyId, groupId);
    }
    
    
   /*
    * 修改用户的时候更新下最近一次更新时间
    * 定义排序号规则
    * (non-Javadoc)
    * @see cn.com.qytx.platform.base.service.impl.AbstractBaseServiceImpl#saveOrUpdate(java.lang.Object)
    */
    @Override
    @CacheEvict(value="userTreeCache",allEntries=true)
  	public UserInfo saveOrUpdate(UserInfo entity) {
    	entity.setLastUpdateTime(new Date(Calendar.getInstance().getTimeInMillis()));
    	Object oldUserId = entity.getUserId();
    	String oldPhone = "";
    	if(oldUserId != null){
    		UserInfo oldUserInfo = super.findOne(Integer.valueOf(oldUserId.toString()));
    		oldPhone = oldUserInfo.getPhone();
    	}
  		// TODO Auto-generated method stub
    	UserInfo userInfo = super.saveOrUpdate(entity);
      	//修改用户的最近一次更新时间
    	if(oldUserId == null){
    		PublishEventUtil.publishEvent(new EventForAddUser(userInfo));
    	}else{
    		PublishEventUtil.publishEvent(new EventForUpdateUser(userInfo,oldPhone));
    	}
  		return userInfo;
  	}
  	@Override
  	 @CacheEvict(value="userTreeCache",allEntries=true)
  	public List<UserInfo> saveOrUpdate(Iterable<UserInfo> entities) {
  		// TODO Auto-generated method stub
  		for(UserInfo userInfo:entities){
  			userInfo.setLastUpdateTime(new Date(Calendar.getInstance().getTimeInMillis()));
  			groupService.updateGroupTime(userInfo.getGroupId());
  		}
  		return super.saveOrUpdate(entities);
  	}
  	
  	@Override
  	 @CacheEvict(value="userTreeCache",allEntries=true)
  	public int delete(UserInfo entity, boolean fromDB) {
  		// TODO Auto-generated method stub
  		if(!fromDB){
  			saveOrUpdate(entity);
  		}
  		return super.delete(entity, fromDB);
  	}
  	@Override
  	 @CacheEvict(value="userTreeCache",allEntries=true)
  	public int delete(Integer id, boolean fromDB) {
  		// TODO Auto-generated method stub
  		if(!fromDB){
  			UserInfo user = super.findOne(id);
  			saveOrUpdate(user);
  		}
  		return super.delete(id, fromDB);
  	}
  	@Override
  	 @CacheEvict(value="userTreeCache",allEntries=true)
  	public int deleteByIds(Iterable<Integer> ids, boolean fromDB) {
  		// TODO Auto-generated method stub
  		if(!fromDB){
  			for(Integer id:ids){
  				UserInfo user = super.findOne(id);
  				saveOrUpdate(user);
  			}
  		}
  		return super.deleteByIds(ids, fromDB);
  	}
  	
  	public int getMaxUserOrder(int companyId){
  		
  		return 0;
  	}

	@Override
	public List<UserInfo> findUsersByRoleCodeAndGroup(String roleCode,
			int groupId) {
		// TODO Auto-generated method stub
		return userDao.findUsersByRoleCodeAndGroup(roleCode, groupId);
	}

	/* (non-Javadoc)
	 * 根据二级局查找二级局下属的部门-人员树，如果二级局为空则查询所有的部门-人员树
	 * 有缓存的实现，
	 * @see cn.com.qytx.platform.org.service.IUser#selectUserByGroup(cn.com.qytx.platform.org.domain.UserInfo, cn.com.qytx.platform.org.domain.GroupInfo, java.lang.String, int)
	 */
	@Override
//	@Cacheable(value="userTreeCache",key="#key+#moduleName+#showType+#groupType+'usergroup'")
	public List<TreeNode> selectUserByGroup(UserInfo adminUser,GroupInfo forkGroup,String moduleName,int showType,int key,String path,int groupType) {
			List<TreeNode> treeNodes = new ArrayList<TreeNode>();
	      	CompanyInfo companyInfo = companyDao.findOne(adminUser.getCompanyId());
	        List<GroupInfo> groupList = new ArrayList<GroupInfo>();
	        if(companyInfo!=null)
	        {
	            if(forkGroup==null){
	            	TreeNode treeHead = new TreeNode();
	            	treeHead.setId("gid_0");//部门ID前加gid表示类型为部门
	            	treeHead.setName(StringUtils.isBlank(companyInfo.getShortName())?companyInfo.getCompanyName():companyInfo.getShortName());
	              	treeHead.setTitle(StringUtils.isBlank(companyInfo.getShortName())?companyInfo.getCompanyName():companyInfo.getShortName());
	            	treeHead.setPId("gid_-1");
	            	treeHead.setIcon(path + "/images/company.png");
	            	treeHead.setOpen(true);
	            	treeNodes.add(treeHead);
	            	groupList = groupService.getGroupList(companyInfo.getCompanyId(), groupType);
	            }else{
	            	//add by jiayq
	            	TreeNode treeHead = new TreeNode();
	            	treeHead.setId("gid_"+forkGroup.getGroupId());
	            	treeHead.setName(forkGroup.getGroupName());
	            	treeHead.setTitle(forkGroup.getGroupName());
	            	treeHead.setPId("gid_-1");
	            	treeHead.setIcon(path + "/images/group.png");
	            	treeHead.setOpen(true);
	            	treeNodes.add(treeHead);
	            	groupList = groupService.getSubGroupList(forkGroup.getGroupId());
	            }
	        }
	       
	        if (groupList != null)
	        {
	            String ids = "";
	            // 遍历部门
	            for (GroupInfo group : groupList)
	            {
	                ids += group.getGroupId() + ",";
	               // String groupName = StringUtils.substring(group.getGroupName(), 0, 8);
	                TreeNode treeNode = new TreeNode();
	                treeNode.setId("gid_" + group.getGroupId().toString());// 部门ID前加gid表示类型为部门
	                treeNode.setName(group.getGroupName());
	                treeNode.setTitle(group.getGroupName());
	                treeNode.setObj(group.getOrderIndex());
	                treeNode.setPId("gid_" + group.getParentId().toString());
	                treeNode.setIcon(path + "/images/group.png");
	                treeNodes.add(treeNode);
	            }
	            // 需要显示人员
	            if (showType == 3)
	            {
	
	                if (ids.endsWith(","))
	                {
	                    // 移除后面的,
	                    ids = ids.substring(0, ids.length() - 1);
	                }
	                
	                //add by jiayq，把二级局单位下面的人员也添加上
	                if(forkGroup!=null){
	                	ids+=","+forkGroup.getGroupId();
	                }
	                
	                // 遍历人员
	                List<UserInfo> userList = this.findUsersByGroupId(ids);
	               // List<GroupUser> groupUsers = groupUserService.findAll();
	                if (userList != null)
	                {
	                    for (UserInfo user : userList)
	                    {
	                        if ("roleManager".equals(moduleName) && user.getUserState()!=null && user.getUserState().intValue() == UserInfo.USERSTATE_UNLOGIN){
	                            continue;
	                        }
	                        TreeNode treeNode = new TreeNode();
	                        treeNode.setId("uid_" + user.getUserId());// 部门ID前加gid表示类型为部门
	                        treeNode.setName(user.getUserName());
	                        if(!"userTree".equals(moduleName)  && user.getIsVirtual() != null && user.getIsVirtual().intValue() == 1){
	                        	 treeNode.setId("uid_" + user.getLinkId());// 部门ID前加gid表示类型为部门
	                        }
	                        
	                        treeNode.setObj(user.getPhone()); // 把号码存放到node里面，js里面调用
	                        treeNode.setPId("gid_"
	                                + user.getGroupId());
	                        if (null != user.getSex() && 0 == user.getSex())
	                            {
	                                treeNode.setIcon(path + "/images/woman.png");
	                            }
	                            else
	                            {
	                                treeNode.setIcon(path + "/images/man.png");
	                            }
	                        treeNodes.add(treeNode);
	                    }
	                }
	            }
	        }
	        return treeNodes;
		}
	
	/* (non-Javadoc)
	 * 根据二级局查找二级局下属的部门-人员树，如果二级局为空则查询所有的部门-人员树
	 * 有缓存的实现，
	 * @see cn.com.qytx.platform.org.service.IUser#selectUserByGroup(cn.com.qytx.platform.org.domain.UserInfo, cn.com.qytx.platform.org.domain.GroupInfo, java.lang.String, int)
	 */
	@Override
	public List<TreeNode> selectUserByGroup(int companyId,GroupInfo forkGroup,String moduleName,int showType,int key,String path,int groupType,Integer optType,Integer state) {
			List<TreeNode> treeNodes = new ArrayList<TreeNode>();
	      	CompanyInfo companyInfo = companyDao.findOne(companyId);
	        List<GroupInfo> groupList = new ArrayList<GroupInfo>();
	        if(companyInfo!=null)
	        {
	            if(forkGroup==null){
	            	TreeNode treeHead = new TreeNode();
	            	treeHead.setId("gid_0");//部门ID前加gid表示类型为部门
	            	treeHead.setName(StringUtils.isBlank(companyInfo.getShortName())?companyInfo.getCompanyName():companyInfo.getShortName());
	              	treeHead.setTitle(StringUtils.isBlank(companyInfo.getShortName())?companyInfo.getCompanyName():companyInfo.getShortName());
	            	treeHead.setPId("gid_-1");
	            	treeHead.setIcon(path + "/images/company.png");
	            	treeHead.setOpen(true);
	            	treeHead.setObj(0);
	            	treeNodes.add(treeHead);
	            	groupList = groupService.getGroupList(companyInfo.getCompanyId(), groupType);
	            }else{
	            	//add by jiayq
	            	TreeNode treeHead = new TreeNode();
	            	treeHead.setId("gid_"+forkGroup.getGroupId());
	            	treeHead.setName(forkGroup.getGroupName());
	            	treeHead.setTitle(forkGroup.getGroupName());
	            	treeHead.setPId("gid_-1");
	            	treeHead.setIcon(path + "/images/group.png");
	            	treeHead.setOpen(true);
	            	treeNodes.add(treeHead);
	            	groupList = groupService.getSubGroupList(forkGroup.getGroupId());
	            }
	        }
	       
	        if (groupList != null)
	        {
	            String ids = "";
	            // 遍历部门
	            for (GroupInfo group : groupList)
	            {
	                ids += group.getGroupId() + ",";
	               // String groupName = StringUtils.substring(group.getGroupName(), 0, 8);
	                TreeNode treeNode = new TreeNode();
	                treeNode.setId("gid_" + group.getGroupId().toString());// 部门ID前加gid表示类型为部门
	                treeNode.setName(group.getGroupName());
	                treeNode.setTitle(group.getGroupName());
	                treeNode.setPId("gid_" + group.getParentId().toString());
	                treeNode.setIcon(path + "/images/group.png");
	                treeNode.setObj(group.getOrderIndex());
	                treeNodes.add(treeNode);
	            }
	            // 需要显示人员
	            if (showType == 3)
	            {
	
	                if (ids.endsWith(","))
	                {
	                    // 移除后面的,
	                    ids = ids.substring(0, ids.length() - 1);
	                }
	                
	                //add by jiayq，把二级局单位下面的人员也添加上
	                if(forkGroup!=null){
	                	ids+=","+forkGroup.getGroupId();
	                }
	                
	                // 遍历人员
	                List<UserInfo> userList = this.findUsersByGroupId(ids);
	                //List<GroupUser> groupUsers = groupUserService.findAll();
	                if (userList != null)
	                {
	                    for (UserInfo user : userList)
	                    {
	                        if ("roleManager".equals(moduleName) && user.getUserState()!=null && user.getUserState().intValue() == UserInfo.USERSTATE_UNLOGIN){
	                            continue;
	                        }
	                        //判断漏话提醒 转接设置
	                        if (optType!=null&&optType==1) {//漏话提醒
								if (state!=null&&state==1) {//开通了   过滤开通了的人员
									if (user.getMcnType()!=null&&user.getMcnType()==1) {
										continue;
									}
								}else if(state!=null&&state==0){//没开通  过滤未开通了的人员
									if (user.getMcnType()!=null&&user.getMcnType()==0) {
										continue;
									}
								}
							}
	                        //判断转接设置
	                        if (optType!=null&&optType==2) {//漏话提醒
								if (state!=null&&state==2) {//不可被转接   过滤不可被转接的人员
									if (user.getUserPower()!=null&&user.getUserPower()==2) {
										continue;
									}
								}else if(state!=null&&state==1){//仅限内部转接  过滤仅限内部转接的人员
									if (user.getUserPower()!=null&&user.getUserPower()==1) {
										continue;
									}
								}else if(state!=null&&state==0){//不限制  过滤不限制的人员
									if (user.getUserPower()!=null&&user.getUserPower()==0) {
										continue;
									}
								}
							}
	                        TreeNode treeNode = new TreeNode();
	                        treeNode.setId("uid_" + user.getUserId());// 部门ID前加gid表示类型为部门
	                        treeNode.setName(user.getUserName());
	                        treeNode.setObj(user.getPhone()); // 把号码存放到node里面，js里面调用
	                        treeNode.setPId("gid_"
	                                + user.getGroupId());
	                        if (null != user.getSex() && 0 == user.getSex())
	                            {
	                                treeNode.setIcon(path + "/images/woman.png");
	                            }
	                            else
	                            {
	                                treeNode.setIcon(path + "/images/man.png");
	                            }
	                        treeNodes.add(treeNode);
	                    }
	                }
	            }
	        }
	        return treeNodes;
		}
	  	
//    private int getGroupIdByUserId(List<GroupUser> groupUsers,Integer userId){
//    	for(int i=0; i<groupUsers.size(); i++){
//    		if(groupUsers.get(i).getUserId()!=null && groupUsers.get(i).getUserId().intValue() == userId.intValue()){
//    			return groupUsers.get(i).getGroupId();
//    		}
//    	}
//    	return 0;
//    }

	@Override
	public List<TreeNode> selectUserByRole(UserInfo userInfo,String moduleName, int showType,String path) {
		 List<TreeNode> treeNodes = new ArrayList<TreeNode>();
	     List<RoleInfo> roleList = roleService.unDeleted().findAll();
	     if (roleList != null){
	            String ids = "";
	            // 遍历部门
	            for (RoleInfo role : roleList)
	            {
	                ids += role.getRoleId() + ",";
	                TreeNode treeNode = new TreeNode();
	                treeNode.setTitle(role.getRoleName());
	                treeNode.setId("rid_" + role.getRoleId().toString());// rid表示类型为角色
	                treeNode.setName(role.getRoleName());
	                treeNode.setPId("0");
	                treeNodes.add(treeNode);
	            }
	            // 需要显示人员
	            if (showType == 3){
	            	List<RoleUser> roleusers = roleUserService.findAll();
	            	
	            	if(roleusers!=null && roleusers.size()>0 && !"roleManager".equals(moduleName)){
	            		for(RoleUser roleUser:roleusers){
	            			if(roleUser!=null && roleUser.getUserId()!=null){
	            				UserInfo user = super.unDeleted().findOne(roleUser.getUserId());
	            				if(user!=null && user.getUserState().intValue() != UserInfo.USERSTATE_UNLOGIN){
	            					TreeNode treeNode = new TreeNode();
	    	                        treeNode.setId("uid_" + user.getUserId());// 部门ID前加gid表示类型为部门
	    	                        treeNode.setName(user.getUserName());
	    	                        if(user.getIsVirtual() != null && user.getIsVirtual().intValue() == 1){
	    	                        	 treeNode.setId("uid_" + user.getLinkId());// 部门ID前加gid表示类型为部门
	    	                        }
	    	                        treeNode.setObj(user.getPhone()); // 把号码存放到node里面，js里面调用
	    	                        treeNode.setPId("rid_"+roleUser.getRoleId());
	    	                        if (null != user.getSex() && 0 == user.getSex())
	    	                        {
	    	                            treeNode.setIcon(path + "/images/woman.png");
	    	                        }
	    	                        else
	    	                        {
	    	                            treeNode.setIcon(path + "/images/man.png");
	    	                        }

	    	                        treeNodes.add(treeNode);
	            				}
	            			}
	            		}
	            	}
	            }
	        }
		return treeNodes;
	}
	
	@Override
	public boolean phoneIsRepeat(String phone) {
		// TODO Auto-generated method stub
		List<UserInfo> us = userDao.findByPhoneWithoutCompany(phone);
		if(us == null || us.size() == 0){
			return false;
		}else{
			return true;
		}
	}
	
	public boolean phoneIsRepeatWithCompany(String phone) {
		// TODO Auto-generated method stub
		List<UserInfo> us = userDao.findByPhone(phone);
		if(us == null || us.size() == 0){
			return false;
		}else{
			return true;
		}
	}

	@Override
	public List<UserInfo> findUsersByPhone(String phone) {
		return userDao.findByPhone(phone);
	}
	
	@Override
	 @CacheEvict(value="userTreeCache",allEntries=true)
	@Deprecated
	public void addOrUpdateUserWithOrder(UserInfo userInfo,String oldPhone) {
		userInfo.setPartitionCompanyId(userInfo.getCompanyId()%10);
		
		int groupId = userInfo.getGroupId();
		GroupInfo gi = groupService.findOne(groupId);
		userInfo.setIsForkGroup(gi.getIsForkGroup());
		
		//update by jiayq,判断当前操作室修改还是添加:true添加；false修改
		boolean flag = true;
		if(userInfo.getUserId() != null){
			flag = false;
		}
		userInfo.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));

		if (null==userInfo.getOrderIndex()) {
			userInfo.setOrderIndex(userDao.getMaxOrderIndex(userInfo.getCompanyId())+1);
			super.saveOrUpdate(userInfo);
		}
		else {
			super.saveOrUpdate(userInfo);
			//userDao.updateUserOrder(userInfo);  //anxing fix 20141130
		}
		
/*		if(userInfo.getUserId() == null){//如果是新增用户的话,修改部门的修改时间
			groupService.updateGroupTime(userInfo.getGroupId());
		}*/
		groupService.updateGroupTime(userInfo.getGroupId());
		//广播添加用户事件
		if(flag){
			PublishEventUtil.publishEvent(new EventForAddUser(userInfo));
		}else{
			PublishEventUtil.publishEvent(new EventForUpdateUser(userInfo,oldPhone));
		}
	}
	
	@Override
	@CacheEvict(value="userTreeCache",allEntries=true)
	@Deprecated
	public void addOrUpdateUserWithOrder(UserInfo userInfo) {
		this.addOrUpdateUserWithOrder(userInfo, null);
	}

	@Override
	public boolean phoneIsRepeat(String userName, String phone) {
		List<UserInfo> us = userDao.findByUserNameAdnPhone(userName, phone);
		if(us == null || us.size() ==0){
			return false;
		}
		return true;
	}

	@Override
	public List<UserInfo> findUsersByLoginNames(String loginNames) {
		return userDao.findByLoginNames(loginNames);
	}

	@Override
	public List<TreeNode> selectUserByQunzu(int companyId, int qunzuType,String contextPath) {
		List<TreeNode> nodelist = new ArrayList<TreeNode>();
		
		//查找群组
		List<GroupInfo> grouplist = groupService.findGroupTree(companyId, qunzuType);
		for(int i=0; i<grouplist.size(); i++){
			GroupInfo gi = grouplist.get(i);
			TreeNode treeNode = new TreeNode();
			treeNode.setId("gid_"+gi.getGroupId());
			treeNode.setName(gi.getGroupName());
			treeNode.setPId("gid_"+(gi.getParentId()==null?0:gi.getParentId().toString()));
			treeNode.setIcon( contextPath+ "/images/group.png");
			nodelist.add(treeNode);
		}
		
		//查找人员
		List<GroupUser> groupUsers = groupUserService.findByType(companyId, qunzuType);
		for(int i=0; i<groupUsers.size(); i++){
			GroupUser gu = groupUsers.get(i);
			TreeNode treeNode = new TreeNode();
			UserInfo ui = userDao.findOne(gu.getUserId());
			if(ui == null || ui.getIsDelete().intValue()==1){
				continue;
			}
			treeNode.setId("uid_" + ui.getUserId());
			if(ui.getIsVirtual() != null && ui.getIsVirtual().intValue() == 1){
           	  treeNode.setId("uid_" + ui.getLinkId());
            }
			treeNode.setObj(ui.getPhone());
			treeNode.setName(ui.getUserName());
			treeNode.setPId("gid_"+gu.getGroupId());
			if (null != ui.getSex() && 0 == ui.getSex())
            {
                treeNode.setIcon(contextPath + "/images/woman.png");
            }
            else
            {
                treeNode.setIcon(contextPath + "/images/man.png");
            }
			nodelist.add(treeNode);
		}
		return nodelist;
	}
    /**
     * 得到
     * @param loginName
     * @param loginPass
     * @return
     */
	public  List<UserInfo> findByLoginNamePass(String loginName,String loginPass){
		return userDao.findByLoginNamePass(loginName, loginPass);
	}
    /**
     * 得到
     * @param loginName
     * @param loginPass
     * @return
     */
	public  List<UserInfo> findByLoginNamePassTXZL(String loginName,String loginPass){
		return userDao.findByLoginNamePassTXZL(loginName, loginPass);
	}
    /**
     * 
     * 功能：设置人员信息（是否可见，人工转接，漏话提醒）
     * <br/>@param type  1 设置人员是否可见 2 人工转接设置 3 漏话提醒设置 
     * <br/>@param companyId
     * <br/>@param userIds
     * <br/>@param state  是否可见(0显示1隐藏) 人工转接设置(1：不限制0：仅内部转接2：不可被转接) 漏话提醒设置(0：不需要，1：短信通知，2：语音留言)
     * <br/>@return  更新的记录数
     */
    public int updateUser(int type,int companyId,String userIds,int state){
    	int re = userDao.updateUser(type, companyId, userIds, state);
    	PublishEventUtil.publishEvent(new EventForSortUser(companyId));
    	return re;
    }
    /**
     * 功能：根据公司id和人员登录名得到用户
     * @param companyId
     * @param loginName
     * @return
     */
	public UserInfo loadUserByCompyIdName(Integer companyId,String loginName){
		return userDao.loadUserByCompyIdName(companyId, loginName);
	}

    /**
     * 根据单位ID和人员ID获取单位所有人员信息
     * @param companyId 单位ID
     * @param userId 人员ID(可能需要根据该人员的权限)
     * @return
     */
    public List<UserInfo> findAll(Integer companyId,Integer userId)
    {
        return userDao.findAll(companyId, userId);
    }

	@Override
//	@CacheEvict(value="userTreeCache",allEntries=true)
	public void deleteUserByIds(String userIds, boolean flag,int companyId) {
		// TODO Auto-generated method stub
        List<UserInfo> userList =  this.findUsersByIds(userIds);
        
        groupService.updateGroupsByUserIds(companyId, userIds);//删除用户的同时将用户的部门的lastUpdateTime更新
		
        List<UserInfo> virtualUsers = userDao.findAll(" isDelete = 0 and isVirtual = 1 and linkId in ("+userIds+")");
        if(virtualUsers!=null && virtualUsers.size()>0){
        	if(!userIds.endsWith(",")){
        		userIds = userIds+",";
        	}
        	if(!userIds.startsWith(",")){
        		userIds = ","+userIds;
        	}
        	List<Integer> workedUserList = new ArrayList<Integer>();
        	for(int i=0;i<virtualUsers.size();i++){
        		UserInfo user = virtualUsers.get(i);
        		if(userIds.indexOf(","+user.getUserId()+",") >= 0 || workedUserList.contains(user.getLinkId())){//该虚拟用户在删除队列中
        			continue;
        		}else{
        			UserInfo mainUser = userDao.findOne(user.getLinkId());
        			//删除主用户部门人员关联
        			groupUserService.deleteGroupUserByUserIds(mainUser.getCompanyId(), mainUser.getGroupId(), mainUser.getUserId()+"");
        			//将虚拟用户的信息更新到主用户上
        			userDao.executeQuery("update " + userDao.getEntityClass().getSimpleName() + " set groupId = ? , officeTel= ? , orderIndex=? , job = ?  where userId =?", user.getGroupId(),user.getOfficeTel(),user.getOrderIndex(),user.getJob(),user.getLinkId());
        			//删除虚拟用户
        			deleteUserByIds(user.getUserId()+"", flag,companyId);
        			//通知队列该用户已经处理过了
        			userIds = userIds.replace(","+user.getLinkId()+",", ",");
        			workedUserList.add(user.getLinkId());
        		}
        	}
        	if(userIds.endsWith(",")){
        		userIds = userIds.substring(0,userIds.length()-1);
        	}
        	if(userIds.startsWith(",")){
        		userIds = userIds.substring(1);
        	}
        }
        if(StringUtils.isNotEmpty(userIds)){
        	userDao.deleteUserByIds(userIds);
        	roleUserService.deleteRoleUserByUserIds(userIds);
        }
        //发布删除用户的事件
        PublishEventUtil.publishEvent(new EventForDeleteUser(userList));
	}
	 public List<TreeNode> selecFilterGroup(UserInfo adminUser,GroupInfo forkGroup,String moduleName,int showType,int key,String path,int groupType) {
	  		List<TreeNode> treeNodes = new ArrayList<TreeNode>();
	        	CompanyInfo companyInfo = companyDao.findOne(adminUser.getCompanyId());
	          List<GroupInfo> groupList = new ArrayList<GroupInfo>();
	          if(companyInfo!=null)
	          {
	              if(forkGroup==null){
	              	TreeNode treeHead = new TreeNode();
	              	treeHead.setId("gid_0");//部门ID前加gid表示类型为部门
	              	treeHead.setName(StringUtils.isBlank(companyInfo.getShortName())?companyInfo.getCompanyName():companyInfo.getShortName());
	              	treeHead.setTitle(StringUtils.isBlank(companyInfo.getShortName())?companyInfo.getCompanyName():companyInfo.getShortName());
	              	treeHead.setPId("gid_-1");
	              	treeHead.setIcon(path + "/images/company.png");
	              	treeHead.setOpen(true);
	              	treeNodes.add(treeHead);
	              	groupList = groupService.getGroupList(companyInfo.getCompanyId(), groupType);
	              	UserGroup ug=userGroupService.findByUserCompany(adminUser.getUserId(), adminUser.getCompanyId());
	              	List delList = new ArrayList();
	              	if(ug!=null){
	              		List list=Arrays.asList(ug.getGroupPower().substring(1).split(","));
	              		for (GroupInfo groupInfo : groupList) {
	              			if(!list.contains(groupInfo.getGroupId().toString())){
	              				delList.add(groupInfo);
	              			}
	              		}
	              	}
	              	groupList.removeAll(delList);
	              }else{
	              	//add by jiayq
	              	TreeNode treeHead = new TreeNode();
	              	treeHead.setId("gid_"+forkGroup.getGroupId());
	              	treeHead.setName(forkGroup.getGroupName());
	              	treeHead.setTitle(forkGroup.getGroupName());
	              	treeHead.setPId("gid_-1");
	              	treeHead.setIcon(path + "/images/group.png");
	              	treeHead.setOpen(true);
	              	treeNodes.add(treeHead);
	              	groupList = groupService.getSubGroupList(forkGroup.getGroupId());
	              	UserGroup ug=userGroupService.findByUserCompany(adminUser.getUserId(), adminUser.getCompanyId());
	              	List delList = new ArrayList();
	              	if(ug!=null){
	              		List<String> list=Arrays.asList(ug.getGroupPower().substring(1).split(","));
	              		for (GroupInfo groupInfo : groupList) {
	              			if(!list.contains(groupInfo.getGroupId().toString())){
	              				delList.add(groupInfo);
	              			}
	              		}
	              	}
	              	groupList.removeAll(delList);
	              }
	          }
	         
	          if (groupList != null)
	          {
	              String ids = "";
	              // 遍历部门
	              for (GroupInfo group : groupList)
	              {
	                  ids += group.getGroupId() + ",";
	                 // String groupName = StringUtils.substring(group.getGroupName(), 0, 8);
	                  TreeNode treeNode = new TreeNode();
	                  treeNode.setId("gid_" + group.getGroupId().toString());// 部门ID前加gid表示类型为部门
	                  treeNode.setName(group.getGroupName());
	                  treeNode.setTitle(group.getGroupName());
	                  treeNode.setPId("gid_" + group.getParentId().toString());
	                  treeNode.setIcon(path + "/images/group.png");
	                  treeNodes.add(treeNode);
	              }
	              // 需要显示人员
	              if (showType == 3)
	              {

	                  if (ids.endsWith(","))
	                  {
	                      // 移除后面的,
	                      ids = ids.substring(0, ids.length() - 1);
	                  }
	                  
	                  //add by jiayq，把二级局单位下面的人员也添加上
	                  if(forkGroup!=null){
	                  	ids+=","+forkGroup.getGroupId();
	                  }
	                  
	                  // 遍历人员
	                  List<UserInfo> userList = userDao.findUsersByGroupIdAndFilter(ids);
	                  //List<GroupUser> groupUsers = groupUserService.findAll();
	                  if (userList != null)
	                  {
	                      for (UserInfo user : userList)
	                      {
	                          if ("roleManager".equals(moduleName) && user.getUserState()!=null && user.getUserState().intValue() == UserInfo.USERSTATE_UNLOGIN){
	                              continue;
	                          }
	                          TreeNode treeNode = new TreeNode();
	                          treeNode.setId("uid_" + user.getUserId());// 部门ID前加gid表示类型为部门
	                          treeNode.setName(user.getUserName());
	                          treeNode.setObj(user.getPhone()); // 把号码存放到node里面，js里面调用
	                          treeNode.setPId("gid_"
	                                  + user.getGroupId());
	                          if (null != user.getSex() && 0 == user.getSex())
	                              {
	                                  treeNode.setIcon(path + "/images/woman.png");
	                              }
	                              else
	                              {
	                                  treeNode.setIcon(path + "/images/man.png");
	                              }
	                          treeNodes.add(treeNode);
	                      }
	                  }
	              }
	          }
	          return treeNodes;
	  	}
	 
	  /**
	 * 功能：过滤隐藏人员和管理范围
	 * @param user
	 * @param searchName
	 * @return
	 */
	public List<UserInfo> findAllUsersAndFilter(UserInfo user,String searchName){
		  List<UserInfo> uList=userDao.findAllUsersAndFilter(user.getCompanyId(), searchName);
		  UserGroup ug=userGroupService.findByUserCompany(user.getUserId(), user.getCompanyId());
		  List<String> list=Arrays.asList(ug.getGroupPower().substring(1).split(","));
		  List delList = new ArrayList();
		  for (UserInfo u : uList) {
			  if(!list.contains(u.getGroupId().toString())){
        			delList.add(u);
        		}
		  }
		  uList.removeAll(delList);
		  return uList;
	  }
	
	/**
	 * 功能：获得用户的所有关联人
	 * @param userId
	 * @param companyId
	 * @return
	 */
	public List<UserInfo> findVirtualUsers(int userId,int companyId){
		return userDao.findVirtualUsers(userId, companyId);
	}
	
	/**
	 * 功能：将手机端传递过来的用户中虚拟用户转换为真实用户
	 * @param userIds
	 * @param flag true 要前后',' false 不要前后','
	 * @return
	 */
	public String transVirtualToTrueUser(String userIds,boolean flag){
		return userDao.transVirtualToTrueUser(userIds, flag);
	}

	@Override
	public Map<Integer, Integer> getUserNumByGroup(int companyId) {
		// TODO Auto-generated method stub
		return userDao.getUserNumByGroup(companyId);
	}

	@Override
	public Integer getUserNumsByGroupId(int companyid, int groupId) {
		// TODO Auto-generated method stub
		return userDao.getUserNumsByGroupId(companyid,groupId);
	}
	
	/**
	 * 功能：根据部门name 姓名获得用户信息
	 * @param companyId
	 * @param groupId
	 * @param userName
	 * @return
	 */
	public List<UserInfo> getUserByGroupUserName(int companyId,String groupName,String userName){
		return userDao.getUserByGroupUserName(companyId, groupName, userName);
	}

	@Override
	public Integer getMaxOrderIndex(Integer companyId) {
		// TODO Auto-generated method stub
		return userDao.getMaxOrderIndex(companyId);
	}
	
	public Timestamp getLastUpdateNew(int companyId) {
		return userDao.getLastUpdateNew(companyId);
	}
	

	public List<UserInfo> findUsersByLastUpdateTime(int companyId, String lastUpdateTime) {		
		return userDao.findUsersByLastUpdateTime(companyId,lastUpdateTime);
	}
	
	public List<UserInfo> findUsersByPhone(int companyId,String phone){
		return userDao.findByPhone(companyId,phone);
	}

	@Override
	public UserInfo getUserByCompanyIdPhone(int companyId, String phone) {
		// TODO Auto-generated method stub
		return userDao.getUserByCompanyIdPhone(companyId, phone);
	}

	@Override
	public List<UserInfo> getUserInfoByPhones(String phones) {
		// TODO Auto-generated method stub
		return userDao.getUserInfoByPhones(phones);
	}
	/**
	 * 功能：根据主用户获得虚拟用户
	 * @param userIds
	 * @return
	 */
	public List<UserInfo> getVirtualUsers(String userIds){
		return userDao.getVirtualUsers(userIds);
	}
	
	 /**
     * 获取人员列表
     * @param companyId 单位ID
     * @param lastUpdateTime 上次更新时间
     * @param count 数量
     * @return
     */
    public List<UserInfo> getUserList(Integer companyId,Integer userId,String lastUpdateTime,Integer count)
    {
        return userDao.getUserList(companyId, userId, lastUpdateTime, count);
    }

	@Override
	public UserInfo getUserInfoByLoginName(Integer companyId, String loginName) {
		return userDao.getUserInfoByLoginName(loginName, companyId);
	}

	@Override
	public UserInfo findDefaultUser(Integer companyId) {
		return userDao.findDefaultUser(companyId);
	}

	@Override
	public List<UserInfo> findCompanyUsers(Integer companyId) {
		// TODO Auto-generated method stub
		return userDao.findCompanyUsers(companyId);
	}

	@Override
	public List<UserInfo> searchUserlist(Integer companyId, String searchkey) {
		// TODO Auto-generated method stub
		return userDao.searchUserList(companyId, searchkey);
	}

	@Override
	public List<UserInfo> findAccount(Integer companyId) {
		return userDao.findAccount(companyId);
	}

	@Override
	public void updateUserOrderIndex(int userId, int orderIndex) {
		// TODO Auto-generated method stub
		userDao.updateUserOrders(userId, orderIndex);
	}
	
	@Override
	public List<UserInfo> searchUserlistByNameAndPhone(Integer companyId,
			String searchkey) {
		// TODO Auto-generated method stub
		return userDao.searchUserListByNameAndPhone(companyId, searchkey);
	}
}
