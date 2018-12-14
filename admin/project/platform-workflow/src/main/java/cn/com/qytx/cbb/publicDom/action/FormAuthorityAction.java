package cn.com.qytx.cbb.publicDom.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.jbpm.api.task.Task;

import cn.com.qytx.cbb.customForm.domain.FormAttribute;
import cn.com.qytx.cbb.customForm.domain.FormCategory;
import cn.com.qytx.cbb.customForm.domain.FormProperties;
import cn.com.qytx.cbb.customForm.service.IFormAttribute;
import cn.com.qytx.cbb.customForm.service.IFormCategory;
import cn.com.qytx.cbb.customForm.service.IFormProperties;
import cn.com.qytx.cbb.customJpdl.domain.NodeFormAttribute;
import cn.com.qytx.cbb.customJpdl.service.NodeFormAttributeService;
import cn.com.qytx.cbb.jbpmApp.service.IWorkFlowService;
import cn.com.qytx.cbb.publicDom.domain.FormAuthority;
import cn.com.qytx.cbb.publicDom.service.IFormAuthority;
import cn.com.qytx.cbb.publicDom.service.PublicDomService;
import cn.com.qytx.platform.base.application.TransportUser;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.RoleInfo;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IUser;

import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionContext;

/**
 * 功能：表单权限控制
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 下午3:22:34 
 * 修改日期：下午3:22:34 
 * 修改列表：
 */
public class FormAuthorityAction extends MyBaseAction {

	@Resource(name="formCategoryService")
	IFormCategory formCategoryService;
	
	@Resource(name="formAttributeService")
	IFormAttribute formAttributeService;
	
	@Resource(name="formPropertiesService")
	IFormProperties formPropertiesService;
	
	@Resource(name="formAuthorityService")
	IFormAuthority formAuthorityService;
	
	@Resource(name="nodeFormAttributeService")
	NodeFormAttributeService nodeFormAttributeService;
	
	@Resource(name="publicDomService")
	private PublicDomService publicDomService;
	
	@Resource
	private IWorkFlowService workflowService;
	 /** 用户信息 */
    @Resource(name = "userService")
    IUser userService;
	private int formType;
	private String formName;
	private int formId;
	private int propertyId;
	private FormAuthority formAuthority;
	private String taskId = "";
	private String userIds;
	private Integer formAuId;
	
	public FormAuthority getFormAuthority() {
		return formAuthority;
	}

	public Integer getFormAuId() {
		return formAuId;
	}

	public void setFormAuId(Integer formAuId) {
		this.formAuId = formAuId;
	}

	public void setFormAuthority(FormAuthority formAuthority) {
		this.formAuthority = formAuthority;
	}

	public int getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(int propertyId) {
		this.propertyId = propertyId;
	}

	public int getFormType() {
		return formType;
	}

	public void setFormType(int formType) {
		this.formType = formType;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public String getUserIds() {
		return userIds;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

	public int getFormId() {
		return formId;
	}

	public void setFormId(int formId) {
		this.formId = formId;
	}

	/**
	 * 功能：进入列表界面
	 * @param
	 * @return
	 * @throws   
	 */
	public String toSet(){
		return "toSet";
	}
	
	/**
	 * 功能：获取所有表单分类
	 * @param
	 * @return
	 * @throws   
	 */
	public List<FormCategory> getFormCategorys(){
		return formCategoryService.findAll();
	}
	
	/**
	 * 功能：
	 * @param
	 * @return
	 * @throws   
	 */
	public String getFormList(){
        Page<FormAttribute> page = formAttributeService.findByPage(getPageable(), getLoginUser().getCompanyId(),  formName,0);
        List<FormAttribute> list = page.getContent();
        List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
        for(int i=0; i<list.size(); i++){
            FormAttribute fa = (FormAttribute) list.get(i);
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("no", i+1);
            map.put("formName", fa.getFormName());
            FormCategory temp = formCategoryService.findById(fa.getCategoryId());
            map.put("formType", temp==null?"":temp.getCategoryName());	            
            map.put("option","");
            map.put("formId", fa.getFormId());
            result.add(map);
        }
        ajaxPage(page, result);
		return null;
	}
	
	/**
	 * 功能：进入权限属性设置列表界面
	 * @param
	 * @return
	 * @throws   
	 */
	public String toPropertyList(){
		return "toPropertyList";
	}
	
	/**
	 * 设置权限用户
	 * @return
	 */
	public String setAuthority(){
	      FormAuthority fau =null;
		  if(formAuId>0){
			    fau = formAuthorityService.findByFormPropertyId(formAuId);
		  }
		if(fau!=null){
			fau.setCandidate(userIds);
			formAuthorityService.saveOrUpdateFormAuthority(fau);
		}else{
			FormAuthority fauNew = new FormAuthority();
			FormProperties fatt = formPropertiesService.findById(propertyId);
			String propertyName="";
			if(fatt!=null){
				  propertyName=fatt.getPropertyName();
			}
			fauNew.setCandidate(userIds);
			fauNew.setFormPropertyId(propertyId);
			fauNew.setFormId(formId);
			fauNew.setPropertyName(propertyName);
			fauNew.setCompanyId(TransportUser.get().getCompanyId());
			formAuthorityService.saveOrUpdateFormAuthority(fauNew);
		}
		ajax("success");
		 return null;
	}
	
	public String getPropertyList(){
		Page<FormProperties> page = formPropertiesService.findByPage(getPageable(), formId);
		List<FormAuthority> aulist = formAuthorityService.findByFormId(formId);
		Map<String,FormAuthority> formAuthMap = new HashMap<String,FormAuthority>(); 
		for(FormAuthority fa:aulist){
			formAuthMap.put(fa.getFormPropertyId()+"", fa);
		}
		List<FormProperties> list = page.getContent();
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		for(int i=0; i<list.size(); i++){
			StringBuffer userIds=new StringBuffer();
			String uIds = "";
			StringBuffer userNames=new StringBuffer();
			String uNames="";
			FormProperties fa = (FormProperties) list.get(i);
			Map<String,Object> map = new HashMap<String, Object>();
			FormAuthority formAu = null;
			if(fa!=null && fa.getPropertyId()!=null){
				formAu = formAuthMap.get(fa.getPropertyId()+"");
			}
			if(formAu!=null){
				String candidateStr = formAu.getCandidate();
				 List<UserInfo> userList = getUserList(candidateStr);
				 for(UserInfo userInfo :userList){
					 userIds.append(userInfo.getUserId()+",");
					 userNames.append(userInfo.getUserName()+",");
				 }
				 if(userIds.toString().endsWith(",")){
					 uIds = userIds.toString().substring(0,userIds.toString().length()-1);
				 }
				 if(userNames.toString().endsWith(",")){
					 uNames = userNames.toString().substring(0,userNames.toString().length()-1);
				 }
			}
			map.put("no", i+1);
			map.put("propertyNameCh", fa==null?"":fa.getPropertyNameCh());
			map.put("formId", fa==null?"":fa.getFormId());
			map.put("userNames",uNames);
			map.put("userIds", uIds);
			map.put("propertyId", fa!=null?fa.getPropertyId():"");
			map.put("formAuId",formAu!=null?formAu.getId():"0" );
			result.add(map);
		}
		ajaxPage(page,result);
		return null;
	}
	
	private List<UserInfo>  getUserList(String candidateStr){
		UserInfo user = (UserInfo) ActionContext.getContext().getSession().get("adminUser");//得到登陆用户信息
		List<UserInfo> userAlllist = new ArrayList<UserInfo>();//所有人员
		if (candidateStr != null && !candidateStr.equals("")) {
			String[] useridArr = candidateStr.split(",");
			if (useridArr != null && useridArr.length > 0) {
				for (int i = 0; i < useridArr.length; i++) {
					int userid = Integer.parseInt(useridArr[i]);
					UserInfo  u = userService.findOne(userid);
					if(u!=null){
						userAlllist.add(u);
					}
				}
			}
		}
		return userAlllist;
	}
	
	/**
	 * 功能：获取所有的表单属性
	 * @param
	 * @return
	 * @throws   
	 */
	public List<FormProperties> getFormProperties(){
		List<FormProperties> result = formPropertiesService.findByFormId(formId);
		List<FormAuthority> aulist = formAuthorityService.findByFormId(formId);
		for (Iterator iterator = aulist.iterator(); iterator.hasNext();) {
			FormAuthority formAuthority = (FormAuthority) iterator.next();
			boolean b1 = formAuthority.getCandidate()!=null && !formAuthority.getCandidate().equals("");
			boolean b2 = formAuthority.getGroups()!=null && !formAuthority.getGroups().equals("");
			boolean b3 = formAuthority.getRoles()!=null && !formAuthority.getRoles().equals("");
			if(b1 || b2|| b3){
				for(int i=0; i<result.size(); i++){
					FormProperties fp = result.get(i);
					if(fp.getPropertyId().intValue() == formAuthority.getFormPropertyId().intValue()){
						fp.setState("已设置");
						break;
					}
				}
			}else{
				for(int i=0; i<result.size(); i++){
					FormProperties fp = result.get(i);
					if(fp.getPropertyId().intValue() == formAuthority.getFormPropertyId().intValue()){
						fp.setState("未设置");
						break;
					}
				}
			}
		}
		return result;
	}
	
	
  /**
    * 获取有权限的字段
    * @return
    */
	public String getReadOnlyFormAuthority(){
		List<Task> tasks = processEngine.getTaskService().createTaskQuery().processInstanceId(instanceId).list();
		if(tasks!=null){
			String gongwenTypeId = workflowService.getVariablebyInstance(instanceId, "gongwenType_id");
			int processId = (gongwenTypeId == null?0:Integer.parseInt(gongwenTypeId)); 
			NodeFormAttribute nodeForm  = nodeFormAttributeService.findByProcessIdAndTaskName(processId*-1, tasks.get(0).getName());
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
	
	/**获取保密字段
	 * @return
	 */
	public String getSecretProperties(){
		List<Task> tasks = processEngine.getTaskService().createTaskQuery().processInstanceId(instanceId).list();
		if(tasks!=null){
			String gongwenTypeId = workflowService.getVariablebyInstance(instanceId, "gongwenType_id");
			int processId = (gongwenTypeId == null?0:Integer.parseInt(gongwenTypeId)); 
			NodeFormAttribute nodeForm  = nodeFormAttributeService.findByProcessIdAndTaskName(processId*-1, tasks.get(0).getName());
			if(nodeForm!=null){
				String secrets = nodeForm.getSecretProperties();
				if(secrets!=null&&!"".equals(secrets)){
					if (secrets.length()>1&&secrets.endsWith(",")) {
						secrets = secrets.substring(0, secrets.length()-1);
					}
					List<FormProperties>	 formProList = formPropertiesService.findAllByIds(secrets);
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
	 * 功能：设置表单属性的权限
	 * @param
	 * @return
	 * @throws   
	 */
	public String toSetUsers(){
		return "toSetUsers";
	}
	
	/**
	 * 功能：根据表单属性ID查找表单属性
	 * @param
	 * @return
	 * @throws   
	 */
	public FormProperties getFormP(){
		return formPropertiesService.findById(propertyId);
	}
	
	/**
	 * 功能：获取用户信息
	 * @param
	 * @return
	 * @throws   
	 */
	public List<UserInfo> getUserInfos(){
		FormAuthority  fa = formAuthorityService.findByFormPropertyId(propertyId);
		if(fa!=null){
			String candidate = fa.getCandidate();
			if(candidate!=null && !candidate.equals("")){
				if(candidate.endsWith(",")){
					candidate = candidate.substring(0, candidate.length()-1);
					List<UserInfo> userInfo = nodeFormAttributeService.findByUserIds(candidate);
					return userInfo;
				}
			}
		}
		return null;
	}
	
	/**
	 * 功能：获取部门信息
	 * @param
	 * @return
	 * @throws   
	 */
	public List<GroupInfo> getGroupInfos(){
		FormAuthority  fa = formAuthorityService.findByFormPropertyId(propertyId);
		if(fa!=null){
			String groups = fa.getGroups();
			if(groups!=null && !groups.equals("")){
				if(groups.endsWith(",")){
					groups = groups.substring(0, groups.length()-1);
					List<GroupInfo> groupInfo = nodeFormAttributeService.findByGroupIds(groups);
					return groupInfo;
				}
			}
		}
		return null;
	}
	
	public FormAuthority getFormAu(){
		return  formAuthorityService.findByFormPropertyId(propertyId);
	
	}
	
	/**
	 * 功能：获取角色信息
	 * @param
	 * @return
	 * @throws   
	 */
	public List<RoleInfo> getRoleInfos(){
		FormAuthority  fa = formAuthorityService.findByFormPropertyId(propertyId);
		if(fa!=null){
			String roles = fa.getRoles();
			if(roles!=null && !roles.equals("")){
				if(roles.endsWith(",")){
					roles = roles.substring(0, roles.length()-1);
					List<RoleInfo> roleInfos = nodeFormAttributeService.findByRoleIds(roles);
					return roleInfos;
				}
			}
		}
		return null;
	}
	
	/**
	 * 功能：保存权限信息
	 * @param
	 * @return
	 * @throws   
	 */
	public String saveFormAuthority(){
		formAuthority.setCompanyId(getLoginUser().getCompanyId());
		formAuthorityService.saveOrUpdateFormAuthority(formAuthority);
		return "toPropertyList";
	}
	
	/**
	 * @throws Exception 
	 * 功能：获取当前用户对表单各个属性的编辑权限，
	 * 输入参数包括：当前用户ID，表单ID
	 * @param
	 * @return
	 * @throws   
	 */ 
	public String getCurrentUserAuthority() throws Exception{
		FormAttribute fa = publicDomService.getFormSource(taskId);
		Integer userId = getLoginUser().getUserId();
		List<String> list = formAuthorityService.findHasAuthorityProperties(fa.getFormId().toString(), userId+"");
		Gson gson = new Gson();
		String result = gson.toJson(list);
		ajax(result);
		return null;
	}
}
