package cn.com.qytx.cbb.myapply.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import cn.com.qytx.cbb.myapply.domain.MyStarted;
import cn.com.qytx.cbb.myapply.service.MyApplyService.ModuleCode;
import cn.com.qytx.platform.base.dao.BaseDao;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;

@Repository
public class MyStartedDao extends BaseDao<MyStarted,Integer>{
	
	public Page<MyStarted> findListByUserId(Pageable pageable,Integer userId,String moduleCode) {
		List<Object> params=new ArrayList<Object>();
		String hql="createrId=?1 ";
		params.add(userId);
		if(StringUtils.isNotBlank(moduleCode)){
			hql+=" and moduleCode=?2";
			params.add(moduleCode);
		}
		return findAll(hql, pageable, params.toArray());
	}
	
	public MyStarted findByModuleCodeAndInstanceId(ModuleCode code,String instanceId){
		String hql = "moduleCode = ? and instanceId = ?";
		return findOne(hql, code.getCode(),instanceId);
	}
	public MyStarted findByModuleCodeAndInstanceId(String instanceId){
		String hql = "instanceId = ?";
		return findOne(hql,instanceId);
	}
	public void del(String instanceIds, String moduleCode) {
		String[] ids = instanceIds.split(",");
		for(String id : ids){
			String hql = "delete from MyStarted where instanceId =?1 and moduleCode = ?2 ";
			super.executeQuery(hql,id,moduleCode);
		}
	}
}
