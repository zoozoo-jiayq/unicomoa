package cn.com.qytx.cbb.jbpmApp.action.mobile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import cn.com.qytx.cbb.customForm.domain.FormAttribute;
import cn.com.qytx.cbb.customForm.service.IFormAttribute;
import cn.com.qytx.cbb.customJpdl.domain.ProcessAttribute;
import cn.com.qytx.cbb.customJpdl.service.ProcessAttributeService;
import cn.com.qytx.cbb.jbpmApp.action.mobile.jsonModel.DataElementImplMyApproved;
import cn.com.qytx.cbb.jbpmApp.action.mobile.jsonModel.DataElementImplMyStart;
import cn.com.qytx.cbb.jbpmApp.action.mobile.jsonModel.DataElementImplNotApproved;
import cn.com.qytx.cbb.jbpmApp.action.mobile.jsonModel.DataElementImplWaitApprove;
import cn.com.qytx.cbb.jbpmApp.action.mobile.jsonModel.IJson;
import cn.com.qytx.cbb.jbpmApp.action.mobile.jsonModel.JsonImplNull;
import cn.com.qytx.cbb.jbpmApp.action.mobile.jsonModel.JsonImplServerException;
import cn.com.qytx.cbb.jbpmApp.action.mobile.jsonModel.JsonImplSuccess;
import cn.com.qytx.cbb.jbpmApp.domain.WorkFlowView;
import cn.com.qytx.cbb.jbpmApp.service.IWorkFlowService;
import cn.com.qytx.cbb.jbpmApp.service.impl.command.SearchWaitProcessTaskList;
import cn.com.qytx.cbb.jbpmApp.service.mobile.JbpmMobileService;
import cn.com.qytx.cbb.publicDom.service.PublicDomService;
import cn.com.qytx.cbb.publicDom.vo.PublicDomView;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IUser;

import com.google.gson.Gson;

/**
 * 功能：查询列表
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 上午9:06:28 
 * 修改日期：上午9:06:28 
 * 修改列表：
 */
public class SearchListAction extends MyBaseAction {

	@Resource(name="jbpmMobileService")
	private JbpmMobileService jbpmMobileService;
	/** 流程定义服务类 */
	@Resource(name="processAttributeService")
	private ProcessAttributeService processAttributeService;
	/** 表单基本属性服务类 */
	@Resource(name = "formAttributeService")
	IFormAttribute formAttributeService;
	/*@Resource(name = "notifyService")
	private INotify notifyService; //公告服务类
*/	@Resource(name = "workFlowService")
	IWorkFlowService workFlowService;  //工作流服务
	@Resource(name="publicDomService")
	private PublicDomService publicDomService;
	@Autowired
	private IUser userService;
	
	private String instanceId;
    private String formSource;
	/**
	 * @throws Exception 
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 * 功能：我发起的
	 * @param
	 * @return
	 * @throws   
	 */
	public String myStart() throws Exception{
		IJson json = null;
		try{
			UserInfo u = userService.findOne(Integer.parseInt(userId));
			 List<DataElementImplMyStart>  viewlist = jbpmMobileService.findByStart(u.getLoginName(),getPageable());
			 if(viewlist!=null && viewlist.size()>0){
				 json = new JsonImplSuccess<List<DataElementImplMyStart>>();
				 json.setDatas(viewlist);
//				 json.setCurrentPage(getPage().getPageNo()*getPage().getPageSize());
			 }else{
				 json = new JsonImplNull();
			 }
		}catch(Exception e){
			json = new JsonImplServerException();
			e.printStackTrace();
		}finally{
			if(json!=null){
				ajax(json);
			}
		}
		return null;
	}
	
	/**
	 * @throws Exception 
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 * 功能：待我审批
	 * @param
	 * @return
	 * @throws   
	 */
	public String notProcessed() throws Exception{
		UserInfo u = userService.findOne(Integer.parseInt(userId));
		DataElementImplNotApproved result = jbpmMobileService.findMyNotApproved(u.getLoginName());
		IJson json = null;
		try{
			json = new JsonImplSuccess<DataElementImplNotApproved>();
			json.setDatas(result);
		}catch(Exception e){
			json = new JsonImplServerException();
			e.printStackTrace();
		}finally{
			if(json!=null){
				ajax(json);
			}
		}
		
		return null;
	}
	
	/**
	 * @throws Exception 
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 * 功能：待审批
	 * @param
	 * @return
	 * @throws   
	 */
	public String notProcessed_waitApprove() throws Exception{
		IJson json = null;
		try{
			UserInfo u = userService.findOne(Integer.parseInt(userId));
			 List<DataElementImplWaitApprove>  viewlist = jbpmMobileService.findMyWaitApprove(0,u.getLoginName(), getPageable());
			 if(viewlist!=null && viewlist.size()>0){
				 json = new JsonImplSuccess<List<DataElementImplWaitApprove>>();
				 json.setDatas(viewlist);
			 }else{
				 json = new JsonImplNull();
			 }
		}catch(Exception e){
			json = new JsonImplServerException();
			e.printStackTrace();
		}finally{
			if(json == null){
				json = new JsonImplServerException();
			}
			ajax(json);
		}
		return null;
	}
	
	/**
	 * @throws Exception 
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 * 功能：已挂起
	 * @param
	 * @return
	 * @throws   
	 */
	public String notProcessed_suspend() throws Exception{
		IJson json = null;
		try{
			UserInfo u = userService.findOne(Integer.parseInt(userId));
			 List<DataElementImplWaitApprove>  viewlist = jbpmMobileService.findMyWaitApprove(1,u.getLoginName(), getPageable());
			 if(viewlist!=null && viewlist.size()>0){
				 json = new JsonImplSuccess<List<DataElementImplWaitApprove>>();
				 json.setDatas(viewlist);
			 }else{
				 json = new JsonImplNull();
			 }
		}catch(Exception e){
			json = new JsonImplServerException();
			e.printStackTrace();
		}finally{
			if(json!=null){
				ajax(json);
			}
		}
		return null;
	}
	
	/**
	 * @throws Exception 
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 * 功能：经我处理
	 * @param
	 * @return
	 * @throws   
	 */
	public String myProcessed() throws Exception{
		IJson json = null;
		try{
			UserInfo u = userService.findOne(Integer.parseInt(userId));
			 List<DataElementImplMyApproved>  viewlist = jbpmMobileService.findMyApproved(u.getLoginName(),getPageable());
			 if(viewlist!=null && viewlist.size()>0){
				 json = new JsonImplSuccess<List<DataElementImplWaitApprove>>();
				 json.setDatas(viewlist);
			 }else{
				 json = new JsonImplNull();
			 }
		}catch(Exception e){
			json = new JsonImplServerException();
			e.printStackTrace();
		}finally{
			if(json!=null){
				ajax(json);
			}
		}
		return null;
	}
	
	/**
	 * 获取待办的总数
	 * @return
	 */
	public String getApproveCount(){
		//type:1公文；2工作流
		UserInfo user = userService.findOne(Integer.valueOf(userId));
		int size =jbpmMobileService.getApproveCount(userId); //待处理公文个数
		String loginName = user.getLoginName();
		int size2 =jbpmMobileService.getApproveCount(loginName);//待处理工作流个数
		int userIdInt = 0;
		if(userId!=null&&!"".equals(userId)){
			userIdInt = Integer.parseInt(userId);
		}
		//公告个数
		int notifyCount = 0;
		//notifyCount=notifyService.countOfNotReadNotify(userIdInt,1);
		
		//公文阅读数量
		List<String> nodeNames = new ArrayList<String>();
		nodeNames.add("收文阅读");
		Page<PublicDomView> page = publicDomService.searchWaittingProcessTask("gather", nodeNames, null, userId, null, getPageable());
		 //int readCount = getPage().getTotalCount();
		
		try {
			ajax("100||"+size2+","+size+","+notifyCount+","+page.getTotalElements());
//			ajax("100||"+size2+","+size+","+notifyCount+","+0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	 /**
	 * @throws Exception 
	 * 功能：查询工作流的数量
	 * @param
	 * @return
	 * @throws   
	 */
	public String flowCount() throws Exception{
		Map<String,Long> map = new HashMap<String, Long>();
		UserInfo u = userService.findOne(Integer.parseInt(userId));
		Page<WorkFlowView> workPage1 = workFlowService.findByStartList(u.getLoginName(),"", getPageable());
		map.put("myStarted",workPage1.getTotalElements());
		Page<WorkFlowView> workPage2 = workFlowService.findWaitProcessViewlist(u.getLoginName(), SearchWaitProcessTaskList.PROCESS_STATE_ACTIVE,"", getPageable());
		map.put("waitProcess",workPage2.getTotalElements());
		Page<WorkFlowView> workPage3 = workFlowService.findWaitProcessViewlist(u.getLoginName(), SearchWaitProcessTaskList.PROCESS_STATE_SUSPEND,"", getPageable());
		map.put("suspended", workPage3.getTotalElements());
		Page<WorkFlowView> workPage4 = workFlowService.findByApprovedList(u.getLoginName(),"",getPageable());
		map.put("completed", workPage4.getTotalElements());
		Gson gson = new Gson();
		String data = gson.toJson(map);
		ajax("100||"+data);
		 return null;
	 }
	
	public String formView() throws Exception{
		try{
			instanceId =  java.net.URLDecoder.decode(instanceId,"UTF-8"); 
		}catch(Exception e){
			e.printStackTrace();
		}
		if(instanceId!=null&&!"".equals(instanceId)){
			String processId = workFlowService.getVariablebyInstance(instanceId, "processAttributeId");
			ProcessAttribute pa =processAttributeService.getProcessById(Integer.parseInt(processId));
			if(pa!=null){
			 FormAttribute formAttribute=formAttributeService.findById(pa.getFormId());
                formSource = formAttribute.getFormSource();
			}
		}
    	return SUCCESS;
    }

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getFormSource() {
		return formSource;
	}

	public void setFormSource(String formSource) {
		this.formSource = formSource;
	}
	
	
	
}


