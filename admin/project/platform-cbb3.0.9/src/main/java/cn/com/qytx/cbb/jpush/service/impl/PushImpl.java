package cn.com.qytx.cbb.jpush.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.jpush.service.IPush;
import cn.com.qytx.rangecontrol.PushControl;
import cn.jpush.api.JPushClient;
import cn.jpush.api.MessageResult;

/**
 * User:黄普友
 * Date: 13-7-6
 * Time: 上午10:38
 */
@Service("pushService")
@Transactional
public class PushImpl implements IPush {
/*
    private final String masterSecret="";
    private final String appKey="";*/
    
    public int sendNotificationWithTag(int sendNo, String tag, String msgTitle, String msgContent,String masterSecret,String appKey) {
        JPushClient jpush = new JPushClient(masterSecret, appKey);
        MessageResult mr= jpush.sendNotificationWithTag(sendNo, tag, msgTitle, msgContent);
        return mr.getErrcode();
    }
    
    public int sendNotificationWithTag(String tag, String msgTitle, String msgContent,String masterSecret,String appKey) {
        int sendNo= getRandomSendNo();
        return  sendNotificationWithTag(sendNo,tag,msgTitle,msgContent,masterSecret,appKey);

    }
    /**
     * 发送带Tag的消息
     * @param sendNo
     * @param tag
     * @param msgTitle
     * @param msgContent
     */
    public int sendCustomMessageWithTag(int sendNo,String tag,String msgTitle,String msgContent,String masterSecret,String appKey)
    {
        JPushClient jpush = new JPushClient(masterSecret, appKey);
    	String androidTag = "android_meeting_"+tag;
    	String iosTag = "ios_meeting_"+tag;
    	MessageResult mr= jpush.sendCustomMessageWithTag(sendNo, androidTag, msgTitle, msgContent);
    	@SuppressWarnings("unused")
		MessageResult mr1= jpush.sendNotificationWithTag(sendNo, iosTag, msgTitle, msgContent);
        return mr.getErrcode();
    }
    /**
     * 发送带Tag的消息
     * @param tag
     * @param msgTitle
     * @param msgContent
     */
    public int sendCustomMessageWithTag(String tag,String msgTitle,String msgContent,String masterSecret,String appKey)
    {
        int sendNo= getRandomSendNo();
        return  sendCustomMessageWithTag(sendNo,tag,msgTitle,msgContent, masterSecret, appKey);
    }
    /**
     * 发送带扩展属性的消息
     * @param sendNo
     * @param tag
     * @param msgTitle
     * @param msgContent
     * @param msgContentType
     * @param map
     * @return
     */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int sendCustomMessageWithTag(int sendNo,String tag,String msgTitle,String msgContent ,String msgContentType ,Map map,String masterSecret,String appKey){
    	JPushClient jpush = new JPushClient(masterSecret, appKey);
    	MessageResult mr= jpush.sendCustomMessageWithTag(sendNo, tag, msgTitle, msgContent, msgContentType, map);
        return mr.getErrcode();
    }
    /**
     * 发送带扩展属性的消息
     * @param tag
     * @param msgTitle
     * @param msgContent
     * @param msgContentType
     * @param map
     * @return
     */
    @SuppressWarnings("rawtypes")
	public int sendCustomMessageWithTag(String tag,String msgTitle,String msgContent ,String msgContentType ,Map map,String masterSecret,String appKey){
    	int sendNo= getRandomSendNo();
    	if (PushControl.isTest()) {
    		tag="test_"+tag;
		}
        return  sendCustomMessageWithTag(sendNo,tag,msgTitle,msgContent,msgContentType,map, masterSecret, appKey);
    }
    /**
     * 发送带扩展属性的通知
     * @param sendNo
     * @param tag
     * @param msgTitle
     * @param msgContent
     * @param msgContentType
     * @param map
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public int sendNotificationWithTag(int sendNo,String tag,String msgTitle,String msgContent ,int msgContentType ,Map map,String masterSecret,String appKey){
    	JPushClient jpush = new JPushClient(masterSecret, appKey);
    	if (PushControl.isTest()) {
    		tag="test_"+tag;
		}
        MessageResult mr= jpush.sendNotificationWithTag(sendNo, tag, msgTitle, msgContent, msgContentType, map);
        return mr.getErrcode();
    }
    /**
     * 发送带扩展属性的通知
     * @param tag
     * @param msgTitle
     * @param msgContent
     * @param msgContentType
     * @param map
     * @return
     */
    @SuppressWarnings("rawtypes")
	public int sendNotificationWithTag(String tag,String msgTitle,String msgContent ,int msgContentType ,Map map,String masterSecret,String appKey){
    	int sendNo= getRandomSendNo();
        return  sendNotificationWithTag(sendNo,tag,msgTitle,msgContent,msgContentType,map, masterSecret, appKey);
    }
    
    /**
     * 保持 sendNo 的唯一性是有必要的
     * @return sendNo
     */
    private int getRandomSendNo() {
        return (int) (MIN + Math.random() * (MAX - MIN));
    }
    public static final int MAX = Integer.MAX_VALUE;
    public static final int MIN = MAX/2;

}
