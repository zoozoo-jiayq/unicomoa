package cn.com.qytx.cbb.notify.service.impl;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.notify.dao.WapNotifyDao;
import cn.com.qytx.cbb.notify.domain.Notify;
import cn.com.qytx.cbb.notify.service.IWapNotify;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;
import cn.com.qytx.platform.org.dao.UserDao;
import cn.com.qytx.platform.org.domain.UserInfo;

@Transactional
@Service("wapNotifyService")
public class WapNotifyImpl extends BaseServiceImpl<Notify> implements IWapNotify{

	@Resource(name="wapNotifyDao")
	private WapNotifyDao wapNotifyDao;
	@Resource(name="userDao")
	private UserDao<UserInfo> userDao;
	
	@Transactional(readOnly=true)
	public Page<Notify> viewList(Pageable pageable,String subject,int userId, int notifyType,int columnId,Integer readStatus) {
		return wapNotifyDao.viewList(pageable,subject,userId,notifyType,columnId,readStatus);
	}
	@Transactional(readOnly=true)
	public Page<Notify> clmViewList(Pageable pageable,String subject,int userId, int notifyType,int columnId ,String groupId) {
		return wapNotifyDao.clmViewList(pageable,subject,userId,notifyType,columnId,groupId);
	}
	@Transactional(readOnly=true)
	public Notify getLastNotify(Integer columnId,Integer notifyType,Integer userId){
		return wapNotifyDao.getLastNotify(columnId,notifyType,userId);
	}
	@Transactional(readOnly=true)
	public boolean getUnReadNotifyCount(Integer columnId,Integer notifyType,Integer userId){
		return wapNotifyDao.getUnReadNotifyCount(columnId,notifyType,userId);
	}
	@Transactional(propagation=Propagation.REQUIRED)
	public void mobileApprove(Integer notifyId, int userId,Integer status,String reason){
		Notify notify = wapNotifyDao.findOne(notifyId);
		notify.setAuditer(userId);
		notify.setStatus(status);
		notify.setReason(reason);
		notify.setApproveDate(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		wapNotifyDao.saveOrUpdate(notify);
	}
	
	
	/**
	 * 功能：获得公告未读数量
	 * @param columnId
	 * @param userId
	 * @param companyId
	 * @return
	 */
	@Transactional(readOnly=true)
	public int getNotifyUnReadCount(int columnId,int userId,int companyId){
		return wapNotifyDao.getNotifyUnReadCount(columnId, userId, companyId);
	}
	
	/**
	 * 获得公告待审批数量
	 */
	@Transactional(readOnly=true)
	public int getNotifyApproveCount(int columnId,int userId,int companyId){
		return wapNotifyDao.getNotifyApproveCount(columnId, userId, companyId);
	}
}
