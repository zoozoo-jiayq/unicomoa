package cn.com.qytx.oa.email.domain;

/**
 * 登陆日志常量类
 * @author FDX
 */
public class LogType{
    /**
     * 全部日志:0
     */
    public static final int LOG_ALL = 0;
    /**
     * 登陆成功:1
     */
    public static final int LOG_LOGIN_SUCCESS = 1;
    /**
     * 登陆日志之密码错误:2
     */
    public static final int LOG_LOGIN_PASSWARD = 2;
    /**
     * 登陆日志之用户名错误:3
     */
    public static final int LOG_LOGIN_USERNAME = 3;
    /**
     * 登陆日志之清除密码:4
     */
    public static final int LOG_LOGIN_PASSWARD_CLEAN = 4;
    /**
     *登陆日志之非法IP登陆:
     */
    public static final int LOG_LOGIN_ILLICIT_IP = 5;
    /**
     * 登陆日志之退出系统:6
     */
    public static final int LOG_LOGIN_OUT = 6;
    
    /**
     * 用户添加：7
     */
    public static final int LOG_USER_ADD = 7;
    /**
     * 用户修改：8
     */
    public static final int LOG_USER_UPDATE = 8;
    /**
     * 用户删除：9
     */
    public static final int LOG_USER_DELETE = 9;
    /**
     * 用户离职：10
     */
    public static final int LOG_USER_DIMISSION = 10;
    /**
     * 用户修改登陆密码：11
     */
    public static final int LOG_USER_UPDATE_LOGPASS = 11;
 
    /**
     * 部门添加：12
     */
    public static final int LOG_GROUP_ADD = 12;
    /**
     * 部门修改：13
     */
    public static final int LOG_GROUP_UPDATE = 13;
    /**
     * 部门删除：14
     */
    public static final int LOG_GROUP_DELETE =14;
    
    /**
     * 公告通知管理：15
     */
    public static final int LOG_NOTIFY_ADD =15;
    
    /**
     * 公共文件柜：16
     */
    public static final int LOG_FILE_SORT =16;
    
    /**
     * 邮件删除：17
     */
    public static final int LOG_EMAIL_DELETE =17;
    
    /**
     * 按模块设置管理范围：18
     */
    public static final int LOG_RANGE_SETUP =18;
 }
