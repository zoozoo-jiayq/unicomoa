package cn.com.qytx.cbb.publicDom.action.mobile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.task.Task;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.qytx.cbb.customForm.domain.FormAttribute;
import cn.com.qytx.cbb.file.config.FilePathConfig;
import cn.com.qytx.cbb.file.service.IAttachment;
import cn.com.qytx.cbb.jbpmApp.service.IWorkFlowService;
import cn.com.qytx.cbb.publicDom.domain.GongwenVar;
import cn.com.qytx.cbb.publicDom.mobile.vo.AttachObj;
import cn.com.qytx.cbb.publicDom.mobile.vo.MobileReadState;
import cn.com.qytx.cbb.publicDom.mobile.vo.MobleViewProcessDetail;
import cn.com.qytx.cbb.publicDom.service.DocumentTypeService;
import cn.com.qytx.cbb.publicDom.service.GongwenVarService;
import cn.com.qytx.cbb.publicDom.service.IDocumentExtService;
import cn.com.qytx.cbb.publicDom.service.PublicDocumentConstant;
import cn.com.qytx.cbb.publicDom.service.PublicDomService;
import cn.com.qytx.cbb.publicDom.service.impl.PublicDomServiceImpl;
import cn.com.qytx.cbb.publicDom.service.mobile.MobilePublicDomService;
import cn.com.qytx.cbb.publicDom.vo.ApproveHistoryRecord;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IUser;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 功能：公文详情控制器
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 上午11:51:43 
 * 修改日期：上午11:51:43 
 * 修改列表：
 */
public class MobileDetailAction extends MobileBaseAction {

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
	@Resource
	private IWorkFlowService workflowService;
	@Resource
	private FilePathConfig filePathConfig;
	@Resource(name="documentTypeService")
	private DocumentTypeService documentTypeService;
	
	@Resource(name="gongwenVarService")
	private GongwenVarService gongwenVarService;
	
	@Autowired
	private IUser userService;
	
	private String instanceId;
	private String nextUser;
	private String nextAction;
	private String menu;
	
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


	public String getInstanceId() {
		return instanceId;
	}


	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getMenu() {
		return menu;
	}


	public void setMenu(String menu) {
		this.menu = menu;
	}


	/**
	 * @throws Exception 
	 * 功能：查询公文详情
	 * @param
	 * @return
	 * @throws   
	 */
	public String searchGongwenDetail() throws Exception{
		MobleViewProcessDetail mvpd = mobilePublicDomService.findDomDetail(instanceId, userId+"");
		if(mvpd!=null && mvpd.getAttach()!=null && mvpd.getAttach().size()>0){
			String path = getRequest().getContextPath();
			String basePath = getRequest().getScheme()+"://"+getRequest().getServerName()+":"+getRequest().getServerPort()+path+"/";
			for(AttachObj ao: mvpd.getAttach()){
				ao.setViewUrl(basePath+"filemanager/previewDoc.action?_clientType=wap&attachmentId="+ao.getId());
				ao.setUrl(basePath+"filemanager/downfile.action?_clientType=wap&attachmentId="+ao.getId());
			}
		}
		
		Gson gson = new Gson();
		String result = gson.toJson(mvpd);
		ajaxSuccess(result);
		return null;
	}
	
	/**
	 * @throws IOException 
	 * 功能：获取PDf
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
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * 功能：查询原发文单详情
	 * @param
	 * @return
	 * @throws   
	 */
	public String searchSourceCustom() throws IOException{
		String sourceInstanceId = workflowService.getVariablebyInstance(instanceId, PublicDocumentConstant.GATHER_SOURCE_DISPATCH_EXECUTIONID);
		PublicDomServiceImpl impl = (PublicDomServiceImpl)publicDomService;
		File f = impl.getCustomerForm(sourceInstanceId,null);
		if(f==null){
			return null;
		}
		FileInputStream fis = new FileInputStream(f);
		this.getResponse().setCharacterEncoding("GBK");
		byte[] bs = new byte[1024];
		OutputStream out = this.getResponse().getOutputStream();
		while(fis.read(bs)!=-1){
			out.write(bs);
		}
		out.flush();
		out.close();
		fis.close();
		return null;
	}
	
	/**
	 * @throws Exception 查看原始表单
	 * 功能：
	 * @param
	 * @return
	 * @throws   
	 */
	public String searchOrialForm() throws Exception{
		FormAttribute fa = publicDomService.getFormSourceByInstanceId(instanceId);
		if(fa!=null){
			String source = fa.getFormSource();
			ajax(source);
		}else{
			ajax("");
		}
		return null;
	}
	
	/**
	 * @throws Exception 
	 * 功能：查询阅读状态
	 * @param
	 * @return
	 * @throws   
	 */
	public String searchReadState() throws Exception{
		MobileReadState mrs = mobilePublicDomService.findReadStateByInstanceId(instanceId);
		Gson gson = new Gson();
		String data = gson.toJson(mrs);
		ajaxSuccess(data);
		return null;
	}
	
	/**
	 * 功能：获取审批历史记录
	 * @param
	 * @return
	 * @throws   
	 */
	public String searchApproveHistory() throws Exception{
	    ProcessInstance pi = processEngine.getExecutionService().createProcessInstanceQuery().processInstanceId(instanceId).uniqueResult();
	    String str = "";
	    if(pi!=null){
	    	str = (String) processEngine.getExecutionService().getVariable(instanceId, PublicDocumentConstant.APPROVE_HIST_RECORD);
	    }else{
	    	str = (String) processEngine.getHistoryService().getVariable(instanceId, PublicDocumentConstant.APPROVE_HIST_RECORD);
	    }
		Gson gson = new Gson();
		List<ApproveHistoryRecord> list = gson.fromJson(str, new TypeToken<List<ApproveHistoryRecord>>(){}.getType());
		if(list == null){
			ajaxSuccess("[]");
			return null;
		}
		if(list!=null){
			String phones = "";
			for(Iterator<ApproveHistoryRecord> it = list.iterator(); it.hasNext();){
				ApproveHistoryRecord ahr = it.next();
				String option = ahr.getOption();
				if(option.equals("创建发文单") || option.equals("创建收文单")){
					it.remove();
				}else if(option.equals("发文拟稿")){
					ahr.setOption("拟稿");
				}else if(option.equals("发文核稿")){
					ahr.setOption("核稿");
				}else if(option.equals("套红盖章")){
					ahr.setOption("套红");
				}else if(option.equals("收文登记")){
					ahr.setOption("登记");
				}else if(option.equals("领导批阅")){
					ahr.setOption("批阅");
				}else if(option.equals("收文分发")){
					ahr.setOption("分发");
				}else if(option.equals("收文阅读")){
					ahr.setOption("阅读");
				}
				if(StringUtils.isNotEmpty(ahr.getPhone())) {
					if(phones.indexOf(ahr.getPhone())<0){
						phones+=ahr.getPhone()+",";
					}
				}
			}
			if(StringUtils.isNotEmpty(phones)){
				List<UserInfo> userList = userService.getUserInfoByPhones(phones);
				if(userList!=null && userList.size()>0){
					Map<String, UserInfo> phoneUserMap = new HashMap<String, UserInfo>();
					for(UserInfo user : userList){
						if(user.getIsVirtual()==null || user.getIsVirtual()!=1){
							phoneUserMap.put(user.getPhone(), user);
						}
					}
					for(ApproveHistoryRecord ahr : list){
						if(StringUtils.isNotEmpty(ahr.getPhone())){
							UserInfo user = phoneUserMap.get(ahr.getPhone());
							ahr.setUserId(user.getUserId());
							ahr.setPhoto(user.getPhoto());
						}
					}
				}
			}
		}
		ajaxSuccess(gson.toJson(list));
		return null;
	}
	
	/**
	 * @throws Exception 
	 * 功能：查询当前的可用操作
	 * @param
	 * @return
	 * @throws   
	 */
	public String getEnableOperations() throws Exception{
		Task task = null;
		int m = 0;
		if(menu!=null && menu.equals("gd")){
			m = 9;
			List<Task> tasklist = processEngine.getTaskService().createTaskQuery().processInstanceId(instanceId).list();
			if(tasklist == null || tasklist.size() == 0 ){
				ajax("100||流程已结束");
				return null;
			}else{
				task = tasklist.get(0);
			}
			
		}else{
			task = processEngine.getTaskService().createTaskQuery().processInstanceId(instanceId).assignee(userId+"").uniqueResult();
		}
		if(task!=null){
			List<String> list = publicDomService.getEnableOperations(task.getId(), m);
			Gson gson = new Gson();
			String str = gson.toJson(list);
			ajax("100||"+str);
		}else{
			ajax("100||[]");
		}
		return null;
	}

	/**
	 * 功能：获得公文的基本信息
	 */
	public void getGongWenBaseInfo(){
		try {
			GongwenVar gongwenVar = gongwenVarService.findByInstanceId(instanceId);
			Map<String, Object> map = new HashMap<String, Object>();
			if(gongwenVar!=null){
				map.put("title", gongwenVar.getTitle()==null?"":gongwenVar.getTitle());//公文标题
				map.put("miji", gongwenVar.getMiji()==null?"":gongwenVar.getMiji());//公文密级
				map.put("huanji", gongwenVar.getHuanji()==null?"":gongwenVar.getHuanji());//公文缓急
				map.put("wenhao", gongwenVar.getWenhao()==null?"":gongwenVar.getWenhao());//公文文号
				map.put("fromGroup", gongwenVar.getFromGroup()==null?"":gongwenVar.getFromGroup());//发文单位
			}
			Gson gson = new Gson();
			String str = gson.toJson(map);
			ajax("100||"+str);
		} catch (Exception e) {
			e.printStackTrace();
			ajax("101||服务器异常");
		}
	}
}
