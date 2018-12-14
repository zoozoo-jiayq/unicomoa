package cn.com.qytx.cbb.login.action;

/**
 * 功能：Logo配置参数
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 下午1:58:04 
 * 修改日期：下午:58:04 
 * 修改列表：
 */
public class LogoConfig { // NOPMD by jishubu on 14-8-15 ����2:26
	private static  LogoConfig instance = null;
	private LogoConfig(){
		
	}
	public synchronized static LogoConfig getInstance(){
		if(instance == null){
			instance = new LogoConfig();
		}
		return instance;
	}
	
	private String logoUrl;
	private String sysName;
	public String getLogoUrl() {
		return logoUrl;
	}
	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}
	public String getSysName() {
		return sysName;
	}
	public void setSysName(String sysName) {
		this.sysName = sysName;
	}
	
}
