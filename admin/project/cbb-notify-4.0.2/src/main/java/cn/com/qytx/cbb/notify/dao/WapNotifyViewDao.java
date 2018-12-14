package cn.com.qytx.cbb.notify.dao;

import org.springframework.stereotype.Repository;

import cn.com.qytx.cbb.notify.domain.NotifyView;
import cn.com.qytx.platform.base.dao.BaseDao;

@Repository("wapNotifyViewDao")
public class WapNotifyViewDao extends BaseDao<NotifyView,Integer>{

	public boolean isReadNotifyView(Integer userId, Integer id) {
		String hql = "createUser.userId=?1 and notify.id=?2";
		Integer count = super.count(hql,userId,id);
		return count>0;
	}

}
