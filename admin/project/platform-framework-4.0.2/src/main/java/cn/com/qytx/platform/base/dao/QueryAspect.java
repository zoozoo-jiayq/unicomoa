package cn.com.qytx.platform.base.dao;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;

/**
 * 切面对象
 */
@Aspect
public class QueryAspect {

	 	@After("execution(* cn.com.qytx.*.*.dao.*Dao.*(..)) and !execution(* cn.com.qytx.platform.base.dao.BaseDao.*(..))")
	    public void doAfter(JoinPoint point) throws Throwable {
	        if(point.getTarget() instanceof BaseDao){
	            BaseDao baseDao = (BaseDao) point.getTarget();
	            baseDao.clearParamAppended();
	        }
	    }
	    
	    @After("execution(* cn.com.qytx.*.dao.*Dao.*(..))")
	    public void doAfter1(JoinPoint point) throws Throwable {
	        if(point.getTarget() instanceof BaseDao){
	            BaseDao baseDao = (BaseDao) point.getTarget();
	            baseDao.clearParamAppended();
	        }
	    }

	    @After("@annotation(cn.com.qytx.platform.base.dao.ClearParamAfterMethod)")
	    public void doAfter2(JoinPoint point) throws Throwable {
	    	if(point.getTarget() instanceof BaseDao){
	    		BaseDao baseDao = (BaseDao) point.getTarget();
	    		baseDao.clearParamAppended();
	    	}
	    }

}
