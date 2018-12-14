package cn.com.qytx.cbb.systemSet.action;

import java.util.Map;

import javax.annotation.Resource;

import cn.com.qytx.cbb.systemSet.domain.SysConfig;
import cn.com.qytx.cbb.systemSet.service.SysConfigService;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.utils.encrypt.MD5;

/**
 * 功能：系统设置
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 下午1:33:19 
 * 修改日期：下午1:33:19 
 * 修改列表：
 */
public class CommonSet extends BaseActionSupport {
	@Resource
	private SysConfigService sysConfigService;
	
	private Map<String,String> config;
	
	private SysConfig c;
	
	public SysConfig getC() {
		return c;
	}

	public void setC(SysConfig c) {
		this.c = c;
	}

	public Map<String, String> getConfig() {
		return config;
	}

	public void setConfig(Map<String, String> config) {
		this.config = config;
	}
	
    /**进入系统设置界面
	 * 功能：
	 * @param
	 * @return
	 * @throws   
	 */
	public String toCommonSet(){
		return SUCCESS;
	}
	
	/**
	 * 功能：保存通用设置
	 * 作者：jiayongqiang
	 * 参数：
	 * 输出：
	 */
	public String saveSet(){
		sysConfigService.saveOrUpdateSysConfig(config);
		getRequest().setAttribute("flag", "success");
		return SUCCESS;
	}
	
	/**
	 * 功能：获取通用设置
	 * 作者：jiayongqiang
	 * 参数：
	 * 输出：
	 */
	public String getCommonSet(){
	    Map map = sysConfigService.getSysConfig();
	    ajax(map);
	    return null;
	}
	
	/**
	 * 功能：判断是否是默认密码
	 * 作者：jiayongqiang
	 * 参数：
	 * 输出：
	 */
	public String isDefaultPwd(){
	    UserInfo u = getLoginUser();
	    MD5 md5 = new MD5();
        String str = md5.encrypt(SysConfig.DEFAULT_PASSWORD);
        if(str.equals(u.getLoginPass())){
            ajax("default");
        }
	    return null;
	}
	
	/**获取系统设置
	 * 功能：
	 * @param
	 * @return
	 * @throws   
	 */
	public Map<String,String> getSysConfig(){
		 Map<String,String> map = sysConfigService.getSysConfig();
		return map;
	}
	
	/**
	 * 功能：跳转到公文设置页面
	 * 作者：jiayongqiang
	 * 参数：
	 * 输出：
	 */
	public String toDomConfig(){
	    return "domConfig";
	}
	
	/**
	 * 功能：保存公文设置
	 * 作者：jiayongqiang
	 * 参数：
	 * 输出：
	 */
	public String saveDomConfig(){
	    sysConfigService.saveOrUpdateSysConfig(config);
	    return "domConfig";
	}
	 
}
