package cn.com.qytx.cbb.publicDom.service;

import java.util.List;

import cn.com.qytx.cbb.publicDom.domain.FormAuthority;
import cn.com.qytx.platform.base.service.BaseService;

public interface IFormAuthority extends BaseService<FormAuthority> {

	/**
	 * 功能：根据表单属性ID查找表单属性权限对象
	 * @param
	 * @return
	 * @throws   
	 */
	public FormAuthority findByFormPropertyId(int formPropertyId);
	
	public void saveOrUpdateFormAuthority(FormAuthority fa);
	
	/**
	 * 功能：根据用户ID和表单ID查询用户有权限的表单属性列表
	 * @param
	 * @return
	 * @throws   
	 */
	@Deprecated
	public List<String> findHasAuthorityProperties(String formId,String userId);
	
	public List<FormAuthority> findByFormId(int formId);
	
}
