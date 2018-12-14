package cn.com.qytx.oa.productionSchedule.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import cn.com.qytx.oa.productionSchedule.domain.ProductionSchedule;
import cn.com.qytx.platform.base.dao.BaseDao;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;

@Component
public class ProductionScheduleDao extends BaseDao<ProductionSchedule, Integer> implements Serializable{

	/**
	 * 生产过程分页
	 * @param page
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Page<ProductionSchedule> findPageByVo(Pageable page,
			String startTime, String endTime){
		StringBuilder hql = new StringBuilder();
		hql.append(" isDelete=0");
		List<Object> params = new ArrayList<Object>();
		   if(startTime!=null&&!"".equals(startTime)){
			   hql.append(" and rTime >= '"+startTime+"' ");
            }
            if(endTime!=null&&!"".equals(endTime)){
            	endTime = endTime + " 23:59:59";
            	hql.append(" and rTime <= '"+endTime+"' ");
            }
            return super.findAll( hql.toString(),page, params.toArray());	
		
	}
	
	
	/**
	 * 统计生产过程
	 * @return
	 */
	public List<Object[]> getTotelProduction(String startTime,String endTime){
		StringBuilder sql = new StringBuilder();
		 sql.append("select ISNULL(SUM(daily_car_assembly), 0) a,ISNULL(SUM(stop), 0) b,ISNULL(SUM(transport_schedule), 0) c,"
					+ "ISNULL(SUM(coal_loading), 0) d,ISNULL(SUM(middle), 0) e,ISNULL(SUM(door_to_stand), 0) f,ISNULL(SUM(large_coal_mine), 0) g,"
					+ "ISNULL(SUM(income), 0) h,ISNULL(SUM(bothincome), 0) i,"
					+ "ISNULL(SUM(small_coal_mine), 0) j,ISNULL(SUM(sender), 0) k,"
					+ "ISNULL(SUM(stand_door), 0) l,ISNULL(SUM(groceries), 0) m,ISNULL(SUM(use_of_vehicles), 0) n,ISNULL(SUM(dead_load), 0) o,"
					+ "ISNULL(SUM(compared_plan), 0) p,ISNULL(SUM(door_to_door), 0) q,ISNULL(SUM(send_tons), 0) r,ISNULL(SUM(send_plan), 0) s,"
					+ "ISNULL(SUM(unload), 0) t,ISNULL(SUM(send_person_plan), 0) u from tb_production_schedule where is_delete=0");
		   if(startTime!=null&&!"".equals(startTime)){
			   sql.append(" and rTime >= '"+startTime+"' ");
            }
            if(endTime!=null&&!"".equals(endTime)){
            	endTime = endTime + " 23:59:59";
            	sql.append(" and rTime <= '"+endTime+"' ");
            }
		List<Object[]> productionScheduleList =  this.entityManager.createNativeQuery(sql.toString()).getResultList();
	    return productionScheduleList;
	}
	
	
/**
 * 查询数据库是否存在该数据
 * @param rTime
 * @return
 */
	public ProductionSchedule getproductionSchedule(String rTime){

		String sql = " from ProductionSchedule where Convert(varchar,rTime,120)  LIKE '%"+rTime+"%' and isDelete=0";
		List<ProductionSchedule> productionScheduleList =  this.entityManager.createQuery(sql).getResultList();
		if(productionScheduleList!=null&&productionScheduleList.size()>0){
			return productionScheduleList.get(0);
		}else{
			return null;
		}
	}
}
