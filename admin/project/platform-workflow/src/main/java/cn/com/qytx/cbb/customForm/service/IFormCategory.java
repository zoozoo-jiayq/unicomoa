package cn.com.qytx.cbb.customForm.service;

import java.util.List;

import cn.com.qytx.cbb.customForm.domain.FormCategory;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.BaseService;

/**
 * 分类管理
 * 版本: 1.0
 * 开发人员: 吴洲
 * 创建日期: 2013-3-25
 * 修改日期: 2013-3-25
 * 修改列表:
 */
public interface IFormCategory extends BaseService<FormCategory>{
	
	/**
	 * 通过分类id查找分类信息
	 * @param id 分类id
	 * @return  分类信息
	 */
	public FormCategory findById(Integer id);
	/**
	 * 通过公司id和分类类别获取所有分类信息
	 * @param companyId 公司id
	 * @param type 分类类别 1，表单分类       2，流程分类
	 * @return  分类list
	 */
	public List<FormCategory> findByTypeCompanyId(Integer companyId,Integer type);
	/**
	 * 
	 * 通过公司id和分类类别获取所有分类信息数量
	 * @param companyId 公司id
	 * @param type 分类类别 1，表单分类     2.流程分类
	 * @return  分类数量
	 */
	public Integer getCountByTypeCompanyId(Integer companyId,Integer type);
	/**
	 * 通过分类id删除分类信息
	 * @param id 分类id
	 * @param type 分类类别 1，表单分类     2.流程分类
	 * @return  1.删除成功  0.分类下存在实例，不能删除
	 */
	public Integer delete(Integer id,Integer type);
	/**
	 * 通过公司id和分类类别得到分页
	 * @param page
	 * @param companyId 公司id
	 * @param type  分类类别
	 * @return page
	 */
	public Page<FormCategory> findByPage(Pageable page,Integer companyId,Integer type);
	/**
	 * 通过公司id和分类类别得到分类总数
	 * @param companyId 公司id
	 * @param type 分类类别
	 * @return 分类总数
	 */
	public Integer getTotalCount(Integer companyId,Integer type);
	/**
	 * 通过公司id，分类类别和分类名称查找分类
	 * @param companyId 公司id
	 * @param type  分类类别 1，表单分类     2.流程分类
	 * @param categoryName 分类名称
	 * @return 分类list
	 */
	public List<FormCategory> findCategoryByCategoryName(Integer companyId,Integer type,String categoryName);
	
	public List<FormCategory> findAll();
}
