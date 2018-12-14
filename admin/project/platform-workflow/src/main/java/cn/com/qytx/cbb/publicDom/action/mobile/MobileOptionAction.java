package cn.com.qytx.cbb.publicDom.action.mobile;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.jbpm.api.ProcessEngine;
import org.jbpm.api.task.Task;

import cn.com.qytx.cbb.customWidget.vo.JbpmAdvice;
import cn.com.qytx.cbb.dict.domain.Dict;
import cn.com.qytx.cbb.dict.service.IDict;
import cn.com.qytx.cbb.file.service.IAttachment;
import cn.com.qytx.cbb.publicDom.domain.DocumentType;
import cn.com.qytx.cbb.publicDom.domain.GongwenVar;
import cn.com.qytx.cbb.publicDom.mobile.vo.AdviceObj;
import cn.com.qytx.cbb.publicDom.service.DocumentTypeService;
import cn.com.qytx.cbb.publicDom.service.GongwenVarService;
import cn.com.qytx.cbb.publicDom.service.IDocumentExtService;
import cn.com.qytx.cbb.publicDom.service.PublicDocumentConstant;
import cn.com.qytx.cbb.publicDom.service.PublicDomService;
import cn.com.qytx.cbb.publicDom.service.mobile.MobilePublicDomService;
import cn.com.qytx.cbb.publicDom.util.DomDocUtil;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IGroup;

import com.google.gson.Gson;

/**
 * 功能：公文操作控制器
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 上午11:02:35 
 * 修改日期：上午11:02:35 
 * 修改列表：
 */
public class MobileOptionAction extends MobileBaseAction {

	@Resource(name="mobilePublicDomService")
	private MobilePublicDomService mobilePublicDomService;
	@Resource(name="documentExtService")
	private IDocumentExtService documentExtService;
	@Resource(name="attachmentService")
	private IAttachment attachmentService;
	@Resource(name="publicDomService")
	private PublicDomService publicDomService;
	@Resource(name="processEngine")
	private ProcessEngine processEngine;
	@Resource(name="dictService")
	private IDict infoTypeImpl;
	@Resource
	private IGroup groupService;
	@Resource(name="documentTypeService")
	private DocumentTypeService documentTypeService;
	@Resource(name="gongwenVarService")
	private GongwenVarService gongwenVarService;
	
	private String instanceId;
	private String nextUser;
	private String nextAction;
	
	private String gongwenTypeId;
	private String gongwenTypeName;
	private String wenhao;
	private String formData;
	
	//add by jiayq，添加公文类型标识，收/发文
	private Integer gongwenType;
	
	public Integer getGongwenType() {
		return gongwenType;
	}

	public void setGongwenType(Integer gongwenType) {
		this.gongwenType = gongwenType;
	}

	public String getFormData() {
		return formData;
	}

	public void setFormData(String formData) {
		this.formData = formData;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	//一级公文类型
	private int firstLevelId;
	
	//控件标志
	private String editorId;
	private String content;
	
	public String getEditorId() {
		return editorId;
	}

	public void setEditorId(String editorId) {
		this.editorId = editorId;
	}

	public String getGongwenTypeId() {
		return gongwenTypeId;
	}

	public void setGongwenTypeId(String gongwenTypeId) {
		this.gongwenTypeId = gongwenTypeId;
	}

	public String getGongwenTypeName() {
		return gongwenTypeName;
	}

	public void setGongwenTypeName(String gongwenTypeName) {
		this.gongwenTypeName = gongwenTypeName;
	}

	public String getWenhao() {
		return wenhao;
	}

	public void setWenhao(String wenhao) {
		this.wenhao = wenhao;
	}

	public int getFirstLevelId() {
		return firstLevelId;
	}

	public void setFirstLevelId(int firstLevelId) {
		this.firstLevelId = firstLevelId;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getNextUser() {
		return nextUser;
	}

	public void setNextUser(String nextUser) {
		this.nextUser = nextUser;
	}

	public String getNextAction() {
		return nextAction;
	}

	public void setNextAction(String nextAction) {
		this.nextAction = nextAction;
	}

	/**
	 * 功能：完成任务
	 * @param
	 * @return
	 * @throws   
	 */
	public String completeTask(){
		try{
			UserInfo ui = getCurrentUser();
			
			//保存表单数据
			mobilePublicDomService.saveDom(instanceId, ui, formData);
			
			//转PDF
//			changePdf(instanceId);
			try{
			DomDocUtil.toPdf(instanceId);
			}catch(Exception e){
				e.printStackTrace();
			}
			//提交任务
			
			Task task = null;
			if(nextAction==null || nextAction.equals("")){
				ajax("102||参数nextAction不能为空");
				return null;
			}
			if(!nextAction.equals("归档")){
				task = processEngine.getTaskService().createTaskQuery().processInstanceId(instanceId).assignee(userId+"").uniqueResult();
			}else{
				List<Task> tasklist  =processEngine.getTaskService().createTaskQuery().processInstanceId(instanceId).orderAsc("id").list();
				task = tasklist.get(0);
			}
			if(task == null){
			    ajax("101||该任务已经办理完毕！");
			    return null;
			}
			publicDomService.completeTask(ui,task.getId(), nextUser,nextAction);
			ajax("100||success");
		}catch(Exception e){
			e.printStackTrace();
		}
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
		UserInfo ui = getCurrentUser();
		
		//保存表单数据
		mobilePublicDomService.saveDom(instanceId, ui, formData);
		ajaxSuccess("");
		return null;
	}
	
	
	/**
	 * @throws Exception 
	 * 功能：进入系统内收文登记页面
	 * @param
	 * @return
	 * @throws   
	 */
	public String toRegister() throws Exception{
		Set<String> set = new HashSet<String>();
		set.add(PublicDocumentConstant.SECRET_LEVEL);
		set.add(PublicDocumentConstant.HUANJI);
		set.add(PublicDocumentConstant.WENHAO);
		set.add(PublicDocumentConstant.TITLE);
		set.add(PublicDocumentConstant.SENDER_DEPT);
		Map<String,Object> vars = mobilePublicDomService.getVarsMap(instanceId, set);
		Gson gson = new Gson();
		String data = gson.toJson(vars);
		ajaxSuccess(data);
		return null;
	}
	
	/**
	 * @throws Exception 
	 * 功能：获取一级公文类型列表
	 * @param
	 * @return
	 * @throws   
	 */
	public String getFirstLevelGongwenType() throws Exception{
		List<Dict> list = infoTypeImpl.findList("dom_category", 1);
		Gson gson = new Gson();
		String data = gson.toJson(list);
		ajaxSuccess(data);
		return null;
	}
	
	/**
	 * @throws Exception 
	 * 功能：获取二级公文类型
	 * @param
	 * @return
	 * @throws   
	 */
	public String getSecondLevelGongwenType() throws Exception{
		UserInfo u = getCurrentUser();
		//List<GroupInfo> glist = groupService.getGroupByUserId(u.getCompanyId(), GroupInfo.DEPT, u.getUserId());
    	
//		GroupInfo gi = null;
//    	if(glist!=null && glist.size()>0){
//    		gi = glist.get(0);
//    	}
    	List<DocumentType> list =  new ArrayList<DocumentType>();
    	list = documentTypeService.getDocumentTypesByCategoryId(firstLevelId,u,gongwenType);
//    	if(gi!=null){
//    	}
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
    	ajaxSuccess(sb.toString());
		return null;
	}
	
	
	/**
	 * @throws Exception 
	 * 功能：系统内收文登记
	 * @param
	 * @return
	 * @throws   
	 */
	public String updateInnerSystemGather() throws Exception{
		Map<String,String> varMap = new HashMap<String, String>();
		varMap.put(PublicDocumentConstant.GONGWENTYPE_ID, gongwenTypeId);
		varMap.put(PublicDocumentConstant.GONGWENTYPE, gongwenTypeName);
		varMap.put(PublicDocumentConstant.WENHAO, wenhao);
		processEngine.getExecutionService().setVariables(instanceId, varMap);
		
		GongwenVar gongwenVar = gongwenVarService.findByInstanceId(instanceId);
		gongwenVar.setGongwenTypeName(gongwenTypeName);
		gongwenVar.setWenhao(wenhao);
		gongwenVarService.saveOrUpdate(gongwenVar);
		ajaxSuccess("success");
		return null;
	}
	
	/**
	 * @throws Exception 
	 * 功能：获取审批意见列表
	 * @param
	 * @return
	 * @throws   
	 */
	public String getAdviceList() throws Exception{
		List<AdviceObj> advicelist = mobilePublicDomService.getAdviceObjlist(instanceId, editorId);
		Gson gson = new Gson();
		String data = gson.toJson(advicelist);
		ajaxSuccess(data);
		return null;
	}
	
	/**
	 * @throws Exception 
	 * 功能：提交审批意见
	 * @param
	 * @return
	 * @throws   
	 */
	public String updateAdvice() throws Exception{
		JbpmAdvice advice = new JbpmAdvice();
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		advice.setAdviceTime(sdf.format(d));
		advice.setContent(content);
		advice.setCreateTime(sdf.format(d));
		advice.setEditorName(editorId);
		advice.setInstanceId(instanceId);
		UserInfo ui = getCurrentUser();
		advice.setUserName(ui.getUserName());
		advice.setOrderIndex(ui.getOrderIndex());
		advice.setSignType(ui.getSignType()==null?0:ui.getSignType());
		advice.setAdviceTime(sdf.format(d));
		if(advice.getSignType() == 2){
    		advice.setSignUrl(ui.getSignUrl());
    	}
		if(instanceId.startsWith("gather") || instanceId.startsWith("dispatch")){
			
		}else{
			userId = ui.getLoginName();
		}
		String taskId = processEngine.getTaskService().createTaskQuery().processInstanceId(instanceId).assignee(userId).uniqueResult().getId();
		advice.setTaskId(taskId);
		//添加审批时间
		advice.setCreateTimeOrial(sdf1.format(new Timestamp(Calendar.getInstance().getTimeInMillis())));
		advice.setHeadPhoto(ui.getPhoto());
		advice.setUserId(ui.getUserId().toString());
		publicDomService.saveAdvice(instanceId, advice);
		ajax("100||success");
		return null;
	}
}
