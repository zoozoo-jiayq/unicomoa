package cn.com.qytx.platform.utils;

import org.hibernate.annotations.common.util.StringHelper;
import org.hibernate.cfg.ImprovedNamingStrategy;

/**
 * 公司自定义命名策略
 */
public class NamingStategy extends ImprovedNamingStrategy {
    //unqualify()是hiberante中StringHelper类提供的方法，参数为类名，返回值是不带包名的类名
    public String classToTableName(String className){
        return StringHelper.unqualify(className).toUpperCase();
    }

    public String propertyToColumnName(String propertyName){
        return propertyName.toUpperCase();
    }

    public String tableName(String tableName){
        return tableName;
    }

    public String columnName(String columnName){
        return columnName;
    }

    public String propertyToTableName(String className,String propertyName){
        return classToTableName(className)+'_'+propertyToColumnName(propertyName);
    }
}
