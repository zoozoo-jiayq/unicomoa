package cn.com.qytx.cbb.redpoint.utils;

import java.util.HashMap;
import java.util.Map;

import cn.com.qytx.cbb.push.service.PushPlatService;
import cn.com.qytx.cbb.push.service.PushPlatService.IOS_PUSH_TYPE;
import cn.com.qytx.cbb.push.service.PushPlatService.JPUSH_PUSH_TYPE;
import cn.com.qytx.cbb.push.service.PushPlatService.PLAT_TYPE;
import cn.com.qytx.platform.utils.spring.SpringUtil;
import cn.com.qytx.rangecontrol.PushControl;
import cn.jpush.api.JPushClient;

import com.google.gson.Gson;

/**
 * 功能: 常用工具类 
 * 版本: 1.0
 * 开发人员: zyf
 * 创建日期: 2015年3月19日
 * 修改日期: 2015年3月19日
 * 修改列表:
 */
public class RedPointUtil {
	/**
     * 保持 sendNo 的唯一性是有必要的
     * @return sendNo
     */
    public static int getRandomSendNo() {
        return (int) (MIN + Math.random() * (MAX - MIN));
    }
    public static final int MAX = Integer.MAX_VALUE;
    public static final int MIN = MAX/2;
    
    /**
     * 功能：推送指定消息
     * @param jpush
     * @param tag
     * @param extra
     */
    public static void pushInfo(JPushClient jpush,String tag,Map<String,Object> extra,int companyId){
    	int sendNo= getRandomSendNo();
    	String iosTag = "ios_user_"+tag;
    	String androidTag = "android_user_"+tag;
    	
    	Map<String, Object> iosExtra = new HashMap<String, Object>();
    	iosExtra.put("content-available", "1");

    	extra.put("ios", iosExtra);
    	
    	PushPlatService pushPlatService = (PushPlatService) SpringUtil.getBean("pushPlatService");
    	
    	Gson json = new Gson();
    	pushPlatService.pushJpushToPlat(companyId, checkTest(androidTag),PLAT_TYPE.ANDROID, null,"", json.toJson(extra), JPUSH_PUSH_TYPE.TAG,null);
//    	pushPlatService.pushJpushToPlat(companyId, checkTest(iosTag), PLAT_TYPE.IOS, null,"", json.toJson(extra), JPUSH_PUSH_TYPE.TAG,null);
    	pushPlatService.pushJpushToPlat(companyId, checkTest(iosTag), PLAT_TYPE.IOS, null,"", json.toJson(extra), JPUSH_PUSH_TYPE.TAG,IOS_PUSH_TYPE.MESSAGE,null);
//    	MessageResult mr1= jpush.sendNotificationWithTag(sendNo, checkTest(iosTag), "最新消息", "", 1, extra);
//    	MessageResult mr2= jpush.sendCustomMessageWithTag(sendNo, checkTest(androidTag), "最新消息", "", "0", extra);
    }
    
    /**
     * 功能：推送指定消息
     * @param jpush
     * @param tag
     * @param extra
     */
    public static void pushInfoWithContent(JPushClient jpush,String tag,Map<String,Object> extra,int companyId,String title,String content){
    	int sendNo= getRandomSendNo();
    	String iosTag = "ios_user_"+tag;
    	String androidTag = "android_user_"+tag;
    	
    	Map<String, Object> iosExtra = new HashMap<String, Object>();
    	iosExtra.put("sound", "default");
    	iosExtra.put("content-available", "1");
    	iosExtra.put("badge", "");
    	extra.put("ios", iosExtra);
    	
    	PushPlatService pushPlatService = (PushPlatService) SpringUtil.getBean("pushPlatService");
    	
    	Gson json = new Gson();
    	pushPlatService.pushJpushToPlat(companyId, checkTest(androidTag),PLAT_TYPE.ANDROID, content,title, json.toJson(extra), JPUSH_PUSH_TYPE.TAG,null);
    	pushPlatService.pushJpushToPlat(companyId, checkTest(iosTag), PLAT_TYPE.IOS, content,content, json.toJson(extra), JPUSH_PUSH_TYPE.TAG,IOS_PUSH_TYPE.NOTIFY,null);
    	
//    	MessageResult mr1= jpush.sendNotificationWithTag(sendNo, checkTest(iosTag), "最新消息", "", 1, extra);
//    	MessageResult mr2= jpush.sendCustomMessageWithTag(sendNo, checkTest(androidTag), "最新消息", "", "0", extra);
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
