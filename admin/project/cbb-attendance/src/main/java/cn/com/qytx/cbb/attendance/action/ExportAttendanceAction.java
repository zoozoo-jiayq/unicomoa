package cn.com.qytx.cbb.attendance.action;

import java.io.OutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import cn.com.qytx.cbb.attendance.service.AttendancePlanService;
import cn.com.qytx.platform.utils.ExportExcel;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.org.domain.UserInfo;

/**
 * 功能 考勤统计导出  
 * <br/>版本  1.0
 * <br/>开发人员  zyf
 * <br/>创建日期  2016年7月22日
 * <br/>修改日期  2016年7月22日
 * <br/>修改列表
 */
public class ExportAttendanceAction extends BaseActionSupport {
	private static final Logger logger = Logger.getLogger(ExportAttendanceAction.class);
	@Resource
	private AttendancePlanService planService;
	
	private String month;

	/**
     * 导出考勤记录
     */
    public void exportAttendance() {
        HttpServletResponse response = this.getResponse();
        response.setContentType("application/vnd.ms-excel");
        OutputStream outp = null;
        try
        {
        	UserInfo userInfo=this.getLoginUser();
        	Integer companyId=userInfo.getCompanyId();
        	if(StringUtils.isNoneBlank(month)){
        		month = URLDecoder.decode(month, "UTF-8");
        	}
			List<Map<String, Object>> mapList = planService.getAllRecord(companyId, month);
		
	        String fileName = URLEncoder.encode(month+"考勤报表.xls", "UTF-8");
	        // 把联系人信息填充到map里面
	        response.addHeader("Content-Disposition",
	                "attachment;filename=" + fileName);// 解决中文
			outp = response.getOutputStream();
            ExportExcel exportExcel = new ExportExcel(outp, getExportHeadList(), mapList, getExportKeyList());
            exportExcel.exportWithSheetName("考勤记录");
        }catch(Exception e){
        	logger.error(e.getMessage());
        	e.printStackTrace();
        }
    }


	private List<String> getExportHeadList(){
        List<String> headList = new ArrayList<String>();
        headList.add("姓名");
        headList.add("部门");
        headList.add("日期");
        headList.add("星期");
        headList.add("上午上班时间");
        headList.add("上午上班情况");
        headList.add("上午上班地点");
        headList.add("上午上班备注");
        
        /*headList.add("上午下班时间");
        headList.add("上午下班情况");
        headList.add("上午下班地点");
        headList.add("上午下班备注");*/
        
       /* headList.add("下午上班时间");
        headList.add("下午上班情况");
        headList.add("下午上班地点");
        headList.add("下午上班备注");*/
        
        headList.add("下午下班时间");
        headList.add("下午下班情况");
        headList.add("下午下班地点");
        headList.add("下午下班备注");
        
        return headList;
    }
    
    private List<String> getExportKeyList(){
        List<String> headList = new ArrayList<String>();
        headList.add("userName");
        headList.add("groupName");
        headList.add("createDate");
        headList.add("week");
        
        headList.add("onTime");
        headList.add("onState");
        headList.add("onPosition");
        headList.add("onMemo");
        
       /* headList.add("amOffTime");
        headList.add("amOffState");
        headList.add("amOffPosition");
        headList.add("amOffMemo");
        
        headList.add("pmOnTime");
        headList.add("pmOnState");
        headList.add("pmOnPosition");
        headList.add("pmOnMemo");*/
        
        
        headList.add("offTime");
        headList.add("offState");
        headList.add("offPosition");
        headList.add("offMemo");
        return headList;
    }
	
	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}
}
