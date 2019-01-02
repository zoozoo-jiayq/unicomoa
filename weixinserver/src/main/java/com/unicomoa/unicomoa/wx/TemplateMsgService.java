package com.unicomoa.unicomoa.wx;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.unicomoa.unicomoa.base.GlobalConfig;
import com.unicomoa.unicomoa.base.HttpHelper;
import com.unicomoa.unicomoa.wx.token.WxToken;
import com.unicomoa.unicomoa.wx.token.WxTokenService;

@Service
public class TemplateMsgService {
	@Resource
	private GlobalConfig config;
	@Resource
	private WxTokenService tokenService;
	
	public void sendMsg(String receiverOpenid,String templateId,String formid,Map<String,Object> data){
		WxToken token = tokenService.getToken(config.getAppid(), config.getAppsecret());
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("touser", receiverOpenid);
		params.put("template_id", templateId);
		params.put("page", "pages/member/member");
		params.put("form_id", formid);
		params.put("data", data);
		
		Gson gson = new Gson();
		String postData = gson.toJson(params);
		System.out.println(postData);
		HttpHelper.textPost("https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token="+token.getToken(), postData);
	}
}
