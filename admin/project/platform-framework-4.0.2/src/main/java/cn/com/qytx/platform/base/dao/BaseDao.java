package cn.com.qytx.platform.base.dao;

import static cn.com.qytx.platform.base.query.QueryUtils.COUNT_QUERY_STRING;
import static cn.com.qytx.platform.base.query.QueryUtils.DEFAULT_ALIAS;
import static cn.com.qytx.platform.base.query.QueryUtils.FIND_ALL_QUERY_STRING;
import static cn.com.qytx.platform.base.query.QueryUtils.applySorting;
import static cn.com.qytx.platform.base.query.QueryUtils.executeCountQuery;
import static cn.com.qytx.platform.base.query.QueryUtils.getQueryString;
import static org.apache.commons.lang3.StringUtils.contains;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.join;
import static org.apache.commons.lang3.StringUtils.startsWithAny;
import static org.apache.commons.lang3.StringUtils.trimToNull;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.classic.Session;
import org.hibernate.impl.SessionImpl;
import org.hibernate.transform.Transformers;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import cn.com.qytx.platform.base.PlatformException;
import cn.com.qytx.platform.base.application.TransportUser;
import cn.com.qytx.platform.base.datafilter.FilterContext;
import cn.com.qytx.platform.base.datafilter.FilterContextImpl;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.PageImpl;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.query.Sort;
import cn.com.qytx.platform.security.SystemContextHolder;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

/**
 * dao操作基类，所有dao实现类都要继承此类，子类需要通过泛型声明操作对象和操作对象的数据类型
 */
public class BaseDao<T,ID extends Serializable> extends AbstractBaseDao<T,ID> {

	private static String hqlLog;

    /**
     * threadlocal类型的静态变量用来保存查询条件中的删除状态。<br/>
     * 为什么要使用threadlocal类型变量来保存?<br/>
     * 因为我们使用了Spring容器来创建Dao对象，默认情况下，被创建的对象都使用了单例模式，是非线程安全的，而使用Threadlocal变量来保存可以做到线程安全。
     */
    protected ThreadLocal<Integer> isDelete = new ThreadLocal<Integer>();
    
    /**
     * threadlocal类型的静态变量用来保存数据权限的操作类型
     */
    protected ThreadLocal<String> dataFilterType = new ThreadLocal<String>();
    
    /**
     * threadlocal类型的静态变量用来保存查询条件中的单位ID 
     */
    protected ThreadLocal<Integer> companyId = new ThreadLocal<Integer>();

    /**
     * threadlocal类型的静态变量用来保存查询过滤器
     */
    protected ThreadLocal<Integer> partition = new ThreadLocal<Integer>();
    
    /**
     * 获取当前dao的操作模型类,即类声明的泛型参数中的第一个参数类型
     * @return
     */
    @SuppressWarnings("unchecked")
	public Class<T> getEntityClass(){
        // 获取父类声明的泛型类
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
        	Type[] trueType = ((ParameterizedType) type).getActualTypeArguments();
        	Class<T> currentEntityClass =  (Class<T>) trueType[0];
        	return currentEntityClass;
        }else{
        	return null;
        }
    }

    /**
     * 清除附加的查询过滤条件 比如删除/单位/权限过滤等标识
     */
    public void clearParamAppended(){
        isDelete.set(null);
        dataFilterType.set(null);
        companyId.set(null);
        partition.set(null);
    }

    /**
     * 附加查询条件，按照指定单位ID查询
     * @param orgId 单位ID
     * @return 返回当前对象，方便链式调用
     */
    public BaseDao<T, ID> companyId(Integer orgId) {
        this.companyId.set(orgId);
        return this;
    }


    /**
     * 附加查询条件，按照SystempContextHolder上下文中指定的单位ID查询
     * @return 返回当前对象，方便链式调用
     */
    public BaseDao<T, ID> companyId() {
    	if(SystemContextHolder.getSessionContext()!=null && SystemContextHolder.getSessionContext().getUser()!=null){
    		this.companyId.set(SystemContextHolder.getSessionContext().getUser().getCompanyId());
    	}else{
    		this.companyId.set(null);
    	}
        return this;
    }

    /**
     * 附加查询条件，使用指定条件的数据权限
     * @param dataFilterType 指定的数据权限条件
     * @return 返回当前对象，方便链式调用
     */
    public BaseDao<T, ID> dataFilter(String dataFilterType) {
        this.dataFilterType.set(dataFilterType);
        return this;
    }
    
    /**
     * 默认使用"READ"类型的数据权限
     * @return
     */
    public BaseDao<T,ID> dataFilter(){
    	this.dataFilterType.set("READ");
    	return this;
    }

    /**
     * 附加查询条件，查询未删除的对象
     * @return 返回当前对象，方便链式调用
     */
    public BaseDao<T, ID> unDeleted() {
        this.isDelete.set(0);
        return this;
    }

    /**
     * 附加查询条件，查询已删除的对象
     * @return 返回当前对象，方便链式调用
     */
    public BaseDao<T, ID> isDeleted() {
        this.isDelete.set(1);
        return this;
    }


    /**
     * 保存或更新指定实体类
     *
     * @param entity  实体类
     */
    @ClearParamAfterMethod
    public T saveOrUpdate(T entity) {
        if(entityManager.contains(entity)){
            entityManager.merge(entity);
        }else{//如果保存的entity是一个游离态的对象，但是id在数据库中存在，则执行更新
            if(ReflectionUtils.getField(getIdType(),entity)!=null){
                entityManager.merge(entity);
            }else{
                entityManager.persist(entity);
            }
        }
        //doFilter(entity);
        return entity;
    }

    /**
     * 批量保存或更新指定实体列表
     * @param entities  实体类列表
     */
    @ClearParamAfterMethod
    public List<T> saveOrUpdate(Iterable<T> entities) {
        List<T> result = new ArrayList<T>();

        if (entities == null) {
            return result;
        }

        //保存临时的过滤声明
        String tempFilter = dataFilterType.get();

        //声明不过滤
        dataFilter(null);
        int i = 0 ;
        for (T entity : entities) {
        	i++;
            result.add(saveOrUpdate(entity));
          
        }
        //还原方法调用之前的过滤声明
        //dataFilter(tempFilter);
        //根据过滤声明执行权限过滤判断
        //doFilter(result);

        return result;
    }

    /**
     * 删除指定实体，实体主键是Int类型
     * @param entity 待删除的实体
     * @param fomrDB true:真删除，false假删除
     */
    @ClearParamAfterMethod
    public int delete(T entity,boolean fromDB) {
        //doFilter(entity);
        if(fromDB){//delete
            entityManager.remove(entity);
            return 1;
        }else{//update deleteStatus
            return updateDeleteStatus(Lists.newArrayList(entity),true,Integer.class);
        }
    }
    /**
     * 删除指定的实体，该实体的主键必须是字符串类型的
     * @param entity 带删除的实体
     * @param fromDB true:真删除,false:假删除
     * @return
     */
    @ClearParamAfterMethod
    @Deprecated
    public int deleteEntityWithStringId(T entity,boolean fromDB) {
        //doFilter(entity);
        if(fromDB){//delete
            entityManager.remove(entity);
            return 1;
        }else{//update deleteStatus
            return updateDeleteStatus(Lists.newArrayList(entity),true,String.class);
        }
    }

    /**
     * 根据主键ID删除对象，主键ID的类型和该类泛型声明称的ID类型一致
     * @param id 主键ID
     * @param fromDB true:真删除;false:假删除
     */
    @ClearParamAfterMethod
    public int delete(ID id,boolean fromDB) {
        Assert.notNull(id, "给定的ID不能为空!");
        T entity = entityManager.find(getEntityClass(), id);
        return delete(entity,fromDB);
    }
    /**根据主键ID删除对象，主键ID是字符串类型
     * @param fromDB true:真删除;false:假删除
     * @param id 主键ID
     * @return
     */
    @ClearParamAfterMethod
    @Deprecated
    public int delete(boolean fromDB,String id) {
        Assert.notNull(id, "给定的ID不能为空!");
        T entity = entityManager.find(getEntityClass(), id);
        return deleteEntityWithStringId(entity,fromDB);
    }
    
    /**
     * 根据IDS集合删除对象集合
     * @param ids id集合
     * @param fromDB true:真删除;false:假删除
     * @return
     */
    @ClearParamAfterMethod
    public int deleteByIds(Iterable<ID> ids,boolean fromDB) {
        Query query = entityManager.createQuery(getQueryString(FIND_ALL_QUERY_STRING, getEntityClass().getSimpleName()) + " where x."+getIdType().getName()+" in ?1");
        query.setParameter(1,ids);

        List<T> tlist = query.getResultList();
       // doFilter(tlist);
        if(fromDB){//delete
            Query executeQuery = entityManager.createQuery("delete from "+getEntityClass().getSimpleName()+" x where x."+getIdType().getName()+" in ?1");
            executeQuery.setParameter(1,ids);
            return executeQuery.executeUpdate();
        }else{// update deleteStatus
            return updateDeleteStatus(tlist,true,Integer.class);
        }
    }
    
    /**
     * 根据字符串类型的ID集合删除对象集合
     * @param fromDB true:真删除;false:假删除
     * @param ids 字符串类型的ID集合
     * @return
     */
    @ClearParamAfterMethod
    @Deprecated
    public int deleteByIds(boolean fromDB,Iterable<String> ids) {
        Query query = entityManager.createQuery(getQueryString(FIND_ALL_QUERY_STRING, getEntityClass().getSimpleName()) + " where x."+getIdType().getName()+" in ?1");
        query.setParameter(1,ids);

        List<T> tlist = query.getResultList();
        //doFilter(tlist);
        if(fromDB){//delete
            Query executeQuery = entityManager.createQuery("delete from "+getEntityClass().getSimpleName()+" x where x."+getIdType().getName()+" in ?1");
            executeQuery.setParameter(1,ids);
            return executeQuery.executeUpdate();
        }else{// update deleteStatus
            return updateDeleteStatus(tlist,true,String.class);
        }
    }

    /**
     * 查找指定ID实体类对象
     *
     * @param id
     *            实体ID
     * @return 实体对象
     */
    @ClearParamAfterMethod
    public T findOne(ID id) {
    	return this.entityManager.find(getEntityClass(), id);
    }
    
    /**
     * 按照jpql语句查询唯一对象.
     *
     * @param condition
     *            where 条件子句 例如： id = ?1
     * @param values
     *            可变参数集合 例如：889
     * @return OBJECT对象
     */
    @ClearParamAfterMethod
    public T findOne(String condition, Object... values) {
        if(isEmpty(condition)){
            throw new PlatformException("条件不能为空!");
        }
        String regex  = "[=><\\s]+";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(condition);
        if(m.find()){
        	Query query = createQuery(condition, values);
        	List<T> resultList = query.getResultList();
        	//当有多条结果时，返回第一条
        	if(resultList!=null&&resultList.size()>0){
        		return resultList.get(0);
        	}
        }else{
        	return findOne("x."+getIdType().getName()+" = ?1", condition);
        }
        return null;
    }
    

    /**
     * 查询所有集合
     * @return
     */
    @ClearParamAfterMethod
    public List<T> findAll() {
        return findAll(null);
    }

    /**
     * 查询指定条件，并返回集合
     *
     * @param condition
     *             where 条件子句 例如： name = ?1 and sex = '女' and age < ?2
     * @param values
     *            可变的参数列表  例如： 李广 19
     * @return 集合
     */
    @ClearParamAfterMethod
    public List<T> findAll(String condition, Object... values) {
        return createQuery(condition, values).getResultList();
    }

    /**
     * 获取指定实体Class的记录总数
     *
     * @return 记录总数
     */
    @ClearParamAfterMethod
    public int count() {
        return executeCountQuery(createCountQuery(null, null)).intValue();
    }

    /**
     * 获取条目数
     * @param condition where 条件子句 例如： id = ?1
     * @param values
     * @return int类型的条目数
     */
    @ClearParamAfterMethod
    public int count(String condition, Object... values) {
        return executeCountQuery(createCountQuery(condition, values)).intValue();
    }


    /**
     * 查询指定条件，并返回集合
     * @param condition where 条件子句 例如： name = ?1 and sex = '女' and age < ?2
     * @param sort 排序对象
     * @param values 可变的参数列表  例如： 李广 19
     * @return 集合
     */
    @ClearParamAfterMethod
    public List<T> findAll(String condition, Sort sort, Object... values) {
        return createQuery(condition, sort, values).getResultList();
    }

    /**
     * 返回指定条件的分页对象
     * @param condition where 条件子句 例如： name = ?1 and sex = '女' and age < ?2
     * @param pageable 分页信息
     * @param values 参数
     * @return
     */
    @ClearParamAfterMethod
    public Page<T> findAll(String condition, Pageable pageable, Object... values) {
        if(pageable==null){
            return new PageImpl<T>((List<T>) findAll(condition,values));
        }

        Integer total = count(condition,values);

        Query query = createQuery(condition, pageable.getSort(), values);
        query.setFirstResult(pageable.getOffset());//设置从起始值
        query.setMaxResults(pageable.getPageSize());//设置要读取多少条

        List<T> content = total > pageable.getOffset() ? query.getResultList() : Collections.<T> emptyList();

        return new PageImpl<T>(content, pageable, total);
    }
    /**
     * sql分页查询
     * @param sql sql查询语句
     * @param pageable 分页参数
     * @param values 可变查询参数
     * @return 分页查询结果
     */
    @ClearParamAfterMethod
    @Deprecated
    public Page<Map<String,Object>> findMapByPager(String sql,Pageable pageable ,Object... values){
    	if(pageable==null){
           throw new PlatformException("分页数据异常");
        }
    	 Integer total = totalCountBySql(sql, values);
    	 Query query = entityManager.createNativeQuery(sql);
    	 applySQLQueryParameter(query,values);
         query.setFirstResult(pageable.getOffset());//设置从起始值
         query.setMaxResults(pageable.getPageSize());//设置要读取多少条
         query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
         List<Map<String,Object>> content = total > pageable.getOffset() ? query.getResultList() : Collections.<Map<String,Object>> emptyList();

         return new PageImpl<Map<String,Object>>(content,pageable,total);
    }
    	
    /**
     * 得到一个Map对象
     * @param sql sql查询语句
     * @param values 可变查询参数
     * @return 返回一行数据库记录对应的MAP对象
     */
    @Deprecated
    public Map<String,Object> findOneReturnMapBySql(String sql,Object... values){
    	 Query query = entityManager.createNativeQuery(sql);
    	 applySQLQueryParameter(query,values);
    	 return (Map<String,Object>) query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).uniqueResult();
    }
    /**
     * 得到Map的list
     * @param sql
     * @param values
     * @return
     */
    @Deprecated
    public List<Map<String,Object>> findReturnMapList(String sql,Object... values){
    	 Query query = entityManager.createNativeQuery(sql);
    	 applySQLQueryParameter(query,values);
    	 return query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
    }
    private Integer totalCountBySql(String sql,Object... values){
    	String driverName="";
    	try {
	    	SessionImpl sessionImpl = (SessionImpl)entityManager.getDelegate();
	        Session session = sessionImpl.getSessionFactory().openSession();
	        driverName = session.connection().getMetaData().getDriverName();
    	} catch (HibernateException e) {
    		
    	} catch (SQLException e) {
    		
    	} 
    	 Integer begin = sql.toLowerCase().indexOf("select")+6;
    	 Query totalQuery = entityManager.createNativeQuery("select count(*) as counting from ("+"select top 10000000000 "+ sql.substring(begin,sql.length())+" ) a");
    	 if(driverName.indexOf("Oracle")>=0){
    		 totalQuery = entityManager.createNativeQuery("select count(*) as counting from ("+"select "+ sql.substring(begin,sql.length())+" ) a");
    	 }
    	 applySQLQueryParameter(totalQuery,values);
    	 return Integer.parseInt(totalQuery.getSingleResult().toString());
    }
    
    private void applySQLQueryParameter(Query query,Object... values){
    	 if(values!=null){
             int i = 0;
             for(Object value:values){
                 i++;
                 query.setParameter(i,value);
             }
         }
/*
         if(isDelete.get()!=null){
             query.setParameter("isDelete", isDelete.get());
         }
         if(companyId.get()!=null){
             query.setParameter("companyId",companyId.get());
         }*/
    }
    /**
     * 创建cout查询
     * @param condition where 条件子句 例如： id = ?1
     * @param values 可变的参数列表
     * @return
     */
    private TypedQuery createCountQuery(String condition, Object[] values){

        JpqlQueryHolder queryHolder = new JpqlQueryHolder(condition,values);

        return queryHolder.createCountQuery();
    }

    /**
     * 创建查询
     * @param condition where 条件子句 例如： name = ?1 and sex = '女' and age < ?2
     * @param values 参数
     * @return
     */
    private Query createQuery(String condition, Object[] values) {
        return createQuery(condition,null,values);
    }

    /**
     * 创建可排序排序的查询
     * @param condition
     * @param sort
     * @param values
     * @return
     */
    private Query createQuery(String condition, Sort sort, Object[] values) {

        JpqlQueryHolder queryHolder = new JpqlQueryHolder(condition,sort,values);

        return queryHolder.createQuery();
    }

    /**
     * 过滤数据权限
     * @param entityList
     */
    private void doFilter(Collection<T> entityList){

        final Field idtype = getIdType();

        if(null!=dataFilterType.get()){
            Collection<ID> ids = Collections2.transform(entityList, new Function<T, ID>() {
                
                public ID apply(T input) {

                    return (ID) ReflectionUtils.getField(idtype, input);
                }
            });
            if(entityList.size()!=count(idtype.getName()+" in ?1",ids)){
                throw new PlatformException("没有权限!");
            }
        }
    }

    /**
     * 过滤数据权限
     * @param entity
     */
    private void doFilter(T entity){

        if(entity==null){
            return;
        }

        Field idtype = getIdType();

        if(null!=dataFilterType.get()){
            //count(...) 会附加权限进行过滤 通过判断过滤后的数据count 来判断是否有权限
            if (count(idtype.getName()+" = ?1",ReflectionUtils.getField(idtype, entity))==0) {
                throw new PlatformException("没有权限!");
            }
        }
    }
    
    /**
     * 使用SQL进行分页查询
     * @param sql sql查询语句
     * @param sr SqlResult类型的对象，根据此对象将Object[]类型的数组转为一个java对象
     * @param page 分页参数
     * @return 分页查询结果
     */
    @ClearParamAfterMethod
    public Page<Object> findBySqlPage(String sql,SqlResult<Object> sr,Pageable page){
    	String str =  sql;
    	int index = str.indexOf("from");
    	String query = str.substring(index);
    	String countsql = "select count(*) as asfs "+query;
    	int count = (Integer)entityManager.createNativeQuery(countsql).getSingleResult();
    	
    	int currentPage = page.getPageNumber();
		int beginNum = currentPage*page.getPageSize();
		List<Object[]> list = entityManager.createNativeQuery(str).setFirstResult(beginNum)
				.setMaxResults(page.getPageSize()).getResultList();
		List<Object> res = new ArrayList<Object>();
		for(int i=0; i<list.size(); i++){
			res.add(sr.transform(list.get(i)));
		}
    	return new PageImpl<Object>(res, page, count);
    }
    

    /**
     * jpa 查询 Query对象持有者，创建的query 都会根据 unDeleted(..) companyId(..) datafilter(..) 查询参数，执行过滤
     */
    private class JpqlQueryHolder {

        //传入的condition 排除列表
        private final String[] IGNORE_CONSTAINS_CHARSEQUENCE = {"where","WHERE","from","FROM"};
        //条件
        private String condition = null;
        //排序
        private Sort sort;
        //参数数组
        private Object[] values;


        private JpqlQueryHolder(String condition, Sort sort, Object[] values) {
            this(condition,values);
            this.sort = sort;
        }

        private JpqlQueryHolder(String condition, Object[] values) {

            if(startsWithAny(condition,IGNORE_CONSTAINS_CHARSEQUENCE)){
                throw new PlatformException("查询条件中只能包含WHERE条件表达式!");
            }
            this.condition = trimToNull(condition);
            this.values = values;
        }

        //创建查询query对象
        private Query createQuery(){
            StringBuilder sb = new StringBuilder();
            // select x from table
            sb.append(getQueryString(FIND_ALL_QUERY_STRING, getEntityClass().getSimpleName()))
                    //where
                    .append(applyCondition());
            hqlLog = sb.toString();
            Query query = entityManager.createQuery(applySorting(sb.toString(), sort, DEFAULT_ALIAS));
            applyQueryParameter(query);
            return query;
        }
        //创建查询coutQuery对象
        private TypedQuery<Long> createCountQuery(){
            String ql = String.format(COUNT_QUERY_STRING, DEFAULT_ALIAS, "%s");
            ql = getQueryString(ql, getEntityClass().getSimpleName());
            ql += applyCondition();

            TypedQuery<Long> query = entityManager.createQuery(ql,Long.class);
            applyQueryParameter(query);
            return query;
        }

        //附加 unDeleted delete companyId datafilter  的查询参数
        private String applyCondition(){
        	
        	//添加统一的权限过滤
            //添加机构条件
            if(companyId.get()==null){
            	companyId();
            }
            if(dataFilterType.get() == null){
            	dataFilter("READ");
            }
            
            List<String> conditions = Lists.newArrayList();
            if(condition!=null) {
            	conditions.add(condition);
            }
            //添加删除状态条件
            if(isDelete.get()!=null)  {
                conditions.add(DEFAULT_ALIAS+"."+getDeleteType().getName()+" = :isDelete");
            }
           
           if(companyId.get()!=null) {
        	   if(getCompanyIdType()!=null){
        		   conditions.add(DEFAULT_ALIAS+"."+getCompanyIdType().getName()+" = :companyId");
        	   }else{
        		   companyId.set(null);
        	   }
            }
           
           //add by jiayq，添加分区查询
           if(partition.get()!=null){
           	conditions.add(DEFAULT_ALIAS+"."+getPartitionType().getName()+" = :partition");
           }

           
            //数据规则ql 以 or 连接
            if(null!=dataFilterType.get()){
                String filterRuleCondition = "";
                FilterContext context = new FilterContextImpl(entityManager);
                if(context!=null){
                	if(SystemContextHolder.getSessionContext()!=null && SystemContextHolder.getSessionContext().getUser()!=null){
                		filterRuleCondition = join(context.getFilterRuleJpqlList(getEntityClass(), dataFilterType.get() , SystemContextHolder.getSessionContext().getUser().getCompanyId())," or ");
                	}
                }
                //如果 规则条件返回 null 或者 "" 则 组装 为 (1=1) 否则 (fs=?1 or fs=?2 or fs=?3)
                conditions.add("("+(filterRuleCondition.equals("")?"1=1":filterRuleCondition)+")");
            }

            condition = join(conditions, " and ");
            return isEmpty(condition)?"":" where "+condition;
        }

        //附加 unDeleted delete companyId datafilter  的查询参数值
        private void applyQueryParameter(Query query){

            if(values!=null){
                int i = 0;
                for(Object value:values){
                    i++;
                    query.setParameter(i,value);
                }
            }

            if(isDelete.get()!=null){
                query.setParameter("isDelete", isDelete.get());
            }
            if(companyId.get()!=null){
                query.setParameter("companyId",companyId.get());
            }
            
            if(partition.get()!=null){
            	query.setParameter("partition", partition.get());
            }
           
           
//            query.setParameter("companyId",SystemContextHolder.getSessionContext().getUser().getCompanyId());
            if(!isEmpty(dataFilterType.get()) && SystemContextHolder.getSessionContext()!=null){
                Map<String, Object> filterParameters = SystemContextHolder.getSessionContext().getFilterParameters();
                Iterator<String> iterator = filterParameters.keySet().iterator();
                while(iterator.hasNext()){
                    String key = iterator.next();
                    if(contains(condition,":"+key)){
                        //如果规则ql中包含变量参数名称，则放入用户的上下文变量信息
                        query.setParameter(key,filterParameters.get(key));
                    }
                }
            }
        }
    }
    
    /*
     * 分区查询方法
     */
    public BaseDao<T,ID> partition(Integer partitionId){
    	this.partition.set(partitionId);
    	return this;
    }
    public BaseDao<T,ID> partition(){
    	if(this.companyId.get()!=null){
    		this.partition.set(this.companyId.get()%10);
    	}else if(SystemContextHolder.getSessionContext()!=null && SystemContextHolder.getSessionContext().getUser()!=null){
    		this.partition.set(SystemContextHolder.getSessionContext().getUser().getPartitionCompanyId());
    	}
    	return this;
    }

	public String getHqlLog() {
		return hqlLog;
	}

	public void setHqlLog(String hqlLog) {
		this.hqlLog = hqlLog;
	}
    
    
}