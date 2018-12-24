package cn.com.qytx.cbb.attendance.action;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import cn.com.qytx.cbb.attendance.domain.Attendance;
import cn.com.qytx.cbb.attendance.service.IAttendance;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IUser;
import cn.com.qytx.platform.utils.datetime.Lunar;

import com.google.gson.Gson;
/**
 * 
 * 功能:考勤登记
 * 版本: 1.0
 * 开发人员: 王添
 * 创建日期: 2014-7-10
 * 修改日期: 2014-7-10
 * 修改列表:
 */
public class AttendanceRegisterAction extends BaseActionSupport{
	/**  序列号 */
    private static final long serialVersionUID = 2314184560157105391L;
    /**  日志类 */
    private static final Logger logger = Logger.getLogger(AttendanceIpSetAction.class);
	//用户信息
	@Resource(name="userService")
	IUser userService;
	//打卡信息接口
	@Resource(name="attendanceService")
	IAttendance attendanceService;
	
	//当天时间yyyy-MM-dd
	private String day;
	//最后打卡时间
	private String lastTime;
	//打开限定时间
	private Long limitTime;
	
	/**
	 * 
	 * 功能：获取服务器时间
	 * @return
	 */
	public String getServiceTime(){
		PrintWriter out=null;
		try {
			out=new PrintWriter(this.getResponse().getWriter());
			Date date=new Date();
			//日期 -年月日
			SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdfMMDD=new SimpleDateFormat("MM/dd");
			String date1=sdf1.format(date);
			String dateMMDD=sdfMMDD.format(date);
			//日期-日
			SimpleDateFormat sdf2=new SimpleDateFormat("dd");
			String date2=sdf2.format(date);
			//日期-星期
			SimpleDateFormat sdf3=new SimpleDateFormat("EEEE");
			String date3=sdf3.format(date);
			//日期-农历
			String date4=new Lunar(Calendar.getInstance()).toString();
			//时间-时分秒
			SimpleDateFormat sdf4=new SimpleDateFormat("HH:mm");
			String h_m=sdf4.format(date);
			SimpleDateFormat sdf5=new SimpleDateFormat("ss");
			String seconds=sdf5.format(date);
			
			//判断打卡限制（0不能打卡,1可以打卡）
			int canDo=0;
			String ipAdd=this.getRequest().getRemoteAddr();
			//String ipAdd=InetAddress.getLocalHost().toString();
			//判断IP是否在范围
			UserInfo userInfo = this.getLoginUser();
			boolean t=attendanceService.ipCheck(ipAdd);
			if(t==true&&userInfo!=null){
				Date lastT =null;
				    SimpleDateFormat sip = new SimpleDateFormat("yyyy-MM-dd");
					String day = sip.format(new Date());
					List<Attendance> punchRecords=attendanceService.todayRecord(userInfo.getUserId(),day);
					//获取最后打卡时间
					if(punchRecords!=null&&punchRecords.size()>0){
							Attendance att = punchRecords.get(punchRecords.size()-1);
							if(att!=null){
								lastT = att.getCreateTime();
							}
					}
				if(lastT!=null){
					if(limitTime==null){
						limitTime=10l;
					}
					long a1 = date.getTime()-lastT.getTime();
					long b=limitTime*60*1000;
					if(a1<b){
						canDo=0;
					}else{
						canDo=1;
					}
				}else if(StringUtils.isEmpty(lastTime)){
					canDo=1;
				}
			}else{
				canDo=0;
			}
					
			String dateStr=date1+">"+date2+">"+date3+">"+date4+">"+h_m+">"+seconds+">"+canDo+">"+dateMMDD;
			Gson gson=new Gson();			
			String gsonStr=gson.toJson(dateStr);
			out.print(gsonStr);			
		} catch (Exception e) {
			e.printStackTrace();
			out.print("");
		}finally{
			if(out!=null){
				out.close();
			}
		}
		return null;
	}
	/**
	 * 
	 * 功能：打卡
	 * @return
	 */
	public String punchCard(){
		PrintWriter out=null;
		
		try {
			out=new PrintWriter(this.getResponse().getWriter());
			UserInfo userInfo=this.getLoginUser();
			String ipAdd=this.getRequest().getRemoteAddr();
			//判断IP是否在范围
			boolean b=attendanceService.ipCheck(ipAdd);
		if(b){				
			if(userInfo.getUserId()!=null){
				Attendance att=new Attendance();
				Date date=new Date();				
				att.setCreateTime(new Timestamp(date.getTime()));
				att.setCreateUserId(userInfo.getUserId());
				att.setCompanyId(userInfo.getCompanyId());
				ipAdd=this.getRequest().getRemoteAddr();
				att.setPosition(ipAdd);
				att.setAttSource(1);//PC端 
				attendanceService.saveOrUpdate(att);
				out.print("1");
			}
		}else{
			out.print("0");
		}
		} catch (Exception e) {
			e.printStackTrace();
			out.print("-1");
		}finally{
			if(out!=null){
				out.close();
			}
		}
		return null;
	}
	  
	
	/**
	 * 
	 * 功能：获取当天的打卡记录
	 * @return
	 */
	public String punchReport(){
		PrintWriter out = null;
		try {
			out = new PrintWriter(this.getResponse().getWriter());
			UserInfo userInfo=this.getLoginUser();
		  if(userInfo!=null){
			Integer userId=userInfo.getUserId();			
			List<Attendance> punchRecords=attendanceService.todayRecord(userId,day);
			StringBuffer a=new StringBuffer();
			if(punchRecords==null || punchRecords.isEmpty()){
				 a.append("<tr class=\"odd\">");
				 a.append("<td class=\"dataTables_empty\" vAlign=\"top\" colSpan=\"4\">" );
				 a.append("没有检索到数据");
				 a.append("</td>");
				 a.append("</tr>");
				 Gson gson1 = new Gson();
				 String noneStr=gson1.toJson(a.toString());
				 out.print(noneStr);
			}else{
				SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
				int j=0;
				Attendance att=null;
				for(int i=0;i<punchRecords.size();i++){					
					att=punchRecords.get(i);
					if(i%2==0){
						a.append("<tr class=\"odd\">");
					}else{
						a.append("<tr class=\"even\">");
					}
					a.append("<td>");
					a.append(++j);
					a.append("</td>");
					a.append("<td>");
					a.append(sdf.format(att.getCreateTime()));
					a.append("</td>");
					a.append("<td>");
					if(att.getAttSource() != null){
						if(att.getAttSource() == 1){
							a.append("电脑");
						}else{
							a.append("手机");
						}
					}else{
						a.append("&nbsp;");
					}
					a.append("</td>");
					a.append("<td class=\"right_bdr0 data_l\">");
					a.append(att.getPosition());
					a.append("</td>");
					a.append("</tr>");
					

				}		
				//获取最后打卡时间
				att=punchRecords.get(punchRecords.size()-1);
				a.append("+");
				SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				a.append(sdf1.format(att.getCreateTime()));
				
				Gson gson = new Gson();
				String gsonStr = gson.toJson(a.toString());
				out.print(gsonStr);
			
			}
			}
		} catch (Exception e) {
			e.printStackTrace();
			out.print("");
		}finally{
			if(out != null){
				out.close();
			}
		}
		return null;
	}

	public String getDay() {
		return day;
	}


	public void setDay(String day) {
		this.day = day;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public static Logger getLogger() {
		return logger;
	}
	public String getLastTime() {
		return lastTime;
	}
	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}
	public Long getLimitTime() {
		return limitTime;
	}
	public void setLimitTime(Long limitTime) {
		this.limitTime = limitTime;
	}
	
	 
}
