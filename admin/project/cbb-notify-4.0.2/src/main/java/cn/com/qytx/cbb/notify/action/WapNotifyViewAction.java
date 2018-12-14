package cn.com.qytx.cbb.notify.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cn.com.qytx.cbb.file.config.FilePathConfig;
import cn.com.qytx.cbb.notify.domain.Notify;
import cn.com.qytx.cbb.notify.domain.NotifyView;
import cn.com.qytx.cbb.notify.service.INotify;
import cn.com.qytx.cbb.notify.service.INotifyView;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.base.application.TransportUser;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IGroup;
import cn.com.qytx.platform.org.service.IUser;
import cn.com.qytx.platform.utils.JsonUtils;
import cn.com.qytx.platform.utils.datetime.DateTimeUtil;

/**
 * 功能:手机端（查看、发布范围、已读/未读人数）
 */
public class WapNotifyViewAction extends BaseActionSupport{
	
	@Resource(name="notifyViewService")
	private INotifyView notifyViewService;
	@Resource(name="notifyService")
	private INotify notifyService;
	@Resource(name="userService")
	private IUser userService;
	@Resource(name="groupService")
	private IGroup groupService;
	@Resource(name="filePathConfig")
	private FilePathConfig filePathConfig;
	
	private Integer userId;
	private Integer notifyId;
	private Integer id;
	
	/**
	 * 功能：查看详情后保存查阅信息
	 * @return
	 */
	public String viewNotify(){
		NotifyView notifyView = new NotifyView();
		Notify notify = notifyService.findOne(notifyId);
		notifyView.setCreateUser(userService.findOne(userId));
		notifyView.setNotify(notify);
		UserInfo userInfo = TransportUser.get();
		notifyView.setCompanyId(userInfo.getCompanyId());
		notifyViewService.saveNV(notifyView,notify);
		ajax("100||0");
		return null;
	}
	/**
	 * 手机端发布范围
	 * @return
	 */
	public String mobileView(){
		List<String[]> valuesList = notifyViewService.mobileView(notifyId);
		Map<String,Object> returnMap = new HashMap<String, Object>();
		List<Map<String,Object>> returnList = new ArrayList<Map<String,Object>>();
		Integer totalCount = 0 ;
		if (valuesList!=null&&valuesList.size()>0) {
			for(String[] key:valuesList){
				Map<String,Object> valueMap = new HashMap<String, Object>();
				valueMap.put("departName",key[0]);
				valueMap.put("person",key[1]);
				Integer departCount = key[1].split("、").length;
				valueMap.put("personCount",departCount);
				totalCount += departCount;
				returnList.add(valueMap);
			}
		}
		returnMap.put("totalCount",totalCount);
		returnMap.put("data",returnList);
		ajax("100||"+JsonUtils.generJsonString(returnMap));
		return null;
	}
	/**
	 * 已读人数
	 * @return
	 */
	public String mobileRead(){
		List<NotifyView> list = notifyViewService.mobileRead(notifyId);
		List<Map<String,Object>> returnList = new ArrayList<Map<String,Object>>();
		if(list!=null &&!list.isEmpty()){
			for(NotifyView notifyView:list){
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("userId",notifyView.getCreateUser().getUserId());
				map.put("username",notifyView.getCreateUser().getUserName());
				map.put("job",notifyView.getCreateUser().getJob()==null?"":notifyView.getCreateUser().getJob());
				map.put("createDate",DateTimeUtil.dateToString(notifyView.getCreateTime(),"yyyy-MM-dd HH:mm"));
				map.put("groupName",notifyView.getCreateUser().getGroupId()==null?"--":groupService.findOne(notifyView.getCreateUser().getGroupId()).getGroupName());
//				map.put("photo",filePathConfig.getFileViewPath()+"/upload/"+notifyView.getCreateUser().getPhoto());//trunk版本的地址
				map.put("photo",notifyView.getCreateUser().getPhoto());//阿里云版本的地址
				map.put("phone", notifyView.getCreateUser().getPhone());
				returnList.add(map);
			}
		}
		ajax("100||"+JsonUtils.generJsonString(returnList));
		return null;
	}
	/**
	 * 未读人数
	 * @return
	 */
	public String mobileUnread(){
		List<UserInfo> list = notifyViewService.mobileUnread(notifyId);
		List<Map<String,Object>> returnList = new ArrayList<Map<String,Object>>();
		if(list!=null && !list.isEmpty()){
			for(UserInfo userInfo:list){
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("userId",userInfo.getUserId());
				map.put("username",userInfo.getUserName());
				map.put("job",userInfo.getJob()==null?"":userInfo.getJob());
				map.put("groupName",userInfo.getGroupId()==null?"--":groupService.findOne(userInfo.getGroupId()).getGroupName());
//				map.put("photo",filePathConfig.getFileViewPath()+"/upload/"+userInfo.getPhoto());//trunk版本的地址
				map.put("photo",userInfo.getPhoto());//阿里云版本的地址
				map.put("phone", userInfo.getPhone());
				returnList.add(map);
			}
		}
		ajax("100||"+JsonUtils.generJsonString(returnList));
		return null;
	}
	
	/**
	 * 功能：查看
	 * @return
	 */
	public String viewById(){
		Notify notify = notifyService.findOne(id);
		if(notify!=null){
			List<NotifyView> list = notifyViewService.mobileRead(id);
			List<UserInfo> listUser = notifyViewService.mobileUnread(id);
			int readCount=0;
			int unreadCount=0;
			if(list!=null){
				readCount=list.size();
			}
			if(listUser!=null){
				unreadCount=listUser.size();
			}
			notify.setReadCount(readCount);
			notify.setUnreadCount(unreadCount);
			String differenceTime="";
			differenceTime=DateTimeUtil.getTime(new Date(), notify.getApproveDate());
			notify.setDifferenceTime(differenceTime);
			notify.setCreateUser(userService.findOne(notify.getCreateUser().getUserId()));
			ajax(JsonUtils.generWithDateJsonString("M月dd日  HH:mm",notify,"updateUser","viewList","commentList"));
		}
		return null;
	}
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getNotifyId() {
		return notifyId;
	}

	public void setNotifyId(Integer notifyId) {
		this.notifyId = notifyId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
}
