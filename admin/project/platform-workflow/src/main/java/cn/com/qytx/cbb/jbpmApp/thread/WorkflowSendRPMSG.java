package cn.com.qytx.cbb.jbpmApp.thread;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.qytx.cbb.customJpdl.service.JpdlInterface;
import cn.com.qytx.cbb.jbpmApp.action.mobile.jsonModel.ApproveHistory;
import cn.com.qytx.cbb.myapply.domain.MyProcessed;
import cn.com.qytx.cbb.myapply.domain.MyStarted;
import cn.com.qytx.cbb.myapply.domain.MyWaitProcess;
import cn.com.qytx.cbb.myapply.service.IMyProcessed;
import cn.com.qytx.cbb.myapply.service.IMyStarted;
import cn.com.qytx.cbb.myapply.service.IMyWaitProcess;
import cn.com.qytx.cbb.myapply.service.impl.MyProcessImpl;
import cn.com.qytx.cbb.redpoint.utils.RedPointUtil;
import cn.com.qytx.platform.utils.spring.SpringUtil;
import cn.jpush.api.JPushClient;

/**
 * 功能: 异步处理审批未读数量推送 
 * 版本: 1.0
 * 开发人员: zyf
 * 创建日期: 2015年3月20日
 * 修改日期: 2015年3月20日
 * 修改列表:
 */
public class WorkflowSendRPMSG implements Runnable {
	protected final static Logger LOGGER = LoggerFactory.getLogger(WorkflowSendRPMSG.class);
	
	private Object[] args;
	
	private String methodName;
	
	private String type;
	
	private Integer companyId;
	
	public WorkflowSendRPMSG(Object[] args,String methodName,String type,Integer companyId){
		this.args = args;
		this.methodName = methodName;
		this.type = type;
		this.companyId = companyId;
	}
	
	@Override
	public void run() {
		try {
			if(methodName.equals("saveOrUpdate")){//保存工作流待办事项
				MyWaitProcess wait = (MyWaitProcess) args[0];
				dealMyWait(wait);
			//保存工作流待办事项  end
			}else if(methodName.equals("saveList")){//批量保存工作流待办
				List<MyWaitProcess> list = (List<MyWaitProcess>) args[0];
				dealMyWaitList(list);
			//批量保存工作流待办 end
			}else if(methodName.equals("del")){//删除工作流待办事项
				List<Integer> list = (List<Integer>) args[2];
				if(list!=null && list.size()>0){
					Properties prop =  new Properties();
					prop.load(getClass().getResourceAsStream("/application.properties"));
					JPushClient jpush = new JPushClient(prop.getProperty("masterSecret"), prop.getProperty("appKey"));
					IMyWaitProcess myWaitProcessService = (IMyWaitProcess) SpringUtil.getBean("myWaitProcessService");
					for(Integer userId:list){
						Map<String, Object> extra = new HashMap<String, Object>();
						extra.put("mt", "redPoint");
						extra.put(type, myWaitProcessService.myWaitModuleCount(userId, null)+"");
						RedPointUtil.pushInfo(jpush, userId+"", extra,companyId);
					}
				}
			//删除工作流待办事项 end
			}else if(methodName.equals("approve") || methodName.equals("rollback")){//审批工作流
				String approveResult = (String) args[5];
				if(approveResult.equals(ApproveHistory.APPROVED) || approveResult.equals(ApproveHistory.TURN) || approveResult.equals(ApproveHistory.ROOLBACK) || approveResult.equals(JpdlInterface.PROCESS_STATE_ROLLBACK)){
					//审批 流转 拒绝
					String instanceId = (String) args[1];
					
					IMyStarted myStartedService = (IMyStarted) SpringUtil.getBean(IMyStarted.class);
					MyStarted myStarted = myStartedService.findByInstanceId(instanceId);
					
					Properties prop =  new Properties();
					prop.load(getClass().getResourceAsStream("/application.properties"));
					IMyWaitProcess myWaitProcessService = (IMyWaitProcess) SpringUtil.getBean("myWaitProcessService");
					
					JPushClient jpush = new JPushClient(prop.getProperty("masterSecret"), prop.getProperty("appKey"));
					
					//通知推送
					Map<String, Object> extraNotice = new HashMap<String, Object>();
					extraNotice.put("mt", "approveWait");
					extraNotice.put("id", instanceId);
					String title="新密市人民检察院办公平台";
					String content="你的审批已经被批复";
					RedPointUtil.pushInfoWithContent(jpush, myStarted.getCreaterId()+"", extraNotice,companyId, title, content);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 功能：处理待审批数量变更
	 * @param notify
	 */
	private void dealMyWait(MyWaitProcess wait) throws Exception{
		if(wait!=null){//新增的是工作流内容
			IMyProcessed myProcessedService = (IMyProcessed) SpringUtil.getBean(MyProcessImpl.class);
			List<MyProcessed> list = myProcessedService.findByInstanceId(wait.getInstanceId());
			if(list!=null && list.size()>0){
				Properties prop =  new Properties();
				prop.load(getClass().getResourceAsStream("/application.properties"));
				IMyWaitProcess myWaitProcessService = (IMyWaitProcess) SpringUtil.getBean("myWaitProcessService");
				
				JPushClient jpush = new JPushClient(prop.getProperty("masterSecret"), prop.getProperty("appKey"));
				int processerId = wait.getProcesserId();
				//红点推送
				Map<String, Object> extra = new HashMap<String, Object>();
				extra.put("mt", "redPoint");
				extra.put(type, myWaitProcessService.myWaitModuleCount(processerId, null)+"");
				RedPointUtil.pushInfo(jpush, processerId+"", extra,companyId);
			}
		}
	}
	
	/**
	 * 功能：处理待审批数量变更
	 * @param notify
	 */
	private void dealMyWaitList(List<MyWaitProcess> list) throws Exception{
		if(list!=null && list.size()>0){
			IMyProcessed myProcessedService = (IMyProcessed) SpringUtil.getBean(MyProcessImpl.class);
			List<MyProcessed> listP = myProcessedService.findByInstanceId(list.get(0).getInstanceId());
			if(listP!=null && listP.size()>0){
				for(MyWaitProcess wait:list){
					if(wait!=null ){//新增的是工作流内容
						Properties prop =  new Properties();
						prop.load(getClass().getResourceAsStream("/application.properties"));
						IMyWaitProcess myWaitProcessService = (IMyWaitProcess) SpringUtil.getBean("myWaitProcessService");
						
						JPushClient jpush = new JPushClient(prop.getProperty("masterSecret"), prop.getProperty("appKey"));
						int processerId = wait.getProcesserId();
//						System.out.println("新增审批推送目标："+processerId);
						//红点推送
						Map<String, Object> extra = new HashMap<String, Object>();
						extra.put("mt", "redPoint");
						extra.put(type, myWaitProcessService.myWaitModuleCount(processerId, null)+"");
						RedPointUtil.pushInfo(jpush, processerId+"", extra,companyId);
						//通知推送
						Map<String, Object> extraNotice = new HashMap<String, Object>();
						extraNotice.put("mt", "addWait");
						extraNotice.put("id", wait.getInstanceId());
						String title="新密市人民检察院办公平台";
						String content=wait.getCreaterName()+"给你发了一条审批";
						RedPointUtil.pushInfoWithContent(jpush, processerId+"", extraNotice,companyId, title, content);
					}
				}
			}
			
		}
	}

}
