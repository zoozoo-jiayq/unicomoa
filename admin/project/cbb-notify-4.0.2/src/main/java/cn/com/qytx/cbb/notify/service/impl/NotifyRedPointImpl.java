package cn.com.qytx.cbb.notify.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.notify.thread.NotifySendRPMSG;
import cn.com.qytx.cbb.redpoint.service.RedPointService;
import cn.com.qytx.platform.utils.ThreadPoolUtils;

/**
 * 功能: 获得公告未读数量 
 * 版本: 1.0
 * 开发人员: zyf
 * 创建日期: 2015年3月19日
 * 修改日期: 2015年3月19日
 * 修改列表:
 */
@Service("notifyRedPointService")
public class NotifyRedPointImpl implements RedPointService {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "notifyUnReadCount";
	}

	@Override
	public void dealAspect(Object[] args, String methodName,Integer companyId) {
		// TODO Auto-generated method stub
		ThreadPoolUtils.getInstance().getThreadPool().execute(new Thread(new NotifySendRPMSG(args, methodName, this.getName(),companyId)));
	}

}
