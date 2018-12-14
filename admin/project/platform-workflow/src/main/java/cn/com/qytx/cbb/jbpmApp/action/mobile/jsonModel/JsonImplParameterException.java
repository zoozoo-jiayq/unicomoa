package cn.com.qytx.cbb.jbpmApp.action.mobile.jsonModel;

import cn.com.qytx.cbb.jbpmApp.action.mobile.MyBaseAction.RESPONSE_CODE;

public class JsonImplParameterException extends IJson<String> {

	@Override
	public RESPONSE_CODE getResponseCode() {
		// TODO Auto-generated method stub
		return RESPONSE_CODE.RESPONSE_PARAMETER_ERROR;
	}

	@Override
	public String getDatas() {
		// TODO Auto-generated method stub
		return "参数异常";
	}

}
