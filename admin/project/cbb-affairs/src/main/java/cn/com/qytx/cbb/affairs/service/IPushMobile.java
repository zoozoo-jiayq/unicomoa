package cn.com.qytx.cbb.affairs.service;



public interface IPushMobile {
	/**
	 * 向手机端发送推送接口
	 * @param userId 发送人id
	 * @param pushSubject 推送标题
	 * @param pushContent 推送内容
	 * @param moduleName 模块名
	 * @param toids 接收人ids集合
	 * @param recordId 记录id
	 * @param pushUrl 推送链接
	 * @param pushType 
	 */
	public int sendPush(String userId ,String pushSubject,String pushContent, String moduleName,
			String toids,String recordId,String pushUrl,String pushType,String attmentIds);
	
	public int sendPush(String userId ,String pushSubject,String pushContent, String moduleName,
			String toids,String recordId,String pushUrl,String pushType);
}
