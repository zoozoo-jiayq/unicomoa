package com.unicomoa.unicomoa.wx.token;


import java.util.Date;

import javax.persistence.Entity;

import com.unicomoa.unicomoa.base.BaseModel;

@Entity
public class WxToken extends BaseModel{

	private String token;
	private Date refreshTime;
	private int expires = 0 ;//秒
	
	//返回true超时，false不超时
	public boolean isExpired(){
		Date now = new Date();
		if(now.getTime()-refreshTime.getTime()>expires*1000){
			return true;
		}
		return false;
	}
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Date getRefreshTime() {
		return refreshTime;
	}
	public void setRefreshTime(Date refreshTime) {
		this.refreshTime = refreshTime;
	}
	public int getExpires() {
		return expires;
	}
	public void setExpires(int expires) {
		this.expires = expires;
	}
	
}
