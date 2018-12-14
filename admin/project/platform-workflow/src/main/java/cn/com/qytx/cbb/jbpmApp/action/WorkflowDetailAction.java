package cn.com.qytx.cbb.jbpmApp.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.jadira.usertype.spi.utils.lang.StringUtils;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.history.HistoryProcessInstance;
import org.jbpm.api.task.Task;

import cn.com.qytx.cbb.customForm.domain.FormAttribute;
import cn.com.qytx.cbb.customForm.domain.FormProperties;
import cn.com.qytx.cbb.customForm.service.IFormAttribute;
import cn.com.qytx.cbb.customForm.service.IFormProperties;
import cn.com.qytx.cbb.customForm.service.IFormPropertyValue;
import cn.com.qytx.cbb.customJpdl.domain.NodeFormAttribute;
import cn.com.qytx.cbb.customJpdl.domain.ProcessAttribute;
import cn.com.qytx.cbb.customJpdl.service.JpdlInterface;
import cn.com.qytx.cbb.customJpdl.service.NodeFormAttributeService;
import cn.com.qytx.cbb.customJpdl.service.ProcessAttributeService;
import cn.com.qytx.cbb.customWidget.vo.JbpmAdvice;
import cn.com.qytx.cbb.file.domain.Attachment;
import cn.com.qytx.cbb.jbpmApp.domain.AdviceHistory;
import cn.com.qytx.cbb.jbpmApp.domain.WorkflowVar;
import cn.com.qytx.cbb.jbpmApp.service.IJbpmApp;
import cn.com.qytx.cbb.jbpmApp.service.IWorkFlowService;
import cn.com.qytx.cbb.jbpmApp.service.IWorkflowVar;
import cn.com.qytx.cbb.jbpmApp.service.impl.JbpmNodeState;
import cn.com.qytx.cbb.publicDom.domain.DocumentExt;
import cn.com.qytx.cbb.publicDom.service.IDocumentExtService;
import cn.com.qytx.cbb.publicDom.util.AttachUtil;
import cn.com.qytx.cbb.publicDom.util.DocumentExtUtil;
import cn.com.qytx.cbb.systemSet.domain.SysConfig;
import cn.com.qytx.cbb.systemSet.service.SysConfigService;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IGroup;
import cn.com.qytx.platform.org.service.IUser;
import cn.com.qytx.platform.utils.StringUtil;
import cn.com.qytx.platform.utils.datetime.DateTimeUtil;
import cn.com.qytx.platform.utils.tree.SimpleTreeNode;

import com.google.gson.Gson;


/**
 * 功能：查询工作流中的细节
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 下午4:28:15 
 * 修改日期：下午4:28:15 
 * 修改列表：
 */
public class WorkflowDetailAction extends BaseActionSupport {

	private int processId;
	private String processInstanceId;
	private String taskId ;
	private int nodeId;
	private String taskName;
	private String redo;
	private String domflow;
	private String affireflag;
	private NodeFormAttribute defaultNextNode;
	//add by jiayq,新产生一个office文档的ID
	private Integer documentExtId;
	
	@Resource(name="processAttributeService")
	private ProcessAttributeService processAttributeService;
	@Resource(name="jbpmAppService")
	private IJbpmApp jbpmAppService;
	@Resource(name = "formAttributeService")
	private IFormAttribute formAttributeService;
	@Resource(name = "workFlowService")
	private IWorkFlowService workFlowService;
	@Resource
	private ProcessEngine processEngine;
	@Resource(name = "nodeFormAttributeService")
	private NodeFormAttributeService nodeFormAttributeService;
	@Resource(name = "formPropertiesService")
	private IFormProperties formPropertiesService;
	@Resource(name = "formPropertyValueService")
	private IFormPropertyValue formPropertyValueService;
	@Resource
	private SysConfigService sysConfigService;
	@Resource
	private IDocumentExtService documentExtService;
	@Resource
	private IUser userService;
	@Resource
	private IGroup groupService;
	@Resource
	private IWorkflowVar workflowVarService;
	
	/**
	 * 功能：进入查看流程图页面
	 * @param
	 * @return
	 * @throws   
	 */
	public String processDefineDetail(){
		return "processDefineDetail";
	}
	
	/**
	 * @throws Exception 
	 * 功能：判断用户是否有权限发起此流程
	 * @param
	 * @return
	 * @throws   
	 */
	public String isCanApply() throws Exception{
		boolean isCan = processAttributeService.isCanApply(getLoginUser(),processId);
		if(isCan){
			ajax("yes");
		}else{
			ajax("no");
		}
		return null;
	}
	
	/**
	 * 功能：进入发起流程申请页面
	 * @param
	 * @return
	 * @throws   
	 */
	public String toStartProcess() {
		//先清空session中保存的审批意见
		getSession().setAttribute("tdAdvice", null);
		
		//update by jiayq,公文和工作流集成,发起工作流的时候先创建一个空白的office文档
		String uuid = UUID.randomUUID().toString();
		try {
			documentExtId = DocumentExtUtil.generateDocumentExt(uuid, "workflowdom");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "applyBefore";
	}
	
	/**
	 * 功能：打印预览
	 * @param
	 * @return
	 * @throws   
	 */
	public String printView(){
		return "print";
	}
	
	/**
	 * 功能：判断流程名称是否可修改
	 * @param
	 * @return
	 * @throws   
	 */
	public String processNameCanupdate() throws Exception{
		ProcessAttribute pa = getProcessAttribute();
		int canUpdate = pa.getProcessNameCanupdate();
		ajax(""+canUpdate);
		return null;
	}
	
	/**
	 * @throws Exception 
	 * 功能：判断是否允许上传附件
	 * @param
	 * @return
	 * @throws   
	 */
	public String isEnableUpload() throws Exception{
		ProcessAttribute pro  = processAttributeService.getProcessById(processId);
		if(pro!=null){
			 int res = 0;
			 if(pro.getIsAttach()!=null){
				 res = pro.getIsAttach();
			 }
			 ajax(res+"");
		}
		return null;
	}
	
	/**
	 * @throws Exception 
	 * 功能：判断表单域是否隐藏
	 * @param
	 * @return
	 * @throws   
	 */
	public String isFormHidden() throws Exception{
		String taskName = getTaskNameById();
		NodeFormAttribute nodeForm  = nodeFormAttributeService.findByProcessIdAndTaskName(processId, taskName);
		if(nodeForm!=null){
			String secretPropertiesIds = nodeForm.getSecretProperties();
				if(secretPropertiesIds!=null&&!"".equals(secretPropertiesIds)){
					if (secretPropertiesIds.length()>1&&secretPropertiesIds.endsWith(",")) {
						secretPropertiesIds = secretPropertiesIds.substring(0, secretPropertiesIds.length()-1);
					}
					List<FormProperties> formProList = formPropertiesService.findAllByIds(secretPropertiesIds);
					if(formProList!=null&&formProList.size()>0){
						 Gson json = new Gson();
				         String jsons = json.toJson(formProList);
				         ajax(jsons);
					 }
				}
		}
		return null;
	}
	
	/**
	 * @throws Exception 
	 * 功能：判断表单域是否可编辑
	 * @param
	 * @return
	 * @throws   
	 */
	public String isWriteable() throws Exception{
		String taskName = getTaskNameById();
		if(taskName!=null&&!"".equals(taskName)&&processId>0){
			NodeFormAttribute nodeForm  = nodeFormAttributeService.findByProcessIdAndTaskName(processId, taskName);
			if(nodeForm!=null){
				String writeableIds = nodeForm.getWriteableProperties();
				if(writeableIds!=null&&!"".equals(writeableIds)){
					if (writeableIds.length()>1&&writeableIds.endsWith(",")) {
						writeableIds = writeableIds.substring(0, writeableIds.length()-1);
					}
					List<FormProperties>	 formProList = formPropertiesService.findAllByIds(writeableIds);
					 if(formProList!=null&&formProList.size()>0){
						 Gson json = new Gson();
				         String jsons = json.toJson(formProList);
				         ajax(jsons);
					 }
				}
			}
		}
		return null;
	}
	
	/**
	 * @throws Exception 
	 * 功能：获取表单数据
	 * @param
	 * @return
	 * @throws   
	 */
	public String getFormData() throws Exception{
		ProcessAttribute pa = processAttributeService.getProcessById(processId);
		String jsons=formPropertyValueService.findAllValueByProcessId(processInstanceId, pa.getFormId());
		ajax(jsons);
		return null;
	}
	
	/**
	 * @throws Exception 
	 * 功能：获取下一步的处理人
	 * @param
	 * @return
	 * @throws   
	 */
	public String getNextNodeUsers() throws Exception{
		List<UserInfo> userlist = nodeFormAttributeService.getUsersByNodeId(nodeId);
		//对查询出来的所有用户过滤，只返回和发起人同部门的人员
		//获取发起人信息
		UserInfo creater = getCreaterUserInfo();
		List<UserInfo> lastUserInfos = filterUsers(creater, userlist);
		
		List<SimpleTreeNode> treeNodes = new ArrayList<SimpleTreeNode>();
		if(lastUserInfos!=null&&lastUserInfos.size()>0){
			for (UserInfo userinfo : lastUserInfos){
		            SimpleTreeNode treeNode = new SimpleTreeNode();
		            //新公文
		            if(domflow!=null&& domflow.equals("domflow")){
		            	treeNode.setId(userinfo.getUserId().toString());
		            }else{//工作流
		            	treeNode.setId(userinfo.getLoginName());
		            }
		            treeNode.setName(userinfo.getUserName());
		            treeNode.setPId("0");
		            treeNodes.add(treeNode);
		        }
		 }
		 Gson json = new Gson();
		 String jsons = json.toJson(treeNodes);
		 ajax(jsons);
		return null;
	}
	
	/**
	 * 获取发起人信息
	 * @return
	 */
	private UserInfo getCreaterUserInfo(){
		if(StringUtil.isEmpty(processInstanceId)){
			return getLoginUser();
		}else{
			WorkflowVar wfv = workflowVarService.findByInstanceId(processInstanceId);
			String creater = wfv.getCreater();
			return userService.findByLoginName(creater);
		}
	}
	
	/**
	 * 过滤人员列表中和发起人处于同一部门或者处于发起人上级部门的人员
	 * @param creater
	 * @param ulist
	 * @return
	 */
	private List<UserInfo> filterUsers(UserInfo creater,List<UserInfo> ulist){
		List<UserInfo> result = new ArrayList<UserInfo>();
		if(creater.getGroupId()!=null){
			GroupInfo createGroup = groupService.findOne(creater.getGroupId());
			String createGroupPath = createGroup.getPath();
			String[] groupPaths = createGroupPath.split(",");
			if(ulist!=null){
				if(ulist.size()==1){
					return ulist;
				}
				for(int i=0; i<ulist.size(); i++){
					UserInfo temp = ulist.get(i);
					if(temp.getGroupId()!=null && creater.getUserId().intValue()!=temp.getUserId().intValue()){
						//处于同一级部门
						if(temp.getGroupId().intValue()==creater.getGroupId().intValue()){
							result.add(temp);
							break;
						}
						//处于发起人的上级部门
						int tGid = temp.getGroupId();
						for(int j=groupPaths.length-1;j>=0;j--){
							if(groupPaths[j].equals(tGid+"")){
								result.add(temp);
								break;
							}
						}
						if(result.size()==1){
							break;
						}
					}
				}
			}
		}
		return result;
	}
	
	/**
	 * 功能：获得下步流程
	 * @return
	 */
	public List<Map<String,String>> getApplyNextNodes(){
		List<Map<String,String>> list = workFlowService.findApplyNextNodes(taskId, processId);
		return list;
	} 
	
	/**
	 * 任务详情查看
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public String view() throws UnsupportedEncodingException{
		processInstanceId = URLDecoder.decode(processInstanceId, "UTF-8");
		return "view";
	}
	
	/**获取流程状态
	 * @return
	 */
	public String getProcessInstanceState(){
		HistoryProcessInstance insta = processEngine.getHistoryService().createHistoryProcessInstanceQuery().processInstanceId(processInstanceId).uniqueResult();
		return insta.getState();
	}
	
	/**
	 * 获取查询办理历史
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public String adviceHistoryShowList() throws UnsupportedEncodingException{
	    processInstanceId = URLDecoder.decode(processInstanceId,"UTF-8");
		return "adviceHistoryShowList";
	}
	
	/**
	 * 功能：进入审批界面
	 * @param
	 * @return
	 * @throws   
	 */
	public String toApply(){
		return "apply";
	}
	
	/**
	 * @throws UnsupportedEncodingException 
	 * 功能：add by 贾永强,判断当前任务是否已经结束,如果结束返回提示消息，如果未结束进入处理页面。
	 * @param
	 * @return
	 * @throws   
	 */
	public String  taskIsEndForAffire() throws UnsupportedEncodingException{
		UserInfo userObject=getLoginUser();
		this.affireflag = "affireflag";
		processInstanceId = URLDecoder.decode(processInstanceId, "UTF-8");
		List<Task> tasks = processEngine.getTaskService().createTaskQuery().assignee(userObject.getLoginName()).processInstanceId(processInstanceId).list();
		if(tasks==null || tasks.size() == 0){
			return  "has_completed";
		}else{
			this.processId = Integer.parseInt(workFlowService.getVariablebyInstance(processInstanceId, "processAttributeId").toString());
			this.taskId = tasks.get(0).getId();
			return toApply();
		}
	}
	
	/**
	 * 功能：进入查看工作流详情界面，通过事物提醒进入
	 * 作者：jiayongqiang
	 * 参数：
	 * 输出：
	 * @throws UnsupportedEncodingException 
	 */
	public String viewForAffire() throws UnsupportedEncodingException{
		this.affireflag = "affireflag";
		this.processId = Integer.parseInt(workFlowService.getVariablebyInstance(processInstanceId, "processAttributeId").toString());
		return view();
	}
	
	/**
	 * 功能：获取审批历史
	 * @param
	 * @return
	 * @throws   
	 */
	public List<AdviceHistory>  getAdviceHistoryList(){
		List<AdviceHistory> list=new ArrayList<AdviceHistory>();
		List<JbpmAdvice> adviceMap = jbpmAppService.getAdviceList(processInstanceId);
        if(adviceMap!=null){
             for(int i=0; i<adviceMap.size(); i++){
                       JbpmAdvice map=adviceMap.get(i);
                       Timestamp createTime = new Timestamp(Calendar.getInstance().getTimeInMillis());
                       if(map.getCreateTime()!=null){
                    	   createTime= DateTimeUtil.stringToTimestamp(map.getCreateTime().toString(),"yyyy-MM-dd HH:mm:ss");
                       }
                       AdviceHistory history =new AdviceHistory();
                       history.setContent(map.getContent()==null?"":map.getContent().toString());
                       history.setResult(map.getResult()==null?"":map.getResult().toString());
                       history.setTaskName(map.getTaskName()==null?"":map.getTaskName().toString());
                       history.setCreateTime(createTime);
                       history.setUserId( map.getUserId()==null?"":map.getUserId().toString());
                       history.setUserName(map.getUserName()==null?"":map.getUserName().toString());
                       history.setJob(map.getJob()==null?"":map.getJob().toString());
                       history.setGroupName( map.getGroupName()==null?"":map.getGroupName().toString());
                       list.add(history);
             	}
            }
        return list;
    }
	
	
	   /**
     * 功能：查询工作流节点列表
     * @param
     * @return
     * @throws   
     */
    public List<JbpmNodeState> getWorkflowState(){
    	try{
    		 List<JbpmNodeState> list = workFlowService.findJbpmNodesState(processInstanceId, processId);
    		 return list; 
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return null;
    }
	public List<Attachment> getAttachMap(){
		if(!StringUtils.isEmpty(processInstanceId)){
			List<Attachment> list = jbpmAppService.getAttachList(processInstanceId);
			for(int i=0; i<list.size(); i++){
				String sufx = AttachUtil.getClassByFileTypeApply(list.get(i).getAttachName());
				list.get(i).setAttacthSuffix(sufx);
			}
			return list;
		}else{
			return null;
		}
	}
	
	/**
	 * 功能：获得流程名称
	 * @return
	 */
	public String getProcessName(){
		if(processInstanceId==null || processInstanceId.equals("")){
			processInstanceId = processEngine.getTaskService().getTask(taskId).getExecutionId();
		}
		return  jbpmAppService.getTitle(processInstanceId);
	}
	
	public String getApproveComment(){
		return sysConfigService.getSysConfig().get(SysConfig.SYS_APPROVE_COMMENT);
	}
	
	private String getTaskNameById(){
		String taskName = "";
		if(taskId==null||"".equals(taskId)){ 
			taskName  = JpdlInterface.START_NODE_NAME; //获取任务的第一个任务 start 的名称
		}else{
			taskName = processEngine.getTaskService().getTask(taskId).getName();
		}
		return taskName;
	}
	
	/**
	 * 功能：发起流程的时候获取下一步节点
	 * @param
	 * @return
	 * @throws   
	 */
	public List<NodeFormAttribute> getNextNodes(){
		List<NodeFormAttribute> list = workFlowService.findStartSelectNode(processId);
		this.setDefaultNextNode(list.get(0));
		return list;
		
	}
	
	/**
	 * 功能：获取下一步的默认节点信息
	 * add by 贾永强
	 * @param
	 * @return
	 * @throws   
	 */
	public Map<String,String> getDefaultApplyNextNode(){
		List<Map<String,String>> list = workFlowService.findApplyNextNodes(taskId, processId);
		return list.get(0);
	}
	
	public int getCurrentTaskCount(){
		 return jbpmAppService.getCurrentTaskCount(processInstanceId);
	}
	
	/**
	 * 功能：产生流程实例名称
	 * @param
	 * @return
	 * @throws   
	 */
	public String getGenerateProcessInstanceName(){
		String str = jbpmAppService.generateProcessInstanceName(getLoginUser(), getProcessAttribute());
		return str;
	}
	
	/**
	 * 功能：返回自定义表单源码
	 * @param
	 * @return
	 * @throws   
	 */
	public String getFormSource(){
		ProcessAttribute pa = getProcessAttribute();
		 FormAttribute formAttribute=formAttributeService.findById(pa.getFormId());
	     return  formAttribute.getFormSource();
	}
	
	public ProcessAttribute getProcessAttribute(){
		ProcessAttribute processAttribute= processAttributeService.getProcessById(processId);
		return processAttribute;
	}

	/**
	 * 功能：返回下一步节点的名称，
	 * @param
	 * @return
	 * @throws   
	 */
	public String getNextEnd(){
		List<Map<String,String>> list = workFlowService.findApplyNextNodes(taskId, processId);
		if(list.size()==1){
			return list.get(0).get("name");
		}
		return "";
	}
	
	/**
	 * 功能：查看办理历史
	 * 作者：jiayongqiang
	 * 参数：
	 * 输出：
	 */
	public String toViewHistory(){
	    return "viewHistory";
	}
	
	/**
	 * @return
	 * @throws IOException 
	 */
	public String download() throws IOException{
		processInstanceId = URLDecoder.decode(processInstanceId, "UTF-8");
		File f = jbpmAppService.downloadFormWithData(processId, processInstanceId);
		if(f!=null){
	    	String title= URLEncoder.encode(f.getName(), "UTF-8");
	    	getResponse().addHeader("Content-Disposition","attachment; filename=\"" + title + "\"");
	    	OutputStream os = getResponse().getOutputStream();
	    	FileInputStream fos = new FileInputStream(f);
	    	byte[] bs = new byte[1024];
	    	int i = fos.read(bs);
	    	while(i>0){
	    		os.write(bs, 0, i);
	    		bs = new byte[1024];
	    		i = fos.read(bs);
	    	}
	    	os.flush();
	    	fos.close();
	    	f.delete();
		}
		return null;
	}
	
	/**
	 * 根据任务ID，流程定义ID获取当前节点属性
	 * @return
	 */
	public NodeFormAttribute getCurrentNodeFormAttribute(){
		String name = processEngine.getTaskService().getTask(taskId).getName();
		return nodeFormAttributeService.findByProcessIdAndTaskName(processId,name);
	}

	/**
	 * 功能：
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public String getWenhao() throws UnsupportedEncodingException{
		processInstanceId = URLDecoder.decode(processInstanceId, "UTF-8");
		WorkflowVar var = workflowVarService.findByInstanceId(processInstanceId);
		if(var!=null){
			return var.getTitle();
		}
		return "";
	}

	public int getProcessId() {
		return processId;
	}


	public void setProcessId(int processId) {
		this.processId = processId;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public int getNodeId() {
		return nodeId;
	}

	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getRedo() {
		return redo;
	}

	public void setRedo(String redo) {
		this.redo = redo;
	}

	public String getDomflow() {
		return domflow;
	}

	public void setDomflow(String domflow) {
		this.domflow = domflow;
	}

	public String getAffireflag() {
		return affireflag;
	}

	public void setAffireflag(String affireflag) {
		this.affireflag = affireflag;
	}

	public void setDefaultNextNode(NodeFormAttribute defaultNextNode) {
		this.defaultNextNode = defaultNextNode;
	}

	public NodeFormAttribute getDefaultNextNode() {
		return defaultNextNode;
	}

	public Integer getDocumentExtId() {
		if(StringUtil.isEmpty(processInstanceId)){
			return documentExtId;
		}else{
			DocumentExt ext = documentExtService.findByProcessInstanceId(processInstanceId);
			if(ext!=null){
				return ext.getDocumentExtId();
			}
			return null;
		}
	}

	public void setDocumentExtId(Integer documentExtId) {
		this.documentExtId = documentExtId;
	}

}
