package cn.com.qytx.cbb.comment.action;

import javax.annotation.Resource;

import cn.com.qytx.cbb.comment.domain.Comment;
import cn.com.qytx.cbb.comment.service.IComment;
import cn.com.qytx.platform.base.action.BaseActionSupport;

public class CommentAction extends BaseActionSupport{
		
	@Resource(name="commentSerivce")
	private IComment commentService;
	
	private String instanceId;
	private String type;
	private Comment comment;
	
	public String save(){
		comment.setCreateUser(getLoginUser());
		commentService.saveOrUpdate(comment);
		ajax("{\"success\":true}");
		return null;
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
	public Comment getComment() {
		return comment;
	}
	public void setComment(Comment comment) {
		this.comment = comment;
	}
	
}
