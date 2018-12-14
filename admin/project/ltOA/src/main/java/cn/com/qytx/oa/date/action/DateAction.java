package cn.com.qytx.oa.date.action;

import java.util.Date;

import cn.com.qytx.platform.base.action.BaseActionSupport;

public class DateAction extends BaseActionSupport{
	
	public void getNowDate(){
		Date d = new Date();
		this.ajax(d.getTime());
	}
}
