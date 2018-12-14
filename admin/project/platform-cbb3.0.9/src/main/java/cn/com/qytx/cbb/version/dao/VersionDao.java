package cn.com.qytx.cbb.version.dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.com.qytx.cbb.version.domain.VersionInfo;
import cn.com.qytx.platform.base.dao.BaseDao;
import cn.com.qytx.platform.base.query.Sort;
import cn.com.qytx.platform.base.query.Sort.Direction;

@Repository("versionDao")
public class VersionDao extends BaseDao<VersionInfo, Integer> implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 * 功能：获取最新版本信息
	 * @param companyId
	 * @param userId
	 * @param versionCode
	 * @return
	 * @throws SQLException 
	 */
	public VersionInfo getVersionInfo(String companyId, String userId,
			String versionCode,Integer type) throws SQLException {
		VersionInfo version=null;
		//获取最新的正式版本
		VersionInfo formalVer=getFormalVersion(type);
		//获取最新灰度版本
		VersionInfo grayVer=getGrayVerson(companyId, userId,type);
		if(formalVer!=null&&grayVer!=null){
			version=formalVer.getVersionCode()>grayVer.getVersionCode()?formalVer:grayVer;
		}else if(formalVer==null&&grayVer!=null){
			version=grayVer;
		}else if(formalVer!=null&&grayVer==null){
			version=formalVer;
		}
		//最新版本与当前版本code比较
		if(version!=null){
			//最新版本小于等于当前版本
			if(version.getVersionCode()<=Integer.parseInt(versionCode)){
				version=null;
			}
		}
		return version;
	}
	/**
	 * 
	 * 功能：获取最新的正式版本信息
	 * @return
	 * @throws SQLException 
	 */
	public VersionInfo getFormalVersion(Integer type) throws SQLException{
		String hql = " isDelete=0 and isFormal=0 and type=?";
		Sort sort=new Sort(Direction.DESC,"versionCode");
		List<VersionInfo> list = this.findAll(hql, sort,type);
		VersionInfo info = null;
		if (list != null && list.size() > 0) {
			info = list.get(0);
		}
		return info;
	}
	/**
	 * 
	 * 功能：获取最新灰度版本信息
	 * @return
	 * @throws SQLException 
	 */
	public VersionInfo getGrayVerson(String companyId,String userId,Integer type) throws SQLException{
		String hql = " isDelete=0 and type=? and vid in (select versionId from VersionCompany where companyId=? ";
		List<Object> params = new ArrayList<Object>();
		params.add(type);
		params.add(Integer.parseInt(companyId));
//		if (userId != null && userId != "") {
//			hql += " and userId= ? ";
//			params.add(Integer.parseInt(userId));
//		}
		hql += ")";
		Sort sort=new Sort(Direction.DESC,"versionCode");
		List<VersionInfo> list = this.findAll(hql,sort,params.toArray());
		VersionInfo info = null;
		if (list != null && list.size() > 0) {
			info = list.get(0);
		}
		return info;
	}
}
