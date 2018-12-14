package cn.com.qytx.cbb.jpush.service;

import java.util.Map;

/**
 * 给客户端推送接口
 * User:黄普友
 * Date: 13-7-6
 * Time: 上午10:23
 */
public interface IPush {
    /**
     * 发送带Tag的通知
     * @param sendNo
     * @param tag
     * @param msgTitle
     * @param msgContent
     */
     public int sendNotificationWithTag(int sendNo,String tag,String msgTitle,String msgContent,String masterSecret,String appKey);
    /**
     * 发送带Tag的通知
     * @param tag
     * @param msgTitle
     * @param msgContent
     */
    public int sendNotificationWithTag(String tag,String msgTitle,String msgContent,String masterSecret,String appKey);

    /**
     * 发送带Tag的消息
     * @param sendNo
     * @param tag
     * @param msgTitle
     * @param msgContent
     */
    public int sendCustomMessageWithTag(int sendNo,String tag,String msgTitle,String msgContent,String masterSecret,String appKey);
    /**
     * 发送带Tag的消息
     * @param tag
     * @param msgTitle
     * @param msgContent
     */
    public int sendCustomMessageWithTag(String tag,String msgTitle,String msgContent,String masterSecret,String appKey);
    /**
     * 发送带扩展属性的消息
     * @param tag
     * @param msgTitle
     * @param msgContent
     * @param msgContentType
     * @param map
     * @return
     */
    public int sendCustomMessageWithTag(String tag,String msgTitle,String msgContent ,String msgContentType ,Map map,String masterSecret,String appKey);
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
    public int sendCustomMessageWithTag(int sendNo,String tag,String msgTitle,String msgContent ,String msgContentType ,Map map,String masterSecret,String appKey);
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
    public int sendNotificationWithTag(int sendNo,String tag,String msgTitle,String msgContent ,int msgContentType ,Map map,String masterSecret,String appKey);
    /**
     * 发送带扩展属性的通知
     * @param tag
     * @param msgTitle
     * @param msgContent
     * @param msgContentType
     * @param map
     * @return
     */
    public int sendNotificationWithTag(String tag,String msgTitle,String msgContent ,int msgContentType ,Map map,String masterSecret,String appKey);

}
