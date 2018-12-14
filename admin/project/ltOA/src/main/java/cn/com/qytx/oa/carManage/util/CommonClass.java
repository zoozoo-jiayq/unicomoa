package cn.com.qytx.oa.carManage.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import cn.com.qytx.platform.utils.TimeUtil;

public class CommonClass {

	public static JSONObject setRequestHeadParam(String command){
		JSONObject returnJSON = new JSONObject();
		returnJSON.element("command", command);
		returnJSON.element("message_id", "0000010");
		returnJSON.element("device_id", "7427EA1D1AE17427EA1D1AE17427EA1D");
		returnJSON.element("sign_type", "MD5");
		returnJSON.element("sign", "f3AKCWksumTLzW5Pm38xiP9llqwHptZl9QJQxcm7zRvcXA4g");
		returnJSON.element("charset", "UTF-8");
		returnJSON.element("timestamp", TimeUtil.dateToStr(new Date(),"yyyyMMddHHmmss"));
		return returnJSON;
	}

		
	public static HttpEntity postMethod(String url, Object formParams, String contentType){
		//创建默认的HttpClient实例
		HttpClient httpClient = new DefaultHttpClient();
		//创建httpost
		HttpPost httpPost = new HttpPost(url);
		try {
			//编码
			StringEntity entity = new StringEntity(formParams.toString(), "UTF-8");
			httpPost.setEntity(entity);
			if(!"".equalsIgnoreCase(contentType))
				entity.setContentType(contentType);
			//提交POST请求,返回Response
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity returnEntity = response.getEntity();
			return returnEntity;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			
		}
		return null;
	}
}
