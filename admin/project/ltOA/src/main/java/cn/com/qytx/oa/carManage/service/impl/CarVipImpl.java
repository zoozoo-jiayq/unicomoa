package cn.com.qytx.oa.carManage.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.com.qytx.oa.carManage.service.ICarVip;
import cn.com.qytx.oa.carManage.util.CommonClass;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.utils.PropertiesUtils;
import cn.com.qytx.platform.utils.TimeUtil;

/**
 * @author 吴胜光
 * @time 2017/10/18
 * @des 车辆vip开通、关闭
 */
@Service("carVipService")
public class CarVipImpl implements ICarVip{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -931794755172837454L;
	
	/**
	 * 日志
	 */
	protected static final Logger LOGGER = Logger.getLogger(CarVipImpl.class.getName());
	
	/**
	 * vip票名称 （需要先调用增加vip类型增加）
	 */
	public static final String VIP_TYPE_NAME = "gwc";

	@Override
	public String openVipTicket(String car_owner, String telphone, String car_no, UserInfo user) {
		String code = "-1";
		try {
			LOGGER.info("openVipTicket begin car_owner:"+car_owner+";telphone"+telphone+";car_no"+car_no);
			car_no = car_no.toUpperCase();
			car_owner = "公务车";
			telphone = "13838196113";
			Date date = new Date();
			//当前时间/vip开始时间
			String start_time = TimeUtil.dateToStr(date,"yyyy-MM-dd HH:mm:ss");
			date.setYear(date.getYear()+1);
			//vip结束时间 默认开通一年
			String end_time = TimeUtil.dateToStr(date,"yyyy-MM-dd HH:mm:ss");
			JSONObject param = CommonClass.setRequestHeadParam("OPEN_VIP_TICKET");
			JSONObject biz_content = new JSONObject();
			biz_content.element("vip_type_name", VIP_TYPE_NAME);//VIP类型名称
			biz_content.element("car_owner", car_owner);//车主姓名
			biz_content.element("telphone", telphone);//车主电话
			biz_content.element("operator", user.getUserName());//操作员
			biz_content.element("operate_time", start_time);//操作时间
			biz_content.element("original_price", "100");//原价
			biz_content.element("discount_price", "0");//折后价
			biz_content.element("open_value", "1");//开通值（ 时间类型，填月数；余额类型，填储值）
			biz_content.element("open_car_count", "1");//车位数
			//车辆列表
			List<JSONObject> car_list = new ArrayList<JSONObject>();
			JSONObject car = new JSONObject();
			car.element("car_no", car_no);
			car_list.add(car);
			biz_content.element("car_list", car_list);
			//有效时间段列表
			List<JSONObject> time_period_list = new ArrayList<JSONObject>();
			JSONObject time_period = new JSONObject();
			time_period.element("start_time", start_time);
			time_period.element("end_time", end_time);
			time_period_list.add(time_period);
			biz_content.element("time_period_list", time_period_list);
			
			param.element("biz_content", biz_content); 
			//String requestUrl = "http://221.4.168.157:19093/vems/cxfService/external/extReq";
			Properties properties = PropertiesUtils.loadProperties("/application.properties");
			String carUrl = properties.getProperty("carUrl");
			LOGGER.info("openVipTicket param:"+param.toString());
			HttpEntity entity = CommonClass.postMethod(carUrl, param,"");
			JSONObject result = JSONObject.fromObject(EntityUtils.toString(entity, "UTF-8"));
			LOGGER.info("openVipTicket result:"+result.toString());
			if (result != null) {
				JSONObject content = JSONObject.fromObject(result.get("biz_content"));
				if(content != null){
					code = (String) content.get("code");
				}
			}
		} catch (Exception e) {
			code = "-1";
			LOGGER.error("openVipTicket error", e);
		}
		return code;
	}

	@Override
	public String closeVipTicket(String car_on) {
		String code = "1";
		String vip_ticket_seq = getVipTicket(car_on);
		if(StringUtils.isNoneBlank(vip_ticket_seq)){
			code = refundVipTicket(vip_ticket_seq);
		}
		return code;
	}
	
	/**
	 * 获取VIP票序列号
	 * @param car_no
	 * @return
	 */
	private String getVipTicket(String car_no) {
		String vip_ticket_seq = "";
		try {
			JSONObject param = CommonClass.setRequestHeadParam("GET_VIP_TICKET");
			JSONObject biz_content = new JSONObject();
			biz_content.element("car_no", car_no);
			biz_content.element("page_num", "1");
			biz_content.element("page_size", "100");
			param.element("biz_content", biz_content); 
			//String requestUrl = "http://221.4.168.157:19093/vems/cxfService/external/extReq";
			Properties properties = PropertiesUtils.loadProperties("/application.properties");
			String carUrl = properties.getProperty("carUrl");
			HttpEntity entity = CommonClass.postMethod(carUrl, param,"");
			JSONObject result = JSONObject.fromObject(EntityUtils.toString(entity, "UTF-8"));
			
			if (result != null) {
				JSONObject content = JSONObject.fromObject(result.get("biz_content"));
				if(content != null){
					String code = content.getString("code");
					if (content.size() > 0 && Integer.parseInt(code)==0 ) {
						JSONArray ticket_list = JSONArray.fromObject(content.get("ticket_list"));
						if (ticket_list !=null && ticket_list.size()>0) {
							for ( int i =0;i < ticket_list.size(); i++) {
						 		JSONObject oneInfo = ticket_list.getJSONObject(i);
								if(VIP_TYPE_NAME.equals(oneInfo.getString("vip_type_name")) && oneInfo.getString("ticket_status").equals("生效中")){
									vip_ticket_seq = oneInfo.getString("vip_ticket_seq");
									break;
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vip_ticket_seq;
	}
	
	/**
	 * VIP票退费
	 * @param vip_ticket_seq
	 * @return 0成功 1 失败
	 */
	private String refundVipTicket(String vip_ticket_seq){
		try {
			JSONObject param = CommonClass.setRequestHeadParam("REFUND_VIP_TICKET");
			JSONObject biz_content = new JSONObject();
			biz_content.element("vip_ticket_seq", vip_ticket_seq);
			biz_content.element("refund_price", "0");
			biz_content.element("operator", "系统操作");
			biz_content.element("operate_time", TimeUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss"));
			param.element("biz_content", biz_content); 
			
			//String requestUrl = "http://221.4.168.157:19093/vems/cxfService/external/extReq";
			Properties properties = PropertiesUtils.loadProperties("/application.properties");
			String carUrl = properties.getProperty("carUrl");
			HttpEntity entity = CommonClass.postMethod(carUrl, param,"");
			JSONObject result = JSONObject.fromObject(EntityUtils.toString(entity, "UTF-8"));
			
			if (result != null) {
				JSONObject content = JSONObject.fromObject(result.get("biz_content"));
				if(content != null){
					String code = content.getString("code");
					if (content.size() > 0 ) {
						return code;
					}
				}
			}
		} catch (Exception e) {
			return "1";
		}
		return "1";
	}

}
