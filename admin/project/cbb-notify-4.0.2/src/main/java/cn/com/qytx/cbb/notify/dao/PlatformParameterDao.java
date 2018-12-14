package cn.com.qytx.cbb.notify.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import cn.com.qytx.cbb.notify.domain.PlatformParameter;
import cn.com.qytx.platform.base.dao.BaseDao;

import com.google.gson.Gson;

@Repository("platformParameterDao")
public class PlatformParameterDao extends BaseDao<PlatformParameter,Integer> {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	public Object findEntityByInstenceId(int instentceid) {
		String hql = "instenceid=?1 and isDelete = 0 ";
		PlatformParameter pp = (PlatformParameter) companyId().findOne(hql,instentceid);
		if(pp == null){
			return null;
		}
		Object obj = null;
		try {
			obj = Class.forName(pp.getParItems()).newInstance();
			Gson gson = new Gson();
			obj = gson.fromJson(((cn.com.qytx.cbb.notify.domain.PlatformParameter) pp).getParValueColl(),obj.getClass());
		} catch (InstantiationException e) {
			logger.error(e.getMessage());
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage());
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage());
		}
		return obj;
	}

	public PlatformParameter findByInstenceId(int instentceid) {
		String hql = "instenceid = ?1 and isDelete=?2";
		return super.companyId().findOne(hql,instentceid,0);
	}

}
