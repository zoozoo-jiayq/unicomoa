package cn.com.qytx.cbb.notify.service;

import java.util.List;

import cn.com.qytx.cbb.notify.domain.NotifyComment;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.BaseService;

public interface IWapNotifyComment extends BaseService<NotifyComment>{
	
	/**
	 * 查询评论功能列表
	 * @param pageable
	 * @param notifyId
	 * @return
	 */
	public Page<NotifyComment> commentList(Pageable pageable,Integer notifyId);

	/**
	 * 前两条评论记录
	 * @param notifyId
	 * @return
	 */
	public List<NotifyComment> getMaxTwoComment(Integer notifyId);
}
