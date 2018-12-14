package cn.com.qytx.cbb.jbpmApp.action.mobile.jsonModel;

import cn.com.qytx.cbb.jbpmApp.action.mobile.MyBaseAction.RESPONSE_CODE;

public class JsonImplNull extends IJson<String> {

	@Override
	public RESPONSE_CODE getResponseCode() {
		// TODO Auto-generated method stub
		return RESPONSE_CODE.RESPONSE_DATA_NULL;
	}

	@Override
	public String getDatas() {
		// TODO Auto-generated method stub
		return "无数据";
	}

}
