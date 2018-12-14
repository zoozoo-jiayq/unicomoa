package cn.com.qytx.oa.productionSchedule.service;

import java.util.List;
import java.util.Map;

import cn.com.qytx.oa.productionSchedule.domain.ProductionSchedule;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.BaseService;

public interface IProductionSchedule extends BaseService<ProductionSchedule>{

	 Page<ProductionSchedule> findPageByVo(Pageable page, String startTime,String endTime);

     List<Map<String,Object>> getTotelProduction(String startTime,String endTime);

     public ProductionSchedule getproductionSchedule(String rTime);
}
