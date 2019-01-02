package com.unicomoa.unicomoa.wx.token;


import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.unicomoa.unicomoa.base.BaseRepository;
import com.unicomoa.unicomoa.base.BaseServiceProxy;
import com.unicomoa.unicomoa.base.HttpHelper;
import com.unicomoa.unicomoa.utils.Variant;

@Service
@Transactional
public class WxTokenService extends BaseServiceProxy<WxToken> {

	@Resource
	private WxTokenRepository wxTokenRepository;

	@Override
	protected BaseRepository<WxToken> getBaseRepository() {
		// TODO Auto-generated method stub
		return wxTokenRepository;
	}

	private WxToken findTheOne() {
		Iterable<WxToken> its = wxTokenRepository.findAll();
		if (its != null) {
			Iterator<WxToken> it = its.iterator();
			if (it.hasNext()) {
				return it.next();
			}
		}
		return null;
	}

	public WxToken getToken(String appId, String appSecret) {
		WxToken token = this.findTheOne();
		if (token == null || token.isExpired()) {
			if (token == null) {
				token = new WxToken();
			}
			String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appId
					+ "&secret=" + appSecret;
			String result = HttpHelper.textGet(url);
			Map map = new Gson().fromJson(result, Map.class);
			String accessToken = (String) map.get("access_token");
			if (accessToken != null) {
				token.setToken(accessToken);
				token.setExpires(Variant.valueOf(map.get("expires_in")).intValue());
				token.setRefreshTime(new Date());
				this.save(token);
			}
		}
		return token;
	}

}
