package cn.com.qytx.cbb.jbpmApp.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.jbpmApp.thread.WorkflowSendRPMSG;
import cn.com.qytx.cbb.redpoint.service.RedPointService;
import cn.com.qytx.platform.utils.ThreadPoolUtils;

/**
 * 功能:工作流红点实现 
 * 版本: 1.0
 * 开发人员: zyf
 * 创建日期: 2015年3月23日
 * 修改日期: 2015年3月23日
 * 修改列表:
 */
@Service("workflowRedPointService")
@Transactional
public class WorkflowRedPointImpl implements RedPointService {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "workflowApproveCount";
	}

	@Override
	public void dealAspect(Object[] args, String methodName,Integer companyId) {
		// TODO Auto-generated method stub
		ThreadPoolUtils.getInstance().getThreadPool().execute(new Thread(new WorkflowSendRPMSG(args, methodName, this.getName(),companyId)));
	}
	
}
