package cn.com.qytx.cbb.notify.aspect;

import org.aspectj.lang.ProceedingJoinPoint;

import cn.com.qytx.cbb.notify.domain.Notify;
import cn.com.qytx.cbb.notify.domain.NotifyView;
import cn.com.qytx.cbb.notify.service.INotifyView;
import cn.com.qytx.cbb.redpoint.service.RedPointService;
import cn.com.qytx.platform.security.SystemContextHolder;
import cn.com.qytx.platform.utils.spring.SpringUtil;
/**
 * 功能: pc公告查看切面 
 * 版本: 1.0
 * 开发人员: zyf
 * 创建日期: 2015年3月18日
 * 修改日期: 2015年3月18日
 * 修改列表:
 */
public class NotifyViewAddAspect {
	/**
	 * 
	 * 功能：pc公告查看切面 ，需要使用前置切面
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	  public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
			//获取执行方法的参数
	        Object[] args = pjp.getArgs();
	        String methodName = pjp.getSignature().getName();
	        
	        if(SystemContextHolder.getSessionContext()!=null){
	        	if(SystemContextHolder.getSessionContext().getUser()!=null){
	        		int companyId = SystemContextHolder.getSessionContext().getUser().getCompanyId();
	        		NotifyView notifyView = (NotifyView) args[0];
	        		if(notifyView!=null){
	        			INotifyView notifyViewService = (INotifyView) SpringUtil.getBean("notifyViewService");
	        			//未读
	        			if(notifyViewService.getNotifyView(notifyView.getCreateUser().getUserId(), notifyView.getNotify().getId())){
	        				RedPointService redPointService =(RedPointService) SpringUtil.getBean("notifyRedPointService");
	        				redPointService.dealAspect(args, methodName,companyId);
	        			}
	        		}
	        	}
	        }
	        
	        
	        //放行
	        Object retVal = pjp.proceed();
	        return retVal;
	    }
	  
}
