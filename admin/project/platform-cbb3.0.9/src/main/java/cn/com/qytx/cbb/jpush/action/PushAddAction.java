package cn.com.qytx.cbb.jpush.action;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import cn.com.qytx.cbb.jpush.domain.PushInfo;
import cn.com.qytx.cbb.jpush.domain.PushUser;
import cn.com.qytx.cbb.jpush.service.IPush;
import cn.com.qytx.cbb.jpush.service.IPushInfo;
import cn.com.qytx.cbb.jpush.service.IPushUser;
import cn.com.qytx.cbb.jpush.service.impl.ErrorCode;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.org.domain.UserInfo;

/**
 * 
 * <br/>功能: 添加推送
 * <br/>版本: 1.0
 * <br/>开发人员: 任
 * <br/>创建日期: 2013-3-22
 * <br/>修改日期: 2013-3-22
 * <br/>修改列表:
 */
public class PushAddAction  extends BaseActionSupport{
	private static final long serialVersionUID = 1L;
	@Resource(name = "pushInfoService")
    IPushInfo pushInfoService;
	@Resource(name = "pushUserService")
    IPushUser pushUserService;
	@Resource(name = "pushService")
    IPush pushService;
	private PushInfo pushInfo;//推送
	private String userIds=""; //推送人员
	/**
	 *  	
	 * 功能：添加推送
	 */
	public String addPush(){
		try {
			HttpServletRequest request = getRequest();
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
		            + request.getServerName() + ":" + request.getServerPort()
		            + path+"/" ;
			UserInfo userInfo=(UserInfo) this.getSession().getAttribute("adminUser");
			int res=0;
			if(userInfo!=null&&pushInfo!=null){
					pushInfo.setUserId(userInfo.getUserId());
					pushInfo.setUserName(userInfo.getUserName());
					pushInfo.setInsertTime(new Timestamp(System.currentTimeMillis()));
					pushInfo.setPushTime(new Timestamp(System.currentTimeMillis()));
					pushInfo.setIsDelete(0);
					pushInfoService.saveOrUpdate(pushInfo); //保存数据
					int pushId=pushInfo.getPushId();
					if(pushId>0){
						pushUserService.addPushUser(pushId, userIds);
					}
					//发送活动通知
					if(StringUtils.isNotEmpty(userIds)){
						String[] userIdArr=userIds.split(",");
						for (String userId : userIdArr) {
							PushUser pushUser=new PushUser();
							pushUser.setPushId(pushId);
							pushUser.setUserId(Integer.parseInt(userId));
							pushUser.setUserType(1);
							Map<String, String> map = new HashMap<String,String>();
							String pushUrl = basePath + "mobile/findById.action?id="+pushId+"&_clientType=wap";
							map.put("pushUrl", pushUrl);
							Integer result= pushService.sendNotificationWithTag(userId,"最新消息",pushInfo.getSubject(),0,map,getValue("masterSecret"),getValue("appKey"));
							LOGGER.info("result"+result+";"+ErrorCode.NOERROR.value());
							/*if (result != ErrorCode.NOERROR.value()) {
								res=0;
								pushInfoService.delete(pushId,false);
							}*/
							if (result == ErrorCode.NOERROR.value()) {
								res=1;
							}
						}
					}
					if (res==0) {
						pushInfoService.delete(pushId,true);
					}
				}
			
			ajax(res);
			
		} catch (Exception e) {  
			LOGGER.error(e.getMessage());
			return null;
		}
		return null;
	}
	
	/**
	 * http推送接口
	 * add by 7-10
	 * @return
	 */
	private String fromUserId;
	private String subJect;
	private String pushContent;
	private String moduleName;
	private String recordId;
	private String pushUrl;
	private String pushType;
	private String attmentIds;
	
	public String sendPush(){
		//发送活动通知
		userIds=userIds.trim();
		if (userIds.startsWith(",")) {
			userIds=userIds.substring(1, userIds.length());
		}
		if (userIds.endsWith(",")) {
			userIds=userIds.substring(0,userIds.length()-1);
		}
		int res=0;
		if(StringUtils.isNotEmpty(userIds)){
			Map<String, String> map = new HashMap<String,String>();
			map.put("fromUserId", fromUserId);
//			if(StringUtils.isNoneBlank(subJect)){
//			map.put("subJect", subJect);
//			}
			if(StringUtils.isNoneBlank(pushContent)){
			map.put("pushContent", pushContent);
			}
			if(StringUtils.isNoneBlank(moduleName)){
			map.put("moduleName", moduleName);
			}
			if(StringUtils.isNoneBlank(recordId)){
			map.put("recordId", recordId);
			}
			if(StringUtils.isNoneBlank(pushUrl)){
			map.put("pushUrl", pushUrl);
			}
			if (StringUtils.isNotEmpty(pushType)) {
				map.put("type", pushType);
			}
			if(StringUtils.isNotBlank(attmentIds)){
				map.put("attmentIds", attmentIds);
			}
			String[] userIdArr=userIds.split(",");
			for (String userId : userIdArr) {
				Integer result= pushService.sendNotificationWithTag(userId,"最新消息",subJect,0,map,getValue("masterSecret"),getValue("appKey"));
				LOGGER.info("result"+result+";"+ErrorCode.NOERROR.value());
				if (result == ErrorCode.NOERROR.value()) {
					res=1;
				}
			}
		}
		ajax(res);  //1成功 0 失败
		return null;
	}
	
	
	/**
     * 获取配置信息
     * @param keyString
     * @return
     */
    public  String getValue(String keyString){
    	Properties prop = null;
		prop = new Properties();
		try {
			prop.load(getClass().getResourceAsStream("/application.properties"));
			return prop.getProperty(keyString);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return null;
    }
    
	public PushInfo getPushInfo() {
		return pushInfo;
	}
	public void setPushInfo(PushInfo pushInfo) {
		this.pushInfo = pushInfo;
	}
	public String getUserIds() {
		return userIds;
	}
	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}
	public String getFromUserId() {
		return fromUserId;
	}
	public void setFromUserId(String fromUserId) {
		this.fromUserId = fromUserId;
	}
	public String getSubJect() {
		return subJect;
	}
	public void setSubJect(String subJect) {
		this.subJect = subJect;
	}
	public String getPushContent() {
		return pushContent;
	}
	public void setPushContent(String pushContent) {
		this.pushContent = pushContent;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getRecordId() {
		return recordId;
	}
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	public String getPushUrl() {
		return pushUrl;
	}
	public void setPushUrl(String pushUrl) {
		this.pushUrl = pushUrl;
	}
	public String getPushType() {
		return pushType;
	}
	public void setPushType(String pushType) {
		this.pushType = pushType;
	}


	public String getAttmentIds() {
		return attmentIds;
	}


	public void setAttmentIds(String attmentIds) {
		this.attmentIds = attmentIds;
	}
	
}
