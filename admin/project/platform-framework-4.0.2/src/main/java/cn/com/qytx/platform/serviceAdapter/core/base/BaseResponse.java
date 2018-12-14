package cn.com.qytx.platform.serviceAdapter.core.base;
/**
 * @ClassName:     BaseResponse.java
 * @Description:   TODO(用一句话描述该文件做什么) 
 * 
 * @author         wangxj
 * @version        V1.0  
 * @Date           2015-6-1 上午09:50:57 
 */
public class BaseResponse {
	public String retCode;//返回编码，调用第三方或者service层的返回代码
	public String retMsg;//返回信息，第三方或者service层真正的返回信息
	
	/** 
	 * @Title:        parseResponseMsg 
	 * @Description:  TODO(这里用一句话描述这个方法的作用) 
	 * @param:        @param responseMsg 第三方返回字符串信息，可在此方法中进行解析
	 * @param:        @return    
	 * @return:       BaseResponse    
	 * @throws 
	 * @author        wangxj
	 * @Date          2015-6-1 上午10:11:34 
	 */
	public BaseResponse parseResponseMsg(String responseMsg){
		return null;
	};

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getRetMsg() {
		return retMsg;
	}

	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}
}
