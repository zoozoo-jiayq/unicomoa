package cn.com.qytx.cbb.notify.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.com.qytx.cbb.notify.domain.Notify;
import cn.com.qytx.cbb.notify.domain.NotifyComment;
import cn.com.qytx.platform.base.dao.BaseDao;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.query.Sort;

/**
 * 功能:评论
 */
@Repository("wapNotifyCommentDao")
public class WapNotifyCommentDao extends BaseDao<NotifyComment,Integer>{

	public Page<NotifyComment> commentList(Pageable pageable,Integer notifyId) {
		String hql = "notify.id =?1  and notify.isDelete = 0";
		return super.findAll(hql,pageable,notifyId);
	}
	public List<NotifyComment> getMaxTwoComment(Integer notifyId){
		String hpql = "notify.isDelete = 0 and  notify.id="+notifyId ;
		List<NotifyComment> list = super.findAll(hpql, new Sort(new Sort.Order(Sort.Direction.DESC, "createDate")));
		List<NotifyComment> newList = new ArrayList<NotifyComment>();
		if (list!=null&&list.size()>0) {
			if (list.size()>=2) {
				for (int i = 0; i < 2; i++) {
					newList.add(list.get(i));
				}
			}else {
				newList.add(list.get(0));
			}
		}
		return newList;
	}
}
