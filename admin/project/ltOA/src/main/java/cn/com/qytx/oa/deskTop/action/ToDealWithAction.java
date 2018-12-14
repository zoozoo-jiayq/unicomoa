package cn.com.qytx.oa.deskTop.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import cn.com.qytx.cbb.myapply.service.IMyWaitProcess;
import cn.com.qytx.cbb.notify.domain.PlatformParameter;
import cn.com.qytx.cbb.notify.service.INotify;
import cn.com.qytx.cbb.notify.service.IPlatformParameter;
import cn.com.qytx.cbb.taskManager.service.ITaskService;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IUser;
import cn.com.qytx.platform.utils.JsonUtils;


public class ToDealWithAction extends BaseActionSupport{
	
	@Autowired
	private IUser userService;
	@Resource(name="notifyService")
	private INotify notifyService;
	@Autowired
	private IMyWaitProcess myWaitProcessService;
	
	@Resource(name="platformParameterService")
	private IPlatformParameter platformParameterService;
	/** 任务管理 */
	@Autowired
	ITaskService taskServiceImp;
	protected String userId ;
	
	public void dealWith(){
	  	Map<String,Object> returnMap = new HashMap<String, Object>();
		UserInfo user = userService.findOne(Integer.valueOf(userId));
		int size =0; //待处理公文个数
		//returnMap.put("domSize", size);
		
		String loginName = user.getLoginName();
//		int size2 =jbpmMobileService.getApproveCount(loginName);//待处理工作流个数
		int size2 = myWaitProcessService.myWaitCount(Integer.valueOf(userId));//获得所有待审批数量
		returnMap.put("jbpmSize",size2);
		
		int userIdInt = 0;
		if(userId!=null&&!"".equals(userId)){
			userIdInt = Integer.parseInt(userId);
		}
		//公告个数
		int notifyCount = notifyService.countOfNotReadNotify(userIdInt,1);
		returnMap.put("notifySize",notifyCount);

        List<PlatformParameter> pplist = platformParameterService.findAll();
		 if(pplist!=null&&pplist.size()>0){
			    for(PlatformParameter p : pplist){
				 String parItem = p.getParItems();
				 int instanceId = p.getInstenceid();
				// String code = p.getCode()!=null?p.getCode():"";
				 //判断是公告类型的
				 if("cn.com.qytx.cbb.notify.vo.TbColumnSetting".endsWith(parItem)){
						int nount = notifyService.countOfNotReadNotify(userIdInt,instanceId);
						returnMap.put("notifySize",nount);
				 }
			 }
	  }
	  

		//公文阅读数量
		List<String> nodeNames = new ArrayList<String>();
		nodeNames.add("收文阅读");
		returnMap.put("readSize",0);
		returnMap.put("domSize", 0);
		//问卷调查未做数量
		returnMap.put("questionCount",0);



			//承办人任务数量
		int taskCount = taskServiceImp.countOfTask(userId); 
		returnMap.put("taskCount",taskCount);

		ajax("100||"+JsonUtils.generJsonString(returnMap));
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
