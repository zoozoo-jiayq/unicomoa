package cn.com.qytx.cbb.publicDom.service.impl;

import javax.annotation.Resource;

import org.jbpm.api.ProcessEngine;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.publicDom.service.PublicDocumentConstant;

/**
 * 功能：发布公文
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 2013-4-16 下午6:01:10 
 * 修改日期：2013-4-16 下午6:01:10 
 * 修改列表：
 */
@Service("deployDom")
@Transactional
public class DeployDom {
	
	@Resource
	private ProcessEngine engin;
	
	public void deployGatherDom(){
		engin.getRepositoryService().createDeployment().addResourceFromClasspath(PublicDocumentConstant.GATHER_FILE).deploy();
	}
	
	public void deployDispatcherDom(){
		engin.getRepositoryService().createDeployment().addResourceFromClasspath(PublicDocumentConstant.DISPATCHER_FILE).deploy();
	}
}
