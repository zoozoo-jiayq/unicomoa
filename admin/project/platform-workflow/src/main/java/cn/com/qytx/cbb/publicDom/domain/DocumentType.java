package cn.com.qytx.cbb.publicDom.domain;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.com.qytx.cbb.publicDom.dao.DocumentTypeDao;
import cn.com.qytx.cbb.publicDom.service.DocumentTypeService;
import cn.com.qytx.platform.base.domain.BaseDomain;
import cn.com.qytx.platform.utils.spring.SpringUtil;


/**
 * 功能: 公文类型实体类
 * 版本: 1.0
 * 开发人员: 陈秋利
 * 创建日期: 2013-4-16
 * 修改日期: 2014-12-1
 * 修改列表:alter table tb_document_type add gongwen_type int;
 */
@Entity
@Table(name="tb_document_type")
public class DocumentType extends BaseDomain   implements java.io.Serializable {

	//收文
	public final static Integer GATHER = 1;
	//发文
	public final static Integer DISPATCH = 2;
	
	// Fields

	/**
	 * 描述含义
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="doctype_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer doctypeId;
	
	@Column(name="doctype_name",length=100)
	private String doctypeName;
	
	@Column(name="category_id")
	private Integer categoryId;
	
	@Column(name="doc_desc",length=1000)
	private String docDesc;
	
	@Column(name="doc_bm",length=2000)
	private String docBm;
	
	@Column(name="form_id")
	private Integer formId;
	
	@Column(name="expr",length=100)
	private String expr;
	
	@Column(name="beginnum")
	private Integer beginNum;
	
	@Column(name="num_length")
	private Integer numLength;
	
	//add by 贾永强,文号是否允许修改，默认能修改：1能修改；0不能修改
	@Column(name="canupdate",length=10)
	private String canUpdate = "1";
	
	//1 套打；2非套打 - add by 贾永强
	@Column(name="taoda")
	private Integer taoDa = 1;
	//打印维护代码
	@Column(name="printtemplatecode",length=5000)
	private String printTemplateCode;
	//实际打印代码
	@Column(name="printcode",length=5000)
	private String printCode;
	
	@Column(name="doctemplateid")
    private Integer docTemplateId;
	
	//套打背景图片ID
	@Column(name="attachment_id")
	private Integer attachId;
	
	//收/发文类型  1收文2发文
	@Column(name="gongwen_type")
	private Integer gongwenType;

    public Integer getAttachId() {
		return attachId;
	}

	public void setAttachId(Integer attachId) {
		this.attachId = attachId;
	}

	public String getCanUpdate(){
    	return canUpdate;
    }

    public void setCanUpdate(String canUpdate){
    	this.canUpdate = canUpdate;
    }

	public String getPrintCode() {
		return printCode;
	}

	public void setPrintCode(String printCode) {
		this.printCode = printCode;
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

	public Integer getDoctypeId() {
		return this.doctypeId;
	}

	public void setDoctypeId(Integer doctypeId) {
		this.doctypeId = doctypeId;
	}

	public String getDoctypeName() {
		return this.doctypeName;
	}

	public void setDoctypeName(String doctypeName) {
		this.doctypeName = doctypeName;
	}

	public Integer getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getDocDesc() {
		return this.docDesc;
	}

	public void setDocDesc(String docDesc) {
		this.docDesc = docDesc;
	}

	public String getDocBm() {
		return this.docBm;
	}

	public void setDocBm(String docBm) {
		this.docBm = docBm;
	}

	public Integer getFormId() {
		return this.formId;
	}

	public void setFormId(Integer formId) {
		this.formId = formId;
	}

	public String getExpr() {
		return this.expr;
	}

	public void setExpr(String expr) {
		this.expr = expr;
	}

	public Integer getBeginNum() {
		return this.beginNum;
	}

	public void setBeginNum(Integer beginNum) {
		this.beginNum = beginNum;
	}

	public Integer getNumLength() {
		return numLength;
	}

	public void setNumLength(Integer numLength) {
		this.numLength = numLength;
	}

	/**
	 * 功能：构造文号,
	 * @param categoryName:流程分类名称
	 * @return
	 * @throws   
	 */
	public String getWenhao(String userName,String groupName,String categoryName){
		String result = null;
		if(this.expr!=null){
			Calendar calendar = Calendar.getInstance();
			result = this.expr;
			result = result.replaceAll("\\{F\\}", this.getDoctypeName());
			
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
			if(numLength == 0){
				result = result.replaceAll("\\{N\\}", beginNum+"");
			}else{
				String allStr = "100000000000000000"+beginNum;
				allStr = allStr.substring(allStr.length()-numLength);
				result = result.replaceAll("\\{N\\}", allStr);
			}
			if(this.expr.contains("{N}")){
				DocumentTypeService documentTypeService = (DocumentTypeService) SpringUtil.getBean("documentTypeService");
				documentTypeService.updateBeginNum(this.doctypeId);
			}
		}
		return result;
	}

	public Integer getDocTemplateId() {
		return docTemplateId;
	}

	public void setDocTemplateId(Integer docTemplateId) {
		this.docTemplateId = docTemplateId;
	}

	public Integer getGongwenType() {
		if(gongwenType == null){
			return 1;
		}
		return gongwenType;
	}

	public void setGongwenType(Integer gongwenType) {
		this.gongwenType = gongwenType;
	}
	
}