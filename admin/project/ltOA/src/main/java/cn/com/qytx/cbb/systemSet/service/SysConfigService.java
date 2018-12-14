package cn.com.qytx.cbb.systemSet.service;

import java.util.List;
import java.util.Map;

import cn.com.qytx.cbb.systemSet.domain.SysConfig;

/**
 * 功能：系统配置服务接口
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 下午5:28:45 
 * 修改日期：下午5:28:45 
 * 修改列表：
 */
public interface SysConfigService {

	/**
	 * 功能：获取系统配置
	 * @param
	 * @return
	 * @throws   
	 */
	public Map<String,String> getSysConfig();
	
	/**
	 * 功能：获取系统配置
	 * @param
	 * @return
	 * @throws   
	 */
	public List<SysConfig> getSysConfigs();
	
	/**
	 * 功能：保存系统配置
	 * @param
	 * @return
	 * @throws   
	 */
	public void saveOrUpdateSysConfig(Map<String,String> map);
}
