package cn.com.qytx.platform.org.service.impl;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.jadira.usertype.spi.utils.lang.StringUtils;
import org.jsoup.helper.StringUtil;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;
import cn.com.qytx.platform.event.PublishEventUtil;
import cn.com.qytx.platform.org.dao.GroupDao;
import cn.com.qytx.platform.org.dao.GroupUserDao;
import cn.com.qytx.platform.org.dao.UserDao;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.GroupUser;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.EventForAddGroup;
import cn.com.qytx.platform.org.service.EventForDeleteGroup;
import cn.com.qytx.platform.org.service.EventForUpdateGroup;
import cn.com.qytx.platform.org.service.IGroup;
import cn.com.qytx.platform.org.service.IGroupUser;
import cn.com.qytx.platform.utils.datetime.DateTimeUtil;

/**
 * 部门/群组管理实现类
 * User: 黄普友
 * Date: 13-2-16
 * Time: 下午3:00
 */
@Service("groupService")
@Transactional
public class GroupImpl extends BaseServiceImpl<GroupInfo> implements IGroup {
   
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Resource(name="groupDao")
	private GroupDao<GroupInfo> groupDao;
    
	@Resource(name="groupUserService")
	IGroupUser groupUserService;
	
	@Resource
	private UserDao<UserInfo> userDao;
	
	@Resource
	GroupUserDao<GroupUser> groupUserDao;

    /**
     * 获取自己所在的部门/群组
     * @param companyId
     * @param userId
     * @return
     */
	@Transactional(readOnly=true)
	public GroupInfo getGroupByUserId(int companyId,int userId)
    {
		UserInfo u = userDao.companyId(companyId).findOne(userId);
		if(u.getGroupId()!=null){
			GroupInfo g = groupDao.findOne(u.getGroupId());
			return g;
		}else{
			return null;
		}
    }
    
    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
    public List<GroupInfo> getGroupList(int companyId, int groupType) {
        return groupDao.getGroupList(companyId,groupType);
    }
    /**
     * 判断部门下面是否有子部门
     * @param companyId
     * @param groupId
     */
    @Transactional(readOnly=true)
    public int checkExistsChildGroup(int companyId,int groupId)
    {
            return groupDao.checkExistsChildGroup(companyId,groupId);
    }

	/**
	 * @Title: findGroupTree 
	 * @Description: TODO( 获取全部群组信息) 
	 * @param companyId
	 * @param groupType 组类型
	 * @return List<Group>    返回类型
	 */
    @Transactional(readOnly=true)
	public List<GroupInfo> findGroupTree(Integer companyId,Integer groupType){
		return groupDao.getGroupList(companyId, groupType);
	}

    @Transactional(readOnly=true)
	public GroupInfo getForkGroup(int companyId,int userId) {
//    	UserInfo u = userDao.findOne(userId);
//    	GroupInfo g = groupDao.findOne(u.getGroupId());
//		return getForkGroup(g); 
    	return null;
	}
	
	
	/**
	 * 功能：迭代返回所属分支机构
	 * @param
	 * @return
	 * @throws   
	 */
	private GroupInfo getForkGroup(GroupInfo gi){
		GroupInfo result = null;
		if(gi!=null){
			if(gi.getIsForkGroup()!=null && gi.getIsForkGroup() == 1){
				return gi;
			}else{
				Integer parentGroupId = gi.getParentId();
				if(parentGroupId == 0){
					return null;
				}else{
					GroupInfo groupInfo = findOne(parentGroupId);
					result = getForkGroup(groupInfo);
				}
			}
		}
		return result;
	}

	@Override
	public  String getGroupNamePathByUserId(int companyId, int userId) {
		// TODO Auto-generated method stub
		UserInfo u = userDao.findOne(userId);
		if(u!=null && u.getGroupId()!=null){
			GroupInfo g = groupDao.findOne(u.getGroupId());
			return groupDao.getGroupNamePath(companyId, g.getPath());
		}else{
			return "";
		}
	}
	
	public String getGroupNamePath(int companyId,String groupIdPath){
		return groupDao.getGroupNamePath(companyId, groupIdPath);
	}

	@Override
	public List<GroupInfo> getSubGroupList(int parentGroupId) {
		return groupDao.getSubGroupList(parentGroupId);
	}
	
	
    /**
     * 获取部门/群组列表
     * @param companyId
     * @param userId
     * @param needUserGroup 是否需要群组信息
     * @return
     */
    public List<GroupInfo> getGroupList(Integer companyId,Integer userId,Date lastUpdateTime,boolean needUserGroup)
    {
        return groupDao.getGroupList(companyId,userId,lastUpdateTime,needUserGroup);
    }
    /**
     * 获取部门/群组列表 (变更过的，sql server或框架不支持union拼写)
     * @param companyId
     * @param userId
     * @param needUserGroup 是否需要群组信息
     * @return
     */
    public List<GroupInfo> getGroupListChanged(Integer companyId,Integer userId,Date lastUpdateTime,boolean needUserGroup)
    {
    	return groupDao.getGroupListChanged(companyId,userId,lastUpdateTime,needUserGroup);
    }

	@Override
	public GroupInfo loadGroupByPathName(Integer companyId, String groupPathName) {
		// TODO Auto-generated method stub
		return groupDao.loadGroupByPathName(companyId, groupPathName);
	}

	@Override
	@Transactional(readOnly=true)
	public boolean isHasSameGroupName(int parentId, String groupName,int groupType,int companyId) {
		// TODO Auto-generated method stub
		return groupDao.isHasSameGroupName(parentId, groupName,groupType,companyId);
	}

	@Override
	public boolean isHasChild(int groupId) {
		// TODO Auto-generated method stub
		return groupDao.isHasChild(groupId);
	}

	@Override
	public boolean isHasUsers(int companyid,int groupId) {
		// TODO Auto-generated method stub
		int count = userDao.getUserNumsByGroupId(companyid, groupId);
		if(count>0){
			return true;
		}
		return false;
	}

	@Override
	public List<GroupInfo> findGradeGroupTree(Integer companyId,
			Integer groupType) {
		// TODO Auto-generated method stub
		return groupDao.findGradeGroupTree(companyId, groupType);
	}
    /**
     * 部门排序
     * @param companyId 单位ID
     * @param groupId   部门ID
     * @param startIndex 开始索引
     * @param endIndex   结束索引
     */
    public void sortOrder(Integer companyId,Integer groupId,Integer startIndex,Integer endIndex)
    {
        startIndex=startIndex+1;
        endIndex=endIndex+1;
        GroupInfo groupInfo=groupDao.findOne(groupId);
        if(groupInfo!=null)
        {
            //部门当前索引
            int startOrder=groupInfo.getOrderIndex();
            //得到父部门id
            int parentId=groupInfo.getParentId();
            GroupInfo endGroup=groupDao.getSortOrderGroup(companyId, parentId, groupId, endIndex);
            //部门目标排序
            int endOrder=0;
            if(endGroup!=null)
            {
                endOrder=endGroup.getOrderIndex();
            }
            //更新开始结束之间索引
            if(startOrder>=0&&endOrder>=0){
                 groupDao.sortOrder(companyId,parentId,startOrder,endOrder);
                groupInfo.setOrderIndex(endOrder);
                groupInfo.setLastUpdateTime(DateTimeUtil.stringToDate(DateTimeUtil.getCurrentTime(), "yyyy-MM-dd HH:mm:ss"));
                groupDao.saveOrUpdate(groupInfo);
            }
        }
    }
    /**
     * 获取最大排序号
     * @param companyId
     * @return
     */
    public Integer getMaxOrderIndex(Integer companyId,Integer parentId,int groupType)
    {
            return groupDao.getMaxOrderIndex(companyId,parentId,groupType);
    }

    //更新用户的最后一次更新时间
	@Override
	 @CacheEvict(value="userTreeCache",allEntries=true)
	public GroupInfo saveOrUpdate(GroupInfo entity) {
		// TODO Auto-generated method stub
		entity.setLastUpdateTime(new Date(Calendar.getInstance().getTimeInMillis()));
		return super.saveOrUpdate(entity);
	}

	@Override
	 @CacheEvict(value="userTreeCache",allEntries=true)
	public List<GroupInfo> saveOrUpdate(Iterable<GroupInfo> entities) {
		// TODO Auto-generated method stub
		for(GroupInfo groupInfo:entities){
			groupInfo.setLastUpdateTime(new Date(Calendar.getInstance().getTimeInMillis()));
		}
		return super.saveOrUpdate(entities);
	}

	@Override
	 @CacheEvict(value="userTreeCache",allEntries=true)
	public int delete(GroupInfo entity, boolean fromDB) {
		// TODO Auto-generated method stub
		if(!fromDB){
			this.saveOrUpdate(entity);
		}
		return super.delete(entity, fromDB);
	}

	@Override
	 @CacheEvict(value="userTreeCache",allEntries=true)
	public int delete(Integer id, boolean fromDB) {
		// TODO Auto-generated method stub
		if(!fromDB){
			GroupInfo g = this.findOne(id);
			this.saveOrUpdate(g);
		}
		return super.delete(id, fromDB);
	}

	@Override
	 @CacheEvict(value="userTreeCache",allEntries=true)
	public int deleteByIds(Iterable<Integer> ids, boolean fromDB) {
		// TODO Auto-generated method stub
		if(!fromDB){
			for(Integer id:ids){
				GroupInfo g = this.findOne(id);
				this.saveOrUpdate(g);
			}
		}
		return super.deleteByIds(ids, fromDB);
	}

	/**
     * 根据部门分机号码和公司id获取部门/群组
     * @param companyId
     * @param groupCode
     * @return
     */
	@Override
	public List<GroupInfo> getGroupListByGroupCode(int companyId,String groupCode){
    	return groupDao.getGroupListByGroupCode(companyId, groupCode);
    }
	
	public Set<GroupInfo> getAllparentGroups(List<GroupInfo> all,List<GroupInfo> childs){
		Set<GroupInfo> result = new HashSet<GroupInfo>();
		for(int i=0; i<childs.size(); i++){
			Set set = getParentGroupByChild(all, childs.get(i));
			result.addAll(set);
		}
		return result;
	}
	
	/**
	 * 功能：根据指定的部门获取父部门集合，包含该子部门
	 * 作者：jiayongqiang
	 * 参数：
	 * 输出：
	 */
	private Set<GroupInfo> getParentGroupByChild(List<GroupInfo> all,GroupInfo g){
		Set<GroupInfo> r = new HashSet<GroupInfo>();
		r.add(g);
		if(g.getPath()!=null){
			String[] strs = g.getPath().split(",");
			for(int i=0; i<strs.length; i++){
				String temp = strs[i];
				if(StringUtils.isNotEmpty(temp)){
					int id = Integer.parseInt(temp);
					GroupInfo gi = getGroupInfoFromList(all, id);
					if(gi!=null){
						r.add(gi);
					}
				}
			}
		}
		return r;
	}
	
	private GroupInfo getGroupInfoFromList(List<GroupInfo> list,int id){
		for(int i=0; i<list.size(); i++){
			if(list.get(i).getGroupId() == id){
				return list.get(i);
			}
		}
		return null;
	}
    
	public List<GroupInfo> getGroupListByIds(Integer companyId,String groupIds){
		return groupDao.getGroupListByIds(companyId, groupIds);
	}
	
	public int upOrDownOrder(GroupInfo groupInfo,Integer value){
		 return groupDao.upOrDownOrder(groupInfo, value);
	}
	
	@CacheEvict(value="userTreeCache",allEntries=true)
	public void addOrUpdateGroup(GroupInfo groupInfo){
		boolean isAdd = true;
		if(groupInfo.getGroupId()!=null){
			isAdd = false;
		}
		
		if(!isAdd){
			generateForkGroupForUpdate(groupInfo);
		}
		
		if (null==groupInfo.getOrderIndex()) {
			groupInfo.setOrderIndex(groupDao.getMaxOrderIndex(groupInfo.getCompanyId(),groupInfo.getParentId(),GroupInfo.DEPT)+1);
			super.saveOrUpdate(groupInfo);
		}
		else {
			super.saveOrUpdate(groupInfo);
			groupDao.updateGroupOrder(groupInfo);
		}
		
		//执行新增逻辑
		if(isAdd){
			Integer parentId = groupInfo.getParentId();
			if(parentId==0){
				groupInfo.setPath(String.valueOf(groupInfo.getGroupId()));
				groupInfo.setGrade(0);
	    	}else{
	        	//得到父对象
	    		GroupInfo groupParent=super.findOne(parentId);
	    		if(groupParent!=null){
	    			//设置路径
	    			String pathParent = groupParent.getPath();
	    			if(pathParent!=null){
	    				groupInfo.setPath(pathParent+","+groupInfo.getGroupId());
	    			}
	            	//设置级别
	    			Integer gradeParent=groupParent.getGrade();
	    			if(gradeParent!=null){
	    				groupInfo.setGrade(gradeParent+1);
	    			}
	    		}
	    	}
			
			generateForkGroupForAdd(groupInfo);
		}
		
		//广播添加用户事件
		PublishEventUtil.publishEvent(new EventForAddGroup(groupInfo));
	}
	
	/*
	 * add by jiayq,2015.4.10
	 * 如果该部门是1级部门，并且is_fork_group=1，该部门才是分支机构，如果is_fork_group=0,则不是分支机构
	 * 如果该部门不是1级部门，则设置为何一级部门相同的forkgroup
	 */
	private void generateForkGroupForAdd(GroupInfo groupInfo){
		if(groupInfo.getGrade()==null || groupInfo.getGrade() == 0 ){
			groupInfo.setIsForkGroup(groupInfo.getGroupId());
			groupDao.saveOrUpdate(groupInfo);
		}else{
			String path = groupInfo.getPath();
			if(path!=null){
				String[] strs = path.split(",");
				if(StringUtil.isNumeric(strs[0])){
					int groupId = Integer.parseInt(strs[0]);
					GroupInfo g = groupDao.findOne(groupId);
					groupInfo.setIsForkGroup(g.getIsForkGroup());
				}
				groupDao.saveOrUpdate(groupInfo);
			}
		}
	}
	
	/**add by jiayq，2015.4.10
	 * 如果部门是1级部门，不处理
	 * 如果不是1级部门，取一级部门的forkGroup
	 */
	private void generateForkGroupForUpdate(GroupInfo groupInfo){
		if(groupInfo.getGrade()!=null && groupInfo.getGrade() == 0 ){
			groupInfo.setIsForkGroup(groupInfo.getGroupId());
		}else{
			String path = groupInfo.getPath();
			if(path!=null){
				String[] strs = path.split(",");
				int groupId = Integer.parseInt(strs[0]);
				GroupInfo g = groupDao.findOne(groupId);
				groupInfo.setIsForkGroup(g.getIsForkGroup());
			}
		}
	}

	@Override
	@CacheEvict(value="userTreeCache",allEntries=true)
	public void deleteGroup(int id) {
		// TODO Auto-generated method stub
		//this.delete(id, true);
		GroupInfo gi= groupDao.findOne(id);
		gi.setLastUpdateTime(new Date());
		gi.setIsDelete(1);
		groupDao.saveOrUpdate(gi);
		
		//删除群组人员关联
		groupUserDao.deleteBySetId(gi.getCompanyId(), gi.getGroupId());
		//广播删除部门事件
		PublishEventUtil.publishEvent(new EventForDeleteGroup(groupDao.getGroupListByIds(gi.getCompanyId(),String.valueOf(id))));
	}
	
	/**
	 * 
	 * 功能：得到公司部门人员数map
	 * @param companyId
	 * @return
	 */
	public Map<Integer,Integer> getCompanyGroupCountMap(Integer companyId){
		//不包括子部门
		Map<Integer,Integer> map = groupDao.getCompanyGroupCountMap(companyId);
		
		List<GroupInfo> glist = this.getGroupList(companyId, GroupInfo.DEPT);
		Map<Integer,Integer> mapNum = new HashMap<Integer, Integer>();
		Set<Integer> set = new HashSet<Integer>();
	    set= map.keySet();
	    for(Integer groupId:set){
	    	mapNum.put(groupId, map.get(groupId));
	    }
		for(int i=0; i<glist.size(); i++){
			GroupInfo g = glist.get(i);
			int groupId = g.getGroupId();
			String path = g.getPath();
			if(StringUtils.isNotEmpty(path)){
				String[] strs = path.split(",");
				for(String str: strs){
					if(StringUtils.isNotEmpty(str)){
						int targetGroupId = Integer.parseInt(str);
						if( targetGroupId != groupId){
							int oldNum = (mapNum.get(targetGroupId)==null?0:mapNum.get(targetGroupId));
							int incrNum = (map.get(groupId)==null?0:map.get(groupId));
							mapNum.put(targetGroupId,oldNum+incrNum);
							if(targetGroupId == 393702){
								System.out.println(oldNum+"!!!!!!!!"+incrNum);
								System.out.println(oldNum+incrNum+g.getGroupName());
							}
						}
					}
				}
			}
		}
		return mapNum;
	}
	
	/**
	 * 修改指定的部门的修改时间为当前时间
	 */
	public void updateGroupTime(int groupId){
		groupDao.updateGroupTime(groupId);
	}

	/**
	 * 
	 * 功能：修改部门最后一次更新时间 根据人员ids
	 * @param companyId
	 * @param ids
	 */
	public void updateGroupsByUserIds(int companyId, String ids){
		groupDao.updateGroupsByUserIds(companyId, ids);
	}

	@Override
	public List<GroupInfo> getGroupsByUserIds(int companyId, String userIds) {
		// TODO Auto-generated method stub
		return groupDao.getGroupsByUserIds(companyId, userIds);
	}

	@Override
	public List<GroupInfo> findGroupListByGrade(int grade) {
		// TODO Auto-generated method stub
		return groupDao.findGroupListByGrade(grade);
	}

	@Override
	public List<GroupInfo> findForkGroupList() {
		// TODO Auto-generated method stub
		return groupDao.findForkGroupList();
	}
	
	public Timestamp getLastUpdateTime(int companyId){
		 return groupDao.getLastUpdateNew(companyId);
	}
	
	
	@Override
	@CacheEvict(value="userTreeCache",allEntries=true)
	public void addGroup(GroupInfo groupInfo) {
		// TODO Auto-generated method stub
			if (null==groupInfo.getOrderIndex()) {
				groupInfo.setOrderIndex(groupDao.getMaxOrderIndex(groupInfo.getCompanyId(),groupInfo.getParentId(),groupInfo.getGroupType())+1);
				super.saveOrUpdate(groupInfo);
			}
			else {
				super.saveOrUpdate(groupInfo);
				groupDao.updateGroupOrder(groupInfo);
			}
			
			updateGroupPath(groupInfo);
			
			if(groupInfo.getGroupType() == 5){//新增个人群组的时候需要添加个人进入个人群组
	    		GroupUser groupUser = new GroupUser();
	    		groupUser.setUserId(groupInfo.getUserId());
	    		groupUser.setCompanyId(groupInfo.getCompanyId());
	    		groupUser.setGroupId(groupInfo.getGroupId());
	    		groupUserService.saveOrUpdate(groupUser);
	    	}
			
			//广播添加部门事件
			PublishEventUtil.publishEvent(new EventForAddGroup(groupInfo));
	}

	@CacheEvict(value="userTreeCache",allEntries=true)
	@Override
	public void updateGroup(GroupInfo groupInfo) {
		// TODO Auto-generated method stub
			if (null==groupInfo.getOrderIndex()) {
				groupInfo.setOrderIndex(groupDao.getMaxOrderIndex(groupInfo.getCompanyId(),groupInfo.getParentId(),groupInfo.getGroupType())+1);
				super.saveOrUpdate(groupInfo);
			}
			else {
				super.saveOrUpdate(groupInfo);
				groupDao.updateGroupOrder(groupInfo);
			}
			
			updateGroupPath(groupInfo);
			//广播修改部门事件
			PublishEventUtil.publishEvent(new EventForUpdateGroup(groupInfo));
	}
	
	//add by jiayq 2014//11/28
		//设置路径
		private void updateGroupPath(GroupInfo groupInfo){
	    	if(groupInfo.getGrade()==0){
	    		groupInfo.setPath(groupInfo.getGroupId()+"");
	    	}else{
	    		Integer parentGroupId = groupInfo.getParentId();
	    		if(parentGroupId!=null && parentGroupId>0){
	    			GroupInfo parentGroup = groupDao.findOne(parentGroupId); 
	    			String pathParent = parentGroup.getPath();
	    			if(pathParent!=null){
	    				groupInfo.setPath(pathParent+","+groupInfo.getGroupId());
	    			}
	    		}
	    	}
	    	groupDao.saveOrUpdate(groupInfo);
		}

		@Override
		public List<GroupInfo> getGroupListChanged(Integer companyId,
				Integer userId, Date lastUpdateTime) {
			// TODO Auto-generated method stub
			return this.getGroupListChanged(companyId, userId, lastUpdateTime,true);
		}
		
	    /**
	     * 获得今天所有的部门修改
	     * @param companyId
	     * @return
	     */
	    public List<GroupInfo> getTodayGroupListChanged(Integer companyId){
	    	return groupDao.getTodayGroupListChanged(companyId);
	    }
	    
	    /**
	     * 获取部门列表
	     * @param companyId 单位ID
	     * @param lastUpdateTime 上次更新时间
	     * @param count 数量
	     * @return
	     */
	    public List<GroupInfo> getGroupList(Integer companyId,Integer userId,String lastUpdateTime,Integer count)
	    {
	        return groupDao.getGroupList(companyId, userId, lastUpdateTime, count);
	    }

		@Override
		public List<Map<String,Object>> findGroupListByParentIdAndGroupkey(Integer companyId,Integer parentId, String groupName,String gids) {
			// TODO Auto-generated method stub
			return groupDao.findGroupListByParentIdAndGroupkey(companyId,parentId, groupName,gids);
		}

		@Override
		public List<GroupInfo> getGroupListByParentId(Integer companyId,
				Integer parentId) {
			// TODO Auto-generated method stub
			return groupDao.getGroupInfoByParentId(parentId);
		}
		/**
		 * 在指定ID下面是否有相同的部门名称
		 * 功能：parentId
		 * @param parentId
		 * @param groupName
		 * @return
		 */
		@Transactional(readOnly=true)
		public boolean isHasSameGroupNameForSet(Integer parentId,String groupName,int groupType,int companyId,int userId){
			return groupDao.isHasSameGroupNameForSet(parentId, groupName, groupType, companyId, userId);
		}
}
