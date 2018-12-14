package cn.com.qytx.cbb.affairs.service;

public class MessageConst {
	/**
     * 消息提醒
     * 0已发送数据库 1已发送推送 2已阅 3已删除
     */
    public static final int SENDED = 0;
    public static final int RECEIVED = 1;
    public static final int READED = 2;
    public static final int DELETED = 3;
    
    /**
     * 事务提醒人员类型 1发送人 2收信人
     */
    public static final int USERTYPE_SEND = 1;
    public static final int USERTYPE_RECEIVE = 2;
    
    /**
     * 通讯薄事务提醒类型
     */
    public static final String ADDRESS_SMSTYPE = "通讯薄";
    /**
     * 公告通知提醒类型
     */
    public static final String NOTIFY_SMSTYPE = "公告通知";
    
    /**
     * 微讯类型 1微讯网页消息
     */
    public static final String MSGTYPE_FROM_WEBPAGE = "网页消息";
    public static final int MSGTYPE_FROM_WEBPAGE_VALUE = 1;
    public static final String MSGTYPE_FROM_ANDROID = "移动版(Android)";
    public static final int MSGTYPE_FROM_ANDROID_VALUE = 2;
    
    
    /**
     * 提醒类型 2表示已提醒
     */
    public static final int REMIND_YES = 2;
    
    /**
     * 已提醒导出显示字符串
     */
    public static final String REMIND_YES_STR = "是";
    
    /**
     * 未提醒道初相识字符串
     */
    public static final String REMIND_NO_STR = "否";
}
