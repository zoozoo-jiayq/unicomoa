package cn.com.qytx.cbb.customForm.service;

import java.util.List;
import java.util.Map;

import cn.com.qytx.cbb.customForm.domain.FormPropertyValue;
import cn.com.qytx.platform.base.service.BaseService;


public interface IFormPropertyValue extends BaseService<FormPropertyValue> {
	/**
	 * 
	 * @Title: findFormPropertyValueByformId
	 * @Description: TODO(通过表单id查找表单是否被用) 
	 * @param  id  表单id
	 * @return Integer 0。 未被使用   1。已被使用
	 */
	public Integer findFormPropertyValueByformId(Integer formId);
	/**
	 * 
	 * @Title: save 
	 * @Description: TODO(保存表单控件值) 
	 * @param  json  表单控件的name和value组成json字符串
	 * @param  processInstanceId 流程实例ID
	 * @param  formId   表单id
	 * @return void
	 */
	public void save(String json,String processInstanceId,Integer formId);
	/**
	 * 
	 * @Title: save 
	 * @Description: TODO(保存或者更新表单控件值) 
	 * @param  json  表单控件的name和value组成json字符串
	 * @param  processInstanceId 流程实例ID
	 * @param  formId   表单id
	 * @return void
	 */
	public void saveOrUpdate(String json,String processInstanceId,Integer formId);
	/**
	 * 
	 * @Title:  findAllValueByProcessId
	 * @Description: TODO(以json格式返回表单控件值（name,value）) 
	 * @param  json  表单控件的name和value组成json字符串
	 * @param  processInstanceId 流程实例ID
	 * @param  formId   表单id
	 * @return String   json数据
	 */
	public String findAllValueByProcessId(String processInstanceId,Integer formId);
	/**
	 * 
	 * 以map格式返回表单控件值（name,value）
	 * @param processInstanceId 流程实例ID
	 * @param formId 表单id
	 * @return map数据
	 */
	public Map findAllValueMapByProcessId(String processInstanceId, Integer formId);
	
	/**
	 * 功能：根流程实例ID删除表单数据
	 * @param
	 * @return
	 * @throws   
	 */
	public void deleteFormDataByBeanId(String beanId);
	
	/**
	 * 功能：根据流程id获得表单属性value
	 * @param instanceId
	 * @return
	 */
	public List<FormPropertyValue> findByInstanceId(String instanceId);
	
}
