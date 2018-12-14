package cn.com.qytx.cbb.affairs.service.impl;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.lang3.StringUtils;

import cn.com.qytx.cbb.affairs.domain.AffairsBody;
import cn.com.qytx.cbb.affairs.domain.ReminderInfo;
import cn.com.qytx.cbb.affairs.service.IAffairsBody;
import cn.com.qytx.cbb.affairs.service.ISMS;
import cn.com.qytx.cbb.push.service.PushPlatService;
import cn.com.qytx.platform.base.PlatformException;
import cn.com.qytx.platform.base.application.SpringContextHolder;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IUser;
import cn.com.qytx.platform.utils.CbbConst;
import cn.com.qytx.platform.utils.spring.SpringUtil;
import cn.com.qytx.rangecontrol.PushControl;
import cn.com.qytx.cbb.push.service.PushPlatService.ANDROID_PUSH_TYPE;
import cn.com.qytx.cbb.push.service.PushPlatService.JPUSH_PUSH_TYPE;
import cn.com.qytx.cbb.push.service.PushPlatService.PLAT_TYPE;

import com.google.gson.Gson;

public class ThreadSendMsg implements Runnable {
	
	private ReminderInfo reminderInfo;
	
	

	public ThreadSendMsg(ReminderInfo reminderInfo) {
		super();
		this.reminderInfo = reminderInfo;
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		if(StringUtils.isBlank(reminderInfo.getSendType()))
		{
			return;//发送类型为空，不发送消息
		}
		checkReminderInfo(reminderInfo);
		String toIds = reminderInfo.getToids();
		
		if(StringUtils.isNoneBlank(toIds))
		{
			if(toIds.startsWith(","))
			{
				toIds=toIds.substring(1,toIds.length());
			}
			if(toIds.endsWith(","))
			{
				toIds=toIds.substring(0,toIds.length()-1);
			}
		}
		
		reminderInfo.setToids(toIds);
		String sendType=reminderInfo.getSendType();
		String[] arry=sendType.split("_");
		int ret=0;
		if("1".equals(arry[0])){
			//发送在线提醒
			ret=sendAffair(reminderInfo.getFromUserInfo() ,reminderInfo.getAffairContent(), reminderInfo.getToids(), 
					reminderInfo.getAffairUrl(), reminderInfo.getModuleName(),reminderInfo.getSendTime(),reminderInfo.getRecordId());
		}
		if("1".equals(arry[1]))
		{
			//发送短信
			ret=sendSms(reminderInfo.getFromUserInfo() ,reminderInfo.getSmsContent(), reminderInfo.getToids());
		}
		if("1".equals(arry[2]))
		{
			//手机推送
			ret=sendPush(reminderInfo.getFromUserInfo() ,reminderInfo.getPushSubjcet(),reminderInfo.getPushContent(), 
					reminderInfo.getModuleName(),reminderInfo.getToids(),reminderInfo.getRecordId(),
					reminderInfo.getPushUrl(),reminderInfo.getPushType(),reminderInfo.getAttmentIds());
		}
	}

	/**
	 * 检查发送提醒的必填信息是否为空
	 * @param reminderInfo
	 */
	private void checkReminderInfo(ReminderInfo reminderInfo){
		if(reminderInfo==null)
		{
			throw new PlatformException("发送提醒信息不能为空！");
		}
		
		String sendType=reminderInfo.getSendType();
		String[] arry=sendType.split("_");
		if("1".equals(arry[0])){
			//发送在线提醒
			if(StringUtils.isBlank(reminderInfo.getAffairUrl())){
				throw new PlatformException("在线提醒链接不能为空！");
			}			
			if(StringUtils.isBlank(reminderInfo.getAffairContent())){
				throw new PlatformException("在线提醒内容不能为空！");
			}
			
			if(StringUtils.isBlank(reminderInfo.getModuleName())){
				throw new PlatformException("模块名称不能为空！");
			}
			
			if(StringUtils.isBlank(reminderInfo.getToids())){
				throw new PlatformException("被提醒人员Id不能为空！");
			}
			
		}
		if("1".equals(arry[1]))
		{
			//发送短信
			if(StringUtils.isBlank(reminderInfo.getSmsContent())){
				throw new PlatformException("短信内容不能为空！");
			}
			
			if(StringUtils.isBlank(reminderInfo.getToids())){
				throw new PlatformException("被提醒人员Id不能为空！");
			}
			
		}
		if("1".equals(arry[2]))
		{
			//手机推送
			
			if(StringUtils.isBlank(reminderInfo.getToids())){
				throw new PlatformException("被提醒人员Id不能为空！");
			}
									
			if(reminderInfo.getFromUserInfo()==null){
				throw new PlatformException("登录人员不能为空");
			}
			
		}
		
	}
	/**
	 * 发送在线提醒
	 * @param fromUserInfo 发送人
	 * @param affairContent 提醒内容
	 * @param toids 提醒人ids集合
	 * @param affairUrl 提醒链接
	 * @param moduleName 模块名
	 */
	public int sendAffair(UserInfo fromUserInfo ,String affairContent, String toids, String affairUrl, String moduleName, Timestamp sendTime,String recordId){
		AffairsBody affairsBody = new AffairsBody();
        affairsBody.setCompanyId(fromUserInfo.getCompanyId());
        Timestamp createTime = new Timestamp(new Date().getTime());
        affairsBody.setCreateTime(createTime);
        affairsBody.setCreateUserInfo(fromUserInfo);
        affairsBody.setIsDelete(CbbConst.NOT_DELETE);
        affairsBody.setFromUserInfo(fromUserInfo);
        
        // 设置提醒人员 以,分割
        affairsBody.setToIds(toids);

        // 模块名
        affairsBody.setSmsType(moduleName);

        // 提醒内容
        affairsBody.setContentInfo(affairContent);
        affairsBody.setSendTime(sendTime);

        // 设置提醒对应URL
        affairsBody.setRemindUrl(affairUrl);
        //affairsBody.setRecordId(recordId);
        SpringContextHolder.getBean(IAffairsBody.class).saveOrUpdate(affairsBody);
        return 0;
	}
	/**
	 * 发送短信
	 * @param fromUserInfo 发送人
	 * @param smsContent 发送内容
	 * @param toids 被发送人ids集合
	 * @throws IOException 
	 * @throws HttpException 
	 */
	public int sendSms(UserInfo fromUserInfo ,String smsContent, String toids) {
		
		String[] arry=toids.split(",");
		String phones="";
		for(int i=0;i<arry.length;i++)
		{
			UserInfo userInfo=SpringContextHolder.getBean(IUser.class).findOne(arry[i]==null?0:(Integer.valueOf(arry[i].toString())));
			if(userInfo!=null){
				phones+=userInfo.getPhone()+",";
			}
		}
		
		return SpringContextHolder.getBean(ISMS.class).sendSms(phones, smsContent);
		
	}
	/**
	 * 手机推送
	 * @param fromUserInfo 发送人
	 * @param pushSubject  推送标题
	 * @param pushContent 推送内容
	 * @param moduleName 模块名称
	 * @param toids  被推送人ids集合
	 * @param recordId 记录ID
	 * @param pushUrl 推送链接
	 * @throws IOException 
	 * @throws HttpException 
	 */
	public int  sendPush(UserInfo fromUserInfo ,String pushSubject,String pushContent, String moduleName,
			String toids,String recordId,String pushUrl,String pushType,String attmentIds){
		PushPlatService pushPlatService = (PushPlatService) SpringUtil.getBean("pushPlatService");
		Integer companyId = fromUserInfo.getCompanyId();
		String iosTags="";
		String androidTags="";
		if(StringUtils.isNotEmpty(toids)){
			String[] idArr = toids.split(",");
			String iosTag = "";
			String androidTag = "";
			for(String id:idArr){
				if(StringUtils.isNotEmpty(id)){
					androidTag = "android_user_"+id;
					iosTag = "ios_user_"+id;
					androidTags+=(checkTest(androidTag)+",");
					iosTags+=(checkTest(iosTag)+",");
				}
			}
		}
    	Map<String,Object> extra = new HashMap<String, Object>();
    	extra.put("fromUserId", fromUserInfo.getUserId()+"");
    	if(StringUtils.isNotEmpty(moduleName)){
    		if(moduleName.equals("通知公告")){
    			pushSubject = "新密检察院办公OA";
    		}
    		extra.put("moduleName", moduleName);
    	}
    	extra.put("mt", "notice");
    	if(StringUtils.isNotEmpty(recordId)){
    		extra.put("recordId", recordId);
    	}
    	if(StringUtils.isNotEmpty(pushUrl)){
    		extra.put("pushUrl", pushUrl);
    	}
    	if(StringUtils.isNotBlank(pushType)){
    		extra.put("type", pushType);
    	}
    	if(StringUtils.isNotEmpty(attmentIds)){
    		extra.put("attmentIds", attmentIds);
    	}
    	if(StringUtils.isEmpty(pushContent)){
    		pushContent = pushSubject;
    	}
    	Gson json = new Gson();
    	pushPlatService.pushJpushToPlat(companyId, androidTags, PLAT_TYPE.ANDROID, pushContent, pushSubject, json.toJson(extra), JPUSH_PUSH_TYPE.TAG, null);
    	
    	pushPlatService.pushJpushToPlat(companyId, iosTags, PLAT_TYPE.IOS, pushContent, pushSubject, json.toJson(extra), JPUSH_PUSH_TYPE.TAG, null,ANDROID_PUSH_TYPE.NOTIFY);
		
		//发送推送
    	return 0;
	}
    /**
     * 功能：判断是否是测试环境
     * @param tag
     * @return
     */
    private static String checkTest(String tag){
    	if (PushControl.isTest()) {
			tag="test_"+tag;
		}
    	return tag;
    }

}
