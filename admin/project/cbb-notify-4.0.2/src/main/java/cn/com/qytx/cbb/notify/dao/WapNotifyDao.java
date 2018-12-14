package cn.com.qytx.cbb.notify.dao;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import cn.com.qytx.cbb.notify.domain.Notify;
import cn.com.qytx.platform.base.application.TransportUser;
import cn.com.qytx.platform.base.dao.BaseDao;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.PageImpl;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.query.Sort;
import cn.com.qytx.platform.base.query.Sort.Direction;
import cn.com.qytx.platform.base.query.Sort.Order;
import cn.com.qytx.platform.utils.datetime.DateTimeUtil;

@Repository("wapNotifyDao")
public class WapNotifyDao extends BaseDao<Notify,Integer>{
	
	private String dateFormat="yyyy-MM-dd";
	
	public Page<Notify> viewList(Pageable pageable, String subject, int userId,int notifyType,int columnId,Integer readStatus){
		StringBuffer sqlCondition = new StringBuffer(" status = 2 and columnId="+columnId+" and (publishScopeUserIds like '%,"+userId+",%' or publishScopeUserIds like '"+userId+",%')");
		if(notifyType!=0){
			sqlCondition.append(" and notifyType = ").append(notifyType);
		}
		if(subject!=null &&!"".equals(subject)){
			sqlCondition.append(" and subject like '%"+subject+"%'");
		}
		if(readStatus == 1){//已读
			sqlCondition.append(" and id in (select notify.id from NotifyView where createUser.userId = "+userId+")");
		}
		if(readStatus == 2){//未读
			sqlCondition.append(" and id not in (select notify.id from NotifyView where createUser.userId = "+userId+")");
		}
		
		sqlCondition.append(" and ('"+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"'<= endDate or endDate is null)");
		sqlCondition.append(" and ('"+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"'>= beginDate or beginDate is null)");
        return super.unDeleted().findAll(sqlCondition.toString(), pageable);
	}
	/**
	 * 
	 * 功能：部门专栏列表
	 * @param pageable
	 * @param subject
	 * @param userId
	 * @param notifyType
	 * @param columnId
	 * @param groupId
	 * @return
	 */
	public Page<Notify> clmViewList(Pageable pageable, String subject, int userId,int notifyType,int columnId,String groupId){
		String hql = " status=2 and columnId ="+columnId;
		if(subject!=null && !"".equals(subject)){
			hql +=" and suject like '%"+subject+"%'";
		}
		if(groupId != null && !"".equals(groupId)){
			hql +=" and publishGroupId = '"+groupId+"'";
		}
		if(notifyType!=0){
			hql += " and notifyType="+notifyType;
		}
		hql +=" and ( publishScopeUserIds like '%,"+userId+",%' or publishScopeUserIds like '"+userId+",%') ";
		hql +=" and (endDate is null or endDate > '"+DateTimeUtil.getCurrentTime()+"')";
		return super.findAll(hql,pageable);
	}
	public Notify getLastNotify(Integer columnId,Integer notifyType,Integer userId) {
		String hql = "isDelete = 0 and status=2 and columnId = "+columnId +" and notifyType="+notifyType ;
		hql +=" and ( publishScopeUserIds like '%,"+userId+",%' or publishScopeUserIds like '"+userId+",%') ";
		hql +=" and (endDate is null or endDate > '"+DateTimeUtil.getCurrentTime()+"') ";
		hql +=" and ('"+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"'>= beginDate or beginDate is null)";
		Order order = new Order(Direction.DESC, "createDate");
		Sort sort = new Sort(order);
	    List<Notify> list = findAll(hql, sort);
	    if(list != null && list.size() > 0){
	    	return list.get(0);
	    }
		return null;
	}
	public boolean getUnReadNotifyCount(Integer columnId, Integer notifyType,Integer userId) {
		String hql = "isDelete = 0 and status=2 and columnId = "+columnId +" and notifyType="+notifyType ;
		hql +=" and ( publishScopeUserIds like '%,"+userId+",%' or publishScopeUserIds like '"+userId+",%') ";
		hql +=" and (endDate is null or endDate > '"+DateTimeUtil.getCurrentTime()+"')";
		hql +=" and ('"+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"'>= beginDate or beginDate is null)";
		hql += "and id not in (select notify.id from NotifyView where createUser.userId = "+userId+")";
		Integer count = super.count(hql);
		if(count > 0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 功能：获得公告未读数量
	 * @param columnId
	 * @param userId
	 * @param companyId
	 * @return
	 */
	public int getNotifyUnReadCount(int columnId,int userId,int companyId){
		int count = 0;
		try{
			if(columnId!=-1){
				StringBuffer hpql = new StringBuffer(" status = 2 and columnId="+columnId+" and (publishScopeUserIds like '%,"+userId+",%' or publishScopeUserIds like '"+userId+",%')");
				hpql.append(" and ('"+DateTimeUtil.getCurrentTime()+"'< endDate or endDate is null)");
				hpql.append(" and id not in (select notify.id from NotifyView where createUser.userId = "+userId+")");
				count = super.unDeleted().companyId(companyId).count(hpql.toString());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return count;
	}
	
	/**
	 * 获得公告待审批数量
	 */
	public int getNotifyApproveCount(int columnId,int userId,int companyId){
		int count = 0;
		try{
			if(columnId!=-1){
				String hpql = " columnId = ?1 and auditer = ?2 and status = 1 ";
				count = super.unDeleted().companyId(companyId).count(hpql,columnId,userId);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return count;
	}
}
