package cn.com.qytx.cbb.schedule.action;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import cn.com.qytx.cbb.org.util.ExportExcel;
import cn.com.qytx.cbb.schedule.service.IScheduleService;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.org.domain.UserInfo;

/**
 * 功能 导出单位内指定日期的日程内容  
 * <br/>版本  1.0
 * <br/>开发人员  zyf
 * <br/>创建日期  2016年9月30日
 * <br/>修改日期  2016年9月30日
 * <br/>修改列表
 */
public class ExportScheduleAction extends BaseActionSupport {
	private static final Logger logger = Logger.getLogger(ExportScheduleAction.class);

	@Resource
	private IScheduleService scheduleServie;
	
	private String startTime;

	private String endTime;

	/**
     * 导出考勤记录
     */
    public void exportAttendance() {
        HttpServletResponse response = this.getResponse();
        response.setContentType("application/vnd.ms-excel");
        OutputStream outp = null;
        try
        {
        	startTime = startTime.replace("月", "-").replace("日", "").replace("年", "-");
        	endTime = endTime.replace("月", "-").replace("日", "").replace("年", "-");
        	UserInfo userInfo=this.getLoginUser();
        	Integer companyId=userInfo.getCompanyId();
			List<Map<String, Object>> mapList = scheduleServie.getExportList(companyId, startTime, endTime);
		
	        String fileName = URLEncoder.encode("日程报表.xls", "UTF-8");
	        // 把联系人信息填充到map里面
	        response.addHeader("Content-Disposition",
	                "attachment;filename=" + fileName);// 解决中文
			outp = response.getOutputStream();
            ExportExcel exportExcel = new ExportExcel(outp, getExportHeadList(), mapList, getExportKeyList());
            exportExcel.exportWithSheetName("日程记录");
        }catch(Exception e){
        	logger.error(e.getMessage());
        	e.printStackTrace();
        }
    }


	private List<String> getExportHeadList(){ 
        List<String> headList = new ArrayList<String>();
        headList.add("日期");
        headList.add("姓名");
        headList.add("部门");
        headList.add("已完成日程");
        headList.add("待完成日程");
        return headList;
    }
    
    private List<String> getExportKeyList(){
        List<String> headList = new ArrayList<String>();
        headList.add("createDate");
        headList.add("userName");
        headList.add("groupName");
        headList.add("success");
        headList.add("failed");
        return headList;
    }


	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
}
