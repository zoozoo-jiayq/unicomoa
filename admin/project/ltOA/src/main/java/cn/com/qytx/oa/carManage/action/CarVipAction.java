package cn.com.qytx.oa.carManage.action;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.qytx.oa.carManage.service.ICarVip;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.org.domain.UserInfo;

/**
 * @author 吴胜光
 * @time 2017/10/17
 * @des 车辆vip开通、关闭
 */
public class CarVipAction extends BaseActionSupport{

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = -7285921504799345753L;
	
	/**
	 * 日志
	 */
	protected static final Logger LOGGER = Logger.getLogger(CarVipAction.class.getName());
	
	/**
	 * vip接口
	 */
	@Autowired
	private ICarVip carVipService;

	/**
	 * 车主姓名
	 */
	private String car_owner;
	
	/**
	 * 车主电话
	 */
	private String telphone;
	
	/**
	 * 车牌号
	 */
	private String car_no;
	
	/**
	 * 开通VIP接口
	 * 返回1 成功 0 失败 -1异常
	 */
	public void openVipTicket(){
		try {
			UserInfo user = getLoginUser();
			if(user!=null){
				String code = carVipService.openVipTicket(car_owner, telphone, car_no, user);
				ajax(code);
			}
		} catch (Exception e) {
			ajax("-1");
			LOGGER.error("openVipTicket error", e);
		}
	}
	
	/**
	 * 关闭VIP接口
	 * 返回1 成功 0 失败 -1异常
	 */
	public void closeVipTicket(){
		try {
			UserInfo user = getLoginUser();
			if(user!=null){
				String code = carVipService.closeVipTicket(car_no);
				ajax(code);
			}
		} catch (Exception e) {
			ajax("-1");
			LOGGER.error("closeVipTicket error", e);
		}
	}

	public String getCar_owner() {
		return car_owner;
	}

	public void setCar_owner(String car_owner) {
		this.car_owner = car_owner;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public String getCar_no() {
		return car_no;
	}

	public void setCar_no(String car_no) {
		this.car_no = car_no;
	}
	
	
	
}
