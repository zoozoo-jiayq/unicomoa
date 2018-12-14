package cn.com.qytx.cbb.affairSetting.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.affairSetting.dao.AffairSettingDao;
import cn.com.qytx.cbb.affairSetting.domain.AffairSetting;
import cn.com.qytx.cbb.affairSetting.service.IAffairSetting;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;



@Transactional
@Service
public class AffairSettingImpl extends BaseServiceImpl<AffairSetting> implements IAffairSetting{
	
	@Resource(name="affairSettingDao")
	AffairSettingDao affairSettingDao;
	
	
	public List<AffairSetting> findList() {
		
		return affairSettingDao.findList();
	}

	
	public AffairSetting findByName(String moduleName) {
		return affairSettingDao.findByName(moduleName);
	}

	
	public void updateAll(List<AffairSetting> affairSettings) {
		for(AffairSetting as:affairSettings){
			affairSettingDao.saveOrUpdate(as);
		}
		
	}

	
	public AffairSetting findByCode(String moduleCode) {
        return affairSettingDao.findByCode(moduleCode);
	}

	
	public AffairSetting findById(Integer id) {
		return affairSettingDao.findOne(id);
	}

	
	public String findDefaultByCode(String moduleCode) {
		AffairSetting affairSetting = affairSettingDao.findByCode(moduleCode);
		if (affairSetting != null) {
			String sendType = affairSetting.getAffairPriv() + "_"
					+ affairSetting.getSmsPriv() + "_"
					+ affairSetting.getPushPriv();
			return sendType;
		} else {
			return null;
		}
	}
	
}
