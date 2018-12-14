package cn.com.qytx.cbb.baseworkflow.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;

import cn.com.qytx.platform.base.application.SpringContextHolder;

/**
 * @author jiayongqiang
 *	从配置文件中初始化固定流程类型
 *  单例模式，仅在第一次使用的时候读取一次
 */
public class InitBaseWorkflowConfig {
	private static InitBaseWorkflowConfig instance =  null;
	private InitBaseWorkflowConfig(){
		initConfig();
		initInstanceId();
	}
	public static synchronized InitBaseWorkflowConfig getInstance(){
		if(instance == null){
			instance = new InitBaseWorkflowConfig();
		}
		return instance ;
	} 
	
	private void initInstanceId(){
//		BaseWorkflowService service = SpringContextHolder.getBean(BaseWorkflowService.class);
//		String temp = service.getLastBaseworkflowInstanceId();
//		if(temp == null){
//			this.currentInstanceIdSuf = "00000000";
//		}else{
//			int index = temp.indexOf(".");
//			this.currentInstanceIdSuf = temp.substring(index+1);
//		}
	}
	
	/**
	 *  初始化配置文件
	 */
	private void initConfig(){
		InputStream is = this.getClass().getResourceAsStream("/cn/com/qytx/cbb/baseworkflow/baseworkflow.properties");
		Properties p = new Properties();
		try {
			p.load(is);
			Set<Object> keys = p.keySet();
			for(Iterator<Object> it = keys.iterator(); it.hasNext();){
				String key = it.next().toString();
				String value = p.getProperty(key);
				this.baseWorkflowConfig.put(key, value);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/***********************************************/
	private Map<String,String> baseWorkflowConfig = new HashMap<String, String>();
	
	
	private String currentInstanceIdSuf;
	
	public Map<String, String> getBaseWorkflowConfig() {
		return baseWorkflowConfig;
	}
	/**
	 * 返回8位数字后缀
	 * @return
	 */
	public synchronized String getNextInstanceIdSuf(){
//		int id = Integer.parseInt(this.currentInstanceIdSuf);
//		id++;
//		this.currentInstanceIdSuf = buildInstanceIdSuf(id);
//		return this.currentInstanceIdSuf;
		return UUID.randomUUID().toString();
	}
	
//	private String buildInstanceIdSuf(int id){
//		String str = "00000000";
//		str+=id;
//		int l = str.length();
//		int index = l-8;
//		return str.substring(index);
//	}
}
