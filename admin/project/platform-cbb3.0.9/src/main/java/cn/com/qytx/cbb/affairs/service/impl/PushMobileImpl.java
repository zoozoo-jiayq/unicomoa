package cn.com.qytx.cbb.affairs.service.impl;

import java.io.FileInputStream;
import java.util.Properties;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.affairs.service.IPushMobile;
@Service
@Transactional
public class PushMobileImpl implements IPushMobile {

	public int  sendPush(String userId, String pushSubject, String pushContent,
			String moduleName, String toids, String recordId, String pushUrl,String pushType,String attmentIds){
		PostMethod postMethod=null;
		try{
			String path = this.getClass().getResource("/application.properties").getPath();
			FileInputStream file=new FileInputStream(path);
			Properties propertie=new Properties();
			propertie.load(file);
			String responseMsg="";
			//1.构造HttpClient的实例
			HttpClient httpClient=new HttpClient();
			
			httpClient.getParams().setContentCharset("utf-8");
			
			String url=propertie.getProperty("pushUrl");
			
			postMethod=new PostMethod(url);
			if(StringUtils.isNotBlank(userId)){
			postMethod.addParameter("fromUserId", userId);
			}
			if(StringUtils.isNotBlank(pushSubject)){
				postMethod.addParameter("subJect", pushSubject);
			}
			if(StringUtils.isNotBlank(pushContent)){				
				postMethod.addParameter("pushContent", pushContent);
			}
			if(StringUtils.isNotBlank(moduleName)){				
				postMethod.addParameter("moduleName", moduleName);
			}
			if(StringUtils.isNotBlank(recordId)){				
				postMethod.addParameter("recordId", recordId);
			}
			if(StringUtils.isNotBlank(pushUrl)){				
				postMethod.addParameter("pushUrl", pushUrl);
			}
			if(StringUtils.isNotBlank(toids)){				
				postMethod.addParameter("userIds", toids);
			}
			if(pushType!=null){
				postMethod.addParameter("pushType", pushType);
			}
			if(StringUtils.isNotBlank(attmentIds)){
				postMethod.addParameter("attmentIds", attmentIds);
			}
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
	public int  sendPush(String userId, String pushSubject, String pushContent,
			String moduleName, String toids, String recordId, String pushUrl,String pushType){
		PostMethod postMethod=null;
		try{
			String path = this.getClass().getResource("/application.properties").getPath();
			FileInputStream file=new FileInputStream(path);
			Properties propertie=new Properties();
			propertie.load(file);
			String responseMsg="";
			//1.构造HttpClient的实例
			HttpClient httpClient=new HttpClient();
			
			httpClient.getParams().setContentCharset("utf-8");
			
			String url=propertie.getProperty("pushUrl");
			
			postMethod=new PostMethod(url);
			if(StringUtils.isNotBlank(userId)){
			postMethod.addParameter("fromUserId", userId);
			}
			if(StringUtils.isNotBlank(pushSubject)){
				postMethod.addParameter("subJect", pushSubject);
			}
			if(StringUtils.isNotBlank(pushContent)){				
				postMethod.addParameter("pushContent", pushContent);
			}
			if(StringUtils.isNotBlank(moduleName)){				
				postMethod.addParameter("moduleName", moduleName);
			}
			if(StringUtils.isNotBlank(recordId)){				
				postMethod.addParameter("recordId", recordId);
			}
			if(StringUtils.isNotBlank(pushUrl)){				
				postMethod.addParameter("pushUrl", pushUrl);
			}
			if(StringUtils.isNotBlank(toids)){				
				postMethod.addParameter("userIds", toids);
			}
			if(pushType!=null){
				postMethod.addParameter("pushType", pushType);
			}
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
