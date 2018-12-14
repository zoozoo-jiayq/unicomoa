package cn.com.qytx.cbb.jbpmApp.service.mobile.htmlElement;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 下午6:35:59 
 * 修改日期：下午6:35:59 
 * 修改列表：
 */
public   class HtmlElement {
	
	//审批控件类型
	public final static  String ELEMENT_TYPE_APPROVE = "approve";
	//计算控件类型
	public final static String ELEMENT_TYPE_CACU = "cacu";
	//日历类型
	public final static String ELEMENT_TYPE_CALENDAR = "calendar";
	//复选
	public final static String ELEMENT_TYPE_CHECKBOX = "checkbox";
	//选择部门
	public final static String ELEMENT_TYPE_GROUP = "group";
	//单行输入框
	public final static String ELEMENT_TYPE_INPUT = "text";
	//单选
	public final static String ELEMENT_TYPE_RADIO = "radio";
	//阅读控件
	public final static String ELEMENT_TYPE_READERS = "readers";
	//下拉菜单控件
	public final static String ELEMENT_TYPE_SELECT = "select";
	//多行输入框
	public final static String ELEMENT_TYPE_TEXTAREA = "textarea";
	//选择人员
	public final static String ELEMENT_TYPE_USERS = "users";
	
	//明细控件
	public final static String ELEMENT_TYPE_DETAIL = "detail";
	
	public HtmlElement() {
	    super();
	    // TODO Auto-generated constructor stub
    }

	public HtmlElement(String htmlType) {
	    super();
	    this.htmlType = htmlType;
    }

	//明细控件的子控件
	private List<HtmlElement> details = new ArrayList<HtmlElement>();//明细控件的属性，存储子控件
	private String parentName;//子控件的属性，用来标记所属的明细控件
	
	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public List<HtmlElement> getDetails() {
		return details;
	}

	public void setDetails(List<HtmlElement> details) {
		this.details = details;
	}
	
	

	//属性ID
	private int propertyId;
	
	//控件的中文名字
	private String cnName;
	
	//控件的属性
	private String name;
	
	//控件的值,（复选框被选中的值是checked）
	private String value;
	
	//控件的权限，能否编辑,true能编辑，false不能编辑
	private boolean canEdit = true;
	
	//计算控件的表达式
	private String expr;
	
	private String htmlType;
	
	private List<String> radios;
	
	private List<String> options ;
	
	//数据类型,number/string,两种类型
	private String contentType;
	
	//是否必填，true必填，false不必填
	private boolean required = false;

	//日期范围控件计算表达式
	private String calendarRangeExpr;
	
	//日期范围控件的标记 start/end
	private String calendarRangeFlag;
	
	//日期范围控件对应的另一个控件
	private String other;
	
	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public String getCalendarRangeFlag() {
		return calendarRangeFlag;
	}

	public void setCalendarRangeFlag(String calendarRangeFlag) {
		this.calendarRangeFlag = calendarRangeFlag;
	}

	public String getCalendarRangeExpr() {
		return calendarRangeExpr;
	}

	public void setCalendarRangeExpr(String calendarRangeExpr) {
		this.calendarRangeExpr = calendarRangeExpr;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	private String dateFormat;//日期格式
	
	private String defaltValue;//默认值
	
	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public String getDefaltValue() {
		return defaltValue;
	}

	public void setDefaltValue(String defaltValue) {
		this.defaltValue = defaltValue;
	}

	public void addOptions(String str){
		if(options==null){
			options = new ArrayList<String>();
		}
		this.options.add(str);
	}
	
	public List<String> getOptions() {
		return options;
	}

	public void setOptions(List<String> options) {
		this.options = options;
	}

	public void addRadios(String str){
		if(this.radios == null){
			this.radios = new ArrayList<String>();
		}
		this.radios.add(str);
	}
	
	public List<String> getRadios() {
		return radios;
	}

	public void setRadios(List<String> radios) {
		this.radios = radios;
	}

	public String getExpr() {
		return expr;
	}

	public void setExpr(String expr) {
		this.expr = expr;
	}

	public String getHtmlType() {
		return htmlType;
	}

	public void setHtmlType(String htmlType) {
		this.htmlType = htmlType;
	}

	public String getCnName() {
		return cnName;
	}

	public void setCnName(String cnName) {
		this.cnName = cnName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isCanEdit() {
		return canEdit;
	}

	public void setCanEdit(boolean canEdit) {
		this.canEdit = canEdit;
	}


	public int getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(int propertyId) {
		this.propertyId = propertyId;
	}
	
}
