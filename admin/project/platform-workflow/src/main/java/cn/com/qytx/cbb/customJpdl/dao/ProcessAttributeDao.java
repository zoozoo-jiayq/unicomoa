package cn.com.qytx.cbb.customJpdl.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.com.qytx.cbb.customJpdl.domain.ProcessAttribute;
import cn.com.qytx.platform.base.dao.BaseDao;
import cn.com.qytx.platform.base.query.Sort;
import cn.com.qytx.platform.base.query.Sort.Direction;

/**
 * 功能：流程定义DAO 
 * <br/>版本: 1.0 
 * <br/>开发人员：贾永强 
 * <br/>创建日期: 2013-3-21 下午5:22:08 
 * <br/>修改日期：2013-3-21 下午5:22:08 
 * <br/>修改列表：
 */
@Repository("processAttributeDao")
public class ProcessAttributeDao extends BaseDao<ProcessAttribute, Integer>
		implements Serializable {

	/**
	 * 描述含义
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 功能：根绝流程定义ID删除流程定义
	 * 
	 * @param：id,流程定义ID
	 * @return
	 * @throws
	 */
	public void delete(int id) {
		String hql = "delete from ProcessAttribute where id = ?";
		super.bulkDelete(hql, id);
	}

	/**
	 * 功能：根据类别ID获取该类别下的所有流程实例
	 * 
	 * @param
	 * @return
	 * @throws
	 */
	public List<ProcessAttribute> findByCategoryId(int categoryId, int companyId) {
		return super.findAll("categoryId = ? and companyId = ?", new Sort(
				new Sort.Order(Direction.ASC, "id")), categoryId,
				companyId);
	}

	/**
	 * 功能：根据类别ID获取该类别下的所有流程实例
	 * 
	 * @param
	 * @return
	 * @throws
	 */
	public List<ProcessAttribute> findByCategoryId(int categoryId,String searchkey) {
		if(searchkey == null){
			searchkey = "";
		}
		if (categoryId > 0) {
			return findAll("categoryId = ? and processState=2 and processName like ?",new Sort(new Sort.Order(Direction.ASC,"createDate")), categoryId,"%"+searchkey+"%");
		} else {
			return findAll("processState=2 and processName like ?",new Sort(new Sort.Order(Direction.ASC,"createDate")),"%"+searchkey+"%");
		}
	}

	/**
	 * 功能：根据类别ID分组查询每组的数量
	 * 
	 * @param
	 * @return
	 * @throws
	 */
	public List<Object> getCountByCategory() {
		String sql = "select category_id,COUNT(process_attribute_id) as num from tb_cbb_process_attribute group by category_id";
		return entityManager.createNativeQuery(sql).getResultList();
	}

	/**
	 * 功能：根据流程定义ID查询流程定义属性
	 * 
	 * @param
	 * @return
	 * @throws
	 */
	public ProcessAttribute getProcessAttributeByDefineId(String definId) {
		return findOne("processDefineId = ?1",definId);
//		return findUnique("select p from  ProcessAttribute p processDefineId = ?", definId);
	}

	/**
	 * 功能：根据流程名称查询流程定义属性
	 * 
	 * @param
	 * @return
	 * @throws
	 */
	public ProcessAttribute getProcessAttributeByProcessName(String processName) {
		return findUnique("select p from ProcessAttribute p processName = ?", processName);
	}

	/**
	 * 功能：获取所有的流程定义根据公司ID
	 * 
	 * @param
	 * @return
	 * @throws
	 */
	public List<ProcessAttribute> getAllProcessAttributesByCompanyId(
			int companyId) {
		String hql = "select new ProcessAttribute(processName,id,categoryId,processDefineId) from ProcessAttribute where companyId = "+companyId;
		return entityManager.createQuery(hql).getResultList();
	}

	/**
	 * 功能：查询已发布的流程
	 * 
	 * @param
	 * @return
	 * @throws
	 */
	public List<ProcessAttribute> getAllDeployedProcessAttributes(int companyid) {
		return findAll("companyId = ? and processState = 2", companyid);
	}

	/**
	 * 功能：验证流程名称是否重复
	 * 
	 * @param
	 * @return true:重复；false：不重复
	 * @throws
	 */
	public boolean checkProcessNameIsRepeat(String processName,
			Integer processAttributeId) {
		List list = null;
		if (processAttributeId == null) {
			String hql = "processName = ? and processState !=?";
			list = this.findAll(hql, processName,ProcessAttribute.STOP_STATE);
		} else {
			String hql = "processName = ? and id != ? and processState != ?";
			list = this.findAll(hql, processName, processAttributeId,ProcessAttribute.STOP_STATE);
		}
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 功能：sql更新processAttribute对象
	 * 
	 * @param
	 * @return
	 * @throws
	 */
	@SuppressWarnings("deprecation")
	public void saveOrUpdateBysql(ProcessAttribute pa) {
		/*String sql = "update tb_cbb_process_attribute set process_define_byjson = ?,process_define_byxml = ?,category_id = ?,"
				+ "form_id = ?,process_name = ?,process_order = ?,directions = ?,process_state = ?,is_attach = ?,company_id = ?,"
				+ "process_name_expr = ?,process_name_beginNum = ?,process_name_num_length = ?,process_name_can_update = ? "
				+ "where process_attribute_id = ?";
		bulkUpdate(sql, pa.getProcsssDefinByJSON(), pa.getProcessDefineByXML(),
				pa.getCategoryId(), pa.getFormId(), pa.getProcessName(),
				pa.getProcessOrder() == null ? 0 : pa.getProcessOrder(),
				pa.getDirections(), ProcessAttribute.NOT_DEPLOY,
				pa.getIsAttach(), pa.getCompanyId(), pa.getProcessNameExpr(),
				pa.getProcessNameBeginNum(), pa.getProcessNamLength(),
				pa.getProcessNameCanupdate(), pa.getId());*/
		saveOrUpdate(pa);
	}

	/**
	 * 功能：sql更新processAttribute对象
	 * 
	 * @param
	 * @return
	 * @throws
	 */
	public void saveOrUpdateBysql(int processAttributeId, String jsonData,
			String xmlData) {
		String sql = "update ProcessAttribute set procsssDefinByJSON = ?,processDefineByXML = ? "
				+ "where process_attribute_id = ?";
		bulkUpdate(sql, jsonData, xmlData, processAttributeId);
	}

	/**
	 * 功能：更新processAttribute的起始编号
	 * 
	 * @param
	 * @return
	 * @throws
	 */
	public void updateProcessAttributeBeginNum(int processAttributeId) {
		String hql = "update ProcessAttribute set processNameBeginNum=processNameBeginNum+1 where id= "
				+ processAttributeId;
		bulkUpdate(hql);
	}
	
	public List<ProcessAttribute> findByFormId(int formId){
	    String hql = "formId = ?";
	    return super.findAll(hql, formId);
	}

}
