package cn.com.qytx.cbb.notify.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import cn.com.qytx.cbb.notify.domain.Notify;
import cn.com.qytx.cbb.notify.domain.NotifyView;
import cn.com.qytx.platform.base.dao.BaseDao;

@Repository("notifyViewDao")
public class NotifyViewDao extends BaseDao<NotifyView,Integer>{

	public boolean getNotifyView(Integer createUserId, Integer notifyId) {
		String hql = "createUser.userId =?1 and notify.id = ?2";
		NotifyView notifyView = findOne(hql,createUserId,notifyId);
		if(notifyView == null){
			return true;
		}
		return false;
	}

	public Integer countNotifyPeoples(Integer id) {
		String hql = "select count(DISTINCT(create_user_id)) as counting from tb_cbb_notify_view  where notify_id = "+id;
		return Integer.valueOf(entityManager.createNativeQuery(hql).getSingleResult().toString());
	}
	public List<Map<String,Object>> getMapPublishUsers(List<Integer> ids){
		String sql = "select a.user_id,a.user_name,b.group_id ,b.group_name  from tb_user_info a left join tb_group_info b  on a.group_id = b.group_id where a.user_id in (?1) order by b.grade asc ,  b.order_index asc";
		return entityManager.createNativeQuery(sql).setParameter(1,ids).unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	public List<NotifyView> mobileRead(Integer notifyId) {
		String sql = "SELECT * FROM tb_cbb_notify_view a WHERE NOT EXISTS ( SELECT * FROM tb_cbb_notify_view b WHERE a.create_user_id = b.create_user_id AND a.create_time> b.create_time and notify_id = "+notifyId+" ) and notify_id= "+notifyId+" ORDER BY a.create_time desc ";
		return entityManager.createNativeQuery(sql,NotifyView.class).getResultList();
	}
	/**
	 * 获得发布范围内的人员信息
	 * @param notify
	 * @return
	 */
	public List<Map<String, Object>> viewList(Notify notify) {
		List<Integer> list = new ArrayList<Integer>();
		String[] userIds = notify.getPublishScopeUserIds().split(",");
		for(String str:userIds){
			if(str!=null&&!"".equals(str)){
				list.add(Integer.parseInt(str));
			}
		}
		String sql ="select a.user_id as userId,a.user_name as userName,b.group_id as groupId,b.group_name as groupName,c.counting from tb_user_info  a left join tb_group_info b on a.group_id = b.group_id left join (select b.user_id ,count(*) as counting from tb_cbb_notify_view a left join tb_user_info b on a.create_user_id = b.user_id where a.notify_id = "+notify.getId()+" group by user_id  )  c on a.user_id = c.user_id where a.USER_ID in (?1) order by b.grade asc ,  b.order_index asc ";
//		return super.findReturnMapList(sql,list);
		return entityManager.createNativeQuery(sql).setParameter(1,list).unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}
	/**
	 * 获取公告的查看人数
	 * @param companyId
	 * @return
	 */
	public Map<Integer,Integer> getViewUserNumByNotigyId(String notifyIds,Integer userId){
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		if (StringUtils.isNotBlank(notifyIds)) {
			if (notifyIds.startsWith(",")) {
				notifyIds = notifyIds.substring(1);
			}
			if (notifyIds.endsWith(",")) {
				notifyIds = notifyIds.substring(0, notifyIds.length()-1);
			}
			String hql = "select notify.id,COUNT(*) from NotifyView where notify.id in ("+notifyIds+") ";
			if (userId!=null) {
				hql += " and createUser.userId="+userId;
			}
					hql += " group by notify.id";
			List<Object> list = super.find(hql);
			for(int i=0; i<list.size(); i++){
				Object[] obj = (Object[])list.get(i);
				Integer key = Integer.parseInt(obj[0].toString());
				Integer count = Integer.parseInt(obj[1].toString());
				map.put(key, count);
			}
		}
		
		return map;
	}
}
