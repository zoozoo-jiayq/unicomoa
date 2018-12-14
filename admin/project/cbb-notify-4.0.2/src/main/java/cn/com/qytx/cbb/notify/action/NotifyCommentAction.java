package cn.com.qytx.cbb.notify.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import cn.com.qytx.cbb.file.config.FilePathConfig;
import cn.com.qytx.cbb.notify.domain.NotifyComment;
import cn.com.qytx.cbb.notify.service.INotifyComment;
import cn.com.qytx.cbb.notify.service.IPlatformParameter;
import cn.com.qytx.cbb.notify.vo.TbColumnSetting;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.utils.JsonUtils;

/**
 * 功能:评论
 */
public class NotifyCommentAction extends BaseActionSupport{
	
	@Resource(name="platformParameterService")
	private IPlatformParameter platformParameterService;
	@Resource(name="filePathConfig")
	private FilePathConfig filePathConfig;
	@Resource(name="notifyCommentService")
	private INotifyComment notifyCommentService;
	
	private Integer columnId;
	private Integer notifyId;
	private TbColumnSetting tbColumnSetting;
	private NotifyComment notifyComment;
	private Integer id;
	/**
	 * 功能：查看设置
	 * @return
	 */
	public String setting(){
		tbColumnSetting = (TbColumnSetting) platformParameterService.findEntityByInstenceId(columnId);
		Map<String,Object> returnMap = new HashMap<String, Object>();
		returnMap.put("isComment",tbColumnSetting.getIsComment());  //是否允许评论
		if(tbColumnSetting.getIsComment()==1){
			if(getLoginUser().getPhone()!=null && !"".equals(getLoginUser().getPhone())){
				returnMap.put("photo",filePathConfig.getFileViewPath()+"/"+getLoginUser().getPhoto());  //用户照片
			}else{
				returnMap.put("photo",""); //用户照片
			}
			returnMap.put("username",getLoginUser().getUserName());
			returnMap.put("counting",notifyCommentService.countComment(notifyId));
		}
		ajax(JsonUtils.generJsonString(returnMap));
		return null;
	}
	/**
	 * 功能：保存
	 * @return
	 */
	public String save(){
		notifyComment.setCreateUserInfo(getLoginUser());
		notifyComment.setCompanyId(getLoginUser().getCompanyId());
		notifyCommentService.saveOrUpdate(notifyComment);
		ajax(true);
		return null;
	}
	/**
	 * 功能：删除
	 * @return
	 */
	public String del(){
		notifyCommentService.delete(id,true);
		ajax(true);
		return null;
	}
	public Integer getColumnId() {
		return columnId;
	}

	public void setColumnId(Integer columnId) {
		this.columnId = columnId;
	}

	public Integer getNotifyId() {
		return notifyId;
	}

	public void setNotifyId(Integer notifyId) {
		this.notifyId = notifyId;
	}

	public TbColumnSetting getTbColumnSetting() {
		return tbColumnSetting;
	}

	public void setTbColumnSetting(TbColumnSetting tbColumnSetting) {
		this.tbColumnSetting = tbColumnSetting;
	}
	public NotifyComment getNotifyComment() {
		return notifyComment;
	}
	public void setNotifyComment(NotifyComment notifyComment) {
		this.notifyComment = notifyComment;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
}
