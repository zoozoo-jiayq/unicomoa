package cn.com.qytx.cbb.baseworkflow.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.com.qytx.cbb.baseworkflow.domain.BaseWorkflow;
import cn.com.qytx.platform.base.dao.StrongBaseDao;

/**
 * 功能  固定流程dao
 * <br/>版本  1.0
 * <br/>开发人员  zyf
 * <br/>创建日期  2016年1月6日
 * <br/>修改日期  2016年1月6日
 * <br/>修改列表
 */
@Repository
public class BaseWorkflowDao extends StrongBaseDao<BaseWorkflow, Integer> {

	/**
	 * 获取最大的流程实例Id
	 * @return
	 */
	public String getLastBaseWorkflow(){
		String hql = "select instanceId from BaseWorkflow where id in (select max(id) from BaseWorkflow)";
		List<String> list = super.sfindAll(hql, null);
		if(list!=null && list.size() == 1){
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 根据流程ID获取流程数据
	 * @param instanceId
	 * @return
	 */
	public BaseWorkflow findByInstanceId(String instanceId){
		String hql = "from BaseWorkflow where instanceId = ?";
		List<BaseWorkflow> list = super.sfindAll(hql,null, instanceId);
		if(list!=null && list.size() == 1){
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 功能：根据流程id删除固定流程
	 * @param instanceId
	 */
	public void deleteByInstanceId(String instanceId){
		String hql = "delete from BaseWorkflow where instanceId = ?";
		super.bulkDelete(hql,instanceId);
	}
}
