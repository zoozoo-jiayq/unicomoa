package cn.com.qytx.cbb.jbpmApp.action;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cn.com.qytx.cbb.customForm.domain.FormCategory;
import cn.com.qytx.cbb.customForm.service.IFormCategory;
import cn.com.qytx.cbb.customJpdl.action.ProcessCategory;
import cn.com.qytx.cbb.customJpdl.domain.ProcessAttribute;
import cn.com.qytx.cbb.customJpdl.service.JpdlInterface;
import cn.com.qytx.cbb.customJpdl.service.ProcessAttributeService;
import cn.com.qytx.cbb.jbpmApp.domain.WorkFlowView;
import cn.com.qytx.cbb.jbpmApp.service.IJbpmApp;
import cn.com.qytx.cbb.jbpmApp.service.IWorkFlowService;
import cn.com.qytx.cbb.jbpmApp.service.impl.command.SearchWaitProcessTaskList;
import cn.com.qytx.cbb.jbpmApp.util.TimeFormatUtil;
import cn.com.qytx.cbb.systemSet.service.SysConfigService;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IUser;
import cn.com.qytx.platform.utils.StringUtil;

/**
 * 功能：查询工作流中的所有列表
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 下午4:27:54 
 * 修改日期：下午4:27:54 
 * 修改列表：
 */
public class WorkflowListAction extends BaseActionSupport {

	/** 表单类别服务类 */
	@Resource(name="formCategoryService")
	private IFormCategory formCategoryService;
	@Resource(name="processAttributeService")
	private ProcessAttributeService processAttributeService;
	@Resource(name = "workFlowService")
	IWorkFlowService workFlowService;  //工作流服务
    @Resource(name = "userService")
    IUser userService;
	@Resource(name="jbpmAppService")
	private IJbpmApp jbpmAppService;
    @Resource
    private SysConfigService sysConfigService;
    
	private int categoryId;
	
	//流程类型；2工作流分类；12公文分类，默认是2
	private Integer type;
	
	//1，收文；2发文
	private Integer domType;
	
	private String searchkey;
	
	private String beginDate;
	private String endDate;
	//流程Id
	private Integer processattributeid;
	
	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getSearchkey() {
		return searchkey;
	}

	public void setSearchkey(String searchkey) {
		this.searchkey = searchkey;
	}

	public Integer getDomType() {
		return domType;
	}

	public void setDomType(Integer domType) {
		this.domType = domType;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}


	public void setProcessattributeid(Integer processattributeid) {
		this.processattributeid = processattributeid;
	}

	public void setInstanceId(Integer instanceId) {
		this.processattributeid = instanceId;
	}

	/**
	 * 功能：查询工作流分类
	 * @param
	 * @return
	 * @throws   
	 */
	public String often(){
			if(type == null){
				type = FormCategory.CATEGORY_TYPE_PROCESS;
			}
			return "often";
	}
	
	/**
	 * 功能：获取所有流程分类，及其流程定义
	 * @param
	 * @return
	 * @throws   
	 */
	public List<ProcessCategory> getProcssCategorys() throws Exception{
		List<ProcessCategory> list = new ArrayList<ProcessCategory>();
		UserInfo user = getLoginUser();
		//获取所有分类
		List<FormCategory> categories = formCategoryService.findByTypeCompanyId(user.getCompanyId(), type);
		for(int i=0;i<categories.size();i++){
			ProcessCategory pcCategory = new ProcessCategory(null,categories.get(i));
			try{
				List<ProcessAttribute> temp = processAttributeService.findProcessAttributesByPermissions(getLoginUser(), categories.get(i).getCategoryId(), null);
				if(temp!=null){
					pcCategory.setCount(temp.size());
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			list.add(pcCategory);
			
		}
		for(Iterator<ProcessCategory> it = list.iterator(); it.hasNext();){
			if(it.next().getCount() == 0 ){
				it.remove();
			}
		}
		return list;
	}
	
	  /**
	 * 功能：查询工作流定义列表
	 * @param
	 * @return
	 * @throws   
	 */
	public String categorylist() {
	        return "categorylist";
	  }
	
	/**
	 * 功能：我发起的工作
	 * @return
	 */
	public String startTaskList(){
		List<UserInfo> userInfolist = userService.unDeleted().findAll();
		UserInfo ui = getLoginUser();
        Page<WorkFlowView> page= workFlowService.findByStartList(ui.getLoginName(),searchkey, getPageable());
        List<WorkFlowView> viewlist =page.getContent();
	   //把信息填充到map里面
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		if(viewlist!=null){
			for(int i=0; i<viewlist.size(); i++){
				WorkFlowView hisPro = viewlist.get(i);
				String createTime=new SimpleDateFormat("yyyy-MM-dd HH:mm").format(hisPro.getProcessStart());//  DateUtils.date2ShortStr(hisPro.getStartTime());//创建时间
            	Map<String, Object> map = new HashMap<String, Object>();
            	map.put("no", this.getIDisplayStart()+1+i);
                map.put("createTime",createTime );
                map.put("processType",hisPro.getProcessType());
                map.put("processInstanceName",hisPro.getJobName() );
                map.put("processInstanceId",hisPro.getInstanceId());
                map.put("categoryName", hisPro.getCategoryName());
                map.put("title",hisPro.getJobName()==null?"":hisPro.getJobName());
                UserInfo user = getUserNameByUserId(hisPro.getCreater(),userInfolist);
                if(user!=null){
                	map.put("userName",user.getUserName() );
                }else{
                	map.put("userName","");
                }
                map.put("state",hisPro.getCurrentState() );
                map.put("executionId",hisPro.getInstanceId() );
                map.put("taskId","");
                map.put("processId",hisPro.getProcessId());
                map.put("taskName",hisPro.getCurrentTaskName());
                if(!"结束".equals(hisPro.getCurrentTaskName())){
                	if(StringUtil.isEmpty(hisPro.getCurrentUser())){
                		map.put("taskNameShow",hisPro.getCurrentTaskName());
                	}else{
                		map.put("taskNameShow",hisPro.getCurrentTaskName()+"-"+hisPro.getCurrentUser());
                	}
                }else{
                	map.put("taskNameShow",hisPro.getCurrentTaskName());
                }
                
                //判断是否可以撤销，只要流程没有结束就可以撤销
                String isCanCancel = "yes";
                if(hisPro.getCurrentState().equals(JpdlInterface.PROCESS_STATE_END) || hisPro.getCurrentState().equals(JpdlInterface.PROCESS_STATE_ROLLBACK)
                		|| hisPro.getCurrentState().equals(JpdlInterface.PROCESS_STATE_REPEAL)){
                	isCanCancel = "no";
                }
                map.put("isCanCancle", isCanCancel);
                
                //判断是否可以重新发起，只有撤销的和驳回的可以重新发起
                String isCanRedo = "no";
                if(hisPro.getCurrentState().equals(JpdlInterface.PROCESS_STATE_ROLLBACK) || hisPro.getCurrentState().equals(JpdlInterface.PROCESS_STATE_REPEAL)){
                	isCanRedo = "yes";
                }
                map.put("isCanRedo", isCanRedo);
                map.put("taskStart", getArrivalTime(hisPro));
                mapList.add(map);
			}
			ajaxPage(page, mapList);
		} 
        return null;
	}
	
	
	/**
	 * 功能：查询请假
	 * @return
	 */
	public String startAllTaskList(){
		List<UserInfo> userInfolist = userService.unDeleted().findAll();
		UserInfo ui = getLoginUser();
        Page<WorkFlowView> page= workFlowService.findByStartAllList(ui.getLoginName(),searchkey, getPageable(),processattributeid);
        List<WorkFlowView> viewlist =page.getContent();
	   //把信息填充到map里面
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		if(viewlist!=null){
			for(int i=0; i<viewlist.size(); i++){
				WorkFlowView hisPro = viewlist.get(i);
				String createTime=new SimpleDateFormat("yyyy-MM-dd HH:mm").format(hisPro.getProcessStart());//  DateUtils.date2ShortStr(hisPro.getStartTime());//创建时间
            	Map<String, Object> map = new HashMap<String, Object>();
            	map.put("no", this.getIDisplayStart()+1+i);
                map.put("createTime",createTime );
                map.put("processType",hisPro.getProcessType());
                map.put("processInstanceName",hisPro.getJobName() );
                map.put("processInstanceId",hisPro.getInstanceId());
                map.put("categoryName", hisPro.getCategoryName());
                map.put("title",hisPro.getJobName()==null?"":hisPro.getJobName());
                UserInfo user = getUserNameByUserId(hisPro.getCreater(),userInfolist);
                if(user!=null){
                	map.put("userName",user.getUserName() );
                }else{
                	map.put("userName","");
                }
                map.put("state",hisPro.getCurrentState() );
                map.put("executionId",hisPro.getInstanceId() );
                map.put("taskId","");
                map.put("processId",hisPro.getProcessId());
                map.put("taskName",hisPro.getCurrentTaskName());
                if(!"结束".equals(hisPro.getCurrentTaskName())){
                	if(StringUtil.isEmpty(hisPro.getCurrentUser())){
                		map.put("taskNameShow",hisPro.getCurrentTaskName());
                	}else{
                		map.put("taskNameShow",hisPro.getCurrentTaskName()+"-"+hisPro.getCurrentUser());
                	}
                }else{
                	map.put("taskNameShow",hisPro.getCurrentTaskName());
                }
                
                //判断是否可以撤销，只要流程没有结束就可以撤销
                String isCanCancel = "yes";
                if(hisPro.getCurrentState().equals(JpdlInterface.PROCESS_STATE_END) || hisPro.getCurrentState().equals(JpdlInterface.PROCESS_STATE_ROLLBACK)
                		|| hisPro.getCurrentState().equals(JpdlInterface.PROCESS_STATE_REPEAL)){
                	isCanCancel = "no";
                }
                map.put("isCanCancle", isCanCancel);
                
                //判断是否可以重新发起，只有撤销的和驳回的可以重新发起
                String isCanRedo = "no";
                if(hisPro.getCurrentState().equals(JpdlInterface.PROCESS_STATE_ROLLBACK) || hisPro.getCurrentState().equals(JpdlInterface.PROCESS_STATE_REPEAL)){
                	isCanRedo = "yes";
                }
                map.put("isCanRedo", isCanRedo);
                map.put("taskStart", getArrivalTime(hisPro));
                mapList.add(map);
			}
			ajaxPage(page, mapList);
		} 
        return null;
	}
	
	
	
	/** 获取格式化后的已停留时间
	 * @param view
	 * @return
	 */
	private String getArrivalTime(WorkFlowView view){
		if(view.getCurrentState().equals(JpdlInterface.PROCESS_STATE_APPROVE)){
			Timestamp taskStart = view.getTaskStart();
			if(taskStart == null){
				return "";
			}else{
				long times = Calendar.getInstance().getTimeInMillis()-taskStart.getTime();
				return TimeFormatUtil.formatTime(times);
			}
		}else{
			return "";
		}
	}
	
	/**
	 * 功能：办理中的工作
	 * @return
	 */
	public String receivedTaskList(){
        	List<UserInfo> userInfolist = userService.unDeleted().findAll();
        	UserInfo ui = getLoginUser();
        	Page<WorkFlowView> page=workFlowService.findWaitProcessViewlist(ui.getLoginName(),SearchWaitProcessTaskList.PROCESS_STATE_ACTIVE,searchkey, getPageable());
            List<WorkFlowView> list =page.getContent();
            List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
            if (list != null) {
                for(int i=0; i<list.size(); i++) {
                	WorkFlowView taskView = list.get(i);
                	String recivedTime=taskView.getTaskStart()==null?"":new SimpleDateFormat("yyyy-MM-dd  HH:mm").format(taskView.getTaskStart());//创建时间
                	String processType=taskView.getProcessType();
                	String processInstanceName=taskView.getJobName();
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("no", getIDisplayStart()+i+1);
                    map.put("createTime",new SimpleDateFormat("yyyy-MM-dd  HH:mm").format(taskView.getProcessStart()));
                    map.put("processType",processType);
                    map.put("processInstanceName",processInstanceName );
                    map.put("categoryName", taskView.getCategoryName());
                    UserInfo ut = getUserNameByUserId(taskView.getCreater(), userInfolist);
                    if(ut!=null){
                    	map.put("userName",ut.getUserName() );
                    }else{
                    	map.put("userName","");
                    }
                    map.put("taskId",taskView.getTaskId());
                    map.put("executionId",taskView.getInstanceId());
                    map.put("processId",taskView.getProcessId());
                    String taskName = taskView.getCurrentTaskName();
                    map.put("taskName",taskName);
                    map.put("breforeTaskName",taskView.getBeforeTaskName()+"-"+taskView.getBeforeUser());
                    if(JpdlInterface.END_NODE_NAME.equals(taskName)){
                    	map.put("taskNameShow",taskName);
                    }else{
                    	if(StringUtil.isEmpty(taskView.getCurrentUser())){
                    		map.put("taskNameShow",taskName);
                    	}else{
                    		map.put("taskNameShow",taskName+"-"+taskView.getCurrentUser());
                    	}
                    }
                    map.put("firstTaskName","发起流程"); 
                    map.put("title",taskView.getJobName()); 
                    map.put("recivedTime",recivedTime);  
                    
                    map.put("taskStart", getArrivalTime(taskView));
                    mapList.add(map);
                }
                ajaxPage(page, mapList);
            }
		return null;
	}
	
	/**
	 * 功能：已经挂起的工作
	 * @return
	 */
	public String suspendTaskList(){
		List<UserInfo> userInfolist = userService.unDeleted().findAll();
        //得到登录用户
        UserInfo adminUser=(UserInfo)this.getSession().getAttribute("adminUser");
        if(adminUser!=null){
        	Page<WorkFlowView> page= workFlowService.findWaitProcessViewlist(adminUser.getLoginName(),SearchWaitProcessTaskList.PROCESS_STATE_SUSPEND,searchkey, getPageable());
            List<WorkFlowView> list =page.getContent();
            List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
            if (list != null) {
                for(int i=0; i<list.size(); i++) {
                	WorkFlowView taskView = list.get(i);
                	String recivedTime = "";
                	if(taskView.getSuspendTime()!=null){
                		recivedTime =new SimpleDateFormat("MM-dd  HH:mm").format(taskView.getSuspendTime());//创建时间
                	}
                	String processType=taskView.getProcessType();
                	String processInstanceName=taskView.getJobName();
                	
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("no", getIDisplayStart()+i+1);
                    map.put("createTime",new SimpleDateFormat("MM-dd  HH:mm").format(taskView.getProcessStart()));
                    map.put("processType",processType);
                    map.put("processInstanceName",processInstanceName );
                    map.put("userName",getUserNameByUserId(taskView.getCreater(), userInfolist).getUserName() );
                    map.put("taskId",taskView.getTaskId());
                    map.put("executionId",taskView.getInstanceId());
                    map.put("processId",taskView.getProcessId());
                    map.put("taskName",taskView.getCurrentTaskName());
                    map.put("breforeTaskName",taskView.getBeforeTaskName()+"-"+taskView.getBeforeUser());
                    String taskName = taskView.getCurrentTaskName();
                    if(JpdlInterface.END_NODE_NAME.equals(taskName)){
                    	map.put("taskNameShow",taskName);
                    }else{
                    	map.put("taskNameShow",taskName+"-"+taskView.getCurrentUser());
                    }
                    map.put("firstTaskName","发起流程"); 
                    map.put("title",taskView.getJobName()); 
                    map.put("recivedTime",recivedTime);  
                    mapList.add(map);
                }
              ajaxPage(page,mapList);
            }
          
        }
	return null;		
	}
	
	/**
	 * 功能：已经完成的工作
	 * @return
	 */
	public String completedTaskList(){
        //得到登录用户
        UserInfo adminUser=(UserInfo)this.getSession().getAttribute("adminUser");
        if(adminUser!=null){
        	Page<WorkFlowView> page=workFlowService.findByApprovedList(adminUser.getLoginName(),searchkey,getPageable());
            List<WorkFlowView> list = page.getContent();
            List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
            if (list != null) {
                for(int i=0; i<list.size(); i++) {
                	WorkFlowView taskView = list.get(i);
                	String recivedTime=new SimpleDateFormat("yyyy-MM-dd  HH:mm").format(taskView.getTaskEnd());//创建时间
                	String processType=taskView.getProcessType();
                	String processInstanceName=taskView.getJobName();
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("no", getIDisplayStart()+i+1);
                    if(taskView.getProcessStart()==null){
                    	map.put("createTime","");
                    }else{
                    	map.put("createTime",new SimpleDateFormat("yyyy-MM-dd  HH:mm").format(taskView.getProcessStart()));
                    }
                    map.put("processType",processType);
                    map.put("processInstanceName",processInstanceName );
                    map.put("userName",taskView.getCreater() );
                    map.put("taskId",taskView.getTaskId());
                    map.put("executionId",taskView.getInstanceId());
                    map.put("processId",taskView.getProcessId());
                    map.put("taskName",taskView.getCurrentTaskName());
                    map.put("breforeTaskName",taskView.getBeforeTaskName()+"-"+taskView.getBeforeUser());
                    map.put("categoryName", taskView.getCategoryName());
                    String taskName = taskView.getCurrentTaskName();
                    if(JpdlInterface.END_NODE_NAME.equals(taskName)){
                    	map.put("taskNameShow",taskName);
                    }else{
                    	if(StringUtil.isEmpty(taskView.getCurrentUser())){
                    		map.put("taskNameShow",taskName);
                    	}else{
                    		map.put("taskNameShow",taskName+"-"+taskView.getCurrentUser());
                    	}
                    }
                    map.put("firstTaskName","发起流程"); 
                    map.put("title",taskView.getJobName()==null?"":taskView.getJobName()); 
                    map.put("recivedTime",recivedTime); 
                    map.put("state", taskView.getCurrentState());
                    map.put("taskStart", getArrivalTime(taskView));
                    mapList.add(map);
                }
                ajaxPage(page, mapList);
            }
        }
        return null;	
	}
	/**
	 * 功能：已经办结的工作
	 * @return
	 */
	public String endTaskList(){
        //得到登录用户
        UserInfo adminUser=(UserInfo)this.getSession().getAttribute("adminUser");
        if(adminUser!=null){
        	Page<WorkFlowView> page=workFlowService.findByendList(null,searchkey, beginDate, endDate, getPageable());
            List<WorkFlowView> list = page.getContent();
            List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
            if (list != null) {
                for(int i=0; i<list.size(); i++) {
                	WorkFlowView taskView = list.get(i);
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("no", getIDisplayStart()+i+1);
                    map.put("createTime",new SimpleDateFormat("yyyy-MM-dd  HH:mm").format(taskView.getProcessStart()));
                    map.put("processType",taskView.getProcessType());
                    map.put("userName",taskView.getCreater() );
                    map.put("executionId",taskView.getInstanceId());
                    map.put("processId",taskView.getProcessId());
                    map.put("title",taskView.getJobName()); 
                    map.put("endTime",new SimpleDateFormat("yyyy-MM-dd  HH:mm").format(taskView.getTaskEnd()));
                    mapList.add(map);
                }
                ajaxPage(page, mapList);
            }
        }
        return null;	
	}
	
	/**监控列表
	 * @return
	 */
	public String listenerlist() throws Exception{
		List<UserInfo> userInfolist = userService.unDeleted().findAll();
		Page<WorkFlowView> viewlist = workFlowService.findListenerlist(searchkey, beginDate, endDate, getPageable());
		List<WorkFlowView> list =viewlist.getContent();
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        if (list != null) {
            for(int i=0; i<list.size(); i++) {
            	WorkFlowView taskView = list.get(i);
            	//String recivedTime=new SimpleDateFormat("yyyy-MM-dd  HH:mm").format(taskView.getTaskStart());//创建时间
            	String processType=taskView.getProcessType();
            	String processInstanceName=taskView.getJobName();
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("no", getIDisplayStart()+i+1);
                map.put("createTime",new SimpleDateFormat("yyyy-MM-dd  HH:mm").format(taskView.getProcessStart()));
                map.put("processType",processType);
                map.put("processInstanceName",processInstanceName );
                map.put("categoryName", taskView.getCategoryName());
                UserInfo ut = getUserNameByUserId(taskView.getCreater(), userInfolist);
                if(ut!=null){
                	map.put("userName",ut.getUserName() );
                }else{
                	map.put("userName","");
                }
                map.put("taskId",taskView.getTaskId());
                map.put("executionId",taskView.getInstanceId());
                map.put("processId",taskView.getProcessId());
                String taskName = taskView.getCurrentTaskName();
                map.put("taskName",taskName);
               // map.put("breforeTaskName",taskView.getBeforeTaskName()+"-"+taskView.getBeforeUser());
                if(JpdlInterface.END_NODE_NAME.equals(taskName)){
                	map.put("taskNameShow",taskName);
                }else{
                	if(StringUtil.isEmpty(taskView.getCurrentUser())){
                		map.put("taskNameShow",taskName);
                	}else{
                		map.put("taskNameShow",taskName+"-"+taskView.getCurrentUser());
                	}
                }
                map.put("firstTaskName","发起流程"); 
                map.put("title",taskView.getJobName()); 
                //map.put("recivedTime",recivedTime);  
                
                map.put("taskStart", getArrivalTime(taskView));
                mapList.add(map);
            }
            ajaxPage(viewlist, mapList);
        }
		return null;
	}
	
	/**
	 * 功能：根据用户ID获取用户名
	 * @param userId
	 * @return
	 */
	private UserInfo getUserNameByUserId(String userId,List<UserInfo> userInfos) {
		if(userId == null){
			return null;
		}
		if(userId != null&&userId.indexOf("_")>-1){
			userId = userId.substring(userId.indexOf("_")+1);
		}
		if(userInfos!=null){
			for(int i=0; i<userInfos.size(); i++){
				UserInfo ui = userInfos.get(i);
				if(ui!=null && ui.getLoginName()!=null && ui.getLoginName().equals(userId)){
					return ui;
				}
			}
		}
		return null;
	}
	
	/**
	 * @return根据流程名称查询流程定义列表
	 */
	public String searchProcessDetail(){
		return "often";
	}
	
	public List<ProcessAttribute> getProcessAttributeList(){
		return processAttributeService.findProcessAttributesByPermissions(getLoginUser(), categoryId,searchkey);
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

}
