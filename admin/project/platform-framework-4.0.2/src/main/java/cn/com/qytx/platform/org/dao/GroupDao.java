package cn.com.qytx.platform.org.dao;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import cn.com.qytx.platform.base.dao.BaseDao;
import cn.com.qytx.platform.base.query.Sort;
import cn.com.qytx.platform.base.query.Sort.Direction;
import cn.com.qytx.platform.base.query.Sort.Order;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.utils.datetime.DateTimeUtil;

/**
 * 部门/群组数据库操作类
 * User: 黄普友
 * Date: 13-2-20
 * Time: 下午3:36
 */
@Repository("groupDao")
public class GroupDao <T extends GroupInfo>  extends BaseDao<GroupInfo,Integer> implements Serializable{

    /**
	 * 描述含义
	 */
	private static final long serialVersionUID = 1L;

    /**
     * 获取部门/群组列表
     * @param companyId 单位ID
     * @param userId 如果参数needUserGroup为true，查询结果需要包含userId所在的个人群组和公共群组
     * @param lastUpdateTime 上次更新时间，为null 表示所有数据
     * @param needUserGroup 是否需要群组信息，true包含群组；false不包含群组
     * @return 指定单位的部门/群组列表 
     */
	@Deprecated
    public List<GroupInfo> getGroupList(Integer companyId,Integer userId,Date lastUpdateTime,boolean needUserGroup)
    {
        String timeHql="";
        if(lastUpdateTime!=null)
        {
            //查询所有时间数据
            timeHql=" and  g.lastUpdateTime>='"+DateTimeUtil.dateToString(lastUpdateTime,"yyyy-MM-dd HH:mm:ss")+"'";
        }

        //企业通讯录,公共通讯录
         String hql="select g from GroupInfo g where isDelete=0 and g.companyId="+companyId+" and g.groupType in(1,2) "+timeHql+" order by g.orderIndex asc ";
         if(needUserGroup)
         {
             //个人群组
            hql+=" union ";
            hql+=" select g from GroupInfo g where isDelete=0 and  g.companyId="+companyId+" and g.userId="+userId+" and g.groupType =5 "+timeHql+" order by g.orderIndex asc ";
             //公共群组
            hql+=" union ";
            hql+=" select g from GroupInfo g where isDelete=0 and  g.companyId="+companyId+" and g.groupType =4 "+timeHql+" order by g.orderIndex asc ";
         }
        //私人通讯录
        hql+=" union ";
        hql+=" select g from GroupInfo g where isDelete=0 and g.companyId="+companyId+" and g.userId="+userId+" and g.groupType =3 "+timeHql+" order by g.orderIndex asc , g.groupId asc ";

        List<GroupInfo> list=super.entityManager.createQuery(hql).getResultList();
        return list;
    }
	
    /**
     * 获取部门/群组列表
     * @param companyId 单位ID
     * @param userId 如果参数needUserGroup为true，查询结果需要包含userId所在的个人群组和公共群组
     * @param lastUpdateTime 上次更新时间，为null 表示所有数据
     * @param needUserGroup 是否需要群组信息，true包含群组；false不包含群组
     * @return 指定单位的部门/群组列表 
     */
    public List<GroupInfo> getGroupListChanged(Integer companyId,Integer userId,Date lastUpdateTime,boolean needUserGroup)
    {
        String timeHql="";
        String hql="";
        if(lastUpdateTime!=null)
        {
            //查询所有时间数据
            timeHql=" and  lastUpdateTime>='"+DateTimeUtil.dateToString(lastUpdateTime,"yyyy-MM-dd HH:mm:ss")+"'";
             hql="select g from GroupInfo g where companyId="+companyId+" and ( groupType in(1,2) ";
        }

        //企业通讯录,公共通讯录
        else {
        	hql="select g from GroupInfo g where isDelete=0 and companyId="+companyId+" and ( groupType in(1,2) ";
		}
         if(needUserGroup)
         {
             //个人群组
            hql+=" or ";
            hql+=" ( userId="+userId+" and groupType =5 ) ";
             //公共群组
            hql+=" or ";
            hql+=" ( groupId in (select a.groupId from GroupInfo a ,GroupUser b where a.companyId = "+companyId+" and a.groupType = 4 and a.isDelete = 0 and a.groupId = b.groupId and b.userId = "+userId+" ) ) ";
         }
        //私人通讯录
        hql+=" or ";
        hql+=" ( userId="+userId+" and groupType =3 )) "+timeHql+" order by groupType asc, orderIndex asc , groupId asc ";

        List<GroupInfo> list=super.entityManager.createQuery(hql).getResultList();
        return list;
    }
    
	/**
     * 获取某个部门下面的子部门数量
     * @param companyId 单位ID
     * @param groupId 部门ID
     */
    public int checkExistsChildGroup(int companyId,int groupId)
    {
         String hql="isDelete=0  and parentId="+groupId+" and companyId="+companyId;
         return this.count(hql);
    }

    /**
     * 获取部门/群组列表
     * @param companyId  企业ID
     * @param groupType  部门类型: 1.公共部门 2.公共群组 3.外部部门 4.个人群组
     * @return
     */
    @SuppressWarnings("unchecked")
	public List<GroupInfo> getGroupList(int companyId,int groupType)
    {
        //根据条件获取未删除的部门/群组列表
         String hql="companyId=? and groupType=? and isDelete=0 ";
         Order o = new Order(Direction.ASC,"orderIndex");
         Order o1 = new Order(Direction.ASC,"groupId");
     	 Sort sort = new Sort(o,o1);
         return this.findAll(hql,sort,companyId,groupType);
    }

    /**
     * 获取个人创建的部门/群组列表
     * @param companyId  企业ID
     * @param groupType  部门类型: 1.公共部门 2.公共群组 3.外部部门 4.个人群组
     * @return
     */
    @SuppressWarnings("unchecked")
	public List<GroupInfo>  getGroupListByCreater(int companyId,int groupType,int userId)
    {
        String hql="companyId=? and groupType=? and isDelete=0 and userId=? ";
        Order o = new Order(Direction.ASC,"orderIndex");
        Sort s = new Sort(o);
        return this.findAll(hql,s,companyId,groupType,userId);
    }
    
    /**
     * 根据部门分机号码和公司id获取部门/群组
     * @param companyId 单位ID
     * @param groupCode 部门code
     * @return
     */
    @SuppressWarnings("unchecked")
	public List<GroupInfo> getGroupListByGroupCode(int companyId,String groupCode)
    {
        String hql="companyId=? and groupCode = ? and isDelete=0 ";
        Order o = new Order(Direction.ASC,"orderIndex");
        Order o1 = new Order(Direction.ASC,"groupId");
        Sort s = new Sort(o,o1);
        return this.findAll(hql,s,companyId,groupCode);
    }
    
    /**
     * 功能：获取用户的部门结构
     * 作者：jiayongqiang
     * 参数：
     * 输出：
     */
    public  String getGroupNamePath(int companyId,String groupIdPath) {
        if(groupIdPath!=null){
            String hqlAll=" companyId=?  and isDelete=0 and groupId in ("+groupIdPath+")";
            Order o = new Order(Direction.ASC,"grade");
            Sort sort = new Sort(o);
            List<GroupInfo> groupList = super.findAll(hqlAll,sort, companyId);
            StringBuffer pathSb = new StringBuffer();
            for(GroupInfo group : groupList){
                if (null != group){
                    pathSb.append(group.getGroupName()+"\\");
                }
            }
            if (pathSb.length() > 0){
                return pathSb.substring(0, pathSb.length()-1);
            }
        }
        return "";
    }
    
	/**
	 * 根据父ID子部门列表
	 * @param parentGroupId 父部门ID
	 * @return 子部门列表
	 */
	public List<GroupInfo> getGroupInfoByParentId(int parentGroupId){
		String hql = " isDelete = 0 and parentId = ? ";
		return super.findAll(hql,new Sort(Direction.ASC, "orderIndex") ,parentGroupId);
	}
	
    /**
     * 功能： 根据部门的id获得下面的子部门的内容
     * @param groupId
     * @param groupType
     * @param companyId
     * @return
     */
    public List<GroupInfo> getGroupInfoByParentId(int groupId,int groupType,int companyId){
    	String condition = " isDelete = 0 and parentId = ? and groupType = ? and companyId = ? order by orderIndex asc , groupId asc ";
		return this.findAll(condition, groupId,groupType,companyId);
    }

	
    /**
     * 功能：根据部门名称得到部门(级联部门以"\"分割)
     * @param companyId 公司id
     * @param groupPathName 部门名称
     * @return 部门
     */
    public GroupInfo loadGroupByPathName(Integer companyId,String groupPathName)
    {
        if (!StringUtils.isEmpty(groupPathName)){
            // 按\拆分群组关系
            String[] groupArr = groupPathName.split("\\\\");
            Integer parentId = 0;
            GroupInfo parentGroup = null;
            for(int i=0; i<groupArr.length; i++)
            {
                // 查询
                String hql=" companyId=?  and isDelete=0 and parentId = ? and groupName = ? and groupType = ?";
                parentGroup = super.findOne(hql, companyId, parentId, groupArr[i],GroupInfo.DEPT);
                if (parentGroup != null){
                    parentId = parentGroup.getGroupId();
                    continue;
                }else{
                    return null;
                }
            }
            return parentGroup;
        }
        return null;
    }
	
    
	/**
	 * 在指定部门下面是否有相同的部门名称
	 * @param parentId 父部门ID
	 * @param groupName 部门名称
	 * @param groupType 部门类型
	 * @param companyId 单位ID
	 * @return true重复，false不重复
	 */
	public boolean isHasSameGroupName(Integer parentId,String groupName,int groupType,int companyId){
		String sql="parentId=? and groupName=?  and isDelete=0 and groupType = ? and companyId = ?";
		Object object=super.findOne(sql, parentId,groupName,groupType,companyId);
		if(object==null){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * 判断指定部门是否有子部门，true有，false没有
	 * @param groupId 父部门ID
	 * @return true有，false没有
	 */
	public boolean isHasChild(Integer groupId){
		String sql=" parentId=? and isDelete=0";
		Object object=super.findOne(sql, groupId);
		if(object==null){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * 返回部门结构树
	 * @param companyId 单位ID
	 * @param groupType 部门类型
	 * @return 部门列表
	 */
	public List<GroupInfo> findGradeGroupTree(Integer companyId,Integer groupType) {
		String hql = " isDelete=0 and companyId=? and groupType=?";
		List<GroupInfo> allGroupList= super.findAll(hql,new Sort(Direction.ASC, "orderIndex","groupId"),companyId,groupType);
		if(allGroupList!=null&&allGroupList.size()>0){
			return recursivGroupTreeList(allGroupList, null, null);
		}else{
			return null;
		}
	}
	
	/**
	 * 
	 * 功能：得到分类树
	 * @param allGroupList
	 * @param g
	 * @param temp
	 * @return 部门树列表
	 */
	private List<GroupInfo> recursivGroupTreeList(List<GroupInfo> allGroupList, GroupInfo g, List<GroupInfo> templ) {
		if (templ == null) {
			templ = new ArrayList<GroupInfo>();
		}
		for (GroupInfo group : allGroupList) {
			Integer parentId = group.getParentId();
			if(parentId!=null){
				boolean bOne=g == null && parentId.intValue()==0;
				boolean bTwo=g != null && ((g.getGroupId()).intValue()==parentId.intValue());
				if (bOne||bTwo) {
					templ.add(group);
					recursivGroupTreeList(allGroupList, group, templ);
				}
			}
		}
		return templ;
	}
    /**
     * 获取指定部门的子部门的最大排序号
     * @param companyId 单位ID
     * @param parentId 部门ID
     * @param groupType 部门类型
     * @return 返回最大排序号
     */
    public Integer getMaxOrderIndex(Integer companyId,Integer parentId,int groupType)
    {
    	String hql="select t from GroupInfo t where groupType= "+groupType+" and isDelete=0 and companyId="+companyId+" and parentId="+parentId+" order by orderIndex desc";
        List<GroupInfo> list=this.entityManager.createQuery(hql).getResultList();
        if(list!=null && list.size()>0)
        {
            Integer order = list.get(0).getOrderIndex();
            if (order==null) {
				return 0;
			}else {
				return order;
			}
        }
        return 0;
    }
    /**
     * 获取指定范围内排序号最大的部门
     * @param companyId 单位ID
     * @param parentId 父部门ID
     * @param top 查询范围
     * @return 返回部门对象
     */
    public GroupInfo getSortOrderGroup(Integer companyId,Integer parentId,Integer groupId,Integer top)
    {
        //首先按照orderIndex正序排列
        String hql="select t from GroupInfo t where t.groupType=1 and  t.companyId="+companyId+" and t.parentId="+parentId+" order by  t.orderIndex asc";
        List<GroupInfo> list= super.entityManager.createQuery(hql).setMaxResults(top).getResultList();
        if(list!=null&&list.size()>0)
        {
            GroupInfo groupInfo=list.get(list.size()-1);//获取最后一个

            return groupInfo;
        }
        return null;
    }
    /**
     * 同一个父部门下的所有子部门更新排序号，如果startOrder大于endOrder,部门排序号减1，否则加1
     * @param comanyId 单位ID
     * @param parentId 父部门ID
     * @param startOrder 起始范围
     * @param endOrder 结束范围
     */
    public void sortOrder(Integer comanyId,Integer parentId,Integer startOrder,Integer endOrder)
    {
        String hql="";
        if(startOrder<endOrder)
        {
            hql="update GroupInfo set orderIndex=orderIndex-1, lastUpdateTime='"+ DateTimeUtil.getCurrentTime()+"' where  groupType=1 and companyId="+comanyId+" and parentId="+ parentId+" and orderIndex>"+startOrder+" and orderIndex<="+endOrder;
        }
        else
        {
            hql="update GroupInfo set orderIndex=orderIndex+1, lastUpdateTime='"+ DateTimeUtil.getCurrentTime()+"' where groupType=1 and  companyId="+comanyId+" and parentId="+ parentId+" and orderIndex>="+endOrder+" and orderIndex<"+startOrder;
        }
        this.entityManager.createQuery(hql).executeUpdate();
    }
    
    
    /**
     * 功能：根据公司 部门ids获取部门
     * @param companyId 单位ID
     * @param groupIds 部门ID集合，多个ID间用","隔开
     * @return
     */
    public List<GroupInfo> getGroupListByIds(Integer companyId,String groupIds){
    	List<Integer> ids =new ArrayList<Integer>();
    	String[] idStrs = groupIds.split(",");
    	for(String id:idStrs){
    		if (!id.equals("")) {
    			ids.add(Integer.parseInt(id));
			}
    	}
    	if(ids.size()>0){
    		return findAll("companyId=?1 and  groupId in (?2)", companyId,ids);
    	}else{
    		return new ArrayList<GroupInfo>();
    	}
    }
   
    
    /**
     * 功能：更新部门排序 新增部门或修改部门时重新排序(同一父部门内)
     * @param groupInfo  部门对象
     * @return
     */
    public int updateGroupOrder(GroupInfo groupInfo){
    	if (null!=findOne("orderIndex=?1 and parentId=?2 and companyId=?3 and groupId<>?4",groupInfo.getOrderIndex(),groupInfo.getParentId(),groupInfo.getCompanyId(),groupInfo.getGroupId())) {
    		String hql="update GroupInfo set orderIndex=orderIndex+1 where isDelete=0 and groupType="+groupInfo.getGroupType()+" and orderIndex>="+groupInfo.getOrderIndex()+" and groupId<>"+groupInfo.getGroupId()+" and parentId="+groupInfo.getParentId()+" and companyId="+groupInfo.getCompanyId();
    		String updateLastUpdateTimeHql="update GroupInfo set lastUpdateTime = '"+ DateTimeUtil.getCurrentTime()+"' where isDelete=0 and groupType="+groupInfo.getGroupType()+" and orderIndex>="+groupInfo.getOrderIndex()+" and groupId<>"+groupInfo.getGroupId()+" and parentId="+groupInfo.getParentId()+" and companyId="+groupInfo.getCompanyId();
    		executeQuery(updateLastUpdateTimeHql);
        	return executeQuery(hql);
		};
    	return 0;
    }
    
    /**
     * 功能：部门上移 下移操作 0上移 1 下移
     * @param groupInfo 部门对象
     * @param value 操作类型，0上移  1下移
     * @return
     */
    public int upOrDownOrder(GroupInfo groupInfo,Integer value){
    	List<GroupInfo> uList = new ArrayList<GroupInfo>();
    	if (value==0) {
    		uList = entityManager.createQuery("from GroupInfo where companyId="+groupInfo.getCompanyId()+" and parentId="+groupInfo.getParentId()+" and orderIndex <"+groupInfo.getOrderIndex()+" order by orderIndex desc").getResultList();
		}else {
			uList = entityManager.createQuery("from GroupInfo where companyId="+groupInfo.getCompanyId()+" and parentId="+groupInfo.getParentId()+" and orderIndex >"+groupInfo.getOrderIndex()+" order by orderIndex asc").getResultList();
		}
    	if (null!=uList&&uList.size()>0) {
    		GroupInfo group=uList.get(0);
			int orderIndex=group.getOrderIndex();
			group.setOrderIndex(groupInfo.getOrderIndex());
			groupInfo.setOrderIndex(orderIndex);
			saveOrUpdate(group);
			saveOrUpdate(groupInfo);
			return 1;
		}
    	return 0;
    }
    
    /**
	 * 功能：得到公司部门人员数map
	 * @param companyId
	 * @return Map,key值是部门ID,value是部门人员数
	 */
	public Map<Integer,Integer> getCompanyGroupCountMap(Integer companyId){
		Map<Integer,Integer> map=new HashMap<Integer, Integer>();
		String sql="select group_id,count(*) as num from tb_user_info where  is_delete=0 and  company_id="+companyId+"   group by group_id";
		Query query=super.entityManager.createNativeQuery(sql);
		List list=query.getResultList();
		if(list!=null&&list.size()>0){
			for (Object object : list) {
				Object[] groupArr=(Object[])object;
				Integer groupId=0;
				Integer userNum=0;
				if(groupArr[0]!=null){
					groupId=Integer.valueOf(groupArr[0].toString());
				}
				if(groupArr[1]!=null){
					userNum=Integer.valueOf(groupArr[1].toString());
				}
				map.put(groupId, userNum);
			}
		}
		return map;
	}
	
	/**
	 * 修改指定的部门的最后一次更新时间
	 * @param groupId  部门ID
	 */
	public void updateGroupTime(int groupId){
		String sql="update GroupInfo set lastUpdateTime=? where groupId = "+groupId+" ";
		Date currentTime = new Date();
		this.entityManager.createQuery(sql).setParameter(1, currentTime).executeUpdate();
	}
	
	/**
	 * 
	 * 更新与指定人员相关部门的最后一次更新时间
	 * @param companyId 单位ID
	 * @param ids 人员ID集合，用","隔开
	 */
	public void updateGroupsByUserIds(int companyId, String ids) {
		String sql="update GroupInfo set lastUpdateTime=? where groupType in (1,2) and companyId="+companyId+" and groupId in (select groupId from UserInfo where companyId="+companyId+" and userId in ("+ids+"))";
		Date currentTime = new Date();
		this.entityManager.createQuery(sql).setParameter(1, currentTime).executeUpdate();
	}
	
	/**根据用户获取所有的部门列表
	 * @param companyId
	 * @param userIds
	 * @return
	 */
	public List<GroupInfo> getGroupsByUserIds(int companyId, String userIds) {
		if(userIds == null || userIds.equals("")){
			return new ArrayList<GroupInfo>();
		}else{
			String hql = "groupId in (select groupId from UserInfo where userId in ("+userIds+") and companyId = "+companyId+")";
			return super.findAll(hql);
		}
	}
	
	/**
	 * 根据部门类型获得公司下部门 
	 * @param companyId
	 * @param groupType
	 * @param userId
	 * @return
	 */
	public List<GroupInfo> getGroupListByType(int companyId,int groupType,int userId){
		String condition = " companyId=? and groupType =? and isDelete=? ";
		if(userId!=-1){
			condition += " and userId = "+userId+" ";
		}
		Order o1 = new Order(Direction.ASC,"orderIndex");
        Order o2 = new Order(Direction.ASC,"groupId");
    	Sort sort = new Sort(o1,o2);
		return this.findAll(condition,sort,companyId,groupType,0);
	}
	
	
	public List<GroupInfo> getSubGroupList(int parentGroupId) {
		List<GroupInfo> result = new ArrayList<GroupInfo>();
		iteratorSubGroup(parentGroupId, result);
		return result;
	}
	

	/**
	 * 功能：迭代获取子部门
	 * @param
	 * @return
	 * @throws   
	 */
	private void iteratorSubGroup(int parentGroupId,List<GroupInfo> result){
		List<GroupInfo> temp = this.getGroupInfoByParentId(parentGroupId);
		if(temp!=null){
			result.addAll(temp);
			for(int i=0; i<temp.size(); i++){
				GroupInfo gi = temp.get(i);
				int gid = gi.getGroupId();
				iteratorSubGroup(gid, result);
			}
		}else{
			return;
		}
	}
	
	/**
	 * 根据部门类型获得部门的信息，用于手机端接口
	 * @param groupType
	 * @return
	 */
	public GroupInfo getGroupInfoByTypeAndUserId(int groupType,int companyId,String groupName,int userId){
		GroupInfo groupInfo = null;
		String hql="";
		//如果是公共群组，企业通讯录，公共群组 名称不能重复
        if(groupType==1 || groupType==2 || groupType==4)
        {
            hql="companyId=?1 and groupName=?2";
            groupInfo=this.findOne(hql,companyId,groupName);
        }
        //如果是私人通讯录，个人群组 名称不能重复
        else if(groupType==3||groupType==5)
        {
            hql="companyId=?1 and groupName=?2 and userId=?3";
            groupInfo=this.findOne(hql,companyId,groupName,userId);
        }
        
        return groupInfo;
	}

	/**
	 * 获得公共的部门/群组
	 * @param companyId
	 * @param groupName
	 * @return
	 */
	public GroupInfo getPublicGroupInfo(int companyId,String groupName){
        //公共的部门/群组
        String hql="companyId=?1 and groupName=?2 and groupType in (1,2,4)";
        return this.findOne(hql,companyId,groupName);
	}
	
	/**
	 * 根据部门的级别查找部门列表
	 * @param companyId
	 * @return
	 */
	public List<GroupInfo> findGroupListByGrade(int grade){
		String hql = "grade = ? and groupType = 1";
		return super.unDeleted().findAll(hql, grade);
	}
	
	/**查找指定部门的fork_group
	 * @param groupId
	 * @return
	 */
	public Integer getForkGroup(int groupId){
		String hql = "select isForkGroup from GroupInfo where groupId = ?";
		return super.findInt(hql, groupId);
	}
	
	/** 将原来的oldforkGroup，更新为0
	 * @param oldForkGroup
	 */
	public void updateForkGroup(int oldForkGroup,int companyId){
		String hql = "update GroupInfo set isForkGroup = ? where isForkGroup = ? and companyId = ?";
		super.bulkUpdate(hql, 0,oldForkGroup,companyId);
	}
	
	public List<GroupInfo> findForkGroupList(){
		String hql = "groupId = isForkGroup";
		return super.unDeleted().findAll(hql);
	}
	
	public Timestamp getLastUpdateNew(int companyId){
		String hql = "select max(lastUpdateTime) as lastUpdateTime from  GroupInfo gi  where  companyId = "+companyId;
		List list= this.entityManager.createQuery(hql).getResultList();
		if(list.size()>0){
			return (Timestamp) list.get(0);
		}else{
			return null;
		}
	}
	
    /**
     * 获得今天所有的部门修改
     * @param companyId
     * @return
     */
    public List<GroupInfo> getTodayGroupListChanged(Integer companyId)
    {
        String timeHql="";
        String hql="";
        //查询所有时间数据
        timeHql=" and  lastUpdateTime>='"+DateTimeUtil.dateToString(new Date(),"yyyy-MM-dd")+" 00:00:00'";
         hql="select g from GroupInfo g where companyId="+companyId+" and groupType in (1,2,3,4,5) ";
        
        hql+=timeHql+" order by groupType asc, orderIndex asc , groupId asc ";

        List<GroupInfo> list=super.entityManager.createQuery(hql).getResultList();
        return list;
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
        boolean needUserGroup=true;
        String timeHql="";
        if(lastUpdateTime!=null)
        {
            //查询所有时间数据
            timeHql=" and  g.lastUpdateTime>='"+lastUpdateTime+"'";
        }

        //企业通讯录,公共通讯录
        String hql="select  g from GroupInfo g where isDelete=0 and g.companyId="+companyId+" and g.groupType in(1,2) "+timeHql+" order by g.orderIndex asc ";
        if(needUserGroup)
        {
            //个人群组
            hql+=" union ";
            hql+=" select g from GroupInfo g where isDelete=0 and  g.companyId="+companyId+" and g.userId="+userId+" and g.groupType =5 "+timeHql+" order by g.orderIndex asc ";
            //公共群组
            hql+=" union ";
            hql+=" select g from GroupInfo g where isDelete=0 and  g.companyId="+companyId+" and g.groupType =4 "+timeHql+" order by g.orderIndex asc ";
        }
        //私人通讯录
        hql+=" union ";
        hql+=" select g from GroupInfo g where isDelete=0 and g.companyId="+companyId+" and g.userId="+userId+" and g.groupType =3 "+timeHql+" order by g.orderIndex asc , g.groupId asc ";

        List<GroupInfo> list=super.entityManager.createQuery(hql).getResultList();
        return list;
    }
    
    /**
     * SELECT * FROM tb_group_info g LEFT JOIN (SELECT u.group_id AS uid,COUNT(u.user_id) AS cou FROM tb_user_info AS u WHERE company_id=31146 AND is_delete=0 GROUP BY uid ) AS v ON g.group_id = v.uid WHERE g.company_id = 31146
     * 根据父部门ID和部门明恒关键字查询部门ID、名字、人数列表
     * @param parentId
     * @param key
     * @return
     */
    public List<Map<String,Object>> findGroupListByParentIdAndGroupkey(Integer companyId,Integer parentId,String key,String gids){
    	List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
    	String hql = "SELECT g.group_id as gid,g.group_name as gname,g.parent_id as pid,v.cou AS c,g.path as path FROM tb_group_info as g left join (SELECT u.group_id AS uid,COUNT(u.user_id) AS cou FROM tb_user_info AS u WHERE company_id="+companyId+" AND is_delete=0 GROUP BY u.group_id ) AS v on g.group_id = v.uid where g.is_delete = 0 and g.group_type = 1 ";
    	if(parentId!=null && parentId>0){
    		hql += " and g.parent_id = "+parentId;
    	}
    	if(key!=null && !key.equals("")){
    		hql+=" and g.group_name like "+"'%"+key+"%'";
    	}
    	if(gids!=null && !gids.equals("")){
    		hql+=" and g.group_id in ("+gids+")";
    	}
    	hql+=" and g.company_id = "+companyId;
    	hql+=" order by g.grade,g.order_index asc";
    	List<Object[]> rs = super.entityManager.createNativeQuery(hql).getResultList();
    	if(rs!=null){
    		for(int i=0; i<rs.size(); i++){
    			Object[] temp = rs.get(i);
    			Map<String,Object> map = new HashMap<String, Object>();
    			map.put("gid", temp[0]);
    			map.put("gname", temp[1]==null?"":temp[1]);
    			map.put("pid", temp[2]==null?0:temp[2]);
    			map.put("c", temp[3]==null?0:temp[3]);
    			map.put("path", temp[4]==null?"":temp[4]);
    			list.add(map);
    		}
    	}
    	return list;
    }
    /**
	 * 在指定ID下面是否有相同的部门名称
	 * 功能：parentId
	 * @param parentId
	 * @param groupName
	 * @return
	 */
	public boolean isHasSameGroupNameForSet(Integer parentId,String groupName,int groupType,int companyId,int userId){
		String sql="parentId=? and groupName=?  and isDelete=0 and groupType = ? and companyId = ? and userId=? ";
		Object object=super.findOne(sql, parentId,groupName,groupType,companyId,userId);
		if(object==null){
			return false;
		}else{
			return true;
		}
	}
}
