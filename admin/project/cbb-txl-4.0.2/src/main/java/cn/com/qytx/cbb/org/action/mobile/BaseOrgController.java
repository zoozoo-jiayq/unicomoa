package cn.com.qytx.cbb.org.action.mobile;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import cn.com.qytx.cbb.push.service.PushPlatService;
import cn.com.qytx.cbb.push.service.PushPlatService.JPUSH_PUSH_TYPE;
import cn.com.qytx.cbb.push.service.PushPlatService.PLAT_TYPE;
import cn.com.qytx.platform.base.action.BaseControllerSupport;

import com.google.gson.Gson;

public class BaseOrgController extends BaseControllerSupport {
	@Autowired
	PushPlatService pushPlatService;
	/**
	 * 
	 * 功能：发送推送
	 * 
	 * @param companyId
	 */
	public void sendPush(final Integer companyId) {
		String msgTitle = "";
		String msgContent = "";
		String androidTag = "company_android_" + companyId;
		String iosTag = "company_ios_" + companyId;
		Map<String, String> hm = new HashMap<String, String>();
		hm.put("mt", "upbasicdata");

		String extra = new Gson().toJson(hm);

		// 推送至android
		pushPlatService.pushJpushToPlat(companyId, androidTag,
				PLAT_TYPE.ANDROID, msgContent, msgTitle, extra,
				JPUSH_PUSH_TYPE.TAG, null);
		// 推送至ios
		pushPlatService.pushJpushToPlat(companyId, iosTag, PLAT_TYPE.IOS,
				msgContent, msgTitle, extra, JPUSH_PUSH_TYPE.TAG, null);
	}
}
