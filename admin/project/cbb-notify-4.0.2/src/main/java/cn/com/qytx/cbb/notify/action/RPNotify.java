package cn.com.qytx.cbb.notify.action;

import javax.annotation.Resource;

import cn.com.qytx.cbb.notify.service.IWapNotify;
import cn.com.qytx.platform.base.action.BaseActionSupport;

/**
 * 功能: 统计公告未读数量 
 * 版本: 1.0
 * 开发人员: zyf
 * 创建日期: 2015年3月17日
 * 修改日期: 2015年3月17日
 * 修改列表:
 */
public class RPNotify extends BaseActionSupport {
	
	/**
	 * 描述含义
	 */
	private static final long serialVersionUID = 1676461315713291855L;
	
	@Resource
	private IWapNotify wapNotifyService;
	
	/**
	 * 用户id 
	 */
	private Integer userId;
	
	/**
	 * 单位id
	 */
	private Integer companyId;
	
	/**
	 * 公告模块id
	 */
	private Integer columnId;

	/**
	 * 功能：统计公告未读数量 
	 */
	public void getNotifyUnReadCount(){
		if(userId==null || companyId == null){
			LOGGER.info("用户id、公司id不能为空");
			ajax(0);
			return;
		}
		if(columnId == null){
			columnId = -1;
		}
		int count = wapNotifyService.getNotifyUnReadCount(columnId, userId, companyId);
		ajax(count);
	}
	
	/**
	 * 功能：统计公告待审批数量 
	 */
	public void getNotifyApproveCount(){
		if(userId==null || companyId == null){
			LOGGER.info("用户id、公司id不能为空");
			ajax(0);
			return;
		}
		if(columnId == null){
			columnId = -1;
		}
		int count = wapNotifyService.getNotifyApproveCount(columnId, userId, companyId);
		ajax(count);
	}

	public IWapNotify getWapNotifyService() {
		return wapNotifyService;
	}

	public void setWapNotifyService(IWapNotify wapNotifyService) {
		this.wapNotifyService = wapNotifyService;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getColumnId() {
		return columnId;
	}

	public void setColumnId(Integer columnId) {
		this.columnId = columnId;
	}
	
}
