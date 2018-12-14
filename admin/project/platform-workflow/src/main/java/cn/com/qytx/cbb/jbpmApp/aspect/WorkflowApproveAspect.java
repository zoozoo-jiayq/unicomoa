package cn.com.qytx.cbb.jbpmApp.aspect;

import org.aspectj.lang.ProceedingJoinPoint;

import cn.com.qytx.cbb.redpoint.service.RedPointService;
import cn.com.qytx.platform.security.SystemContextHolder;
import cn.com.qytx.platform.utils.spring.SpringUtil;

/**
 * 功能 工作流审批切面  
 * 版本  1.0
 * 开发人员  zyf
 * 创建日期  2015年12月8日
 * 修改日期  2015年12月8日
 * 修改列表
 */
public class WorkflowApproveAspect {
	/**
	 * 
	 * 功能：工作流审批切面，需要使用后置切面
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	  public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
			//获取执行方法的参数
	        Object[] args = pjp.getArgs();
	        String methodName = pjp.getSignature().getName();
	        //放行
	        Object retVal = pjp.proceed();
	        
        	if(SystemContextHolder.getSessionContext()!=null){
	        	if(SystemContextHolder.getSessionContext().getUser()!=null){
	        		int companyId = SystemContextHolder.getSessionContext().getUser().getCompanyId();
	        		RedPointService redPointService =(RedPointService) SpringUtil.getBean("workflowRedPointService");
	        		redPointService.dealAspect(args, methodName,companyId);
	        	}
	        }
	        return retVal;
	    }
	  
}
