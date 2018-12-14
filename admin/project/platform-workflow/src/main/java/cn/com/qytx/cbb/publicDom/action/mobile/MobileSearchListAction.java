package cn.com.qytx.cbb.publicDom.action.mobile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.jbpm.api.ProcessEngine;
import org.jbpm.api.model.Activity;
import org.jbpm.api.model.Transition;
import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.model.ProcessDefinitionImpl;
import org.jbpm.pvm.internal.model.TransitionImpl;

import cn.com.qytx.cbb.customJpdl.domain.NodeFormAttribute;
import cn.com.qytx.cbb.customJpdl.service.NodeFormAttributeService;
import cn.com.qytx.cbb.publicDom.mobile.vo.MobilePublicDomView;
import cn.com.qytx.cbb.publicDom.service.PublicDomService;
import cn.com.qytx.cbb.publicDom.service.mobile.MobilePublicDomService;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.ICompany;
import cn.com.qytx.platform.org.service.IGroup;
import cn.com.qytx.platform.org.service.IUser;
import cn.com.qytx.platform.utils.tree.TreeNode;

import com.google.gson.Gson;


/**
 * 功能：公文列表查询
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 下午3:09:01 
 * 修改日期：下午3:09:01 
 * 修改列表：
 */
public class MobileSearchListAction extends MobileBaseAction {
	
	@Resource(name="publicDomService")
	private PublicDomService publicDomService;
	
	@Resource(name="mobilePublicDomService")
	private MobilePublicDomService mobilePublicDomService;
	
    /**用户信息*/
    @Resource(name = "userService")
    IUser userService;
    /**
     * 部门/群组管理接口
     */
    @Resource(name = "groupService")
    private IGroup groupService;
    @Resource(name="companyService")
    private ICompany companyService;
    @Resource
    private IGroup groupInfoVOService;
    @Resource
    private ProcessEngine processEngine;
    @Resource
    private NodeFormAttributeService nodeFormAttributeService;
	
	private String menu ;
	private String searchType;
	private String title;
	private String instanceId;
	private String nextAction;
	
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}


	public String getMenu() {
		return menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}

	/**
	 * 功能：查询未完成的公文列表
	 * @param
	 * @return
	 * @throws   
	 */
	public String notCompleted() throws Exception{
		List<MobilePublicDomView> viewlist = mobilePublicDomService.findNotCompletedDom(getCurrentUser(), menu, getPageable());
		Gson gon = new Gson();
		String data = gon.toJson(viewlist);
		ajaxSuccess(data);
		return null;
	}
	
	/**
	 * 功能：查询已完成的公文列表
	 * @param
	 * @return
	 * @throws   
	 */
	public String completed() throws Exception{
		List<MobilePublicDomView> viewlist = mobilePublicDomService.findCompletedDom(getCurrentUser(), menu,title,getPageable());
		Gson gson = new Gson();
		String data = gson.toJson(viewlist);
		ajaxSuccess(data);
		return null;
	}
	
	/**
	 * 功能：查询未处理的公文数量
	 * @param
	 * @return
	 * @throws   
	 */
	public String notCompletedCount() throws Exception{
		Map<String,Long> map = mobilePublicDomService.findNotCompletedTaskCount(searchType, getCurrentUser(), getPageable());
		Gson gson = new Gson();
		String data = gson.toJson(map);
		ajaxSuccess(data);
		return null;
	}
	
	/**
	 * @throws Exception 
	 * 功能：获取用户所属二级局的所有单位和人员
	 * @param
	 * @return
	 * @throws   
	 */
	public String getUsersAndGroups() throws Exception{
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("isSingle", "true");
		Task task = processEngine.getTaskService().createTaskQuery().processInstanceId(instanceId).assignee(userId).uniqueResult();
		String documentType = (String) processEngine.getExecutionService().getVariable(task.getExecutionId(), "gongwenType_id");
		String documentTypeFlag = instanceId.substring(0, instanceId.indexOf("."));
		ProcessDefinitionImpl define = (ProcessDefinitionImpl) processEngine.getRepositoryService().createProcessDefinitionQuery().processDefinitionKey(documentTypeFlag).uniqueResult();
		Transition tran =  define.findActivity(task.getName()).findOutgoingTransition(nextAction);
		Activity activity = getNextTaskActivity(tran.getDestination());
		
		if(activity.getName().equals("收文阅读")){
			result.put("isSingle", "false");
		}
		NodeFormAttribute nodeFormAtt = nodeFormAttributeService.findByProcessIdAndTaskName(Integer.parseInt(documentType)*-1, activity.getName());
		String candidateStr = nodeFormAtt.getCandidate();
		String deptsStr = nodeFormAtt.getDepts();
		String rolesStr = nodeFormAtt.getRoles();
		List<UserInfo> userlist = userService.findUsersByIds(candidateStr, deptsStr, rolesStr);
		List<TreeNode> nodelist = new ArrayList<TreeNode>();
		String ids = "";
		for (UserInfo user : userlist){
            TreeNode treeNode = new TreeNode();
            treeNode.setId("uid_" + user.getUserId());//部门ID前加gid表示类型为部门
            treeNode.setName(user.getUserName());
            treeNode.setPId("gid_"+(user.getGroupId()==null?"":user.getGroupId().toString()));
            nodelist.add(treeNode);
            ids+=user.getUserId()+",";
        }
		if(ids.endsWith(",")){
			ids = ids.substring(0, ids.length()-1);
		}
		List<GroupInfo> grouplist = groupService.getGroupsByUserIds(getCurrentUser().getCompanyId(),  ids);
    	for(int i=0; i<grouplist.size(); i++){
    		GroupInfo giv = grouplist.get(i);
    		TreeNode treeNode = new TreeNode();
            treeNode.setId("gid_"+giv.getGroupId());
            treeNode.setName(giv.getGroupName());
            treeNode.setPId("gid_"+giv.getParentId());
            nodelist.add(treeNode);
    	}
		result.put("orgs", nodelist);
		Gson gson = new Gson();
		ajaxSuccess(gson.toJson(result));
		return null;
	}
	
	private Activity getNextTaskActivity(Activity activity){
		if(activity.getType().equals("task")){
			return activity;
		}else{
			List<TransitionImpl> list = (List<TransitionImpl>) activity.getOutgoingTransitions();
			if(list!=null){
				Activity nextactivity = list.get(0).getDestination(); 
				return getNextTaskActivity(nextactivity);
			}else{
				return null;
			}
		}
		
	}
}
