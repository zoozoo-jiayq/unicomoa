package cn.com.qytx.cbb.version.service;

import java.sql.SQLException;

import cn.com.qytx.cbb.version.domain.VersionInfo;

public interface IVersion {
	/**
	 * 
	 * 功能：获取最新版本信息
	 * @param companyId
	 * @param userId
	 * @param versionCode
	 * @return
	 */
	public VersionInfo getVersionInfo(String companyId, String userId,
			String versionCode,Integer type)throws SQLException;
}
