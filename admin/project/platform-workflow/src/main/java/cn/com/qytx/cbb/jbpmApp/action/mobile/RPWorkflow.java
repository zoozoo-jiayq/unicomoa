package cn.com.qytx.cbb.jbpmApp.action.mobile;

import javax.annotation.Resource;

import cn.com.qytx.cbb.myapply.service.IMyWaitProcess;
import cn.com.qytx.platform.base.action.BaseActionSupport;

/**
 * 功能: 统计工作流模块但审批数量 
 * 版本: 1.0
 * 开发人员: zyf
 * 创建日期: 2015年3月23日
 * 修改日期: 2015年3月23日
 * 修改列表:
 */
public class RPWorkflow extends BaseActionSupport {

	/**
	 * 描述含义
	 */
	private static final long serialVersionUID = 7533000533685498936L;

	@Resource
	IMyWaitProcess myWaitProcessService;
	
	private Integer userId;
	
	private Integer companyId;

	/**
	 * 功能：工作流模块我的待审批数量
	 */
	public void getWorkflowApproveCount(){
		if(userId == null || companyId == null){
			ajax("0");
			LOGGER.info("用户id、公司id不能为空！");
			return;
		}
		
		int count = myWaitProcessService.myWaitModuleCount(userId, null);
		ajax(count);
	}
	 
	
	
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	
}
