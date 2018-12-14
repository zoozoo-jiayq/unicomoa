package cn.com.qytx.cbb.jbpmApp.dao;

import java.io.Serializable;


import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import cn.com.qytx.cbb.jbpmApp.domain.AttenceVo;
import cn.com.qytx.cbb.jbpmApp.domain.WorkflowLeave;
import cn.com.qytx.platform.base.dao.BaseDao;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.PageImpl;
import cn.com.qytx.platform.base.query.Pageable;

/**
 * 功能：
 * 版本：1.0
 * 开发人员: pb
 * 创建日期：2017年4月12日
 * 修改日期：2017年4月12日	
 */
@Repository("workflowleaveDao")
public class WorkflowLeaveDao extends BaseDao<WorkflowLeave, Integer> implements Serializable{
	private static final long serialVersionUID = -342083156582601586L;
	
	
	/**
	 * 功能：根据instanceId获取请假单
	 * @param instanceId
	 * @return
	 */
	public WorkflowLeave findLeaveByInstanceId(String instanceId){
		return super.findOne(" instanceId=?", instanceId);
	}

	
	
	
	/**
	 * 功能：获取请假列表
	 * @return
	 */
	public List<WorkflowLeave> findLeaveList(Integer userId){
		String hql = " isDelete=0 and isCancel=0 ";
		if(userId!=null){
			hql += " and userId="+userId;
		}
		return super.findAll(hql);
		
	}
	
	
	
	/**
	 * 功能：获取请假列表
	 * @return
	 */
	public List<WorkflowLeave> findLeaveList(String time,Integer companyId){
		String hql = " isDelete=0 and isCancel=0 and state=1 and type=1 and companyId="+companyId;
		if(StringUtils.isNoneBlank(time)){
			hql+=" and startLeaveTime >='"+time+" 00:00:00.000'";
			hql+=" and endLeaveTime <='"+time+" 23:59:59.999'";
		}
		return super.findAll(hql);
		
	}
	
	

   /**
    * 获取在职人数/到岗人数/出勤率
    * @return
    */
	public Map<String,Object> findMapNum(Integer companyId,Integer state,Integer groupId){
		String sql=" select count(*) from tb_user_info  u LEFT JOIN tb_ohr_user_record r ON u.user_id=r.user_id ";
				sql+= " where u.is_delete=0 and r.is_delete=0";
		if(companyId!=null){
			sql+=" and  u.company_id="+companyId;
			sql+=" and  r.company_id="+companyId;
		}
		if(groupId!=null&&groupId!=0){
			sql+=" and  u.group_id="+groupId;
		}
		//在职人数
		int zaizhiNum=(Integer) entityManager.createNativeQuery(sql).getSingleResult();
		
		//请假人数
		sql =" select  count(*) from tb_cbb_leave  where user_id in (select user_id from tb_group_info where 1=1";
		 if(groupId!=null && groupId!=0){
			 sql+=" and group_id="+groupId;
		 }    
		
		sql	+= ") and is_cancel=0 and is_delete=0 and is_remove=0 and DATEDIFF(minute,start_leave_time,GETDATE())>=0";
		
		Integer qingjiaNum=(Integer) super.entityManager.createNativeQuery(sql).getSingleResult();
		Integer daogangNum=zaizhiNum-qingjiaNum;
		if(state!=null){
			if(state==0){
				daogangNum=zaizhiNum-qingjiaNum;
			}else{
				daogangNum=qingjiaNum;
			}
		}
		
		NumberFormat numberFormat = NumberFormat.getInstance();
		numberFormat.setMaximumFractionDigits(2);
		String per= numberFormat.format((float)daogangNum/(float)zaizhiNum*100);
		Map<String,Object> map= new HashMap<String, Object>();
		map.put("zaizhiNum",zaizhiNum);
		map.put("daogangNum",daogangNum);
		map.put("per",per);
		return map;
	}
	/**
	 * 获得考勤详情列表
	 * @return
	 */
	public Page<AttenceVo> getPageList(Pageable pageable, Integer state,Integer groupId){
		String sql=" select userName as userName,groupName as groupName,groupId as groupId,state as state from (";
		sql += " SELECT a.user_name as userName,a.group_name as groupName,a.group_id as groupId,(CASE WHEN aa.user_id is null THEN 0 ELSE 1 END) as state";
		sql +=" from(SELECT u.user_id,u.user_name,u.group_id,g.group_name FROM tb_user_info u";
		sql +=" LEFT JOIN tb_ohr_user_record ohr ON u.user_id = ohr.user_id";
		sql +=" LEFT JOIN tb_group_info g ON u.group_id in(g.path)";
		sql +=" WHERE u.is_delete = 0	AND ohr.is_delete = 0  AND  g.is_delete = 0) a";
		sql +=" LEFT JOIN (SELECT *FROM tb_cbb_leave leave WHERE leave.is_delete = 0 AND leave.is_cancel = 0 AND leave.is_remove = 0";
		sql +=" AND DATEDIFF(MINUTE,leave.start_leave_time,	GETDATE()) > 0) aa on a.user_id=aa.user_id ) s where 1=1";
		if(state!=null){
			sql +=" and s.state="+state;
		}
        if(groupId!=null&&groupId!=0){
        	sql +=" and s.groupId="+groupId;
		}
        sql +=" ORDER BY state desc";
        String sqlCount =" select count(*) from (";
        sqlCount += " SELECT a.user_name as userName,a.group_name as groupName,a.group_id as groupId,(CASE WHEN aa.user_id is null THEN 0 ELSE 1 END) as state";
        sqlCount +=" from(SELECT u.user_id,u.user_name,u.group_id,g.group_name FROM tb_user_info u";
        sqlCount +=" LEFT JOIN tb_ohr_user_record ohr ON u.user_id = ohr.user_id";
        sqlCount +=" LEFT JOIN tb_group_info g ON u.group_id in(g.path)";
        sqlCount +=" WHERE u.is_delete = 0	AND ohr.is_delete = 0  AND  g.is_delete = 0) a";
        sqlCount +=" LEFT JOIN (SELECT * FROM tb_cbb_leave leave WHERE leave.is_delete = 0 AND leave.is_cancel = 0 AND leave.is_remove = 0";
        sqlCount +=" AND DATEDIFF(MINUTE,leave.start_leave_time,	GETDATE()) > 0) aa on a.user_id=aa.user_id )s where 1=1";
		if(state!=null){
			sqlCount +=" and s.state="+state;
		}
        if(groupId!=null){
        	sqlCount +=" and s.groupId="+groupId;
		}
        int count = (Integer) entityManager.createNativeQuery(sqlCount).getSingleResult();
        Query query = super.entityManager.createNativeQuery(sql);
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(AttenceVo.class));
        query.setFirstResult(pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());
        return  new PageImpl(query.getResultList(), pageable, count);
	}




	public List<WorkflowLeave> getWorkflowLeaveList(String startTime,
			String endTime, Integer companyId,Integer userId,Integer type) {
		String hql = " ((startLeaveTime <='"+startTime+" 00:00:00.000' and endLeaveTime >='"+startTime+" 00:00:00.000' )";
		hql += " or ( startLeaveTime<='"+endTime+" 23:59:59.999' and endLeaveTime >= '"+endTime+" 23:59:59.999')";
		hql += " or ( startLeaveTime>='"+startTime+" 00:00:00.000' and endLeaveTime <= '"+endTime+" 23:59:59.999')";
		hql += " or ( startLeaveTime<='"+startTime+" 00:00:00.000' and endLeaveTime >= '"+endTime+" 23:59:59.999')";
		hql += " ) and companyId="+companyId +" and state=1 and isDelete=0 and isCancel=0";
		hql+=" and userId  not in (select u.userId from RoleUser u,RoleInfo r where u.roleId=r.roleId and r.roleCode='leader' AND r.isDelete=0 )";
		if(userId!=null){
			hql += " and userId="+userId;
		}
		if(type!=null){
			hql += " and type="+type;
		}
		return super.findAll(hql);
	}
	

}
