package cn.com.qytx.cbb.customForm.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.customForm.dao.FormAttributeDao;
import cn.com.qytx.cbb.customForm.dao.FormCategoryDao;
import cn.com.qytx.cbb.customForm.domain.FormAttribute;
import cn.com.qytx.cbb.customForm.domain.FormCategory;
import cn.com.qytx.cbb.customForm.service.IFormCategory;
import cn.com.qytx.cbb.customJpdl.dao.ProcessAttributeDao;
import cn.com.qytx.cbb.customJpdl.domain.ProcessAttribute;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;

/**
 * 分类管理impl
 * 版本: 1.0
 * 开发人员: 吴洲
 * 创建日期: 2013-3-25
 * 修改日期: 2013-3-25
 * 修改列表:
 */
@Service("formCategoryService")
@Transactional
public class FormCategoryImpl extends BaseServiceImpl<FormCategory> implements IFormCategory,Serializable{
	
	/**分类设置 */
	@Resource(name = "formCategoryDao")
    FormCategoryDao formCategoryDao;
	/**表单属性 */
	@Resource(name = "formAttributeDao")
    FormAttributeDao formAttributeDao;
	/**流程实例*/
	@Resource(name = "processAttributeDao")
    ProcessAttributeDao processAttributeDao;

	/**
	 * 通过分类id查找分类信息
	 * @param id 分类id
	 * @return 表单对象
	 */
	public FormCategory findById(Integer id){
		return formCategoryDao.findById(id);
	}
	/**
	 * 通过公司id和分类类别获取所有分类信息
	 * @param companyId 公司id
	 * @param type 分类类别 1，表单分类       2，流程分类
	 * @return  分类list
	 */
	public List<FormCategory> findByTypeCompanyId(Integer companyId,Integer type){
		return formCategoryDao.findAll(companyId, type);
	}
	/**
	 * 
	 * 通过公司id和分类类别获取所有分类信息数量
	 * @param companyId 公司id
	 * @param type 分类类别 1，表单分类     2.流程分类
	 * @return  分类数量
	 */
	public Integer getCountByTypeCompanyId(Integer companyId,Integer type){
		return formCategoryDao.getTotalCount(companyId, type);
	}
	/**
	 * 通过分类id删除分类信息
	 * @param id 分类id
	 * @param type 分类类别   1，表单分类     2.流程分类
	 * @return  1.删除成功  0.分类下存在实例，不能删除
	 */
	public Integer delete(Integer id,Integer type){
		//判断分类下是否包含实例，已被使用则不能删除
		int isUse = 0;
		if (type==1) {
			List<FormAttribute> formList = formAttributeDao.findByCategoryId(id);
			if (formList != null && formList.size()>0) {
				isUse = 1;
			}
		}else {
			List<ProcessAttribute> palist = processAttributeDao.findByCategoryId(id,"");
			if (palist != null && palist.size()>0) {
				isUse = 1;
			}
		}
		//表单已经被使用，不能删除
		if (isUse==1) {
			return 0;
		}
		//表单未被使用，可以删除
		formCategoryDao.delete(id);
		return 1;
	}
	/**
	 * 通过公司id和分类类别得到分页
	 * @param page
	 * @param companyId 公司id
	 * @param type  分类类别
	 * @return page
	 */
	public Page<FormCategory> findByPage(Pageable page,Integer companyId,Integer type) {
		return formCategoryDao.findByPage(page, companyId, type);
	}
	/**
	 * 通过公司id和分类类别得到分类总数
	 * @param companyId 公司id
	 * @param type 分类类别
	 * @return 分类总数
	 */
	public Integer getTotalCount(Integer companyId,Integer type) {
		return formCategoryDao.getTotalCount(companyId,type);
	}
	/**
	 * 通过公司id，分类类别和分类名称查找分类
	 * @param companyId 公司id
	 * @param type  分类类别 1，表单分类     2.流程分类
	 * @param categoryName 分类名称
	 * @return 分类list
	 */
	public List<FormCategory> findCategoryByCategoryName(Integer companyId,Integer type,String categoryName){
		return formCategoryDao.findCategoryByCategoryName(companyId,type,categoryName);
	}
}
