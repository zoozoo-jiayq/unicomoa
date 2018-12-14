package cn.com.qytx.platform.base.service.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.platform.base.dao.BaseDao;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.query.Sort;
import cn.com.qytx.platform.base.service.BaseService;
import cn.com.qytx.platform.utils.enumeration.DataFilterType;

/**
 * baseService的抽象实现，主要完成了basedao的初始化功能
 * @param <T>
 */
@Transactional
abstract class AbstractBaseServiceImpl<T> implements BaseService<T> ,ApplicationContextAware {

    private transient BaseDao<T,Integer> baseDao;

    private ApplicationContext applicationContext;

    @PostConstruct
    public void initRepository() {
        // 获取父类声明的泛型类
        Type type = getClass().getGenericSuperclass();
        Type[] trueType = ((ParameterizedType) type).getActualTypeArguments();
        Class<T> currentEntityClass =  (Class<T>) trueType[0];

        //找到所有的Repository bean
        String[] beanNamesForType = applicationContext.getBeanNamesForType(BaseDao.class);
        for (String beanName : beanNamesForType){
            BaseDao dao = (BaseDao) applicationContext.getBean(beanName);

            //如果当前repository的操作model类 跟 当前泛型类一致 , 则将当前respository 作为当前service的操作主repository
            if(currentEntityClass.equals(dao.getEntityClass())){
                baseDao = dao;
                break;
            }
        }

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    @Override
    public BaseDao<T, Integer> companyId(Integer orgId) {
        return baseDao.companyId(orgId);
    }

    @Override
    public BaseDao<T, Integer> companyId() {
        return baseDao.companyId();
    }

    @Override
    public BaseDao<T, Integer> dataFilter(DataFilterType dataFilterType) {
        return baseDao.dataFilter(dataFilterType.getValue());
    }

    @Override
    public BaseDao<T, Integer> unDeleted() {
        return baseDao.unDeleted();
    }

    @Override
    public BaseDao<T, Integer> isDeleted() {
        return baseDao.isDeleted();
    }

    @Override
    public T saveOrUpdate(T entity) {
        return baseDao.saveOrUpdate(entity);
    }

    @Override
    public List<T> saveOrUpdate(Iterable<T> entities) {
        return baseDao.saveOrUpdate(entities);
    }

    @Override
    public int delete(T entity, boolean fromDB) {
        return baseDao.delete(entity,fromDB);
    }

    @Override
    public int delete(Integer id, boolean fromDB) {
        return baseDao.delete(id,fromDB);
    }
    
    @Override
    public void delete(String id, boolean fromDB) {
        baseDao.delete(fromDB,id);
    }
    

    @Override
    public int deleteByIds(Iterable<Integer> ids, boolean fromDB) {
        return baseDao.deleteByIds(ids,fromDB);
    }
    @Override
    public int deleteByIds(boolean fromDB,Iterable<String> ids) {
        return baseDao.deleteByIds(fromDB,ids);
    }

    @Override
    public T findOne(Integer id) {
        return baseDao.findOne(id);
    }
    
    @Override
    public T findById(String id){
    	return baseDao.findOne(id);
    }

    @Override
    public T findOne(String condition, Object... values) {
        return baseDao.findOne(condition,values);
    }

    @Override
    public List<T> findAll() {
        return baseDao.findAll();
    }

    @Override
    public List<T> findAll(String condition, Object... values) {
        return baseDao.findAll(condition,values);
    }

    @Override
    public List<T> findAll(String condition, Sort sort, Object... values) {
        return baseDao.findAll(condition,sort,values);
    }

    @Override
    public Page<T> findAll(String condition, Pageable pageable, Object... values) {
        return baseDao.findAll(condition,pageable,values);
    }

    @Override
    public long count() {
        return baseDao.count();
    }

    @Override
    public long count(String condition, Object... values) {
        return baseDao.count(condition,values);
    }
}
