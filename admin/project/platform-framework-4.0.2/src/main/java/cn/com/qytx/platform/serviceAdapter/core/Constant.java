package cn.com.qytx.platform.serviceAdapter.core;

/**
 * @ClassName:     Constant.java
 * @Description:   TODO(用一句话描述该文件做什么) 
 * 
 * @author         wangxj
 * @version        V1.0  
 * @Date           2015-6-1 下午02:28:56 
 */
public class Constant {

	/**
	 * RetInfo 类默认参数
	 * */
	public static String RETINFO_SUCCESS_RESULT = "0";
	public static String RETINFO_SUCCESS_RETMSG = "成功";
	public static String RETINFO_FAILURE_RESULT = "-1";
	public static String RETINFO_FAILURE_RETMSG = "系统异常，请稍后重试。";
	
	/**
	 * 查询公司信息时查询类型
	 * 
	 * */
	public static String COMPANYCODE = "COMPANYCODE";//公司管理员登陆
	public static String COMPANY = "COMPANY";//公司标示
	public static String PERSONAL = "PERSONAL";//个人标示
	
	
	public static String USERNOTFOUND = "用户名或者密码错误，请检查";
	public static String COMPANYNOTFOUND = "用户名或者密码错误或者检查您所属公司状态已失效";
	
	/**
	 * 登陆参数校验提示
	 * 
	 * */
	public static String USER_LOGIN_USERNAME_ERROR = "用户名不能为空";
	public static String USER_LOGIN_PASSWORD_ERROR = "请输入密码";
	public static String USER_LOGIN_GRAPHICS_ERROR = "请输入图形验证码";
	public static String USER_LOGIN_USERTYPE_ERROR = "请选择登陆类型";
	
	
	/**
	 * 注册错误信息提示
	 * 
	 * */
	public static String USER_REG_USERNAME_USED = "该帐号已被注册";
	public static String USER_REG_PHONE_ISVALID = "手机号码为空或者不可用";
	public static String USER_REG_SMSCODE_ISVALID = "短信验证码错误";
	
	/**
	 * 默认角色
	 * 
	 * */
	public static int USER_REG_USERROLE_MANAGER = 0;
	
	
	/**
	 * 静态数据字典表
	 * 
	 * **/
	public static String DICT_STATIC_PROVICE ="PROVICE";
	public static String DICT_STATIC_CITY ="CITY";
	public static String DICT_STATIC_MODULETYPE = "MODULETYPE";
	
	/**
	 * is_delete 取值
	 * 
	 * **/
	public static int IS_DELETE_SUCCESS = 0;
	public static int IS_DELETE_ACTIVATED = 0;//激活
	public static int IS_DELETE_SELFEXIT = 2;//自主退出
	public static int IS_DELETE_UNACTIVATED = 1;//待激活
	
	public static int IS_DELETE_FAILURE = -1;
	/**
	 * order_index 取值
	 * 
	 * **/
	public static int ORDER_INDEX_DEFAULT = 0;
	/**
	 * ROLE_TYPE 取值
	 * 
	 * **/
	public static int ROLE_TYPE_DEFAULT = 0;
	
	/**
	 * ROLEINFO TYPE 取值
	 * 
	 * **/
	public static int ROLE_DEFAULT_STATE = 0;
	
	/**
	 * ROLEINFO TYPE 取值
	 * 
	 * **/
	public static int ROLE_INFO_TYPE_DEFAULT = 0;
	/**
	 * pcompanyinfo_openedservices OPEN_FLAG 取值
	 * 
	 * **/
	public static int OPENEDSERVICES_OPEN_FLAG = 1;
	/***
	 * 
	 * */
	public static String ROLE_INFO_ROLE_CODE_MANAGER="manager";
	public static String ROLE_INFO_ROLE_CODE_STAFF="staff";
	
	/**
	 * 注册短信发送语
	 * */
	public static String REG_SMS_CONTENT="欢迎您注册乐工作平台，您的注册码为【%s】,30分钟有效，请妥善保管。";
	public static String ACCOUNT_MANAGER_SMS_CONTENT="乐工作平台短信换绑短信验证码为【%s】,30分钟有效，请妥善保管。";
	public static String ACCOUNT_USER_REG_SMS_CONTENT="乐工作平台短信换绑短信验证码为【%s】,30分钟有效，请妥善保管。";
	public static String REG_SMS_HTTPURL="http://10.100.6.140:10000/txzl/mobile/sendSms.action";
	public static String REG_SMS_SUSSCESS_RESULT="0";
	/**
	 * 短信验证码有效时长 单位分钟
	 */
	public static int SMS_CODE_VALID_TIME=30;
	/**
	 * 同一个号码同一个模块能够再次获取的时间间隔
	 */
	public static int PHONE_MODULE_REPLACE_TIME=1;
	
	
	public static String DEFAULT_GROUP_NAME="乐工作管理员";
	
}
