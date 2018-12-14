package cn.com.qytx.cbb.jbpmApp.action.mobile.jsonModel;

import cn.com.qytx.cbb.jbpmApp.action.mobile.MyBaseAction.RESPONSE_CODE;


/**
 * 功能：向客户端返回列表
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 下午5:33:37 
 * 修改日期：下午5:33:37 
 * 修改列表：
 * @param <T>
 */
public class JsonImplSuccess<T> extends IJson<T> {

	@Override
	public RESPONSE_CODE getResponseCode() {
		// TODO Auto-generated method stub
		return RESPONSE_CODE.RESPONSE_NORMAL_CODE;
	}

}
