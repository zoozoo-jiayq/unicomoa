package cn.com.qytx.cbb.publicDom.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.todocount.service.IDesktopTask;

/**
 * 功能：收文等级
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 上午10:37:12 
 * 修改日期：上午10:37:12 
 * 修改列表：
 */
@Service("gatherAddDeskTopService")
@Transactional
public class GatherAddDeskTopImpl extends PublicDomMenuUrlMap implements
		IDesktopTask {

	@Override
	public int countOfTask(int userId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getTaskUrl() {
		// TODO Auto-generated method stub
		return URLMAP.get("收文登记");
	}

	@Override
	public String getModuleName() {
		// TODO Auto-generated method stub
		return "收文登记";
	}
}
