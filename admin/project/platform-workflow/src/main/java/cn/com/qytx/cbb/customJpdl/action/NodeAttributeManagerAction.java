package cn.com.qytx.cbb.customJpdl.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import cn.com.qytx.cbb.customForm.domain.FormProperties;
import cn.com.qytx.cbb.customForm.service.IFormProperties;
import cn.com.qytx.cbb.customJpdl.domain.NodeFormAttribute;
import cn.com.qytx.cbb.customJpdl.service.NodeFormAttributeService;
import cn.com.qytx.cbb.customJpdl.service.ProcessAttributeService;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.RoleInfo;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IUser;


/**
 * 功能：节点属性管理控制器
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 2013-3-21 下午5:15:35 
 * 修改日期：2013-3-21 下午5:15:35 
 * 修改列表：
 */
public class NodeAttributeManagerAction extends BaseActionSupport {

	@Resource(name="nodeFormAttributeService")
	private NodeFormAttributeService nodeFormAttributeService;
	
	@Resource(name="formPropertiesService")
	private IFormProperties formPropertiesService;
	
	@Resource(name="userService")
	private IUser userService;
	
	@Resource
	private ProcessAttributeService processAttributeService;
	
	//节点ID
	private Integer nodeId;
	
	private Integer processAttributeId;
	
	private NodeFormAttribute node;
	
	private Integer editDoc;
	
	private Integer type;

	

	/**
	 * 功能：进入编辑候选人页面
	 * @param
	 * @return
	 * @throws   
	 */
	public String candidate() throws Exception{
		setNode(nodeFormAttributeService.findById(nodeId));
		getRequest().setAttribute("userInfos", getUserInfos());
		getRequest().setAttribute("groupInfos", getGroupInfos());
		getRequest().setAttribute("roleInfos", getRoleInfos());
		return "candidate";
	}
	
	/**
	 * 功能:进入编辑可写字段页面
	 * @param
	 * @return
	 * @throws   
	 */
	public String writeAble() throws Exception{
		setNode(nodeFormAttributeService.findById(nodeId));
		return "writeable";
	}
	
	/**
	 * 功能：根据流程定义ID查询表单属性
	 * @param
	 * @return
	 * @throws   
	 */
	public List<FormProperties> getFormProperties(){
		int formId = node.getProcessAttribute().getFormId();
		List<FormProperties> list = formPropertiesService.findByFormId(formId);
		List<FormProperties> resultlist = new ArrayList<FormProperties>();
		for(int i=0; i<list.size(); i++){
			if(list.get(i).getParentName() == null || list.get(i).getParentName().length() == 0){
				resultlist.add(list.get(i));
			}
		}
		return resultlist;
	}
	
	/**
	 * 功能：进入编辑保密字段页面
	 * @param
	 * @return
	 * @throws   
	 */
	public String secretAble() throws Exception{
		setNode(nodeFormAttributeService.findById(nodeId));
		return "secretable";
	}
	
	/**
	 * 功能：修改流程节点
	 * @param
	 * @return
	 * @throws   
	 */
	public String update(){
		node.setCompanyId(getLoginUser().getCompanyId());
		nodeFormAttributeService.updateNodeFormAttribute(node);
		//ActionContext.getContext().getSession().put("processAttributeId", node.getProcessAttribute().getId());
		return "node_edit";
	}
	
	/**
	 * 功能：获取用户信息
	 * @param
	 * @return
	 * @throws   
	 */
	public List<UserInfo> getUserInfos(){
		NodeFormAttribute  node = nodeFormAttributeService.findById(nodeId);
		if(node.getCandidate()!=null && !node.getCandidate().equals("")){
			String candidate = node.getCandidate();
			if(candidate.endsWith(",")){
				candidate = candidate.substring(0, candidate.length()-1);
				List<UserInfo> userInfo = nodeFormAttributeService.findByUserIds(candidate);
				return userInfo;
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
		try{
		NodeFormAttribute  node = nodeFormAttributeService.findById(nodeId);
		if(node.getDepts()!=null && !node.getDepts().equals("")){
			String candidate = node.getDepts();
			if(candidate.endsWith(",")){
				candidate = candidate.substring(0, candidate.length()-1);
				List<GroupInfo> groupInfo = nodeFormAttributeService.findByGroupIds(candidate);
				return groupInfo;
			}
		}}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 功能：获取角色信息
	 * @param
	 * @return
	 * @throws   
	 */
	public List<RoleInfo> getRoleInfos(){
		NodeFormAttribute  node = nodeFormAttributeService.findById(nodeId);
		if(node.getRoles()!=null && !node.getRoles().equals("")){
			String candidate = node.getRoles();
			if(candidate.endsWith(",")){
				candidate = candidate.substring(0, candidate.length()-1);
				List<RoleInfo> roleInfos = nodeFormAttributeService.findByRoleIds(candidate);
				return roleInfos;
			}
		}
		return null;
	}
	
	/**
	 * 功能：获取节点的可写字段
	 * @param
	 * @return
	 * @throws   
	 */
	public List<FormProperties> getWriteAbleProperties(){
		NodeFormAttribute node = nodeFormAttributeService.findById(nodeId);
		if(node.getWriteableProperties()!=null && !node.getWriteableProperties().equals("")){
			String writes = node.getWriteableProperties();
			if(writes.endsWith(",")){
				writes = writes.substring(0, writes.length()-1);
				List<FormProperties> properties = nodeFormAttributeService.findFormPropertiesByIds(writes);
				return properties;
			}
		}
		return null;
	}
	
	/**
	 * 功能：获取节点的保密字段
	 * @param
	 * @return
	 * @throws   
	 */
	public List<FormProperties> getSecretProperties(){
		NodeFormAttribute node = nodeFormAttributeService.findById(nodeId);
		if(node.getSecretProperties()!=null && !node.getSecretProperties().equals("")){
			String writes = node.getSecretProperties();
			if(writes.endsWith(",")){
				writes = writes.substring(0, writes.length()-1);
				List<FormProperties> properties = nodeFormAttributeService.findFormPropertiesByIds(writes);
				return properties;
			}
		}
		return null;
	}
	
	/**
	 * 功能：公文流程节点设置属性
	 * @param
	 * @return
	 * @throws   
	 */
	public String toNodeSetup(){
		this.node = nodeFormAttributeService.findById(nodeId);
		return "nodeSetup";
	}
	
	public String nodeSetup(){
		this.node = nodeFormAttributeService.findById(nodeId);
		node.setEditDoc(editDoc);
		nodeFormAttributeService.updateNodeFormAttribute(node);
		return "node_edit";
	}
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getEditDoc() {
		return editDoc;
	}

	public void setEditDoc(Integer editDoc) {
		this.editDoc = editDoc;
	}

	public NodeFormAttribute getNode() {
		return node;
	}

	public void setNode(NodeFormAttribute node) {
		this.node = node;
	}

	public Integer getNodeId() {
		return nodeId;
	}

	public void setNodeId(Integer nodeId) {
		this.nodeId = nodeId;
	}

	public Integer getProcessAttributeId() {
		return processAttributeId;
	}

	public void setProcessAttributeId(Integer processAttributeId) {
		this.processAttributeId = processAttributeId;
	}
}
