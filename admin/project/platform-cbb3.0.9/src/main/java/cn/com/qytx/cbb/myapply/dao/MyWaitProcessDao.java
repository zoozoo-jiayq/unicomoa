package cn.com.qytx.cbb.myapply.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import cn.com.qytx.cbb.myapply.domain.MyWaitProcess;
import cn.com.qytx.cbb.myapply.service.MyApplyService.ModuleCode;
import cn.com.qytx.platform.base.dao.BaseDao;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;

@Repository
public class MyWaitProcessDao extends BaseDao<MyWaitProcess,Integer>{
	
	public Page<MyWaitProcess> findListByUserId(Pageable pageable,Integer userId,String moduleCode) {
		List<Object> params=new ArrayList<Object>();
		String hql="(processerId=?1 or processerId is null) ";
		params.add(userId);
		if(StringUtils.isNotBlank(moduleCode)){
			hql+=" and moduleCode=?2";
			params.add(moduleCode);
		}
		return findAll(hql, pageable, params.toArray());
	}
	
	public MyWaitProcess findByModuleCodeInstanceId(ModuleCode code,String instanceId,Integer approver){
		String hql = "moduleCode = ? and instanceId = ?";
		if(approver!=null){
			hql+=" and processerId = "+approver;
		}
		return super.findOne(hql, code.getCode(),instanceId);
	}

	public void del(String instanceIds, String moduleCode) {
		String[] ids = instanceIds.split(",");
		for(String id:ids){
			String hql = "delete from MyWaitProcess where instanceId = ?1 and moduleCode=?2";
			executeQuery(hql,id,moduleCode);
		}
	}
	
	public int myWaitCount(Integer userId){
		return count("processerId=?1 or processerId is null", userId);
	}
	
	/**
	 * 功能：获得指定模块下面待处理数量
	 * @param userId
	 * @param moduleCode
	 * @return
	 */
	public int myWaitModuleCount(Integer userId, String moduleCode){
		List<Object> params=new ArrayList<Object>();
		String hql="(processerId=?1 or processerId is null) ";
		params.add(userId);
		if(StringUtils.isNotBlank(moduleCode)){
			hql+=" and moduleCode=?2";
			params.add(moduleCode);
		}
		return count(hql, params.toArray());
	}
	
	/**
	 * 功能：获得指定模块下面指定流程的审批人
	 * @param instanceIds
	 * @param moduleCode
	 * @return
	 */
	public List<Integer> getProcesserByInstanceIds(String instanceIds,String moduleCode){
		List<Integer> list = new ArrayList<Integer>();
		if(StringUtils.isNotEmpty(instanceIds) && StringUtils.isNotEmpty(moduleCode)){
			if(instanceIds.startsWith(",")){
				instanceIds = instanceIds.substring(1);
			}
			if(instanceIds.endsWith(",")){
				instanceIds = instanceIds.substring(0,instanceIds.length()-1);
			}
			if(StringUtils.isNotEmpty(instanceIds)){
				list = entityManager.createQuery("select distinct processerId from MyWaitProcess where instanceId in (?) and moduleCode = '"+moduleCode+"' ").setParameter(1, instanceIds).getResultList();
			}
		}
		return list;
	}
	
	/**
	 * 根据流程ID查询待审批的流程
	 * @param instanceId
	 * @return
	 */
	public List<MyWaitProcess> findByInstanceId(String instanceId){
		String hql = "instanceId = ?";
		return super.findAll(hql,instanceId);
	}
}
