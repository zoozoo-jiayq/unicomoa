package cn.com.qytx.cbb.log.action;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import cn.com.qytx.cbb.consts.CbbConst;
import cn.com.qytx.cbb.org.util.ExportExcel;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.log.domain.Log;
import cn.com.qytx.platform.log.service.ILog;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IGroup;
import cn.com.qytx.platform.utils.datetime.DateTimeUtil;

import com.google.gson.Gson;


/**
 * 功能: 日志action
 * 版本: 1.0
 * 开发人员: 冯东旭
 * 创建日期: 2013-3-21
 * 修改日期: 2013-3-21
 * 修改列表:
 */
public class LogAction  extends BaseActionSupport {
	private static final long serialVersionUID = 1L;
    /** 最大显示条数*/
    private Integer max;

    /** 系统日志接口 */
    @Resource(name = "logService")
    private  ILog logImpl;
    
    @Resource(name = "groupService")
    private IGroup groupService;
    
	/**查询条件 start*/
    private Integer logType;
    private String userIds;
    private String startTime;
    private String endTime;
    private String ip;
    private String remark;
    
    private String userName;
    /**查询条件 start*/
    
    //日志ID
    private Integer logId;
    //日志IDs
    private String logIds;
	/**
	 * 待删除的ID集合
	 */
	private String deleteIds;
	
    public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
     * 批量删除日志
     * @return
     */
    public String deleteListByIds(){
    	String[] ids = deleteIds.split(",");
    	for(int i=0;i<ids.length;i++){
    		logImpl.delete(Integer.parseInt(ids[i]),false);
    	}
    	ajax("0");
		return null;
    }

	/**
	 * 日志查询
	 * @return
	 */
	public String getLogList(){
			UserInfo userInfo = (UserInfo) getSession().getAttribute("adminUser");
			if(userIds!=null&&!userIds.equals("")){
				userIds = userIds.substring(1, userIds.length()-1);
			}
			String  gId = getGroupIds(userInfo.getUserId());
			List<Log> list =logImpl.getLogListByParam(max, logType, userIds, startTime, endTime, ip, remark,userName,gId);
			List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
			String nullString="&nbsp;";
			int orderNumber=0;	//序号
			Map<String, Object> map = null;
			if (list != null) {
	            for(Log  l: list) {
	            	orderNumber++;
	            	map = new HashMap<String, Object>();
	                map.put("id", l.getId());
	                map.put("orderNumber", orderNumber);
	                map.put("logType", null==l.getLogType()?nullString:l.getLogType());
	                map.put("userName",null==l.getUserName()?nullString:l.getUserName());
	                map.put("insertTime",null==l.getInsertTime()?nullString: DateTimeUtil.dateToString(l.getInsertTime(), CbbConst.TIME_FORMAT_SUBSTR));
	                map.put("ip",null== l.getIp()?nullString:l.getIp());
	                map.put("ipAddress",null==l.getIpAddress()?nullString:l.getIpAddress());
	                map.put("remark",null==l.getRemark()?nullString:l.getRemark());
	                mapList.add(map);
	            }
	        Map<String, Object> jsonMap = new HashMap<String, Object>();
	        jsonMap.put("aaData", mapList);
	       ajax(jsonMap);
		}
		return null;
	}

    /**
     * 查询条件 批量删除日志
     * @return
     */
    public String deleteListByParam(){
			if(userIds!=null&&!userIds.equals("")){
				userIds = userIds.substring(1, userIds.length()-1);
			}
    		logImpl.deleteListByParam(max,logType, userIds, startTime, endTime, ip, remark);
    		ajax("0");
		return null;
    }
    
	/**
	 * 日志概况
	 * 功能：
	 * @return
	 */
	public String getAllLogNums(){
		String allLogNum = logImpl.getAllLogNums();
		String[] allLogNums = allLogNum.split("-");
        Map<String, Object> jsonMap = new HashMap<String, Object>();
	    try {
	        jsonMap.put("allDay", allLogNums[0]);
	        jsonMap.put("allNum", allLogNums[1]);
	        jsonMap.put("thisYearNum", allLogNums[2]);
	        jsonMap.put("thisMonthNum", allLogNums[3]);
	        jsonMap.put("todayNum", allLogNums[4]);
	        jsonMap.put("averageNum", allLogNums[5]);
	        Gson json = new Gson();
	        String jsons = json.toJson(jsonMap);
	        PrintWriter writer;
			writer = new PrintWriter(this.getResponse().getWriter());
	        writer.print(jsons);
	        writer.flush();
	        writer.close();
        } catch (IOException e) {
        	LOGGER.error(e.getMessage(), e);
		}
        return null;
	}

	public Integer getMax() {
		return max;
	}

	public void setMax(Integer max) {
		this.max = max;
	}

	public Integer getLogType() {
		return logType;
	}

	public void setLogType(Integer logType) {
		this.logType = logType;
	}

	public String getUserIds() {
		return userIds;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
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

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getLogId() {
		return logId;
	}

	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	public String getLogIds() {
		return logIds;
	}

	public void setLogIds(String logIds) {
		this.logIds = logIds;
	}

	public String getDeleteIds() {
		return deleteIds;
	}

	public void setDeleteIds(String deleteIds) {
		this.deleteIds = deleteIds;
	}

	
    private List<Map<String, Object>> logResult(List<Log> list){
        // 把订单信息填充到map里面
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        if (list != null)
        {
            for (Log l : list)
            {
                Map<String, Object> map = new HashMap<String, Object>();
                // ID
                map.put("id", l.getId());

                // 日志日期
                if(l.getUserName()!=null){
                	 map.put("userName",l.getUserName());
                }else{
                	map.put("userName","");
                }
                // 创建时间
                if(l.getInsertTime()!=null){
                	 map.put("insertTime",DateTimeUtil.dateToString(l.getInsertTime(), CbbConst.TIME_FORMAT_STR)); 	
                }else{
                	 map.put("insertTime","");
                }
                //IP
                if(l.getIp()!=null){
                	map.put("ip", l.getIp());
                }else{
                	map.put("ip", "");
                }
                //ip所在地
                if(l.getIpAddress()!=null){
                	map.put("ipAddress", l.getIpAddress());
                }else{
                	map.put("ipAddress", "");
                }
                //日志类型
                String logTypeName = "";
                if(l.getLogType()!=null){
                	   Integer logType=l.getLogType();
				   	   if(logType==0){
				   		   logTypeName="全部日志";
				   	   }else if(logType==1){
				   		   logTypeName="登录日志";
				   	   }else if(logType==2){
				   		   logTypeName="密码错误";
				   	   }else if(logType==3){
				   		   logTypeName="用户名错误";
				   	   }
//				   	   else if(logType==4){
//				   		   logTypeName="清除密码";
//				   	   }else if(logType==5){
//				   		   logTypeName="非法IP登录";
//				   	   }
				   	   else if(logType==6){
				   		   logTypeName="退出系统";
				   	   }else if(logType==7){
				   		   logTypeName="用户添加";
				   	   }else if(logType==8){
				   		   logTypeName="用户修改";
				   	   }else if(logType==9){
				   		   logTypeName="用户删除";
				   	   }
//				   	   else if(logType==10){
//				   		   logTypeName="用户离职";
//				   	   }
				   	   else if(logType==11){
				   		   logTypeName="用户修改登录密码";
				   	   }else if(logType==12){
				   		   logTypeName="部门添加";
				   	   }else if(logType==13){
				   		   logTypeName="部门修改";
				   	   }else if(logType==14){
				   		   logTypeName="部门删除";
				   	   }else if(logType==15){
				   		   logTypeName="公告删除";
				   	   }else if(logType==16){
				   		   logTypeName="邮件发送";
				   	   }else if(logType==17){
				   		   logTypeName="邮件删除";
				   	   }
//				   	   else if(logType==18){
//				   		   logTypeName="按模块设置管理范围";
//				   	   }
                }	map.put("logType", logTypeName);
                //备注
                if(l.getRemark()!=null){
                	map.put("remark", l.getRemark());
                }else{
                	map.put("remark", "");
                }
                mapList.add(map);
            }
        }
        return mapList;
    }

    /**
     * 功能： 系统日志表头
     * @return
     */
    private List<String> getLogExportHeadList(){
        List<String> headList = new ArrayList<String>();
        headList.add("时间");
        headList.add("用户姓名");
        headList.add("IP地址");
//        headList.add("IP所在地");
        headList.add("日志类型");
        headList.add("备注");
        return headList;
    }

    /**
     * 功能：
     * @return
     */
    private List<String> getLogExportKeyList(){
        List<String> headList = new ArrayList<String>();
        headList.add("insertTime");
        headList.add("userName");
        headList.add("ip");
//        headList.add("ipAddress");
        headList.add("logType");
        headList.add("remark");
        return headList;
    }
    /**
     * 导出文件(系统日志)
     */
    public void exportLog(){
        HttpServletResponse response = this.getResponse();
        response.setContentType("application/vnd.ms-excel");
        OutputStream outp;
        
        try{
            // 首先获取登录人基本信息
            UserInfo user = (UserInfo) this.getSession().getAttribute("adminUser");
			
			// 处理导出时,人员前后的,
	        if(userIds!=null&&!userIds.equals("")){
	            userIds = userIds.substring(1, userIds.length()-1);
	        }
			List<Log> list =logImpl.getLogListByParam(max, logType, userIds, startTime, endTime, ip, remark,userName,getGroupIds(user.getUserId()));

            // 把联系人信息填充到map里面
            response.addHeader("Content-Disposition",
                    "attachment;filename=" + new String("系统日志.xls".getBytes(), "iso8859-1"));// 解决中文
                                                                                           // 文件名问题
            outp = response.getOutputStream();
            List<Map<String, Object>> mapList = logResult(list);

            ExportExcel exportExcel = new ExportExcel(outp, getLogExportHeadList(), mapList,getLogExportKeyList());
            exportExcel.export();
        } catch (Exception e){
            LOGGER.error("getAddressGroupList error. ", e);
        }
    }

	/**
	 * 功能：根据二级局获取部门ID集合
	 * @param
	 * @return
	 * @throws   
	 */
	private String getGroupIds(int userId){
		UserInfo userInfo = (UserInfo) getSession().getAttribute("adminUser");
		GroupInfo forkGroup = groupService.getForkGroup(userInfo.getCompanyId(),userId);
		List<GroupInfo> groupList = null;
		if(forkGroup != null){
			groupList = groupService.getSubGroupList(forkGroup.getGroupId());
			groupList.add(forkGroup);
		}
		String groupIds = "";
		if(groupList!=null){
			for(Iterator<GroupInfo> it = groupList.iterator(); it.hasNext();){
				GroupInfo temp = it.next();
				groupIds+=temp.getGroupId();
				if(it.hasNext()){
					groupIds+=",";
				}
			}
		}
		return groupIds;
	}
}
