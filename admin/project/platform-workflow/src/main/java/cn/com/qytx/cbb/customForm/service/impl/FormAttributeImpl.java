package cn.com.qytx.cbb.customForm.service.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.customForm.dao.FormAttributeDao;
import cn.com.qytx.cbb.customForm.dao.FormPropertiesDao;
import cn.com.qytx.cbb.customForm.dao.FormPropertyValueDao;
import cn.com.qytx.cbb.customForm.domain.FormAttribute;
import cn.com.qytx.cbb.customForm.service.IFormAttribute;
import cn.com.qytx.cbb.customJpdl.dao.ProcessAttributeDao;
import cn.com.qytx.cbb.customJpdl.domain.ProcessAttribute;
import cn.com.qytx.cbb.publicDom.dao.DocumentTypeDao;
import cn.com.qytx.cbb.publicDom.domain.DocumentType;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;

/**
 * 表单属性impl
 * 版本: 1.0
 * 开发人员: 吴洲
 * 创建日期: 2013-3-21
 * 修改日期: 2013-3-21
 * 修改列表:
 */
@Service("formAttributeService")
@Transactional
public class FormAttributeImpl extends BaseServiceImpl<FormAttribute> implements IFormAttribute{
	
	/**表单属性 */
	@Resource(name = "formAttributeDao")
    FormAttributeDao formAttributeDao;
	/**表单控件属性 */
	@Resource(name = "formPropertiesDao")
    FormPropertiesDao formPropertiesDao;
	/**表单控件的值 */
	@Resource(name = "formPropertyValueDao")
    FormPropertyValueDao formPropertyValueDao;
	@Resource
	private ProcessAttributeDao processAttributeDao;
	@Resource
	private DocumentTypeDao documentTypeDao;
	/**
	 * 更新表单所有历史版本信息
	 * @param  companyId 公司id
	 * @param  oldFormName 表单原始名称
	 * @param  newFormName 表单新名称
	 * @param  categoryId  表单类别id
	 * @param  updateTime  更新时间
	 */
	public void update(Integer companyId,String oldFormName,String newFormName,Integer categoryId,Timestamp updateTime){
		formAttributeDao.update(companyId, oldFormName, newFormName, categoryId, updateTime);
	}
	/**
	 * 通过表单id查找表单信息
	 * @param id 表单id
	 * @return 表单对象
	 */
	public FormAttribute findById(Integer id){
		return formAttributeDao.findById(id);
	}
	/**
	 * 通过公司id和表单名字获取所有表单（所有历史版本）信息
	 * @param  companyId 公司id
	 * @param  formName 表单名称 
	 * @return 表单list
	 */
	public List<FormAttribute> findByFormNameCompanyId(Integer companyId,String formName){
		return formAttributeDao.findByFormNameCompanyId(companyId, formName);
	}
	/**
	 * 通过公司id和表单名字获取所有表单（所有历史版本）数量
	 * @param  companyId 公司id
	 * @param  formName 表单名称 
	 * @return 表单数量
	 */
	public Integer getCountByFormNameCompanyId(Integer companyId,String formName){
		return formAttributeDao.getCountByFormNameCompanyId(companyId, formName);
	}
	/**
	 * 通过公司id和表单名字删除表单（所有历史版本），表单控件属性
	 * @param  companyId 公司id
	 * @param  formId 表单名称 
	 * @return Integer  1.删除成功  0.表单已被使用，不能删除
	 */
	public Integer deleteByFormNameCompanyId(int companyId,String formName,int formId){
		//判断表单是否被使用，已被使用则不能删除
		int isUse = 0;//是否被使用    0，未被使用1、已经被使用
		List<ProcessAttribute> processAttributeList = processAttributeDao.findByFormId(formId);
        List<DocumentType> documentList = documentTypeDao.findByFormId(formId);
        if((processAttributeList!=null && processAttributeList.size()>0) 
                || (documentList!=null && documentList.size()>0)){
            isUse = 1;
        }
		//表单已经被使用，不能删除
		if (isUse==1) {
			return 0;
		}
		//表单未被使用，可以删除
		//先删除所有历史版本的表单控件，然后再删除所有历史版本表单
		List<FormAttribute> formList = formAttributeDao.findByFormNameCompanyId(companyId, formName);
		for (int i = 0; i < formList.size(); i++) {
			formPropertiesDao.delete(formList.get(i).getFormId());
			formAttributeDao.delete(formList.get(i).getFormId());
		}
		return 1;
	}
	/**
	 * 通过公司id和表单类别得到分页
	 * @param  pageInfo  
	 * @param  companyId 公司id
	 * @param  formType 表单类别
	 * @param  searchName 搜索内容
	 * @return 分页信息  
	 */
	public Page<FormAttribute> findByPage(Pageable page, Integer companyId, String searchName,int categoryId) {
		return formAttributeDao.findByPage(page, companyId, searchName,categoryId);
	}
	/**
	 * 通过公司id和表单类别得到表单总数
	 * @param  companyId 公司id
	 * @param  formType 表单类别
	 * @param  searchName 搜索内容
	 * @return 表单总数
	 */
	public Integer getTotalCount(Integer companyId, Integer formType,String searchName) {
		return formAttributeDao.getTotalCount(companyId,formType,searchName);
	}
	/**
	 * 通过公司id查找所有表单信息
	 * @param companyId 公司id
	 * @return 表单list
	 */
	public List<FormAttribute> findAll(Integer companyId){
		return formAttributeDao.findAll(companyId);
	}
	/**
	 * 得到类别分组
	 * @return 类别id和该类别下表单的数量的list
	 */
	public List<Object> findGroupByCategory(){
		return formAttributeDao.findGroupByCategory();
	}
	/**
	 * 通过类别id查找改类别下的所有表单
	 * @param categoryId
	 * @return  表单list
	 */
	public List<FormAttribute> findByCategoryId(Integer categoryId){
		return formAttributeDao.findByCategoryId(categoryId);
	}
}
