package cn.com.qytx.cbb.customForm.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.com.qytx.cbb.customForm.domain.FormProperties;
import cn.com.qytx.platform.base.dao.BaseDao;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.query.Sort;
import cn.com.qytx.platform.base.query.Sort.Direction;
/**
 * 功能: 表单控件属性Dao
 * 版本: 1.0
 * 开发人员: 吴洲
 * 创建日期: 2013-3-21
 * 修改日期: 2013-3-21
 * 修改列表:
 */
@Repository("formPropertiesDao")
public class FormPropertiesDao extends BaseDao<FormProperties, Integer> implements Serializable{
	/**
	 * 描述含义
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 通过表单控件id查找表单控件对象
	 * @param id 表单控件id
	 * @return 表单控件对象
	 */
	public FormProperties findById(Integer id){
		String hql = "select f from FormProperties f where id=?";
		Object obj = super.findUnique(hql, id);
		if (obj!=null) {
			return (FormProperties)obj;
		}else {
			return null;
		}
	}
	/**
	 * 通过表单id删除表单所有控件
	 * @param formId 表单id
	 */
	public void delete(Integer formId){
		String hql = "delete from FormProperties where formId=?";
		super.bulkDelete(hql, formId);
	}
	/**
	 * 通过表单id查找表单所有控件
	 * @param formId 表单id
	 * @return 表单控件对象list
	 */
	public List<FormProperties> findByFormId(Integer formId){
		List<FormProperties> fpList ;
		List<Sort.Order> orderlist = new ArrayList<Sort.Order>();
		orderlist.add(new Sort.Order(Direction.ASC,"orderIndex"));
		orderlist.add(new Sort.Order(Direction.ASC,"createTime"));
		fpList = super.findAll("formId=?", new Sort(orderlist),formId);
		return fpList;
	}
	
	/**
	 * 功能：通过表单控件id字符串查找控件对象list
	 * @param formProps 控件id字符串（多个用逗号分隔）
	 * @return 控件对象list
	 */
	public List<FormProperties> findAllByIds(String ids){
	    String formProps = ids;
		List<FormProperties> fpList = new ArrayList<FormProperties>();
		if (formProps != null && !formProps.equals("")) {
			if (formProps.endsWith(",")) {
				formProps = formProps.substring(0, formProps.length()-1);
			}
			String hql = "propertyId in ("+formProps+")";
			fpList = super.findAll(hql);
		}
		return fpList;
	}
	
	/**
	 * 功能：获得表单属性分页
	 * @param page
	 * @param formId
	 * @return
	 */
	public Page<FormProperties> findByPage(Pageable page, int formId) {
		String hql="select f from FormProperties f where formId=?";
		hql+=" order by propertyId asc";
		return super.findByPage(page,hql,formId);
	}
	
	public void deleteLastWidget(int formId,int currentMaxOrder){
		String hql = "delete from FormProperties where formId = ? and orderIndex >?";
		super.executeQuery(hql, formId,currentMaxOrder);
	}
	 
}
