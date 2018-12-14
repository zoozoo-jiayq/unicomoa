package cn.com.qytx.cbb.comment.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cn.com.qytx.cbb.comment.domain.Comment;
import cn.com.qytx.cbb.comment.service.IComment;
import cn.com.qytx.cbb.util.JsonUtils;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.base.application.TransportUser;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Sort;
import cn.com.qytx.platform.base.query.Sort.Direction;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IUser;
import cn.com.qytx.platform.utils.datetime.DateTimeUtil;

public class WapCommentAction extends BaseActionSupport{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Resource(name = "commentSerivce")
	private IComment commentService;
	@Resource(name = "userService")
	private IUser userService;
	
	private Comment comment;
	private Integer userId;
	private String instanceId;
	private String type;
	private Integer maxSize = 2;
	private Integer id;
	
	public void save(){
		if(comment != null){
			UserInfo userInfo = TransportUser.get();
			if(comment.getCreateUser().getUserId()!=null){
				userInfo = userService.findOne(comment.getCreateUser().getUserId());
			}
			comment.setCompanyId(userInfo.getCompanyId());
		}
		commentService.saveOrUpdate(comment);
		ajax("100||0");
	}
	public void two(){
		List<Comment> list = commentService.getBusinessComments(instanceId, type, maxSize);
		List<Map<String,Object>> returnList = new ArrayList<Map<String,Object>>();
		for(Comment comment:list){
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("id",comment.getId());
			map.put("content",comment.getContent());
			map.put("createDate",DateTimeUtil.dateToString(comment.getCreateDate(),"yyyy-MM-dd HH:mm:ss"));
			map.put("username",comment.getCreateUser().getUserName());
			returnList.add(map);
		}
		ajax("100||"+JsonUtils.generJsonString(returnList));
	}
	public void list(){
		UserInfo loginUser = userService.findOne(userId);
		Sort sort = new Sort(new Sort.Order(Direction.DESC,"createDate"));
		Page<Comment> pageInfo = commentService.findPager(getPageable(sort),instanceId,type);
		List<Comment> list = pageInfo.getContent();
		List<Map<String,Object>> returnList = new ArrayList<Map<String,Object>>();
		for(Comment comment:list){
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("id",comment.getId());
			map.put("content",comment.getContent());
			map.put("createDate",DateTimeUtil.dateToString(comment.getCreateDate(),"yyyy-MM-dd HH:mm:ss"));
			map.put("username",comment.getCreateUser().getUserName());
			if(loginUser.getUserId() == comment.getCreateUser().getUserId() || comment.getCreateUser().getIsDefault()==0){
				map.put("isDelete",1);
			}else{
				map.put("isDelete",0);
			}
			returnList.add(map);
		}
		ajax("100||"+JsonUtils.generJsonString(returnList));
	}
	public void del(){
		commentService.delete(id,true);
		ajax("100||0");
	}
	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getMaxSize() {
		return maxSize;
	}
	public void setMaxSize(Integer maxSize) {
		this.maxSize = maxSize;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
}
