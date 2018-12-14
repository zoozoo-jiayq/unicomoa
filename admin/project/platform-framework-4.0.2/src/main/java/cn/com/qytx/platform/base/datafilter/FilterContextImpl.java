package cn.com.qytx.platform.base.datafilter;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import cn.com.qytx.platform.security.SystemContextHolder;
import cn.com.qytx.platform.security.domain.DataFilter;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;


/**
 * 获取数据权限的上下文信息
 */
public class FilterContextImpl implements FilterContext {


    private EntityManager entityManager;
    private String alias;
    
    /**
     * 初始化数据库连接
     * @param entityManager
     */
    public FilterContextImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    public FilterContextImpl(EntityManager entityManager,String alias){
    	this.entityManager = entityManager;
    	this.alias = alias;
    }

    /* 从DataFilter表中查询出符合条件的数据权限HQL集合
     * @see cn.com.qytx.platform.base.datafilter.FilterContext#getFilterRuleJpqlList(java.lang.Class, java.lang.String, java.lang.Integer)
     */
    @Override
    public Collection<String> getFilterRuleJpqlList(Class modelClass, String dataFilterType, Integer companyId) {

        //根据用户的关系id和用户的单位id 还有 当前的权限过滤类型和当前模块 找到对应的数据规则
        String ql = "select x from DataFilter x where x.modelClassName = ?1 and x.companyId = ?2 and x.operationType = ?3 and x.relationId in ?4";
        
        Query query = entityManager.createQuery(ql);
        query.setParameter(1,modelClass.getName());
        query.setParameter(2,companyId);
        query.setParameter(3, dataFilterType);
        query.setParameter(4, SystemContextHolder.getSessionContext().getRelationIds());
        List<DataFilter> resultList = query.getResultList();

        Collection<String> jpqlList = Collections2.transform(resultList, new Function<DataFilter, String>() {
            @Override
            public String apply(DataFilter input) {
            	String r = input.getConditionJpql();
            	if(StringUtils.isNotBlank(alias)){
            		r=alias+"."+r;
            	}
                return r;
            }
        });
        return jpqlList;
    }
}
