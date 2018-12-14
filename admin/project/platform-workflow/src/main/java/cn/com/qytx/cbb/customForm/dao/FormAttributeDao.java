package cn.com.qytx.cbb.customForm.dao;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.com.qytx.cbb.customForm.domain.FormAttribute;
import cn.com.qytx.platform.base.dao.BaseDao;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.query.Sort;
import cn.com.qytx.platform.base.query.Sort.Direction;

/**
 * 表单属性Dao
 * 版本: 1.0
 * 开发人员: 吴洲
 * 创建日期: 2013-3-21
 * 修改日期: 2013-3-21
 * 修改列表:
 */
@Repository("formAttributeDao")
public class FormAttributeDao extends BaseDao<FormAttribute, Integer> implements Serializable{
	/**
	 * 描述含义
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 更新表单所有历史版本信息
	 * @param  companyId 公司id
	 * @param  oldFormName 表单原始名称
	 * @param  newFormName 表单新名称
	 * @param  categoryId  表单类别id
	 * @param  updateTime  更新时间
	 */
	public void update(Integer companyId,String oldFormName,String newFormName,Integer categoryId,Timestamp updateTime){
		String hql = "update FormAttribute set formName=? , categoryId=? ,lastUpdateTime=? where formName=? and companyId=?";
		super.bulkUpdate(hql, newFormName,categoryId,updateTime,oldFormName,companyId);
	}
	/**
	 * 通过表单id查找表单信息
	 * @param id 表单id
	 * @return 表单属性对象
	 */
	public FormAttribute findById(Integer id){
		String hql = "select f from FormAttribute f where formId=?";
		Object obj = super.findUnique(hql, id);
		if (obj!=null) {
			return (FormAttribute)obj;
		}else {
			return null;
		}
	}
	/**
	 * 通过公司id查找所有表单信息
	 * @param companyId 公司id
	 * @return 表单对象list
	 */
	public List<FormAttribute> findAll(Integer companyId){
		List<FormAttribute> falist;
		falist = super.findAll("companyId=? ",new Sort(Direction.DESC, "createTime"), companyId);
		return falist;
	}
	/**
	 * 通过id删除表单信息
	 * @param id 表单id
	 */
	public void delete(Integer id){
		String hql = "delete from FormAttribute where formId=?";
		super.bulkDelete(hql, id);
	}
	/**
	 * 通过公司id和表单名字获取所有表单（所有历史版本）信息
	 * @param companyId 公司id
	 * @param formName 表单名称
	 * @return 表单属性list
	 */
	public List<FormAttribute> findByFormNameCompanyId(Integer companyId,String formName){
		return super.findAll("companyId=? and formName=?", companyId,formName);
	}
	/**
	 * 通过公司id和表单名字获取所有表单（所有历史版本）数量
	 * @param companyId 公司id
	 * @param formName 表单名称
	 * @return 表单数量
	 */
	public Integer getCountByFormNameCompanyId(Integer companyId,String formName){
		String hql = "select count(*) from FormAttribute where companyId=? and formName=?";
		Number number = 0;
		number = (Number)super.findUnique(hql, companyId,formName);
		if (number!=null) {
			return number.intValue();
		}else {
			return 0;
		}
	}
	/**
	 * 通过公司id和表单类别得到分页
	 * @param pages
	 * @param companyId 公司id
	 * @param formType 表单类别
	 * @param searchName搜索内容
	 * @return 分页
	 */
	public Page<FormAttribute> findByPage(Pageable page, Integer companyId, String searchName,int categoryId){
		String hql="select f from FormAttribute f where companyId=?";
		List params = new ArrayList();
		params.add(companyId);
		if (searchName!=null && !"null".equals(searchName) && !searchName.equals("")) {
			hql+=" and ( formName like ? ) )";
			params.add("%"+searchName+"%");
		}
		if(categoryId>0){
			hql+=" and categoryId = ? ";
			params.add(categoryId);
		}
		hql+=" order by createTime desc";
		return super.findByPage(page,hql,params.toArray());
	}
	/**
	 * 通过公司id和表单类别得到表单总数
	 * @param companyId 公司id
	 * @param formType 表单类别
	 * @param searchName 搜索内容
	 * @return 表单数量
	 */
	public Integer getTotalCount(Integer companyId, Integer formType,String searchName){
		String hql="select count(*) from FormAttribute where companyId=? ";
		List params = new ArrayList();
		params.add(companyId);
		if (formType!=null && formType!=0) {
			hql+=" and categoryId=?";
			params.add(formType);
		}
		if (searchName!=null && !searchName.equals("")) {
			hql+=" and formName like ?";
			params.add("%"+searchName+"%");
		}
		Number number=0;
		number=(Number)super.findUnique(hql,params.toArray());
		if(number!=null){
			return number.intValue();
		}else{
			return 0;
		}
	}
	/**
	 * 通过类别id查找改类别下的所有表单
	 * @param categoryId
	 * @return  表单list
	 */
	public List<FormAttribute> findByCategoryId(Integer categoryId){
		List<FormAttribute> list ;
		list = super.findAll("categoryId=?", categoryId);
		return list;
	}
	/**
	 * 得到类别分组
	 * @return 类别id和该类别下表单的数量的list
	 */
	public List findGroupByCategory(){
		String hql = "select categoryId,COUNT(*) from FormAttribute group by categoryId";
		List list;
		list = super.find(hql);
		return list;
	}
}
