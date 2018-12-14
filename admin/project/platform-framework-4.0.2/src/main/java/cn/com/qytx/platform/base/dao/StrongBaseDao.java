package cn.com.qytx.platform.base.dao;

import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.join;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Query;

import cn.com.qytx.platform.base.datafilter.FilterContext;
import cn.com.qytx.platform.base.datafilter.FilterContextImpl;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.PageImpl;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.query.Sort;
import cn.com.qytx.platform.base.query.Sort.Order;
import cn.com.qytx.platform.security.SystemContextHolder;

/**
 * @author jiayq
 * 增强版的BaseDao,多表关联查询的时候使用,会自动在数据权限前面添加别名
 * @param <T>
 * @param <ID>
 */
public class StrongBaseDao<T,ID extends Serializable> extends BaseDao<T, ID> {


    /**
     * 附加查询条件，按照指定单位ID查询
     * @param orgId 单位ID
     * @return 返回当前对象，方便链式调用
     */
    public StrongBaseDao<T, ID> companyId(Integer orgId) {
        super.companyId(orgId);
        return this;
    }


    /**
     * 附加查询条件，按照SystempContextHolder上下文中指定的单位ID查询
     * @return 返回当前对象，方便链式调用
     */
    public StrongBaseDao<T, ID> companyId() {
    	super.companyId();
        return this;
    }

    /**
     * 附加查询条件，使用指定条件的数据权限
     * @param dataFilterType 指定的数据权限条件
     * @return 返回当前对象，方便链式调用
     */
    public StrongBaseDao<T, ID> dataFilter(String dataFilterType) {
    	super.dataFilter(dataFilterType);
    	return this;
    }
    
    /**
     * 默认使用"READ"类型的数据权限
     * @return
     */
    public StrongBaseDao<T,ID> dataFilter(){
    	super.dataFilter();
    	return this;
    }
    
    /*
     * 分区查询方法
     */
    public StrongBaseDao<T,ID> partition(Integer partitionId){
    	super.partition(partitionId);
    	return this;
    }
    public StrongBaseDao<T,ID> partition(){
    	super.partition();
    	return this;
    }

    /**
     * 附加查询条件，查询未删除的对象
     * @return 返回当前对象，方便链式调用
     */
    public StrongBaseDao<T, ID> unDeleted() {
        super.unDeleted();
        return this;
    }

    /**
     * 附加查询条件，查询已删除的对象
     * @return 返回当前对象，方便链式调用
     */
    public StrongBaseDao<T, ID> isDeleted() {
        super.isDeleted();
        return this;
    }
	
	/**
	 * 例1：select userId,groupName from UserInfo u,GroupInfo g where u.groupId=g.groupId and ....
	 *      返回，List,每个元素是一个Object[],
	 * 例2：select new map(userId,groupName) from UserInfo u,GroupInfo g where ......
	 *      返回，List,每个元素是一个Map<String,Object>     
	 * @param <R>
	 * @param hql,
	 * @param sort
	 * @param args
	 * @return
	 */
	@ClearParamAfterMethod
	public <R> List<R> sfindAll(String hql,Sort sort,Object... args){
		return hqlDataFilterBuild(hql, sort, new ProcessQuery<List<R>>() {
			@Override
			public List<R> execute(String lastHql,String alias,Query query) {
				return query.getResultList();
			}
		}, args);
	}
	
	@ClearParamAfterMethod
	public <R> List<R> sfindAll(String hql,Sort sort){
		return this.sfindAll(hql, sort, null);
	}
	
	/**
	 * @param hql
	 * @param page
	 * @param args
	 * @return
	 */
	@ClearParamAfterMethod
	public <R> Page<R> sfindByPage(String hql,final Pageable page,final Object... args){
		return hqlDataFilterBuild(hql, page.getSort(), new ProcessQuery<PageImpl<R>>() {
			@Override
			public PageImpl<R> execute(String lastHql,String alias,Query query) {
				// TODO Auto-generated method stub
				int total = scount(lastHql,alias, args);
				query.setFirstResult(page.getOffset());//设置从起始值
				query.setMaxResults(page.getPageSize());//设置要读取多少条
				List<R> content = total > page.getOffset() ? query.getResultList() : Collections.<T> emptyList();
				return new PageImpl<R>(content, page, total);
			}
		}, args);
	}
	
	/*******************************以下是私有方法**********************************/
	
	/**
	 * 模板方法
	 * @param <R>
	 * @param hql
	 * @param sort
	 * @param args
	 */
	private <R> R hqlDataFilterBuild(String hql,Sort sort,ProcessQuery<R> callback,Object... args){
		String alias = getMainDomainsAlias(hql);
		String lastHql = appendConditions(hql,alias);
		Query query = super.entityManager.createQuery(appendOrder(lastHql, sort));
		applyQueryParameter(lastHql, query, args);
		return callback.execute(lastHql,alias,query);
	}
	
	/**
	 * 回调函数处理
	 * @author jiayongqiang
	 *
	 */
	interface ProcessQuery<R>{
		public  R execute(String lastHql,String alias,Query query);
	}
	
	/**
	 * 获取主表的别名，如果没有别名则返回""
	 * @param hql
	 * @return
	 */
	private String getMainDomainsAlias(String hql){
		String alias = "";
		String regex = "\\s+"+super.getEntityClass().getSimpleName()+"\\s+(as){0,1}\\s*([a-zA-Z]+)(where|,|left|right){0,1}";
//		String regex = "UserInfo\\s+(as){0,1}\\s*([a-zA-Z]+)(where|,|left|right){0,1}";
		Pattern p = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
		Matcher match = p.matcher(hql);
		if(match.find()){
			alias = match.group(2);
		}
		if(alias.toLowerCase().equals("where")){
			alias = "";
		}
		return alias;
	}
	
//	public static void main(String[] args) {
//		StrongBaseDao<UserInfo, Integer> sbd = new StrongBaseDao<UserInfo, Integer>();
//		String test1 = "from UserInfo ,GroupInfo where";
//		String alias = sbd.getMainDomainsAlias(test1);
//		System.out.println(test1+"===="+alias);
//		
//		String test2 = "from UserInfo u where";
//		alias = sbd.getMainDomainsAlias(test2);
//		System.out.println(test2+"===="+alias);
//		
//		String test3 = "from UserInfo u,GroupInfo g where";
//		alias = sbd.getMainDomainsAlias(test3);
//		System.out.println(test3+"===="+alias);
//		
//		String test4 = "from GroupInfo g,UserInfo u where";
//		alias = sbd.getMainDomainsAlias(test4);
//		System.out.println(test4+"===="+alias);
//		
//		String test5 = "from UserInfo as u";
//		alias = sbd.getMainDomainsAlias(test5);
//		System.out.println(test5+"===="+alias);
//		
//		String test6 = "from GroupInfo as g,UserInfo as u";
//		alias = sbd.getMainDomainsAlias(test6);
//		System.out.println(test6+"===="+alias);
//		
//		String test7 = "from UserInfo  as  u,GroupInfo as g";
//		alias = sbd.getMainDomainsAlias(test7);
//		System.out.println(test7+"===="+alias);
//	}
	
	
	/**
	 * 将 select * from UserInfo,转为 select count(*) from UserInfo 
	 * @return
	 */
	private int scount(String hql,String alias,Object... args){
		String lastHql = hql;
		int index = lastHql.toLowerCase().indexOf("from");
		if(index>=0){
			lastHql = "select count(*) as countNum "+lastHql.substring(index);
		}
		Query query = super.entityManager.createQuery(lastHql);
		applyQueryParameter(lastHql, query, args);
		String obj = query.getSingleResult().toString();
		return Integer.parseInt(obj);
	}
	
	
	/**
	 * 附件查询参数
	 * @param query
	 * @param values
	 */
	private void applyQueryParameter(String hql,Query query,Object[] values){

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
        if(!isEmpty(dataFilterType.get()) && SystemContextHolder.getSessionContext()!=null){
            Map<String, Object> filterParameters = SystemContextHolder.getSessionContext().getFilterParameters();
            Iterator<String> iterator = filterParameters.keySet().iterator();
            while(iterator.hasNext()){
                String key = iterator.next();
                if(hql.indexOf(":"+key)>=0){
                    //如果规则ql中包含变量参数名称，则放入用户的上下文变量信息
                    query.setParameter(key,filterParameters.get(key));
                }
            }
        }
    }
	
	/**
	 * 拼接附带order的hql
	 * @param hql
	 * @param sort
	 * @return
	 */
	private String appendOrder(String hql,Sort sort){
		String lastHql = hql;
		if(sort!=null){
			lastHql+=" order by ";
			List<String> orders = new ArrayList<String>();
			String ascOrDesc = "asc";
			for(Iterator<Order> it = sort.iterator(); it.hasNext();){
				Order temp = it.next();
				ascOrDesc = temp.getDirection().toString();
				orders.add(temp.getProperty());
			}
			lastHql+= join(orders,",")+" "+ascOrDesc;
		}
		return lastHql;
	}
	
	/**
	 * 拼接附带权限的HQL
	 * @param hql
	 * @return
	 */
	private String appendConditions(String hql,String alias){
		List<String> cdislist = new ArrayList<String>();
		String lastHql = hql;
		
		//初始化数据权限
		if(super.companyId.get()==null){
			companyId();
		}
		if(super.dataFilterType.get()==null){
			dataFilter("READ");
		}
		
		if(hql.toLowerCase().indexOf("where")==-1){
			lastHql+=" where 1=1 ";
		}
		//拼接查询条件
		cdislist.add(lastHql);
		
		 //添加删除状态条件
        if(isDelete.get()!=null)  {
        	cdislist.add(alias+(isEmpty(alias)?"":".")+getDeleteType().getName()+" = :isDelete");
        }
        
        //添加单位的查询条件
		if(companyId.get()!=null) {
			if(getCompanyIdType()!=null){
				   cdislist.add(alias+(isEmpty(alias)?"":".")+getCompanyIdType().getName()+" = :companyId");
			}else{
			   companyId.set(null);
			}
		}
		
		//添加分区的查询条件
		if(partition.get()!=null){
			cdislist.add(alias+(isEmpty(alias)?"":".")+getPartitionType().getName()+" = :partition");
		}
		
        //数据规则ql 以 or 连接
        if(null!=dataFilterType.get()){
            String filterRuleCondition = "";
            FilterContext context = new FilterContextImpl(entityManager,alias);
            if(context!=null){
            	if(SystemContextHolder.getSessionContext()!=null && SystemContextHolder.getSessionContext().getUser()!=null){
            		filterRuleCondition = join(context.getFilterRuleJpqlList(getEntityClass(), dataFilterType.get() , SystemContextHolder.getSessionContext().getUser().getCompanyId())," or ");
            	}
            }
            //如果 规则条件返回 null 或者 "" 则 组装 为 (1=1) 否则 (fs=?1 or fs=?2 or fs=?3)
            cdislist.add("("+(filterRuleCondition.equals("")?"1=1":filterRuleCondition)+")");
        }
        lastHql = join(cdislist, " and ");
        return lastHql;
	}
	
	
}
