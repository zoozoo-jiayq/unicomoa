package cn.com.qytx.oa.knowledge.service;

import cn.com.qytx.oa.knowledge.domain.KnowledgeCollect;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.BaseService;

/**
 * 功能: 知识库收藏的实现类接口
 * 版本: 1.0
 * 开发人员: 马恺
 * 创建日期: 2014-11-25
 * 修改日期: 2014-11-25
 * 修改列表:
 *
 */
public interface IKnowledgeCollect extends BaseService<KnowledgeCollect>{

	/**
	 * 分页查询收藏的知识库
	 * @param page
	 * @param kc
	 * @return
	 */
	Page<KnowledgeCollect> findByPage(Pageable page,KnowledgeCollect kc);
	
	/**
	 * 删除收藏的知识
	 * @param id
	 */
	void changeStateById(Integer id);
	
	/**
	 * 更新
	 * @param kc
	 */
	void saveOrUpdateKC(KnowledgeCollect kc);
	
	/**
	 * 根据知识库ID和坐席id查询收集信息
	 * @param knowledgeId
	 * @param seatId
	 * @return
	 */
	KnowledgeCollect findOne(Integer knowledgeId,Integer seatId);
}
