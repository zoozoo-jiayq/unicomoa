package cn.com.qytx.cbb.attendance.action;

import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;

import cn.com.qytx.cbb.attendance.domain.Attendance;
import cn.com.qytx.cbb.attendance.domain.AttendancePlan;
import cn.com.qytx.cbb.attendance.service.AttendancePlanService;
import cn.com.qytx.cbb.attendance.service.IAttendance;
import cn.com.qytx.platform.base.action.BaseActionSupport;

import com.google.gson.Gson;

/**
 * 功能 考勤手机端接口  
 * <br/>版本  1.0
 * <br/>开发人员  zyf
 * <br/>创建日期  2016年7月19日
 * <br/>修改日期  2016年7月19日
 * <br/>修改列表
 */
public class NewAttendanceWapAction extends BaseActionSupport {

	/**
	 * 描述含义
	 */
	private static final long serialVersionUID = -6923772862486093797L;
	private Integer userId;//用户id
	private Integer companyId;//单位id
	private String year;
	private String month;
	private String position;//打卡位置
	private String longitude;//经度
	private String latitude;//纬度
	private Integer attType;//打卡类型   10上午上班签到   11上午下班班    20下午上班签到 21下午下班签退     
	private Integer outOfRange;//0打卡范围内 1外勤打卡
	private String memo;
	private String systermPowerTime;
	private Integer carType;//1 代表车辆打卡
	
	@Resource
	private AttendancePlanService planService;
	
	@Resource
	private IAttendance attendanceService;
	
	/**
	 * 功能：获取用户所在考勤组信息
	 */
	public void init(){
		Map<String, Object> result = new HashMap<String, Object>();
		Gson json = new Gson();
		try {
			if(userId==null){
				ajax("101||当前操作人不能为空");
				return;
			}
			if(companyId==null){
				ajax("101||公司id不能为空");
				return;
			}
			result.put("hasPlan", "0");//默认没有考勤方案
			SimpleDateFormat format = new SimpleDateFormat("HH:mm");
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String today = format1.format(new Date());
			result.put("systemTime", today);
			
			/***********获取考勤方案***********/
			AttendancePlan plan = planService.getPlanByUserId(userId, companyId);
			if(plan!=null){
				result.put("hasPlan", "1");//默认没有考勤方案
				result.put("location", plan.getLocation()==null?"":plan.getLocation());
				result.put("longitude", plan.getLongitude()==null?"":plan.getLongitude());
				result.put("latitude", plan.getLatitude()==null?"":plan.getLatitude());
				result.put("range", plan.getRange()+"");
				result.put("signIn", "1");//可签到次数
				result.put("amSignOut", "1");//上午可签退次数
				result.put("pmSignIn", "1");//下午可签到次数
				result.put("signOut", "1");//可签退次数
				result.put("isShowOn", "1");
				result.put("isShowOff", "1");
				result.put("rest", "1");//是否需要休息
				result.put("onTime", "");
				result.put("amOffTime", "");
				result.put("pmOnTime", "");
				result.put("offTime", "");
				String week=getWeekCode(new Date());
				Class clazz = plan.getClass();
				Method restMethod =clazz.getDeclaredMethod("get"+week+"Rest");
				if(restMethod.invoke(plan)!=null && (Integer)restMethod.invoke(plan)==0){
					result.put("rest", "0");//是否需要休息
					//当天需要考勤
					result.put("onTime", format.format(plan.getCommonOn()));
					result.put("amOffTime", format.format(plan.getCommonAmOff()));
					result.put("pmOnTime",format.format(plan.getCommonPmOn()));
					result.put("offTime", format.format(plan.getCommonOff()));
					Method onMethod =clazz.getDeclaredMethod("get"+week+"On");
					if(onMethod.invoke(plan)!=null){
						result.put("onTime", format.format((Timestamp)onMethod.invoke(plan)));
					}
					
					Method offMethod =clazz.getDeclaredMethod("get"+week+"Off");
					if(offMethod.invoke(plan)!=null){
						result.put("offTime", format.format((Timestamp) offMethod.invoke(plan)));
					}
				}
				
			}
			/*Date date = new Date();
			SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
			SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");
			Long createTime = date.getTime();
			
			String pmOnTimeStr = dayFormat.format(date)+" "+timeFormat.format(plan.getCommonPmOn());
			Long pmOnTimes = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(pmOnTimeStr).getTime();
			
			String amOffTimeStr = dayFormat.format(date)+" "+timeFormat.format(plan.getCommonAmOff());
			Long amOffTimes = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(amOffTimeStr).getTime();*/
			
			/***********获取当天打卡记录*************/
			List<Attendance> list = attendanceService.todayRecord(userId, new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			List<Map<String, Object>> listMap = new ArrayList<Map<String,Object>>();
			if(list!=null && list.size()>0){
				for(Attendance attendance:list){
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("attId", attendance.getAttId()+"");
					map.put("position", attendance.getPosition()==null?"":attendance.getPosition());
					map.put("longitude", attendance.getLongitude()==null?"":attendance.getLongitude());
					map.put("latitude", attendance.getLatitude()==null?"":attendance.getLatitude());
					map.put("createTime",format1.format(attendance.getCreateTime()));
					map.put("settingTime", attendance.getSettingTime()==null?"":format.format(attendance.getSettingTime()));
					map.put("outOfRange", attendance.getOutOfRange()==null?"0":attendance.getOutOfRange()+"");//外勤
					map.put("attState", attendance.getAttState()==null?"1":attendance.getAttState()+"");//1正常 2迟到 3早退
					map.put("memo", attendance.getMemo()==null?"":attendance.getMemo());
					int type = attendance.getAttType()==null?10:attendance.getAttType();
					map.put("attType", type+"");
					if(type==10){
						//签到
						result.put("signIn", "0");//可签到次数
					}
					if(type==20){
						//上午签退次数
						result.put("amSignOut", "0");
					}
					if(type==11){
						//下午签到次数
						result.put("pmSignIn", "0");
						result.put("signIn", "0");
					}
					if(type==21){
						result.put("signOut", "0");//可签退次数
						result.put("amSignOut", "0");
					}
					listMap.add(map);
				}
			}
			result.put("list", listMap);
			ajax("100||"+json.toJson(result));
		} catch (Exception e) {
			e.printStackTrace();
			ajax("102||服务器异常");
		}
	}

	/**
	 * 功能：打卡
	 */
	public void saveRecord(){
		try {
			if(userId==null){
				ajax("101||当前操作人不能为空");
				return;
			}
			if(companyId==null){
				ajax("101||公司id不能为空");
				return;
			}
			if(StringUtils.isBlank(position)){
				ajax("101||打卡位置不能为空");
				return;
			 }
			 
			if(StringUtils.isBlank(longitude)){
				ajax("101||打卡位置的经度不能为空");
				return;
			 }
			if(StringUtils.isBlank(latitude)){
				ajax("101||打卡位置的纬度不能为空");
				return;
			 }
			if(attType==null){
				ajax("101||打卡类型不能为空");
				return;
			}
			if(outOfRange==null){
				outOfRange=0;
			}
			Map<String,Object> result = new HashMap<String, Object>();
			Gson json = new Gson();
			
			Date date = new Date();
			if(StringUtils.isNotEmpty(systermPowerTime)){
				date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(systermPowerTime);
			}
			
			Attendance attendance = new Attendance();
			attendance.setCreateTime(new Timestamp(date.getTime()));
			attendance.setCreateUserId(userId);
			attendance.setCompanyId(companyId);
			attendance.setAttSource(2);
			attendance.setAttState(1);//这里需要判断当前打卡是否是迟到早退
			AttendancePlan plan = planService.getPlanByUserId(userId, companyId);
			if(plan!=null){
				/**********判断用户迟到早退************/
				String week=getWeekCode(date);
				Class clazz = plan.getClass();
				Method restMethod =clazz.getDeclaredMethod("get"+week+"Rest");
				
				SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
				SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");
				Long createTime = date.getTime();
				
				//String pmOnTimeStr = dayFormat.format(date)+" "+timeFormat.format(plan.getCommonPmOn());
				//Long pmOnTimes = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(pmOnTimeStr).getTime();
				
				//String amOffTimeStr = dayFormat.format(date)+" "+timeFormat.format(plan.getCommonAmOff());
				//Long amOffTimes = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(amOffTimeStr).getTime();
				/*if(attType==10&&createTime>amOffTimes){
					attType = 11;
				}
				
				if(attType==21&&createTime<pmOnTimes){
					attType = 20;
				}
				
				if(attType==20&&createTime>pmOnTimes){
					attType = 21;
				}*/
				if(restMethod.invoke(plan)!=null && (Integer)restMethod.invoke(plan)==0){//正常考勤
					//当天需要考勤
					if(attType==10){
						//上午上班考勤
						Timestamp onTime = plan.getCommonOn();
						Method onMethod =clazz.getDeclaredMethod("get"+week+"On");
						if(onMethod.invoke(plan)!=null){
							onTime = (Timestamp)onMethod.invoke(plan);
						}
						attendance.setSettingTime(onTime);
						String onTimeStr = dayFormat.format(date)+" "+timeFormat.format(onTime);
						Long onTimes = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(onTimeStr).getTime();
						if(createTime>onTimes){
							attendance.setAttState(2);//迟到
						}
					}else if(attType==11){
						Timestamp pmOnTime = plan.getCommonPmOn();
						attendance.setSettingTime(pmOnTime);
						String onTimeStr = dayFormat.format(date)+" "+timeFormat.format(pmOnTime);
						Long onTimes = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(onTimeStr).getTime();
						if(createTime>onTimes){
							attendance.setAttState(2);//迟到
						}
					}else if(attType==20){
						Timestamp amOffTime = plan.getCommonAmOff();
						attendance.setSettingTime(amOffTime);
						String offTimeStr = dayFormat.format(date)+" "+timeFormat.format(amOffTime);
						Long offTimes = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(offTimeStr).getTime();
						if(createTime<offTimes){
							attendance.setAttState(3);//上午早退
						}
					}else if(attType==21){
						//下午下班考勤
						Timestamp offTime = plan.getCommonOff();
						Method offMethod =clazz.getDeclaredMethod("get"+week+"Off");
						if(offMethod.invoke(plan)!=null){
							offTime=(Timestamp) offMethod.invoke(plan);
						}
						attendance.setSettingTime(offTime);
						String offTimeStr = dayFormat.format(date)+" "+timeFormat.format(offTime);
						Long offTimes = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(offTimeStr).getTime();
						if(createTime<offTimes){
							attendance.setAttState(3);//早退
						}
					}
				}else{//休息日考勤
					attendance.setAttState(7);//加班
				}
				
				attendance.setAttType(attType);
				attendance.setOutOfRange(outOfRange);
				if(StringUtils.isNotEmpty(memo) && !"".equals(memo.trim())){
					attendance.setMemo(memo);
				}
				attendance.setPosition(position);
				attendance.setLatitude(latitude);
				attendance.setLongitude(longitude);
				attendance.setCarType(carType);
				attendanceService.save(attendance);
				
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				result.put("createTime",format.format(attendance.getCreateTime()));
				result.put("outOfRange", attendance.getOutOfRange()+"");//外勤
				result.put("attState", attendance.getAttState()+"");//1正常 2迟到 3早退
				ajax("100||"+json.toJson(result));
			}else{
				ajax("103||该用户不在考勤组内");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			ajax("102||服务器异常");
		}
	}
	
	/**
	 * 功能：获得打卡记录
	 */
	public void recordReport(){
		try{
			if(userId==null){
				ajax("101||当前操作人不能为空");
				return;
			}
			if(companyId==null){
				ajax("101||公司id不能为空");
				return;
			}
			if(StringUtils.isEmpty(year)||StringUtils.isEmpty(month)){
				ajax("101||日期格式不正确");
				return;
			}
			Map<String, Object> map = planService.getRecordReport(userId, year+"-"+month,companyId);
			Gson json = new Gson();
			ajax("100||"+json.toJson(map));
		} catch (Exception e) {
			e.printStackTrace();
			ajax("102||服务器异常");
		}
	}
	
	/**
	 * 功能：获取当前星期
	 * @param date
	 * @return
	 */
	private int getWeek(Date date){
		Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK)-1;
        return w;
	}
	
	/**
	 * 功能：获取当前星期
	 * @param date
	 * @return
	 */
	private String getWeekCode(Date date){
		int w = getWeek(date);
		String str = "Mon";
		switch (w) {
			case 1:
				str="Mon";
				break;
			case 2:
				str="Tues";
				break;
			case 3:
				str="Wed";
				break;
			case 4:
				str="Thur";
				break;
			case 5:
				str="Fri";
				break;
			case 6:
				str="Sat";
				break;
			case 0:
				str="Sun";
				break;
			default:
				break;
		}
		return str;
	}
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public Integer getAttType() {
		return attType;
	}

	public void setAttType(Integer attType) {
		this.attType = attType;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public Integer getOutOfRange() {
		return outOfRange;
	}

	public void setOutOfRange(Integer outOfRange) {
		this.outOfRange = outOfRange;
	}

	public String getSystermPowerTime() {
		return systermPowerTime;
	}

	public void setSystermPowerTime(String systermPowerTime) {
		this.systermPowerTime = systermPowerTime;
	}

	public Integer getCarType() {
		return carType;
	}

	public void setCarType(Integer carType) {
		this.carType = carType;
	}
	
	
}
