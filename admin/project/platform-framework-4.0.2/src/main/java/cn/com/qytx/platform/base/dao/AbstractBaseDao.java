package cn.com.qytx.platform.base.dao;

import static cn.com.qytx.platform.base.query.QueryUtils.executeCountQuery;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.ResultTransformer;
import org.springframework.util.Assert;

import cn.com.qytx.platform.base.PlatformException;
import cn.com.qytx.platform.base.domain.CompanyId;
import cn.com.qytx.platform.base.domain.DeleteState;
import cn.com.qytx.platform.base.domain.Partition;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.PageImpl;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.query.QueryUtils;
import cn.com.qytx.platform.utils.ReflectionUtils;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

/**
 * 抽象dao基类 ， 该类下的方法不建议直接调用
 */
public abstract class AbstractBaseDao<T,ID extends Serializable> {

    /**
     *  注入的hibernate JPA实现
     */
    @PersistenceContext
    protected EntityManager entityManager;

    /**
     * 批量更新对象的删除状态
     * @param entities 实现Iterator接口的对象集合
     * @param isDeleteStatus 删除状态，true代表1(删除)，false代表0(不删除)
     * @param cls，暂时无用
     * @return
     */
    @ClearParamAfterMethod
    public <S> int  updateDeleteStatus(Iterable<T> entities,boolean isDeleteStatus,Class<S> cls){
        String deleteQL = "update "+getEntityClass().getSimpleName()+" x set x."+getDeleteType().getName()+" = ?1 where "+getIdType().getName()+" in ?2";
        Query query = entityManager.createQuery(deleteQL);
        query.setParameter(1,isDeleteStatus?1:0);
        
        Iterable<S> ids = Iterables.transform(entities,new Function<T, S>() {
        	@Override
        	public S apply(T input) {
        		return (S) org.springframework.util.ReflectionUtils.getField(getIdType(),input);
        	}
        });
        query.setParameter(2, Lists.newArrayList(ids));
        return query.executeUpdate();
    }

    public abstract Class<T> getEntityClass();

    /**
     * 获取主键字段，主键字段指标注有@Id注解的字段
     * @return 返回主键字段
     */
    protected Field getIdType(){
        return getFieldFromEntityClass(Id.class);
    }

    /**
     * 获取删除状态字段，删除字段指标注有@DeleteState注解的字段
     * @return 返回删除状态字段
     */
    protected Field getDeleteType(){
        Field deleteField = getFieldFromEntityClass(DeleteState.class);
        if(!deleteField.getType().isAssignableFrom(Integer.class)){
            throw new PlatformException("类::" + getEntityClass().getName()+" 删除状态必须为Integer类型");
        }
        return deleteField;
    }

    /**
     * 获取单位公司字段，单位字段指标注有@CompanyId注解的字段
     * @return 返回单位ID字段
     */
    protected Field getCompanyIdType(){
        Field companyIdField = getFieldFromEntityClass(CompanyId.class);
        if(companyIdField == null){
        	return null;
        }
        if(!companyIdField.getType().isAssignableFrom(Integer.class)){
            throw new PlatformException("类::" + getEntityClass().getName()+" 单位字段Integer类型");
        }
        return companyIdField;
    }

    /**
     * 通过标注的注解类型获取对应的字段
     * @param annotationClass 注解类型
     * @return
     */
    private Field getFieldFromEntityClass(Class annotationClass){
        List<Field> declaredFields = ReflectionUtils.getAccessibleFields(getEntityClass());
        for(Field field : declaredFields){
            Annotation annotation = field.getAnnotation(annotationClass);
            if(null!=annotation){
                return field;
            }
        }
//        throw new PlatformException("类::" + getEntityClass().getName()+" 没有设置注解:"+annotationClass.getName());
        return null;
    }


    /**
     * 执行jpql语句，insert/update/delete等。例如：
     * </br>
     * 1. delete form User x where x.id in ?1
     * </br>
     * 2. update User x set x.name = ?1 where x.id = ?2
     * </br>
     * 3. insert into User(id,name) values(?1,?2)
     * </br>
     * @param qlQuery hql语句
     * @param values 可变参数
     */
    @ClearParamAfterMethod
    public int executeQuery(String qlQuery, Object... values) {
        Assert.notNull(qlQuery, "给定的qlQuery不能为空!");
        Query query  = entityManager.createQuery(qlQuery);
        applyQeuryParams(query,values);
        return query.executeUpdate();
    }

    /**
     * 查询cout max min 或者单个对象等
     * </br>
     * 1. select from cout(*) from User where age > ?1      Integer.class   19
     * </br>
     * 2. select min/max(age) from User where id in ?1      Integer.class   List<String> ids
     * </br>
     * 3. select x from User x where x.name = ?1            User.class      李广
     * </br>
     * @param qlQuery jpql
     * @param tClass 返回的类型
     * @param values 可变参数集合 
     * @return 返回一个java对象
     */
    @ClearParamAfterMethod
    public <S> S queryForObject(String qlQuery, Class<S> tClass, Object... values) {
        Assert.notNull(qlQuery, "给定的qlQuery不能为空!");
        TypedQuery<S> query  = entityManager.createQuery(qlQuery,tClass);
        applyQeuryParams(query,values);
        return query.getSingleResult();
    }

    /**
     * 拼装查询参数
     * @param query jpa查询对象
     * @param values 可变参数
     */
    protected void applyQeuryParams(Query query,Object... values){
        if(values!=null){
            int i = 0;
            for(Object value:values){
                query.setParameter(++i, value);
            }
        }
    }


    ///////////////////////////////////////////以下为兼容老cbb方法//////////////////////////////////////////////////

    /**
     * 查询指定JPQL，并返回集合
     *
     * @param jpql
     *            jpql语句
     * @param values
     *            可变的参数列表
     * @return 集合
     */
    @Deprecated
    @ClearParamAfterMethod
    public <S> List<S> find(String jpql, Object... values) {
        Query query = entityManager.createQuery(jpql);
        applyQeuryParams(query,values);
        return query.getResultList();
    }
    /**
     * 根据一个jpql 返回指定类型的结果集
     * @param jpql jpql语句
     * @param transformer {@link org.hibernate.transform.Transformers}
     * @param values 参数列表
     * @return 指定类型的集合
     */
    @Deprecated
    @ClearParamAfterMethod
    public <S> List<S> findList(String jpql,ResultTransformer transformer,Object... values){
        Query query = entityManager.createQuery(jpql);
        query.unwrap(SQLQuery.class).setResultTransformer(transformer);
        applyQeuryParams(query, values);
        return query.getResultList();
    }
    /**
     * 按照JPQL语句查询唯一对象.
     *
     * @param jpql
     *            jpql语句
     * @param values
     *            可变参数集合
     * @return OBJECT对象
     */
    @Deprecated
    @ClearParamAfterMethod
    public <S> S findUnique(String jpql, Object... values) {
        Query query = entityManager.createQuery(jpql);
        applyQeuryParams(query,values);

        List<S> resultList = query.getResultList();
        if(resultList!=null&&resultList.size()>0){
            return resultList.get(0);
        }
        return null;
    }

    /**
     * 查找指定JPQL并返回INT型
     *
     * @param jpql
     *            JPQL语句
     * @param values
     *            可变参数列表
     * @return INT
     */
    @Deprecated
    @ClearParamAfterMethod
    public int findInt(String jpql, Object... values) {
        TypedQuery<Long> query = entityManager.createQuery(jpql,Long.class);
        applyQeuryParams(query,values);
        Long r = query.getSingleResult();
        if(r!=null){
        	return r.intValue();
        }else{
        	return 0;
        }
    	
    }

    /**
     * 按JPQL分页查询.
     *
     * @param pageable
     *            页面对象
     * @param jpql
     *            JPQL语句
     * @param values
     *            可变参数列表
     * @return 分页数据
     */
    @Deprecated
    @ClearParamAfterMethod
    public Page<T> findByPage(Pageable pageable, String jpql, Object... values) {
        Query query = entityManager.createQuery(jpql);
        applyQeuryParams(query,values);

        if(pageable==null){
            return new PageImpl<T>(query.getResultList());
        }
        query.setFirstResult(pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        TypedQuery<Long> countQuery = entityManager.createQuery(QueryUtils.createCountQueryFor(jpql),Long.class);
        applyQeuryParams(countQuery,values);
        Integer total = executeCountQuery(countQuery).intValue();

        List<T> content = total > pageable.getOffset() ? query.getResultList() : Collections.<T> emptyList();

        return new PageImpl<T>(content, pageable, total);
    }

    /**
     * 查询指定索引范围内的索引
     * @param jpql hql语句
     * @param firstRow 查询结果的第一条的索引值
     * @param maxRow 查询结果的最大索引值
     * @return 返回指定索引范围内的记录
     */
    @Deprecated
    @ClearParamAfterMethod
    public <S> List<S> findMaxRow(String jpql, int firstRow, int maxRow) {
        Query query = entityManager.createQuery(jpql);
        query.setFirstResult(firstRow);
        query.setMaxResults(maxRow);
        return query.getResultList();
    }
    /**
     * 删除指定实体
     * @param jpql 删除语句
     * @values 可变参数
     */
    @Deprecated
    @ClearParamAfterMethod
    public int bulkDelete(String jpql, Object... values) {
        Query query = entityManager.createQuery(jpql);
        applyQeuryParams(query,values);
        return query.executeUpdate();
    }
    /**
     * 删除指定实体
     * @param jpql 
     */
    @Deprecated
    @ClearParamAfterMethod
    public int bulkDelete(String jpql) {
        Query query = entityManager.createQuery(jpql);
        return query.executeUpdate();
    }
    /**
     * 使用带参数的jpql语句增加、更新、删除实体
     * @param queryString hql语句
     * @param values 可变参数
     * @return
     */
    @Deprecated
    @ClearParamAfterMethod
    public int bulkUpdate(String queryString, Object... values) {
        Query query = entityManager.createQuery(queryString);
        applyQeuryParams(query,values);
        return query.executeUpdate();
    }

    /**
     * 执行hql语句
     * @param hql
     * @return
     */
    @Deprecated
    @ClearParamAfterMethod
    public int createQuery(String hql) {
        Query query = entityManager.createQuery(hql);
        return query.executeUpdate();
    }
    /**
     *执行sql语句的查询
     *@param sql sql语句
     *@return List集合
     */
    @Deprecated
    @ClearParamAfterMethod
    public <S> List<S> createSqlQuery(String sql) {
        Query nativeQuery = entityManager.createNativeQuery(sql);
        return nativeQuery.getResultList();
    }
    /**
     * 按SQL分页查询.
     *
     * @param pageable
     *            页面对象
     * @param sql
     *            sQL语句
     * @param values
     *            可变参数列表
     * @return 分页数据
     */
    @Deprecated
    @ClearParamAfterMethod
    public <S> Page<S> createPageQuery(Pageable pageable, String sql, Object... values) {
        Query query = entityManager.createNativeQuery(sql);
        applyQeuryParams(query,values);

        if(pageable==null){
            return new PageImpl(query.getResultList());
        }
        query.setFirstResult(pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        Query countQuery = entityManager.createNativeQuery(sql,Integer.class);
        Integer total = (Integer) countQuery.getSingleResult();

        List content = total > pageable.getOffset() ? query.getResultList() : Collections.emptyList();

        return new PageImpl(content, pageable, total);
    }
    /**
     * 执行存储过程
     * @param procName 存储过程名称
     * @param params 存储过程输入参数
     */
    @Deprecated
    @ClearParamAfterMethod
    public int executeProcedure(String procName, List<Object> params) {
        Collection<String> paramdot = Collections2.transform(params,new Function<Object, String>() {
            
            public String apply(Object input) {
                return "?";
            }
        });
        Query nativeQuery = entityManager.createNativeQuery("{call " + procName + "(" + StringUtils.join(paramdot, ",") + ")}");
        applyQeuryParams(nativeQuery,params.toArray());
        return nativeQuery.executeUpdate();
    }
    
    /**
     * 执行存储过程 返回结果，最后一位为返回参数
     * @param procName 存储过程名称
     * @param params 查询参数
     * @return 返回存储过程执行结果
     */
    @Deprecated
    @ClearParamAfterMethod
    public String executeProcedureOutParameter(String procName, List<Object> params) {
        Collection<String> paramdot = Collections2.transform(params,new Function<Object, String>() {
            
            public String apply(Object input) {
                return "?";
            }
        });
        Query nativeQuery = entityManager.createNativeQuery("{call " + procName + "(" + StringUtils.join(paramdot, ",") + ")}");
        applyQeuryParams(nativeQuery,params.toArray());
        Object result = nativeQuery.getSingleResult();
        return result==null?"":result.toString();
    }

    /*
     * 获取分区注解的字段
     */
    protected Field getPartitionType(){
    	Field partitionField = getFieldFromEntityClass(Partition.class);
    	if(!partitionField.getType().isAssignableFrom(Integer.class)){
    		throw new PlatformException("类::" + getEntityClass().getName()+" 单位字段Integer类型");
    	}
    	return partitionField;
    }
}
