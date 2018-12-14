package cn.com.qytx.cbb.version.impl;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.version.dao.VersionDao;
import cn.com.qytx.cbb.version.domain.VersionInfo;
import cn.com.qytx.cbb.version.service.IVersion;
@Transactional
@Service("versionService")
public class VersionImpl implements IVersion{
	@Resource(name="versionDao")
	VersionDao versionDao;
	@Override
	public VersionInfo getVersionInfo(String companyId, String userId,
			String versionCode,Integer type) throws SQLException {
		
		return versionDao.getVersionInfo(companyId,userId,versionCode,type);
	}

}
