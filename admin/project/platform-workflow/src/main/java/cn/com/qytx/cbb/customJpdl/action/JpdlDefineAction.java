package cn.com.qytx.cbb.customJpdl.action;

import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import cn.com.qytx.cbb.customForm.domain.FormProperties;
import cn.com.qytx.cbb.customForm.service.IFormProperties;
import cn.com.qytx.cbb.customJpdl.domain.NodeFormAttribute;
import cn.com.qytx.cbb.customJpdl.domain.ProcessAttribute;
import cn.com.qytx.cbb.customJpdl.service.ProcessAttributeService;
import cn.com.qytx.platform.base.action.BaseActionSupport;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


/**
 * 功能：流程设计器控制器
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 2013-3-21 下午5:08:28 
 * 修改日期：2013-3-21 下午5:08:28 
 * 修改列表：
 */
public class JpdlDefineAction extends BaseActionSupport {

	@Resource(name="processAttributeService")
	private ProcessAttributeService processAttributeService;
	
	@Resource(name="formPropertiesService")
	private IFormProperties formPropertiesService;
	
	//节点的路由信息
	private String router;
	
	//表单ID
	private Integer formId;
	
	//json格式的JPDL定义
	private String jsonTypeJpdl;
	
	//流程定义ID
	private Integer processAttributeId;
	
	//当前活动节点
	private String actives;
	
	private Integer type;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getActives() {
		return actives;
	}

	public void setActives(String actives) {
		this.actives = actives;
	}

	public Integer getProcessAttributeId() {
		return processAttributeId;
	}

	public void setProcessAttributeId(Integer processAttributeId) {
		this.processAttributeId = processAttributeId;
	}

	public void setJsonTypeJpdl(String jsonTypeJpdl) {
		this.jsonTypeJpdl = jsonTypeJpdl;
	}

	public String getJsonTypeJpdl() {
		return jsonTypeJpdl;
	}

	public Integer getFormId() {
		return formId;
	}

	public void setFormId(Integer formId) {
		this.formId = formId;
	}

	public String getRouter() {
		return router;
	}

	public void setRouter(String router) {
		this.router = router;
	}
	
	public ProcessAttribute getProcess() {
		return processAttributeService.getProcessById(processAttributeId);
	}
	

	/**
	 * 功能：进入流程定义页面
	 * @param：无
	 * @return
	 * @throws   
	 */
	public String define() throws Exception{
		return "myflow";
	}
	
	
	/**
	 * 功能：进入表达式编辑界面
	 * @param
	 * @return
	 * @throws   
	 */
	public String exprEditor() throws Exception{
		return "exprEditor";
	}
	
	
	/**
	 * 功能：解析判断节点的路由信息
	 * @param
	 * @return
	 * @throws   
	 */
	public List<Router> getRouters() throws Exception{
		Gson gson = new Gson();
		Type type = new TypeToken<ArrayList<Router>>(){}.getType();
		return gson.fromJson(router, type);
	}
	
	
	/**
	 * 功能：根据formId获取表单详细属性
	 * @param
	 * @return
	 * @throws   
	 */
	public List<FormProperties> getFormProperties(){
		return formPropertiesService.findByFormId(formId);
	}
	
	/**
	 * 功能：解析JSON格式的JPDL定义，解析成XML格式的JPDL定义
	 * @param
	 * @return
	 * @throws   
	 */
	public String parseJsonToJpdl() throws Exception{
		jsonTypeJpdl = URLDecoder.decode(jsonTypeJpdl, "UTF-8"); 
		processAttributeService.updateProcessAttributeByJsonData(processAttributeId, jsonTypeJpdl,type);
		ajax("{states:\"success\"}");
		return null;
	}
	
	
	/**
	 * 功能：设置节点属性
	 * @param
	 * @return
	 * @throws   
	 */
	public String setNodeAttribute() throws Exception{
		return "nodelist";
	}
	
	public Set<NodeFormAttribute> getNodeList(){
		ProcessAttribute paAttribute = processAttributeService.getProcessById(processAttributeId);
		return paAttribute.getNodeSet();
	}
	
	/**
	 * 功能：预览流程定义
	 * @param
	 * @return
	 * @throws   
	 */
	public String view() throws Exception{
		return "view";
	}
	
	
	/**
	 * 功能：获取JSON格式的JPDL定义数据
	 * @param
	 * @return
	 * @throws   
	 */
	public String getJsonData(){
		String json = processAttributeService.getProcessById(processAttributeId).getProcsssDefinByJSON();
		return json;
	}
	
	
	/**
	 * 
	 * 功能：判断流程是否已经设好
	 * @param
	 * @return
	 * @throws   
	 */
	public String isDefine() throws Exception{
		ProcessAttribute attribute = processAttributeService.getProcessById(processAttributeId);
		if(attribute!=null && attribute.getProcsssDefinByJSON()!=null){
			ajax("success");
		}else{
			ajax("error");
		}
		
		return null;
	}
	
	/**
	 * 功能：进入节点属性编辑
	 * @param
	 * @return
	 * @throws   
	 */
	public String nodeEdit(){
//		if(this.processAttributeId == null){
//			this.processAttributeId = (Integer) ActionContext.getContext().getSession().get("processAttributeId");
//		}
		return "node_edit";
	}
	
	public ProcessAttribute getProcessAttribute(){
		return processAttributeService.getProcessById(processAttributeId);
	}
	
}
