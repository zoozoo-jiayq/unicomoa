package cn.com.qytx.cbb.datafilter.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.com.qytx.platform.base.dao.BaseDao;
import cn.com.qytx.platform.security.domain.DataFilter;

@Repository
public class DataFilterDao extends BaseDao<DataFilter, Integer> {

	public List<DataFilter> findByModule(String moduleClass){
		String hql = "modelClassName = ?";
		return super.findAll(hql, moduleClass);
	}
}
