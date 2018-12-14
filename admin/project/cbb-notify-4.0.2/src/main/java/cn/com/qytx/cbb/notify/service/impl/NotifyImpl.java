package cn.com.qytx.cbb.notify.service.impl;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.httpclient.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.affairs.domain.ReminderInfo;
import cn.com.qytx.cbb.affairs.service.IAffairsBody;
import cn.com.qytx.cbb.dict.service.IDict;
import cn.com.qytx.cbb.notify.dao.NotifyDao;
import cn.com.qytx.cbb.notify.dao.PlatformParameterDao;
import cn.com.qytx.cbb.notify.domain.Notify;
import cn.com.qytx.cbb.notify.service.INotify;
import cn.com.qytx.cbb.notify.service.IPlatformParameter;
import cn.com.qytx.cbb.notify.vo.TbColumnSetting;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;
import cn.com.qytx.platform.org.dao.UserDao;
import cn.com.qytx.platform.org.domain.UserInfo;

@Service("notifyService")
@Transactional
public class NotifyImpl extends BaseServiceImpl<Notify> implements INotify {
	@Resource(name = "notifyDao")
	private NotifyDao notifyDao;
	@Resource(name="platformParameterDao")
	private PlatformParameterDao parmsDao;
	@Autowired
	private IPlatformParameter parameter;
	@Autowired
	private UserDao<UserInfo> userDao;
	@Resource(name="dictService")
	private IDict dictService;
	/**
     * 事务提醒主体Impl
     */
	@Autowired
    IAffairsBody affairsBodyImpl;

	@Transactional(readOnly = true)
	public Page<Notify> viewList(Pageable pageable,Integer userId, int notifyType, String searchWord, Integer columnId,Integer isShowOut) {
		Page<Notify> page = notifyDao.viewList(pageable, notifyType, searchWord,columnId,userId,isShowOut);
		return page;
	}
	@Transactional(readOnly = true)
	public Integer countOfNotReadNotify(Integer userId,Integer columnId) {
		return notifyDao.countOfNotReadNotify(userId,columnId);

	}

	@Transactional(readOnly = true)
	public Page<Notify> list(Pageable pageable, Integer notifyType,String subject, Date beginDate, Date endDate, UserInfo userInfo,Integer columnId,Integer status) {
		return notifyDao.list(pageable, notifyType, subject, beginDate,endDate, userInfo, columnId,status);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void save(Notify notify) {
		if(notify.getStatus()==2){  //如果不需要审批，直接发布。设置发布时间是当前
			notify.setApproveDate(new Timestamp(System.currentTimeMillis()));
			notifyDao.saveOrUpdate(notify);
			sendReminder(notify);
		}else {
			notifyDao.saveOrUpdate(notify);
		}
		syncMobile(notify); //同步数据
	}
	@Transactional(propagation=Propagation.REQUIRED)
	public void update(Notify notify){
		if(notify.getStatus()==2){  //如果不需要审批，直接发布。设置发布时间是当前
			notify.setApproveDate(new Timestamp(System.currentTimeMillis()));
			notifyDao.saveOrUpdate(notify);
			sendReminder(notify);
		}else {
			notifyDao.saveOrUpdate(notify);
		}
//		myApplyService.delMyStarted(String.valueOf(notify.getId()),ModuleCode.NOTIFY); //删除数据
		Notify reNotify = notifyDao.findOne(notify.getId());
		syncMobile(reNotify); //同步数据
	}
	@Transactional(readOnly = true)
	public List<Notify> getTopFiveNotifys(Integer typeId){
		return notifyDao.getTopFiveNotifys(typeId);
	}
	
	/**
	 * 功能：审批列表
	 */
	public Page<Notify> approveList(Pageable pageable,Integer userId, Integer notifyType, String subject,Date beginDate, Date endDate,Integer columnId,Integer status){
		TbColumnSetting tbColumnSetting=(TbColumnSetting) parmsDao.findEntityByInstenceId(columnId);
		int isDataFilter=0;
		if (null!=tbColumnSetting&&tbColumnSetting.getIsDataFilter()==1) {
			isDataFilter=1;
		}
		return notifyDao.approveList(pageable,userId, notifyType, subject, beginDate,  endDate, columnId,status,isDataFilter);
	}
	////状态  0草稿，1:审批中2:通过（已发布）  3:未通过  4:终止  
	public void approveNotify(Notify notify,UserInfo loginUser){
		notifyDao.saveOrUpdate(notify);
//		if(notify.getStatus()==3){
//			myApplyService.updateState(ModuleCode.NOTIFY,String.valueOf(notify.getId()),"未通过");
//			myApplyService.approve(ModuleCode.NOTIFY,String.valueOf(notify.getId()),loginUser,"不同意","不同意","");
//		}else if(notify.getStatus()==2){
//			myApplyService.updateState(ModuleCode.NOTIFY,String.valueOf(notify.getId()),"通过");
//			myApplyService.approve(ModuleCode.NOTIFY,String.valueOf(notify.getId()),loginUser,"同意","同意","");
//		}
	}
	@Transactional(propagation = Propagation.REQUIRED)
	public void delByIds(String ids) {
		notifyDao.delByIds(ids);
//		myApplyService.delMyStarted(ids,ModuleCode.NOTIFY); //删除数据
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateTop(String ids, Integer isTop) {
		notifyDao.updateTop(ids, isTop);
	}
	@Transactional(propagation = Propagation.REQUIRED)
	public void draw(Integer id){
		notifyDao.draw(id);
//		myApplyService.delMyStarted(String.valueOf(id),ModuleCode.NOTIFY);
		syncMobile(notifyDao.findOne(id));
	}
	@Transactional(propagation = Propagation.REQUIRED)
	public void stop(Integer id){
		notifyDao.stop(id);
//		myApplyService.updateState(ModuleCode.NOTIFY,String.valueOf(id),"终止");
	}
	@Transactional(propagation = Propagation.REQUIRED)
	public void effect(Integer id, String startDateStr, String endDateStr){
		notifyDao.effect(id,startDateStr,endDateStr);
//		myApplyService.updateState(ModuleCode.NOTIFY,String.valueOf(id),"通过");
	}
	
	@Transactional(readOnly = true)
	public Page<Map<String, Object>> clmViewList(Pageable pageable,Integer userId, int notifyType, String searchWord, Integer columnId,String groupId) {
		return notifyDao.clmViewList(pageable, userId, notifyType, searchWord,columnId, groupId);
	}
	
	/**
	 * 增加或者修改时，同步手机端数据
	 * @param notify
	 */
	private void syncMobile(Notify notify){
		//手机端审批公告暂时关闭2014-09-16
		/*PlatformParameter platformParameter = parameter.findByInstenceId(notify.getColumnId());
		switch (notify.getStatus()) {
		case 0:
			myApplyService.createProcess(notify.getCreateUser(),platformParameter.getModuleName(),notify.getSubject(), String.valueOf(notify.getId()),ModuleCode.NOTIFY);
			myApplyService.updateState(ModuleCode.NOTIFY,String.valueOf(notify.getId()), "草稿");
			break;
		case 1:
			myApplyService.createProcess(notify.getCreateUser(),platformParameter.getModuleName(),notify.getSubject(), String.valueOf(notify.getId()),ModuleCode.NOTIFY);
			myApplyService.addApprover(platformParameter.getModuleName(),notify.getSubject(),String.valueOf(notify.getId()),null,ModuleCode.NOTIFY);
			myApplyService.updateState(ModuleCode.NOTIFY,String.valueOf(notify.getId()), "审批中");
			break;
		case 2:
			myApplyService.createProcess(notify.getCreateUser(),platformParameter.getModuleName(),notify.getSubject(), String.valueOf(notify.getId()),ModuleCode.NOTIFY);
			myApplyService.updateState(ModuleCode.NOTIFY,String.valueOf(notify.getId()), "通过");
		default:
			break;
		}*/
	}
	public String sendReminder(Notify notify){
		ReminderInfo reminderInfo = new ReminderInfo();
		reminderInfo.setSendType(notify.getSendType());//"0_1_1"
        reminderInfo.setAffairContent("您有一条来自"+notify.getCreateUser().getUserName()+"的公告,标题:"+notify.getSubject());
        reminderInfo.setAffairUrl("logined/notify/view.jsp?notifyId="+notify.getId()+"&columnId="+notify.getColumnId()+"&returnType=1");
        reminderInfo.setToids(notify.getPublishScopeUserIds()+"");
        reminderInfo.setFromUserInfo(notify.getCreateUser());
        reminderInfo.setModuleName("公告管理");
        reminderInfo.setSmsContent("您有一条来自"+notify.getCreateUser().getUserName()+"的公告,标题:"+notify.getSubject());
        reminderInfo.setPushContent("您有一条来自"+notify.getCreateUser().getUserName()+"的公告,标题:"+notify.getSubject());
        reminderInfo.setRecordId(notify.getId()+"");
        reminderInfo.setPushSubjcet(notify.getSubject());
        reminderInfo.setModuleName("通知公告");
        reminderInfo.setAttmentIds(notify.getAttment());
        reminderInfo.setSendTime(new Timestamp(System.currentTimeMillis()));
        try {
			affairsBodyImpl.sendReminder(reminderInfo);
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public Map<String,Object> viewNotify(Integer notifyId){
		return notifyDao.viewNotify(notifyId);
	}
}
