package cn.com.qytx.cbb.publicDom.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.todocount.service.IDesktopTask;

/**
 * 功能：发文拟稿
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 上午10:12:54 
 * 修改日期：上午10:12:54 
 * 修改列表：
 */
@Service("dispatcherWriteDocService")
@Transactional
public class DispatcherWriteDocDestTopImpl extends PublicDomMenuUrlMap
		implements IDesktopTask {

	@Override
	public int countOfTask(int userId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getTaskUrl() {
		// TODO Auto-generated method stub
		return URLMAP.get("发文拟稿");
	}

	@Override
	public String getModuleName() {
		// TODO Auto-generated method stub
		return "发文拟稿";
	}
}
