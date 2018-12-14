package cn.com.qytx.cbb.publicDom.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.jbpm.api.ProcessInstance;
import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.task.TaskImpl;

import cn.com.qytx.cbb.customForm.domain.FormAttribute;
import cn.com.qytx.cbb.customForm.domain.FormProperties;
import cn.com.qytx.cbb.customForm.service.IFormCategory;
import cn.com.qytx.cbb.customForm.service.IFormProperties;
import cn.com.qytx.cbb.customForm.service.IFormPropertyValue;
import cn.com.qytx.cbb.customJpdl.service.ProcessAttributeService;
import cn.com.qytx.cbb.customWidget.vo.JbpmAdvice;
import cn.com.qytx.cbb.dict.domain.Dict;
import cn.com.qytx.cbb.file.config.FilePathConfig;
import cn.com.qytx.cbb.file.domain.Attachment;
import cn.com.qytx.cbb.file.service.IAttachment;
import cn.com.qytx.cbb.jbpmApp.service.IWorkFlowService;
import cn.com.qytx.cbb.publicDom.domain.DocTemplate;
import cn.com.qytx.cbb.publicDom.domain.DocumentExt;
import cn.com.qytx.cbb.publicDom.domain.DocumentType;
import cn.com.qytx.cbb.publicDom.domain.GongwenVar;
import cn.com.qytx.cbb.publicDom.domain.HistoryDoc;
import cn.com.qytx.cbb.publicDom.service.DocumentTypeService;
import cn.com.qytx.cbb.publicDom.service.GongwenVarService;
import cn.com.qytx.cbb.publicDom.service.HistoryDocService;
import cn.com.qytx.cbb.publicDom.service.IDocTemplateService;
import cn.com.qytx.cbb.publicDom.service.IDocumentExtService;
import cn.com.qytx.cbb.publicDom.service.PublicDocumentConstant;
import cn.com.qytx.cbb.publicDom.service.PublicDocumentConstant.GATHER_SOURCE;
import cn.com.qytx.cbb.publicDom.service.PublicDocumentConstant.SearchType;
import cn.com.qytx.cbb.publicDom.service.PublicDomService;
import cn.com.qytx.cbb.publicDom.service.impl.PublicDomServiceImpl;
import cn.com.qytx.cbb.publicDom.util.AttachUtil;
import cn.com.qytx.cbb.publicDom.util.DomDocUtil;
import cn.com.qytx.cbb.publicDom.vo.ApproveHistoryRecord;
import cn.com.qytx.cbb.publicDom.vo.PublicDomView;
import cn.com.qytx.cbb.publicDom.vo.PublicDomVo;
import cn.com.qytx.cbb.publicDom.vo.ReadStateCount;
import cn.com.qytx.cbb.publicDom.vo.ReadStateView;
import cn.com.qytx.cbb.publicDom.vo.ZipReport;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.org.domain.CompanyInfo;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.ICompany;
import cn.com.qytx.platform.org.service.IGroup;
import cn.com.qytx.platform.org.service.IUser;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 功能：处理公文模块的通用页面跳转
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 上午11:03:34 
 * 修改日期：上午11:03:34 
 * 修改列表：
 * @param <ReaderName>
 */
public class NewPublicDomAction extends MyBaseAction {
	/*------------spring注入属性begin---------------*/
	@Resource(name="publicDomService")
	private PublicDomService publicDomService;
	@Resource(name="formPropertyValueService")
	private IFormPropertyValue formPropertyValueService;
	@Resource(name="documentExtService")
	private IDocumentExtService documentExtService;
	@Resource(name="userService")
	private IUser userService;
	@Resource(name="documentTypeService")
	private DocumentTypeService documentTypeService;
	@Resource(name="attachmentService")
	private IAttachment attachmentService;
	@Resource(name="historyDocService")
	private HistoryDocService historyDocService;
	@Resource(name="docTemplateService")
	private IDocTemplateService docTemplateService;
	@Resource(name="formPropertiesService")
	private IFormProperties formPropertiesService;
	@Resource(name="formCategoryService")
	private IFormCategory formCategoryService;
	@Resource(name="processAttributeService")
	private ProcessAttributeService processAttributeService;
	@Resource(name="gongwenVarService")
	private GongwenVarService gongwenVarService;

    @Resource(name = "groupService")
    private IGroup groupService;
    @Resource
    private IWorkFlowService workflowService;
    @Resource
    private ICompany companyService;
    @Resource
    private FilePathConfig filePathConfig;

	/*---------------私有属性begin------------------*/
	private PublicDomVo publicDomVo;
	private String gongwenTypeId;
	private String title;//代表公文标题、公文类型、文号三个查询条件
	private String action;
	private String pageTitle;
	private String nextUser;
	private Integer templateId;
	private String taskName;
	private int attachId;
	private int formId;
	private String filePath;
	private int categoryId;
	private Integer docTypeId;
	private String taskIds;
	private String instanceIds;
	private Integer userId;
	private String imgs;
	private Integer documentExtId;
	private Integer gongwenType;
	
	public Integer getGongwenType() {
		return gongwenType;
	}
	public void setGongwenType(Integer gongwenType) {
		this.gongwenType = gongwenType;
	}
	public void setDocumentExtId(Integer documentExtId) {
		this.documentExtId = documentExtId;
	}
	public String getImgs() {
		return imgs;
	}

	public void setImgs(String imgs) {
		this.imgs = imgs;
	}

	private Integer processId;
	private String processName;

	public Integer getProcessId() {
		return processId;
	}

	public void setProcessId(Integer processId) {
		this.processId = processId;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getInstanceIds() {
		return instanceIds;
	}

	public void setInstanceIds(String instanceIds) {
		this.instanceIds = instanceIds;
	}

	public String getTaskIds() {
		return taskIds;
	}

	public void setTaskIds(String taskIds) {
		this.taskIds = taskIds;
	}

	public Integer getDocTypeId() {
		return docTypeId;
	}

	public void setDocTypeId(Integer docTypeId) {
		this.docTypeId = docTypeId;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public int getFormId() {
		return formId;
	}

	public void setFormId(int formId) {
		this.formId = formId;
	}

	public int getAttachId() {
		return attachId;
	}

	public void setAttachId(int attachId) {
		this.attachId = attachId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	
	//history==history,查看已办结的任务
	private String history;
	//print == print,打印查看，只有【打印】按钮功能
	private String print;
	
	private String docExtId;

	public Integer getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}

	public String getDocExtId() {
		return docExtId;
	}

	public void setDocExtId(String docExtId) {
		this.docExtId = docExtId;
	}

	public String getPrint() {
		return print;
	}

	public void setPrint(String print) {
		this.print = print;
	}

	public String getHistory() {
		return history;
	}

	public void setHistory(String history) {
		this.history = history;
	}

	public String getNextUser() {
		return nextUser;
	}

	public void setNextUser(String nextUser) {
		this.nextUser = nextUser;
	}

	public String getPageTitle() {
		return pageTitle;
	}

	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}


	private String customFormValue;
	
	public String getCustomFormValue() {
		return customFormValue;
	}

	public void setCustomFormValue(String customFormValue) {
		this.customFormValue = customFormValue;
	}


	public Task getTask() {
		if(taskId!=null){
			return processEngine.getTaskService().getTask(taskId);
		}
		return null;
	}

	public String getGongwenTypeId() {
		return gongwenTypeId;
	}

	public void setGongwenTypeId(String gongwenTypeId) {
		this.gongwenTypeId = gongwenTypeId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public PublicDomVo getPublicDomVo() {
		return publicDomVo;
	}

	public void setPublicDomVo(PublicDomVo publicDomVo) {
		this.publicDomVo = publicDomVo;
	}

	/*---------------私有属性end------------------*/
	
	/*---------------控制/公共方法-------------*/
	
	/**
	 * 功能：发起公文，转入正文页面
	 * @param
	 * @return
	 * @throws   
	 */
	public String startDom(){
		//创建流程实例
		taskId = publicDomService.createInstance(publicDomVo, getLoginUser(),GATHER_SOURCE.SOURCE_CREATE);
		ajax(taskId);
		return null;
	}
	
	/**
	 * 功能：通过forward跳转到主办理页面
	 * @param
	 * @return
	 * @throws   
	 */
	public String toMainByForward(){
		if(taskId!=null){
			Task task = processEngine.getTaskService().getTask(taskId);
			TaskImpl impl = (TaskImpl)task;
			instanceId = impl.getProcessInstance().getId();
		}
		return "main";
	}
	
	public PublicDomVo getGongwenVars(){
	    PublicDomVo vo = new PublicDomVo();
	    GongwenVar var =  gongwenVarService.findByInstanceId(instanceId);
	    String firstLevel = workflowService.getVariablebyInstance(instanceId, PublicDocumentConstant.FIRST_LEVEL_NAME);
	    vo.setFirstLevel(firstLevel);
	    vo.setDomTypeName(var.getGongwenTypeName());
	    vo.setSecretLevel(var.getMiji());
	    vo.setHuanji(var.getHuanji());
	    if(var.getWenhao()==null){
	        vo.setWenhao("(核稿后生成)");
	    }else{
	        vo.setWenhao(var.getWenhao());
	    }
	    vo.setTitle(var.getTitle());
	    vo.setSourceDept(var.getFromGroup());
	    return vo;
	}
	
	/**
	 * @throws Exception 
	 * 功能：获取原发文单的实例ID
	 * @param
	 * @return
	 * @throws   
	 */
	public String getHistoryDispatchInstanceId() {
		String sourceInstanceId = workflowService.getVariablebyInstance(instanceId, PublicDocumentConstant.GATHER_SOURCE_DISPATCH_EXECUTIONID);
		return sourceInstanceId;
	}
	
	/**
	 * @throws Exception 
	 * 功能：获取原发文单的documentext对象
	 * @param
	 * @return
	 * @throws   
	 */
	public int getHistoryDispatchDocumentExtId() {
		String sourceInstanceId = workflowService.getVariablebyInstance(instanceId, PublicDocumentConstant.GATHER_SOURCE_DISPATCH_EXECUTIONID);
		if(sourceInstanceId!=null && !sourceInstanceId.equals("")){
			DocumentExt documentExt= documentExtService.findByProcessInstanceId(sourceInstanceId);
			return documentExt.getDocumentExtId();
		}
		return 0;
	}
	
	/**
	 * @throws UnsupportedEncodingException 
	 * 功能：查看历史任务
	 * @param
	 * @return
	 * @throws   
	 */
	public String toView() throws UnsupportedEncodingException{
		return "main";
	}
	
	/**
	 * @throws Exception 
	 * 功能：获取表单模板数据
	 * @param
	 * @return
	 * @throws   
	 */
	public String ajaxFormTemplate() throws Exception{
		instanceId =  java.net.URLDecoder.decode(instanceId,"UTF-8");   
		FormAttribute fa = publicDomService.getFormSourceByInstanceId(instanceId);
		if(fa!=null){
			String str = fa.getFormSource();
			ajax(str);
		}else{
			ajax("delete");
		}
		return null;
	}
	
  
	
	/**
	 * @throws Exception 
	 * 功能：获取表单属性值数据
	 * @param
	 * @return
	 * @throws   
	 */
	public String ajaxFormPropertyValue() throws Exception{
		instanceId =  java.net.URLDecoder.decode(instanceId,"UTF-8");   
		FormAttribute fa = publicDomService.getFormSourceByInstanceId(instanceId);
		if(fa!=null){
			String str = formPropertyValueService.findAllValueByProcessId(instanceId, fa.getFormId());
			ajax(str);
		}
		return null;
	}
	
	public int getDocumentExtId(){
		DocumentExt documentExt= documentExtService.findByProcessInstanceId(instanceId);
		return documentExt.getDocumentExtId();
	}
	
	/**
	 * 功能：保存附件
	 * 作者：jiayongqiang
	 * 参数：
	 * 输出：
	 */
	public String addAttach(){
		AttachUtil.saveAttach(getAttachId(), instanceId, getLoginUser());
		return null;
	}
	
	/**
	 * @throws Exception 
	 * 功能：获取附件列表
	 * @param
	 * @return
	 * @throws   
	 */
	public String getAttachList() throws Exception{
		String str= AttachUtil.getAttachment(instanceId);
		ajax(str);
		return null;
	}
	
	/**
	 * @throws Exception 
	 * 功能：保存公文
	 * @param
	 * @return
	 * @throws   
	 */
	public String saveDom() throws Exception{
		try{
			TaskImpl task = (TaskImpl) processEngine.getTaskService().getTask(taskId);
			if(task == null){
				ajax("delete");
			}else{
				FormAttribute fa = publicDomService.getFormSource(taskId);
				instanceId = task.getProcessInstance().getId();
				publicDomService.saveFormDataAndCommants(customFormValue, instanceId,fa==null?null:fa.getFormId());
				GongwenVar gongwenVar = gongwenVarService.findByInstanceId(instanceId);
				gongwenVar.setSignImg(imgs);
				gongwenVar.setCompanyId(getLoginUser().getCompanyId());
				gongwenVarService.saveOrUpdate(gongwenVar);
				ajax("success");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 功能：查询公文
	 * @param
	 * @return
	 * @throws   
	 */
	public String searchMyDom() throws Exception{
		Page<PublicDomView> viewlist = null;
		SearchType type = null;
		if(searchType.equals("processing")){
			type = SearchType.MY_PROCESSING;
		}else if(searchType.equals("completed")){
			type = SearchType.MY_COMPLETE;
		}
		List<String> nodes = new ArrayList<String>();
		String process = null;
		String userId = getLoginUser().getUserId()+"";
		if(menu == 1){
			nodes.add("收文登记");
		}else if(menu == 2){
			nodes.add("领导批阅");
		}else if(menu == 3){
			nodes.add("收文分发");
		}else if(menu == 4){
			nodes.add("收文阅读");
		}else if(menu == 5){
			nodes.add("发文拟稿");
		}else if(menu == 6){
			nodes.add("发文核稿");
		}else if(menu == 7){
			nodes.add("套红盖章");
		}else if(menu == 8){
			nodes.add("发文分发");
		}else if(menu == 9){//收文阅读和收文归档
			nodes.add("收文阅读");
			nodes.add("归档");
			process = "gather";
			userId = null;
		}else if(menu == 10){//发文归档
			nodes.add("归档");
			process = "dispatch";
			userId = null;
		}else if(menu == 11){
			nodes.add("归档");
			userId = null;
		}
		
		//获取登记人的分支机构
		GroupInfo forkGroup = null;
		if(menu>8){
			forkGroup = groupService.getForkGroup(getLoginUser().getCompanyId(),getLoginUser().getUserId());
		}
		Map<String,String> vars = new HashMap<String,String>();
		vars.put(PublicDocumentConstant.GONGWENTYPE_ID, gongwenTypeId);
		vars.put(PublicDocumentConstant.TITLE, title);
		if(type!=null){
    		if(type.equals(SearchType.MY_PROCESSING)){
    			viewlist = publicDomService.searchWaittingProcessTask(process, nodes, title, userId,forkGroup, getPageable());
    		}else{
    			viewlist = publicDomService.searchMyCompletedProcessTask(process,nodes,title,userId,forkGroup ,getPageable());
    		}
		}
		if(viewlist!=null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			List<Map<String, Object>> mapList = new ArrayList<Map<String,Object>>();
			for(Iterator<PublicDomView> iterator = viewlist.getContent().iterator();iterator.hasNext();){
				PublicDomView view = iterator.next();
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("title", view.getTitle());
				map.put("wenhao", (view.getWenhao()==null || view.getWenhao().equals(""))?"<font color='gray'>(拟稿后自动生成)</font>":view.getWenhao());
				map.put("gongwenType", view.getGongwenTypeName());
				map.put("state", view.getParsedState());
				map.put("taskId", view.getTaskId());
				map.put("option", view.getTaskId());
				map.put("sendDept", view.getFromGroup());//发文单位
				map.put("gatherDept", view.getReceiverGroup());//收文部门
				map.put("secretLevel",view.getMiji());
				map.put("huanji", view.getHuanji());
				map.put("batchZip", view.getTaskId());
				map.put("batchDownload", view.getInstanceId());
				int source = 0 ;
				if(view.getGatherSource().equals("dispatch")){
					source = 1;
				}
				map.put("source", source);
				map.put("instanceId", view.getInstanceId());
				if(source == 0){
					map.put("sourceZh", "系统外");
				}else{
					map.put("sourceZh", "系统内");
				}
				String creater= view.getCreater();
				map.put("creater", creater);
				
				map.put("startTime", sdf.format(view.getCreateTime()));
				if(view.getEndTime()!=null){
					map.put("endTime", sdf.format(view.getEndTime()));
				}else{
					map.put("endTime", "");
				}
				if(menu == 9){
					String instanceId = view.getInstanceId();
					List<ReadStateView> readStateList = publicDomService.getReadStateViewCount(instanceId);
					ReadStateCount  count = getReadStateCount(readStateList);
					map.put("ydrs", count.getTotal());
					map.put("sdrs", count.getReadComplete());
					boolean flag = publicDomService.isCanZip(instanceId);
					if(flag){
						map.put("isZip", "yes");
					}else{
						map.put("isZip", "no");
					}
					
				}
				map.put("phone",view.getPhone());
				
				//add by jiayq,上一步任务的结束时间
				if(view.getLastTimeStamp()!=null){
					map.put("lastTimestamp", sdf.format(view.getLastTimeStamp()));
				}else{
					map.put("lastTimestamp", "");
				}
				mapList.add(map);
			}
			ajaxPage(viewlist,mapList);
		}
		return null;
	}
	
	
	public ReadStateCount getReadStateCount(List<ReadStateView> list){
		ReadStateCount rsc = new ReadStateCount();
		rsc.setTotal(list.size());
		int reading = 0;
		int readcomplete = 0;
		for(int i=0; i<list.size(); i++){
			if(list.get(i).getState().contains("未阅读")){
				reading++;
			}else if(list.get(i).getState().contains("已阅读")){
				readcomplete++;
			}
		}
		rsc.setReading(reading);
		rsc.setReadComplete(readcomplete);
		return rsc;
	}
	
	/**
	 * 功能：删除流程实例，同时删除表单数据
	 * @param
	 * @return
	 * @throws   
	 */
	public String delete(){
		publicDomService.deleteInstance(taskId);
		try {
			ajax("success");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 功能：选择人员
	 * @param
	 * @return
	 * @throws   
	 */
	public String toSelectUser(){
		return "selectuser";
	}
	
	/**
	 * @throws Exception 
	 * 功能：完成任务
	 * @param
	 * @return
	 * @throws   
	 */
	public String completeTask() throws Exception{
		try{
			action = URLDecoder.decode(action, "UTF-8");
			String[] strs = taskId.split(",");
			for(int i=0;i<strs.length;i++){
				String taskId = strs[i];
				if(taskId!=null && !taskId.equals("")){
					TaskImpl temp = (TaskImpl) processEngine.getTaskService().getTask(taskId);
					if(temp!=null){
						String instanceIdTemp = temp.getProcessInstance().getId();
						long l = Calendar.getInstance().getTimeInMillis();
						try{
							DomDocUtil.toPdf(instanceIdTemp);
						}catch(Exception e){
							e.printStackTrace();
						}
					}
					publicDomService.completeTask(getLoginUser(),taskId, nextUser, action);
				}
			}
			ajax("success");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 功能：获取审批记录
	 * @param
	 * @return
	 * @throws   
	 */
	public List<ApproveHistoryRecord> getRecords(){
		String str = (String) processEngine.getExecutionService().getVariable(instanceId, PublicDocumentConstant.APPROVE_HIST_RECORD);
		Gson gson = new Gson();
		List<ApproveHistoryRecord> list = gson.fromJson(str, new TypeToken<List<ApproveHistoryRecord>>(){}.getType());
		
		String result = publicDomService.getAdvice(instanceId);
		List<JbpmAdvice> advicelist = gson.fromJson(result,new TypeToken<List<JbpmAdvice>>(){}.getType());
		if(list!=null && advicelist!=null){
			for(int i=0; i<list.size(); i++){
				ApproveHistoryRecord record = list.get(i);
				for(Iterator it = advicelist.iterator(); it.hasNext();){
					JbpmAdvice advice = (JbpmAdvice) it.next();
					if(record.getOption()!=null && advice.getTaskName()!=null){
						if(record.getOption().equals(advice.getTaskName()) && record.getUserName().equals(advice.getUserName())){
							String old = record.getContent();
							if(old == null){
								old = "";
							}else{
								old+="<br/>";
							}
							record.setContent(old+advice.getContent());
							int signType = advice.getSignType();
							String sign = "";
							if(signType == 1){
								sign=record.getUserName();
							}else if(signType == 2){
								String signUrl = getRequest().getContextPath()+"/upload/prevViewByPath.action?filePath="+(advice.getSignUrl()==null?"":advice.getSignUrl());
								sign = "<img width='120px' height='60px' src='"+signUrl+"'/>";
							}
							record.setSign(sign);
						}
					}
				}
			}
		}
		return list;
	}
	

		/**
	 * @throws Exception 
	 * 功能：从流程变量中获取文号
	 * @param
	 * @return
	 * @throws   
	 */
	public String getWenhaoFromVar() throws Exception{
		Object wenhao = null;
		if(taskId!=null&&!"undefined".equals(taskId)&&!"".equals(taskId)){
			TaskImpl impl = (TaskImpl) processEngine.getTaskService().getTask(taskId);
			String eid = impl.getProcessInstance().getId();
			wenhao = workflowService.getVariablebyInstance(eid, PublicDocumentConstant.WENHAO);
		}
		if(wenhao==null){
			ajax("");
		}else{
			ajax(wenhao.toString());
		}
		return null;
	}

		/**
	 * @throws Exception 
	 * 功能：获取历史记录
	 * @param
	 * @return
	 * @throws   
	 */
	public String getDocHistory() throws Exception{
		Task task = processEngine.getTaskService().getTask(taskId);
		TaskImpl impl = (TaskImpl)task;
		String eid = impl.getProcessInstance().getId();
		List<HistoryDoc> list = historyDocService.findByInstanceId(eid);
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			HistoryDoc historyDoc = (HistoryDoc) iterator.next();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss");
			if(historyDoc.getUpdateTime()!=null){
				historyDoc.setFormTime(sdf.format(historyDoc.getUpdateTime()));
			}
		}
		Gson gson = new Gson();
		ajax(gson.toJson(list));
		return null;
	}
	
	
	/**
	 * 功能：归档查询
	 * @param
	 * @return
	 * @throws   
	 */
	public String zipSearch(){
		return "zip_search";
	}
	
	
	/**
	 * @throws IOException 
	 * 功能：返回PDF文档的输出流
	 * @param
	 * @return
	 * @throws   
	 */
	public String getPdf() throws IOException{
		String filePath = this.getRequest().getRealPath("/");
		String imgPath = getRequest().getContextPath();
		String str = publicDomService.getOfficeHtmlContent(instanceId, filePath, imgPath);
		ajax(str);
		return null;
	}
	
	/**
	 * @throws Exception 
	 * 功能：判断标题是否重复，success 不重复；fail 重复
	 * @param
	 * @return
	 * @throws   
	 */
	public String titleIsRepeat() throws Exception{
		title = URLDecoder.decode(title, "UTF-8");
		boolean result = publicDomService.titleIsRepeat(title);
		if(result){
			ajax("fail");
		}else{
			ajax("success");
		}
		return null;
	}
	
	/**
	 * @throws Exception 
	 * @throws UnsupportedEncodingException 
	 * 功能：删除附件
	 * @param
	 * @return
	 * @throws   
	 */
	public String deleteAttach() throws Exception{
		AttachUtil.deleteAttach(instanceId, attachId);
		ajax("success");
		return null;
	}
	
	/**
	 * @throws Exception 
	 * 功能：获取表单字段属性
	 * @param
	 * @return
	 * @throws   
	 */
	public String getFormProperties() throws Exception{
		List<FormProperties> list = formPropertiesService.findByFormId(formId);
		Gson gons = new Gson();
		ajax(gons.toJson(list));
		return null;
	}
	
	/**
	 * @throws Exception 
	 * 功能：判断该任务是否为空，yes:为空;no:不为空
	 * @param
	 * @return
	 * @throws   
	 */
	public String taskIsNull() throws Exception{
		Task task = processEngine.getTaskService().getTask(taskId);
		if(task == null){
			ajax("yes");
		}else{
			ajax("no");
		}
		return null;
	}
	
	/**
	 * 添加处理意见
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public String addAdvice() throws UnsupportedEncodingException{
		return "advice";
	}
	
	/**
	 * 功能：事务提醒消息路径
	 * @param
	 * @return
	 * @throws   
	 */
	public String taskIsDelete(){
		Task task = processEngine.getTaskService().createTaskQuery().processInstanceId(instanceId).
				assignee(getLoginUser().getUserId()+"").uniqueResult();
		if(task == null){
			return "hasDeleted";
		}else{
			this.taskId = task.getId();
			this.taskName = task.getName();
			if(this.taskName.equals("收文登记")){
				this.menu = 1;
			}else if(this.taskName.equals("领导批阅")){
				this.menu = 2;
			}else if(this.taskName.equals("收文分发")){
				this.menu = 3;
			}else if(this.taskName.equals("收文阅读")){
				this.menu = 4;
			}else if(this.taskName.equals("发文拟稿")){
				this.menu = 5;
			}else if(this.taskName.equals("发文核稿")){
				this.menu = 6;
			}else if(this.taskName.equals("套红盖章")){
				this.menu = 7;
			}else if(this.taskName.equals("发文分发")){
				this.menu = 8;
			}
			return toMainByForward();
		}
	}
	
	
	/**
	 * @throws Exception 
	 * @throws UnsupportedEncodingException 
	 * 功能：判断流程实例是否已删除
	 * @param
	 * @return
	 * @throws   
	 */
	public String processInstanceIsDelete() throws Exception{
		instanceId = URLDecoder.decode(instanceId, "UTF-8");
		ProcessInstance processInstance = processEngine.getExecutionService().findProcessInstanceById(instanceId);
		if(processInstance==null){
			ajax("delete");
		}else{
			ajax("success");
		}
		return null;
	}
	
    
    /**
     * @throws Exception 
     * 功能：获取所有的公文分类
     * @param
     * @return
     * @throws   
     */
    public String getAllCategorys() throws Exception{
    	List<Dict> list = infoTypeService.findList("dom_category", 1);
    	Gson gson = new Gson();
    	String result = gson.toJson(list);
    	ajax(result);
    	return null;
    }
    
    public String getDocumentType() throws Exception{
    	UserInfo ui = getLoginUser();
    	List<DocumentType> list = documentTypeService.getDocumentTypesByCategoryId(categoryId,ui,gongwenType);
    	StringBuffer sb = new StringBuffer();
    	sb.append("[");
    	if(list!=null){
    		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				DocumentType documentType = (DocumentType) iterator.next();
				sb.append("{");
				sb.append("\"id\":\"").append(documentType.getDoctypeId()).append("\",");
				sb.append("\"name\":\"").append(documentType.getDoctypeName()).append("\"");
				sb.append("}");
				if(iterator.hasNext()){
					sb.append(",");
				}
			}
    	}
    	sb.append("]");
    	ajax(sb.toString());
    	return null;
    }
   
    
    /**
     * 功能：归档报表
     * @param
     * @return
     * @throws   
     */
    public String zipReport(){
    	return "zipReport";
    }
    
    /**
     * 功能：返回归档报表对象
     * @param
     * @return
     * @throws   
     */
    public ZipReport getZipReport(){
    	ZipReport zipReport = new ZipReport();
    	UserInfo userInfo = getLoginUser();
    	GroupInfo forkGroup = groupService.getForkGroup(userInfo.getCompanyId(),userInfo.getUserId());
    	setIDisplayStart(0);
    	setIDisplayLength(Integer.MAX_VALUE);
    	List<String> nodes = new ArrayList<String>();
    	nodes.add("归档");
    	Page<PublicDomView> completedlist = publicDomService.searchMyCompletedProcessTask("dispatch",nodes,null,null,forkGroup ,getPageable());
    	zipReport.setDisCompZip(completedlist.getContent().size());
    	completedlist = publicDomService.searchMyCompletedProcessTask("gather",nodes,null,null,forkGroup ,getPageable());
    	zipReport.setGatherCompZip(completedlist.getContent().size());
    	Page<PublicDomView> waitlist = publicDomService.searchWaittingProcessTask("dispatch", nodes, null, null,forkGroup, getPageable());
    	zipReport.setDisWaitZip(waitlist.getContent().size());
    	
    	nodes.add("收文阅读");
    	waitlist = publicDomService.searchWaittingProcessTask("gather", nodes, null, null,forkGroup, getPageable());
    	zipReport.setGatherWaitZip(waitlist.getContent().size());
    	
    	String forkGroupName = "";
    	if(forkGroup != null){
    		forkGroupName = forkGroup.getGroupName();
    	}else{
    		CompanyInfo ci = companyService.findOne(userInfo.getCompanyId());
    		if(ci!=null){
    			forkGroupName = ci.getCompanyName();
    		}
    	}
    	zipReport.setForkGroupName(forkGroupName);
    	return zipReport;
    	
    }
    
    /**
     * 功能：归档下载(单个公文)
     * @param
     * @return
     * @throws   
     */
    public String downloadZipFile() throws Exception{
    	PublicDomServiceImpl impl = (PublicDomServiceImpl)publicDomService;
    	String title = impl.getTitleByInstanceId(instanceId);
    	title= URLEncoder.encode(title, "UTF-8");
    	getResponse().addHeader("Content-Disposition","attachment; filename=\"" + title+".zip" + "\"");
    	OutputStream os = getResponse().getOutputStream();
    	File zipFile = publicDomService.zipForDownload(instanceId);
    	FileInputStream fos = new FileInputStream(zipFile);
    	byte[] bs = new byte[1024];
    	int i = fos.read(bs);
    	while(i>0){
    		os.write(bs, 0, i);
    		bs = new byte[1024];
    		i = fos.read(bs);
    	}
    	os.flush();
    	fos.close();
    	zipFile.delete();
    	return null;
    }
    
    /**
     * @throws Exception 
     * 功能：批量归档
     * @param
     * @return
     * @throws   
     */
    public String batchZip() throws Exception{
    	if(taskIds!=null){
    		String[] taskId = taskIds.split(",");
    		publicDomService.batchCompleteTask(getLoginUser(),taskId);
    		ajax("success");
    	}
    	return null;
    }
    
    /**
     * 功能：批量下载
     * @param
     * @return
     * @throws   
     */
    public String batchDowanload() throws Exception{
    	if(instanceIds!=null){
    		String[] instanceIdList = instanceIds.split(",");
    		File zipFile = publicDomService.batchZipForDowanload(instanceIdList);
//    		String fileName = new String("批量下载".getBytes("GBK"),"ISO-8859-1");
    		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    		String d = sdf.format(new Date(Calendar.getInstance().getTimeInMillis()));
    		String fileName =  URLEncoder.encode("公文归档批量下载"+d, "UTF-8");
    		getResponse().addHeader("Content-Disposition","attachment; filename=\"" + fileName+".zip" + "\"");
        	OutputStream os = getResponse().getOutputStream();
        	FileInputStream fos = new FileInputStream(zipFile);
        	byte[] bs = new byte[1024];
        	int i = fos.read(bs);
        	while(i>0){
        		os.write(bs, 0, i);
        		bs = new byte[1024];
        		i = fos.read(bs);
        	}
        	os.flush();
        	fos.close();
        	zipFile.delete();
    	}
    	return null;
    }
    
    /**
     * @throws Exception 
     * 功能：根据流程ID判断公文类型ID是否存在，如果存在返回success，进入编辑页面，如果不存在则进入公文登记页面
     * @param
     * @return
     * @throws   
     */
    public String findGongwenTypeId() throws Exception{
    	String gongwenTypeId = (String) processEngine.getExecutionService().getVariable(instanceId,PublicDocumentConstant.GONGWENTYPE_ID );
    	if(gongwenTypeId!=null  &&  !gongwenTypeId.equals("")){
    		ajax("success");
    	}
    	return null;
    }
    
    /**
     * @throws IOException 
     * 功能：获取套红模板的Id
     * @param
     * @return
     * @throws   
     */
    public int getTaohongTemplate() {
    	return publicDomService.getTaohongTemplate(taskId, null);
    }
    
    /**获取套红末班路径
     * @return
     */
    public String getTohongTemplatePath(){
    	DocTemplate dt = docTemplateService.findOne(templateId);
    	if(dt!=null){
    		 ajax(dt.getDocUrl());
    	}
    	return null;
    }
    
    /**
     * 功能：打印预览
     * @param
     * @return
     * @throws   
     */
    public String printView(){
    	return "printView";
    }
    
    /**
     * 功能：返回正文office的输出流
     * 作者：jiayongqiang
     * 参数：
     * 输出：
     * @throws IOException 
     */
    public String getOfficeContent() throws IOException{
    	HttpServletResponse response = this.getResponse();
		String path=getDocPath(documentExtId);
		OutputStream out = response.getOutputStream();
		File temp = new File(path);
		if(!temp.exists()){
			return null;
		}
		FileInputStream fis = new FileInputStream(temp);
		byte[] bs = new byte[1024*10];
		int l = fis.read(bs);
		while (l != -1) {
			out.write(bs,0,l);
			bs = new byte[1024*10];
			l = fis.read(bs);
		}
		fis.close();
		out.flush();
    	return null;
    }
    
	private String getDocPath(Integer documentExtid){
		DocumentExt ext = documentExtService.findOne(documentExtid);
		int attachId = ext.getAttachId();
		Attachment attach = attachmentService.getAttachment(attachId);
		String path = attach.getAttachFile();
		return filePathConfig.getFileUploadPath()+"\\upload\\"+path;
	}
	
	/**
	 * 功能：返回yes,套打，返回No,非套打
	 * @param
	 * @return
	 * @throws   
	 */
	public String isTaoDa() throws Exception{
		String isTaoDa = "no";
		instanceId = URLDecoder.decode(instanceId,"UTF-8");
		String gongwenTypeId = (String) processEngine.getHistoryService().getVariable(instanceId, PublicDocumentConstant.GONGWENTYPE_ID);
		if(gongwenTypeId!=null){
			DocumentType dy = documentTypeService.findOne(Integer.parseInt(gongwenTypeId));
			if(dy.getTaoDa()==1){
				isTaoDa = "yes";
			}
		}
		ajax(isTaoDa);
		return null;
	}
	
	/**
	 * 功能：获取打印设置
	 * @param
	 * @return
	 * @throws   
	 */
	public String printSet() throws Exception{
		if(instanceId == null || instanceId.equals("")){
			ajax("");
			return null;
		}
		instanceId = URLDecoder.decode(instanceId, "UTF-8");
		ProcessInstance pi = 
				processEngine.getExecutionService().createProcessInstanceQuery().processInstanceId(instanceId).uniqueResult();
		String gongwenTypeId = workflowService.getVariablebyInstance(instanceId, PublicDocumentConstant.GONGWENTYPE_ID);
		if(gongwenTypeId!=null){
			DocumentType dy = documentTypeService.findOne(Integer.parseInt(gongwenTypeId));
			ajax(dy.getPrintCode());
		}
		return null;
	}
	
	/**
	 * 功能：获取收文类型：create（新建及系统外）/dispatch(发文发送来的即发文发送的 系统内)
	 * 作者：jiayongqiang
	 * 参数：
	 * 输出：
	 */
	public String getSource(){
		return workflowService.getVariablebyInstance(getInstanceId(), PublicDocumentConstant.GATHER_SOURCE);
	}
	
	/**设置公文内容中各个TAB是否显示
	 * @return
	 * Map:key tab的标签；value：1显示，0不显示
	 * key:pdf:版式文件的TAB
	 *     doc:正文的TAB
	 *     history_form_div：历史发文档的TAB
	 */
	public Map<String,String> getTabsIsShow(){
		Task t = getTask();
		if(t == null){
			taskName = "";
		}else{
			taskName = t.getName();
		}
		Map<String,String> map = new HashMap<String, String>();
		map.put("pdf", "0");
		map.put("doc", "0");
		map.put("history_form_div", "0");
		if(history == null || history.equals("")){
			if(taskName.equals("发文拟稿") || taskName.equals("发文核稿")){
				map.put("doc", "1");
			}else if(taskName.equals("发文分发") || taskName.equals("归档")){
				map.put("pdf", "1");
			}else if(taskName.equals("套红盖章")){
				map.put("pdf", "1");
				map.put("doc", "1");
			}
			
			if(getSource().equals("create")){
				if(taskName.equals("收文登记") ){
					map.put("doc", "1");
				}else if(taskName.equals("收文分发") || taskName.equals("收文阅读") || taskName.equals("归档") || taskName.equals("领导批阅")){
					map.put("pdf", "1");
				}
			}else if(getSource().equals("dispatch")){
				map.put("pdf", "1");
			}
			
		}else if(history.equals("history")){
			map.put("pdf", "1");
		}
		
		//如果是系统内收文，历史发文单一直显示
		if(getSource().equals("dispatch")){
			map.put("history_form_div", "1");
		}
		return map;
	}
	
}


