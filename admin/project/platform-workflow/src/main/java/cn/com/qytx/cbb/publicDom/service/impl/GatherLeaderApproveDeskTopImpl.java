package cn.com.qytx.cbb.publicDom.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.publicDom.service.PublicDomService;
import cn.com.qytx.cbb.publicDom.service.PublicDomService.DomType;
import cn.com.qytx.cbb.publicDom.service.PublicDomService.MenuType;
import cn.com.qytx.cbb.todocount.service.IDesktopTask;

/**
 * 功能：领导批阅桌面任务提醒
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 上午9:57:01 
 * 修改日期：上午9:57:01 
 * 修改列表：
 */
@Service("gatherLeaderApproveDeskTopService")
@Transactional
public class GatherLeaderApproveDeskTopImpl extends  PublicDomMenuUrlMap implements IDesktopTask{

	@Resource
	private PublicDomService publicDomService;

	@Override
	public int countOfTask(int userId) {
		// TODO Auto-generated method stub
		return publicDomService.getPersonTaskCount(DomType.GATHER, MenuType.GA_APPROVE, userId);
	}

	@Override
	public String getTaskUrl() {
		// TODO Auto-generated method stub
		return URLMAP.get(MenuType.GA_APPROVE.getNodeName());
	}

	@Override
	public String getModuleName() {
		// TODO Auto-generated method stub
		return MenuType.GA_APPROVE.getNodeName();
	}

}
