package cn.com.qytx.cbb.publicDom.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.com.qytx.cbb.publicDom.domain.FormAuthority;
import cn.com.qytx.platform.base.dao.BaseDao;

/**
 * 功能：表单权限DAO
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 上午9:06:57 
 * 修改日期：上午9:06:57 
 * 修改列表：
 */
@Repository("formAuthorityDao")
public class FormAuthorityDao extends BaseDao<FormAuthority, Integer> implements Serializable{

	/**
	 * 描述含义
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 功能：
	 * @param
	 * @return
	 * @throws   
	 */
	public FormAuthority findByFormPropertyId(int formPropertyId){
		String hql = "select f from FormAuthority f where id = ?";
		return (FormAuthority) super.findUnique(hql, formPropertyId);
	}
	
	public List<FormAuthority> findByFormId(int formId){
		return super.findAll("formId = ?", formId);
	}
	
}
