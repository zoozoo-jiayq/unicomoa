package cn.com.qytx.cbb.publicDom.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.com.qytx.cbb.publicDom.domain.GongwenVar;
import cn.com.qytx.platform.base.dao.BaseDao;


/**
 * 功能：公文流程变量DAO
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 下午4:41:33 
 * 修改日期：下午4:41:33 
 * 修改列表：
 */
@Repository("gongwenVarDao")
public class GongwenVarDao extends BaseDao<GongwenVar, Integer> implements Serializable{

	/**
	 * 描述含义
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 功能：根据流程ID查询公文变量
	 * 作者：jiayongqiang
	 * 参数：
	 * 输出：
	 */
	public GongwenVar findByInstanceId(String instanceId){
		String hql = " instanceId = ?";
		return super.findOne(hql, instanceId);
	}
	
	/**
	 * 功能：根据流程实例ID删除公文变量表
	 * 作者：jiayongqiang
	 * 参数：
	 * 输出：
	 */
	public void deleteGongwenByInstanceId(String instanceId){
		String hql = "delete from GongwenVar where instanceId = ?";
		super.bulkDelete(hql, instanceId);
	}
	
	/**
	 * 功能：
	 * 作者：jiayongqiang
	 * 参数：
	 * 输出：
	 */
	public List<GongwenVar> findByTitle(String title){
		String hql = "title = ?";
		return super.findAll(hql, title);
	}
}
