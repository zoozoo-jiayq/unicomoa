package cn.com.qytx.cbb.notify.action;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.qytx.cbb.affairs.service.IPushMobile;
import cn.com.qytx.cbb.dict.domain.Dict;
import cn.com.qytx.cbb.dict.service.IDict;
import cn.com.qytx.cbb.file.service.IAttachment;
import cn.com.qytx.cbb.notify.domain.Notify;
import cn.com.qytx.cbb.notify.domain.PlatformParameter;
import cn.com.qytx.cbb.notify.im.PublishNotify;
import cn.com.qytx.cbb.notify.service.INotify;
import cn.com.qytx.cbb.notify.service.INotifyView;
import cn.com.qytx.cbb.notify.service.IPlatformParameter;
import cn.com.qytx.cbb.notify.utils.ColumnUtils;
import cn.com.qytx.cbb.notify.vo.TbColumnSetting;
import cn.com.qytx.cbb.push.service.PushPlatService;
import cn.com.qytx.cbb.push.service.PushPlatService.IOS_PUSH_TYPE;
import cn.com.qytx.cbb.push.service.PushPlatService.JPUSH_PUSH_TYPE;
import cn.com.qytx.cbb.push.service.PushPlatService.PLAT_TYPE;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.base.application.TransportUser;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Sort;
import cn.com.qytx.platform.base.query.Sort.Direction;
import cn.com.qytx.platform.log.service.ILog;
import cn.com.qytx.platform.org.service.IGroup;
import cn.com.qytx.platform.org.service.IUser;
import cn.com.qytx.platform.utils.JsonUtils;
import cn.com.qytx.platform.utils.datetime.DateTimeUtil;
import cn.com.qytx.rangecontrol.PushControl;

import com.google.gson.Gson;

/**
 * 功能：公告操作类
 * @author liyanlei
 */
public class NotifyAction extends BaseActionSupport {
	
	private static final long serialVersionUID = 1L;	
	@Resource(name="notifyService")
	private INotify notifyService;
	@Resource(name="dictService")
	private IDict dictService;
	@Resource(name="notifyViewService")
	private INotifyView notifyViewService;
	@Resource(name="platformParameterService")
	private IPlatformParameter platformParameterService;
	@Resource(name="groupService")
	private IGroup groupService;
	@Resource(name="userService")
	private IUser userService;
	@Resource(name="attachmentService")
	private IAttachment attachmentService;
	@Autowired
	private PushPlatService pushPlatService;
	@Resource
    private ILog logService;
	@Autowired
	IPushMobile pushMobileService;
	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	private Integer notifyType;
	private String subject;
	private Date beginDate;
	private Date endDate;
	private Integer columnId;
	private String ids;
	private Integer isTop;
	private Notify notify;
	private TbColumnSetting tbColumnSetting;
	private Integer instentceid;
	private Integer id;
	private String searchWord;
	private String startDateStr;
	private String endDateStr;
	private Integer status;
	private String groupId;
	private Integer isShowOut;//是否显示 不在范围的通知公告   1是不显示  
	/**
	 * 分页查询列表
	 * @return null
	 */
	public String viewList() {
		List<Sort.Order> orders = new ArrayList<Sort.Order>();
		orders.add(new Sort.Order(Sort.Direction.DESC, "isTop"));
        orders.add(new Sort.Order(Sort.Direction.DESC, "approveDate"));
        Sort sort = new Sort(orders);
		Page<Notify> pageInfo = notifyService.viewList(getPageable(sort),getLoginUser().getUserId(),notifyType,subject,columnId,isShowOut);
		List<Notify> list = pageInfo.getContent();
		//得到维表
		Map<String, String> dictMap = dictService.findMap("notifyType"+columnId, 1);
		String notifyIds = "";
		if (list != null && list.size() > 0) {
			for (Notify notify : list) {
				notifyIds += ","+notify.getId();
			}
		}
		//得到公告的查看次数
		Map<Integer, Integer> viewMap = notifyViewService.getViewUserNumByNotigyId(notifyIds,null);
		//得到我自己的公告查看次数
		Map<Integer, Integer> myviewMap = notifyViewService.getViewUserNumByNotigyId(notifyIds,getLoginUser().getUserId());
		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
		if (list!=null&&list.size()>0) {
			for(Notify notify:list){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("notifyId",notify.getId());
				map.put("subject",notify.getSubject());
				if (dictMap==null) {
					map.put("typename","");
				}else {
					if (dictMap.get(notify.getNotifyType().toString())==null) {
						map.put("typename","");
					}else {
						map.put("typename",dictMap.get(notify.getNotifyType().toString()));
					}
				}
				map.put("username",notify.getCreateUser().getUserName());
				map.put("approveDate", DateTimeUtil.dateToString(notify.getApproveDate(),"yyyy-MM-dd HH:mm"));
				map.put("isTop",notify.getIsTop());
				map.put("totalCount",viewMap==null?0:viewMap.get(notify.getId())==null?0:viewMap.get(notify.getId()));
				map.put("counting", myviewMap==null?0:myviewMap.get(notify.getId())==null?0:myviewMap.get(notify.getId()));
				resultList.add(map);
			}
		}
		ajaxPage(pageInfo, resultList);
		return null;
	}

	/**
	 * 查看详细信息.
	 * @return null
	 */
	public String view() {
		notify = notifyService.findOne(id);
		if (notify.getImages() != null && !"".equals(notify.getImages())) {
			notify.setImagesList(attachmentService.getAttachmentsByIds(notify.getImages()));
		}
		if (notify.getAttment() != null && !notify.getAttment().equals("")) {
			notify.setAttachmentList(attachmentService.getAttachmentsByIds(notify.getAttment()));
		}
		notify.setCreateUser(userService.findOne(notify.getCreateUser().getUserId()));
		ajax(JsonUtils.generWithDateJsonString("yyyy年MM月dd日  HH:mm",notify,"updateUser","viewList","commentList"));
		return null;
	}
	
	/**
	 * 分页查询待审批列表
	 * @return null
	 */
	public String viewApproveList(){
		tbColumnSetting = (TbColumnSetting) platformParameterService.findEntityByInstenceId(columnId);
		Map<String,Object> dataMap = new HashMap<String,Object>();
		Sort sort = new Sort(new Sort.Order(Direction.DESC,"isTop"),new Sort.Order(Direction.DESC,"createDate"));
		Page<Notify> pageInfo = notifyService.approveList(getPageable(sort),getLoginUser().getUserId(),notifyType,subject,beginDate,endDate,columnId,status);
		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
		for(Notify notify:pageInfo.getContent()){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id",notify.getId());
			map.put("subject",notify.getSubject());
			map.put("isTop",notify.getIsTop());
			if(notify.getNotifyType()!=null){
				Dict dict = dictService.loadByTypeTagValue( "notifyType"+columnId+"", 1, notify.getNotifyType());
				map.put("notifyType",dict!=null?dict.getName():"--");
			}else{
				map.put("notifyType","--");
			}
			map.put("username",notify.getCreateUser().getUserName());
			map.put("createDate",DateTimeUtil.dateToString(notify.getCreateDate(),"yyyy-MM-dd HH:mm"));
			map.put("browse",notifyViewService.countNotifyPeoples(notify.getId())+"/"+notify.getPublishScopeUserIds().substring(0,notify.getPublishScopeUserIds().length()-1).split(",").length);
			map.put("status",notify.getStatus());
			map.put("isEdit",tbColumnSetting.getIsEdit());
			resultList.add(map);
		}
		dataMap.put("iTotalRecords", pageInfo.getTotalElements());
		dataMap.put("iTotalDisplayRecords", pageInfo.getTotalElements());
		dataMap.put("aaData", resultList);
		ajax(dataMap);
		return null;
		
	}
	/**
	 * 功能：审批时修改
	 * @return
	 * @throws ParseException
	 */
	public String approveEdit() throws ParseException{
		notify.setUpdateUser(getLoginUser());
		if(startDateStr!=null && !"".equals(startDateStr)){
			notify.setBeginDate(new Timestamp(dateFormat.parse(startDateStr).getTime()));
		}
		if(endDateStr!=null && !"".equals(endDateStr)){
			notify.setEndDate(new Timestamp(dateFormat.parse(endDateStr).getTime()));
		}
		notify.setCompanyId(getLoginUser().getCompanyId());
		notifyService.update(notify);
		return null;
	}
	/**
	 * 功能：生效
	 */
	public void effect(){
		notifyService.effect(id,startDateStr,endDateStr);
		ajax(true);
	}
	
	/**
	 * 审批
	 * @return null
	 */
	public String approve(){
		Notify n = new  Notify();
		n = notifyService.findOne(notify.getId());
		n.setReason(notify.getReason());
		n.setStatus(notify.getStatus());
		n.setAuditer(getLoginUser().getUserId());
		n.setApproveDate(new Timestamp(System.currentTimeMillis()));
		notifyService.approveNotify(n,getLoginUser());
		if (notify.getStatus()==2) {
			notifyService.sendReminder(n);
		}
		
		//add by jiayq,发布通知公告到IM
		Dict dict = dictService.loadByTypeTagValue( "notifyType"+columnId+"", 1, notify.getNotifyType());
		PublishNotify pn = new PublishNotify(n.getSubject(),dict==null?"":dict.getName(), n.getPublishScopeUserIds());
		pn.start();
		
		ajax(1);
		return null;
	}
	/**
	 * 保存
	 * @return null
	 * @throws ParseException 
	 */
	public String save() throws ParseException{
		notify.setCreateUser(getLoginUser());
		notify.setUpdateUser(getLoginUser());
		notify.setCompanyId(getLoginUser().getCompanyId());
		int groupId = ColumnUtils.getGroupColumnIdByUser(getLoginUser().getUserId());
		notify.setPublishGroupId(groupId);
		if(startDateStr!=null && !"".equals(startDateStr)){
			notify.setBeginDate(new Timestamp(dateFormat.parse(startDateStr).getTime()));
		}
		if(endDateStr!=null && !"".equals(endDateStr)){
			notify.setEndDate(new Timestamp(dateFormat.parse(endDateStr).getTime()));
		}
		if(notify.getStatus()==2){  //如果不需要审批，直接发布。设置发布时间是当前
			notify.setAuditer(getLoginUser().getUserId());
		}
		notifyService.save(notify);
		if (notify.getId() != null && notify.getStatus() == 2) {
			final int userId = this.getLoginUser().getUserId();
			new Thread(new Runnable() {
				@Override
				public void run() {
//					pushMobileService.sendPush(userId + "",
//							notify.getSubject(), "", "公告",
//							notify.getPublishScopeUserIds(), notify.getId()
//									+ "", "", "");
					String ids = notify.getPublishScopeUserIds();
					if(StringUtils.isNotEmpty(ids)){
						String[] idsArr = ids.split(",");
						String iosTags="";
						String androidTags="";
						for(String id:idsArr){
							if(StringUtils.isNotEmpty(id)){
								String iosTag = "ios_user_"+id;
								String androidTag = "android_user_"+id;
								iosTags+=(checkTest(iosTag)+",");
								androidTags+=(checkTest(androidTag)+",");
							}
						}
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("recordId", notify.getId());
						map.put("moduleName", "通知公告");
						map.put("mt", "notice");
						if(StringUtils.isNotEmpty(notify.getAttment())){
							map.put("attmentIds", notify.getAttment());
						}
						
						Map<String, Object> extraSub=new HashMap<String, Object>();
						extraSub.put("sound", "default");
						extraSub.put("content-available", "1");
						extraSub.put("badge", "");
						map.put("ios",extraSub);
						String title="新密市人民检察院办公平台";
						String content="你收到了一条公告";
						Gson json = new Gson();
						pushPlatService.pushJpushToPlat(notify.getCompanyId(), androidTags,PLAT_TYPE.ANDROID, content,title, json.toJson(map), JPUSH_PUSH_TYPE.TAG,null);
				    	pushPlatService.pushJpushToPlat(notify.getCompanyId(), iosTags, PLAT_TYPE.IOS, content,content, json.toJson(map), JPUSH_PUSH_TYPE.TAG,IOS_PUSH_TYPE.NOTIFY,null);
					}
				}
			}).start();
		}
		return null;
	}
	
	/**
	 * 修改
	 */
	public String update() throws ParseException{
		notify.setUpdateUser(getLoginUser());
		if(startDateStr!=null && !"".equals(startDateStr)){
			notify.setBeginDate(new Timestamp(dateFormat.parse(startDateStr).getTime()));
		}
		if(endDateStr!=null && !"".equals(endDateStr)){
			notify.setEndDate(new Timestamp(dateFormat.parse(endDateStr).getTime()));
		}
		notify.setCompanyId(TransportUser.get().getCompanyId());
		if(notify.getStatus()==2){  //如果不需要审批，直接发布。设置发布时间是当前
			notify.setAuditer(getLoginUser().getUserId());
			final int userId = this.getLoginUser().getUserId();
			new Thread(new Runnable() {
				@Override
				public void run() {
//					pushMobileService.sendPush(userId+"",notify.getSubject(), "", "通知公告",  notify.getPublishScopeUserIds(),notify.getId()+"","", "");
					String ids = notify.getPublishScopeUserIds();
					if(StringUtils.isNotEmpty(ids)){
						String[] idsArr = ids.split(",");
						String iosTags="";
						String androidTags="";
						for(String id:idsArr){
							if(StringUtils.isNotEmpty(id)){
								String iosTag = "ios_user_"+id;
								String androidTag = "android_user_"+id;
								iosTags+=(checkTest(iosTag)+",");
								androidTags+=(checkTest(androidTag)+",");
							}
						}
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("recordId", notify.getId());
						map.put("moduleName", "通知公告");
						map.put("mt", "notice");
						if(StringUtils.isNotEmpty(notify.getAttment())){
							map.put("attmentIds", notify.getAttment());
						}
						Map<String, Object> extraSub=new HashMap<String, Object>();
						extraSub.put("sound", "default");
						extraSub.put("content-available", "1");
						extraSub.put("badge", "");
						map.put("ios",extraSub);
						String title="新密市人民检察院办公平台";
						String content="您有一条新的通知";
						Gson json = new Gson();
						pushPlatService.pushJpushToPlat(notify.getCompanyId(), androidTags,PLAT_TYPE.ANDROID, content,title, json.toJson(map), JPUSH_PUSH_TYPE.TAG,null);
				    	pushPlatService.pushJpushToPlat(notify.getCompanyId(), iosTags, PLAT_TYPE.IOS, content,content, json.toJson(map), JPUSH_PUSH_TYPE.TAG,IOS_PUSH_TYPE.NOTIFY,null);
					}
				}
			}).start();
			
		}
		notify.setUpdateDate(new Date());
		notifyService.update(notify);
		return null;
	}
	/**
	 * 维护列表
	 * @return
	 */
	public String list(){
		Sort sort = new Sort(new Sort.Order(Direction.DESC,"isTop"),new Sort.Order(Direction.DESC,"createDate"));
		Page<Notify> pageInfo = notifyService.list(getPageable(sort),notifyType,subject,beginDate,endDate,getLoginUser(),columnId,status);
		List<Notify> list = pageInfo.getContent();
		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
		for(Notify notify:list){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id",notify.getId());
			map.put("subject",notify.getSubject());
			map.put("isTop",notify.getIsTop());
			if(notify.getNotifyType()!=null){
				Dict dict = dictService.loadByTypeTagValue( "notifyType"+columnId+"", 1, notify.getNotifyType());
				map.put("notifyType",dict!=null?dict.getName():"--");
			}else{
				map.put("notifyType","--");
			}
			map.put("username",notify.getCreateUser().getUserName());
			map.put("createDate",DateTimeUtil.dateToString(notify.getCreateDate(),"yyyy-MM-dd HH:mm"));
			map.put("browse",notifyViewService.countNotifyPeoples(notify.getId())+"/"+notify.getPublishScopeUserIds().substring(0,notify.getPublishScopeUserIds().length()-1).split(",").length);
			if(notify.getSendType()!=null && notify.getStatus()==0){
				map.put("browse","--");
			}else{
			map.put("browse",notifyViewService.countNotifyPeoples(notify.getId())+"/"+notify.getPublishScopeUserIds().substring(0,notify.getPublishScopeUserIds().length()-1).split(",").length);
			}
			if(notify.getEndDate()!=null &&notify.getStatus()==2){
				if(dateFormat.format(new Date()).equals(dateFormat.format(notify.getEndDate()))){
					map.put("status",notify.getStatus());
				}else{
					if(new Date().before(notify.getEndDate())){
						map.put("status",notify.getStatus());
					}else{
						map.put("status",4);
					}
				}
			}else{
				map.put("status",notify.getStatus());
			}
			map.put("beginDate",DateTimeUtil.dateToString(notify.getBeginDate(),"yyyy-MM-dd HH:mm"));
			map.put("endDate",DateTimeUtil.dateToString(notify.getEndDate(),"yyyy-MM-dd HH:mm"));
			if(notify.getIsForkGroup()!=null&&notify.getIsForkGroup().equals(getLoginUser().getIsForkGroup())){
				map.put("isForkGroup",1);
			}else{
				map.put("isForkGroup",0);
			}
			resultList.add(map);
		}
		ajaxPage(pageInfo, resultList);
		return null;
	}
	/**
	 * 初始化按钮.
	 * 若不需要审批前台为“发布”；若需要审批则可直接发布人前台为“发布”，其他人员为“提交”。
	 * @return null
	 */
	public String initButtion(){
		String result = "";
		tbColumnSetting = (TbColumnSetting) platformParameterService.findEntityByInstenceId(columnId);
		if(null!=tbColumnSetting){
			String isAuditing = tbColumnSetting.getIsAuditing();  //是否需要审批
			if("0".equals(isAuditing)){
				result = "0";
			}else{
				String rangeExceptionUser = tbColumnSetting.getPublishUserIds();//指定可不经审批直接发布公告的人员
				if(rangeExceptionUser.contains(String.valueOf(","+getLoginUser().getUserId()+",")) || rangeExceptionUser.startsWith(getLoginUser().getUserId()+",")){
					result = "0";
				}else{
					result = "1";
				}
			}
		}else{
			result = "0";
		}
		ajax(result);
		return null;
	}
	/**
	 * 功能：查看是否具有转发权限.
	 * 可直接发布人员有转发权限
	 * @return null
	 */
	public String initTransmitButtion(){
		String result = "0";
		tbColumnSetting = (TbColumnSetting) platformParameterService.findEntityByInstenceId(columnId);
		if(null!=tbColumnSetting){
				String rangeExceptionUser = tbColumnSetting.getPublishUserIds();//指定可不经审批直接发布公告的人员
				if(rangeExceptionUser.contains(String.valueOf(","+getLoginUser().getUserId()+",")) || rangeExceptionUser.startsWith(getLoginUser().getUserId()+",")){
					result = "1";
				}
		}
		ajax(result);
		return null;
	}
	
	/**
	 * 加载配置信息
	 * @return null
	 */
	public String loadSetInfo(){
		tbColumnSetting = (TbColumnSetting) platformParameterService.findEntityByInstenceId(columnId);
		ajax(JsonUtils.generJsonString(tbColumnSetting));
		return null;
	}
	/**
	 * 设置配置信息
	 * @return null
	 */
	public String notifySet(){
		String resultStr = "设置失败！";
			PlatformParameter platformParameter = platformParameterService.findByInstenceId(instentceid);
		Gson json = new Gson();
		if(null!=platformParameter){
			platformParameter.setParValueColl(json.toJson(tbColumnSetting));
		}else{
			platformParameter = new PlatformParameter();
			platformParameter.setParItems(tbColumnSetting.getClass().getCanonicalName());
			platformParameter.setIsDelete(0);
			platformParameter.setParValueColl(json.toJson(tbColumnSetting));
			platformParameter.setCompanyId(getLoginUser().getCompanyId());
		}
		platformParameterService.saveOrUpdate(platformParameter);
		resultStr = "设置成功！";
		ajax(resultStr);
		return null;
	}
	/**
	 * 功能：查询公告可审批人员
	 * @return 返回到前台json数据
	 */
	/*public String getAuditer(){
		tbColumnSetting = (TbColumnSetting) platformParameterService.findEntityByInstenceId(columnId);
		String rangeAllId = tbColumnSetting.getApproveUserId(); //得到审批人ID
		List<UserInfo> userlist = new ArrayList<UserInfo>();
		//新添加的加入部门主管的信息
		if(tbColumnSetting.getNotifyAuditingManager() == 1){ // 是部门主管
			GroupInfo groupInfo= groupService.findOne(getLoginUser().getGroupId());
		    int groupinfovoid=groupInfo.getDirectorId();
		    UserInfo  userinfo=userService.findOne(groupinfovoid);
		    userlist.add(userinfo);
		}
		//新添加的加入部门主管的信息
		if(!StringUtils.isEmpty(rangeAllId)){
			userlist = userService.findUsersByIds(rangeAllId);
		}
		ajax(userlist);
        return null;
	}*/
	/**
	 * 删除一条记录
	 */
	public void del(){
		notifyService.delByIds(id+"");
		ajax(true);
	}
	/**
	 * 撤销
	 */
	public void draw(){
		notifyService.draw(id);
		ajax(true);
	}
	/**
	 * 终止
	 */
	public void stop(){
		notifyService.stop(id);
		ajax(true);
	}
	/**
	 * 删除多条记录
	 */
	public void delByIds(){
		notifyService.delByIds(ids);
		ajax(true);
	}
	/**
	 * 置顶设置
	 */
	public void topSet(){
		notifyService.updateTop(ids,isTop);
		ajax(true);
	}
	
	/**
	 * 部门专栏查看列表
	 */
	@SuppressWarnings("deprecation")
	public String clmViewList() {
		Page<Map<String, Object>> pageInfo = notifyService.clmViewList(getPageable(),getLoginUser().getUserId(),notifyType,subject,columnId,groupId);
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("iTotalRecords", pageInfo.getTotalElements());
		jsonMap.put("iTotalDisplayRecords", pageInfo.getTotalElements());
		jsonMap.put("aaData", pageInfo.getContent());
		ajax(JsonUtils.generWithDateJsonString("yyyy-MM-dd HH:mm",jsonMap));
		return null;
	}
	/**
	 * 功能：部门专栏查看
	 * @return
	 */
	public String viewNotify(){
		Map<String,Object> returnMap = notifyService.viewNotify(id);
		returnMap.put("totalCount",returnMap.get("publishUserIds").toString().substring(0,returnMap.get("publishUserIds").toString().length()-1).split(",").length);
		ajax(JsonUtils.generWithDateJsonString("yyyy年MM月dd日 HH:mm",returnMap));
		return null;
	}
	
	/**
     * 功能：判断是否是测试环境
     * @param tag
     * @return
     */
    private String checkTest(String tag){
    	if (PushControl.isTest()) {
			tag="test_"+tag;
		}
    	return tag;
    }
	
	public Integer getNotifyType() {
		return notifyType;
	}

	public void setNotifyType(Integer notifyType) {
		this.notifyType = notifyType;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getColumnId() {
		return columnId;
	}

	public void setColumnId(Integer columnId) {
		this.columnId = columnId;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public Integer getIsTop() {
		return isTop;
	}

	public void setIsTop(Integer isTop) {
		this.isTop = isTop;
	}

	public Notify getNotify() {
		return notify;
	}

	public void setNotify(Notify notify) {
		this.notify = notify;
	}

	public TbColumnSetting getTbColumnSetting() {
		return tbColumnSetting;
	}

	public void setTbColumnSetting(TbColumnSetting tbColumnSetting) {
		this.tbColumnSetting = tbColumnSetting;
	}

	public Integer getInstentceid() {
		return instentceid;
	}

	public void setInstentceid(Integer instentceid) {
		this.instentceid = instentceid;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSearchWord() {
		return searchWord;
	}

	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}

	public String getStartDateStr() {
		return startDateStr;
	}

	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}

	public String getEndDateStr() {
		return endDateStr;
	}

	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public Integer getIsShowOut() {
		return isShowOut;
	}

	public void setIsShowOut(Integer isShowOut) {
		this.isShowOut = isShowOut;
	}
	
}
