package cn.com.qytx.cbb.notify.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.notify.dao.WapNotifyViewDao;
import cn.com.qytx.cbb.notify.domain.NotifyView;
import cn.com.qytx.cbb.notify.service.IWapNotifyView;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;

@Service("wapNotifyViewImpl")
@Transactional
public class WapNotifyViewImpl extends BaseServiceImpl<NotifyView> implements IWapNotifyView{
	
	@Resource(name="wapNotifyViewDao")
	private WapNotifyViewDao wapNotifyViewDao;
	
	@Transactional(readOnly=true)
	public boolean isReadNotifyView(Integer userId,Integer id){
		return wapNotifyViewDao.isReadNotifyView(userId,id);
	}
}
