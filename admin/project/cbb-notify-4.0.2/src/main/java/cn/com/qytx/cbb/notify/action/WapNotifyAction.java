package cn.com.qytx.cbb.notify.action;

import java.io.File;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;

import cn.com.qytx.cbb.dict.domain.Dict;
import cn.com.qytx.cbb.dict.service.IDict;
import cn.com.qytx.cbb.file.config.FilePathConfig;
import cn.com.qytx.cbb.file.domain.Attachment;
import cn.com.qytx.cbb.file.service.IAttachment;
import cn.com.qytx.cbb.notify.domain.Notify;
import cn.com.qytx.cbb.notify.domain.NotifyComment;
import cn.com.qytx.cbb.notify.service.INotify;
import cn.com.qytx.cbb.notify.service.IPlatformParameter;
import cn.com.qytx.cbb.notify.service.IWapNotify;
import cn.com.qytx.cbb.notify.service.IWapNotifyComment;
import cn.com.qytx.cbb.notify.service.IWapNotifyView;
import cn.com.qytx.cbb.notify.utils.ColumnUtils;
import cn.com.qytx.cbb.notify.vo.TbColumnSetting;
import cn.com.qytx.cbb.push.service.PushPlatService;
import cn.com.qytx.cbb.push.service.PushPlatService.IOS_PUSH_TYPE;
import cn.com.qytx.cbb.push.service.PushPlatService.JPUSH_PUSH_TYPE;
import cn.com.qytx.cbb.push.service.PushPlatService.PLAT_TYPE;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Sort;
import cn.com.qytx.platform.base.query.Sort.Direction;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IUser;
import cn.com.qytx.platform.utils.JsonUtils;
import cn.com.qytx.platform.utils.datetime.DateTimeUtil;
import cn.com.qytx.rangecontrol.PushControl;

/**
 * 功能:手机端（列表、附件、详情）
 */
public class WapNotifyAction extends BaseActionSupport{
	
	@Resource(name = "platformParameterService")
	private IPlatformParameter platformParameterService;
	@Resource(name = "wapNotifyService")
	private IWapNotify wapNotifyService;
	@Resource(name = "wapNotifyViewImpl")
	private IWapNotifyView wapNotifyViewService;
	@Resource(name="wapNotifyCommentImpl")
	private IWapNotifyComment wapNotifyCommentService;
	@Resource(name="userService")
	private IUser userService;
	@Resource(name="filePathConfig")
	private FilePathConfig filePathConfig;
	@Resource(name="attachmentService")
	private IAttachment attachmentService;
	@Resource(name="dictService")
	private IDict dictService;
	@Resource(name="notifyService")
	private INotify notifyService;
	@Autowired
	private PushPlatService pushPlatService;
	private int columnId;
	private Integer userId;
	private int notifyType;
	private String subject;
	private Integer notifyId;
	private String attmentIds;
	private Integer status;
	private String reason;
	private Integer readStatus=0;//0或不传全部 1已读 2 未读
	
	private Notify notify;
	
	/**
	 * 保存
	 * @return null
	 * @throws ParseException 
	 */
	public String save() throws ParseException{
		try{
			if(userId == null){
				ajax("102||userId不能为空!");
			}
			if(notify==null||StringUtils.isEmpty(notify.getSubject())||StringUtils.isEmpty(notify.getPublishScopeUserIds())||StringUtils.isEmpty(notify.getContent())){
				ajax("102||公告参数不能为空!");
			}
			UserInfo userInfo = userService.findOne(userId);
			notify.setViewCount(0);
			notify.setCreateUser(userInfo);
			notify.setUpdateUser(userInfo);
			notify.setCompanyId(userInfo.getCompanyId());
			int groupId = ColumnUtils.getGroupColumnIdByUser(userId);
			notify.setPublishGroupId(groupId);
			if(notify.getStatus()==2){  //如果不需要审批，直接发布。设置发布时间是当前
				notify.setAuditer(userId);
			}
			notifyService.save(notify);
			if (notify.getId() != null && notify.getStatus() == 2) {
				new Thread(new Runnable() {
					@Override
					public void run() {
//						pushMobileService.sendPush(userId + "",
//								notify.getSubject(), "", "公告",
//								notify.getPublishScopeUserIds(), notify.getId()
//										+ "", "", "");
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
						}
					}
				}).start();
			}
			ajax("100||操作成功");
		}catch(Exception e){
			ajax("102||系统异常,请稍后重试!");
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 公告列表
	 * @return
	 */
	public String viewList(){
		Sort sort = new Sort(new Sort.Order(Direction.DESC,"isTop"),new Sort.Order(Direction.DESC,"approveDate"));
        TbColumnSetting tbColumnSetting = (TbColumnSetting) platformParameterService.findEntityByInstenceId(columnId);
        if(readStatus == null){
        	readStatus=0;
        }
        Page<Notify> pageInfo = wapNotifyService.viewList(getPageable(sort),subject,userId,notifyType,columnId,readStatus);
        List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
        List<Notify> list = pageInfo.getContent();
        if(list!=null && list.size() > 0){
        	 String basePath = getRequest().getScheme() + "://" + getRequest().getServerName() + ":" + getRequest().getServerPort()+getRequest().getContextPath()+"/";
        	for(Notify notify:list){
        		Map<String,Object> map = new HashMap<String, Object>();
        		map.put("notifyId",notify.getId());
        		map.put("createTime",DateTimeUtil.dateToString(notify.getCreateDate(),"yyyy-MM-dd HH:mm:ss"));
        		map.put("approveTime", DateTimeUtil.dateToString(notify.getApproveDate(),"yyyy-MM-dd HH:mm:ss"));
        		map.put("subject",notify.getSubject());
        		String content = notify.getContent()==null?"":notify.getContent();

        		if(StringUtils.isNotEmpty(content)){
        			content = content.replaceAll("<.*?>", "");
        			content = content.replaceAll("&nbsp;", "");
        			if(content.length()>100){
        				content = content.substring(0,100);
        			}
        		}
        			        		
        		map.put("content",content);
        		map.put("createUserName",notify.getCreateUser().getUserName());
        		map.put("createUserId", notify.getCreateUser().getUserId());
        		map.put("notifyType",notify.getNotifyType());
        		map.put("isTop",notify.getIsTop());
        		if(wapNotifyViewService.isReadNotifyView(userId,notify.getId())){
        			map.put("isSignCheck",1);
        		}else{
        			map.put("isSignCheck",0);
        		}
        		if(StringUtils.isNotBlank(notify.getImages())){
        			String[] imageId = notify.getImages().split(",");
        			if(imageId[0]!=null &&!"".equals(imageId[0])){
        				map.put("image",basePath+"/"+"filemanager/downfile.action?attachmentId="+imageId[0]+"&_clientType=wap");
        			}else{
        				map.put("image",basePath+"/"+"filemanager/downfile.action?attachmentId="+imageId[1]+"&_clientType=wap");
        			}
        		}else{
        			map.put("image","");
        		}
        		map.put("attmentIds",notify.getAttment());
        		dataList.add(map);
        	}
        }
	    Map<String,Object> returnMap = new HashMap<String,Object>();    
	    returnMap.put("showImage",tbColumnSetting.getShowImage());
	    returnMap.put("isComment",tbColumnSetting.getIsComment());
	    returnMap.put("mapList",dataList);
	    ajax("100||"+JsonUtils.generJsonString(returnMap));
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
	/**
	 * 功能：详情
	 * @return
	 */
	public String view() {
		Notify notify = wapNotifyService.findOne(notifyId);
		if (notify.getImages() != null && !"".equals(notify.getImages())) {
			notify.setImagesList(attachmentService.getAttachmentsByIds(notify.getImages()));
		}
		if (notify.getAttment() != null && !notify.getAttment().equals("")) {
			notify.setAttachmentList(attachmentService.getAttachmentsByIds(notify.getAttment()));
		}
		ajax(JsonUtils.generWithDateJsonString("yyyy-MM-dd HH:mm:ss",notify,"updateUser","viewList","commentList"));
		return null;
	}
	/**
	 * 功能：详情
	 * @return
	 */
	public String viewInfo(){
		Notify notify = wapNotifyService.findOne(notifyId);
		Map<String,Object> returnMap = new HashMap<String, Object>();
		returnMap.put("total",notify.getViewCount());
		returnMap.put("publishDept",notify.getPublishScopeUserIds());
		if(notify.getAuditer()!=null){
			returnMap.put("auditer",userService.findOne(notify.getAuditer()).getUserName());
		}
		String format = "yyyy-MM-dd HH:mm:ss";
		returnMap.put("summary",notify.getSummary());
		returnMap.put("attamentId",notify.getAttment());
		returnMap.put("subject",notify.getSubject());
		returnMap.put("lastUpdateTime",DateTimeUtil.dateToString(notify.getUpdateDate(),format));
		returnMap.put("lastModifyUser",notify.getUpdateUser().getUserName());
		returnMap.put("endDate",DateTimeUtil.dateToString(notify.getEndDate(),format));
		returnMap.put("content",delHTMLTag(notify.getContent()));
		returnMap.put("createUserName",notify.getCreateUser().getUserName());
		returnMap.put("createUserId", notify.getCreateUser().getUserId()+"");
		returnMap.put("createTimeStr",DateTimeUtil.dateToString(notify.getCreateDate(),format));
		returnMap.put("begDate",DateTimeUtil.dateToString(notify.getBeginDate(),format));
		returnMap.put("notifyType",notify.getNotifyType());
		returnMap.put("isTop",notify.getIsTop());
		returnMap.put("approveTime",DateTimeUtil.dateToString(notify.getApproveDate(),format));
		List<NotifyComment> list = wapNotifyCommentService.getMaxTwoComment(notifyId);
		if(list!=null && list.size()>0){
			List<Map<String,Object>> commentList = new ArrayList<Map<String,Object>>();
			for(NotifyComment notifyComment:list){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("id",notifyComment.getId());
				map.put("username",notifyComment.getCreateUserInfo().getUserName());
				map.put("comment",notifyComment.getComment());
				map.put("createDate",DateTimeUtil.dateToString(notifyComment.getCreateDate(),format));
				if(notifyComment.getCreateUserInfo().getUserId().equals(userId) || userService.findOne(userId).getIsDefault()==0){
					map.put("isDelete",1);
				}else{
					map.put("isDelete",0);
				}
				commentList.add(map);
			}
			returnMap.put("commentList",commentList);
		}else{
			returnMap.put("commentList",new ArrayList());
		}
		ajax("100||"+JsonUtils.generJsonString(returnMap));
		return null;
	}
	/**
	 * 功能：附件
	 * @return
	 */
	public String attachList(){
		HttpServletRequest request = ServletActionContext.getRequest (); 
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":"
            + request.getServerPort() + path + "/";
		 List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		 if(attmentIds!=null && !"".equals(attmentIds)){
		   String atta[]=attmentIds.split(",");
			for(String attmentId : atta){
				Map<String, Object> dataMap = new HashMap<String, Object>();
					if(!StringUtils.isEmpty(attmentId)){
						Attachment attachment = attachmentService.getAttachment(Integer.parseInt(attmentId));
						if(null!=attachment){
						//根据不同的文件后缀显示不同的图片
							String suffix = "";
							if(attachment.getAttachName().toLowerCase().indexOf(".")>=0){
								suffix=attachment.getAttachName().toLowerCase().substring(attachment.getAttachName().lastIndexOf(".")+1);
							}
				        byte[] bytesize= attachment.getAttachName().getBytes();
				        String url=basePath+"filemanager/downfile.action?_clientType=wap&attachmentId="+attachment.getId();
				        String catalinaHome = filePathConfig.getFileUploadPath();
			            String targetDirectory = catalinaHome + "/upload/";
			            String filedownload = targetDirectory + attachment.getAttachFile();
			            File f = new File(filedownload);
			            String fileSize = "";
			            if(f.exists()){
			                fileSize=	formetFileSize(f.length());
			            }
			            String viewUrl=basePath+"filemanager/previewDoc.action?_clientType=wap&attachmentId="+attachment.getId();
			            dataMap.put("suffix", suffix);
			            dataMap.put("viewUrl",viewUrl);
			            dataMap.put("fileSize", fileSize);
			            dataMap.put("attachmentName", attachment.getAttachName());
			            dataMap.put("url", url);
			        	mapList.add(dataMap);
				}
			}
	   }
	}
		 ajax("100||"+JsonUtils.generJsonString(mapList));
		 return null;
	}
	/**
	 * 手机端详细信息
	 * @return
	 */
	public String mobileInfo(){
		Map<String,Object> returnMap = new HashMap<String, Object>();
		Notify notify = wapNotifyService.findOne(notifyId);
		returnMap.put("subject",notify.getSubject());
		returnMap.put("createDate",DateTimeUtil.dateToString(notify.getCreateDate(),"yyyy-MM-dd HH:mm:ss"));
		returnMap.put("username",notify.getCreateUser().getUserName());
		returnMap.put("content",notify.getContent());
		ajax("100||"+JsonUtils.generJsonString(returnMap));
		return null;
	}
	/**
	 * 手机端流程
	 * @return
	 */
	public String mobileFlow(){
		Map<String,Object> returnMap = new HashMap<String, Object>();
		Notify notify = wapNotifyService.findOne(notifyId);
		returnMap.put("createDate",DateTimeUtil.dateToString(notify.getCreateDate(),"yyyy-MM-dd HH:mm:ss"));
		returnMap.put("username",notify.getCreateUser().getUserName());
		returnMap.put("status",notify.getStatus());
		returnMap.put("approveDate",DateTimeUtil.dateToString(notify.getApproveDate(),"yyyy-MM-dd HH:mm:ss"));
		returnMap.put("approveName",notify.getAuditer()==null?"":userService.findOne(notify.getAuditer()).getUserName());
		ajax("100||"+JsonUtils.generJsonString(returnMap));
		return null;
	}
	  /**
     * 
     * 功能：根据大小得到b k  m  g
     * @param fileS
     * @return
     */
    public String formetFileSize(long fileS) {//转换文件大小
     //   DecimalFormat df = new DecimalFormat("#.00");
        DecimalFormat df = new DecimalFormat("0.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }
	/**
     * 功能： 获取附件后缀
     * @return
     */
    public String getAttacthSuffix(String attacthName){
    	String attacthSuffix = "fileTxt";

    	if(attacthName.trim().equals("")){
			attacthSuffix = "fileTxt";
		}else{
			String[] atts = attacthName.split("\\.");
			if(atts.length>1){
				 String att = atts[atts.length-1];
		       	 if(att.endsWith("doc")||att.endsWith("docx")){
		       		attacthSuffix = "fileWord";
		    	 }else if(att.endsWith("xls")||att.endsWith("xlsx")){
		    		 attacthSuffix = "fileExcel";
		    	 }else if(att.endsWith("ppt")||att.endsWith("pptx")){
		    		 attacthSuffix = "filePPT";
		    	 }else if(att.endsWith("jpg")||att.endsWith("gif")||att.endsWith("png")||att.endsWith("jpeg")||att.endsWith("JPG")||att.endsWith("GIF")||att.endsWith("PNG")||att.endsWith("JPEG")){
		    		 attacthSuffix = "filePicture";
		    	 }else if(att.endsWith("rar")){
		    		 attacthSuffix = "fileRar";
		    	 }
			}else{
				attacthSuffix = "fileTxt";
			}
		}
		return attacthSuffix;
    }
	public static String delHTMLTag(String htmlStr){ 
        String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>"; 
        String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>"; 
        String regEx_html="<[^>]+>";  
        
        Pattern p_script=Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE); 
        Matcher m_script=p_script.matcher(htmlStr); 
        htmlStr=m_script.replaceAll(""); 
        
        Pattern p_style=Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE); 
        Matcher m_style=p_style.matcher(htmlStr); 
        htmlStr=m_style.replaceAll("");  
        
        Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE); 
        Matcher m_html=p_html.matcher(htmlStr); 
        htmlStr=m_html.replaceAll("");  

       return htmlStr.trim(); 
    }
	
	public String columnList(){
		List<Map<String,Object>> returnList = new ArrayList<Map<String,Object>>();
		List<Dict> list = dictService.findList("notifyType"+String.valueOf(columnId),1);
		TbColumnSetting tbColumnSetting = (TbColumnSetting) platformParameterService.findEntityByInstenceId(columnId);
		for(Dict dict:list){
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("name",dict.getName());
			map.put("image",filePathConfig.getFileViewPath()+"upload/notify/mobile/title.jpg");
			Notify notify = wapNotifyService.getLastNotify(columnId,dict.getValue(), userId);
			map.put("subject","");
			map.put("createDate","");
			if(notify!=null){
				map.put("subject",notify.getSubject());
				map.put("createDate",DateTimeUtil.dateToString(notify.getCreateDate(),"yyyy-MM-dd HH:mm:ss"));
			}
			map.put("HasRead",wapNotifyService.getUnReadNotifyCount(columnId, dict.getValue(), userId));
			map.put("notifyType",dict.getValue());
			map.put("showImage",tbColumnSetting.getShowImage());
			returnList.add(map);
		}
		ajax("100||"+JsonUtils.generJsonString(returnList));
		return null;
	}
	public String mobileApprove(){
		wapNotifyService.mobileApprove(notifyId,userId,status,reason);
		ajax("100||0");
		return null;
	}
	
	/**
	 * 功能：获得客户端风格
	 */
	public void getClientStyle(){
		Integer result = 1;
		TbColumnSetting tbColumnSetting = (TbColumnSetting) platformParameterService.findEntityByInstenceId(columnId);
		if(tbColumnSetting!=null){
			if(tbColumnSetting.getClientType()!=null){
				result = tbColumnSetting.getClientType();
			}
		}
		ajax("100||"+result);
	}
	
	public int getColumnId() {
		return columnId;
	}

	public void setColumnId(int columnId) {
		this.columnId = columnId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public int getNotifyType() {
		return notifyType;
	}

	public void setNotifyType(int notifyType) {
		this.notifyType = notifyType;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Integer getNotifyId() {
		return notifyId;
	}

	public void setNotifyId(Integer notifyId) {
		this.notifyId = notifyId;
	}
	public String getAttmentIds() {
		return attmentIds;
	}
	public void setAttmentIds(String attmentIds) {
		this.attmentIds = attmentIds;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Integer getReadStatus() {
		return readStatus;
	}
	public void setReadStatus(Integer readStatus) {
		this.readStatus = readStatus;
	}

	public Notify getNotify() {
		return notify;
	}

	public void setNotify(Notify notify) {
		this.notify = notify;
	} 
	
	
}
