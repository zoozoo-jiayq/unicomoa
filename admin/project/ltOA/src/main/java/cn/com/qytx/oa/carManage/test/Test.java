package cn.com.qytx.oa.carManage.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;

import cn.com.qytx.oa.carManage.util.CommonClass;
import cn.com.qytx.oa.util.TimeUtil;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.utils.PropertiesUtils;

public class Test {

	public static void main(String[] args) {
		UserInfo user = new UserInfo();
		user.setUserName("张三");
		//String code = openVipTicket("吴胜光12", "13838196122", "豫ABU227", user);
		//System.out.println(code);
		//closeVipTicket("吴胜光2", "13838196112", user);
		//ADD_CUS_VIP();
		carInTest("豫cw7m59");
		//carOutTest("豫SJC002");
		//String vip_ticket_seq = GET_VIP_CAR("豫E00221");
		//REFUND_VIP_TICKET(vip_ticket_seq);
		//System.out.println(vip_ticket_seq);
		//System.out.println("豫Sjc002".toUpperCase());
	}
	
	public static String openVipTicket(String car_owner, String telphone, String car_no, UserInfo user) {
		String code = "-1";
		try {
			Date date = new Date();
			//当前时间/vip开始时间
			String start_time = TimeUtil.dateToStr(date,"yyyy-MM-dd HH:mm:ss");
			String ticket_no = TimeUtil.dateToStr(date,"yyyyMMddHHmmssSSS");
			date.setYear(date.getYear()+1);
			//vip结束时间 默认开通一年
			String end_time = TimeUtil.dateToStr(date,"yyyy-MM-dd HH:mm:ss");
			JSONObject param = CommonClass.setRequestHeadParam("OPEN_VIP_TICKET");
			JSONObject biz_content = new JSONObject();
			biz_content.element("vip_type_name", "222222");//VIP类型名称
			biz_content.element("ticket_no", ticket_no);//票唯一编号  ticket_no 作为月卡缴费单号
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
			HttpEntity entity = CommonClass.postMethod(carUrl, param,"");
			JSONObject result = JSONObject.fromObject(EntityUtils.toString(entity, "UTF-8"));
			if (result != null) {
				JSONObject content = JSONObject.fromObject(result.get("biz_content"));
				if(content != null){
					code = (String) content.get("code");
				}
			}
		} catch (Exception e) {
			code = "-1";
		}
		return code;
	}
	
	
	public static String closeVipTicket(String car_owner, String telphone, UserInfo user) {
		String code = "-1";
		try {
			Date date = new Date();
			//当前时间
			String start_time = TimeUtil.dateToStr(date,"yyyy-MM-dd HH:mm:ss");
			JSONObject param = CommonClass.setRequestHeadParam("DEL_CAR_OWNER");
			JSONObject biz_content = new JSONObject();
			biz_content.element("owner_id", "");//车主ID  车主ID不为空，则根据其去查找车主。否则根据姓名和手机号码去查找车主
			biz_content.element("name", car_owner);//车主姓名
			biz_content.element("telphone", telphone);//车主电话
			biz_content.element("operator", user.getUserName());//操作员
			biz_content.element("operate_time", start_time);//操作时间
			param.element("biz_content", biz_content); 
			//String requestUrl = "http://221.4.168.157:19093/vems/cxfService/external/extReq";
			Properties properties = PropertiesUtils.loadProperties("/application.properties");
			String carUrl = properties.getProperty("carUrl");
			HttpEntity entity = CommonClass.postMethod(carUrl, param,"");
			JSONObject result = JSONObject.fromObject(EntityUtils.toString(entity, "UTF-8"));
			if (result != null) {
				JSONObject content = JSONObject.fromObject(result.get("biz_content"));
				if(content != null){
					code = (String) content.get("code");
				}
			}
		} catch (Exception e) {
			code = "-1";
		}
		return code;
	}
	
	public static String ADD_CUS_VIP() {
		String code = "-1";
		try {
			JSONObject param = CommonClass.setRequestHeadParam("ADD_CUS_VIP");
			JSONObject biz_content = new JSONObject();
			biz_content.element("code", "111111");//VIP类型编号
			biz_content.element("name", "222222");//VIP类型名称
			biz_content.element("settle_type", "0");
			biz_content.element("settle_price", "0.00");
			biz_content.element("is_dynamic", "0");
			biz_content.element("carports", "1");
			biz_content.element("is_date_pri", "0");
			biz_content.element("is_time_pri", "0");
			biz_content.element("is_custom_charge", "0");
			biz_content.element("charge_group_code", "");
			biz_content.element("is_full_limit", "0");
			biz_content.element("full_limit_value", "1");
			biz_content.element("exp_alert_value", "30");
			param.element("biz_content", biz_content); 
			//String requestUrl = "http://221.4.168.157:19093/vems/cxfService/external/extReq";
			Properties properties = PropertiesUtils.loadProperties("/application.properties");
			String carUrl = properties.getProperty("carUrl");
			HttpEntity entity = CommonClass.postMethod(carUrl, param,"");
			JSONObject result = JSONObject.fromObject(EntityUtils.toString(entity, "UTF-8"));
			if (result != null) {
				JSONObject content = JSONObject.fromObject(result.get("biz_content"));
				if(content != null){
					code = (String) content.get("code");
				}
			}
		} catch (Exception e) {
			code = "-1";
		}
		return code;
	}
	
	public static String GET_VIP_CAR(String car_no ) {
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
								if(oneInfo.getString("vip_type_name").equals("222222")){
									vip_ticket_seq = (String)oneInfo.get("vip_ticket_seq");
									break;
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			
		}
		return vip_ticket_seq;
	}
	
	public static String REFUND_VIP_TICKET(String vip_ticket_seq){
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
	
	public static String carInTest(String car_no) {
		String code = "-1";
		try {
			JSONObject param = CommonClass.setRequestHeadParam("REPORT_CAR_IN_LIST");
			JSONObject biz_content = new JSONObject();
			biz_content.element("car_license_number", car_no);
			biz_content.element("correct_confidence", "0");
			biz_content.element("correct_type", "0");
			biz_content.element("enter_car_card_number", "");
			param.element("biz_content", biz_content); 
			String requestUrl = "http://bysj.s1.natapp.cc/cloudoa/carManage/carInOut.action?_clientType=wap";
			HttpEntity entity = CommonClass.postMethod(requestUrl, param,"");
			String result = EntityUtils.toString(entity, "UTF-8");
			System.out.println("result："+result);
		} catch (Exception e) {
			code = "-1";
		}
		return code;
	}

	public static String carOutTest(String car_no) {
		String code = "-1";
		try {
			JSONObject param = CommonClass.setRequestHeadParam("REPORT_CAR_OUT_LIST");
			JSONObject biz_content = new JSONObject();
			biz_content.element("car_license_number", car_no);
			biz_content.element("correct_confidence", "0");
			biz_content.element("correct_type", "0");
			biz_content.element("enter_car_card_number", "");
			param.element("biz_content", biz_content); 
			String requestUrl = "http://bysj.s1.natapp.cc/cloudoa/carManage/carInOut.action?_clientType=wap";
			HttpEntity entity = CommonClass.postMethod(requestUrl, param,"");
			String result = EntityUtils.toString(entity, "UTF-8");
			System.out.println("result："+result);
		} catch (Exception e) {
			code = "-1";
		}
		return code;
	}
}
