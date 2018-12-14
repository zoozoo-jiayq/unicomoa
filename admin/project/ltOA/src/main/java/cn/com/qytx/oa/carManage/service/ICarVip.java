package cn.com.qytx.oa.carManage.service;

import java.io.Serializable;

import cn.com.qytx.platform.org.domain.UserInfo;

/**
 * 功能:车辆VIP service
 * 版本: 1.0
 * 开发人员: 吴胜光
 * 创建日期: 2017-10-19
 * 修改日期: 2017-10-19
 * 修改列表:
 */
public interface ICarVip extends Serializable {
    
	/**
	 * 开通VIP接口
	 * @param car_owner 车主姓名
	 * @param telphone 车主电话
	 * @param car_no 车牌号
	 * @param user 当前登录用户
	 * @return 0成功 1 失败 -1异常
	 */
	public String openVipTicket(String car_owner, String telphone, String car_no, UserInfo user);
	
	/**
	 * 关闭VIP接口（移除车主）
	 * @param car_no 车牌照
	 * @return 0成功 1失败 -1异常
	 */
	public String closeVipTicket(String car_no);

}
