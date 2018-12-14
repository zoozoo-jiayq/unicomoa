package cn.com.qytx.cbb.notify.dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
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
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.utils.datetime.DateTimeUtil;
import cn.com.qytx.platform.utils.enumeration.DataFilterType;

/**
 * 功能:分页列表
 */
@Repository("notifyDao")
public class NotifyDao extends BaseDao<Notify,Integer>{
	
	private String dateFormat="yyyy-MM-dd";
	private SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	@SuppressWarnings("unchecked")
	public Page<Notify> viewList(Pageable pageable, int notifyType,String searchWord,Integer columnId,Integer userId,Integer isShowOut) {
		StringBuffer sqlCondition = new StringBuffer(" status = 2 and columnId="+columnId+" and (publishScopeUserIds like '%,"+userId+",%' or publishScopeUserIds like '"+userId+",%')");
		if(notifyType!=0){
			sqlCondition.append(" and notifyType = ").append(notifyType);
		}
		if(searchWord!=null &&!"".equals(searchWord)){
			sqlCondition.append(" and subject like '%"+searchWord+"%'");
		}
		if(isShowOut!=null&&isShowOut==1){
			sqlCondition.append(" and ('"+new SimpleDateFormat(dateFormat).format(new Date())+"'>= beginDate or beginDate is null)");
		}
		sqlCondition.append(" and ('"+new SimpleDateFormat(dateFormat).format(new Date())+"'<= endDate or endDate is null)");
		return super.unDeleted().findAll(sqlCondition.toString(), pageable);
	}

	public Page<Notify> approveList(Pageable pageable,Integer userId, Integer notifyType, String subject,Date beginDate, Date endDate,Integer columnId,Integer status,Integer isDataFilter) {
		//StringBuffer hql = new StringBuffer(" isDelete = 0 and (status=1 or status=2 or status=3) ");
		List<Object> value = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer(" isDelete = 0");
		//hql.append(" and auditer=").append(userId);
		if (status==1) {
			hql.append(" and status=").append(status);
		}
		if (status==2) {
			hql.append(" and (status=2 or status=3)");
		}
		if(notifyType!=null){ 
			hql.append(" and notifyType = ").append(notifyType);
		}
		if(subject!=null && !"".equals(subject)){
			hql.append(" and subject like '%"+subject+"%' ");
		}
		if(beginDate!=null){
			hql.append(" and createDate >= ? ");
			value.add(beginDate);
		}
		if(endDate!=null){
			hql.append(" and createDate < ? ");
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(endDate);
			calendar.add(calendar.DATE, 1);
			value.add(calendar.getTime());
		}
		hql.append(" and columnId = ").append(columnId);
		
		if (isDataFilter==1) {
			if(value.size()<=0){
				return super.dataFilter(DataFilterType.READ.getValue()).findAll(hql.toString(),pageable);
			}else{
				return super.dataFilter(DataFilterType.READ.getValue()).findAll(hql.toString(),pageable,value.toArray());
			}
		}
		if(value.size()<=0){
			return super.companyId().findAll(hql.toString(),pageable);
		}else{
			return super.companyId().findAll(hql.toString(),pageable,value.toArray());
		}
	}
	
	public Page<Notify> list(Pageable pageable, Integer notifyType,String subject, Date beginDate, Date endDate,UserInfo userInfo,Integer columnId,Integer status){
		StringBuffer hql = new StringBuffer(" isDelete = 0 ");
		List<Object> value = new ArrayList<Object>();
		if(notifyType!=null){ 
			hql.append(" and notifyType = ").append(notifyType);
		}
		if(subject!=null && !"".equals(subject)){
			hql.append(" and subject like '%"+subject+"%' ");
		}
		if(beginDate!=null){
			hql.append(" and createDate >= ? ");
			value.add(beginDate);
		}
		if(endDate!=null){
			hql.append(" and createDate < ? ");
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(endDate);
			calendar.add(calendar.DATE,1);
			value.add(calendar.getTime());
		}
		if(userInfo.getIsDefault()!=null && userInfo.getIsDefault()!=0){
			hql.append(" and createUser.userId = ").append(userInfo.getUserId());
		}
		if(status!=null && !"".equals(status)){
			hql.append(" and status = "+status);
		}
		hql.append(" and columnId = ").append(columnId);
		if(value.size()<=0){
			return super.findAll(hql.toString(),pageable);
		}else{
			return super.findAll(hql.toString(),pageable,value.toArray());
		}
	}

	public Integer countOfNotReadNotify(Integer userId,Integer columnId) {
		StringBuffer sqlCondition = new StringBuffer(" status = 2 and isDelete = 0 and companyId="+TransportUser.get().getCompanyId()); 
		if(columnId!=null&&columnId>1){
			  sqlCondition.append( " and columnId="+columnId +" ");
		}
		sqlCondition.append(" and (publishScopeUserIds like '%,"+userId+",%' or publishScopeUserIds like '"+userId+",%')");
		sqlCondition.append(" and  id not in (select notify.id from NotifyView where createUser.userId= '"+userId+"' ) ");
		
		sqlCondition.append(" and ('"+DateTimeUtil.getCurrentTime()+"'< endDate or endDate is null)");
		sqlCondition.append(" and ('"+new SimpleDateFormat(dateFormat).format(new Date())+"'> beginDate or beginDate is null)");
		Integer total = super.count(sqlCondition.toString());
		return total;
	}

	public void delByIds(String ids) {
		String hql = "update Notify set isDelete = 1 where id in ("+ids+")";
		entityManager.createQuery(hql).executeUpdate();
	}

	public void updateTop(String ids, Integer isTop) {
		String hql = "update Notify set isTop = "+isTop+" where id in ("+ids+")";
		entityManager.createQuery(hql).executeUpdate();
	}

	public void updateViewCount(Notify notify) {
		String hql = "update Notify set viewCount=viewCount+1 where id="+notify.getId();
		super.executeQuery(hql);
	}

	public void draw(Integer id) {
		String hql = "update Notify set status=0 where id=?1";
		super.executeQuery(hql,id);
	}

	public void stop(Integer id) {
		String hql = "update Notify set status=4 where id=?1";
		super.executeQuery(hql,id);
	}

	public void effect(Integer id, String startDateStr, String endDateStr) {
		String hql = "update Notify set status=?1,beginDate=?2,endDate=?3 where id =?4";
		try {
			super.executeQuery(hql,2,format.parse(startDateStr),"".equals(endDateStr)?null:format.parse(endDateStr),id);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * 功能：部门专栏列表展示
	 * @param pageable
	 * @param userId
	 * @param notifyType
	 * @param searchWord
	 * @param columnId
	 * @param groupId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Deprecated
	public Page<Map<String,Object>> clmViewList(Pageable pageable,Integer userId, int notifyType,String searchWord,Integer columnId,String groupId) {
		StringBuffer sqlCondition = new StringBuffer("n.status = 2 AND n.is_delete = 0 and columnId="+columnId+" AND (n.publish_user_ids like '%,"+userId+",%' or n.publish_user_ids like '"+userId+",%')");
		if(notifyType!=0){
			sqlCondition.append(" and n.notify_type = ").append(notifyType);
		}
		if(groupId != null && !"".equals(groupId)){
			sqlCondition.append(" and n.publish_group_id = '"+groupId+"'");
		}
		if(searchWord!=null &&!"".equals(searchWord)){
			sqlCondition.append(" and n.subject like '%"+searchWord+"%'");
		}
		sqlCondition.append(" and ('"+DateTimeUtil.getCurrentTime()+"'< n.end_date or n.end_date is null)");
		Object objTotal =  entityManager.createNativeQuery("select count(*) as counting from tb_cbb_notify n where "+sqlCondition).getSingleResult();
		Integer total = 0;
		if(objTotal!=null &&!"".equals(objTotal)){
			total = Integer.parseInt(objTotal.toString());
		}
		String sql = "SELECT n.id as notifyId,n.subject,(select name from tb_cbb_dict where info_type = 'notifyType"+columnId+"' and sys_tag != -1 and value =n.notify_type) as typename,(select user_name from tb_user_info where user_id = n.create_user_id) as username,n.approve_date as approveDate,n.is_top as isTop, (SELECT COUNT (*) FROM tb_cbb_notify_view WHERE create_user_id = "+userId+" AND notify_id = n.id) AS counting, (select count(*) from tb_cbb_notify_view where notify_id = n.id ) as totalCount FROM tb_cbb_notify n WHERE "+sqlCondition.toString()+" ORDER BY is_top desc ,counting asc ,approve_date desc";
        Query query = entityManager.createNativeQuery(sql);
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).setFirstResult(pageable.getOffset()).setMaxResults(pageable.getPageSize());
        List<Map<String,Object>> content = total > pageable.getOffset() ? query.getResultList() : Collections.<Map<String,Object>> emptyList();
        return new PageImpl<Map<String,Object>>(content,pageable,total);
	}

	public List<Notify> getTopFiveNotifys(Integer typeId) {
		String hpql = " notifyType=?1 and status=2";
		List<Notify> list = super.unDeleted().findAll(hpql, new Sort(new Sort.Order(Sort.Direction.DESC, "createDate")),typeId);
		List<Notify> newList = new ArrayList<Notify>();
		if (list!=null&&list.size()>0) {
			if (list.size()>=5) {
				for (int i = 0; i < 5; i++) {
					newList.add(list.get(i));
				}
			}else {
				for (Notify notify : list) {
					newList.add(notify);
				}
			}
		}
		return newList;
	}
	
	public Map<String,Object> viewNotify(Integer notifyId) {
		String sql = "select a.approve_date as approveDate,a.subject,b.user_name as username,a.view_count as viewCount,a.publish_user_ids as publishUserIds ,isnull(c.counting,0) as counting from tb_cbb_notify a  left join tb_user_info b on a.create_user_id = b.user_id left join (select notify_id as notifyId,count(DISTINCT(create_user_id)) as counting from tb_cbb_notify_view where notify_id = "+notifyId+"  group by notify_id ) c on a.id = c.notifyId where a.id = "+notifyId;
		List<Map<String, Object>> list = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		if (list!=null&&list.size()>0) {
			return list.get(0);
		}else {
			return null;
		}
	}
	public Notify loadNotify(Integer notifyId){
		return super.findOne(notifyId);
	}
	
	@Override
	public Notify saveOrUpdate(Notify entity) {
		entity.setIsForkGroup(0);
		return super.saveOrUpdate(entity);
	}


	/*@Override
	public BaseDao<Notify, Integer> dataFilter(String dataFilterType) {
		super.dataFilter(dataFilterType);
		return this;
	}*/
	
}
