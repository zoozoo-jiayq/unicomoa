package cn.com.qytx.cbb.baseworkflow.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.baseworkflow.dao.BaseWorkflowDao;
import cn.com.qytx.cbb.baseworkflow.domain.BaseWorkflow;
import cn.com.qytx.cbb.baseworkflow.model.ViewModule;
import cn.com.qytx.cbb.baseworkflow.service.BMyProcessed;
import cn.com.qytx.cbb.baseworkflow.service.BMyStarted;
import cn.com.qytx.cbb.baseworkflow.service.BMyWaitApprove;
import cn.com.qytx.cbb.baseworkflow.service.BaseWorkflowService;
import cn.com.qytx.cbb.baseworkflow.service.InitBaseWorkflowConfig;
import cn.com.qytx.cbb.jbpmApp.action.mobile.jsonModel.ApproveHistory;
import cn.com.qytx.cbb.myapply.domain.MyProcessed;
import cn.com.qytx.cbb.myapply.domain.MyStarted;
import cn.com.qytx.cbb.myapply.domain.MyWaitProcess;
import cn.com.qytx.cbb.myapply.service.IMyProcessed;
import cn.com.qytx.cbb.myapply.service.IMyStarted;
import cn.com.qytx.cbb.myapply.service.IMyWaitProcess;
import cn.com.qytx.cbb.myapply.service.MyApplyService;
import cn.com.qytx.cbb.myapply.service.MyApplyService.ModuleCode;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.PageImpl;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IUser;
import cn.com.qytx.platform.utils.TimeUtil;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

/**
 * 功能  固定流程实现
 * <br/>版本  1.0
 * <br/>开发人员  zyf
 * <br/>创建日期  2016年1月6日
 * <br/>修改日期  2016年1月6日
 * <br/>修改列表
 */
@Service
@Transactional
public class BaseWorkflowServiceImpl extends BaseServiceImpl<BaseWorkflow> implements
		BaseWorkflowService {

	@Resource
	private BaseWorkflowDao baseWorkflowDao;
	@Resource
	private IUser userService;
	@Resource
	private MyApplyService myApplyService;
	@Resource
	private IMyStarted myStartedService;
	@Resource
	private IMyWaitProcess myWaitProcessService;
	@Resource
	private IMyProcessed myProcessedService;
	
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	//审批人的存储结构
	public class ApproveUser{
		//用户ID
		private String userId;
		//用户状态，默认为normal,turn为转交
		private String state = "normal";
		
		private String userName;
		
		private String photoUrl;
		
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public String getPhotoUrl() {
			return photoUrl;
		}
		public void setPhotoUrl(String photoUrl) {
			this.photoUrl = photoUrl;
		}
	
		public ApproveUser(String userId, String state, String userName,
				String photoUrl) {
			super();
			this.userId = userId;
			this.state = state;
			this.userName = userName;
			this.photoUrl = photoUrl;
		}
		public String getUserId() {
			return userId;
		}
		public void setUserId(String userId) {
			this.userId = userId;
		}
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
	}
	
	/**
	 * 获取最大的流程实例Id
	 * @return
	 */
	@Override
	public String getLastBaseworkflowInstanceId() {
		return baseWorkflowDao.getLastBaseWorkflow();
	}

	
	/**
	 * 发起流程
	 * @param userId 发起人
	 * @param formData 表单数据
	 * @param userIds 审批人列表，多个人之间用逗号隔开
	 * @param code 类型code
	 */
	@Override
	public String start(int userId, String formData, String userInfos, String code) {
		
		//保存流程基本信息
		UserInfo u = userService.findOne(userId);
		BaseWorkflow bwf = new BaseWorkflow();
		bwf.setCode(code);
		bwf.setCompanyId(u.getCompanyId());
		bwf.setFormData(formData);
		
		Gson gson = new Gson();
		List<ApproveUser> list = new ArrayList<ApproveUser>();
		String lastUrl = "";
		if(u.getPhoto()!=null && !u.getPhoto().equals("")){
			lastUrl = InitBaseWorkflowConfig.getInstance().getBaseWorkflowConfig().get("photoUrl")+u.getPhoto();
		}
		list.add(new ApproveUser(userId+"", "normal", u.getUserName(),lastUrl ));
		List<ApproveUser> others = gson.fromJson(userInfos, new TypeToken<List<ApproveUser>>() {}.getType()); 
		list.addAll(others);
		
		String r = gson.toJson(list);
		bwf.setUserIds(r);
		
		String title = u.getUserName()+"的"+InitBaseWorkflowConfig.getInstance().getBaseWorkflowConfig().get(code)+"申请";
		String instanceId = code+"."+InitBaseWorkflowConfig.getInstance().getNextInstanceIdSuf();
		bwf.setInstanceId(instanceId);
		baseWorkflowDao.saveOrUpdate(bwf);
		
		String categoryName = InitBaseWorkflowConfig.getInstance().getBaseWorkflowConfig().get(code);
		
		//保存发起流程记录
		myApplyService.createProcess(u, categoryName, title, instanceId, ModuleCode.BASEWORKFLOW);
		//保存发起人的待审批记录
		myApplyService.addApprover(categoryName, title, instanceId, userId+"", ModuleCode.BASEWORKFLOW);
		
		//保存下一个人的审批记录
		if(others.size()>0){
			ApproveUser au = others.get(0);
			myApplyService.approve(ModuleCode.BASEWORKFLOW, instanceId, u, "", "审批中("+au.getUserName()+")","-1");
			myApplyService.addApprover(categoryName, title, instanceId, au.getUserId(), ModuleCode.BASEWORKFLOW);
		}
		return instanceId;
	}

	/**
	 * 审批
	 * @param userId  审批人
	 * @param instanceId 流程实例ID
	 * @param formData 审批数据
	 * @param approveResult 审批结果
	 */
	@Override
	public void approve(int userId, String instanceId, String advice,
			String approveResult) {
		UserInfo approver = userService.findOne(userId);
		BaseWorkflow bwf = baseWorkflowDao.findByInstanceId(instanceId);
		UserInfo nextUser = null;
		
		//判断审批状态
		String state = "";
		//审批通过
		String userIds = bwf.getUserIds();
		List<ApproveUser> ids = new Gson().fromJson(userIds, new TypeToken<List<ApproveUser>>() {}.getType());
		if(approveResult.equals(ApproveHistory.APPROVED) || approveResult.equals(ApproveHistory.TURN)){
			ApproveUser nextUsert = getNext(ids, userId+""); 
			if(nextUsert == null ){
				state = "通过(完成)";
			}else{
				nextUser = userService.findOne(Integer.parseInt(nextUsert.getUserId()));
				state = "审批中("+nextUser.getUserName()+")";
			}
			
		}else if(approveResult.equals(ApproveHistory.ROOLBACK)){//驳回
			state="拒绝("+approver.getUserName()+")";
		}else if(approveResult.equals(ApproveHistory.REPEAL)){//撤销
			state = "撤销";
			myWaitProcessService.del(instanceId, ModuleCode.BASEWORKFLOW.getCode());
		}
		myApplyService.approve(ModuleCode.BASEWORKFLOW, instanceId, approver, advice, state,approveResult);
		if(nextUser!=null){
			MyStarted tms = myStartedService.findByInstanceId(ModuleCode.BASEWORKFLOW, instanceId);
			String categoryName = tms.getCategoryName();
			String title = tms.getTitle();
			myApplyService.addApprover(categoryName, title, instanceId, nextUser.getUserId()+"", ModuleCode.BASEWORKFLOW);
		}
	}

	/**
	 * 功能：获得下部流程人员
	 * @param users
	 * @param currentUser
	 * @return
	 */
	private ApproveUser getNext(List<ApproveUser> users,String currentUser){
		ApproveUser nextUser = null;
		for(int i=0; i<users.size(); i++){
			ApproveUser temp = users.get(i);
			if(temp.getUserId().equals(currentUser)){
				if(i<users.size()-1){
					nextUser = users.get(i+1);
					break;
				}
			}
		}
		return nextUser;
	}
	
	private String getFirstUser(List<ApproveUser> users){
		if(users!=null){
			ApproveUser u = users.get(0);
			return u.getUserId();
		}
		return "";
	}
	
//	@Override
//	public ViewModule findDetailInfoByInstanceId(String instanceId) {
//		if(instanceId == null || instanceId.equals("")){
//			return new ViewModule();
//		}
//		BaseWorkflow bwf = baseWorkflowDao.findByInstanceId(instanceId);
//		MyStarted ms = myStartedService.findByInstanceId(ModuleCode.BASEWORKFLOW, instanceId);
//		UserInfo creater = userService.findOne(ms.getCreaterId());
//		ViewModule vm = new ViewModule();
//		vm.setFormData(bwf.getFormData());
//		vm.setInstanceId(instanceId);
//		vm.setCreaterName(creater.getUserName());
//		if(creater.getPhoto()==null){
//			vm.setCreaterPhoto("");
//		}else{
//			vm.setCreaterPhoto(InitBaseWorkflowConfig.getInstance().getBaseWorkflowConfig().get("photoUrl")+creater.getPhoto());
//		}
//		
//		String uids = bwf.getUserIds();
//		List<ApproveUser> alist = new Gson().fromJson(uids, new TypeToken<List<ApproveUser>>() {}.getType());
//		
//		/********************************初始化审批历史记录******************************/
//		for(int i=0; i<alist.size(); i++){
//			ApproveUser u = alist.get(i);
//			ApproveHistory ah = new ApproveHistory();
//			ah.setIndex(i);
//			ah.setApprover(u.getUserName());
//			ah.setTaskName(u.getUserName());
//			ah.setState(ApproveHistory.NOT_ARRIVED);
//			ah.setApproverId(u.getUserId().toString());
//			ah.setPhotoUrl(u.getPhotoUrl());
//			vm.addHistory(ah);
//		}
//		//添加结束节点
//		ApproveHistory end = new ApproveHistory();
//		end.setIndex(alist.size());
//		end.setState(ApproveHistory.NOT_ARRIVED);
//		end.setPhotoUrl("/mobile/images/over.png");
//		vm.addHistory(end);
//		
//		/******************************更新实际的审批记录*********************************/
//		List<MyProcessed> list = myApplyService.findHistoryByInstanceId(instanceId);
//		if(list!=null){
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//			for(Iterator<MyProcessed> it = list.iterator(); it.hasNext();){
//				MyProcessed mp = it.next();
//				for(Iterator<ApproveHistory> iter = vm.getHistory().iterator(); iter.hasNext();){
//					ApproveHistory ah = iter.next();
//					if(mp.getProcesserId().toString().equals(ah.getApproverId())){
//						ah.setAdvice(mp.getAdvice());
//						ah.setTime(sdf.format(mp.getEndTime()));
//						ah.setState(ApproveHistory.ARRIVED);
//						//审批状态默认都是通过
//						ah.setApproveResult(mp.getApproveResult());
//					}
//				}
//			}
//		}
//		/****************************更新整个流程状态，判断整个流程的状态，是否结束，以及当前审批人是谁***********************************/
//		
//		
//		List<MyWaitProcess> wp = myWaitProcessService.findByInstanceId(instanceId);
//		if(wp==null || wp.size()==0){
//			vm.setTotalState("over");
//		}else{
//			MyWaitProcess wmp = wp.get(0);
//			vm.setApproverId(wmp.getProcesserId().toString());
//			/***********************更新审批中状态*************************/
//			for(int i=0; i<vm.getHistory().size(); i++){
//				ApproveHistory ah = vm.getHistory().get(i);
//				if(ah.getApproveResult()==null && i<vm.getHistory().size()-1){
//					ah.setApproveResult(ApproveHistory.APPROVE);
//					break;
//				}
//			}
//		}
//		return vm;
//	}
	
	/**
	 * 查看流程的详细信息
	 * @param instanceId
	 * @return
	 */
	@Override
	public ViewModule findDetailInfoByInstanceId(String instanceId) {
		if(instanceId == null || instanceId.equals("")){
			return new ViewModule();
		}
		BaseWorkflow bwf = baseWorkflowDao.findByInstanceId(instanceId);
		MyStarted ms = myStartedService.findByInstanceId(ModuleCode.BASEWORKFLOW, instanceId);
		UserInfo creater = userService.findOne(ms.getCreaterId());
		ViewModule vm = new ViewModule();
		vm.setFormData(bwf.getFormData());
		vm.setInstanceId(instanceId);
		vm.setCreaterName(creater.getUserName());
		if(StringUtils.isBlank(creater.getPhoto())){
			vm.setCreaterPhoto("");
		}else{
			vm.setCreaterPhoto(InitBaseWorkflowConfig.getInstance().getBaseWorkflowConfig().get("photoUrl")+creater.getPhoto());
		}
		List<ApproveUser> alist = new Gson().fromJson(bwf.getUserIds(), new TypeToken<List<ApproveUser>>() {}.getType());
		
		//审批历史的处理逻辑：先读取审批历史表，如果流程已结束，则返回；如果流程未结束，则读取流程配置表，将未到达的处理人状态置为待审批
		//@1，读取审批历史
		vm.getHistory().addAll(initApproveHistory(instanceId, alist));
		
		//@2,判断流程是否结束
		List<MyWaitProcess> wp = myWaitProcessService.findByInstanceId(instanceId);
		if(wp==null || wp.size()==0){
			vm.setTotalState("over");
		}else{
			//@3，向审批历史中添加【审批中】的人员信息(1条)
			ApproveHistory lastAh = vm.getHistory().get(vm.getHistory().size()-1);
			ApproveUser nextUser = getNext(alist, lastAh.getApproverId());
			if(nextUser!=null){
				initApproveHistory(nextUser, vm,ApproveHistory.APPROVE);
			}
			
			//@4,向审批历史中添加【待审批】人员信息(0条或者多条)
			ApproveUser approveUser = getNext(alist, nextUser.getUserId());
			initApproveHistory(approveUser, vm, alist);
		}
		return vm;
	}
	
	//迭代填充【待审批】记录
	private void initApproveHistory(ApproveUser nextUser,ViewModule vm,List<ApproveUser> alist){
		if(nextUser!=null){
			initApproveHistory(nextUser, vm, ApproveHistory.WAIT);
			ApproveUser temp = getNext(alist, nextUser.getUserId());
			initApproveHistory(temp, vm, alist);
		}
	}
	
	
	
	/**
	 * 根据审批人初始化审批历史
	 * @param nextUser
	 * @param vm
	 */
	private void initApproveHistory(ApproveUser nextUser,ViewModule vm,String approveResult){
		ApproveHistory temp = new ApproveHistory();
		temp.setApprover(nextUser.getUserName());
		temp.setIndex(vm.getHistory().size());
		temp.setTaskName(nextUser.getUserName());
		temp.setState(ApproveHistory.NOT_ARRIVED);
		temp.setApproverId(nextUser.getUserId());
		temp.setPhotoUrl(nextUser.getPhotoUrl());
		temp.setApproveResult(approveResult);
		vm.getHistory().add(temp);
	}
	
	/**
	 * 获取审批历史
	 * @param instanceId
	 * @param alist
	 * @return
	 */
	private List<ApproveHistory> initApproveHistory(String instanceId,List<ApproveUser> alist){
		List<ApproveHistory> result = new ArrayList<ApproveHistory>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		List<MyProcessed> list = myApplyService.findHistoryByInstanceId(instanceId);
		for(int i=0; i<list.size(); i++){
			MyProcessed mp = list.get(i);
			ApproveHistory ah = new ApproveHistory();
			ah.setApprover(mp.getProcesserName());
			ah.setTime(sdf.format(mp.getEndTime()));
			ah.setIndex(i);
			ah.setTaskName(mp.getProcesserName());
			ah.setState(ApproveHistory.NOT_ARRIVED);
			ah.setApproverId(mp.getProcesserId().toString());
			for(Iterator<ApproveUser> it = alist.iterator(); it.hasNext();){
				ApproveUser au = (ApproveUser) it.next();
				if(au.getUserId().equals(mp.getProcesserId().toString())){
					ah.setPhotoUrl(au.getPhotoUrl());
					break;
				}
			}
			ah.setAdvice(mp.getAdvice());
			ah.setApproveResult(mp.getApproveResult());
			result.add(ah);
		}
		return result;
	}

	/**
	 * 删除流程
	 * @param instanceId
	 */
	@Override
	public void deleteByInstanceId(String instanceId) {
		myApplyService.delMyStarted(instanceId, ModuleCode.BASEWORKFLOW);
		baseWorkflowDao.deleteByInstanceId(instanceId);
	}


	/**
	 * 转交
	 * @param userId 用户ID，当前转交人ID
	 * @param turner 转交人，转交对象
	 * @param instanceId 流程实例ID
	 * @param advice  转交意见
	 */
	@Override
	public void turn(Integer userId, String turner, String instanceId,
			String advice) {
		//第一步，将转交人信息插入baseworkflow表中
		BaseWorkflow bwf = baseWorkflowDao.findByInstanceId(instanceId);
		String userInfo = bwf.getUserIds();
		List<ApproveUser> approveusers = new Gson().fromJson(userInfo, new TypeToken<List<ApproveUser>>(){}.getType());
		Gson gson = new Gson();
		ApproveUser aus = gson.fromJson(turner, ApproveUser.class);
		List<ApproveUser> dest = new ArrayList<BaseWorkflowServiceImpl.ApproveUser>();
		for(int i=0; i<approveusers.size(); i++){
			dest.add(approveusers.get(i));
			if(approveusers.get(i).getUserId().equals(userId.toString())){
				dest.add(aus);
			}
		}
		bwf.setUserIds(new Gson().toJson(dest));
		baseWorkflowDao.saveOrUpdate(bwf);
		//第二步，调用approve接口
		this.approve(userId, instanceId, advice, ApproveHistory.TURN);
	}

	/**
	 * 分页查询我申请的
	 * @param page
	 * @param userId
	 * @return
	 */
	@Override
	public Page<BMyStarted> findByStartedList(Pageable page, Integer userId,String moduleCode) {
		List<BMyStarted> list = new ArrayList<BMyStarted>();
		Page<MyStarted> tr =  myStartedService.findListByUserId(page, userId,moduleCode);
		for(int i=0; i<tr.getContent().size(); i++){
			MyStarted ms = tr.getContent().get(i);
			BMyStarted bm = new BMyStarted();
			TimeUtil.getCurrentMonthFirst();
			bm.setApplyTime(format.format(ms.getCreaterTime()));
			String instanceId = ms.getInstanceId();
			String code = instanceId.substring(0, instanceId.indexOf("."));
			bm.setCode(code);
			bm.setInstanceId(instanceId);
			bm.setState(ms.getState());
			if(ms.getModuleCode().equals("baseworkflow")){
				bm.setTitle(InitBaseWorkflowConfig.getInstance().getBaseWorkflowConfig().get(code));
			}else{
				bm.setTitle(ms.getTitle());
			}
			bm.setModuleCode(ms.getModuleCode());
			bm.setProcesserUser(ms.getProcesserUser());
			list.add(bm);
		}
		return new PageImpl<BMyStarted>(list, page, tr.getTotalElements());
	}

	/**
	 * 查询待我审批的
	 * @param page
	 * @param userId
	 * @return
	 */
	@Override
	public Page<BMyWaitApprove> findByWaitApproveList(Pageable page,
			Integer userId,String moduleCode) {
		List<BMyWaitApprove> list = new ArrayList<BMyWaitApprove>();
		Page<MyWaitProcess> tr =  myWaitProcessService.findListByUserId(page, userId,moduleCode);
		
		for(int i=0; i<tr.getContent().size(); i++){
			MyWaitProcess ms = tr.getContent().get(i);
			BMyWaitApprove bm = new BMyWaitApprove();
			bm.setArriveTime(format.format(ms.getStartTime()));
			bm.setCurrentUserId(userId.toString());
			bm.setInstanceId(ms.getInstanceId());
			MyStarted temp = myStartedService.findByInstanceId(ms.getInstanceId());
			Integer tempId = temp.getCreaterId();
			UserInfo tempU = userService.findOne(tempId);
			if(StringUtils.isBlank(tempU.getPhoto())){
				bm.setPhotoUrl("");
			}else{
				bm.setPhotoUrl(InitBaseWorkflowConfig.getInstance().getBaseWorkflowConfig().get("photoUrl")+tempU.getPhoto());
			}
			bm.setCreaterName(tempU.getUserName());
			bm.setTitle(ms.getTitle());
			bm.setModuleCode(ms.getModuleCode());
			list.add(bm);
		}
		return new PageImpl<BMyWaitApprove>(list, page, tr.getTotalElements());
	}

	/**
	 * 查询我已经审批过的
	 */
	@Override
	public Page<BMyProcessed> findMyProcessedList(Pageable page, Integer userId,String moduleCode) {
		List<BMyProcessed> list = new ArrayList<BMyProcessed>();
		Page<MyProcessed> tr =  myProcessedService.findListByUserId(page, userId,moduleCode);
		
		for(int i=0; i<tr.getContent().size(); i++){
			MyProcessed ms = tr.getContent().get(i);
			BMyProcessed bm = new BMyProcessed();
			bm.setApproveTime(format.format(ms.getEndTime()));
			bm.setInstanceId(ms.getInstanceId());
			bm.setTitle(ms.getTitle());
			MyStarted temp = myStartedService.findByInstanceId(ms.getInstanceId());
			bm.setState(temp.getState());
			UserInfo tempU = userService.findOne(temp.getCreaterId());
			if(StringUtils.isBlank(tempU.getPhoto())){
				bm.setPhotoUrl("");
			}else{
				bm.setPhotoUrl(InitBaseWorkflowConfig.getInstance().getBaseWorkflowConfig().get("photoUrl")+tempU.getPhoto());
			}
			bm.setCreaterName(tempU.getUserName());
			bm.setModuleCode(ms.getModuleCode());
			list.add(bm);
		}
		return new PageImpl<BMyProcessed>(list, page, tr.getTotalElements());
	}

	/**
	 * 查询待我审批的数量
	 * @param userId
	 * @param moduleCode
	 */
	@Override
	public int myWaitCount(Integer userId, String moduleCode) {
		return myWaitProcessService.myWaitModuleCount(userId, moduleCode);
		
	}
}
