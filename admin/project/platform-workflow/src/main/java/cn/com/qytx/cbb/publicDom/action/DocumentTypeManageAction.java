package cn.com.qytx.cbb.publicDom.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import cn.com.qytx.cbb.customForm.domain.FormAttribute;
import cn.com.qytx.cbb.customForm.domain.FormProperties;
import cn.com.qytx.cbb.customForm.service.IFormAttribute;
import cn.com.qytx.cbb.customForm.service.IFormCategory;
import cn.com.qytx.cbb.customForm.service.IFormProperties;
import cn.com.qytx.cbb.customJpdl.domain.NodeFormAttribute;
import cn.com.qytx.cbb.customJpdl.service.NodeFormAttributeService;
import cn.com.qytx.cbb.dict.domain.Dict;
import cn.com.qytx.cbb.dict.service.IDict;
import cn.com.qytx.cbb.file.domain.Attachment;
import cn.com.qytx.cbb.file.service.IAttachment;
import cn.com.qytx.cbb.publicDom.domain.DocTemplate;
import cn.com.qytx.cbb.publicDom.domain.DocumentType;
import cn.com.qytx.cbb.publicDom.service.DocumentTypeService;
import cn.com.qytx.cbb.publicDom.service.IDocTemplateService;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.RoleInfo;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IGroup;
import cn.com.qytx.platform.org.service.IUser;

import com.opensymphony.xwork2.ActionContext;

/**
 * 功能: 文档类型Action
 * 版本: 1.0
 * 开发人员: 陈秋利
 * 创建日期: 2013-4-16
 * 修改日期: 2013-4-16
 */
public class DocumentTypeManageAction extends BaseActionSupport {
	/** 公文类型Serivce */
	@Resource(name="documentTypeService")
	DocumentTypeService  documentTypeService; 
	@Resource(name="formCategoryService")
	private IFormCategory formCategoryService;
	// 表单属性服务类
	@Resource(name = "formAttributeService")
	IFormAttribute formAttributeService;
	/**组信息*/
	@Resource(name = "groupService")
	IGroup groupService;
	/** 正文模板服务类   */
	@Resource(name="docTemplateService")
	private IDocTemplateService docTemplateService;
	@Resource
	private IDict infoTypeImpl;
	@Resource
	private IAttachment attachmentService;
	@Resource
	private NodeFormAttributeService nodeFormAttributeService;
	@Resource
	private IUser userService;
	@Resource(name="formPropertiesService")
	private IFormProperties formPropertiesService;
	
	private DocumentType docType;
	private Integer doctypeId;
	private String formName;
	private String docName;
	private Attachment attachment;
	private Integer nodeId;
	private NodeFormAttribute nodef;
	
	public NodeFormAttribute getNodef() {
		return nodef;
	}
	public void setNodef(NodeFormAttribute nodef) {
		this.nodef = nodef;
	}
	public Integer getNodeId() {
		return nodeId;
	}
	public void setNodeId(Integer nodeId) {
		this.nodeId = nodeId;
	}
	public Attachment getAttachment() {
		return attachment;
	}
	public void setAttachment(Attachment attachment) {
		this.attachment = attachment;
	}
	public String getDocName() {
		return docName;
	}
	public void setDocName(String docName) {
		this.docName = docName;
	}
	/**
	 * 功能：添加公文类型
	 * @return
	 */
	public String add(){
		
		return SUCCESS;
	}
	/**
	 * 功能：跳转到更新页面
	 * @return
	 */
	public String updateDoc(){
		docType = documentTypeService.findOne(doctypeId);
		int formId = docType.getFormId();
		FormAttribute fa =formAttributeService.findById(formId);
		if(fa!=null){
			formName = fa.getFormName();
		}
		
		Integer attachId = docType.getAttachId();
		if(attachId!=null){
			attachment = attachmentService.findOne(attachId);
		}
		return SUCCESS;
	}

	/**
	 * 功能：保存
	 * @return
	 */
	public String save(){
		docType.setCompanyId(getLoginUser().getCompanyId());
		documentTypeService.saveOrUpdate(docType);
		return SUCCESS;
	}
	 
	/**
	 * 功能：更新
	 * @return
	 */
	public String update(){
		documentTypeService.saveOrUpdate(docType);
		return SUCCESS;
	}
	 
	/**
	 * 功能：更新
	 * @return
	 */
	public String delete() throws Exception{
			if(doctypeId>0){
				documentTypeService.delete(doctypeId,true);
			}
			ajax(1);
	    	return null;
	 
	}
	
	 public String checkDocNameIsRepeat() throws Exception{
	    	Object obj =  documentTypeService.getDocumentByName(docName);
	    	if(doctypeId == null || doctypeId ==0){
	    		if(obj!=null){
	    			List list = (List)obj;
	    			if(list!=null&&list.size()>0){
	    				ajax("repeat");
	    			}
	    		}else{
	    			ajax("success");
	    		}
	    	}else{
	    		if(obj==null ){
	    			ajax("success");
	    		}
	    		else{
	    			DocumentType doc = null;
	    			if(obj instanceof List){
	    				List<DocumentType> list = (List<DocumentType>) obj;
	    				if(list.size()>1){
	    					ajax("repeat");
	    				}else{
	    					doc = list.get(0);
	    				}
	    			}else{
	    				doc = (DocumentType) obj;
	    			}
					if(doc!=null&&doc.getDoctypeId().intValue() == doctypeId.intValue()){
						ajax("success");
					}else{
						ajax("repeat");
					}
	    		}
	    	}
	    	return null;
	    }
	 
	 /**
	  * 获取套红模板的类型
	 * @return
	 */
	public String getRedTemplateType(){
		 List<Dict> dictlist = infoTypeImpl.findList("redTemplate", 1);
		 ajax(dictlist,true);
		 return null;
	 }
	
	
	/**
	 * 功能：获取流程分类
	 * @param
	 * @return
	 * @throws   
	 */
	public List<Dict> getCategories(){
		return infoTypeImpl.findList("dom_category", 1);
	}
	
	/**进入设置公文界面，首先判断是否已经初始化表：tb_cbb_node_form_attribute,如果未初始化则初始化
	 * @return
	 */
	public String setUpGongwen() throws Exception{
		setDocType(documentTypeService.findOne(doctypeId));
		return "nodeEdit";
	}
	
	/**设置单个节点的权限
	 * @return
	 * @throws Exception
	 */
	public String setUpNode() throws Exception{
		return "nodeSetUp";
	}
	
	public NodeFormAttribute getNode(){
		return nodeFormAttributeService.findById(nodeId);
	}
	
	public List<UserInfo> getUserInfos(){
		NodeFormAttribute nfa = getNode();
		String userids = nfa.getCandidate();
		return userService.findUsersByIds(userids);
	}
	
	public List<GroupInfo> getGroupInfos(){
		NodeFormAttribute nfa = getNode();
		String groupIds = nfa.getDepts();
		return groupService.getGroupListByIds(getLoginUser().getCompanyId(), groupIds);
	}
	
	public List<RoleInfo> getRoleInfos(){
		NodeFormAttribute  node = getNode();
		if(node.getRoles()!=null && !node.getRoles().equals("")){
			String candidate = node.getRoles();
			List<RoleInfo> roleInfos = nodeFormAttributeService.findByRoleIds(candidate);
			return roleInfos;
		}
		return null;
	}
	
	/**公文节点列表
	 * @return
	 */
	public List<NodeFormAttribute> getNodes(){
		return documentTypeService.initDocumentTypeToNodes(doctypeId);
	}

	public List<DocTemplate> getDocTemplates(){
		UserInfo user = (UserInfo) ActionContext.getContext().getSession().get("adminUser");
		return docTemplateService.findAll(user.getCompanyId());
	}
	
	/**更新NODE
	 * @return
	 */
	public String updateNode() throws Exception{
		nodeFormAttributeService.updateNodeFormAttribute(nodef.getId(), nodef.getCandidate(), nodef.getDepts(), nodef.getRoles(), nodef.getWriteableProperties(), nodef.getSecretProperties(),nodef.getEditDoc());
		return "nodeEdit";
	}
	
	/**获取所有的表单属性
	 * @return
	 */
	public List<FormProperties> getFormProperties(){
		Integer formId =  documentTypeService.findOne(doctypeId).getFormId();
		if(formId == null){
			return new ArrayList<FormProperties>();
		}else{
			return formPropertiesService.findByFormId(formId);
		}
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
	
	public DocumentType getDocType() {
		return docType;
	}

	public void setDocType(DocumentType docType) {
		this.docType = docType;
	}

	public int getDoctypeId() {
		return doctypeId;
	}

	public void setDoctypeId(int doctypeId) {
		this.doctypeId = doctypeId;
	}
 
	public String getFormName() {
		return formName;
	}
	public void setFormName(String formName) {
		this.formName = formName;
	}



}
