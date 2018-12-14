package cn.com.qytx.cbb.notify.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.notify.dao.NotifyCommentDao;
import cn.com.qytx.cbb.notify.domain.NotifyComment;
import cn.com.qytx.cbb.notify.service.INotifyComment;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;

@Service("notifyCommentService")
@Transactional
public class NotifyCommentImpl extends BaseServiceImpl<NotifyComment> implements INotifyComment{
	
	@Resource(name="notifyCommentDao")
	private NotifyCommentDao notifyCommentDao;
	
	@Transactional(propagation=Propagation.REQUIRED)
	public Integer countComment(Integer notifyId){
		return notifyCommentDao.countComment(notifyId);
	}
	
	@Transactional(readOnly=true)
	public Page<NotifyComment> pageComment(Pageable pageable,Integer notifyId){
		return notifyCommentDao.pageComment(pageable,notifyId);
	}
}
