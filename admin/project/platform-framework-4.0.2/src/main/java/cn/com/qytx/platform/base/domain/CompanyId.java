package cn.com.qytx.platform.base.domain;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 该注解针对entity类的field字段
 * <br/>
 * 添加该注解的字段则会被basedao认为是单位id字段
 */
@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CompanyId {
}
