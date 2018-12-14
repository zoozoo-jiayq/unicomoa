package cn.com.qytx.cbb.notify.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.notify.dao.WapNotifyCommentDao;
import cn.com.qytx.cbb.notify.domain.NotifyComment;
import cn.com.qytx.cbb.notify.service.IWapNotifyComment;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;

@Service("wapNotifyCommentImpl")
@Transactional
public class WapNotifyCommentImpl extends BaseServiceImpl<NotifyComment> implements IWapNotifyComment{
		
	@Resource(name="wapNotifyCommentDao")
	private WapNotifyCommentDao wapNotifyCommentDao;
	/**
	 * 评论列表
	 */
	@Transactional(readOnly=true)
	public Page<NotifyComment> commentList(Pageable pageable,Integer notifyId){
		return wapNotifyCommentDao.commentList(pageable,notifyId);
	}
	@Transactional(readOnly=true)
	public List<NotifyComment> getMaxTwoComment(Integer notifyId){
		return wapNotifyCommentDao.getMaxTwoComment(notifyId);
	}
}	
