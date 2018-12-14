package cn.com.qytx.cbb.customForm.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.customForm.dao.FormPropertiesDao;
import cn.com.qytx.cbb.customForm.dao.FormPropertyValueDao;
import cn.com.qytx.cbb.customForm.domain.Form;
import cn.com.qytx.cbb.customForm.domain.FormProperties;
import cn.com.qytx.cbb.customForm.domain.FormPropertyValue;
import cn.com.qytx.cbb.customForm.service.IFormPropertyValue;
import cn.com.qytx.platform.base.application.TransportUser;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
/**
 * 表单控件值impl
 * 版本: 1.0
 * 开发人员: 吴洲
 * 创建日期: 2013-3-21
 * 修改日期: 2013-3-21
 * 修改列表:
 */
@Service("formPropertyValueService")
@Transactional
public class FormPropertyValueImpl extends BaseServiceImpl<FormPropertyValue>implements IFormPropertyValue{
	
	/**表单控件的值 */
	@Resource(name = "formPropertyValueDao")
    FormPropertyValueDao formPropertyValueDao;
	/**表单控件属性 */
	@Resource(name = "formPropertiesDao")
    FormPropertiesDao formPropertiesDao;

	/**
	 * 通过表单id查找表单是否被用
	 * @param formId 表单id
	 * @return Integer 0。 未被使用   1。已被使用
	 */
	public Integer findFormPropertyValueByformId(Integer formId){
		return formPropertyValueDao.findFormPropertyValueByformId(formId);
	}
	/**
	 * 保存表单控件值
	 * @param json 表单控件的name和value组成json字符串
	 * @param processInstanceId 流程实例ID
	 * @param formId 表单id
	 */
	public void save(String json,String processInstanceId,Integer formId){
		Gson gson = new Gson();
		List<Form> ps = gson.fromJson(json, new TypeToken<List<Form>>(){}.getType());
		//把formId对应的表单控件放到formPropertyMap里
		List<FormProperties> fpList = formPropertiesDao.findByFormId(formId);
        Map<String, Integer> formPropertyMap = new HashMap<String, Integer>();
        if (fpList.size() > 0) {
        	
			for (FormProperties fp : fpList) {
				formPropertyMap.put(fp.getPropertyName(), fp.getPropertyId());
			}
		}
		for(int i = 0; i < ps.size() ; i++)
		{
			Form p = ps.get(i);
			FormPropertyValue fpv = new FormPropertyValue();
		    fpv.setBeanId(processInstanceId);
		    if (formPropertyMap.get(p.getName()) != null) {
				fpv.setBeanPropertyId(formPropertyMap.get(p.getName()));
			}else {
				fpv.setBeanPropertyId(0);
			}
		    fpv.setBeanValue(p.getValue());
		    fpv.setCompanyId(TransportUser.get().getCompanyId());
		    formPropertyValueDao.saveOrUpdate(fpv);
		}
	}
	/**
	 * 保存或者更新表单控件值
	 * @param json 表单控件的name和value组成json字符串
	 * @param processInstanceId 流程实例ID
	 * @param formId 表单id
	 */
	public void saveOrUpdate(String json,String processInstanceId,Integer formId){
		Gson gson = new Gson();
		List<Form> ps = gson.fromJson(json, new TypeToken<List<Form>>(){}.getType());
		//判断此流程的表单是否保存
		List<FormPropertyValue> fpvlist = formPropertyValueDao.findFormPropertyValueByProcessId(processInstanceId);
		if (fpvlist != null && fpvlist.size() > 0) {   
			/****已经保存，进行更新操作**/
			//把formId对应的表单控件放到formPropertyMap里
			List<FormProperties> fpList = formPropertiesDao.findByFormId(formId);
	        Map<Integer, String> formPropertyMap = null;
	        if (fpList.size() > 0) {
	        	formPropertyMap = new HashMap<Integer, String>();
				for (FormProperties fp : fpList) {
					formPropertyMap.put(fp.getPropertyId() , fp.getPropertyName());
				}
			}
			//把表单控件的值放到formPropertyValueMap里
	        Map<String, FormPropertyValue> formPropertyValueMap = null;
	        if (fpvlist.size() > 0) {
	        	formPropertyValueMap = new HashMap<String, FormPropertyValue>();
				for (FormPropertyValue fpv : fpvlist) {
					if (formPropertyMap!=null&&formPropertyMap.get(fpv.getBeanPropertyId()) != null) {
						formPropertyValueMap.put(formPropertyMap.get(fpv.getBeanPropertyId()), fpv);
					}
					
				}
			}
	        for(int i = 0; i < ps.size() ; i++)
			{
				Form p = ps.get(i);
				FormPropertyValue fpv = formPropertyValueMap.get(p.getName());
			    if (fpv !=null ) {
			    	fpv.setBeanValue(p.getValue());
			    	fpv.setLastUpdateTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			    	fpv.setCompanyId(TransportUser.get().getCompanyId());
				    formPropertyValueDao.saveOrUpdate(fpv);
				    p.setFlag(true);
				}
			}
	        
	        //add by 贾永强
	      //把formId对应的表单控件放到formPropertyMap里
	        Map<String, Integer> tempMap =null;
	        if (fpList.size() > 0) {
	        	tempMap = new HashMap<String,Integer>();
				for (FormProperties fp : fpList) {
					tempMap.put(fp.getPropertyName(), fp.getPropertyId());
				}
			}
	       for(int i=0; i<ps.size(); i++){
	    	   if(!ps.get(i).isFlag()){
	    		   FormPropertyValue fpv = new FormPropertyValue();
	    		   fpv.setBeanId(processInstanceId);
				    
				    if (formPropertyMap!=null &&tempMap.get(ps.get(i).getName()) != null) {
						fpv.setBeanPropertyId(tempMap.get(ps.get(i).getName()));
					}else {
						fpv.setBeanPropertyId(0);
					}
				    fpv.setBeanValue(ps.get(i).getValue());
				    fpv.setCreateTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
				    fpv.setCompanyId(TransportUser.get().getCompanyId());
				    formPropertyValueDao.saveOrUpdate(fpv);
	    	   }
	       }
		}else {
			/**没有保存，进行插入操作**/
			//把formId对应的表单控件放到formPropertyMap里
			List<FormProperties> fpList = formPropertiesDao.findByFormId(formId);
	        Map<String, Integer> formPropertyMap =null;
	        if (fpList.size() > 0) {
	        	formPropertyMap = new HashMap<String,Integer>();
				for (FormProperties fp : fpList) {
					formPropertyMap.put(fp.getPropertyName(), fp.getPropertyId());
				}
			}
			for(int i = 0; i < ps.size() ; i++)
			{
				Form p = ps.get(i);
				FormPropertyValue fpv = new FormPropertyValue();
			    fpv.setBeanId(processInstanceId);
			    
			    if (formPropertyMap!=null &&formPropertyMap.get(p.getName()) != null) {
					fpv.setBeanPropertyId(formPropertyMap.get(p.getName()));
				}else {
					fpv.setBeanPropertyId(0);
				}
			    fpv.setBeanValue(p.getValue());
			    fpv.setCreateTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			    fpv.setCompanyId(TransportUser.get().getCompanyId());
			    formPropertyValueDao.saveOrUpdate(fpv);
			}
		}
	}
	/**
	 * 
	 * 以json格式返回表单控件值（name,value）
	 * @param processInstanceId 流程实例ID
	 * @param formId 表单id
	 * @return json数据
	 */
	public String findAllValueByProcessId(String processInstanceId, Integer formId) {
		Gson gson = new Gson();
		List<Form> formlist = new ArrayList<Form>();
		List<FormPropertyValue> fpvlist ;
		fpvlist = formPropertyValueDao.findFormPropertyValueByProcessId(processInstanceId);
		//把formId对应的表单控件放到formPropertyMap里
		List<FormProperties> fpList = formPropertiesDao.findByFormId(formId);
        Map<Integer, String> formPropertyMap = new HashMap<Integer, String>();
        if (fpList.size() > 0) {
        	
			for (FormProperties fp : fpList) {
				formPropertyMap.put(fp.getPropertyId() , fp.getPropertyName());
			}
		}
		if (fpvlist != null) {
			for (int i = 0; i < fpvlist.size(); i++) {
				Form f = new Form();
				f.setName(formPropertyMap.get(fpvlist.get(i).getBeanPropertyId()));
				f.setValue(fpvlist.get(i).getBeanValue());
				formlist.add(f);
			}
		}
		String str = gson.toJson(formlist);
		return str;
	}
	/**
	 * 
	 * 以map格式返回表单控件值（name(已经过滤掉首尾下划线),value）
	 * @param processInstanceId 流程实例ID
	 * @param formId 表单id
	 * @return map数据
	 */
	public Map findAllValueMapByProcessId(String processInstanceId, Integer formId) {
		Map map=new HashMap();
		List<FormPropertyValue> fpvlist;
		fpvlist = formPropertyValueDao.findFormPropertyValueByProcessId(processInstanceId);
		//把formId对应的表单控件放到formPropertyMap里
		List<FormProperties> fpList = formPropertiesDao.findByFormId(formId);
        Map<Integer, String> formPropertyMap = new HashMap<Integer, String>();
        if (fpList!=null&&fpList.size() > 0) {
			for (FormProperties fp : fpList) {
				formPropertyMap.put(fp.getPropertyId() , fp.getPropertyName());
			}
		}
		if (fpvlist != null) {
			for (int i = 0; i < fpvlist.size(); i++) {
				if(formPropertyMap.get(fpvlist.get(i).getBeanPropertyId())!=null){
					map.put(formPropertyMap.get(fpvlist.get(i).getBeanPropertyId()), fpvlist.get(i).getBeanValue());
				}
			}
		}
		return map;
	}
	/**
	 * 功能：
	 * @param
	 * @return
	 * @throws   
	 */
	@Override
	public void deleteFormDataByBeanId(String beanId) {
		formPropertyValueDao.deleteByBeanId(beanId);
	}

	/**
	 * 功能：根据流程id获得表单属性value
	 * @param instanceId
	 * @return
	 */
	@Override
	public List<FormPropertyValue> findByInstanceId(String instanceId) {
		List<FormPropertyValue> list = formPropertyValueDao.findFormPropertyValueByProcessId(instanceId);
		return list;
	}
}
