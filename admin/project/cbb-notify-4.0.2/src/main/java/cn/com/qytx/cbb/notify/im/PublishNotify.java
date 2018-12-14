package cn.com.qytx.cbb.notify.im;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Properties;

import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

import com.alibaba.druid.util.HttpClientUtils;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

/**
 * 启动一个新的线程将新闻发送到IM
 * @author jiars
 *
 */
public class PublishNotify extends Thread{
	
	private int appId;
	private String imserver;
	private String title;
	private String column;
	private String toUserIds;
	
	
	public PublishNotify(String title,String column,
			String toUserIds) {
		
		Properties p = init();
		this.appId = Integer.parseInt(p.getProperty("appId"));
		this.imserver = p.getProperty("imserver");
		this.title = title;
		this.column = column;
		this.toUserIds = toUserIds;
	}
	
	private Properties init(){
		InitImProperties im = new InitImProperties();
		return im.init();
	}


	private void pnews(int appId,String imserver,String column,String title,String toUserIds){
		String data = "";
		data+="appId="+appId;
		data+="&type=2";
		if(toUserIds!=null){
			data+="&toUserIds="+toUserIds;
		}
		try {
			if(column!=null){
				data+="&column="+URLEncoder.encode(column,"UTF-8");
			}
			if(title!=null){
				data+="&title="+URLEncoder.encode(title,"UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		System.out.println("start----------------------------");
		HttpClientUtils.post(imserver, data, 6000);
		LogFactory.getLog(PublishNotify.class.getClass()).info("发送post请求:"+imserver+"?"+data);
		System.out.println("end----------------------------");
	}
	
	@Override
	public void run() {
		try{
			this.pnews(appId, imserver,column, title, toUserIds);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
