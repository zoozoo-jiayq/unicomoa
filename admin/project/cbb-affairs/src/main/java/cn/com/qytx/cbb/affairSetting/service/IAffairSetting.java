package cn.com.qytx.cbb.affairSetting.service;

import java.util.List;

import cn.com.qytx.cbb.affairSetting.domain.AffairSetting;

public interface IAffairSetting {
	/**
	 * 根据模块名获取该模块的设置信息
	 * @param moduleName
	 * @return
	 */
	public AffairSetting findByName(String moduleName);
	/**
	 * 获取所有设置集合
	 * @return
	 */
	public List<AffairSetting> findList();
	
	/**
	 * 更新所有的设置信息
	 * @param affairSettings 设置信息集合
	 */
	public void updateAll(List<AffairSetting> affairSettings);
	
	/**
	 * 根据模块代码取该模块的设置信息
	 * @param moduleCode
	 * @return
	 */
	public AffairSetting findByCode(String moduleCode);
	/**
	 * 根据id获取设置信息
	 * @param id
	 * @return
	 */
	public AffairSetting findById(Integer id);
	/**
	 * 根据模块编码获取该模块设置信息
	 * @param moduleCode
	 * @return “1_0_0” 
	 */
	public String findDefaultByCode(String moduleCode);
}
