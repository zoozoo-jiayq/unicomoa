package cn.com.qytx.cbb.publicDom.action;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.jbpm.api.ProcessEngine;
import org.jbpm.api.RepositoryService;

import cn.com.qytx.cbb.publicDom.service.PublicDomService.DomType;
import cn.com.qytx.cbb.publicDom.service.impl.DeployDom;
import cn.com.qytx.platform.utils.spring.SpringUtil;

public class PublicDomDeployServlet extends HttpServlet {

	
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		//检测公文流程是否已发布
		ProcessEngine engine = (ProcessEngine) SpringUtil.getBean("processEngine");
		DeployDom dom = (DeployDom) SpringUtil.getBean("deployDom");
		RepositoryService service = engine.getRepositoryService();
		List gatherlist = service.createProcessDefinitionQuery().processDefinitionName(DomType.GATHER.getProcessName()).list();
		List dispatcherlist = service.createProcessDefinitionQuery().processDefinitionName(DomType.DISPATCHER.getProcessName()).list();
		if(gatherlist==null || gatherlist.size()==0){
			dom.deployGatherDom();
		}
		if(dispatcherlist == null || dispatcherlist.size() ==0){
			dom.deployDispatcherDom();
		}
		
	}



}
