package cn.com.qytx.cbb.jpush.action;


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
 * <br/>功能: 编辑推送
 * <br/>版本: 1.0
 * <br/>开发人员: 任
 * <br/>创建日期: 2013-3-22
 * <br/>修改日期: 2013-3-22
 * <br/>修改列表:
 */
public class PushEditAction  extends BaseActionSupport{
	private static final long serialVersionUID = 1L;
	@Resource(name = "pushInfoService")
    IPushInfo pushInfoService;
	@Resource(name = "pushUserService")
    IPushUser pushUserService;
	@Resource(name = "pushService")
    IPush pushService;//推送
	private Integer pushId;//推送人员
	private String userIds;

	/**
	 * 
	 * 功能：编辑推送
	 */
	public String editPush(){
		try {
			HttpServletRequest request = getRequest();
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
		            + request.getServerName() + ":" + request.getServerPort()
		            + path+"/" ;
			UserInfo userInfo=(UserInfo) this.getSession().getAttribute("adminUser");
			if(userInfo!=null&&pushId!=null){
					//得到推送
				//	PushInfo pushInfo=pushInfoService.findById(pushId);
					PushInfo pushInfo=pushInfoService.findOne(pushId);
					//添加推送人员
					pushUserService.addPushUser(pushId, userIds);

					//发送活动通知
					if(StringUtils.isNotEmpty(userIds)){
						String[] userIdArr=userIds.split(",");
						for (String userId : userIdArr) {
							if(userId.length()>3){
								PushUser pushUser=new PushUser();
								pushUser.setPushId(pushId);
								pushUser.setUserId(Integer.valueOf(userId.substring(2)));
								if(userId.startsWith("m_")){
									//管理员
									pushUser.setUserType(1);
								}else if(userId.startsWith("c_")){
									//公司
									String newUserId=userId.replace("c_", "company_");
									pushUser.setUserType(2);
									Map<String, String> map = new HashMap<String,String>();
									String pushUrl = basePath + "mobile/findById.action?id="+pushId+"&_clientType=wap";
									map.put("pushUrl", pushUrl);
									Integer result= pushService.sendNotificationWithTag(newUserId,"最新消息",pushInfo.getSubject(),0,map,getValue("masterSecret"),getValue("appKey"));
									if (result == ErrorCode.NOERROR.value()) {
										ajax("1");
									} else {
										ajax("2");
									}
								}
							}
						}
					//保存日志
				 /*   String action="活动推送";
				    String logContent="提交活动推送成功";*/
				//    logDao.addLog(this.getRequest(),action,logContent);
				    ajax("3");
				}
			}
		} catch (Exception e) {  
		    LOGGER.error(e.getMessage());
			return null;
		}
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
		} catch (Exception e1) {
		    LOGGER.error(e1.getMessage());
		}
		return null;
    }
	public Integer getPushId() {
		return pushId;
	}

	public void setPushId(Integer pushId) {
		this.pushId = pushId;
	}

	public String getUserIds() {
		return userIds;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}
}
