package cn.com.qytx.platform.org.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;
import cn.com.qytx.platform.org.dao.GroupDao;
import cn.com.qytx.platform.org.dao.GroupUserDao;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.GroupUser;
import cn.com.qytx.platform.org.service.IGroupUser;

/**   
 * 项目名称：wzerp   
 * 类名称：GroupUserImpl   
 * 类描述：   
 * 创建人：CQL   
 * 创建时间：2012-10-8 下午08:15:31   
 * 修改人：CQL   
 * 修改时间：2012-10-8 下午08:15:31   
 * 修改备注：   
 * @version      
 */
@Transactional
@Service("groupUserService")
public class GroupUserImpl  extends BaseServiceImpl<GroupUser> implements IGroupUser{
	
	@Resource(name="groupUserDao")
	private GroupUserDao<GroupUser> groupUserDao;
	@Resource(name="groupDao")
	private GroupDao<GroupInfo> groupDao;
    /**
     *  根据用户ID删除部门/群组人员对应关系
     */
    public void deleteGroupUserByUserIds(String ids,Integer groupType,Integer companyId) {
        groupUserDao.deleteGroupUserByUserIds(ids,groupType,companyId);
        
    }
    /**
     * 根据部门ID，用户ID删除部门/群组人员对应关系
     * @param ids
     */
    public void deleteGroupUserByUserIds(Integer companyId,Integer groupId,String ids)
    {
        groupUserDao.deleteGroupUserByUserIds(companyId, groupId, ids);
    }
	
	/**
	 * 
	 * @Title: getGroupUserByUserId 
	 * @Description: TODO(根据用户id得到对应) 
	 * @param @param id
	 * @param @return
	 * @return GroupUser    返回类型
	 */
	@Transactional(readOnly=true)
	public List<GroupUser> getGroupUserByUserId(Integer id) {
		return  groupUserDao.getGroupUserByUserId(id);
	}
	

	@Override
	public List<GroupUser> findByType(Integer companyId, Integer groupType) {
		// TODO Auto-generated method stub
		return groupUserDao.getByType(companyId, groupType);
	}


	@Override
	public int getUsersCountBySetId(int groupId) {
		// TODO Auto-generated method stub
		return groupUserDao.getUsersBySetId(groupId);
	}
    /**
     * 根据群组ID获取该群组下所有人员ID
     * @param companyId
     * @param groupId
     * @return
     */
    public List<Integer> getUserIdsBySetId(Integer companyId,Integer groupId)
    {
          return groupUserDao.getUserIdsBySetId(companyId,groupId);
    }
    
	/**
	 * 根据群组id删除群组人员关系
	 * @param companyId
	 * @param groupId
	 */
	public void deleteBySetId(int companyId,int groupId){
		groupUserDao.deleteBySetId(companyId, groupId);
	}
	
	@Override
	public List<Integer> getUserIdsBySetId(int groupId) {
		// TODO Auto-generated method stub
		return groupUserDao.getUserIdsBySetId(groupId);
	}
}
