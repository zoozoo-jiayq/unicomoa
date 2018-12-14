package cn.com.qytx.cbb.notify.service;

import cn.com.qytx.cbb.notify.domain.NotifyComment;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.BaseService;

public interface INotifyComment extends BaseService<NotifyComment>{
	
	/**
	 * 当前公告多少条评论
	 * @param notifyId
	 * @return
	 */
	public Integer countComment(Integer notifyId);
	
	/**
	 * 评论分页
	 * @param pageable
	 * @param notifyId
	 */
	public Page<NotifyComment> pageComment(Pageable pageable, Integer notifyId);
	
}
