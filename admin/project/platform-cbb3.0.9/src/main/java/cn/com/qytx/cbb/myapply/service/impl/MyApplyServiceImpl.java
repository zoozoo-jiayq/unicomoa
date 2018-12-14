package cn.com.qytx.cbb.myapply.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.myapply.dao.MyStartedDao;
import cn.com.qytx.cbb.myapply.domain.MyProcessed;
import cn.com.qytx.cbb.myapply.domain.MyStarted;
import cn.com.qytx.cbb.myapply.domain.MyWaitProcess;
import cn.com.qytx.cbb.myapply.service.IMyProcessed;
import cn.com.qytx.cbb.myapply.service.IMyWaitProcess;
import cn.com.qytx.cbb.myapply.service.MyApplyService;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IUser;

/**
 * 功能：我的申请/审批 API接口的实现
 * 作者： jiayongqiang
 * 创建时间：2014年8月21日
 */
@Transactional
@Service("myApplyService")
public class MyApplyServiceImpl implements MyApplyService {

	@Resource
	private IMyProcessed myProcessedService;
	@Resource
	private IMyWaitProcess myWaitProcessService;
	@Resource
	private IUser userService;
	@Resource
	private MyStartedDao myStartedDao;
	
	
	@Override
	public void createProcess(UserInfo creater, String categoryName,
			String title, String instanceId,
			ModuleCode moduleCode) {
		//创建我的申请对象
		MyStarted myStarted = new MyStarted();
		myStarted.setCategoryName(categoryName);
		myStarted.setTitle(title);
		myStarted.setInstanceId(instanceId);
		myStarted.setCreaterId(creater.getUserId());
		myStarted.setCreaterName(creater.getUserName());
		myStarted.setCreaterTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		myStarted.setModuleCode(moduleCode.getCode());
		myStarted.setCompanyId(creater.getCompanyId());
		myStartedDao.saveOrUpdate(myStarted);
	}
	
	/**
	 * 功能：构造待审批对象
	 * 作者：jiayongqiang
	 * 参数：
	 * 输出：
	 */
	private MyWaitProcess generateWaitProcess(UserInfo creater,String categoryName,
			String title,String instanceId,UserInfo processer,
			ModuleCode moduleCode){
		MyWaitProcess wait = new MyWaitProcess();
		wait.setCategoryName(categoryName);
		wait.setTitle(title);
		wait.setInstanceId(instanceId);
		if(processer!=null){
			wait.setProcesserId(processer.getUserId());
			wait.setProcesserName(processer.getUserName());
		}
		wait.setStartTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		wait.setModuleCode(moduleCode.getCode());
		wait.setCreaterName(creater.getUserName());
		wait.setCompanyId(creater.getCompanyId());
		return wait;
	}

	@Override
	public void approve(ModuleCode moduleCode, String instanceId,
			UserInfo approver, String advice,String state,String approveResult) {
		MyStarted start = myStartedDao.findByModuleCodeAndInstanceId(moduleCode, instanceId);
		MyWaitProcess wait =  myWaitProcessService.findByModuleCodeAndInstanceId(moduleCode, instanceId, approver.getUserId());
		if(wait!=null){
			//删除待审批记录
//			myWaitProcessService.delete(wait, true);
			myWaitProcessService.del(instanceId, wait.getModuleCode());
		}
		//创建已审批记录
		MyProcessed processed = new MyProcessed();
		processed.setCategoryName(start.getCategoryName());
		processed.setTitle(start.getTitle());
		processed.setInstanceId(instanceId);
		processed.setProcesserId(approver.getUserId());
		processed.setProcesserName(approver.getUserName());
		processed.setEndTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		processed.setModuleCode(moduleCode.getCode());
		processed.setCreaterName(start.getCreaterName());
		processed.setAdvice(advice);
		processed.setCompanyId(approver.getCompanyId());
		processed.setApproveResult(approveResult);
		myProcessedService.saveOrUpdate(processed);
		//更新申请表的状态
		updateState(moduleCode, instanceId, state);
	}

	@Override
	public void addApprover(String categoryName, String title,
			String instanceId, String nextAssigner, ModuleCode moduleCode) {
		// TODO Auto-generated method stub
		MyStarted started = myStartedDao.findByModuleCodeAndInstanceId(moduleCode, instanceId);
		if(started!=null){
			Integer userId = started.getCreaterId();
			UserInfo createrUser = userService.findOne(userId);
			if(nextAssigner == null){
				MyWaitProcess wait = generateWaitProcess(createrUser, categoryName, title, instanceId, null, moduleCode);
				myWaitProcessService.saveOrUpdate(wait);
			}else{
				List<MyWaitProcess> waits = new ArrayList<MyWaitProcess>();
				List<UserInfo> userlist = userService.findUsersByIds(nextAssigner);
				for(UserInfo u:userlist){
					MyWaitProcess w = generateWaitProcess(createrUser, categoryName, title, instanceId, u, moduleCode);
					waits.add(w);
				}
				myWaitProcessService.saveList(waits);
			}
			
		}
		
	}

	@Override
	public void updateState(ModuleCode moduleCode, String instanceId,
			String state) {
		// TODO Auto-generated method stub
		MyStarted started = myStartedDao.findByModuleCodeAndInstanceId(moduleCode, instanceId);
		if(started!=null){
			started.setState(state);
			myStartedDao.saveOrUpdate(started);
		}
	}
	
	public void delMyStarted(String instanceIds,ModuleCode moduleCode){
		myProcessedService.del(instanceIds,moduleCode.getCode());
		myWaitProcessService.del(instanceIds,moduleCode.getCode());
		myStartedDao.del(instanceIds,moduleCode.getCode());
	}
	@Override
	public void rollback(ModuleCode moduleCode, String instanceId,
			UserInfo approver, String advice,String state,String rollbackType) {
		// TODO Auto-generated method stub
		MyStarted start = myStartedDao.findByModuleCodeAndInstanceId(moduleCode, instanceId);
		myWaitProcessService.del(instanceId, moduleCode.getCode());
		//创建已审批记录
		if(start!=null){
			MyProcessed processed = new MyProcessed();
			processed.setCategoryName(start.getCategoryName());
			processed.setTitle(start.getTitle());
			processed.setInstanceId(start.getInstanceId());
			processed.setProcesserId(approver.getUserId());
			processed.setProcesserName(approver.getUserName());
			processed.setEndTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			processed.setModuleCode(moduleCode.getCode());
			processed.setCreaterName(start.getCreaterName());
			processed.setCompanyId(approver.getCompanyId());
			processed.setAdvice(advice);
			if(rollbackType.equals("endnoagree")){
				processed.setApproveResult("1");
			}else if(rollbackType.equals("repeal")){
				processed.setApproveResult("2");
			}
			myProcessedService.saveOrUpdate(processed);
		}
		//更新申请表的状态
		updateState(moduleCode, instanceId, state);
	}

	@Override
	public List<MyProcessed> findHistoryByInstanceId(String instanceId) {
		// TODO Auto-generated method stub
		return myProcessedService.findByInstanceId(instanceId);
	}
}
