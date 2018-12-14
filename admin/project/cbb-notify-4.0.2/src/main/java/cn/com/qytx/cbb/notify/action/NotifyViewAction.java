package cn.com.qytx.cbb.notify.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import cn.com.qytx.cbb.notify.domain.Notify;
import cn.com.qytx.cbb.notify.domain.NotifyView;
import cn.com.qytx.cbb.notify.service.INotify;
import cn.com.qytx.cbb.notify.service.INotifyView;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IGroup;
import cn.com.qytx.platform.org.service.IUser;

import com.google.gson.Gson;

/**
 * 功能:查看、阅读状态
 */
public class NotifyViewAction extends BaseActionSupport{
	
	private static final long serialVersionUID = 1L;
	@Resource(name="userService")
	private IUser userService;
	@Resource(name="notifyService")
	private INotify notifyService;
	@Resource(name="groupService")
	private IGroup groupService;
	@Resource(name="notifyViewService")
	private INotifyView notifyViewService;
	
	private Integer columnId;
	private Integer notifyId;
	
	/**
	 * 功能：保存阅读记录
	 */
	public void save(){
		Notify notify = notifyService.findOne(notifyId);
		NotifyView notifyView = new NotifyView();
		notifyView.setCreateUser(getLoginUser());
		notifyView.setNotify(notify);
		notifyViewService.saveNV(notifyView,notify);
	}
	/**
	 * 功能：阅读状态 （未读人数、已读人数列表）
	 */
	public String viewList(){
		try{
			
			Map<String, Object> jsonMap = new HashMap<String, Object>();
	        jsonMap.put("aaData",notifyViewService.viewList(notifyId));
	        ajax(jsonMap);
		}catch(Exception e){
			e.printStackTrace();
		    LOGGER.error(e.getMessage());
		}
		return null;
		}
	
	/**
	 * 功能：根据用户Id(List)查询部门Id(List)
	 * @return 部门Id列表
	 */
	public List<Integer> getGroupIdListByUserIdList(List<Integer> userIdList){
		List<Integer> groupIdList = new ArrayList<Integer>();
		for(int userId : userIdList){
			GroupInfo groupInfoBean = groupService.findOne(userService.findOne(userId).getGroupId());
			if(null!=groupInfoBean){
				groupIdList.add(groupInfoBean.getGroupId());
			}
		}
		return filterDuplicate(groupIdList);
	}
	/**
	 * 功能：去掉重复数据
	 * @param oldList 原始数据List
	 * @return 去掉重复数据的List
	 */
	public List<Integer> filterDuplicate(List<Integer> oldList){
		Set<Integer> set = new HashSet<Integer>();  
		List<Integer> newList = new ArrayList<Integer>();  
		for (Iterator<Integer> iter = oldList.iterator(); iter.hasNext();) {  
			Integer element = (Integer) iter.next();  
			if (set.add(element))  {
			    newList.add(element);  
			   }
			 } 
		return newList;
	}
	/**
	 * 功能：阅读状态
	 * add 8-1
	 * @return
	 */
	public String readStatus(){
		int readerSum=notifyViewService.countNotifyPeoples(notifyId);
		int totalSum=notifyService.findOne(notifyId).getPublishScopeUserIds().split(",").length;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("readerSum", readerSum);
		map.put("total", totalSum);
		ajax(map);
		return null;
	}
	/**
	 * 
	 * 功能：获取已读人数/共人数
	 * @return
	 */
	public String memCount(){
		PrintWriter out=null;
		try {
			out = new PrintWriter(this.getResponse().getWriter());
			int readerSum = 0;
			int notReaderSum = 0;
			List<Integer> userIdList = new ArrayList<Integer>();
			Map<String, Object> map = new HashMap<String, Object>();
			Notify notify =	notifyService.findOne(notifyId);
			String uids[]=notify.getPublishScopeUserIds().split(",");
			for (int i = 0; i < uids.length; i++) {
				userIdList.add(Integer.parseInt(uids[i]));
			}
			List<Integer> groupIdList = getGroupIdListByUserIdList(userIdList);//根据人员列表查询所有部门Id
			for(int groupId : groupIdList){
				GroupInfo groupInfo = groupService.findOne(groupId);
				StringBuffer readerBuf = new StringBuffer();
				StringBuffer notReaderBuf = new StringBuffer();
				for(int userId :userIdList){
					if(null!=groupService.findOne(userService.findOne(userId).getGroupId())&&groupService.findOne(userService.findOne(userId).getGroupId()).getGroupId()==groupId){
						UserInfo userInfo = userService.findOne(userId);
						if(null!=userInfo){
							if(!notifyViewService.getNotifyView(userInfo.getUserId(),notifyId)){
								readerBuf.append(userInfo.getUserName()).append(",");
								readerSum++;
							}else{
								notReaderBuf.append(userInfo.getUserName()).append(",");
								notReaderSum++;
							}
						 }
					   }
					}
				map.put("readerSum", readerSum);
				map.put("notReaderSum", notReaderSum);
			}
			Gson gson=new Gson();
			String gsonStr=gson.toJson(map);
			out.print(gsonStr);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}finally{
			if(out!=null){
				out.close();
			}
		}
		return null;
	}
	public Integer getColumnId() {
		return columnId;
	}

	public void setColumnId(Integer columnId) {
		this.columnId = columnId;
	}

	public Integer getNotifyId() {
		return notifyId;
	}

	public void setNotifyId(Integer notifyId) {
		this.notifyId = notifyId;
	}
}
