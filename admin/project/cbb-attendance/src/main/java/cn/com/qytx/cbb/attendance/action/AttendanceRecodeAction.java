package cn.com.qytx.cbb.attendance.action;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.qytx.cbb.attendance.domain.Attendance;
import cn.com.qytx.cbb.attendance.domain.AttendanceDays;
import cn.com.qytx.cbb.attendance.service.IAttendance;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Sort;
import cn.com.qytx.platform.base.query.Sort.Direction;
import cn.com.qytx.platform.base.query.Sort.Order;
import cn.com.qytx.platform.org.domain.UserInfo;

/**
 * 功能: 考勤记录 action
 * 版本: 1.0
 * 开发人员: 陈秋利
 * 创建日期: 2014-7-8
 */
public class AttendanceRecodeAction extends BaseActionSupport{
    /**  序列号 */
    private static final long serialVersionUID = 2314184560157105391L;
    /**  日志类 */
    private static final Logger logger = Logger.getLogger(AttendanceIpSetAction.class);
  
    /** 考勤 service */
    @Resource(name="attendanceService")
    IAttendance attendanceService;
    /** 考勤 service */
    @Autowired
    IAttendance attendance;
    private String beginT; //开始时间
    private String endT; //结束时间
    private Integer userId; //用户ID
    
    /**
     * 查询我的考勤
     * @return
     */
    public String getRecode(){
    	UserInfo userInfo = this.getLoginUser();
    	if(userInfo!=null){
    		int uid=userInfo.getUserId();
    		if(userId!=null&&userId.intValue()>0){
    			uid = userId;
    		}
    		 Order order = new Order(Direction.DESC,"day");
    		 Sort s = new Sort(order);
    		 if(StringUtils.isBlank(endT)){
    			 SimpleDateFormat simp = new SimpleDateFormat("yyyy-MM-dd");
    			 endT= simp.format(new Date());
    		 }
    		 Page<AttendanceDays> pageInfo = attendanceService.findCustomPage(this.getPageable(s), uid, beginT, endT);
        	 String html = PageUtil.getAjaxPageHtml(pageInfo);
        	 List<AttendanceDays> dayList = pageInfo.getContent();
        	 String contents="";
        	 if(dayList!=null&&dayList.size()>0){
                   contents = getRecode(uid,dayList);
              }
             Map<String, Object> jsonMap = new HashMap<String, Object>();
             jsonMap.put("pageHtml", html);
			 jsonMap.put("contents", contents);
             ajax(jsonMap);
    	}
    	return null;
    }
    
    /**
     * 获取打卡记录
     * @param dayList
     * @return
     */
	private String getRecode(Integer uid,List<AttendanceDays> dayList) {
		
		AttendanceDays day1 = dayList.get(0);
		AttendanceDays day2 = dayList.get(dayList.size()-1);
		String pattern="yyyy-MM-dd";
		SimpleDateFormat dateUtils = new SimpleDateFormat(pattern);
		Map<String,List<Attendance>> attMap= new HashMap<String,List<Attendance>>();
		String beginT=dateUtils.format(day2.getDay());
		String endT=dateUtils.format(day1.getDay());
		List<Attendance> list =attendance.getAttendanceRecodes(uid, beginT, endT,null,null);
		if(list!=null&&list.size()>0){
			for(Attendance atd:list){
				Timestamp crt = atd.getCreateTime();
				String cdate = dateUtils.format(crt);
				List<Attendance> listAtt = attMap.get(cdate);
				if(listAtt==null){
					listAtt = new ArrayList<Attendance>();
				}
				listAtt.add(atd);
				attMap.put(cdate, listAtt);
			}
		}
		return createHTMLContent(dayList,attMap);
		
	}
	
	/**
	 * 创建HTML内容
	 * @param dayList
	 * @return
	 */
	private String createHTMLContent(List<AttendanceDays> dayList,Map<String,List<Attendance>> attMap){
		StringBuffer stb = new StringBuffer();
		String pattern="yyyy-MM-dd";
		SimpleDateFormat dateUtils = new SimpleDateFormat(pattern);
		for(AttendanceDays attDay:dayList){
			String key=dateUtils.format(attDay.getDay());
			List<Attendance> lista = attMap.get(key);
			SimpleDateFormat weekFormat = new SimpleDateFormat("EEEE");
			String week = weekFormat.format(attDay.getDay());
			if(lista!=null&&lista.size()>0){
				stb.append("<div class=\"record\">");
				stb.append("<h4>"+key+" ("+week+")</h4>");
				stb.append("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"recordTable\">");
				 int i=1;
				  for(Attendance attendance:lista){
					  SimpleDateFormat sip = new SimpleDateFormat("HH:mm:ss");
					  String time =sip.format(attendance.getCreateTime());
					  String position = attendance.getPosition()!=null?attendance.getPosition():"";
					  stb.append("<tr>");
					  stb.append("<td style=\"width:70px;\">"+time+"</td>"); 
					  stb.append("<td style=\"width:90px;\">第"+i+"次打卡</td>");
					  stb.append("<td>"+position+"</td>");
			          stb.append("</tr>");
			          i++;
				  }
			        stb.append("</table>");
					stb.append("</div>");
			}else{
				stb.append("<div class=\"record\" >");
				stb.append("<h4>"+key+" ("+week+")</h4>");
				stb.append("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"recordTable\">");
				stb.append("<tr>");
				stb.append("<td>无打卡记录</td>");
				stb.append("<td style=\"width:90px;\"></td>");
				stb.append("<td></td>");
				stb.append("</tr>");
				stb.append("</table>");
				stb.append("</div>");
			}
		}
		return stb.toString();
	}
	
	/**
	 * @return the beginT
	 */
	public String getBeginT() {
		return beginT;
	}
	/**
	 * @param beginT the beginT to set
	 */
	public void setBeginT(String beginT) {
		this.beginT = beginT;
	}
	/**
	 * @return the endT
	 */
	public String getEndT() {
		return endT;
	}
	/**
	 * @param endT the endT to set
	 */
	public void setEndT(String endT) {
		this.endT = endT;
	}
	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
    
    
  
    
}
