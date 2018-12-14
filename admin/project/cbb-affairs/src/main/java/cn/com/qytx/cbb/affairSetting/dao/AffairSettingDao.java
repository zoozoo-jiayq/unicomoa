package cn.com.qytx.cbb.affairSetting.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.com.qytx.cbb.affairSetting.domain.AffairSetting;
import cn.com.qytx.platform.base.dao.BaseDao;

@Repository("affairSettingDao")
public class AffairSettingDao extends BaseDao<AffairSetting,Integer> {
	
	/**
	 * 根据模块名获取该模块的设置信息
	 * @param moduleName
	 * @return
	 */
	public AffairSetting findByName(String moduleName){
		return this.findOne("moduleName=?1", moduleName);
	}
	/**
	 * 获取所有的提醒设置
	 * @return
	 */
	public List<AffairSetting> findList(){
		return this.findAll();
	}
	
	/**
	 * 根据模块代码取该模块的设置信息
	 * @param moduleCode
	 * @return
	 */
	public AffairSetting findByCode(String moduleCode){
		return this.findOne("moduleCode=?1", moduleCode);
	}
}
