package cn.com.qytx.cbb.jbpmApp.aspect;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;

import cn.com.qytx.cbb.myapply.service.IMyWaitProcess;
import cn.com.qytx.cbb.redpoint.service.RedPointService;
import cn.com.qytx.platform.security.SystemContextHolder;
import cn.com.qytx.platform.utils.spring.SpringUtil;
/**
 * 功能: 工作流待审批删除切面 
 * 版本: 1.0
 * 开发人员: zyf
 * 创建日期: 2015年3月20日
 * 修改日期: 2015年3月20日
 * 修改列表:
 */
public class WorkflowDeleteAspect {
	/**
	 * 
	 * 功能：工作流待审批删除切面，需要使用后置切面
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	  public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
			//获取执行方法的参数
	        Object[] args = pjp.getArgs();
	        String methodName = pjp.getSignature().getName();
	        List<Integer> userIds = new ArrayList<Integer>(); 
	        try{
	        	String instanceIds = args[0].toString();
	        	String moduleCode = args[1].toString();
        		IMyWaitProcess myWaitProcessService = (IMyWaitProcess) SpringUtil.getBean("myWaitProcessService");
        		userIds = myWaitProcessService.getProcesserByInstanceIds(instanceIds, moduleCode);
	        }catch(Exception e){
	        	e.printStackTrace();
	        }
	        
	        //放行
	        Object retVal = pjp.proceed();
	        if(userIds != null && userIds.size()>0){
	        	Object[] objs = new Object[3];
	        	objs[0] = args[0];
	        	objs[1] = args[1];
	        	objs[2] = userIds;
	        	if(SystemContextHolder.getSessionContext()!=null){
		        	if(SystemContextHolder.getSessionContext().getUser()!=null){
		        		int companyId = SystemContextHolder.getSessionContext().getUser().getCompanyId();
		        		RedPointService redPointService =(RedPointService) SpringUtil.getBean("workflowRedPointService");
		        		redPointService.dealAspect(objs, methodName,companyId);
		        	}
		        }
	        }
	        return retVal;
	    }
	  
}
