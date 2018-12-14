package cn.com.qytx.cbb.attendance.action;

import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;

import cn.com.qytx.cbb.attendance.domain.AttendancePlan;
import cn.com.qytx.cbb.attendance.service.AttendancePlanService;
import cn.com.qytx.cbb.attendance.vo.PlanVo;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Sort;
import cn.com.qytx.platform.base.query.Sort.Direction;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IUser;

/**
 * 功能 考勤组方案设置  
 * <br/>版本  1.0
 * <br/>开发人员  zyf
 * <br/>创建日期  2016年7月20日
 * <br/>修改日期  2016年7月20日
 * <br/>修改列表
 */
public class AttendancePlanAction extends BaseActionSupport {

	@Resource
	private AttendancePlanService planService;
	
	@Resource
	private IUser userService;
	
	private Integer planId;
	private PlanVo vo;
	private String userIds;
	
	/**
	 * 功能：获取考勤组方案列表
	 */
	public void list(){
		Sort sort = new Sort(new Sort.Order(Direction.ASC,"createTime"));
		Page<AttendancePlan> pageInfo = planService.getPlanList(getPageable(sort), getLoginUser().getCompanyId());
		List<Map<String, Object>> listMap = new ArrayList<Map<String,Object>>(); 
		if(pageInfo!=null){
			List<AttendancePlan> planList = pageInfo.getContent();
			if(planList!=null && planList.size()>0){
				int n = pageInfo.getNumber() * pageInfo.getSize() + 1;
				for(AttendancePlan plan : planList){
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("no", n);
					map.put("planId", plan.getId()+"");
					map.put("subject", plan.getSubject()==null?"":plan.getSubject());
					map.put("detail",getPlanTime(plan));
					map.put("userIds", plan.getUserIds()==null?"":plan.getUserIds());
					map.put("userCounts", "0");
					if(plan.getUserIds()!=null && !"".equals(plan.getUserIds()) && !",".equals(plan.getUserIds())){
						String uIds = plan.getUserIds();
						uIds=uIds.substring(1);
						uIds=uIds.substring(0,uIds.length()-1);
						List<UserInfo> userList=userService.findUsersByIds(uIds);
						int userCounts=0;
						if(userList!=null){
							userCounts=userList.size();
						}
						map.put("userCounts", userCounts+"");
					}
					n++;
					listMap.add(map);
				}
			}
		}
		ajaxPage(pageInfo,listMap);
	}
	
	/**
	 * 功能：根据用户ids获得用户名
	 */
	public void getUserNames(){
		String userNames="--";
		if(StringUtils.isNotEmpty(userIds)){
			userIds = userIds.replaceAll("^,|,$", "");
			if(StringUtils.isNotEmpty(userIds)){
				List<UserInfo> userList = userService.findUsersByIds(userIds); 
				if(userList!=null && userList.size()>0){
					userNames="";
					for(UserInfo u:userList){
						userNames+=u.getUserName()+"、";
					}
				}
			}
		}
		ajax(userNames.replaceAll("^、|、$", ""));
	}
	
	/**
	 * 功能：删除指定的考勤方案
	 */
	public void del(){
		AttendancePlan plan = planService.findOne(planId);
		if(plan!=null){
			plan.setIsDelete(1);
			planService.saveOrUpdate(plan);
		}
		ajax("1");
	}
	
	/**
	 * 功能：保存或者修改方案
	 */
	public void save(){
		try {
			if(vo!=null){
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
				AttendancePlan plan = new AttendancePlan();
				plan.setCreateTime(new Timestamp(System.currentTimeMillis()));
				plan.setCompanyId(getLoginUser().getCompanyId());
				plan.setCreateUserId(getLoginUser().getUserId());
				plan.setIsDelete(0);
				plan.setSubject(vo.getSubject());
				if(vo.getId()!=null){
					plan = planService.findOne(vo.getId());
				}
				plan.setUserIds(vo.getUserIds());
				plan.setLocation(vo.getLocation());
				plan.setLongitude(vo.getLongitude());
				plan.setLatitude(vo.getLatitude());
				plan.setRange(vo.getRange());
				plan.setCommonOn(new Timestamp(sdf.parse(vo.getCommonOn()).getTime()));
				plan.setCommonAmOff(new Timestamp(sdf.parse(vo.getCommonAmOff()).getTime()));
				plan.setCommonPmOn(new Timestamp(sdf.parse(vo.getCommonPmOn()).getTime()));
				plan.setCommonOff(new Timestamp(sdf.parse(vo.getCommonOff()).getTime()));
				plan.setMonRest(vo.getMonRest());
				plan.setTuesRest(vo.getTuesRest());
				plan.setWedRest(vo.getWedRest());
				plan.setThurRest(vo.getThurRest());
				plan.setFriRest(vo.getFriRest());
				plan.setSatRest(vo.getSatRest());
				plan.setSunRest(vo.getSunRest());
				if(vo.getMonRest()!=null && vo.getMonRest()==0){//周一
					plan.setMonOn(vo.getMonOn()==null?null:new Timestamp(sdf.parse(vo.getMonOn()).getTime()));
					plan.setMonOff(vo.getMonOff()==null?null:new Timestamp(sdf.parse(vo.getMonOff()).getTime()));
				}
				if(vo.getTuesRest()!=null && vo.getTuesRest()==0){//周二
					plan.setTuesOn(vo.getTuesOn()==null?null:new Timestamp(sdf.parse(vo.getTuesOn()).getTime()));
					plan.setTuesOff(vo.getTuesOff()==null?null:new Timestamp(sdf.parse(vo.getTuesOff()).getTime()));
				}
				if(vo.getWedRest()!=null && vo.getWedRest()==0){//周三
					plan.setWedOn(vo.getWedOn()==null?null:new Timestamp(sdf.parse(vo.getWedOn()).getTime()));
					plan.setWedOff(vo.getWedOff()==null?null:new Timestamp(sdf.parse(vo.getWedOff()).getTime()));
				}
				if(vo.getThurRest()!=null && vo.getThurRest()==0){//周四
					plan.setThurOn(vo.getThurOn()==null?null:new Timestamp(sdf.parse(vo.getThurOn()).getTime()));
					plan.setThurOff(vo.getThurOff()==null?null:new Timestamp(sdf.parse(vo.getThurOff()).getTime()));
				}
				if(vo.getFriRest()!=null && vo.getFriRest()==0){//周五
					plan.setFriOn(vo.getFriOn()==null?null:new Timestamp(sdf.parse(vo.getFriOn()).getTime()));
					plan.setFriOff(vo.getFriOff()==null?null:new Timestamp(sdf.parse(vo.getFriOff()).getTime()));
				}
				if(vo.getSatRest()!=null && vo.getSatRest()==0){//周六
					plan.setSatOn(vo.getSatOn()==null?null:new Timestamp(sdf.parse(vo.getSatOn()).getTime()));
					plan.setSatOff(vo.getSatOff()==null?null:new Timestamp(sdf.parse(vo.getSatOff()).getTime()));
				}
				if(vo.getSunRest()!=null && vo.getSunRest()==0){//周日
					plan.setSunOn(vo.getSunOn()==null?null:new Timestamp(sdf.parse(vo.getSunOn()).getTime()));
					plan.setSunOff(vo.getSunOff()==null?null:new Timestamp(sdf.parse(vo.getSunOff()).getTime()));
				}
				planService.save(plan);
				ajax("1");
			}else{
				ajax("2");
			}
		} catch (Exception e) {
			e.printStackTrace();
			ajax("0");
		}
	}

	/**
	 * 功能：检查用户是否在其他的考勤方案中
	 */
	public void checkUserIds(){
		try {
			Map<String,Object> result = new HashMap<String, Object>();
			result.put("successUserIds", userIds);//正常用户
			result.put("failUserIds", "");//在其他考勤组用户
			result.put("failUserNames", "");//在其他考勤组用户
			result.put("count", "0");//在其他考勤组用户的数量
			if(StringUtils.isNotEmpty(userIds)){
				String successUserIds=",";
				String failUserIds=",";
				String failUserNames="";
				int count=0;
				String[] arr = userIds.split(",");
				Map<String,String> map = planService.checkUserIds(getLoginUser().getCompanyId(), userIds, planId);
				for(String userId:arr){
					if(StringUtils.isNotEmpty(userId)){
						if(map.get(userId)!=null){//在其他考勤组里面
							failUserIds+=userId+",";
							failUserNames+=map.get(userId).toString()+",";
							count++;
						}else{
							successUserIds+=userId+",";
						}
					}
				}
				if(StringUtils.isNotEmpty(failUserNames)){
					failUserNames = failUserNames.substring(0,failUserNames.length()-1);
				}
				result.put("successUserIds", successUserIds);//正常用户
				result.put("failUserIds", failUserIds);//在其他考勤组用户
				result.put("failUserNames", failUserNames);//在其他考勤组用户
				result.put("count", count);//在其他考勤组用户的数量
			}
			ajax(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 功能：跳转到修改考勤方案页面
	 * @return
	 */
	public String toEditPlan(){
		try{
			AttendancePlan plan = planService.findOne(planId);
			if(plan!=null){
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
				vo = new PlanVo();
				vo.setId(plan.getId());
				vo.setSubject(plan.getSubject());
				vo.setUserIds(plan.getUserIds());
				vo.setLocation(plan.getLocation());
				vo.setLongitude(plan.getLongitude());
				vo.setLatitude(plan.getLatitude());
				vo.setRange(plan.getRange());
				vo.setCommonOn(sdf.format(plan.getCommonOn().getTime()));
				vo.setCommonOff(sdf.format(plan.getCommonOff().getTime()));
				vo.setCommonAmOff(sdf.format(plan.getCommonAmOff().getTime()));
				vo.setCommonPmOn(sdf.format(plan.getCommonPmOn().getTime()));
				vo.setMonRest(plan.getMonRest()==null?1:plan.getMonRest());//周一
				vo.setTuesRest(plan.getTuesRest()==null?1:plan.getTuesRest());//周二
				vo.setWedRest(plan.getWedRest()==null?1:plan.getWedRest());//周三
				vo.setThurRest(plan.getThurRest()==null?1:plan.getThurRest());//周四
				vo.setFriRest(plan.getFriRest()==null?1:plan.getFriRest());//周五
				vo.setSatRest(plan.getSatRest()==null?1:plan.getSatRest());//周六
				vo.setSunRest(plan.getSunRest()==null?1:plan.getSunRest());//周日
				vo.setMonOn(plan.getMonOn()==null?"":sdf.format(plan.getMonOn().getTime()));//周一
				vo.setMonOff(plan.getMonOff()==null?"":sdf.format(plan.getMonOff().getTime()));//周一
				vo.setTuesOn(plan.getTuesOn()==null?"":sdf.format(plan.getTuesOn().getTime()));//周二
				vo.setTuesOff(plan.getTuesOff()==null?"":sdf.format(plan.getTuesOff().getTime()));//周二
				vo.setWedOn(plan.getWedOn()==null?"":sdf.format(plan.getWedOn().getTime()));//周三
				vo.setWedOff(plan.getWedOff()==null?"":sdf.format(plan.getWedOff().getTime()));//周三
				vo.setThurOn(plan.getThurOn()==null?"":sdf.format(plan.getThurOn().getTime()));//周四
				vo.setThurOff(plan.getThurOff()==null?"":sdf.format(plan.getThurOff().getTime()));//周四
				vo.setFriOn(plan.getFriOn()==null?"":sdf.format(plan.getFriOn().getTime()));//周五
				vo.setFriOff(plan.getFriOff()==null?"":sdf.format(plan.getFriOff().getTime()));//周五
				vo.setSatOn(plan.getSatOn()==null?"":sdf.format(plan.getSatOn().getTime()));//周六
				vo.setSatOff(plan.getSatOff()==null?"":sdf.format(plan.getSatOff().getTime()));//周六
				vo.setSunOn(plan.getSunOn()==null?"":sdf.format(plan.getSunOn().getTime()));//周日
				vo.setSunOff(plan.getSunOff()==null?"":sdf.format(plan.getSunOff().getTime()));//周日
				
				int userCounts=0;
				String uIds = plan.getUserIds();
				if(StringUtils.isNotEmpty(uIds) && !",".equals(uIds)){
					uIds=uIds.substring(1);
					uIds=uIds.substring(0,uIds.length()-1);
					List<UserInfo> userList=userService.findUsersByIds(uIds);
					if(userList!=null){
						userCounts=userList.size();
					}
				}
				super.getRequest().setAttribute("count", userCounts);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	/**
	 * 功能：考勤组人员变更
	 */
	public void changeUsers(){
		try {
			AttendancePlan plan = planService.findOne(planId);
			if(plan!=null){
				plan.setUserIds(userIds);
				planService.save(plan);
				ajax("1");
			}else{
				ajax("2");
			}
		} catch (Exception e) {
			e.printStackTrace();
			ajax("0");
		}
	}
	
	/**
	 * 功能：格式化考勤方案时间
	 * @param plan
	 * @return
	 */
	private String getPlanTime(AttendancePlan plan){
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		String str = "";
		try{
			String commonOn=sdf.format(plan.getCommonOn());
			String commonOff=sdf.format(plan.getCommonOff());
			String str1="";//通用类考勤时间
			String str2="";//特殊考勤时间
			String str3="";//休息日
			String[] arr={"Mon","Tues","Wed","Thur","Fri","Sat","Sun"};
			String[] chineseArr={"一","二","三","四","五","六","日"};
			for(int i=0;i<arr.length;i++){
				String week=arr[i];
				Class clazz = plan.getClass();
				Method restMethod =clazz.getDeclaredMethod("get"+week+"Rest");
				if(restMethod.invoke(plan)!=null && (Integer)restMethod.invoke(plan)==0){//正常考勤
					Method onMethod =clazz.getDeclaredMethod("get"+week+"On");
					Method offMethod =clazz.getDeclaredMethod("get"+week+"Off");
					String thisOn="";
					if(onMethod.invoke(plan)!=null){
						thisOn=sdf.format((Timestamp) onMethod.invoke(plan));
					}else{
						thisOn=commonOn;
					}
					
					String thisOff="";
					if(offMethod.invoke(plan)!=null){
						thisOff=sdf.format((Timestamp) offMethod.invoke(plan));
					}else{
						thisOff=commonOff;
					}
					
					if(thisOn.equals(commonOn) && thisOff.equals(commonOff)){
						//通用时间规则
						if(StringUtils.isNotEmpty(str1)){
							str1+="、";
						}
						str1+=chineseArr[i];
					}else{
						//不是通用时间规则
						if(StringUtils.isNotEmpty(str2)){
							str2+="<br/>";
						}
						str2+="每周"+chineseArr[i]+" "+thisOn+"-"+thisOff;
					}
					
				}else{
					if(StringUtils.isNotEmpty(str3)){
						str3+="、";
					}
					str3+=chineseArr[i];
				}
			}
			
			if(StringUtils.isNotEmpty(str1)){
				str+="每周";
				str+=str1;
				str+=" "+commonOn+"-"+commonOff;
			}
			if(StringUtils.isNotEmpty(str2)){
				str+="<br/>";
				str+=str2;
			}
			if(StringUtils.isNotEmpty(str3)){
				str+="<br/>";
				str+="每周";
				str+=str3;
				str+="休息";
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return str;
	}
	
	public Integer getPlanId() {
		return planId;
	}

	public void setPlanId(Integer planId) {
		this.planId = planId;
	}

	public PlanVo getVo() {
		return vo;
	}

	public void setVo(PlanVo vo) {
		this.vo = vo;
	}

	public String getUserIds() {
		return userIds;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

}
