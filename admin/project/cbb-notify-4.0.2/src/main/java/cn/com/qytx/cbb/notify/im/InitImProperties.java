package cn.com.qytx.cbb.notify.im;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author jiars
 *加载IM配置文件
 */
 class InitImProperties {
	
	public Properties init(){
		InputStream is = this.getClass().getResourceAsStream("/notify-im.properties");
		Properties p = new Properties();
		try {
			p.load(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return p;
	}
	
}
