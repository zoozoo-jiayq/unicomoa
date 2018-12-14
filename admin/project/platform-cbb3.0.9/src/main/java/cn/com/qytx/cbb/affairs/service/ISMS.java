package cn.com.qytx.cbb.affairs.service;


public interface ISMS {

	/**
	 * 给多个手机号发送同一条短信
	 * @param phones 以，隔开  
	 * @param content
	 * @return
	 */
	public int sendSms(String phones,String content);
}
