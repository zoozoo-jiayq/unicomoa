package cn.com.qytx.cbb.customJpdl.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.jbpm.api.ProcessEngine;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import cn.com.qytx.cbb.customForm.domain.FormAttribute;
import cn.com.qytx.cbb.customForm.domain.FormCategory;
import cn.com.qytx.cbb.customForm.service.IFormAttribute;
import cn.com.qytx.cbb.customForm.service.IFormCategory;
import cn.com.qytx.cbb.customJpdl.domain.ProcessAttribute;
import cn.com.qytx.cbb.customJpdl.service.JsonParserService;
import cn.com.qytx.cbb.customJpdl.service.ProcessAttributeService;
import cn.com.qytx.cbb.file.config.FilePathConfig;
import cn.com.qytx.cbb.publicDom.domain.DocTemplate;
import cn.com.qytx.cbb.publicDom.service.IDocTemplateService;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.org.domain.UserInfo;

import com.opensymphony.xwork2.ActionContext;


/**
 * 功能：自定义流程管理控制器
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 2013-3-21 下午5:02:27 
 * 修改日期：2013-3-21 下午5:02:27 
 * 修改列表：
 */

public class JbpmManagerAction extends BaseActionSupport {
    private final int BUFF_SIZE = 1024;
    
	//流程名称
	private String processName;
	//流程定义属性
	private ProcessAttribute processAttribute;
	//流程定义ID
	private Integer processAttributeId;
	
	private String option;
	private String tabName;
	
	private String xmlfile ;//上传路径;
	
	//类别ID
	private Integer categoryId;
	@Resource(name="formAttributeService")
	private IFormAttribute formAttributeService;
	@Resource(name="formCategoryService")
	private IFormCategory formCategoryService;
	@Resource(name="jsonParserService")
	private JsonParserService jsonParserService;
	@Resource(name="processAttributeService")
	private ProcessAttributeService processAttributeService;
	@Resource(name="processEngine")
	private ProcessEngine processEngine;
	@Resource
	private IDocTemplateService docTemplateService;
	@Resource
	private FilePathConfig filePathConfig;
	
	//add by jiayq
	private Integer type = 2;
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getProcessAttributeId() {
		return processAttributeId;
	}
	public void setProcessAttributeId(Integer processAttributeId) {
		this.processAttributeId = processAttributeId;
	}
	public ProcessAttributeService getProcessAttributeService() {
		return processAttributeService;
	}
	public void setProcessAttributeService(
			ProcessAttributeService processAttributeService) {
		this.processAttributeService = processAttributeService;
	}
	public void setProcessAttribute(ProcessAttribute processAttribute) {
		this.processAttribute = processAttribute;
	}
	public ProcessAttribute getProcessAttribute() {
		return processAttribute;
	}
	public String getOption() {
		return option;
	}
	public void setOption(String option) {
		this.option = option;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getTabName() {
		return tabName;
	}
	public void setTabName(String tabName) {
		this.tabName = tabName;
	}
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	
	
public String getXmlfile() {
        return xmlfile;
    }
    public void setXmlfile(String xmlfile) {
        this.xmlfile = xmlfile;
    }
/**
   * 功能：转向创建流程定义页面
   * @param：无
   * @return
   * @throws   
   */
	public String createDefine() throws Exception{
		return "new_define";
	}
	
  /**
   * 功能：保存流程定义属性
   * @param：无
   * @return
   * @throws   
   */
	public String saveProcess() throws Exception{
		processAttribute.setCompanyId(getLoginUser().getCompanyId());
		processAttributeService.save(processAttribute);
		setProcessAttributeId(processAttribute.getId());
		return "option_success";
	}

  /**
   * 功能：修改流程定义
   * @param
   * @return
   * @throws   
   */
	public String update(){
		setProcessAttribute(processAttributeService.getProcessById(processAttributeId));
		return "update";
	}
	
	/**
	 * 功能：进入向导页面
	 * @param
	 * @return
	 * @throws   
	 */
	@Override
	public String execute() throws Exception {
		if(this.type==null || this.type ==0){
			this.type = 2;
		}
		return SUCCESS;
	}
	
	public DocTemplate getDocTemplate(){
		ProcessAttribute pa = processAttributeService.getProcessById(processAttributeId);
		int docTemplate = pa.getRedTemplate();
		return docTemplateService.findOne(docTemplate);
	}
	
	/**
	 * 功能：获取所有流程分类，及其流程定义
	 * @param
	 * @return
	 * @throws   
	 */
	public List<ProcessCategory> getProcssCategorys() {
		List<ProcessCategory> list = new ArrayList<ProcessCategory>();
		UserInfo u = (UserInfo) ActionContext.getContext().getSession().get("adminUser");
		//获取所有分类
		List<FormCategory> categories = getCategories();
		//获取所有流程定义
		List<ProcessAttribute> processAttributes = processAttributeService.findAll(u.getCompanyId());
		for(int i=0;i<categories.size();i++){
			ProcessCategory pcCategory = new ProcessCategory(processAttributes,categories.get(i));
			list.add(pcCategory);
		}
		return list;
	}
	
	/**
	 * 功能：查询所有的套红模板
	 * @param
	 * @return
	 * @throws   
	 */
	public List<DocTemplate> getDocTemplates(){
		UserInfo user = (UserInfo) ActionContext.getContext().getSession().get("adminUser");
		return docTemplateService.findAll(user.getCompanyId());
	}
	
	/**
	 * 功能：进入新建向导页面
	 * @param
	 * @return
	 * @throws   
	 */
	public String prepCreate(){
		return "prep_create";
	}
	
	/**
	 * 功能：跳转到流程修改界面
	 * @param
	 * @return
	 * @throws   
	 */
	public String editProcess(){
		if(processAttributeId==null){
			return "wizard";
		}else{
			setProcessAttribute(processAttributeService.getProcessById(processAttributeId));
			return "edit_process";
		}
	}
	
	
	/**
	 * 功能：获取流程分类
	 * @param
	 * @return
	 * @throws   
	 */
	public List<FormCategory> getCategories(){
		UserInfo user = (UserInfo) ActionContext.getContext().getSession().get("adminUser");
		return formCategoryService.findByTypeCompanyId(user.getCompanyId(), this.type);
	}
	
	/**
	 * 功能：判断是否可删除流程定义，如果状态是停用,未发布，可删除
	 * @param
	 * @return:success 可删除；false不可删除
	 * @throws   
	 */
	public String isCanDelete() throws Exception{
		String state = "";
		ProcessAttribute pa = processAttributeService.getProcessById(processAttributeId);
		//未发布，可以删除
		if(pa.getProcessState().intValue() == ProcessAttribute.NOT_DEPLOY.intValue()){
			state = "success";
		//停用
		}else if(pa.getProcessState().intValue()==ProcessAttribute.STOP_STATE.intValue()){
			List list = processEngine.getExecutionService().createProcessInstanceQuery()
					.processDefinitionId(pa.getProcessDefineId()).list();
			//如果没有再用的流程实例 可以删除
			if(list == null || list.size() ==0){
				state = "success";
			}else{
				state = "false";
			}
		}
		//已发布，不能删除
		else if(pa.getProcessState().intValue() == ProcessAttribute.DEPLOY_STATE){
			state = "false";
		}
		ajax(state);
		return null;
	}
	
	/**
	 * 功能：删除流程定义
	 * @param
	 * @return
	 * @throws   
	 */
	public String deleteProcess() throws Exception{
		processAttributeService.delete(processAttributeId,true);
		ajax("success");
		return null;
	}
	
	/**
	 * 功能：检测
	 * @param
	 * @return
	 * @throws   
	 */
	public String check() throws Exception{
		//校验流程定义文件是否合法
		boolean result = jsonParserService.checkJpdlXmslByXSD(processAttributeId);
		//校验是否已设置经办权限
		List<String> list = processAttributeService.checkIsSetCandidate(processAttributeId);
		getRequest().setAttribute("fileCheck", result?1:2);
		getRequest().setAttribute("usersCheck", list);
		return "checkResult";
	}
	
	/**
	 * @throws Exception 
	 * 功能：发布
	 * @param
	 * @return
	 * @throws   
	 */
	public String deploy() throws Exception{
		processAttributeService.deploy(processAttributeId);
		ajax("success");
		return null;
	}
	
	
	/**
	 * 功能：停用
	 * @param
	 * @return
	 * @throws   
	 */
	public String stop() throws Exception{
		processAttributeService.stop(processAttributeId);
		ajax("success");
		return null;
	}
	
	/**
	 * 功能：启用
	 * @param
	 * @return
	 * @throws   
	 */
	public String start() throws Exception{
		ProcessAttribute pa = processAttributeService.getProcessById(processAttributeId);
		String processName = pa.getProcessName();
		String[] strs = processName.split("\\(");
		processName = strs[0];
		boolean b =processAttributeService.checkProcessNameIsRepeat(processName, null);
		if(b){
			ajax("repeat");
		}else{
			processAttributeService.start(processAttributeId);
			ajax("success");
		}
		return null;
	}
	
	/**
	 * 功能：根据类别获取流程定义列表
	 * @param
	 * @return
	 * @throws   
	 */
	public String findProcessListByCategoryId() throws Exception{
		UserInfo ui = (UserInfo) ActionContext.getContext().getSession().get("adminUser");
		StringBuffer result = new StringBuffer("[");
		List<ProcessAttribute> list = processAttributeService.findByCategoryId(categoryId,ui.getCompanyId());
		if(list!=null && list.size()>0){
			for(int i=0;i<list.size();i++){
				if(list.get(i).getProcessName()==null || list.get(i).getProcessName().equals("")){
					continue;
				}
				result.append(list.get(i).toString());
				if(i!=list.size()-1){
					result.append(",");
				}
			}
		}
		result.append("]");
		ajax(result.toString());
		return null;
	}
	
	/**
	 * 功能：复制流程
	 * @param
	 * @return
	 * @throws   
	 */
	public String copyProcess(){
		setProcessAttribute(processAttributeService.copyProcess(processAttributeId));
		return  "copy";
	}
	
	
	/**
	 * 功能：重构流程的名字
	 * @param
	 * @return
	 * @throws   
	 */
	public String rebuildJpdlByProcessName(){
		processAttributeService.rebuildProcessByProcessName(processAttribute,type,processAttributeId);
		setProcessAttributeId(processAttribute.getId());
		return "option_success";
	}
	
	/**
	 * 功能：验证流程定义名称是否重复
	 * @param
	 * @return success:重复；fail:不重复
	 * @throws   
	 */
	public String  checkProcessNameIsRepeat() throws Exception{
		processName =  URLDecoder.decode(processName, "UTF-8");
		boolean result = processAttributeService.checkProcessNameIsRepeat(processName,processAttributeId);
		String str = "fail";
		if(result){
			str = "success";
		}
		ajax(str);
		return null;
	}
	
	/**
	 * 功能：获取form表单分类的json数据
	 * @param
	 * @return
	 * @throws   
	 */
	public String getFormCategoryTree() throws Exception{
		UserInfo ui = (UserInfo) ActionContext.getContext().getSession().get("adminUser");
		//获取所有的表单分类
		int formType = FormCategory.CATEGORY_TYPE_FORM;
		if(type != null && type ==FormCategory.DOC_FLOW){
			formType = FormCategory.DOC_FORM;
		}
		List<FormCategory> categories = formCategoryService.findByTypeCompanyId(ui.getCompanyId(), formType);
		//获取所有的表单
		List<FormAttribute> attributes = formAttributeService.findAll(ui.getCompanyId());
		StringBuffer sb = new StringBuffer("[");
		for(Iterator<FormCategory> iterator = categories.iterator();iterator.hasNext();){
			FormCategory  temp =  iterator.next();
			sb.append("{");
			sb.append("\"id\":\"c_").append(temp.getCategoryId()).append("\",");
			sb.append("\"pId\":\"").append(0).append("\",");
			sb.append("\"name\":\"").append(temp.getCategoryName()).append("\",");
			sb.append("\"isParent\":\"").append("true").append("\"");
			sb.append("},");
		}
		for(int i=0;i<attributes.size();i++){
			FormAttribute fa = attributes.get(i);
			if(!isExist(fa.getCategoryId().intValue(), categories)){
				continue;
			}
			sb.append("{");
			sb.append("\"id\":\"").append(fa.getFormId()).append("\",");
			sb.append("\"pId\":\"c_").append(fa.getCategoryId()).append("\",");
			sb.append("\"isParent\":\"").append("false").append("\",");
			sb.append("\"name\":\"").append(fa.getFormName()).append("\"");
			sb.append("},");
		}
		String result = sb.substring(0, sb.length()-1);
		result+="]";
		//System.out.println(result);
		ajax(result);
		return null;
	}
	
	/**
	 * add by jiayq
	 * 功能：构造套红模板树结构
	 * @param
	 * @return
	 * @throws   
	 */
	public String getRedTemplateTree() throws Exception{
		UserInfo ui = (UserInfo) ActionContext.getContext().getSession().get("adminUser");
		List<FormCategory> templateCategorys = formCategoryService.findByTypeCompanyId(ui.getCompanyId(), FormCategory.DOC_RED);
		List<DocTemplate> docTemplates = docTemplateService.findAll(ui.getCompanyId(), ui.getUserId());
		StringBuffer sb = new StringBuffer("[");
		for(Iterator<FormCategory> iterator = templateCategorys.iterator();iterator.hasNext();){
			FormCategory  temp =  iterator.next();
			sb.append("{");
			sb.append("\"id\":\"c_").append(temp.getCategoryId()).append("\",");
			sb.append("\"pId\":\"").append(0).append("\",");
			sb.append("\"name\":\"").append(temp.getCategoryName()).append("\",");
			sb.append("\"isParent\":\"").append("true").append("\"");
			//isParent
			sb.append("},");
		}
		for(int i=0;i<docTemplates.size();i++){
			DocTemplate doc = docTemplates.get(i);
			sb.append("{");
			sb.append("\"id\":\"").append(doc.getDocTemplateId()).append("\",");
			sb.append("\"pId\":\"c_").append(doc.getCategoryId()).append("\",");
			sb.append("\"name\":\"").append(doc.getDocTemplateName()).append("\"");
			sb.append("},");
		}
		String result = sb.substring(0, sb.length()-1);
		result+="]";
		//System.out.println(result);
		ajax(result);
		return null;
	}
	
	private boolean isExist(int id,List<FormCategory> formCategorys){
		for(int i=0; i<formCategorys.size(); i++){
			if(formCategorys.get(i).getCategoryId().intValue() == id){
				return true;
			}
		}
		return false;
	}
	
	public FormAttribute getFormAttribute(){
		return formAttributeService.findById(processAttribute.getFormId());
	}
	
	/**
	 * @throws Exception 
	 * 功能：导出流程定义，不包含表单属性和分类属性
	 * @param
	 * @return
	 * @throws   
	 */
	public String exportProcessDefine() throws Exception{
		File f = processAttributeService.exportProcessDefine(processAttributeId);
		String name = URLEncoder.encode(f.getName(), "UTF-8");
		getResponse().setCharacterEncoding("UTF-8");
		getResponse().addHeader("Content-Disposition","attachment; filename=\"" + name + "\"");
		OutputStream os = getResponse().getOutputStream();
    	FileInputStream fis = new FileInputStream(f);
    	byte[] bs = new byte[BUFF_SIZE];
    	while(fis.read(bs)!=-1){
    		os.write(bs);
    		bs = new byte[BUFF_SIZE];
    	}
    	os.flush();
    	fis.close();
		return null;
	}
	
	/**
	 * 功能：导入
	 * @param
	 * @return
	 * @throws   
	 */
	public String importProcessDefine() throws Exception{
		String catalinaHome = filePathConfig.getFileUploadPath();
		
		File file = new File(catalinaHome+"/upload/"+xmlfile);//读取模块文件
		BufferedReader  br = new BufferedReader(new FileReader(file));
		StringBuffer sb = new StringBuffer();
		String temp = br.readLine();
		while(temp!=null){
			sb.append(temp);
			temp = br.readLine();
		}
		Document doc = Jsoup.parse(sb.toString());
		String name = doc.select("name").get(0).text();
		SimpleDateFormat sdf = new SimpleDateFormat("mmss");
		name = "导入("+name+")"+sdf.format(new Date(Calendar.getInstance().getTimeInMillis()));
		String directions = doc.select("directions").get(0).text();
		String state = doc.select("state").get(0).text();
		String json = doc.select("jsondefine").get(0).text();
		String expr = doc.select("expr").get(0).text();
		String beginNum = doc.select("beginNum").get(0).text();
		String numlength = doc.select("numlength").get(0).text();
		String namecanedit = doc.select("namecanedit").get(0).text();
		String selectmode = doc.select("selectmode").get(0).text();
		String companyId = doc.select("companyId").get(0).text();
		ProcessAttribute pa = new ProcessAttribute();
		pa.setProcessName(name);
		pa.setDirections(directions);
		pa.setProcessState(Integer.parseInt(state));
		pa.setProcsssDefinByJSON(json);
		pa.setProcessNameExpr(expr);
		pa.setProcessNameBeginNum(Integer.parseInt(beginNum));
		pa.setProcessNamLength(Integer.parseInt(numlength));
		pa.setProcessNameCanupdate(Integer.parseInt(namecanedit));
		pa.setSelectUserMode(Integer.parseInt(selectmode));
		pa.setCategoryId(categoryId);
		pa.setCompanyId(Integer.parseInt(companyId));
		processAttributeService.importProcess(pa,type);
		getResponse().getWriter().flush();
		getResponse().getWriter().write("{status:'success',processAttributeId:'"+pa.getId()+"'}");
		getResponse().getWriter().flush();
		return null;
	}
	
}
