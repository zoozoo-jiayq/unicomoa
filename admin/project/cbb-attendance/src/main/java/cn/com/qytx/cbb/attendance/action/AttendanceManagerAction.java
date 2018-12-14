package cn.com.qytx.cbb.attendance.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.qytx.cbb.attendance.service.IAttendance;
import cn.com.qytx.cbb.attendance.service.IAttendanceIpSet;
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

/**
 * 功能: 考勤管理 action
 * 版本: 1.0
 * 开发人员: 陈秋利
 * 创建日期: 2014-7-8
 */
public class AttendanceManagerAction extends BaseActionSupport{
    /**  序列号 */
    private static final long serialVersionUID = 2314184560157105391L;
    /**  日志类 */
    private static final Logger logger = Logger.getLogger(AttendanceIpSetAction.class);
 
    /** 考勤-IP设置 service */
    @Autowired
    IAttendanceIpSet attendanceIpSet;
    /** 考勤 service */
    @Autowired
    IAttendance attendance;
    /** 用户信息 */
	@Autowired
	IUser userService;
	 /** 群组信息 */
	@Autowired
	IGroup groupService;
    //groupId
    private Integer groupId;
    //查询关键字:姓名
    private String keyword;
    //开始时间
    private String beginT;
    //结束时间
    private String endT;
    
    /**
     * 功能：获取考勤统计列表
     * @return
     */
    public String listRecords(){
    	PrintWriter out=null;
    	try {
			UserInfo userInfo=this.getLoginUser();
			Integer companyId=userInfo.getCompanyId();
			if(groupId!=null&&groupId==0){
				groupId=null;
			}
			UserVo userVo = new UserVo();
			userVo.setLoginName(keyword);
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
			//获取分页信息
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
	              Map<Integer,Integer> countRecords=attendance.tjAttendanceCount(ids, beginT, endT,companyId);
	             int n = page.getNumber() * page.getSize() + 1;
	             mapList = usersToMap(userList, n, countRecords);
			}
			this.ajaxPage(page, mapList);
		} catch (Exception e) {
			 logger.error(e.getMessage());
		}finally{
			if(out!=null){
				out.close();
			}
		}
    	return null;
    }

    /**
     * 将用户对象转为MAP对象
     * @param userList
     * @param n
     * @param countRecords
     * @return
     */
    private List<Map<String,Object>> usersToMap(List<UserInfo> userList,int n,Map<Integer,Integer> countRecords){
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
            	map.put("deptName", "&nbsp;");
            }
            //姓名
            String userName=user.getUserName();
            if(StringUtils.isNotEmpty(userName)){
            	map.put("userName", userName);
            }else{
            	map.put("userName", "&nbsp;");
            }
          //职务
            String job=user.getJob();
            if(StringUtils.isNotEmpty(job)){
            	map.put("job", job);
            }else{
            	map.put("job", "&nbsp;");
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

	public Integer getGroupId() {
		return groupId;
	}


	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}


	public String getKeyword() {
		return keyword;
	}


	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}


	public String getBeginT() {
		return beginT;
	}


	public void setBeginT(String beginT) {
		this.beginT = beginT;
	}


	public String getEndT() {
		return endT;
	}


	public void setEndT(String endT) {
		this.endT = endT;
	}
    
}
