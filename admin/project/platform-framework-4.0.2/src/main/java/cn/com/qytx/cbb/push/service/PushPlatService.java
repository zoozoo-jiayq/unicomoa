package cn.com.qytx.cbb.push.service;

/**
 * 功能: 与推送平台对接接口 
 * 版本: 1.0
 * 开发人员: zyf
 * 创建日期: 2015年4月30日
 * 修改日期: 2015年4月30日
 * 修改列表:
 */
public interface PushPlatService {
	
	/**
	 * 功能：向平台发送极光推送请求
	 */
	public void pushJpushToPlat(int companyId,String destination,PLAT_TYPE platType,String content,String title,String extra,JPUSH_PUSH_TYPE jpushPushType,IOS_PUSH_TYPE iosPushType,ANDROID_PUSH_TYPE androidPushType);
	
	/**
	 * 功能：向平台发送极光推送请求
	 */
	@Deprecated
	public void pushJpushToPlat(int companyId,String destination,PLAT_TYPE platType,String content,String title,String extra,JPUSH_PUSH_TYPE jpushPushType,IOS_PUSH_TYPE iosPushType);
	
	/**
	 * 功能：向平台发送极光推送请求
	 */
	@Deprecated
	public void pushJpushToPlat(int companyId,String destination,String platType,String content,String title,String extra,String jpushPushType);
	
	/**
	 * 功能: 推送目标平台类型 
	 * 版本: 1.0
	 * 开发人员: zyf
	 * 创建日期: 2015年5月15日
	 * 修改日期: 2015年5月15日
	 * 修改列表:
	 */
	enum PLAT_TYPE{
		IOS("ios"),ANDROID("android"),ALL("all");
		
		private String value;
		PLAT_TYPE(String value){
			this.value = value;
		}
		
		public String getValue(){
			return this.value;
		}
	}
	
	/**
	 * 功能: jpush推送使用类型 tag/alias 
	 * 版本: 1.0
	 * 开发人员: zyf
	 * 创建日期: 2015年5月15日
	 * 修改日期: 2015年5月15日
	 * 修改列表:
	 */
	enum JPUSH_PUSH_TYPE{
		TAG("tag"),ALIAS("alias");
		private String value;
		JPUSH_PUSH_TYPE(String value){
			this.value = value;
		}
		
		public String getValue(){
			return this.value;
		}
	}
	
	/**
	 * 功能: ios 推送方式 通知/消息
	 * 版本: 1.0
	 * 开发人员: zyf
	 * 创建日期: 2015年5月15日
	 * 修改日期: 2015年5月15日
	 * 修改列表:
	 */
	enum IOS_PUSH_TYPE{
		NOTIFY("notify"),MESSAGE("message");
		private String value;
		IOS_PUSH_TYPE(String value){
			this.value = value;
		}
		
		public String getValue(){
			return this.value;
		}
	}
	
	/**
	 * 功能: android 推送方式 通知/消息
	 * 版本: 1.0
	 * 开发人员: zyf
	 * 创建日期: 2015年5月15日
	 * 修改日期: 2015年5月15日
	 * 修改列表:
	 */
	enum ANDROID_PUSH_TYPE{
		NOTIFY("notify"),MESSAGE("message");
		private String value;
		ANDROID_PUSH_TYPE(String value){
			this.value = value;
		}
		
		public String getValue(){
			return this.value;
		}
	}
}
