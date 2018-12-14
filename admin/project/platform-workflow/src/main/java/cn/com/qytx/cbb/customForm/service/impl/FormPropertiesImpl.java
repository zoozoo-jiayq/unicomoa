package cn.com.qytx.cbb.customForm.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;

import cn.com.qytx.cbb.customForm.dao.FormPropertiesDao;
import cn.com.qytx.cbb.customForm.domain.FormProperties;
import cn.com.qytx.cbb.customForm.service.IFormProperties;
import cn.com.qytx.cbb.jbpmApp.service.mobile.impl.JbpmMobileServiceImpl;
import cn.com.qytx.cbb.publicDom.service.IFormAuthority;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;

/**
 * 表单控件属性接口实现类
 * 版本: 1.0
 * 开发人员: 吴洲
 * 创建日期: 2013-3-21
 * 修改日期: 2013-3-21
 * 修改列表:
 */
@Service("formPropertiesService")
@Transactional
public class FormPropertiesImpl extends BaseServiceImpl<FormProperties> implements IFormProperties{
	private Logger log = LoggerFactory.getLogger(JbpmMobileServiceImpl.class);
	/**表单控件属性 */
	@Resource(name = "formPropertiesDao")
    FormPropertiesDao formPropertiesDao;
	
	@Resource(name="formAuthorityService")
	IFormAuthority formAuthorityService;
	
	/**
	 * 插入工作流表单项，先删除后插入
	 * @param formPropertiesList 表单控件list
	 * update by jiayq,只删除修改后的表单中不存在的属性，不再全部删除，本次修改后支持表单在流程运转中修改
	 */
	public void insertFormItem(List<FormProperties> formPropertiesList) {
		if (formPropertiesList.size()>0) {
			log.info("提交的表单属性:"+new Gson().toJson(formPropertiesList));
			//查询原表单的所有属性
			int formId = formPropertiesList.get(0).getFormId();
			List<FormProperties> oldProperties = formPropertiesDao.findByFormId(formId);
			if(oldProperties!=null){
				
				//如果该属性在修改后的表单中不存在(返回false)则删除该属性
				for(int i=0; i<oldProperties.size(); i++){
					FormProperties fp = oldProperties.get(i);
					if(!isContains(fp.getPropertyName(), formPropertiesList)){
						formPropertiesDao.delete(fp,true);
						log.info("删除的表单属性:"+new Gson().toJson(fp));
					}
				}
			}
			
			//遍历新表单的属性，如果在旧表单中不存在(返回false)则新增改属性
			for (int i = 0; i < formPropertiesList.size(); i++) {
				FormProperties tempFp = formPropertiesList.get(i);
				if(!isContains(tempFp.getPropertyName(), oldProperties)){
					formPropertiesDao.saveOrUpdate(formPropertiesList.get(i));
					log.info("新增的表单属性:"+new Gson().toJson(formPropertiesList.get(i)));
				}else{
					//update by jiayq,添加排序
					FormProperties t = getByPropertyName(tempFp.getPropertyName(), oldProperties);
					
					t.setOrderIndex(tempFp.getOrderIndex());
					t.setParentName(tempFp.getParentName());
					formPropertiesDao.saveOrUpdate(t);
					log.info("修改的表单属性:"+new Gson().toJson(t));
				}
			}
			
			//update by jiayq,删除多余的控件
			formPropertiesDao.deleteLastWidget(formId, formPropertiesList.size());
		}
	}
	
	private FormProperties getByPropertyName(String propertyName,List<FormProperties> properties){
		for(int i=0; i<properties.size(); i++){
			if(properties.get(i).getPropertyName().equals(propertyName)){
				return properties.get(i);
			}
		}
		return null;
	}
	
	/**
	 * 功能：如果formProperties列表中含有name属性，则返回true,否则返回false
	 * @param
	 * @return
	 * @throws   
	 */
	private boolean isContains(String name,List<FormProperties> formProperties){
		boolean result = false;
		for(int i=0; i<formProperties.size(); i++){
			FormProperties fp = formProperties.get(i);
			if(fp.getPropertyName().equals(name)){
				result = true;
				break;
			}
		}
		return result;
	}
	
	
	/**
	 * 通过表单id删除表单所有控件
	 * @param formId 表单id
	 */
	public void deleteByFormId(Integer formId){
		// TODO Auto-generated method stub
		formPropertiesDao.delete(formId);
	}
	/**
	 * 
	 * 通过表单控件id查找表单控件信息
	 * @param id 表单控件id
	 * @return 表单控件对象
	 */
	public FormProperties findById(Integer id) {
		return formPropertiesDao.findById(id);
	}
	/**
	 * 通过表单id查找表单所有控件信息
	 * @param formId 表单id
	 * @return 表单控件list
	 */
	public List<FormProperties> findByFormId(Integer formId){
		return formPropertiesDao.findByFormId(formId);
	}
	/**
	 * 功能：通过表单控件id字符串查找控件对象list
	 * @param ids 控件id字符串（多个用逗号分隔）
	 * @return 控件对象list
	 */
	public List<FormProperties> findAllByIds(String ids){
		return formPropertiesDao.findAllByIds(ids);
	}

	/**
	 * 功能：获得表单属性分页
	 * @param page
	 * @param formId
	 * @return
	 */
	public Page<FormProperties> findByPage(Pageable page, int formId) {
		 return formPropertiesDao.findByPage(page, formId);
	}
	
	/**
	 * 功能：通过表单id查找表单所有控件信息
	 * @param formId
	 * @param userId
	 * @return
	 */
	public List<FormProperties> findByFormId(Integer formId, String userId) {
		List<FormProperties> res = new ArrayList<FormProperties>(); 
		List<FormProperties> list = formPropertiesDao.findByFormId(formId);
		Map<String,String> map = new HashMap<String,String>(); 
	    List<FormProperties> formProList = formPropertiesDao.findByFormId(formId);
		for(FormProperties fp : formProList){
	 		map.put(fp.getPropertyName().trim(), fp.getPropertyName().trim());
	 	}
		Map<String,String> resMap = new HashMap<String,String>();
		if(userId!=null){
			List<String> auList = formAuthorityService.findHasAuthorityProperties(formId+"", userId+"");
			for(String propertyName:auList){
				resMap.put(propertyName, map.get(propertyName));
			}
			for(FormProperties fp : list){
				if(resMap.get(fp.getPropertyName())!=null){
					fp.setCanEdit(1);
				}else{
					fp.setCanEdit(0);
				}
				res.add(fp);
			}
		}else{
			res = list;
		}
		return res;
	}

}
