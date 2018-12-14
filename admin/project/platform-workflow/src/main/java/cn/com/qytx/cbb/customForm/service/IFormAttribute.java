package cn.com.qytx.cbb.customForm.service;

import java.sql.Timestamp;
import java.util.List;

import cn.com.qytx.cbb.customForm.domain.FormAttribute;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.BaseService;

/**
*项目名称：oa3.0
*类名称：IFormAttribute.java
*类描述：
*创建人：吴洲
*创建时间：2013-3-13
*@version
 */
public interface IFormAttribute extends BaseService<FormAttribute>{
	/**
	 * 
	 * @Title: update 
	 * @Description: TODO(更新表单所有历史版本信息) 
	 * @param  companyId 公司id
	 * @param  oldFormName 表单原始名称
	 * @param  newFormName 表单新名称
	 * @param  categoryId  表单类别id
	 * @param  updateTime  更新时间
	 * @return void
	 */
	public void update(Integer companyId,String oldFormName,String newFormName,Integer categoryId,Timestamp updateTime);
	/**
	 * 
	 * @Title: findById 
	 * @Description: TODO(通过表单id查找表单信息) 
	 * @param  id
	 * @return void
	 */
	public FormAttribute findById(Integer id);
	/**
	 * 
	 * @Title: findByFormNameCompanyId 
	 * @Description: TODO(通过公司id和表单名字获取所有表单（所有历史版本）信息) 
	 * @param  companyId 公司id
	 * @param  formName 表单名称 
	 * @return list
	 */
	public List<FormAttribute> findByFormNameCompanyId(Integer companyId,String formName);
	/**
	 * 
	 * @Title: getCountByFormNameCompanyId 
	 * @Description: TODO(通过公司id和表单名字获取所有表单（所有历史版本）数量) 
	 * @param  companyId 公司id
	 * @param  formName 表单名称 
	 * @return Integer
	 */
	public Integer getCountByFormNameCompanyId(Integer companyId,String formName);
	/**
	 * 
	 * @Title: deleteByFormNameCompanyId 
	 * @Description:  
	 * @param  companyId 公司id
	 * @param  formId 表单ID 
	 * @return Integer  1.删除成功  0.表单已被使用，不能删除
	 */
	public Integer deleteByFormNameCompanyId(int companyId,String formName,int formId);
	/**
	 * 
	 * @Title: findByPage 
	 * @Description: TODO(通过公司id和表单类别得到分页) 
	 * @param  pageInfo  
	 * @param  companyId 公司id
	 * @param  formType 表单类别
	 * @param  searchName 搜索内容
	 * @return page  
	 */
	public Page<FormAttribute> findByPage(Pageable page,Integer companyId,String searchName,int categoryId);
	/**
	 * 
	 * @Title: getTotalCount 
	 * @Description: TODO(通过公司id和表单类别得到表单总数) 
	 * @param  companyId 公司id
	 * @param  formType 表单类别
	 * @param  searchName 搜索内容
	 * @return Integer  
	 */
	public Integer getTotalCount(Integer companyId,Integer formType,String searchName);
	/**
	 * 
	 * @Title: findById 
	 * @Description: TODO(通过公司id查找所有表单信息) 
	 * @param  companyId
	 * @return List<FormAttribute>
	 */
	public List<FormAttribute> findAll(Integer companyId);
	/**
	 * 得到类别分组
	 * @return 类别id和该类别下表单的数量的list
	 */
	public List<Object> findGroupByCategory();
	/**
	 * 通过类别id查找改类别下的所有表单
	 * @param categoryId
	 * @return  表单list
	 */
	public List<FormAttribute> findByCategoryId(Integer categoryId);
}
