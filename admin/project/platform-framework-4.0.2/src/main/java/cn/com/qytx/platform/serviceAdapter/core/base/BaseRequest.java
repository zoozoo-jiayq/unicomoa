package cn.com.qytx.platform.serviceAdapter.core.base;

/**
 * @ClassName:     BaseRequest.java
 * @Description:   公共基本调用类，入参参数继承此类
 * 
 * @author         wangxj
 * @version        V1.0  
 * @Date           2015-6-1 上午09:06:46 
 */
public class BaseRequest {
	public long user_id;//用户标示
	public String user_name ;//用户名
	public long company_id;//公司标示
	public String busi_type;//业务编码
	public String module_code;//模块编码
	public String user_type;//用户类型
	
	
	/** 
	 * @Title:        getRequestMsg 
	 * @Description:  拼写入参参数 ,可为json格式等等
	 * @param:        @return    
	 * @return:       String    
	 * @throws 
	 * @author        wangxj
	 * @Date          2015-6-1 上午09:55:50 
	 */
	public String getRequestMsg(){
		String retVal = "";
		return retVal;
	};
	
	public long getUser_id() {
		return user_id;
	}
	public void setUser_id(long userId) {
		user_id = userId;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String userName) {
		user_name = userName;
	}
	public long getCompany_id() {
		return company_id;
	}
	public void setCompany_id(long companyId) {
		company_id = companyId;
	}
	public String getBusi_type() {
		return busi_type;
	}
	public void setBusi_type(String busiType) {
		busi_type = busiType;
	}
	public String getModule_code() {
		return module_code;
	}
	public void setModule_code(String moduleCode) {
		module_code = moduleCode;
	}
}
