package cn.com.qytx.cbb.groupExt.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.com.qytx.platform.base.dao.BaseDao;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.org.dao.GroupDao;
import cn.com.qytx.platform.org.dao.UserDao;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.UserInfo;
@Component
public class GroupExtDao extends BaseDao<UserInfo, Integer>{

	@Autowired
	UserDao<UserInfo> userDao;
	
	@Autowired
	GroupDao<GroupInfo> groupDao;
	
	public void moveOutUserFromGroup(String ids,int groupId){
		
		 String[] arr = ids.split(",");
		 for(String id:arr){
			 if(!id.equals("")){
				 String jpql = "delete GroupUser x where userId = "+Integer.valueOf(id)+" and groupId = "+groupId+"";
				 entityManager.createQuery(jpql).executeUpdate();
			 }
		 }
	}
	
	/**
	 * 根据部门id和是否展示状态获得部门下面的所有的用户
	 * @param page
	 * @param groupId 
	 * @return
	 */
	public Page<UserInfo> getPageUserListByGroupId(Integer companyId,Pageable page,int groupId,String searchName,Integer sex){
		String condation = " isDelete = 0 and companyId="+companyId;
		//根据父部门ID获取所有的子部门
		if(groupId!=0){
			List<GroupInfo>  groupInfoList  = groupDao.getSubGroupList(groupId);
			StringBuffer sb = new StringBuffer(groupId+",");
			String groupIds = "";
			if(groupInfoList!=null&&groupInfoList.size()>0){
				for(GroupInfo groupInfo : groupInfoList){
					if(groupInfo.getGroupId()!=null)
						sb.append(groupInfo.getGroupId()+",");
				}
			}
			if(sb.length()>0){
				groupIds = sb.toString().substring(0,sb.length()-1);
			}
			condation += " and userId in (select gu.userId from GroupUser gu where gu.groupId in ("+groupIds+"))";
		}else{
			condation += " and userId in (select gu.userId from GroupUser gu where gu.groupId in (select f.groupId from GroupInfo f where isDelete = 0 and groupType = 4))";
		}
		if(searchName!=null&&!"".equals(searchName)){
			condation +=" and (userName like '%"+searchName+"%' or phone like '%"+searchName+"%' or vNum like '%"+searchName+"%')";
		}
		if(sex != null){
			condation +=" and sex="+sex;
		}
		return userDao.findAll(condation, page);
	}
}
