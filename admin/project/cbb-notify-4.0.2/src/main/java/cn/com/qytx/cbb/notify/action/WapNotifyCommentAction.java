package cn.com.qytx.cbb.notify.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cn.com.qytx.cbb.notify.domain.NotifyComment;
import cn.com.qytx.cbb.notify.service.IWapNotify;
import cn.com.qytx.cbb.notify.service.IWapNotifyComment;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.base.application.TransportUser;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Sort;
import cn.com.qytx.platform.base.query.Sort.Direction;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IUser;
import cn.com.qytx.platform.utils.JsonUtils;
import cn.com.qytx.platform.utils.datetime.DateTimeUtil;

/**
 * 功能:手机端评论（列表、保存、删除）
 */
public class WapNotifyCommentAction extends BaseActionSupport{
	
	@Resource(name="wapNotifyCommentImpl")
	private IWapNotifyComment wapNotifyCommentService;
	@Resource(name="wapNotifyService")
	private IWapNotify wapNotifyService;
	@Resource(name="userService")
	private IUser userService;
	
	private Integer notifyId;
	private Integer userId;
	private String comment;
	private Integer id;
	
	/**
	 * 功能：评论列表
	 * @return
	 */
	public String commentList(){
		//order by createDate desc
		Sort sort = new Sort(new Sort.Order(Direction.DESC,"createDate"));
		Page<NotifyComment> pageInfo = wapNotifyCommentService.commentList(getPageable(sort),notifyId);
		List<Map<String,Object>> returnList = new ArrayList<Map<String,Object>>();
		UserInfo currUserinfo = userService.findOne(userId);
		List<NotifyComment> list = pageInfo.getContent();
		if(list!=null && list.size() > 0){
			for(NotifyComment notifyComment:list){
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("id",notifyComment.getId());
				map.put("createDate",DateTimeUtil.dateToString(notifyComment.getCreateDate(),"yyyy-MM-dd HH:mm:ss"));
				map.put("comment",notifyComment.getComment());
				map.put("username", notifyComment.getCreateUserInfo().getUserName());
				if(currUserinfo.getIsDefault() == 0 || userId.equals(notifyComment.getCreateUserInfo().getUserId())){
					map.put("isDelete",1);
				}else{
					map.put("isDelete",0);
				}
				returnList.add(map);
			}
			Map<String,Object> returnMap = new HashMap<String, Object>();
			returnMap.put("mapList",returnList);
			ajax("100||"+JsonUtils.generJsonString(returnMap));
			
		}else{
			ajax("100||{\"mapList\":[]}");
		}
		return null;
	}
	/**
	 * 功能：保存评论
	 * @return
	 */
	public String save(){
		NotifyComment notifyComment = new NotifyComment();
		notifyComment.setNotify(wapNotifyService.findOne(notifyId));
		notifyComment.setCreateUserInfo(userService.findOne(userId));
		notifyComment.setComment(comment);
		UserInfo userInfo = TransportUser.get();
		notifyComment.setCompanyId(userInfo.getCompanyId());
		wapNotifyCommentService.saveOrUpdate(notifyComment);
		ajax("100||0");
		return null;
	}
	
	/**
	 * 功能：删除评论
	 * @return
	 */
	public String del(){
		wapNotifyCommentService.delete(id,true);
		ajax("100||0");
		return null;
	}
	public Integer getNotifyId() {
		return notifyId;
	}

	public void setNotifyId(Integer notifyId) {
		this.notifyId = notifyId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
}
