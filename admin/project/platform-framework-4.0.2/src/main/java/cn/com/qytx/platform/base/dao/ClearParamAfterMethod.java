package cn.com.qytx.platform.base.dao;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 该注解用在BaseDao 和 AbstractBaseDao 的方法上
 * <br/>
 * 当加有这个注解的方法执行完毕后, 会执行QueryAspect的逻辑，清空baseDao中定义的三个threadlocal变量
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ClearParamAfterMethod {

}
