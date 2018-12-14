package cn.com.qytx.cbb.notify.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.notify.dao.NotifyDao;
import cn.com.qytx.cbb.notify.dao.NotifyViewDao;
import cn.com.qytx.cbb.notify.domain.Notify;
import cn.com.qytx.cbb.notify.domain.NotifyView;
import cn.com.qytx.cbb.notify.service.INotifyView;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;
import cn.com.qytx.platform.org.dao.CompanyDao;
import cn.com.qytx.platform.org.dao.GroupDao;
import cn.com.qytx.platform.org.dao.UserDao;
import cn.com.qytx.platform.org.domain.CompanyInfo;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.utils.StringUtil;

@Service("notifyViewService")
@Transactional
public class NotifyViewImpl extends BaseServiceImpl<NotifyView> implements INotifyView{
		
	@Resource(name="notifyViewDao")
	private NotifyViewDao notifyViewDao;
	@Resource(name="notifyDao")
	private NotifyDao notifyDao;
	@Resource(name="groupDao")
	private GroupDao<GroupInfo> groupDao;
	@Autowired
	CompanyDao<CompanyInfo> companyDao;
	@Resource(name="userDao")
	private UserDao<UserInfo> userDao;
	
	@Transactional(readOnly=true)
	public boolean getNotifyView(Integer createUserId,Integer notifyId){
		return notifyViewDao.getNotifyView(createUserId,notifyId);
	}
	@Transactional(readOnly=true)
	public Integer countNotifyPeoples(Integer id){
		return notifyViewDao.countNotifyPeoples(id);
	}
	@Transactional(propagation=Propagation.REQUIRED)
	public void saveNV(NotifyView notifyView, Notify notify){
		notifyView.setCompanyId(notify.getCompanyId());
		notifyViewDao.saveOrUpdate(notifyView);
		notifyDao.updateViewCount(notify);
	}
	@Transactional(readOnly=true)
	public List<String[]> mobileView(Integer notifyId){
		Notify notify = notifyDao.findOne(notifyId);
		List<Integer> idsList = new ArrayList<Integer>();
		String[] ids = notify.getPublishScopeUserIds().split(",");
		for(String id:ids){
			if(!StringUtil.isEmpty(id)){
				idsList.add(Integer.parseInt(id));
			}
		}
		List<Map<String,Object>> resultList = notifyViewDao.getMapPublishUsers(idsList);  //得到所有人员、及所在部门
		
		Map<String,String> dataMap = new HashMap<String, String>();  //保存得到的数据
		String groupIds = "";
		for(Map map:resultList){
			if(map.get("group_id")!=null){
				if(dataMap.get(map.get("group_id").toString())==null){  
					dataMap.put(map.get("group_id").toString(),map.get("user_name").toString()); 
					groupIds += "," + Integer.parseInt(map.get("group_id").toString());
				}else{
					dataMap.put(map.get("group_id").toString(),dataMap.get(map.get("group_id").toString())+"、"+map.get("user_name").toString());  
				}
			}
		}
		//部门排序
		List<GroupInfo> allgroup = groupDao.findGradeGroupTree(notify.getCompanyId(), 1);
		List<GroupInfo> orderGroupList = recursivGroupTreeList(allgroup,null,null);
		List<GroupInfo> newGroupList = new ArrayList<GroupInfo>();
		groupIds = groupIds+","; 
		for (GroupInfo groupInfo : orderGroupList) {
			if (groupIds.contains(","+groupInfo.getGroupId()+",")) {
				newGroupList.add(groupInfo);
			}
		}
		//重新生成结果集
		List<String[]> newList = new ArrayList<String[]>(); 
		if (newGroupList!=null&&newGroupList.size()>0) {
			for (GroupInfo info : newGroupList) {
				String[] str = {getPathGroup(info.getGroupId()),dataMap.get(info.getGroupId().toString())};
				newList.add(str);
			}
		}
		return newList;
	}
	@Transactional(readOnly=true)
	private String getPathGroup(Integer groupId){
		GroupInfo groupInfo = groupDao.findOne(groupId);
		StringBuffer buffer = new StringBuffer(groupInfo.getGroupName());
		while(groupInfo.getParentId()!=null && groupInfo.getParentId()!=0){
			GroupInfo parentGroupInfo = groupDao.findOne(groupInfo.getParentId());
			buffer.insert(0,parentGroupInfo.getGroupName()+"-");
			groupInfo = parentGroupInfo;
		}
		return buffer.toString();
	}
	@Transactional(readOnly=true)
	public List<NotifyView> mobileRead(Integer notifyId){
		return notifyViewDao.mobileRead(notifyId);
	}
	@Transactional(readOnly=true)
	public List<UserInfo> mobileUnread(Integer notifyId){
		Notify notify = notifyDao.findOne(notifyId);
		String[] ids = notify.getPublishScopeUserIds().split(",");
		List<Integer> list = new ArrayList<Integer>();
		for(int i = 0 ; i < ids.length ; i++){
			if(ids[i]!=null&&!"".equals(ids[i])){
				list.add(Integer.parseInt(ids[i]));
			}
		}
		List<NotifyView> viewList = notifyViewDao.mobileRead(notifyId);
		for(NotifyView notifyView:viewList){
			list.remove(notifyView.getCreateUser().getUserId());
		}
		if(list.size() > 0){
//			return userDao.findUserInfoByIds(list);
			return userDao.partition().findAll("userId in (?1)",list);
		}else{
			return new ArrayList<UserInfo>();
		}
		
	}
	@Transactional(readOnly=true)
	public List<Map<String,Object>> viewList(Integer notifyId){
		Notify notify = notifyDao.loadNotify(notifyId);
		List<Map<String,Object>> returnList = new ArrayList<Map<String,Object>>();  //返回的数组
		List<Map<String,Object>> list = notifyViewDao.viewList(notify);  //得到数组数据
		
		Map<Integer,Map<String,Object>> returnMap = new HashMap<Integer,Map<String,Object>>();  //key 保存数组名称，map值保存内容
		String groupIds = "";
		for(Map<String,Object> map:list){
			if(map.get("groupId") != null){
				if(returnMap.get(Integer.parseInt(map.get("groupId").toString()))==null){  //如果没有对象先创建
					Map<String,Object> valueMap = new HashMap<String, Object>();
					if(map.get("counting")==null || map.get("counting").toString().equals("")){
						valueMap.put("notReader",map.get("userName").toString());
					}else{
						valueMap.put("reader",map.get("userName").toString());
					}
					returnMap.put(Integer.parseInt(map.get("groupId").toString()),valueMap);
					groupIds += "," + Integer.parseInt(map.get("groupId").toString());
				}else{		//有对象取出
					Map<String,Object> valueMap = returnMap.get(map.get("groupId"));
					if(map.get("counting")==null || map.get("counting").toString().equals("")){
						if(valueMap.get("notReader")==null){
							valueMap.put("notReader",map.get("userName").toString());
						}else{
							valueMap.put("notReader",valueMap.get("notReader")+","+map.get("userName").toString());
						}
					}else{
						if(valueMap.get("reader")==null){
							valueMap.put("reader",map.get("userName").toString());
						}else{
							valueMap.put("reader",valueMap.get("reader")+","+map.get("userName").toString());
						}
					}
					returnMap.put(Integer.parseInt(map.get("groupId").toString()),valueMap);
				}
			}
		}
		//部门排序
		List<GroupInfo> allgroup = groupDao.findGradeGroupTree(notify.getCompanyId(), 1);
		List<GroupInfo> orderGroupList = recursivGroupTreeList(allgroup,null,null);
		List<GroupInfo> newGroupList = new ArrayList<GroupInfo>();
		groupIds = groupIds+","; 
		for (GroupInfo groupInfo : orderGroupList) {
			if (groupIds.contains(","+groupInfo.getGroupId()+",")) {
				newGroupList.add(groupInfo);
			}
		}
		
		//重新生成结果集
		List<String[]> newList = new ArrayList<String[]>(); 
		if (newGroupList!=null&&newGroupList.size()>0) {
			for (GroupInfo info : newGroupList) {
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("department",getPathGroup(info.getGroupId()));
				map.put("reader",returnMap.get(info.getGroupId()).get("reader")==null?"":returnMap.get(info.getGroupId()).get("reader"));
				map.put("notReader",returnMap.get(info.getGroupId()).get("notReader")==null?"":returnMap.get(info.getGroupId()).get("notReader"));
				returnList.add(map);
			}
		}
		return returnList;
	}
	/**
	 * 获取公告的查看人数
	 * @param companyId
	 * @return
	 */
	public Map<Integer,Integer> getViewUserNumByNotigyId(String notifyIds,Integer userId){
		return notifyViewDao.getViewUserNumByNotigyId(notifyIds,userId);
	}
	/**
	 * 
	 * 功能：得到分类树
	 * @param allGroupList
	 * @param g
	 * @param temp
	 * @return 部门树列表
	 */
	private List<GroupInfo> recursivGroupTreeList(List<GroupInfo> allGroupList, GroupInfo g, List<GroupInfo> templ) {
		if (templ == null) {
			templ = new ArrayList<GroupInfo>();
		}
		for (GroupInfo group : allGroupList) {
			Integer parentId = group.getParentId();
			if(parentId!=null){
				boolean bOne=g == null && parentId.intValue()==0;
				boolean bTwo=g != null && ((g.getGroupId()).intValue()==parentId.intValue());
				if (bOne||bTwo) {
					templ.add(group);
					recursivGroupTreeList(allGroupList, group, templ);
				}
			}
		}
		return templ;
	}
}
