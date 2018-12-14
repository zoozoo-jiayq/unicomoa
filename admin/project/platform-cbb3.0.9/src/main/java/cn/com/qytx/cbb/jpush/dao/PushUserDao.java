package cn.com.qytx.cbb.jpush.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.com.qytx.cbb.jpush.domain.PushUser;
import cn.com.qytx.platform.base.dao.BaseDao;
/**
 * 功能:pushUserDao
 * 版本: 1.0
 * 开发人员: zhangjingjing
 * 创建日期: 2014-6-24
 * 修改日期: 2014-6-24
 * 修改列表: 
 */
@Repository("pushUserDao")
public class PushUserDao extends BaseDao<PushUser,Integer> {
	
	/**
	 * 功能：根据推送id获取推送人id
	 * @param pushId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PushUser> findUserByPushId(Integer pushId){
		String hql = "select p from PushUser p where userType=1 and pushId="+pushId;
		return entityManager.createQuery(hql).getResultList();
	}
	public PushUser findByPushIdAndUserId(Integer pushId,Integer userId){
		return findOne("pushId=?1 and userId=?2", pushId,userId);
	}
}