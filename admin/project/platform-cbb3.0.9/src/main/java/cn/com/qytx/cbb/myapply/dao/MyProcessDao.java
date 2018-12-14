package cn.com.qytx.cbb.myapply.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import cn.com.qytx.cbb.myapply.domain.MyProcessed;
import cn.com.qytx.platform.base.dao.BaseDao;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.query.Sort;
import cn.com.qytx.platform.base.query.Sort.Direction;

@Repository
public class MyProcessDao extends BaseDao<MyProcessed,Integer>{
	
	public Page<MyProcessed> findListByUserId(Pageable pageable,Integer userId,String moduleCode){
		List<Object> params=new ArrayList<Object>();
		String hql="processerId=?1 and approveResult <> -1 and approveResult <> 2 ";
		params.add(userId);
		if(StringUtils.isNotBlank(moduleCode)){
			hql+=" and moduleCode=?2";
			params.add(moduleCode);
		}
		return findAll(hql, pageable, params.toArray());
	}

	public void del(String instanceIds, String moduleCode) {
		String[] ids = instanceIds.split(",");
		for(String id:ids){
			String hql = " delete from MyProcessed where instanceId =?1 and moduleCode=?2";
			executeQuery(hql,id,moduleCode);
		}
	}
	
	public List<MyProcessed> findByInstanceId(String instanceId){
		Sort sort = new Sort(Direction.ASC, "endTime");
		return findAll("instanceId = ? ",sort,instanceId);
	}
}
