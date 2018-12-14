/**
 *
 */
package cn.com.qytx.oa.email.service;

/**
 * 功能:电子邮件常量类
 * 版本:1.0
 * 开发人员: 汤波涛
 * 创建日期: 2013-03-14
 * 修改日期: 2013-03-14
 * 修改人员: 汤波涛
 * 修改列表:初始加入的常量
 */
public class EmailConst {

    /**
     * 邮件收件箱类型：默认收件箱
     */
    public static final Integer EMAIL_BOX_TYPE_INBOX = 1;

    /**
     * 邮件收件箱类型：默认发件箱
     */
    public static final Integer EMAIL_BOX_TYPE_OUTBOX = 2;

    /**
     * 邮件收件箱类型：默认草稿箱
     */
    public static final Integer EMAIL_BOX_TYPE_DRAFT = 3;

    /**
     * 邮件收件箱类型：默认废纸篓
     */
    public static final Integer EMAIL_BOX_TYPE_WASTEBASKET = 4;

    /**
     * 邮件收件箱类型：用户自定义
     */
    public static final Integer EMAIL_BOX_TYPE_DIY = 5;

    /**
     * 邮件收件人类型:收件人
     */
    public static final Integer EMAIL_TO_TYPE_TO = 1;

    /**
     * 邮件收件人类型:抄送人
     */
    public static final Integer EMAIL_TO_TYPE_COPY = 2;

    /**
     * 邮件收件人类型:密送人
     */
    public static final Integer EMAIL_TO_TYPE_SECRET = 3;

    /**
     * 邮件已发送状态
     */
    public static final Integer EMAIL_SENDED = 1;

    /**
     * 邮件未发送状态
     */
    public static final Integer EMAIL_NOT_SEND = 0;

    /**
     * 邮件已读状态
     */
    public static final Integer EMAIL_READED = 1;

    /**
     * 邮件未读状态
     */
    public static final Integer EMAIL_NOT_READ = 0;

    /**
     * 邮件未接收状态
     */
    public static final Integer EMAIL_NOT_RECEIVE = 0;

    /**
     * 邮件已接收状态
     */
    public static final Integer EMAIL_RECEIVED = 1;

    /**
     * 邮件已取消发送状态
     */
    public static final Integer EMAIL_CANCEL_SEND = 2;

    /**
     * 邮件箱默认列表大小
     */
    public static final Integer EMAIL_PAGE_MAX = 15;

    /**
     * 邮件接收人分隔符
     */
    public static final String EMAIL_TO_SEPARATOR = ",";

    /**
     * 最近联系人数量
     */
    public static final Integer EMAIL_RECENT_CONTACTS_COUNT = 5;

    /**
     * 重要级别-一般=0
     */
    public static final Integer EMAIL_IMPORTANT_LEVEL_NORMAL = 0;

    /**
     * 重要级别-重要=1
     */
    public static final Integer EMAIL_IMPORTANT_LEVEL_IMP = 1;

    /**
     * 重要级别-非常重要=2
     */
    public static final Integer EMAIL_IMPORTANT_LEVEL_VERY_IMP = 2;

    /**
     * 发送事物提醒
     */
    public static final Integer EMAIL_SEND_SMS_REMIND = 1;

    /**
     * 不发送事物提醒
     */
    public static final Integer EMAIL_NOT_SEND_SMS_REMIND = 0;

    /**
     * 需要收条
     */
    public static final  Integer EMAIL_NEED_RECEIPT=1;

    /**
     * 不需要收条
     */
    public static final  Integer EMAIL_NOT_NEED_RECEIPT=0;

}
