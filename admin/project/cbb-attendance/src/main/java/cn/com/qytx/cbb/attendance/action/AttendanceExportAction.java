package cn.com.qytx.cbb.attendance.action;

import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.tools.ant.util.DateUtils;

import cn.com.qytx.cbb.attendance.domain.Attendance;
import cn.com.qytx.cbb.attendance.service.IAttendance;
import cn.com.qytx.platform.utils.ExportExcel;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.PageImpl;
import cn.com.qytx.platform.base.query.Sort;
import cn.com.qytx.platform.base.query.Sort.Direction;
import cn.com.qytx.platform.base.query.Sort.Order;
import cn.com.qytx.platform.org.dao.UserVo;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IGroup;
import cn.com.qytx.platform.org.service.IUser;
import cn.com.qytx.platform.utils.datetime.DateTimeUtil;

/**
 * 功能: 考勤管理 导出
 * 版本: 1.0
 * 开发人员: 陈秋利
 * 创建日期: 2014-7-8
 */
public class AttendanceExportAction extends BaseActionSupport{
    /**  序列号 */
    private static final long serialVersionUID = 2314184560157105391L;
    /**  日志类 */
    private static final Logger logger = Logger.getLogger(AttendanceIpSetAction.class);
 
    /** 考勤 service */
    @Resource(name="attendanceService")
    IAttendance attendanceService;
	@Resource(name="groupService")
	private IGroup groupService;
	
    private Integer groupId;
    private String keyWord;
    private String beginT;
    private String endT;
    
	/** 用户管理 */
	@Resource
	private IUser userService;
    
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
        	if(groupId!=null&&groupId==0){
        		groupId=null;
        	}
        	this.setIDisplayStart(0);
    		this.setIDisplayLength(999999);

        	UserVo userVo = new UserVo();
			userVo.setLoginName(keyWord);
			List<Order> orderList = new ArrayList<Sort.Order>();
			Order order = new Order(Direction.ASC,"orderIndex");
			Order pyOrder = new Order(Direction.ASC,"py");
			orderList.add(order);
			orderList.add(pyOrder);
	        Sort s = new Sort(orderList);
			userVo.setCompanyId(companyId);
			userVo.setProjectName(userVo.TXZL);
	        Page<UserInfo> page = null;
	        Page<Map<String, Object>> userMaplist = null;
	        
	        if(groupId ==null){
	        	userMaplist = userService.findByPageAll(userVo, getPageable(s),null);
	        	page = generateUserlist(userMaplist);
	        }else{
	        	List<GroupInfo> groupList = groupService.getSubGroupList(groupId);
				String groupIds = "," + groupId;
				if (groupList != null && groupList.size() > 0) {
					for (GroupInfo groupInfo : groupList) {
						groupIds += "," + groupInfo.getGroupId();
					}
				}
				groupIds = groupIds.substring(1);
				userMaplist = userService.findByPageAll(userVo, getPageable(s),
						groupIds);
				page = generateUserlist(userMaplist);
	        }
			List<UserInfo> userList=page.getContent();
			List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
			if(userList!=null){
				 String ids="";
				 for(UserInfo u:userList){
					 if(u.getIsVirtual()!=null && u.getIsVirtual().intValue()==1){
						 u.setUserId(u.getLinkId());
					 }
					 ids+=u.getUserId()+",";
				 }
					 if(StringUtils.isNotBlank(ids)&&ids.endsWith(",")){
						 ids=ids.substring(0,ids.length()-1);
					 }
	              Map<Integer,Integer> countRecords=attendanceService.tjAttendanceCount(ids, beginT, endT,companyId);
	             int n = page.getNumber() * page.getSize() + 1;
				mapList = transUserToMap(userList, n, countRecords);
			}
			outp = response.getOutputStream();
             ExportExcel exportExcel = new ExportExcel(outp, getExportHeadList(), mapList, getExportKeyList());
             exportExcel.exportWithSheetName("考勤记录");
        
        }catch(Exception e){
        	logger.error(e.getMessage());
        	e.printStackTrace();
        }
    }

    /**
     * 将用户列表转为MAP格式
     * @param userList
     * @param n
     * @param countRecords
     * @return
     */
    private List<Map<String,Object>> transUserToMap(List<UserInfo> userList,int n,Map<Integer,Integer> countRecords){
    	List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
    	for(int i=0;i<userList.size();i++){
			UserInfo user = userList.get(i);
            Map<String, Object> map = new HashMap<String, Object>();
            //序号
            map.put("no", n);
            //部门
            String deptName=user.getGroupName();	               
            if(StringUtils.isNotEmpty(deptName)){
            	map.put("deptName", deptName);
            }else{
            	map.put("deptName", "");
            }
            //姓名
            String userName=user.getUserName();
            if(StringUtils.isNotEmpty(userName)){
            	map.put("userName", userName);
            }else{
            	map.put("userName", "");
            }
          //职务
            String job=user.getJob();
            if(StringUtils.isNotEmpty(job)){
            	map.put("job", job);
            }else{
            	map.put("job", "");
            }
            
            //ID
            Integer userId=user.getUserId();
            if(userId!=null){
            	map.put("userId", userId);
            }else{
            	map.put("userId", "");
            }
            //打卡次数
            int counts=0;
            if(countRecords.get(user.getUserId())!=null){
            	counts=countRecords.get(user.getUserId());
            }
            map.put("counts", counts);
            mapList.add(map);
            n++;
		}
    	return mapList;
    }

    /**
     * 返回查询用户Map
     * @return
     */
    private Map<Integer, UserInfo> getUserMap(int companyId) {
    	Map<Integer,UserInfo> uMap = new HashMap<Integer, UserInfo>();
    	String groupIds =  "";
    	List<GroupInfo> glist = null; 
         if((groupId!=null&&groupId==0)||groupId==null){
        	 groupIds =null;
         }else{
        	 glist = groupService.getGroupList(companyId, 1);
        	    
         	if(glist!=null&&glist.size()>0){
         		for(GroupInfo ginfo:glist){
         			groupIds+=ginfo.getGroupId()+",";
         		}
         		if(groupIds.endsWith(",")){
         			groupIds = groupIds.substring(0,groupIds.length()-1);
         		}
         	}else{
         		groupIds = String.valueOf(groupId);
         	}
         }
    	 
		UserVo vo = new UserVo();
		vo.setUserName(keyWord);
		vo.setCompanyId(companyId);
		 Order order = new Order(Direction.ASC,"orderIndex");
		Sort s = new Sort(order);
		Page<UserInfo> res = userService.findAllUsersByPage(vo , this.getPageable(s), groupIds);
		List<UserInfo> ulist = res.getContent();
		if(ulist!=null&&ulist.size()>0){ 
			for(UserInfo uinfo:ulist){
				GroupInfo ginfo =groupService.getGroupByUserId(companyId, uinfo.getUserId());
				if(ginfo!=null){
					uinfo.setGroupId(ginfo.getGroupId());
					uinfo.setGroupName(ginfo.getGroupName());
				}
				uMap.put(uinfo.getUserId(), uinfo);
			}
		}
		return uMap;
	}


	private List<String> getExportHeadList(){
        List<String> headList = new ArrayList<String>();
        headList.add("序号");
        headList.add("姓名");
        headList.add("部门");
        headList.add("职务");
        headList.add("打卡次数");
        return headList;
    }
    
    private List<String> getExportKeyList(){
        List<String> headList = new ArrayList<String>();
        headList.add("no");
        headList.add("userName");
        headList.add("deptName");
        headList.add("job");
        headList.add("counts");
        return headList;
    }
    
    
    /**
     * 设置内容
     * @param uMap 
     * @param companyId 
     * @param map
     * @return
     */
    private List<Map<String, Object>> analyzeResult(Map<String, List<Attendance>>  attmap, Map<Integer, UserInfo> uMap, Integer companyId) {
        // 把要导出的信息填充到map里面
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        int index=1;
        if (attmap != null) {
        	for (String key : attmap.keySet()) {
        	    List<Attendance> list = attmap.get(key);
        	    if(list!=null&&list.size()>0){
        	    	Map<String, Object> map = new HashMap<String, Object>();
        	    	// 序号
        	    	map.put("index", index);
        	    	Attendance one = list.get(0);
        	    	String uname=""; //姓名
        	    	String bm=""; //部门
        	    	String attDate=""; //打卡日期
        	    	String week=""; //星期
        	    	String count=String.valueOf(list.size());//打卡次数
        	    	if(one!=null){
        	    		Integer uid = one.getCreateUserId();
        	    	   UserInfo user=uMap.get(uid);
        	    	   if(user!=null){
        	    		  uname = user.getUserName();
        	    		  bm = user.getGroupName();
        	    	   }else{
        	    		   user = userService.findOne("userId=?", uid);
        	    		   GroupInfo ginfo =groupService.getGroupByUserId(companyId,uid);
    						if(ginfo!=null){
    							 bm =ginfo.getGroupName();
    						}
        	    		   uname = user.getUserName();
        	    	   }
        	    	   Timestamp crt = one.getCreateTime();
        	    	   SimpleDateFormat sip = new SimpleDateFormat("yyyy-MM-dd");
        	    	   attDate = sip.format(crt);
        	    	   week = DateUtils.format(crt, "EEEE");
        	    	}
        	    	map.put("uname", uname);
        	    	map.put("bm", bm);
        	    	map.put("attDate",attDate);
        	    	map.put("week", week);
        	    	map.put("count", count);
        	    	int i=0;
        	    	for(Attendance att:list){
        	    		SimpleDateFormat sip = new SimpleDateFormat("HH:mm:ss");
        	    		String sv = sip.format(att.getCreateTime());
        	    		String address = att.getPosition();
        	    	    map.put("time"+i, sv+" "+address);
        	    	    i++;
        	    	}
        	    	 mapList.add(map);
                     index++;
                }
            }
        }
        return mapList;
    }

    private Page<UserInfo> generateUserlist(Page<Map<String, Object>> mapPage){
  		List<UserInfo> content = new ArrayList<UserInfo>();
  		List<Map<String, Object>> userList = mapPage.getContent();
  		for (int i = 0; i < userList.size(); i++) {
  			UserInfo user= new UserInfo();
  			user.setUserId(Integer.parseInt(userList.get(i).get("userId").toString()));
  			user.setUserName(userList.get(i).get("userName")==null?"":userList.get(i).get("userName").toString());
  			user.setPhone(userList.get(i).get("phone")==null?"":userList.get(i).get("phone").toString());
  			user.setGroupName(userList.get(i).get("groupName")==null?"":userList.get(i).get("groupName").toString());
  			user.setTitle(userList.get(i).get("title")==null?"":userList.get(i).get("title").toString());
  			user.setvNum(userList.get(i).get("vNum")==null?"":userList.get(i).get("vNum").toString());
  			user.setGroupId((Integer) (userList.get(i).get("groupId")==null?"":userList.get(i).get("groupId")));
  			user.setJob(userList.get(i).get("job")==null?"":userList.get(i).get("job").toString());
  			user.setLoginName(userList.get(i).get("loginName")==null?"":userList.get(i).get("loginName").toString());
  			user.setSex((Integer) (userList.get(i).get("sex")==null?"":userList.get(i).get("sex")));
  			user.setLastLoginTime((Date) userList.get(i).get("lastLoginTime"));
  			user.setPhonePublic((Integer) userList.get(i).get("phonePublic"));
  			user.setRegisterTime((Date) userList.get(i).get("registerTime"));
  			user.setCreateTime((Date) userList.get(i).get("createTime"));
  			user.setUserState((Integer) (userList.get(i).get("userState")==null?UserInfo.USERSTATE_UNLOGIN:userList.get(i).get("userState")));
  			user.setIsDefault((Integer) (userList.get(i).get("isDefault")==null?1:userList.get(i).get("isDefault")));
  			user.setMobileShowState((Integer) (userList.get(i).get("mobileShowState")==null?0:userList.get(i).get("mobileShowState")));
  			user.setIsVirtual((Integer) (userList.get(i).get("isVirtual")==null?0:userList.get(i).get("isVirtual")));
  			user.setLinkId((Integer) (userList.get(i).get("linkId")==null?null:userList.get(i).get("linkId")));
  			content.add(user);
  		}
  		
  		return new PageImpl<UserInfo>(content, getPageable(), mapPage.getTotalElements());
  	}


	/**
	 * @return the groupId
	 */
	public Integer getGroupId() {
		return groupId;
	}


	/**
	 * @param groupId the groupId to set
	 */
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}


	/**
	 * @return the keyWord
	 */
	public String getKeyWord() {
		return keyWord;
	}


	/**
	 * @param keyWord the keyWord to set
	 */
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
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
    
    
    
}
