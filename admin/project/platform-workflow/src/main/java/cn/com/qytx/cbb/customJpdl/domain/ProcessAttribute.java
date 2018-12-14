package cn.com.qytx.cbb.customJpdl.domain;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.jbpm.api.ExecutionService;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.junit.Test;

import cn.com.qytx.cbb.customJpdl.service.JpdlInterface;
import cn.com.qytx.cbb.customJpdl.service.ProcessAttributeService;
import cn.com.qytx.platform.base.domain.BaseDomain;
import cn.com.qytx.platform.utils.spring.SpringUtil;

import com.google.gson.annotations.Expose;

/**
 * 功能：流程定义模型
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 2013-3-21 下午5:23:41 
 * 修改日期：2013-3-21 下午5:23:41 
 * 修改列表：
 */
@Entity
@Table(name="tb_cbb_process_attribute")
public class ProcessAttribute extends BaseDomain implements Serializable {
	
	//未发布
	public static final Integer NOT_DEPLOY = 1;
	//已发布
	public static final Integer DEPLOY_STATE = 2;
	//停用
	public static final Integer STOP_STATE = 3;

	/**
	 * 
	 */
	public ProcessAttribute() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param processName
	 */
	public ProcessAttribute(String processName,Integer id,Integer categoryId,String processDefineId) {
		super();
		this.processName = processName;
		this.id = id;
		this.categoryId = categoryId;
		this.processDefineId = processDefineId;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="process_attribute_id")
	@Expose
	private Integer id;
	//类别
	@Column(name="category_id")
	private Integer categoryId;
	//表单Id
	@Column(name="form_id")
	private Integer formId;
	//部门ID
	@Column(name="dept")
	private Integer dept;
	//工作流名称
	@Expose
	@Column(name="process_name",length=50)
	private String processName;
	//工作流定义ID
	@Column(name="process_define_id")
	private String processDefineId;
	//工作流顺序  
	/*@Column(name="process_order")
	private Integer processOrder=0;*/
	//是否允许附件;1允许；0拒绝
	@Column(name="is_attach")
	private Integer isAttach;
	//工作流描述
	@Column(name="directions")
	private String directions;
	//工作流定义的状态
//	1，已定义 ，未发布
//	2，已发布，并启用
//	3，停用
	@Column(name="process_state")
	private Integer processState = 1;
	
	public String getProcessStateCN(){
		if(this.processState == null){
			this.processState = 1;
		}
		if(this.processState == 1){
			return "未发布";
		}else if(this.processState == 2){
			return "已发布";
		}else if(this.processState == 3){
			return "停用";
		}
		return "未知状态";
	}
	
	//工作流定义的JSON格式
	@Column(name="process_define_byjson",length=5000)
	private String procsssDefinByJSON;
	//工作流定义的XML格式
	@Column(name="process_define_byxml",length=5000)
	private String processDefineByXML;
	@Column(name="create_time")
	private Date createDate;
	@Column(name="update_time")
	private Date updateDate;
	@OneToMany(mappedBy="processAttribute",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@OrderBy("nodeOrder asc")
	private Set<NodeFormAttribute> nodeSet;
	
	//文号表达式
	@Column(name="process_name_expr")
	private String processNameExpr ;
	
	//文号开始序号
	@Column(name="process_name_beginnum")
	private Integer processNameBeginNum = 1;
	
	//文号序号长度
	@Column(name="process_name_num_length")
	private Integer processNamLength = 4;
	
	//文号是否能修改:0否；1能
	@Column(name="process_name_can_update")
	private Integer processNameCanupdate;
	
	//选择审批人的方式，1代表直接选人；2代表通过部门选人,默认是1
	@Column(name="selectusermode")
	private Integer selectUserMode = 1;
	
	//add by jiayq
	//套红模板
	@Column(name="redtemplate")
	private Integer redTemplate;
	//1 套打；2非套打 
	@Column(name="taoda")
	private Integer taoDa = 2;
	//打印维护代码
	@Column(name="printtemplatecode")
	private String printTemplateCode;
	//实际打印代码
	@Column(name="printcode")
	private String printCode;
	//是否公文流程 0否1是
	@Expose
	@Column(name="process_order")
	private Integer isDom=0;
	
	
	public Integer getIsDom() {
		if (null==isDom) {
			isDom=0;
		}
		return isDom;
	}

	public void setIsDom(Integer isDom) {
		this.isDom = isDom;
	}

	public Integer getRedTemplate() {
		return redTemplate;
	}

	public void setRedTemplate(Integer redTemplate) {
		this.redTemplate = redTemplate;
	}

	public Integer getTaoDa() {
		return taoDa;
	}

	public void setTaoDa(Integer taoDa) {
		this.taoDa = taoDa;
	}

	public String getPrintTemplateCode() {
		return printTemplateCode;
	}

	public void setPrintTemplateCode(String printTemplateCode) {
		this.printTemplateCode = printTemplateCode;
	}

	public String getPrintCode() {
		return printCode;
	}

	public void setPrintCode(String printCode) {
		this.printCode = printCode;
	}

	/**
	 * 功能：获取当前在用实例数量
	 * @param
	 * @return
	 * @throws   
	 */
	public int getInstanceNum(){
		int result = 0;
		if(this.processDefineId!=null && !this.processDefineId.equals("")){
			ProcessEngine engine = (ProcessEngine) SpringUtil.getBean("processEngine");
			result = (int) engine.getExecutionService().createProcessInstanceQuery().processDefinitionId(processDefineId).count();
		}
		return result;
	}
	
	/**
	 * 功能：获取流程开始节点
	 * @param
	 * @return
	 * @throws   
	 */
	public NodeFormAttribute getStartNode(){
		if(this.nodeSet!=null && this.nodeSet.size()>0){
			for(Iterator<NodeFormAttribute> iterator = this.nodeSet.iterator();iterator.hasNext();){
				NodeFormAttribute nfa = iterator.next();
				if(nfa.getNodeType().equals(JpdlInterface.NODE_TYPE_START)){
					return nfa;
				}
			}
		}
		return null;
	}
	

	
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
		
	}
	public Integer getFormId() {
		return formId;
	}
	public void setFormId(Integer formId) {
		this.formId = formId;
	}
	public Integer getDept() {
		return dept;
	}
	public void setDept(Integer dept) {
		this.dept = dept;
	}
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	public String getProcessDefineId() {
		return processDefineId;
	}
	public void setProcessDefineId(String processDefineId) {
		this.processDefineId = processDefineId;
	}
	/*public Integer getProcessOrder() {
		if(null==processOrder){
			processOrder=0;
		}
		return processOrder;
	}
	public void setProcessOrder(Integer processOrder) {
		this.processOrder = processOrder;
	}*/
	public String getDirections() {
		return directions;
	}
	public void setDirections(String directions) {
		this.directions = directions;
	}
	public Integer getProcessState() {
		return processState;
	}
	public void setProcessState(Integer processState) {
		this.processState = processState;
	}
	public String getProcsssDefinByJSON() {
		return procsssDefinByJSON;
	}
	public void setProcsssDefinByJSON(String procsssDefinByJSON) {
		this.procsssDefinByJSON = procsssDefinByJSON;
	}
	public String getProcessDefineByXML() {
		return processDefineByXML;
	}
	public void setProcessDefineByXML(String processDefineByXML) {
		this.processDefineByXML = processDefineByXML;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public Set<NodeFormAttribute> getNodeSet() {
		return nodeSet;
	}
	public void setNodeSet(Set<NodeFormAttribute> nodeSet) {
		this.nodeSet = nodeSet;
	}
	public Integer getIsAttach() {
		return isAttach;
	}
	public void setIsAttach(Integer isAttach) {
		this.isAttach = isAttach;
	}
	
	//流程是否使用:false未使用。true使用
	public boolean isUse(){
		ProcessEngine engine = (ProcessEngine) SpringUtil.getBean("processEngine");
		ExecutionService executionService = engine.getExecutionService();
		if(processDefineId==null){
			return false;
		}else{
			List<ProcessInstance> instanceslist = executionService.createProcessInstanceQuery().processDefinitionId(processDefineId).list();
			if(instanceslist!=null && instanceslist.size()>0){
				return true;
			}
		}
		return false;
	}
	
	
	public String getProcessNameExpr() {
		return processNameExpr;
	}

	public void setProcessNameExpr(String processNameExpr) {
		this.processNameExpr = processNameExpr;
	}

	public Integer getProcessNameBeginNum() {
		return processNameBeginNum;
	}

	public void setProcessNameBeginNum(Integer processNameBeginNum) {
		this.processNameBeginNum = processNameBeginNum;
	}

	public Integer getProcessNamLength() {
		return processNamLength;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setProcessNamLength(Integer processNamLength) {
		this.processNamLength = processNamLength;
	}

	public Integer getProcessNameCanupdate() {
		return processNameCanupdate;
	}

	public void setProcessNameCanupdate(Integer processNameCanupdate) {
		this.processNameCanupdate = processNameCanupdate;
	}
	
	public Integer getSelectUserMode() {
		return selectUserMode;
	}

	public void setSelectUserMode(Integer selectUserMode) {
		this.selectUserMode = selectUserMode;
	}

	/**
	 * 功能：构造文号,
	 * @param categoryName:流程分类名称
	 * @return
	 * @throws   
	 */
	public String generateProcessInstanceName(String userName,String groupName,String categoryName){
		String result = null;
		if(this.processNameExpr!=null){
			Calendar calendar = Calendar.getInstance();
			result = this.processNameExpr;
			result = result.replaceAll("\\{F\\}", this.getProcessName());
			result = result.replaceAll("\\{Y\\}", calendar.get(Calendar.YEAR)+"");
			String month = calendar.get(Calendar.MONTH)+1+"";
			if(month.length()==1){
				month="0"+month;
			}
			result = result.replaceAll("\\{M\\}", month);
			String d = (calendar.get(Calendar.DAY_OF_MONTH))+"";
			if(d.length() == 1){
				d="0"+d;
			}
			result = result.replaceAll("\\{D\\}", d);
			String h = calendar.get(Calendar.HOUR_OF_DAY)+"";
			if(h.length() == 1){
				h="0"+h;
			}
			result = result.replaceAll("\\{H\\}", h);
			String i = (calendar.get(Calendar.MINUTE))+"";
			if(i.length() == 1){
				i="0"+i;
			}
			result = result.replaceAll("\\{I\\}", i);
			String s = (calendar.get(Calendar.SECOND))+"";
			if(s.length() == 1){
				s="0"+s;
			}
			result = result.replaceAll("\\{S\\}",s );
			result = result.replaceAll("\\{FS\\}", categoryName);
			result = result.replaceAll("\\{U\\}", userName);
			result = result.replaceAll("\\{DE\\}", groupName);
			if(processNamLength == 0){
				result = result.replaceAll("\\{N\\}", processNameBeginNum+"");
			}else{
				String allStr = "100000000000000000"+processNameBeginNum;
				allStr = allStr.substring(allStr.length()-processNamLength);
				result = result.replaceAll("\\{N\\}", allStr);
			}
			if(this.processNameExpr.contains("{N}")){
				ProcessAttributeService service = (ProcessAttributeService) SpringUtil.getBean("processAttributeService");
				service.processNameBeginNumAddOne(this.id);
			}
			
		}
		return result;
	}
	
	@Test
	public void testgenerate(){
		this.processName = "测试";
		this.processNameExpr = "{F}流程({Y}-{M}-{D} {H}:{I}:{S}){N}{DE}";
	}

	@Override
	public String toString() {
		return "{\"id\":\""+this.getId()+"\",\"name\":\""+this.getProcessName()+"\"}";
	}
	
}
