package cn.com.qytx.cbb.jbpmApp.dao;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import cn.com.qytx.platform.base.dao.BaseDao;

/**
 * 功能：回退的时候删除子任务
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 下午7:32:59 
 * 修改日期：下午7:32:59 
 * 修改列表：
 */
@Repository("muliSignRollbackDao")
public class MuliSignRollbackDao extends BaseDao<Object, Integer> implements Serializable{
	
	/**
	 * 描述含义
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 功能： 并删除子任务
	 * @param
	 * @return
	 * @throws   
	 */
	public void deleteSubTask(String parentTaskId){
		String sql3 = "delete from JBPM4_TASK WHERE  SUPERTASK_ = "+parentTaskId;
		entityManager.createNativeQuery(sql3).executeUpdate();
	}
}
