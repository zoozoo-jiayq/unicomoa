package cn.com.qytx.cbb.comment.service.impl;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.comment.dao.CommentDao;
import cn.com.qytx.cbb.comment.domain.Comment;
import cn.com.qytx.cbb.comment.service.IComment;
import cn.com.qytx.cbb.systemSet.dao.SysConfigDao;
import cn.com.qytx.cbb.systemSet.domain.SysConfig;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;

@Service("commentSerivce")
@Transactional
public class CommentImpl extends BaseServiceImpl<Comment> implements IComment{
	
	@Resource(name="commentDao")
	private CommentDao commentDao;
	@Autowired
	private SysConfigDao sysConfigDao;
	
	@Transactional(readOnly=true)
	public Page<Comment> findPager(Pageable pageable, String instanceId,String type) {
		return commentDao.findPager(pageable, instanceId, type);
	}
	
	@Transactional(readOnly=true)
	public Integer count(String instanceId, String type) {
		return commentDao.count(instanceId, type);
	}
	
	@Transactional(readOnly=true)
	public Page<Comment> findPager(Pageable pageable, String instanceId,
			String type, String beginDate, String endDate, String content) {
		return commentDao.findPager(pageable, instanceId, type, beginDate, endDate, content);
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public Integer approveComment(String ids, Integer statue) {
		return commentDao.approveComment(ids, statue);
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void delComment(String ids) {
		super.deleteByIds(true,(Arrays.asList(ids.split(","))));
	}
	
	@Transactional(readOnly=true)
	public Page<Comment> findPagerMyComment(Pageable pageable, String type) {
		return commentDao.findPagerMyComment(pageable,type);
	}
	@Transactional(readOnly=true)
	public List<Comment> findList(String instanceId,String type){
		return commentDao.findList(instanceId,type);
	}
	@Transactional(propagation=Propagation.REQUIRED)
	public Comment saveOrUpdate(Comment comment) {
		SysConfig sysConfig = sysConfigDao.getSysConfig("COMMENT_"+comment.getType()+"_APPROVE"); 
		if(sysConfig!=null && sysConfig.getConfigKey().equals("1")){
			comment.setStatue(0);
		}else{
			comment.setStatue(1);
		}
		return commentDao.saveOrUpdate(comment);
	}
	@Transactional(readOnly=true)
	public List<Comment> getBusinessComments(String instanceId,String type,Integer maxSize){
		return commentDao.getBusinessComments(instanceId,type,maxSize);
	}
}
