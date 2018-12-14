package cn.com.qytx.cbb.notify.service;


import cn.com.qytx.cbb.notify.domain.Notify;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.BaseService;

public interface IWapNotify extends BaseService<Notify>{
	
	/**
	 * 功能：公告列表
	 * @param pageable
	 * @param subject
	 * @param userId
	 * @param notifyType
	 * @param columnId
	 * @return
	 */
	public Page<Notify> viewList(Pageable pageable,String subject,int userId, int notifyType,int columnId,Integer readStatus);
	
	/**
	 * 功能：部门专栏公告列表
	 * @param pageable
	 * @param subject
	 * @param userId
	 * @param notifyType
	 * @param columnId
	 * @param groupId
	 * @return
	 */
	public Page<Notify> clmViewList(Pageable pageable,String subject,int userId, int notifyType,int columnId,String groupId);
	
	/**
	 * 得到最新的发布给userId用户的通知公告
	 * @param columnId
	 * @return
	 */
	public Notify getLastNotify(Integer columnId,Integer notifyType,Integer userId);
	
	/**
	 * 得到未读的通知公告
	 * @param columnId
	 * @param notifyType
	 * @param userId
	 * @return
	 */
	public boolean getUnReadNotifyCount(Integer columnId,Integer notifyType,Integer userId);
	
	/**
	 * 通知公告审批接口
	 * @param notifyId
	 * @param userId
	 */
	public void mobileApprove(Integer notifyId, int userId,Integer status,String reason);
	
	/**
	 * 功能：获得公告未读数量
	 * @param columnId
	 * @param userId
	 * @param companyId
	 * @return
	 */
	public int getNotifyUnReadCount(int columnId,int userId,int companyId);
	
	/**
	 * 获得公告待审批数量
	 */
	public int getNotifyApproveCount(int columnId,int userId,int companyId);

}
