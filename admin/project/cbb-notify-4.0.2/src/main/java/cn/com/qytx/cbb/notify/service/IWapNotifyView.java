package cn.com.qytx.cbb.notify.service;

import cn.com.qytx.cbb.notify.domain.NotifyView;
import cn.com.qytx.platform.base.service.BaseService;

public interface IWapNotifyView extends BaseService<NotifyView>{
	
	/**
	 * 判断是否已经阅读  true 已经阅读， false 未阅读
	 * @param userId
	 * @param id
	 * @return
	 */
	public boolean isReadNotifyView(Integer userId,Integer id);
}
