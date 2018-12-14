package cn.com.qytx.platform.serviceAdapter.core.base;
/**
 * @ClassName:     RetInfo.java
 * @Description:   TODO(用一句话描述该文件做什么) 
 * 
 * @author         wangxj
 * @version        V1.0  
 * @Date           2015-6-1 上午10:13:57 
 */
public class RetInfo {
	public String retResult;//返回结果 0 成功，1失败
	public String retMsg;//返回信息，系统处理过信息，可展示给用户看的信息，用户语言。
	public Object obj;//返回类，页面和action层交互数据类型，action层和service层交互类型。
	public String getRetResult() {
		return retResult;
	}
	public void setRetResult(String retResult) {
		this.retResult = retResult;
	}
	public String getRetMsg() {
		return retMsg;
	}
	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
}
