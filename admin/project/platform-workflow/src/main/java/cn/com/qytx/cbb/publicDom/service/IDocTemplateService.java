package cn.com.qytx.cbb.publicDom.service;

import java.util.List;

import cn.com.qytx.cbb.publicDom.domain.DocTemplate;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.BaseService;

/**
 * 功能: 正文模板服务类 版本: 1.0 开发人员: 陈秋利 创建日期: 2013-4-15 修改日期: 2013-4-15 修改列表:
 */
public interface IDocTemplateService extends BaseService<DocTemplate>{


	/**
	 * 功能：根据公文一级公文类型，和名称查询公文类型列表
	 * 作者：jiayongqiang
	 * 参数：
	 * 输出：
	 */
	Page<DocTemplate> findPageList(Integer docTemplateType, String name,
			int companyId, Pageable pageInfo);


	/**
	 * 功能：根据模板名称查询模板对象
	 * 作者：jiayongqiang
	 * 参数：
	 * 输出：
	 */
	DocTemplate findByName(String name);

	/**
	 * 功能：查询公文分类下的公文数量
	 * 作者：jiayongqiang
	 * 参数：
	 * 输出：
	 */
	int getCount(Integer categoryId);

	/**
	 * 功能：查询公文分类下的公文模板列表
	 * 作者：jiayongqiang
	 * 参数：
	 * 输出：
	 */
	List<DocTemplate> findByCategory(int categoryId, Integer userId,
			Integer companyId);

	/**
	 * 功能：查询用户有权限的公文模板
	 * 作者：jiayongqiang
	 * 参数：
	 * 输出：
	 */
	List<DocTemplate> findAll(int companyId, int userId);
	
	/**
	 * 功能：查询指定单位下的公文套红模板
	 * 作者：jiayongqiang
	 * 参数：
	 * 输出：
	 */
	public List<DocTemplate> findAll(Integer companyId);
	
	/**
	 * 功能：批量删除
	 * 作者：jiayongqiang
	 * 参数：
	 * 输出：
	 */
	public boolean deleteMore(String ids);
	

}
