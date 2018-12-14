package cn.com.qytx.platform.org.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.com.qytx.platform.base.dao.BaseDao;
import cn.com.qytx.platform.org.domain.GroupUser;

/**
 * 人员部门关系操作Dao
 */
@Repository("groupUserDao")
public class GroupUserDao <T extends GroupUser> extends BaseDao<GroupUser,Integer> implements Serializable{
	/**
	 * 描述含义
	 */
	private static final long serialVersionUID = 1L;

    /**
     * 根据部门ID，用户ID删除部门/群组人员对应关系
     * @param companyId 单位ID
     * @param groupId 单位Id
     * @param ids 人员ID集合，以逗号隔开
     */
    public void deleteGroupUserByUserIds(Integer companyId,Integer groupId,String ids)
    {
        if(ids==null||ids.equals(""))
            return;
        if(ids.endsWith(","))
        {
            ids=ids.substring(0,ids.length()-1);
        }
        if(ids.startsWith(","))
        {
            ids=ids.substring(1,ids.length());
        }

        String hql="delete from  "+this.getEntityClass().getName()+" where userId in ("+ids+") and groupId="+groupId+" and companyId="+companyId;
        this.entityManager.createQuery(hql).executeUpdate();
    }
	/**
     *  根据用户ID删除部门/群组人员对应关系
     */
    public void deleteGroupUserByUserIds(String str,Integer groupType,Integer companyId) {
    	String ids = str;
        if(ids==null||ids.equals(""))
            return;
        if(ids.endsWith(","))
        {
            ids=ids.substring(0,ids.length()-1);
        }
        if(ids.startsWith(","))
        {
            ids=ids.substring(1,ids.length());
        }
        String hql="delete from  "+this.getEntityClass().getSimpleName()+" where userId in ("+ids+") ";
        if (groupType!=null) {
			hql += " and groupId in (select groupId from GroupInfo where companyId="+companyId+" and groupType="+groupType+")";
		}
        
        this.createQuery(hql);
    }


	/**
	 * 获取人员的所有部门关系
	 * @param userId 人员ID
	 * @return 部门人员列表
	 */
	public List<GroupUser> getGroupUserByUserId(Integer userId) {
		String hql="userId=?1 ";
		return super.companyId().findAll(hql,userId);
	}

	/**
	 * 获取指定单位下，指定类型的群组，人数映射
	 * @param companyId 单位ID
	 * @param groupType 部门类型
	 * @return Map,key部门ID，value人员数量
	 */
	public Map findSetIdUserNumMap(Integer companyId, Integer groupType) {
		Map userGroupMap=new HashMap();
		String hql="select a.id,count(*) as userNum from GroupInfo a,GroupUser b where  a.companyId=? and a.groupType=? and a.id=b.groupId and b.userId in (select c.id from UserInfo c where c.isDelete=0) group by a.id";
		List list=super.find(hql,companyId,groupType);
		if(list!=null&&list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				Object[] object=(Object[])list.get(i);
				userGroupMap.put(object[0], ((Long)(object[1])).intValue());
			}
		}
		return userGroupMap;
	}
	
	/**
	 * 获取单位下指定类型的所有人员部门/群组关系
	 * @param companyId 单位ID
	 * @param type 部门类型
	 * @return groupUser集合
	 */
	public List<GroupUser> getByType(Integer companyId,int type){
		String hql = "companyId = ? and groupId in (select groupId from GroupInfo where isDelete =0 and groupType = ?)";
		return super.findAll(hql, companyId,type);
	}
	
	/**
	 * 根据群组ID获取群组人员数
	 * @param groupId 
	 * @return 部门人员数
	 */
	public int getUsersBySetId(int groupId){
		String hql = "select count(*) from "+this.getEntityClass().getSimpleName()+" gu ,UserInfo u where gu.userId = u.userId and u.isDelete =0 and gu.groupId = ?";
		return super.findInt(hql, groupId);
	}
	
    /**
     * 根据群组ID获取该群组下所有人员ID
     * @param companyId 单位ID
     * @param groupId 部门ID
     * @return 人员ID列表
     */
    public List<Integer> getUserIdsBySetId(Integer companyId,Integer groupId) {
         String hql="select userId from GroupUser where companyId="+companyId+" and groupId="+groupId;
         List<Integer> list=super.entityManager.createQuery(hql).getResultList();
         return list;    
	}
    
    /**
     * 根据groupId删除部门人员关联
     * @param groupId
     */
    public void deleteGroupUserByGroupId(int groupId){
    	List<GroupUser> list = this.findAll(" groupId = ? ",groupId);
		if(list!=null&&list.size()>0){
			for(GroupUser groupUser:list){
				this.delete(groupUser, true);
			}
		}
    }
    
	/**
	 * 根据groupId获得群组列表
	 */
	public List<GroupUser> getSetByGoupIdUndeleteUser(int groupId){
		return this.findAll(" x.groupId = ? and x.userId in (select u.userId from UserInfo u where isDelete = 0)", groupId);
	}
	
	/**
	 * 根据公司id，部门id，用户id获得部门用户关联
	 * @param companyId
	 * @param groupId
	 * @param userId
	 * @return
	 */
	public GroupUser getGroupUser(int companyId,int groupId,int userId){
		return findOne("companyId=?1 and groupId=?2 and userId=?3",companyId,groupId,userId);
	}
	
	/**
	 * 根据公司id和部门id获得部门人员关系列表 
	 * @param companyId
	 * @param groupId
	 * @return
	 */
	public List<GroupUser> getGroupUserList(int companyId,int groupId){
		return this.findAll("groupId=?1 and companyId=?2",groupId,companyId);
	}
	
	/**
	 * 根据群组id删除群组人员关系
	 * @param companyId
	 * @param groupId
	 */
	public void deleteBySetId(int companyId,int setId){
		String hql="delete from  "+this.getEntityClass().getName()+" where  groupId="+setId+" and companyId="+companyId;
        this.entityManager.createQuery(hql).executeUpdate();
	}
	
	/**根据群组ID获取群组下的人员ID
	 * @param groupId
	 * @return
	 */
	public List<Integer> getUserIdsBySetId(int groupId){
		List<Integer> rlist = new ArrayList<Integer>();
		String hql = "groupId = ?";
		List<GroupUser> gulist = super.findAll(hql, groupId);
		for(int i=0; i<gulist.size(); i++){
			rlist.add(gulist.get(i).getUserId());
		}
		return rlist;
	}
}
