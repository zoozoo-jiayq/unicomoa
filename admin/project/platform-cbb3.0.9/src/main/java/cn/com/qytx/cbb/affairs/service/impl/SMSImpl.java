package cn.com.qytx.cbb.affairs.service.impl;

import java.io.FileInputStream;
import java.util.Properties;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.affairs.service.ISMS;

@Service
@Transactional
public class SMSImpl implements ISMS {

	public int sendSms(String data, String content) {
		String phones = data;
		PostMethod postMethod=null;
		try{
				if(StringUtils.isNoneBlank(phones))
				{
					if(phones.startsWith(","))
					{
						phones=phones.substring(1,phones.length());
					}
					if(phones.endsWith(","))
					{
						phones=phones.substring(0,phones.length()-1);
					}
				}
				String path = this.getClass().getResource("/application.properties").getPath();
				FileInputStream file=new FileInputStream(path);
				Properties propertie=new Properties();
				propertie.load(file);
				String responseMsg="";
				//1.构造HttpClient的实例
				HttpClient httpClient=new HttpClient();
				
				httpClient.getParams().setContentCharset("utf-8");
				
				String url=propertie.getProperty("smsUrl");
				
				postMethod=new PostMethod(url);
				
				postMethod.addParameter("content", content);
				postMethod.addParameter("phones", phones);
		
			 	httpClient.executeMethod(postMethod);
				responseMsg=postMethod.getResponseBodyAsString();
				if("0".equals(responseMsg)){
					return 0;
				}else{
					return 1;
				}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(postMethod!=null)
			{
				postMethod.releaseConnection();
			}
		}
		return 0;
	}
	
}
