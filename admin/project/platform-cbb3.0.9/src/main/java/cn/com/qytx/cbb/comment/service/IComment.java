package cn.com.qytx.cbb.comment.service;

import java.util.List;

import cn.com.qytx.cbb.comment.domain.Comment;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.BaseService;

/**
 * 
 * @author liyanlei
 *
 */
public interface IComment extends BaseService<Comment>{
		
	/**
	 * 分页查询业务
	 * @param pageable
	 * @param instanceId
	 * @param type
	 * @return
	 */
	public Page<Comment> findPager(Pageable pageable,String instanceId,String type);
	
	/**
	 * 统计评论数
	 * @param instanceId
	 * @param type
	 * @return
	 */
	public Integer count(String instanceId,String type);
	
	/**
	 * PC端列表展示
	 * @param pageable
	 * @param instanceId
	 * @param type
	 * @param beginDate
	 * @param endDate
	 * @param content
	 * @return
	 */
	public Page<Comment> findPager(Pageable pageable,String instanceId,String type,String beginDate,String endDate,String content);
	
	/**
	 * 批量审批
	 * @return
	 */
	public Integer approveComment(String ids,Integer statue);
	
	/**
	 * 通过多个ID删除对象
	 * @param ids
	 */
	public void delComment(String ids);
	
	/**
	 * 我的评论分页查询
	 * @param pageable
	 * @param type
	 * @return
	 */
	public Page<Comment> findPagerMyComment(Pageable pageable,String type);
	
	/**
	 * 得到所有评论
	 * 功能：
	 * @param instanceId
	 * @param type
	 * @return
	 */
	public List<Comment> findList(String instanceId,String type);
	
	/**
	 * 单个评论的前几条列表
	 * @param instanceId
	 * @param type
	 * @param maxSize
	 * @return
	 */
	public List<Comment> getBusinessComments(String instanceId,String type,Integer maxSize);
	
}
