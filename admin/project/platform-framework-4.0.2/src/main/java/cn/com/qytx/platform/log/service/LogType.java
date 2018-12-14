package cn.com.qytx.platform.log.service;

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
     * 登陆日志之用密码错误:2
     */
    public static final int LOG_LOGIN_PASSWARD = 2;
    /**
     * 登陆日志之用户名错误:3
     */
    public static final int LOG_LOGIN_USERNAME = 3;
    /**
     * 登陆日志之清除密码:4
    public static final int LOG_LOGIN_PASSWARD_CLEAN = 4;
     */
    /**
     *登陆日志之非法IP登陆:
    public static final int LOG_LOGIN_ILLICIT_IP = 5;
     */
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
    public static final int LOG_USER_DIMISSION = 10;
     */
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
     * 删除公告：15
     */
    public static final int LOG_NOTIFY_ADD =15;
    
    /**
     * 发送邮件：16
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
    
    /**
     * 增加单位信息
     */
    public static final int LOG_COMPANY_ADD = 19;
   
    /**
     * 管理员密码修改
     */
    public static final int LOG_ADMINPASS_UPDATE = 21;
    /**
     * 手机端登录
     */
    public static final int LOG_LOGIN_MOBILE=20;
    /**
     * 管理员添加
     */
    public static final int LOG_ADMIN_ADD = 22;
    /**
     * 管理员更新
     */
    public static final int LOG_ADMIN_UPDATE = 23;
    /**
     * 管理员删除
     */
    public static final int LOG_ADMIN_DELETE = 24;
    /**
     * 重置单位密码
     */
    public static final int LOG_COMPANYPASS_RESET = 25;
    
    
 }
