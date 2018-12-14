package cn.com.qytx.cbb.notify.dao;

import org.springframework.stereotype.Repository;

import cn.com.qytx.cbb.notify.domain.NotifyComment;
import cn.com.qytx.platform.base.dao.BaseDao;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;

/**
 * 功能:评论
 */
@Repository("notifyCommentDao")
public class NotifyCommentDao extends BaseDao<NotifyComment,Integer>{

	public Integer countComment(Integer notifyId) {
		String hql = " notify.id="+notifyId;
		return count(hql);
	}

	public Page<NotifyComment> pageComment(Pageable pageable, Integer notifyId) {
		String hql = "notify.id ="+notifyId;
		return super.findAll(hql,pageable);
	}

}
