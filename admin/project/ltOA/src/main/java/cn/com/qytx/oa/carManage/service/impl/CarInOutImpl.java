package cn.com.qytx.oa.carManage.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.qytx.cbb.car.domain.Car;
import cn.com.qytx.cbb.car.service.CarService;
import cn.com.qytx.cbb.redpoint.utils.HttpPostUtil;
import cn.com.qytx.oa.carManage.service.ICarInOut;
import cn.com.qytx.oa.carManage.service.ICarVip;
import cn.com.qytx.platform.utils.DateUtils;
import cn.com.qytx.platform.utils.PropertiesUtils;


/**
 * 功能: 车辆进场/出场接受消息接口
 * 版本: 1.0
 * 开发人员: 吴胜光
 * 创建日期: 2017年10月19日
 * 修改日期: 2017年10月19日
 * 修改列表:
 */
@Service("carInOutService")
public class CarInOutImpl implements ICarInOut{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -5697905358714136584L;
	
	@Autowired
    private CarService carService;
	
	/**
	 * vip接口
	 */
	@Autowired
	private ICarVip carVipService;

	/* (non-Javadoc)
	 * @see cn.com.qytx.oa.carManage.service.ICarInOut#carIn(java.lang.String)
	 * 根据车牌号查询车辆
	 * 判断是私家车还是公务车
	 * 如果是私家车 上班打卡
	 * 如果是公务车 调用移除VIP接口
	 */
	@Override
	public void carIn(String car_no) {
		car_no = car_no.toUpperCase();
		Car car = carService.findByNo(car_no);
		if(car != null){
			Integer fromType = car.getFromType();
			if(fromType==2){//私家车
				Properties p = PropertiesUtils.loadProperties("/application.properties");
				String basePath = p.getProperty("basePath");
				String postUrl = basePath + "/attWap/saveRecord.action";
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("attType", 10);
				param.put("companyId", car.getCompanyId());
				param.put("latitude", "34.542419");
				param.put("longitude", "113.363636");
				param.put("outOfRange", 0);
				param.put("position", "中国河南省郑州市新密市平安路");
				param.put("carType", 1);
				param.put("systermPowerTime", DateUtils.date2LongStr(new Date()));
				param.put("userId", car.getCarHostId());
				try {
					HttpPostUtil.doPost(postUrl, param);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else{//公务车
				carVipService.closeVipTicket(car_no);
			}
		}
		
	}

	/* (non-Javadoc)
	 * @see cn.com.qytx.oa.carManage.service.ICarInOut#carOut(java.lang.String)
	 * 根据车牌号查询车辆
	 * 判断是私家车还是公务车
	 * 如果是私家车 下班打卡
	 * 如果是公务车 无操作
	 */
	@Override
	public void carOut(String car_no) {
		car_no = car_no.toUpperCase();
		Car car = carService.findByNo(car_no);
		if(car != null){
			Integer fromType = car.getFromType();
			if(fromType==2){//私家车
				Properties p = PropertiesUtils.loadProperties("/application.properties");
				String basePath = p.getProperty("basePath");
				String postUrl = basePath + "/attWap/saveRecord.action";
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("attType", 21);
				param.put("companyId", car.getCompanyId());
				param.put("carType", 1);
				param.put("latitude", "34.542419");
				param.put("longitude", "113.363636");
				param.put("outOfRange", 0);
				param.put("position", "中国河南省郑州市新密市平安路");
				param.put("systermPowerTime", DateUtils.date2LongStr(new Date()));
				param.put("userId", car.getCarHostId());
				try {
					HttpPostUtil.doPost(postUrl, param);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	
}
