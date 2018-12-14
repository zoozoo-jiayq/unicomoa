package cn.com.qytx.cbb.publicDom.util;

import java.io.IOException;

import org.apache.commons.httpclient.HttpException;

import cn.com.qytx.cbb.affairSetting.service.IAffairSetting;
import cn.com.qytx.cbb.affairs.domain.ReminderInfo;
import cn.com.qytx.cbb.affairs.service.IAffairsBody;
import cn.com.qytx.platform.base.application.SpringContextHolder;
import cn.com.qytx.platform.org.domain.UserInfo;

/**
 * 功能：发送事物提醒的工具类
 * 作者： jiayongqiang
 * 创建时间：2014年7月11日
 */
public class AffairUtils {

	/**
	 * 功能：调用事物提醒接口发送事物提醒消息
	 * 作者：jiayongqiang
	 * 参数：
	 * 输出：
	 */
	public static void insertAffair(UserInfo fromUser, String toUserIds,
			String menuType,String title,String instanceId,String nextAction)  {
		if(nextAction==null){
			nextAction = "";
		}
		IAffairSetting  affairSetting = SpringContextHolder.getBean(IAffairSetting.class);
		IAffairsBody affairBody = SpringContextHolder.getBean(IAffairsBody.class);
		String sendType = "";
		String moduleCode = null;
		if(menuType.equals("发文核稿")){
			moduleCode = "gwgl_fwhg";
		}else if(menuType.equals("套红盖章")){
			moduleCode = "gwgl_thgz";
		}else if(menuType.equals("发文分发")){
			moduleCode = "gwgl_fwff";
		}else if(menuType.equals("收文登记")){
			moduleCode = "gwgl_swdj";
		}else if(menuType.equals("领导批阅")){
			moduleCode = "gwgl_ldpy";
		}else if(menuType.equals("收文分发")){
		    moduleCode = "gwgl_swff";
		}else if(menuType.equals("发文拟稿")){
		    moduleCode="gwgl_fwng";
		}
		if(moduleCode == null){
			return;
		}
		sendType = affairSetting.findDefaultByCode(moduleCode);
		String content ="您有"+fromUser.getUserName()+"提交的公文待处理："+title;
		
		ReminderInfo reminderInfo = new ReminderInfo();
		reminderInfo.setSendType(sendType);
		reminderInfo.setAffairContent(content);
		reminderInfo.setAffairUrl("/dom/public!taskIsDelete.action?instanceId="+instanceId);
		reminderInfo.setToids(toUserIds);
		reminderInfo.setFromUserInfo(fromUser);
		reminderInfo.setModuleName("公文管理:"+nextAction);
		reminderInfo.setPushSubjcet("公文管理:"+nextAction);
		//reminderInfo.setPushContent(content);
		reminderInfo.setRecordId(instanceId);
		reminderInfo.setSmsContent("【OA办公系统提醒】"+content);
		try {
			affairBody.sendReminder(reminderInfo);
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
