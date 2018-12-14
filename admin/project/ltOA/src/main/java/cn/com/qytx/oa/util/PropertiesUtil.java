package cn.com.qytx.oa.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Properties;

import cn.com.qytx.platform.base.action.BaseActionSupport;

@SuppressWarnings("serial")
public class PropertiesUtil extends BaseActionSupport {
	private static Properties pro = null;

	static {
		InputStream in = PropertiesUtil.class.getResourceAsStream("/voxFilePath.properties");
		try {
			pro = new Properties();
			pro.load(in);
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				LOGGER.error(e.getMessage());
			}
		}
	}

	public static String get(String name, String defaultValue) {
		if (pro == null){
			return defaultValue;
		}
		else if (!pro.containsKey(name)){
			return defaultValue;
		}
		else {
			try {
				return new String(pro.getProperty(name).getBytes("iso_8859_1"),
						"utf-8");
			} catch (UnsupportedEncodingException e) {
				return defaultValue;
			}
		}
	}
	/**
     * 读取properties配置文件中的value值
     * @param srcFile  properties配置文件的路径
     * @param key  properties配置文件中的key
     * @return  properties配置文件中的value
     */
    public static String getPropertiesValue(String srcFile,String key){
    	String result = "";
    	try {
    		Properties properties = new Properties();
    		String path = Thread.currentThread().getContextClassLoader().getResource(srcFile).getPath();  
    		path=URLDecoder.decode(path, "utf-8");
    		InputStream is = new FileInputStream(path);  
    		properties.load(is);
    		result = (String) properties.get(key);
    		is.close();
    	} catch (IOException e) {
    		LOGGER.error(e.getMessage());
    	}
    	return result;
    }
}
