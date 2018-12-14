package cn.com.qytx.cbb.jbpmApp.action.mobile.jsonModel;

import cn.com.qytx.cbb.jbpmApp.action.mobile.MyBaseAction.RESPONSE_CODE;

public  class JsonImplServerException extends IJson<String>{
	
	
	public JsonImplServerException() {
		super();
		// TODO Auto-generated constructor stub
		this.setDatas("服务器异常");
	}

	@Override
	public RESPONSE_CODE getResponseCode() {
		// TODO Auto-generated method stub
		return RESPONSE_CODE.RESPONSE_SERVER_ERROR;
	}

	@Override
	public String getDatas() {
		// TODO Auto-generated method stub
		return datas;
	}

}
