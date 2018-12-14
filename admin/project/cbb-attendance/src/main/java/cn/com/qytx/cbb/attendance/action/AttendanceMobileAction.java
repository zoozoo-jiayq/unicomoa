package cn.com.qytx.cbb.attendance.action;

import java.math.BigDecimal;
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
import cn.com.qytx.cbb.attendance.domain.AttendanceSetting;
import cn.com.qytx.cbb.attendance.domain.CompanyAddress;
import cn.com.qytx.cbb.attendance.service.IAttendance;
import cn.com.qytx.cbb.attendance.service.IAttendanceSetting;
import cn.com.qytx.cbb.attendance.service.ICompanyAddress;
import cn.com.qytx.cbb.util.Distance;
import cn.com.qytx.platform.base.action.BaseActionSupport;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 新设计打卡Action
 * @ClassName: AttendanceMobileAction   
 * @author: WANG
 * @Date: 2016年6月2日 下午3:12:52   
 *
 */
public class AttendanceMobileAction extends BaseActionSupport{
	private static final long serialVersionUID = 1L;
	@Resource(name="companyAddressImpl")
	private ICompanyAddress companyAddressImpl;
	@Resource(name="attendanceSettingImpl")
	private IAttendanceSetting attendanceSettingImpl;
	@Resource(name="attendanceService")
	private IAttendance attendanceService;

	private Integer userId;//当前操作人
	private Integer companyId;//公司id
	private String position;// 位置
	private String longitude; //经度
	private String latitude; //纬度
	private Integer attType;//打卡类型  10上午上班签到   11下午上班签到   20上午下班签退 21下午下班签退
	private String memo;//备注
	private Integer year;
	private Integer month;
	/**
	 * 添加打卡信息
	 * @Title: addSign   
	 */
	public void addSign(){
		try{
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
			CompanyAddress companyAddress= companyAddressImpl.findCompanyAddress(companyId);
			AttendanceSetting attendanceSetting= attendanceSettingImpl.findAttendanceSetting(companyId);
			if(attendanceSetting==null||companyAddress==null){
				ajax("101||请先进行打卡设置");
				return;
			}
			if(attendanceSetting.getDutyType()==1&&(attType==11||attType==20)){
				ajax("101||打卡类型错误");
				return;
			}
			if(!(attType==10||attType==21||attType==11||attType==20)){
				ajax("101||打卡类型错误");
				return;
			}
			//判断是第几次打卡，及是否可以继续打卡
			List<Attendance> list=attendanceService.todayRecord(userId, new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			Map<String,Integer> map=getUnSignCount(attendanceSetting,list);
			int signIn=map.get("signIn");
			int signOut=map.get("signOut");
			if((attType==10||attType==11)&&signIn<=0){
				ajax("101||当天签到次数已用完");
				return;
			}else if((attType==20||attType==21)&&signOut<=0){
				ajax("101||当天签退次数已用完");
				return;
			}
			Attendance att=new Attendance();
			Date date=new Date();				
			att.setCreateTime(new Timestamp(date.getTime()));
			att.setCreateUserId(userId);				
			att.setPosition(position);
			att.setLatitude(latitude);
			att.setLongitude(longitude);
			att.setAttSource(2);//手机端端打卡
			att.setCompanyId(companyId);
			att.setAttType(attType);
			att.setMemo(memo);
			//获取打卡状态
			Integer attState=getAttState(userId,att,companyAddress,attendanceSetting);
			att.setAttState(attState);
			attendanceService.saveOrUpdate(att);
			ajax("100||打卡成功");
		}catch(Exception e){
			e.printStackTrace();
			ajax("102||操作异常");
		}
	}
	/**
	 * 获取当天的签到签退剩余的打卡次数及打卡信息
	 * @Title: getSignCountAndInfo   
	 */
	public void getSignCountAndInfo(){
		try{
			if(userId==null){
				ajax("101||当前操作人不能为空");
				return;
			}
			if(companyId==null){
				ajax("101||公司id不能为空");
				return;
			}
			Map<String,Object> map=new HashMap<String,Object>();
			//打卡设置
			CompanyAddress companyAddress= companyAddressImpl.findCompanyAddress(companyId);
			AttendanceSetting attendanceSetting= attendanceSettingImpl.findAttendanceSetting(companyId);
			if(companyAddress==null||attendanceSetting==null){
				ajax("101||请先进行打卡设置");
				return;
			}
			//打卡记录
			List<Attendance> list=attendanceService.todayRecord(userId, new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			//判断是否可以继续打卡
			Map<String,Integer> mapSign=getUnSignCount(attendanceSetting,list);
			int signIn=mapSign.get("signIn");
			int signOut=mapSign.get("signOut");
			map.put("companyAddress", companyAddress);
			map.put("attendanceSetting", attendanceSetting);
			map.put("list", list);
			map.put("signIn", signIn);
			map.put("signOut", signOut);
			map.put("systemTime", new Timestamp(System.currentTimeMillis()));
			Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
			ajax("100||"+gson.toJson(map));
		}catch(Exception e){
			e.printStackTrace();
			ajax("102||操作异常");
		}
	}
	/**
	 * 查看打卡详情
	 * @Title: getSignDetail   
	 */
	public void getSignDetail(){
		try{
			if(userId==null){
				ajax("101||当前操作人不能为空");
				return;
			}
			if(companyId==null){
				ajax("101||公司id不能为空");
				return;
			}
			Map<String,Object> map=new HashMap<String,Object>();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			String beginT="";
			String endT="";
			String order="asc";
			Calendar calendar=Calendar.getInstance();
			int nowYear=calendar.get(Calendar.YEAR);
			int nowMonth=calendar.get(Calendar.MONTH);
			if(year==null||month==null){
				year=nowYear;
				month=nowMonth+1;
			}
			if(year==nowYear&&month==nowMonth+1){
				order="asc";
			}else{
				order="desc";
			}
			calendar.set(Calendar.YEAR, year);
			calendar.set(Calendar.MONTH, month-1);
			int day=1;
			if(year==nowYear&&month==nowMonth+1){
				day=calendar.get(Calendar.DATE);
			}else{
				day=calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			}
			if(month<10){
				beginT=year+"-0"+month+"-01";
				if(day<10){
					endT=year+"-0"+month+"-0"+day;
				}else{
					endT=year+"-0"+month+"-"+day;
				}
				
			}else{
				beginT=year+"-"+month+"-01";
				if(day<10){
					endT=year+"-"+month+"-0"+day;
				}else{
					endT=year+"-"+month+"-"+day;
				}
			}
			//打卡记录
			List<Attendance> list=attendanceService.getAttendanceRecodes(userId, beginT, endT,null, order);
			AttendanceSetting attendanceSetting= attendanceSettingImpl.findAttendanceSetting(companyId);
			if(attendanceSetting==null){
				ajax("101||请先进行打卡设置");
				return;
			}
			Map<String,Object> attMap=new HashMap<String,Object>();
			
			int normal=0;//正常考勤
			int late=0;//迟到
			int early=0;//早退
			int noSign=0;//未打卡
			if(list!=null&&list.size()>0){
				for(Attendance att:list){
					Timestamp createTime=att.getCreateTime();
					Integer attType=att.getAttType();
					attMap.put(sdf.format(createTime)+" "+attType, att);
				}
			}
			List<Map<String,Object>> dataList=new ArrayList<Map<String,Object>>();
			if(attendanceSetting!=null){
				if(year==nowYear&&month==nowMonth+1){
					for(int i=day;i>0;i--){
						Map<String,Object> dayMap=new HashMap<String,Object>();
						String ymd=year+"-";
						if(month<10){
							ymd+="0"+month+"-";
						}else{
							ymd+=month+"-";
						}
						if(i<10){
							ymd+="0"+i;
						}else{
							ymd+=i;
						}
						dayMap=getDataMap(ymd,attMap,normal,late,early,noSign,attendanceSetting.getDutyType());
						normal=(Integer)dayMap.get("normal");
						late=(Integer)dayMap.get("late");
						early=(Integer)dayMap.get("early");
						noSign=(Integer)dayMap.get("noSign");
						dayMap.remove("normal");
						dayMap.remove("late");
						dayMap.remove("early");
						dayMap.remove("noSign");
						dataList.add(dayMap);
					}
				}else{
					for(int i=1;i<=day;i++){
						Map<String,Object> dayMap=new HashMap<String,Object>();
						String ymd=year+"-";
						if(month<10){
							ymd+="0"+month+"-";
						}else{
							ymd+=month+"-";
						}
						if(i<10){
							ymd+="0"+i;
						}else{
							ymd+=i;
						}
						dayMap=getDataMap(ymd,attMap,normal,late,early,noSign,attendanceSetting.getDutyType());
						normal=(Integer)dayMap.get("normal");
						late=(Integer)dayMap.get("late");
						early=(Integer)dayMap.get("early");
						noSign=(Integer)dayMap.get("noSign");
						dayMap.remove("normal");
						dayMap.remove("late");
						dayMap.remove("early");
						dayMap.remove("noSign");
						dataList.add(dayMap);
					}
				}
			}
			
			map.put("normal", normal);
			map.put("late", late);
			map.put("early", early);
			map.put("noSign", noSign);
			map.put("dataList", dataList);
			Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
			ajax("100||"+gson.toJson(map));
		}catch(Exception e){
			e.printStackTrace();
			ajax("102||操作异常");
		}
	}
	/**
	 * 
	 * @Title: getDataList    
	 * @return
	 */
	private Map<String, Object> getDataMap(String ymd,Map<String,Object> attMap,int normal,int late,int early,int noSign,Integer dutyType) {
		Map<String, Object> map=new HashMap<String,Object>();
		String day=ymd.substring(ymd.lastIndexOf("-")+1);
		map.put("date", day+"日");
		int normalNum=0;
		Object obj=attMap.get(ymd+" 10");
		String amOnState="";
		if(obj!=null){
			Attendance att=(Attendance)attMap.get(ymd+" 10");
			if(att.getAttState()!=null){
				if(att.getAttState()==1){
					amOnState="正常";
					normalNum+=1;
				}else if(att.getAttState()==2){
					amOnState="迟到";
					late=late+1;
				}else if(att.getAttState()==3){
					amOnState="早退";
					early=early+1;
				}else if(att.getAttState()==4){
					amOnState="外勤";
				}
			}
		}else{
			amOnState="未打卡";
			noSign=noSign+1;
		}
		map.put("amOn", amOnState);
		Object obj1=attMap.get(ymd+" 11");
		String amOffState="";
		if(obj1!=null){
			Attendance att=(Attendance)attMap.get(ymd+" 11");
			if(att.getAttState()!=null){
				if(att.getAttState()==1){
					amOffState="正常";
					normalNum+=1;
				}else if(att.getAttState()==2){
					amOffState="迟到";
					late=late+1;
				}else if(att.getAttState()==3){
					amOffState="早退";
					early=early+1;
				}else if(att.getAttState()==4){
					amOffState="外勤";
				}
			}
		}else{
			amOffState="未打卡";
			if(dutyType==2){
				noSign=noSign+1;
			}
		}
		map.put("amOff", amOffState);
		Object obj2=attMap.get(ymd+" 20");
		String pmOnState="";
		if(obj2!=null){
			Attendance att=(Attendance)attMap.get(ymd+" 20");
			if(att.getAttState()!=null){
				if(att.getAttState()==1){
					pmOnState="正常";
					normalNum+=1;
				}else if(att.getAttState()==2){
					pmOnState="迟到";
					late=late+1;
				}else if(att.getAttState()==3){
					pmOnState="早退";
					early=early+1;
				}else if(att.getAttState()==4){
					pmOnState="外勤";
				}
			}
		}else{
			pmOnState="未打卡";
			if(dutyType==2){
				noSign=noSign+1;
			}
		}
		map.put("pmOn", pmOnState);
		Object obj3=attMap.get(ymd+" 21");
		String pmOffState="";
		if(obj3!=null){
			Attendance att=(Attendance)attMap.get(ymd+" 21");
			if(att.getAttState()!=null){
				if(att.getAttState()==1){
					pmOffState="正常";
					normalNum+=1;
				}else if(att.getAttState()==2){
					pmOffState="迟到";
					late=late+1;
				}else if(att.getAttState()==3){
					pmOffState="早退";
					early=early+1;
				}else if(att.getAttState()==4){
					pmOffState="外勤";
				}
			}
		}else{
			pmOffState="未打卡";
			noSign=noSign+1;
		}
		if(dutyType==1&&normalNum==2){
			normal=normal+1;
		}else if(dutyType==2&&normalNum==4){
			normal=normal+1;
		}
		map.put("pmOff", pmOffState);
		map.put("normal", normal);
		map.put("late", late);
		map.put("early", early);
		map.put("noSign", noSign);
		return map;
	}
	/**
	 * 
	 * @Title: getAttState    
	 * @return
	 */
	private int getAttState(Integer userId,Attendance att,CompanyAddress companyAddress,AttendanceSetting attendanceSetting){
		int state=1;
		if(att!=null){
			String latitude=att.getLatitude();
			String longitude=att.getLongitude();
			if(companyAddress!=null&&attendanceSetting!=null){
				BigDecimal lng= companyAddress.getLng();
				BigDecimal lat= companyAddress.getLat();
				Integer range=attendanceSetting.getRange();
				if(StringUtils.isNotBlank(latitude)&&StringUtils.isNotBlank(longitude)&&lng!=null&&lat!=null&&range!=null){
					BigDecimal posLng=new BigDecimal(longitude);
					BigDecimal posLat=new BigDecimal(latitude);
					//计算两个经纬度直接的距离
					double distance=Distance.getDistance(lng.doubleValue(), lat.doubleValue(), posLng.doubleValue(), posLat.doubleValue());
					if(distance>range){
						//打卡属于外勤
						state=4;
						return state;
					}
				}
				//如果不是外勤，判断是1正常 2迟到 3早退
				//上午上班时间
				Timestamp amOnDuty=attendanceSetting.getAmOnDuty();
				//上午下班班时间
				Timestamp amOffDuty=attendanceSetting.getAmOffDuty();
				//下午上班时间
				Timestamp pmOnDuty=attendanceSetting.getPmOnDuty();
				//下午下班时间
				Timestamp pmOffDuty=attendanceSetting.getPmOffDuty();
				//打卡类型  10上午上班签到   11下午上班签到   20上午下班签退 21下午下班签退
				Integer attType=att.getAttType();
				//当前时间
				Timestamp nowTime=new Timestamp(System.currentTimeMillis());
				//设置时间
				Timestamp startTime=null;
				Timestamp endTime=null;
				if(attType==10){
					startTime=amOnDuty;
				}else if(attType==11){
					startTime=pmOnDuty;
				}else if(attType==20){
					endTime=amOffDuty;
				}else if(attType==21){
					endTime=pmOffDuty;
				}
				SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
				if(startTime!=null){
					if(sdf.format(startTime).compareTo(sdf.format(nowTime))<0){
						state=2;//迟到
					}else{
						state=1;//正常
					}
				}
				if(endTime!=null){
					if(sdf.format(endTime).compareTo(sdf.format(nowTime))>0){
						state=3;//早退
					}else{
						state=1;//正常
					}
				}
			}
			
		}
		return state;
	}
	
	//获取当天未打卡次数
	private Map<String,Integer> getUnSignCount(AttendanceSetting attendanceSetting,List<Attendance> list){
		Map<String,Integer> map=new HashMap<String,Integer>();
		//1一日两次，2一日4次
		Integer dutyType=attendanceSetting.getDutyType();
		int signIn=0;
		int signOut=0;
		if(dutyType==1){
			signIn=1;
			signOut=1;
		}else{
			signIn=2;
			signOut=2;
		}
		if(list!=null&&list.size()>0){
			for(Attendance att:list){
				Integer attType=att.getAttType();
				if(attType==10||attType==11){
					signIn=signIn-1;
				}else if(attType==20||attType==21){
					signOut=signOut-1;
				}
			}
		}
		if(signIn<0){
			signIn=0;
		}
		if(signOut<0){
			signOut=0;
		}
		map.put("signIn", signIn);
		map.put("signOut", signOut);
		return map;
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
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	
}
