package cn.com.qytx.oa.productionSchedule.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.oa.productionSchedule.dao.ProductionScheduleDao;
import cn.com.qytx.oa.productionSchedule.domain.ProductionSchedule;
import cn.com.qytx.oa.productionSchedule.service.IProductionSchedule;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;

@Service
@Transactional
public class ProductionScheduleImpl extends BaseServiceImpl<ProductionSchedule> implements IProductionSchedule{
	@Autowired
	private ProductionScheduleDao productionScheduleDao;
/**
 * 分页生产过程
 */
	@Override
	public Page<ProductionSchedule> findPageByVo(Pageable page,
			String startTime, String endTime) {
		// TODO Auto-generated method stub
		return productionScheduleDao.findPageByVo(page,startTime,endTime);
	}
	
	/**
	 * 封装页面统计数据
	 */
@Override
public List<Map<String, Object>> getTotelProduction(String startTime,String endTime) {
	// TODO Auto-generated method stub
	List<Object[]> list= productionScheduleDao.getTotelProduction(startTime,endTime);
	List<Map<String, Object>> mapList = new ArrayList<Map<String,Object>>();
	if(list!=null&&list.size()>0){
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("dailyCarAssembly", list.get(0)[0]);
			map.put("stop", list.get(0)[1]);
			map.put("transportSchedule", list.get(0)[2]);
			map.put("coalLoading", list.get(0)[3]);
			map.put("middle", list.get(0)[4]);
			map.put("doorToStand", list.get(0)[5]);
			map.put("largeCoalMine", list.get(0)[6]);
			map.put("income", list.get(0)[7]);
			map.put("bothincome", list.get(0)[8]);
			map.put("smallCoalMine", list.get(0)[9]);
			map.put("sender", list.get(0)[10]);
			map.put("standDoor", list.get(0)[11]);
			map.put("groceries", list.get(0)[12]);
			map.put("useOfVehicles", list.get(0)[13]);
			map.put("deadLoad", list.get(0)[14]);
			map.put("comparedPlan", list.get(0)[15]);
			map.put("doorToDoor", list.get(0)[16]);
			map.put("sendTons", list.get(0)[17]);
			map.put("sendPlan", list.get(0)[18]);
			map.put("unload", list.get(0)[19]);
			map.put("sendPersonPlan", list.get(0)[20]);
			mapList.add(map);
	
	}
	return mapList;
}

/**
* 查询数据库是否存在该数据
* @param rTime
* @return
*/
	@Override
	public ProductionSchedule getproductionSchedule(String rTime) {
		// TODO Auto-generated method stub
		return productionScheduleDao.getproductionSchedule(rTime);
	}
}
