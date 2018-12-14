package cn.com.qytx.cbb.publicDom.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.publicDom.service.PublicDomService;
import cn.com.qytx.cbb.publicDom.service.PublicDomService.DomType;
import cn.com.qytx.cbb.publicDom.service.PublicDomService.MenuType;
import cn.com.qytx.cbb.todocount.service.IDesktopTask;

@Service("dispatcherApproveDeskTopService")
@Transactional
public class DispatcherApproveDeskTopImpl extends PublicDomMenuUrlMap implements
		IDesktopTask {

	@Resource
	private PublicDomService publicDomService;
	
	@Override
	public int countOfTask(int userId) {
		// TODO Auto-generated method stub
		return publicDomService.getPersonTaskCount(DomType.DISPATCHER, MenuType.DIS_APPROVE, userId);
	}

	@Override
	public String getTaskUrl() {
		// TODO Auto-generated method stub
		return URLMAP.get(MenuType.DIS_APPROVE.getNodeName());
	}

	@Override
	public String getModuleName() {
		// TODO Auto-generated method stub
		return MenuType.DIS_APPROVE.getNodeName();
	}

}
