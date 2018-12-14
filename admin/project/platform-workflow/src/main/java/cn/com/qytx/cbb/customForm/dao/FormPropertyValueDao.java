package cn.com.qytx.cbb.customForm.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.com.qytx.cbb.customForm.domain.FormPropertyValue;
import cn.com.qytx.platform.base.dao.BaseDao;
/**
 * 表单控件值Dao
 * 版本: 1.0
 * 开发人员: 吴洲
 * 创建日期: 2013-3-21
 * 修改日期: 2013-3-21
 * 修改列表:
 */
@Repository("formPropertyValueDao")
public class FormPropertyValueDao extends BaseDao<FormPropertyValue, Integer> implements Serializable{
	/**
	 * 描述含义
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 通过表单id查找表单是否被用
	 * @param formId 表单id
	 * @return 0。 未被使用   1。已被使用
	 */
	public Integer findFormPropertyValueByformId(Integer formId){
		//update by 贾永强
		//String hql = "from FormPropertyValue where beanPropertyId in (select propertyId from FormProperties where formId=?)";
		List<FormPropertyValue> list = super.findAll("id = ?", formId);
		if (list != null && list.size()>0) {
			return 1;
		}else {
			return 0;
		}
	}
	/**
	 * 通过流程id查找表单所有控件的值
	 * @param processInstanceId 流程实例id
	 * @return 表单控件值list
	 */
	public List<FormPropertyValue> findFormPropertyValueByProcessId(String processInstanceId){
		List<FormPropertyValue> list;
		list = super.findAll("beanId=?", processInstanceId);
		return list;
	}
	
	/**
	 * 功能：根据流程实例ID删除表单数据
	 * @param
	 * @return
	 * @throws   
	 */
	public void deleteByBeanId(String beanId){
		String sql = "delete from FormPropertyValue where beanId = ?";
		bulkDelete(sql,beanId);
	}
}
