package cn.com.qytx.cbb.customForm.service;

import java.util.List;

import cn.com.qytx.cbb.customForm.domain.FormProperties;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.BaseService;

/**
 *项目名称：oa3.0
 *类名称：IFormProperties.java
 *类描述：表单控件属性接口
 *创建人：吴洲
 *创建时间：2013-3-13
 *@version
 */
public interface IFormProperties extends BaseService<FormProperties> {
	
	/**
	 * 
	 * @Title: deleteByFormId 
	 * @Description: TODO(通过表单id删除表单所有控件) 
	 * @param  formId 表单id
	 * @return void
	 */
	public void deleteByFormId(Integer formId);
	
	/**
	 * 
	 * @Title: insertFormItem 
	 * @Description: TODO(插入工作流表单项，先删除后插入) 
	 * @param  formPropertiesList  表单控件对象list
	 * @return void
	 */
	public void insertFormItem(List<FormProperties> formPropertiesList);
	
	/**
	 * 
	 * @Title: findById 
	 * @Description: TODO(通过表单控件id查找表单控件信息) 
	 * @param  id
	 * @return void
	 */
	public FormProperties findById(Integer id);
	
	/**
	 * 
	 * @Title: findByFormId 
	 * @Description: TODO(通过表单id查找表单所有控件信息) 
	 * @param  id
	 * @return void
	 */
	public List<FormProperties> findByFormId(Integer formId);
	
	/**
	 * 功能：通过表单控件id字符串查找控件对象list
	 * @param ids 控件id字符串（多个用逗号分隔）
	 * @return 控件对象list
	 */
	public List<FormProperties> findAllByIds(String ids);
	
	/**
	 * 功能：获得表单属性分页
	 * @param page
	 * @param formId
	 * @return
	 */
	public Page<FormProperties> findByPage(Pageable page, int formId);
	
	/**
	 * 功能：通过表单id查找表单所有控件信息
	 * @param formId
	 * @param userId
	 * @return
	 */
	public List<FormProperties> findByFormId(Integer formId, String userId);
}
