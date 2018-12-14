package cn.com.qytx.oa.carManage.service;

import java.io.Serializable;

import cn.com.qytx.platform.org.domain.UserInfo;

/**
 * 功能: 车辆进场/出场接受消息接口
 * 版本: 1.0
 * 开发人员: 吴胜光
 * 创建日期: 2017年10月19日
 * 修改日期: 2017年10月19日
 * 修改列表:
 */
public interface ICarInOut extends Serializable {
    
	/**
	 * 车辆进场
	 * @param car_no 车牌号
	 */
	public void carIn(String car_no);
	
	/**
	 * 车辆出场
	 * @param car_no
	 */
	public void carOut(String car_no);
}
