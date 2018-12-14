package cn.com.qytx.platform.base.service;

import java.util.List;

import cn.com.qytx.platform.base.dao.BaseDao;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.query.Sort;
import cn.com.qytx.platform.utils.enumeration.DataFilterType;

/**
 * service服务的接口，定义了service层的一些常用方法
 */
public interface BaseService<T>{

    /**
     * 附加单位数据约束 对于 Specification 查询模型方法无效
     */
    BaseDao<T,Integer> companyId(Integer orgId);

    /**
     * 附加当前用户的单位约束
     * @return
     */
    BaseDao<T,Integer> companyId();


    /**
     * 过滤数据权限
     * @param dataFilterType {@see xxx.yyy.sys.datafilter.OperationType}
     * @return
     */
    BaseDao<T,Integer> dataFilter(DataFilterType dataFilterType);

    /**
     * 未标志删除状态的数据 对于 Specification 查询模型方法无效
     * @return
     */
    BaseDao<T,Integer> unDeleted();

    /**
     * 已经标志为删除状态的数据 对于 Specification 查询模型方法无效
     * @return
     */
    BaseDao<T,Integer> isDeleted();


    /**
     * 保存或更新指定实体类
     *
     * @param entity  实体类
     */
    public T saveOrUpdate(T entity);
    /**
     * 保存或更新指定实体列表
     *
     * @param entities  实体类列表
     */
    public List<T> saveOrUpdate(Iterable<T> entities);

    /**
     * 删除指定实体
     * @param entity
     */
    public int delete(T entity,boolean fromDB);
    /**
     * 删除指定Integer的实体
     * @param id
     */
    public int delete(Integer id,boolean fromDB);
    public void delete(String id,boolean fromDB);
    

    /**
     * 通过ids删除对应的集合
     * @param ids
     */
    public int deleteByIds(Iterable<Integer> ids,boolean fromDB);
    public int deleteByIds(boolean fromDB,Iterable<String> ids);


    /**
     * 查找指定Integer实体类对象
     *
     * @param id
     *            实体Integer
     * @return 实体对象
     */
    public T findOne(Integer id);
    
    /**
     * 按照jpql语句查询唯一对象.
     *
     * @param condition
     *            where 条件子句 例如： id = ?1
     * @param values
     *            可变参数集合 例如：889
     * @return OBJECT对象
     */
    public T findOne(String condition, Object... values);
    /**
     * 查询所有集合
     * @return
     */
    public List<T> findAll();
    /**
     * 查询指定条件，并返回集合
     *
     * @param condition
     *             where 条件子句 例如： name = ?1 and sex = '女' and age < ?2
     * @param values
     *            可变的参数列表  例如： 李广 19
     * @return 集合
     */
    public List<T> findAll(String condition, Object... values) ;

    /**
     * 查询指定条件，并返回集合
     * @param condition where 条件子句 例如： name = ?1 and sex = '女' and age < ?2
     * @param sort 排序对象
     * @param values 可变的参数列表  例如： 李广 19
     * @return 集合
     */
    public List<T> findAll(String condition, Sort sort , Object... values);

    /**
     * 返回指定条件的分页对象
     * @param condition where 条件子句 例如： name = ?1 and sex = '女' and age < ?2
     * @param pageable 分页信息
     * @param values 参数
     * @return
     */
    public Page<T> findAll(String condition, Pageable pageable , Object... values);


    /**
     * 获取指定实体Class的记录总数
     *
     * @return 记录总数
     */
    public long count() ;

    /**
     * 获取条目数
     * @param condition where 条件子句 例如： id = ?1
     * @param values
     * @return
     */
    public long count(String condition,Object... values) ;
    
    /**
     * 功能：根据ID查询对象
     * 作者：jiayongqiang
     * 参数：
     * 输出：
     */
    public T findById(String id);
    
}
