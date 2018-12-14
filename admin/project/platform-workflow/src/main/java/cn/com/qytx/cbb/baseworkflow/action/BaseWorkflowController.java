package cn.com.qytx.cbb.baseworkflow.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.com.qytx.cbb.baseworkflow.domain.BaseWorkflow;
import cn.com.qytx.cbb.baseworkflow.model.ViewModule;
import cn.com.qytx.cbb.baseworkflow.service.BMyProcessed;
import cn.com.qytx.cbb.baseworkflow.service.BMyStarted;
import cn.com.qytx.cbb.baseworkflow.service.BMyWaitApprove;
import cn.com.qytx.cbb.baseworkflow.service.BaseWorkflowService;
import cn.com.qytx.cbb.baseworkflow.service.impl.BaseWorkflowServiceImpl.ApproveUser;
import cn.com.qytx.cbb.jbpmApp.action.mobile.OptionAction;
import cn.com.qytx.cbb.myapply.domain.MyWaitProcess;
import cn.com.qytx.cbb.myapply.service.IMyWaitProcess;
import cn.com.qytx.platform.base.action.BaseControllerSupport;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.PageRequest;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.query.Sort;
import cn.com.qytx.platform.base.query.Sort.Direction;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

/**
 * @author jiayongqiang
 * 手机端固定流程SpringMVC接口
 */
@Controller
@RequestMapping(value="/baseworkflow")
public class BaseWorkflowController extends BaseControllerSupport {
	private static final Logger logger=LoggerFactory.getLogger(BaseWorkflowController.class);
	@Resource
	private BaseWorkflowService baseWorkflowService;
	@Resource
	private IMyWaitProcess myWaitProcessService;
	
	/**
	 * 功能：提交申请
	 * @param userId 登录人id
	 * @param formData 表单内容
	 * @param userIds 流程下一步人员
	 * @param code 表单类型： qingjia/shenpi.....
	 * @param response
	 */
	@RequestMapping("/start.c")
	public void start(@RequestParam(value="userId",required=true)Integer userId,
						@RequestParam(value="formData",required=true)String formData,
						@RequestParam(value="userIds",required=true)String userIds,
						@RequestParam(value="code",required=true)String code,
						HttpServletResponse response){
		
		String instanceId = baseWorkflowService.start(userId, formData, userIds, code);
		//获取审批人员信息
		List<Map<String,String>> processerUser=new ArrayList<Map<String,String>>();
		if(StringUtils.isNotBlank(instanceId)){
			List<MyWaitProcess> waitList=myWaitProcessService.findByInstanceId(instanceId);
			if(waitList!=null&&waitList.size()>0){
				for(MyWaitProcess myWaitProcess:waitList){
					Map<String,String> map=new HashMap<String,String>();
					map.put("userName", myWaitProcess.getProcesserName());
					map.put("userId", myWaitProcess.getProcesserId().toString());
					processerUser.add(map);
				}
			}
		}
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("instanceId", instanceId);
		map.put("processerUser", processerUser);
		SUCCESS(map,response);
	}
	
	
	/**
	 * 审批
	 * @param instanceId 流程id
	 * @param approveResult 0通过,1驳回，2撤销
	 * @param userId 审批人id
	 * @param advice 审批意见
	 * @return
	 */
	@RequestMapping("/approve.c")
	public void approve(@RequestParam(value="instanceId",required = true)String instanceId,
						@RequestParam(value="approveResult",required=true)String approveResult,
						@RequestParam(value="userId",required=true)int userId,
						@RequestParam(value="advice",required=false)String advice,
						HttpServletResponse response){
		baseWorkflowService.approve(userId, instanceId, advice, approveResult);
		SUCCESS(response);
	}
	
	/**
	 * 功能：查看详情
	 * @param instanceId
	 * @param response
	 */
	@RequestMapping("/view.c")
	public void view(@RequestParam(value="instanceId",required=true)String instanceId,HttpServletResponse response){
		ViewModule vm = baseWorkflowService.findDetailInfoByInstanceId(instanceId);
		SUCCESS(vm,response);
	}
	
	/**
	 * 功能：删除流程
	 * @param instanceId
	 * @param response
	 */
	@RequestMapping("/delete.c")
	public void delete(@RequestParam(value="instanceId",required=true)String instanceId,HttpServletResponse response){
		baseWorkflowService.deleteByInstanceId(instanceId);
		SUCCESS(response);
	}

	/**
	 * 功能：转交
	 * @param instanceId 流程id
	 * @param userId 操作人id
	 * @param turner 转交人，转交对象
	 * @param advice 意见
	 * @param response
	 */
	@RequestMapping("/turn.c")
	public void turn(@RequestParam(value="instanceId",required=true)String instanceId,
					   @RequestParam(value="userId",required=true)Integer userId,
					   @RequestParam(value="turner",required=true)String turner,
					   @RequestParam(value="advice",required=false)String advice,
					   HttpServletResponse response) {
		baseWorkflowService.turn(userId, turner, instanceId, advice);
		SUCCESS(response);
	}
	
	/**
	 * 我的申请列表
	 */
	@RequestMapping("/myStarted.c")
	public void myStarted(@RequestParam(value="iDisplayStart",required=true,defaultValue="0")Integer iDisplayStart,
							@RequestParam(value="iDisplayLength",required=true,defaultValue="15")Integer iDisplayLength,
							@RequestParam(value="userId",required=true)Integer userId,
							@RequestParam(value="moduleCode",required=false)String moduleCode,
							HttpServletResponse response){
		long t1=System.currentTimeMillis();
		logger.info(Thread.currentThread().getName()+"开始获取我的申请列表，操作人员userId="+userId+",开始时间t1="+t1);
		Sort sort = new Sort(Direction.DESC,"createrTime");
		Pageable page = getPageable(iDisplayStart, iDisplayLength,sort);
		Page<BMyStarted> list = baseWorkflowService.findByStartedList(page, userId,moduleCode);
		long t2=System.currentTimeMillis();
		logger.info(Thread.currentThread().getName()+"已获取我的申请列表数据,当前时间t2="+t2+",间隔时间t2-t1="+(t2-t1));
		SUCCESS_PAGE(list,list.getContent(),response);
	}

	/**
	 * 待审批列表
	 */
	@RequestMapping("/myWaitProcess.c")
	public void myWaitProcess(@RequestParam(value="iDisplayStart",required=true,defaultValue="0")Integer iDisplayStart,
			@RequestParam(value="iDisplayLength",required=true,defaultValue="15")Integer iDisplayLength,
			@RequestParam(value="userId",required=true)Integer userId,
			@RequestParam(value="moduleCode",required=false)String moduleCode,
			HttpServletResponse response){
		long t1=System.currentTimeMillis();
		logger.info(Thread.currentThread().getName()+"开始获取待审批列表，操作人员userId="+userId+",开始时间t1="+t1);
		Sort sort = new Sort(Direction.ASC,"startTime");
		Pageable page = getPageable(iDisplayStart, iDisplayLength, sort);
		Page<BMyWaitApprove> list = baseWorkflowService.findByWaitApproveList(page, userId,moduleCode);
		long t2=System.currentTimeMillis();
		logger.info(Thread.currentThread().getName()+"已获取待审批列表数据,当前时间t2="+t2+",间隔时间t2-t1="+(t2-t1));
		SUCCESS_PAGE(list,list.getContent(),response);
	}
	
	/**
	 * 已审批列表
	 */
	@RequestMapping("/myProcessed.c")
	public void myProcessed(@RequestParam(value="iDisplayStart",required=true,defaultValue="0")Integer iDisplayStart,
			@RequestParam(value="iDisplayLength",required=true,defaultValue="15")Integer iDisplayLength,
			@RequestParam(value="userId",required=true)Integer userId,
			@RequestParam(value="moduleCode",required=false)String moduleCode,
			HttpServletResponse response){
		long t1=System.currentTimeMillis();
		logger.info(Thread.currentThread().getName()+"开始获取已审批列表，操作人员userId="+userId+",开始时间t1="+t1);
		Sort sort = new Sort(Direction.DESC,"endTime");
		Pageable page = getPageable(iDisplayStart, iDisplayLength, sort);
		Page<BMyProcessed> list = baseWorkflowService.findMyProcessedList(page, userId,moduleCode);
		long t2=System.currentTimeMillis();
		logger.info(Thread.currentThread().getName()+"已获取已审批列表数据,当前时间t2="+t2+",间隔时间t2-t1="+(t2-t1));
		SUCCESS_PAGE(list,list.getContent(),response);
	}
	
	/**
	 * 获取待审批的数量
	 * @return
	 */
	@RequestMapping("/myWaitCount.c")
	public void myWaitCount(@RequestParam(value="userId",required=true)Integer userId,
						@RequestParam(value="moduleCode",required=false)String moduleCode,
						HttpServletResponse response){
		//获取待我审批的数量
		long t1=System.currentTimeMillis();
		logger.info(Thread.currentThread().getName()+"开始获取待审批的数量，操作人员userId="+userId+",开始时间t1="+t1);
		int count=baseWorkflowService.myWaitCount(userId, moduleCode);
		long t2=System.currentTimeMillis();
		logger.info(Thread.currentThread().getName()+"获取待审批的数量结果，count="+count+",当前时间t2="+t2+",间隔时间t2-t1="+(t2-t1));
		SUCCESS(count,response);
	}
	
	/**
	 * 校验转交人是否重复
	 * @param response
	 * @param turnUserId
	 */
	@RequestMapping("/checkTurnerIsRepeat.c")
	public void checkTurnerIsRepeat(HttpServletResponse response,
							@RequestParam(value="turnUserId",required=true) String turnUserId,
							@RequestParam(value="instanceId",required=true) String instanceId){
		BaseWorkflow bwf = baseWorkflowService.findOne("instanceId = ?", instanceId);
		String us = bwf.getUserIds();
		List<ApproveUser> approveusers = new Gson().fromJson(us, new TypeToken<List<ApproveUser>>(){}.getType());
		boolean flag = false;
		for(int i=0; i<approveusers.size(); i++){
			ApproveUser au = approveusers.get(i);
			if(au.getUserId().equals(turnUserId)){
				flag = true;
				break;
			}
		}
		SUCCESS("{result:"+flag+"}", response);
	}
	
	private Pageable getPageable(int iDisplayStart,int iDisplayLength,Sort sort){
		int page = iDisplayStart / iDisplayLength ;
		Pageable pageable = new PageRequest(page,iDisplayLength,sort);
		return pageable;
	}
}
