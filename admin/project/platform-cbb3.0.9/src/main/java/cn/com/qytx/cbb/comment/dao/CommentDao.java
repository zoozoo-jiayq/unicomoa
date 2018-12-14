package cn.com.qytx.cbb.comment.dao;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import cn.com.qytx.cbb.comment.domain.Comment;
import cn.com.qytx.platform.base.dao.BaseDao;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.query.Sort;
import cn.com.qytx.platform.base.query.Sort.Direction;

@Repository("commentDao")
public class CommentDao extends BaseDao<Comment,Integer>{
      
	public Page<Comment> findPager(Pageable pageable,String instanceId,String type){
		return findAll("instanceId=?1 and type=?2", pageable, instanceId,type);
	}
	
	public Integer count(String instanceId,String type){
		return count("instanceId=?1 and type=?2", instanceId,type);
	}
	
	public Page<Comment> findPager(Pageable pageable,String instanceId,String type,String beginDate,String endDate,String content){
		StringBuilder hql = new StringBuilder();
		if (StringUtils.isNotBlank(instanceId)) {
			hql.append(" instanceId=").append(instanceId);
		}
		if (StringUtils.isNotBlank(type)) {
			hql.append(" type=").append(type);
		}
		if (StringUtils.isNotBlank(beginDate)) {
			hql.append(" createDate >= '").append(beginDate).append(" 00:00:00'");
		}
		if (StringUtils.isNotBlank(endDate)) {
			hql.append(" createDate <= '").append(endDate).append(" 23:59:59'");
		}
		if (StringUtils.isNotBlank(content)) {
			hql.append(" content like '%").append(content).append("%'");
		}
		return findAll(hql.toString(), pageable);
	}
	
	public Integer approveComment(String ids, Integer statue){
		StringBuilder hql = new StringBuilder(" update Comment set statue =");
		hql.append(statue).append(" where id in (").append(ids).append(")");
		return entityManager.createQuery(hql.toString()).executeUpdate();
	}
	
	public Page<Comment> findPagerMyComment(Pageable pageable,String type){
		return findAll("type=?1", pageable, type);
	}

	public List<Comment> findList(String instanceId, String type) {
		String hql = "instanceId = ?1 and type = ?2 and parent is null ";
		return super.findAll(hql,new Sort(Direction.DESC, "createDate"),instanceId,type);
	}

	public List<Comment> getBusinessComments(String instanceId, String type,Integer maxSize) {
		String hql = "from Comment where instanceId = ?1 and type = ?2 and parent is null order by createDate desc ";
		return entityManager.createQuery(hql).setParameter(1,instanceId).setParameter(2,type).setMaxResults(maxSize).getResultList();
	}
	
	public Integer getCommentCount(String instanceId, String type){
    	String condition=" instanceId = ? and type = ? and parent.id is null";
    	return this.count(condition, instanceId,type);
    }
}



