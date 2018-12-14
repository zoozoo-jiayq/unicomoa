package cn.com.qytx.cbb.jbpmApp.action.mobile.jsonModel;

import cn.com.qytx.cbb.jbpmApp.action.mobile.MyBaseAction.RESPONSE_CODE;

import com.google.gson.Gson;


/**
 * 功能：向手机返回的JSON对象对应的Java对象  抽象类
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 下午5:32:43 
 * 修改日期：下午5:32:43 
 * 修改列表：
 */
public  abstract   class IJson <T> {
	public abstract RESPONSE_CODE getResponseCode();
	public T datas;

	public T getDatas() {
		// TODO Auto-generated method stub
		return datas;
	}
	
	public void setDatas(T datas){
		this.datas = datas;
	}
	
	public String getMobileClientResponse(){
		if(!(this instanceof JsonImplSuccess)){
			StringBuffer sb = new StringBuffer();
			sb.append(this.getResponseCode().getResCode()+"||");
			Gson gson = new Gson();
			sb.append(gson.toJson(getDatas()));
			return sb.toString();
		}else{
			StringBuffer sb = new StringBuffer();
			sb.append(this.getResponseCode().getResCode()+"||");
			Gson gson = new Gson();
			Object datas = getDatas();
			sb.append(gson.toJson(datas));
			return sb.toString();
		}
	}
}
