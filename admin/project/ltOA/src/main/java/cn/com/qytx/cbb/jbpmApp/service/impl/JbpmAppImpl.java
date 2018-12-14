package cn.com.qytx.cbb.jbpmApp.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.weaver.World;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.task.TaskImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.affairSetting.service.IAffairSetting;
import cn.com.qytx.cbb.affairs.domain.ReminderInfo;
import cn.com.qytx.cbb.affairs.service.IAffairsBody;
import cn.com.qytx.cbb.customForm.domain.Form;
import cn.com.qytx.cbb.customForm.domain.FormCategory;
import cn.com.qytx.cbb.customForm.service.IFormCategory;
import cn.com.qytx.cbb.customForm.service.IFormPropertyValue;
import cn.com.qytx.cbb.customJpdl.dao.NodeFormAttributeDao;
import cn.com.qytx.cbb.customJpdl.domain.NodeFormAttribute;
import cn.com.qytx.cbb.customJpdl.domain.ProcessAttribute;
import cn.com.qytx.cbb.customJpdl.service.JpdlInterface;
import cn.com.qytx.cbb.customJpdl.service.ProcessAttributeService;
import cn.com.qytx.cbb.customJpdl.service.impl.template.RollbackTaskInfo;
import cn.com.qytx.cbb.customWidget.vo.JbpmAdvice;
import cn.com.qytx.cbb.file.domain.Attachment;
import cn.com.qytx.cbb.file.service.IAttachment;
import cn.com.qytx.cbb.jbpmApp.action.mobile.jsonModel.ApproveHistory;
import cn.com.qytx.cbb.jbpmApp.dao.WorkflowLeaveDao;
import cn.com.qytx.cbb.jbpmApp.dao.WorkflowVarDao;
import cn.com.qytx.cbb.jbpmApp.domain.WorkflowLeave;
import cn.com.qytx.cbb.jbpmApp.domain.WorkflowVar;
import cn.com.qytx.cbb.jbpmApp.service.IJbpmApp;
import cn.com.qytx.cbb.jbpmApp.service.IWorkFlowService;
import cn.com.qytx.cbb.jbpmApp.service.JbpmStateDef;
import cn.com.qytx.cbb.jbpmApp.service.impl.command.JudgmentInstanceCanDelete;
import cn.com.qytx.cbb.myapply.domain.MyProcessed;
import cn.com.qytx.cbb.myapply.service.MyApplyService;
import cn.com.qytx.cbb.myapply.service.MyApplyService.ModuleCode;
import cn.com.qytx.cbb.publicDom.util.DomContentUtil;
import cn.com.qytx.oa.carManage.service.ICarVip;
import cn.com.qytx.platform.org.dao.GroupDao;
import cn.com.qytx.platform.org.dao.UserDao;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IGroup;
import cn.com.qytx.platform.org.service.IUser;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 功能 工作流接口  
 * <br/>版本  1.0
 * <br/>开发人员  zyf
 * <br/>创建日期  2016年1月8日
 * <br/>修改日期  2016年1月8日
 * <br/>修改列表
 */
@Service("jbpmAppService")
@Transactional
public class JbpmAppImpl implements IJbpmApp {
	@Resource(name = "workFlowService")
	IWorkFlowService workFlowService; // 工作流服务
	@Resource(name = "formPropertyValueService")
	IFormPropertyValue formPropertyValueService;
	/** 附件 */
	@Resource(name = "attachmentService")
	IAttachment attachmentService;

	/** 流程定义服务类 */
	@Resource(name = "processAttributeService")
	private ProcessAttributeService processAttributeService;
	@Resource(name = "nodeFormAttributeDao")
	private NodeFormAttributeDao nodeFormAttributeDao;
	@Resource(name = "processEngine")
	private ProcessEngine processEngine;
	@Resource(name="myApplyService")
	private MyApplyService myApplyService;
	/**
	 * 部门,群组管理接口
	 */
	/*
	 * @Resource(name = "groupInfoVOImpl") private IGroupInfoVO groupInfoVOImpl;
	 */
	@Resource(name = "groupService")
	IGroup groupService;
	
	/**
	 * 派车业务接口
	 */
	@Resource(name = "carVipService")
	ICarVip carVipService;
	
	@Resource(name = "userDao")
	UserDao<UserInfo> userDao;
	@Resource(name = "userService")
	IUser userService;
	@Resource(name = "workflowVarDao")
	WorkflowVarDao workflowVarDao;
	@Resource(name = "groupDao")
	private GroupDao<GroupInfo> groupDao;
	
	@Resource(name = "workflowleaveDao")
	private WorkflowLeaveDao workflowleaveDao;
	
	
	
	@Resource
	IAffairSetting affairSetting;
	@Resource
	IAffairsBody affairsBody;
	@Resource
	IFormCategory formCategoryService;

	/**
	 * 保存意见 添加审批结果
	 * 
	 * @param advice
	 * @param processInstanceId
	 * @param taskId
	 * @param httpSession
	 */
	public void saveAdvice(String advice, String instanceId, UserInfo userinfo,
			String result) {
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<JbpmAdvice> list = new ArrayList<JbpmAdvice>();
		Gson gson = new Gson();
		if (workFlowService.getVariablebyInstance(instanceId,
				JpdlInterface.VAR_ADVICE) != null) {
			String str = (String) workFlowService.getVariablebyInstance(
					instanceId, JpdlInterface.VAR_ADVICE);
			list = gson.fromJson(str, new TypeToken<List<JbpmAdvice>>() {
			}.getType());
		}
		JbpmAdvice jbpmAdvice = new JbpmAdvice();
		jbpmAdvice.setUserId(userinfo.getLoginName());
		jbpmAdvice.setUserName(userinfo.getUserName());
		jbpmAdvice.setCreateTime(sdf.format(new Date()));
		jbpmAdvice.setContent(advice);
		jbpmAdvice.setJob(userinfo.getJob());
		String groupName = "";
		GroupInfo g = groupService.findOne(userinfo.getGroupId());
		if (null != g ) {
			groupName = g.getGroupName();
		}
		jbpmAdvice.setGroupName(groupName);
		jbpmAdvice.setResult(result);
		jbpmAdvice.setSignType(userinfo.getSignType() == null ? 0 : userinfo
				.getSignType());
		jbpmAdvice.setOrderIndex(userinfo.getOrderIndex());
		if (userinfo.getSignType() != null) {
			if (userinfo.getSignType() == 1) {
				jbpmAdvice.setSignUrl(userinfo.getSignUrl());
			} else if (userinfo.getSignType() == 2) {
				jbpmAdvice.setSignUrl(userinfo.getNtkoUrl());
			}
		}
		List<Task> tasklist = processEngine.getTaskService().createTaskQuery().processInstanceId(instanceId).list();
		jbpmAdvice.setTaskName(tasklist.get(0).getName());
		list.add(jbpmAdvice);
		String adviceStr = gson.toJson(list);
		processEngine.getExecutionService().setVariable(instanceId,
				JpdlInterface.VAR_ADVICE, adviceStr);
	}

	/**
	 * 保存附件信息
	 * 
	 * @param attachJSON
	 * @param processInstanceId
	 * @param userId
	 */
	private void saveAttachJson(String attachJSON, String processInstanceId) {
		if(!StringUtils.isEmpty(attachJSON)){
			List<Attachment> list = attachmentService.getAttachmentsByIds(attachJSON);
			List<Attachment> alist = getAttachList(processInstanceId);
			if(alist!=null){
			    list.addAll(alist);
			}
			Gson gson = new Gson();
			String result = gson.toJson(list);
			processEngine.getExecutionService().setVariable(processInstanceId,"attach", result);
		}
	}

	/**
	 * 获取流程实例下的附件信息
	 */
	@Override
	public List<Attachment> getAttachList(String processInstanceId) {
		List<Attachment> list = null;
		String str = getHistVarList(processInstanceId, "attach");
		if (str != null) {
			Gson gson = new Gson();
			list = gson.fromJson(str, new com.google.common.reflect.TypeToken<List<Attachment>>() {
			}.getType());
		}
		return list;
	}

	/**
	 * 获取标题
	 */
	@Override
	public String getTitle(String processInstanceId) {
		String res = getHistVarList(processInstanceId, "title");
		return res;
	}

	@Override
	public List<JbpmAdvice> getAdviceList(String processInstanceId) {
		List<JbpmAdvice> res = null;
		String str = getHistVarList(processInstanceId, JpdlInterface.VAR_ADVICE);
		Gson gson = new Gson();
		if (str != null) {
			res = gson.fromJson(str, new com.google.common.reflect.TypeToken<List<JbpmAdvice>>() {
			}.getType());
		}
		return res;
	}

	/**
	 * 功能：根绝流程ID和变量名查找流程变量
	 * 
	 * @param
	 * @return
	 * @throws
	 */
	@Override
	public String getHistVarList(String processInstanceId, String searchKey) {
		ProcessInstance pi = processEngine.getExecutionService()
				.createProcessInstanceQuery()
				.processInstanceId(processInstanceId).uniqueResult();
		if (pi != null) {
			return (String) processEngine.getExecutionService().getVariable(
					processInstanceId, searchKey);
		} else {
			return (String) processEngine.getHistoryService().getVariable(
					processInstanceId, searchKey);
		}
	}

	/**
	 * 需要办理任务的个数
	 */
	@Override
	public int countOfTask(int userId) {
		int res = 0;
		UserInfo user = (UserInfo) userDao.findOne(userId);
		if (user != null) {
			String r = processEngine.getTaskService().createTaskQuery()
					.assignee(user.getLoginName()).count()
					+ "";
			res = Integer.parseInt(r);
		}
		return res;
	}

	/**
	 * 功能：根据流程id获得当前任务数量
	 * @param processInstanceId
	 * @return
	 */
	@Override
	public int getCurrentTaskCount(String processInstanceId) {
		// return userTaskWapDao.getCurrentTaskCount(processInstanceId);
		List list = processEngine.getTaskService().createTaskQuery().processInstanceId(processInstanceId).list();
		if(list!=null){
			return list.size();
		}
		return 0;
	}

	/**
	 * 功能：判断流程是否可以被删除，只有当除了发起人外没其他人处理的时候才可以删除
	 * @param instanceId
	 * @return
	 */
	@Override
	public boolean canDeleteInstance(String instanceId) {
		boolean result = processEngine.execute(new JudgmentInstanceCanDelete(
				instanceId));
		return result;
	}

	/**
	 * 功能：流转任务
	 * @param ui 操作人信息
	 * @param taskId 任务id
	 * @param attachData 附件信息
	 * @param formData 表单内容
	 * @param advice 建议
	 * @param nextAction 下一步操作
	 * @param nextAssigners 下一步流程人
	 * @throws UnsupportedEncodingException
	 */
	@Override
	public void completeTask(UserInfo ui, String taskId, String attachData,
			String formData, String advice, String nextAction,
			String nextAssigners) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		TaskImpl task = (TaskImpl) processEngine.getTaskService().getTask(taskId);
		String instanceId = task.getProcessInstance().getId();
		String processIdStr = processEngine.getExecutionService()
				.getVariable(instanceId, "processAttributeId")
				.toString();
		ProcessAttribute pa = processAttributeService.getProcessById(Integer
				.parseInt(processIdStr));

		Integer formId = pa.getFormId();
		
		// 更新表单数据
		if (formId != null && formData != null && !"".equals(formData)) {
			formPropertyValueService.saveOrUpdate(formData,instanceId, formId);
		}
		// 更新附件数据
		if (attachData != null && !attachData.equals("")) {
			saveAttachJson(attachData, instanceId);
		}
		
		// 更新审批意见
		saveAdvice(advice, instanceId, ui, JpdlInterface.APPROVE_RESULT_AGREE);
		
		workFlowService.completeTask(task,ui,nextAction, nextAssigners,instanceId);
		
		// 发送提醒消息
		if (nextAssigners != null && !nextAssigners.equals("")) {
			setAffairsSign(nextAssigners, ui, instanceId);
		}
		
		//调用【我的申请】的审批接口
		//add by jiayq,2014-8-21
		WorkflowVar var = workflowVarDao.findByInstanceId(instanceId);
		String state = var.getCurrentState();
		String cnState = "";
		//审批中
		if(state.equals(JpdlInterface.PROCESS_STATE_APPROVE)){
			if(var.getCurrentUser()!=null && !var.getCurrentUser().equals("")){
				cnState = "审批中("+var.getCurrentUser()+")";
			}else{
				cnState = "审批中";
			}
		}else if(state.equals(JpdlInterface.PROCESS_STATE_END)){
			cnState = "通过(完成)";
		}
		//判断是申请还是审批通过，如果该流程没有历史处理记录，则是申请，否则是审批通过
		List<MyProcessed> historylist = myApplyService.findHistoryByInstanceId(instanceId);
		if(historylist==null || historylist.size()==0){
			myApplyService.approve(ModuleCode.WORKFLOW, instanceId, ui, advice,cnState,ApproveHistory.APPLY);
		}else{
			myApplyService.approve(ModuleCode.WORKFLOW, instanceId, ui, advice,cnState,ApproveHistory.APPROVED);
		}
		
		//调用【添加审批任务接口】
		if((!StringUtils.isEmpty(nextAssigners)) && (!state.equals(JpdlInterface.PROCESS_STATE_END)) ){
			List<UserInfo> users = userService.findUsersByLoginNames(nextAssigners);
			String nextUsers = "";
			for(UserInfo u:users){
				nextUsers+=u.getUserId()+",";
			}
			String title = workFlowService.getVariablebyInstance(instanceId, "title");
			int categoryId = pa.getCategoryId();
			FormCategory fc = formCategoryService.findOne(categoryId);
			myApplyService.addApprover(fc.getCategoryName(), title, instanceId, nextUsers, ModuleCode.WORKFLOW);
		}
		
		if("322".equals(processIdStr)){//请假单处理
			WorkflowLeave wfl = null;
			if("发起流程".equals(var.getBeforeTaskName())){//记录请假单
				Gson gson = new Gson();
				WorkflowLeave leave = new WorkflowLeave();
				List<Form> ps = gson.fromJson(formData, new TypeToken<List<Form>>(){}.getType());
				for(Form fr:ps){
					if("_kaissj_".equals(fr.getName())){
						leave.setStartLeaveTime(Timestamp.valueOf(fr.getValue()+":00.000"));
					}
					if("_jiessj_".equals(fr.getName())){
						leave.setEndLeaveTime(Timestamp.valueOf(fr.getValue()+":00.000"));
					}
					if(leave.getStartLeaveTime()!=null&&leave.getEndLeaveTime()!=null){
						break;
					}
				}
				leave.setState(0);
				leave.setType(1);
				leave.setCompanyId(ui.getCompanyId());
				leave.setCreateTime(new Timestamp(System.currentTimeMillis()));
				leave.setIsCancel(0);
				leave.setIsDelete(0);
				leave.setIsRemove(0);
				leave.setUserId(ui.getUserId());
				leave.setInstanceId(instanceId);
				workflowleaveDao.saveOrUpdate(leave);
			}
			if("TO 结束".equals(nextAction)){//销假处理
				wfl = workflowleaveDao.findLeaveByInstanceId(instanceId);
				wfl.setState(1);
				workflowleaveDao.saveOrUpdate(wfl);
			}
			if("销假".equals(task.getActivityName())){
				wfl = workflowleaveDao.findLeaveByInstanceId(instanceId);
				wfl.setIsRemove(1);
				wfl.setRemoveTime(new Timestamp(System.currentTimeMillis()));
				workflowleaveDao.saveOrUpdate(wfl);
			}
		}
		
		if("325".equals(processIdStr)){//警务车辆用车
			if("TO 结束".equals(nextAction)){
				Gson gson = new Gson();
				List<Form> ps = gson.fromJson(formData, new TypeToken<List<Form>>(){}.getType());
				String _chehao_ = "";
				String _jiasyxm_ = "";
				for(Form fr:ps){
					if("_chehao_".equals(fr.getName())){
						_chehao_ = fr.getValue();
					}
					if("_jiasyxm_".equals(fr.getName())){
						_jiasyxm_ = fr.getValue();
					}
				}
				carVipService.openVipTicket(_jiasyxm_, "", _chehao_, ui);
			}
		}
		
		if("326".equals(processIdStr)){//工作假 处理
			WorkflowLeave wfl = null;
			if("发起流程".equals(var.getBeforeTaskName())){//记录工作假
				Gson gson = new Gson();
				WorkflowLeave leave = new WorkflowLeave();
				List<Form> ps = gson.fromJson(formData, new TypeToken<List<Form>>(){}.getType());
				for(Form fr:ps){
					if("_kaissj_".equals(fr.getName())){
						leave.setStartLeaveTime(Timestamp.valueOf(fr.getValue()+":00.000"));
					}
					if("_jiessj_".equals(fr.getName())){
						leave.setEndLeaveTime(Timestamp.valueOf(fr.getValue()+":00.000"));
					}
					if(leave.getStartLeaveTime()!=null&&leave.getEndLeaveTime()!=null){
						break;
					}
				}
				leave.setState(0);
				leave.setType(2);
				leave.setCompanyId(ui.getCompanyId());
				leave.setCreateTime(new Timestamp(System.currentTimeMillis()));
				leave.setIsCancel(0);
				leave.setIsDelete(0);
				leave.setIsRemove(0);
				leave.setUserId(ui.getUserId());
				leave.setInstanceId(instanceId);
				workflowleaveDao.saveOrUpdate(leave);
			}
			if("TO 结束".equals(nextAction)){
				wfl = workflowleaveDao.findLeaveByInstanceId(instanceId);
				wfl.setState(1);
				workflowleaveDao.saveOrUpdate(wfl);
			}
		}
		
		if("327".equals(processIdStr)){//公休假 处理
			WorkflowLeave wfl = null;
			if("发起流程".equals(var.getBeforeTaskName())){//记录工作假
				Gson gson = new Gson();
				WorkflowLeave leave = new WorkflowLeave();
				List<Form> ps = gson.fromJson(formData, new TypeToken<List<Form>>(){}.getType());
				for(Form fr:ps){
					if("_xiujkssj_".equals(fr.getName())){
						leave.setStartLeaveTime(Timestamp.valueOf(fr.getValue()+":00.000"));
					}
					if("_xiujjssj_".equals(fr.getName())){
						leave.setEndLeaveTime(Timestamp.valueOf(fr.getValue()+":00.000"));
					}
					if(leave.getStartLeaveTime()!=null&&leave.getEndLeaveTime()!=null){
						break;
					}
				}
				leave.setState(0);
				leave.setType(3);
				leave.setCompanyId(ui.getCompanyId());
				leave.setCreateTime(new Timestamp(System.currentTimeMillis()));
				leave.setIsCancel(0);
				leave.setIsDelete(0);
				leave.setIsRemove(0);
				leave.setUserId(ui.getUserId());
				leave.setInstanceId(instanceId);
				workflowleaveDao.saveOrUpdate(leave);
			}
			if("TO 结束".equals(nextAction)){
				wfl = workflowleaveDao.findLeaveByInstanceId(instanceId);
				wfl.setState(1);
				workflowleaveDao.saveOrUpdate(wfl);
			}
		}
		
		
	}
	
	

	/**
	 * 功能：添加提醒
	 * 
	 * @param nextTaskMainUserId2
	 * @param nextTaskCandidateUserIdList2
	 * @param session
	 * @throws UnsupportedEncodingException
	 */
	public void setAffairsSign(String nextTaskMainUserId2, UserInfo userInfo,String instanceId) throws UnsupportedEncodingException {
	    WorkflowVar var = workflowVarDao.findByInstanceId(instanceId);
	    int index =  instanceId.lastIndexOf(".");
	    String processName = instanceId.substring(0, index);
		String content = "您有"+userInfo.getUserName()+"提交的"+processName+"待处理："+var.getTitle();
		//通知下一步经办人
		String defaultNextSendType = affairSetting.findDefaultByCode("gzl_txxybjbr");
		ReminderInfo reminderInfo = new ReminderInfo();
		reminderInfo.setSendType(defaultNextSendType);
		reminderInfo.setAffairContent(content);
		String url = "/jbpmflow/detailSearch!taskIsEndForAffire.action?processInstanceId="+URLEncoder.encode(instanceId, "UTF-8");
		reminderInfo.setAffairUrl(url);
		reminderInfo.setToids(getToIds(nextTaskMainUserId2));
		reminderInfo.setFromUserInfo(userInfo);
		reminderInfo.setModuleName("工作流");
		reminderInfo.setPushSubjcet(content);
		//reminderInfo.setPushContent(content);
		reminderInfo.setRecordId(instanceId);
		reminderInfo.setSmsContent("【驻马店市投资有限公司】"+content);
		try {
			affairsBody.sendReminder(reminderInfo);
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 功能：剔除用户ids中的当前登录人id
	 * @param nextTaskMainUserId2
	 * @return
	 */
	private String getToIds(String nextTaskMainUserId2) {
		StringBuffer res = new StringBuffer();
		if (nextTaskMainUserId2 != null && !"".equals(nextTaskMainUserId2)) {
			String[] arr = nextTaskMainUserId2.split(",");
			for (String userId : arr) {
				if (userId != null && !"".equals(userId)) {
					UserInfo userInfo = userService.findByLoginName(userId);
					if (userInfo != null) {
						res.append(userInfo.getUserId() + ",");
					}
				}
			}

		}
		String resStr = "";
		if (res != null && !"".equals(res.toString())
				&& res.toString().endsWith(",")) {
			resStr = res.toString().substring(0, res.length() - 1);
		}
		return resStr;
	}

	/**
	 * 功能：拒绝/撤销
	 * @param instanceId 流程id
	 * @param attachData 附件信息
	 * @param formData 表单信息
	 * @param userinfo 操作人信息
	 * @param advice 建议
	 * @param rollbackType 操作类型
	 */
	@Override
	public void rollbackTask(String instanceId, String attachData, String formData,
			UserInfo userinfo, String advice,String rollbackType) {
		//保存审批记录
		String r = "";
		if(rollbackType.equals(JpdlInterface.PROCESS_STATE_ROLLBACK)){
			r = JpdlInterface.APPROVE_RESULT_ROLLBACK;
		}else if(rollbackType.equals(JpdlInterface.PROCESS_STATE_REPEAL)){
			r = JpdlInterface.APPROVE_RESULT_REPEAL;
		}
		saveAdvice(advice, instanceId, userinfo,r);

		// add by 贾永强，将流程变量更新到历史表中
		Set<String> set = processEngine.getExecutionService().getVariableNames(
				instanceId);
		Map<String, Object> mapstr = processEngine.getExecutionService()
				.getVariables(instanceId, set);
		processEngine.getExecutionService().createVariables(
				instanceId, mapstr, true);
		
		//发送打回的消息
		try {
		    sendRollbackMessage(instanceId, userinfo,rollbackType,advice);
		} catch (UnsupportedEncodingException e1) {
		    // TODO Auto-generated catch block
		    e1.printStackTrace();
		}
		//调用JBPMAPI 完成任务，更新变量表流程状态
		
		List<Task> tasks = processEngine.getTaskService().createTaskQuery().processInstanceId(instanceId).list();
		String taskName = "";
		if(tasks!=null && tasks.size()>0){
			taskName = tasks.get(0).getName();
		}
		workFlowService.rollbackTask(instanceId, userinfo,rollbackType);
		
		//调用【我的审批的审批接口】，add by Jiayq
		String state ="";
		if(rollbackType.equals(JpdlInterface.PROCESS_STATE_ROLLBACK)){
			state = "拒绝("+userinfo.getUserName()+")";
		}else{
			state = "撤销";
		}
		myApplyService.rollback(ModuleCode.WORKFLOW, instanceId, userinfo, advice,state,rollbackType);
		
		/**
		 * 撤销  请假  工作假 或者 公休假
		 */
		WorkflowLeave wfl = workflowleaveDao.findLeaveByInstanceId(instanceId);
		if(wfl!=null){
			wfl.setIsCancel(1);
			workflowleaveDao.saveOrUpdate(wfl);
		}
	}
	
	/**
     * @throws UnsupportedEncodingException 
     * 功能：向所有处理人发送驳回/撤销消息
     * @param
     * @return
     * @throws   
     */
    private void sendRollbackMessage(String instanceId,UserInfo fromUser,String type,String advice) throws UnsupportedEncodingException{
        //消息内容
        String contentInfo = "";
        //消息接收人
        String ids = "";
        //流程名称
        int index =  instanceId.lastIndexOf(".");
        String processName = instanceId.substring(0, index);
        //模块代码
        String moduleCode = "";
        
        //获取发起人信息
        WorkflowVar var = workflowVarDao.findByInstanceId(instanceId);
        String createrLoginName = var.getCreater();
        UserInfo cr = userService.findByLoginName(createrLoginName);
        
        //撤销，发送所有办理人员
        if(type.equals(JpdlInterface.PROCESS_STATE_REPEAL)){
            contentInfo = cr.getUserName()+"提交的"+processName+"已撤销："+var.getTitle()+"。撤销原因："+advice;
            //获取所有的审批人
            String info = (String) processEngine.getExecutionService()
                    .getVariable(instanceId, JpdlInterface.ROLL_BACK_INFO);
            Gson gson = new Gson();
            List<RollbackTaskInfo> list = gson.fromJson(info, 
                    new TypeToken<List<RollbackTaskInfo>>(){}.getType());
            List<UserInfo> us = getToUsers(list);
            
            if(us!=null){
                for(int i=0; i<us.size(); i++){
                    ids+=us.get(i).getUserId();
                    if(i<us.size()-1){
                        ids+=",";
                    }
                }
            }
            moduleCode = "gzl_chexiao";
        }else if(type.equals(JpdlInterface.PROCESS_STATE_ROLLBACK)){//驳回
            contentInfo = "您提交的"+processName+"被"+fromUser.getUserName()+"驳回，驳回原因："+advice;
            ids+=cr.getUserId();
            moduleCode = "gzl_bohui";
        }
        
        
        //通知下一步经办人
        String defaultNextSendType = affairSetting.findDefaultByCode(moduleCode);
        ReminderInfo reminderInfo = new ReminderInfo();
        reminderInfo.setSendType(defaultNextSendType);
        reminderInfo.setAffairContent(contentInfo);
        String url = "/jbpmflow/detailSearch!viewForAffire.action?processInstanceId="+URLEncoder.encode(instanceId, "UTF-8");
        reminderInfo.setAffairUrl(url);
        
        reminderInfo.setToids(ids);
        reminderInfo.setFromUserInfo(fromUser);
        reminderInfo.setModuleName("工作流");
        reminderInfo.setPushSubjcet(contentInfo);
        //reminderInfo.setPushContent(contentInfo);
        reminderInfo.setRecordId(instanceId);
        reminderInfo.setSmsContent("【OA办公系统提醒】"+contentInfo);
        try {
            affairsBody.sendReminder(reminderInfo);
        } catch (HttpException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private List<UserInfo> getToUsers(List<RollbackTaskInfo> rollbackTasks){
        List<UserInfo> result = new ArrayList<UserInfo>();
        for(int i=1;i<rollbackTasks.size();i++){
            RollbackTaskInfo temp = rollbackTasks.get(i);
            String loginName = temp.getUser();
            UserInfo u = userService.findByLoginName(loginName);
            if(!result.contains(u)){
                result.add(u);
            }
        }
        return result;
    }
    
    /**
     * 功能：挂起任务
     * @param taskId
     * @param ui
     */
	@Override
	public void suspendTask(String taskId, UserInfo ui) {
		// TODO Auto-generated method stub
		workFlowService.suspendTask(ui.getLoginName(), taskId);
		Map<String, Object> var = new HashMap<String, Object>();
		String advice = "";
		Task task = processEngine.getTaskService().getTask(taskId);
		String processInstanceId = task.getExecutionId();
		saveAdvice(advice, taskId, ui, JpdlInterface.APPROVE_RESULT_SUSPEND);
		var.put("currentState", JbpmStateDef.SUSPEND); // 挂起
		var.put("suspendTime", new Timestamp(Calendar.getInstance()
				.getTimeInMillis()));// 挂起时间
		workFlowService.setVariablebyInstance(processInstanceId, var);
	}

	/**
	 * 功能：
	 * 
	 * @param ui:发起人 title:标题 formDataJSON:表单数据 attachJSON:附件数据 advice:审批意见
	 *        processId:流程定义ID
	 * @return
	 * @throws
	 */
	@Override
	public String startProcess(UserInfo ui, String title, String formDataJSON,
			String attachJSON, String advice, int processId, String nextAction,
			String nextUsers) {
		// TODO Auto-generated method stub
		String processInstanceId = "";
		ProcessAttribute pa = processAttributeService.getProcessById(processId);
		if (pa != null) {
			ProcessInstance processInstance = workFlowService
					.startProcessInstance(pa.getProcessDefineId(),
							ui.getLoginName());
			if (processInstance != null) {
				processInstanceId = processInstance.getId();
				// 设置流程定义ID变量值
				Map<String, Object> vars = new HashMap<String, Object>();
				vars.put("processAttributeId", processId + "");
				vars.put("title", title);
				processEngine.getExecutionService().setVariables(
						processInstance.getId(), vars);
				// 更新流程变量
				WorkflowVar wfv =  new WorkflowVar();
				wfv.setInstanceId(processInstance.getId());
				wfv.setCreateTime(new Timestamp(Calendar.getInstance()
						.getTimeInMillis()));
				wfv.setTitle(title);
				wfv.setProcessAttributeId(processId);
				wfv.setCreater(ui.getLoginName());
				wfv.setCurrentTaskName("发起流程");
				wfv.setCurrentUser(ui.getUserName());  
				wfv.setRefprocess(ui.getUserName());
				wfv.setCompanyId(ui.getCompanyId());
				//调用【我的申请】模块“我的申请”的API，将工作流数据转存在【我的申请表】中，供手机端查询使用
				//add by jiayq,2014-8-21
				//update by jiayq 2014-10-9
				int categoryId = pa.getCategoryId();
				FormCategory fc = formCategoryService.findOne(categoryId);
				wfv.setCategoryName(fc.getCategoryName());
				
				workflowVarDao.updateWorkflowVar(wfv);
				Task task = processEngine.getTaskService().createTaskQuery()
						.processInstanceId(processInstance.getId())
						.uniqueResult();
				
				myApplyService.createProcess(ui, fc.getCategoryName(), title, processInstanceId,  ModuleCode.WORKFLOW);
				
				if (task != null) {
					try {
						completeTask(ui, task.getId(), attachJSON,
								formDataJSON, advice, nextAction, nextUsers);
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		
		return processInstanceId;
	}

	/**
	 * 功能：生成流程名字
	 * @param userInfo
	 * @param pa
	 * @return
	 */
	@Override
	public String generateProcessInstanceName(UserInfo userInfo, ProcessAttribute pa) {
		return getGenerateProcessInstanceName(userInfo, pa);
	}

	/**
	 * 功能：生成流程名字
	 * @param userInfo
	 * @param pa
	 * @return
	 */
	private String getGenerateProcessInstanceName(UserInfo userInfo,
			ProcessAttribute processAttribute2) {
		String userName = userInfo.getUserName();
		String categoryName = "";
		String groupName = getGroupNameByUserInfo(userInfo);
		if (processAttribute2 != null) {
			categoryName = processAttribute2.getProcessName();
		}
		if(processAttribute2!=null){
			String res = processAttribute2.generateProcessInstanceName(userName,
					groupName, categoryName);
			return res;
		}else{
			return "";
		}
	}

	/**
	 * 功能：根据用户信息获得部门名称
	 * @param userInfo
	 * @return
	 */
	private String getGroupNameByUserInfo(UserInfo userInfo) {
		String res = "";
		GroupInfo g = groupService.findOne(userInfo.getGroupId());
		res = g.getGroupName();
		return res;
	}

	/**
	 * 功能：获得审批数量
	 * @param userIdOrLoginName
	 * @return
	 */
	@Override
	public int getApproveCount(String userIdOrLoginName) {
		return workFlowService.getApproveCount(userIdOrLoginName);
	}

	/**
	 * 功能：根据流程id获得审批意见
	 * @param instanceId
	 * @return
	 */
	@Override
	public List<JbpmAdvice> findTdAdvicebyInstanceId(String instanceId) {
		// TODO Auto-generated method stub
		String tdAdvice = "";
		List<JbpmAdvice> tdAdvicelist = new ArrayList<JbpmAdvice>();
		ProcessInstance pi = processEngine.getExecutionService()
				.createProcessInstanceQuery().processInstanceId(instanceId)
				.uniqueResult();
		if (pi == null) {
			tdAdvice = (String) processEngine.getHistoryService().getVariable(
					instanceId, "tdAdvice");
		} else {
			tdAdvice = (String) processEngine.getExecutionService()
					.getVariable(instanceId, "tdAdvice");
		}
		Gson gson = new Gson();
		if (tdAdvice != null && !tdAdvice.equals("")) {
			tdAdvicelist = gson.fromJson(tdAdvice,
					new TypeToken<List<JbpmAdvice>>() {
					}.getType());
		}
		return tdAdvicelist;
	}

	/**
	 * 功能：根据流程id获得阅读人员名字
	 * @param instanceId
	 * @return
	 */
	@Override
	public String[] findReaderNamesByInstanceId(String instanceId) {
		// TODO Auto-generated method stub
		String readerName = "";
		ProcessInstance pi = processEngine.getExecutionService()
				.createProcessInstanceQuery().processInstanceId(instanceId)
				.uniqueResult();
		if (pi == null) {
			readerName = (String) processEngine.getHistoryService()
					.getVariable(instanceId, "readerName");
		} else {
			readerName = (String) processEngine.getExecutionService()
					.getVariable(instanceId, "readerName");
		}
		if (readerName != null) {
			return readerName.split(",");
		}
		return new String[0];
	}

	/**
	 * 功能：下载表单（带数据）
	 * @param processId
	 * @param instanceId
	 * @return
	 */
	@Override
	public File downloadFormWithData(int processId,String instanceId) {
		// TODO Auto-generated method stub
		ProcessAttribute pa = processAttributeService.findOne(processId);
		Integer formId = pa.getFormId();
		if(formId==null){
			return null;
		}else{
			File f = DomContentUtil.getCustomform(formId, instanceId, "", instanceId);
			return f;
		}
	}

	/**
	 * 功能：根据流程id获得当前节点
	 * @param instanceId
	 * @return
	 */
	@Override
	public NodeFormAttribute findCurrentNodeByInstanceId(String instanceId) {
		// TODO Auto-generated method stub
		List<Task> tasklist = processEngine.getTaskService().createTaskQuery().processInstanceId(instanceId).list();
		if(tasklist!=null){
			String nodeName = tasklist.get(0).getName();
			String processAttributeId = (String) processEngine.getExecutionService().getVariable(instanceId, "processAttributeId");
			int processId = Integer.parseInt(processAttributeId);
			NodeFormAttribute nfa = nodeFormAttributeDao.findByProcessIdAndName(processId, nodeName);
			return nfa;
		}
		return null;
	}

	@Override
	public void updateFormData(String taskId, String formData) {
		// TODO Auto-generated method stub
		TaskImpl task = (TaskImpl) processEngine.getTaskService().getTask(taskId);
		String instanceId = task.getProcessInstance().getId();
		String processIdStr = processEngine.getExecutionService()
				.getVariable(instanceId, "processAttributeId")
				.toString();
		ProcessAttribute pa = processAttributeService.getProcessById(Integer
				.parseInt(processIdStr));

		Integer formId = pa.getFormId();
		
		// 更新表单数据
		if (formId != null && formData != null && !"".equals(formData)) {
			formPropertyValueService.saveOrUpdate(formData,instanceId, formId);
		}
	}
}
