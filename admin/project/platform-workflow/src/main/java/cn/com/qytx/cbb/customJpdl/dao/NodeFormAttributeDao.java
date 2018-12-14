package cn.com.qytx.cbb.customJpdl.dao;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;

import cn.com.qytx.cbb.customJpdl.domain.NodeFormAttribute;
import cn.com.qytx.cbb.customJpdl.service.JpdlInterface;
import cn.com.qytx.platform.base.dao.BaseDao;

/**
 * 功能：流程节点属性DAO 
 * <br/>版本: 1.0 
 * <br/>开发人员：贾永强 
 * <br/>创建日期: 2013-3-21 下午5:20:52 
 * <br/>修改日期：2013-3-21 下午5:20:52 
 * <br/>修改列表：
 */
@Repository("nodeFormAttributeDao")
public class NodeFormAttributeDao extends BaseDao<NodeFormAttribute, Integer>
		implements Serializable {

	/**
	 * 描述含义
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 功能：批量删除节点属性
	 * 
	 * @param：sets,节点对象集合
	 * @return
	 * @throws
	 */
	public void delete(Set<NodeFormAttribute> sets) {
		for (Iterator<NodeFormAttribute> iterator = sets.iterator(); iterator
				.hasNext();) {
			NodeFormAttribute nfAttribute = iterator.next();
			super.delete(nfAttribute, true);
		}
	}

	/**
	 * 功能：根据流程ID和任务名称获取节点属性
	 * 
	 * @param
	 * @return
	 * @throws
	 */
	public NodeFormAttribute findByProcessIdAndName(final int processId, String toTaskName) {
	    String taskName = toTaskName;
		if (taskName != null && taskName.startsWith("TO ")) {
		    String[] strs = taskName.split(" ");
			taskName = strs[1];
		}
		return findOne("processAttribute.id = ?1 and nodeName = ?2", processId,taskName);
	}

	public List<NodeFormAttribute> findByProcessAttributeId(int processAttributeId) {
		return super.findAll("processAttribute.id = ?", processAttributeId);
	}

	/**
	 * 功能：获取流程的第一个节点 也就是 状态为start
	 * 
	 * @param processId2
	 * @return
	 */
	public NodeFormAttribute findFirstTask(int processAttributeId) {
		return findOne("nodeType?1 and processAttribute.id = ?2", JpdlInterface.NODE_TYPE_START,processAttributeId);
	}

	/**
	 * 功能：批量保存
	 * 
	 * @param
	 * @return
	 * @throws
	 */
	public void saveBatch(List<NodeFormAttribute> list) {
		for (int i = 0; i < list.size(); i++) {
			NodeFormAttribute nfa = list.get(i);
			saveOrUpdate(nfa);
		}
	}

	/**
	 * 功能：删除字段
	 * @param processAttributeId
	 */
	public void deleteBatch(int processAttributeId) {
		String sql = "delete from tb_cbb_node_form_attribute where process_attribute_id = "+processAttributeId;
		entityManager.createNativeQuery(sql).executeUpdate();
	}
	
	/**
	 * 功能：更新可编辑字段
	 * @param nodeId
	 * @param candidate
	 * @param groups
	 * @param roles
	 * @param writerables
	 * @param secrets
	 * @param editDoc
	 */
	public void updateNodeFormAttribute(int nodeId, String candidate,
			String groups, String roles, String writerables, String secrets,int editDoc){
		String sql = "update tb_cbb_node_form_attribute set writeable_properties = ?,candidate = ?,depts = ? ,roles = ?,secret_properties =?,editdoc = ? where id = ? ";
		super.entityManager.createNativeQuery(sql)
		.setParameter(1, writerables==null?"":writerables)
		.setParameter(2, candidate==null?"":candidate)
		.setParameter(3, groups == null?"":groups)
		.setParameter(4, roles == null?"":roles)
		.setParameter(5, secrets == null?"":secrets)
		.setParameter(6, editDoc)
		.setParameter(7, nodeId).executeUpdate();
	}
}
