package cn.com.qytx.cbb.push.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.stereotype.Service;

import cn.com.qytx.cbb.push.service.PushPlatService;
import cn.com.qytx.cbb.redpoint.utils.HttpPostUtil;

/**
 * 功能: 与推送平台对接接口 
 * 版本: 1.0
 * 开发人员: zyf
 * 创建日期: 2015年4月30日
 * 修改日期: 2015年4月30日
 * 修改列表:
 */
@Service("pushPlatService")
public class PushPlatImpl implements PushPlatService {

	/**
	 * 功能：推送到推送平台
	 * @param companyId 单位id
	 * @param destination 目的地
	 * @param platType 推送目标 android/ios 不传默认全推送
	 * @param content 内容
	 * @param extra 附件信息 json字符串
	 * @param jpushPushType tag/alais 不传默认为别名
	 * @param androidPushType notify/message 不传默认为 message
	 */
	@Override
	public void pushJpushToPlat(int companyId,String destination,PLAT_TYPE platType,String content,String title,String extra,JPUSH_PUSH_TYPE jpushPushType,IOS_PUSH_TYPE iosPushType,ANDROID_PUSH_TYPE androidPushType) {
		try {
			Properties prop = new Properties();
			prop.load(this.getClass().getResourceAsStream("/application.properties"));
			
			String pushUrl = prop.getProperty("pushUrl");
			
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("companyId", companyId);
			param.put("destination", destination);
			param.put("platType", platType.getValue());
			param.put("jpushPushType", jpushPushType.getValue());
			if(iosPushType!=null){
				param.put("iosPushType", iosPushType.getValue());
			}
			if(androidPushType!=null){
				param.put("androidPushType", androidPushType.getValue());
			}
			//封装消息体
			param.put("content", content);
			param.put("title", title);
			param.put("extra", extra);
			param.put("isOld", "true");
			HttpPostUtil.doPost(pushUrl, param);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 功能：推送到推送平台
	 * @param companyId 单位id
	 * @param destination 目的地
	 * @param platType 推送目标 android/ios 不传默认全推送
	 * @param content 内容
	 * @param extra 附件信息 json字符串
	 * @param jpushPushType tag/alais 不传默认为别名
	 */
	@Deprecated
	@Override
	public void pushJpushToPlat(int companyId,String destination,PLAT_TYPE platType,String content,String title,String extra,JPUSH_PUSH_TYPE jpushPushType,IOS_PUSH_TYPE iosPushType) {
		try {
			Properties prop = new Properties();
			prop.load(this.getClass().getResourceAsStream("/application.properties"));
			
			String pushUrl = prop.getProperty("pushUrl");
			
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("companyId", companyId);
			param.put("destination", destination);
			param.put("platType", platType.getValue());
			param.put("jpushPushType", jpushPushType.getValue());
			if(iosPushType!=null){
				param.put("iosPushType", iosPushType.getValue());
			}
			//封装消息体
			param.put("content", content);
			param.put("title", title);
			param.put("extra", extra);
			param.put("isOld", "true");
			HttpPostUtil.doPost(pushUrl, param);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	@Deprecated
	public void pushJpushToPlat(int companyId, String destination,
			String platType, String content, String title, String extra,
			String jpushPushType) {
		// TODO Auto-generated method stub
		try {
			Properties prop = new Properties();
			prop.load(this.getClass().getResourceAsStream("/application.properties"));
			
			String pushUrl = prop.getProperty("pushUrl");
			
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("companyId", companyId);
			param.put("destination", destination);
			param.put("platType", platType);
			param.put("jpushPushType", jpushPushType);
			//封装消息体
			param.put("content", content);
			param.put("title", title);
			param.put("extra", extra);
			param.put("isOld", "true");
			HttpPostUtil.doPost(pushUrl, param);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

}
